package com.Venus.NakedSkin;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VenusDb {

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    private static final String KEY_ID             = "_ID";
    private static final String KEY_FIRST_RUN      = "FIRST_RUN";
    private static final String KEY_ALARM_MOD      = "ALARM_MOD";
    private static final String KEY_ALARM_VALUE    = "ALARM_VALUE";
    private static final String KEY_CALENDAR_INDEX = "CALENDAR_INDEX";

    private static final String DB_NAME = "VenusDb";
    private static final int DB_VERSION = 4;

    private static final String DB_TABLE = "VenusTable";
    private static final String DB_TABLE_CREATE =
            "CREATE TABLE " +
                    DB_TABLE +
                    " (" +
                    KEY_ID +
                    " integer primary key autoincrement, " +
                    KEY_FIRST_RUN +
                    " integer not null, " +
                    KEY_ALARM_MOD +
                    " integer, " +
                    KEY_ALARM_VALUE +
                    " integer, " +
                    KEY_CALENDAR_INDEX +
                    " long );";
    private static final String DB_TABLE_DROP =
            "DROP TABLE IF EXISTS " +
                    DB_TABLE;

    private static final String PREFS_NAME                   = "PG_VENUS_PREFS";
    private static final String FIRST_TREATMENT_BOOL_PREF    = "isFirstTreatmentReminder";
    private static final String MAINTENANCE_BOOL_PREF        = "hasSwitchedToMaintenence";
    private static final String UA_BIKINI_TREATMENT_INT_PREF = "underarmBikiniTreatmentLength";
    private static final String UL_LL_TREATMENT_INT_PREF     = "upperLowerLegTreatmentLength";
    private static final String WHOLE_TREATMENT_INT_PREF     = "wholeBodyTreatmentLength";

    /**
     * Default database helper, modified onCreate()
     * @author Jingran Wang
     *
     */
    private class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper( Context context ) {
            super(context, DB_NAME, null, DB_VERSION);
        }
        public void onCreate( SQLiteDatabase db ) {
            db.execSQL( DB_TABLE_CREATE );
            VenusDb.setFirstRun( db );
            ContentValues storedValues = new ContentValues();
            storedValues.put( KEY_ALARM_MOD, 0 );       //Currently unused
            storedValues.put( KEY_ALARM_VALUE, 5 );     //Currently unused
            storedValues.put( KEY_CALENDAR_INDEX, -1 );	//Index of -1 as invalid calendar ID
            db.insert( DB_TABLE, null, storedValues );
        }
        public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
            /*Log.w( TAG, "Upgrading database from version " +
                        oldVersion +
                        " to " +
                        newVersion +
                        ", which will destroy all old data" );*/
            db.execSQL( DB_TABLE_DROP );
            onCreate( db );
        }
    }

    /**
     * Creates a new DB instance
     * @param c A Context
     */
    public VenusDb( Context c ) {
        mContext = c;
        open();
    }

    /**
     * Helper, opens the DB
     * @return This object
     * @throws SQLException
     */
    public VenusDb open() throws SQLException {
        mDbHelper = new DatabaseHelper( mContext );
        mDatabase = mDbHelper.getWritableDatabase();
        return this;
    }

    /**
     * Closes the DB
     */
    public void close() {
        mDbHelper.close();
    }

    /**
     * Sets the calendar ID
     * @param cal_id Calendar ID user wishes to use
     */
    public void setCalendarId( Long cal_id ) {
        ContentValues storedValues = new ContentValues();
        storedValues.put( KEY_CALENDAR_INDEX, cal_id );
        mDatabase.update( DB_TABLE, storedValues, null, null );
    }

    /**
     * Queries the DB for the calendar ID
     * @return int The calendar ID
     */
    public int getCalendarId() {
        Cursor c = mDatabase.query( DB_TABLE,
                                    new String[] {KEY_CALENDAR_INDEX},
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    "1" );
        c.moveToFirst();
        return c.getInt( 0 );
    }

    /**
     * Currently unused, is supposed to set the alarm.
     * @param mod Mod value (min, hour, etc.)
     * @param value The value
     */
    public void setAlarm( int mod, int value ) {
        ContentValues storedValues = new ContentValues();
        storedValues.put( KEY_ALARM_MOD, mod );
        storedValues.put( KEY_ALARM_VALUE, value );
        mDatabase.update( DB_TABLE, storedValues, null, null );
    }

    /**
     * Currently unused, gets the mod of the alarm setting (min, hour, etc.)
     * @return
     */
    public int getAlarmMod() {
        Cursor c = mDatabase.query( DB_TABLE,
                                    new String[] {KEY_ALARM_MOD},
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    "1" );
        c.moveToFirst();
        return c.getInt( 0 );
    }

    /**
     * Currently unused, gets the alarm value.
     * @return
     */
    public int getAlarmValue() {
        Cursor c = mDatabase.query( DB_TABLE,
                                    new String[] {KEY_ALARM_VALUE},
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    "1" );
        c.moveToFirst();
        return c.getInt( 0 );
    }

    /**
     * Debugging, sets the first run.
     * @param db
     */
    public static void setFirstRun( SQLiteDatabase db ) {
        ContentValues storedValues = new ContentValues();
        storedValues.put( KEY_FIRST_RUN, 0 );
        db.insert( DB_TABLE, null, storedValues );
    }

    /**
     * Query the DB to see if this is the first time we're being run, and set that variable if we are.
     * @return true if first run
     */
    public boolean isFirstRun() {
        Cursor c = mDatabase.query( DB_TABLE,
                                    new String[] {KEY_FIRST_RUN},
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    "1" );
        c.moveToFirst();
        if( 0 == c.getInt( 0 ) ) {
            unsetFirstRun();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Unsets the first run DB variable
     */
    private void unsetFirstRun() {
        ContentValues storedValues = new ContentValues();
        storedValues.put( KEY_FIRST_RUN, 1 );
        mDatabase.update( DB_TABLE, storedValues, KEY_ID + "=1", null );
    }

    /**
     * Gets the shared preferences
     * @return SharedPreferences
     */
    private SharedPreferences getSharedPreferences() {
        return mContext.getSharedPreferences(PREFS_NAME, 0);
    }

    /**
     * Creates the default preferences
     */
    public void createDefaultPreferences() {
        SharedPreferences settings = getSharedPreferences();
        SharedPreferences.Editor editor = settings.edit();

        editor.putBoolean( FIRST_TREATMENT_BOOL_PREF, true);
        editor.putBoolean( MAINTENANCE_BOOL_PREF, false);

        editor.putInt( UA_BIKINI_TREATMENT_INT_PREF, Constants.TEN_MINUTES);
        editor.putInt( UL_LL_TREATMENT_INT_PREF, Constants.FOURTY_FIVE_MINUTES);
        editor.putInt( WHOLE_TREATMENT_INT_PREF, Constants.TWO_HOURS);

        editor.commit();
    }

    /**
     * Sets a boolean preference
     * @param key The key of the preference
     * @param value The value to set
     */
    public void setBooleanPreference(String key, boolean value) {
        SharedPreferences settings = getSharedPreferences();
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * Queries first treatment
     * @return True if this is the first treatment, false otherwise
     */
    public boolean isFirstTreatmentReminder() {
        return getSharedPreferences().getBoolean( FIRST_TREATMENT_BOOL_PREF, true);
    }

    /**
     * Sets the first treatment boolean to be false
     */
    public void setIsNotFirstTreatmentReminder(Context ctx) {
        setBooleanPreference( FIRST_TREATMENT_BOOL_PREF, false);
    }

    /**
     * Sets an integer preference value
     * @param key The key of the preference
     * @param value The value to set
     */
    public void setIntegerPreference(String key, int value) {
        SharedPreferences settings = getSharedPreferences();
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * Returns an integer preference
     * @param key The preference to return
     * @param default_value The default value if unset
     * @return int The value
     */
    public int getIntegerPreference(String key, int default_value) {
        return getSharedPreferences().getInt(key, default_value);
    }

    /**
     * Gets the Underarm and Bikini treatment length preference
     * @return int The value
     */
    public int getUnderarmBikiniTreatmentLength() {
        return getIntegerPreference( UA_BIKINI_TREATMENT_INT_PREF, Constants.TEN_MINUTES);
    }

    /**
     * Gets the whole body treatment length preference
     * @return int The value
     */
    public int getWholeBodyTreatmentLength() {
        return this.getIntegerPreference( WHOLE_TREATMENT_INT_PREF, Constants.TWO_HOURS);
    }

    /**
     * Gets the upper and lower leg treatment lengths
     * @return int The value
     */
    public int getUpperLowerLegTreatmentLength() {
        return this.getIntegerPreference( UL_LL_TREATMENT_INT_PREF, Constants.FOURTY_FIVE_MINUTES);
    }

}
