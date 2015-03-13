/**
 * 
 */
package net.geant.nsicontest.reservation;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import net.geant.nsicontest.core.Nsi;
import net.geant.nsicontest.core.NsiInfo;

import org.apache.log4j.Logger;
import org.ogf.schemas.nsi._2013._12.connection._interface.ServiceException;
import org.ogf.schemas.nsi._2013._12.connection.provider.ConnectionProviderPort;
import org.ogf.schemas.nsi._2013._12.connection.provider.ConnectionServiceProvider;
import org.ogf.schemas.nsi._2013._12.connection.requester.ConnectionRequesterPort;
import org.ogf.schemas.nsi._2013._12.connection.types.ConnectionStatesType;
import org.ogf.schemas.nsi._2013._12.connection.types.DataPlaneStatusType;
import org.ogf.schemas.nsi._2013._12.connection.types.EventEnumType;
import org.ogf.schemas.nsi._2013._12.connection.types.GenericAcknowledgmentType;
import org.ogf.schemas.nsi._2013._12.connection.types.QueryNotificationConfirmedType;
import org.ogf.schemas.nsi._2013._12.connection.types.QueryNotificationType;
import org.ogf.schemas.nsi._2013._12.connection.types.QueryRecursiveResultType;
import org.ogf.schemas.nsi._2013._12.connection.types.QueryResultResponseType;
import org.ogf.schemas.nsi._2013._12.connection.types.QuerySummaryConfirmedType;
import org.ogf.schemas.nsi._2013._12.connection.types.QuerySummaryResultType;
import org.ogf.schemas.nsi._2013._12.connection.types.QueryType;
import org.ogf.schemas.nsi._2013._12.connection.types.ReservationConfirmCriteriaType;
import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;
import org.ogf.schemas.nsi._2013._12.framework.types.ServiceExceptionType;
import org.ogf.schemas.nsi._2013._12.framework.types.TypeValuePairListType;

/**
 * Implements Requester role.
 */
public class Requester extends Reservation implements ConnectionRequesterPort {
	
	final static protected Logger log = Logger.getLogger(Requester.class);
	
	protected String providerUrl;
	protected String providerNSA;
	protected String replyTo;
	protected String requesterNSA;
	private ConnectionProviderPort provider;
	private List<ConnectionRequesterPort> listeners = new ArrayList<ConnectionRequesterPort>();
	
	private boolean reserved, committed, aborted, provisioned, released, terminated;

	public Requester(String connectionId, String globalReservationId, String description, Integer version, Calendar startTime, Calendar endTime) {
		
		super(connectionId, globalReservationId, description, version, startTime, endTime);
	}
	
	public void setProviderClient(String providerUrl, String providerNSA, String replyTo, String requesterNSA) {
	
		this.providerUrl = providerUrl;
		this.providerNSA = providerNSA;
		this.replyTo = replyTo;
		this.requesterNSA = requesterNSA;
		
		this.provider = new ConnectionServiceProvider(providerUrl).getConnectionServiceProviderPort();
	}
	
	public void addListener(ConnectionRequesterPort listener) {
		
		listeners.add(listener);
	}
	
	public void removeListener(ConnectionRequesterPort listener) {
		
		listeners.remove(listener);
	}
	
	public boolean isReserved() {
		return reserved;
	}

	public boolean isCommitted() {
		return committed;
	}

	public boolean isAborted() {
		return aborted;
	}

	public boolean isProvisioned() {
		return provisioned;
	}

	public boolean isReleased() {
		return released;
	}

	public boolean isTerminated() {
		return terminated;
	}

