package com.wordpress.priyankvex.onetouch;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by priyank on 29/1/15.
 */
@SuppressLint("NewApi")
public class FavoritesFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;

    public static FavoritesFragment newInstance(int position) {
        FavoritesFragment f = new FavoritesFragment();
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


        View root = inflater.inflate(R.layout.fragment_favorites, container, false);

        return root;
    }

}
