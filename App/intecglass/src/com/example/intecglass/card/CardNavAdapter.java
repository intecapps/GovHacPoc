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

package com.example.intecglass.card;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.intecglass.R;
import com.example.intecglass.model.Image;
import com.example.intecglass.model.Location;
import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;

/**
 * Adapter class that handles list of cards.
 */
public class CardNavAdapter extends CardScrollAdapter {

    private final List<Card> mCards;
    
    private Location mLocation;
    private Image mImage;
    
    enum ThemeCards {
        INITIAL(0, R.layout.initial),
        AUTH(1, R.layout.auth),
        IMAGE(2, R.layout.images),
        LOCATION(3, R.layout.location);

        private final int mPos;
        private final int mResid;

        ThemeCards(int pos, int resid) {
            mPos = pos;
            mResid = resid;
        }

        int getPosition() {
            return mPos;
        }

        int getResourceID() {
            return mResid;
        }
    }
    
    private LayoutInflater mInflater;
    
    public CardNavAdapter(Context context, List<Card> cards, Location location) {
    	mCards = cards;
    	mInflater = LayoutInflater.from(context);
    	mLocation = location;
    }
    
    public CardNavAdapter(Context context, List<Card> cards, Image image) {
    	mCards = cards;
    	mInflater = LayoutInflater.from(context);
    	mImage = image;
    }

    @Override
    public int getCount() {
        return mCards.size();
    }

    @Override
    public Object getItem(int position) {
        return mCards.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return mCards.get(position).getView(convertView, parent);
        
        View view = null;
        // Because cards in this example are static, a non-null convertView
        // can be reused unconditionally.
        if (convertView != null) {
            view = convertView;
        } else {
            view = mInflater.inflate(ThemeCards.values()[position].getResourceID(), null);
        }
        return view;
    }

    @Override
    public int getViewTypeCount() {
        return Card.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position){
        return mCards.get(position).getItemViewType();
    }

    @Override
    public int getPosition(Object item) {
        for (int i = 0; i < mCards.size(); i++) {
            if (getItem(i).equals(item)) {
                return i;
            }
        }
        return AdapterView.INVALID_POSITION;
    }
    

}
