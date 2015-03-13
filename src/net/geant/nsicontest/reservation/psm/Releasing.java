/**
 * 
 */
package net.geant.nsicontest.reservation.psm;

import javax.xml.ws.Holder;

import net.geant.nsicontest.core.NsiHeader;
import net.geant.nsicontest.reservation.Coordinator;
import net.geant.nsicontest.reservation.Responder;

import org.ogf.schemas.nsi._2013._12.connection.types.ProvisionStateEnumType;
import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;

/**
 * A transient state modeling the act of releasing the reservation�s associated data plane resources.
 */
final class Releasing extends ProvisionState {

	Releasing(ProvisionStateMachine psm) { super(psm); }

	@Override
	public ProvisionStateEnumType asNsiState() { return ProvisionStateEnumType.RELEASING; }
	
	@Override
	public void enter(final Holder<CommonHeaderType> header) {
		
		try {
			psm.getProvider().release();
		} catch (Exception e) {			
			log.info("release error - " + e.getMessage());
		}
		
		// this is potentially dangerous as delivering releaseConfirmed can take some time so we might be late here with deactivation
		// might consider making releaseConfirmed async
		
		if (psm.getProvider().isActive()) {
			// if past current time, deactivate now
			psm.getProvider().nrmDeactivate(header);			
		} else {
			// auto-provision mode
			psm.clearActivation("release requested");							
		}
		
		// acknowledge immediately
		boolean delivered = new Responder(NsiHeader.getReplyTo(header)).releaseConfirmed(header, psm.getProvider().getConnectionId());
		
		if (!delivered && Coordinator.isFailIfResponseNotDelivered()) {
			// going back to provisioned
			psm.switchState(psm.PROVISIONED, header);
			return;			
		} else {			
			psm.switchState(psm.RELEASED, header);
		}
	}
}