	// TODO it is possible to receive answer before provider.reserve call completes. This can be only handled with correlationId.
	public void reserve() throws Exception {
		
		if (connectionId != null)
			throw new IllegalStateException("connectionId must be not set");
		
		if (provider == null)
			throw new IllegalStateException("provider client not created, call setProviderClient prior to calling reserve");
		
		Holder<String> conId = new Holder<String>(null);
		
		reserved = false;
		provider.reserve(conId, globalReservationId, globalReservationId, this.toReservationRequestCriteriaType(), 
				Nsi.createHeader(providerNSA, requesterNSA, replyTo));
		
		if (conId == null || conId.value == null)
			throw new Exception("connectionId not returned");
		
		connectionId = conId.value;
		RequesterManager.add(this);
		log.info(String.format("reserve sent to %s, connectionId assigned: %s", providerUrl, connectionId));
	}
	
	public void modify() throws Exception { 
		
		if (connectionId == null)
			throw new IllegalStateException("connectionId not set");
		
		if (provider == null)
			throw new IllegalStateException("provider client not created, call setProviderClient prior to calling modify");
				
		// TODO support it		
		throw new UnsupportedOperationException();
	}
	
	public void reserveCommit() throws Exception { 
		
		if (connectionId == null)
			throw new IllegalStateException("connectionId not set");
		
		if (provider == null)
			throw new IllegalStateException("provider client not created, call setProviderClient prior to calling reserveCommit");
		
		committed = false;
		provider.reserveCommit(connectionId, Nsi.createHeader(providerNSA, requesterNSA, replyTo));
		log.info(String.format("reserveCommit sent to %s", providerUrl));
	}
	
	public void reserveAbort() throws Exception { 
		
		if (connectionId == null)
			throw new IllegalStateException("connectionId not set");
		
		if (provider == null)
			throw new IllegalStateException("provider client not created, call setProviderClient prior to calling reserveAbort");
		
		aborted = false;
		provider.reserveAbort(connectionId, Nsi.createHeader(providerNSA, requesterNSA, replyTo));
		log.info(String.format("reserveAbort sent to %s", providerUrl));
	}
	
	public void provision() throws Exception { 
		
		if (connectionId == null)
			throw new IllegalStateException("connectionId not set");
		
		if (provider == null)
			throw new IllegalStateException("provider client not created, call setProviderClient prior to calling provision");
		
		provisioned = false;
		provider.provision(connectionId, Nsi.createHeader(providerNSA, requesterNSA, replyTo));
		log.info(String.format("provision sent to %s", providerUrl));
	}
	
	public void release() throws Exception { 
		
		if (connectionId == null)
			throw new IllegalStateException("connectionId not set");
		
		if (provider == null)
			throw new IllegalStateException("provider client not created, call setProviderClient prior to calling release");
		
		released = false;
		provider.release(connectionId, Nsi.createHeader(providerNSA, requesterNSA, replyTo));
		log.info(String.format("release sent to %s", providerUrl));
	}
	
	public void terminate() throws Exception { 
		
		if (connectionId == null)
			throw new IllegalStateException("connectionId not set");
		
		if (provider == null)
			throw new IllegalStateException("provider client not created, call setProviderClient prior to calling terminate");
		
		terminated = false;
		provider.terminate(connectionId, Nsi.createHeader(providerNSA, requesterNSA, replyTo));
		log.info(String.format("terminate sent to %s", providerUrl));
	}
	
	public QuerySummaryConfirmedType querySummarySync(boolean all) throws Exception {
		
		QueryType query = new QueryType(); 
		if (!all) 
			query.getConnectionId().add(connectionId);
		
		return provider.querySummarySync(query, Nsi.createHeader(providerNSA, requesterNSA, replyTo));
	}
	
	public String querySummarySyncAsString(boolean all) throws Exception {
		
		return NsiInfo.asString(querySummarySync(all));
	}
	
	public void querySummary(boolean all) throws Exception {
		
		QueryType query = new QueryType();
		if (!all) 
			query.getConnectionId().add(connectionId);
		
		provider.querySummary(query, Nsi.createHeader(providerNSA, requesterNSA, replyTo));
	}
	
