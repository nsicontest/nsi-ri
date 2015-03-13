/**
 * 
 */
package net.geant.nsicontest.topology.path;

/**
 * For given two Stps (source and destination) a path between them can be calculated.
 * The path can be categorized with regards to type of domains it goes through. 
 */
public enum PathType {
	
	INVALID_PATH, // non-existent path, i.e. one of the Stps could not be found in topology
	LOCAL_DOMAIN, // source and destination belong to a domain where this path has been calculated
	LOCAL_EXTERNAL_DOMAINS, // source belongs to local domain whereas destination is found in other domain
	EXTERNAL_DOMAINS, // source and destination are places outside local domain 
}
