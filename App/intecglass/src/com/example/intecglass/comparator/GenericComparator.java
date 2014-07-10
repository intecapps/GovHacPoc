package com.example.intecglass.comparator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.intecglass.util.StringUtil;

public class GenericComparator<T extends Comparable<?>> implements Comparator<T> {

	/**
     * The prefix for the getter method.
     */
    public static final String GET = "get";

    /**
     * The log4j object.
     */
    private static final String TAG = GenericComparator.class.getName();
    
    /**
     * The name of the field.
     */
    private String fieldName;

    /**
     * flag indicating whether the list should be displayed in ascending order.
     */
    private boolean ascending = false;

    /**
     * The class that we are comparing.
     */
    private Class<T> clazz;

    /**
     * Constructor taking in the class type we are comparing.
     * @param clazz the Class.
     */
    public GenericComparator(final Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * Constructor taking in the class type we are comparing.
     * @param clazz the Class.
     */
    public GenericComparator(final Class<T> clazz, boolean ascending) {
        this.clazz = clazz;
        this.ascending = ascending;
    }
    
    /**
     * Constructor.
     * @param fieldName the name of the field on the class to compare.
     * @param asc if true then will be sent in ascending order.
     * @param clazz the class that we are comparing.
     */
    public GenericComparator(final String fieldName, final boolean asc,
            final Class<T> clazz) {
        this.fieldName = fieldName;
        this.ascending = asc;
        this.clazz = clazz;
        // check the passed in field name is good.
        checkFieldName(fieldName);
    }

    /**
     * This method checks to see that the field name has a valid getter method
     * and that the return type on the getter implements Comparable, if either
     * of these are credentials are not satisfied then a RunTimeException will
     * be thrown.
     * @param fieldName the name of the field.
     */
    private void checkFieldName(final String fieldName) {
        final Method method = getMethod(getMethodName(fieldName));

        if (method == null) {
            throw new RuntimeException("No getter for field: " + fieldName);
        }
    }

    /**
     * This method implements the compare method. If the field name has not been
     * set then the comparison is done on the createDateTime field.
     * @param obj1 the object to compare.
     * @param obj2 the object to compare.
     * @return 0, 1, -1.
     */
    public int compare(T obj1, T obj2) {
        final int comp;
        if (obj1 == obj2) {
            // same object return 0.
            comp = 0;
        } else if (obj1 == null && obj2 != null) {
            comp = -1;
        } else if (obj1 != null && obj2 == null) {
            comp = 1;
        } else  {
            if (StringUtil.isEmpty(fieldName)) {
                comp = compareComparable(obj1, obj2);
            } else {
                comp = compareField(obj1, obj2);
            }
        }

        return comp;
    }

    /**
     * This method compares the field set in the fieldName property. If the
     * return value of the property does not implement Comparable then a
     * RuntimeException will be thrown.
     * @param obj1 the object to compare.
     * @param obj2 the object to compare.
     * @return 0, 1, -1.
     */
    @SuppressWarnings("rawtypes")
	private int compareField(final T obj1, final T obj2) {
        int comp=0;
        try {
            final Method method = getMethod(getMethodName(fieldName));
            final Object returnVal1 = method.invoke(obj1);
            final Object returnVal2 = method.invoke(obj2);

            if (returnVal1 instanceof Comparable
                    && returnVal2 instanceof Comparable) {

                comp = compareComparable((Comparable)returnVal1,
                    (Comparable)returnVal2);
            } else {
                throw new RuntimeException(fieldName
                        + " does not implement comparable this comparator cannot be used.");
            }
        } catch (IllegalAccessException ex) {
            Log.e(TAG, ex.getMessage(), ex);
        } catch (IllegalArgumentException ex) {
        	Log.e(TAG, ex.getMessage(), ex);
        } catch (InvocationTargetException ex) {
        	Log.e(TAG, ex.getMessage(), ex);
        }
        return comp;
    }

    /**
     * This method compares the passed in comparable objects. If the ascending
     * property set on this class is true the the value of obj2.compareTo(obj1)
     * will be returned otherwise obj1.compareTo(obj2) is returned.
     * @param obj1 the object to compare.
     * @param obj2 the object to compare.
     * @return if it's ascending then obj2.compareTo(obj1) is returned otherwise
     * obj1.compareTo(obj2) is returned.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private int compareComparable(final Comparable obj1, final Comparable obj2) {
        final int comp;
        if (obj1 == obj2) {
            comp = 0;
        } else if (obj1 == null && obj2 != null) {
            comp = -1;
        } else if (obj1 != null && obj2 == null) {
            comp = -1;
        } else {
            if (ascending) {
                comp = obj1.compareTo(obj2);
            } else {
                comp = obj2.compareTo(obj1);
            }
        }

        return comp;
    }

    /**
     * This method returns the method with no parameters on the passed in clazz.
     * If no method of that name is found then null will be returned.
     * @param methodName the name of the method.
     * @return the Method object for the method of the passed in name.
     */
    private Method getMethod(final String methodName) {
        Method method = null;
        try {
            method = clazz.getMethod(methodName);
        } catch (NoSuchMethodException ex) {
        	Log.e(TAG, ex.getMessage(), ex);
        } catch (SecurityException ex) {
        	Log.e(TAG, ex.getMessage(), ex);
        }
        return method;
    }

    /**
     * This method gets the name of the method for the given fieldName. If the
     * passed in fieldName is empty this an empty string is returned. If the
     * passed in fieldName is not empty then the name of the getter method for
     * that field name will be returned. For example if "test" is passed in the
     * returned value will be "getTest".
     * @param fieldName the name of the field.
     * @return the name of the getter method for the passed in field name.
     */
    @SuppressLint("DefaultLocale")
	private String getMethodName(final String fieldName) {
        final String methodName;

        if (StringUtil.isEmpty(fieldName)) {
            methodName = "";
        } else {
            final char[] array = fieldName.toCharArray();
            final StringBuilder sb = new StringBuilder();
            
            sb.append(GET);
            for (int i=0, size=array.length; i < size; i++) {
                if (i == 0) {
                    sb.append(String.valueOf(array[i]).toUpperCase());
                } else {
                    sb.append(array[i]);
                }
            }

            methodName = sb.toString();
        }

        return methodName;
    }

}
