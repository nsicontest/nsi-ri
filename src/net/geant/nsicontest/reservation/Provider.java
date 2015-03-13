/**
 * 
 */
package net.geant.nsicontest.reservation;

import java.util.Calendar;
import java.util.List;

import javax.xml.ws.Holder;

import net.geant.nsicontest.core.NsiError;
import net.geant.nsicontest.core.NsiErrorId;
import net.geant.nsicontest.core.NsiHeader;
import net.geant.nsicontest.core.Range;
import net.geant.nsicontest.nrm.Nrm;
import net.geant.nsicontest.nrm.NrmException;
import net.geant.nsicontest.nrm.ReserveOutcome;
import net.geant.nsicontest.nrm.ReserveParameters;
import net.geant.nsicontest.reservation.lsm.LifecycleStateMachine;
import net.geant.nsicontest.reservation.psm.ProvisionStateMachine;
import net.geant.nsicontest.reservation.rsm.ReservationStateMachine;
import net.geant.nsicontest.topology.BidirectionalPort;
import net.geant.nsicontest.topology.GlobalTopology;
import net.geant.nsicontest.topology.path.DepthFirstPce;
import net.geant.nsicontest.topology.path.PathResult;
import net.geant.nsicontest.topology.path.Pce;

import org.apache.log4j.Logger;
import org.ogf.schemas.nsi._2013._12.connection._interface.ServiceException;
import org.ogf.schemas.nsi._2013._12.connection.types.ConnectionStatesType;
import org.ogf.schemas.nsi._2013._12.connection.types.DataPlaneStatusType;
import org.ogf.schemas.nsi._2013._12.connection.types.EventEnumType;
import org.ogf.schemas.nsi._2013._12.connection.types.QueryRecursiveResultType;
import org.ogf.schemas.nsi._2013._12.connection.types.QuerySummaryResultType;
import org.ogf.schemas.nsi._2013._12.connection.types.ReservationConfirmCriteriaType;
import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;

/**
 * Implements Provider role.
 */
final public class Provider extends Reservation implements Reservable, Provisionable, Lifecyclable {
	
	final static private Logger log = Logger.getLogger(Provider.class);
	
	private ReservationStateMachine reservationStateMachine;
	private ProvisionStateMachine provisionStateMachine;
	private LifecycleStateMachine lifecycleStateMachine;
	private Long notificationId;
	private Long resultId;
	private boolean reserved;
	private boolean activated;
	private P2PServiceParameters parameters; // candidate parameters that are assigned during CHECKING state	
	private PathResult path;                 // set during nrmReserve
	private Requester requester;	   	     // created during nrmReserve, if multi domain request	
	private ReserveOutcome reservedParameters; // set during nrmReserve
	private Nrm nrm;
	private StringBuffer stateChanges = new StringBuffer(); // stores history of state changes 

	public Provider(String connectionId, String globalReservationId, String description, Integer version, Calendar startTime, Calendar endTime) {
		
		super(connectionId, globalReservationId, description, version, startTime, endTime);
		
		reservationStateMachine = new ReservationStateMachine(this);
		provisionStateMachine = new ProvisionStateMachine(this);
		lifecycleStateMachine = new LifecycleStateMachine(this);
		reserved = activated = false;
		notificationId = resultId = 0L;		
	}
	
	public void setNrm(Nrm nrm) {
		this.nrm = nrm;
	}
	
	public Nrm getNrm() {
		return nrm;
	}
	
	public boolean isReserved() {
		return reserved;
	}

	public boolean isActivated() {
		return activated;
	}

	public P2PServiceParameters getParameters() {
		return parameters;
	}

	public void setParameters(P2PServiceParameters parameters) {
		this.parameters = parameters;
	}
	
	synchronized public Long getNextNotificationId() {
		
		return ++notificationId;
	}
	
	synchronized public Long getNextResultId() {
		
		return ++resultId;
	}
	
	// to be called from lsm
	public void clearActivation() {
		
		provisionStateMachine.clearActivation("terminate arrived");
	}
	
	public boolean isTerminated() {
		
		return lifecycleStateMachine.isTerminated();
	}
	
	synchronized public void addStateChange(String change) {
		
		stateChanges.append(change + '\n');
	}
	
