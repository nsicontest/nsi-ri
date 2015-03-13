/**
 * 
 */
package net.geant.nsicontest.reservation;

import it.nextworks.nsicontest.messagelogger.MessageLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.ws.Holder;

import net.geant.nsicontest.core.Nsi;
import net.geant.nsicontest.core.NsiError;
import net.geant.nsicontest.core.NsiErrorId;
import net.geant.nsicontest.core.NsiHeader;
import net.geant.nsicontest.core.NsiInfo;
import net.geant.nsicontest.cts.outbound.CtsNotify;
import net.geant.nsicontest.cts.outbound.EventEnum;
import net.geant.nsicontest.main.ConfigParameters;
import net.geant.nsicontest.nrm.DefaultNrm;
import net.geant.nsicontest.nrm.Nrm;
import net.geant.nsicontest.reservation.notification.NotificationManager;
import net.geant.nsicontest.reservation.query.QueryManager;

import org.apache.log4j.Logger;
import org.ogf.schemas.nsi._2013._12.connection._interface.ServiceException;
import org.ogf.schemas.nsi._2013._12.connection.requester.ConnectionRequesterPort;
import org.ogf.schemas.nsi._2013._12.connection.requester.ConnectionServiceRequester;
import org.ogf.schemas.nsi._2013._12.connection.types.NotificationBaseType;
import org.ogf.schemas.nsi._2013._12.connection.types.QueryNotificationConfirmedType;
import org.ogf.schemas.nsi._2013._12.connection.types.QueryNotificationType;
import org.ogf.schemas.nsi._2013._12.connection.types.QueryRecursiveResultType;
import org.ogf.schemas.nsi._2013._12.connection.types.QueryResultResponseType;
import org.ogf.schemas.nsi._2013._12.connection.types.QuerySummaryConfirmedType;
import org.ogf.schemas.nsi._2013._12.connection.types.QueryType;
import org.ogf.schemas.nsi._2013._12.connection.types.ReservationRequestCriteriaType;
import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;
import org.ogf.schemas.nsi._2013._12.framework.types.ServiceExceptionType;

import com.google.common.collect.ImmutableMap;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Coordinator's main role is to dispatch messages to providers and requesters based on their connectionId.
 * There is only one Coordinator running ensuring proper concurrent access.
 * Processing of each method should be as quick as possible, delegating heavy tasks to thread pool.
 */
final public class Coordinator {
	
	final private static Logger log = Logger.getLogger(Coordinator.class);

	// map of providers with key being connectionId
	private static ConcurrentMap<String, Provider> providers = new ConcurrentHashMap<String, Provider>();

	// configurable values available to Provider, Requester and Responder
	private static String providerNSA;
	private static String replyTo;
	private static String serviceType;
	private static int heldTimeout; 
	private static boolean failIfResponseNotDelivered;
	private static boolean setEro;
	private static int requesterTimeout;
	private static boolean logHeader;
	private static boolean disabledNsiContest;
	private static CtsNotify ctsNotify;
	private static Connection connection;
	private static Channel channel;
	private static String nrmImplName;
	private static Nrm nrm;
	
	// thread pool for message processing
	private static Executor threadPool;
	
	private Coordinator() { }
	
	public static String getProviderNSA() {
		return providerNSA;
	}
	
	public static String getReplyTo() {
		return replyTo;
	}
	
	public static String getServiceType() {
		return serviceType;
	}

	public static int getHeldTimeout() {
		return heldTimeout;
	}

	public static boolean isFailIfResponseNotDelivered() {
		return failIfResponseNotDelivered;
	}
	
	public static boolean isSetEro() {
		return setEro;
	}
	
	public static int getRequesterTimeout() {
		return requesterTimeout;
	}
	
	public static boolean isLogHeader() {
		return logHeader;
	}
	
	public static boolean isDisabledNsiContest() { 
		return disabledNsiContest;
	}
	
	public static Nrm getNrm() { 
		return nrm;
	}
	
	public static boolean isCtsNotifyEnabled() {
		
		return ctsNotify != null && !disabledNsiContest;
	}
	
