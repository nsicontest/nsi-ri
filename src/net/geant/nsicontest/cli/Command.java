/**
 * 
 */
package net.geant.nsicontest.cli;

import java.util.Arrays;

/**
 * Supports single command and its parameters that can be invoked from command line.
 */
class Command {
	
	final private StringBuilder sb = new StringBuilder();
	protected String name, shortName, description;
	protected boolean showHelp;
	
	Command(String name, String shortName, String description, boolean showHelp) {
		
		this.name = name;
		this.shortName = shortName;
		this.description = description;
		this.showHelp = showHelp;
		
		if (name.equals(shortName)) {
			sb.append(String.format("%s - %s\r\n", name, description));
		} else {
			sb.append(String.format("%s | %s - %s\r\n", name, shortName, description));
		}
	}
	
	protected void addCommandHelp(String arguments, String description) {
		
		sb.append(String.format("\t%s - %s\r\n", arguments, description));
	}

	public String getName() {
		return name;
	}

	public String getShortName() {
		return shortName;
	}

	public String getDescription() {
		return description;
	}
	
	public boolean isShowHelp() {
		return showHelp;
	}

	/**
	 * Returns description of available commands
	 * @return
	 */
	public String help() {
		
		return sb.toString();
	}
	
	/**
	 * Processes command and returns command output
	 * @param args
	 * @return
	 */
	public String process(String... args) throws Exception {
		
		return getName() +  " - " + Arrays.toString(args);
	}
	
	protected String unknownArgs(String... args) {
		
		return "unknown arguments - " + Arrays.toString(args);
	}
}
