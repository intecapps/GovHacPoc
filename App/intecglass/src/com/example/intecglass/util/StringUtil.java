package com.example.intecglass.util;


public class StringUtil {
	
	
	/**
     * This method returns whether or not a string is empty.
     * If the string is null or it length is 0 or is trimmed length is 0 then
     * the string is considered to be empty and is true is returned, otherwise
     * false is returned.
     * @param str - String to look at.
     * @return - true if the string is empty otherwise false is returned.
     */
    public static boolean isEmpty(final String str) {   
        boolean isEmpty;

        if (str == null) {
            isEmpty = true;
        } else if (str.length() == 0) {
            isEmpty = true;
        } else {
            isEmpty = containsAllWhiteSpace(str);
        }
        
        return isEmpty;
    }
    
    /**
     * This method determines if the string passed in contains only
     * white spaces.
     * @param str - String to look at.
     * @return true if the string only contains white space else false will be
     * returned.
     */
    private static boolean containsAllWhiteSpace(final String str) {
        boolean isAllWhiteSpace = true;

        char[] array = str.toCharArray();
        for (char ch : array) {
            if (!Character.isWhitespace(ch)) {
                isAllWhiteSpace = false;
                break;
            }
        }
        return isAllWhiteSpace;
    }
    
}
