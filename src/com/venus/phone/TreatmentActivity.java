package com.venus.phone;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
    List<String> eventName = new ArrayList<String>();
    List<String> eventDescription = new ArrayList<String>();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);
        mView = (CalendarView)findViewById(R.id.calendar);
        this.fetchFromCalendar(this);
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
        if(mView.eventDay(day)){
        	setContentView(R.layout.treatment);
        	TextView title = (TextView) findViewById(R.id.treatmenttitle);
        	TextView desc = (TextView) findViewById(R.id.treatmentdescription);
        	this.eventName.add("Underarm Treatment Reminder");
        	this.eventDescription.add("This is treatment number 5");
        	title.setText(this.eventName.get(0));
        	desc.setText(this.eventDescription.get(0));
        	findViewById(R.id.treatmentBack).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	//// Make sign up email functions here.
                	returnCalendar();
                }
        	 });
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
    
    
    private void fetchFromCalendar(Context ctx)
    {/*
    	Cursor cursor=getContentResolver().query(Uri.parse("content://com.android.calendar/calendars"), 
    			new String[]{"_id", "displayname"}, null, null, null);
    	cursor.moveToFirst();
    	// fetching calendars name
    	String CNames[] = new String[cursor.getCount()];
    	// fetching calendars id
    	int CId[] = new int[cursor.getCount()];
    	for (int i = 0; i < CNames.length; i++)
    	{
    	         CId[i] = cursor.getInt(0);
    	         CNames[i] = cursor.getString(1);
    	         cursor.moveToNext();
    	}
    	ContentResolver contentResolver = getContentResolver();
    	    	
    	Uri.Builder builder = Uri.parse("content://com.android.calendar/instances/when").buildUpon();
        long now = new Date().getTime();
        ContentUris.appendId(builder, now - 10*864000000);
        ContentUris.appendId(builder, now + 10*864000000);
    	Cursor eventCursor = contentResolver.query(builder.build(),
                new String[] { "event_id"}, "Calendars._id=" + 1,
                null, "startDay ASC, startMinute ASC");
        // For a full list of available columns see http://tinyurl.com/yfbg76w
        while (eventCursor.moveToNext()) {
            String uid2 = eventCursor.getString(0);
            Log.v("eventID : ", uid2);

        }
    	
    	
    	/*
    	Uri.Builder CALENDAR_URI = Uri.parse("content://com.android.calendar/instances/when").buildUpon();
        Cursor c = getContentResolver().query(CALENDAR_URI.build(), null, null, null, null);
        
        if (c.moveToFirst())
        {
                while (c.moveToNext())
                {
                        String desc = c.getString(c.getColumnIndex("description"));
                        String location = c.getString(c.getColumnIndex("eventLocation"));                      
                        // event id
                        String id = c.getString(c.getColumnIndex("_id"));
                        
                }
        }
    	/*
        ContentResolver cr = ctx.getContentResolver();
        Cursor cursor;
        Uri.Builder builder;
        if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 8)
        	builder = Uri.parse("content://com.android.calendar/calendars/events").buildUpon();
        	
        else
        	builder = Uri.parse("content://calendar/instances/when").buildUpon();
        
        long now = new Date().getTime();
        ContentUris.appendId(builder, now - DateUtils.WEEK_IN_MILLIS);
        ContentUris.appendId(builder, now + DateUtils.WEEK_IN_MILLIS);
        
        cursor = cr.query(builder.build(),
                    new String[] { "title", "begin", "end", "allDay"}, "Calendars._id=" + calendarId,
                    null, "startDay ASC, startMinute ASC");
       
       
        
         
        while (cursor.moveToNext()) {
            final String title2 = cursor.getString(0);
            final Date begin = new Date(cursor.getLong(1));
            final Date end = new Date(cursor.getLong(2));
            final Boolean allDay = !cursor.getString(3).equals("0");
        }
        
        cursor.close();*/
    }

}
