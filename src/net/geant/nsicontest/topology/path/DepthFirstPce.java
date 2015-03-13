/**
 * 
 */
package net.geant.nsicontest.topology.path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import net.geant.nsicontest.topology.BidirectionalPort;
import net.geant.nsicontest.topology.GlobalTopology;
import net.geant.nsicontest.topology.Nsa;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Implements depth-first searching.
 */
public class DepthFirstPce extends PceBase {
	
	final static private Logger log = Logger.getLogger(DepthFirstPce.class);
	
	/* (non-Javadoc)
	 * @see net.geant.nsicontest.topology.Pce#search(java.util.Collection, net.geant.nsicontest.topology.Nsa, java.lang.String, java.lang.String, java.lang.String[])
	 */
	@Override
	public PathResult search(Map<String, Nsa> nsas, String localNsaId, String sourceStp, String destStp, String... ero) {
		
		try {
			preSearch(nsas, localNsaId, sourceStp, destStp, ero);
		} catch (Exception e) {
			return new PathResult(e.getMessage());			
		}
		
		log.info("path type: " + pathType.name());
		PathResult path = new PathResult(pathType, sourcePort, sourceVlans, destPort, destVlans);		
			
		// in some cases there is no need to compute paths at all
		if (sourcePort.getNsa().equals(destPort.getNsa())) {			
			
			return path;
		}
		
		// find out traversal domains
		Stack<Nsa> stack = new Stack<Nsa>();
		List<List<Nsa>> paths = new ArrayList<List<Nsa>>();		
		search_r(sourcePort.getNsa(), destPort.getNsa(), stack, paths);
		
		if (paths.isEmpty()) {
			return new PathResult("no paths found");			
		}
		
		// for each path consisting of NSAs, find STP connectivity
		List<List<BidirectionalPort>> stpPaths = new ArrayList<List<BidirectionalPort>>();
		
		for (List<Nsa> p : paths) {
			
			if (p.size() < 2) {
				log.info("path with insufficient number of domains: " + p.size());
				continue;
			}
			
			List<BidirectionalPort> stpPath = getStpPath(p);
			stpPaths.add(stpPath);
			
			Collections.sort(stpPaths, new Comparator<List<BidirectionalPort>>() {

				@Override
				public int compare(List<BidirectionalPort> o1, List<BidirectionalPort> o2) {
					
					if (o1.size() > o2.size())
						return 1;
					else if (o1.size() < o2.size())
						return -1;
					return 0;
				}				
			});				
		}
		
		path.setPaths(stpPaths);
		return path;
	}
	
	private void search_r(Nsa nsa, Nsa dst, Stack<Nsa> stack, List<List<Nsa>> paths) {
		
		stack.push(nsa);
		
		// get neighbors
		List<Nsa> neighbors = new ArrayList<Nsa>();
		for (String s : nsa.getPeersWith()) {
			
			Nsa neighbor = nsas.get(s);
			if (neighbor == null) { // can happen if topology is partially loaded
				log.info("topology for nsa " + s + " not loaded");
			} else {
				neighbors.add(neighbor);
			}
		}
		
		for (Nsa n : neighbors) {
			
			// skip self and already visited
			if (n.equals(nsa) || stack.contains(n)) 
				continue;
			
			if (n == dst) {
				
				List<Nsa> path = new ArrayList<Nsa>();
				Iterator<Nsa> it = stack.iterator();
				while (it.hasNext())
					path.add(it.next());
				
				path.add(dst);
				paths.add(path);
				
			} else {
				search_r(n, dst, stack, paths);				
			}
		}		
		stack.pop();
	}
	
	private List<BidirectionalPort> getStpPath(List<Nsa> nsas) {
		
		List<BidirectionalPort> path = new ArrayList<BidirectionalPort>();
		
		for (int i=0; i<nsas.size() - 1; i++) {
			
			Nsa left = nsas.get(i);
			Nsa right = nsas.get(i + 1);
			
			BidirectionalPort[] sdps = getSdp(left, right);
			
			if (sdps != null) {
				path.add(sdps[0]);
				path.add(sdps[1]);
			}
		}
		
		return path;
	}
	
	// slow method of getting 
	private BidirectionalPort[] getSdp(Nsa fromNsa, Nsa toNsa) {
		
		for (BidirectionalPort fromPort : fromNsa.getBidirectionalPorts()) {
			
			String inboundId = fromPort.getOutbound().getAliasPort();
			
			for (BidirectionalPort toPort : toNsa.getBidirectionalPorts()) {
				
				if (toPort.getInbound().getId().equals(inboundId)) {
					
					return new BidirectionalPort[] { fromPort, toPort };
				}				
			}
		}
		
		return null;
	}
	
	public static void main(String... args) throws Exception {

		PropertyConfigurator.configure("etc/log4j.properties");
		
		GlobalTopology.getInstance().setLocalNsaId("urn:ogf:network:aruba.example:2013:nsa");
		GlobalTopology.getInstance().addFromFile("docs/aruba.xml");
		GlobalTopology.getInstance().addFromFile("docs/bonaire.xml");
		GlobalTopology.getInstance().addFromFile("docs/curacao.xml");
		GlobalTopology.getInstance().addFromFile("docs/dominica.xml");
		
		//System.out.println(GlobalTopology.getInstance().info());
		
		String src = "urn:ogf:network:aruba.example:2013:bi-ps";
		String dst = "urn:ogf:network:aruba.example:2013:bi-aruba-bonaire";
		String dst1 = "urn:ogf:network:bonaire.example:2013:bi-ps";
		String dst2 = "urn:ogf:network:curacao.example:2013:bi-ps";
		
		Pce pce = new DepthFirstPce();
		PathResult path = pce.search(GlobalTopology.getInstance().getAllNsas(), "urn:ogf:network:aruba.example:2013:nsa", src, dst1);
		
		System.out.println("NUMPATHS: " + path.getPaths().size());		
		for (List<BidirectionalPort> p : path.getPaths()) {
			System.out.println("PATH: " + p.size());			
			for (BidirectionalPort bp : p) {				
				System.out.println("STP: " + bp.getId());
			}			
		}
	}
}
