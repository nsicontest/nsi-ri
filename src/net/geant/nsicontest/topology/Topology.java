/**
 * 
 */
package net.geant.nsicontest.topology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <nml:Topology> 
 */
public class Topology {
	
	private String name;
	private Map<String, BidirectionalPort> ports = new HashMap<String, BidirectionalPort>();
	private Nsa owner;
	
	public Topology() { }

	public Topology(String name) {
	
		this.name = name;
	}
	
	public void addPort(BidirectionalPort port) {
		
		port.setOwner(this);
		ports.put(port.getId(), port);
	}
	
	public List<BidirectionalPort> getPortsAsList() {
		
		return new ArrayList<BidirectionalPort>(ports.values());
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Nsa getOwner() {
		return owner;
	}

	public void setOwner(Nsa owner) {
		this.owner = owner;
	}

	public Map<String, BidirectionalPort> getPorts() {
		return ports;
	}

	public void setPorts(Map<String, BidirectionalPort> ports) {
		this.ports = ports;
	}

	@Override
	public String toString() {
		return "Topology [name=" + name + "]";
	}
}
