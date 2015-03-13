/**
 * 
 */
package net.geant.nsicontest.reservation.notification;

import java.util.Calendar;

import net.geant.nsicontest.core.Nsi;

import org.ogf.schemas.nsi._2013._12.connection.types.MessageDeliveryTimeoutRequestType;

/**
 * MessageDeliveryTimeout notification.
 */
public class MessageDeliveryTimeout extends Notification {

	private String correlationId;
	
	public MessageDeliveryTimeout() { }
	
	public MessageDeliveryTimeout(String connectionId, long notificationId, Calendar timeStamp, String correlationId) {

    	super(connectionId, notificationId, timeStamp);
    	this.correlationId = correlationId;
	}
	
	public MessageDeliveryTimeout(String connectionId, long notificationId, String correlationId) {
		
		this(connectionId, notificationId, Calendar.getInstance(), correlationId);
	}
	
	public static MessageDeliveryTimeout fromMessageDeliveryTimeoutRequestType(MessageDeliveryTimeoutRequestType messageTimeout) {
		
		return new MessageDeliveryTimeout(messageTimeout.getConnectionId(), messageTimeout.getNotificationId(), 
				messageTimeout.getTimeStamp().toGregorianCalendar(), messageTimeout.getCorrelationId());
	}
	
	@Override
	public MessageDeliveryTimeoutRequestType toNsiType() {
		
		MessageDeliveryTimeoutRequestType messageTimeout = new MessageDeliveryTimeoutRequestType();
		messageTimeout.setConnectionId(connectionId);
		messageTimeout.setNotificationId(notificationId);
		messageTimeout.setTimeStamp(Nsi.createXmlCalendar(timeStamp));
		messageTimeout.setCorrelationId(correlationId);
		return messageTimeout;
	}
	
	@Override
	public String toString() {
		
		return String.format("message delivery timeout notification: %s", super.toString());
	}

	//
	// setters & getters
	//
	
	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
}
