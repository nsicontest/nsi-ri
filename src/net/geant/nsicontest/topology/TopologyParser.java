/**
 * 
 */
package net.geant.nsicontest.topology;


import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.geant.nsicontest.core.Nsi;
import net.geant.nsicontest.core.Range;

import org.apache.log4j.Logger;
import org.ogf.schemas.nml._2013._05.base.BidirectionalPortType;
import org.ogf.schemas.nml._2013._05.base.LabelGroupType;
import org.ogf.schemas.nml._2013._05.base.NetworkObject;
import org.ogf.schemas.nml._2013._05.base.PortGroupRelationType;
import org.ogf.schemas.nml._2013._05.base.PortGroupType;
import org.ogf.schemas.nml._2013._05.base.TopologyRelationType;
import org.ogf.schemas.nml._2013._05.base.TopologyType;
import org.ogf.schemas.nsi._2013._09.topology.NSARelationType;
import org.ogf.schemas.nsi._2013._09.topology.NSAType;
import org.ogf.schemas.nsi._2013._09.topology.ObjectFactory;
import org.ogf.schemas.nsi._2013._09.topology.ServiceType;
import org.w3c.dom.Element;

/**
 * Provides methods for converting NSAType to more convenient Nsa class.
 * NSAType is a messy format that prevents its direct usage hence
 * all internal calculations are performed on Nsa objects. 
 */
public class TopologyParser {
	
	static private final Logger log = Logger.getLogger(TopologyParser.class);
	
	static private final String RELATION_ADMIN_CONTACT = "http://schemas.ogf.org/nsi/2013/09/topology#adminContact";
	static private final String RELATION_PEERS_WITH = "http://schemas.ogf.org/nsi/2013/09/topology#peersWith";
	static private final String RELATION_INBOUND_PORT = "http://schemas.ogf.org/nml/2013/05/base#hasInboundPort";
	static private final String RELATION_OUTBOUND_PORT = "http://schemas.ogf.org/nml/2013/05/base#hasOutboundPort";
	static private final String RELATION_IS_ALIAS = "http://schemas.ogf.org/nml/2013/05/base#isAlias";
	static private final String LABEL_VLAN = "http://schemas.ogf.org/nml/2012/10/ethernet#vlan";
	
	/**
	 * Reads nsi/nml topology file and returns NSAType object
	 * @param filename
	 * @return
	 * @throws TopologyException
	 */
	static public NSAType read(String filename) throws TopologyException {
		
		try {
			
			File file = new File(filename);
			JAXBContext context = JAXBContext.newInstance("org.ogf.schemas.nsi._2013._09.topology");
			Unmarshaller unmar = context.createUnmarshaller();
			JAXBElement<?> elem  = (JAXBElement<?>)unmar.unmarshal(file);
			NSAType nsaType = (NSAType)elem.getValue();
			
			return nsaType;
			
		} catch (Exception e) {
			throw new TopologyException(e);
		}		
	}
	
	static public void save(String filename, NSAType nsaType) throws TopologyException {
		
		try {
			
			File file = new File(filename);
			JAXBContext context = JAXBContext.newInstance("org.ogf.schemas.nsi._2013._09.topology");
			Marshaller mar = context.createMarshaller();			
			ObjectFactory factory = new ObjectFactory();
			mar.marshal(factory.createNSA(nsaType), file);
			
		} catch (Exception e) {
			throw new TopologyException(e);
		}
	}
	
	static public void save(String filename, Nsa nsa) throws TopologyException {

		save(filename, create(nsa));
	}
	
	/**
	 * Reads nsi/nml topology file and returns Nsa object 
	 * @param filename
	 * @return
	 * @throws TopologyException
	 */
	static public Nsa create(String filename) throws TopologyException {
		
		return create(read(filename));
	}
	
	static private ServiceType getService(List<ServiceType> services, String type) {
		
		// makes no sense to filter by type
		
		if (services.size() != 1) throw new IllegalArgumentException("services.size != 1");
		
		return services.get(0);	
	}
	
