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

    private Handler splashHandler = new Handler() {
        public void handleMessage( Message msg ) {
            Intent intent = null;
            switch( msg.what ) {
            case FIRST_RUN:
                intent = new Intent( getApplicationContext(), TutorialActivity.class );
                intent.putExtra( Constants.FIRST, true );
                intent.putExtra( Constants.HAS_CALENDARS, (false == hasCalendars) ? false : true );
                break;
            case RUN:
                intent = new Intent( getApplicationContext(), TutorialActivity.class );
                intent.putExtra( Constants.FIRST, false );
                intent.putExtra( Constants.HAS_CALENDARS, (false == hasCalendars) ? false : true );
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
