package com.venus.phone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

    public void onClick( View v ) {
        switch( v.getId() ) {
        case R.id.howtoVideoButton :
            startActivity( new Intent( getApplicationContext(), VideoPlayer.class) );
            break;
        case R.id.trVideoButton :
            startActivity( new Intent( getApplicationContext(), VideoPlayer.class) );
            break;
        case R.id.workVideoButton :
            startActivity( new Intent( getApplicationContext(), VideoPlayer.class) );
            break;
        default :
            break;
        }
    }
    
}
