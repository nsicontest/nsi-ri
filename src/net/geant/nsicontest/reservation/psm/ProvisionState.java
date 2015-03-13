/**
 * 
 */
package net.geant.nsicontest.reservation.psm;

import javax.xml.ws.Holder;

import net.geant.nsicontest.reservation.Provisionable;
import net.geant.nsicontest.reservation.State;

import org.apache.log4j.Logger;
import org.ogf.schemas.nsi._2013._12.connection.types.ProvisionStateEnumType;
import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;

/**
 * Base class for Provision states - implements the Provisionable interface with default behavior of
 * throwing UnsupportedOperationException.
 */
abstract class ProvisionState extends State<ProvisionStateEnumType> implements Provisionable {
	
	protected ProvisionStateMachine psm;
	protected Logger log;
	
	protected ProvisionState(ProvisionStateMachine psm) {
		assert psm != null;
		this.psm = psm;	
		this.log = ProvisionStateMachine.log;
	}

	@Override
	public void provision(final Holder<CommonHeaderType> header) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void release(final Holder<CommonHeaderType> header) {
		throw new UnsupportedOperationException();
	}
}
