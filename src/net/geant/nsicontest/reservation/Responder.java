/**
 *
 */
package net.geant.nsicontest.reservation;



import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import net.geant.nsicontest.core.Nsi;
import net.geant.nsicontest.reservation.notification.NotificationManager;

import org.apache.log4j.Logger;
import org.ogf.schemas.nsi._2013._12.connection.requester.ConnectionRequesterPort;
import org.ogf.schemas.nsi._2013._12.connection.requester.ConnectionServiceRequester;
import org.ogf.schemas.nsi._2013._12.connection.types.ConnectionStatesType;
import org.ogf.schemas.nsi._2013._12.connection.types.DataPlaneStatusType;
import org.ogf.schemas.nsi._2013._12.connection.types.EventEnumType;
import org.ogf.schemas.nsi._2013._12.connection.types.ReservationConfirmCriteriaType;
import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;
import org.ogf.schemas.nsi._2013._12.framework.types.ServiceExceptionType;
import org.ogf.schemas.nsi._2013._12.framework.types.TypeValuePairListType;

/**
 * Helper class used by Provider to send responses back to Requester.
 * All operations are blocking calls (can take some time to complete),
 * it is expected they will be called from background threads.
 */
final public class Responder {
	
	private static final Logger log = Logger.getLogger(Responder.class);
	
	private String endpoint;					// where to send response to
	private int numTries, delayBetweenTries;    // how many times we should try to deliver response and at what rate
	private ConnectionRequesterPort requester;
	
	public static Boolean logEnabled;
	
	public Responder(String endpoint, int numTries, int delayBetweenTries) {

		// endpoint can be null or empty
		assert numTries > 0;
		assert delayBetweenTries >= 0;
		this.endpoint = endpoint;
		this.numTries = numTries;
		this.delayBetweenTries = delayBetweenTries;
		
		if (endpoint != null && !endpoint.isEmpty()) { 
			ConnectionServiceRequester service = new ConnectionServiceRequester(endpoint);
			requester = service.getConnectionServiceRequesterPort();
		}
	}
	
	public Responder(String endpoint) { this(endpoint, 1, 0); }
	
	private void logMethod(String connectionId, String method) {
		
		if (logEnabled && endpoint != null) {
			log.info(String.format("%s responds with %s to %s", connectionId, method, endpoint));
		}
	}
	
	private void logMethodDelivered(String connectionId, String method) {
		
		if (logEnabled && endpoint != null) {
			log.info(String.format("%s %s delivered to %s", connectionId, method, endpoint));
		}
	}
	
	private void logMethodFail(String connectionId, String method, Throwable exception) {
		
		if (logEnabled && endpoint != null) {
			log.info(String.format("%s %s could not be delivered to %s due to %s", connectionId, method, endpoint, exception.getMessage()));
            log.debug("Error info: ", exception);
		}
	}
	
	public boolean reserveConfirmed(Holder<CommonHeaderType> header, String connectionId, String globalReservationId, String description,
			ReservationConfirmCriteriaType criteria) {
		
		String method = "reserveConfirmed";
		for (int i = 0; i < numTries; i++) {
			logMethod(connectionId, method);
			try {
				if (requester != null)
					requester.reserveConfirmed(connectionId, globalReservationId, description, criteria, header);
				logMethodDelivered(connectionId, method);
				return true;
			} catch (Exception e) {
				logMethodFail(connectionId, method, e);
			}
			
			if (delayBetweenTries > 0) {
				try {
					Thread.sleep(delayBetweenTries);
				} catch (Exception e) { }
			}
		}		
		log.info(String.format("%s %s was not delivered to %s", connectionId, method, endpoint));
		return false;
	}
	
	public boolean reserveFailed(Holder<CommonHeaderType> header, String connectionId, ConnectionStatesType states, ServiceExceptionType ex) {
		
		String method = "reserveFailed";
		for (int i = 0; i < numTries; i++) {
			logMethod(connectionId, method);
			try {
				if (requester != null)
					requester.reserveFailed(connectionId, states, ex, header);
				logMethodDelivered(connectionId, method);
				return true;
			} catch (Exception e) {
				logMethodFail(connectionId, method, e);
			}
			try {
				Thread.sleep(delayBetweenTries);
			} catch (Exception e) { }
		}		
		log.info(String.format("%s %s was not delivered to %s", connectionId, method, endpoint));
		return false;
	}
	
