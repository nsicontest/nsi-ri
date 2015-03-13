/**
 * 
 */
package net.geant.nsicontest.reservation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.ogf.schemas.nsi._2013._12.services.types.OrderedStpType;
import org.ogf.schemas.nsi._2013._12.services.types.StpListType;

/**
 * Represents Explicit Route Object that may be specified in the reserve request.
 * Ero should always contain an even number of hops (it is not enforced by this class).
 */
public class Ero {

	// describes single element of ero
	private class Hop {
		String id;
		int num;
	}
	
	private List<Hop> hops = new ArrayList<Hop>();
	
	public Ero() { }
	
	/**
	 * Checks if hop with given id or num already exists
	 * @param id
	 * @param num
	 * @return
	 */
	public boolean exists(String id, int num) {
		
		for (Hop hop : hops) {			
			if (hop.id.equals(id) || hop.num == num)
				return true;
		}		
		return false;
	}

	/**
	 * Adds new hop
	 * @param id
	 * @param num
	 */
	public void addHop(String id, int num) {
		
		if (exists(id, num))
			throw new IllegalArgumentException(String.format("%s,  %s already exists", id, num));
		
		Hop hop = new Hop();
		hop.id = id;
		hop.num = num;
		
		hops.add(hop);
		sort();
	}
	
	/**
	 * Useful methods to remove first and last element, needed when handling ero
	 */
	public void removeFirstLast() {
		
		if (hops.size() > 1) {
			hops.remove(0);
			hops.remove(hops.size() - 1);
		}
	}
	
	public boolean isEmpty() {
		
		return hops.isEmpty();
	}
	
	public boolean hasEvenHops() {
		
		return hops.size() % 2 == 0;
	}
	
	/**
	 * Sorts internal hops according to their order (num field)
	 */
	public void sort() {
		
		Collections.sort(hops, new Comparator<Hop>() {

			@Override
			public int compare(Hop o1, Hop o2) {
				if (o1.num < o2.num)
					return -1;
				else if (o1.num > o2.num)
					return 1;
				else
					return 0;
			}
		});
	}
	
	/**
	 * Converts ero to nsi representation
	 * @return
	 */
	public StpListType toStpListType() {
		
		StpListType list = new StpListType();

		for (Hop hop : hops) {
			OrderedStpType stp = new OrderedStpType();
			stp.setStp(hop.id);
			stp.setOrder(hop.num);
			list.getOrderedSTP().add(stp);
		}
		return list;
	}
	
	/**
	 * This is helper method for reading path (comma separated STPs) from String.
	 * Order numbers are assigned from 0 to stps.length - 1
	 * @param hops
	 * @return
	 */
	public static Ero fromString(String hops) {
		
		Ero ero = new Ero();
		
		try {
			int index = 0;
			String[] tokens = hops.split(",");
			for (String s : tokens) {
				ero.addHop(s, index++);
			}
		} catch (Exception e) { }
		
		return ero;
	}
	
	/**
	 * Creates Ero object from Nsi ero representation
	 * @param list
	 * @return
	 */
	public static Ero fromStpListType(StpListType list) {
		
		if (list == null || list.getOrderedSTP() == null)
			return null;

		// Ero object is created regardless list argument is valid or not (i.e. the number of hops is odd)
		
		Ero ero = new Ero();
		for (OrderedStpType stp : list.getOrderedSTP()) {			
			ero.addHop(stp.getStp(), stp.getOrder());
		}		
		return ero;
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		for (Hop hop : hops) {			
			sb.append(String.format("%s - %s\n", hop.num, hop.id));
		}		
		return sb.toString();
	}
}
