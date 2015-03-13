/**
 * 
 */
package net.geant.nsicontest.reservation.rsm;

import javax.xml.ws.Holder;

import org.ogf.schemas.nsi._2013._12.connection.types.ReservationStateEnumType;
import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;

/**
 * A steady state for the reservation state machine in which the initial reservation or a subsequent modification request has successfully 
 * had the request resources reserved, but has not yet been committed.
 */
final class Held extends ReservationState {

	Held(ReservationStateMachine rsm) { super(rsm); }
	
	@Override
	public ReservationStateEnumType asNsiState() { return ReservationStateEnumType.RESERVE_HELD; }
	
	@Override
	public void reserveCommit(final Holder<CommonHeaderType> header) { 

		rsm.clearHeldTimeout("reserve commit arrived");
		rsm.switchState(rsm.COMMITTING, header);		
	}	
	
	@Override
	public void reserveAbort(final Holder<CommonHeaderType> header) {

		rsm.clearHeldTimeout("reserve abort arrived");
		rsm.switchState(rsm.ABORTING, header);
	}
	
	@Override
	public void timeout(final Holder<CommonHeaderType> header) {
		
		rsm.clearHeldTimeout("timeout");
		rsm.switchState(rsm.TIMEOUT, header);
	}
}