	public static void createNrm() throws Exception {
		
		if (nrmImplName == null || nrmImplName.isEmpty()) {
			nrm = new DefaultNrm();
		} else {
			try {
				Class<?> cl = Class.forName(nrmImplName);
				nrm = (Nrm)cl.newInstance();
			} catch (Exception e ) {
				log.info("could not create nrm, using default - " + e.getMessage());
				nrm = new DefaultNrm();
			}			
		}		
		log.info("nrm: " + nrm.getClass().getCanonicalName());
	}

	public static void initialize(ConfigParameters params) throws Exception {

		Coordinator.providerNSA = params.getProviderNSA();
		Coordinator.replyTo = params.getReplyTo();
		Coordinator.serviceType = params.getServiceType();
		Coordinator.heldTimeout = params.getHeldTimeout();
		Coordinator.failIfResponseNotDelivered = params.isFailIfResponseNotDelivered();
		Coordinator.setEro = params.isSetEro();
		Coordinator.requesterTimeout = params.getRequesterTimeout();
		Coordinator.logHeader = params.isLogHeader();
		Coordinator.disabledNsiContest = params.isDisabledNsiContest();
		Coordinator.nrmImplName = params.getNrmClass();
				
		threadPool = Executors.newFixedThreadPool(params.getNumThreads());
		createNrm();
		
		if (params.isRabbitmqEnabled()) {
		
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(params.getRabbitmqHost());
			Coordinator.connection = factory.newConnection();
			Coordinator.channel = connection.createChannel();
			ctsNotify = new MessageLogger(providerNSA, channel);
		}
					
		log.info(String.format("Coordinator initialized with %s fixed threads", params.getNumThreads())); 
	}

	/**
	 *  Performs Coordinator resource cleanup
	 */
	public static void shutdown() { 
		
		if (ctsNotify != null) {
			
			try {
				channel.close();
				connection.close();
			} catch (IOException e) {
				log.info("could not close rabbitmq client - " + e.getMessage());				
			}
		}
		
		// make sure all timers are gone, otherwise we will hang
		for (Entry<String, Provider> entry : providers.entrySet()) {
			entry.getValue().cleanup();
		}
				
		// clear thread pool itself from any pending tasks
		if (threadPool != null) 
			((ExecutorService)threadPool).shutdown();
		log.info("Coordinator shutdown successfully");		
	}
	
	/**
	 * Schedules a task on common thread pool
	 * @param action
	 */
	public static void scheduleTask(Runnable action) {

		threadPool.execute(action);
	}
	
	//
	// COMMON TASKS
	//
	
	// invalid connectionId communicate with error message rather than with reserveFailed
	private static void connectionIdNonExistent(final Holder<CommonHeaderType> header, final ServiceExceptionType serviceEx) {
		
		scheduleTask(new Runnable() {
			@Override
			public void run() {
				
				new Responder(header.value.getReplyTo()).error(header, serviceEx);				
			}
		});
	}
		
	static private void logHeader(Holder<CommonHeaderType> header) {
		
		if (logHeader)
			log.info(NsiInfo.asString(header));
	}
		
	//
	// PROVIDER 
	//
	
	synchronized public static Provider getProviderByReservationId(String reservationId) {
		
		if (reservationId == null || reservationId.isEmpty()) return null;
		
		for (Entry<String, Provider> entry : providers.entrySet()) {
			String resId = entry.getValue().getGlobalReservationId();
			if (resId != null && resId.equals(reservationId))
				return entry.getValue();
		}
		log.info(String.format("Provider %s not found by its reservationId", reservationId));
		return null;	
	}
	
	synchronized public static String getProvidersAsString() {
		
		StringBuilder sb = new StringBuilder();
		for (Entry<String, Provider> entry : providers.entrySet()) {
			sb.append(entry.getValue().toString() + '\n');			
		}		
		return sb.toString();
	}
	
