package com.Venus.NakedSkin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class VenusDb {
    private static final String TAG = "VenusDB";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    private static final String KEY_ID = "_ID";
    private static final String KEY_FIRST_RUN = "FIRST_RUN";
    private static final String KEY_ALARM_MOD = "ALARM_MOD";
    private static final String KEY_ALARM_VALUE = "ALARM_VALUE";
    private static final String KEY_CALENDAR_INDEX = "CALENDAR_INDEX";

    private static final String DB_NAME = "VenusDb";
    private static final int DB_VERSION = 3; //TODO Reset version to 1 for release

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

    private static final String PREFS_NAME = "PG_VENUS_PREFS";

    private class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper( Context context ) {
            super(context, DB_NAME, null, DB_VERSION);
        }
        public void onCreate( SQLiteDatabase db ) {
            db.execSQL( DB_TABLE_CREATE );
            VenusDb.setFirstRun( db );
            ContentValues storedValues = new ContentValues();
            storedValues.put( KEY_ALARM_MOD, 0 );
            storedValues.put( KEY_ALARM_VALUE, 5 );
            storedValues.put( KEY_CALENDAR_INDEX, -1 );
            db.insert( DB_TABLE, null, storedValues );
        }
        public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
            Log.w( TAG, "Upgrading database from version " +
                        oldVersion +
                        " to " +
                        newVersion +
                        ", which will destroy all old data" );
            db.execSQL( DB_TABLE_DROP );
            onCreate( db );
        }
    }

    public VenusDb( Context c ) {
        mContext = c;
        open();
    }

    public void setCalendarId( Long cal_id ) {
        ContentValues storedValues = new ContentValues();
        storedValues.put( KEY_CALENDAR_INDEX, cal_id );
        mDatabase.update( DB_TABLE, storedValues, null, null );
    }

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

    public void setAlarm( int mod, int value ) {
        ContentValues storedValues = new ContentValues();
        storedValues.put( KEY_ALARM_MOD, mod );
        storedValues.put( KEY_ALARM_VALUE, value );
        mDatabase.update( DB_TABLE, storedValues, null, null );
    }

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

    public static void setFirstRun( SQLiteDatabase db ) {
        ContentValues storedValues = new ContentValues();
        storedValues.put( KEY_FIRST_RUN, 0 );
        db.insert( DB_TABLE, null, storedValues );
    }

    public void setFirstRun() {
        mDatabase.execSQL( DB_TABLE_DROP );
        mDatabase.execSQL( DB_TABLE_CREATE );
        ContentValues storedValues = new ContentValues();
        storedValues.put( KEY_FIRST_RUN, 0 );

        mDatabase.insert( DB_TABLE, null, storedValues );
    }

    public VenusDb open() throws SQLException {
        mDbHelper = new DatabaseHelper( mContext );
        mDatabase = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
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
     *
     */

    private SharedPreferences getSharedPreferences(Context ctx)
    {
      return ctx.getSharedPreferences(PREFS_NAME, 0);
    }

    public void createDefaultPreferences(Context ctx) {
      SharedPreferences settings = getSharedPreferences(ctx);
      SharedPreferences.Editor editor = settings.edit();

      editor.putBoolean("isFirstTreatmentReminder", true);
      editor.putBoolean("hasSwitchedToMaintenence", false);

      editor.putInt("underarmBikiniTreatmentLength", Constants.TEN_MINUTES);
      editor.putInt("upperLowerLegTreatmentLength", Constants.FOURTY_FIVE_MINUTES);
    }

    public void setBooleanPreference(Context ctx, String key, boolean value)
    {
      SharedPreferences settings = getSharedPreferences(ctx);
      SharedPreferences.Editor editor = settings.edit();
      editor.putBoolean(key, value);
    }

    public boolean isFirstTreatmentReminder(Context ctx)
    {
      SharedPreferences settings = getSharedPreferences(ctx);
      return settings.getBoolean("isFirstTreatmentReminder", true);

    }

    public void setIsNotFirstTreatmentReminder(Context ctx)
    {
      this.setBooleanPreference(ctx, "isFirstTreatmentReminder", false);
    }

    public void setIntegerPreference(Context ctx, String key, int value)
    {
      SharedPreferences settings = getSharedPreferences(ctx);
      SharedPreferences.Editor editor = settings.edit();
      editor.putInt(key, value);
    }

    public int getIntegerPreference(Context ctx, String key, int default_value)
    {
      SharedPreferences settings = getSharedPreferences(ctx);
      return settings.getInt(key, default_value);
    }

    public int getUnderarmBikiniTreatmentLength(Context ctx)
    {
      return this.getIntegerPreference(ctx, "underarmBikiniTreatmentLength", Constants.TEN_MINUTES);
    }
    public int getUpperLowerLegTreatmentLength(Context ctx)
    {
      return this.getIntegerPreference(ctx, "upperLowerLegTreatmentLength", Constants.FOURTY_FIVE_MINUTES);
    }
}
