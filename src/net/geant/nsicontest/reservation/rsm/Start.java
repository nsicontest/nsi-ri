/**
 * 
 */
package net.geant.nsicontest.reservation.rsm;

import javax.xml.ws.Holder;

import org.ogf.schemas.nsi._2013._12.connection.types.ReservationStateEnumType;
import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;


/**
 * A steady state for the reservation state machine in which a reservation is created and committed.
 */
final class Start extends ReservationState {

	Start(ReservationStateMachine rsm) { super(rsm); }

	@Override
	public ReservationStateEnumType asNsiState() { return ReservationStateEnumType.RESERVE_START; }
	
	@Override
	public void reserve(final Holder<CommonHeaderType> header) {
			
		rsm.switchState(rsm.CHECKING, header);		
	}
}
