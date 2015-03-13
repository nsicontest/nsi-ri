/**
 * 
 */
package net.geant.nsicontest.reservation.rsm;

import javax.xml.ws.Holder;

import org.ogf.schemas.nsi._2013._12.connection.types.ReservationStateEnumType;
import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;

/**
 * A steady state for the reservation state machine in which the held resources have been locally timed out on a uPA, 
 * resulting in a transition from the ReserveHeld to ReserveTimeout state.
 */
final class Timeout extends ReservationState {

	public Timeout(ReservationStateMachine rsm) { super(rsm); }

	@Override
	public ReservationStateEnumType asNsiState() { return ReservationStateEnumType.RESERVE_TIMEOUT; }

	@Override
	public void reserveCommit(final Holder<CommonHeaderType> header) { 
	
		rsm.switchState(rsm.COMMITTING, header);		
	}	
	
	@Override
	public void reserveAbort(final Holder<CommonHeaderType> header) {
		
		rsm.switchState(rsm.ABORTING, header);
	}
}
