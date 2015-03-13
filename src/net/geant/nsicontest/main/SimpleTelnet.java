/**
 * 
 */
package net.geant.nsicontest.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import net.geant.nsicontest.cli.Cli;

import org.apache.log4j.Logger;

/**
 * Opens a tcp port and awaits connections (must be local, remote will be rejected).
 * Once connection has been established, it offers command line functionality.
 */
final public class SimpleTelnet extends Thread {

	final private static Logger log = Logger.getLogger(SimpleTelnet.class);
	
	private int port;

	SimpleTelnet(int port) {
		
		this.port = port;
		this.start();
		log.info("command line server started on port " + port);
	}

	@Override
	public void run() { 
		
		ServerSocket server = null;
		Socket client = null;
		PrintWriter out = null;
		BufferedReader in = null;
		boolean stop = false;
		boolean shutdown = false;
		
		try {
			 server = new ServerSocket(port);
		} catch (IOException e) {
			log.info("command line server could not start listening - " + e.getMessage());
			return;			
		}
										
		while (true) {

			try {
				if (client == null) {
					client = server.accept();
					stop = false;
					if (!client.getInetAddress().isLoopbackAddress()) {
						client.close();
						log.info("connection attempt from remote host - " + client.getInetAddress().getHostName());
						client = null;
						continue;
					} else {
						log.info("new client connected - " + client.getInetAddress().getHostAddress());
					}

					out = new PrintWriter(client.getOutputStream(), true);
					in = new BufferedReader(new InputStreamReader(client.getInputStream()));
					out.println(Cli.help());
				}
			} catch (IOException e) { }

			try {
				out.print("nsi>");
				out.flush();
				
				String line = in.readLine();
				String output = Cli.process(line);
				if (output == null || output.isEmpty())
					continue;
				
				out.write(output + "\r\n");
				out.flush();
				if (output.equals("exit") || output.equals("quit")) {
					log.info("dropping client");
					stop = true;
				} else if (output.equals("shutdown")) {
					log.info("dropping client");
					stop = true;
					shutdown = true;
				} else if (output.equals("unexcpected error - null")) {	// a rather strange way of detecting telnet client drop	
					log.info("client dropped due to ctrl + ]");
					client = null;
				} 
				
			} catch (Exception e) {
				log.info("connection dropped - " + e.getMessage());
				client = null;
			}

			if (stop) {
				try {
					out.close();
					in.close();
					client.close();
					client = null;
					if (shutdown) {
						server.close();
						NsiApp.shutdown();
						break;						
					}
				} catch (IOException e) { }
			}
		}
	}
}
