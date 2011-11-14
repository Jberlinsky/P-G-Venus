package com.venus.phone;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;

public class DiaryActivity extends TabActivity {
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.diary);
	    
	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tabReusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, ScheduleActivity.class);
        spec = tabHost.newTabSpec("schedule").setIndicator("Schedule",
                          res.getDrawable(R.drawable.ic_launcher))
                      .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs
        intent = new Intent().setClass(this, TreatmentActivity.class);
        spec = tabHost.newTabSpec("treatment").setIndicator("Treatment",
                          res.getDrawable(R.drawable.ic_launcher))
                      .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, HowtoActivity.class);
        spec = tabHost.newTabSpec("howto").setIndicator("How to",
                          res.getDrawable(R.drawable.ic_launcher))
                      .setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, MusicActivity.class);
        spec = tabHost.newTabSpec("music").setIndicator("Music",
                          res.getDrawable(R.drawable.ic_launcher))
                      .setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, SettingActivity.class);
        spec = tabHost.newTabSpec("settings").setIndicator("Settings",
                          res.getDrawable(R.drawable.ic_launcher))
                      .setContent(intent);
        tabHost.addTab(spec);
        
        Bundle extras = getIntent().getExtras();
	    if(extras !=null) {
	    	String value = extras.getString("tabNumber");
	    	if (value.contains("1"))
	    		tabHost.setCurrentTab(0);
	    	else if (value.contains("2"))
	    		tabHost.setCurrentTab(1);
	    	else if (value.contains("3"))
	    		tabHost.setCurrentTab(2);
	    	else if (value.contains("4"))
	    		tabHost.setCurrentTab(3);
	    	else if (value.contains("5"))
	    		tabHost.setCurrentTab(4);
	    	else
	    		tabHost.setCurrentTab(0);
	    }
	    

        
    }
	
}
