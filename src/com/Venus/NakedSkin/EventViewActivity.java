package com.Venus.NakedSkin;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
//import android.util.Log;
import android.widget.TextView;

public class EventViewActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.treatment);

        int year  = getIntent().getIntExtra( Constants.CALENDAR_YEAR_EXTRA, -1 );
        int month = getIntent().getIntExtra( Constants.CALENDAR_MONTH_EXTRA, -1 );
        int day   = getIntent().getIntExtra( Constants.CALENDAR_DAY_EXTRA, -1 );

        TextView eventTitle = (TextView)findViewById( R.id.eventcalendartitle );

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set( year, month, day );
        Date date = cal.getTime();

        DateFormat df = DateFormat.getDateInstance( DateFormat.MEDIUM );
        String timeText = "    " +  df.format( date );
        eventTitle.setText( timeText );

        //duplicate of StartActivity.refresh()
        ArrayList<String> bodyParts = new ArrayList<String>();
        ArrayList<Long> startTimes = new ArrayList<Long>();
        Cursor eventCursor = Utilities.queryDayEvents( this, cal );
        try {
            while( eventCursor.moveToNext() ) {
                startTimes.add( eventCursor.getLong( Constants.EVENT_START_INDEX ) );
                if( eventCursor.getString( Constants.EVENT_DESC_INDEX ).contains( getString( R.string.completed ) ) ) {
                    bodyParts.add( Utilities.getBodyPartString( this, eventCursor
                                                                          .getString( Constants.EVENT_TITLE_INDEX )
                                                                              .substring( 11 ) ) + getString( R.string.done ) );
                } else {
                    bodyParts.add( Utilities.getBodyPartString( this, eventCursor
                                                                          .getString( Constants.EVENT_TITLE_INDEX )
                                                                              .substring( 11 ) ) );
                }
            }

            EventArrayAdapter adapter = new EventArrayAdapter( this, bodyParts, startTimes );
            setListAdapter( adapter );

        } catch( CursorIndexOutOfBoundsException cioobe ) {
            //Log.d( "Venus", cioobe.getMessage() );
            //error happened...
        }
    }

    /**
     * We want to exit the activity when the user presses back
     */
    public void onBackPressed() {
        finish();
    }

}