	// admin contact can be extracted as a string, however there are issues when converting back
	// therefore this method is not used
	static private String getAdminContact(NSAType nsaType) {
		
		NSARelationType relAdminContact = getNsaRelation(nsaType.getRelation(), RELATION_ADMIN_CONTACT);
		
		if (relAdminContact != null) {
			if (relAdminContact.getAny() != null && !relAdminContact.getAny().isEmpty()) {
				
				Object obj = relAdminContact.getAny().get(0);
				if (obj instanceof Element) {					
					Element el = (Element)obj;					
					// in future adminContact will be a Vcard
					return el.getTextContent();
				}
			}			
		}
		
		return null;
	}
	
	static private NSARelationType getNsaRelation(List<NSARelationType> relations, String type) {
		
		for (NSARelationType rel : relations) {
			
			if (rel.getType() != null && rel.getType().equals(type))
				return rel;
		}
		return null;
	}
	
	static private PortGroup getPortGroup(PortGroupType port, PortType type) {
		
		String id = port.getId();
		
		// get vlans
		Range vlans = null;
		
		for (LabelGroupType label : port.getLabelGroup()) {
		
			if (label.getLabeltype() != null && label.getLabeltype().equals(LABEL_VLAN)) {
				
				vlans = Range.create(label.getValue());
			}
		}		
		
		// get remote port
		String alias = null;
		
		for (PortGroupRelationType rel : port.getRelation()) {

			if (rel.getType() != null && rel.getType().equals(RELATION_IS_ALIAS)) {
				
				// get first port group id
				for (PortGroupType remotePort : rel.getPortGroup()) {
					
					alias =  remotePort.getId();
					break;
				}
			}
		}
				
		return new PortGroup(id, alias, type, vlans);
	}
	
	/**
	 * Converts messy NSAType into simpler Nsa object 
	 * @param nsaType
	 * @return
	 * @throws TopologyException
	 */
	static public Nsa create(NSAType nsaType) throws TopologyException {
		
		if (nsaType == null) throw new IllegalArgumentException("nsa");

		String nsaId = nsaType.getId();		
		if (nsaId == null || nsaId.isEmpty()) throw new IllegalArgumentException("nsa.id");
		
		Calendar version = nsaType.getVersion().toGregorianCalendar();
		if (version == null) throw new IllegalArgumentException("nsa.version");
		
		Location location = Location.fromLocationType(nsaType.getLocation());
		
		// look for exactly one service (at least examples do not provide more)
		ServiceType serviceType = getService(nsaType.getService(), null);
		Service service = Service.fromServiceType(serviceType);
				
		// get AdminContact relation
		NSARelationType adminContact = getNsaRelation(nsaType.getRelation(), RELATION_ADMIN_CONTACT);
				
		// add peering nsas
		List<String> peersWith = new ArrayList<String>();
		for (NSARelationType rel : nsaType.getRelation()) {
			
			if (rel.getType() != null && rel.getType().equals(RELATION_PEERS_WITH)) {
				
				for (NSAType remoteNsa : rel.getNSA()) {
					
					String remoteId = remoteNsa.getId();
					if (remoteId != null && !remoteId.isEmpty()) {
						peersWith.add(remoteId);
					} else {
						log.info("nsa.peersWith with invalid nsaId - " + remoteId);
					}
				}				
			}			
		}
		
		// process topologies and add them to nsa
		List<Topology> topologies = new ArrayList<Topology>();
		
		for (TopologyType topo : nsaType.getTopology()) {
			
			Topology topology = new Topology(topo.getName()); 
			topologies.add(topology);
							
			// first group inbound and outbound port groups
			Map<String, PortGroup> portGroups = new HashMap<String, PortGroup>();
			
			for (TopologyRelationType rel : topo.getRelation()) {
				
				for (PortGroupType portGroup : rel.getPortGroup()) {
					
					if (rel.getType() == null || rel.getType().isEmpty()) {
						log.info("topology.relation with null/empty type");
						continue;					
					}
				
					if (rel.getType().equals(RELATION_INBOUND_PORT)) {
						
						PortGroup p = getPortGroup(portGroup, PortType.INBOUND);
						portGroups.put(p.getId(), p);		
					
					} else if (rel.getType().equals(RELATION_OUTBOUND_PORT)) {
					
						PortGroup p = getPortGroup(portGroup, PortType.OUTBOUND);
						portGroups.put(p.getId(), p);
					
					} else {
						log.info("topology.relation with unsupported type: " + rel.getClass().getSimpleName());
					}
				}
			}
						
			// get bidirectional ports, other types of port are not supported 
			for (NetworkObject net : topo.getGroup()) {
						
				if (net instanceof BidirectionalPortType) {

					BidirectionalPortType biPort = (BidirectionalPortType)net;
							
					// check for port id
					String stpId = biPort.getId();					
					DecomposedStp decomposed = null;
					try {
						decomposed = DecomposedStp.parse(stpId);
					} catch (Exception e) {
						throw new TopologyException(e);
					}
					BidirectionalPort port = new BidirectionalPort(stpId, decomposed, null, null);
					topology.addPort(port);
						
					// get port groups
					for (Object obj : biPort.getRest()) {

						JAXBElement<?> el = (JAXBElement<?>)obj;

						if (el.getValue() instanceof PortGroupType) {
							
							PortGroupType portGroup = (PortGroupType)el.getValue();
						
							// add to port
							PortGroup pg = portGroups.get(portGroup.getId());
							if (pg != null) {
								
								if (pg.getType() == PortType.INBOUND)
									port.setInbound(pg);
								else if (pg.getType() == PortType.OUTBOUND)
									port.setOutbound(pg);
								
							} else {							
								log.info("topology.bidirport with no port group: " + portGroup.getId());
							}
							
						} else {
							log.info("topology.group.rest with unsupported type: " + obj.getClass().getCanonicalName());
						}						
					} 
							
				} else {
					log.info("topology.group with unsupported type: " + net.getClass().getCanonicalName());
				}
			}
		}			
		
		return new Nsa(nsaId, version, location, service, adminContact, peersWith, topologies);
	}
	
