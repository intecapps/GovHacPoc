package com.example.intecglass.model;


public class Image extends Base {
	private String ImageId;
    
    private String Name;

    public String Description;
    
    public String AcreNumber;
    
    
    private String ImageUrl;
   
    public void setImageId(String locationId) {
        this.ImageId = locationId;
    }

    public String getImageId() {
        return ImageId;
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
    
    public String getAcreNumber() {
        return AcreNumber;
    }

    public void setAcreNumber(String acreNumber) {
        this.AcreNumber = acreNumber;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.ImageUrl = imageUrl;
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
        result = calculateHashCode(prime, result, ImageId);
        result = calculateHashCode(prime, result, Name);
        result = calculateHashCode(prime, result, Description);
        result = calculateHashCode(prime, result, AcreNumber);
        result = calculateHashCode(prime, result, ImageUrl);
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

        if (obj instanceof Image) {
        	Image other = (Image) obj;
            res = (obj == this) || (
            		super.equals(obj)
                    && equals(ImageId, other.ImageId)
                    && equals(Name, other.Name)
                    && equals(Description, other.Description)
                    && equals(AcreNumber, other.AcreNumber)
                    && equals(ImageUrl, other.ImageUrl)
                    );
        } else {
            res = false;
        }

        return res;
    }
    
    
    
}
