/**
 * 
 */
package net.geant.nsicontest.reservation.notification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.xml.datatype.XMLGregorianCalendar;

import org.ogf.schemas.nsi._2013._12.connection.types.EventEnumType;
import org.ogf.schemas.nsi._2013._12.connection.types.NotificationBaseType;
import org.ogf.schemas.nsi._2013._12.framework.types.ServiceExceptionType;
import org.ogf.schemas.nsi._2013._12.framework.types.TypeValuePairListType;

/**
 * Stores notifications that have been sent by this instance. Stored notifications can be retrieved by RA.
 * For getNotifications, in case of lack notifications, an empty collection is returned rather than null.
 */
public class NotificationManager {
	
	private static ConcurrentMap<String, List<Notification>> notifications = new ConcurrentHashMap<String, List<Notification>>(); 
	
	/**
	 * Adds new Notification (or derived classes)
	 * @param notification
	 */
	static public void addNotification(Notification notification) {
					
		List<Notification> nots = notifications.get(notification.getConnectionId());
		
		if (nots == null) {
			
			nots = new ArrayList<Notification>();
			nots.add(notification);
			notifications.put(notification.getConnectionId(), nots);
			
		} else {			
			nots.add(notification);
		}
	}
	
	/**
	 * Removes all stored notifications
	 */
	static public void clear() {
		
		notifications.clear();
	}
	
	/**
	 * Retrieves notifications denoted by connectionId
	 * @param connectionId
	 * @param startNotificationId
	 * @param endNotificationId
	 * @return
	 */
	static public Collection<Notification> getNotifications(String connectionId, long startNotificationId, long endNotificationId) {
		
		List<Notification> result = new ArrayList<Notification>();		
		List<Notification> nots = notifications.get(connectionId);
		
		if (nots != null) {
		
			for (Notification not : nots) {			
				if (not.getNotificationId() >= startNotificationId && not.getNotificationId() <= endNotificationId)
					result.add(not);
			}
		}				
		return Collections.unmodifiableCollection(result);
	}
	
	/**
	 * Retrieves notifications denoted by connectionId in NSI format
	 * @param connectionId
	 * @param startNotificationId
	 * @param endNotificationId
	 * @return
	 */
	static public Collection<NotificationBaseType> getNotificationsAsNsi(String connectionId, long startNotificationId, long endNotificationId) {
		
		List<NotificationBaseType> result = new ArrayList<NotificationBaseType>();
		Collection<Notification> nots = getNotifications(connectionId, startNotificationId, endNotificationId);
		
		if (!nots.isEmpty()) {				
			for (Notification n : nots) {
				result.add(n.toNsiType());
			}
		} 		
		return Collections.unmodifiableCollection(result);
	}
	
	/**
	 * Adds DataPlaneChange notification to database
	 * @param connectionId
	 * @param notificationId
	 * @param timeStamp
	 * @param active
	 * @param version
	 * @param versionConsistent
	 */
	public static void addDataPlaneStateChange(String connectionId, long notificationId, XMLGregorianCalendar timeStamp, 
			boolean active, int version, boolean versionConsistent) {
		
		DataPlaneChange not = new DataPlaneChange(connectionId, notificationId, timeStamp.toGregorianCalendar(), active, version, versionConsistent);
		addNotification(not);
	}
	
	/**
	 * Adds ReserveTimeout notification to database
	 * @param connectionId
	 * @param notificationId
	 * @param timeStamp
	 * @param timeoutValue
	 * @param originatingConnectionId
	 * @param originatingNSA
	 */
	public static synchronized void addReserveTimeout(String connectionId, long notificationId, XMLGregorianCalendar timeStamp,
			int timeoutValue, String originatingConnectionId, String originatingNSA) {
		
		ReserveTimeout not = new ReserveTimeout(connectionId, notificationId, timeStamp.toGregorianCalendar(), 
				timeoutValue, originatingConnectionId, originatingNSA);
		
		addNotification(not);
	}
	
	/**
	 * Adds MessageDeliveryTimeout notification to database
	 * @param connectionId
	 * @param notificationId
	 * @param timeStamp
	 * @param correlationId
	 */
	public static synchronized void addMessageDeliveryTimeout(String connectionId, long notificationId, XMLGregorianCalendar timeStamp,
			String correlationId) {
		
		MessageDeliveryTimeout not = new MessageDeliveryTimeout(connectionId, notificationId, timeStamp.toGregorianCalendar(), correlationId);
		addNotification(not);		
	}
	
	/**
	 * Adds ErrorEvent notification to database
	 * @param connectionId
	 * @param notificationId
	 * @param timeStamp
	 * @param event
	 * @param originatingConnectionId
	 * @param originatingNSA
	 * @param additionalInfo
	 * @param serviceException
	 */
	public static synchronized void addErrorEvent(String connectionId, long notificationId, XMLGregorianCalendar timeStamp,
			EventEnumType event,  String originatingConnectionId, String originatingNSA, 
			TypeValuePairListType additionalInfo, ServiceExceptionType serviceException) {

		ErrorEvent not = new ErrorEvent(connectionId, notificationId, timeStamp.toGregorianCalendar(), event.value(), 
				originatingConnectionId, originatingNSA, additionalInfo, serviceException);
		
		addNotification(not);
	}
}
