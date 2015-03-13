/**
 * 
 */
package net.geant.nsicontest.reservation;

import java.util.Calendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import net.geant.nsicontest.core.NsiError;
import net.geant.nsicontest.core.NsiErrorId;

import org.ogf.schemas.nsi._2013._12.connection._interface.ServiceException;
import org.ogf.schemas.nsi._2013._12.connection.types.ReservationRequestCriteriaType;
import org.ogf.schemas.nsi._2013._12.connection.types.ScheduleType;
import org.ogf.schemas.nsi._2013._12.services.point2point.P2PServiceBaseType;
import org.ogf.schemas.nsi._2013._12.services.types.DirectionalityType;
import org.w3c.dom.Node;

/**
 * Houses parameters extracted from P2PServiceBaseType.
 * Objects of this class are created in Coordinator.reserve method.
 */
final public class P2PServiceParameters {
	
	private Calendar startTime;		// if null reservation should start immediately
	private Calendar endTime;		// if null reservation lasts forever or until terminate message arrives 
	
	private int version;		
	private String serviceType;
	
	// service specific (P2P) fields
	private String sourceSTP;		// fully or underspecified STP (definition of STP is unknown at the time of this writing)
	private String destSTP;			// fully or underspecified STP (definition of STP is unknown at the time of this writing)
	private long capacity;
	private boolean bidirectional;
	private Boolean symmetricPath;
	private Ero ero;
	
	// additional parameters (unlikely to add as this is something NSI-CONTEST will not be dealing with)
	// schema extensions (unlikely to add as this is something NSI-CONTEST will not be dealing with)
		
	public P2PServiceParameters() { }
	
	public Calendar getStartTime() {
		return startTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public int getVersion() {
		return version;
	}

	public String getServiceType() {
		return serviceType;
	}

	public String getSourceSTP() {
		return sourceSTP;
	}

	public String getDestSTP() {
		return destSTP;
	}

	public long getCapacity() {
		return capacity;
	}

	public boolean isBidirectional() {
		return bidirectional;
	}

	public Boolean getSymmetricPath() {
		return symmetricPath;
	}
	
	public Ero getEro() {
		return ero;
	}

	/**
	 * This method should be called in Coordinator.reserve when header's replyTo field is null. It will validate the contents of
	 * P2PServiceParamaters and return appropriate error. Also, if replyTo is set, this method should be called during CHECKING state.
	 * TODO add topology to check for existence of STPs. 
	 * 
	 * @param nsaId
	 * @param connectionId
	 * @param currentVersion
	 * @param supportedServiceType
	 * @return
	 */
	public ServiceException validate(String nsaId, String connectionId, int currentVersion, String supportedServiceType) {
		
		if (version <= currentVersion) 
			return NsiError.create("incorrect version", nsaId, connectionId, supportedServiceType, NsiErrorId.UNSUPPORTED_PARAMETER);
		
		if (serviceType == null || !serviceType.equalsIgnoreCase(supportedServiceType))
			return NsiError.create("incorrect serviceType - " + serviceType, nsaId, connectionId, supportedServiceType, NsiErrorId.UNSUPPORTED_PARAMETER);
		
		if (startTime != null) {			
			if (startTime.before(Calendar.getInstance())) 
				return NsiError.create("start time set before current time", nsaId, connectionId, supportedServiceType, NsiErrorId.UNSUPPORTED_PARAMETER);				
			
			if (endTime != null) {				
				if (endTime.before(startTime)) 
					return NsiError.create("end time set before start time", nsaId, connectionId, supportedServiceType, NsiErrorId.UNSUPPORTED_PARAMETER);
			}			
		} else {

			if (endTime != null) {				
				if (endTime.before(Calendar.getInstance())) 
					return NsiError.create("end time set before current time", nsaId, connectionId, supportedServiceType, NsiErrorId.UNSUPPORTED_PARAMETER);
			}
		}
		
		// TODO validate p2p specific parameters - unfortunately topology is needed to do that
		
		// no error found
		return null;
	}
	
	/**
	 * This method is intended to convert reserve parameter ReservationRequestCriteriaType into more convenient P2PServiceParameters object. 
	 * @param nsaId
	 * @param criteria
	 * @return
	 * @throws ServiceException
	 */
	static public P2PServiceParameters fromReservationRequestCriteriaType(String nsaId, ReservationRequestCriteriaType criteria) throws ServiceException {
		
		P2PServiceParameters parameters = new P2PServiceParameters();
		
		ScheduleType schedule = criteria.getSchedule();
		if (schedule != null) {

			if (schedule.getStartTime() != null)
				parameters.startTime = schedule.getStartTime().toGregorianCalendar();
			
			if (schedule.getEndTime() != null)
				parameters.endTime = schedule.getEndTime().toGregorianCalendar();
		}
		
		parameters.version = criteria.getVersion() != null ? criteria.getVersion() : 0;
		parameters.serviceType = criteria.getServiceType();
		
		// extract P2PServiceBaseType
		
		if (criteria.getAny() == null)
			throw NsiError.create("request with no service", nsaId, NsiErrorId.MISSING_PARAMETER);
		
		Object p2p = criteria.getAny().get(0);
		if (p2p == null)
			throw NsiError.create("request with no service", nsaId, NsiErrorId.MISSING_PARAMETER);
		
		P2PServiceBaseType service = null;

		try {
			@SuppressWarnings("rawtypes")
			Class serviceClass = P2PServiceBaseType.class;
			JAXBContext ctx = JAXBContext.newInstance(serviceClass);
			Unmarshaller unmarshaller = ctx.createUnmarshaller();
			@SuppressWarnings("unchecked")
			JAXBElement<P2PServiceBaseType> elemP2P = (JAXBElement<P2PServiceBaseType>)unmarshaller.unmarshal((Node)p2p, serviceClass);
			service = elemP2P.getValue();			
		} catch (Exception e) {
						
			throw NsiError.create("unable to process request - " + e.getMessage(), nsaId, NsiErrorId.INTERNAL_ERROR);
		}
		
		if (service == null)
			throw NsiError.create("unable to process request", nsaId, NsiErrorId.INTERNAL_ERROR);
		
		parameters.sourceSTP = service.getSourceSTP();
		parameters.destSTP = service.getDestSTP();
		parameters.capacity = service.getCapacity();
		parameters.bidirectional = service.getDirectionality() == DirectionalityType.BIDIRECTIONAL;
		parameters.symmetricPath = service.isSymmetricPath();
		parameters.ero = Ero.fromStpListType(service.getEro());
		
		return parameters;
	}
}
