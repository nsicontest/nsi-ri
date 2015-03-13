/**
 * 
 */
package net.geant.nsicontest.reservation;

import javax.xml.ws.Holder;

import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;

/**
 * Describes available operations on Lifecycle State Machine.
 */
public interface Lifecyclable {

	/**
	 * The terminate message is sent from a RA to a PA when an existing reservation is to be transitioned into a terminated state 
	 * and all associated resources in the network are freed. The terminateACK indicates that the PA has accepted the terminate request 
	 * for processing. A terminateConfirmed message will be sent asynchronously to the RA when terminate processing has completed. 
	 * There is no associated Failed message for this operation.
	 * @param header
	 */
	void terminate(final Holder<CommonHeaderType> header);
	
	/**
	 * Reservation reached its end of life mark.
	 * @param header
	 */
	void endOfTime(final Holder<CommonHeaderType> header);
	
	/**
	 * Received forced end message from a remote requester.
	 * @param header
	 */
	void forcedEnd(final Holder<CommonHeaderType> header);
}
