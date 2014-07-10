package com.example.intecglass.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Location extends Base {
	private String code;
    
    private String title;
    
    private Date openingDate;
    
    private Date closingDate;

    public String tenderState;
    
    private String tenderType;
    
    private String agencyName;
    
    private String category;
    
    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTenderState() {
        return tenderState;
    }

    public void setTenderState(String tenderState) {
        this.tenderState = tenderState;
    }

    public String getTenderType() {
        return tenderType;
    }

    public void setTenderType(String tenderType) {
        this.tenderType = tenderType;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
        result = calculateHashCode(prime, result, code);
        result = calculateHashCode(prime, result, title);
        result = calculateHashCode(prime, result, openingDate);
        result = calculateHashCode(prime, result, closingDate);
        result = calculateHashCode(prime, result, tenderState);
        result = calculateHashCode(prime, result, tenderType);
        result = calculateHashCode(prime, result, agencyName);
        result = calculateHashCode(prime, result, category);
        

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
                    && equals(code, other.code)
                    && equals(title, other.title)
                    && equals(getOpeningDate(), other.getOpeningDate())
                    && equals(getClosingDate(), other.getClosingDate())
                    && equals(tenderState, other.tenderState)
                    && equals(tenderType, other.tenderType)
                    && equals(agencyName, other.agencyName)
                    && equals(category, other.category)
                    );
        } else {
            res = false;
        }

        return res;
    }
    
    private static final int NUMBER_OF_TENDERS = 300;
    
    public static List<Location> generateTenders(String state, String type) {
        final List<Location> tenders = new ArrayList<Location>(NUMBER_OF_TENDERS);
        
        for (int i=0; i<NUMBER_OF_TENDERS; i++) {
            final Location t = new Location();
            final Calendar openDate = Calendar.getInstance();
            openDate.set(Calendar.YEAR, openDate.get(Calendar.YEAR) - 1);
            openDate.add(Calendar.DAY_OF_YEAR, i);
            
            final Calendar closingDate = Calendar.getInstance();
            closingDate.set(Calendar.YEAR, closingDate.get(Calendar.YEAR) + 1);
            closingDate.add(Calendar.DAY_OF_YEAR, i);
            
            t.setOpeningDate(openDate.getTime());
            t.setClosingDate(closingDate.getTime());
            t.setCode("Code" + i);
            t.setId(i);
            t.setTenderState(state);
            t.setTenderType(type);
            t.setTitle("This is the title for the tender with the code of Code" +i);
            t.setAgencyName("Agency " + i);
            t.setCategory((i * 10000) + " Category number " + i);
            
            tenders.add(t);
        }
        
        return tenders;
    }
    
    
}
