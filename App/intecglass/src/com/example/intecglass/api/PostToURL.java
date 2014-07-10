package com.example.intecglass.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.example.intecglass.listener.ServerCallbackListener;

public class PostToURL {
	/**
	 * The url to post to.
	 */
	private String url;
	
	/**
	 * A list of parameters to post to the url.
	 */
	private List<BasicNameValuePair> parameters;
	
	/**
	 * Use the https.
	 */
	private boolean useHttps;
	
	/**
	 * The callback listener.
	 */
	private ServerCallbackListener listener;
	
	/**
	 * Constructor taking in a url, it will initialise the parameters list to an
	 * empty string.
	 * @param url the url to post to.
	 */
	public PostToURL(String url, ServerCallbackListener listener) {
		this(url, listener, new ArrayList<BasicNameValuePair>());
	}
	
	/**
	 * Constructor taking in a url, it will initialise the parameters list to an
	 * empty string.
	 * @param url the url to post to.
	 * @param useHttps - flag indicating whether to use a https connection.
	 */
	public PostToURL(String url, ServerCallbackListener listener, boolean useHttps) {
		this(url, listener, new ArrayList<BasicNameValuePair>(), useHttps);
	}
	
	/**
	 * Constructor taking in the url to post to and the parameters to pass to the url. If the
	 * parameters passed in is null then an empty list will be created.
	 * @param url - the url to post to.
	 * @param parameters - the parameters to send when posting to the url.
	 */
	public PostToURL(String url, ServerCallbackListener listener, List<BasicNameValuePair> parameters) {
		this(url, listener, parameters, false);
	}

	/**
	 * Constructor taking in the url to post to and the parameters to pass to the url. If the
	 * parameters passed in is null then an empty list will be created.
	 * @param url - the url to post to.
	 * @param parameters - the parameters to send when posting to the url.
	 * @param useHttps - flag indicating whether to use a https connection.
	 */
	public PostToURL(String url, ServerCallbackListener listener, List<BasicNameValuePair> parameters, boolean useHttps) {
		this.url = url;
		this.listener = listener;
		if (parameters == null) {
			this.parameters = new ArrayList<BasicNameValuePair>();
		} else {
			this.parameters = parameters;
		}
		this.useHttps = useHttps;
	}
	/**
	 * This method adds the passed in parameter to the parameters instance if it is not null
	 * and the parameter is not already contained within the list.
	 * @param param the parameter to add to the parameters list.
	 */
	public void addParameter(BasicNameValuePair param) {
		if (param != null && !parameters.contains(param)) {
			parameters.add(param);
		}
	}
	
	/**
	 * This method adds a new parameter if the key and value are not null. 
	 * @param key the name of the parameter to add to the parameters list.
	 * @param value the value of the parameter to add to the parameters list.
	 */
	public void addParameter(String key, String value) {
		if (key != null && value != null) {
			addParameter(new BasicNameValuePair(key, value));
		}
	}

	/**
	 * Getter for the url instance variable.
	 * @return the value of the url instance variable.
	 */
	public String getUrl() {
		return url;
	}


	/**
	 * Setter for the url instance variable.
	 * @param url the value to set.
	 */
	public void setUrl(final String url) {
		this.url = url;
	}


	/**
	 * This method returns the parameters list.
	 * @return the value of the parameters instance variable.
	 */
	public List<BasicNameValuePair> getParameters() {
		return parameters;
	}

	/**
	 * @return the useHttps
	 */
	public boolean isUseHttps() {
		return useHttps;
	}

	/**
	 * @param useHttps the useHttps to set
	 */
	public void setUseHttps(boolean useHttps) {
		this.useHttps = useHttps;
	}

	/**
	 * @return the listener
	 */
	public ServerCallbackListener getListener() {
		return listener;
	}

	/**
	 * @param listener the listener to set
	 */
	public void setListener(ServerCallbackListener listener) {
		this.listener = listener;
	}
}
