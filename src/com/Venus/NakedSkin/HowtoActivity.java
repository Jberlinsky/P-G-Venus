package com.Venus.NakedSkin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

/**
 * Links to How-To Videos on YouTube
 * @author Jingran Wang
 *
 */
public class HowtoActivity extends Activity implements View.OnClickListener {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.howto);

        ((ImageButton)findViewById( R.id.howtoVideoButton )).setOnClickListener( this );
        ((ImageButton)findViewById( R.id.trVideoButton )).setOnClickListener( this );
        ((ImageButton)findViewById( R.id.workVideoButton )).setOnClickListener( this );
    }

    /**
     * Create the menu
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.tabmenu, menu);
        return true;
    }

    /**
     * Handle menu clicks
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.schedulemenu:
            startActivity( new Intent( this, ScheduleActivity.class ) );
            finish();
            return true;
        case R.id.treatmentmenu:
            startActivity( new Intent( this, TreatmentActivity.class ) );
            finish();
            return true;
        case R.id.settingmenu:
            startActivity( new Intent( this, SettingActivity.class ) );
            finish();
            return true;
        case R.id.homemenu:
            startActivity( new Intent( this, TutorialActivity.class ).putExtra( Constants.FIRST, false ) );
            finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Prevents user from accidentally exiting the app.
     * User needs to press "home"
     * Could be changed to "Do you want to exit?" dialog.
     */
    public void onBackPressed(){ }

    /**
     * Handles clicks for the how-to videos
     */
    public void onClick( View v ) {
        String url = "";
        switch( v.getId() ) {
        case R.id.howtoVideoButton :
            url = getString( R.string.howtovideo_url );
            break;
        case R.id.trVideoButton :
            url = getString( R.string.trvideo_url );
            break;
        case R.id.workVideoButton :
            url = getString( R.string.workvideo_url );
            break;
        default :
            break;
        }
        startActivity( new Intent( Intent.ACTION_VIEW,
                                   Uri.parse( url ) ) );
    }
}
