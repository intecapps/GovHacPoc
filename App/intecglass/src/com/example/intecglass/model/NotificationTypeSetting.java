package com.example.intecglass.model;


public class NotificationTypeSetting extends Base {
	private int notificationTypeId; 
	private String name;
	private Boolean enabled;
	private Boolean addedTenderNotificationSetting;
	private Boolean removedTenderNotificationSetting;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getNotificationTypeId() {
        return notificationTypeId;
    }

    public void setNotificationTypeId(int notificationTypeId) {
        this.notificationTypeId = notificationTypeId;
    }
    
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    public Boolean getAddedTenderNotificationSetting() {
        return addedTenderNotificationSetting;
    }

    public void setAddedTenderNotificationSetting(Boolean addedTenderNotificationSetting) {
        this.addedTenderNotificationSetting = addedTenderNotificationSetting;
    }
    
    public Boolean getRemovedTenderNotificationSetting() {
        return removedTenderNotificationSetting;
    }

    public void setRemovedTenderNotificationSetting(Boolean removedTenderNotificationSetting) {
        this.removedTenderNotificationSetting = removedTenderNotificationSetting;
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
        result = calculateHashCode(prime, result, notificationTypeId);
        result = calculateHashCode(prime, result, name);
        result = calculateHashCode(prime, result, enabled);
        result = calculateHashCode(prime, result, addedTenderNotificationSetting);
        result = calculateHashCode(prime, result, removedTenderNotificationSetting);

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

        if (obj instanceof NotificationTypeSetting) {
        	NotificationTypeSetting other = (NotificationTypeSetting) obj;
            res = (obj == this) || (
            		super.equals(obj)
                    && equals(notificationTypeId, other.notificationTypeId)
                    && equals(name, other.name)
                    && equals(enabled, other.enabled)
                    && equals(addedTenderNotificationSetting, other.addedTenderNotificationSetting)
                    && equals(removedTenderNotificationSetting, other.removedTenderNotificationSetting)
                    );
        } else {
            res = false;
        }

        return res;
    }
    
}
