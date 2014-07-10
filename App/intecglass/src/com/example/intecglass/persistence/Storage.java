package com.example.intecglass.persistence;

import java.util.UUID;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings.Secure;
import android.util.Log;

import com.example.intecglass.R;
import com.example.intecglass.util.StringUtil;

public class Storage {
	
	private static final String TAG = Storage.class.getName();
	/**
	 * The name of the preferences store.
	 */
	private static final String PREFERENCES_NAME = "userPreferences-%s";
	
	/**
	 * They key used to store the fact that the terms and conditions have been updated.
	 */
	private static final String ACCEPTED_TERMS_AND_CONDITIONS = "TAndCAccepted";
	
	private static final String DEVICE_UUID = "DeviceUUID";
	
	private static final String USER_ID = "UserId";
	
	/**
	 * The key used to store the registrationId in the preferences store.
	 */
	private static final String REGISTRATION_ID_KEY = "RegistrationId";
	
	private static final String UPDATED_DEVICE_TOKEN_KEY = "UpdatedDeviceToken";
	/**
	 * This method sets the fact that the terms and conditions have been accepted.
	 * @param context
	 */
	public static void persistAcceptTermsAndConditions(Context context) {
		persistObject(context, ACCEPTED_TERMS_AND_CONDITIONS, true);
	}
	
	/**
	 * This method sets the returned tenderUserId after authorisation
	 * @param context
	 */
	public static void persistUserId(Context context, String userId) {
		persistObject(context, USER_ID, userId);
	}
	
	/**
	 * This method gets returns true if the terms and conditions have been accepted. 
	 * @param context
	 * @return true if the user has accepted the terms and conditions otherwise false will be returned.
	 */
	public static boolean getAcceptedTermsAndConditions(Context context) {
		return getPersistedBoolean(context, ACCEPTED_TERMS_AND_CONDITIONS);
	}
	
	/**
	 * This persists the passed in object to the {@link SharedPreferences} object.
	 * @param context
	 * @param key the key
	 * @param value and the value
	 */
	private static void persistObject(Context context, String key, Object value) {
		if (value == null) {
			return;
		}
		
		
		final SharedPreferences settings = context.getSharedPreferences(getPreferenceName(context), 0);
		// need to get the editor to persist objects to the preferences.
		final SharedPreferences.Editor editor = settings.edit();
		
		if (value instanceof Boolean) {
			editor.putBoolean(key, (Boolean)value);
		} else if (value instanceof Float) {
			editor.putFloat(key, (Float)value);
		} else if (value instanceof Integer) {
			editor.putInt(key, (Integer)value);
		} else if (value instanceof Long) {
			editor.putLong(key, (Long)value);
		} else if (value instanceof String) {
			editor.putString(key, (String)value);
		} else {
			throw new RuntimeException("Invalid type to persist: " + value.getClass().getName());
		}
		
		// Commit the edits!
		editor.commit();
	}
	
	/**
	 * This method retrieve boolean value of for the passed in key. If the key is not found then a default
	 * value of false will be returned.
	 * @param context
	 * @param key the name of the key.
	 * @return the value for the passed in key or false if it does not exist.
	 */
	private static boolean getPersistedBoolean(Context context, String key) {
		return context.getSharedPreferences(getPreferenceName(context), 0).getBoolean(key, false);
	}
	
	/**
	 * This method retrieves the value of the passed in key stored within the {@link SharedPreferences} object.
	 * @param context 
	 * @param key the name of the key to return the value of.
	 * @return the value of the passed in key as stored in the {@link SharedPreferences} object.
	 */
	private static String getPersistedString(Context context, String key) {
		return context.getSharedPreferences(getPreferenceName(context), 0).getString(key, "");
	}
	
	private static String getPreferenceName(Context context) {
		final String prefName = String.format(PREFERENCES_NAME, context.getString(R.string.app_name));
		
		return prefName;
	}
	
	public static String getDeviceUniqueId(Context context) {
		final String prefName = getPreferenceName(context);
		String deviceUID = context.getSharedPreferences(prefName, 0).getString(DEVICE_UUID, "");
		
		if (StringUtil.isEmpty(deviceUID)) {
			final String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
			
			Log.d(TAG, androidId);
			// generate a random uuid.
			final UUID uuid = UUID.nameUUIDFromBytes(androidId.getBytes());
			deviceUID = String.valueOf(uuid);
			persistObject(context, DEVICE_UUID, deviceUID);
		}
		
		return deviceUID;
	}
	
	public static String getUserId(Context context) {
		final String prefName = getPreferenceName(context);
		String userId = context.getSharedPreferences(prefName, 0).getString(USER_ID, "");
		if (StringUtil.isEmpty(userId)) {
			userId = "";
		}
		return userId;
	}
	
	/**
	 * This method saves the passed in registrationId to storage. If the passed in registrationId
	 * is null or empty then nothing will be stored.
	 * @param context the activity the persist object is getting called from.
	 * @param registrationId the registration id retrieved from the GCM on registration of the device.
	 */
	public static void persistRegistrationId(Context context, String registrationId) {
		if (StringUtil.isEmpty(registrationId)) {
			return;
		}
		
		persistObject(context, REGISTRATION_ID_KEY, registrationId);
	}
	
	/**
	 * This method returns the value of the registration stored on the device.
	 * @param context 
	 * @return string representing the registrationId of the device.
	 */
	public static String getRegistrationId(Context context) {
		return getPersistedString(context, REGISTRATION_ID_KEY);
	}
	
	public static boolean isDeviceTokenUpdated(Context context) {
		return !StringUtil.isEmpty(getPersistedString(context, UPDATED_DEVICE_TOKEN_KEY));
	}
	
	public static void persistDeviceTokenUpdated(Context context, String str) {
		if (StringUtil.isEmpty(str)) {
			return;
		}
		
		persistObject(context, UPDATED_DEVICE_TOKEN_KEY, str);
	}
}
