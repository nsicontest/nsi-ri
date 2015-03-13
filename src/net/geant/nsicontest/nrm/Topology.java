/**
 * 
 */
package net.geant.nsicontest.nrm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Simplified topology class to be used by NRM implementations to export their underlying topologies.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Topology", propOrder = { "nsaId", "version", "link", "adminContact", "topologyName", "latitude", "longitude", 
		"peersWith", "bidirPorts" })
public class Topology {
	
	private String nsaId;
	private Calendar version;
	private String link;
	private String adminContact;
	private String topologyName;
	
	private float latitude;
	private float longitude;
	
	private List<String> peersWith = new ArrayList<String>();	
	private List<BidirPort> bidirPorts = new ArrayList<BidirPort>();
	
	public Topology() { }

	public Topology(String nsaId, Calendar version, String link, String adminContact, String topologyName, float latitude, float longitude) {

		this.nsaId = nsaId;
		this.version = version;
		this.link = link;
		this.adminContact = adminContact;
		this.topologyName = topologyName;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public void addPeerWith(String nsaId) {
		
		peersWith.add(nsaId);
	}
	
	public void addBidirPort(BidirPort port) { 
		
		bidirPorts.add(port);
	}
	

	public String getNsaId() {
		return nsaId;
	}

	public void setNsaId(String nsaId) {
		this.nsaId = nsaId;
	}

	public Calendar getVersion() {
		return version;
	}

	public void setVersion(Calendar version) {
		this.version = version;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getAdminContact() {
		return adminContact;
	}

	public void setAdminContact(String adminContact) {
		this.adminContact = adminContact;
	}

	public String getTopologyName() {
		return topologyName;
	}

	public void setTopologyName(String topologyName) {
		this.topologyName = topologyName;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public List<String> getPeersWith() {
		return peersWith;
	}

	public void setPeersWith(List<String> peersWith) {
		this.peersWith = peersWith;
	}

	public List<BidirPort> getBidirPorts() {
		return bidirPorts;
	}

	public void setBidirPorts(List<BidirPort> bidirPorts) {
		this.bidirPorts = bidirPorts;
	}
}
