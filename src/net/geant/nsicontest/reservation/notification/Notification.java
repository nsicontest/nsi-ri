/**
 * 
 */
package net.geant.nsicontest.reservation.notification;

import java.util.Calendar;

import org.ogf.schemas.nsi._2013._12.connection.types.NotificationBaseType;

/**
 * Provides serialization for NotifcationBaseType and derived classes.
 */
public abstract class Notification {

	protected String connectionId;
	protected long notificationId;    
    protected Calendar timeStamp;
	
	public Notification() { }

	public Notification(String connectionId, long notificationId, Calendar timeStamp) {

		this.connectionId = connectionId;
		this.notificationId = notificationId;		
		this.timeStamp = timeStamp;
	}
	
	/**
	 * Derived classes must return appropriate nsi type
	 * @return
	 */
	public abstract NotificationBaseType toNsiType();
	
	@Override
	public String toString() {
		
		return String.format("%s, %s, %s", connectionId, notificationId, timeStamp.getTime());
	}
	
	//
	// setters & getters
	//

	public long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
	}

	public String getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}
	
	public Calendar getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Calendar timeStamp) {
		this.timeStamp = timeStamp;
	}
}
