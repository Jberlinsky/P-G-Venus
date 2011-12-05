package com.venus.phone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
        ((Button)findViewById( R.id.settingsInstructions )).setOnClickListener(this);

    }

    public void onClick(View v) {
        switch( v.getId() ) {
        case R.id.settingsInstructions :
            startActivity( new Intent( getApplicationContext(), TutorialActivity.class ) );
            finish();
            break;
        default:
            break;
        }
    }
}
