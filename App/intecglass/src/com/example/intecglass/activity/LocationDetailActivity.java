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

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.intecglass.R;
import com.example.intecglass.card.CardListAdapter;
import com.example.intecglass.model.Location;
import com.google.android.glass.app.Card;
import com.google.android.glass.app.Card.ImageLayout;
import com.google.android.glass.media.Sounds;
import com.google.android.glass.widget.CardScrollView;

/**
 * Creates a card scroll view with examples of different image layout cards.
 */
public final class LocationDetailActivity extends AbstractActivity{

    private CardScrollView mCardScroller;
    
    ArrayList<Card> cards = new ArrayList<Card>();

    public String outputString  = "There was an isue getting the loction data. Please try again later";
    public Bitmap image;
   
    
    @Override
   	public boolean onCreateOptionsMenu(Menu menu) {
   		// TODO Auto-generated method stub
   		//return super.onCreateOptionsMenu(menu);
       	MenuInflater inflater = getMenuInflater();
           inflater.inflate(R.menu.location_menu, menu);
           return true;
   	}

   	@Override
   	public boolean onOptionsItemSelected(MenuItem item) {
   		// Handle item selection. Menu items typically start another
           // activity, start a service, or broadcast another intent.
           switch (item.getItemId()) {
               case R.id.menu_report:
                   startActivity(new Intent(this,
                   AuthActivity.class));
                   return true;
               case R.id.menu_directions:
                   startActivity(new Intent(this,
                   AuthActivity.class));
                   return true;
               case R.id.menu_check:
                   startActivity(new Intent(this,
                   AuthActivity.class));
                   return true;
               case R.id.menu_find_photos:
                   startActivity(new Intent(this,
                   AuthActivity.class));
                   return true;
               default:
   		return super.onOptionsItemSelected(item);
           }
   	}
   	
   /*	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
          if (event.getAction() == KeyEvent.ACTION_DOWN) {
              openOptionsMenu();
              return true;
          }
          return false;
    }*/

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Location location = (Location) getIntent().getSerializableExtra("Location");
        //set up scroll
        
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(new CardListAdapter( createCards(this)));
        setContentView(mCardScroller);
        setCardScrollerListener();
    }
    

    /**
     * Create list of cards that showcase different type of {@link Card} API.
     */
    private List<Card> createCards(Context context) {
    	Location location = (Location) getIntent().getSerializableExtra("Location");
    	Card card = new Card(context);
    	card.setImageLayout(ImageLayout.FULL)
        .setText(location.getDistance()+" metres from you \n"+location.getDescription());
        cards.add(card);
        
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
    
    /**
     * Different type of activities can be shown, when tapped on a card.
     */
    private void setCardScrollerListener() {
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("LocationDetailActivity", "Clicked view at position " + position + ", row-id " + id);
                int soundEffect = Sounds.TAP;
                openOptionsMenu();

                // Play sound.
                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.playSoundEffect(soundEffect);
            }
        });
    }

 
}