	public boolean reserveAbortConfirmed(Holder<CommonHeaderType> header, String connectionId) { 
		
		String method = "reserveAbortConfirmed";
		for (int i = 0; i < numTries; i++) {
			logMethod(connectionId, method);
			try {
				if (requester != null)
					requester.reserveAbortConfirmed(connectionId, header);
				logMethodDelivered(connectionId, method);
				return true;
			} catch (Exception e) {
				logMethodFail(connectionId, method, e);
			}
			try {
				Thread.sleep(delayBetweenTries);
			} catch (Exception e) { }
		}		
		log.info(String.format("%s %s was not delivered to %s", connectionId, method, endpoint));
		return false;
	}
	
	public boolean reserveCommitConfirmed(Holder<CommonHeaderType> header, String connectionId) { 
		
		String method = "reserveCommitConfirmed";
		for (int i = 0; i < numTries; i++) {
			logMethod(connectionId, method);
			try {
				if (requester != null)
					requester.reserveCommitConfirmed(connectionId, header);
				logMethodDelivered(connectionId, method);
				return true;
			} catch (Exception e) {
				logMethodFail(connectionId, method, e);
			}
			try {
				Thread.sleep(delayBetweenTries);
			} catch (Exception e) { }
		}		
		log.info(String.format("%s %s was not delivered to %s", connectionId, method, endpoint));
		return false;
	}
	
	public boolean reserveCommitFailed(Holder<CommonHeaderType> header, String connectionId, ConnectionStatesType states, ServiceExceptionType ex) { 
		
		String method = "reserveCommitFailed";
		for (int i = 0; i < numTries; i++) {
			logMethod(connectionId, method);
			try {
				if (requester != null)
					requester.reserveCommitFailed(connectionId, states, ex, header);
				logMethodDelivered(connectionId, method);
				return true;
			} catch (Exception e) {
				logMethodFail(connectionId, method, e);
			}
			try {
				Thread.sleep(delayBetweenTries);
			} catch (Exception e) { }
		}		
		log.info(String.format("%s %s was not delivered to %s", connectionId, method, endpoint));
		return false;
	}
	
	public boolean reserveTimeout(Holder<CommonHeaderType> header, String connectionId, int timeout, long notificationId, String originNSA) { 
		
		XMLGregorianCalendar cal = Nsi.createXmlCalendar();
		NotificationManager.addReserveTimeout(connectionId, notificationId, cal, timeout, connectionId, originNSA);

		String method = "reserveTimeout";
		for (int i = 0; i < numTries; i++) {
			logMethod(connectionId, method);
			try {
				if (requester != null) 					
					requester.reserveTimeout(connectionId, notificationId, cal, timeout, connectionId, originNSA, header);
				logMethodDelivered(connectionId, method);
				return true;
			} catch (Exception e) {
				logMethodFail(connectionId, method, e);
			}
			try {
				Thread.sleep(delayBetweenTries);
			} catch (Exception e) { }
		}
		log.info(String.format("%s %s was not delivered to %s", connectionId, method, endpoint));
		return false;
	}
	
	public boolean provisionConfirmed(Holder<CommonHeaderType> header, String connectionId) { 
		
		String method = "provisionConfirmed";
		for (int i = 0; i < numTries; i++) {
			logMethod(connectionId, method);
			try {
				if (requester != null)
					requester.provisionConfirmed(connectionId, header);
				logMethodDelivered(connectionId, method);
				return true;
			} catch (Exception e) {
				logMethodFail(connectionId, method, e);
			}
			try {
				Thread.sleep(delayBetweenTries);
			} catch (Exception e) { }
		}		
		log.info(String.format("%s %s was not delivered to %s", connectionId, method, endpoint));
		return false;
	}
		
	public boolean releaseConfirmed(Holder<CommonHeaderType> header, String connectionId) {
		
		String method = "releaseConfirmed";
		for (int i = 0; i < numTries; i++) {
			logMethod(connectionId, method);
			try {
				if (requester != null)
					requester.releaseConfirmed(connectionId, header);
				logMethodDelivered(connectionId, method);
				return true;
			} catch (Exception e) {
				logMethodFail(connectionId, method, e);
			}
			try {
				Thread.sleep(delayBetweenTries);
			} catch (Exception e) { }
		}
		log.info(String.format("%s %s was not delivered to %s", connectionId, method, endpoint));
		return false;
	}
	
