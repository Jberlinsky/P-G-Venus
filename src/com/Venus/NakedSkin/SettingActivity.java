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
import android.widget.Toast;

public class SettingActivity extends Activity implements OnClickListener {

    int mod;
    int value;

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

        ((Button)findViewById( R.id.applySettingButton )).setOnClickListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( this,
                R.array.reminder_array,
                android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        Spinner sessionSpinner = (Spinner)findViewById( R.id.daySpinner );
        sessionSpinner.setAdapter(adapter);
        sessionSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
        public void onItemSelected( AdapterView<?> parent, View arg1, int pos, long id ) {
            switch( pos ) {
            case 0:
                value = 5;
                mod = Constants.MINUTE;
                break;
            case 1:
                value = 1;
                mod = Constants.HOUR;
                break;
            case 2:
                value = 1;
                mod = Constants.DAY;
                break;
            case 3:
                value = 2;
                mod = Constants.DAY;
                break;
            case 4:
                value = 3;
                mod = Constants.DAY;
                break;
            case 5:
                value = 1;
                mod = Constants.WEEK;
                break;
            default:
                break;
            }
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
            VenusDb vdb = new VenusDb( this );
            vdb.setAlarm( mod, value );
            vdb.close();
            String toastText = "Default reminder period set to ";
            switch( mod ) {
            case Constants.MINUTE:
                toastText += value + "minutes";
                break;
            case Constants.HOUR:
                toastText += value + "hour";
                break;
            case Constants.DAY:
                toastText += value + "day";
                break;
            case Constants.WEEK:
                toastText += value + "week";
                break;
            default:
                break;
            }
            Toast.makeText( this.getApplicationContext(), toastText, Toast.LENGTH_SHORT ).show();
            break;
        case R.id.viewTutorialButton :
            Intent intent = new Intent( getApplicationContext(), TutorialActivity.class );
            intent.putExtra("first", true);
            startActivity( intent );
            finish();
            break;
        default:
            break;
        }
    }
}
