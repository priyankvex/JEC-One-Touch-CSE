package com.wordpress.priyankvex.onetouch;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by priyank on 29/1/15.
 */
@SuppressLint("NewApi")
public class LivefeedFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;

    TextView text;
    Typeface typeface;

    public static LivefeedFragment newInstance(int position) {
        LivefeedFragment f = new LivefeedFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt(ARG_POSITION);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_livefeed, container, false);

        TextView tv = (TextView)root.findViewById(R.id.text1);
        typeface = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Ubuntu-L.ttf");
        tv.setTypeface(typeface);

        return root;
    }

}
