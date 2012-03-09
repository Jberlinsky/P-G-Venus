package Utility;

public class Event {
    public String title;
    public String description;
    public long start;
    public long end;

    public Event() { }

    public Event( String t, String d, long s, long e ) {
        title = t;
        description = d;
        start = s;
        end = e;
    }

    public int day;
    public int month;
    public int year;
    public int hour;
    public int minute;
    public int AMPM;
}
