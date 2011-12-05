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

        ((ImageButton)findViewById( R.id.videoButton1 )).setOnClickListener( this );
    }

    public void onClick( View v ) {
        switch( v.getId() ) {
        case R.id.videoButton1 :
            startActivity( new Intent( getApplicationContext(), VideoPlayer.class) );
            break;
        default :
            break;
        }
    }
}
