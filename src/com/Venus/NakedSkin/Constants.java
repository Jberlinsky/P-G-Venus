package com.Venus.NakedSkin;

import android.graphics.Color;
import android.text.Html;
import android.text.Spanned;

public class Constants {
	public final static String NAKED_SKIN = "Naked Skin ";
	
    public final static int SPLASH_TIMEOUT = 1000; //1 second

    //Tutorial copy
    public final static String TUTORIAL_POSITIVE = "Ok";
    public final static String TUTORIAL_NEGATIVE = "Retour";

    public final static Spanned TUTORIAL_MESSAGE =
            Html.fromHtml("Bienvenue chère déesse! </br>Votre voyage vers Naked Skin<sup><small>®</small></sup> commence maintenant. Cliquez sur \"Ok\" pour découvrir comment cette application va faciliter votre voyage. Vous devez cliquer sur chaque icone pour avancer et terminer le séminaire.");
    public final static String TUTORIAL_MESSAGE_2 = "You must click through each icon to proceed and finish the tutorial.";
    public final static Spanned TUTORIAL_SCHEDULE_MESSAGE =
        Html.fromHtml("Allez sur Voir Calendrier pour voir les traitements passés et à venir (entourés en bleu) ainsi que la date d'aujourd'hui (entourée en blanc). Cliquez sur n'importe quelle date entourée en bleu pour voir les détails de votre rendez-vous avec Naked Skin<sup><small>®</small></sup>.");
    public final static String TUTORIAL_TREATMENT_MESSAGE = "Considérez le Programmateur des traitements comme votre assistant personnel. " +
        "Sélectionnez la partie du corps que vous voulez rendre douce comme de la soie et la date à laquelle vous voulez démarrer votre traitement, et cela plannifiera automatiquement les traitements restants.";
    public final static Spanned TUTORIAL_HOWTO_MESSAGE = Html.fromHtml("Regardez nos courtes vidéos pour savoir comment Naked Skin<sup><small>®</small></sup> fonctionne.");
  //  public final static String TUTORIAL_PLAYLIST_MESSAGE = "CURRENTLY NOT IMPLEMENTED\n" +
  //      "Here, you'll be able to select a playlist to listen to while you treat yourself.";
    public final static String TUTORIAL_SETTINGS_MESSAGE = "Vous pouvez changer le mode de rappel des traitements sous Programmation des rappels. Vous êtes maintenant prête à commencer.";
    public final static String TUTORIAL_START_MESSAGE = "Dans la rubrique “Traitement du jour” vous pouvez facilement voir quels traitements sont prévus aujourd’hui.";
    public final static String TREATMENT_OPTION_MESSAGE = "Souhaitez-vous continuer la première phase de traitement et programmer un rappel pour traiter dans deux semaines, ou êtes-vous prête pour la phase de suivi et programmer un rappel dans deux mois?";

    public final static String TUTORIAL_CALENDAR_SELECT_TITLE = "Choisir un calendrier";
    public final static Spanned TUTORIAL_CALENDAR_SELECT_MESSAGE = Html.fromHtml( "Avant de commencer, vous devrez choisir un calendrier à utiliser avec Naked Skin<sup><small>®</small></sup>. Vous pouvez toujours le changer dans le menu." );
    public final static String TUTORIAL_CALENDAR_MISSING = "You don't have any calendars! This app won't work without a valid calendar on your device. Please double-check in the calendar app.";
    
    //Shared variable names
    public final static String TAB_NUMBER = "tabNumber";

    //Colors
    public final static int COLOR_SELECTED = Color.BLUE;
    public final static int COLOR_UNSELECTED = Color.WHITE;


    public final static int COLOR_BG_SELECTED = Color.argb(250, 0, 153, 203);
    public final static int COLOR_ALPHA_SELECTED = 150;
    public final static int COLOR_BG_UNSELECTED = Color.WHITE;
    public final static int COLOR_ALPHA_UNSELECTED = 0;

    //Times in ms
    public final static int ONE_HOUR = 1000 * 60 * 60;
    public final static int TEN_MINUTES = 1000 * 60 * 10;
    public final static int FOURTY_FIVE_MINUTES = 1000 * 60 * 45;
    public final static int TWO_HOURS = 1000 * 60 * 60 * 2;

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
    public final static String FIRST_PICK_DIALOG = "Merci de sélectionner la date à laquelle vous souhaitez commencer.";
    public final static String DATE_PICK_DIALOG = "Merci de sélectionner la date de votre traitement le plus récent.";
    public final static String TIME_PICK_DIALOG = "Merci de sélectionner l'heure de votre rappel.";
    public final static String MAINTENANCE = "Suivi";
    public final static String STARTUP = "Première phase de traitement";

    public final static String december = "Décembre";
    public final static String november = "Novembre";
    public final static String october = "Octobre";
    public final static String september = "Septembre";
    public final static String august = "Août";
    public final static String july = "Juillet";
    public final static String june = "Juin";
    public final static String may = "Mai";
    public final static String april = "Avril";
    public final static String march = "Mars";
    public final static String february = "Février";
    public final static String january = "Janvier";

    //Intent extras for TutorialActivity
    public final static String TUTORIAL_INTENT_EXTRA_FIRSTRUN = "first";
    
    public final static String REMINDER_FIRST = "Les rappels pour vos six premiers ";
    public final static String REMINDER_SECOND = " traitements ont été programmés automatiquement.";
    public final static String REMINDER_THIRD = "Le rappel du ";
    public final static String REMINDER_FOURTH = " nombre de traitements ";
    public final static String REMINDER_FIFTH = " a été programmé.";
    public final static String REMINDER_SIXTH = "Rappel(s) pour l'entretien de ";
    public final static String TREATMENT_REMINDER = " Rappel(s) pour le traitements";
    public final static String THISISTREATMENTREMINDER = "Nombre de Traitements ";
    public final static String YES = "Oui";
    public final static String NO = "Non";
    
    
    public final static String WHOLE = "Corps";
    public final static String UNDER = "Aisselles";
    public final static String LOWER = "Demi";
    public final static String UPPER = "Cuisse";
    public final static String BIKINI = "Maillot";
    
    public final static String COMPLETED = "Terminé!";
    public final static String DONE = " Fait";
    
    public final static String UN = "Ai";
    public final static String UNDERARM = "Aisselles";
    public final static String BI = "Ma";
    public final static String BIKINIAREA = "Maillot";
    public final static String UP = "Cu";
    public final static String UPPERLEG = "Cuisse";
    public final static String LO = "De";
    public final static String LOWERLEG = "Demi-jambe";
    public final static String WH = "Co";
    public final static String WHOLEBODY = "Corps entier";
    public final static String OTHER = "Autre";
    
}
