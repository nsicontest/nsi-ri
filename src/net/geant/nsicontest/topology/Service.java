/**
 * 
 */
package net.geant.nsicontest.topology;

import org.ogf.schemas.nsi._2013._09.topology.ServiceType;

/**
 * Simplified version of ServiceType
 */
public class Service {

	private String id;
	private String type;
	private String link;
	
	public Service() { }

	public Service(String id, String type, String link) {

		this.id = id;
		this.type = type;
		this.link = link;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	static public Service fromServiceType(ServiceType service) {
	
		if (service == null) throw new IllegalArgumentException("service");
		
		return new Service(service.getId(), service.getType(), service.getLink().trim());
	}
	
	public ServiceType toServiceType() {
		
		ServiceType service = new ServiceType();
		service.setId(this.getId());
		service.setType(this.getType());
		service.setLink(this.getLink());
		
		return service;
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
		Service other = (Service) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Service [id=" + id + ", type=" + type + ", link=" + link + "]";
	}
}
