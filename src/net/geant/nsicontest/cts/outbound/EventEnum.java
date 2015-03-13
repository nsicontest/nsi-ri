package net.geant.nsicontest.cts.outbound;

/**
 *  enumeration of all possible CTS messages
 */
public enum EventEnum {
	ERROR						("error.error"),
	REQ_RESERVE					("request.reserve"),
	REQ_RESERVE_COMMIT			("request.reserveCommit"), 
	REQ_RESERVE_ABORT			("request.reserveAbort"), 
	REQ_PROVISION				("request.provision"), 
	REQ_RELEASE					("request.release"), 
	REQ_TERMINATE				("request.terminate"), 
	REQ_QUERY_NOTIFICATION		("request.queryNotification"), 
	REQ_QUERY_SUMMARY			("request.querySummary"),
	REQ_QUERY_RESULT			("request.queryResult"),
	REQ_QUERY_SUMMARY_SYNC		("request.querySummarySync"),
	REQ_QUERY_RESULT_SYNC		("request.queryResultSync");
	// TODO: to be continued...
	
    private final String name;       

    private EventEnum(String s) {
        name = s;
    }

    public boolean equalsName(String otherName){
        return (otherName == null )? false : name.equals(otherName);
    }

    public String toString(){
       return name;
    }

}
