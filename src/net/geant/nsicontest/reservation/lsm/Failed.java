/**
 * 
 */
package net.geant.nsicontest.reservation.lsm;

import javax.xml.ws.Holder;

import org.ogf.schemas.nsi._2013._12.connection.types.LifecycleStateEnumType;
import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;

/**
 * A steady state for the lifecycle state machine that is reached if a forcedEnd error is received from a uPA.
 */
final class Failed extends LifecycleState {

	Failed(LifecycleStateMachine lsm) { super(lsm);	}

	@Override
	public LifecycleStateEnumType asNsiState() { return LifecycleStateEnumType.FAILED; }
	
	@Override
	public void enter(final Holder<CommonHeaderType> header) {
		
		lsm.clearSentinel("forced end received");

		if (lsm.getProvider().isActivated())
			lsm.getProvider().nrmDeactivate(header);				
	}
	
	@Override
	public void terminate(final Holder<CommonHeaderType> header) {
		
		lsm.switchState(lsm.TERMINATING, header);	
	}
}
