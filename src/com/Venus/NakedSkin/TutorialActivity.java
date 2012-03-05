package com.Venus.NakedSkin;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TutorialActivity extends Activity implements OnClickListener {

    //private AlertDialog.Builder mBuilder;
    boolean firstUsage = true;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial);

        firstUsage = this.getIntent().getBooleanExtra("first", false);

        if (firstUsage)
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
        findViewById(R.id.appStartButton).setOnClickListener(this);
        findViewById(R.id.buygelButton).setOnClickListener(this);
    }

    public void onClick(View v) {
        if (firstUsage){
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
                break;
            case R.id.appStartButton:
                this.startApp();
            }
        } else {
            switch(v.getId()) {
            case R.id.scheduleButton:
                startActivity( new Intent( getApplicationContext(), TreatmentActivity.class ) );
                finish();
                break;
            case R.id.treatmentButton:
                startActivity( new Intent( getApplicationContext(), ScheduleActivity.class ) );
                finish();
                break;
            case R.id.howtoButton:
                startActivity( new Intent( getApplicationContext(), HowtoActivity.class ) );
                finish();
                break;
            case R.id.settingButton:
                startActivity( new Intent( getApplicationContext(), SettingActivity.class ) );
                finish();
                break;
            case R.id.buygelButton:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.gillettevenus.com/"));
                startActivity(browserIntent);
                break;
            case R.id.appStartButton:
                this.startApp();
                break;
            default:
                break;
            }
        }
    }




    public void startApp() {
        if( firstUsage ) {
            final VenusDb vdb = new VenusDb( this );
            final ArrayList<Long> indices = new ArrayList<Long>();
            ArrayList<CharSequence> values = new ArrayList<CharSequence>();

            //Querying for the calendars on the device
            Cursor cursor = Utilities.queryForCalendars( this );
            while (cursor.moveToNext()) {
                indices.add( cursor.getLong( Constants.PROJECTION_ID_INDEX ) );
                values.add( cursor.getString( Constants.PROJECTION_DISPLAY_NAME_INDEX ) );
            }
            cursor.close();

            //Setting the items array to the results
            final CharSequence[] items = new CharSequence[ values.size() ];
            values.toArray( items );

            //Building the alert dialog that goes second
            //onClick will save the calendar ID to the DB and start the treatment activity
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle( Constants.TUTORIAL_CALENDAR_SELECT_TITLE );
            builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    vdb.setCalendarId( indices.get( item ) );
                    vdb.close();
                    dialog.dismiss();
                    startActivity( new Intent( getApplicationContext(), TreatmentActivity.class ) );
                    finish();
                }
            });
            final AlertDialog alert = builder.create();

            //Build the app tutorial dialog that goes first
            final Dialog dialog = new Dialog(this);
            TextView dialogtext;
            Button button;
            dialog.setContentView(R.layout.customdialog);
            dialog.setTitle("App Tutorial");
            dialog.setCancelable(true);
            dialogtext = (TextView) dialog.findViewById(R.id.contenttext);
            dialogtext.setText(Constants.TUTORIAL_CALENDAR_SELECT_MESSAGE);
            button = (Button) dialog.findViewById(R.id.okaybutton);
            button.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    dialog.cancel();
                    alert.show();
                }
            });
            dialog.show();
        } else {
        /*final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.customdialog);
        dialog.setTitle("App Tutorial");
        dialog.setCancelable(true);
        TextView dialogtext = (TextView) dialog.findViewById(R.id.contenttext);
        dialogtext.setText(Constants.TUTORIAL_START_MESSAGE);
        Button button = (Button) dialog.findViewById(R.id.okaybutton);
        button.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
            //startActivity( new Intent( getApplicationContext(), DiaryActivity.class ).putExtra( Constants.TAB_NUMBER, "1" ) );

            }
        });
        dialog.show();*/
            startActivity( new Intent( getApplicationContext(), TreatmentActivity.class ) );
            finish();
        }
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

    private void openDialog(Spanned string)
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

    public void onBackPressed(){
        //This is to prevent user from accidently exiting the app
        //pressing Home will exit the app
    }

}
