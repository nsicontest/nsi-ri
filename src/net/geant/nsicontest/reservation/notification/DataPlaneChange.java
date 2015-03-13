/**
 * 
 */
package net.geant.nsicontest.reservation.notification;

import java.util.Calendar;

import net.geant.nsicontest.core.Nsi;

import org.ogf.schemas.nsi._2013._12.connection.types.DataPlaneStateChangeRequestType;
import org.ogf.schemas.nsi._2013._12.connection.types.DataPlaneStatusType;

/**
 * DataPlaneStateChange notification.
 */
public class DataPlaneChange extends Notification {
	
	private boolean active;
	private int version;
	private boolean versionConsistent;
	
	public DataPlaneChange() { }
	
	public DataPlaneChange(String connectionId, long notificationId, Calendar timeStamp, boolean active, int version, boolean versionConsistent) {
		
		super(connectionId, notificationId, timeStamp);
		this.active = active;
		this.version = version;
		this.versionConsistent = versionConsistent;
	}
	
	public DataPlaneChange(String connectionId, long notificationId, boolean active, int version, boolean versionConsistent) {
		
		this(connectionId, notificationId, Calendar.getInstance(), active, version, versionConsistent);
	}
	
	static public DataPlaneChange fromDataPlaneStateChangeRequestType(DataPlaneStateChangeRequestType dataPlane) {
	
		DataPlaneStatusType status = dataPlane.getDataPlaneStatus();
		
		return new DataPlaneChange(dataPlane.getConnectionId(), dataPlane.getNotificationId(), dataPlane.getTimeStamp().toGregorianCalendar(), 
				status.isActive(), status.getVersion(), status.isVersionConsistent());	
	}
	
	@Override
	public DataPlaneStateChangeRequestType toNsiType() {
	
		DataPlaneStateChangeRequestType not = new DataPlaneStateChangeRequestType();		
		not.setConnectionId(connectionId);
		not.setNotificationId(notificationId);
		not.setTimeStamp(Nsi.createXmlCalendar(timeStamp));
		DataPlaneStatusType status = new DataPlaneStatusType();
		status.setActive(active);
		status.setVersion(version);
		status.setVersionConsistent(versionConsistent);		
		not.setDataPlaneStatus(status);		
		return not;
	}
	
	@Override
	public String toString() {
		
		return String.format("data plane change notification: %s,  %s, %s, %s", super.toString(), active, version, versionConsistent);
	}
	
	//
	// setters & getters
	//
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public int getVersion() {
		return version;
	}
	
	public void setVersion(int version) {
		this.version = version;
	}
	
	public boolean isVersionConsistent() {
		return versionConsistent;
	}
	
	public void setVersionConsistent(boolean versionConsistent) {
		this.versionConsistent = versionConsistent;
	}
}
