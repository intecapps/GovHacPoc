package com.example.intecglass.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;

import com.example.intecglass.R;
import com.example.intecglass.constant.Constant;
import com.google.android.glass.app.Card;

public class AbstractActivity extends Activity implements Constant {

	/**
     * Create list of API demo cards.
     */
    public static List<Card> createHomeCards(Context context) {
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(HOME, new Card(context)
        .setFootnote(R.string.home_level)
        .setText(R.string.home_text)
        );
        return cards;
    }

    public static List<Card> createAuthCards(Context context) {
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(AUTH, new Card(context).setText(R.string.home_menu_what_it_does).setFootnote("Tap to continue"));
        return cards;
    }
    
    public static List<Card> createOptionsCards(Context context) {
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(LOCATIONS, new Card(context).setText(R.string.text_heritage).setFootnote("What do you want to go see? Swipe for options"));
        cards.add(PHOTOS, new Card(context).setText(R.string.text_photos));
        return cards;
    }
    
    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTimeStamp = dateFormat.format(new Date()); // Find todays date

            return currentTimeStamp;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
	
}
