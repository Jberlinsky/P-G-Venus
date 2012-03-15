package com.Venus.NakedSkin;

import java.util.ArrayList;
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
            ArrayList<Integer> ed = mView.eventDay(day);

            if (ed.size() == 1){
                String title = this.events.get(ed.get(0)).title;
                 String desc = this.events.get(ed.get(0)).description;

                 Intent eventInt = new Intent(this, EventViewActivity.class);
                 eventInt.putExtra("title", title);
                 eventInt.putExtra("desc", desc);
                 eventInt.putExtra("year", this.events.get(ed.get(0)).year);
                 eventInt.putExtra("month", this.events.get(ed.get(0)).month);
                 eventInt.putExtra("day", this.events.get(ed.get(0)).day);
                 eventInt.putExtra("hour", this.events.get(ed.get(0)).hour);
                 eventInt.putExtra("min", this.events.get(ed.get(0)).minute);
                 eventInt.putExtra("ampm", this.events.get(ed.get(0)).AMPM);
                 startActivity(eventInt);
                 finish();
            }
            else if(ed.size() > 0){
                String title = "";
                boolean checkforWhole = false;
                for (int i = 0;i < ed.size();i++){
                    if (this.events.get(ed.get(i)).title.contains("Bikini"))
                        title += "Bikini Area, ";
                    if (this.events.get(ed.get(i)).title.contains("Underarm"))
                        title += "Underarm, ";
                    if (this.events.get(ed.get(i)).title.contains("Lower"))
                        title += "Lower Leg, ";
                    if (this.events.get(ed.get(i)).title.contains("Upper"))
                        title += "Upper Leg, ";
                    if (this.events.get(ed.get(i)).title.contains("Whole"))
                    	checkforWhole = true;
                }
                if (checkforWhole)
                	title = "Whole Body Reminder";
                else
                	title += "Reminders";
                String desc = "Mutliple reminders";

                Intent eventInt = new Intent(this, EventViewActivity.class);
                eventInt.putExtra("title", title);
                eventInt.putExtra("desc", desc);
                eventInt.putExtra("year", this.events.get(ed.get(0)).year);
                eventInt.putExtra("month", this.events.get(ed.get(0)).month);
                eventInt.putExtra("day", this.events.get(ed.get(0)).day);
                eventInt.putExtra("hour", this.events.get(ed.get(0)).hour);
                eventInt.putExtra("min", this.events.get(ed.get(0)).minute);
                eventInt.putExtra("ampm", this.events.get(ed.get(0)).AMPM);
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
            events.add( new Event( eventCursor.getString( Constants.EVENT_TITLE_INDEX ).substring( 11 ),
                                   eventCursor.getString( Constants.EVENT_DESC_INDEX ),
                                   eventCursor.getLong( Constants.EVENT_START_INDEX ) ) );
        }
        eventCursor.close();
        return events;
    }

}
