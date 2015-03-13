/**
 * 
 */
package net.geant.nsicontest.topology;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.ogf.schemas.nsi._2013._09.topology.NSAType;

/**
 * Defines CRUD operations for Topology service. Single topology entry is represented as NSAType.
 * TODO implement this as a REST service once specification is available.
 */
@WebService(targetNamespace = "http://topology.nsicontest.geant.net/", name = "TopologyService")
public interface TopologyService {
	
	/**
	 * Adds new nsa to topology service
	 * @param nsa
	 * @throws TopologyException
	 */
	@WebMethod
	void create(@WebParam(name="nsa")NSAType nsa) throws TopologyException;
	
	/**
	 * Removes nsa from topology service identified by nsaId 
	 * @param nsaId
	 * @throws TopologyException
	 */
	@WebMethod
	void delete(@WebParam(name="nsaId")String nsaId) throws TopologyException;
	
	/**
	 * Updates existing nsa, old version is returned
	 * @param nsa
	 * @return
	 * @throws TopologyException
	 */
	@WebMethod
	NSAType update(@WebParam(name="nsa")NSAType nsa) throws TopologyException;
	
	/**
	 * Gets topology for nsa represented by nsaId
	 * @param nsaId
	 * @return
	 */
	@WebMethod
	NSAType get(@WebParam(name="nsaId")String nsaId);
		
	/**
	 * Gets all topologies stored in this service
	 * @return
	 */
	@WebMethod
	List<NSAType> getAll();
}
