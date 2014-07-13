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
import com.google.android.glass.app.Card.ImageLayout;

public class AbstractActivity extends Activity implements Constant {

	/**
     * Create list of API demo cards.
     */
    public static List<Card> createHomeCards(Context context) {
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(HOME, new Card(context)
        .setImageLayout(ImageLayout.FULL)
        .addImage(R.drawable.splash)
        .setFootnote(R.string.home_level)
        .setText(R.string.home_text));
        
        return cards;
    }

    public static List<Card> createAuthCards(Context context) {
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(AUTH, new Card(context).setText(R.string.home_menu_what_it_does).setFootnote("Tap to continue"));
        return cards;
    }
    
    public static List<Card> createOptionsCards(Context context) {
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(LOCATIONS, new Card(context).setText(R.string.text_heritage).setImageLayout(ImageLayout.FULL)
                .addImage(R.drawable.locationbg).setFootnote("Swipe for locations, photos and challenges"));
        
        cards.add(PHOTOS, new Card(context).setText(R.string.text_photos).setImageLayout(ImageLayout.FULL)
                .addImage(R.drawable.photobg));
        
        cards.add(BADGE1, new Card(context)
        .setImageLayout(ImageLayout.FULL)
        .addImage(R.drawable.badgebar)
        .setText("Check into 3 Law Courts\n")
        .setFootnote("Progress 3/3 COMPLETE")
        );
        
        cards.add(BADGE2, new Card(context)
        .setImageLayout(ImageLayout.FULL)
        .addImage(R.drawable.badgebeat)
        .setText("Check into 3 Police Stations\n")
        .setFootnote("Progress 1/3 INCOMPLETE")
        );
        
        cards.add(BADGE3, new Card(context)
        .setImageLayout(ImageLayout.FULL)
        .addImage(R.drawable.badgebonecollector)
        .setText("Find the Adelaide Bone sculpture\n")
        .setFootnote("Progress COMPLETE")
        );
        
        cards.add(BADGE4, new Card(context)
        .setImageLayout(ImageLayout.FULL)
        .addImage(R.drawable.badgesundayschool)
        .setText("Check into 3 religious places\n")
        .setFootnote("Progress 3/3 COMPLETE")
        );
        
        cards.add(BADGE5, new Card(context)
        .setImageLayout(ImageLayout.FULL)
        .addImage(R.drawable.badgeweary)
        .setText("Check into 5 Pubs\n")
        .setFootnote("Progress 5/5 COMPLETE")
        );
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
