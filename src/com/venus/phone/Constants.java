package com.venus.phone;

import android.graphics.Color;

public class Constants {

    public final static int SPLASH_TIMEOUT = 1000; //1 second

    //Tutorial copy
    public final static String TUTORIAL_POSITIVE = "Okay";
    public final static String TUTORIAL_NEGATIVE = "Go back";
    public final static String TUTORIAL_MESSAGE = "Hello, and welcome to Venus Naked Skin!\n" +
        "This seems to be your first time running this app, so let's get started with a tutorial. " +
        "This tutorial will help you get comfortable with the device.";
    public final static String TUTORIAL_SCHEDULE_MESSAGE = "In the Schedule view, you can log your treatments. " +
        "Just choose a body part, fill in some details, and we'll help you set a reminder for the next time you need to treat that body part.";
    public final static String TUTORIAL_TREATMENT_MESSAGE = "In the Treatment view, you can look at upcoming treatment reminders.";
    public final static String TUTORIAL_HOWTO_MESSAGE = "Here, you can access How To videos, available at the Venus Naked Skin website.";
    public final static String TUTORIAL_PLAYLIST_MESSAGE = "CURRENTLY NOT IMPLEMENTED\n" +
        "Here, you'll be able to select a playlist to listen to while you treat yourself.";
    public final static String TUTORIAL_SETTINGS_MESSAGE = "You can change your settings in this view.";
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

}
