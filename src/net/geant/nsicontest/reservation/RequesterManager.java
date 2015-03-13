/**
 * 
 */
package net.geant.nsicontest.reservation;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import org.apache.log4j.Logger;
import org.ogf.schemas.nsi._2013._12.connection._interface.ServiceException;
import org.ogf.schemas.nsi._2013._12.connection.requester.ConnectionRequesterPort;
import org.ogf.schemas.nsi._2013._12.connection.types.ConnectionStatesType;
import org.ogf.schemas.nsi._2013._12.connection.types.DataPlaneStatusType;
import org.ogf.schemas.nsi._2013._12.connection.types.EventEnumType;
import org.ogf.schemas.nsi._2013._12.connection.types.GenericAcknowledgmentType;
import org.ogf.schemas.nsi._2013._12.connection.types.QueryNotificationConfirmedType;
import org.ogf.schemas.nsi._2013._12.connection.types.QueryRecursiveResultType;
import org.ogf.schemas.nsi._2013._12.connection.types.QueryResultResponseType;
import org.ogf.schemas.nsi._2013._12.connection.types.QuerySummaryResultType;
import org.ogf.schemas.nsi._2013._12.connection.types.ReservationConfirmCriteriaType;
import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;
import org.ogf.schemas.nsi._2013._12.framework.types.ServiceExceptionType;
import org.ogf.schemas.nsi._2013._12.framework.types.TypeValuePairListType;

/**
 * Redirects message flow from the ConnectionRequesterPortImpl to registered Requester identified by its connectionId.
 */
final public class RequesterManager implements ConnectionRequesterPort {
	
	final static private Logger log = Logger.getLogger(RequesterManager.class);
		
	// map of requesters with key being connectionId
	final static private ConcurrentMap<String, Requester> requesters = new ConcurrentHashMap<String, Requester>();
	
	final static private ConnectionRequesterPort instance = new RequesterManager();
	
	private RequesterManager() { }
	
	public static ConnectionRequesterPort getInstance() {
		
		return instance;
	}
	
	public static void add(Requester requester) {
		
		if (requester == null)		
			throw new IllegalArgumentException("requester not set");
		
		String conId = requester.getConnectionId();
		if (conId == null || conId.isEmpty())
			throw new IllegalArgumentException("connectionId not set");
				
		requesters.put(conId, requester);
		log.info("added requester - " + conId);		
	}
	
	public static String getConnectionsId() {
		
		StringBuilder sb = new StringBuilder();
		for (Requester req : requesters.values())
			sb.append(req.getConnectionId() + "\r\n");
		
		return sb.toString();
	}
	
	public Requester get(String connectionId) {
						
		return requesters.get(connectionId);
	}
	
	private Requester getByReservationId(String reservationId) {
		
		for (Requester req : requesters.values()) {
			
			if (req.getGlobalReservationId().equals(reservationId))
				return req;
		}		
		return null;
	}

	
	//
	// ConnectionRequesterPort 
	//

	@Override
	public void errorEvent(String connectionId, long notificationId, XMLGregorianCalendar timeStamp, EventEnumType event,
			String originatingConnectionId, String originatingNSA, TypeValuePairListType additionalInfo,
			ServiceExceptionType serviceException, Holder<CommonHeaderType> header) throws ServiceException {
		
		Requester req = get(connectionId);
		
		if (req != null) {			
			req.errorEvent(connectionId, notificationId, timeStamp, event, 
				originatingConnectionId, originatingNSA, additionalInfo, serviceException, header);
		} else {			
			log.info("received errorEvent with unknown connectionId - " + connectionId);
		}
	}

	@Override
	public void queryResultConfirmed(List<QueryResultResponseType> result, Holder<CommonHeaderType> header) throws ServiceException {
		
		// TODO print it here
	}

	@Override
	public void reserveAbortConfirmed(String connectionId, Holder<CommonHeaderType> header) throws ServiceException {
		
		Requester req = get(connectionId);
		
		if (req != null) {			
			req.reserveAbortConfirmed(connectionId, header);
		} else {			
			log.info("received reserveAbortConfirmed with unknown connectionId - " + connectionId);
		}
	}

	@Override
	public void reserveFailed(String connectionId, ConnectionStatesType connectionStates,
			ServiceExceptionType serviceException, Holder<CommonHeaderType> header) throws ServiceException {
		
		Requester req = get(connectionId);
		
		if (req != null) {			
			req.reserveFailed(connectionId, connectionStates, serviceException, header);
		} else {			
			log.info("received reserveFailed with unknown connectionId - " + connectionId);
		}
	}

