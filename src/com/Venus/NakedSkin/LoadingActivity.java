package com.Venus.NakedSkin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Loading screen for startup.  Displays the official logo.
 * @author Jingran Wang
 *
 */
public class LoadingActivity extends Activity {
    boolean hasCalendars = true;

    private final int FIRST_RUN = 100;
    private final int RUN = 500;

    /**
     * A handler to start TutorialActivity when the timer runs out
     */
    private Handler splashHandler = new Handler() {
        public void handleMessage( Message msg ) {
            startActivity( new Intent( getApplicationContext(), TutorialActivity.class )
                .putExtra( Constants.FIRST, (FIRST_RUN == msg.what) ? true : false )
                .putExtra( Constants.HAS_CALENDARS, hasCalendars ) );
            super.handleMessage( msg );
            finish();
         }
    };

    /**
     * Checks the DB for a calendar, and sends it to the splashHandler
     */
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.loading );

        Message msg = new Message();
        VenusDb vdb = new VenusDb( this );
        if( vdb.isFirstRun() ) {
            msg.what = FIRST_RUN;
            vdb.createDefaultPreferences();
        } else {
            msg.what = RUN;
        }
        vdb.close();
        splashHandler.sendMessageDelayed( msg, 2000 );
        if( null == Utilities.queryForCalendars( this ) ) {
            hasCalendars = false;
        }
    }
}
