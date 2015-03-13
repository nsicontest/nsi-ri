/**
 * 
 */
package net.geant.nsicontest.reservation.lsm;

import javax.xml.ws.Holder;

import net.geant.nsicontest.reservation.Lifecyclable;
import net.geant.nsicontest.reservation.State;

import org.apache.log4j.Logger;
import org.ogf.schemas.nsi._2013._12.connection.types.LifecycleStateEnumType;
import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;

/**
 * Base class for Lifecycle states - implements the Lifecycleable interface with default behavior of
 * throwing UnsupportedOperationException. 
 */
abstract class LifecycleState extends State<LifecycleStateEnumType> implements Lifecyclable {
	
	protected LifecycleStateMachine lsm;
	protected Logger log;
	
	protected LifecycleState(LifecycleStateMachine lsm) {
		assert lsm != null;
		this.lsm = lsm;	
		this.log = LifecycleStateMachine.log;
	}

	@Override
	public void terminate(final Holder<CommonHeaderType> header) {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void endOfTime(final Holder<CommonHeaderType> header) {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void forcedEnd(final Holder<CommonHeaderType> header) {
		throw new UnsupportedOperationException();
	}
}
