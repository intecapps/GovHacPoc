package com.example.intecglass.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	/**
	 * The default date time format for displayed in the application.
	 * dd-MM-yyyy HH:mm 
	 */
	public static final String DEFAULT_DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";
	
	public static final String DATE_TIME_WITH_TIMEZONE_FORMAT = "dd-MM-yyyy HH:mm zzz";
	
	/**
	 * The default date format for displayed in the application.
	 * dd/MM/yyyy
	 */
	public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
	
	/**
	 * The default date format to send the server.
	 */
	public static final String SERVER_DATE_FORMAT = "yyyy-MM-dd";
	
	/**
	 * Returns a string in dd/MM/yyyy format from the passed in date.
	 * @param date 
	 * @return a string in dd/MM/yyyy format from the passed in date.
	 */
	public static String getDisplayDateAsString(Date date) {
		return getDateAsString(DEFAULT_DATE_FORMAT, date);
	}
	
	/**
	 * Returns a string in yyyy-MM-dd format from the passed in date.
	 * @param date
	 * @return a string in yyyy-MM-dd format from the passed in date.
	 */
	public static String getServerDateAsString(Date date) {
		return getDateAsString(SERVER_DATE_FORMAT, date);
	}
	
	/**
	 * Return a string in dd-MM-yyyy HH:mm format from the passed in date.
	 * @param date
	 * @return a string in dd-MM-yyyy HH:mm format from the passed in date.
	 */
	public static String getDisplayDateTimeAsString(Date date) {
		return getDateAsString(DEFAULT_DATE_TIME_FORMAT, date);
	}
	
	public static String getDisplayDateTimeWithTimezone(Date date) {
		return getDateAsString(DATE_TIME_WITH_TIMEZONE_FORMAT, date);
	}
	/**
	 * Returns a date from the passed in string formatted in dd/MM/yyyy.
	 * @param dateStr
	 * @return a date from the passed in string formatted in dd/MM/yyyy.
	 */
	public static Date parseDefaultDateFormat(String dateStr) {
		return parseDate(dateStr, DEFAULT_DATE_FORMAT);
	}
	/**
	 * This method return a string from the passed in date using the pattern provided.
	 * @param pattern the pattern (i.e. dd/MM/yyyy)
	 * @param date the date to return a string from.
	 * @return string representing the date in the passed in format or an empty string if it
	 * has failed.
	 */
	public static String getDateAsString(String pattern, Date date) {
    	String dateStr;
    	boolean emptyPattern = StringUtil.isEmpty(pattern);
		if (emptyPattern && date == null) {
			dateStr = "";
		} else if (emptyPattern && date != null) {
			dateStr = date.toString();	
		} else if (date == null) {
			dateStr = "";
		} else {
			try {
				final SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
				dateStr = sdf.format(date);
			} catch (Exception e) {
				dateStr = date.toString();
			}
		}
		
		return dateStr;
	}
    
	/**
	 * This method parses the passed in date string with the passed in pattern. If the dateStr contains
	 * only number then a new Date(long); will be returned otherwise a new date will be returned using the
	 * pattern provided. If this fails to parse then a null date is returned.
	 * be returned.
	 * @param dateStr the 
	 * @param pattern
	 * @return
	 */
    public static Date parseDate(String dateStr, String pattern) {
		Date date = null;
		
		// service is returning a number for the date.
		if (dateStr.matches("^[0-9]+$")) {
			date = new Date(Long.parseLong(dateStr));
		} else {
			final SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
			try {
				date = sdf.parse(dateStr);
			} catch (ParseException e) {
				date = null;
				//TODO fix this later.
				final SimpleDateFormat serverDateFormat = new SimpleDateFormat(SERVER_DATE_FORMAT, Locale.getDefault());
				try {
					date = serverDateFormat.parse(dateStr);
				} catch (ParseException pe) {
					date = null;
				}
			}
		}
		
		return date;
	}
}
