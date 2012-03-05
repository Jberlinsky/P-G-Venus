package com.Venus.NakedSkin;

import java.util.Calendar;

/* These imports were for the old method of inserting to calendar
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
*/
import android.app.Activity;
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

    /*
    SelectionButton lua;
    SelectionButton lfa;
    SelectionButton lul;
    SelectionButton lll;
    SelectionButton rua;
    SelectionButton rfa;
    SelectionButton rul;
    SelectionButton rll;*/

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);

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

      //Listener
        this.setListeners();

    }

    private void setListeners() {
        /*
        lua = (SelectionButton)findViewById(R.id.underarmleft);
        lua.setOnClickListener(this);
        rua = (SelectionButton)findViewById(R.id.underarmright);
        rua.setOnClickListener(this);
        lfa = (SelectionButton)findViewById(R.id.forearmleft);
        lfa.setOnClickListener(this);
        rfa = (SelectionButton)findViewById(R.id.forearmright);
        rfa.setOnClickListener(this);
        lul = (SelectionButton)findViewById(R.id.upperlegleft);
        lul.setOnClickListener(this);
        rul = (SelectionButton)findViewById(R.id.upperlegright);
        rul.setOnClickListener(this);
        lll = (SelectionButton)findViewById(R.id.lowerlegleft);
        lll.setOnClickListener(this);
        rll = (SelectionButton)findViewById(R.id.lowerlegright);
        rll.setOnClickListener(this);
        */

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
              this.prepopulateMinutes(Constants.TEN_MINUTES);
                break;
        /*
        case R.id.underarmleft:
            if( lua.isSelected ) {
                lua.setUnselected();
            } else {
                lua.setSelectedCustom();
                rua.setUnselected();
                lfa.setUnselected();
                rfa.setUnselected();
                ba.setUnselected();
                lul.setUnselected();
                rul.setUnselected();
                lll.setUnselected();
                rll.setUnselected();
            }
            break;

        case R.id.underarmright:
            if( rua.isSelected ) {
                rua.setUnselected();
            } else {
                rua.setSelectedCustom();
                lua.setUnselected();
                lfa.setUnselected();
                rfa.setUnselected();
                ba.setUnselected();
                lul.setUnselected();
                rul.setUnselected();
                lll.setUnselected();
                rll.setUnselected();
            }
            break;
        case R.id.forearmleft:
            if( lfa.isSelected ) {
                lfa.setUnselected();
            } else {
                lfa.setSelectedCustom();
                lua.setUnselected();
                rua.setUnselected();
                rfa.setUnselected();
                ba.setUnselected();
                lul.setUnselected();
                rul.setUnselected();
                lll.setUnselected();
                rll.setUnselected();
            }
            break;
        case R.id.forearmright:
            if( rfa.isSelected ) {
                rfa.setUnselected();
            } else {
                rfa.setSelectedCustom();
                lua.setUnselected();
                rua.setUnselected();
                lfa.setUnselected();
                ba.setUnselected();
                lul.setUnselected();
                rul.setUnselected();
                lll.setUnselected();
                rll.setUnselected();
            }
            break;*/
        case R.id.bikiniarea:
            if( ba.isSelected ) {
                ba.setUnselected();
            } else {
                ba.setSelectedCustom();
                ua.setUnselected();
                ul.setUnselected();
                ll.setUnselected();
                /*
                lua.setUnselected();
                rua.setUnselected();
                lfa.setUnselected();
                rfa.setUnselected();
                lul.setUnselected();
                rul.setUnselected();
                lll.setUnselected();
                rll.setUnselected();*/
            }
            this.prepopulateMinutes(Constants.TEN_MINUTES);
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
            this.prepopulateMinutes(Constants.FOURTY_FIVE_MINUTES);
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
            this.prepopulateMinutes(Constants.FOURTY_FIVE_MINUTES);
            break;
            /*
        case R.id.upperlegleft:
            if( lul.isSelected ) {
                lul.setUnselected();
            } else {
                lul.setSelectedCustom();
                lua.setUnselected();
                rua.setUnselected();
                lfa.setUnselected();
                rfa.setUnselected();
                ba.setUnselected();
                rul.setUnselected();
                lll.setUnselected();
                rll.setUnselected();
            }
            break;
        case R.id.upperlegright:
            if( rul.isSelected ) {
                rul.setUnselected();
            } else {
                rul.setSelectedCustom();
                lua.setUnselected();
                rua.setUnselected();
                lfa.setUnselected();
                rfa.setUnselected();
                ba.setUnselected();
                lul.setUnselected();
                lll.setUnselected();
                rll.setUnselected();
            }
            break;
        case R.id.lowerlegleft:
            if( lll.isSelected ) {
                lll.setUnselected();
            } else {
                lll.setSelectedCustom();
                lua.setUnselected();
                rua.setUnselected();
                lfa.setUnselected();
                rfa.setUnselected();
                ba.setUnselected();
                lul.setUnselected();
                rul.setUnselected();
                rll.setUnselected();
            }
            break;
        case R.id.lowerlegright:
            if( rll.isSelected ) {
                rll.setUnselected();
            } else {
                rll.setSelectedCustom();
                lua.setUnselected();
                rua.setUnselected();
                lfa.setUnselected();
                rfa.setUnselected();
                ba.setUnselected();
                lul.setUnselected();
                rul.setUnselected();
                lll.setUnselected();
            }
            break;*/
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
            /*
            lua.setUnselected();
            rua.setUnselected();
            lfa.setUnselected();
            rfa.setUnselected();

            lul.setUnselected();
            rul.setUnselected();
            lll.setUnselected();
            rll.setUnselected();*/
            break;
        case R.id.phaseProceed:
            setContentView(R.layout.setreminder);
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
            VenusDb vdb = new VenusDb( this );
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
