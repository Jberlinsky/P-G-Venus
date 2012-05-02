package com.Venus.NakedSkin;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EventArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> values;
    private final ArrayList<Long> timeValues;
    public EventArrayAdapter(Context context, ArrayList<String> values, ArrayList<Long> time ) {
        super(context, R.layout.rowlayout, values);
        this.context = context;
        this.values = values;
        this.timeValues = time;
    }

    @Override
    /**
     * Modified this to be more compact, but a lot less readable.
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = ((LayoutInflater)context.getSystemService( Context.LAYOUT_INFLATER_SERVICE ))
                                                   .inflate( R.layout.rowlayout, parent, false );

        ((TextView)rowView.findViewById( R.id.label )).setText( values.get( position ) );
        ((TextView)rowView.findViewById( R.id.listtime ))
            .setText( DateFormat.getTimeInstance( DateFormat.SHORT )
                    .format( new Date( timeValues.get( position ) ) ) );

        String s = values.get(position);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        if( s.contains( context.getString( R.string.whole ) ) ) {
            imageView.setImageResource( R.drawable.backgroundbody );
        } else if( s.contains( context.getString( R.string.under ) ) ) {
            imageView.setImageResource( R.drawable.underarm );
        } else if( s.contains( context.getString( R.string.lower ) ) ) {
            imageView.setImageResource( R.drawable.lowerleg );
        } else if( s.contains( context.getString( R.string.upper ) ) ) {
            imageView.setImageResource( R.drawable.upperleg );
        } else if( s.contains( context.getString( R.string.bikini ) ) ) {
            imageView.setImageResource( R.drawable.bikiniarea );
        } else {
            imageView.setImageResource( R.drawable.backgroundbody );
        }

        return rowView;
    }

    public void update( int index ) {
        String original = values.get( index );
        remove( original );
        insert( original + Constants.DONE, index );
    }
}
