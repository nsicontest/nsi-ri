/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package org.ogf.schemas.nsi._2013._12.connection.requester;

import net.geant.nsicontest.reservation.RequesterManager;

/**
 * This class was generated by Apache CXF 2.7.4 2014-02-18T11:21:11.621+01:00
 * Generated source version: 2.7.4
 * 
 */

@javax.jws.WebService(serviceName = "ConnectionServiceRequester", portName = "ConnectionServiceRequesterPort", 
targetNamespace = "http://schemas.ogf.org/nsi/2013/12/connection/requester", 
wsdlLocation = "file:etc/wsdl/ConnectionService/ogf_nsi_connection_requester_v2_0.wsdl", 
endpointInterface = "org.ogf.schemas.nsi._2013._12.connection.requester.ConnectionRequesterPort")
public class ConnectionRequesterPortImpl implements ConnectionRequesterPort {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ogf.schemas.nsi._2013._12.connection.requester.ConnectionRequesterPort
	 * #errorEvent(java.lang.String connectionId ,)long notificationId
	 * ,)javax.xml.datatype.XMLGregorianCalendar timeStamp
	 * ,)org.ogf.schemas.nsi._2013._12.connection.types.EventEnumType event
	 * ,)java.lang.String originatingConnectionId ,)java.lang.String
	 * originatingNSA
	 * ,)org.ogf.schemas.nsi._2013._12.framework.types.TypeValuePairListType
	 * additionalInfo
	 * ,)org.ogf.schemas.nsi._2013._12.framework.types.ServiceExceptionType
	 * serviceException
	 * ,)org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType header
	 * )*
	 */
	public void errorEvent(java.lang.String connectionId, long notificationId, javax.xml.datatype.XMLGregorianCalendar timeStamp,
			org.ogf.schemas.nsi._2013._12.connection.types.EventEnumType event,	java.lang.String originatingConnectionId,
			java.lang.String originatingNSA,
			org.ogf.schemas.nsi._2013._12.framework.types.TypeValuePairListType additionalInfo,
			org.ogf.schemas.nsi._2013._12.framework.types.ServiceExceptionType serviceException,
			javax.xml.ws.Holder<org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType> header)
			throws org.ogf.schemas.nsi._2013._12.connection._interface.ServiceException {
		
		RequesterManager.getInstance().errorEvent(connectionId, notificationId, timeStamp, event, originatingConnectionId, originatingNSA, 
				additionalInfo, serviceException, header);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ogf.schemas.nsi._2013._12.connection.requester.ConnectionRequesterPort
	 * #
	 * queryResultConfirmed(java.util.List<org.ogf.schemas.nsi._2013._12.connection
	 * .types.QueryResultResponseType> result
	 * ,)org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType header
	 * )*
	 */
	public void queryResultConfirmed(java.util.List<org.ogf.schemas.nsi._2013._12.connection.types.QueryResultResponseType> result,
			javax.xml.ws.Holder<org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType> header)
			throws org.ogf.schemas.nsi._2013._12.connection._interface.ServiceException {

		RequesterManager.getInstance().queryResultConfirmed(result, header);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ogf.schemas.nsi._2013._12.connection.requester.ConnectionRequesterPort
	 * #reserveAbortConfirmed(java.lang.String connectionId
	 * ,)org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType header
	 * )*
	 */
	public void reserveAbortConfirmed(java.lang.String connectionId,
			javax.xml.ws.Holder<org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType> header)
			throws org.ogf.schemas.nsi._2013._12.connection._interface.ServiceException {

		RequesterManager.getInstance().reserveAbortConfirmed(connectionId, header);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ogf.schemas.nsi._2013._12.connection.requester.ConnectionRequesterPort
	 * #reserveFailed(java.lang.String connectionId
	 * ,)org.ogf.schemas.nsi._2013._12.connection.types.ConnectionStatesType
	 * connectionStates
	 * ,)org.ogf.schemas.nsi._2013._12.framework.types.ServiceExceptionType
	 * serviceException
	 * ,)org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType header
	 * )*
	 */
	public void reserveFailed(java.lang.String connectionId,
			org.ogf.schemas.nsi._2013._12.connection.types.ConnectionStatesType connectionStates,
			org.ogf.schemas.nsi._2013._12.framework.types.ServiceExceptionType serviceException,
			javax.xml.ws.Holder<org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType> header)
			throws org.ogf.schemas.nsi._2013._12.connection._interface.ServiceException {

		RequesterManager.getInstance().reserveFailed(connectionId, connectionStates, serviceException, header);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ogf.schemas.nsi._2013._12.connection.requester.ConnectionRequesterPort
	 * #messageDeliveryTimeout(java.lang.String connectionId ,)long
	 * notificationId ,)javax.xml.datatype.XMLGregorianCalendar timeStamp
	 * ,)java.lang.String correlationId
	 * ,)org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType header
	 * )*
	 */
	public void messageDeliveryTimeout(java.lang.String connectionId, long notificationId,
			javax.xml.datatype.XMLGregorianCalendar timeStamp, java.lang.String correlationId,
			javax.xml.ws.Holder<org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType> header)
			throws org.ogf.schemas.nsi._2013._12.connection._interface.ServiceException {

		RequesterManager.getInstance().messageDeliveryTimeout(connectionId, notificationId, timeStamp, correlationId, header);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ogf.schemas.nsi._2013._12.connection.requester.ConnectionRequesterPort
	 * #reserveConfirmed(java.lang.String connectionId ,)java.lang.String
	 * globalReservationId ,)java.lang.String description
	 * ,)org.ogf.schemas.nsi._2013
	 * ._12.connection.types.ReservationConfirmCriteriaType criteria
	 * ,)org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType header
	 * )*
	 */
	public void reserveConfirmed(java.lang.String connectionId,	java.lang.String globalReservationId, java.lang.String description,
			org.ogf.schemas.nsi._2013._12.connection.types.ReservationConfirmCriteriaType criteria,
			javax.xml.ws.Holder<org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType> header)
			throws org.ogf.schemas.nsi._2013._12.connection._interface.ServiceException {

		RequesterManager.getInstance().reserveConfirmed(connectionId, globalReservationId, description, criteria, header);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ogf.schemas.nsi._2013._12.connection.requester.ConnectionRequesterPort
	 * #dataPlaneStateChange(java.lang.String connectionId ,)long notificationId
	 * ,)javax.xml.datatype.XMLGregorianCalendar timeStamp
	 * ,)org.ogf.schemas.nsi._2013._12.connection.types.DataPlaneStatusType
	 * dataPlaneStatus
	 * ,)org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType header
	 * )*
	 */
	public void dataPlaneStateChange(java.lang.String connectionId,	long notificationId, javax.xml.datatype.XMLGregorianCalendar timeStamp,
			org.ogf.schemas.nsi._2013._12.connection.types.DataPlaneStatusType dataPlaneStatus,
			javax.xml.ws.Holder<org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType> header)
			throws org.ogf.schemas.nsi._2013._12.connection._interface.ServiceException {

		RequesterManager.getInstance().dataPlaneStateChange(connectionId, notificationId, timeStamp, dataPlaneStatus, header);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ogf.schemas.nsi._2013._12.connection.requester.ConnectionRequesterPort
	 * #reserveTimeout(java.lang.String connectionId ,)long notificationId
	 * ,)javax.xml.datatype.XMLGregorianCalendar timeStamp ,)int timeoutValue
	 * ,)java.lang.String originatingConnectionId ,)java.lang.String
	 * originatingNSA
	 * ,)org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType header
	 * )*
	 */
	public void reserveTimeout(java.lang.String connectionId, long notificationId, javax.xml.datatype.XMLGregorianCalendar timeStamp,
			int timeoutValue, java.lang.String originatingConnectionId,	java.lang.String originatingNSA,
			javax.xml.ws.Holder<org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType> header)
			throws org.ogf.schemas.nsi._2013._12.connection._interface.ServiceException {

		RequesterManager.getInstance().reserveTimeout(connectionId, notificationId, timeStamp, timeoutValue, originatingConnectionId, originatingNSA, header);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ogf.schemas.nsi._2013._12.connection.requester.ConnectionRequesterPort
	 * #releaseConfirmed(java.lang.String connectionId
	 * ,)org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType header
	 * )*
	 */
	public void releaseConfirmed(java.lang.String connectionId, 
			javax.xml.ws.Holder<org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType> header)
			throws org.ogf.schemas.nsi._2013._12.connection._interface.ServiceException {

		RequesterManager.getInstance().releaseConfirmed(connectionId, header);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ogf.schemas.nsi._2013._12.connection.requester.ConnectionRequesterPort
	 * #reserveCommitConfirmed(java.lang.String connectionId
	 * ,)org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType header
	 * )*
	 */
	public void reserveCommitConfirmed(java.lang.String connectionId,
			javax.xml.ws.Holder<org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType> header)
			throws org.ogf.schemas.nsi._2013._12.connection._interface.ServiceException {

		RequesterManager.getInstance().reserveCommitConfirmed(connectionId, header);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ogf.schemas.nsi._2013._12.connection.requester.ConnectionRequesterPort
	 * #reserveCommitFailed(java.lang.String connectionId
	 * ,)org.ogf.schemas.nsi._2013._12.connection.types.ConnectionStatesType
	 * connectionStates
	 * ,)org.ogf.schemas.nsi._2013._12.framework.types.ServiceExceptionType
	 * serviceException
	 * ,)org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType header
	 * )*
	 */
	public void reserveCommitFailed(java.lang.String connectionId,
			org.ogf.schemas.nsi._2013._12.connection.types.ConnectionStatesType connectionStates,
			org.ogf.schemas.nsi._2013._12.framework.types.ServiceExceptionType serviceException,
			javax.xml.ws.Holder<org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType> header)
			throws org.ogf.schemas.nsi._2013._12.connection._interface.ServiceException {

		RequesterManager.getInstance().reserveCommitFailed(connectionId, connectionStates, serviceException, header);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ogf.schemas.nsi._2013._12.connection.requester.ConnectionRequesterPort
	 * #
	 * queryNotificationConfirmed(org.ogf.schemas.nsi._2013._12.connection.types
	 * .QueryNotificationConfirmedType queryNotificationConfirmed
	 * ,)org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType header
	 * )*
	 */
	public org.ogf.schemas.nsi._2013._12.connection.types.GenericAcknowledgmentType queryNotificationConfirmed(
			org.ogf.schemas.nsi._2013._12.connection.types.QueryNotificationConfirmedType queryNotificationConfirmed,
			javax.xml.ws.Holder<org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType> header)
			throws org.ogf.schemas.nsi._2013._12.connection._interface.ServiceException {

		return RequesterManager.getInstance().queryNotificationConfirmed(queryNotificationConfirmed, header);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ogf.schemas.nsi._2013._12.connection.requester.ConnectionRequesterPort
	 * #error(org.ogf.schemas.nsi._2013._12.framework.types.ServiceExceptionType
	 * serviceException
	 * ,)org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType header
	 * )*
	 */
	public void error(org.ogf.schemas.nsi._2013._12.framework.types.ServiceExceptionType serviceException,
			javax.xml.ws.Holder<org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType> header)
			throws org.ogf.schemas.nsi._2013._12.connection._interface.ServiceException {

		RequesterManager.getInstance().error(serviceException, header);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ogf.schemas.nsi._2013._12.connection.requester.ConnectionRequesterPort
	 * #terminateConfirmed(java.lang.String connectionId
	 * ,)org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType header
	 * )*
	 */
	public void terminateConfirmed(java.lang.String connectionId,
			javax.xml.ws.Holder<org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType> header)
			throws org.ogf.schemas.nsi._2013._12.connection._interface.ServiceException {

		RequesterManager.getInstance().terminateConfirmed(connectionId, header);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ogf.schemas.nsi._2013._12.connection.requester.ConnectionRequesterPort
	 * #
	 * querySummaryConfirmed(java.util.List<org.ogf.schemas.nsi._2013._12.connection
	 * .types.QuerySummaryResultType> reservation
	 * ,)org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType header
	 * )*
	 */
	public void querySummaryConfirmed(java.util.List<org.ogf.schemas.nsi._2013._12.connection.types.QuerySummaryResultType> reservation,
			javax.xml.ws.Holder<org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType> header)
			throws org.ogf.schemas.nsi._2013._12.connection._interface.ServiceException {

		RequesterManager.getInstance().querySummaryConfirmed(reservation, header);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ogf.schemas.nsi._2013._12.connection.requester.ConnectionRequesterPort
	 * #provisionConfirmed(java.lang.String connectionId
	 * ,)org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType header
	 * )*
	 */
	public void provisionConfirmed(java.lang.String connectionId,
			javax.xml.ws.Holder<org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType> header)
			throws org.ogf.schemas.nsi._2013._12.connection._interface.ServiceException {

		RequesterManager.getInstance().provisionConfirmed(connectionId, header);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ogf.schemas.nsi._2013._12.connection.requester.ConnectionRequesterPort
	 * #queryRecursiveConfirmed(java.util.List<org.ogf.schemas.nsi._2013._12.
	 * connection.types.QueryRecursiveResultType> reservation
	 * ,)org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType header
	 * )*
	 */
	public void queryRecursiveConfirmed(java.util.List<org.ogf.schemas.nsi._2013._12.connection.types.QueryRecursiveResultType> reservation,
			javax.xml.ws.Holder<org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType> header)
			throws org.ogf.schemas.nsi._2013._12.connection._interface.ServiceException {

		RequesterManager.getInstance().queryRecursiveConfirmed(reservation, header);
	}
}
