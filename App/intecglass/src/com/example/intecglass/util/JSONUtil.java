package com.example.intecglass.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.example.intecglass.model.Module;
import com.example.intecglass.model.NotificationTypeSetting;
import com.example.intecglass.model.Location;
import com.example.intecglass.model.LocationDetail;

public class JSONUtil {
	
	private static final String RESPONSE_OBJECT = "ResponseObject";
	private static final String TAG = JSONUtil.class.getName();
	private static final String ACK_CODE = "AckCode";
	private static final String ACK_MESSAGE = "AckMsg";
	private static final int ACK_SUCCESS = 1;
	
	/**
     * The key for the ActionName property in the returned JSONObject.
     */
     private static final String ACTION_NAME_KEY = "ActionName";
	
	public static List<Location> getTendersFromJsonObject(JSONObject obj) {
		List<Location> tenders = new ArrayList<Location>();
		
		try {
			final JSONArray responseObject = obj.getJSONArray(RESPONSE_OBJECT);
			
			int size=responseObject.length();
			for (int i=0; i<size; i++) {
				final JSONObject row = (JSONObject) responseObject.get(i);
				final Location tender = getTenderFromJSONObject(row);
				if (tender != null) {
					tenders.add(tender);
				}
			}
			
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
		}
		return tenders;
	}
	
	
	public static List<NotificationTypeSetting> getNotificationTypeSettingsFromJsonObject(JSONObject obj) {
		List<NotificationTypeSetting> notificationTypeSettings = new ArrayList<NotificationTypeSetting>();
		
		try {
			final JSONArray responseObject = obj.getJSONArray(RESPONSE_OBJECT);
			
			int size=responseObject.length();
			for (int i=0; i<size; i++) {
				final JSONObject row = (JSONObject) responseObject.get(i);
				Log.d(TAG,"Row"+ row.toString());
				final NotificationTypeSetting nt = getNotificationTypeSettingFromJSONObject(row);
				if (nt != null) {
					notificationTypeSettings.add(nt);
				}
			}
			
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
		}
		return notificationTypeSettings;
	}
	
	/**
	 * This method returns a {@link SearchProfile} object from the passed in JSONObject. If we have issues parsing the
	 * JSONObject then a null NotificationTypeSetting will be returned.
	 * @param row the JSONObject to parse.
	 * @return a {@link Location} populated with the details in the JSONObject.
	 */
	public static NotificationTypeSetting getNotificationTypeSettingFromJSONObject(JSONObject row) {
		NotificationTypeSetting nt; 
		try {
			nt = new NotificationTypeSetting();
			Log.d(TAG,"Row is: "+ row.toString());
			nt.setNotificationTypeId(row.getInt("NotificationTypeId"));
			nt.setName(row.getString("Name"));
			nt.setEnabled(row.getBoolean("Enabled"));
			
		} catch (Exception e) {
			Log.e(TAG, " Exception in Get Search Profile:" + e.getMessage());
			nt = null;
		}
		
		return nt;
	}
	
	
	public static boolean isServerCallSuccessful(JSONObject obj) {
		boolean greatSuccess;
		if (obj == null) {
			greatSuccess =  false;
		} else {
			try {
				if (obj.has(ACK_CODE) && obj.getInt(ACK_CODE) == ACK_SUCCESS) {
					greatSuccess = true;
				} else {
					greatSuccess = false;
				}
			} catch (JSONException e) {
				greatSuccess = false;
			}
		}
		return greatSuccess;
	}
	
	public static String getAckMessage(JSONObject obj) {
		String ackMessage;
		
		if (obj == null) {
			ackMessage = "";
		} else if (obj.has(ACK_MESSAGE)) {
			try {
				ackMessage = obj.getString(ACK_MESSAGE);
			} catch (JSONException e) {
				ackMessage = "";
			}
		} else {
			ackMessage = "";
		}
		
		return ackMessage;
	}
	/**
	 * This method returns a {@link Location} object from the passed in JSONObject. If we have issues parsing the
	 * JSONObject then a null tender will be returned.
	 * @param row the JSONObject to parse.
	 * @return a {@link Location} populated with the details in the JSONObject.
	 */
	public static Location getTenderFromJSONObject(JSONObject row) {
		Location tender; 
		try {
			tender = new Location();
			tender.setAgencyName(row.getString("AgencyName"));
			tender.setCategory(row.getString("Category"));
			tender.setClosingDate(DateUtil.parseDefaultDateFormat(row.getString("ClosingDate")));
			tender.setCode(row.getString("Code"));
			tender.setId(row.getLong("Id"));
			tender.setOpeningDate(DateUtil.parseDefaultDateFormat(row.getString("OpeningDate")));
			tender.setTenderState(row.getString("TenderState"));
			tender.setTenderType(row.getString("TenderType"));
			tender.setTitle(row.getString("Title"));
			
		} catch (Exception e) {
			Log.e(TAG, " Exception in Get Tender:" + e.getMessage());
			tender = null;
		}
		
		return tender;
	}


	
	public static LocationDetail getTenderDetailsFromJSONObject(JSONObject row) {
		
		LocationDetail tenderDetail = null;
		try {
			row = row.getJSONObject(RESPONSE_OBJECT);
			final Location tender = getTenderFromJSONObject(row);
			
			tenderDetail = new LocationDetail(tender);
			tenderDetail.setDescription(row.getString("Description"));
			tenderDetail.setElectronicTender(row.getBoolean("ElectronicTender"));
			tenderDetail.setWatchedTender(row.getBoolean("WatchedTender"));
		} catch (JSONException e) {
			Log.e(TAG, "Exception in getTenderDetailsFromJSONObject: " + e.getMessage());
		} 

		return tenderDetail;
	}



