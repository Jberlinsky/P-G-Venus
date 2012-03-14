package com.Venus.NakedSkin;

import java.util.ArrayList;
import java.util.Calendar;

import Utility.Event;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class StartActivity extends Activity {
    /**
     * @see android.app.Activity#onCreate(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Cursor eventCursor = Utilities.queryTodaysEvents( this );
        try { //calls proceed() with either only 1 choice, or after dialog
            String desc = null;
            int treatmentNumberTemp;
            if( 1 < eventCursor.getCount() ) { //more than one event today.
                ArrayList<CharSequence> bodyParts = new ArrayList<CharSequence>(); //store body parts here, show these to user
                ArrayList<Integer> treatmentNumbers = new ArrayList<Integer>(); //store (potential) treatment numbers here, keep internal
                while( eventCursor.moveToNext() ) {
                    bodyParts.add( getBodyPartString( eventCursor.getString( Constants.EVENT_TITLE_INDEX ).substring( 11 ) ) ); //getting the body parts
                    try { //try to get the treatment number (doesn't exist for maint)
                        desc = eventCursor.getString( Constants.EVENT_DESC_INDEX );
                        treatmentNumberTemp = Integer.parseInt( desc.substring( desc.length() - 2, desc.length() ).trim() );
                    } catch( NumberFormatException nfe ) { //this exception means maintenance, set to -1
                        treatmentNumberTemp = -1;
                    }
                    treatmentNumbers.add( treatmentNumberTemp );
                }
                final CharSequence[] bodyPartArray = bodyParts.toArray( new CharSequence[ bodyParts.size() ] );
                final Integer[] treatmentNumberArray = treatmentNumbers.toArray( new Integer[ treatmentNumbers.size() ] );
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle( "Which body part are we treating right now?" );
                builder.setItems(bodyPartArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        proceed( bodyPartArray[ item ], treatmentNumberArray[ item ] );
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            } else if( 1 == eventCursor.getCount() ) {
                eventCursor.moveToNext();
                CharSequence bodyPart = getBodyPartString( eventCursor.getString( Constants.EVENT_TITLE_INDEX ).substring( 11 ) );
                try { //try to get the treatment number (doesn't exist for maint)
                    desc = eventCursor.getString( Constants.EVENT_DESC_INDEX );
                    treatmentNumberTemp = Integer.parseInt( desc.substring( desc.length() - 2, desc.length() ).trim() );
                } catch( NumberFormatException nfe ) { //this exception means maintenance, set to -1
                    treatmentNumberTemp = -1;
                }
                proceed( bodyPart, new Integer( treatmentNumberTemp ) );
            } else {
                Log.d( "Venus", Integer.toString( eventCursor.getCount() ) );
                Log.d( "Venus", "No treatments scheduled today" );
                return;
            }
        } catch( CursorIndexOutOfBoundsException cioobe ) {
            Log.d( "Venus", cioobe.getMessage() );
            //error happened...
        }
    }

    private void proceed( final CharSequence bodyPartTemp, Integer treatmentNumberTemp ) { //only has to deal with one case.
        if( -1 == treatmentNumberTemp ) {
            Log.d( "Venus", "No action: in maintenance phase" );
            return;
        } else if( 12 == treatmentNumberTemp ) {
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
            Log.d( "Venus", "No action: in startup phase" );
        }

    }

    private void scheduleMaintenance( CharSequence bodyPart ) {
        int treatmentDuration = getTreatmentLength( (String) bodyPart );
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

    private void scheduleOneStartUp( CharSequence bodyPart, int treatmentNumber ) {
        int treatmentDuration = getTreatmentLength( (String) bodyPart );
        Calendar _calendar = Calendar.getInstance();
        final Context ctx = this;
        _calendar.add( Calendar.WEEK_OF_YEAR, 2 );
        final Event e = new Event( "Naked Skin " + bodyPart + " treatment reminder",
                                   "This is treatment number " + ( treatmentNumber + 1 ),
                                   _calendar.getTimeInMillis(),
                                   _calendar.getTimeInMillis() + ( ( -1 == treatmentDuration ) ? Constants.ONE_HOUR : treatmentDuration ) );

        new Thread( new Runnable() {
            public void run() {
                Utilities.addToCalendar( ctx, e );
            }
        } ).start();
        Toast.makeText( this,
                        "Reminder for " + bodyPart + " treatment number " + ( treatmentNumber + 1 ) + " has been set.",
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
        int treatmentLength = -1;
        if( bodyPart.substring( 0, 2 ).equalsIgnoreCase( "Un" ) ) {
            treatmentLength = vdb.getUnderarmBikiniTreatmentLength( this );
        } else if( bodyPart.substring( 0, 2 ).equalsIgnoreCase( "Bi" ) ) {
            treatmentLength = vdb.getUnderarmBikiniTreatmentLength( this );
        } else if( bodyPart.substring( 0, 2 ).equalsIgnoreCase( "Up" ) ) {
            treatmentLength = vdb.getUpperLowerLegTreatmentLength( this );
        } else if( bodyPart.substring( 0, 2 ).equalsIgnoreCase( "Lo" ) ) {
            treatmentLength = vdb.getUpperLowerLegTreatmentLength( this );
        }
        vdb.close();
        return treatmentLength;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.tabmenu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.schedulemenu:
            startActivity( new Intent( getApplicationContext(), ScheduleActivity.class ) );
            return true;
        case R.id.treatmentmenu:
            startActivity( new Intent( getApplicationContext(), TreatmentActivity.class ) );
            finish();
            return true;
        case R.id.howtomenu:
            startActivity( new Intent( getApplicationContext(), HowtoActivity.class ) );
            finish();
            return true;
        case R.id.settingmenu:
            startActivity( new Intent( getApplicationContext(), SettingActivity.class ) );
            finish();
            return true;
        case R.id.homemenu:
            Intent intent =  new Intent( getApplicationContext(), TutorialActivity.class );
            intent.putExtra("first", false);
            startActivity(intent);
            finish();
            return true;

        default:
            return super.onOptionsItemSelected(item);
        }
    }

}
