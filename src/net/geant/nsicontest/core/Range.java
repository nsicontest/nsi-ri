/**
 * 
 */
package net.geant.nsicontest.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Contains a continuous range of numbers (integers) denoted with min and max. 
 * Min and max can be equal to represent single number.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Range", propOrder = { "min", "max" })
final public class Range implements Comparable<Range> {
	
	private Integer min, max;
	
	public Range() { }
	
	public Range(Integer min, Integer max) {
		
		if (min > max) throw new IllegalArgumentException(min + " cannot be greater than " + max);
		
		this.min = min;
		this.max = max;		
	}
	
	/**
	 * Creates new Range from String. This can be a single digit or digit-digit format.
	 * @param range
	 * @return
	 */
	static public Range create(String range) {
		
		if (range == null || range.isEmpty())
			throw new IllegalArgumentException("empty range");
		
		String[] parts = range.split("\\-");
		
		if (parts.length == 1) {			
			int num = Integer.parseInt(parts[0]);
			return new Range(num, num);			
		} else if (parts.length == 2) {			
			int min = Integer.parseInt(parts[0]);
			int max = Integer.parseInt(parts[1]);
			return new Range(min, max);			
		} else {
			throw new IllegalArgumentException("too many ranges");
		}
	}
	
	public Integer min() { return min; }
	
	public Integer max() { return max; }
	
	public int count() { return max - min; }
	
	public List<Integer> list() {
		
		List<Integer> numbers = new ArrayList<Integer>();
		for (int i = min; i != max; i++)
			numbers.add(i);
		
		return Collections.unmodifiableList(numbers);
	}
		
	public boolean contains(Range range) {
		
		return range.min >= this.min && range.max <= this.max;		
	}
	
	public boolean constains(Integer number) {
		
		return number >= min && number <= max;
	}
	
	public boolean intersects(Range range) {
		
		return (range.min >= this.min && range.min <= this.max) || (range.max >= this.min && range.max <= this.max); 
	}
	
	public Range subRange(Range range) {
		
		if (range.min > this.max || range.max < this.min)
			return null;
		
		int min = range.min <= this.min ? this.min : range.min;
		int max = range.max >= this.max ? this.max : range.max;
		
		return new Range(min, max);
	}
	
	@Override
	public int compareTo(Range o) {
		
		if (this.min > o.min || this.max > o.max) return 1;
		if (this.min < o.min || this.max < o.max) return -1;		
		return 0;
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (o == null || !(o instanceof Range)) return false;
		Range r = (Range)o;
		return min.equals(r.min) && max.equals(r.max);
	}
	
	@Override
	public int hashCode() {
		return min.hashCode() ^ max.hashCode();
	}
	
	@Override
	public String toString() {
		
		return String.format("%s-%s", min, max);
	}
}
