package com.Venus.NakedSkin;

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

        Button tutorialButton = (Button)findViewById(R.id.viewTutorialButton);
        tutorialButton.setOnClickListener(this);
        Button applyButton = (Button)findViewById(R.id.applySettingButton);
        applyButton.setOnClickListener(this);
        
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
    
    public void onBackPressed(){
    	//This is to prevent user from accidently exiting the app
    	//pressing Home will exit the app
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.schedulemenu:
            startActivity( new Intent( getApplicationContext(), ScheduleActivity.class ) );
            finish();
            return true;
        case R.id.treatmentmenu:
        	startActivity( new Intent( getApplicationContext(), TreatmentActivity.class ) );
        	finish();
            return true;
        case R.id.howtomenu:
        	startActivity( new Intent( getApplicationContext(), HowtoActivity.class ) );
        	finish();
            return true;
        case R.id.homemenu:
        	Intent intent =  new Intent( getApplicationContext(), TutorialActivity.class );
        	intent.putExtra("first", false);
        	startActivity(intent);
        	finish();
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
        case R.id.viewTutorialButton :
        	Intent intent = new Intent( getApplicationContext(), TutorialActivity.class );
        	startActivity( intent );
        	finish();
        	break;
        default:
            break;
        }
    }
}
