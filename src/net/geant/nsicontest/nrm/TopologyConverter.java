/**
 * 
 */
package net.geant.nsicontest.nrm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import net.geant.nsicontest.topology.DecomposedStp;
import net.geant.nsicontest.topology.TopologyParser;

import org.ogf.schemas.nml._2013._05.base.BidirectionalPortType;
import org.ogf.schemas.nml._2013._05.base.LabelGroupType;
import org.ogf.schemas.nml._2013._05.base.LocationType;
import org.ogf.schemas.nml._2013._05.base.PortGroupRelationType;
import org.ogf.schemas.nml._2013._05.base.PortGroupType;
import org.ogf.schemas.nml._2013._05.base.TopologyRelationType;
import org.ogf.schemas.nml._2013._05.base.TopologyType;
import org.ogf.schemas.nsi._2013._09.topology.NSARelationType;
import org.ogf.schemas.nsi._2013._09.topology.NSAType;
import org.ogf.schemas.nsi._2013._09.topology.ServiceRelationType;
import org.ogf.schemas.nsi._2013._09.topology.ServiceType;

/**
 * Converts nrm topology to nsi/nml topology
 */
public class TopologyConverter {
	
	public static Topology nrmTopologyExample() {
		
		Topology topo = new Topology();
		topo.setNsaId("urn:ogf:network:aist.net:2013:nsa");
		topo.setAdminContact("adminContact");
		topo.setLink("http://localhost");
		topo.setVersion(Calendar.getInstance());
		topo.setTopologyName("topo.test");
		topo.setLongitude(0.0f);
		topo.setLatitude(0.0f);
		
		// endpoint
		String stp = "bi-ps";		
		GroupPort inbound = new GroupPort(stp + "-in", "1000", null);		
		GroupPort outbound = new GroupPort(stp + "-out", "1000", null);
		BidirPort port = new BidirPort(stp, inbound, outbound);
		topo.addBidirPort(port);
		
		// peering port
		String bistp = "aist-jgn";
		String inboundStp = "jgn-aist";		
		String inboundAlias = "urn:ogf:network:jgn.net:2013:jgn-aist";
		String outboundStp = "aist-jgn";		
		String outboundAlias = "urn:ogf:network:jgn.net:2013:aist-jgn";
		
		inbound = new GroupPort(inboundStp, "1000", inboundAlias);
		outbound = new GroupPort(outboundStp, "1000", outboundAlias);
		port = new BidirPort(bistp, inbound, outbound);
		topo.addBidirPort(port);
				
		return topo;
	}
		
	private static PortGroupType convertPort(GroupPort groupPort, String type, String networkId) {
		
		PortGroupType port = new PortGroupType();
		String portGroupId = networkId + ":" + groupPort.getId();
		port.setId(portGroupId);
		
		String vlans;
		if (groupPort.getVlan() == null || groupPort.getVlan().isEmpty())
			vlans = "4-4096";
		else
			vlans = groupPort.getVlan();
		
		
		LabelGroupType label = new LabelGroupType();
		label.setLabeltype("http://schemas.ogf.org/nml/2012/10/ethernet#vlan");
		label.setValue(vlans);			
		port.getLabelGroup().add(label);
		
		String alias = groupPort.getAlias();
				
		if (alias != null && !alias.isEmpty()) {
			
			PortGroupRelationType relAlias = new PortGroupRelationType();
			relAlias.setType("http://schemas.ogf.org/nml/2013/05/base#isAlias");
			PortGroupType remotePort = new PortGroupType();
			remotePort.setId(alias);
			relAlias.getPortGroup().add(remotePort);			
			port.getRelation().add(relAlias);
		}
		
		return port;
	}
	
	private static String getNsaId(String alias) {
		
		try {
			DecomposedStp stp = DecomposedStp.parse(alias);
			return stp.getNetworkIdWithNsa();
		} catch (Exception e) {
			return null;
		}
	}
		
