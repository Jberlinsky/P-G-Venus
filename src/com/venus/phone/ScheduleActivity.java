package com.venus.phone;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ScheduleActivity extends Activity implements OnClickListener{

	private static final int SHAVE_ID = 1;
	boolean underarmleft = false;
	boolean underarmright = false;
	boolean forearmleft = false;
	boolean forearmright = false;
	boolean bikiniarea = false;
	boolean upperlegleft = false;
	boolean upperlegright = false;
	boolean lowerlegleft = false;
	boolean lowerlegright = false;

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
            phaseBack
            setReminderBack
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
        setVisual();
        
      //Listener
        this.setListeners();
        
        
        
		

	}
	private void setListeners() {
		findViewById(R.id.underarmleft).setOnClickListener(this);
        findViewById(R.id.underarmright).setOnClickListener(this);
        findViewById(R.id.forearmleft).setOnClickListener(this);
        findViewById(R.id.forearmright).setOnClickListener(this);
        findViewById(R.id.bikiniarea).setOnClickListener(this);
        findViewById(R.id.upperlegleft).setOnClickListener(this);
        findViewById(R.id.upperlegright).setOnClickListener(this);
        findViewById(R.id.lowerlegleft).setOnClickListener(this);
        findViewById(R.id.lowerlegright).setOnClickListener(this);
        findViewById(R.id.scheduleProceed).setOnClickListener(this);
	}
	private void setVisual() {
		for (int i = R.id.underarmleft;i <= R.id.lowerlegright; i++)
        	changeAlpha(i, 0);
	}
	
	private void changeAlpha(int rid, int alpha)
	{
		findViewById(rid).setBackgroundColor(Color.parseColor("#ffffff"));
        findViewById(rid).getBackground().setAlpha(alpha);
	}
	
	public void onClick(View v) {
		//super.onClick(v);
		switch(v.getId()) {
		
		case R.id.underarmleft:
			
			if (underarmleft)
			{
				changeAlpha(R.id.underarmleft,0);
				underarmleft = false;
			}
			else 
			{
				changeAlpha(R.id.underarmleft,60);
				underarmleft = true;
			}
			break;
		case R.id.underarmright:
			if (underarmright)
			{
				changeAlpha(R.id.underarmright,0);
				underarmright = false;
			}
			else 
			{
				changeAlpha(R.id.underarmright,60);
				underarmright = true;
			}
			break;
		case R.id.forearmleft:
			if (forearmleft)
			{
				changeAlpha(R.id.forearmleft,0);
				forearmleft = false;
			}
			else 
			{
				changeAlpha(R.id.forearmleft,60);
				forearmleft = true;
			}
			break;
		case R.id.forearmright:
			if (forearmright)
			{
				changeAlpha(R.id.forearmright,0);
				forearmright = false;
			}
			else 
			{
				changeAlpha(R.id.forearmright,60);
				forearmright = true;
			}
			break;
		case R.id.bikiniarea:
			if (bikiniarea)
			{
				changeAlpha(R.id.bikiniarea,0);
				bikiniarea = false;
			}
			else 
			{
				changeAlpha(R.id.bikiniarea,60);
				bikiniarea = true;
			}
			break;
		case R.id.upperlegleft:
			if (upperlegleft)
			{
				changeAlpha(R.id.upperlegleft,0);
				upperlegleft = false;
			}
			else 
			{
				changeAlpha(R.id.upperlegleft,60);
				upperlegleft = true;
			}
			break;
		case R.id.upperlegright:
			if (upperlegright)
			{
				changeAlpha(R.id.upperlegright,0);
				upperlegright = false;
			}
			else 
			{
				changeAlpha(R.id.upperlegright,60);
				upperlegright = true;
			}
			break;
		case R.id.lowerlegleft:
			if (lowerlegleft)
			{
				changeAlpha(R.id.lowerlegleft,0);
				lowerlegleft = false;
			}
			else 
			{
				changeAlpha(R.id.lowerlegleft,60);
				lowerlegleft = true;
			}
			
			break;
		case R.id.lowerlegright:
			if (lowerlegright)
			{
				changeAlpha(R.id.lowerlegright,0);
				lowerlegright = false;
			}
			else 
			{
				changeAlpha(R.id.lowerlegright,60);
				lowerlegright = true;
			}
			break;
		case R.id.startupButton:
			//Visual//
			findViewById(R.id.startupButton).setBackgroundColor(0xFFFFFFFF);
			findViewById(R.id.maintenanceButton).setBackgroundColor(0x00000000);
			findViewById(R.id.sessionSpinner).setVisibility(View.VISIBLE);
	        findViewById(R.id.sessionText).setVisibility(View.VISIBLE);
	        /////////
			break;
		case R.id.maintenanceButton:
			//Visual//
			findViewById(R.id.startupButton).setBackgroundColor(0x00000000);
			findViewById(R.id.maintenanceButton).setBackgroundColor(0xFFFFFFFF);
			findViewById(R.id.sessionSpinner).setVisibility(View.INVISIBLE);
	        findViewById(R.id.sessionText).setVisibility(View.INVISIBLE);
			break;
		case R.id.scheduleProceed:
			setContentView(R.layout.phase);
			//Visual and listener//
			findViewById(R.id.sessionSpinner).setVisibility(View.INVISIBLE);
	        findViewById(R.id.sessionText).setVisibility(View.INVISIBLE);
	        findViewById(R.id.startupButton).setOnClickListener(this);
	        findViewById(R.id.maintenanceButton).setOnClickListener(this);
	        findViewById(R.id.phaseProceed).setOnClickListener(this);
	        findViewById(R.id.phaseBack).setOnClickListener(this);
			break;
		case R.id.phaseBack:
			setContentView(R.layout.schedule);
			//Visual and listener//
			this.setListeners();
			this.setVisual();
			break;
		case R.id.phaseProceed:
			setContentView(R.layout.setreminder);
			//Visual and listener//
			findViewById(R.id.saveReminder).setOnClickListener(this);
			findViewById(R.id.setReminderBack).setOnClickListener(this);
			break;
		case R.id.setReminderBack:
			setContentView(R.layout.phase);
			//Visual and listener//
			findViewById(R.id.sessionSpinner).setVisibility(View.INVISIBLE);
	        findViewById(R.id.sessionText).setVisibility(View.INVISIBLE);
	        findViewById(R.id.startupButton).setOnClickListener(this);
	        findViewById(R.id.maintenanceButton).setOnClickListener(this);
	        findViewById(R.id.phaseProceed).setOnClickListener(this);
	        findViewById(R.id.phaseBack).setOnClickListener(this);
			break;
		case R.id.saveReminder:
			// Do the reminder magic!
			String ns = Context.NOTIFICATION_SERVICE;
			NotificationManager mNotificationManager = (NotificationManager)getSystemService(ns);
			int icon = android.R.drawable.ic_dialog_email;
			CharSequence tickerText = "Reminder from Venus";
			long when = System.currentTimeMillis();

			Notification notification = new Notification(icon, tickerText, when);

			Context context = getApplicationContext();
			CharSequence contentTitle = "Time to Treat!";
			CharSequence contentText = "You have a treatment scheduled with the Venus.";
			
			Intent notificationIntent = new Intent(this, ScheduleActivity.class);
			PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

			notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

			mNotificationManager.notify(SHAVE_ID, notification);

			Toast.makeText(this, "Reminder has been set!", Toast.LENGTH_SHORT).show();
			setContentView(R.layout.schedule);
			this.setListeners();
			this.setVisual();
		}
	}
}
