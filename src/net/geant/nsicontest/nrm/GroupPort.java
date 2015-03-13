/**
 * 
 */
package net.geant.nsicontest.nrm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Simplified port group class. Id is a port group identifier, vlan is a single or range vlan,
 * alias is a port group identifier of remote port group (most typically obtained from remote peering site).
 * Alias can be null to indicate this port group is not connected anywhere. 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroupPort", propOrder = { "id", "vlan", "alias" })
public class GroupPort {
	
	private String id;
	private String vlan;
	private String alias;
	
	public GroupPort() { }
	
	public GroupPort(String id, String vlan, String alias) {

		this.id = id;
		this.vlan = vlan;
		this.alias = alias;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVlan() {
		return vlan;
	}

	public void setVlan(String vlan) {
		this.vlan = vlan;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}
