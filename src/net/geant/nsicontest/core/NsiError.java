/**
 * 
 */
package net.geant.nsicontest.core;

import org.ogf.schemas.nsi._2013._12.connection._interface.ServiceException;
import org.ogf.schemas.nsi._2013._12.framework.types.ServiceExceptionType;

/**
 * Roughly corresponds to NSI ServiceExceptionType
 */
final public class NsiError {
	
	private String nsaId; 		 // mandatory
	private String connectionId; // optional
	private String serviceType;  // optional
	private String errorId; 	 // mandatory
	private String text;		 // mandatory
	
	// TODO
	// variables 		// optional
	// childException   // optional
	
	public NsiError() { }
	
	public NsiError(String nsaId, String connectionId, String serviceType, String errorId, String text) {

		this.nsaId = nsaId;
		this.connectionId = connectionId;
		this.serviceType = serviceType;
		this.errorId = errorId;
		this.text = text;
	}
	
	public NsiError(String nsaId, String errorId, String text) {

		this(nsaId, null, null, errorId, text);
	}
	
	public NsiError(String nsaId, String connectionId, String serviceType, NsiErrorId error) {
		
		this(nsaId, connectionId, serviceType, error.getErrorId(), error.getText());
	}
	
	public NsiError(String nsaId, NsiErrorId error) {
		
		this(nsaId, null, null, error.getErrorId(), error.getText());
	}
	
	public String getNsaId() {
		return nsaId;
	}

	public void setNsaId(String nsaId) {
		this.nsaId = nsaId;
	}

	public String getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
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

	public ServiceExceptionType toServiceExceptionType() {
		
		ServiceExceptionType serviceEx = new ServiceExceptionType();
		serviceEx.setNsaId(nsaId);
		serviceEx.setConnectionId(connectionId);
		serviceEx.setServiceType(serviceType);
		serviceEx.setErrorId(errorId);
		serviceEx.setText(text);
		serviceEx.setVariables(null);
		return serviceEx;
	}
	
	public ServiceException toServiceException(String message) {
		
		return new ServiceException(message, toServiceExceptionType());
	}
		
	static public ServiceException create(String message, String nsaId, String connectionId, String serviceType, NsiErrorId error) {
		
		ServiceExceptionType serviceEx = new ServiceExceptionType();
		serviceEx.setNsaId(nsaId);
		serviceEx.setConnectionId(connectionId);
		serviceEx.setServiceType(serviceType);
		serviceEx.setErrorId(error.getErrorId());
		serviceEx.setText(error.getText());
		serviceEx.setVariables(null);
		
		return new ServiceException(message, serviceEx); 
	}
	
	static public ServiceException create(String message, String nsaId, NsiErrorId error) {
		
		return create(message, nsaId, null, null, error);
	}
	
	static public NsiError fromServiceExceptionType(ServiceExceptionType serviceEx) {
		
		if (serviceEx == null)
			return null;
		
		return new NsiError(serviceEx.getNsaId(), serviceEx.getConnectionId(), serviceEx.getServiceType(), serviceEx.getErrorId(), serviceEx.getText());
	}
	
	@Override
	public String toString() { 
		
		return String.format("nsa: %s, connectionId: %s\nerrorId: %s, %s", nsaId, connectionId, errorId, text);		
	}
}
