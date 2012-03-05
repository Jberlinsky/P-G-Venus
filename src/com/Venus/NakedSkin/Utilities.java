package com.Venus.NakedSkin;

import java.util.Date;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.format.DateUtils;

public class Utilities {

    public static Cursor queryForCalendars( Context c ) {
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
        return cr.query( Uri.parse( uri ),
                         new String[]{ "_id", displayName },
                         "(account_type = ?)",
                         new String[] { "com.google" },
                         null );
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

}
