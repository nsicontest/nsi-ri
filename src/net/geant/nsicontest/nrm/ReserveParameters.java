/**
 * 
 */
package net.geant.nsicontest.nrm;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import net.geant.nsicontest.core.Range;

/**
 * Contains reservation parameters that are requested by Provider. Correlates to P2PServiceBaseType.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReserveParameters", propOrder = { "startTime", "endTime", "sourceLocalId", "sourceVlans", "destLocalId", "destVlans",
			"capacity", "bidirectional", "symmetric" })
public class ReserveParameters {
	
	private Calendar startTime;
	private Calendar endTime;
	
	private String sourceLocalId;
	private Range sourceVlans;
	
	private String destLocalId;
	private Range destVlans;
	
	private long capacity;
	private boolean bidirectional;
	private boolean symmetric;
	
	public ReserveParameters() { }
	
	public ReserveParameters(Calendar startTime, Calendar endTime, String sourceLocalId, Range sourceVlans, String destLocalId,
			Range destVlans, long capacity, boolean bidirectional,	boolean symmetric) {

		this.startTime = startTime;
		this.endTime = endTime;
		this.sourceLocalId = sourceLocalId;
		this.sourceVlans = sourceVlans;
		this.destLocalId = destLocalId;
		this.destVlans = destVlans;
		this.capacity = capacity;
		this.bidirectional = bidirectional;
		this.symmetric = symmetric;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public String getSourceLocalId() {
		return sourceLocalId;
	}

	public void setSourceLocalId(String sourceLocalId) {
		this.sourceLocalId = sourceLocalId;
	}

	public Range getSourceVlans() {
		return sourceVlans;
	}

	public void setSourceVlans(Range sourceVlans) {
		this.sourceVlans = sourceVlans;
	}

	public String getDestLocalId() {
		return destLocalId;
	}

	public void setDestLocalId(String destLocalId) {
		this.destLocalId = destLocalId;
	}

	public Range getDestVlans() {
		return destVlans;
	}

	public void setDestVlans(Range destVlans) {
		this.destVlans = destVlans;
	}

	public long getCapacity() {
		return capacity;
	}

	public void setCapacity(long capacity) {
		this.capacity = capacity;
	}

	public boolean isBidirectional() {
		return bidirectional;
	}

	public void setBidirectional(boolean bidirectional) {
		this.bidirectional = bidirectional;
	}

	public boolean isSymmetric() {
		return symmetric;
	}

	public void setSymmetric(boolean symmetric) {
		this.symmetric = symmetric;
	}
}
