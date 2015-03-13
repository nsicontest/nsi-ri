/**
 * 
 */
package net.geant.nsicontest.reservation;

import javax.xml.ws.Holder;

import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;

/**
 * Describes available operations on Provision State Machine.
 */
public interface Provisionable {
		
	/**
     * The provision message is sent from a Requester NSA to a Provider
     * NSA when an existing reservation is to be transitioned into a
     * provisioned state. The provisionACK indicates that the Provider
     * NSA has accepted the provision request for processing. A
     * provisionConfirmed message will be sent asynchronously to the
     * Requester NSA when provision processing has completed.  There is
     * no associated Failed message for this operation.
     */
	void provision(final Holder<CommonHeaderType> header);

	/**
     * The release message is sent from a Requester NSA to a Provider
     * NSA when an existing reservation is to be transitioned into a
     * released state. The releaseACK indicates that the Provider NSA
     * has accepted the release request for processing. A
     * releaseConfirmed message will be sent asynchronously to the
     * Requester NSA when release processing has completed.  There is
     * no associated Failed message for this operation.
     */
	void release(final Holder<CommonHeaderType> header);
}
