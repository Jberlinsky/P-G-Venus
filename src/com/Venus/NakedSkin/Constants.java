package com.Venus.NakedSkin;

import android.graphics.Color;

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

    //Content URIs for calendars
    public final static String URI_CALENDAR_ICS = "content://com.android.calendar/calendars";
    public final static String URI_CALENDAR_FROYO = "content://com.android.calendar/calendars";
    public final static String URI_CALENDAR_OLD = "content://calendar/calendars";

    //Display Name for calendars
    public final static String DISPLAY_NAME_ICS = "calendar_displayName";
    public final static String DISPLAY_NAME_FROYO = "displayName";

    //ID for calendars
    public final static String CALENDAR_ID = "_id";

    //Content URIs for events
    public final static String URI_EVENT_FROYO = "content://com.android.calendar/events";
    public final static String URI_EVENT_OLD = "content://calendar/events";

    //Colors
    public final static int COLOR_SELECTED = Color.BLUE;
    public final static int COLOR_UNSELECTED = Color.WHITE;

    public final static int COLOR_BG_SELECTED = Color.argb(250, 0, 153, 203);
    public final static int COLOR_ALPHA_SELECTED = 150;
    public final static int COLOR_BG_UNSELECTED = Color.WHITE;
    public final static int COLOR_ALPHA_UNSELECTED = 0;

    //Calendar index values
    public final static int PROJECTION_ID_INDEX = 0;
    public final static int PROJECTION_DISPLAY_NAME_INDEX = 1;

    //Event index values
    public final static int EVENT_TITLE_INDEX = 0;
    public final static int EVENT_DESC_INDEX = 1;
    public final static int EVENT_START_INDEX = 2;

}
