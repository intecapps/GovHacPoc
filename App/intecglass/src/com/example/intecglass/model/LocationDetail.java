package com.example.intecglass.model;


public class LocationDetail extends Location {

	private String description;


	private boolean electronicTender;
	
	private boolean watchedTender;
	
	public LocationDetail() {
		this(null);
	}
	
	public LocationDetail(Location tender) {
		init(tender);
	}
	
	private void init(Location location) {
		if (location != null) {
			setName(location.getName());
			setDescription(location.getDescription());
			setLat(location.getLat());
			setLon(location.getLon());
			setLocationId(location.getLocationId());
			setPlaceMarkerTypeId(location.getPlaceMarkerTypeId());
		}
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isElectronicTender() {
		return electronicTender;
	}

	public void setElectronicTender(boolean electronicTender) {
		this.electronicTender = electronicTender;
	}
	
	public boolean isWatchedTender() {
		return watchedTender;
	}

	public void setWatchedTender(boolean watchedTender) {
		this.watchedTender = watchedTender;
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
        result = calculateHashCode(prime, result, description);
        result = calculateHashCode(prime, result, electronicTender);

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

        if (obj instanceof LocationDetail) {
        	LocationDetail other = (LocationDetail) obj;
            res = (obj == this) || (
            		super.equals(obj)
                    && equals(description, other.description)
                    && equals(electronicTender, other.electronicTender)
                    && equals(watchedTender, other.watchedTender)
                    );
        } else {
            res = false;
        }

        return res;
    }

	
	
}
