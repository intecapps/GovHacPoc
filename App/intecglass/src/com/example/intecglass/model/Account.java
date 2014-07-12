package com.example.intecglass.model;

import java.util.Date;

public class Account extends Base {
	private String id;
    
    private String token;
    
    private Date updateDate;
    
    

    public String getCode() {
        return id;
    }

    public void setCode(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
  
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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
        result = calculateHashCode(prime, result, id);
        result = calculateHashCode(prime, result, updateDate);
        result = calculateHashCode(prime, result, token);

        return result;
    }

    /**
     * This method overrides the equal method. Comparing the
     * passed in object to this object.
     * @param obj - The object to compare.
     * @return true if the objects are equal otherwise false is returned.
     */
    /*@Override
    public boolean equals(final Object obj) {
        boolean res;

        if (obj instanceof Account) {
        	Account other = (Account) obj;
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
    
    public static List<Account> generateTenders(String state, String type) {
        final List<Account> tenders = new ArrayList<Account>(NUMBER_OF_TENDERS);
        
        for (int i=0; i<NUMBER_OF_TENDERS; i++) {
            final Account t = new Account();
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
    }*/
    
    
}
