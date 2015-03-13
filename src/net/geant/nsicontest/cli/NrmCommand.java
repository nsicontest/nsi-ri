/**
 * 
 */
package net.geant.nsicontest.cli;

import net.geant.nsicontest.nrm.TopologyConverter;
import net.geant.nsicontest.reservation.Coordinator;
import net.geant.nsicontest.topology.TopologyParser;

import org.ogf.schemas.nsi._2013._09.topology.NSAType;

/**
 * Handles commands associated with the Nrm functionality.
 */
final class NrmCommand extends Command {

	NrmCommand() {
		
		super("nrm", "nrm", "nrm interaction", true);
		
		addCommandHelp("topology | topo", "calls getTopology on provided nrm impl and saves it in nml format");
		addCommandHelp("new", "recreates nrm");
		addCommandHelp("impl", "displays nrm impl name");
	}
	
	private String getId(String nsaId) {

		return nsaId.replace("urn:ogf:network:", "").replace(":2013:nsa", "");		
	}
	
	@Override
	public String process(String... args) throws Exception {
		
		if (args.length == 1) {
		
			String cmd = args[0];
			
			if (cmd.equalsIgnoreCase("topology") || cmd.equalsIgnoreCase("topo")) {
				
				net.geant.nsicontest.nrm.Topology topo = Coordinator.getNrm().getTopology();
				NSAType nsa = TopologyConverter.convert(topo);
				String filename = getId(topo.getNsaId());
				TopologyParser.save(filename + ".xml", nsa);				
				return "nrm topology exported as " + filename;
				
			} else if (cmd.equalsIgnoreCase("impl")) {				
				return Coordinator.getNrm().getClass().getCanonicalName();			
			} else if (cmd.equalsIgnoreCase("new")) {				
				Coordinator.createNrm();
				return null;
			}
		}
		
		return unknownArgs(args);
	}
}
