package com.example.intecglass.api;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.example.intecglass.listener.ServerCallbackListener;
import com.example.intecglass.util.HttpUtil;
import com.example.intecglass.util.JSONUtil;

public class PostToURLTask extends AsyncTask<PostToURL, Void, JSONObject>{
	
	private static String TAG = PostToURLTask.class.getName();
	/**
	 * The listener class to call when the results are retrieved.
	 */
	private ServerCallbackListener listener;
	
	/**
	 * Flag indicating whether the request is a GET request.
	 */
	private boolean isGet;
	
	
	/**
	 * This method executes the url passed in. It only support 1 URL at a time if more than 1 is passed in
	 * the a RuntimeException will be thrown. If no urls are passed in then a RuntimeException will be thrown.
	 */
	@Override
	protected JSONObject doInBackground(PostToURL... urls) {
		final JSONObject returnedValue;
		
		if (urls == null || urls.length == 0) {
			throw new RuntimeException("No urls supplied");
		}
		
		if (urls.length > 1) {
			throw new RuntimeException("Multiple urls are not supported.");
		}
		
		
		final PostToURL url = urls[0];
		
		// if the passed in url is of type GetURL we will do a GET to the server otherwise a POST will be performed.
		if (url instanceof GetURL) {
			Log.d(TAG,"Get"+ url.getParameters());
			returnedValue = HttpUtil.getData(url.getParameters(), url.getUrl(), url.isUseHttps());
			isGet = true;
		} else {
			Log.d(TAG,"POST"+ url.getParameters());
			returnedValue = HttpUtil.postData(url.getParameters(), url.getUrl(), url.isUseHttps());
			isGet = false;
		}
		
		this.listener = url.getListener();
		
		// add the name of the action that was called. This will ensure that we know which action returned the
		// results.
		JSONUtil.addActionName(returnedValue, url.getUrl());
		
		return returnedValue;
	}

	
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(JSONObject result) {
		if (listener == null) {
			return;
		}
		if (isGet) {
			// call the method that will handle the GET payload from the server.
			listener.processGetCallback(result);
		} else {
			// call the method that will handle the POST payload from the server.
			listener.processPostCallback(result);
		}
	}
}
