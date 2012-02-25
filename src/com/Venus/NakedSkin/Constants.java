package com.Venus.NakedSkin;

import android.graphics.Color;
import android.text.Html;
import android.text.Spanned;

public class Constants {

    public final static int SPLASH_TIMEOUT = 1000; //1 second
      
    


    //Tutorial copy
    public final static String TUTORIAL_POSITIVE = "Okay";
    public final static String TUTORIAL_NEGATIVE = "Go back";
    
    public final static Spanned TUTORIAL_MESSAGE = 
    		Html.fromHtml("Hi there, goddess!</br> Your journey to Naked Skin<sup><small>®</small></sup> starts now. Click “Okay” to learn how this app is going to make your journey easier.");
    public final static Spanned TUTORIAL_SCHEDULE_MESSAGE = 
    	Html.fromHtml("Simply go to the Calendar View to view both past and upcoming treatments (circled in blue) as well as today’s date (circled in white). Click on any blue circled date to see the details of your appointment with Naked Skin<sup><small>®</small></sup>.");
    public final static String TUTORIAL_TREATMENT_MESSAGE = "Treat the Treatment Area Scheduler like your personal assistant. " +
        "Simply select the body part you want to become silky smooth and the " +
    	"date you want your first treatment, and it will automatically schedule the remaining treatments."	;
    public final static Spanned TUTORIAL_HOWTO_MESSAGE = Html.fromHtml("Check out short videos to learn how Naked Skin<sup><small>®</small></sup> works.");
    public final static String TUTORIAL_PLAYLIST_MESSAGE = "CURRENTLY NOT IMPLEMENTED\n" +
        "Here, you'll be able to select a playlist to listen to while you treat yourself.";
    public final static String TUTORIAL_SETTINGS_MESSAGE = "You can change how you want to be reminded under Reminder Settings.  Now you’re ready to get started.";
    public final static String TUTORIAL_START_MESSAGE = "Great! Let's get started on your first treatment!";

    //Shared variable names
    public final static String TAB_NUMBER = "tabNumber";

    //Colors
    public final static int COLOR_SELECTED = Color.BLUE;
    public final static int COLOR_UNSELECTED = Color.WHITE;

    public final static int COLOR_BG_SELECTED = Color.BLACK;
    public final static int COLOR_ALPHA_SELECTED = 60;
    public final static int COLOR_BG_UNSELECTED = Color.WHITE;
    public final static int COLOR_ALPHA_UNSELECTED = 0;

    //Times in ms
    public final static int ONE_HOUR = 1000 * 60 * 60;
    public final static int TEN_MINUTES = 1000 * 60 * 10;
    public final static int FOURTY_FIVE_MINUTES = 1000 * 60 * 45;


}
