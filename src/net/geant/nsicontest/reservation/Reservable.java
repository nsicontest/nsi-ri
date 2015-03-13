/**
 * 
 */
package net.geant.nsicontest.reservation;

import javax.xml.ws.Holder;

import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;

/**
 * Describes available operations on Reservation State Machine.
 */
public interface Reservable {
	
	/**
	 * The reserve message is sent from a RA to a PA when a new reservation is being requested, 
	 * or a modification to an existing reservation is required. The reserveResponse indicates that 
	 * the PA has accepted the reservation request for processing and has assigned it the returned connectionId. 
	 * A reserveConfirmed or reserveFailed message will be sent asynchronously to the RA when reserve operation has completed processing.
	 * @param header
	 */
	void reserve(final Holder<CommonHeaderType> header);
	
	/**
	 * The NSI CS reserveCommit message allows a RA to commit a previously allocated reservation or modification on a reservation. 
	 * The reserveCommit request MUST arrive at the Provider Agent before the reservation timeout occurs.
	 * @param header
	 */
	void reserveCommit(final Holder<CommonHeaderType> header);
	
	/**
	 * The NSI CS reserveAbort message allows a RA to abort a previously requested reservation or modification on a reservation.  
	 * @param header
	 */
	void reserveAbort(final Holder<CommonHeaderType> header);
		
	/**
	 * The reserveTimeout message is an autonomous message issued from a PA to an RA when a timeout on an existing reserve request occurs, 
	 * and the PA has freed any uncommitted resources associated with the reservation. This type of event is originated from an uPA 
	 * managing network resources associated with the reservation, and propagated up the request tree to the originating uRA.  
	 * An aggregator NSA (performing both a PA and RA role) will map the received connectionId into a context understood by its direct parent RA 
	 * in the request tree, then propagate the event upwards.  The originating connectionId and uPA are provided in separate elements to 
	 * maintain the original context generating the timeout.  The timeoutValue and timeStamp are populated
 	 * by the originating uPA and propagated up the tree untouched by intermediate NSA.
	 * @param header
	 */
	void timeout(final Holder<CommonHeaderType> header);
}
