/**
 * 
 */
package net.geant.nsicontest.reservation.lsm;

import javax.xml.ws.Holder;

import org.ogf.schemas.nsi._2013._12.connection.types.LifecycleStateEnumType;
import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;

/**
 * The reservation has exceeded scheduled end time.
 */
final class EndTime extends LifecycleState {

	EndTime(LifecycleStateMachine lsm) { super(lsm); }

	@Override
	public LifecycleStateEnumType asNsiState() { return LifecycleStateEnumType.PASSED_END_TIME; }
	
	@Override
	public void terminate(final Holder<CommonHeaderType> header) {
		
		lsm.switchState(lsm.TERMINATING, header);	
	}
}
