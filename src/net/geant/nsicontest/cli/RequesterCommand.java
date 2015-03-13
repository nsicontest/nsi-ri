/**
 * 
 */
package net.geant.nsicontest.cli;


import net.geant.nsicontest.reservation.FileRequester;
import net.geant.nsicontest.reservation.Requester;
import net.geant.nsicontest.reservation.RequesterManager;

/**
 * Handles commands associated with the Requester functionality.
 */
final class RequesterCommand extends Command {
	
	private Requester requester;
	
	RequesterCommand() {
		
		super("requester", "req", "interaction with requester", true);
		
		addCommandHelp("all", "displays all requesters connectionId");
		addCommandHelp("mode", "displays current requester mode");
		addCommandHelp("new", "creates new requester");
		addCommandHelp("newasync", "creates new requester");
		addCommandHelp("rescomprov", "issues reserve, commit and provision messages");
		addCommandHelp("reserve", "issues reserve message");
		addCommandHelp("modify [connectionId]", "issues modify message");
		addCommandHelp("commit [connectionId]", "issues reserveCommit message");
		addCommandHelp("abort [connectionId]", "issues reserveAbort message");
		addCommandHelp("provision [connectionId]", "issues provision message");
		addCommandHelp("release [connectionId]", "issues release message");
		addCommandHelp("terminate [connectionId]", "issues terminate message");
		addCommandHelp("query [connectionId]", "issues query message");
		addCommandHelp("queryall | qall [connectionId]", "issues query message to retrieve all reservations");
		addCommandHelp("querysync | qsync [connectionId]", "calls querySync");
		addCommandHelp("queryrec | qrec [connectionId]", "calls query recursive");
		addCommandHelp("querynot | qnot [connectionId]", "calls query notification");		
	}
	
	private Requester getRequester(String connectionId) throws Exception {
		
		Requester req = ((RequesterManager)RequesterManager.getInstance()).get(connectionId);
		if (req == null) throw new Exception("requester not found: " + connectionId);
		return req;
	}
	
