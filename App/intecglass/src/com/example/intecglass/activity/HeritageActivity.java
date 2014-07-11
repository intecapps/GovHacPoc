/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.intecglass.activity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.intecglass.R;
import com.example.intecglass.card.CardAdapter;
import com.example.intecglass.model.Location;
import com.example.intecglass.util.JSONUtil;
import com.google.android.glass.app.Card;
import com.google.android.glass.app.Card.ImageLayout;
import com.google.android.glass.widget.CardScrollView;

/**
 * Creates a card scroll view with examples of different image layout cards.
 */
public final class HeritageActivity extends Activity {

    private CardScrollView mCardScroller;
    
    ArrayList<Card> cards = new ArrayList<Card>();
    
    public String outputString  = "No Data";
    public Bitmap image;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
     // get an image
        String url = "https://farm8.staticflickr.com/7338/13711227263_3d8e0a35c5_b.jpg";
        new ImageDownloader().execute(url);
        
        //get some data
        new ReadWeatherJSONFeedTask().execute("http://etendersazure.azurewebsites.net/api/SA/GetOpenTenders?deviceUID=0");  
        
        
        
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(new CardAdapter(createCards(this)));
        setContentView(mCardScroller);
    }
    
    
    private List<Card> updateCards(Context context) {
			cards.add(getImagesCard(context)
					.setImageLayout(ImageLayout.LEFT)
	        		.addImage(image)
	                .setText(outputString));
        return cards;
    }

    public String readJSONFeed(String URL) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
            } else {
                Log.d("JSON", "Failed to download file");
            }
        } catch (Exception e) {
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }        
        return stringBuilder.toString();
    }
    
    private class ReadWeatherJSONFeedTask extends AsyncTask
    <String, Void, String> {
        protected String doInBackground(String... urls) {
            return readJSONFeed(urls[0]);
        }
 
        protected void onPostExecute(String result) {
            try {
            	//iterate through results adding cards
            	JSONObject jsonObject = new JSONObject(result);
                List<Location> tenderList = JSONUtil.getTendersFromJsonObject(jsonObject);
                for (Location item : tenderList) {
					StringBuilder sb = new StringBuilder();
					sb.append(item.getCode()+"  ");
					sb.append(item.getTitle()+"  ");
					sb.append(item.getAgencyName()+"  ");
					sb.append(item.getCategory()+"  ");
					sb.append(item.getClosingDate()+"  ");
					outputString = sb.toString();
					updateCards(getBaseContext());
				}
            
                
                
            } catch (Exception e) {
                Log.d("ReadJSONFeedTask", e.getLocalizedMessage());
            }          
        }
    }
    
    
    /**
     * Create list of cards that showcase different type of {@link Card} API.
     */
    private List<Card> createCards(Context context) {

        cards.add(getImagesCard(context)
                .setImageLayout(ImageLayout.FULL)
                .setText(R.string.home_menu_explanation));
        return cards;
    }

    private Card getImagesCard(Context context) {
        Card card = new Card(context);
        return card;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCardScroller.activate();
    }

    @Override
    protected void onPause() {
        mCardScroller.deactivate();
        super.onPause();
    }
    
    private class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... param) {
			// TODO Auto-generated method stub
			return downloadBitmap(param[0]);
		}

		@Override
		protected void onPreExecute() {
			Log.i("Async-Example", "onPreExecute Called");
			

		}

		@Override
		protected void onPostExecute(Bitmap result) {
			Log.i("Async-Example", "onPostExecute Called");
			image = result;

		}

		private Bitmap downloadBitmap(String url) {
			// initilize the default HTTP client object
			final DefaultHttpClient client = new DefaultHttpClient();

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
 
}
