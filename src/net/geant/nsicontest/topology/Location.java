/**
 * 
 */
package net.geant.nsicontest.topology;


import org.ogf.schemas.nml._2013._05.base.LocationType;


/**
 * Represents geographical location with latitude and longitude - <nml:Location>
 */
public class Location {
	
	private float latitude;
	private float longitude;
	
	public Location() { }

	public Location(float latitude, float longitude) {
	
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
	static public Location fromLocationType(LocationType location) {
		
		if (location == null) throw new IllegalArgumentException("location");
		
		return new Location(location.getLat(), location.getLong());
	}
	
	public LocationType toLocationType() {

		LocationType loc = new LocationType();
		loc.setLat(this.getLatitude());
		loc.setLong(this.getLongitude());		
		return loc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(latitude);
		result = prime * result + Float.floatToIntBits(longitude);
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
		Location other = (Location) obj;
		if (Float.floatToIntBits(latitude) != Float.floatToIntBits(other.latitude))
			return false;
		if (Float.floatToIntBits(longitude) != Float.floatToIntBits(other.longitude))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Location [latitide=" + latitude + ", longitude=" + longitude + "]";
	}
}
