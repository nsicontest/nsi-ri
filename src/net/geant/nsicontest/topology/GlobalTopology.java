/**
 * 
 */
package net.geant.nsicontest.topology;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.ogf.schemas.nsi._2013._09.topology.NSAType;

/**
 * Topology repository for NSAType. Each nsa can upload or delete
 * its topology using this service.
 */
public class GlobalTopology implements TopologyService {

	private static Logger log = Logger.getLogger(GlobalTopology.class);

	private static final GlobalTopology instance = new GlobalTopology();
		
	private ConcurrentMap<String, NSAType> nsaTypeMap = new ConcurrentHashMap<String, NSAType>();	
	private ConcurrentMap<String, Nsa> nsaMap = new ConcurrentHashMap<String, Nsa>();	
	private String localNsaId; 
	
	private GlobalTopology() { }
	
	public static GlobalTopology getInstance() {
		
		return instance;
	}
	
	public void setLocalNsaId(String nsaId) {
				
		localNsaId = nsaId;
		log.info("this instance nsa id: " + localNsaId);
	}
	
	public String getLocalNsaId() {
		
		return localNsaId;
	}
	
	//
	// support for topology files and command line
	//
	
	/**
	 * Read nsi/nml topology from file.
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static NSAType fromFile(String filename) throws Exception {
		
		File f = new File(filename);
		if (!f.exists()) {
			throw new Exception(filename + " does not exist");
		}
		
		JAXBContext context = JAXBContext.newInstance("org.ogf.schemas.nsi._2013._09.topology");
		Unmarshaller unmar = context.createUnmarshaller();
		JAXBElement<?> elem  = (JAXBElement<?>)unmar.unmarshal(f);
		NSAType nsa = (NSAType)elem.getValue();
		return nsa;	
	}
	
	public void addFromFile(String filename) throws Exception {
	
		create(fromFile(filename));		
	}	
	
	public void addOrUpdateFromFile(String filename) throws Exception {
	
		NSAType nsa = fromFile(filename);
		String nsaId = getNsaId(nsa);
		
		if (!nsaTypeMap.containsKey(nsaId)) {
			create(nsa);
		} else {
			update(nsa);			
		}
	}	
	
	public void clear() {
		
		nsaTypeMap.clear();
		nsaMap.clear();
		log.info("topology cleared");
	}
	
	public String info() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("global view of topology:");
		for (Nsa n : nsaMap.values()) {			
			sb.append("\r\n" + n.toStringDetailed());						
		}		
		return sb.toString();
	}
	
	//
	// Internal data structures expose to path computation
	//
	
	public Map<String, Nsa> getAllNsas() {
		
		return Collections.unmodifiableMap(nsaMap);
	}
	
	//
	// TopologyService implementation
	//
	
	private String getNsaId(NSAType nsa) throws TopologyException {
		
		if (nsa == null) throw new TopologyException("invalid value - nsa");
		if (nsa.getId() == null) throw new TopologyException("invalid value - nsaId");
		if (nsa.getId().isEmpty()) throw new TopologyException("empty value - nsaId");
				
		return nsa.getId();
	}
	
	@Override
	public void create(NSAType nsa) throws TopologyException {
	
		String nsaId = getNsaId(nsa);
		
		if (nsaTypeMap.containsKey(nsaId)) { 			
			log.info(String.format("%s already exist,  cannot create", nsaId));
			throw new TopologyException(nsaId + " already exist, use update"); 
		}
		
		Nsa n = TopologyParser.create(nsa);
		nsaMap.put(nsaId, n);		
		nsaTypeMap.put(nsaId, nsa);		
		log.info("topology create " + nsaId);
	}

	@Override
	public void delete(String nsaId) throws TopologyException {
		
		if (!nsaTypeMap.containsKey(nsaId)) {
			log.info(nsaId + " does not exist, cannot delete");
			throw new TopologyException(nsaId + " does not exist");
		}
		
		nsaMap.remove(nsaId);
		nsaTypeMap.remove(nsaId);
		log.info("topology delete " + nsaId);
	}

	@Override
	public NSAType update(NSAType nsa) throws TopologyException {
		
		String nsaId = getNsaId(nsa);
		
		if (!nsaTypeMap.containsKey(nsaId)) {
			log.info(nsaId + " does not exist, cannot update");
			throw new TopologyException(nsaId + " does not exist");
		}
		
		nsaMap.remove(nsaId);
		NSAType oldNsa = nsaTypeMap.remove(nsaId);
		create(nsa);
		log.info("topology update " + nsaId);
		return oldNsa;
	}

	@Override
	public NSAType get(String nsaId) {
		
		return nsaTypeMap.get(nsaId);
	}

	@Override
	public List<NSAType> getAll() {

		return Collections.unmodifiableList(new ArrayList<NSAType>(nsaTypeMap.values()));
	}
}