	public void queryRecursive() throws Exception {
		
		QueryType query = new QueryType();
		query.getConnectionId().add(connectionId);
		provider.queryRecursive(query, Nsi.createHeader(providerNSA, requesterNSA, replyTo));
	}
	
	public QueryNotificationConfirmedType queryNotificationSync(Long start, Long end) throws Exception {
		
		QueryNotificationType query = new QueryNotificationType();
		query.setConnectionId(connectionId);
		query.setStartNotificationId(start);
		query.setEndNotificationId(end);		
		return provider.queryNotificationSync(query, Nsi.createHeader(providerNSA, requesterNSA, replyTo));		
	}
	
	public String queryNotificationSyncAsString(Long start, Long end) throws Exception {
		
		QueryNotificationConfirmedType res = queryNotificationSync(start, end);		
		return NsiInfo.asString(res);
	}
	
	public void queryNotification(Long start, Long end) throws Exception {
	
		provider.queryNotification(connectionId, start, end, Nsi.createHeader(providerNSA, requesterNSA, replyTo));
	}		
	
	
	//
	// Responses are redirected by the RequesterManager here
	//
	
	private void response(String connectionId, String method) {
		
		log.info(String.format("%s - %s arrived", connectionId, method));
	}

	@Override
	public void errorEvent(String connectionId, long notificationId, XMLGregorianCalendar timeStamp, EventEnumType event,
			String originatingConnectionId, String originatingNSA, TypeValuePairListType additionalInfo,
			ServiceExceptionType serviceException, Holder<CommonHeaderType> header) throws ServiceException {
		
		response(connectionId, "errorEvent");
		
		List<ConnectionRequesterPort> temp = listeners;
		for (ConnectionRequesterPort callback : temp) {
			callback.errorEvent(connectionId, notificationId, timeStamp, event, originatingConnectionId, originatingNSA, additionalInfo, serviceException, header);
		}
	}

	@Override
	public void queryResultConfirmed(List<QueryResultResponseType> result, Holder<CommonHeaderType> header) throws ServiceException {
		
		// handled by RequesterManager, not redirected here
	}

	@Override
	public void reserveAbortConfirmed(String connectionId, Holder<CommonHeaderType> header) throws ServiceException {
		
		response(connectionId, "reserveAbortConfirmed");
		aborted = true;
		
		List<ConnectionRequesterPort> temp = listeners;
		for (ConnectionRequesterPort callback : temp) {
			callback.reserveAbortConfirmed(connectionId, header);
		}
	}

	@Override
	public void reserveFailed(String connectionId, ConnectionStatesType connectionStates, ServiceExceptionType serviceException,
			Holder<CommonHeaderType> header) throws ServiceException {
		
		response(connectionId, "reserveFailed");
		reserved = false;
		
		List<ConnectionRequesterPort> temp = listeners;
		for (ConnectionRequesterPort callback : temp) {
			callback.reserveFailed(connectionId, connectionStates, serviceException, header);
		}
	}

	@Override
	public void messageDeliveryTimeout(String connectionId, long notificationId, XMLGregorianCalendar timeStamp,
			String correlationId, Holder<CommonHeaderType> header) throws ServiceException {
		
		response(connectionId, "messageDeliveryTimeout");
		
		List<ConnectionRequesterPort> temp = listeners;
		for (ConnectionRequesterPort callback : temp) {
			callback.messageDeliveryTimeout(connectionId, notificationId, timeStamp, correlationId, header);
		}
	}

	@Override
	public void reserveConfirmed(String connectionId, String globalReservationId, String description,
			ReservationConfirmCriteriaType criteria, Holder<CommonHeaderType> header) throws ServiceException {
		
		response(connectionId, "reserveConfirmed");
		reserved = true;
				
		List<ConnectionRequesterPort> temp = listeners;
		for (ConnectionRequesterPort callback : temp) {
			callback.reserveConfirmed(connectionId, globalReservationId, description, criteria, header);
		}
	}

