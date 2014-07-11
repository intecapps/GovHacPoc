package com.example.intecglass.model;

import com.example.intecglass.util.GenericUtil;
import com.example.intecglass.util.StringUtil;

public class Base implements Comparable<Base>{
	private long id;

	private static final String NEW_LINE = System.getProperty("line.separator");
	
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

	@Override
	public int compareTo(Base entity) {
		final int results;

        if (entity == null) {
            results = -1;
        } else {
            if (equals(entity)) {
                results = 0;
            } else {
                results = 1;
            }
        }
        return results;
	}
	
	/**
     * This method overrides the hashCode method.
     * @return int representing the hashcode.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = calculateHashCode(prime, result, id);
        
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

        if (obj instanceof Base) {
            Base other = (Base) obj;
            res = (obj == this) || (equals(id, other.id));
        } else {
            res = false;
        }

        return res;
    }
	
    /**
     * This method compares whether the two passed in objects are equal.
     * @param o1 - the first object to compare.
     * @param o2 - the second object to compare
     * @return true if the object are equal otherwise false is returned.
     */
    protected boolean equals(final Object o1, final Object o2) {
        return GenericUtil.equals(o1, o2);
    }
    
    /**
     * This method calculates the hash code multiplying the prime and result
     * numbers. The result of this is added to obj.hashCode() if the object is
     * not null.
     * @param prime - the prime number.
     * @param result - the passed in result.
     * @param obj - the object to add the hash code value of.
     * @return int representing the hash code.
     */
    protected int calculateHashCode(final int prime, final int result,
        final Object obj
        ) {
        return GenericUtil.calculateHashCode(prime, result, obj);
    }
    
    protected void appendToBuilder(boolean addSpace, StringBuilder sb, String value) {
    	appendToBuilder(addSpace, sb, value, false);
    }
    
    protected void appendToBuilder(boolean addSpace, StringBuilder sb, String value, boolean addNewLineAtEnd) {
    	if (!StringUtil.isEmpty(value)) {
    		if (addSpace) {
    			sb.append(" ");
    		}
    		sb.append(value);
    		if (addNewLineAtEnd) {
    			sb.append(NEW_LINE);
    		}
    		
    	}
    }
}
