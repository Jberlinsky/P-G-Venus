package com.Venus.NakedSkin;

import java.util.Calendar;

import android.content.Context;

/**
 * An event class that encapsulates all functionality of a calendar event
 * @author Jingran Wang
 *
 */
public class Event {
    public String title;
    public String description;
    public long start;
    public long end;
    public int day;
    public int month;
    public int year;
    public int hour;
    public int minute;
    public int AMPM;

    /**
     * This constructor takes a title, description, and a start time.
     * @param t
     * @param d
     * @param s
     */
    public Event( String t, String d, long s ) {
        title       = t;
        description = d;

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis( s );

        day    = c.get( Calendar.DATE );
        month  = c.get( Calendar.MONTH );
        year   = c.get( Calendar.YEAR );
        hour   = c.get( Calendar.HOUR_OF_DAY );
        AMPM   = c.get( Calendar.AM_PM );
        minute = c.get( Calendar.MINUTE );
    }

    /**
     * This constructor takes an ending time as well
     * @param t
     * @param d
     * @param s
     * @param e
     */
    public Event( String t, String d, long s, long e ) {
        title       = t;
        description = d;
        start       = s;
        end         = e;
    }

    /**
     * This is a generic event that is obtained just to mark it as complete
     * @param bodyPart
     * @param startTime
     */
    public Event( Context c, CharSequence bodyPart, Long startTime ) {
        title = c.getString( R.string.event_title_prefix ) + bodyPart + c.getString( R.string.event_title_suffix );
        start = startTime;
    }

    /**
     * This is a copy constructor
     * @param e
     */
    public Event( Event e ) {
        title       = e.title;
        description = e.description;
        start       = e.start;
        end         = e.end;
    }

}
