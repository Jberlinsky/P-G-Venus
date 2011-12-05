package com.venus.phone;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Loading screen for startup.  Displays the official logo.
 * @author Jingran Wang
 *
 */
public class LoadingActivity extends Activity {
    private final String TAG = "LoadingActivity";

    private final int FIRST_RUN = 100;
    private final int RUN = 500;

    private Handler splashHandler = new Handler() {
        public void handleMessage( Message msg ) {
            Intent intent = null;
            switch( msg.what ) {
            case FIRST_RUN:
                intent = new Intent( getApplicationContext(), TutorialActivity.class );
                Log.d( TAG, "First run detected, running tutorial" );
                break;
            case RUN:
                intent = new Intent( getApplicationContext(), ScheduleActivity.class );
                Log.d( TAG, "Not first run, running normal diary activity" );
                break;
            default:
                break;
            }
            startActivity( intent );
            super.handleMessage( msg );
            finish();
         }
    };

    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.loading );

        Message msg = new Message();
        VenusDb vdb = new VenusDb( this );
        vdb.setFirstRun();
        msg.what = (vdb.isFirstRun()) ? FIRST_RUN : RUN;
        vdb.close();
        splashHandler.sendMessageDelayed( msg, Constants.SPLASH_TIMEOUT );
    }
}