	/**
	 * This method creates new or modifies existing reservation.
	 * The specification does not explain under what circumstances ServiceException should be thrown - we do throw it if
	 * header's replyTo is null, that's it, the caller is not interested in async response.
	 * 
	 *  
	 * @param header
	 * @param connectionId
	 * @param globalReservationId
	 * @param description
	 * @param criteria
	 * @throws ServiceException
	 */
	synchronized public static void reserve(final Holder<CommonHeaderType> header, final Holder<String> connectionId, final String globalReservationId, 
			final String description, final ReservationRequestCriteriaType criteria) throws ServiceException {
		
		logHeader(header);
				
		String conId;
		
		if (connectionId != null && connectionId.value != null) { 
			conId = connectionId.value;
			log.info("incoming reserve with connectionId: " + conId);
		} else {
			conId = null;
			log.info("new reservation request arrived");
		}
		
		// if criteria are missing, consider it a serious problem and deny request immediately without leaving any traces
		if (criteria == null) {			
			log.error("criteria not found");
			throw NsiError.create("criteria not found", providerNSA, NsiErrorId.MISSING_PARAMETER);
		}
		
		boolean hasReplyTo = NsiHeader.hasReplyTo(header);
		
		// gather criteria into P2PServiceParameters object. Note that in future we may be more generic here to handle other types of services.
		P2PServiceParameters parameters = null;
		
		try {
		
			parameters = P2PServiceParameters.fromReservationRequestCriteriaType(providerNSA, criteria);
			
			// if replyTo is not set, validate it now
			if (!hasReplyTo)
				parameters.validate(providerNSA, conId, 0, serviceType);
			
		} catch (ServiceException e) {
			
			log.error("criteria error - " + e.getMessage());
			// if replyTo is not set, then we throw here
			if (!hasReplyTo)
				throw e;			
		}
		
		final Provider provider;
				
		if (conId == null) { // new reservation

			conId = Nsi.generateUuid();
			connectionId.value = conId;  // return newly generated connectionId			
			
			// create new Provider object with P2PServiceParameters even if they are invalid (validation will take place in CHECKING state)	
			// we assign parameters here because we may receive query message			
			if (parameters != null) {
				provider = new Provider(conId, globalReservationId, description, parameters.getVersion(), parameters.getStartTime(), parameters.getEndTime());
				
				provider.setP2PServiceBase(parameters.getServiceType(), parameters.getSourceSTP(), parameters.getDestSTP(), 
						parameters.getCapacity(), parameters.isBidirectional(), parameters.getSymmetricPath(), parameters.getEro());
				
			} else {
				// could not even get reservation parameters, still create Provider object and reserveFailed will be returned from CHECKING state
				provider = new Provider(conId, globalReservationId, description, null, null, null);
			}			
			provider.setParameters(parameters);
			provider.setNrm(nrm);
			providers.put(conId, provider);
			log.info("added provider - " + conId);
			
		} else { // modify existing reservation
			
			provider = providers.get(conId);
			if (provider == null) {
				
				log.error("provider not found - " + conId);				
				if (!hasReplyTo) {
					throw NsiError.create(conId + " not found", providerNSA, NsiErrorId.CONNECTION_NONEXISTENT);					
				} else {
					connectionIdNonExistent(header, NsiError.create(null, providerNSA, NsiErrorId.CONNECTION_NONEXISTENT).getFaultInfo()); 
					return;
				}				
				
			} else {
				provider.setParameters(parameters);
			}
		}
		
		// log CTS event, now connectionId has been assigned
		try {
			if (ctsNotify != null) 
				ctsNotify.info(EventEnum.REQ_RESERVE, header.value, ImmutableMap.of(
				"connectionId", 		provider.getConnectionId(),
				"globalReservationId", 	globalReservationId,
				"description", 			description,
				"criteria",				criteria
				));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		scheduleTask(new Runnable() {
			@Override
			public void run() {
				provider.reserve(header);					
			}
		});
	}
	
	public static void reserveCommit(final Holder<CommonHeaderType> header, final String connectionId) throws ServiceException {
		
		logHeader(header);
		boolean hasReplyTo = NsiHeader.hasReplyTo(header);		
	
		try {
			if (ctsNotify != null) ctsNotify.info(EventEnum.REQ_RESERVE_COMMIT, header.value, ImmutableMap.of(
				"connectionId", 		connectionId
				));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		final Provider provider = connectionId != null ? providers.get(connectionId) : null;
		
		if (provider == null) {
			
			log.info("provider not found - " + connectionId);
			if (!hasReplyTo) {
				throw NsiError.create(connectionId + " not found", providerNSA, NsiErrorId.CONNECTION_NONEXISTENT);					
			} else {
				connectionIdNonExistent(header, NsiError.create(null, providerNSA, NsiErrorId.CONNECTION_NONEXISTENT).getFaultInfo());
			}
		} else {
			
			scheduleTask(new Runnable() {
				@Override
				public void run() {
					provider.reserveCommit(header);					
				}
			});
		}
	}
	
	public static void reserveAbort(final Holder<CommonHeaderType> header, final String connectionId) throws ServiceException {
		
		logHeader(header);
		boolean hasReplyTo = NsiHeader.hasReplyTo(header);		
	
		try {
			if (ctsNotify != null) ctsNotify.info(EventEnum.REQ_RESERVE_ABORT, header.value, ImmutableMap.of(
				"connectionId", 		connectionId
				));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		final Provider provider = connectionId != null ? providers.get(connectionId) : null;
		
		if (provider == null) {
			
			log.error("provider not found - " + connectionId);
			if (!hasReplyTo) {
				throw NsiError.create(connectionId + " not found", providerNSA, NsiErrorId.CONNECTION_NONEXISTENT);					
			} else {
				connectionIdNonExistent(header, NsiError.create(null, providerNSA, NsiErrorId.CONNECTION_NONEXISTENT).getFaultInfo());
			}
		} else {
			
			scheduleTask(new Runnable() {
				@Override
				public void run() {
					provider.reserveAbort(header);					
				}
			});
		}
	}
	
	synchronized public static void provision(final Holder<CommonHeaderType> header, final String connectionId) throws ServiceException {

		logHeader(header);
		boolean hasReplyTo = NsiHeader.hasReplyTo(header);		
		
		try {
			if (ctsNotify != null) ctsNotify.info(EventEnum.REQ_PROVISION, header.value, ImmutableMap.of(
				"connectionId", 		connectionId
				));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		final Provider provider = connectionId != null ? providers.get(connectionId) : null;
		
		if (provider == null) {
			
			log.error("provider not found - " + connectionId);
			if (!hasReplyTo) {
				throw NsiError.create(connectionId + " not found", providerNSA, NsiErrorId.CONNECTION_NONEXISTENT);					
			} else {
				connectionIdNonExistent(header, NsiError.create(null, providerNSA, NsiErrorId.CONNECTION_NONEXISTENT).getFaultInfo());
			}
		} else {
			
			scheduleTask(new Runnable() {
				@Override
				public void run() {
					provider.provision(header);					
				}
			});
		}
	}
	
	public static void release(final Holder<CommonHeaderType> header, final String connectionId) throws ServiceException {

		logHeader(header);
		boolean hasReplyTo = NsiHeader.hasReplyTo(header);
	
		try {
			if (ctsNotify != null) ctsNotify.info(EventEnum.REQ_RELEASE, header.value, ImmutableMap.of(
				"connectionId", 		connectionId
				));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		final Provider provider = connectionId != null ? providers.get(connectionId) : null;
		
		if (provider == null) {
			
			log.error("provider not found - " + connectionId);
			if (!hasReplyTo) {
				throw NsiError.create(connectionId + " not found", providerNSA, NsiErrorId.CONNECTION_NONEXISTENT);					
			} else {
				connectionIdNonExistent(header, NsiError.create(null, providerNSA, NsiErrorId.CONNECTION_NONEXISTENT).getFaultInfo());
			}
		} else {
			
			scheduleTask(new Runnable() {
				@Override
				public void run() {
					provider.release(header);					
				}
			});
		}
	}
	
	public static void terminate(final Holder<CommonHeaderType> header, final String connectionId) throws ServiceException {

		logHeader(header);
		boolean hasReplyTo = NsiHeader.hasReplyTo(header);
		
		try {
			if (ctsNotify != null) ctsNotify.info(EventEnum.REQ_TERMINATE, header.value, ImmutableMap.of(
				"connectionId", 		connectionId
				));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		final Provider provider = connectionId != null ? providers.get(connectionId) : null;
		
		if (provider == null) {
			
			log.error("provider not found - " + connectionId);
			if (!hasReplyTo) {
				throw NsiError.create(connectionId + " not found", providerNSA, NsiErrorId.CONNECTION_NONEXISTENT);					
			} else {
				connectionIdNonExistent(header, NsiError.create(null, providerNSA, NsiErrorId.CONNECTION_NONEXISTENT).getFaultInfo());
			}
		} else {
			
			scheduleTask(new Runnable() {
				@Override
				public void run() {
					provider.terminate(header);					
				}
			});
		}
	}
	
	//
	// QUERY NOTIFICATION
	//
	
	static private QueryNotificationConfirmedType getQueryNotification(String connectionId, Long startNotificationId, Long endNotificationId) {
		
		long from = startNotificationId != null ? startNotificationId : 0L;
		long to = endNotificationId != null ? endNotificationId : Long.MAX_VALUE;
		
		QueryNotificationConfirmedType result = new QueryNotificationConfirmedType();
		Collection<NotificationBaseType> nots = NotificationManager.getNotificationsAsNsi(connectionId, from, to);
		result.getErrorEventOrReserveTimeoutOrDataPlaneStateChange().addAll(nots);
		
		return result;
	}
	
	static public QueryNotificationConfirmedType queryNotificationSync(final Holder<CommonHeaderType> header, 
			QueryNotificationType query) throws Error {
		
		logHeader(header);

		if (query == null) {
			throw new Error("query is null");
		}
		
		String conId = query.getConnectionId();
		Long start = query.getStartNotificationId();
		Long end = query.getEndNotificationId();				
		
		return getQueryNotification(conId, start, end);
	}
		
	static public void queryNotification(final Holder<CommonHeaderType> header, final String connectionId, 
			final Long startNotificationId, final Long endNotificationId) throws ServiceException {
		
		logHeader(header);
				
		try {
			if (ctsNotify != null) ctsNotify.info(EventEnum.REQ_QUERY_NOTIFICATION, header.value, ImmutableMap.of(
				"connectionId", 		connectionId,
				"start",				startNotificationId,
				"end",					endNotificationId
				));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		scheduleTask(new Runnable() {			
			@Override
			public void run() {
				
				try {
				
					QueryNotificationConfirmedType result = getQueryNotification(connectionId, startNotificationId, endNotificationId);					
					ConnectionServiceRequester service = new ConnectionServiceRequester(header.value.getReplyTo());
					ConnectionRequesterPort requester = service.getConnectionServiceRequesterPort();
					requester.queryNotificationConfirmed(result, header);
					
                } catch (Exception e) {
                    log.info("could not send query notification response: " + e.getMessage());
                }
			}			
		});
	}
	
	//
	// QUERY RESULT
	//
	
	static private List<QueryResultResponseType> getQueryResult(String connectionId, Long startResultId, Long endResultId) {
	
		long from = startResultId != null ? startResultId : 0L;
		long to = endResultId != null ? endResultId : Long.MAX_VALUE;
		
		return QueryManager.getQueriesAsNsi(connectionId, from, to);
	}	
	
	synchronized static public List<QueryResultResponseType> queryResultSync(final Holder<CommonHeaderType> header, String connectionId, 
			Long startResultId, Long endResultId) throws Error {
		
		logHeader(header);
		
		try {
			if (ctsNotify != null) ctsNotify.info(EventEnum.REQ_QUERY_RESULT_SYNC, header.value, ImmutableMap.of(
				"connectionId", 		connectionId,
				"start",				startResultId,
				"end",					endResultId
				));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		return getQueryResult(connectionId, startResultId, endResultId);
	}
	
	static public void queryResult(final Holder<CommonHeaderType> header, final String connectionId, 
			final Long startResultId, final Long endResultId) throws ServiceException {
		
		logHeader(header);
		
		try {
			if (ctsNotify != null) ctsNotify.info(EventEnum.REQ_QUERY_RESULT, header.value, ImmutableMap.of(
				"connectionId", 		connectionId,
				"start",				startResultId,
				"end",					endResultId
				));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		scheduleTask(new Runnable() {
			@Override
			public void run() {
				
				try {
					
					List<QueryResultResponseType> result = getQueryResult(connectionId, startResultId, endResultId);
					ConnectionServiceRequester service = new ConnectionServiceRequester(header.value.getReplyTo());
					ConnectionRequesterPort requester = service.getConnectionServiceRequesterPort();
					requester.queryResultConfirmed(result, header);
					
				} catch (Exception e) {
					log.info("could not send query result response: " + e.getMessage());
				}
			}		
		});
	}
		
	//
	// QUERY SUMMARY
	//
	
	static private QuerySummaryConfirmedType getQuerySummary(String requesterNSA, List<String> conIds, List<String> resIds) {
		
		QuerySummaryConfirmedType result = new QuerySummaryConfirmedType();
		
		if ((conIds == null || conIds.isEmpty()) && (resIds == null || resIds.isEmpty())) {
			
			for (Provider provider : providers.values()) {
				result.getReservation().add(provider.getQuerySummary(requesterNSA));
			}
		} else {
		
			if (conIds != null) {
				for (String conId : conIds) { 
					Provider provider = providers.get(conId);
					if (provider != null) {
						result.getReservation().add(provider.getQuerySummary(requesterNSA));
					}
				}
			}
		
			if (resIds != null) {
				for (String resId : resIds) {
					for (Provider provider : providers.values()) {
						if (provider.getGlobalReservationId().equals(resId)) {
							result.getReservation().add(provider.getQuerySummary(requesterNSA));
						}
					}	
				}
			}
		}
		
		return result;
	}
	
	static public QuerySummaryConfirmedType querySummarySync(final Holder<CommonHeaderType> header, QueryType query) throws Error {
		
		logHeader(header);
		

		try {
			if (ctsNotify != null) ctsNotify.info(EventEnum.REQ_QUERY_SUMMARY_SYNC, header.value, ImmutableMap.of(
				"query",				query
				));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		if (query == null) {
			throw new Error("query is null");
		}
		return getQuerySummary(header.value.getRequesterNSA(), query.getConnectionId(), query.getGlobalReservationId());
	}
	
	static public void querySummary(final Holder<CommonHeaderType> header, final QueryType query) throws ServiceException {

		logHeader(header);
		
		try {
			if (ctsNotify != null) ctsNotify.info(EventEnum.REQ_QUERY_SUMMARY, header.value, ImmutableMap.of(
				"query",				query
				));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
						
		scheduleTask(new Runnable() {
			@Override
			public void run() {
				
				try {
					
					QuerySummaryConfirmedType result = getQuerySummary(header.value.getRequesterNSA(), query.getConnectionId(), query.getGlobalReservationId());
					ConnectionServiceRequester service = new ConnectionServiceRequester(header.value.getReplyTo());
					ConnectionRequesterPort requester = service.getConnectionServiceRequesterPort();
					requester.querySummaryConfirmed(result.getReservation(), header);
					
				} catch (Exception e) {
					log.info("could not send query summary response: " + e.getMessage());
				}
			}		
		});
	}
	
	//
	// QUERY RECURSIVE
	//
		
	static private List<QueryRecursiveResultType> getQueryRecursive(String requesterNSA, List<String> conIds, List<String> resIds) {
				
		List<QueryRecursiveResultType> result = new ArrayList<QueryRecursiveResultType>();
		
		if ((conIds == null || conIds.isEmpty()) && (resIds == null || resIds.isEmpty())) {
			
			for (Provider provider : providers.values()) {
				result.add(provider.getQueryRecursive(requesterNSA));
			}
			
		} else {
		
			if (conIds != null) {
				for (String conId : conIds) { 
					Provider provider = providers.get(conId);
					if (provider != null) {
						result.add(provider.getQueryRecursive(requesterNSA));
					}
				}
			}
		
			if (resIds != null) {
				for (String resId : resIds) {
					for (Provider provider : providers.values()) {
						if (provider.getGlobalReservationId().equals(resId)) {
							result.add(provider.getQueryRecursive(requesterNSA));
						}
					}	
				}
			}
		}
		
		return result;
	}
	
	static public void queryRecursive(final Holder<CommonHeaderType> header, final QueryType query) throws ServiceException {
	
		logHeader(header);
		
		scheduleTask(new Runnable() {
			@Override
			public void run() {
				
				try {
					
					List<QueryRecursiveResultType> result = getQueryRecursive(header.value.getRequesterNSA(), query.getConnectionId(), query.getGlobalReservationId());
					ConnectionServiceRequester service = new ConnectionServiceRequester(header.value.getReplyTo());
					ConnectionRequesterPort requester = service.getConnectionServiceRequesterPort();
					requester.queryRecursiveConfirmed(result, header);
					
				} catch (Exception e) {
					log.info("could not send query summary response: " + e.getMessage());
				}
			}		
		});
	}
}
