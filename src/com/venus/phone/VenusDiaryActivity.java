package com.venus.phone;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;




public class VenusDiaryActivity extends Activity {
    /** Called when the activity is first created. */
	Intent tutorialIntent;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tutorialIntent = new Intent(this, TutorialActivity.class);
        
        /*TextEdit
         * mainEmailTextEdit
         */
        
        findViewById(R.id.mainStartButtonImage).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	//// Make sign up email functions here.
            	startActivity(tutorialIntent);
            }
        });
    }
    
    
}