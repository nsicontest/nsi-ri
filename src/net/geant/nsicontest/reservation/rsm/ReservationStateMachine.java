/**
 * 
 */
package net.geant.nsicontest.reservation.rsm;


import java.util.Timer;
import java.util.TimerTask;

import javax.xml.ws.Holder;

import net.geant.nsicontest.core.NsiHeader;
import net.geant.nsicontest.reservation.Coordinator;
import net.geant.nsicontest.reservation.Provider;
import net.geant.nsicontest.reservation.Reservable;
import net.geant.nsicontest.reservation.Responder;

import org.apache.log4j.Logger;
import org.ogf.schemas.nsi._2013._12.connection.types.ReservationStateEnumType;
import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;

/**
 * Follows State Machine pattern to implement the Reservable interface.
 */
final public class ReservationStateMachine implements Reservable {
	
	static Logger log = Logger.getLogger(ReservationStateMachine.class);
	
	final ReservationState START;
	final ReservationState CHECKING;
	final ReservationState HELD;
	final ReservationState COMMITTING;
	final ReservationState ABORTING;
	final ReservationState FAILED;
	final ReservationState TIMEOUT;
	
	private ReservationState current, previous;
	private Timer timeout;
	final private Provider provider;
	
	public ReservationStateMachine(Provider provider) {
		
		this.provider = provider;		
		
		previous = current = START = new Start(this);
		CHECKING = new Checking(this);
		HELD = new Held(this);
		COMMITTING = new Committing(this);
		ABORTING = new Aborting(this);
		FAILED = new Failed(this);
		TIMEOUT = new Timeout(this);		
	}
	
	ReservationState getPreviousState() { 
		return previous; 
	}
	
	synchronized void switchState(final ReservationState next, final Holder<CommonHeaderType> header) {
		
		log.info(String.format("%s -> %s", current, next));
		previous = current;
		current = next;
		current.enter(header);        
	}
	
	// called from successful checking
	void setHeldTimeout(final Holder<CommonHeaderType> header) {
		
		assert timeout == null;
		
		final int heldTimeout = Coordinator.getHeldTimeout();
		
		TimerTask timeoutTask = new TimerTask() {
			@Override
			public void run() {
				current.timeout(header);
				new Responder(NsiHeader.getReplyTo(header)).reserveTimeout(header, provider.getConnectionId(), heldTimeout, 
					provider.getNextNotificationId(), Coordinator.getProviderNSA());
			}			
		};
		
		int delay = Math.min(heldTimeout, 1000);
		assert delay > 0;		
		timeout = new Timer("held timeout");
		timeout.schedule(timeoutTask, delay * 1000L);
		log.debug("held timeout set to " + delay);		
	}
	
	synchronized public void clearHeldTimeout(String reason) {
		
		if (timeout != null) {
			timeout.cancel();
			timeout = null;
			log.debug("held timeout timer cleared - " + reason);
		}		
	}
	
	public Provider getProvider() { return provider; }
	
	public ReservationStateEnumType asNsiState() { return current.asNsiState(); }
	
	@Override
	public String toString() { return current.toString(); }
	
	@Override
	public void reserve(Holder<CommonHeaderType> header) {
		
		current.reserve(header);			
	}

	@Override
	public void reserveCommit(Holder<CommonHeaderType> header) {

		current.reserveCommit(header);			
	}

	@Override
	public void reserveAbort(Holder<CommonHeaderType> header) {
		
		current.reserveAbort(header);			
	}

	@Override
	public void timeout(Holder<CommonHeaderType> header) { 
		
		current.timeout(header);
	}
}
