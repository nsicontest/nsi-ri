/**
 * 
 */
package net.geant.nsicontest.nrm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Contains configurable Nrm parameters, useful for debugging Nrm implementations.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NrmParameters", propOrder = { "reserveFail", "reserveDelay", "modifyFail", "modifyDelay", "commitFail", "commitDelay", 
		"activateFail", "activateDelay", "deactivateFail", "deactivateDelay" })
public class NrmParameters {
	
	private boolean reserveFail;
	private int reserveDelay;
	
	private boolean modifyFail;
	private int modifyDelay;
	
	private boolean commitFail;
	private int commitDelay;
	
	private boolean activateFail;
	private int activateDelay;
	
	private boolean deactivateFail;
	private int deactivateDelay;
	
	public boolean isReserveFail() {
		return reserveFail;
	}
	
	public void setReserveFail(boolean reserveFail) {
		this.reserveFail = reserveFail;
	}
	
	public int getReserveDelay() {
		return reserveDelay;
	}
	
	public void setReserveDelay(int reserveDelay) {
		this.reserveDelay = reserveDelay;
	}
	
	public boolean isModifyFail() {
		return modifyFail;
	}
	
	public void setModifyFail(boolean modifyFail) {
		this.modifyFail = modifyFail;
	}
	
	public int getModifyDelay() {
		return modifyDelay;
	}
	
	public void setModifyDelay(int modifyDelay) {
		this.modifyDelay = modifyDelay;
	}
	
	public boolean isCommitFail() {
		return commitFail;
	}
	
	public void setCommitFail(boolean commitFail) {
		this.commitFail = commitFail;
	}
	
	public int getCommitDelay() {
		return commitDelay;
	}
	
	public void setCommitDelay(int commitDelay) {
		this.commitDelay = commitDelay;
	}
	
	public boolean isActivateFail() {
		return activateFail;
	}
	
	public void setActivateFail(boolean activateFail) {
		this.activateFail = activateFail;
	}
	
	public int getActivateDelay() {
		return activateDelay;
	}
	
	public void setActivateDelay(int activateDelay) {
		this.activateDelay = activateDelay;
	}
	
	public boolean isDeactivateFail() {
		return deactivateFail;
	}
	
	public void setDeactivateFail(boolean deactivateFail) {
		this.deactivateFail = deactivateFail;
	}
	
	public int getDeactivateDelay() {
		return deactivateDelay;
	}
	
	public void setDeactivateDelay(int deactivateDelay) {
		this.deactivateDelay = deactivateDelay;
	}
}