	public String getStateChanges() {
		
		return stateChanges.toString(); 
	}
	
	/**
	 * Returns ReservationConfirmCriteriaType with fully specified STPs.
	 * Fully specified STP is a combination of requested STP with concrete vlan number.
	 * @return
	 * @throws Exception
	 */
	public ReservationConfirmCriteriaType toReservationConfirmCriteriaType() throws Exception {

		if (reservedParameters == null) throw new Exception("no nrm reservation yet");		
		if (path == null) throw new Exception("path computation not run");
		if (path.isInvalid()) throw new Exception("path with error");
			
		String src = String.format("%s?vlan=%s", path.getSource().getId(), reservedParameters.getIngressVlan());
		String dst = String.format("%s?vlan=%s", path.getDest().getId(), reservedParameters.getEgressVlan());
		
		return super.toReservationConfirmCriteriaType(src, dst);
	}
	
	// to be called from comitting state
	public void setCommited(final Holder<CommonHeaderType> header) {
	
		if (!reserved) {
		
			reserved = true;
			log.debug("reserved flag set");
			lifecycleStateMachine.setSentinel();			
						
		} else {

			log.debug("modifying reservation");
			startTime = parameters.getStartTime();
			endTime = parameters.getEndTime();
			version = parameters.getVersion();
			serviceType = parameters.getServiceType();
			bidirectional = parameters.isBidirectional();
			symmetricPath = parameters.getSymmetricPath();
			capacity = parameters.getCapacity();
			requestedSourceStp = parameters.getSourceSTP();
			requestedDestStp = parameters.getDestSTP();
			
			// restart timers
			provisionStateMachine.clearActivation("modify");
			if (!isActive() && provisionStateMachine.isProvisioned())
				provisionStateMachine.setActivation(header);
			
			lifecycleStateMachine.clearSentinel("modify");			
			if (!isFinished())
				lifecycleStateMachine.setSentinel();
			
			// setup data plane accordingly
			if (!isActive() && activated) { // tear down data plane, will be brought up by activation timer				
				nrmDeactivate(header);				
			}
			
			if (isActive() && !activated) { // bring up data plane now
				nrmActivate(header);				
			}
			log.debug("modify parameters taken over");
		}		
		setParameters(null);
		log.info("commited reservation: " + this);
		if (startTime == null) {
			log.info("start time reached");
		} else {
			long diff = (startTime.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) / 1000L;
			log.info(String.format("start time will be reached in %s seconds", diff));
		}
	}
	
	public void cleanup() {
		
		reservationStateMachine.clearHeldTimeout("cleanup");
		provisionStateMachine.clearActivation("cleanup");
		if (!lifecycleStateMachine.isTerminated())
			lifecycleStateMachine.terminate(null);
	}
		
	
	//
	// nrm specific 
	//
	
