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
                if( eventCursor.getString( Constants.EVENT_DESC_INDEX ).contains( Constants.COMPLETED ) ) {
                    bodyParts.add( getBodyPartString( eventCursor.getString( Constants.EVENT_TITLE_INDEX ).substring( 11 ) ) + Constants.DONE );
                } else {
                    bodyParts.add( getBodyPartString( eventCursor.getString( Constants.EVENT_TITLE_INDEX ).substring( 11 ) ) );
                }
            }

            EventArrayAdapter adapter = new EventArrayAdapter( this, bodyParts, startTimes );
            setListAdapter( adapter );

        } catch( CursorIndexOutOfBoundsException cioobe ) {
            //Log.d( "Venus", cioobe.getMessage() );
            //error happened...
        }
    }

    public void onBackPressed(){
        finish();
    }

    private String getBodyPartString( String subString ) {
        if( subString.substring( 0, 2 ).equalsIgnoreCase( Constants.UN) ) {
            return Constants.UNDERARM;
        } else if( subString.substring( 0, 2 ).equalsIgnoreCase( Constants.BI ) ) {
            return Constants.BIKINIAREA;
        } else if( subString.substring( 0, 2 ).equalsIgnoreCase( Constants.UP ) ) {
            return Constants.UPPERLEG;
        } else if( subString.substring( 0, 2 ).equalsIgnoreCase( Constants.LO ) ) {
            return Constants.LOWERLEG;
        } else if( subString.substring( 0, 2 ).equalsIgnoreCase( Constants.WH ) ) {
            return Constants.WHOLEBODY;
        } else {
            return Constants.OTHER;
        }
    }
}
