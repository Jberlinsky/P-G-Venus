package com.venus.phone;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DigitalClock;
import android.widget.ImageView;
import android.widget.TextView;

public class EventViewActivity extends Activity{
		
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.treatment);
	        
	        String tit = this.getIntent().getStringExtra("title");
	        String des = this.getIntent().getStringExtra("desc");
	        int month = this.getIntent().getIntExtra("month",-1);
	        int day = this.getIntent().getIntExtra("day",-1);
	        int hour = this.getIntent().getIntExtra("hour",-1);
	        int minute = this.getIntent().getIntExtra("min",-1);
	        int ampm = this.getIntent().getIntExtra("ampm",-1);
	        
	        
	        TextView title = (TextView) findViewById(R.id.treatmenttitle);
        	TextView desc = (TextView) findViewById(R.id.treatmentdescription);
        	TextView date = (TextView) findViewById(R.id.treatmentdate);
        	
        	date.setText(this.setDate(month, day)+ "2012, " + this.setTime(hour, minute, ampm));
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
        		img.setImageResource(R.drawable.other);
        	
        	findViewById(R.id.treatmentBack).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	//// Make sign up email functions here.
                	finish();
                }
        	 });
	    }
	    
	    private String setTime(int hour, int min, int ampm) {
	    	String timeText = "";
	    	if (hour > 12)
	    		hour = hour - 12;
	    	timeText = timeText + String.valueOf(hour) + ":";
	    	timeText = timeText + String.valueOf(min) + " ";
	    	if (ampm == Calendar.AM)
	    		timeText = timeText + "AM";
	    	else
	    		timeText = timeText + "PM";
	    	return timeText;
	    }
	    
	    private String setDate(int mon, int day) {
	    	String date = "";
	    	String sday = String.valueOf(day);
	    	if (mon == 11)
	        {
	        	date = date + "December " + sday;
	        }
	        else if (mon == 10)
	        {
	        	
	        	date = date + "November " + sday;
	        }
	        else if (mon == 9)
	        {
	        	
	        	date = date + "October " + sday;
	        }
	        else if (mon == 8)
	        {
	        	
	        	date = date + "September " + sday;
	        }
	        else if (mon == 7)
	        {
	        	
	        	date = date + "August " + sday;
	        }
	        else if (mon == 6)
	        {
	        	
	        	date = date + "July " + sday;
	        }
	        else if (mon == 5)
	        {
	        	
	        	date = date + "June " + sday;
	        }
	        else if (mon == 4)
	        {
	        	
	        	date = date + "May " + sday;
	        }
	        else if (mon == 3)
	        {
	        	
	        	date = date + "April " + sday;
	        }
	        else if (mon == 2)
	        {
	        	
	        	date = date + "March " + sday;
	        }
	        else if (mon == 1)
	        {
	        	
	        	date = date + "February " + sday;
	        }
	        else if (mon == 0)
	        {
	        	
	        	date = date + "January " + sday;
	        }
	    	date = date + " ";
	    	return date;
	    }
}
