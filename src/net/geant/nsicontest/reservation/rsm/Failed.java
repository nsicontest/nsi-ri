/**
 * 
 */
package net.geant.nsicontest.reservation.rsm;

import javax.xml.ws.Holder;

import org.ogf.schemas.nsi._2013._12.connection.types.ReservationStateEnumType;
import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;

/**
 * A steady state for the reservation state machine in which the initial reservation or a subsequent modification request has failed.
 */
final class Failed extends ReservationState {
		
	Failed(ReservationStateMachine rsm) { super(rsm); }

	@Override
	public ReservationStateEnumType asNsiState() { return ReservationStateEnumType.RESERVE_FAILED; }
	
	@Override
	public void reserveAbort(final Holder<CommonHeaderType> header) {
	
		rsm.switchState(rsm.ABORTING, header);		
	}
}
