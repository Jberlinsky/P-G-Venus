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
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TutorialActivity extends Activity implements OnClickListener {

    boolean firstUsage = true;
    boolean hasCalendars = false;

    /**
     * Sets all the listeners and opens an initial dialog if necessary
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial);

        firstUsage = getIntent().getBooleanExtra( Constants.FIRST, false);

        if( firstUsage ) {
            openDialog( Html.fromHtml( getString( R.string.tutorial_message ) ) );
        }
        hasCalendars = getIntent().getBooleanExtra( Constants.HAS_CALENDARS, true);

        findViewById(R.id.scheduleButton).setOnClickListener(this);
        findViewById(R.id.treatmentButton).setOnClickListener(this);
        findViewById(R.id.howtoButton).setOnClickListener(this);
        findViewById(R.id.settingButton).setOnClickListener(this);
        findViewById(R.id.appStartButton).setOnClickListener(this);
        findViewById(R.id.buygelButton).setOnClickListener(this);
    }

    /**
     * Deals with all clicks
     * Special case for no calendar
     * Buy gel button always works
     */
    public void onClick( View v ) {
        if( firstUsage ) {
            switch( v.getId() ) {
            case R.id.scheduleButton:
                openDialog( Html.fromHtml( getString( R.string.tutorial_schedule ) ) );
                break;
            case R.id.treatmentButton:
                openDialog( getString( R.string.tutorial_treatment ) );
                break;
            case R.id.howtoButton:
                openDialog( Html.fromHtml( getString( R.string.tutorial_howto ) ) );
                break;
            case R.id.settingButton:
                openDialog( getString( R.string.tutorial_settings ) );
                break;
            case R.id.buygelButton:
                startActivity( new Intent( Intent.ACTION_VIEW,
                                           Uri.parse( getString( R.string.gel_url ) ) ) );
                break;
            case R.id.appStartButton:
                if ( !hasCalendars ) {
                    openDialog( getString( R.string.calendar_missing ) );
                } else {
                    startApp();
                }
            }
        } else if( !hasCalendars ) {
            switch( v.getId() ) {
            case R.id.buygelButton:
                startActivity( new Intent( Intent.ACTION_VIEW,
                                           Uri.parse( getString( R.string.gel_url ) ) ) );
                break;
            case R.id.scheduleButton:
            case R.id.treatmentButton:
            case R.id.howtoButton:
            case R.id.settingButton:
            case R.id.appStartButton:
                openDialog( getString( R.string.calendar_missing ) );
                break;
            default:
                break;
            }
        } else {
            switch( v.getId() ) {
            case R.id.scheduleButton:
                startActivity( new Intent( this, TreatmentActivity.class ) );
                finish();
                break;
            case R.id.treatmentButton:
                startActivity( new Intent( this, ScheduleActivity.class ) );
                finish();
                break;
            case R.id.howtoButton:
                startActivity( new Intent( this, HowtoActivity.class ) );
                finish();
                break;
            case R.id.settingButton:
                startActivity( new Intent( this, SettingActivity.class ) );
                finish();
                break;
            case R.id.buygelButton:
                startActivity( new Intent( Intent.ACTION_VIEW,
                                           Uri.parse( getString( R.string.gel_url ) ) ) );
                break;
            case R.id.appStartButton:
                startApp();
                break;
            default:
                break;
            }
        }
    }

    /**
     * Starts the app with the correct Intents and shows the correct dialogs if necessary
     */
    public void startApp() {
        try {
            if( firstUsage ) {
                final VenusDb vdb = new VenusDb( this );
                final ArrayList<Long> indices = new ArrayList<Long>();
                ArrayList<CharSequence> values = new ArrayList<CharSequence>();

                //Querying for the calendars on the device
                //We are guaranteed a calendar at this point
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
                AlertDialog.Builder builder = new AlertDialog.Builder( this );
                builder.setTitle( getString( R.string.calendar_select_title ) )
                       .setSingleChoiceItems( items, -1, new DialogInterface.OnClickListener() {
                           public void onClick( DialogInterface dialog, int item ) {
                               vdb.setCalendarId( indices.get( item ) );
                               vdb.close();
                               dialog.dismiss();
                               startActivity( new Intent( getApplicationContext(), TreatmentActivity.class ) );
                               finish();
                           }
                       } );
                final AlertDialog alert = builder.create();

                //Build the calendar select dialog that goes second
                final Dialog dialog = new Dialog( this );
                TextView dialogtext;
                Button button;
                dialog.setContentView( R.layout.customdialog );
                dialog.setTitle( getString( R.string.app_tutorial ) );
                dialog.setCancelable( true );
                dialogtext = (TextView)dialog.findViewById( R.id.contenttext );
                dialogtext.setText( Html.fromHtml( getString( R.string.calendar_select_text ) ) );
                button = (Button)dialog.findViewById( R.id.okaybutton );
                button.setOnClickListener( new OnClickListener() {
                    public void onClick(View v) {
                        dialog.cancel();
                        alert.show();
                    }
                } );

                //Build the first dialog
                final Dialog dialog1 = new Dialog( this );
                TextView dialogtext1;
                Button button1;
                dialog1.setContentView( R.layout.customdialog );
                dialog1.setTitle( getString( R.string.app_tutorial ) );
                dialog1.setCancelable( true );
                dialogtext1 = (TextView)dialog1.findViewById( R.id.contenttext );
                dialogtext1.setText( getString( R.string.tutorial_start ) );
                button1 = (Button)dialog1.findViewById( R.id.okaybutton );
                button1.setOnClickListener( new OnClickListener() {
                    public void onClick( View v ) {
                        dialog1.cancel();
                        dialog.show();
                    }
                } );
                dialog1.show();
            } else {
                startActivity( new Intent( this, StartActivity.class ) );
                finish();
            }
        } catch( NullPointerException npe ) {
            openDialog( getString( R.string.calendar_missing ) );
        }
    }

    /**
     * A convenience function to open a dialog from a string
     * @param string
     */
    private void openDialog( String string ) {
        final Dialog dialog = new Dialog( this );
        TextView dialogtext;
        Button button;
        dialog.setContentView( R.layout.customdialog );
        dialog.setTitle( getString( R.string.app_tutorial ) );
        dialog.setCancelable( true );
        dialogtext = (TextView)dialog.findViewById( R.id.contenttext );
        dialogtext.setText( string );
        button = (Button)dialog.findViewById( R.id.okaybutton );
        button.setOnClickListener( new OnClickListener() {
            public void onClick( View v ) {
                dialog.cancel();
            }
        } );
        dialog.show();
    }

    /**
     * A convenience function to open a dialog from a Spanned object
     * @param string
     */
    private void openDialog( Spanned string ) {
        final Dialog dialog = new Dialog( this );
        TextView dialogtext;
        Button button;
        dialog.setContentView( R.layout.customdialog );
        dialog.setTitle( getString( R.string.app_tutorial ) );
        dialog.setCancelable( true );
        dialogtext = (TextView)dialog.findViewById( R.id.contenttext );
        dialogtext.setText( string );
        button = (Button)dialog.findViewById( R.id.okaybutton );
        button.setOnClickListener( new OnClickListener() {
            public void onClick( View v ) {
                dialog.cancel();
            }
        } );
        dialog.show();
    }

    /**
     * Prevents user from accidentally exiting the app.
     * User needs to press "home"
     * Could be changed to "Do you want to exit?" dialog.
     */
    public void onBackPressed(){ }

}