	public boolean terminateConfirmed(Holder<CommonHeaderType> header, String connectionId) {
		
		String method = "terminateConfirmed";
		for (int i = 0; i < numTries; i++) {
			logMethod(connectionId, method);
			try {
				if (requester != null)
					requester.terminateConfirmed(connectionId, header);
				logMethodDelivered(connectionId, method);
				return true;
			} catch (Exception e) {
				logMethodFail(connectionId, method, e);								
			}
			try {
				Thread.sleep(delayBetweenTries);
			} catch (Exception e) { }
		}
		log.info(String.format("%s %s was not delivered to %s", connectionId, method, endpoint));
		return false;
	}
	
	public boolean dataPlaneStateChanged(Holder<CommonHeaderType> header, String connectionId, long notificationId,
			boolean active, int version, boolean versionConsistent) {
		
		XMLGregorianCalendar cal = Nsi.createXmlCalendar();
		NotificationManager.addDataPlaneStateChange(connectionId, notificationId, cal, active, version, versionConsistent);
		
		String method = "dataPlaneStateChanged";
		for (int i = 0; i < numTries; i++) {
			logMethod(connectionId, method);
			try {
				if (requester != null) {
					
					DataPlaneStatusType status = new DataPlaneStatusType();
					status.setActive(active);
					status.setVersion(version);
					status.setVersionConsistent(versionConsistent);
					requester.dataPlaneStateChange(connectionId, notificationId, cal, status, header);
				}
				logMethodDelivered(connectionId, method);
				return true;
			} catch (Exception e) {
				logMethodFail(connectionId, method, e);								
			}
			try {
				Thread.sleep(delayBetweenTries);
			} catch (Exception e) { }
		}
		log.info(String.format("%s %s was not delivered to %s", connectionId, method, endpoint));
		return false;
	}
	
	public boolean errorEvent(Holder<CommonHeaderType> header, String connectionId, Long notificationId, EventEnumType event, 
			String originatingConnectionId, String originatingNSA, 
			TypeValuePairListType additionalInfo, ServiceExceptionType serviceException) {
		
		XMLGregorianCalendar cal = Nsi.createXmlCalendar();
		NotificationManager.addErrorEvent(connectionId, notificationId, cal, event, originatingConnectionId, originatingNSA,
				additionalInfo, serviceException);
		
		String method = "errorEvent";
		for (int i = 0; i < numTries; i++) {
			logMethod(connectionId, method);
			try {
				if (requester != null) {
					requester.errorEvent(connectionId, notificationId, cal, event, originatingConnectionId, originatingNSA, 
							additionalInfo, serviceException, header);
				}
				logMethodDelivered(connectionId, method);
				return true;
			} catch (Exception e) {
				logMethodFail(connectionId, method, e);								
			}
			try {
				Thread.sleep(delayBetweenTries);
			} catch (Exception e) { }
		}
		log.info(String.format("%s %s was not delivered to %s", connectionId, method, endpoint));
		return false;
	}
	
	public boolean messageDeliveryTimeout(Holder<CommonHeaderType> header, String connectionId, int notificationId, String correlationId) {
		
		XMLGregorianCalendar cal = Nsi.createXmlCalendar();
		NotificationManager.addMessageDeliveryTimeout(connectionId, notificationId, cal, correlationId);
		
		String method = "messageDeliveryTimeout";
		for (int i = 0; i < numTries; i++) {
			logMethod(connectionId, method);
			try {
				if (requester != null)
					requester.messageDeliveryTimeout(connectionId, notificationId, cal, correlationId, header);
				logMethodDelivered(connectionId, method);
				return true;
			} catch (Exception e) {
				logMethodFail(connectionId, method, e);								
			}
			try {
				Thread.sleep(delayBetweenTries);
			} catch (Exception e) { }
		}
		log.info(String.format("%s %s was not delivered to %s", connectionId, method, endpoint));
		return false;
	}
	
	public boolean error(Holder<CommonHeaderType> header, ServiceExceptionType serviceException) {

		log.info(String.format("responds with error to %s", endpoint));
		for (int i = 0; i < numTries; i++) {			
			try {
				if (requester != null)
					requester.error(serviceException, header);
				log.info(String.format("error delivered to %s", endpoint));
				return true;
			} catch (Exception e) {
				log.info(String.format("error could not be delivered to %s due to %s", endpoint, e.getMessage()));
                log.debug("Error info: ", e);
			}
			try {
				Thread.sleep(delayBetweenTries);
			} catch (Exception e) { }
		}
		log.info(String.format("error was not delivered to %s", endpoint));
		return false;		
	}
}
