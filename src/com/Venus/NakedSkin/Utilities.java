package com.Venus.NakedSkin;

import java.util.Date;
import java.util.TimeZone;

import Utility.Event;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.text.format.DateUtils;
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
        String id, calendar_id;
        if( Integer.parseInt( android.os.Build.VERSION.SDK ) >= 14 ) {
            id = "calendar_id";
        } else {
            id = "Calendars._id";
        }

        Uri.Builder builder;
        if( Integer.parseInt( android.os.Build.VERSION.SDK ) >= 8 ) {
            builder = Uri.parse( "content://com.android.calendar/instances/when" ).buildUpon();
        } else {
            builder = Uri.parse( "content://calendar/instances/when" ).buildUpon();
        }

        long now = new Date().getTime();
        ContentUris.appendId( builder, now - DateUtils.WEEK_IN_MILLIS * 104 );
        ContentUris.appendId( builder, now + DateUtils.WEEK_IN_MILLIS * 104 );

        calendar_id = Long.toString( vdb.getCalendarId() );
        vdb.close();
        return cr.query( builder.build(),
                         new String[] { "title", "description","dtstart", "end", "allDay"},
                         "(" + id + " = ?)",
                         new String[] { calendar_id },
                         "startDay ASC, startMinute ASC" );
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
//        cv.put( "hasAlarm", 1);
        cv.put( "dtend", e.end );
        cv.put( "eventTimezone", TimeZone.getDefault().getDisplayName() );

        if( Integer.parseInt( android.os.Build.VERSION.SDK ) >= 8) {
            uri = "content://com.android.calendar/events";
        } else {
            uri = "content://calendar/events";
        }
        vdb.close();
        cr.insert( Uri.parse( uri ), cv);
    }

}
