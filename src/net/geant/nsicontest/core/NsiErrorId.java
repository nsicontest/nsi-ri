/**
 * 
 */
package net.geant.nsicontest.core;

/**
 * Convince class with error codes as found in the specification. 
 * NsiErrorId main purpose is to be passed as argument to NsiError.
 */
final public class NsiErrorId {
	
	private String errorId;
	private String text;
	
	public NsiErrorId() { }
	
	public NsiErrorId(String errorId, String text) { 
		
		this.errorId = errorId;
		this.text = text;
	}
	
	public String getErrorId() {
		return errorId;
	}

	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() { 
		return String.format("%s, %s", errorId, text); 
	}
	
	// error codes as found in the specification (16.1)
	
	// validation/connection errors
	public static final NsiErrorId NO_ERROR = new NsiErrorId("00000", "No error occured");
	public static final NsiErrorId PAYLOAD_ERROR = new NsiErrorId("00100", "Payload error");
	public static final NsiErrorId MISSING_PARAMETER = new NsiErrorId("00101", "Invalid or missing parameter");
	public static final NsiErrorId UNSUPPORTED_PARAMETER = new NsiErrorId("00102", "A provided request parameter that must be processed " +
			"contains an unsupported value");
	public static final NsiErrorId NOT_IMPLEMENTED = new NsiErrorId("00103", "Not implemented");
	public static final NsiErrorId VERSION_NOT_SUPPORTED = new NsiErrorId("00104", "The service version requested in NSI header is not supported");
	public static final NsiErrorId CONNECTION_ERROR = new NsiErrorId("00200", "Connection error");
	public static final NsiErrorId INVALID_TRANSITION = new NsiErrorId("00201", "Connection state machine is in invalid stated for received message");
	public static final NsiErrorId CONNECTION_EXISTS = new NsiErrorId("00202", "Schedule already exists for connectionId");
	public static final NsiErrorId CONNECTION_NONEXISTENT = new NsiErrorId("00203", "Schedule does not exist for connectionId");
	public static final NsiErrorId CONNECTION_GONE = new NsiErrorId("00204", "Connection gone");
	public static final NsiErrorId CONNECTION_CREATE_ERROR = new NsiErrorId("00205", "Failed to create connection (payload was ok, " +
			"something went wrong");
	
	// AA errors
	public static final NsiErrorId SECURITY_ERROR = new NsiErrorId("00300", "Security error");
	public static final NsiErrorId AUTHENTICATION_FAILURE = new NsiErrorId("00301", "Authentication failure");
	public static final NsiErrorId UNAUTHORIZED = new NsiErrorId("00302", "Unauthorized");
	
	// processing erros
	public static final NsiErrorId TOPOLOGY_ERROR = new NsiErrorId("00400", "Topology error");
	public static final NsiErrorId NO_PATH_FOUND = new NsiErrorId("00403", "Path computation failed to resolve route for reservation");
	public static final NsiErrorId INTERNAL_ERROR = new NsiErrorId("00500", "An internal error has caused a message processing failure");
	public static final NsiErrorId INTERNAL_NRM_ERROR = new NsiErrorId("00501", "An internal NRM error has caused a message processing failure");
	public static final NsiErrorId RESOURCE_UNAVAILABLE = new NsiErrorId("00600", "Resource unavailable");
	public static final NsiErrorId SERVICE_ERROR = new NsiErrorId("00700", "Parent error classification for a service-specific error");
	
	// point-to-point service (19.3 in the specification)
	public static final NsiErrorId UNKNOWN_STP = new NsiErrorId("00701", "Could not find STP in topology database");
	public static final NsiErrorId STP_RESOLUTION_ERROR = new NsiErrorId("00702", "Could not resolve STP to a managing NSA");
	public static final NsiErrorId VLANID_INTERCHANGE_NOT_SUPPORTED = new NsiErrorId("00703", "VLAN interchange not supported for requested path");
	public static final NsiErrorId STP_UNAVAILABLE = new NsiErrorId("00704", "Specified STP already in use");
	public static final NsiErrorId CAPACITY_UNAVAILABLE = new NsiErrorId("00705", "Insufficient capacity available for reservation");
}
