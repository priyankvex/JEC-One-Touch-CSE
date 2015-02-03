package com.wordpress.priyankvex.onetouch;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by priyank on 29/1/15.
 */
@SuppressLint("NewApi")
public class CategoriesFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;

    public static CategoriesFragment newInstance(int position) {
        CategoriesFragment f = new CategoriesFragment();
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

        View root = inflater.inflate(R.layout.fragment_categories, container, false);

        return root;
    }

    //Helper function to get the screen width of the device in dp
    private float getScreenWidth(){
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels;
        return dpWidth;
    }

}
