/**
 * 
 */
package net.geant.nsicontest.reservation.notification;

import java.util.Calendar;

import net.geant.nsicontest.core.Nsi;

import org.ogf.schemas.nsi._2013._12.connection.types.ReserveTimeoutRequestType;

/**
 * ReserveTimeout notification.
 */
public class ReserveTimeout extends Notification {
	
    private int timeoutValue;
    private String originatingConnectionId;
    private String originatingNSA;
    
    public ReserveTimeout() { }
    
    public ReserveTimeout(String connectionId, long notificationId, Calendar timeStamp, 
    		int timeoutValue, String originatingConnectionId, String originatingNSA) {
    	
    	super(connectionId, notificationId, timeStamp);
    	this.timeoutValue = timeoutValue;
		this.originatingConnectionId = originatingConnectionId;
		this.originatingNSA = originatingNSA;
    }
    
    public ReserveTimeout(String connectionId, long notificationId,  
    		int timeoutValue, String originatingConnectionId, String originatingNSA) {
    	
    	this(connectionId, notificationId, Calendar.getInstance(), timeoutValue, originatingConnectionId, originatingNSA);
    }
    
    public static ReserveTimeout fromReserveTimeoutRequestType(ReserveTimeoutRequestType reserveTimeout) {
		
		return new ReserveTimeout(reserveTimeout.getConnectionId(), reserveTimeout.getNotificationId(), reserveTimeout.getTimeStamp().toGregorianCalendar(), 
				reserveTimeout.getTimeoutValue(), reserveTimeout.getOriginatingConnectionId(), reserveTimeout.getOriginatingNSA());
	}
	
    @Override
	public ReserveTimeoutRequestType toNsiType() {
		
		ReserveTimeoutRequestType reserveTimeout = new ReserveTimeoutRequestType();
		reserveTimeout.setConnectionId(connectionId);
		reserveTimeout.setNotificationId(notificationId);
		reserveTimeout.setTimeStamp(Nsi.createXmlCalendar(timeStamp));
		reserveTimeout.setTimeoutValue(timeoutValue);
		reserveTimeout.setOriginatingConnectionId(originatingConnectionId);
		reserveTimeout.setOriginatingNSA(originatingNSA);		
		return reserveTimeout;
	}
    
    @Override
    public String toString() {
    	
    	return String.format("reserve timeout notification: %s,  %s", super.toString(), timeoutValue);
    }
	
	//
	// setters & getters
	//
    
	public int getTimeoutValue() {
		return timeoutValue;
	}
	
	public void setTimeoutValue(int timeoutValue) {
		this.timeoutValue = timeoutValue;
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
}
