/**
 * 
 */
package net.geant.nsicontest.nrm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Represents answer from NRM, currently we are interested only in reserved vlans.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReserveOutcome", propOrder = { "ingressVlan", "egressVlan" })
final public class ReserveOutcome {
	
	private int ingressVlan;	
	private int egressVlan;
	
	public ReserveOutcome() { }
	
	public ReserveOutcome(int ingressVlan, int egressVlan) {

		this.ingressVlan = ingressVlan;
		this.egressVlan = egressVlan;
	}

	public int getIngressVlan() {
		return ingressVlan;
	}

	public void setIngressVlan(int ingressVlan) {
		this.ingressVlan = ingressVlan;
	}

	public int getEgressVlan() {
		return egressVlan;
	}

	public void setEgressVlan(int egressVlan) {
		this.egressVlan = egressVlan;
	}
}
