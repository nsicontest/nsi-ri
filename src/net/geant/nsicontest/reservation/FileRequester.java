/**
 * 
 */
package net.geant.nsicontest.reservation;

import java.util.Calendar;

import net.geant.nsicontest.main.Config;

/**
 * Requester requires quite a number of parameters in order to issue reserve message.
 * This class makes it easier to create Requester with all parameters set. 
 */
public class FileRequester {

	final public static String FILE_LOCATION = "etc/request.properties";
	
	private static enum Keys { 
		ENDPOINT, PROVIDER_NSA, REPLY_TO, REQUESTER_NSA,		
		RESERVATION_ID, DESCRIPTION, START_TIME, END_TIME, VERSION, SERVICE_TYPE,
		// p2p specific fields
		SOURCE_STP, DEST_STP, ERO, CAPACITY, BIDIRECTIONAL, SYMMETRIC_PATH
	};
	
	private String endpoint, providerNSA, replyTo, requesterNSA;
	private String globalReservationId, description;
	private Calendar startTime, endTime;
	private int version;
	private String serviceType;
	
	private String sourceStp, destStp;
	private Ero ero;
	private long capacity;
	private boolean bidirectional;
	private Boolean symmetricPath;

	private FileRequester() { }
	
	public static FileRequester load(String filename) throws Exception {

		Config conf = Config.load(filename, Keys.values());
		FileRequester req = new FileRequester();
		
		req.endpoint = conf.getString(Keys.ENDPOINT);
		req.providerNSA = conf.getString(Keys.PROVIDER_NSA);
		req.replyTo = conf.getString(Keys.REPLY_TO);
		req.requesterNSA = conf.getString(Keys.REQUESTER_NSA);
		req.globalReservationId = conf.getString(Keys.RESERVATION_ID);
		req.description = conf.getString(Keys.DESCRIPTION);
		
		int stime = conf.getInteger(Keys.START_TIME);
		req.startTime = Calendar.getInstance();
		req.startTime.add(Calendar.SECOND, stime);
		
		int etime = conf.getInteger(Keys.END_TIME);
		req.endTime = Calendar.getInstance();
		req.endTime.add(Calendar.SECOND, etime);
		
		req.version = conf.getInteger(Keys.VERSION);
		req.serviceType = conf.getString(Keys.SERVICE_TYPE);
		req.sourceStp = conf.getString(Keys.SOURCE_STP);
		req.destStp = conf.getString(Keys.DEST_STP);
		
		String stps = conf.getString(Keys.ERO); 
		req.ero = Ero.fromString(stps);
		req.capacity = conf.getLong(Keys.CAPACITY);
		req.bidirectional = conf.getBoolean(Keys.BIDIRECTIONAL);
		
		String sym = conf.getString(Keys.SYMMETRIC_PATH).trim();
		if (sym != null && !sym.isEmpty())
			req.symmetricPath = Boolean.parseBoolean(sym);
		
		return req;
	}
	
	public static Requester create(String filename, boolean synchronous) throws Exception {
		
		FileRequester fr = load(filename);				
		Requester requester;
		if (synchronous)
			requester = new SynchronousRequster(null, fr.globalReservationId, fr.description, fr.version, fr.startTime, fr.endTime);
		else
			requester = new Requester(null, fr.globalReservationId, fr.description, fr.version, fr.startTime, fr.endTime);
		
		requester.setProviderClient(fr.endpoint, fr.providerNSA, fr.replyTo, fr.requesterNSA);
		requester.setP2PServiceBase(fr.serviceType, fr.sourceStp, fr.destStp, fr.capacity, fr.bidirectional, fr.symmetricPath, fr.ero);		
		return requester;
	}
}
