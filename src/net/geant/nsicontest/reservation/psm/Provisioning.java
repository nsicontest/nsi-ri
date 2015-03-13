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
 * A transient state modeling the act of provisioning the reservation�s associated data plane resources.
 */
final class Provisioning extends ProvisionState {

	Provisioning(ProvisionStateMachine psm) { super(psm); }

	@Override
	public ProvisionStateEnumType asNsiState() { return ProvisionStateEnumType.PROVISIONING; }
	
	@Override
	public void enter(final Holder<CommonHeaderType> header) {
			
		try {
			psm.getProvider().provision();
		} catch (Exception e) {			
			log.info("provision error - " + e.getMessage());
		}
		
		// this is potentially dangerous as delivering provisionConfirmed can take some time so we might be late here with activation
		// might consider making provisionConfirmed async
		
		if (psm.getProvider().isActive()) {
			// if past current time, activate now
			psm.getProvider().nrmActivate(header);			
		} else {
			// auto-provision mode
			psm.setActivation(header);									
		}
		
		boolean delivered = new Responder(NsiHeader.getReplyTo(header)).provisionConfirmed(header, psm.getProvider().getConnectionId());
		
		if (!delivered && Coordinator.isFailIfResponseNotDelivered()) {
			// going back to released
			psm.switchState(psm.RELEASED, header);
			return;			
		} else {			
			psm.switchState(psm.PROVISIONED, header);
		}
	}
}
