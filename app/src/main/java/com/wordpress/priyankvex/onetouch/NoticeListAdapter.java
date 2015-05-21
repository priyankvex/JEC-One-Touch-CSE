package com.wordpress.priyankvex.onetouch;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by priyank on 16/2/15.
 * List adapter for the notice list.
 */
public class NoticeListAdapter extends ArrayAdapter<Bundle> {



    public NoticeListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public NoticeListAdapter(Context context, int resource, ArrayList<Bundle> items) {
        super(context, resource, items);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {

            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.livefeed_item_row, null);

        }

        Bundle bundle = getItem(position);

        if (bundle != null) {
            TextView title = (TextView) v.findViewById(R.id.textViewTitle);
            TextView date = (TextView) v.findViewById(R.id.textViewDate);
            Typeface font = Typeface.createFromAsset(getContext().getAssets(),"fonts/Ubuntu-L.ttf");
            title.setTypeface(font);
            date.setTypeface(font);

            title.setText(bundle.getString("Title"));
            date.setText(bundle.getString("date"));
        }

        return v;

    }

}
