package com.example.intecglass.util;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.EditText;

import com.example.intecglass.R;
import com.example.intecglass.api.GetURL;
import com.example.intecglass.api.PostToURL;
import com.example.intecglass.api.PostToURLTask;
import com.example.intecglass.api.ServerCheckTask;
import com.example.intecglass.api.SyncPostToURLTask;
import com.example.intecglass.constant.Constant;
import com.example.intecglass.listener.ServerCallbackListener;

public class GenericUtil {

	private final static String TAG = GenericUtil.class.getName();
	
	private static String uri;
	/**
     * This method compares whether the two passed in objects are equal.
     * @param o1 - the first object to compare.
     * @param o2 - the second object to compare
     * @return true if the object are equal otherwise false is returned.
     */
    public static boolean equals(final Object o1, final Object o2) {
        boolean areEqual;

        if (o1 != null) {
            areEqual = ((o2 != null) && o1.equals(o2));
        } else {
            areEqual = (o2 == null);
        }

        return areEqual;
    }

    /**
     * This method compares 2 strings ignore case.
     * @param o1 string 1.
     * @param o2 string 2 to compare with string 1.
     * @return true if the strings are equal disregarding the case of the
     * string.
     */
    public static boolean equalsIgnoreCase(final String o1, final String o2) {
        boolean areEqual;

        if (o1 != null) {
            areEqual = ((o2 != null) && o1.equalsIgnoreCase(o2));
        } else {
            areEqual = (o2 == null);
        }

        return areEqual;
    }

    /**
     * This method determines whether two object arrays are equal. These arrays are converted into lists
     * and the areCollectionsEquals method.
     * @param array1 - the first array.
     * @param array2 - the second array.
     * @return true if the array are equal.
     */
    public static boolean areArraysEqual(final Object[] array1,
        final Object[] array2
        ) {
        boolean res;

        if (array1 == array2) {
            res = true;
        } else {
            List<?> list1 = getListFromArray(array1);
            List<?> list2 = getListFromArray(array2);
            res = areCollectionsEqual(list1, list2);
        }

        return res;
    }

    /**
     * This method converts an array to a list. If the array is null then an null
     * list will be returned.
     * @param array - the array to convert into a list.
     * @return - a list containing the contents in the array.
     */
    private static List<?> getListFromArray(final Object[] array) {
        List<?> list;

        if (array != null) {
            list = Arrays.asList(array);
        } else {
            list = null;
        }

        return list;
    }

    /**
     * This method verifies whether the passed in collections are equal. If the
     * collections have then same reference if col1 == col2 true is returned.
     * If the collections have different sizes then false is returned.
     * If the collection contains the same number of rows then the results of
     * col1.containsAll(col2) is returned.
     * @param col1 - The first collection to be used in the comparison.
     * @param col2 - The second collection to be used in the comparison.
     * @return true if the passed in collections are equals otherwise false.
     */
    public static boolean areCollectionsEqual(final Collection<?> col1,
        final Collection<?> col2
        ) {
        boolean areEqual;

        if (col1 == col2) {
            areEqual = true;
        } else {
            final int col1Size = getCollectionSize(col1);
            final int col2Size = getCollectionSize(col2);

            // if the sizes are equal then we need to check the elements within.
            if (col1Size == col2Size) {

                if (col1Size == 0) {
                    areEqual = true;
                } else {
                    areEqual = col1.containsAll(col2);
                }
            } else {
                areEqual = false;
            }
        }

        return areEqual;
    }

