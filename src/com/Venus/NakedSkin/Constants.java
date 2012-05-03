package com.Venus.NakedSkin;

import android.graphics.Color;
import android.text.Html;
import android.text.Spanned;

public class Constants {
    //MIME Types
    public final static String MIME_TYPE_CALENDAR = "vnd.android.cursor.dir/vnd.exina.android.calendar.date";

    //Intent Extras
    //Tutorial Activity
    public final static String FIRST = "first";
    public final static String HAS_CALENDARS = "cals";
    //Treatment Activity and Event View Activity
    public final static String CALENDAR_YEAR_EXTRA  = "year";
    public final static String CALENDAR_MONTH_EXTRA = "month";
    public final static String CALENDAR_DAY_EXTRA   = "day";
    //Diary Activity
    public final static String TAB_NUMBER_EXTRA = "tabNumber";

    //Times in ms
    public final static int TEN_MINUTES         = 1000 * 60 * 10;
    public final static int FIFTEEN_MINUTES     = 1000 * 60 * 15;
    public final static int THIRTY_MINUTES      = 1000 * 60 * 30;
    public final static int FOURTY_FIVE_MINUTES = 1000 * 60 * 45;
    public final static int ONE_HOUR            = 1000 * 60 * 60;
    public final static int TWO_HOURS           = 1000 * 60 * 60 * 2;

    //Preference Names
    public final static String FIRST_TREATMENT_BOOL_PREF    = "isFirstTreatmentReminder";
    public final static String MAINTENANCE_BOOL_PREF        = "hasSwitchedToMaintenence";
    public final static String UA_BIKINI_TREATMENT_INT_PREF = "underarmBikiniTreatmentLength";
    public final static String UL_LL_TREATMENT_INT_PREF     = "upperLowerLegTreatmentLength";
    public final static String WHOLE_TREATMENT_INT_PREF     = "wholeBodyTreatmentLength";







    public final static String NAKED_SKIN = "Naked Skin ";

    public final static int SPLASH_TIMEOUT = 1000; //1 second

    //Tutorial copy
    public final static String TUTORIAL_POSITIVE = "Ok";
    public final static String TUTORIAL_NEGATIVE = "Retour";




    //Colors
    public final static int COLOR_SELECTED = Color.BLUE;
    public final static int COLOR_UNSELECTED = Color.WHITE;


    public final static int COLOR_BG_SELECTED = Color.argb(250, 0, 153, 203);
    public final static int COLOR_ALPHA_SELECTED = 150;
    public final static int COLOR_BG_UNSELECTED = Color.WHITE;
    public final static int COLOR_ALPHA_UNSELECTED = 0;



    //Enum for alarms
    public final static int MINUTE = 0;
    public final static int HOUR = 1;
    public final static int DAY = 2;
    public final static int WEEK = 3;

    //Calendar index values
    public final static int PROJECTION_ID_INDEX = 0;
    public final static int PROJECTION_DISPLAY_NAME_INDEX = 1;

    //Event index values
    public final static int EVENT_TITLE_INDEX = 0;
    public final static int EVENT_DESC_INDEX = 1;
    public final static int EVENT_START_INDEX = 2;

    //ScheduleActivity copy
    public final static String FIRST_PICK_DIALOG = "Merci de s�lectionner la date � laquelle vous souhaitez commencer.";
    public final static String DATE_PICK_DIALOG = "Merci de s�lectionner la date de votre traitement le plus r�cent.";
    public final static String TIME_PICK_DIALOG = "Merci de s�lectionner l'heure de votre rappel.";





}
