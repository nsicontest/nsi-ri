/**
 * 
 */
package net.geant.nsicontest.reservation.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.ogf.schemas.nsi._2013._12.connection.types.QueryResultResponseType;

/**
 * Stores events (state change) that have been sent by this instance. Stored events can be retrieved by RA.
 * For getQueries, in case of lack queries, an empty collection is returned rather than null.
 */
public class QueryManager {
	
	private static ConcurrentMap<String, List<Query>> queries = new ConcurrentHashMap<String, List<Query>>();
	
	/**
	 * Adds new Query
	 * @param query
	 */
	static public void addQuery(Query query) {
	
		List<Query> ques = queries.get(query.getConnectionId());
		
		if (ques == null) {
			
			ques = new ArrayList<Query>();
			ques.add(query);
			queries.put(query.getConnectionId(), ques);
			
		} else {
			ques.add(query);
		}		
	}
	
	/**
	 * Removes all stored queries
	 */
	static public void clear() {
		
		queries.clear();
	}
		
	/**
	 * Retrieves notifications denoted by connectionId
	 * @param connectionId
	 * @param startResultId
	 * @param endResultId
	 * @return
	 */
	static public List<Query> getQueries(String connectionId, long startResultId, long endResultId) {
		
		List<Query> result = new ArrayList<Query>();		
		List<Query> ques = queries.get(connectionId);
		
		if (ques != null) {
		
			for (Query q  : ques) {				
				if (q.getResultId() >= startResultId && q.getResultId() <= endResultId)
					result.add(q);
			}
		}				
		return Collections.unmodifiableList(result);
	}
	
	/**
	 * Retrieves notifications denoted by connectionId in NSI format
	 * @param connectionId
	 * @param startResultId
	 * @param endResultId
	 * @return
	 */
	static public List<QueryResultResponseType> getQueriesAsNsi(String connectionId, long startResultId, long endResultId) {
		
		List<QueryResultResponseType> result = new ArrayList<QueryResultResponseType>();
		List<Query> ques = getQueries(connectionId, startResultId, endResultId);
		
		if (!ques.isEmpty()) {				
			for (Query q : ques) {
				result.add(q.toNsiType());
			}
		} 		
		return Collections.unmodifiableList(result);
	}
}
