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

/**
 * This Activity allows the user to change options and settings
 * Currently only two options, and tutorial view
 * @author Jingran Wang
 *
 */
public class SettingActivity extends Activity implements OnClickListener {

    int valueBU; //Bikini and underarm
    int valueLU; //Upperleg and Lowerleg
    int[] reminder_array = {
            Constants.TEN_MINUTES,
            Constants.FIFTEEN_MINUTES,
            Constants.THIRTY_MINUTES,
            Constants.FOURTY_FIVE_MINUTES,
            Constants.ONE_HOUR
    };

    private VenusDb vdb;

    /**
     * Sets up all of the views
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        vdb = new VenusDb( this );

        ((Button)findViewById( R.id.viewTutorialButton )).setOnClickListener( this );
        ((Button)findViewById( R.id.applySettingButton )).setOnClickListener( this );

        //Bikini Underarm
        ArrayAdapter<CharSequence> adapterBU = ArrayAdapter.createFromResource( this,
                                                                                R.array.reminder_array,
                                                                                android.R.layout.simple_spinner_item );
        adapterBU.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        Spinner sessionSpinnerBU = (Spinner)findViewById( R.id.daySpinner1 );
        sessionSpinnerBU.setAdapter(adapterBU);
        switch( vdb.getUnderarmBikiniTreatmentLength() ) {
        case Constants.FIFTEEN_MINUTES:
            sessionSpinnerBU.setSelection(1);
            break;
        case Constants.THIRTY_MINUTES:
            sessionSpinnerBU.setSelection(2);
            break;
        case Constants.FOURTY_FIVE_MINUTES:
            sessionSpinnerBU.setSelection(3);
            break;
        case Constants.ONE_HOUR:
            sessionSpinnerBU.setSelection(4);
            break;
        default: //10 minute case is same as default
            sessionSpinnerBU.setSelection(0);
            break;
        }

        sessionSpinnerBU.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            public void onItemSelected( AdapterView<?> parent, View arg1, int pos, long id ) {
                valueBU = reminder_array[ pos ];
            }
            public void onNothingSelected( AdapterView<?> arg0 ) {
                valueBU = reminder_array[ 0 ]; //Position of "10 minutes"
            }
        } );

        //UpperLower Leg
        ArrayAdapter<CharSequence> adapterLU = ArrayAdapter.createFromResource( this,
                                                                                R.array.reminder_array,
                                                                                android.R.layout.simple_spinner_item );
        adapterLU.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        Spinner sessionSpinnerLU = (Spinner)findViewById( R.id.daySpinner2 );
        sessionSpinnerLU.setAdapter(adapterLU);
        switch( vdb.getUpperLowerLegTreatmentLength() ) {
        case Constants.FIFTEEN_MINUTES:
            sessionSpinnerLU.setSelection(1);
            break;
        case Constants.THIRTY_MINUTES:
            sessionSpinnerLU.setSelection(2);
            break;
        case Constants.FOURTY_FIVE_MINUTES:
            sessionSpinnerLU.setSelection(3);
            break;
        case Constants.ONE_HOUR:
            sessionSpinnerLU.setSelection(4);
            break;
        default: //10 minute case is same as default
            sessionSpinnerLU.setSelection(0);
            break;
        }

        sessionSpinnerLU.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            public void onItemSelected( AdapterView<?> parent, View arg1, int pos, long id ) {
                valueLU = reminder_array[ pos ];
            }
            public void onNothingSelected( AdapterView<?> arg0 ) {
                valueLU = reminder_array[ 3 ]; //Position of "45 minutes"
            }
        } );
    }

    /**
     * Prevents user from accidentally exiting the app.
     * User needs to press "home"
     * Could be changed to "Do you want to exit?" dialog.
     */
    public void onBackPressed(){ }

    /**
     * Creates the menu
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.tabmenu, menu);
        return true;
    }

    /**
     * Handle menu selection
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
        case R.id.howtomenu:
            startActivity( new Intent( this, HowtoActivity.class ) );
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
     * Handles clicking of buttons
     */
    public void onClick(View v) {
        switch( v.getId() ) {
        case R.id.applySettingButton :
            vdb.setIntegerPreference( Constants.UA_BIKINI_TREATMENT_INT_PREF, valueBU );
            vdb.setIntegerPreference( Constants.UL_LL_TREATMENT_INT_PREF,  valueLU );
            Toast.makeText( this, getString( R.string.settings_saved ), Toast.LENGTH_SHORT ).show();
            break;
        case R.id.viewTutorialButton :
            startActivity( new Intent( this, TutorialActivity.class ).putExtra( Constants.FIRST, true ) );
            finish();
            break;
        default:
            break;
        }
    }

    /**
     * Ensure we close the DB connection when the activity finishes.
     */
    public void onDestroy() {
        vdb.close();
        super.onDestroy();
    }
}
