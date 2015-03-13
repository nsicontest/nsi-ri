/**
 * 
 */
package net.geant.nsicontest.reservation.query;

import java.util.Calendar;

import org.ogf.schemas.nsi._2013._12.connection.types.QueryResultResponseType;

/**
 * Represents single event in form suitable for query result methods
 */
public class Query {
	
	private String connectionId;
	private String correlationId;	
	private Event event;
	private long resultId;
	private Calendar timeStamp;
	
	public Query() { }

	public Query(String connectionId, String correlationId, Event event, long resultId, Calendar timeStamp) {

		this.connectionId = connectionId;
		this.correlationId = correlationId;
		this.event = event;
		this.resultId = resultId;
		this.timeStamp = timeStamp;
	} 
	
	public Query(String connectionId, String correlationId, Event event, long resultId) {
		
		this(connectionId, correlationId, event, resultId, Calendar.getInstance()); 
	}
	
	public QueryResultResponseType toNsiType() {
		
		QueryResultResponseType result = new QueryResultResponseType();
		
		return result;
	}
	
	@Override
	public String toString() {
		
		return String.format("query notification: %s, %s, %s, %s", connectionId, resultId, timeStamp.getTime(), event.name());
	}
	
	//
	// setters & getters
	//

	public String getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public long getResultId() {
		return resultId;
	}

	public void setResultId(long resultId) {
		this.resultId = resultId;
	}

	public Calendar getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Calendar timeStamp) {
		this.timeStamp = timeStamp;
	}
}
