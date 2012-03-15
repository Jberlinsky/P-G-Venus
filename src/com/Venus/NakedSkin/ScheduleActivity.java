package com.Venus.NakedSkin;

import java.util.Calendar;

import Utility.Event;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

public class ScheduleActivity extends Activity implements OnClickListener{
    Spinner sessionSpinner;
    private boolean isStartup;
    private String startupNumber;
    private int treatmentDuration = -1;
    private boolean isFirstTreatment;
    private Calendar _calendar;

    private static final int DATE_DIALOG_ID = 101;

    SelectionButton ba;
    SelectionButton ua;
    SelectionButton ul;
    SelectionButton ll;

    VenusDb vdb;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);
        _calendar = Calendar.getInstance();
        vdb = new VenusDb( this );

        /* Button names
         *  underarmleft
            underarmright
            forearmleft
            forearmright
            bikiniarea
            upperlegleft
            upperlegright
            lowerlegleft
            lowerlegright
            scheduleproceed
            phaseProceed
            phaseBack
            setReminderBack
            startupButton
            maintenanceButton
            saveReminder

         */

        /*Edit TExt
         * setReminderEmail
         */

        /*Spinner
         * alarmSoundSpinner
         * daysBeforeSpinner
         */

        /* Checkbox
         * emailCheckBox
         * alertCheckBox
         */

        //Visual Fix
        Button proceedButton = (Button)findViewById(R.id.scheduleProceed);
        int buttonHeight = proceedButton.getHeight();
        proceedButton.layout(buttonHeight*2, buttonHeight, 3, 0);
        proceedButton.setWidth(buttonHeight*2);
        this.setListeners();
    }

    private void setListeners() {
        ba = (SelectionButton)findViewById(R.id.bikiniarea);
        ba.setOnClickListener(this);
        ua = (SelectionButton)findViewById(R.id.underarm);
        ua.setOnClickListener(this);
        ul = (SelectionButton)findViewById(R.id.upperleg);
        ul.setOnClickListener(this);
        ll = (SelectionButton)findViewById(R.id.lowerleg);
        ll.setOnClickListener(this);

        findViewById(R.id.scheduleProceed).setOnClickListener(this);
    }

    private void prepopulateMinutes(int duration)
    {
      // Prepopuate the length of a treatment
      this.treatmentDuration = duration;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.tabmenu, menu);
        return true;
    }

    public void onBackPressed(){
        //This is to prevent user from accidently exiting the app
        //pressing Home will exit the app
        setScheduleContent();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.schedulemenu:
            setScheduleContent();
            //startActivity( new Intent( getApplicationContext(), ScheduleActivity.class ) );
            return true;
        case R.id.treatmentmenu:
            startActivity( new Intent( getApplicationContext(), TreatmentActivity.class ) );
            vdb.close();
            finish();
            return true;
        case R.id.howtomenu:
            startActivity( new Intent( getApplicationContext(), HowtoActivity.class ) );
            vdb.close();
            finish();
            return true;
        case R.id.settingmenu:
            startActivity( new Intent( getApplicationContext(), SettingActivity.class ) );
            vdb.close();
            finish();
            return true;
        case R.id.homemenu:
            Intent intent =  new Intent( getApplicationContext(), TutorialActivity.class );
            intent.putExtra("first", false);
            startActivity(intent);
            vdb.close();
            finish();
            return true;

        default:
            return super.onOptionsItemSelected(item);
        }
    }

    public void checkStartupMaintenenceAndProceed()
    {
        final Context ctx = this;
      // If this is after the first set of treatments, but we have not switched to the maintenence phase yet, warn
            if (Integer.parseInt(startupNumber) >= 6 || (isStartup && !vdb.isFirstTreatmentReminder( this )) )
            {
//              final ScheduleActivity self = this;
              // Prompt with option of switching to maintenence
              AlertDialog.Builder builder = new AlertDialog.Builder(this);
              builder.setMessage(
                  Constants.TREATMENT_OPTION_MESSAGE
              ).setPositiveButton("Maintenance", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                  // Change to maintenence
                    vdb.setIsNotFirstTreatmentReminder( ctx );
                    isStartup = false;
                    setUpEvent();
                }
              }).setNegativeButton("Start Up", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                  setUpEvent();
                }
              });
              AlertDialog alert = builder.create();
              alert.show();
            }
            else {
              setUpEvent();
            }
    }
