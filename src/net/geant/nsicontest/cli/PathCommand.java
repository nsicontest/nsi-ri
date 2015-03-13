/**
 * 
 */
package net.geant.nsicontest.cli;

/**
 * Handles commands associated with the path computation functionality.
 */
final class PathCommand extends Command {

	PathCommand() {
		
		super("path", "path", "commands for path computations", false);
		addCommandHelp("sourceStp destStp", "calculates paths between source and dest STPs");
	}
		
	@Override
	public String process(String... args) throws Exception {
		
		return null;
	}
}
