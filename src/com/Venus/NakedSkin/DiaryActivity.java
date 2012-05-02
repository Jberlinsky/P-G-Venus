package com.Venus.NakedSkin;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class DiaryActivity extends TabActivity {

    private static final String SCHEDULE_TAB_TAG  = "schedule";
    private static final String TREATMENT_TAB_TAG = "treatment";
    private static final String HOWTO_TAB_TAG     = "howto";
    private static final String SETTINGS_TAB_TAG  = "settings";


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary);

        Resources res = getResources(); // Resource object to get Drawables
        final TabHost tabHost = getTabHost();  // The activity TabHost

        //Add all tabs to the TabHost
        tabHost.addTab( tabHost.newTabSpec( SCHEDULE_TAB_TAG )
                               .setIndicator( getString( R.string.schedule ),
                                              res.getDrawable( R.drawable.schedule_icon ) )
                               .setContent( new Intent().setClass( this, ScheduleActivity.class ) ) );

        tabHost.addTab( tabHost.newTabSpec( TREATMENT_TAB_TAG )
                               .setIndicator( getString( R.string.treatment ),
                                              res.getDrawable( R.drawable.treatment_icon ) )
                               .setContent( new Intent().setClass( this, TreatmentActivity.class ) ) );

        tabHost.addTab( tabHost.newTabSpec( HOWTO_TAB_TAG )
                               .setIndicator( getString( R.string.howto ),
                                              res.getDrawable( R.drawable.howto_icon ) )
                               .setContent( new Intent().setClass( this, HowtoActivity.class ) ) );

        tabHost.addTab( tabHost.newTabSpec( SETTINGS_TAB_TAG )
                               .setIndicator( getString( R.string.settings ),
                                              res.getDrawable( R.drawable.setting_icon ) )
                               .setContent( new Intent().setClass( this, SettingActivity.class ) ) );

        Bundle extras = getIntent().getExtras();
        if( null != extras ) {
            String value = extras.getString( Constants.TAB_NUMBER_EXTRA );
            if( value.contains( "1" ) ) {
                tabHost.setCurrentTab( 0 );
            } else if( value.contains( "2" ) ) {
                tabHost.setCurrentTab( 1 );
            } else if( value.contains( "3" ) ) {
                tabHost.setCurrentTab( 2 );
            } else if( value.contains( "4" ) ) {
                tabHost.setCurrentTab( 3 );
            } else {
                tabHost.setCurrentTab( 0 );
            }
        }
    }

}
