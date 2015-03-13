/**
 * 
 */
package net.geant.nsicontest.nrm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.geant.nsicontest.core.Range;
import net.geant.nsicontest.main.Config;

import org.apache.log4j.Logger;

/**
 * Simple (for testing purposes) implementation of the NRM interface. There is only instance of NRM.
 * Simplification: reserved vlans are held in global list, they should be rather associated with STPs
 */
public class SimpleNrm implements Nrm {
	
	final static private String CONFIG_FILE = "etc/nrm.properties"; 
	final static private Logger log = Logger.getLogger(SimpleNrm.class);
	
	private List<String> stps;
	private Range availableVlans;
	private long maxCapacity;		
	
	// track resource usage
	private long usedCapacity;
	private List<Integer> usedVlans = new ArrayList<Integer>(); 
	private Map<String, Resources> resources = new HashMap<String, Resources>();
	
	private class Resources {		
		
		long capacity;
		Integer srcVlan, dstVlan;
		
		Resources(long capacity, Integer srcVlan, Integer dstVlan) {
			this.capacity = capacity;
			this.srcVlan = srcVlan;
			this.dstVlan = dstVlan;
		}
	}
	
	public SimpleNrm() throws Exception {
		
		reload();
	}
	
	// to call it at any time: ((SimpleNrm)SimpleNrm.getInstance).reload();
	public void reload() throws Exception {
		
		Config config = Config.load(CONFIG_FILE, NrmKeys.values());
		
		String commaStps = config.getString(NrmKeys.STPS);
		this.availableVlans = Range.create(config.getString(NrmKeys.VLANS));
		this.maxCapacity = config.getLong(NrmKeys.MAX_CAPACITY);
				
		this.stps = new ArrayList<String>();
		
		if (!commaStps.isEmpty()) {
			String[] stps = commaStps.split(",");
			for (String s : stps) {				
				this.stps.add(s.trim());
			}
		}
						
		log.info(String.format("stps: %s, vlans: %s, capacity: %s", 
				Arrays.toString(this.stps.toArray(new String[this.stps.size()])), this.availableVlans, this.maxCapacity));
	}	
	
	private void checkStp(String stp) throws NrmException {
		
		if (!stps.contains(stp)) {			
			String s = stp + " not found";
			log.info(s);
			throw new NrmException(s);
		}
	}
	
	private Integer getVlan(Range range) {
		
		for (Integer i = range.min(); i != range.max(); i++) {			
			if (!usedVlans.contains(i))
				return i;
		}
		return 0;
	}
	
	synchronized private ReserveOutcome addResources(String connectionId, long capacity, Range srcVlans, Range dstVlans) throws NrmException {

		long cap = capacity + usedCapacity;
		
		if (cap > maxCapacity) {
			String s = String.format("oversubcribed capacity by %s", maxCapacity - cap);
			log.info(s);
			throw new NrmException(s);
		}
		
		Range srcRange = availableVlans.subRange(srcVlans);
		if (srcRange == null) {
			String s = "source vlans outside available vlan range";
			log.info(s);
			throw new NrmException(s);
		}
		
		Integer srcVlan = getVlan(srcRange);
		if (srcVlan == 0) {
			String s = "cannot reserve any of source vlans";
			log.info(s);
			throw new NrmException(s);
		}
		
		Range dstRange = availableVlans.subRange(dstVlans);
		if (dstRange == null) {
			String s = "dst vlans outside available vlan range";
			log.info(s);
			throw new NrmException(s);
		}
		
		Integer dstVlan = getVlan(dstRange);
		if (dstVlan == 0) {
			String s = "cannot reserve any of dest vlans";
			log.info(s);		
			throw new NrmException(s);
		}
			
		usedCapacity += capacity;
		usedVlans.add(srcVlan);
		usedVlans.add(dstVlan);
		resources.put(connectionId, new Resources(capacity, srcVlan, dstVlan));
		
		log.info(String.format("added resources - capacity: %s, src vlan: %s, dst vlan: %s", capacity, srcVlan, dstVlan));
		return new ReserveOutcome(srcVlan, dstVlan);
	}
	
