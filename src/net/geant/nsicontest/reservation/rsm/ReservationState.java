/**
 * 
 */
package net.geant.nsicontest.reservation.rsm;

import javax.xml.ws.Holder;

import net.geant.nsicontest.reservation.Reservable;
import net.geant.nsicontest.reservation.State;

import org.apache.log4j.Logger;
import org.ogf.schemas.nsi._2013._12.connection.types.ReservationStateEnumType;
import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;

/**
 * Base class for Reservation states - implements the Reservable interface with default behavior of 
 * throwing UnsupportedOperationException. 
 */
abstract class ReservationState extends State<ReservationStateEnumType> implements Reservable {

	protected ReservationStateMachine rsm;
	protected Logger log;
	
	protected ReservationState(ReservationStateMachine rsm) {
		assert rsm != null;
		this.rsm = rsm;
		this.log = ReservationStateMachine.log;
	}
		
	@Override
	public void reserve(final Holder<CommonHeaderType> header) {
		throw new UnsupportedOperationException();	
	}

	@Override
	public void reserveCommit(final Holder<CommonHeaderType> header) {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void reserveAbort(final Holder<CommonHeaderType> header) {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void timeout(final Holder<CommonHeaderType> header) {
		throw new UnsupportedOperationException();		
	}
}
