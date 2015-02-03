package com.wordpress.priyankvex.onetouch;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by priyank on 30/1/15.
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

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.imageview_options);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.textView_options);

        Typeface face = Typeface.createFromAsset(context.getAssets(),
                "fonts/Ubuntu-L.ttf");
        txtTitle.setTypeface(face);

        if (position == 0){
            imgIcon.setImageResource(R.drawable.about_jec);
        }
        else if (position == 1){
            imgIcon.setImageResource(R.drawable.feedback);
        }
        else if (position == 2){
            imgIcon.setImageResource(R.drawable.write_post);
        }
        else if (position == 3){
            imgIcon.setImageResource(R.drawable.developers);
        }
        else if (position == 4){
            imgIcon.setImageResource(R.drawable.settings);
        }
        else if (position == 5){
            imgIcon.setImageResource(R.drawable.rate_us);
        }
        else{
            imgIcon.setImageResource(R.drawable.facebook);
        }

        txtTitle.setText(options_items.get(position));

        return convertView;
    }
}
