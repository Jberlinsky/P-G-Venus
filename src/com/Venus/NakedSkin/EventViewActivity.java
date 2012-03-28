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
            Cursor eventCursor = Utilities.queryDayEvents( this, cal);
            try { //calls proceed() with either only 1 choice, or after dialog
                String desc = null;
                int treatmentNumberTemp;
                if( 1 < eventCursor.getCount() ) { //more than one event today.
                    ArrayList<CharSequence> bodyParts = new ArrayList<CharSequence>(); //store body parts here, show these to user
                    ArrayList<Integer> treatmentNumbers = new ArrayList<Integer>(); //store (potential) treatment numbers here, keep internal
                    ArrayList<Long> startTimes = new ArrayList<Long>(); //store (potential) start times here, keep internal
                    ArrayList<String> bodyPartString = new ArrayList<String>();
                    while( eventCursor.moveToNext() ) {
                    	String bodyTitle = getBodyPartString( eventCursor.getString( Constants.EVENT_TITLE_INDEX ).substring( 11 ));
                    	
                        
                        if (eventCursor.getString( Constants.EVENT_DESC_INDEX ).contains("Completed"))
                        	bodyTitle = bodyTitle + " Done";
                        bodyParts.add(bodyTitle); //getting the body parts	
                        bodyPartString.add(bodyTitle); //getting the body parts
                        startTimes.add( eventCursor.getLong( Constants.EVENT_START_INDEX ) );
                        try { //try to get the treatment number (doesn't exist for maint)
                            desc = eventCursor.getString( Constants.EVENT_DESC_INDEX );
                            treatmentNumberTemp = Integer.parseInt( desc.substring( desc.length() - 2, desc.length() ).trim() );
                        } catch( NumberFormatException nfe ) { //this exception means maintenance, set to -1
                            treatmentNumberTemp = -1;
                        }
                        treatmentNumbers.add( treatmentNumberTemp );
                    }
                    //for view;

                    EventArrayAdapter adapter = new EventArrayAdapter(this, bodyPartString,startTimes);
                    setListAdapter(adapter);

                } else if( 1 == eventCursor.getCount() ) {
                    eventCursor.moveToNext();
                    ArrayList<String> bodyPartString = new ArrayList<String>();
                    
                    String bodyPart = getBodyPartString( eventCursor.getString( Constants.EVENT_TITLE_INDEX ).substring( 11 ) );
                    if (eventCursor.getString( Constants.EVENT_DESC_INDEX ).contains("Completed"))
                    	bodyPart = bodyPart + " Done";	
                    bodyPartString.add(bodyPart);
                    Long startTime = eventCursor.getLong( Constants.EVENT_START_INDEX );
                    ArrayList<Long> startTimes = new ArrayList<Long>();
                    startTimes.add(startTime);
                    EventArrayAdapter adapter = new EventArrayAdapter(this, bodyPartString,startTimes);
                    setListAdapter(adapter);


                } else {
                    Log.d( "Venus", Integer.toString( eventCursor.getCount() ) );
                    Log.d( "Venus", "No treatments scheduled today" );
                    return;
                }
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
