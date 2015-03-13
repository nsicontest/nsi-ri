/**
 * 
 */
package net.geant.nsicontest.reservation.lsm;

import org.ogf.schemas.nsi._2013._12.connection.types.LifecycleStateEnumType;

/**
 * A steady state for the lifecycle state machine that is reached when the reservation is terminated by the uRA.
 */
final class Terminated extends LifecycleState {

	Terminated(LifecycleStateMachine lsm) { super(lsm); }

	@Override
	public LifecycleStateEnumType asNsiState() { return LifecycleStateEnumType.TERMINATED; }
	
	// ultimate state, once it gets here it stays forever
}
