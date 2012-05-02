package com.Venus.NakedSkin;

import java.util.Calendar;

import Utility.Event;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class ScheduleActivity extends Activity implements OnClickListener {

    private boolean isStartup;
    private String startupNumber;
    private int treatmentDuration = -1;
    private boolean isFirstTreatment;
    private boolean wholebodySelected = false;

    private Calendar _calendar;
    private VenusDb vdb;
    private Spinner sessionSpinner;

    private Button wb;
    private Button proceedButton;
    private SelectionButton ua;
    private SelectionButton ba;
    private SelectionButton ul;
    private SelectionButton ll;

    private static final int DATE_DIALOG_ID = 101;
    private static final int TIME_DIALOG_ID = 102;
    private static final int FIRST_DIALOG_ID = 103;

    /**
     * Sets everything up correctly for this Activity.
     * Weird visual fix necessary.
     */
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.schedule );
        _calendar = Calendar.getInstance();
        vdb = new VenusDb( this );

        //Visual Fix
        proceedButton = (Button)findViewById( R.id.scheduleProceed );
        int buttonHeight = proceedButton.getHeight();
        proceedButton.layout( buttonHeight * 2, buttonHeight, 3, 0 );
        proceedButton.setWidth( buttonHeight * 2 );
        setListeners();
    }

    /**
     * Sets the OnClickListeners for all of the buttons
     */
    private void setListeners() {
        ba = (SelectionButton)findViewById( R.id.bikiniarea );
        ba.setOnClickListener( this );
        ua = (SelectionButton)findViewById( R.id.underarm );
        ua.setOnClickListener( this );
        ul = (SelectionButton)findViewById( R.id.upperleg );
        ul.setOnClickListener( this );
        ll = (SelectionButton)findViewById( R.id.lowerleg );
        ll.setOnClickListener( this );
        wb = (Button)findViewById( R.id.wholebutton );
        wb.setOnClickListener( this );

        proceedButton.setOnClickListener( this );
    }

    /**
     * Prepopulates the duration of a treatment
     * @param duration Duration of treatment
     */
    private void prepopulateMinutes( int duration ) {
        treatmentDuration = duration;
    }

    /**
     * Creates the menu
     */
    public boolean onCreateOptionsMenu( Menu menu ) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.layout.tabmenu, menu );
        return true;
    }

    /**
     * Handles menu clicks
     */
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch( item.getItemId() ) {
        case R.id.schedulemenu:
            setScheduleContent();
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
            startActivity( new Intent( this, TutorialActivity.class ).putExtra( Constants.FIRST, false ) );
            finish();
            return true;
        default:
            return super.onOptionsItemSelected( item );
        }
    }

    /**
     * Ensures we close the DB
     */
    public void onDestroy() {
        vdb.close();
        super.onDestroy();
    }

    /**
     * Prevents user from accidentally exiting the app.
     * User needs to press "home"
     * Could be changed to "Do you want to exit?" dialog.
     * This one also resets the view to the first page.
     */
    public void onBackPressed(){
        setScheduleContent();
    }

    /**
     * This checks for the status of treatment / maintenance phases, and proceeds accordingly
     */
    public void checkStartupMaintenenceAndProceed() {
        final Context ctx = this;
        //If this is after the first set of treatments, but we have not switched to the maintenance phase yet, warn
        if( Integer.parseInt( startupNumber ) >= 6 &&
            (isStartup && !vdb.isFirstTreatmentReminder()) ) {
            // Prompt with option of switching to maintenance
            AlertDialog.Builder builder = new AlertDialog.Builder( this );
            builder.setMessage( getString( R.string.treatment_option_message ) )
                   .setPositiveButton( getString( R.string.maintenance ), new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                           // Change to maintenance
                           vdb.setIsNotFirstTreatmentReminder( ctx );
                           isStartup = false;
                           setUpEvent();
                       } } )
                   .setNegativeButton( getString( R.string.startup ), new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                           setUpEvent();
                       } } );
            builder.create().show();
        } else {
            setUpEvent();
        }
    }

    /**
     * Handles clicks of the body parts and other clicks
     */
    public void onClick( View v ) {
        switch( v.getId() ) {
        case R.id.underarm:
            if( !wholebodySelected ) {
                if( ua.isSelected ) {
                    ua.setUnselected();
                } else {
                    ua.setSelectedCustom();
                    ba.setUnselected();
                    ul.setUnselected();
                    ll.setUnselected();
                }
                prepopulateMinutes( vdb.getUnderarmBikiniTreatmentLength() );
            }
            break;
        case R.id.bikiniarea:
            if( !wholebodySelected ) {
                if( ba.isSelected ) {
                    ba.setUnselected();
                } else {
                    ba.setSelectedCustom();
                    ua.setUnselected();
                    ul.setUnselected();
                    ll.setUnselected();
                }
                prepopulateMinutes( vdb.getUnderarmBikiniTreatmentLength() );
            }
            break;
        case R.id.upperleg:
            if( !wholebodySelected ) {
                if( ul.isSelected ) {
                    ul.setUnselected();
                } else {
                    ul.setSelectedCustom();
                    ba.setUnselected();
                    ua.setUnselected();
                    ll.setUnselected();
                }
                prepopulateMinutes( vdb.getUpperLowerLegTreatmentLength() );
            }
            break;
        case R.id.lowerleg:
            if( !wholebodySelected ) {
                if( ll.isSelected ) {
                    ll.setUnselected();
                } else {
                    ll.setSelectedCustom();
                    ba.setUnselected();
                    ua.setUnselected();
                    ul.setUnselected();
                }
                prepopulateMinutes( vdb.getUpperLowerLegTreatmentLength() );
            }
            break;
        case R.id.wholebutton:
            if( wholebodySelected ) {
                ll.setUnselected();
                ba.setUnselected();
                ua.setUnselected();
                ul.setUnselected();
                wholebodySelected = false;
            } else {
                ll.setSelectedCustom();
                ba.setSelectedCustom();
                ua.setSelectedCustom();
                ul.setSelectedCustom();
                wholebodySelected = true;
            }
            prepopulateMinutes( vdb.getWholeBodyTreatmentLength() );
            break;
        case R.id.startupButton:
            isStartup = true;
            //Visual//
            findViewById( R.id.startupButton ).setBackgroundColor( Color.WHITE );
            findViewById( R.id.maintenanceButton ).setBackgroundColor( Color.BLACK );
            findViewById( R.id.sessionSpinner ).setVisibility( View.VISIBLE );
            findViewById( R.id.sessionText ).setVisibility( View.VISIBLE );
            /////////
            break;
        case R.id.maintenanceButton:
            isStartup = false;
            //Visual//
            findViewById( R.id.startupButton ).setBackgroundColor( Color.BLACK );
            findViewById( R.id.maintenanceButton ).setBackgroundColor( Color.WHITE );
            findViewById( R.id.sessionSpinner ).setVisibility( View.INVISIBLE );
            findViewById( R.id.sessionText ).setVisibility( View.INVISIBLE );
            break;
        case R.id.scheduleProceed:
            setContentView( R.layout.phase );
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( this,
                                                                                  R.array.sessions_array,
                                                                                  android.R.layout.simple_spinner_item );
            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
            sessionSpinner = (Spinner)findViewById( R.id.sessionSpinner );
            sessionSpinner.setAdapter(adapter);
            sessionSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                public void onItemSelected( AdapterView<?> parent, View arg1, int pos, long id ) {
                    startupNumber = parent.getItemAtPosition(pos).toString();
                }
                public void onNothingSelected( AdapterView<?> arg0 ) { }
            } );

            //Visual and listener//
            sessionSpinner.setVisibility( View.INVISIBLE );
            findViewById(R.id.sessionText).setVisibility( View.INVISIBLE );
            findViewById(R.id.startupButton).setOnClickListener( this );
            findViewById(R.id.maintenanceButton).setOnClickListener( this );
            findViewById(R.id.phaseProceed).setOnClickListener( this );
            findViewById(R.id.phaseBack).setOnClickListener( this );
            break;
        case R.id.phaseBack:
            setScheduleContent();
            break;
        case R.id.phaseProceed:
            if( 0 == Integer.parseInt( startupNumber ) && isStartup ) {
                isFirstTreatment = true;
                showDialog( FIRST_DIALOG_ID );
            } else {
                showDialog( DATE_DIALOG_ID );
            }
            break;
        default:
            break;
        }
    }

    /**
     * Overrides onCreateDialog so we can modify the date picker dialog
     */
    protected Dialog onCreateDialog( int id ) {
        switch( id ) {
        case FIRST_DIALOG_ID:
            DatePickerDialog fpd = new DatePickerDialog( this,
                                                         new OnDateSetListener() {
                                                             public void onDateSet( DatePicker view,
                                                                                    int year,
                                                                                    int monthOfYear,
                                                                                    int dayOfMonth ) {
                                                                 _calendar.set( year, monthOfYear, dayOfMonth );
                                                                 showDialog( TIME_DIALOG_ID );
                                                             } },
                                                         _calendar.get( Calendar.YEAR ),
                                                         _calendar.get( Calendar.MONTH ),
                                                         _calendar.get( Calendar.DATE ) );
            fpd.setTitle( Constants.FIRST_PICK_DIALOG );
            return fpd;
        case DATE_DIALOG_ID:
            DatePickerDialog dpd = new DatePickerDialog( this,
                                                         new OnDateSetListener() {
                                                             public void onDateSet( DatePicker view,
                                                                                    int year,
                                                                                    int monthOfYear,
                                                                                    int dayOfMonth ) {
                                                                 _calendar.set( year, monthOfYear, dayOfMonth );
                                                                 showDialog( TIME_DIALOG_ID );
                                                             } },
                                                         _calendar.get( Calendar.YEAR ),
                                                         _calendar.get( Calendar.MONTH ),
                                                         _calendar.get( Calendar.DATE ) );
            dpd.setTitle( Constants.DATE_PICK_DIALOG );
            return dpd;
        case TIME_DIALOG_ID:
            TimePickerDialog tpd = new TimePickerDialog( this,
                                                         new OnTimeSetListener() {
                                                             public void onTimeSet( TimePicker view,
                                                                                    int hourOfDay,
                                                                                    int minute ) {
                                                                 _calendar.set( Calendar.HOUR_OF_DAY, hourOfDay );
                                                                 _calendar.set( Calendar.MINUTE, minute );
                                                                 checkStartupMaintenenceAndProceed();
                                                             } },
                                                             _calendar.get( Calendar.HOUR_OF_DAY ),
                                                             _calendar.get( Calendar.MINUTE ),
                                                             false );
            tpd.setTitle( Constants.TIME_PICK_DIALOG );
            return tpd;
        default:
            return null;
        }
    }

    /**
     * Sets up the Event to schedule, and adds it to the Calendar
     */
    private void setUpEvent() {
        String bodyPart = null;
        int modifier;
        final Context ctx = this;
        if( wholebodySelected ) {
            bodyPart = getString( R.string.wholebody );
        } else {
            if( ba.isSelected ) {
                bodyPart = getString( R.string.bikini_area );
            } else if( ua.isSelected ) {
                bodyPart = getString( R.string.underarm );
            } else if( ul.isSelected ) {
                bodyPart = getString( R.string.upper_leg );
            } else if( ll.isSelected ) {
                bodyPart = getString( R.string.lower_leg );
            }
        }

        //i tells us how many new events to add.
        //It can add up to 5 new events (i == 0)
        for( int i = ( isFirstTreatment || !isStartup ) ?
                         0 :
                         Integer.parseInt( startupNumber ) > 5 ?
                             5 :
                             Integer.parseInt( startupNumber );
             i < 6;
             i++ ) {
            String desc = null;
            if( isStartup ) {
                desc =  getString( R.string.event_desc_startup_prefix );
                desc += (i + 1);
                desc += getString( R.string.event_desc_startup_suffix );
                modifier = Calendar.WEEK_OF_YEAR;
            } else {
                desc = getString( R.string.maintenance_phase );
                modifier = Calendar.MONTH;
            }
            _calendar.add( modifier, 2 );

            final Event e = new Event( getString( R.string.event_title_prefix ) +
                                       bodyPart +
                                       getString( R.string.event_title_suffix ),
                                       desc,
                                       _calendar.getTimeInMillis(),
                                       _calendar.getTimeInMillis() + ( ( -1 == treatmentDuration ) ? Constants.ONE_HOUR : treatmentDuration ) );
            new Thread( new Runnable() {
                public void run() {
                    Utilities.addToCalendar( ctx, e );
                }
            } ).start();
        } //end for

        String toastText = "";
        if( isStartup && isFirstTreatment ) {
            toastText = getString( R.string.reminder_first_startup_prefix ) +
                        bodyPart +
                        getString( R.string.reminder_first_startup_suffix );
        } else if( isStartup ) {
            toastText = getString( R.string.reminder_startup_prefix ) +
                        bodyPart +
                        getString( R.string.reminder_startup_middle ) +
                        startupNumber +
                        getString( R.string.reminder_startup_suffix );
        } else {
            toastText = getString( R.string.reminder_maintenance_prefix ) +
                        bodyPart +
                        getString( R.string.reminder_maintenance_suffix );
        }
        Toast.makeText( this, toastText, Toast.LENGTH_LONG ).show();

        setScheduleContent();
    }

    /**
     * Makes the view the schedule view, and resets everything.
     */
    private void setScheduleContent(){
        setContentView(R.layout.schedule);
        //Visual and listener//
        _calendar = Calendar.getInstance();
        isStartup = false;
        isFirstTreatment = false;
        setListeners();
        ba.setUnselected();
        ua.setUnselected();
        ul.setUnselected();
        ll.setUnselected();
    }

}
