package com.Venus.NakedSkin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class HowtoActivity extends Activity implements View.OnClickListener{
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.howto);

        ((ImageButton)findViewById( R.id.howtoVideoButton )).setOnClickListener( this );
        ((ImageButton)findViewById( R.id.trVideoButton )).setOnClickListener( this );
        ((ImageButton)findViewById( R.id.workVideoButton )).setOnClickListener( this );
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.tabmenu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.schedulemenu:
        	startActivity( new Intent( getApplicationContext(), ScheduleActivity.class ) );
        	finish();
            return true;
        case R.id.treatmentmenu:
        	startActivity( new Intent( getApplicationContext(), TreatmentActivity.class ) );
        	finish();
            return true;
        case R.id.settingmenu:
        	startActivity( new Intent( getApplicationContext(), SettingActivity.class ) );
        	finish();
            return true;
        case R.id.homemenu:
        	Intent intent =  new Intent( getApplicationContext(), TutorialActivity.class );
        	intent.putExtra("first", false);
        	startActivity(intent);
        	finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed(){
    	//This is to prevent user from accidently exiting the app
    	//pressing Home will exit the app
    }
    
    public void onClick( View v ) {
    	Intent browserIntent;
        switch( v.getId() ) {
        
        case R.id.howtoVideoButton :
        	//French
        	browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=J6LNzyn95mc"));
        	//English
        	//browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=P-9Q1ppDVI8"));
            startActivity(browserIntent);
            break;
        case R.id.trVideoButton :
        	//French
        	browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=z9sD1gbFwhg "));
        	//English
        	//browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=YHWc1btzmr8"));
            startActivity(browserIntent);
            break;
        case R.id.workVideoButton :
        	//French
        	browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=UZSXBZ_gmQk"));
        	//English
        	//browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=8YaO9RYPBaE"));
            startActivity(browserIntent);
            break;
        default :
            break;
        }
    }
    
}
