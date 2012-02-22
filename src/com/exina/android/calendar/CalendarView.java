/*
 * Copyright (C) 2011 Chris Gao <chris@exina.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.exina.android.calendar;

import java.util.ArrayList;
import java.util.Calendar;

import Utility.Event;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.MonthDisplayHelper;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;

import com.Venus.NakedSkin.R;

public class CalendarView extends ImageView {
    private static int WEEK_TOP_MARGIN;
    private static int WEEK_LEFT_MARGIN;
    private static int CELL_WIDTH;
    private static int CELL_HEIGH;
    private static int CELL_MARGIN_TOP;
    private static int CELL_MARGIN_LEFT;
    private static float CELL_TEXT_SIZE;

    private static final String TAG = "CalendarView";
    private Calendar mRightNow = null;
    private Cell mToday = null;
    private Cell[][] mCells = new Cell[6][7];
    private OnCellTouchListener mOnCellTouchListener = null;
    MonthDisplayHelper mHelper;
    Drawable mDecoration = null;
    ArrayList<Integer> eventDays=new ArrayList<Integer>();
    ArrayList<Integer> eventMonth=new ArrayList<Integer>();
    ArrayList<Integer> eventYear=new ArrayList<Integer>();
    ArrayList<Drawable> eDecoration=new ArrayList<Drawable>();
    Context cContext;

    public interface OnCellTouchListener {
        public void onTouch(Cell cell);
    }

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        cContext=context;
        mDecoration = context.getResources().getDrawable(R.drawable.typeb_calendar_today);
        initCalendarView();
    }

    private void initCalendarView() {
        mRightNow = Calendar.getInstance();
        this.setScaleType(ImageView.ScaleType.FIT_START);
        // prepare static 
        mDecoration = cContext.getResources().getDrawable(R.drawable.typeb_calendar_today);
        Resources res = getResources();
        WindowManager wm = (WindowManager) cContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        float cell_width = (float) (display.getWidth()/7.2);
        float cell_height = (float) (cell_width * 0.9);
        float weektopmargin = (float) (cell_width *1.7);
        float weekleftmargin = (float) (cell_width * 0.8);
        float cellmargintop = (float) (cell_width*1.6862069);
        float cellmarginleft = (float) (cell_width*0.732413793);
        float celltextsize = (float) (cell_width*0.448275862);
        
        WEEK_TOP_MARGIN  = (int) weektopmargin;
        WEEK_LEFT_MARGIN = (int) weekleftmargin;

        CELL_WIDTH = (int) cell_width;
        CELL_HEIGH = (int) cell_height;
        CELL_MARGIN_TOP = (int) cellmargintop;
        CELL_MARGIN_LEFT = (int) 0;

        CELL_TEXT_SIZE = celltextsize;
        
        // set background
        //setBackgroundResource(R.drawable.background2);
        setImageResource(R.drawable.background2);
        //mWeekTitle = res.getDrawable(R.drawable.calendar_week);

        mHelper = new MonthDisplayHelper(mRightNow.get(Calendar.YEAR), mRightNow.get(Calendar.MONTH));
    }

    private void initCells() {
        class _calendar {
            public int day;
            public boolean thisMonth;
            public _calendar(int d, boolean b) {
                day = d;
                thisMonth = b;
            }
            public _calendar(int d) {
                this(d, false);
            }
        };
        _calendar tmp[][] = new _calendar[6][7];

        for(int i=0; i<tmp.length; i++) {
            int n[] = mHelper.getDigitsForRow(i);
            for(int d=0; d<n.length; d++) {
                if(mHelper.isWithinCurrentMonth(i,d))
                    tmp[i][d] = new _calendar(n[d], true);
                else
                    tmp[i][d] = new _calendar(n[d]);
            }
        }
        this.eDecoration.clear();
        Calendar today = Calendar.getInstance();
        int thisDay = 0;
        mToday = null;
        if(mHelper.getYear()==today.get(Calendar.YEAR) && mHelper.getMonth()==today.get(Calendar.MONTH)) {
            thisDay = today.get(Calendar.DAY_OF_MONTH);
        }
        // build cells
        Rect Bound = new Rect(0, WEEK_TOP_MARGIN, CELL_WIDTH, CELL_HEIGH+WEEK_TOP_MARGIN);
        for(int week=0; week<mCells.length; week++) {
            for(int day=0; day<mCells[week].length; day++) {
                if(tmp[week][day].thisMonth) {
                    if(day==0 || day==6 )
                        mCells[week][day] = new RedCell(tmp[week][day].day, new Rect(Bound), CELL_TEXT_SIZE);
                    else
                        mCells[week][day] = new Cell(tmp[week][day].day, new Rect(Bound), CELL_TEXT_SIZE);
                } else
                    mCells[week][day] = new GrayCell(tmp[week][day].day, new Rect(Bound), CELL_TEXT_SIZE);

             // get today
                int hei = Bound.height();
                if(tmp[week][day].day==thisDay && tmp[week][day].thisMonth) {
                	mToday = mCells[week][day];
                    mDecoration.setBounds(Bound);
                }
                
                Bound.offset(CELL_WIDTH, 0); // move to next column

                
                
                for (int ei = 0; ei < this.eventDays.size();ei++) {
                	int tt = tmp[week][day].day;
                	int ed = eventDays.get(ei);
                	int mt = this.getMonth();
                	int em = eventMonth.get(ei);
                	if (tt==ed && mt == em && tmp[week][day].thisMonth && this.getYear() == eventYear.get(ei)){
                		Drawable t = cContext.getResources().getDrawable(R.drawable.typeb_calendar_event);
                		t.setBounds(mCells[week][day].getBound());
                		eDecoration.add(t);
                		
                	}
                }
            }
            Bound.offset(0, CELL_HEIGH); // move to next row and first column
            Bound.left = 0;
            Bound.right = CELL_WIDTH;
        }
    }

    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        /*android.util.Log.d(TAG, "left="+left);
        Rect re = getDrawable().getBounds();
        WEEK_LEFT_MARGIN = CELL_MARGIN_LEFT = (right-left - re.width()) / 2;
        */
        initCells();
        super.onLayout(changed, left, top, right, bottom);
    }

    public void setTimeInMillis(long milliseconds) {
        mRightNow.setTimeInMillis(milliseconds);
        initCells();
        this.invalidate();
    }

    public int getYear() {
        return mHelper.getYear();
    }

    public int getMonth() {
        return mHelper.getMonth();
    }

    public void nextMonth() {
        mHelper.nextMonth();
        initCells();
        invalidate();
    }

    public void previousMonth() {
        mHelper.previousMonth();
        initCells();
        invalidate();
    }

    public void setEvent(ArrayList<Event> ev) {
    	this.eventDays.clear();
    	this.eventMonth.clear();
    	this.eventYear.clear();
    	for (int i = 0;i < ev.size();i++){
    		if (!(this.eventMonth.contains(ev.get(i).month) && this.eventDays.contains(ev.get(i).day) && this.eventYear.contains(ev.get(i).year)))
    		{
	    		this.eventMonth.add(ev.get(i).month);
	    		this.eventDays.add(ev.get(i).day);
	    		this.eventYear.add(ev.get(i).year);
    		}
    	}
    	this.initCells();
    	invalidate();
    }
    
    public boolean firstDay(int day) {
        return day==1;
    }

    public boolean lastDay(int day) {
        return mHelper.getNumberOfDaysInMonth()==day;
    }
    
    public int eventDay(int day) {
    	for (int i = 0;i<eventDays.size();i++){
    		if (eventDays.get(i) == day && eventMonth.get(i)== this.getMonth())
    			return i;
        }
        return -1;
    }

    public void goToday() {
        Calendar cal = Calendar.getInstance();
        mHelper = new MonthDisplayHelper(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
        initCells();
        invalidate();
    }

    public Calendar getDate() {
        return mRightNow;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mOnCellTouchListener!=null){
            for(Cell[] week : mCells) {
                for(Cell day : week) {
                    if(day.hitTest((int)event.getX(), (int)event.getY())) {
                        mOnCellTouchListener.onTouch(day);
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public void setOnCellTouchListener(OnCellTouchListener p) {
        mOnCellTouchListener = p;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // draw background
        super.onDraw(canvas);

        // draw cells
        for(Cell[] week : mCells) {
            for(Cell day : week) {
                day.draw(canvas);
            }
        }

        Paint paintToday = new Paint();
        paintToday.setARGB(255, 255, 255, 255);
        paintToday.setStrokeWidth(3);
        paintToday.setStyle(Style.STROKE);
        Paint paintEvent = new Paint();
        paintEvent.setARGB(255, 0, 153, 203);
        paintEvent.setStyle(Style.STROKE);
        paintEvent.setStrokeWidth(3);
        // draw today
        if(mDecoration!=null && mToday!=null) {
            mDecoration.draw(canvas);
        	//canvas.drawCircle(mDecoration.getBounds().centerX(), mDecoration.getBounds().centerY(), (float) (mDecoration.getBounds().width()*0.35), paintToday);
        }
        
        
        if (eventMonth.size() > 0)
        {
	        for (int i = 0;i<eDecoration.size();i++){
	        	//if ( this.getMonth() == eventMonth.get(0)){
	        		eDecoration.get(i).draw(canvas);
	        	//canvas.drawCircle(eDecoration.get(i).getBounds().centerX(), eDecoration.get(i).getBounds().centerY(), (float) (eDecoration.get(i).getBounds().width()*0.35), paintEvent);
	        	//}
	        }
        }
    }

    private class GrayCell extends Cell {
        public GrayCell(int dayOfMon, Rect rect, float s) {
            super(dayOfMon, rect, s);
            mPaint.setColor(Color.LTGRAY);
            this.thismonth = false;
        }
    }

    private class RedCell extends Cell {
        public RedCell(int dayOfMon, Rect rect, float s) {
            super(dayOfMon, rect, s);
            mPaint.setColor(0xdddd0000);
            this.thismonth = true;
        }
    }
}
