/**
 * 
 */
package net.geant.nsicontest.reservation.notification;

import java.util.Calendar;

import net.geant.nsicontest.core.Nsi;
import net.geant.nsicontest.reservation.Coordinator;

import org.ogf.schemas.nsi._2013._12.connection.types.ErrorEventType;
import org.ogf.schemas.nsi._2013._12.connection.types.EventEnumType;
import org.ogf.schemas.nsi._2013._12.framework.types.ServiceExceptionType;
import org.ogf.schemas.nsi._2013._12.framework.types.TypeValuePairListType;

/**
 * Error event notification.
 */
public class ErrorEvent extends Notification {

	private String event;
	private String originatingConnectionId;
	private String originatingNSA;
	private String errorId;
	private String text;	
	
	// TODO
	//TypeValuePairListType additionalInfo; 
	//ServiceExceptionType serviceException;
	
	public ErrorEvent() { }
	
	public ErrorEvent(String connectionId, long notificationId, Calendar timeStamp, 
			String event, String originatingConnectionId, String originatingNSA,
			TypeValuePairListType additionalInfo, ServiceExceptionType serviceException) {
		
		super(connectionId, notificationId, timeStamp);
		this.event = event;
		this.originatingConnectionId = originatingConnectionId;
		this.originatingNSA = originatingNSA;
		this.errorId = serviceException.getErrorId();
		this.text = serviceException.getText();
	}
	
	public ErrorEvent(String connectionId, long notificationId, 
			String event, String originatingConnectionId, String originatingNSA,
			TypeValuePairListType additionalInfo, ServiceExceptionType serviceException) {
		
		this(connectionId, notificationId, Calendar.getInstance(), event, originatingConnectionId, originatingNSA, additionalInfo, serviceException);
	}

	public static ErrorEvent fromErrorEventType(ErrorEventType error) {
		
		return new ErrorEvent(error.getConnectionId(), error.getNotificationId(), error.getTimeStamp().toGregorianCalendar(),
				error.getEvent().value(), error.getOriginatingConnectionId(), error.getOriginatingNSA(),
				error.getAdditionalInfo(), error.getServiceException());
	}
	
	@Override
	public ErrorEventType toNsiType() {
		
		ErrorEventType error = new ErrorEventType();
		error.setConnectionId(connectionId);
		error.setNotificationId(notificationId);
		error.setTimeStamp(Nsi.createXmlCalendar(timeStamp));
		error.setEvent(EventEnumType.fromValue(event));
		error.setOriginatingConnectionId(originatingConnectionId);
		error.setOriginatingNSA(originatingNSA);
		error.setAdditionalInfo(null); 
		
		ServiceExceptionType serviceEx = new ServiceExceptionType();
		serviceEx.setNsaId(Coordinator.getProviderNSA());
		serviceEx.setConnectionId(connectionId);
		serviceEx.setServiceType(Coordinator.getServiceType());
		serviceEx.setErrorId(errorId);
		serviceEx.setText(text);		
		error.setServiceException(serviceEx);
		return error;
	}
	
	@Override
	public String toString() {
		
		return String.format("error notification: %s,  %s", super.toString(), event);
	}
	
	// 
	// setters & getters
	//
	
	public String getEvent() {
		return event;
	}
	
	public void setEvent(String event) {
		this.event = event;
	}
	
	public String getOriginatingConnectionId() {
		return originatingConnectionId;
	}
	
	public void setOriginatingConnectionId(String originatingConnectionId) {
		this.originatingConnectionId = originatingConnectionId;
	}
	
	public String getOriginatingNSA() {
		return originatingNSA;
	}
	
	public void setOriginatingNSA(String originatingNSA) {
		this.originatingNSA = originatingNSA;
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

}
