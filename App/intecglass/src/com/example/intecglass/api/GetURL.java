package com.example.intecglass.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.example.intecglass.listener.ServerCallbackListener;

public class GetURL extends PostToURL {
	/**
	 * Constructor.
	 * @param url the url.
	 */
	public GetURL(String url, ServerCallbackListener listener) {
		this(url, listener, new ArrayList<BasicNameValuePair>());
	}
	/**
	 * Constructor.
	 * @param url the url.
	 * @param useHttps flag indicating whether https connection should be used.
	 */
	public GetURL(String url, ServerCallbackListener listener, boolean useHttps) {
		this(url, listener, new ArrayList<BasicNameValuePair>(), useHttps);
	}
	
	/**
	 * Constructor.
	 * @param url the url.
	 * @param parameters the parameters to be passed in the URL.
	 */
	public GetURL(String url, ServerCallbackListener listener, List<BasicNameValuePair> parameters) {
		this(url, listener, parameters, false);
	}

	/**
	 * Constructor.
	 * @param url the url.
	 * @param parameters the parameters to be passed in the URL.
	 * @param useHttps flag indicating whether https connection should be used.
	 */
	public GetURL(String url, ServerCallbackListener listener, List<BasicNameValuePair> parameters, boolean useHttps) {
		super(url, listener, parameters, useHttps);
	}

}
