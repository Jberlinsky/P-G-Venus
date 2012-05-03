package com.Venus.NakedSkin;

import java.util.ArrayList;
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

/**
 * Treatment Activity that shows a calendar view.
 * Blue circle means a treatment is scheduled
 * White circle is today.
 * @author Jingran Wang
 *
 */
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
        mView.setEvent( getEvents() );
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
        switch (item.getItemId()) {
        case R.id.schedulemenu:
            startActivity( new Intent( this, ScheduleActivity.class ) );
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
            startActivity( new Intent( this, TutorialActivity.class ).putExtra( Constants.FIRST, false) );
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
