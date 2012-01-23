package com.venus.phone;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Utility.Event;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.exina.android.calendar.CalendarView;
import com.exina.android.calendar.Cell;


public class TreatmentActivity extends Activity implements CalendarView.OnCellTouchListener {
    public static final String MIME_TYPE = "vnd.android.cursor.dir/vnd.exina.android.calendar.date";

    CalendarView mView = null;
    TextView mHit;
    Handler mHandler = new Handler();
    static String calendarId = new String();
    ArrayList<Event> events = new ArrayList<Event>();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);
        mView = (CalendarView)findViewById(R.id.calendar);
        mView.setEvent(this.getEvents(this));
        mView.setOnCellTouchListener(this);
        findViewById(R.id.calendarBack).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	//// Make sign up email functions here.
            	mView.previousMonth();
            	setMonth();
            	mHandler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(TreatmentActivity.this, DateUtils.getMonthString(mView.getMonth(), DateUtils.LENGTH_LONG) + " "+mView.getYear(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        findViewById(R.id.calendarNext).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	//// Make sign up email functions here.
            	mView.nextMonth();
            	setMonth();
            	mHandler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(TreatmentActivity.this, DateUtils.getMonthString(mView.getMonth(), DateUtils.LENGTH_LONG) + " "+mView.getYear(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        setMonth();

//        if(getIntent().getAction().equals(Intent.ACTION_PICK))
//            findViewById(R.id.hint).setVisibility(View.INVISIBLE);
    }

    public void onTouch(Cell cell) {
        //Intent intent = getIntent();
        //String action = intent.getAction();
        //TODO : Change to slide action maybe use gestureoverlay?
        int day = cell.getDayOfMonth();
        int inde = mView.eventDay(day);
        if(inde > -1){
        	String title = this.events.get(inde).title;
        	String desc = this.events.get(inde).description;
        	
        	Intent eventInt = new Intent(this, EventViewActivity.class);
        	eventInt.putExtra("title", title);
        	eventInt.putExtra("desc", desc);
        	startActivity(eventInt);
        	
        }
//        if(action.equals(Intent.ACTION_PICK) || action.equals(Intent.ACTION_GET_CONTENT)) {
//            Intent ret = new Intent();
//            ret.putExtra("year", mView.getYear());
//            ret.putExtra("month", mView.getMonth());
//            ret.putExtra("day", cell.getDayOfMonth());
//            this.setResult(RESULT_OK, ret);
//            finish();
//            return;
//        }
        /*
        int day = cell.getDayOfMonth();
        if(mView.firstDay(day))
            mView.previousMonth();
        else if(mView.lastDay(day))
            mView.nextMonth();
        else
            return;

        mHandler.post(new Runnable() {
            public void run() {
                Toast.makeText(TreatmentActivity.this, DateUtils.getMonthString(mView.getMonth(), DateUtils.LENGTH_LONG) + " "+mView.getYear(), Toast.LENGTH_SHORT).show();
            }
        });
        */
    }
        public void returnCalendar(){
        	setContentView(R.layout.calendar);
            mView = (CalendarView)findViewById(R.id.calendar);
            //this.fetchFromCalendar(this);
            mView.setOnCellTouchListener(this);
            findViewById(R.id.calendarBack).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	//// Make sign up email functions here.
                	mView.previousMonth();
                	setMonth();
                	
                }
            });
            
            findViewById(R.id.calendarNext).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	//// Make sign up email functions here.
                	mView.nextMonth();
                	setMonth();
                	
                }
            });
            setMonth();
            mView.setEvent(this.events);
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
    
    //Month Text
    private void setMonth()
    {
    	TextView tempView;
        if (mView.getMonth() == 11)
        {
        	tempView = (TextView)findViewById(R.id.monthtext);
        	tempView.setText("December");
        }
        else if (mView.getMonth() == 10)
        {
        	tempView = (TextView)findViewById(R.id.monthtext);
        	tempView.setText("November");
        }
        else if (mView.getMonth() == 9)
        {
        	tempView = (TextView)findViewById(R.id.monthtext);
        	tempView.setText("October");
        }
        else if (mView.getMonth() == 8)
        {
        	tempView = (TextView)findViewById(R.id.monthtext);
        	tempView.setText("September");
        }
        else if (mView.getMonth() == 7)
        {
        	tempView = (TextView)findViewById(R.id.monthtext);
        	tempView.setText("August");
        }
        else if (mView.getMonth() == 6)
        {
        	tempView = (TextView)findViewById(R.id.monthtext);
        	tempView.setText("July");
        }
        else if (mView.getMonth() == 5)
        {
        	tempView = (TextView)findViewById(R.id.monthtext);
        	tempView.setText("June");
        }
        else if (mView.getMonth() == 4)
        {
        	tempView = (TextView)findViewById(R.id.monthtext);
        	tempView.setText("May");
        }
        else if (mView.getMonth() == 3)
        {
        	tempView = (TextView)findViewById(R.id.monthtext);
        	tempView.setText("April");
        }
        else if (mView.getMonth() == 2)
        {
        	tempView = (TextView)findViewById(R.id.monthtext);
        	tempView.setText("March");
        }
        else if (mView.getMonth() == 1)
        {
        	tempView = (TextView)findViewById(R.id.monthtext);
        	tempView.setText("February");
        }
        else if (mView.getMonth() == 0)
        {
        	tempView = (TextView)findViewById(R.id.monthtext);
        	tempView.setText("January");
        }
    }
    
    
    private ArrayList<Event> getEvents(Context ctx)
    {
      ContentResolver cr = ctx.getContentResolver();
      Cursor cursor;
      if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 8)
        cursor = cr.query(Uri.parse("content://com.android.calendar/calendars"), new String[]{ "_id", "displayName" }, null, null, null);
      else
        cursor = cr.query(Uri.parse("content://calendar/calendars"), new String[]{ "_id", "displayName" }, null, null, null);
      String calendarId = "1";
      Uri.Builder builder;
      if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 8)
        builder = Uri.parse("content://com.android.calendar/instances/when").buildUpon();
      else
        builder = Uri.parse("content://calendar/instances/when").buildUpon();
      long now = new Date().getTime();
      ContentUris.appendId(builder, now - DateUtils.WEEK_IN_MILLIS * 25);
      ContentUris.appendId(builder, now + DateUtils.WEEK_IN_MILLIS * 26);

      Cursor eventCursor = cr.query(builder.build(),
          new String[] { "title", "description","dtstart", "end", "allDay"}, "Calendars._id=" + calendarId,
          null, "startDay ASC, startMinute ASC");
      while (eventCursor.moveToNext())
      {
        Event nEvent = new Event();
        nEvent.title = eventCursor.getString(0);
        nEvent.description = eventCursor.getString(1);
        Long x = eventCursor.getLong(2);
        Calendar b = Calendar.getInstance();
        b.setTimeInMillis(x);
        nEvent.day = b.get(Calendar.DATE);
        nEvent.month = b.get(Calendar.MONTH);
        nEvent.year = b.get(Calendar.YEAR);
        events.add(nEvent);
      }
      return events;
    }

}