/*
    public void proceed(boolean isStartup)
    {
      //Visual and listener//
            findViewById(R.id.saveReminder).setOnClickListener(this);
            findViewById(R.id.setReminderBack).setOnClickListener(this);
            String bodyPart = null;
            if( ba.isSelected ) {
                bodyPart = getString( R.string.bikini_area );
            } else if( ua.isSelected ) {
                bodyPart = getString( R.string.underarm );
            } else if( ul.isSelected ) {
                bodyPart = getString( R.string.upper_leg );
            } else if( ll.isSelected ) {
                bodyPart = getString( R.string.lower_leg );
            }
            else
                bodyPart = "Other";
            //Is this start up or maintenance?
            String desc = null;
            int modifier = 0;
            if( isStartup ) {
                desc = "This is treatment number " + startupNumber;
                modifier = Calendar.WEEK_OF_YEAR;
            } else {
                desc = "Maintenance phase";
                modifier = Calendar.MONTH;
            }
            _calendar.add( modifier, 2 );
            Intent calendarIntent = new Intent( Intent.ACTION_INSERT );
            calendarIntent.setType( "vnd.android.cursor.item/event" );
            calendarIntent.putExtra( "title", "Naked Skin " + bodyPart + " treatment reminder" );
            calendarIntent.putExtra( "description", desc );
            calendarIntent.putExtra( "beginTime", _calendar.getTimeInMillis() );
            if (this.treatmentDuration != -1)
              calendarIntent.putExtra("endTime", _calendar.getTimeInMillis() + this.treatmentDuration);
            else
              calendarIntent.putExtra( "endTime", _calendar.getTimeInMillis() + Constants.ONE_HOUR );
            calendarIntent.putExtra("allDay", 0);
   //status: 0~ tentative; 1~ confirmed; 2~ canceled
            calendarIntent.putExtra("eventStatus", 1);
   //0~ default; 1~ confidential; 2~ private; 3~ public
            calendarIntent.putExtra("visibility", 0);    //
   //0~ opaque, no timing conflict is allowed; 1~ transparency, allow overlap of scheduling
            calendarIntent.putExtra("transparency", 0);

            //TODO Alarm settings don't actually work
            //Alarm settings
   //0~ false; 1~ true
            calendarIntent.putExtra("hasAlarm", 0);
            //Alarm time in UTC long
            switch( vdb.getAlarmMod() ) {
            case Constants.MINUTE:
                modifier = Calendar.MINUTE;
                break;
            case Constants.HOUR:
                modifier = Calendar.HOUR;
                break;
            case Constants.DAY:
                modifier = Calendar.DATE;
                break;
            case Constants.WEEK:
                modifier = Calendar.WEEK_OF_YEAR;
                break;
            default:
                break;
            }
            _calendar.add( modifier, -vdb.getAlarmValue() );
            vdb.close();
            calendarIntent.putExtra( "alarmTime", _calendar.getTimeInMillis() );


            try {
                //TODO user needs to select the correct calendar, as it isn't available as an intent.  the only other way is to force it, which is what we used to do...see addToCalendar right below.
                startActivity( calendarIntent );
            } catch ( Exception e ) {
                Toast.makeText(this.getApplicationContext(), "Sorry, no compatible calendar is found!", Toast.LENGTH_LONG).show();
                //Log.d( "TAG", e.getMessage() );
            }
            finally {
                setContentView(R.layout.schedule);
                this.setListeners();
                ba.setUnselected();
                ua.setUnselected();
                ul.setUnselected();
                ll.setUnselected();
            }

            //addToCalendar(this, bodyPart + " treatment reminder", c.getTimeInMillis(), c.getTimeInMillis() + Constants.ONE_HOUR );

    }*/

    public void onClick( View v ) {
        switch( v.getId() ) {
            case R.id.underarm:
                if( ua.isSelected ) {
                    ua.setUnselected();
                } else {
                    ua.setSelectedCustom();
                    ba.setUnselected();
                    ul.setUnselected();
                    ll.setUnselected();
                }
              this.prepopulateMinutes(vdb.getUnderarmBikiniTreatmentLength(getApplicationContext()));
                break;
            case R.id.bikiniarea:
            if( ba.isSelected ) {
                ba.setUnselected();
            } else {
                ba.setSelectedCustom();
                ua.setUnselected();
                ul.setUnselected();
                ll.setUnselected();
            }
            this.prepopulateMinutes(vdb.getUnderarmBikiniTreatmentLength(getApplicationContext()));
            break;
        case R.id.upperleg:
            if( ul.isSelected ) {
                ul.setUnselected();
            } else {
                ul.setSelectedCustom();
                ba.setUnselected();
                ua.setUnselected();
                ll.setUnselected();
            }
            this.prepopulateMinutes(vdb.getUpperLowerLegTreatmentLength(getApplicationContext()));
            break;
        case R.id.lowerleg:
            if( ll.isSelected ) {
                ll.setUnselected();
            } else {
                ll.setSelectedCustom();
                ba.setUnselected();
                ua.setUnselected();
                ul.setUnselected();
            }
            this.prepopulateMinutes(vdb.getUpperLowerLegTreatmentLength(getApplicationContext()));
            break;
        case R.id.startupButton:
            isStartup = true;
            //Visual//
            findViewById(R.id.startupButton).setBackgroundColor(0xFFFFFFFF);
            findViewById(R.id.maintenanceButton).setBackgroundColor(0x00000000);
            findViewById(R.id.sessionSpinner).setVisibility(View.VISIBLE);
            findViewById(R.id.sessionText).setVisibility(View.VISIBLE);
            /////////
            break;
        case R.id.maintenanceButton:
            isStartup = false;
            //Visual//
            findViewById(R.id.startupButton).setBackgroundColor(0x00000000);
            findViewById(R.id.maintenanceButton).setBackgroundColor(0xFFFFFFFF);
            findViewById(R.id.sessionSpinner).setVisibility(View.INVISIBLE);
            findViewById(R.id.sessionText).setVisibility(View.INVISIBLE);
            break;
        case R.id.scheduleProceed:
            setContentView(R.layout.phase);
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
            findViewById(R.id.sessionSpinner).setVisibility(View.INVISIBLE);
            findViewById(R.id.sessionText).setVisibility(View.INVISIBLE);
            findViewById(R.id.startupButton).setOnClickListener(this);
            findViewById(R.id.maintenanceButton).setOnClickListener(this);
            findViewById(R.id.phaseProceed).setOnClickListener(this);
            findViewById(R.id.phaseBack).setOnClickListener(this);
            break;
        case R.id.phaseBack:
            setScheduleContent();
            break;
        case R.id.phaseProceed:
            if( 0 == Integer.parseInt( startupNumber ) && isStartup ) {
                isFirstTreatment = true;
                checkStartupMaintenenceAndProceed();
            } else {
                showDialog( DATE_DIALOG_ID );
            }
            break;
        }
    }

    @Override
    protected Dialog onCreateDialog( int id ) {
        switch( id ) {
        case DATE_DIALOG_ID:
            DatePickerDialog dpd = new DatePickerDialog( this,
                                                         new OnDateSetListener() {
                                                             public void onDateSet( DatePicker view,
                                                                                    int year,
                                                                                    int monthOfYear,
                                                                                    int dayOfMonth ) {
                                                                 _calendar.set( year, monthOfYear, dayOfMonth );
                                                             }
                                                         },
                                                         _calendar.get( Calendar.YEAR ),
                                                         _calendar.get( Calendar.MONTH ),
                                                         _calendar.get( Calendar.DATE ) ) {
                public void onClick( DialogInterface d, int which ) {
                    if( -1 == which ) {
                        checkStartupMaintenenceAndProceed();
                    }
                }
            };
            dpd.setTitle( Constants.DATE_PICK_DIALOG );
            return dpd;
        default:
            break;
        }
        return null;
    }


    private void setUpEvent() {
        String bodyPart = null;
        int modifier;
        final Context ctx = this;

        if( ba.isSelected ) {
            bodyPart = getString( R.string.bikini_area );
        } else if( ua.isSelected ) {
            bodyPart = getString( R.string.underarm );
        } else if( ul.isSelected ) {
            bodyPart = getString( R.string.upper_leg );
        } else if( ll.isSelected ) {
            bodyPart = getString( R.string.lower_leg );
        } else {
            bodyPart = "Other";
        }

        int i = ( isFirstTreatment || !isStartup ) ? 0 : Integer.parseInt( startupNumber ) > 5 ? 5 : Integer.parseInt( startupNumber );
        for( ; i < 6; i++ ) {
            String desc = null;
            if( isStartup ) {
                desc = "This is treatment number " + ( i + 1 );
                modifier = Calendar.WEEK_OF_YEAR;
            } else {
                desc = "Maintenance phase";
                modifier = Calendar.MONTH;
            }
            _calendar.add( modifier, 2 );

            final Event e = new Event( "Naked Skin " + bodyPart + " treatment reminder",
                                       desc,
                                       _calendar.getTimeInMillis(),
                                       _calendar.getTimeInMillis() + ( ( -1 == treatmentDuration ) ? Constants.ONE_HOUR : treatmentDuration ) );

            new Thread( new Runnable() {
                public void run() {
                    Utilities.addToCalendar( ctx, e );
                }
            } ).start();
        }

        String toastText = "";
        if( isStartup && isFirstTreatment ) {
            toastText = "Reminders for your first six "+ bodyPart + " treatments have been automatically set.";
        } else if( isStartup ) {
            toastText = "Reminder for " + bodyPart + " treatment number " + startupNumber + " have been set.";
        } else {
            toastText = "Maintenance reminders for " + bodyPart + " has been set.";
        }
        Toast.makeText( this, toastText, Toast.LENGTH_LONG ).show();

        setScheduleContent();
    }


    private void setScheduleContent(){
        setContentView(R.layout.schedule);
        //Visual and listener//
        _calendar = Calendar.getInstance();
        isStartup = false;
        isFirstTreatment = false;
        this.setListeners();
        ba.setUnselected();
        ua.setUnselected();
        ul.setUnselected();
        ll.setUnselected();
    }

}
