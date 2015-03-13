/**
 * 
 */
package net.geant.nsicontest.reservation;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.Calendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.geant.nsicontest.core.Nsi;

import org.ogf.schemas.nsi._2013._12.connection.types.ReservationConfirmCriteriaType;
import org.ogf.schemas.nsi._2013._12.connection.types.ReservationRequestCriteriaType;
import org.ogf.schemas.nsi._2013._12.connection.types.ScheduleType;
import org.ogf.schemas.nsi._2013._12.services.point2point.ObjectFactory;
import org.ogf.schemas.nsi._2013._12.services.point2point.P2PServiceBaseType;
import org.ogf.schemas.nsi._2013._12.services.types.DirectionalityType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Base class for reservation. Holds parameters associated with either provider or requester.
 * Provides support for P2PServiceBase type.
 */
public abstract class Reservation {

	protected String connectionId;			// unique identifier for each reservation, set by Provider
	protected String globalReservationId;	// reservation identifier (optional)
	protected String description;			// reservation description (optional)
	protected int version;					// connection version 
	protected Calendar startTime;			// start time of reservation (null means already started)
	protected Calendar endTime;				// end time of reservation (null denotes infinite reservation)
	
	// P2PServiceBaseType
	protected String serviceType;			// for now http://services.ogf.org/nsi/2013/07/descriptions/EVTS.A-GOLE
	protected String requestedSourceStp;	// fully/under specified stp with/without label 				
	protected String requestedDestStp;		// fully/under specified stp with/without label 
	protected long capacity;				// bandwidth
	protected boolean bidirectional;		// true if bidirectional, otherwise unidirectional
	protected Boolean symmetricPath;		// only valid if bidirectional. If not set, false
	protected Ero ero;						// optional Explicit Route Object
		
	/**
	 * Default constructor that sets basic parameters for reservation
	 * @param connectionId
	 * @param globalReservationId
	 * @param description
	 * @param version
	 * @param startTime
	 * @param endTime
	 */
	public Reservation(String connectionId, String globalReservationId, String description,	Integer version, Calendar startTime, Calendar endTime) {
		
		this.connectionId = connectionId;
		this.globalReservationId = globalReservationId;
		this.description = description;
		this.version = version;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	/**
	 * Sets parameters for P2PServiceBase type
	 * @param serviceType
	 * @param requestedSourceStp
	 * @param requestedDestStp
	 * @param capacity
	 * @param bidirectional
	 * @param symmetricPath
	 * @param ero
	 */
	public void setP2PServiceBase(String serviceType, String requestedSourceStp, String requestedDestStp, 
			long capacity, boolean bidirectional, Boolean symmetricPath, Ero ero) {
		
		this.serviceType = serviceType;
		this.requestedSourceStp = requestedSourceStp;
		this.requestedDestStp = requestedDestStp;
		this.capacity = capacity;
		this.bidirectional = bidirectional;
		this.symmetricPath = symmetricPath;
		this.ero = ero;
	}
	
	/**
	 * Called on shutdown, derived classes should release resources (timers, locks, etc, not nrm resources) associated with this reservation
	 */
	public void cleanup() { }
	
	/**
	 * Checks if reservation is active (only start time is considered, not provision state machine or end time)
	 * @return
	 */
	public boolean isActive() {
		
		return startTime == null ? true : startTime.before(Calendar.getInstance());
	}

	/**
	 * Checks if reservation reached its end of life
	 * @return
	 */
	public boolean isFinished() {
		
		return endTime == null ? false : endTime.before(Calendar.getInstance());
	}
	
	public String getStartTimeAsString() { 
		
		return startTime != null ? startTime.getTime().toString() : "infinite"; 
	}
	
	public String getEndTimeAsString() { 
		
		return endTime != null ? endTime.getTime().toString() : "infinite"; 
	}
	
	//
	// SETTERS AND GETTERS
	//

	public String getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}

	public String getGlobalReservationId() {
		return globalReservationId;
	}

