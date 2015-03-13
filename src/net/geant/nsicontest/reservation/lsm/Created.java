/**
 * 
 */
package net.geant.nsicontest.reservation.lsm;

import javax.xml.ws.Holder;

import org.ogf.schemas.nsi._2013._12.connection.types.LifecycleStateEnumType;
import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;

/**
 * A steady state for the lifecycle state machine and the initial state after a reservation has been committed.
 */
final class Created extends LifecycleState {

	Created(LifecycleStateMachine lsm) { super(lsm); }

	@Override
	public LifecycleStateEnumType asNsiState() { return LifecycleStateEnumType.CREATED; }
	
	@Override
	public void terminate(final Holder<CommonHeaderType> header) {
		
		lsm.switchState(lsm.TERMINATING, header);		
	}
	
	@Override
	public void endOfTime(final Holder<CommonHeaderType> header) {
		
		lsm.switchState(lsm.END_TIME, header);
	}
	
	@Override
	public void forcedEnd(final Holder<CommonHeaderType> header) {
				
		lsm.switchState(lsm.FAILED, header);
	}
}
