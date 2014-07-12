package com.example.intecglass.api;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.example.intecglass.listener.DownloadImageCallbackListener;
import com.google.android.glass.app.Card;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
	
	
	/**
	 * The listener class to call when the results are retrieved.
	 */
	private DownloadImageCallbackListener listener;

		public DownloadImage(DownloadImageCallbackListener listener) {
		super();
		this.listener = listener;
	}

		protected Bitmap doInBackground(String... param) {
			// TODO Auto-generated method stub
			return downloadBitmap(param[0], listener);
			
		}

		@Override
		protected void onPreExecute() {
			Log.i("Async-Example", "onPreExecute Called");
			
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			Log.i("Async-Example", "onPostExecute Called");
				if (listener == null) {
					return;
				}
				
				listener.processImage(result);

		}

		private Bitmap downloadBitmap(String url, DownloadImageCallbackListener listener) {
			// initilize the default HTTP client object
			final DefaultHttpClient client = new DefaultHttpClient();
			this.listener = listener;
			//forming a HttoGet request 
			final HttpGet getRequest = new HttpGet(url);
			try {

				HttpResponse response = client.execute(getRequest);

				//check 200 OK for success
				final int statusCode = response.getStatusLine().getStatusCode();

				if (statusCode != HttpStatus.SC_OK) {
					Log.w("ImageDownloader", "Error " + statusCode + 
							" while retrieving bitmap from " + url);
					return null;

				}

				final HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStream inputStream = null;
					try {
						// getting contents from the stream 
						inputStream = entity.getContent();

						// decoding stream data back into image Bitmap that android understands
						final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

						return bitmap;
					} finally {
						if (inputStream != null) {
							inputStream.close();
						}
						entity.consumeContent();
					}
				}
			} catch (Exception e) {
				// You Could provide a more explicit error message for IOException
				getRequest.abort();
				Log.e("ImageDownloader", "Something went wrong while" +
						" retrieving bitmap from " + url + e.toString());
			} 

			return null;
		}

	}


