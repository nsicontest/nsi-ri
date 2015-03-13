/**
 * 
 */
package net.geant.nsicontest.main;

/**
 * Stores configuration parameters (a subset of ConfigKeys) for Coordinator initialization
 */
public class ConfigParameters {
	
	private String providerNSA;
	private String replyTo;
	private String serviceType;
	private int heldTimeout;
	private boolean failIfResponseNotDelivered;
	private boolean setEro;
	private int requesterTimeout;
	private int numThreads;
	private boolean logHeader;
	private boolean disabledNsiContest;
	private boolean rabbitmqEnabled;
	private String rabbitmqHost;
	private String nrmClass;
	private String topologies;
	
	
	public String getProviderNSA() {
		return providerNSA;
	}
	
	public void setProviderNSA(String providerNSA) {
		this.providerNSA = providerNSA;
	}
	
	public String getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

	public String getServiceType() {
		return serviceType;
	}
	
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	public int getHeldTimeout() {
		return heldTimeout;
	}
	
	public void setHeldTimeout(int heldTimeout) {
		this.heldTimeout = heldTimeout;
	}
	
	public boolean isFailIfResponseNotDelivered() {
		return failIfResponseNotDelivered;
	}
	
	public void setFailIfResponseNotDelivered(boolean failIfResponseNotDelivered) {
		this.failIfResponseNotDelivered = failIfResponseNotDelivered;
	}
	
	public boolean isSetEro() {
		return setEro;
	}

	public void setSetEro(boolean setEro) {
		this.setEro = setEro;
	}
	
	public int getRequesterTimeout() {
		return requesterTimeout;
	}

	public void setRequesterTimeout(int requesterTimeout) {
		this.requesterTimeout = requesterTimeout;
	}

	public int getNumThreads() {
		return numThreads;
	}
	
	public void setNumThreads(int numThreads) {
		this.numThreads = numThreads;
	}
	
	public boolean isLogHeader() {
		return logHeader;
	}
	
	public void setLogHeader(boolean logHeader) {
		this.logHeader = logHeader;
	}
	
	public boolean isDisabledNsiContest() {
		return disabledNsiContest;
	}
	
	public void setDisabledNsiContest(boolean disabledNsiContest) {
		this.disabledNsiContest = disabledNsiContest;
	}
	
	public boolean isRabbitmqEnabled() {
		return rabbitmqEnabled;
	}
	
	public void setRabbitmqEnabled(boolean rabbitmqEnabled) {
		this.rabbitmqEnabled = rabbitmqEnabled;
	}
	
	public String getRabbitmqHost() {
		return rabbitmqHost;
	}
	
	public void setRabbitmqHost(String rabbitmqHost) {
		this.rabbitmqHost = rabbitmqHost;
	}

	public String getNrmClass() {
		return nrmClass;
	}

	public void setNrmClass(String nrmClass) {
		this.nrmClass = nrmClass;
	}

	public String getTopologies() {
		return topologies;
	}

	public void setTopologies(String topologies) {
		this.topologies = topologies;
	}
}
