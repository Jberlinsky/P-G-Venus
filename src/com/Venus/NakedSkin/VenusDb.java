package com.Venus.NakedSkin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class VenusDb {
    private static final String TAG = "VenusDB";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    private static final String KEY_ID = "_ID";
    private static final String KEY_FIRST_RUN = "FIRST_RUN";

    private static final String DB_NAME = "VenusDb";
    private static final int DB_VERSION = 1; //TODO Reset version to 1 for release

    private static final String DB_TABLE = "VenusTable";
    private static final String DB_TABLE_CREATE =
        "CREATE TABLE " +
        DB_TABLE +
        " (" +
        KEY_ID +
        " integer primary key autoincrement, " +
        KEY_FIRST_RUN +
        " integer not null );";
    private static final String DB_TABLE_DROP =
        "DROP TABLE IF EXISTS " +
        DB_TABLE;

    private class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper( Context context ) {
            super(context, DB_NAME, null, DB_VERSION);
        }
        public void onCreate( SQLiteDatabase db ) {
            db.execSQL( DB_TABLE_CREATE );
            VenusDb.setFirstRun( db );
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
}
