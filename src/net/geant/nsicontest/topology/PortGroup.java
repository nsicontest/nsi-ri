/**
 * 
 */
package net.geant.nsicontest.topology;

import net.geant.nsicontest.core.Range;

import org.ogf.schemas.nml._2013._05.base.LabelGroupType;
import org.ogf.schemas.nml._2013._05.base.PortGroupRelationType;
import org.ogf.schemas.nml._2013._05.base.PortGroupType;

/**
 * Represents <nml:PortGroup>
 */
public class PortGroup {
	
	static private final String RELATION_IS_ALIAS = "http://schemas.ogf.org/nml/2013/05/base#isAlias";
	static private final String LABEL_VLAN = "http://schemas.ogf.org/nml/2012/10/ethernet#vlan";
	
	private String id;
	private String aliasPort; // remote port id
	private PortType type;
	private Range vlans;
	
	public PortGroup() { }

	public PortGroup(String id, String aliasPort, PortType type, Range vlans) {
	
		this.id = id;
		this.aliasPort = aliasPort;
		this.type = type;
		this.vlans = vlans;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAliasPort() {
		return aliasPort;
	}

	public void setAliasPort(String aliasPort) {
		this.aliasPort = aliasPort;
	}

	public PortType getType() {
		return type;
	}

	public void setType(PortType type) {
		this.type = type;
	}

	public Range getVlans() {
		return vlans;
	}

	public void setVlans(Range vlans) {
		this.vlans = vlans;
	}
	
	public PortGroupType toPortGroupType() {
		
		PortGroupType port = new PortGroupType();
		port.setId(id);
		if (vlans != null) {
			
			LabelGroupType label = new LabelGroupType();
			label.setLabeltype(LABEL_VLAN);
			label.setValue(String.format("%s-%s", vlans.min(), vlans.max()));			
			port.getLabelGroup().add(label);
		}
		
		if (aliasPort != null) {
			
			PortGroupRelationType relAlias = new PortGroupRelationType();
			relAlias.setType(RELATION_IS_ALIAS);
			PortGroupType remotePort = new PortGroupType();
			remotePort.setId(aliasPort);
			relAlias.getPortGroup().add(remotePort);			
			port.getRelation().add(relAlias);
		}
		
		return port;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PortGroup other = (PortGroup) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PortGroup [id=" + id + ", aliasPort=" + aliasPort + ", type=" + type + ", vlans=" + vlans + "]";
	}
}
