package com.Venus.NakedSkin;

import java.util.Calendar;
import java.util.TimeZone;

import Utility.Event;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.util.Log;

public class Utilities {

    public static Cursor queryForCalendars( Context c ) {
        Cursor cursor;
        ContentResolver cr = c.getContentResolver();
        String uri, displayName;
        if( Integer.parseInt( android.os.Build.VERSION.SDK ) >= 14 ) {
            uri         = "content://com.android.calendar/calendars";
            displayName = "calendar_displayName";
        } else if( Integer.parseInt( android.os.Build.VERSION.SDK ) >= 8 ) {
            uri         = "content://com.android.calendar/calendars";
            displayName = "displayName";
        } else {
            uri         = "content://calendar/calendars";
            displayName = "displayName";
        }

        try {
            cursor = cr.query( Uri.parse( uri ),
                               new String[]{ "_id", displayName },
                               "(account_type = ?)",
                               new String[] { "com.google" },
                               null );
            return cursor;
        } catch( SQLiteException e ) {
            Log.d( "Venus", e.getMessage() );
            cursor = cr.query( Uri.parse( uri ),
                               new String[]{ "_id", displayName },
                               null,
                               null,
                               null );
            return cursor;
        }
    }

    public static Cursor queryEvents( Context c ) {
        VenusDb vdb = new VenusDb( c );
        ContentResolver cr = c.getContentResolver();
        Cursor cursor;

        String uri = ( Integer.parseInt( android.os.Build.VERSION.SDK ) >= 8 ) ?
                     "content://com.android.calendar/events" :
                     "content://calendar/events";
        Uri.Builder builder = Uri.parse( uri ).buildUpon();

        Calendar cal = Calendar.getInstance();
        cal.add( Calendar.MONTH, -6 );
        long start = cal.getTimeInMillis();
        cal.add( Calendar.YEAR, 1 );
        long end = cal.getTimeInMillis();

        try {
            cursor = cr.query( builder.build(),
                               new String[] { "title", "description", "dtstart" },
                               "( calendar_id = ? AND dtstart BETWEEN ? AND ? AND title LIKE ? )",
                               new String[] { Long.toString( vdb.getCalendarId() ),
                                              Long.toString( start ),
                                              Long.toString( end ),
                                              "Naked%" },
                               "dtstart ASC" );
            vdb.close();
            return cursor;
        } catch( SQLiteException e ) {
            Log.d( "Venus", e.getMessage() );
            cursor = cr.query( builder.build(),
                               new String[] { "title", "description", "dtstart" },
                               "( Calendars._id = ? AND dtstart BETWEEN ? AND ? AND title LIKE ? )",
                               new String[] { Long.toString( vdb.getCalendarId() ),
                                              Long.toString( start ),
                                              Long.toString( end ),
                                              "Naked%" },
                               "dtstart ASC" );
            vdb.close();
            return cursor;
        }
    }

    public static Cursor queryDayEvents( Context c, Calendar day) {
        VenusDb vdb = new VenusDb( c );
        ContentResolver cr = c.getContentResolver();
        Cursor cursor;

        String uri = ( Integer.parseInt( android.os.Build.VERSION.SDK ) >= 8 ) ?
                     "content://com.android.calendar/events" :
                     "content://calendar/events";
        Uri.Builder builder = Uri.parse( uri ).buildUpon();

        Calendar cal = day;
        int y = cal.get( Calendar.YEAR );
        int m = cal.get( Calendar.MONTH );
        int d = cal.get( Calendar.DATE );
        cal.clear();
        cal.set( y, m, d );
        long start = cal.getTimeInMillis();
        cal.add( Calendar.DATE, 1 );
        long end = cal.getTimeInMillis();

        try {
            cursor = cr.query( builder.build(),
                               new String[] { "title", "description", "dtstart" },
                               "( calendar_id = ? AND dtstart BETWEEN ? AND ? AND title LIKE ? )",
                               new String[] { Long.toString( vdb.getCalendarId() ),
                                              Long.toString( start ),
                                              Long.toString( end ),
                                              "Naked%" },
                               "dtstart ASC" );
            vdb.close();
            return cursor;
        } catch( SQLiteException e ) {
            Log.d( "Venus", e.getMessage() );
            cursor = cr.query( builder.build(),
                               new String[] { "title", "description", "dtstart" },
                               "( Calendars._id = ? AND dtstart BETWEEN ? AND ? AND title LIKE ? )",
                               new String[] { Long.toString( vdb.getCalendarId() ),
                                              Long.toString( start ),
                                              Long.toString( end ),
                                              "Naked%" },
                               "dtstart ASC" );
            vdb.close();
            return cursor;
        }
    }

