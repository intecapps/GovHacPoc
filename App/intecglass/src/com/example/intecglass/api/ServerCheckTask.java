package com.example.intecglass.api;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

import com.example.intecglass.util.HttpUtil;

public class ServerCheckTask extends AsyncTask<String, Void, Boolean>{

	private static final int SUCCESS_CODE = 200;
	
	@Override
	protected Boolean doInBackground(String... params) {
		boolean isOnline  = false;
		final String checkServiceUrl = params[0];
    	try {
	    	// check to see if the service is available.
	    	final HttpResponse response = HttpUtil.performGet(new ArrayList<BasicNameValuePair>(), checkServiceUrl);
	    	isOnline = (response != null 
	    			&& response.getStatusLine() != null 
	    			&& response.getStatusLine().getStatusCode() == SUCCESS_CODE);
    	} catch (Exception e) {
    		isOnline = false;
    	}
		
		return isOnline;
	}

}