	@Override
	public void messageDeliveryTimeout(String connectionId, long notificationId, XMLGregorianCalendar timeStamp,
			String correlationId, Holder<CommonHeaderType> header) throws ServiceException {
		
		Requester req = get(connectionId);
		
		if (req != null) {			
			req.messageDeliveryTimeout(connectionId, notificationId, timeStamp, correlationId, header);
		} else {			
			log.info("received messageDeliveryTimeout with unknown connectionId - " + connectionId);
		}
	}

	@Override
	public void reserveConfirmed(String connectionId, String globalReservationId, String description,
			ReservationConfirmCriteriaType criteria, Holder<CommonHeaderType> header) throws ServiceException {
		
		Requester req = get(connectionId);
		
		if (req != null) {			
			req.reserveConfirmed(connectionId, globalReservationId, description, criteria, header);
		} else {			
			log.info("received reserveConfirmed with unknown connectionId - " + connectionId);
		}
	}

	@Override
	public void dataPlaneStateChange(String connectionId, long notificationId, XMLGregorianCalendar timeStamp,
			DataPlaneStatusType dataPlaneStatus, Holder<CommonHeaderType> header) throws ServiceException {
		
		Requester req = get(connectionId);
		
		if (req != null) {			
			req.dataPlaneStateChange(connectionId, notificationId, timeStamp, dataPlaneStatus, header);
		} else {			
			log.info("received dataPlaneStateChange with unknown connectionId - " + connectionId);
		}
	}

	@Override
	public void reserveTimeout(String connectionId, long notificationId, XMLGregorianCalendar timeStamp, int timeoutValue,
			String originatingConnectionId, String originatingNSA, Holder<CommonHeaderType> header) throws ServiceException {
		
		Requester req = get(connectionId);
		
		if (req != null) {			
			req.reserveTimeout(connectionId, notificationId, timeStamp, timeoutValue, originatingConnectionId, originatingNSA, header);
		} else {			
			log.info("received reserveTimeout with unknown connectionId - " + connectionId);
		}
	}

	@Override
	public void releaseConfirmed(String connectionId, Holder<CommonHeaderType> header) throws ServiceException {
		
		Requester req = get(connectionId);
		
		if (req != null) {			
			req.releaseConfirmed(connectionId, header);
		} else {			
			log.info("received releaseConfirmed with unknown connectionId - " + connectionId);
		}
	}

	@Override
	public void reserveCommitConfirmed(String connectionId, Holder<CommonHeaderType> header) throws ServiceException {
		
		Requester req = get(connectionId);
		
		if (req != null) {			
			req.reserveCommitConfirmed(connectionId, header);
		} else {			
			log.info("received reserveCommitConfirmed with unknown connectionId - " + connectionId);
		}
	}

	@Override
	public void reserveCommitFailed(String connectionId, ConnectionStatesType connectionStates,
			ServiceExceptionType serviceException, Holder<CommonHeaderType> header) throws ServiceException {
		
		Requester req = get(connectionId);
		
		if (req != null) {			
			req.reserveCommitFailed(connectionId, connectionStates, serviceException, header);
		} else {			
			log.info("received reserveCommitFailed with unknown connectionId - " + connectionId);
		}
	}

	@Override
	public GenericAcknowledgmentType queryNotificationConfirmed(QueryNotificationConfirmedType queryNotificationConfirmed,
			Holder<CommonHeaderType> header) throws ServiceException {

		// TODO print it here
		return null;
	}

	@Override
	public void error(ServiceExceptionType serviceException, Holder<CommonHeaderType> header) throws ServiceException {

		// TODO print it here
	}

	@Override
	public void terminateConfirmed(String connectionId,	Holder<CommonHeaderType> header) throws ServiceException {

		Requester req = get(connectionId);
		
		if (req != null) {			
			req.terminateConfirmed(connectionId, header);
		} else {			
			log.info("received terminateConfirmed with unknown connectionId - " + connectionId);
		}
	}

	@Override
	public void querySummaryConfirmed(List<QuerySummaryResultType> reservation,	Holder<CommonHeaderType> header) throws ServiceException {
		
		// TODO print it here
	}

	@Override
	public void provisionConfirmed(String connectionId, Holder<CommonHeaderType> header) throws ServiceException {
		
		Requester req = get(connectionId);
		
		if (req != null) {			
			req.provisionConfirmed(connectionId, header);
		} else {			
			log.info("received provisionConfirmed with unknown connectionId - " + connectionId);
		}
	}

	@Override
	public void queryRecursiveConfirmed(List<QueryRecursiveResultType> reservation,	Holder<CommonHeaderType> header) throws ServiceException {
		
		// TODO print it here
	}
}
