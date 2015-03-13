/**
 * 
 */
package net.geant.nsicontest.topology.path;

import java.util.Map;

import net.geant.nsicontest.topology.Nsa;

/**
 * Defines methods for searching global topology (using internal types).
 */
public interface Pce {

	/**
	 * Searches for path spanning from source to dest, optionally may include ero
	 * @param nsas
	 * @param local
 	 * @param sourceStp
	 * @param destStp
	 * @param ero
	 * @return
	 */
	PathResult search(Map<String, Nsa> nsas, String localNsaId, String sourceStp, String destStp, String... ero);
	
}
