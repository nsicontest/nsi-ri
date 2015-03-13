/**
 * 
 */
package net.geant.nsicontest.topology;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ogf.schemas.nsi._2013._09.topology.NSARelationType;

/**
 * Simplified NSAType.
 */
public class Nsa {
	
	private String id;
	private Calendar version;
	private Location location;
	private Service service;
	private NSARelationType adminContact;  
	private List<String> peersWith = new ArrayList<String>(); 
	private List<Topology> topologies = new ArrayList<Topology>();
	
	public Nsa() { }

	public Nsa(String id, Calendar version, Location location, Service service,	NSARelationType adminContact, 
				List<String> peersWith, List<Topology> topologies) {

		this.id = id;
		this.version = version;
		this.location = location;
		this.service = service;
		this.adminContact = adminContact;
		setPeersWith(peersWith);
		setTopologies(topologies);
	}

	/**
	 * Searches this nsa for stp
	 * @param stpId
	 * @return
	 */
	public BidirectionalPort getBidirectionalPort(String stpId) {
		
		for (Topology topo : topologies) {			
			for (BidirectionalPort port : topo.getPortsAsList()) {				
				if (port.getId().equals(stpId))
					return port;
			}
		}
		return null;
	}
	
	public List<BidirectionalPort> getBidirectionalPorts() {
		
		List<BidirectionalPort> ports = new ArrayList<BidirectionalPort>();
				
		for (Topology topo : topologies) {
			ports.addAll(topo.getPortsAsList());
		}
		return Collections.unmodifiableList(ports);
	}
	
	//
	// getters & setters
	//

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Calendar getVersion() {
		return version;
	}

	public void setVersion(Calendar version) {
		this.version = version;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public NSARelationType getAdminContact() {
		return adminContact;
	}

	public void setAdminContact(NSARelationType adminContact) {
		this.adminContact = adminContact;
	}
	
	public List<String> getPeersWith() {
		return peersWith;
	}

	public void setPeersWith(List<String> peersWith) {
				
		this.peersWith.addAll(peersWith);
	}

	public List<Topology> getTopologies() {
		return topologies;
	}

	public void setTopologies(List<Topology> topologies) {
		
		for (Topology topo : topologies) {
			topo.setOwner(this);
			this.topologies.add(topo);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
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
		Nsa other = (Nsa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		
		return true;
	}
	
	public String toStringDetailed() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("nsaId: %s, version: %s\r\n", id, version.getTime().toString()));
		sb.append("peers with:\r\n");
		
		for (String s : peersWith) {			
			sb.append(s + "\r\n");
		}
		
		sb.append("topologies:\r\n");
		
		for (Topology topo : topologies) {	
			
			sb.append(topo.getName() + "\r\n");			
			for (BidirectionalPort p : topo.getPortsAsList()) {
				sb.append(p + "\r\n");				
			}			
		}				
		return sb.toString();
	}

	@Override
	public String toString() {
		return "Nsa [id=" + id + "]";
	}
}
