/**
 * 
 */
package net.geant.nsicontest.reservation;

import java.util.Arrays;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

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
 * Synchronous version of Requester, it offers same functionality except
 * all methods block until completed (or specified timeout occurs). 
 */
public class SynchronousRequster extends Requester {
	
	public final static int DEFAULT_TIMEOUT = 180;
	private enum Method { RESERVE, COMMIT, ABORT, PROVISION, RELEASE, TERMINATE };
	private EnumSet<Method> awaiting = EnumSet.noneOf(Method.class);
	private Object sync = new Object();
	private int timeout = DEFAULT_TIMEOUT;
			

	public SynchronousRequster(String connectionId, String globalReservationId, String description, Integer version, Calendar startTime, Calendar endTime) {
		super(connectionId, globalReservationId, description, version, startTime, endTime);
		
		addListener(new Listener());
	}
	
	/**
	 * This method blocks current thread and waits for specific event or timeout.
	 * @param methods
	 * @throws Exception
	 */
	private void waitFor(Method... methods) throws Exception {
		
		if (!awaiting.isEmpty()) throw new Exception("already awaiting an event: " + awaiting);
		
		awaiting.addAll(Arrays.asList(methods));
		log.info("awaiting: " + awaiting);
		
		synchronized (sync) {
			sync.wait(timeout * 1000L);
		}
		
		// timeout if awaiting not cleared by wakeUp
		if (!awaiting.isEmpty()) {
			
			String err = String.format("timeout while awaiting: %s", awaiting);
			awaiting.clear();
			log.info(err);
			throw new Exception(err);
		}
	}

	/**
	 * This method signals waitFor that some message arrived. To be called from listener.
	 * @param methods
	 */
	private void wakeUp(Method... methods) {
		
		// see if something arrived that was not expected
		if (awaiting.isEmpty()) {			
			log.info("unexpected messages arrived: " + Arrays.toString(methods));
			return;			
		}
		
		log.info("messages arrived: " + Arrays.toString(methods));
		
		// see if that is what is expected
		if (!awaiting.containsAll(Arrays.asList(methods))) {
			
			// do nothing this will cause an exception to be thrown from waitFor
			
		} else {
			
			log.info("clearing awaiting state");
			awaiting.clear();
		}
		
		// whether responses were correct or not, wake up
		synchronized (sync) {			
			sync.notify();
		}
	}

	private void checkReplyTo() throws Exception {
		
		if (replyTo == null || replyTo.isEmpty())
			throw new Exception("replyTo not set");
	}
	
	@Override
	public void reserve() throws Exception {
		
		checkReplyTo();				
		super.reserve();
		waitFor(Method.RESERVE);
		
	}
	
	@Override
	public void modify() throws Exception {
		
		checkReplyTo();
		super.modify();
		waitFor(Method.RESERVE);
	}
	
	@Override
	public void reserveCommit() throws Exception {
		
		checkReplyTo();
		super.reserveCommit();
		waitFor(Method.COMMIT);
	}
	
	@Override
	public void reserveAbort() throws Exception {
		
		checkReplyTo();
		super.reserveAbort();
		waitFor(Method.ABORT);
	}
	
	@Override
	public void provision() throws Exception { 
		
		checkReplyTo();
		super.provision();
		waitFor(Method.PROVISION);
	}
	
	@Override
	public void release() throws Exception {
		
		checkReplyTo();
		super.release();
		waitFor(Method.RELEASE);
	}
	
	@Override
	public void terminate() throws Exception { 
		
		checkReplyTo();
		super.terminate();
		waitFor(Method.TERMINATE);
	}
		
	// ConnectionRequesterPort listener is needed be set.
	// SynchronousRequester already implements it and we could just override it,
	// however it is not so convienient with code editor so we just have 
	// private class here.
	
	private class Listener implements ConnectionRequesterPort {

		@Override
		public void errorEvent(String connectionId, long notificationId, XMLGregorianCalendar timeStamp, EventEnumType event,
				String originatingConnectionId, String originatingNSA, TypeValuePairListType additionalInfo,
				ServiceExceptionType serviceException, Holder<CommonHeaderType> header) throws ServiceException {
						
		}

		@Override
		public void queryResultConfirmed(List<QueryResultResponseType> result, Holder<CommonHeaderType> header) throws ServiceException {
			
		}

		@Override
		public void reserveAbortConfirmed(String connectionId, Holder<CommonHeaderType> header) throws ServiceException {
		
			wakeUp(Method.ABORT);
		}

		@Override
		public void reserveFailed(String connectionId, ConnectionStatesType connectionStates,
				ServiceExceptionType serviceException, Holder<CommonHeaderType> header) throws ServiceException {
			
			wakeUp(Method.RESERVE);
		}

		@Override
		public void messageDeliveryTimeout(String connectionId,	long notificationId, XMLGregorianCalendar timeStamp,
				String correlationId, Holder<CommonHeaderType> header) throws ServiceException {
			
		}

		@Override
		public void reserveConfirmed(String connectionId, String globalReservationId, String description,
				ReservationConfirmCriteriaType criteria, Holder<CommonHeaderType> header) throws ServiceException {
			
			wakeUp(Method.RESERVE);
		}

		@Override
		public void dataPlaneStateChange(String connectionId, long notificationId, XMLGregorianCalendar timeStamp,
				DataPlaneStatusType dataPlaneStatus, Holder<CommonHeaderType> header) throws ServiceException {
			
		}

		@Override
		public void reserveTimeout(String connectionId, long notificationId, XMLGregorianCalendar timeStamp, int timeoutValue,
				String originatingConnectionId, String originatingNSA, Holder<CommonHeaderType> header) throws ServiceException {
			
		}

		@Override
		public void releaseConfirmed(String connectionId, Holder<CommonHeaderType> header) throws ServiceException {
			
			wakeUp(Method.RELEASE);
		}

		@Override
		public void reserveCommitConfirmed(String connectionId,	Holder<CommonHeaderType> header) throws ServiceException {
			
			wakeUp(Method.COMMIT);
		}

		@Override
		public void reserveCommitFailed(String connectionId, ConnectionStatesType connectionStates, ServiceExceptionType serviceException,
				Holder<CommonHeaderType> header) throws ServiceException {
			
			wakeUp(Method.COMMIT);
		}

		@Override
		public GenericAcknowledgmentType queryNotificationConfirmed(QueryNotificationConfirmedType queryNotificationConfirmed,
				Holder<CommonHeaderType> header) throws ServiceException {

			return null;
		}

		@Override
		public void error(ServiceExceptionType serviceException, Holder<CommonHeaderType> header) throws ServiceException {
			
		}

		@Override
		public void terminateConfirmed(String connectionId,	Holder<CommonHeaderType> header) throws ServiceException {
			
			wakeUp(Method.TERMINATE);
		}

		@Override
		public void querySummaryConfirmed(List<QuerySummaryResultType> reservation, Holder<CommonHeaderType> header) throws ServiceException {
			
		}

		@Override
		public void provisionConfirmed(String connectionId, Holder<CommonHeaderType> header) throws ServiceException {
		
			wakeUp(Method.PROVISION);
		}

		@Override
		public void queryRecursiveConfirmed(List<QueryRecursiveResultType> reservation, 	Holder<CommonHeaderType> header) throws ServiceException {
			
		}
	}
}
