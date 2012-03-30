package com.Venus.NakedSkin;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class EventViewActivity extends ListActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.treatment);

        int month = this.getIntent().getIntExtra("month",-1);
        int day = this.getIntent().getIntExtra("day",-1);
        int year = this.getIntent().getIntExtra("year",-1);

        TextView eventTitle = (TextView) findViewById(R.id.eventcalendartitle);

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set( year, month, day );
        Date date = cal.getTime();

        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String timeText = "    " +  df.format(date);
        eventTitle.setText( timeText);

        //duplicate of StartActivity.refresh()
        ArrayList<String> bodyParts = new ArrayList<String>();
        ArrayList<Long> startTimes = new ArrayList<Long>();
        Cursor eventCursor = Utilities.queryDayEvents( this, cal );
        try {
            while( eventCursor.moveToNext() ) {
                startTimes.add( eventCursor.getLong( Constants.EVENT_START_INDEX ) );
                if( eventCursor.getString( Constants.EVENT_DESC_INDEX ).contains( "Completed!" ) ) {
                    bodyParts.add( getBodyPartString( eventCursor.getString( Constants.EVENT_TITLE_INDEX ).substring( 11 ) ) + " Done" );
                } else {
                    bodyParts.add( getBodyPartString( eventCursor.getString( Constants.EVENT_TITLE_INDEX ).substring( 11 ) ) );
                }
            }

            EventArrayAdapter adapter = new EventArrayAdapter( this, bodyParts, startTimes );
            setListAdapter( adapter );

        } catch( CursorIndexOutOfBoundsException cioobe ) {
            Log.d( "Venus", cioobe.getMessage() );
            //error happened...
        }
    }

    public void onBackPressed(){
        finish();
    }

    private String getBodyPartString( String subString ) {
        if( subString.substring( 0, 2 ).equalsIgnoreCase( "Un" ) ) {
            return "Underarm";
        } else if( subString.substring( 0, 2 ).equalsIgnoreCase( "Bi" ) ) {
            return "Bikini Area";
        } else if( subString.substring( 0, 2 ).equalsIgnoreCase( "Up" ) ) {
            return "Upper Leg";
        } else if( subString.substring( 0, 2 ).equalsIgnoreCase( "Lo" ) ) {
            return "Lower Leg";
        } else if( subString.substring( 0, 2 ).equalsIgnoreCase( "Wh" ) ) {
            return "Whole Body";
        } else {
            return "Other";
        }
    }
}
