package com.Venus.NakedSkin;

import java.util.Calendar;

import Utility.Event;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class StartActivity extends Activity {
    /**
     * @see android.app.Activity#onCreate(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Cursor eventCursor = Utilities.queryTodaysEvents( this );

        eventCursor.moveToNext();
        String desc = eventCursor.getString( Constants.EVENT_DESC_INDEX );
        int treatmentNumberTemp = -1;
        try {
            treatmentNumberTemp = Integer.parseInt( desc.substring( desc.length() - 2, desc.length() ) );
        } catch( NumberFormatException nfe ) {
            //maintenance phase, do nothing
        }
        final String bodyPartTemp = eventCursor.getString( Constants.EVENT_TITLE_INDEX ).substring( 11 );
        if( 12 == treatmentNumberTemp ) {
            scheduleMaintenance( bodyPartTemp );
            Log.d( "Venus", "Forcing maintenance reminders" );
        } else if( 6 <= treatmentNumberTemp ) {
            final int treatmentNumber = treatmentNumberTemp;
            Log.d( "Venus", "Dialog about maintenance or startup" );
            AlertDialog.Builder builder = new AlertDialog.Builder( this );
            builder.setMessage( Constants.TREATMENT_OPTION_MESSAGE )
                   .setPositiveButton( "Maintenance",
                                       new DialogInterface.OnClickListener() {
                                           public void onClick( DialogInterface dialog, int id ) {
                                               Log.d( "Venus", "User chose maintenance" );
                                               scheduleMaintenance( bodyPartTemp );
                                           }
                                       } )
                   .setNegativeButton( "Start Up",
                                       new DialogInterface.OnClickListener() {
                                           public void onClick( DialogInterface dialog, int id ) {
                                               Log.d( "Venus", "User chose startup" );
                                               scheduleOneStartUp( bodyPartTemp, treatmentNumber );
                                           }
                                       } );
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            Log.d( "Venus", "No action: in maintenance or startup phases" );
        }
    }

    private void scheduleMaintenance( String desc ) {
        String bodyPart = getBodyPartString( desc );
        int treatmentDuration = getTreatmentLength( desc );
        Calendar _calendar = Calendar.getInstance();
        final Context ctx = this;
        for( int i = 0; i < 6; i++ ) {
            _calendar.add( Calendar.MONTH, 2 );
            final Event e = new Event( "Naked Skin " + bodyPart + " treatment reminder",
                                       "Maintenance phase",
                                       _calendar.getTimeInMillis(),
                                       _calendar.getTimeInMillis() + ( ( -1 == treatmentDuration ) ? Constants.ONE_HOUR : treatmentDuration ) );

            new Thread( new Runnable() {
                public void run() {
                    Utilities.addToCalendar( ctx, e );
                }
            } ).start();
        }
        Toast.makeText( this,
                        "Maintenance reminders for " + bodyPart + " have been set.",
                        Toast.LENGTH_LONG ).show();
    }

    private void scheduleOneStartUp( String bodyPart, int treatmentNumber ) {
        bodyPart = getBodyPartString( bodyPart );
        int treatmentDuration = getTreatmentLength( bodyPart );
        Calendar _calendar = Calendar.getInstance();
        final Context ctx = this;
        _calendar.add( Calendar.WEEK_OF_YEAR, 2 );
        final Event e = new Event( "Naked Skin " + bodyPart + " treatment reminder",
                                   "This is treatment number ",
                                   _calendar.getTimeInMillis(),
                                   _calendar.getTimeInMillis() + ( ( -1 == treatmentDuration ) ? Constants.ONE_HOUR : treatmentDuration ) );

        new Thread( new Runnable() {
            public void run() {
                Utilities.addToCalendar( ctx, e );
            }
        } ).start();
        Toast.makeText( this,
                        "Reminder for " + bodyPart + " treatment number " + ( treatmentNumber + 1 ) + " have been set.",
                        Toast.LENGTH_LONG ).show();

    }

    private String getBodyPartString( String subString ) {
        if( subString.substring( 0, 2 ).equalsIgnoreCase( "Un" ) ) {
            return "Underarm";
        } else if( subString.substring( 0, 2 ).equalsIgnoreCase( "Bi" ) ) {
            return "Bikini Area";
        } else if( subString.substring( 0, 2 ).equalsIgnoreCase( "Up" ) ) {
            return "Upper Leg";
        } else if( subString.substring( 0, 2 ).equalsIgnoreCase( "Lo" ) ) {
            return "Lower Leg";
        }
        return null;
    }

    private int getTreatmentLength( String bodyPart ) {
        VenusDb vdb = new VenusDb( this );
        if( bodyPart.substring( 0, 2 ).equalsIgnoreCase( "Un" ) ) {
            return vdb.getUnderarmBikiniTreatmentLength( this );
        } else if( bodyPart.substring( 0, 2 ).equalsIgnoreCase( "Bi" ) ) {
            return vdb.getUnderarmBikiniTreatmentLength( this );
        } else if( bodyPart.substring( 0, 2 ).equalsIgnoreCase( "Up" ) ) {
            return vdb.getUpperLowerLegTreatmentLength( this );
        } else if( bodyPart.substring( 0, 2 ).equalsIgnoreCase( "Lo" ) ) {
            return vdb.getUpperLowerLegTreatmentLength( this );
        }
        return -1;
    }
}
