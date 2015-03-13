/**
 * 
 */
package net.geant.nsicontest.core;

import java.util.Arrays;

import javax.xml.ws.Holder;

import org.ogf.schemas.nsi._2013._12.connection.types.ConnectionStatesType;
import org.ogf.schemas.nsi._2013._12.connection.types.DataPlaneStateChangeRequestType;
import org.ogf.schemas.nsi._2013._12.connection.types.ErrorEventType;
import org.ogf.schemas.nsi._2013._12.connection.types.MessageDeliveryTimeoutRequestType;
import org.ogf.schemas.nsi._2013._12.connection.types.NotificationBaseType;
import org.ogf.schemas.nsi._2013._12.connection.types.QueryNotificationConfirmedType;
import org.ogf.schemas.nsi._2013._12.connection.types.QueryRecursiveResultType;
import org.ogf.schemas.nsi._2013._12.connection.types.QueryResultResponseType;
import org.ogf.schemas.nsi._2013._12.connection.types.QuerySummaryConfirmedType;
import org.ogf.schemas.nsi._2013._12.connection.types.QuerySummaryResultType;
import org.ogf.schemas.nsi._2013._12.connection.types.ReserveTimeoutRequestType;
import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;
import org.ogf.schemas.nsi._2013._12.framework.types.ServiceExceptionType;
import org.ogf.schemas.nsi._2013._12.framework.types.TypeValuePairType;

/**
 * Helper class to provide conversion methods of nsi types to string
 */
public class NsiInfo {
	
	/**
	 * Returns CommonHeaderType as string 
	 * @param holder
	 * @return
	 */
	public static String asString(Holder<CommonHeaderType> holder) {
		
		if (holder == null) return "holder not set";
		
		CommonHeaderType header = holder.value;
		if (header == null) return "header not set";
		
		return String.format("protocol: %s, correlation: %s\nprovider: %s\nrequester: %s\nreply: %s", header.getProtocolVersion(), header.getCorrelationId(),
				header.getProviderNSA(), header.getRequesterNSA(), header.getReplyTo()); 
	}
	
	/**
	 * Dumps ServiceExceptionType into string
	 * @param serviceException
	 * @return
	 */
	public static String asString(ServiceExceptionType serviceException) {
		
		if (serviceException == null) return "serviceException not set";
			
		StringBuilder sb = new StringBuilder();		
		sb.append(String.format("ERROR - %s", serviceException.getConnectionId()));
		sb.append(String.format("\n%s", serviceException.getNsaId()));
		sb.append(String.format("\n%s, %s", serviceException.getErrorId(), serviceException.getText()));
		
		if (serviceException.getVariables() != null && serviceException.getVariables().getVariable() != null) {
				
			for (TypeValuePairType item : serviceException.getVariables().getVariable()) 		
				sb.append(String.format("\nns: %s,  type: %s, values: %s", item.getNamespace(), item.getType(), Arrays.toString(item.getValue().toArray())));
		}		
		return sb.toString();
	}
	
	/**
	 * Returns ConnectionStatesType as string
	 * @param states
	 * @return
	 */
	public static String asString(ConnectionStatesType states) {
		
		if (states == null) return "states not set";
		
		return String.format("reservation: %s, provision: %s, lifecycle: %s, dataplane: %s, version: %s, consistent: %s", 
				states.getReservationState().name(), states.getProvisionState().name(), 
				states.getLifecycleState() != null ? states.getLifecycleState().name() : "not created",
				states.getDataPlaneStatus().isActive(), states.getDataPlaneStatus().getVersion(), states.getDataPlaneStatus().isVersionConsistent());
	}

	/**
	 * Returns QuerySummaryResultType as string
	 * @param query
	 * @return
	 */
	public static String asString(QuerySummaryResultType query) {
		
		if (query == null) return "query summary not set";
		
		return String.format("%s,  %s", query.getConnectionId(), asString(query.getConnectionStates()));
	}
	
	/**
	 * Returns QuerySummaryConfirmedType as string
	 * @param query
	 * @return
	 */
	public static String asString(QuerySummaryConfirmedType query) {
		
		if (query == null) return "query summary not set";		
		if (query.getReservation() == null || query.getReservation().isEmpty()) return "query summary with no reservations";
		
		StringBuilder sb = new StringBuilder();
		for (QuerySummaryResultType res : query.getReservation()) {			
			sb.append(asString(res) + '\n');			
		}		
		return sb.toString();
	}

	/**
	 * Not finished - does not use recursion
	 * @param query
	 * @return
	 */
	public static String asString(QueryRecursiveResultType query) {
		
		if (query == null) return "query recursive not set";
		
		return String.format("%s,  %s", query.getConnectionId(), asString(query.getConnectionStates()));
	}
	
	public static String asString(QueryResultResponseType query) {
		
		if (query == null) return "query result response not set";
		
		return ""; // TODO
	}
	
	/**
	 * Returns string representation of notification
	 * @param notification
	 * @return
	 */
	public static String asString(NotificationBaseType notification) {
		
		if (notification == null) return "base notification not set";
		
		String s;
		if (notification instanceof ReserveTimeoutRequestType) {
			
			ReserveTimeoutRequestType timeoutNot = (ReserveTimeoutRequestType)notification;
			s = String.format("timeout %s", timeoutNot.getTimeoutValue());
			
		} else if (notification instanceof DataPlaneStateChangeRequestType) {
			
			DataPlaneStateChangeRequestType stateNot = (DataPlaneStateChangeRequestType)notification;
			s = String.format("data plane active %s", stateNot.getDataPlaneStatus().isActive());
			
		} else if (notification instanceof ErrorEventType) {
			
			ErrorEventType eventNot = (ErrorEventType)notification;
			s = String.format("event %s", eventNot.getEvent());
			
		} else if (notification instanceof MessageDeliveryTimeoutRequestType) {
			
			MessageDeliveryTimeoutRequestType deliveryNot = (MessageDeliveryTimeoutRequestType)notification;
			s = String.format("correlationId %s", deliveryNot.getCorrelationId());
			
		} else {
			
			s = "unknown notification type";
			
		}
		return String.format("%s %s - %s", notification.getTimeStamp().toGregorianCalendar().getTime().toString(), notification.getNotificationId(), s);
	}
	
	public static String asString(QueryNotificationConfirmedType notification) {
		
		if (notification == null) return "notification null";
		
		StringBuilder sb = new StringBuilder();
		for (NotificationBaseType not : notification.getErrorEventOrReserveTimeoutOrDataPlaneStateChange()) {
			sb.append(asString(not) + '\n');
		}		
		return sb.toString();
	}
}
