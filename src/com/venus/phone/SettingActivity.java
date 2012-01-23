package com.venus.phone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SettingActivity extends Activity implements OnClickListener {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        /* Buttons
        settingsOk
        settingsInstructions
        */
        /* EditText
         * settingsEmailAddress
         */

        //TODO get email saving working with shared preferences
        ((Button)findViewById( R.id.applySettingButton )).setOnClickListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( this,
                R.array.reminder_array,
                android.R.layout.simple_spinner_item );
		adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
		Spinner sessionSpinner = (Spinner)findViewById( R.id.daySpinner );
		sessionSpinner.setAdapter(adapter);
		sessionSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
		public void onItemSelected( AdapterView<?> parent, View arg1, int pos, long id ) {
		
		}
		public void onNothingSelected( AdapterView<?> arg0 ) { }
		} );
        
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
        	//startActivity( new Intent( getApplicationContext(), SettingActivity.class ) );
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    public void onClick(View v) {
        switch( v.getId() ) {
        case R.id.applySettingButton :
        	//TODO : Add something to apply to default calendar reminder days 
        	
            /*startActivity( new Intent( getApplicationContext(), TutorialActivity.class ) );
            finish();*/
            break;
        default:
            break;
        }
    }
}
