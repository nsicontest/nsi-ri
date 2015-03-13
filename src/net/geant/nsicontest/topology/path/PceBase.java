/**
 * 
 */
package net.geant.nsicontest.topology.path;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.geant.nsicontest.core.Range;
import net.geant.nsicontest.topology.BidirectionalPort;
import net.geant.nsicontest.topology.DecomposedStp;
import net.geant.nsicontest.topology.Nsa;

/**
 * Provides STPs checking and path classification. Subclasses can focus on path computation only.
 */
public abstract class PceBase implements Pce {
	
	protected Map<String, Nsa> nsas;
	protected String localNsaId;
	protected Nsa local;
	
	protected BidirectionalPort sourcePort, destPort;
	protected Range sourceVlans, destVlans; 
	protected PathType pathType;
	protected List<BidirectionalPort> ero = new ArrayList<BidirectionalPort>();
	
	protected void preSearch(Map<String, Nsa> nsas, String localNsaId, String sourceStp, String destStp, String... ero) throws Exception {
		
		this.nsas = nsas;
		this.localNsaId = localNsaId;
		this.local = nsas.get(localNsaId);
		
		if (local == null) throw new Exception("local nsa not found in topology");
		
		DecomposedStp decomposedSource = DecomposedStp.parse(sourceStp);
		Nsa sourceNsa = nsas.get(decomposedSource.getNetworkIdWithNsa());
		
		if (sourceNsa == null) {
			String s = String.format("source networkId not found - " + decomposedSource.getNetworkIdWithNsa());
			throw new Exception(s);
		}
		
		sourcePort = sourceNsa.getBidirectionalPort(decomposedSource.getStpWithoutLabel());
		if (sourcePort == null) {
			String s = String.format("source localId %s not found in %s" + decomposedSource.getStpWithoutLabel(), sourceNsa.getId());
			throw new Exception(s);							
		}
		sourceVlans = Range.create(decomposedSource.getLabel());
		
		DecomposedStp decomposedDest = DecomposedStp.parse(destStp);
		Nsa destNsa = nsas.get(decomposedDest.getNetworkIdWithNsa());
		
		if (destNsa == null) {
			String s = String.format("dest networkId not found - " + decomposedDest.getNetworkIdWithNsa());
			throw new Exception(s);
		}
		
		destPort = destNsa.getBidirectionalPort(decomposedDest.getStpWithoutLabel());
		if (destPort == null) {
			String s = String.format("dest localId %s not found in %s" + decomposedDest.getStpWithoutLabel(), destNsa.getId());
			throw new Exception(s);							
		}
		destVlans = Range.create(decomposedDest.getLabel());
		
		// classify the path
		if (sourceNsa.equals(local)) {
			pathType = destNsa.equals(local) ? PathType.LOCAL_DOMAIN : PathType.LOCAL_EXTERNAL_DOMAINS;
		} else {
			pathType = PathType.EXTERNAL_DOMAINS;
		}
	}		 
}
