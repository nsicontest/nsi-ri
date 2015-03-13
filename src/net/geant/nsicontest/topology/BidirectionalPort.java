/**
 * 
 */
package net.geant.nsicontest.topology;


/**
 * Represents <nml:BidirectionalPort>
 */
public class BidirectionalPort {
	
	private String id;
	private DecomposedStp decomposed;
	private PortGroup inbound;
	private PortGroup outbound;
	private Topology owner;

	public BidirectionalPort() { }
	
	public BidirectionalPort(String id, DecomposedStp decomposed, PortGroup inbound, PortGroup outbound) {
	
		this.id = id;
		this.decomposed = decomposed;
		this.inbound = inbound;
		this.outbound = outbound;
	}
	
	public Nsa getNsa() {
		
		return owner != null ? owner.getOwner() : null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public DecomposedStp getDecomposed() {
		return decomposed;
	}

	public void setDecomposed(DecomposedStp decomposed) {
		this.decomposed = decomposed;
	}

	public PortGroup getInbound() {
		return inbound;
	}

	public void setInbound(PortGroup inbound) {
		this.inbound = inbound;
	}

	public PortGroup getOutbound() {
		return outbound;
	}

	public void setOutbound(PortGroup outbound) {
		this.outbound = outbound;
	}
	
	public Topology getOwner() {
		return owner;
	}

	public void setOwner(Topology owner) {
		this.owner = owner;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BidirectionalPort other = (BidirectionalPort) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BidirectionalPort [id=" + id + "\ninbound=" + inbound + "\noutbound=" + outbound + "]";
	}
}