	public void setGlobalReservationId(String globalReservationId) {
		this.globalReservationId = globalReservationId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getRequestedSourceStp() {
		return requestedSourceStp;
	}

	public void setRequestedSourceStp(String requestedSourceStp) {
		this.requestedSourceStp = requestedSourceStp;
	}

	public String getRequestedDestStp() {
		return requestedDestStp;
	}

	public void setRequestedDestStp(String requestedDestStp) {
		this.requestedDestStp = requestedDestStp;
	}

	public long getCapacity() {
		return capacity;
	}

	public void setCapacity(long capacity) {
		this.capacity = capacity;
	}

	public boolean isBidirectional() {
		return bidirectional;
	}

	public void setBidirectional(boolean bidirectional) {
		this.bidirectional = bidirectional;
	}

	public Boolean getSymmetricPath() {
		return symmetricPath;
	}

	public void setSymmetricPath(Boolean symmetricPath) {
		this.symmetricPath = symmetricPath;
	}
	
	public Ero getEro() {
		return ero;
	}

	public void setEro(Ero ero) {
		this.ero = ero;
	}
	
	//
	// TO NSI CONVERSION HELPERS
	//

	/**
	 * Converts start and end time to NSI representation
	 * @return
	 */
	protected ScheduleType toScheduleType() {
		
		ScheduleType schedule = new ScheduleType();
		
		if (startTime != null)
			schedule.setStartTime(Nsi.createXmlCalendar(startTime.getTime()));
		
		if (endTime != null)
			schedule.setEndTime(Nsi.createXmlCalendar(endTime.getTime()));
		
		return schedule;
	}
	
	protected Element toP2PServiceBaseType(String sourceStp, String destStp) throws Exception {
		
        org.ogf.schemas.nsi._2013._12.services.point2point.ObjectFactory of = new org.ogf.schemas.nsi._2013._12.services.point2point.ObjectFactory(); 
		P2PServiceBaseType p2p = of.createP2PServiceBaseType();

		p2p.setSourceSTP(sourceStp);
		p2p.setDestSTP(destStp);	 
		p2p.setCapacity(capacity);
		p2p.setDirectionality(bidirectional ? DirectionalityType.BIDIRECTIONAL : DirectionalityType.UNIDIRECTIONAL);
		p2p.setSymmetricPath(symmetricPath);
		if (ero != null)
			p2p.setEro(ero.toStpListType());
	
		JAXBElement<P2PServiceBaseType> p2pType = new ObjectFactory().createP2Ps(p2p);
		JAXBContext context = JAXBContext.newInstance(P2PServiceBaseType.class);
		Marshaller marshaller = context.createMarshaller();
		StringWriter writer = new StringWriter();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(p2pType, writer);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		ByteArrayInputStream stream = new ByteArrayInputStream(writer.toString().getBytes());
		Document document = db.parse(stream);				
		return document.getDocumentElement();
	}

    protected ReservationConfirmCriteriaType toReservationConfirmCriteriaType(String sourceStp, String destStp) throws Exception {

		ReservationConfirmCriteriaType confirm = new ReservationConfirmCriteriaType();
		confirm.setSchedule(toScheduleType());
		confirm.setServiceType(serviceType);
		confirm.setVersion(version);
		confirm.getAny().add(toP2PServiceBaseType(sourceStp, destStp));		
		return confirm;
	}
	
	protected ReservationRequestCriteriaType toReservationRequestCriteriaType() throws Exception {
		
        org.ogf.schemas.nsi._2013._12.connection.types.ObjectFactory of = new org.ogf.schemas.nsi._2013._12.connection.types.ObjectFactory(); 
        
		ReservationRequestCriteriaType request = of.createReservationRequestCriteriaType();
		request.setSchedule(toScheduleType());
		request.setServiceType(serviceType);
		request.setVersion(version);
		request.getAny().add(toP2PServiceBaseType(requestedSourceStp, requestedDestStp));	
		return request;
	}
	
	public String toStringP2PServiceBase() {
		
		return String.format("serviceType: %s\n" +
				"sourceStp: %s, destStp: %s\n" +
				"capacity: %s, bidirectional: %s, symmetric: %s\n" + 
				"ero: %s",
				serviceType, requestedSourceStp, requestedDestStp, capacity, bidirectional, symmetricPath, ero);
	}
	
	@Override
	public String toString() {
		
		return String.format("connectionId: %s\n" +
				"reservationId: %s, description: %s\n" +
				"version:  %s, startTime: %s, endTime: %s\n" +
				"%s", 
				connectionId, globalReservationId, description, version, 
				getStartTimeAsString(), getEndTimeAsString(),
				toStringP2PServiceBase());
	}	
}