    /**
     * This method returns the number of elements within a passed in collection.
     * If the collection is null then 0 is returned.
     * @param collection - The collection to get the size of.
     * @return int representing the size of the collection if the collection is
     * null the 0 is returned.
     */
    private static int getCollectionSize(final Collection<?> collection) {
        int collectionSize;

        if (collection == null) {
            collectionSize = 0;
        } else {
            collectionSize = collection.size();
        }

        return collectionSize;
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
    public static int calculateHashCode(final int prime, final int result,
        final Object obj
        ) {
        int hash;

        if (obj == null) {
            hash = prime * result;
        } else {
            hash = (prime * result) + obj.hashCode();
        }

        return hash;
    }
    
    /**
     * This method returns a new date from the passed in date. If the passed
     * in date is null then null is returned. If the passed in date is not
     * null then a new instance of date is returned from the passed in date.
     * This is done to fix a the findbugs error saying that returning a
     * reference to a mustable reference may expose the internal representation.
     * Creating a new instsance fixes this issue.
     * @param date the date to create the new instance from.
     * @return a new instance of date from the passed in date or null if the
     * passed in date is null.
     */
    public static Date getDateFromDate(final Date date) {
        Date newDate;
        if (date == null) {
            newDate = null;
        } else {
            Calendar cal = Calendar.getInstance(Locale.getDefault());
            cal.setTime(date);
            newDate = cal.getTime();
        }
        return newDate;
    }

    /**
     * This method copies an array from the passed in array.
     * @param array the array to copy.
     * @return a copy of the array or null if the passed in array is null.
     */
    public static Object[] copyArray(final Object[] array) {
        final Object[] copiedArray;
        if (array == null) {
            copiedArray = null;
        } else {
            //copiedArray = Arrays.copyOf(array, array.length);
            copiedArray = array.clone();
        }
        return copiedArray;
    }
    
    /**
	 * This method is called to execute a GET on the server side using the
	 * passed in parameters as GET parameters. This method returns a JSONObject,
	 * or null if an exception has been caught.
	 * 
	 * @param action
	 *            the action to GET to. Please note this process is done in a
	 *            background process to prevent the GUI from locking.
	 * @param parameters
	 *            the parameters to use in the action
	 */
	public static void executeGet(Context context, String action,
			List<BasicNameValuePair> parameters, ServerCallbackListener adapter, Boolean isWebApi) {
		final GetURL getUrl;
		if (!isOnline(context)) {
			ActivityUtil.showNoConnectivityMessage(context);
			return;
		}
		
		final PostToURLTask task = new PostToURLTask();
		if (isWebApi) {
		getUrl = new GetURL(getWebServiceUri(context) + action, adapter,
				parameters);
		}
		else { //its a mobile mirror api call so go there instead
		getUrl = new GetURL(getMobileServiceUri(context) + action, adapter,
					parameters);
		}
		Log.d(TAG, "URL is :" + getUrl.getUrl());
		task.execute(getUrl);
	}

	/**
	 * This method executes a GET request server asynchronously and returns the
	 * JSONObject payload. This should be used sparingly the executeGet method
	 * should be called in favour of this as this will be done asynchronously.
	 * 
	 * @param context
	 *            the android context
	 * @param action
	 *            the name of the URL action.
	 * @param parameters
	 *            the parameters to pass on the GET request.
	 * @return JSONObject payload.
	 */
	public static JSONObject executeGetSycn(Context context, String action,
			List<BasicNameValuePair> parameters) {
		
		if (!isOnline(context)) {
			ActivityUtil.showNoConnectivityMessage(context);
			return null;
		}
		
		final PostToURLTask task = new PostToURLTask();
		final GetURL getUrl = new GetURL(getWebServiceUri(context) + action, null,
				parameters);
		return executeTask(task, getUrl);
	}

	/**
	 * Synchronous. version of Above
	 * 
	 * @param context
	 * @param action
	 * @param parameters
	 * @return JSON Object
	 */
	public static JSONObject executeGetSycnSync(Context context, String action,
			List<BasicNameValuePair> parameters) {
		
		if (!isOnline(context)) {
			ActivityUtil.showNoConnectivityMessage(context);
			return null;
		}
		
		final SyncPostToURLTask task = new SyncPostToURLTask();
		final GetURL getUrl = new GetURL(getWebServiceUri(context) + action, null,
				parameters);
		return task.doAction(getUrl);
	}

	/**
	 * This method is called to execute a post on the server side using the
	 * passed in parameters as POST parameters. This method returns a
	 * JSONObject, or null if an exception has been caught.
	 * 
	 * @param action
	 *            the action to POST to. Please note this process is done in a
	 *            background process to prevent the GUI from locking.
	 * @param parameters
	 *            the parameters to use in the action.
	 */
	public static void executePost(Context context, String action,
			List<BasicNameValuePair> parameters, ServerCallbackListener listener) {
		
		if (!isOnline(context)) {
			ActivityUtil.showNoConnectivityMessage(context);
			return;
		}
		
		final PostToURLTask task = new PostToURLTask();
		final PostToURL postUrl = new PostToURL(getWebServiceUri(context) + action,
				listener, parameters);
		task.execute(postUrl);
	}

	/**
	 * This method executes a POST request server synchronously and returns the
	 * JSONObject payload. This should be used sparingly the executePost method
	 * should be called in favour of this as this will be done asynchronously.
	 * 
	 * @param context
	 *            the android context
	 * @param action
	 *            the name of the URL action.
	 * @param parameters
	 *            the parameters to pass on the POST request.
	 * @return JSONObject payload.
	 */
	public static JSONObject executePostSync(Context context, String action,
			List<BasicNameValuePair> parameters) {
		
		if (!isOnline(context)) {
			ActivityUtil.showNoConnectivityMessage(context);
			return null;
		}
		
		final PostToURLTask task = new PostToURLTask();
		final PostToURL postUrl = new PostToURL(getWebServiceUri(context) + action, null,
				parameters);

		Log.d(TAG, "postUrl - " + postUrl.getUrl());

		return executeTask(task, postUrl);
	}

	/**
	 * This method executes that passed in task and returns the JSONPayload.
	 * 
	 * @param task
	 *            the task to execute.
	 * @param url
	 *            the url to execute.
	 * @return JSONObject payload.
	 */
	private static JSONObject executeTask(PostToURLTask task, PostToURL url) {
		JSONObject obj = null;

		Log.d(TAG, url.getParameters().toString());

		try {
			obj = task.execute(url).get();
		} catch (InterruptedException e) {
			Log.e(TAG, e.getMessage());
		} catch (ExecutionException e) {
			Log.e(TAG, e.getMessage());
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}

		return obj;
	}

	/**
	 * This method returns the URI has stored in R.string.webServiceUri
	 * 
	 * @param context
	 * @return the value of the R.string.webServiceUri string value.
	 */
	public static String getWebServiceUri(Context context) {
		uri = context.getString(R.string.webServiceUrl);
		return uri;
	}
	
	/**
	 * This method returns the URI has stored in R.string.mobileServiceUri
	 * 
	 * @param context
	 * @return the value of the R.string.mobileServiceUri string value.
	 */
	public static String getMobileServiceUri(Context context) {
		uri = context.getString(R.string.mobileServiceUrl);
		return uri;
	}

	/**
	 * This method returns the value of the string resource.
	 * 
	 * @param resourceId
	 *            the id of the string resource.
	 * @return a String as stored in the string.xml resource file.
	 */
	public static String getTextValue(Activity context, int resourceId) {
		final String stringValue;
		final EditText editText = getEditText(context, resourceId);

		if (editText == null || editText.getText() == null) {
			stringValue = "";
		} else {
			stringValue = editText.getText().toString();
		}

		return stringValue;
	}

	/**
	 * This method return the EditText field associated with the passed in
	 * resourceId.
	 * 
	 * @param resourceId
	 *            the id of the text field.
	 * @return the {@link EditText} object associated with the passed in
	 *         resourceId.
	 */
	public static EditText getEditText(Activity context, int resourceId) {
		return ((EditText) context.findViewById(resourceId));
	}
	
	public static Map<String, String> getMapFromString(String str) {
		return getMapFromString(str, "\\|", ";");
	}
	
	public static Map<String, String> getMapFromString(String str, String keyPairDelimeter, String rowDelimeter) {
		final Map<String, String> map = new TreeMap<String, String>();
				
		if (StringUtil.isEmpty(str)) {
			return map;
		}
		
		final String[] rows = str.split(rowDelimeter);
		
		for(String row : rows) {
			final String[] keyPair = row.split(keyPairDelimeter);
			
			if (keyPair != null && keyPair.length >= 2) {
				final String key = keyPair[0];
				final String value = keyPair[1];
				
				map.put(key, value);
			}
		}
		
		return map;
	}
	
	public static boolean isOnline(final Context context) {
		boolean isOnline = false;
		
	    ConnectivityManager cm =
	        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

	    isOnline = cm.getActiveNetworkInfo() != null && 
	       cm.getActiveNetworkInfo().isConnectedOrConnecting();
	    
	    return isOnline;
	}
	
	public static boolean checkServer(final Context context) {
		final String checkServiceUrl = getWebServiceUri(context) + Constant.ACTIONDATASERVICECHECK;
		boolean isOnline = false;
		
    	ServerCheckTask task = new ServerCheckTask();
    	
    	try {
			isOnline = task.execute(checkServiceUrl).get();
		} catch (InterruptedException e) {
			isOnline = false;
		} catch (ExecutionException e) {
			isOnline = false;
		}
    	
    	return isOnline;
	}
}
