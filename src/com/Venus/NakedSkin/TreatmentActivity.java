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
                mView.previousMonth();
                setMonth();
            }
        });
        findViewById(R.id.calendarNext).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mView.nextMonth();
                setMonth();
            }
        });
        setMonth();
    }

    /**
     * Prevents user from accidentally exiting the app.
     * User needs to press "home"
     * Could be changed to "Do you want to exit?" dialog.
     */
    public void onBackPressed(){ }

    /**
     * Called when a cell is tapped.  Only activates for the current month.
     */
    public void onTouch(Cell cell) {
        //TODO : Change to slide action maybe use gestureoverlay?
        if (cell.thismonth) {
            int day = cell.getDayOfMonth();
            ArrayList<Integer> ed = mView.eventDay(day);
            if (ed.size() > 0){
                Intent eventInt = new Intent(this, EventViewActivity.class);
                eventInt.putExtra( Constants.CALENDAR_YEAR_EXTRA, this.events.get(ed.get(0)).year);
                eventInt.putExtra( Constants.CALENDAR_MONTH_EXTRA, this.events.get(ed.get(0)).month);
                eventInt.putExtra( Constants.CALENDAR_DAY_EXTRA, this.events.get(ed.get(0)).day);
                startActivity(eventInt);
            }
        }
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
     * Handle menu selection
     */
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

    /**
     * Sets the month text according to the number
     */
    private void setMonth() {
        String[] months = getResources().getStringArray( R.array.months_array );
        ((TextView)findViewById( R.id.monthtext )).setText( months[mView.getMonth()]
                                                            + " "
                                                            + mView.getYear() );
    }

    /**
     * Queries the device calendar DB for the events
     * Adds them to the app's calendar view
     * @return ArrayList<Event>
     */
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
