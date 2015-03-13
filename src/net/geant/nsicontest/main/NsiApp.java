/**
 * 
 */
package net.geant.nsicontest.main;


import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.xml.ws.Endpoint;

import net.geant.nsicontest.cli.Cli;
import net.geant.nsicontest.reservation.Coordinator;
import net.geant.nsicontest.reservation.Responder;
import net.geant.nsicontest.topology.GlobalTopology;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.CXFBusFactory;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.endpoint.ServerRegistry;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.log4j.PropertyConfigurator;
import org.ogf.schemas.nsi._2013._12.connection.provider.ConnectionProviderPortImpl;
import org.ogf.schemas.nsi._2013._12.connection.requester.ConnectionRequesterPortImpl;

/**
 * Starting point for the application.
 */
public class NsiApp {
	
	public static final String CONFIG_LOCATION = "etc/nsi.properties";
	public static Config config;
		
	private static boolean isEndpointRegistered(String endpoint) {
		
		Bus bus = CXFBusFactory.getDefaultBus();
        ServerRegistry serverRegistry = bus.getExtension(ServerRegistry.class);
        
        for (Server server : serverRegistry.getServers()) {
        
        	String address = server.getEndpoint().getEndpointInfo().getAddress();
        	if (address.equals(endpoint))
        		return true;
        }
		return false;
	}
	
	/**
	 * Re-reads config file allowing some properties to be changed on the fly
	 * @throws Exception
	 */
	public static void reloadConfig() throws Exception {
		
		PropertyConfigurator.configure("etc/log4j.properties");
		config = Config.load(CONFIG_LOCATION, ConfigKeys.values());

		String ip = config.getString(ConfigKeys.IP);
		int port = config.getInteger(ConfigKeys.PORT);
		boolean logSoap = config.getBoolean(ConfigKeys.LOG_SOAP_MESSAGES);
		String requesterUrl = null;
		
		if (ip != null && !ip.isEmpty()) {
			
			String providerUrl = String.format("http://%s:%s/nsicontest/ConnectionProvider", ip, port);
			
			if (!isEndpointRegistered(providerUrl)) {
				EndpointImpl provider = (EndpointImpl)Endpoint.publish(providerUrl, new ConnectionProviderPortImpl());
				if (logSoap) {
					provider.getServer().getEndpoint().getInInterceptors().add(new LoggingInInterceptor());
					provider.getServer().getEndpoint().getOutInterceptors().add(new LoggingOutInterceptor());
				}
			}		
			
			requesterUrl = String.format("http://%s:%s/nsicontest/ConnectionRequester", ip, port);
			
			if (!isEndpointRegistered(requesterUrl)) {
				
				EndpointImpl requester = (EndpointImpl)Endpoint.publish(requesterUrl, new ConnectionRequesterPortImpl());
				if (logSoap) {
					requester.getServer().getEndpoint().getInInterceptors().add(new LoggingInInterceptor());
					requester.getServer().getEndpoint().getOutInterceptors().add(new LoggingOutInterceptor());
				}
			}

			String topologyUrl = String.format("http://%s:%s/nsicontest/topology", ip, port);
			
			if (!isEndpointRegistered(topologyUrl)) {
				EndpointImpl topology = (EndpointImpl)Endpoint.publish(topologyUrl, GlobalTopology.getInstance());
				if (logSoap) {
					topology.getServer().getEndpoint().getInInterceptors().add(new LoggingInInterceptor());
					topology.getServer().getEndpoint().getOutInterceptors().add(new LoggingOutInterceptor());
				}
			}
		}
		
		Responder.logEnabled = config.getBoolean(ConfigKeys.LOG_RESPONDER);
		
		// set Coordinator
		ConfigParameters params = new ConfigParameters();		
		params.setProviderNSA(config.getString(ConfigKeys.PROVIDER_NSA));
		params.setReplyTo(requesterUrl);
		params.setServiceType(config.getString(ConfigKeys.SERVICE_TYPE));
		params.setHeldTimeout(config.getInteger(ConfigKeys.RESERVE_HELD_TIMEOUT));
		params.setFailIfResponseNotDelivered(config.getBoolean(ConfigKeys.FAIL_IF_RESPONSE_NOT_DELIVERED)); 
		params.setNumThreads(config.getInteger(ConfigKeys.NUM_THREADS));
		params.setLogHeader(config.getBoolean(ConfigKeys.LOG_HEADER));
		params.setDisabledNsiContest(config.getBoolean(ConfigKeys.DISABLED_NSI_CONTEST));
		params.setRabbitmqEnabled(config.getBoolean(ConfigKeys.RABBITMQ_ENABLED));
		params.setRabbitmqHost(config.getString(ConfigKeys.RABBITMQ_HOST));
		params.setNrmClass(config.getString(ConfigKeys.NRM));
		
		Coordinator.initialize(params);
		
		// set GlobalTopology
		String localNsaId = config.getString(ConfigKeys.PROVIDER_NSA);
		GlobalTopology.getInstance().setLocalNsaId(localNsaId);
		
		String topologies = config.getString(ConfigKeys.TOPOLOGIES).trim();
		if (topologies != null && !topologies.isEmpty()) {				
			String[] tokens = topologies.trim().split(",");		
			for (String s : tokens) {			
				GlobalTopology.getInstance().addFromFile(s.trim());
			}
		}
	}
	
	public static void shutdown() {
		
		Coordinator.shutdown();
		System.exit(0);
	}
	
	public static void process() throws Exception {
		
		int telnetPort = config.getInteger(ConfigKeys.TELNET_PORT);

		if (telnetPort != 0) {			
			if (telnetPort > 1024 && telnetPort <= 65535) {
				new SimpleTelnet(telnetPort);				
			} else {
				throw new Exception("telnet port must be specified within 1024-65535 range, unlike " + telnetPort);				
			}
		}
				
		boolean cliEnabled = config.getBoolean(ConfigKeys.CLI);
		
		if (cliEnabled) {
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				
				System.out.println("Enter a command:");			
				String line = br.readLine();			
				String result = Cli.process(line);
				
				if (result == null || result.isEmpty())
					continue;
				
				if (result.equalsIgnoreCase("exit") || result.equalsIgnoreCase("quit") || result.equals("shutdown"))
					return;
								
				System.out.println(result);
			}
		} 
			
		while (true) {
			Thread.sleep(Long.MAX_VALUE);
		}
	}
	
	public static void main(String[] args) {
		
		try {
			reloadConfig();
			process();
		} catch (Exception e) {
			System.out.println("An unknown error occured - " + e.getMessage());
		} finally {
			shutdown();
		}
	}
}
