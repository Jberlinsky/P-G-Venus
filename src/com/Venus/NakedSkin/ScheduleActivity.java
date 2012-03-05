package com.Venus.NakedSkin;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
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
import android.widget.Spinner;
import android.widget.Toast;

public class ScheduleActivity extends Activity implements OnClickListener{
    Spinner sessionSpinner;
    private boolean isStartup;
    private String startupNumber;
    private int treatmentDuration = -1;

    SelectionButton ba;
    SelectionButton ua;
    SelectionButton ul;
    SelectionButton ll;

    private VenusDb vdb;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);
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

    public void checkStartupMaintenenceAndProceed()
    {
      // If this is after the first set of treatments, but we have not switched to the maintenence phase yet, warn
            if (isStartup && !vdb.isFirstTreatmentReminder(getApplicationContext()))
            {
              final ScheduleActivity self = this;
              // Prompt with option of switching to maintenence
              AlertDialog.Builder builder = new AlertDialog.Builder(this);
              builder.setMessage(
                  Constants.TREATMENT_OPTION_MESSAGE
              ).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                  // Change to maintenence
                  self.proceed(!isStartup);
                }
              }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                  self.proceed(isStartup);
                }
              });
              AlertDialog alert = builder.create();
              alert.show();
            }
            else {
              this.proceed(isStartup);
            }
    }

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
            Calendar c = Calendar.getInstance();
            if( isStartup ) {
                desc = "This is treatment number " + startupNumber;
                modifier = Calendar.WEEK_OF_YEAR;
            } else {
                desc = "Maintenance phase";
                modifier = Calendar.MONTH;
            }
            c.add( modifier, 2 );
            Intent calendarIntent = new Intent( Intent.ACTION_INSERT );
            calendarIntent.setType( "vnd.android.cursor.item/event" );
            calendarIntent.putExtra( "title", "Naked Skin " + bodyPart + " treatment reminder" );
            calendarIntent.putExtra( "description", desc );
            calendarIntent.putExtra( "beginTime", c.getTimeInMillis() );
            if (this.treatmentDuration != -1)
              calendarIntent.putExtra("endTime", c.getTimeInMillis() + this.treatmentDuration);
            else
              calendarIntent.putExtra( "endTime", c.getTimeInMillis() + Constants.ONE_HOUR );
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
            c.add( modifier, -vdb.getAlarmValue() );
            vdb.close();
            calendarIntent.putExtra( "alarmTime", c.getTimeInMillis() );


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
 
    }

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
            setContentView(R.layout.setreminder);
            this.checkStartupMaintenenceAndProceed();


            break;
        }
    }

    private void setScheduleContent(){
        setContentView(R.layout.schedule);
        //Visual and listener//
        this.setListeners();
        ba.setUnselected();
        ua.setUnselected();
        ul.setUnselected();
        ll.setUnselected();
    }


    /*
     * This is the old way of adding to calendar.  This has since been updated.
     */
/*    private static void addToCalendar(Context ctx, final String title, final long dtstart, final long dtend)
    {
        final ContentResolver cr = ctx.getContentResolver();
        Cursor cursor;
        if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 8)
            cursor = cr.query(Uri.parse("content://com.android.calendar/calendars"), new String[]{ "_id", "displayname" }, null, null, null);
        else
            cursor = cr.query(Uri.parse("content://calendar/calendars"), new String[]{ "_id", "displayname" }, null, null, null);
        if (cursor.moveToFirst())
        {
            final String[] calNames = new String[cursor.getCount()];
            final int[] calIds = new int[cursor.getCount()];
            for (int i = 0; i < calNames.length; i++)
            {
                calIds[i] = cursor.getInt(0);
                calNames[i] = cursor.getString(1);
                cursor.moveToNext();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setSingleChoiceItems(calNames, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        ContentValues cv = new ContentValues();
                        cv.put("calendar_id", calIds[which]);
                        cv.put("title", title);
                        cv.put("dtstart", dtstart);
                        cv.put("hasAlarm", 1);
                        cv.put("dtend", dtend);

                        Uri newEvent;
                        if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 8)
                            newEvent = cr.insert(Uri.parse("content://com.android.calendar/events"), cv);
                        else
                            newEvent = cr.insert(Uri.parse("content://com.android.calendar/events"), cv);
                        if (newEvent != null)
                        {
                            long id = Long.parseLong(newEvent.getLastPathSegment());
                            ContentValues values = new ContentValues();
                            values.put("event_id", id);
                            values.put("method", 1);
                            values.put("minutes", 15);
                            if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 8 )
                                cr.insert( Uri.parse( "content://com.android.calendar/reminders" ), values );
                            else
                                cr.insert( Uri.parse( "content://calendar/reminders" ), values );
                        }
            dialog.cancel();
                    }
            });
            builder.create().show();
        }
        cursor.close();
    }
*/
}