	public void nrmReserve() throws Exception {
		
		String src, dst;
		
		if (!reserved) { 			
			src = requestedSourceStp;
			dst = requestedDestStp;			
		} else {
			src = parameters.getSourceSTP();
			dst = parameters.getDestSTP();
		}

		// path computation
		log.info("path computation:");
		log.info("local nsaId: " + GlobalTopology.getInstance().getLocalNsaId());
		log.info("sourceStp: " + src);
		log.info("destStp: " + dst);
		
		Pce pce = new DepthFirstPce();
		path = pce.search(GlobalTopology.getInstance().getAllNsas(), GlobalTopology.getInstance().getLocalNsaId(), src, dst);
		
		if (path.isInvalid()) {
			log.info("path computation error - " + path.getErrorMessage());
			throw new Exception("path computation error - " + path.getErrorMessage());
		}
				
		
		BidirectionalPort nrmSource;
		BidirectionalPort nrmDest;
				
		// if aggregator needs to be used, do it before doing anything in local domain		
		if (!path.isLocal()) {
			
			if (path.getPaths().isEmpty())
				throw new Exception("no paths found");
			
			List<BidirectionalPort> ports = path.getPaths().get(0);
			
			nrmSource = path.getSource();
			nrmDest = ports.get(0);
			
			BidirectionalPort ingress = ports.get(1);
			String link = ingress.getNsa().getService().getLink();
			String nsaId = ingress.getNsa().getId();
			
			// compose source stp
			String ingressStp = ingress.getId() + "?vlan=" + path.getSourceVlans().toString();
			
			requester = new SynchronousRequster(null, globalReservationId, description, version, startTime, endTime);
			requester.setP2PServiceBase(serviceType, ingressStp, dst, capacity, activated, symmetricPath, ero);
			requester.setProviderClient(link, nsaId, Coordinator.getReplyTo(), Coordinator.getProviderNSA());
			
			try {
				requester.reserve();
				log.info("aggregator assigned connectionId: " + requester.getConnectionId());
			} catch (Exception e) {
				String err = String.format("aggregator to %s error: %s", link, e.getMessage());
				log.info(err);
				throw new Exception(err);
			}
			
		} else {
					
			nrmSource = path.getSource();
			nrmDest = path.getDest();			
		}
		
		// pass reserve parameters down to nrm
		try {
						
			String inStp = nrmSource.getDecomposed().getLocalId();
			Range inVlan = path.getSourceVlans();
			
			String outStp = nrmDest.getDecomposed().getLocalId();
			Range outVlan = path.getDestVlans();
			
			log.info("nrm reserve:");
			log.info(String.format("source: %s,  vlans: %s", inStp, inVlan));
			log.info(String.format("dest: %s,  vlans: %s", outStp, outVlan));
						
			boolean symmetric = symmetricPath != null ? symmetricPath : false;
			ReserveParameters params = new ReserveParameters(startTime, endTime, inStp, inVlan, outStp, outVlan, capacity, bidirectional, symmetric);
			reservedParameters = nrm.reserve(connectionId, params);
			log.info(String.format("vlans reserved by nrm: ingress: %s, egress: %s", reservedParameters.getIngressVlan(), reservedParameters.getEgressVlan()));
			
		} catch (Exception e) {			
			log.info("nrm reserve error - " + e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	public void nrmCommit() throws Exception { 

		if (path.isInvalid())
			return;
		
		if (!path.isLocal()) {			
			requester.reserveCommit();
		}
		try {
			nrm.commit(connectionId);
		} catch (NrmException e) {			
			log.info("nrm commit error - " + e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	public void nrmAbort() {
		
		if (path.isInvalid())
			return;
		
		if (!path.isLocal()) {
						
			try {
				requester.reserveAbort();
			} catch (Exception e) {
				log.info("aggregator abort error - " + e.getMessage());
			}
		}
		nrm.abort(connectionId);
	}
	
	public void provision() throws Exception {
	
		if (path.isInvalid())
			return;
		
		if (!path.isLocal()) {			
			requester.provision();
		}
	}
	
	public void release() throws Exception {
	
		if (path.isInvalid())
			return;
		
		if (!path.isLocal()) {			
			requester.release();
		}
	}
	
	public void nrmTerminate(boolean requested) {
		
		if (path.isInvalid())
			return;
		
		if (requested) {
			if (!path.isLocal()) {		
				
				try {
					requester.terminate();
				} catch (Exception e) {
					log.info("aggregator terminate error - " + e.getMessage());
				}
			}
		}
		
		nrm.terminate(connectionId);
	}
	
	public void nrmActivate(Holder<CommonHeaderType> header) {
		
		if (activated)
			log.info(connectionId + " - attempt to reactivate data plane (already activated)");
		
		Long notId = getNextNotificationId();
		
		try {
			
			nrm.activate(connectionId);
			activated = true;
			log.info(String.format("%s activated data plane", connectionId));
			if (header != null) // can be null if reservation self expired
				new Responder(NsiHeader.getReplyTo(header)).dataPlaneStateChanged(header, connectionId, notId, activated, version, true);
			
		} catch (Exception e) {			
			log.error(String.format("%s data plane activation failed - " + e.getMessage()));
            log.debug("Error info: ", e);
            ServiceException err = NsiError.create("deactivation failed", Coordinator.getProviderNSA(), NsiErrorId.INTERNAL_NRM_ERROR);
			if (header != null) // can be null if reservation self expired
				new Responder(NsiHeader.getReplyTo(header)).errorEvent(header, connectionId, notId, EventEnumType.ACTIVATE_FAILED,
						connectionId, Coordinator.getProviderNSA(), null, err.getFaultInfo());			
		}		
	}
	
	public void nrmDeactivate(Holder<CommonHeaderType> header) {
		
		if (!activated)
			log.info(connectionId + " - attempt to deactivate data plane (already deactivated)");
		
		Long notId = getNextNotificationId();
		
		try {
			
			nrm.deactivate(connectionId);
			activated = false;
			log.info(String.format("%s deactivated data plane", connectionId));
			if (header != null) // can be null if reservation self expired
				new Responder(NsiHeader.getReplyTo(header)).dataPlaneStateChanged(header, connectionId, notId, activated, version, true);
			
		} catch (Exception e) {			
			log.error(String.format("%s data plane deactivation failed - " + e.getMessage()));
            log.debug("Error info: ", e);
			ServiceException err = NsiError.create("deactivation failed", Coordinator.getProviderNSA(), NsiErrorId.INTERNAL_NRM_ERROR);
			if (header != null) { // can be null if reservation self expired
				new Responder(NsiHeader.getReplyTo(header)).errorEvent(header, connectionId, notId, EventEnumType.DEACTIVATE_FAILED,						
						connectionId, Coordinator.getProviderNSA(), null, err.getFaultInfo());
			} 
		}
	}
	
	//
	// public api exposed for Coordinator
	//
		
	private void invalidTransition(final Holder<CommonHeaderType> header, String method, String state) {
		
		String s = String.format("invalid transition for %s while in %s state", method, state); 
		log.info(s);
	
		Coordinator.scheduleTask(new Runnable() {
			
			@Override
			public void run() {
				
				ServiceException ex = NsiError.create(null, Coordinator.getProviderNSA(), NsiErrorId.INVALID_TRANSITION);
				new Responder(NsiHeader.getReplyTo(header)).error(header, ex.getFaultInfo());				
			}
		});
	}
	
	public DataPlaneStatusType getDataPlaneStatus() {
		
		DataPlaneStatusType status = new DataPlaneStatusType();
		status.setActive(activated);
		status.setVersion(version);
		status.setVersionConsistent(true); // for multi domain reservations, get this value out of Aggregator		
		return status;
	}
		
	public ConnectionStatesType getStates() {
		
		ConnectionStatesType states = new ConnectionStatesType();		
		states.setReservationState(reservationStateMachine.asNsiState());
		states.setProvisionState(provisionStateMachine.asNsiState());
		states.setLifecycleState(lifecycleStateMachine.asNsiState());
		states.setDataPlaneStatus(getDataPlaneStatus());		
 		return states;
	}
	
	public QuerySummaryResultType getQuerySummary(String requesterNSA) {
		
		QuerySummaryResultType query = new QuerySummaryResultType();
		query.setConnectionId(connectionId);
		query.setConnectionStates(getStates());
		query.setDescription(description);
		query.setGlobalReservationId(globalReservationId);
		query.setNotificationId(notificationId);
		query.setResultId(resultId);
		query.setRequesterNSA(requesterNSA);				
		return query;
	}
	
	public QueryRecursiveResultType getQueryRecursive(String requesterNSA) {
		
		QueryRecursiveResultType query = new QueryRecursiveResultType();
		query.setConnectionId(connectionId);
		query.setConnectionStates(getStates());
		query.setDescription(description);
		query.setGlobalReservationId(globalReservationId);
		query.setNotificationId(notificationId);
		query.setResultId(resultId);
		query.setRequesterNSA(requesterNSA);		
		return query;
	}

	@Override
	synchronized public void terminate(final Holder<CommonHeaderType> header) {
		
		if (isTerminated()) {
			invalidTransition(header, "terminate", lifecycleStateMachine.toString());
			return;
		}
				
		try {			
			lifecycleStateMachine.terminate(header);			
		} catch (UnsupportedOperationException e) {			
			invalidTransition(header, "terminate", lifecycleStateMachine.toString());			
		} catch (Exception e) { // should never happen			
			log.info(String.format("ERROR while in state %s - %s", lifecycleStateMachine, e.getMessage()));
		}
	}

	@Override
	synchronized public void endOfTime(Holder<CommonHeaderType> header) {
		
		try {			
			lifecycleStateMachine.endOfTime(header);						
		} catch (UnsupportedOperationException e) {			
			invalidTransition(header, "endOfTime", lifecycleStateMachine.toString());			
		} catch (Exception e) { // should never happen			
			log.info(String.format("ERROR while in state %s - %s", lifecycleStateMachine, e.getMessage()));
		}
	}

	@Override
	synchronized public void forcedEnd(Holder<CommonHeaderType> header) {
		
		try {			
			lifecycleStateMachine.forcedEnd(header);					
		} catch (UnsupportedOperationException e) {			
			invalidTransition(header, "forcedEnd", lifecycleStateMachine.toString());			
		} catch (Exception e) { // should never happen			
			log.info(String.format("ERROR while in state %s - %s", lifecycleStateMachine, e.getMessage()));
		}
	}

	@Override
	synchronized public void provision(Holder<CommonHeaderType> header) {
		
		if (isTerminated()) {
			invalidTransition(header, "provision", lifecycleStateMachine.toString());
			return;
		}
		
		try {			
			provisionStateMachine.provision(header);			
		} catch (UnsupportedOperationException e) {			
			invalidTransition(header, "provision", provisionStateMachine.toString());			
		} catch (Exception e) { // should never happen			
			log.info(String.format("ERROR while in state %s - %s", provisionStateMachine, e.getMessage()));
		}
	}

	@Override
	synchronized public void release(Holder<CommonHeaderType> header) {
		
		if (isTerminated()) {
			invalidTransition(header, "release", lifecycleStateMachine.toString());
			return;
		}
		
		try {			
			provisionStateMachine.release(header);			
		} catch (UnsupportedOperationException e) {			
			invalidTransition(header, "release", provisionStateMachine.toString());			
		} catch (Exception e) { // should never happen			
			log.info(String.format("ERROR while in state %s - %s", provisionStateMachine, e.getMessage()));
		}
	}

	@Override
	synchronized public void reserve(Holder<CommonHeaderType> header) {
		
		if (isTerminated()) {
			invalidTransition(header, "reserve", lifecycleStateMachine.toString());
			return;
		}
		
		try {			
			reservationStateMachine.reserve(header);
		} catch (UnsupportedOperationException e) {			
			invalidTransition(header, "reserve", reservationStateMachine.toString());			
		} catch (Exception e) { // should never happen			
			log.info(String.format("ERROR while in state %s - %s", reservationStateMachine, e.getMessage()));
		}
	}

	@Override
	synchronized public void reserveCommit(Holder<CommonHeaderType> header) {
		
		if (isTerminated()) {
			invalidTransition(header, "reserveCommit", lifecycleStateMachine.toString());
			return;
		}
		
		try {			
			reservationStateMachine.reserveCommit(header);
		} catch (UnsupportedOperationException e) {			
			invalidTransition(header, "reserveCommit", reservationStateMachine.toString());					
		} catch (Exception e) { // should never happen			
			log.info(String.format("ERROR while in state %s - %s", reservationStateMachine, e.getMessage()));
		}
	}

	@Override
	synchronized public void reserveAbort(Holder<CommonHeaderType> header) {

		if (isTerminated()) {
			invalidTransition(header, "reserveAbort", lifecycleStateMachine.toString());
			return;
		}
		
		try {			
			reservationStateMachine.reserveAbort(header);
		} catch (UnsupportedOperationException e) {			
			invalidTransition(header, "reserveAbort", reservationStateMachine.toString());					
		} catch (Exception e) { // should never happen			
			log.info(String.format("ERROR while in state %s - %s", reservationStateMachine, e.getMessage()));
		}
	}

	@Override
	synchronized public void timeout(Holder<CommonHeaderType> header) {
		
		try {			
			reservationStateMachine.timeout(header);
		} catch (UnsupportedOperationException e) {			
			invalidTransition(header, "timeout", reservationStateMachine.toString());			
		} catch (Exception e) { // should never happen			
			log.info(String.format("ERROR while in state %s - %s", reservationStateMachine, e.getMessage()));
		}
	}	
}
