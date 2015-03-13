/**
 * 
 */
package net.geant.nsicontest.reservation.query;

/**
 * Represents an event.
 */
public enum Event {
	
	RESERVE_CONFIRMED,
	RESERVE_FAILED,
	RESERVE_ABORT_CONFIRMED,
	RESERVE_COMMIT_CONFIRMED,
	RESERVE_COMMIT_FAILED,
	PROVISION_CONFIRMED,
	RELEASE_CONFIRMED,
	TERMINATE_CONFIRMED,
}
