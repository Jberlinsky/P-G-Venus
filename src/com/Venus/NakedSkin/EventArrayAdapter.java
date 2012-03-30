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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        TextView textTimeView = (TextView) rowView.findViewById(R.id.listtime);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        Date date = new Date(this.timeValues.get(position));
        DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT);
        textTimeView.setText(df.format(date));
        textView.setText(values.get(position));
        // Change the icon for Windows and iPhone
        String s = values.get(position);
        if (s.contains("Whole")) {
            imageView.setImageResource(R.drawable.backgroundbody);
        }else if (s.contains("Under")) {
            imageView.setImageResource(R.drawable.underarm);
        } else if (s.contains("Lower")) {
            imageView.setImageResource(R.drawable.lowerleg);
        } else if (s.contains("Upper")) {
            imageView.setImageResource(R.drawable.upperleg);
        } else if (s.contains("Bikini")) {
            imageView.setImageResource(R.drawable.bikiniarea);
        } else{
            imageView.setImageResource(R.drawable.backgroundbody);
        }

        return rowView;
    }

    public void update( int index ) {
        String original = values.get( index );
        remove( original );
        insert( original + " Done", index );
    }
}
