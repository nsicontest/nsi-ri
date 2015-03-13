/**
 * 
 */
package net.geant.nsicontest.reservation.lsm;

import javax.xml.ws.Holder;

import net.geant.nsicontest.core.NsiHeader;
import net.geant.nsicontest.reservation.Provider;
import net.geant.nsicontest.reservation.Responder;

import org.ogf.schemas.nsi._2013._12.connection.types.LifecycleStateEnumType;
import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;

/**
 * A transient state modeling the act of terminating the reservation.
 */
final class Terminating extends LifecycleState {

	Terminating(LifecycleStateMachine lsm) { super(lsm); }
	
	@Override
	public LifecycleStateEnumType asNsiState() { return LifecycleStateEnumType.TERMINATING; }
	
	@Override
	public void enter(final Holder<CommonHeaderType> header) {
		
		// if coming from ENDTIME state, only go to TERMINATE
		if (lsm.getPreviousState() == lsm.END_TIME) {
			lsm.switchState(lsm.TERMINATED, header);
			return;
		}
	
		lsm.clearSentinel("terminate arrived");
		
		Provider prov = lsm.getProvider();
		prov.clearActivation();
		
		// acknowledge immediately
		new Responder(NsiHeader.getReplyTo(header)).terminateConfirmed(header, prov.getConnectionId());
		
		// regardless delivered go to terminated and do not bother with going back
		lsm.switchState(lsm.TERMINATED, header);
						
		if (prov.isActivated())	
			prov.nrmDeactivate(header);		

		prov.nrmTerminate(true);		
	}
}
