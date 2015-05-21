package com.wordpress.priyankvex.onetouch;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by priyank on 30/1/15.
 * This is used in Options list as well categories list
 */

public class OptionsListAdapter extends BaseAdapter {
    private Context context;
    ArrayList<String> options_items;

    public OptionsListAdapter(Context context, ArrayList<String> options_items){
        this.context = context;
        this.options_items = options_items;
    }

    @Override
    public int getCount() {
        return options_items.size();
    }

    @Override
    public Object getItem(int position) {
        return options_items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.options_list_row, null);
        }

        TextView txtTitle = (TextView) convertView.findViewById(R.id.textView_options);

        Typeface face = Typeface.createFromAsset(context.getAssets(),
                "fonts/Ubuntu-L.ttf");
        txtTitle.setTypeface(face);

        txtTitle.setText(options_items.get(position));

        return convertView;
    }
}