	public static String getTermsAndConditions(JSONObject obj) {
		String tandc = "";
		try {
			obj = obj.getJSONObject(RESPONSE_OBJECT);
		} catch (JSONException e) {
			tandc = "no response object found";
		}
		
		if (obj.has("TAndC")) {
			try {
				tandc = obj.getString("TAndC");
			} catch (JSONException e) {
				Log.d(TAG, "error " + e.getMessage());
				tandc = "could not retrieve terms and conditions";
			}
		}
		
		return tandc;
	}
	
	public static List<Module> getModules(JSONObject obj) {
		final List<Module> modules = new ArrayList<Module>();
		if (isServerCallSuccessful(obj)) {
			try {
				final JSONArray array = obj.getJSONArray(RESPONSE_OBJECT);
				for (int i=0, size=array.length(); i<size; i++) {
					modules.add(getModule(array.getJSONObject(i)));
				}
			} catch(JSONException e) {
				Log.d(TAG, "error " + e.getMessage());
			}
		}
		return modules;
	}
	
	private static Module getModule(JSONObject obj) {
		final Module module = new Module();
		
		try {
			module.setModuleName(obj.getString("ModuleName"));
			module.setActive(obj.getInt("IsActive") == 1);
		} catch (JSONException e) {
			Log.d(TAG, "error " + e.getMessage());
			return null;
		}
		return module;
	}
	
	public static String getUserId(JSONObject obj) {
		String userId = "";
		try {
			obj = obj.getJSONObject(RESPONSE_OBJECT);
		} catch (JSONException e) {
			userId = "no response object found";
		}
		
		if (obj.has("TenderUserId")) {
			try {
				userId = obj.getString("TenderUserId");
			} catch (JSONException e) {
				Log.d(TAG, "error " + e.getMessage());
			}
		}
		
		return userId;
	}
	

     /**
     * This method adds the action name from the uri to the JSONObject.
     * @param obj the JSONObject
     * @param uri the URL to get the name of the action from.
     */
     public static void addActionName(JSONObject obj, String uri) {
         try {
             if (obj == null) {
                 return;
             }
             obj.put(ACTION_NAME_KEY, getActionName(uri));
         } catch (JSONException e) {
             Log.e(TAG, e.getMessage());
         }
     }
     
     /**
     * This method returns the name of the action that the JSONObject was retrieved from.
     * @param obj the JSONObject
     * @return the name of the action invoked.
     */
     public static String getActionName(JSONObject obj) {
         return getStringValue(obj, ACTION_NAME_KEY);
     }
     
     /**
      * This method gets the name of the action from the passed in url
      * @param uri the url to extract the name of the action.
      * @return The name of the action to invoke.
      */
      private static String getActionName(String uri) {
          final int lastIndexOfSlash = uri.lastIndexOf("/");
          final String actionName;
          
          if (lastIndexOfSlash < 0) {
              actionName = uri;
          } else {
              actionName = uri.substring(lastIndexOfSlash + 1);
          }
          
          return actionName; 
      }

     
     /**
      * This method returns the string value for the passed in name from the JSONObject.
      * @param obj the {@link JSONObject}
      * @param name the name of the key for the value for the JSONObject.
      * @return the value of the string or empty string.
      */
      public static String getStringValue(JSONObject obj, String name) {
          String str = "";
          
          if (obj != null && !StringUtil.isEmpty(name)) {
              try {
                  str = obj.getString(name);
              } catch (JSONException e) {
                  str = "";
              }
          }
          
          return str;
      }

  	/**
  	 * This method returns the bool value for the passed in name from the JSONObject response.
  	 * @param obj the {@link JSONObject}
  	 * @param name the name of the key for the value for the JSONObject.
  	 * @return the value of the bool.
  	 */
  	public static Boolean getBoolValue(JSONObject obj, String name) {
  		boolean bool = false;
  		
  		if (obj != null && !StringUtil.isEmpty(name)) {
  			try {
  				obj = obj.getJSONObject(RESPONSE_OBJECT);
  				bool = obj.getBoolean(name);
  			} catch (JSONException e) {
  				Log.d(TAG, "Boolean not found in" + name);
  				Log.e(TAG, e.getMessage(), e);
  			}
  		}
  		
		return bool;
  	}
  	
  	public static boolean hasResponseObject(JSONObject obj) {
  		return obj != null && obj.has(RESPONSE_OBJECT);
  	}
	
  	public static boolean hasNotNullResponseObject(JSONObject obj) {
  		boolean hasNotNullResponseObject = false;
  		try {
  			hasNotNullResponseObject = hasResponseObject(obj) && obj.getJSONObject(RESPONSE_OBJECT) != null;
		} catch (JSONException e) {
		}
  		return hasNotNullResponseObject;
  	}
}