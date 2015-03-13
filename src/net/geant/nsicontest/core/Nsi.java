/**
 * 
 */
package net.geant.nsicontest.core;



import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;

/**
 * Contains well-known NSI constants and few helper methods.
 */
public class Nsi {
	
	final public static String FRAMEWORK_TYPES_NAMESPACE = "http://schemas.ogf.org/nsi/2013/07/framework/types";
	
	final public static String PROVIDER_VERSION = "application/vdn.ogf.nsi.cs.v2.provider+soap";
	final public static String REQUESTER_VERSION = "application/vdn.ogf.nsi.cs.v2.requester+soap";
	final public static String TOPOLOGY_TYPE = "application/vnd.org.ogf.nsi.cs.v2+soap";
	final public static String SERVICE_TYPE = "http://services.ogf.org/nsi/2013/07/descriptions/EVTS.A-GOLE";
	
	final public static String STP_LABEL_DELIMITER = "?";
    final public static String URN_DELIMITER = ":";
	
	public static final int INITIAL_VERSION = 0;
	
	
	/**
	 * Creates string representation of STP
	 * @param localId
	 * @param vlans
	 * @return
	 */
	public static String assemblyStp(String localId, String vlans) {
        String format;
		format = (vlans == null) ? "%s" : "%s?vlan=%s"; 
		return String.format(format, localId, vlans);
	}
	
	/**
	 * Creates string representation of STP
	 * @param localId
	 * @param vlan
	 * @return
	 */
	public static String assemblyStp(String localId, int vlan) {
		
		return assemblyStp(localId, String.valueOf(vlan));
	}

	/**
	 * Returns an uuid, suitable for connectionId and correlationId
	 * @return
	 */
	public static String generateUuid() {
		
		return "urn:uuid:" + UUID.randomUUID().toString();
	}		

	public static Holder<CommonHeaderType> createHeader(String protocolVersion, String correlationId, String providerNSA, String requesterNSA, String replyTo) {
		
		CommonHeaderType header = new CommonHeaderType();
		
		header.setProtocolVersion(protocolVersion);
		header.setCorrelationId(correlationId);
		header.setProviderNSA(providerNSA);
		header.setRequesterNSA(requesterNSA);
		header.setReplyTo(replyTo);
		
		return new Holder<CommonHeaderType>(header);
	}
	
	public static Holder<CommonHeaderType> createHeader(String providerNSA, String requesterNSA, String replyTo) {
	
		return createHeader(Nsi.PROVIDER_VERSION, Nsi.generateUuid(), providerNSA, requesterNSA, replyTo);
	}
	
	/**
	 * Converts from Date to XMLGregorianCalendar used by web services
	 * @param time
	 * @return
	 * @throws DatatypeConfigurationException
	 */
	public static XMLGregorianCalendar createXmlCalendar(Date time) {
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(time);
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		} catch (DatatypeConfigurationException e) { 
			return null;
		}
	}
	
	/**
	 * Converts Calendar into XMLGregorianCalendar
	 * @param cal
	 * @return
	 */
	public static XMLGregorianCalendar createXmlCalendar(Calendar cal) {
		
		return createXmlCalendar(cal.getTime());		
	}	
	
	/**
	 * Converts time in seconds to format suitable for web services
	 * @param time
	 * @return
	 */
	public static XMLGregorianCalendar createXmlCalendar(int time) {
		
		Calendar s = Calendar.getInstance();
		s.add(Calendar.SECOND, time);
		return createXmlCalendar(s.getTime());
	}
	
	/**
	 * Returns representation of current time suitable to be sent with web services
	 * @param 
	 * @return
	 */
	public static XMLGregorianCalendar createXmlCalendar() {
		
		return createXmlCalendar(Calendar.getInstance().getTime());		
	}
}
