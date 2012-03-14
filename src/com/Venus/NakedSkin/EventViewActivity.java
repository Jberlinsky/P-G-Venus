package com.Venus.NakedSkin;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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
            int year = this.getIntent().getIntExtra("year",-1);


            TextView title = (TextView) findViewById(R.id.treatmenttitle);
            TextView desc = (TextView) findViewById(R.id.treatmentdescription);
            TextView date = (TextView) findViewById(R.id.treatmentdate);

            date.setText(this.setDate(month, day)+ year + ", " + this.setTime(hour, minute, ampm));
            title.setText(tit);
            desc.setText(des);
            ;
            //img.setImageResource(R.drawable.bikiniarea);

            if (tit.contains("Underarm")){
                findViewById(R.id.underarmevent).setBackgroundColor(Color.parseColor("#500099cb"));
            }
            if (tit.contains("Upper Leg")){
                findViewById(R.id.upperevent).setBackgroundColor(Color.parseColor("#500099cb"));
            }
            if (tit.contains("Lower Leg"))
                findViewById(R.id.lowerevent).setBackgroundColor(Color.parseColor("#500099cb"));
            if (tit.contains("Bikini"))
                findViewById(R.id.bikinievent).setBackgroundColor(Color.parseColor("#500099cb"));

            findViewById(R.id.treatmentBack).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivity( new Intent( getApplicationContext(), TreatmentActivity.class ) );
                    finish();
                }
             });
        }

        private String setTime(int hour, int min, int ampm) {
            String timeText = "";
            if (hour > 12)
                hour = hour - 12;
            timeText = timeText + String.valueOf(hour) + ":";
            String minn = String.valueOf(min);
            if (minn.length() == 1)
                timeText = timeText + "0" + minn + " ";
            else
                timeText = timeText + minn + " ";
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
