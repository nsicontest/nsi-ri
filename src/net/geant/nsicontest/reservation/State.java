/**
 * 
 */
package net.geant.nsicontest.reservation;

import javax.xml.ws.Holder;

import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;

/**
 * Base class for each state that is associated with one of the Reserve, Provision and Lifecycle state machine.
 * Subclasses do appropriate work as specified by protocol.
 */
abstract public class State<E extends Enum<?>> {
	
	/**
	 * Nsi defines some actions as two-step transitions, i.e. for event provision it is provisioning then provisioned.
	 * To help this situation, transitional states should override enter method.
	 */
	public void enter(final Holder<CommonHeaderType> header) { }

	/**
	 * Returns appropriate NSI state enumeration
	 * @return
	 */
	abstract public E asNsiState();
	
	@Override
	public String toString() { return asNsiState().toString(); }
}
