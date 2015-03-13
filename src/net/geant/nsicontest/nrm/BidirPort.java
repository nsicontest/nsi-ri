/**
 * 
 */
package net.geant.nsicontest.nrm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Simplified bidirectional port. 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BidirPort", propOrder = { "stpId", "inbound", "outbound" })
public class BidirPort {
	
	private String stpId;
	private GroupPort inbound;
	private GroupPort outbound;
	
	public BidirPort() { }

	public BidirPort(String stpId, GroupPort inbound, GroupPort outbound) {
	
		this.stpId = stpId;
		this.inbound = inbound;
		this.outbound = outbound;
	}

	public String getStpId() {
		return stpId;
	}

	public void setStpId(String stpId) {
		this.stpId = stpId;
	}

	public GroupPort getInbound() {
		return inbound;
	}

	public void setInbound(GroupPort inbound) {
		this.inbound = inbound;
	}

	public GroupPort getOutbound() {
		return outbound;
	}

	public void setOutbound(GroupPort outbound) {
		this.outbound = outbound;
	}
}
