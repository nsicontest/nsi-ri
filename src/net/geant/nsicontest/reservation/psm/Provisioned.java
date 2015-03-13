/**
 * 
 */
package net.geant.nsicontest.reservation.psm;

import javax.xml.ws.Holder;

import org.ogf.schemas.nsi._2013._12.connection.types.ProvisionStateEnumType;
import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;

/**
 * A steady state for the provision state machine in which data plane resources for this reservation are in a provisioned state. 
 * This state does not imply that data plane resources are active, but it does indicate that a uPA can active the data plane resources 
 * if current_time is between startTime and endTime.
 */
final class Provisioned extends ProvisionState {

	Provisioned(ProvisionStateMachine psm) { super(psm); }

	@Override
	public ProvisionStateEnumType asNsiState() { return ProvisionStateEnumType.PROVISIONED; }
	
	@Override
	public void release(final Holder<CommonHeaderType> header) {
		
		psm.switchState(psm.RELEASING, header);
	}
}