	public static NSAType convert(Topology topology) throws Exception {
		
		if (topology == null)
			throw new IllegalArgumentException("topology null");
		
		String nsaId = topology.getNsaId();
		if (nsaId == null || nsaId.isEmpty()) throw new Exception("nsaId empty");
		
		if (!nsaId.startsWith("urn:ogf:network:")) throw new Exception("nsaId must start with urn:ogf:network:");
		if (!nsaId.endsWith(":nsa")) throw new Exception("nsaId must end with :nsa");
						
		NSAType nsa = new NSAType();		
		nsa.setId(nsaId);
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(topology.getVersion().getTime());
		
		try {
			nsa.setVersion(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
		} catch (DatatypeConfigurationException e) { 
			nsa.setVersion(null);
		}
		
		LocationType loc = new LocationType();
		loc.setLong(topology.getLongitude());
		loc.setLat(topology.getLatitude());		
		nsa.setLocation(loc);
		
		// service
		ServiceType service = new ServiceType();
		service.setId(topology.getNsaId() + "-provserv");
		service.setType("application/vdn.ogf.nsi.cs.v2.provider+soap");
		service.setLink(topology.getLink());
		ServiceRelationType serviceRel = new ServiceRelationType();
		serviceRel.setType("http://schemas.ogf.org/nsi/2013/09/topology#providedBy");
		NSAType serviceNsa = new NSAType();
		serviceNsa.setId(topology.getNsaId());
		serviceRel.getNSA().add(serviceNsa);		
		service.getRelation().add(serviceRel);		
		nsa.getService().add(service);
		
		// admin contact
		NSARelationType adminContact = new NSARelationType();
		adminContact.setType("http://schemas.ogf.org/nsi/2013/09/topology#adminContact");
		// set xcard
		nsa.getRelation().add(adminContact);
				
		TopologyType topo = new TopologyType();
		topo.setName(topology.getTopologyName());
		nsa.getTopology().add(topo);
		
		List<String> peers = new ArrayList<String>();
		String networkId = nsaId.substring(0, nsaId.length() - ":nsa".length());
		
		for (BidirPort port : topology.getBidirPorts()) {
		
			org.ogf.schemas.nml._2013._05.base.ObjectFactory factory = new org.ogf.schemas.nml._2013._05.base.ObjectFactory();
						
			BidirectionalPortType portType = new BidirectionalPortType();
			String bidirPortId = networkId + ":" + port.getStpId();
			portType.setId(bidirPortId);
			
			PortGroupType inbound = new PortGroupType();
			String inboundPortId = networkId + ":" + port.getInbound().getId();
			inbound.setId(inboundPortId);
			portType.getRest().add(factory.createPortGroup(inbound));
			
			PortGroupType outbound = new PortGroupType();
			String outboundPortId = networkId + ":" + port.getOutbound().getId();
			outbound.setId(outboundPortId);
			portType.getRest().add(factory.createPortGroup(outbound));

			// add bidirectional ports
			topo.getGroup().add(portType);
			
			// add in/outbound ports
			TopologyRelationType portInbound = new TopologyRelationType();
			portInbound.setType("http://schemas.ogf.org/nml/2013/05/base#hasInboundPort");
			portInbound.getPortGroup().add(convertPort(port.getInbound(), "http://schemas.ogf.org/nml/2013/05/base#hasInboundPort", networkId));
			topo.getRelation().add(portInbound);
			
			TopologyRelationType portOutbound = new TopologyRelationType();
			portOutbound.setType("http://schemas.ogf.org/nml/2013/05/base#hasOutboundPort");
			portOutbound.getPortGroup().add(convertPort(port.getOutbound(), "http://schemas.ogf.org/nml/2013/05/base#hasOutboundPort", networkId));
			topo.getRelation().add(portOutbound);
			
			// get peers from alias
			String peerNsa = getNsaId(port.getInbound().getAlias());
			if (peerNsa != null) {				
				if (!peers.contains(peerNsa))
					peers.add(peerNsa);
			}
			
			peerNsa = getNsaId(port.getOutbound().getAlias());
			if (peerNsa != null) {				
				if (!peers.contains(peerNsa))
					peers.add(peerNsa);
			}
		}
		
		// add relations
		NSARelationType peersWith = new NSARelationType();
		peersWith.setType("http://schemas.ogf.org/nsi/2013/09/topology#peersWith");
		for (String nid : peers) {		
			NSAType n = new NSAType();
			n.setId(nid);
			peersWith.getNSA().add(n);			
		}		
		nsa.getRelation().add(peersWith);		
		
		return nsa;
	}
	
	static private String getId(String nsaId) {

		return nsaId.replace("urn:ogf:network:", "").replace(":2013:nsa", "");		
	}
	
	static public void main(String... args) throws Exception {
		
		Topology topo = nrmTopologyExample();
		NSAType nsa = convert(topo);		
		String id = getId(topo.getNsaId());
		System.out.println("id: " + id);
		TopologyParser.save(id + ".xml", nsa);
				
	}
}
