package com.venus.phone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.exina.android.calendar.CalendarView;
import com.exina.android.calendar.Cell;

public class TreatmentActivity extends Activity implements CalendarView.OnCellTouchListener {
    public static final String MIME_TYPE = "vnd.android.cursor.dir/vnd.exina.android.calendar.date";
    CalendarView mView = null;
    TextView mHit;
    Handler mHandler = new Handler();


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);
        mView = (CalendarView)findViewById(R.id.calendar);
        mView.setOnCellTouchListener(this);

//        if(getIntent().getAction().equals(Intent.ACTION_PICK))
//            findViewById(R.id.hint).setVisibility(View.INVISIBLE);
    }

    public void onTouch(Cell cell) {
        Intent intent = getIntent();
        String action = intent.getAction();
//        if(action.equals(Intent.ACTION_PICK) || action.equals(Intent.ACTION_GET_CONTENT)) {
//            Intent ret = new Intent();
//            ret.putExtra("year", mView.getYear());
//            ret.putExtra("month", mView.getMonth());
//            ret.putExtra("day", cell.getDayOfMonth());
//            this.setResult(RESULT_OK, ret);
//            finish();
//            return;
//        }
        int day = cell.getDayOfMonth();
        if(mView.firstDay(day))
            mView.previousMonth();
        else if(mView.lastDay(day))
            mView.nextMonth();
        else
            return;

        mHandler.post(new Runnable() {
            public void run() {
                Toast.makeText(TreatmentActivity.this, DateUtils.getMonthString(mView.getMonth(), DateUtils.LENGTH_LONG) + " "+mView.getYear(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
