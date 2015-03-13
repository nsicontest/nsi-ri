/**
 * 
 */
package net.geant.nsicontest.topology;


/**
 * Helper class to deal with stps. Typical requested stp look like: urn:ogf:network:example.net:2013:topology:ingress?vlan=1700-1800
 * Upon parsing such stp, DecomposedStp contains:
 * id = urn:ogf:network:example.net:2013:ingress (stp without label)
 * networkId = urn:ogf:network:example.net:2013 (contents up to the last ':')
 * localId = ingress (contents after the last ':')
 * label = 1700-1800
 */
public class DecomposedStp {

	private String id;			// original STP
	private String networkId;	// extracted networkId part
	private String localId;     // extracted localId part
	private String label;		// extracted label value	
	private String networkIdWithNsa; // networkId + ":nsa" - it makes up nsaId
	private String stpWithoutLabel; // networkId + localId
	
	public DecomposedStp() { }
	
	public DecomposedStp(String id, String networkId, String localId, String label) {
		
		this.id = id;
		this.networkId = networkId;		
		this.localId = localId;
		this.label = label;
		
		this.networkIdWithNsa = networkId + ":nsa";
		this.stpWithoutLabel = networkId + ":" + localId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}
	
	public String getLocalId() {
		return localId;
	}

	public void setLocalId(String localId) {
		this.localId = localId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getNetworkIdWithNsa() {
		return networkIdWithNsa;
	}

	public void setNetworkIdWithNsa(String networkIdWithNsa) {
		this.networkIdWithNsa = networkIdWithNsa;
	}

	public String getStpWithoutLabel() {
		return stpWithoutLabel;
	}

	public void setStpWithoutLabel(String stpWithoutLabel) {
		this.stpWithoutLabel = stpWithoutLabel;
	}

	/**
	 * Decomposed stp into id, localId, networkId and label parts
	 * @param stp
	 * @return
	 * @throws Exception
	 */
	public static DecomposedStp parse(String stp) throws Exception {
		
		if (stp == null || stp.isEmpty())
			throw new IllegalArgumentException("stp");
		
		if (!stp.startsWith("urn:ogf:network:"))
			throw new IllegalArgumentException("stp must start with urn:ogf:network:");
		
		// must contain at least four ':' and zero or one '?'
		int colons = 0;
		int qmarks = 0;
		for (int i=0; i < stp.length(); i++) {			
			char c = stp.charAt(i);
			if (c == ':') {
				colons++;
			} else if (c == '?') {
				qmarks++;
			}
		}
		
		if (colons < 4)
			throw new IllegalArgumentException("stp must contain at least 4 colons");
		
		if (qmarks > 1)
			throw new IllegalArgumentException("stp must contain zero or one question mark");
		
		// break it into id and label (which is optional)
		String id;
		String label;
		int index = stp.indexOf('?');
		if (index != -1) {

			id = stp.substring(0, index);			
			label = stp.substring(index + 1 + "vlan=".length());
			
		} else {
			
			id = stp;
			label = "4-4096";
		}
		
		// now split id around the last colon
		index = id.lastIndexOf(':');
		String networkId = id.substring(0, index); 
		String localId = id.substring(index + 1);		
		
		return new DecomposedStp(id, networkId, localId, label);
	}
	
	@Override
	public String toString() {
		
		return String.format("networkId: %s, localId: %s, label: %s", networkId, localId, label);
	}
}
