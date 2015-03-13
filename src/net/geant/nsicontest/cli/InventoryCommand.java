/**
 * 
 */
package net.geant.nsicontest.cli;

/**
 * Allows checking state of reservations
 */
final class InventoryCommand extends Command {

	InventoryCommand() {
		
		super("inventory", "inv", "checking reservation states", false);
		
	}
}
