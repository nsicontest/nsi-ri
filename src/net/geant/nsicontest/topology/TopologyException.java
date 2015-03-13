/**
 * 
 */
package net.geant.nsicontest.topology;

/**
 * Generic topology exception.
 */
public class TopologyException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public TopologyException() { }

	public TopologyException(String message) {
		super(message);
	}

	public TopologyException(Throwable cause) {
		super(cause);
	}

	public TopologyException(String message, Throwable cause) {
		super(message, cause);
	}
}