	@Override
	public String process(String... args) throws Exception {

		String cmd = args[0];
			
		if (cmd.equalsIgnoreCase("all")) {
		
			if (args.length != 1) return unknownArgs(args);
			return RequesterManager.getConnectionsId();			
			
		} else if (cmd.equalsIgnoreCase("mode")) {
			
			if (args.length != 1) return unknownArgs(args);			
			if (requester == null)
				return "requester not created";
			
			return requester.getClass().getSimpleName();
			
		} else if (cmd.equalsIgnoreCase("new")) {
			
			if (args.length != 1) return unknownArgs(args);
			requester = FileRequester.create(FileRequester.FILE_LOCATION, true);
			return "new requester created";
			
		} else if (cmd.equalsIgnoreCase("newasync")) {
			
			if (args.length != 1) return unknownArgs(args);
			requester = FileRequester.create(FileRequester.FILE_LOCATION, false);
			return "new requester created";
			
		} else if (cmd.equalsIgnoreCase("rescomprov")) {
			
			if (args.length != 1) return unknownArgs(args);
			requester = FileRequester.create(FileRequester.FILE_LOCATION, true);			
			requester.reserve();
			requester.reserveCommit();
			requester.provision();
			return requester.getConnectionId();
			
		} else if (cmd.equalsIgnoreCase("reserve") || cmd.equalsIgnoreCase("res")) {
			
			if (args.length != 1) return unknownArgs(args);			
			if (requester == null)  
				requester = FileRequester.create(FileRequester.FILE_LOCATION, true);				
			requester.reserve();			
			return requester.getConnectionId();
			
		} else if (cmd.equalsIgnoreCase("modify") || cmd.equalsIgnoreCase("mod")) {
			
			if (args.length == 1) {			
				requester.modify();
			} else if (args.length == 2) {
				getRequester(args[1]).modify();				
			}
			return "modify message sent";
			
		} else if (cmd.equalsIgnoreCase("commit") || cmd.equalsIgnoreCase("com")) {
			
			if (args.length == 1) {
				requester.reserveCommit();
			} else if (args.length == 2) {
				getRequester(args[1]).reserveCommit();
			}
			return "commit message sent";
			
		} else if (cmd.equalsIgnoreCase("abort") || cmd.equalsIgnoreCase("abo")) {
			
			if (args.length == 1) {
				requester.reserveAbort();
			} else if (args.length == 2) {
				getRequester(args[1]).reserveAbort();
			}
			return "abort message sent";
				
		} else if (cmd.equalsIgnoreCase("provision") || cmd.equalsIgnoreCase("prov")) {
			
			if (args.length == 1) {
				requester.provision();
			} else if (args.length == 2) {
				getRequester(args[1]).provision();
			}
			return "provision message sent";
			
		} else if (cmd.equalsIgnoreCase("release") || cmd.equalsIgnoreCase("rel")) {
			
			if (args.length == 1) {
				requester.release();
			} else if (args.length == 2) {
				getRequester(args[1]).release();
			}
			return "release message sent";
				
		} else if (cmd.equalsIgnoreCase("terminate") || cmd.equalsIgnoreCase("term")) {
			
			if (args.length == 1) {
				requester.terminate();
			} else if (args.length == 2) {
				getRequester(args[1]).terminate();
			}
			return "terminate message sent";
							
		} else if (cmd.equalsIgnoreCase("query")) {
			
			if (args.length == 1) {
				requester.querySummary(false);
			} else if (args.length == 2) {
				getRequester(args[1]).querySummary(false);
			}
			return "query summary message sent";
								
		} else if (cmd.equalsIgnoreCase("queryall") || cmd.equalsIgnoreCase("qall")) {
			
			if (args.length == 1) {
				requester.querySummary(true);
			} else if (args.length == 2) {
				getRequester(args[1]).querySummary(true);
			}
			return "query summary all message sent";
						
		} else if (cmd.equalsIgnoreCase("querysync") || cmd.equalsIgnoreCase("qsync")) {
			
			if (args.length == 1) {
				return requester.querySummarySyncAsString(false);
			} else if (args.length == 2) {
				return getRequester(args[1]).querySummarySyncAsString(false);
			}			
			
		} else if (cmd.equalsIgnoreCase("querysyncall") || cmd.equalsIgnoreCase("qsyncall")) {
			
			if (args.length == 1) {
				return requester.querySummarySyncAsString(true);
			} else if (args.length == 2) {
				return getRequester(args[1]).querySummarySyncAsString(true);				
			}
			
		} else if (cmd.equalsIgnoreCase("queryrec") || cmd.equalsIgnoreCase("qrec")) {
			
			if (args.length == 1) {
				requester.queryRecursive();
			} else if (args.length == 2) {
				getRequester(args[1]).queryRecursive();
			}
			return "query recursive message sent";
			
		} else if (cmd.equalsIgnoreCase("querynot") || cmd.equalsIgnoreCase("qnot")) {		
			
			if (args.length == 1) {
				requester.queryNotification(Long.MIN_VALUE, Long.MAX_VALUE);
			} else if (args.length == 2) {
				getRequester(args[1]).queryNotification(Long.MIN_VALUE, Long.MAX_VALUE);
			}
			return "query notification message sent";
			
		} else if (cmd.equalsIgnoreCase("querynotsync") || cmd.equalsIgnoreCase("qnotsync")) {
			
			if (args.length == 1) {
				return requester.queryNotificationSyncAsString(Long.MIN_VALUE, Long.MAX_VALUE);
			} else if (args.length == 2) {
				return getRequester(args[1]).queryNotificationSyncAsString(Long.MIN_VALUE, Long.MAX_VALUE);
			}
		} 
		
		return unknownArgs(args);
	}
}
