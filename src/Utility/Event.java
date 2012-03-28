package Utility;

import java.util.Calendar;

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

    public Event( String t, String d, long s, long e ) {
        title       = t;
        description = d;
        start       = s;
        end         = e;
    }

    public Event( CharSequence bodyPart, Long startTime ) {
        title = "Naked Skin " + bodyPart + " treatment reminder";
        start = startTime;
    }

    public Event( Event e ) {
        title       = e.title;
        description = e.description;
        start       = e.start;
        end         = e.end;
    }

}
