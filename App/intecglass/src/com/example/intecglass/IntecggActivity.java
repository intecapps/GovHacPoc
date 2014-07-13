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

package com.example.intecglass;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.intecglass.activity.AbstractActivity;
import com.example.intecglass.activity.AuthActivity;
import com.example.intecglass.activity.HomeActivity;
import com.example.intecglass.card.CardListAdapter;
import com.example.intecglass.constant.Constant;
import com.example.intecglass.persistence.Storage;
import com.google.android.glass.media.Sounds;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

/**
 * Creates a card scroll view with examples of different GDK APIs.
 *
 * <ol>
 * <li> Cards
 * <li> GestureDetector
 * <li> textAppearance[Large|Medium|Small]
 * <li> OpenGL LiveCard
 * <li> VoiceMenu
 * </ol>
 */
public class IntecggActivity extends AbstractActivity implements Constant{

    private static final String TAG = IntecggActivity.class.getSimpleName();

    private CardScrollAdapter mAdapter;
    private CardScrollView mCardScroller;

    // Visible for testing.
    CardScrollView getScroller() {
        return mCardScroller;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mAdapter = new CardListAdapter(createHomeCards(this));
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(mAdapter);
        setContentView(mCardScroller);
        setCardScrollerListener();
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
                Log.d(TAG, "Clicked view at position " + position + ", row-id " + id);
                int soundEffect = Sounds.TAP;
                switch (position) {
                    case HOME:
                    	if (Storage.getIntroSeen(getBaseContext()))
                    	{
                    		startActivity(new Intent(IntecggActivity.this, HomeActivity.class));
                    	}
                    	else {
                    		startActivity(new Intent(IntecggActivity.this, AuthActivity.class));
                    		// set intro seen by user
                    		Storage.persistIntroSeen(getBaseContext());
                    	}
                        break;

                    default:
                        soundEffect = Sounds.ERROR;
                        Log.d(TAG, "Don't show anything");
                }

                // Play sound.
                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.playSoundEffect(soundEffect);
            }
        });
    }
}
