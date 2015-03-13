/**
 * 
 */
package net.geant.nsicontest.cli;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.geant.nsicontest.main.NsiApp;

/**
 * Handles command line. Only one cli exists.
 */
public class Cli {
	
	static final private Map<String, Command> commands = new HashMap<String, Command>();
	static final private Map<String, Command> shortCommands = new HashMap<String, Command>();	
	static final private StringBuilder sb = new StringBuilder();
	
	static {
	
		sb.append("nsi available commands:\r\n");
		sb.append("exit | quit - exits application\r\n");
		sb.append("reload - reloads configuration\r\n");
		sb.append("help - prints help info\r\n");
		
		addCommand(new ProviderCommand());
		addCommand(new RequesterCommand());
		addCommand(new TopologyCommand());
		addCommand(new NrmCommand());
		addCommand(new PathCommand());
		addCommand(new InventoryCommand());
	}
		
	synchronized static public void addCommand(Command command) {
	
		commands.put(command.getName(), command);
		shortCommands.put(command.getShortName(), command);
		
		if (command.isShowHelp())
			sb.append(command.help());
	}
			
	synchronized static public String process(String line) {
		
		try {
			
			String[] tokens = line.trim().split(" ");
			
			if (tokens == null || tokens.length == 0)
				return "command was not specified";
			
			String cmd = tokens[0];
			
			if (cmd.equalsIgnoreCase("help")) {
				return help();
			} else if (cmd.equalsIgnoreCase("exit") || cmd.equalsIgnoreCase("quit")) {
				return cmd;
			} else if (cmd.equalsIgnoreCase("reload")) {
				NsiApp.reloadConfig();
				return "config reloaded";
			} else {

				Command com = commands.get(cmd);
				if (com == null)
					com = shortCommands.get(cmd);				
		
				if (com != null) {				
					return com.process(Arrays.copyOfRange(tokens, 1, tokens.length));					
				} else {
					return cmd + " was not recognized";
				}
			}
			
		} catch (Exception e) { 			
			return "unexcpected error - " + e.getMessage();
		}
	}
	
	static public String help() {
		
		return sb.toString();
	}
}
