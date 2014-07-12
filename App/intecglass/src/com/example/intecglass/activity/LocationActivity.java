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

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.intecglass.R;
import com.example.intecglass.card.CardListAdapter;
import com.example.intecglass.constant.Constant;
import com.example.intecglass.listener.ServerCallbackListener;
import com.example.intecglass.model.Location;
import com.example.intecglass.persistence.Storage;
import com.example.intecglass.util.GenericUtil;
import com.example.intecglass.util.JSONUtil;
import com.google.android.glass.app.Card;
import com.google.android.glass.app.Card.ImageLayout;
import com.google.android.glass.widget.CardScrollView;

/**
 * Creates a card scroll view with examples of different image layout cards.
 */
public final class LocationActivity extends Activity implements ServerCallbackListener{

    private CardScrollView mCardScroller;
    
    ArrayList<Card> cards = new ArrayList<Card>();

    public String outputString  = "There was an isue getting the loction data. Please try again later";
    public Bitmap image;
   

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
     // get an image
        //String url = "https://farm8.staticflickr.com/7338/13711227263_3d8e0a35c5_b.jpg";
        //new ImageDownloader().execute(url);

        //get my location from mirror
        String action = getString(R.string.action_getlastknownlocation);
        GenericUtil.executeGet(this, action, null, this, false);
        
        

        //set up scroll
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(new CardListAdapter(createCards(this)));
        setContentView(mCardScroller);
    }
    
    
    private List<Card> updateCards(Context context, String name, String description) {
    	Card card = new Card(context);
    	card.setText(name + "\n" + description);
    	
    	//View card1View = card.getView();	
    	
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
                .setText(R.string.menu_heritage));
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
				
				/*String longitude = myLocation.getString("longitude");
				String latitude = myLocation.getString("latitude");*/
				
				//lets get some data for our Locations list
				final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		        String action = getString(R.string.action_getplacemarkers);
				parameters.add(new BasicNameValuePair(Constant.LAT, lat));
				parameters.add(new BasicNameValuePair(Constant.LON, lon));
		        GenericUtil.executeGet(this, action, parameters, this, true);
				
			} catch (JSONException e){
			Log.e("LocationActivity", e.getMessage());
			}
		}
		else {
			List<Location> list = JSONUtil.getLocationsFromJSONObject(obj);
	        for (Location item : list) {
	        	String Name = item.getName();
	        	String Description = item.getDescription();
				updateCards(getBaseContext(), Name, Description);
			}
		}
	}


	@Override
	public void processPostCallback(JSONObject obj) {
		// TODO Auto-generated method stub
		Log.i("LoctaionActivity", obj.toString());
	}


	@Override
	public void showConnectionErrorMessage() {
		// TODO Auto-generated method stub
		
	}
 
}
