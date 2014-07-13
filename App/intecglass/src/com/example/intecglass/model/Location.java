package com.example.intecglass.model;

import java.io.Serializable;


public class Location extends Base implements Serializable {
	private String LocationId;
    
    private String Name;

    public String Description;
    
    public String Lat;
    
    public String Lon;
    
    private String PlaceMarkerTypeId;
    
    private String PlaceMarkerCat;
    
    private String distance; 
   
    public void setLocationId(String locationId) {
        this.LocationId = locationId;
    }

    public String getLocationId() {
        return LocationId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }
    
    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        this.Lat = lat;
    }
    
    public String getLon() {
        return Lon;
    }

    public void setLon(String lon) {
        this.Lon = lon;
    }

    public String getPlaceMarkerTypeId() {
        return PlaceMarkerTypeId;
    }

    public void setPlaceMarkerTypeId(String placeMarkerTypeId) {
        this.PlaceMarkerTypeId = placeMarkerTypeId;
    }
    
    public String getPlaceMarkerCat() {
		return PlaceMarkerCat;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public void setPlaceMarkerCat(String placeMarkerCat) {
		PlaceMarkerCat = placeMarkerCat;
	}

	/**
     * This method overrides the hashCode method.
     * @return int representing the hashcode.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();

        // calculate the hash code for record.
        result = calculateHashCode(prime, result, LocationId);
        result = calculateHashCode(prime, result, Name);
        result = calculateHashCode(prime, result, Description);
        result = calculateHashCode(prime, result, Lat);
        result = calculateHashCode(prime, result, Lon);
        result = calculateHashCode(prime, result, PlaceMarkerTypeId);
        return result;
    }

    /**
     * This method overrides the equal method. Comparing the
     * passed in object to this object.
     * @param obj - The object to compare.
     * @return true if the objects are equal otherwise false is returned.
     */
    @Override
    public boolean equals(final Object obj) {
        boolean res;

        if (obj instanceof Location) {
        	Location other = (Location) obj;
            res = (obj == this) || (
            		super.equals(obj)
                    && equals(LocationId, other.LocationId)
                    && equals(Name, other.Name)
                    && equals(Description, other.Description)
                    && equals(Lat, other.Lat)
                    && equals(Lon, other.Lon)
                    && equals(PlaceMarkerTypeId, other.PlaceMarkerTypeId)
                    );
        } else {
            res = false;
        }

        return res;
    }
    
    
    
}
