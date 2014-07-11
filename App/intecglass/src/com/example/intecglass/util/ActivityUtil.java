package com.example.intecglass.util;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intecglass.model.KeyValuePair;
import com.example.intecglass.persistence.Storage;

public class ActivityUtil {

	public static void setTextViewValue(Activity activity, int resourceId, String value) {
		final View view = activity.findViewById(resourceId);
		if (view instanceof TextView) {
			((TextView)view).setText(value);
		}
	}
	
	public static void setTextViewValue(Activity activity, int resourceId, Spanned value) {
		final View view = activity.findViewById(resourceId);
		if (view instanceof TextView) {
			((TextView)view).setText(value);
		}
	}
	

	
	public static String getTextValue(Activity activity, int resourceId) {
		String str = "";
		
		final View view = activity.findViewById(resourceId);
		
		if (view instanceof EditText) {
			final EditText editText = (EditText) view;
			str = editText.getText().toString();
		} else if (view instanceof Spinner) {
			final Spinner spinner = (Spinner) view;
			final Object obj = spinner.getSelectedItem();
			
			if (obj instanceof KeyValuePair) {
				str = ((KeyValuePair)obj).getKey();
			} else {
				str = obj.toString();
			}
		}
		return str;
	}
	
	public static void toggleViewVisibility(Activity activity, boolean show, int... resourceId) {
		if (resourceId == null) {
			return;
		}
		int visibility = (show) ? View.VISIBLE : View.INVISIBLE;
		
		for(int resId : resourceId) {
			View view = activity.findViewById(resId);
			view.setVisibility(visibility);
		}
	}
	
	public static void showToast(Context context, String message){
		if (context instanceof Activity) {
			Activity activity = (Activity) context;
			
			if (activity.hasWindowFocus()) {
				Toast toast = Toast.makeText(context,message ,Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
				toast.show();
			}
		}
	}

	public static void showNoConnectivityMessage(Context context) {
		showToast(context, "No connectivity come back later.");
	}
	
	public static String getResourceString(Context context, int resourceId) {
		final String str;
		if (context instanceof Activity) {
			str = ((Activity)context).getString(resourceId);
		} else {
			str = "";
		}
		
		return str;
	}
	
	public static int getResourceInt(Context context, int resourceId) {
		final String str = getResourceString(context, resourceId);
		int intVal;
		if (StringUtil.isEmpty(str)) {
			intVal = 0;
		} else {
			try {
				intVal = Integer.parseInt(str);
			} catch (Exception e) {
				intVal = 0;
			}
		}
		return intVal;
	}
	
	public static void processDeviceAssociatedCallback(Activity activity, JSONObject obj) {
		//final String action = activity.getString(R.string.ActionGetWatchedTendersNotificationSettings);
		//if (action.equalsIgnoreCase(JSONUtil.getActionName(obj))) {
			// if the call to get watched tenders returns an exception then the device is not associated with
			// the tenders site so we want to clear the stored value for userId.
			if (!JSONUtil.isServerCallSuccessful(obj)) {
				Storage.persistUserId(activity, "");
				
				
				activity.finish();
			}
		//}
	}
	
	
	
}
