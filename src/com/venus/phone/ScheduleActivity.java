package com.venus.phone;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ScheduleActivity extends Activity implements OnClickListener{

    Spinner sessionSpinner;
    private boolean isStartup;
    private String startupNumber;

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
            return true;
        case R.id.howtomenu:
        	startActivity( new Intent( getApplicationContext(), HowtoActivity.class ) );
            return true;
        case R.id.settingmenu:
        	startActivity( new Intent( getApplicationContext(), SettingActivity.class ) );
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
            setContentView(R.layout.schedule);
            //Visual and listener//
            this.setListeners();
            ba.setUnselected();
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
            break;
        case R.id.phaseProceed:
            setContentView(R.layout.setreminder);
            //Visual and listener//
            findViewById(R.id.saveReminder).setOnClickListener(this);
            findViewById(R.id.setReminderBack).setOnClickListener(this);
            break;
        case R.id.setReminderBack:
            setContentView(R.layout.phase);
            //Visual and listener//
            findViewById(R.id.sessionSpinner).setVisibility(View.INVISIBLE);
            findViewById(R.id.sessionText).setVisibility(View.INVISIBLE);
            findViewById(R.id.startupButton).setOnClickListener(this);
            findViewById(R.id.maintenanceButton).setOnClickListener(this);
            findViewById(R.id.phaseProceed).setOnClickListener(this);
            findViewById(R.id.phaseBack).setOnClickListener(this);
            break;
        case R.id.saveReminder:
            // Do the reminder magic!
            /*
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager mNotificationManager = (NotificationManager)getSystemService(ns);
            int icon = android.R.drawable.ic_dialog_email;
            CharSequence tickerText = "Reminder from Venus";
            long when = System.currentTimeMillis();

            Notification notification = new Notification(icon, tickerText, when);

            Context context = getApplicationContext();
            CharSequence contentTitle = "Time to Treat!";
            CharSequence contentText = "You have a treatment scheduled with the Venus.";

            Intent notificationIntent = new Intent(this, ScheduleActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

            notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

            mNotificationManager.notify(SHAVE_ID, notification);

            Toast.makeText(this, "Reminder has been set!", Toast.LENGTH_SHORT).show();
            */

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
            /*
            if( lua.isSelected ) {
                bodyPart = getString( R.string.left_underarm );
            } else if( rua.isSelected ) {
                bodyPart = getString( R.string.right_underarm );
            } else if( lfa.isSelected ) {
                bodyPart = getString( R.string.left_forearm );
            } else if( rfa.isSelected ) {
                bodyPart = getString( R.string.right_forearm );
            } else if( ba.isSelected ) {
                bodyPart = getString( R.string.bikini_area );
            } else if( lul.isSelected ) {
                bodyPart = getString( R.string.left_upper_leg);
            } else if( rul.isSelected ) {
                bodyPart = getString( R.string.right_upper_leg );
            } else if( lll.isSelected ) {
                bodyPart = getString( R.string.left_lower_leg );
            } else if( rll.isSelected ) {
                bodyPart = getString( R.string.right_lower_leg );
            }*/
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
            //Here we add an event through an intent.
//            Intent calendarIntent = new Intent( Intent.ACTION_EDIT );
//            calendarIntent.setType( "vnd.android.cursor.item/event" );
//            calendarIntent.putExtra( "title", bodyPart + " treatment reminder" );
//            calendarIntent.putExtra( "description", desc );
//            calendarIntent.putExtra( "beginTime", c.getTimeInMillis() );
//            calendarIntent.putExtra( "endTime", c.getTimeInMillis() + Constants.ONE_HOUR );
//   l_intent.putExtra("allDay", 0);
   //status: 0~ tentative; 1~ confirmed; 2~ canceled
//   l_intent.putExtra("eventStatus", 1);
   //0~ default; 1~ confidential; 2~ private; 3~ public
//   l_intent.putExtra("visibility", 0);    //
   //0~ opaque, no timing conflict is allowed; 1~ transparency, allow overlap of scheduling
//   l_intent.putExtra("transparency", 0);
   //0~ false; 1~ true
//   l_intent.putExtra("hasAlarm", 1);
//            try {
//                startActivity( calendarIntent );
//            } catch ( Exception e ) {
//                Toast.makeText(this.getApplicationContext(), "Sorry, no compatible calendar is found!", Toast.LENGTH_LONG).show();
//                Log.d( "TAG", e.getMessage() );
//            }

            addToCalendar(this, bodyPart + " treatment reminder", c.getTimeInMillis(), c.getTimeInMillis() + Constants.ONE_HOUR );

            setContentView(R.layout.schedule);
            this.setListeners();
            ba.setUnselected();
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
    }































    private static void addToCalendar(Context ctx, final String title, final long dtstart, final long dtend)
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




}
