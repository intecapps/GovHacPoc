package com.example.intecglass.listener;

import org.json.JSONObject;

public interface ServerCallbackListener {
	/**
	 * This method is called from the AsynTask used to retrieve data from the service.
	 * @param obj the JSONObject to process from the GET request.
	 */
	public void processGetCallback(JSONObject obj);
	
	/**
	 * This method is called from the AsyncTask used to post to the service.
	 * @param obj the JSONObject to process from the POST request.
	 */
	public void processPostCallback(JSONObject obj);
	
	public void showConnectionErrorMessage();
}
