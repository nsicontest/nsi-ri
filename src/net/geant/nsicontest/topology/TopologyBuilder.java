/**
 * 
 */
package net.geant.nsicontest.topology;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Provides simplified methods to create nsi/nml topologies
 */
public class TopologyBuilder {
	
	static private final Logger log = Logger.getLogger(TopologyBuilder.class);
	
	static private final String PREFIX = "urn:ogf:network:";
	static private final String SERVICE_TYPE = "application/vnd.org.ogf.nsi.cs.v2+soap";
	
	private Map<String, Nsa> nsas = new HashMap<String, Nsa>();
		
	/**
	 * Clears all internal nsas that have been added so far
	 */
	public void clear() {
		
		nsas.clear();
		log.info("internal topology cleared");
	}
	
	private void checkNsaId(String nsaId, boolean added) {
		
		if (nsaId == null || nsaId.isEmpty()) {			
			log.info("nsaId null or empty");
			throw new IllegalArgumentException("nsaId");
		}
		
		boolean found = nsas.containsKey(nsaId);
		
		if (found && !added) {		
			log.info(nsaId + " already added");
			throw new IllegalArgumentException(nsaId + " already added");
		} else if (!found && added) {
			log.info(nsaId + " not added");
			throw new IllegalArgumentException(nsaId + " not added");
		}		
	}
		
	/**
	 * Creates new Nsa object 
	 * @param nsaId
	 * @param link
	 * @param adminContact
	 * @param latitude
	 * @param longitude
	 * @param version
	 * @param topologyName
	 */
	public void addNsa(String nsaId, String link, String adminContact, float latitude, float longitude, Calendar version, String topologyName) {
		
		checkNsaId(nsaId, false);		
		
		Location location = new Location(latitude, longitude);
		Service service = new Service(PREFIX + nsaId + ":2013:nsa-provserv", SERVICE_TYPE, link); 
		Topology topo = new Topology(topologyName);
		
		Nsa nsa = new Nsa(PREFIX + nsaId + ":2013:nsa", version, location, service, null, null, null);
			
		nsas.put(nsaId, nsa);
		log.info(nsaId + " added");		
	}
		
	public void addStp(String nsaId, String stpId, String vlans) {
	
		checkNsaId(nsaId, true);		
		
	}
	
	public void peerNsa(String nsaId, String remoteNsaId) {

		checkNsaId(nsaId, true);
		
	}
	
	public void peerAllNsa() {
	
		
	}
	
	public void save() {
		
		for (Nsa nsa : nsas.values()) {

			try {
				TopologyParser.save(nsa.getId(), nsa);
			} catch (TopologyException e) {
				log.info(String.format("topology %s not saved - " + nsa.getId(), e.getMessage()));				
			}
		}
	}
}
