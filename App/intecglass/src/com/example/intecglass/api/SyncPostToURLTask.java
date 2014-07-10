package com.example.intecglass.api;

import org.json.JSONObject;

import android.util.Log;

import com.example.intecglass.util.HttpUtil;
import com.example.intecglass.util.JSONUtil;

/**
 * This class will execute URL passed to it and return a JSONObject retrieved from the server. It will only support
 * GET and POST methods to the server.
 */
public class SyncPostToURLTask {
	
	private static final String TAG = SyncPostToURLTask.class.getName();
	
	/**
	 * This method executes the url passed in. It only support 1 URL at a time if more than 1 is passed in
	 * the a RuntimeException will be thrown. If no urls are passed in then a RuntimeException will be thrown.
	 */
	public JSONObject doAction(PostToURL... urls){
		final JSONObject returnedValue;
		
		if (urls == null || urls.length == 0) {
			throw new RuntimeException("No urls supplied");
		}
		
		if (urls.length > 1) {
			throw new RuntimeException("Multiple urls are not supported.");
		}
		
		
		final PostToURL url = urls[0];
		Log.d(TAG,"URL:"+ url.getUrl()+url.getParameters()) ;
		// if the passed in url is of type GetURL we will do a GET to the server otherwise a POST will be performed.
		if (url instanceof GetURL) {
			returnedValue = HttpUtil.getData(url.getParameters(), url.getUrl(), url.isUseHttps());
		} else {
			returnedValue = HttpUtil.postData(url.getParameters(), url.getUrl(), url.isUseHttps());
		}
		
		// add the name of the action that was called. This will ensure that we know which action returned the
		// results.
		JSONUtil.addActionName(returnedValue, url.getUrl());
		
		Log.d(TAG,"ret val:"+ returnedValue);
		
		return returnedValue;
	}
}
