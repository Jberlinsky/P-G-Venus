package com.Venus.NakedSkin;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

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


    }


}
