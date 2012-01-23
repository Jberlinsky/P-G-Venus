package com.venus.phone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class EventViewActivity extends Activity{
		
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.treatment);
	        
	        String tit = this.getIntent().getStringExtra("title");
	        String des = this.getIntent().getStringExtra("desc");
	        
	        TextView title = (TextView) findViewById(R.id.treatmenttitle);
        	TextView desc = (TextView) findViewById(R.id.treatmentdescription);
	        
        	title.setText(tit);
        	desc.setText(des);
        	
        	ImageView img = (ImageView) findViewById(R.id.treatmentImage);
        	//img.setImageResource(R.drawable.bikiniarea);
        	
        	if (tit.contains("Underarm"))
        		img.setImageResource(R.drawable.underarm);
        	else if (tit.contains("Upper Leg"))
        		img.setImageResource(R.drawable.upperleg);
        	else if (tit.contains("Lower Leg"))
        		img.setImageResource(R.drawable.lowerleg);
        	else if (tit.contains("Bikini"))
        		img.setImageResource(R.drawable.bikiniarea);
        	else if (tit.contains("Other"))
        		img.setImageResource(R.drawable.body1);
        	
        	findViewById(R.id.treatmentBack).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	//// Make sign up email functions here.
                	finish();
                }
        	 });
	    }
}
