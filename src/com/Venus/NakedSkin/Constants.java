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

    public final static Spanned TUTORIAL_MESSAGE =
            Html.fromHtml("Bienvenue ch�re d�esse! </br>Votre voyage vers Naked Skin<sup><small>�</small></sup> commence maintenant. Cliquez sur \"Ok\" pour d�couvrir comment cette application va faciliter votre voyage. Vous devez cliquer sur chaque icone pour avancer et terminer le s�minaire.");
    public final static String TUTORIAL_MESSAGE_2 = "You must click through each icon to proceed and finish the tutorial.";
    public final static Spanned TUTORIAL_SCHEDULE_MESSAGE =
        Html.fromHtml("Allez sur Voir Calendrier pour voir les traitements pass�s et � venir (entour�s en bleu) ainsi que la date d'aujourd'hui (entour�e en blanc). Cliquez sur n'importe quelle date entour�e en bleu pour voir les d�tails de votre rendez-vous avec Naked Skin<sup><small>�</small></sup>.");
    public final static String TUTORIAL_TREATMENT_MESSAGE = "Consid�rez le Programmateur des traitements comme votre assistant personnel. " +
        "S�lectionnez la partie du corps que vous voulez rendre douce comme de la soie et la date � laquelle vous voulez d�marrer votre traitement, et cela plannifiera automatiquement les traitements restants.";
    public final static Spanned TUTORIAL_HOWTO_MESSAGE = Html.fromHtml("Regardez nos courtes vid�os pour savoir comment Naked Skin<sup><small>�</small></sup> fonctionne.");
  //  public final static String TUTORIAL_PLAYLIST_MESSAGE = "CURRENTLY NOT IMPLEMENTED\n" +
  //      "Here, you'll be able to select a playlist to listen to while you treat yourself.";
    public final static String TUTORIAL_SETTINGS_MESSAGE = "Vous pouvez changer le mode de rappel des traitements sous Programmation des rappels. Vous �tes maintenant pr�te � commencer.";
    public final static String TUTORIAL_START_MESSAGE = "Dans la rubrique �Traitement du jour� vous pouvez facilement voir quels traitements sont pr�vus aujourd�hui.";


    public final static String TUTORIAL_CALENDAR_SELECT_TITLE = "Choisir un calendrier";
    public final static Spanned TUTORIAL_CALENDAR_SELECT_MESSAGE = Html.fromHtml( "Avant de commencer, vous devrez choisir un calendrier � utiliser avec Naked Skin<sup><small>�</small></sup>. Vous pouvez toujours le changer dans le menu." );
    public final static String TUTORIAL_CALENDAR_MISSING = "You don't have any calendars! This app won't work without a valid calendar on your device. Please double-check in the calendar app.";

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

    public final static String TREATMENT_REMINDER = " Rappel(s) pour le traitements";
    public final static String THISISTREATMENTREMINDER = "Nombre de Traitements ";
    public final static String YES = "Oui";
    public final static String NO = "Non";





}
