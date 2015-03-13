/**
 * 
 */
package net.geant.nsicontest.reservation.rsm;

import javax.xml.ws.Holder;

import net.geant.nsicontest.core.NsiHeader;
import net.geant.nsicontest.reservation.Responder;

import org.ogf.schemas.nsi._2013._12.connection.types.ReservationStateEnumType;
import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;

/**
 * A transient state modeling the act of aborting a pending reservation modify request.
 */
final class Aborting extends ReservationState {

	Aborting(ReservationStateMachine rsm) {	super(rsm); }

	@Override
	public ReservationStateEnumType asNsiState() { return ReservationStateEnumType.RESERVE_ABORTING; }
	
	@Override
	public void enter(final Holder<CommonHeaderType> header) {
		
		if (rsm.getPreviousState() == rsm.HELD) {
			
			rsm.getProvider().nrmAbort();
		}
				
		new Responder(NsiHeader.getReplyTo(header)).reserveAbortConfirmed(header, rsm.getProvider().getConnectionId());
		rsm.switchState(rsm.START, header);
	}
}
