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
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.intecglass.R;
import com.example.intecglass.card.CardListAdapter;
import com.example.intecglass.constant.Constant;
import com.example.intecglass.listener.ServerCallbackListener;
import com.example.intecglass.model.Image;
import com.example.intecglass.model.Location;
import com.example.intecglass.util.ActivityUtil;
import com.example.intecglass.util.GenericUtil;
import com.example.intecglass.util.JSONUtil;
import com.google.android.glass.app.Card;
import com.google.android.glass.app.Card.ImageLayout;
import com.google.android.glass.media.Sounds;
import com.google.android.glass.widget.CardScrollView;

/**
 * Creates a card scroll view with examples of different image layout cards.
 */
public final class PhotoActivity extends AbstractActivity implements ServerCallbackListener {

    private CardScrollView mCardScroller;
    
    ArrayList<Card> cards = new ArrayList<Card>();
    List<Image> images;
    
    public String outputString  = "There was an isue getting the loction data. Please try again later";

   Bitmap image;
   
   static boolean imageCallback; 

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        // get an image

        //get my location from mirror
        String action = getString(R.string.action_getlastknownlocation);
        GenericUtil.executeGet(this, action, null, this, false);

        //set up scroll
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(new CardListAdapter(createCards(this)));
        setContentView(mCardScroller);
        //setCardScrollerListener();
    }
    
    
    private List<Card> updateCards(Context context, String name, String description, Bitmap image) {
    	Card card = new Card(context);
    		
		card.setImageLayout(ImageLayout.FULL)
		.addImage(image)
        .setText(name + "\n" + description);
		cards.add(card);
		
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
    
  
    
    
    /**
     * Create list of cards that showcase different type of {@link Card} API.
     */
    private List<Card> createCards(Context context) {

        cards.add(getCard(context)
                .setImageLayout(ImageLayout.FULL)
                .addImage(R.drawable.photobg)
                .setFootnote("Loading...")
                .setText(R.string.menu_photos));
        return cards;
    }

    private Card getCard(Context context) {
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


	@Override
	public void processGetCallback(JSONObject obj) {
		// TODO Auto-generated method stub
		//Log.i("LoctaionActivity", obj.toString());
		if (obj.has("kind") && obj.has("items")) {
			try {
				JSONArray array = obj.getJSONArray("items");
				
				JSONObject myLocation = array.getJSONObject(0);
				
				String lat = myLocation.getString("latitude");
				String lon = myLocation.getString("longitude");
				
				//lets get some data for our Locations list
				final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		        String action = getString(R.string.action_getimages);
				parameters.add(new BasicNameValuePair(Constant.LAT, lat));
				parameters.add(new BasicNameValuePair(Constant.LON, lon));
				parameters.add(new BasicNameValuePair(Constant.PLACEMARKERID, "0"));
		        GenericUtil.executeGet(this, action, parameters, this, true);
				
			} catch (JSONException e){
			Log.e("LocationActivity", e.getMessage());
			}
		}
		else {
			images = JSONUtil.getImagesFromJSONObject(obj);
			
			for (Image item : images) {
	        	Image img = new Image();
	        	img.setName(item.getName());
	        	img.setImageUrl(item.getImageUrl());
	        	img.setDescription(item.getDescription());
	        	new DownloadImage().execute(img);
	        	
			}
			 ActivityUtil.showToast(this, "Swipe to see historic Adelaide photo locations near you");
		}
	}
	
	public class DownloadImage extends AsyncTask<Image, Void, Image> {

	@Override
		protected Image doInBackground(Image... params) {
			// TODO Auto-generated method stub
			return downloadBitmap(params[0]);
		}

		@Override
		protected void onPostExecute(Image result) {
			// TODO Auto-generated method stub
			//super.onPostExecute(result);
			try {
			updateCards(getBaseContext(), result.getName(), result.getDescription(), result.getBitmap());
			}
			catch (Exception e ) {
				Log.e("Image fail", "Failed to download image data");
			}
		}

	private Image downloadBitmap(Image img) {
		// initilize the default HTTP client object
		final DefaultHttpClient client = new DefaultHttpClient();
		//this.listener = listener;
		//forming a HttoGet request 
		final HttpGet getRequest = new HttpGet(img.getImageUrl());
		try {

			HttpResponse response = client.execute(getRequest);

			//check 200 OK for success
			final int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				Log.w("ImageDownloader", "Error " + statusCode + 
						" while retrieving bitmap from " + img.getImageUrl());
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
					img.setBitmap(bitmap);
					return img;
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
					" retrieving bitmap from " + img.getImageUrl() + e.toString());
		} 

		return null;
	}
	}

	@Override
	public void processPostCallback(JSONObject obj) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void showConnectionErrorMessage() {
		// TODO Auto-generated method stub
		
	}
	
	/**
     * Different type of activities can be shown, when tapped on a card.
     */
    private void setCardScrollerListener() {
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("LocationActivity", "Clicked view at position " + position + ", row-id " + id);
                int soundEffect = Sounds.TAP;
                for (Image img : images) {
                	Intent intent = new Intent(PhotoActivity.this, PhotoDetailActivity.class);
                	intent.putExtra("Image", img);
                    startActivity(intent);
				}
                // Play sound.
                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.playSoundEffect(soundEffect);
            }
        
        });
    }
}
