/**
 * 
 */
package net.geant.nsicontest.nrm;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Represents NSI to NRM interface. This interface is to be implemented by local NRM (DM).
 */
@WebService(targetNamespace = "http://nrm.nsicontest.geant.net/", name = "Nrm")
public interface Nrm {
	
	/**
	 * Typically called at startup time (depending on NSI configuration may be invoked at any time),
	 * asks NRM for a list of edge ports. Port identifier can be as follow:
	 * portId - represents port with 4-4096 vlan range
	 * portId?vlan=XX - where XX is a number or range, i.e. vlan=1000 or vlan=1000-2000
	 * @return
	 */
	@WebMethod
	Topology getTopology();
	
	/**
	 * Requests Nrm to behave according to configure parameters, this method is intended for Nrm debugging.
	 * @param parameters
	 * @throws NrmException
	 */
	@WebMethod
	void configure(@WebParam(name="parameters")NrmParameters parameters) throws NrmException;

	/**
	 * Request to reserve local resources as described by ReserveParameters and connectionId literal
	 * (guaranteed to be unique). This is always the first call for newly created reservations. 
	 * NRM is expected to figure out if it can support resources at specified time. Please note that if:
	 * - start time is not specified (null), it counts as from now on
	 * - end time is not specified (null), reservation never expires itself (can only be cancelled) 
	 *   
	 * Single or multiple (range) vlans can be requested, NRM must return single vlan chosen
	 * for source and destination port respectively.
	 * 
	 * @param connectionId
	 * @param params
	 * @return
	 * @throws NrmException
	 */
	@WebMethod
	ReserveOutcome reserve(@WebParam(name="connectionId")String connectionId, @WebParam(name="params")ReserveParameters params) throws NrmException;
		
	/**
	 * Request to alter existing resources. NRM should expect commit or abort calls next.
	 * @param connectionId
	 * @param params
	 * @return
	 * @throws NrmException
	 */
	@WebMethod
	ReserveOutcome modify(@WebParam(name="connectionId")String connectionId, @WebParam(name="params")ReserveParameters params) throws NrmException;
	
	/**
	 * Requested resources with reserve/modify calls are expected to be firmly booked and ready to use
	 * after completion of this call.
	 * @param connectionId
	 * @throws NrmException
	 */
	@WebMethod
	void commit(@WebParam(name="connectionId")String connectionId) throws NrmException;
	
	/**
	 * Previously requested resources either with reserve or modify call should be abandoned by NRM.
	 * Further reserve calls are possible for aborted reservations.
	 * @param connectionId
	 */
	@WebMethod
	void abort(@WebParam(name="connectionId")String connectionId);
	
	/**
	 * Requests data plane activation. Maybe called multiple times during lifetime of reservation
	 * between start and finish time, even if data plane is already activated.
	 * @param connectionId
	 * @throws NrmException
	 */
	@WebMethod
	void activate(@WebParam(name="connectionId")String connectionId) throws NrmException;
	
	/**
	 * Requests data plane deactivation. Maybe called multiple times during lifetime of reservation
	 * between start and finish time, even if data plane is already deactivated.
	 * @param connectionId
	 * @throws NrmException
	 */
	@WebMethod
	void deactivate(@WebParam(name="connectionId")String connectionId) throws NrmException;
	
	/**
	 * Orders to dispose reservation (including data plane). Once terminate has been called, such reservation 
	 * will not receive any further requests. Terminate can occur as a result of the following events:
	 * 1) reservation naturally expired
	 * 2) cancellation was requested
	 * 3) one of the domains involved in reservation went down and dispatched forced-end message 
	 * 
	 * @param connectionId
	 */
	@WebMethod
	void terminate(@WebParam(name="connectionId")String connectionId);
}
