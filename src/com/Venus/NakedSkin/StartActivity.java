package com.Venus.NakedSkin;

import java.util.ArrayList;
import java.util.Calendar;

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
//import android.util.Log;
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
    int lastItemClicked = -1;
    EventArrayAdapter _adapter;

    /**
     * Handles the refreshing when something is clicked.
     * @author Jingran Wang
     *
     */
    class RefreshHandler extends Handler {
        public void handleMessage( Message msg ) {
            _adapter.update( lastItemClicked );
        }
    }

    /**
     * Lays out the view and populates the ArrayList
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        _listView = (ListView) findViewById( android.R.id.list );
        _listView.setOnItemClickListener( this );
        refresh();
    }

    /**
     * Handles clicks on the adapter view.
     * Sends the clicked item onto proceed()
     */
    public void onItemClick( AdapterView<?> arg0, View arg1, final int arg2, long arg3 ) {
        String bodyPart = bodyParts.get( arg2 );
        if( bodyPart.contains( getString( R.string.done ) ) ) {
            return;
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder( this );
            builder.setMessage( bodyPart + getString( R.string.question_mark ) )
                   .setCancelable( false )
                   .setPositiveButton( getString( R.string.yes ), new DialogInterface.OnClickListener() {
                       public void onClick( DialogInterface dialog, int id ) {
                           lastItemClicked = arg2;
                           proceed( bodyParts.get( arg2 ), treatmentNumbers.get( arg2 ), startTimes.get( arg2 ) );
                       } } )
                   .setNegativeButton( getString( R.string.no ), new DialogInterface.OnClickListener() {
                       public void onClick( DialogInterface dialog, int id ) {
                           dialog.cancel();
                       } } );
            builder.create().show();
        }
    }

    /**
     * Figures out if this is a "special case" and acts accordingly, and marks this event as complete
     * Maintenance phase: No special actions
     * Startup phase 12: Force into maintenance phase
     * Startup phase 6-11: Ask if user wants to go into maintenance phase
     * Startup phase 1-5: No special actions
     * @param bodyPart
     * @param treatmentNumber
     * @param startTime
     */
    private void proceed( final CharSequence bodyPart, final Integer treatmentNumber, final Long startTime ) {
        if( -1 == treatmentNumber ) {
            //Log.d( "Venus", "No action: in maintenance phase" );
        } else if( 12 == treatmentNumber ) {
            scheduleMaintenance( bodyPart, startTime );
            //Log.d( "Venus", "Forcing maintenance reminders" );
        } else if( 6 <= treatmentNumber ) {
            //Log.d( "Venus", "Dialog about maintenance or startup" );
            AlertDialog.Builder builder = new AlertDialog.Builder( this );
            builder.setMessage( getString( R.string.treatment_option_message) )
                   .setCancelable( false )
                   .setPositiveButton( getString( R.string.maintenance ),
                                       new DialogInterface.OnClickListener() {
                                           public void onClick( DialogInterface dialog, int id ) {
                                               scheduleMaintenance( bodyPart, startTime );
                                           }
                                       } )
                   .setNegativeButton( getString( R.string.startup ),
                                       new DialogInterface.OnClickListener() {
                                           public void onClick( DialogInterface dialog, int id ) {
                                               scheduleOneStartUp( bodyPart, treatmentNumber, startTime );
                                           }
                                       } );
            builder.create().show();
        } else {
            //Log.d( "Venus", "No action: in startup phase" );
        }
        //for all treatments, mark as complete
        Event e = new Event( bodyPart, startTime );
        Utilities.markAsComplete( this, e, new RefreshHandler() );
    }

    /**
     * Initializes the ArrayLists
     */
    private void refresh() {
        Cursor eventCursor = Utilities.queryTodaysEvents( this );
        try {
            String desc = null;
            int treatmentNumber;

            while( eventCursor.moveToNext() ) {
                startTimes.add( eventCursor.getLong( Constants.EVENT_START_INDEX ) );
                try { //try to get the treatment number (doesn't exist for maint)
                    desc = eventCursor.getString( Constants.EVENT_DESC_INDEX );
                    treatmentNumber = Integer.parseInt( desc.substring( desc.length() - 2, desc.length() ).trim() );
                } catch( NumberFormatException nfe ) { //this exception means maintenance, set to -1
                    treatmentNumber = -1;
                }
                treatmentNumbers.add( treatmentNumber );
                if( desc.contains( getString( R.string.completed ) ) ) {
                    bodyParts.add( Utilities.getBodyPartString( this, eventCursor.getString( Constants.EVENT_TITLE_INDEX ).substring( 11 ) ) + getString( R.string.done ) );
                } else {
                    bodyParts.add( Utilities.getBodyPartString( this, eventCursor.getString( Constants.EVENT_TITLE_INDEX ).substring( 11 ) ) );
                }
            }

            _adapter = new EventArrayAdapter( this, bodyParts, startTimes );
            setListAdapter( _adapter );
        } catch( CursorIndexOutOfBoundsException cioobe ) {
            //error happened...
        } catch( NullPointerException npe ) {
            //This happens when no calendar exists
            AlertDialog.Builder builder = new AlertDialog.Builder( this );
            builder.setMessage( getString( R.string.calendar_missing ) )
                   .setCancelable(false)
                   .setPositiveButton( getString( R.string.okay ), new DialogInterface.OnClickListener(){
                       public void onClick( DialogInterface dialog, int id ) {
                           dialog.cancel();
                       }
                   });
            builder.create().show();
        }
    }

    /**
     * Prevents user from accidentally exiting the app.
     * User needs to press "home"
     * Could be changed to "Do you want to exit?" dialog.
     */
    public void onBackPressed(){ }

    /**
     * Schedules six maintenance events
     * @param bodyPart
     * @param startTime
     */
    private void scheduleMaintenance( CharSequence bodyPart, Long startTime ) {
        int treatmentDuration = Utilities.getTreatmentLength( this, (String)bodyPart );
        Calendar _calendar = Calendar.getInstance();
        _calendar.setTimeInMillis( startTime );
        final Context ctx = this;
        for( int i = 0; i < 6; i++ ) {
            _calendar.add( Calendar.MONTH, 2 );
            final Event e = new Event( getString( R.string.event_title_prefix ) +
                                       bodyPart +
                                       getString( R.string.event_title_suffix ),
                                       getString( R.string.maintenance ),
                                       _calendar.getTimeInMillis(),
                                       _calendar.getTimeInMillis() + ( ( -1 == treatmentDuration ) ? Constants.ONE_HOUR : treatmentDuration ) );

            new Thread( new Runnable() {
                public void run() {
                    Utilities.addToCalendar( ctx, e );
                }
            } ).start();
        }

        Toast.makeText( this,
                        getString( R.string.reminder_maintenance_prefix ) +
                        bodyPart +
                        getString( R.string.reminder_maintenance_suffix ),
                        Toast.LENGTH_LONG ).show();
    }

    /**
     * Schedules a single startup event
     * @param bodyPart
     * @param treatmentNumber
     * @param startTime
     */
    private void scheduleOneStartUp( CharSequence bodyPart, Integer treatmentNumber, Long startTime ) {
        int treatmentDuration = Utilities.getTreatmentLength( this, (String)bodyPart );
        Calendar _calendar = Calendar.getInstance();
        _calendar.setTimeInMillis( startTime );
        final Context ctx = this;
        _calendar.add( Calendar.WEEK_OF_YEAR, 2 );
        final Event e = new Event( getString( R.string.event_title_prefix ) +
                                   bodyPart +
                                   getString( R.string.event_title_suffix ),
                                   getString( R.string.event_desc_startup_prefix ) +
                                   ( treatmentNumber + 1 ),
                                   _calendar.getTimeInMillis(),
                                   _calendar.getTimeInMillis() + ( ( -1 == treatmentDuration ) ? Constants.ONE_HOUR : treatmentDuration ) );

        new Thread( new Runnable() {
            public void run() {
                Utilities.addToCalendar( ctx, e );
            }
        } ).start();

        Toast.makeText( this,
                        getString( R.string.reminder_startup_prefix ) +
                        bodyPart +
                        getString( R.string.reminder_startup_middle ) +
                        ( treatmentNumber + 1 ) +
                        getString( R.string.reminder_startup_suffix ),
                        Toast.LENGTH_LONG ).show();
    }

    /**
     * Creates the menu
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.tabmenu, menu);
        return true;
    }

    /**
     * Handles menu selection
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch( item.getItemId() ) {
        case R.id.schedulemenu:
            startActivity( new Intent( this, ScheduleActivity.class ) );
            return true;
        case R.id.treatmentmenu:
            startActivity( new Intent( this, TreatmentActivity.class ) );
            finish();
            return true;
        case R.id.howtomenu:
            startActivity( new Intent( this, HowtoActivity.class ) );
            finish();
            return true;
        case R.id.settingmenu:
            startActivity( new Intent( this, SettingActivity.class ) );
            finish();
            return true;
        case R.id.homemenu:
            Intent intent =  new Intent( this, TutorialActivity.class );
            intent.putExtra( Constants.FIRST, false);
            startActivity(intent);
            finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

}
