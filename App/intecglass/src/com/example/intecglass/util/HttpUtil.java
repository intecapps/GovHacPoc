package com.example.intecglass.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class HttpUtil {

	/**
	 * Tag used for logging.
	 */
	private static final String TAG = HttpUtil.class.getName();
	
	/**
	 * The encoding of the JSON response returned.
	 */
	private static final String ENCODING = "iso-8859-1";//"UTF-8";
	
	/**
	 * The new line property name.
	 */
	private static final String NEW_LINE_PROPERTY = "line.separator";
	
	/**
	 * The question mark character.
	 */
	private static final char QUESTION_MARK_CHAR = '?';
	
	/**
	 * The separator character used to separate parameters on a query string.
	 */
	private static final char PARMATER_SEPARATOR_CHAR = '&';
	
	/**
	 * The equals character.
	 */
	private static final char EQUALS_CHAR = '=';
	
	/**
	 * This method posts the passed in parameters to the uri supplied and returns a JSONObject to the calling
	 * process.
	 * @param parameters - the parameters to be posted.
	 * @param uri - the URI to post the parameters to.
	 * @return JSONObject returned from the action posted to.
	 */
	public static JSONObject postData(final List<BasicNameValuePair> parameters, final String uri) {
		return postData(parameters, uri, false);
	}
	
	/**
	 * This method posts the passed in parameters to the uri supplied and returns a JSONObject to the calling
	 * process. If the useHttps parameter is true then an SSL connection will be established.
	 * @param parameters - the parameters to be posted.
	 * @param uri - the URI to post the parameters to.
	 * @param useHttps - flag indicating whether or not an SSL connection should be used.
	 * @return JSONObject returned from the action posted to.
	 */
	public static JSONObject postData(final List<BasicNameValuePair> parameters, final String uri, final boolean useHttps) {
		return getJSONObjectFromResponse(performPost(parameters, uri, useHttps)); 
	}
	
	/**
	 * This method executes a GET request on the passed in uri appending the passed in parameters to the query string.
	 * The response is returns a JSONObject object.
	 * @param parameters the parameters to be passed to the GET request.
	 * @param uri the uri to invoke.
	 * @return a JSONObject returned from the request.
	 */
	public static JSONObject getData(final List<BasicNameValuePair> parameters, final String uri) {
		return getData(parameters, uri, false);
	}
	
	/**
	 * This method executes a GET request on the passed in uri appending the passed in parameters to the query string.
	 * The response is returns a JSONObject object.
	 * @param parameters the parameters to be passed to the GET request.
	 * @param uri the uri to invoke.
	 * @param useHttps - flag indicating whether or not an SSL connection should be used. 
	 * @return a JSONObject returned from the request.
	 */
	public static JSONObject getData(final List<BasicNameValuePair> parameters, final String uri, boolean useHttps) {
		return getJSONObjectFromResponse(performGet(parameters, uri, useHttps)); 
	}
	
	/**
	 * This method returns a JSONObject from the passed in HttpResponse.
	 * @param response the HttpResponse.
	 * @return a JSONObject from the passed in HttpResponse.
	 */
	public static JSONObject getJSONObjectFromResponse(HttpResponse response) {
		InputStream inputStream = null;
		JSONObject jsonObject = null;
		String result = "";
		
		try {
			final HttpEntity entity = response.getEntity();
			inputStream = entity.getContent();
			final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, ENCODING), 8);
			final StringBuilder sb = new StringBuilder();

			String line = null;
			while ((line = reader.readLine()) != null)
			{
			    sb.append(line);
			    sb.append(System.getProperty(NEW_LINE_PROPERTY));
			}
			result = sb.toString();
			
			Log.d(TAG, "result from data service call - " + result);

			jsonObject = new JSONObject(result);

		} catch (IOException e) {
			Log.e(TAG,"IO Exception trying to connect>"+ e.getMessage());
		} catch (JSONException e) {
			Log.e(TAG,"JSONException>"+ e.getMessage());			
		} catch(Exception e) {
			Log.e(TAG,"Exception>"+ e.getMessage());
		} finally {
		
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					Log.e(TAG,"IO Exception trying to connect>"+ e.getMessage());
				}
			}
		}
		
		return jsonObject; 
	}
	/**
	 * This method posts the passed in parameters to the uri supplied and returns a HttpResponse to the calling
	 * process.
	 * @param parameters - the parameters to be posted.
	 * @param uri - the URI to post the parameters to.
	 * @return HttpResponse returned from the action posted to.
	 */
	public static HttpResponse performPost(final List<BasicNameValuePair> parameters, final String uri) {
		return performPost(parameters, uri, false);
	}
	
	
	/**
	 * This method posts the passed in parameters to the uri supplied and returns a HttpResponse to the calling
	 * process. If the useHttps parameter is true then an SSL connection will be established.
	 * @param parameters - the parameters to be posted.
	 * @param uri - the URI to post the parameters to.
	 * @param useHttps - flag indicating whether or not an SSL connection should be used.
	 * @return HttpResponse returned from the action posted to.
	 */
	public static HttpResponse performPost(final List<BasicNameValuePair> parameters, final String uri, final boolean useHttps) {
		/*
		//add support for SSL
		//http://stackoverflow.com/questions/995514/https-connection-android
		*/
		final HttpClient client = new DefaultHttpClient();
		final HttpPost httpPost = new HttpPost(uri);
		
        // Add the parameters
		if (parameters.size() > 0) {
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(parameters));
			} catch (UnsupportedEncodingException e) {
				Log.d(TAG, "Error encoding paramters: " + e.getMessage());
			}
		}
	    
		return executeRequest(client, httpPost);
	}
	
	/**
	 * This method executes a GET request on the uri passed in.
	 * @param parameters the parameters to pass through.
	 * @param uri the url to execute the GET.
	 * @return the HttpResponse returned from the GET request.
	 */
	public static HttpResponse performGet(final List<BasicNameValuePair> parameters, final String uri) {
		return performGet(parameters, uri, false);
	}
	
	/**
	 * This method executes a GET request on the uri passed in.
	 * @param parameters the parameters to pass through.
	 * @param uri the url to execute the GET.
	 * @param useHttps flag indicating whether or not an SSL connection should be used.
	 * @return the HttpResponse returned from the GET request.
	 */
	public static HttpResponse performGet(final List<BasicNameValuePair> parameters, final String uri, final boolean useHttps) {
		final  HttpClient client = new DefaultHttpClient();
		String url = getUrlWithParametersInQueryString(uri, parameters);
		Log.d(TAG,"Url actually used is:"+ url);
		final HttpGet httpGet = new HttpGet(url);
		 
		return executeRequest(client, httpGet);
	}
	
	/**
	 * This method executes a request for the HttpClient and HttpUriRequest passed in.
	 * @param client the HttpClient used to execute the request.
	 * @param request the uri request to execute.
	 * @return the HttpResponse returned from the method execution.
	 */
	private static HttpResponse executeRequest(HttpClient client, HttpUriRequest request) {
		HttpResponse response = null;
	
		try {
			response = client.execute(request);
		} catch (ClientProtocolException e) {
	        Log.e(TAG,"Client Protocol Exception trying to connect>"+ e.getMessage());
	    } catch (IOException e) {
	    	Log.e(TAG,"IO Exception trying to connect>"+ e.getMessage());
	    } catch(Exception e){
	    	Log.e(TAG,"Exception trying to connect>"+ e);
	    }
		return response;
	}
	
	/**
	 * This method adds the parameters to the query string.
	 * @param uri the URL to append the parameters to.
	 * @param parameters the parameters to add to.
	 * @return a string with containing the URL with the parameters added to the query string. 
	 */
	private static String getUrlWithParametersInQueryString(String uri, List<BasicNameValuePair> parameters) {
		final StringBuilder sb = new StringBuilder();
		
		sb.append(uri);
		
		if (parameters != null && parameters.size() > 0) {
			sb.append(QUESTION_MARK_CHAR);
			for(BasicNameValuePair valuePair : parameters) {
				sb.append(valuePair.getName());
				sb.append(EQUALS_CHAR);
				
				try {
					sb.append(URLEncoder.encode(valuePair.getValue(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// Try the decoded value instead.
					sb.append(valuePair.getValue());
				}
				sb.append(PARMATER_SEPARATOR_CHAR);
			}
			sb.deleteCharAt(sb.length() -1);
		}
		
		return sb.toString();
	}
	
}
