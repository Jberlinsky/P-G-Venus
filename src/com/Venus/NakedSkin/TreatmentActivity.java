package com.Venus.NakedSkin;

import java.util.ArrayList;
import java.util.Calendar;

import Utility.Event;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
        mView.setEvent(this.getEvents());
        mView.setOnCellTouchListener(this);
        findViewById(R.id.calendarBack).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //// Make sign up email functions here.
                mView.previousMonth();
                setMonth();
                /*
                mHandler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(TreatmentActivity.this, DateUtils.getMonthString(mView.getMonth(), DateUtils.LENGTH_LONG) + " "+mView.getYear(), Toast.LENGTH_SHORT).show();
                    }
                });*/
            }
        });
        findViewById(R.id.calendarNext).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //// Make sign up email functions here.
                mView.nextMonth();
                setMonth();
                /*
                mHandler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(TreatmentActivity.this, DateUtils.getMonthString(mView.getMonth(), DateUtils.LENGTH_LONG) + " "+mView.getYear(), Toast.LENGTH_SHORT).show();
                    }
                });*/
            }
        });
        setMonth();

//        if(getIntent().getAction().equals(Intent.ACTION_PICK))
//            findViewById(R.id.hint).setVisibility(View.INVISIBLE);
    }

    public void onBackPressed(){
        //This is to prevent user from accidently exiting the app
        //pressing Home will exit the app
    }

    public void onTouch(Cell cell) {
        //Intent intent = getIntent();
        //String action = intent.getAction();
        //TODO : Change to slide action maybe use gestureoverlay?
        if (cell.thismonth) {
            int day = cell.getDayOfMonth();
            int inde = mView.eventDay(day);
            if(inde > -1){
                String title = this.events.get(inde).title;
                String desc = this.events.get(inde).description;

                Intent eventInt = new Intent(this, EventViewActivity.class);
                eventInt.putExtra("title", title);
                eventInt.putExtra("desc", desc);
                eventInt.putExtra("year", this.events.get(inde).year);
                eventInt.putExtra("month", this.events.get(inde).month);
                eventInt.putExtra("day", this.events.get(inde).day);
                eventInt.putExtra("hour", this.events.get(inde).hour);
                eventInt.putExtra("min", this.events.get(inde).minute);
                eventInt.putExtra("ampm", this.events.get(inde).AMPM);
                startActivity(eventInt);
                finish();
            }
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

    //Month Text
    private void setMonth()
    {
        TextView tempView;

        if (mView.getMonth() == 11)
        {
            tempView = (TextView)findViewById(R.id.monthtext);
            tempView.setText("December " + mView.getYear());
        }
        else if (mView.getMonth() == 10)
        {
            tempView = (TextView)findViewById(R.id.monthtext);
            tempView.setText("November " + mView.getYear());
        }
        else if (mView.getMonth() == 9)
        {
            tempView = (TextView)findViewById(R.id.monthtext);
            tempView.setText("October " + mView.getYear());
        }
        else if (mView.getMonth() == 8)
        {
            tempView = (TextView)findViewById(R.id.monthtext);
            tempView.setText("September " + mView.getYear());
        }
        else if (mView.getMonth() == 7)
        {
            tempView = (TextView)findViewById(R.id.monthtext);
            tempView.setText("August " + mView.getYear());
        }
        else if (mView.getMonth() == 6)
        {
            tempView = (TextView)findViewById(R.id.monthtext);
            tempView.setText("July " + mView.getYear());
        }
        else if (mView.getMonth() == 5)
        {
            tempView = (TextView)findViewById(R.id.monthtext);
            tempView.setText("June " + mView.getYear());
        }
        else if (mView.getMonth() == 4)
        {
            tempView = (TextView)findViewById(R.id.monthtext);
            tempView.setText("May " + mView.getYear());
        }
        else if (mView.getMonth() == 3)
        {
            tempView = (TextView)findViewById(R.id.monthtext);
            tempView.setText("April " + mView.getYear());
        }
        else if (mView.getMonth() == 2)
        {
            tempView = (TextView)findViewById(R.id.monthtext);
            tempView.setText("March " + mView.getYear());
        }
        else if (mView.getMonth() == 1)
        {
            tempView = (TextView)findViewById(R.id.monthtext);
            tempView.setText("February " + mView.getYear());
        }
        else if (mView.getMonth() == 0)
        {
            tempView = (TextView)findViewById(R.id.monthtext);
            tempView.setText("January " + mView.getYear());
        }
    }


    private ArrayList<Event> getEvents() {
        Cursor eventCursor = Utilities.queryEvents( this );
        while( eventCursor.moveToNext() ) {
            String title = eventCursor.getString(0);
            if( title.contains("Naked") ) {
                Event nEvent = new Event();
                nEvent.title = title.substring(11);
                nEvent.description = eventCursor.getString(1);
                Long x = eventCursor.getLong(2);
                Calendar b = Calendar.getInstance();
                b.setTimeInMillis(x);
                nEvent.day = b.get(Calendar.DATE);
                nEvent.month = b.get(Calendar.MONTH);
                nEvent.year = b.get(Calendar.YEAR);
                nEvent.hour = b.get(Calendar.HOUR_OF_DAY);
                nEvent.AMPM = b.get(Calendar.AM_PM);
                nEvent.minute = b.get(Calendar.MINUTE);
                events.add(nEvent);
            }
        }
        eventCursor.close();
        return events;
    }

}
