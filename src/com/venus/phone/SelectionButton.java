package com.venus.phone;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class SelectionButton extends Button {

    public boolean isSelected = false;

    public SelectionButton(Context context) {
        super(context);
        setUnselected();
    }

    public SelectionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUnselected();
    }

    public SelectionButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setUnselected();
    }

    public void setSelectedCustom() {
        isSelected = true;
        setBackgroundColor( Constants.COLOR_BG_SELECTED );
        getBackground().setAlpha( Constants.COLOR_ALPHA_SELECTED );
    }

    public void setUnselected() {
        isSelected = false;
        setBackgroundColor( Constants.COLOR_BG_UNSELECTED );
        getBackground().setAlpha( Constants.COLOR_ALPHA_UNSELECTED );
    }

}
