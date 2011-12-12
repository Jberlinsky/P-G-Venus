package com.venus.phone;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TutorialActivity extends Activity implements OnClickListener {

    //private AlertDialog.Builder mBuilder;
    

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial);
        
        openDialog(Constants.TUTORIAL_MESSAGE);
        
        /*
        mBuilder = new AlertDialog.Builder( this );
        mBuilder.setMessage( Constants.TUTORIAL_MESSAGE )
                .setPositiveButton( Constants.TUTORIAL_POSITIVE, new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog, int id ) {
                        dialog.cancel();
                    }
                } );
        
        mBuilder.create().show();
*/
        findViewById(R.id.scheduleButton).setOnClickListener(this);
        findViewById(R.id.treatmentButton).setOnClickListener(this);
        findViewById(R.id.howtoButton).setOnClickListener(this);
        findViewById(R.id.settingButton).setOnClickListener(this);
        findViewById(R.id.buygelButton).setOnClickListener(this);
    }

    public void onClick(View v) {
        switch(v.getId()) {
        
        case R.id.scheduleButton:
        	openDialog(Constants.TUTORIAL_SCHEDULE_MESSAGE);
            break;
        case R.id.treatmentButton:
        	openDialog(Constants.TUTORIAL_TREATMENT_MESSAGE);
            break;
        case R.id.howtoButton:
        	openDialog(Constants.TUTORIAL_HOWTO_MESSAGE);
            break;
        case R.id.settingButton:
        	openDialog(Constants.TUTORIAL_SETTINGS_MESSAGE);
            break;
        case R.id.buygelButton:        	
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.gillettevenus.com/"));
            startActivity(browserIntent);
			
        }
    }

    public void onBackPressed() {
    	final Dialog dialog = new Dialog(this);
        
        
        dialog.setContentView(R.layout.customdialog);
        dialog.setTitle("App Tutorial");
        dialog.setCancelable(true);
        TextView dialogtext = (TextView) dialog.findViewById(R.id.contenttext);
        dialogtext.setText(Constants.TUTORIAL_START_MESSAGE);
        Button button = (Button) dialog.findViewById(R.id.okaybutton);
        button.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	//startActivity( new Intent( getApplicationContext(), DiaryActivity.class ).putExtra( Constants.TAB_NUMBER, "1" ) );
        	startActivity( new Intent( getApplicationContext(), ScheduleActivity.class ) );
            finish();
            }
        });
        dialog.show();
    }
    
    private void openDialog(String string)
    {
    	final Dialog dialog = new Dialog(this);
        TextView dialogtext;
        Button button;
        dialog.setContentView(R.layout.customdialog);
        dialog.setTitle("App Tutorial");
        dialog.setCancelable(true);
        dialogtext = (TextView) dialog.findViewById(R.id.contenttext);
        dialogtext.setText(string);
        button = (Button) dialog.findViewById(R.id.okaybutton);
        button.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	dialog.cancel();
            }
        });
        dialog.show();
    }

}
