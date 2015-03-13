/**
 * 
 */
package net.geant.nsicontest.reservation.rsm;

import javax.xml.ws.Holder;

import net.geant.nsicontest.core.NsiError;
import net.geant.nsicontest.core.NsiErrorId;
import net.geant.nsicontest.core.NsiHeader;
import net.geant.nsicontest.reservation.Coordinator;
import net.geant.nsicontest.reservation.Provider;
import net.geant.nsicontest.reservation.Responder;

import org.ogf.schemas.nsi._2013._12.connection._interface.ServiceException;
import org.ogf.schemas.nsi._2013._12.connection.types.ReservationConfirmCriteriaType;
import org.ogf.schemas.nsi._2013._12.connection.types.ReservationStateEnumType;
import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;

/**
 * A transient state modeling the act of checking the feasibility of a new reservation request, or a request to modify an existing reservation.
 */
final class Checking extends ReservationState {

	Checking(ReservationStateMachine rsm) {	super(rsm);	}

	@Override
	public ReservationStateEnumType asNsiState() { return ReservationStateEnumType.RESERVE_CHECKING; }
	
	private void fail(Holder<CommonHeaderType> header, ServiceException error) {
		
		Provider prov = rsm.getProvider();
		new Responder(NsiHeader.getReplyTo(header)).reserveFailed(header, prov.getConnectionId(), prov.getStates(), error.getFaultInfo());
		rsm.switchState(rsm.FAILED, header);
	}
	
	@Override
	public void enter(final Holder<CommonHeaderType> header) {
		
		Provider prov = rsm.getProvider();		
		
		// validate parameters
		int ver = prov.isReserved() ? prov.getVersion() : -1;
		ServiceException err = prov.getParameters().validate(Coordinator.getProviderNSA(), prov.getConnectionId(), ver, Coordinator.getServiceType());
		if (err != null) {
			// TODO better handling of this error
			log.info("parameter validation failed: " + err.getMessage());
			fail(header, err);
			return;
		}
		
		try {
				
			prov.nrmReserve();
			
		} catch (Exception e) {				
			ServiceException nrmError = NsiError.create("internal nrm error", Coordinator.getProviderNSA(), prov.getConnectionId(), 
					Coordinator.getServiceType(), NsiErrorId.INTERNAL_NRM_ERROR);
			fail(header, nrmError);
			return;
		}						
		
		// parameters validated and reservation booked within the nrm - send confirmation		
		ReservationConfirmCriteriaType confirm = null;
		try {
			confirm = prov.toReservationConfirmCriteriaType();
		} catch (Exception e) {
			// an xml serialization issue, cannot do anything about it
			log.info("error encountered while creating reserveCofirmed object - " + e.getMessage());
			ServiceException nrmError = NsiError.create("internal error", Coordinator.getProviderNSA(), prov.getConnectionId(), 
					Coordinator.getServiceType(), NsiErrorId.INTERNAL_ERROR);
			fail(header, nrmError);
			return;
		}
				
		boolean delivered = new Responder(NsiHeader.getReplyTo(header)).reserveConfirmed(header, prov.getConnectionId(), prov.getGlobalReservationId(), 
				prov.getDescription(), confirm);
		
		if (!delivered && Coordinator.isFailIfResponseNotDelivered()) {			
			// could not deliver response, return back resources and go to failed
			log.info("reserve was successful but could not deliver confirmation, aborting");
			prov.nrmAbort();
			rsm.switchState(rsm.FAILED, header);
			return;
		}
		
		// keep resources for some time (reserve_held_timeout in autobahn.properties) 
		rsm.setHeldTimeout(header);		
		rsm.switchState(rsm.HELD, header);				
	}
}