	static public NSAType create(Nsa nsa) {
		
		if (nsa == null) throw new IllegalArgumentException("nsa");
		
		NSAType nsaType = new NSAType();
		
		nsaType.setId(nsa.getId());
		nsaType.setVersion(Nsi.createXmlCalendar(nsa.getVersion()));		
		nsaType.setLocation(nsa.getLocation().toLocationType());
		
		nsaType.getService().add(nsa.getService().toServiceType());
		nsaType.getRelation().add(nsa.getAdminContact());
				
		NSARelationType peersWith = new NSARelationType();
		peersWith.setType(RELATION_PEERS_WITH);
		for (String nsaId : nsa.getPeersWith()) {
		
			NSAType n = new NSAType();
			n.setId(nsaId);
			peersWith.getNSA().add(n);			
		}		
		nsaType.getRelation().add(peersWith);
		
		for (Topology topo : nsa.getTopologies()) {
			
			TopologyType topoType = new TopologyType();
			nsaType.getTopology().add(topoType);
			
			topoType.setName(topo.getName());
	
			for (BidirectionalPort port : topo.getPortsAsList()) {

				org.ogf.schemas.nml._2013._05.base.ObjectFactory factory = new org.ogf.schemas.nml._2013._05.base.ObjectFactory();
				
				BidirectionalPortType portType = new BidirectionalPortType();				
				portType.setId(port.getId());				
				
				PortGroupType inbound = new PortGroupType();
				inbound.setId(port.getInbound().getId());
				portType.getRest().add(factory.createPortGroup(inbound));
				
				PortGroupType outbound = new PortGroupType();
				outbound.setId(port.getOutbound().getId());
				portType.getRest().add(factory.createPortGroup(outbound));
				
				topoType.getGroup().add(portType);
				
				// also add in/outbound ports within this loop
				TopologyRelationType portInbound = new TopologyRelationType();
				portInbound.setType(RELATION_INBOUND_PORT);
				portInbound.getPortGroup().add(port.getInbound().toPortGroupType());
				topoType.getRelation().add(portInbound);
				
				TopologyRelationType portOutbound = new TopologyRelationType();
				portOutbound.setType(RELATION_OUTBOUND_PORT);
				portOutbound.getPortGroup().add(port.getOutbound().toPortGroupType());
				topoType.getRelation().add(portOutbound);
			}
		}			
		
		return nsaType;
	}
}
