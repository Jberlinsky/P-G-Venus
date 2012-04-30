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
    //Bikini and underarm
    int valueBU;
    //Upperleg and Lowerleg
    int valueLU;


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

        //Bikini Underarm
        ArrayAdapter<CharSequence> adapterBU = ArrayAdapter.createFromResource( this,
                R.array.reminder_array,
                android.R.layout.simple_spinner_item );
        adapterBU.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        Spinner sessionSpinnerBU = (Spinner)findViewById( R.id.daySpinner1 );
        sessionSpinnerBU.setAdapter(adapterBU);
        int UABA = (new VenusDb(this)).getUnderarmBikiniTreatmentLength(this);
        if (UABA == 10*60*1000)
        	sessionSpinnerBU.setSelection(0);
        else if (UABA == 15*60*1000)
        	sessionSpinnerBU.setSelection(1);
        else if (UABA == 30*60*1000)
        	sessionSpinnerBU.setSelection(2);
        else if (UABA == 45*60*1000)
        	sessionSpinnerBU.setSelection(3);
        else if (UABA == 60*60*1000)
        	sessionSpinnerBU.setSelection(4);
        else
        	sessionSpinnerBU.setSelection(0);
        
        sessionSpinnerBU.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
        public void onItemSelected( AdapterView<?> parent, View arg1, int pos, long id ) {
            switch( pos ) {
            case 0:
                valueBU = 10;
                mod = Constants.MINUTE;
                break;
            case 1:
                valueBU = 15;
                mod = Constants.MINUTE;
                break;
            case 2:
                valueBU = 30;
                mod = Constants.MINUTE;
                break;
            case 3:
                valueBU = 45;
                mod = Constants.MINUTE;
                break;
            case 4:
                valueBU = 60;
                mod = Constants.MINUTE;
                break;
            default:
                break;
            }
        }
        public void onNothingSelected( AdapterView<?> arg0 ) {valueBU = 10; }
        } );

        //UpperLower Leg
        ArrayAdapter<CharSequence> adapterLU = ArrayAdapter.createFromResource( this,
                R.array.reminder_array,
                android.R.layout.simple_spinner_item );
        adapterLU.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        Spinner sessionSpinnerLU = (Spinner)findViewById( R.id.daySpinner2 );
        sessionSpinnerLU.setAdapter(adapterLU);
        int LULU = (new VenusDb(this)).getUpperLowerLegTreatmentLength(this);
        if (LULU == 10*60*1000)
        	sessionSpinnerLU.setSelection(0);
        else if (LULU == 15*60*1000)
        	sessionSpinnerLU.setSelection(1);
        else if (LULU == 30*60*1000)
        	sessionSpinnerLU.setSelection(2);
        else if (LULU == 45*60*1000)
        	sessionSpinnerLU.setSelection(3);
        else if (LULU == 60*60*1000)
        	sessionSpinnerLU.setSelection(4);
        else
        	sessionSpinnerLU.setSelection(0);
        sessionSpinnerLU.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
        
        public void onItemSelected( AdapterView<?> parent, View arg1, int pos, long id ) {
            switch( pos ) {
            case 0:
                valueLU = 10;
                mod = Constants.MINUTE;
                break;
            case 1:
                valueLU = 15;
                mod = Constants.MINUTE;
                break;
            case 2:
                valueLU = 30;
                mod = Constants.MINUTE;
                break;
            case 3:
                valueLU = 45;
                mod = Constants.MINUTE;
                break;
            case 4:
                valueLU = 60;
                mod = Constants.MINUTE;
                break;
            default:
                break;
            }
        }
        public void onNothingSelected( AdapterView<?> arg0 ) {valueLU = 45; }
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
            //TODO This part doesn't work, but I'm keeping the code here in case someone wants to see it
            //To bad it would have been nice so, New stuff instead

            (new VenusDb(this)).setIntegerPreference(this,"underarmBikiniTreatmentLength", valueBU*60*1000);
            (new VenusDb(this)).setIntegerPreference(this,"upperLowerLegTreatmentLength", valueLU*60*1000);
            Toast.makeText( this.getApplicationContext(), "Settings have been saved!", Toast.LENGTH_SHORT ).show();
            /*
            VenusDb vdb = new VenusDb( this );
            Log.d( "Venus", Long.toString( vdb.getCalendarId() ) );
            vdb.setAlarm( mod, valueBU );
            vdb.close();
            String toastText = "Default reminder period set to ";
            switch( mod ) {
            case Constants.MINUTE:
                toastText += valueBU + "minutes";
                break;
            case Constants.HOUR:
                toastText += valueBU + "hour";
                break;
            case Constants.DAY:
                toastText += valueBU + "day";
                break;
            case Constants.WEEK:
                toastText += valueBU + "week";
                break;
            default:
                break;
            }
            Toast.makeText( this.getApplicationContext(), toastText, Toast.LENGTH_SHORT ).show();
            */

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
