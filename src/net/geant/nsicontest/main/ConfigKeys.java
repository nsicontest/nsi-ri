/**
 * 
 */
package net.geant.nsicontest.main;

/**
 * Stores configuration file (default location at etc/nsi.properties) keys.
 * See that file for keys description.
 */
public enum ConfigKeys {

	IP,
	PORT,
	PROVIDER_NSA,	
	SERVICE_TYPE,
	RESERVE_HELD_TIMEOUT,
	FAIL_IF_RESPONSE_NOT_DELIVERED,
	SET_ERO,
	REQUESTER_TIMEOUT,
	LOG_HEADER, 
	LOG_SOAP_MESSAGES,
	LOG_RESPONDER, 
	NUM_THREADS,	
	NRM,
	TOPOLOGIES,
	CLI,
	TELNET_PORT,
	DISABLED_NSI_CONTEST,
	RABBITMQ_ENABLED,
	RABBITMQ_HOST,
}
