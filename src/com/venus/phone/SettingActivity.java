package com.venus.phone;

import android.app.Activity;
import android.os.Bundle;
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
                R.array.sessions_array,
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
