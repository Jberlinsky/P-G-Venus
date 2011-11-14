package com.venus.phone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ScheduleActivity extends Activity implements OnClickListener{
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);
        /* Button names
         *  underarmleft
            underarmright
            forearmleft
            forearmright
            bikiniarea
            upperlegleft
            upperlegright
            lowerlegleft
            lowerlegright
            scheduleproceed
            phaseProceed
            startupButton
            maintenanceButton
            saveReminder
         */
        
        /*Edit TExt
         * setReminderEmail
         */
        
        /*Spinner
         * alarmSoundSpinner
         * daysBeforeSpinner
         */
        
        /* Checkbox
         * emailCheckBox
         * alertCheckBox
         */
        
      //Visual
        /*
        findViewById(R.id.underarmleft).getBackground().setAlpha(45);
        findViewById(R.id.underarmright).getBackground().setAlpha(45);
        findViewById(R.id.forearmleft).getBackground().setAlpha(45);
        findViewById(R.id.forearmright).getBackground().setAlpha(45);
        findViewById(R.id.bikiniarea).getBackground().setAlpha(45);
        findViewById(R.id.upperlegleft).getBackground().setAlpha(45);
        findViewById(R.id.upperlegright).getBackground().setAlpha(45);
        findViewById(R.id.lowerlegleft).getBackground().setAlpha(45);
        findViewById(R.id.lowerlegright).getBackground().setAlpha(45);
        */
        
        //Listener
        findViewById(R.id.underarmleft).setOnClickListener(this);
        findViewById(R.id.underarmright).setOnClickListener(this);
        findViewById(R.id.forearmleft).setOnClickListener(this);
        findViewById(R.id.forearmright).setOnClickListener(this);
        findViewById(R.id.bikiniarea).setOnClickListener(this);
        findViewById(R.id.upperlegleft).setOnClickListener(this);
        findViewById(R.id.upperlegright).setOnClickListener(this);
        findViewById(R.id.lowerlegleft).setOnClickListener(this);
        findViewById(R.id.lowerlegright).setOnClickListener(this);
        findViewById(R.id.scheduleproceed).setOnClickListener(this);
        
        
        
		

	}
	
	public void onClick(View v) {
		//super.onClick(v);
		switch(v.getId()) {
		
		case R.id.underarmleft:
			//findViewById(R.id.underarmleft).getBackground().setAlpha(100);
			break;
		case R.id.underarmright:
	        //findViewById(R.id.underarmright).getBackground().setAlpha(100);
			break;
		case R.id.forearmleft:
	       // findViewById(R.id.forearmleft).getBackground().setAlpha(100);
			break;
		case R.id.forearmright:
	        //findViewById(R.id.forearmright).getBackground().setAlpha(100);
			break;
		case R.id.bikiniarea:
	       // findViewById(R.id.bikiniarea).getBackground().setAlpha(100);
			break;
		case R.id.upperlegleft:
	      //  findViewById(R.id.upperlegleft).getBackground().setAlpha(100);
			break;
		case R.id.upperlegright:
	     //   findViewById(R.id.upperlegright).getBackground().setAlpha(100);
			break;
		case R.id.lowerlegleft:
	       // findViewById(R.id.lowerlegleft).getBackground().setAlpha(100);
			
			break;
		case R.id.lowerlegright:
	       // findViewById(R.id.lowerlegright).getBackground().setAlpha(100);
			break;
		case R.id.startupButton:
			findViewById(R.id.startupButton).setBackgroundColor(0xFFFFFFFF);
			findViewById(R.id.maintenanceButton).setBackgroundColor(0x00000000);
			findViewById(R.id.sessionSpinner).setVisibility(View.VISIBLE);
	        findViewById(R.id.sessionText).setVisibility(View.VISIBLE);
			break;
		case R.id.maintenanceButton:
			findViewById(R.id.startupButton).setBackgroundColor(0x00000000);
			findViewById(R.id.maintenanceButton).setBackgroundColor(0xFFFFFFFF);
			findViewById(R.id.sessionSpinner).setVisibility(View.INVISIBLE);
	        findViewById(R.id.sessionText).setVisibility(View.INVISIBLE);
			break;
		case R.id.scheduleproceed:
			setContentView(R.layout.phase);
			findViewById(R.id.sessionSpinner).setVisibility(View.INVISIBLE);
	        findViewById(R.id.sessionText).setVisibility(View.INVISIBLE);
	        findViewById(R.id.startupButton).setOnClickListener(this);
	        findViewById(R.id.maintenanceButton).setOnClickListener(this);
	        findViewById(R.id.phaseProceed).setOnClickListener(this);
			break;
		case R.id.phaseProceed:
			setContentView(R.layout.setreminder);
			findViewById(R.id.saveReminder).setOnClickListener(this);
			break;
		case R.id.saveReminder:
			Toast.makeText(this, "Reminder has been set!", Toast.LENGTH_SHORT).show();
			setContentView(R.layout.schedule);
		}
	}
	
}
