package com.venus.phone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

public class TutorialActivity extends Activity implements OnClickListener {

    private AlertDialog.Builder mBuilder;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial);

        mBuilder = new AlertDialog.Builder( this );
        mBuilder.setMessage( Constants.TUTORIAL_MESSAGE )
                .setPositiveButton( Constants.TUTORIAL_POSITIVE, new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog, int id ) {
                        dialog.cancel();
                    }
                } );
        mBuilder.create().show();


        findViewById(R.id.scheduleButton).setOnClickListener(this);
        findViewById(R.id.treatmentButton).setOnClickListener(this);
        findViewById(R.id.howtoButton).setOnClickListener(this);
        findViewById(R.id.settingButton).setOnClickListener(this);
        findViewById(R.id.buygelButton).setOnClickListener(this);
    }

    public void onClick(View v) {
        switch(v.getId()) {
        case R.id.scheduleButton:
            mBuilder.setMessage( Constants.TUTORIAL_SCHEDULE_MESSAGE );
            mBuilder.create().show();
            break;
        case R.id.treatmentButton:
            mBuilder.setMessage( Constants.TUTORIAL_TREATMENT_MESSAGE );
            mBuilder.create().show();
            break;
        case R.id.howtoButton:
            mBuilder.setMessage( Constants.TUTORIAL_HOWTO_MESSAGE );
            mBuilder.create().show();
            break;
        case R.id.settingButton:
            mBuilder.setMessage( Constants.TUTORIAL_SETTINGS_MESSAGE );
            mBuilder.create().show();
            break;
        case R.id.buygelButton:
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.gillettevenus.com/"));
            startActivity(browserIntent);
            break;
        }
    }

    public void onBackPressed() {
        mBuilder.setMessage( Constants.TUTORIAL_START_MESSAGE )
                .setPositiveButton( Constants.TUTORIAL_POSITIVE, new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog, int id ) {
                        startActivity( new Intent( getApplicationContext(), DiaryActivity.class ).putExtra( Constants.TAB_NUMBER, "1" ) );
                        finish();
                    }
                } )
                .setNegativeButton( Constants.TUTORIAL_NEGATIVE, new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog, int id ) {
                        dialog.cancel();
                    }
                } );
        mBuilder.create().show();
    }

}