    //this function ignores "Complete" at the end of events
    public static Cursor queryTodaysIncompleteEvents( Context c ) {
        VenusDb vdb = new VenusDb( c );
        ContentResolver cr = c.getContentResolver();
        Cursor cursor;

        String uri = ( Integer.parseInt( android.os.Build.VERSION.SDK ) >= 8 ) ?
                     "content://com.android.calendar/events" :
                     "content://calendar/events";
        Uri.Builder builder = Uri.parse( uri ).buildUpon();

        Calendar cal = Calendar.getInstance();
        int y = cal.get( Calendar.YEAR );
        int m = cal.get( Calendar.MONTH );
        int d = cal.get( Calendar.DATE );
        cal.clear();
        cal.set( y, m, d );
        long start = cal.getTimeInMillis();
        cal.add( Calendar.DATE, 1 );
        long end = cal.getTimeInMillis();

        try {
            cursor = cr.query( builder.build(),
                               new String[] { "title", "description", "dtstart" },
                               "( calendar_id = ? AND dtstart BETWEEN ? AND ? AND title LIKE ? AND description NOT LIKE ? )",
                               new String[] { Long.toString( vdb.getCalendarId() ),
                                              Long.toString( start ),
                                              Long.toString( end ),
                                              "Naked%",
                                              "%Complete!" },
                               "dtstart ASC" );
            vdb.close();
            return cursor;
        } catch( SQLiteException e ) {
            Log.d( "Venus", e.getMessage() );
            cursor = cr.query( builder.build(),
                               new String[] { "title", "description", "dtstart" },
                               "( Calendars._id = ? AND dtstart BETWEEN ? AND ? AND title LIKE ? AND description NOT LIKE ? )",
                               new String[] { Long.toString( vdb.getCalendarId() ),
                                              Long.toString( start ),
                                              Long.toString( end ),
                                              "Naked%",
                                              "%Complete!" },
                               "dtstart ASC" );
            vdb.close();
            return cursor;
        }
    }

    public static void addToCalendar( Context c, Event e ) {
        VenusDb vdb = new VenusDb( c );
        ContentResolver cr = c.getContentResolver();
        ContentValues cv = new ContentValues();
        String uri;

        cv.put( "calendar_id", vdb.getCalendarId() );
        cv.put( "title", e.title );
        cv.put( "description", e.description );
        cv.put( "dtstart", e.start );
        cv.put( "dtend", e.end );
        cv.put( "eventTimezone", TimeZone.getDefault().getDisplayName() );

        vdb.close();

        if( Integer.parseInt( android.os.Build.VERSION.SDK ) >= 8) {
            uri = "content://com.android.calendar/events";
        } else {
            uri = "content://calendar/events";
        }
        cr.insert( Uri.parse( uri ), cv );
    }

    public static void markAsComplete( Context c, Event e ) {
        //first we need to find the event again
        VenusDb vdb = new VenusDb( c );
        int calendarId = vdb.getCalendarId();
        vdb.close();

        String calendarIdString;

        ContentResolver cr = c.getContentResolver();
        Cursor cursor;

        String uri = ( Integer.parseInt( android.os.Build.VERSION.SDK ) >= 8 ) ?
                     "content://com.android.calendar/events" :
                     "content://calendar/events";
        Uri.Builder builder = Uri.parse( uri ).buildUpon();

        try {
            calendarIdString = "calendar_id";
            cursor = cr.query( builder.build(),
                               new String[] { "title", "description", "dtstart" },
                               "( " + calendarIdString + " = ? AND dtstart = ? AND title LIKE ? )",
                               new String[] { Long.toString( calendarId ),
                                              Long.toString( e.start ),
                                              "Naked%" },
                               "dtstart ASC" );
        } catch( SQLiteException sqle ) {
            calendarIdString = "Calendars._id";
            Log.d( "Venus", sqle.getMessage() );
            cursor = cr.query( builder.build(),
                               new String[] { "title", "description", "dtstart" },
                               "( " + calendarIdString + " = ? AND dtstart = ? AND title LIKE ? )",
                               new String[] { Long.toString( calendarId ),
                                              Long.toString( e.start ),
                                              "Naked%" },
                               "dtstart ASC" );
        }
        //now we have a cursor to the one event, hopefully
        cursor.moveToFirst();

        ContentValues cv = new ContentValues();

        cv.put( "description", cursor.getString( 1 ) + " Complete!" );
        e.description = cursor.getString( 1 );
        try {
            cr.update( builder.build(),
                       cv,
                       "( " + calendarIdString + " = ? AND dtstart = ? AND title = ? AND description = ? )",
                       new String[] { Long.toString( calendarId ),
                                      Long.toString( e.start ),
                                      e.title,
                                      e.description } );
        } catch( SQLiteException sqle ) {
            //serious exception here, we are supposed to catch the correct ID string previously
            Log.d( "Venus", sqle.getMessage() );
        }
    }

}
