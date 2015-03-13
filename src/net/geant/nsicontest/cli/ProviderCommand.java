/**
 * 
 */
package net.geant.nsicontest.cli;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.ws.Holder;

import net.geant.nsicontest.core.Nsi;
import net.geant.nsicontest.core.NsiInfo;
import net.geant.nsicontest.reservation.Coordinator;

import org.ogf.schemas.nsi._2013._12.connection.types.QueryNotificationType;
import org.ogf.schemas.nsi._2013._12.connection.types.QueryType;
import org.ogf.schemas.nsi._2013._12.connection.types.ReservationRequestCriteriaType;
import org.ogf.schemas.nsi._2013._12.connection.types.ScheduleType;
import org.ogf.schemas.nsi._2013._12.framework.headers.CommonHeaderType;
import org.ogf.schemas.nsi._2013._12.services.point2point.ObjectFactory;
import org.ogf.schemas.nsi._2013._12.services.point2point.P2PServiceBaseType;
import org.ogf.schemas.nsi._2013._12.services.types.DirectionalityType;
import org.w3c.dom.Document;


/**
 * Handles commands associated with the Provider functionality.
 */
final class ProviderCommand extends Command {
	
	private Holder<CommonHeaderType> header = Nsi.createHeader("urn:ogf:network:aruba.example:2013:nsa", "test.requester", null);
	private String sourceStp = "urn:ogf:network:aruba.example:2013:bi-ps";
	private String destStp = "urn:ogf:network:aruba.example:2013:bi-aruba-bonaire";
	private long capacity = 100;
	private DirectionalityType dir = DirectionalityType.BIDIRECTIONAL;
	private Boolean symmetricPath = true; 
	private String connectionId = null;
	private int version = 0;
	
	ProviderCommand() {
		
		super("provider", "prov", "interaction with provider", false);
		
		addCommandHelp("new", "creates new provider");
		addCommandHelp("reserve", "reserves resources");
		addCommandHelp("commit", "commits previously reserved resources");
		addCommandHelp("abort", "aborts previously reserved resources");
		addCommandHelp("provision", "provisions resources");
		addCommandHelp("release", "releases resources");
		addCommandHelp("terminate", "terminates resources");
		addCommandHelp("query", "returns current Provider state");
		addCommandHelp("queryall", "returns state of all Providers");
		addCommandHelp("querynot", "returns current Provider notifications");		
	}
	
	private ReservationRequestCriteriaType createCriteria(Integer version, Integer start, Integer end, String src, String dst) throws Exception {
		
		ReservationRequestCriteriaType criteria = new ReservationRequestCriteriaType();
		
		criteria.setVersion(version);
		criteria.setServiceType(Nsi.SERVICE_TYPE);
		ScheduleType schedule = new ScheduleType();
		schedule.setStartTime(start != null ? Nsi.createXmlCalendar(start) : null);
		schedule.setEndTime(end != null ? Nsi.createXmlCalendar(end) : null);
		criteria.setSchedule(schedule);
		
		org.ogf.schemas.nsi._2013._12.services.point2point.ObjectFactory of = new org.ogf.schemas.nsi._2013._12.services.point2point.ObjectFactory(); 
		P2PServiceBaseType p2p = of.createP2PServiceBaseType();
		
		p2p.setSourceSTP(src);
		p2p.setDestSTP(dst);	 
		p2p.setCapacity(capacity);
		p2p.setDirectionality(dir);
		p2p.setSymmetricPath(symmetricPath);
	
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
		criteria.getAny().add(document.getDocumentElement());
		
		return criteria;
	}

	@Override
	public String process(String... args) throws Exception {

		if (args.length == 1) {
			
			String cmd = args[0];
			
			if (cmd.equalsIgnoreCase("new")) {
				
				connectionId = null;
				version = 0;
				return "set connectionId: null, version: 0";
			
			} else if (cmd.equalsIgnoreCase("reserve")) {

				Holder<String> conId = new Holder<String>(connectionId);
				Coordinator.reserve(header, conId, null, "test reservation", createCriteria(version, 30, 60, sourceStp, destStp));
				connectionId = conId.value;
				version++;
				
			} else if (cmd.equalsIgnoreCase("commit")) {
				
				Coordinator.reserveCommit(header, connectionId);
				
			} else if (cmd.equalsIgnoreCase("abort")) {

				Coordinator.reserveAbort(header, connectionId);
				
			} else if (cmd.equalsIgnoreCase("provision")) {
				
				Coordinator.provision(header, connectionId);
				
			} else if (cmd.equalsIgnoreCase("release")) {
				
				Coordinator.release(header, connectionId);
				
			} else if (cmd.equalsIgnoreCase("terminate")) {
				
				Coordinator.terminate(header, connectionId);				
				
			} else if (cmd.equalsIgnoreCase("query")) {
				
				QueryType query = new QueryType();
				query.getConnectionId().add(connectionId);
				return NsiInfo.asString(Coordinator.querySummarySync(header, query));
				
			} else if (cmd.equalsIgnoreCase("queryall")) {
				
				QueryType query = new QueryType();
				return NsiInfo.asString(Coordinator.querySummarySync(header, query));
				
			} else if (cmd.equalsIgnoreCase("querynot")) {
				
				QueryNotificationType query = new QueryNotificationType();
				query.setConnectionId(connectionId);
				query.setStartNotificationId(Long.MIN_VALUE);
				query.setEndNotificationId(Long.MAX_VALUE);;
				return NsiInfo.asString(Coordinator.queryNotificationSync(header, query));
				
			} else {		
				return unknownArgs(args);
			}
			
			return null;
		}
		
		return unknownArgs(args);
	}
}
