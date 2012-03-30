package com.Venus.NakedSkin;

import java.util.ArrayList;
import java.util.Calendar;

import Utility.Event;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class StartActivity extends ListActivity implements OnItemClickListener {

    ListView _listView;
    ArrayList<String> bodyParts = new ArrayList<String>();
    ArrayList<Integer> treatmentNumbers = new ArrayList<Integer>();
    ArrayList<Long> startTimes = new ArrayList<Long>();

    class RefreshHandler extends Handler {
        public void handleMessage( Message msg ) {
            Log.d( "Venus", "Handling a message, refreshing screen" );
            refresh();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music);
        _listView = (ListView) findViewById( android.R.id.list );
        _listView.setOnItemClickListener( this );
        refresh();
    }

    public void onItemClick( AdapterView<?> arg0, View arg1, final int arg2, long arg3 ) {
        String bodyPart = bodyParts.get( arg2 );
        Log.d( "Venus", bodyPart );
        if( bodyPart.contains( "Done" ) ) {
            return;
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder( this );
            builder.setMessage( "Are you treating " + bodyPart + " now?" )
                   .setCancelable( false )
                   .setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                       public void onClick( DialogInterface dialog, int id ) {
                           proceed( bodyParts.get( arg2 ), treatmentNumbers.get( arg2 ), startTimes.get( arg2 ) );
                       } } )
                   .setNegativeButton( "No", new DialogInterface.OnClickListener() {
                       public void onClick( DialogInterface dialog, int id ) {
                           dialog.cancel();
                       } } );
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void proceed( final CharSequence bodyPartTemp, Integer treatmentNumberTemp, final Long startTime ) { //only has to deal with one case.
        if( -1 == treatmentNumberTemp ) {
            Log.d( "Venus", "No action: in maintenance phase" );
        } else if( 12 == treatmentNumberTemp ) {
            scheduleMaintenance( bodyPartTemp, startTime );
            Log.d( "Venus", "Forcing maintenance reminders" );
        } else if( 6 <= treatmentNumberTemp ) {
            final int treatmentNumber = treatmentNumberTemp;
            Log.d( "Venus", "Dialog about maintenance or startup" );
            AlertDialog.Builder builder = new AlertDialog.Builder( this );
            builder.setMessage( Constants.TREATMENT_OPTION_MESSAGE )
                   .setCancelable( false )
                   .setPositiveButton( "Maintenance",
                                       new DialogInterface.OnClickListener() {
                                           public void onClick( DialogInterface dialog, int id ) {
                                               Log.d( "Venus", "User chose maintenance" );
                                               scheduleMaintenance( bodyPartTemp, startTime );
                                           }
                                       } )
                   .setNegativeButton( "Start Up",
                                       new DialogInterface.OnClickListener() {
                                           public void onClick( DialogInterface dialog, int id ) {
                                               Log.d( "Venus", "User chose startup" );
                                               scheduleOneStartUp( bodyPartTemp, treatmentNumber, startTime );
                                           }
                                       } );
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            Log.d( "Venus", "No action: in startup phase" );
        }
        //for all treatments, mark as complete
        Event e = new Event( bodyPartTemp, startTime );
        Utilities.markAsComplete( this, e, new RefreshHandler() );
    }

    private void refresh() {
        bodyParts = new ArrayList<String>();
        treatmentNumbers = new ArrayList<Integer>();
        startTimes = new ArrayList<Long>();
        Cursor eventCursor = Utilities.queryTodaysEvents( this );
        try {
            String desc = null;
            int treatmentNumberTemp;

            while( eventCursor.moveToNext() ) {
                startTimes.add( eventCursor.getLong( Constants.EVENT_START_INDEX ) );
                try { //try to get the treatment number (doesn't exist for maint)
                    desc = eventCursor.getString( Constants.EVENT_DESC_INDEX );
                    treatmentNumberTemp = Integer.parseInt( desc.substring( desc.length() - 2, desc.length() ).trim() );
                } catch( NumberFormatException nfe ) { //this exception means maintenance, set to -1
                    treatmentNumberTemp = -1;
                }
                treatmentNumbers.add( treatmentNumberTemp );
                if( desc.contains( "Completed!" ) ) {
                    bodyParts.add( getBodyPartString( eventCursor.getString( Constants.EVENT_TITLE_INDEX ).substring( 11 ) ) + " Done" );
                } else {
                    bodyParts.add( getBodyPartString( eventCursor.getString( Constants.EVENT_TITLE_INDEX ).substring( 11 ) ) );
                }
            }

            EventArrayAdapter adapter = new EventArrayAdapter( this, bodyParts, startTimes );
            setListAdapter( adapter );

        } catch( CursorIndexOutOfBoundsException cioobe ) {
            Log.d( "Venus", cioobe.getMessage() );
            //error happened...
        }
    }

    public void onBackPressed(){
        //This is to prevent user from accidently exiting the app
        //pressing Home will exit the app
    }

    private void scheduleMaintenance( CharSequence bodyPart, Long startTime ) {
        int treatmentDuration = getTreatmentLength( (String) bodyPart );
        Calendar _calendar = Calendar.getInstance();
        _calendar.setTimeInMillis( startTime );
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

    private void scheduleOneStartUp( CharSequence bodyPart, int treatmentNumber, Long startTime ) {
        int treatmentDuration = getTreatmentLength( (String) bodyPart );
        Calendar _calendar = Calendar.getInstance();
        _calendar.setTimeInMillis( startTime );
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
        } else if( subString.substring( 0, 2 ).equalsIgnoreCase( "Wh" ) ) {
            return "Whole Body";
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
