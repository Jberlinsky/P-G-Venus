package com.venus.phone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class HowtoActivity extends Activity implements View.OnClickListener{
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.howto);

        ((ImageButton)findViewById( R.id.howtoVideoButton )).setOnClickListener( this );
        ((ImageButton)findViewById( R.id.trVideoButton )).setOnClickListener( this );
        ((ImageButton)findViewById( R.id.workVideoButton )).setOnClickListener( this );
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.tabmenu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.schedulemenu:
        	startActivity( new Intent( getApplicationContext(), ScheduleActivity.class ) );
            return true;
        case R.id.treatmentmenu:
        	startActivity( new Intent( getApplicationContext(), TreatmentActivity.class ) );
            return true;
        case R.id.howtomenu:
        	startActivity( new Intent( getApplicationContext(), HowtoActivity.class ) );
            return true;
        case R.id.settingmenu:
        	startActivity( new Intent( getApplicationContext(), SettingActivity.class ) );
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    public void onClick( View v ) {
        switch( v.getId() ) {
        
        case R.id.howtoVideoButton :
            startActivity( new Intent( getApplicationContext(), VideoPlayer.class) );
            break;
        case R.id.trVideoButton :
            startActivity( new Intent( getApplicationContext(), VideoPlayer.class) );
            break;
        case R.id.workVideoButton :
            startActivity( new Intent( getApplicationContext(), VideoPlayer.class) );
            break;
        default :
            break;
        }
    }
    
}
