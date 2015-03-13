/**
 * 
 */
package net.geant.nsicontest.reservation.rsm;


import java.util.Calendar;

import javax.xml.ws.Holder;

import net.geant.nsicontest.core.NsiError;
import net.geant.nsicontest.core.NsiErrorId;
import net.geant.nsicontest.core.NsiHeader;
import net.geant.nsicontest.reservation.Coordinator;
import net.geant.nsicontest.reservation.Provider;
import net.geant.nsicontest.reservation.Responder;

import org.ogf.schemas.nsi._2013._12.connection._interface.ServiceException;
import org.ogf.schemas.nsi._2013._12.connection.types.ReservationStateEnumType;
import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;

/**
 * A transient state modeling the act of committing a held set of reservation resources.
 */
final class Committing extends ReservationState {
	
	Committing(ReservationStateMachine rsm) { super(rsm); }

	@Override
	public ReservationStateEnumType asNsiState() { return ReservationStateEnumType.RESERVE_COMMITTING; }
	
	@Override
	public void enter(final Holder<CommonHeaderType> header) {
		
		Provider prov = rsm.getProvider();
		
		// if coming from TIMEOUT state, just send commit failed
		if (rsm.getPreviousState() == rsm.TIMEOUT) {						
			ServiceException ex = NsiError.create(null, Coordinator.getProviderNSA(), NsiErrorId.INVALID_TRANSITION);
			new Responder(NsiHeader.getReplyTo(header)).reserveCommitFailed(header, prov.getConnectionId(), prov.getStates(), ex.getFaultInfo()); 
			rsm.switchState(rsm.START, header);
			return;
		}
		
		// re-check timers taking into account time spent in HELD state
		// not checking start time (can be before current time)
		
		Calendar endTime = !prov.isReserved() ? prov.getEndTime() : prov.getParameters().getEndTime();
		if (endTime != null && endTime.before(Calendar.getInstance())) {
			ServiceException ex = NsiError.create(null, Coordinator.getProviderNSA(), NsiErrorId.INTERNAL_ERROR);
			new Responder(NsiHeader.getReplyTo(header)).reserveCommitFailed(header, prov.getConnectionId(), prov.getStates(), ex.getFaultInfo()); 
			rsm.switchState(rsm.START, header);
			return;
		}
		
		// make sure nrm acknowledges it
		try {
			
			prov.nrmCommit();
			boolean delivered = new Responder(NsiHeader.getReplyTo(header)).reserveCommitConfirmed(header, prov.getConnectionId());
			if (!delivered && Coordinator.isFailIfResponseNotDelivered()) {				
				prov.nrmAbort();
				// do not bother with sending commit failed				
			} else {
				// set reserved flag, or take over modify parameters
				prov.setCommited(header);
			}			
						
		} catch (Exception e) {
		    log.error("Error during commit: " + e.getMessage());
            log.debug("Error info: ", e);
			ServiceException ex = NsiError.create(null, Coordinator.getProviderNSA(), NsiErrorId.INTERNAL_NRM_ERROR);
			new Responder(NsiHeader.getReplyTo(header)).reserveCommitFailed(header, prov.getConnectionId(), prov.getStates(), ex.getFaultInfo()); 
		}
		
		// regardless of everything, always go to START state
		rsm.switchState(rsm.START, header);
	}
}
