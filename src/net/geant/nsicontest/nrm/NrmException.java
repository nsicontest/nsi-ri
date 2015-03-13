/**
 * 
 */
package net.geant.nsicontest.nrm;

/**
 * Generic exception to be thrown by the NRM interface. 
 */
public class NrmException extends Exception {

	private static final long serialVersionUID = 1L;

	public NrmException() { }

	public NrmException(String message) {
		super(message);
	}

	public NrmException(Throwable cause) {
		super(cause);
	}

	public NrmException(String message, Throwable cause) {
		super(message, cause);
	}
}