	synchronized private void freeResources(String connectionId) {

		if (!resources.containsKey(connectionId))
			return;

		Resources res = resources.remove(connectionId);
		usedCapacity -= res.capacity;
		usedVlans.remove(res.srcVlan);
		usedVlans.remove(res.dstVlan);
	
		log.info(String.format("freed resources - capacity: %s,  src vlan: %s, dst vlan: %s", res.capacity, res.srcVlan, res.dstVlan));
	}
	
	@Override
	public Topology getTopology() {
		
		Topology topo = new Topology();
		
		topo.setNsaId("urn:ogf:network:test.domain:2013:nsa"); // value copied from nsi.properties - provider_nsa
		topo.setLink("http://localhost:8080/nsicontest/ConnectionProvider"); // combination of ip and port from nsi.properties 
		topo.setTopologyName("test.nsa");
		topo.setVersion(Calendar.getInstance());		
		topo.setAdminContact("");
		
		// no peering with other domains, only endpoints				
		for (String s : stps) {
			
			GroupPort inbound = new GroupPort(s + "-in", availableVlans.toString(), null);
			GroupPort outbound = new GroupPort(s + "-out", availableVlans.toString(), null);
			BidirPort port = new BidirPort(s, inbound, outbound);						
			topo.addBidirPort(port);
		}				
		return topo;
	}
	
	@Override
	public void configure(NrmParameters parameters) throws NrmException {
		
		
	}

	/* (non-Javadoc)
	 * @see net.geant.nsicontest.nrm.Nrm#reserve(java.lang.String, net.geant.nsicontest.nrm.ReserveParameters)
	 */
	@Override
	public ReserveOutcome reserve(String connectionId, ReserveParameters params) throws NrmException {
		
		if (resources.containsKey(connectionId))
			throw new NrmException("connectionId already exists"); // should never happen
		
		// not checking STPs because they should be already checked in global topology
		// real nrm should have some kind of STP to port mapping though
		//checkStp(params.getSourceLocalId());
		//checkStp(params.getDestLocalId());
		return addResources(connectionId, params.getCapacity(), params.getSourceVlans(), params.getDestVlans());
	}

	/* (non-Javadoc)
	 * @see net.geant.nsicontest.nrm.Nrm#modify(java.lang.String, net.geant.nsicontest.nrm.ReserveParameters)
	 */
	@Override
	public ReserveOutcome modify(String connectionId, ReserveParameters params)	throws NrmException {
		
		if (!resources.containsKey(connectionId))
			throw new NrmException("no resources to modify");
		
		//checkStp(params.getSourceLocalId());
		//checkStp(params.getDestLocalId());

		// remove old resources, then add requested ones
		freeResources(connectionId);
		return addResources(connectionId, params.getCapacity(), params.getSourceVlans(), params.getDestVlans());
	}

	/* (non-Javadoc)
	 * @see net.geant.nsicontest.nrm.Nrm#commit(java.lang.String)
	 */
	@Override
	public void commit(String connectionId) throws NrmException {

		// by not throwing exception, commit is successful
	}

	/* (non-Javadoc)
	 * @see net.geant.nsicontest.nrm.Nrm#abort(java.lang.String)
	 */
	@Override
	public void abort(String connectionId) {

		freeResources(connectionId);
	}

	/* (non-Javadoc)
	 * @see net.geant.nsicontest.nrm.Nrm#activate(java.lang.String)
	 */
	@Override
	public void activate(String connectionId) throws NrmException {

	}

	/* (non-Javadoc)
	 * @see net.geant.nsicontest.nrm.Nrm#deactivate(java.lang.String)
	 */
	@Override
	public void deactivate(String connectionId) throws NrmException {

	}

	/* (non-Javadoc)
	 * @see net.geant.nsicontest.nrm.Nrm#terminate(java.lang.String)
	 */
	@Override
	public void terminate(String connectionId) {

		freeResources(connectionId);
	}
}
