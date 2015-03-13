/**
 * 
 */
package net.geant.nsicontest.reservation.psm;

import javax.xml.ws.Holder;

import org.ogf.schemas.nsi._2013._12.connection.types.ProvisionStateEnumType;
import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;

/**
 * A steady state for the provision state machine in which data plane resources for this reservation are in a released state, resulting in an inactive data plane.
 */
final class Released extends ProvisionState {

	Released(ProvisionStateMachine psm) { super(psm); }

	@Override
	public ProvisionStateEnumType asNsiState() { return ProvisionStateEnumType.RELEASED; }
	
	@Override
	public void provision(final Holder<CommonHeaderType> header) {
		
		psm.switchState(psm.PROVISIONING, header);
	}	
}
