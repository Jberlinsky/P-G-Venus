package com.Venus.NakedSkin;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * A SelectionButton is a special button that works with all of the different body parts.
 * Either one, none, or all (Whole Body) are selected.
 * @author Jingran Wang
 *
 */
public class SelectionButton extends Button {

    public boolean isSelected = false;

    /**
     * The default constructor that takes a context
     * @param context
     */
    public SelectionButton(Context context) {
        super(context);
        setUnselected();
    }

    /**
     * Another constructor that takes an AttributeSet as well
     * @param context
     * @param attrs
     */
    public SelectionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUnselected();
    }

    /**
     * The final constructor that also takes a style
     * @param context
     * @param attrs
     * @param defStyle
     */
    public SelectionButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setUnselected();
    }

    /**
     * Setting a button to be selected.
     */
    public void setSelectedCustom() {
        isSelected = true;
        setBackgroundColor( Constants.COLOR_BG_SELECTED );
        getBackground().setAlpha( Constants.COLOR_ALPHA_SELECTED );
    }

    /**
     * Set a button to be unselected
     */
    public void setUnselected() {
        isSelected = false;
        setBackgroundColor( Constants.COLOR_BG_UNSELECTED );
        getBackground().setAlpha( Constants.COLOR_ALPHA_UNSELECTED );
    }

}
