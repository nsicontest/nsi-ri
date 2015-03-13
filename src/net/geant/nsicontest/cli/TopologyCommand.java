/**
 * 
 */
package net.geant.nsicontest.cli;

import net.geant.nsicontest.topology.GlobalTopology;


/**
 * Handles commands associated with the Topology functionality.
 */
public class TopologyCommand extends Command {

	TopologyCommand() {
		
		super("topology", "topo", "topology manipulation", true);
	
		addCommandHelp("all", "prints all topologies");
		addCommandHelp("this", "prints nsaId assigned to this agent");
		addCommandHelp("clear", "removes all topologies");
		addCommandHelp("add <filename>", "adds topology from filename");
	}
	
	@Override
	public String process(String... args) throws Exception {
		
		if (args.length == 1) {
		
			String cmd = args[0];
			
			if (cmd.equalsIgnoreCase("all")) {								
				return GlobalTopology.getInstance().info();				
			} else if (cmd.equalsIgnoreCase("this")) {				
				return GlobalTopology.getInstance().getLocalNsaId();				
			} else if (cmd.equalsIgnoreCase("clear")) {				
				GlobalTopology.getInstance().clear();
				return null;
			}
			
		} else if (args.length == 2) {
		
			if (args[0].equalsIgnoreCase("add")) {
				
				GlobalTopology.getInstance().addOrUpdateFromFile(args[1]);
				return null;
			}			
		}
		
		return unknownArgs(args);
	}
}
