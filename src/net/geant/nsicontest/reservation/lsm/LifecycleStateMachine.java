/**
 * 
 */
package net.geant.nsicontest.reservation.lsm;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.ws.Holder;

import net.geant.nsicontest.reservation.Lifecyclable;
import net.geant.nsicontest.reservation.Provider;

import org.apache.log4j.Logger;
import org.ogf.schemas.nsi._2013._12.connection.types.LifecycleStateEnumType;
import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;

/**
 * Follows State Machine pattern to implement the Lifecyclable interface.
 */
public class LifecycleStateMachine implements Lifecyclable {
	
	static Logger log = Logger.getLogger(LifecycleStateMachine.class);
		
	final LifecycleState CREATED;
	final LifecycleState FAILED;
	final LifecycleState END_TIME;
	final LifecycleState TERMINATING;
	final LifecycleState TERMINATED;
	
	private LifecycleState current, previous;
	private Timer sentinel;
	private Provider provider;
	
	public LifecycleStateMachine(Provider provider) {
		
		this.provider = provider;
		
		previous = current = CREATED = new Created(this);
		FAILED = new Failed(this);
		END_TIME = new EndTime(this);
		TERMINATING = new Terminating(this);
		TERMINATED = new Terminated(this);
	}
	
	LifecycleState getPreviousState() { 
		return previous; 
	}
	
	synchronized void switchState(final LifecycleState next, final Holder<CommonHeaderType> header) {
		
		log.info(String.format("%s -> %s", current, next));
		previous = current;
		current = next;
		current.enter(header);	        
	}
	
	synchronized public void setSentinel() {
		
		if (provider.isFinished()) 
			return;
				
		TimerTask sentinelTask = new TimerTask() {				
			@Override
			public void run() {					
				clearSentinel("self expired");
				switchState(END_TIME, null);						
				provider.nrmTerminate(false);
			}
		};
			
		long delay = provider.getEndTime().getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
		assert delay > 0;
		sentinel = new Timer("sentinel");
		sentinel.schedule(sentinelTask, delay);
		log.info(String.format("end-time will take place in %s seconds", delay / 1000));
	}
	
	synchronized public void clearSentinel(String reason) {
		
		if (sentinel != null) {
			sentinel.cancel();
			sentinel = null;
			log.debug("sentinel timer cleared - " + reason);
		}		
	}
	
	Provider getProvider() { return provider; }
	
	public boolean isTerminated() { return current != CREATED; }	
	
	public LifecycleStateEnumType asNsiState() { return current.asNsiState(); }
	
	@Override
	public String toString() { return current.toString(); }
	
	@Override
	public void terminate(Holder<CommonHeaderType> header) {

		current.terminate(header);
	}

	@Override
	public void endOfTime(Holder<CommonHeaderType> header) {
		
		current.endOfTime(header);
	}

	@Override
	public void forcedEnd(Holder<CommonHeaderType> header) {
		
		current.forcedEnd(header);
	}	
}
