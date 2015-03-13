/**
 * 
 */
package net.geant.nsicontest.reservation.psm;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.ws.Holder;

import net.geant.nsicontest.reservation.Provider;
import net.geant.nsicontest.reservation.Provisionable;

import org.apache.log4j.Logger;
import org.ogf.schemas.nsi._2013._12.connection.types.ProvisionStateEnumType;
import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;

/**
 * Follows State Machine pattern to implement the Provisionable interface.
 */
final public class ProvisionStateMachine implements Provisionable {
	
	static Logger log = Logger.getLogger(ProvisionStateMachine.class);
		
	final ProvisionState RELEASED;
	final ProvisionState RELEASING;
	final ProvisionState PROVISIONED;
	final ProvisionState PROVISIONING;
	
	private ProvisionState current;
	private Timer activation;
	final private Provider provider;
		
	public ProvisionStateMachine(Provider provider) {
		
		this.provider = provider;
		
		current = RELEASED = new Released(this);
		RELEASING = new Releasing(this);
		PROVISIONED = new Provisioned(this);
		PROVISIONING = new Provisioning(this);		
	}	
	
	void switchState(final ProvisionState next, final Holder<CommonHeaderType> header) {
		
		log.info(String.format("%s -> %s", current, next));		
		current = next;
		current.enter(header);		
	}
	
	// called either by setCommited or by provisioning state
	synchronized public void setActivation(final Holder<CommonHeaderType> header) {
		
		TimerTask activationTask = new TimerTask() {			
			@Override
			public void run() {
				clearActivation("self expired");
				provider.nrmActivate(header);				
			}
		};
		
		long delay = provider.getStartTime().getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
		assert delay > 0;
		activation = new Timer("activation");
		activation.schedule(activationTask, delay);
		log.info(String.format("auto-provision will take place in %s seconds", delay / 1000));
	}
	
	synchronized public void clearActivation(String reason) {
		
		if (activation != null) {		
			activation.cancel();
			activation = null;
			log.debug("activation timer cleared - " + reason);			
		}		
	}
	
	public boolean isProvisioned() { return current == PROVISIONED; }
	
	public boolean isReleased() { return current == RELEASED; }
	
	Provider getProvider() { return provider; }
	
	public ProvisionStateEnumType asNsiState() { return current.asNsiState(); }
	
	@Override
	public String toString() { return current.toString(); }
	
	@Override
	public void provision(Holder<CommonHeaderType> header) {
	
		current.provision(header);
	}

	@Override
	public void release(Holder<CommonHeaderType> header) {
	
		current.release(header);
	}
}