	@Override
	public void dataPlaneStateChange(String connectionId, long notificationId, XMLGregorianCalendar timeStamp,
			DataPlaneStatusType dataPlaneStatus, Holder<CommonHeaderType> header) throws ServiceException {
		
		response(connectionId, "dataPlaneStateChange");
		
		List<ConnectionRequesterPort> temp = listeners;
		for (ConnectionRequesterPort callback : temp) {
			callback.dataPlaneStateChange(connectionId, notificationId, timeStamp, dataPlaneStatus, header);
		}
	}

	@Override
	public void reserveTimeout(String connectionId, long notificationId, XMLGregorianCalendar timeStamp, int timeoutValue,
			String originatingConnectionId, String originatingNSA, Holder<CommonHeaderType> header) throws ServiceException {
		
		response(connectionId, "reserveTimeout");
		
		List<ConnectionRequesterPort> temp = listeners;
		for (ConnectionRequesterPort callback : temp) {
			callback.reserveTimeout(connectionId, notificationId, timeStamp, timeoutValue, originatingConnectionId, originatingNSA, header);
		}
	}

	@Override
	public void releaseConfirmed(String connectionId, Holder<CommonHeaderType> header) throws ServiceException {
		
		response(connectionId, "releaseConfirmed");
		released = true;
		
		List<ConnectionRequesterPort> temp = listeners;
		for (ConnectionRequesterPort callback : temp) {
			callback.releaseConfirmed(connectionId, header);
		}
	}

	@Override
	public void reserveCommitConfirmed(String connectionId,	Holder<CommonHeaderType> header) throws ServiceException {
		
		response(connectionId, "reserveCommitConfirmed");
		committed = true;
		
		List<ConnectionRequesterPort> temp = listeners;
		for (ConnectionRequesterPort callback : temp) {
			callback.reserveCommitConfirmed(connectionId, header);
		}
	}

	@Override
	public void reserveCommitFailed(String connectionId, ConnectionStatesType connectionStates,
			ServiceExceptionType serviceException, Holder<CommonHeaderType> header) throws ServiceException {
		
		response(connectionId, "reserveCommitFailed");
		committed = false;
		
		List<ConnectionRequesterPort> temp = listeners;
		for (ConnectionRequesterPort callback : temp) {
			callback.reserveCommitFailed(connectionId, connectionStates, serviceException, header);
		}
	}

	@Override
	public GenericAcknowledgmentType queryNotificationConfirmed(QueryNotificationConfirmedType queryNotificationConfirmed,
			Holder<CommonHeaderType> header) throws ServiceException {

		// handled by RequesterManager, not redirected here		
		return null;
	}

	@Override
	public void error(ServiceExceptionType serviceException, Holder<CommonHeaderType> header) throws ServiceException {
		
		// handled by RequesterManager, not redirected here
	}

	@Override
	public void terminateConfirmed(String connectionId, Holder<CommonHeaderType> header) throws ServiceException {
		
		response(connectionId, "terminateConfirmed");
		terminated = true;
		
		List<ConnectionRequesterPort> temp = listeners;
		for (ConnectionRequesterPort callback : temp) {
			callback.terminateConfirmed(connectionId, header);
		}
	}

	@Override
	public void querySummaryConfirmed(List<QuerySummaryResultType> reservation,	Holder<CommonHeaderType> header) throws ServiceException {
		
		// handled by RequesterManager, not redirected here
	}

	@Override
	public void provisionConfirmed(String connectionId, Holder<CommonHeaderType> header) throws ServiceException {
		
		response(connectionId, "provisionConfirmed");
		provisioned = true;
		
		List<ConnectionRequesterPort> temp = listeners;
		for (ConnectionRequesterPort callback : temp) {
			callback.provisionConfirmed(connectionId, header);
		}
	}

	@Override
	public void queryRecursiveConfirmed(List<QueryRecursiveResultType> reservation,	Holder<CommonHeaderType> header) throws ServiceException {
		
		// handled by RequesterManager, not redirected here
	}
}
