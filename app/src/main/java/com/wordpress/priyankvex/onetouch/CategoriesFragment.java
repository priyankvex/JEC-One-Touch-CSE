package com.wordpress.priyankvex.onetouch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

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

        // Getting the text views
        final TextView academics = (TextView)root.findViewById(R.id.textViewAcademics);
        final TextView news = (TextView)root.findViewById(R.id.textViewNews);
        final TextView downloads= (TextView)root.findViewById(R.id.textViewDownloads);
        final TextView tpo = (TextView)root.findViewById(R.id.textViewTpo);
        final TextView others = (TextView)root.findViewById(R.id.textViewOthers);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Ubuntu-L.ttf");

        academics.setTypeface(tf);
        news.setTypeface(tf);
        downloads.setTypeface(tf);
        tpo.setTypeface(tf);
        others.setTypeface(tf);

        // On click listeners on categories
        academics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = "academics";
                Bundle b = new Bundle();
                b.putString("tag", tag);
                YoYo.with(Techniques.RubberBand).playOn(academics);
                Intent i = new Intent(getActivity(), CategoryNoticeActivity.class);
                i.putExtras(b);
                waitForAnimation(i);
            }
        });

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = "news";
                Bundle b = new Bundle();
                b.putString("tag", tag);
                YoYo.with(Techniques.RubberBand).playOn(news);
                Intent i = new Intent(getActivity(), CategoryNoticeActivity.class);
                i.putExtras(b);
                waitForAnimation(i);
            }
        });

        downloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.RubberBand).playOn(downloads);
                String url = "https://www.dropbox.com/sh/0shvz1j9n4siofp/AABE3l_LNF5tu-ymAj9gkcXqa?dl=0";
                Bundle b = new Bundle();
                b.putString("url", url);
                Intent i = new Intent(getActivity(), WebViewActivity.class);
                i.putExtras(b);
                waitForAnimation(i);
            }
        });

        tpo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = "tpo";
                Bundle b = new Bundle();
                b.putString("tag", tag);
                YoYo.with(Techniques.RubberBand).playOn(tpo);
                Intent i = new Intent(getActivity(), CategoryNoticeActivity.class);
                i.putExtras(b);
                waitForAnimation(i);
            }
        });

        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = "other";
                Bundle b = new Bundle();
                b.putString("tag", tag);
                YoYo.with(Techniques.RubberBand).playOn(others);
                Intent i = new Intent(getActivity(), CategoryNoticeActivity.class);
                i.putExtras(b);
                waitForAnimation(i);
            }
        });

        return root;
    }

    void waitForAnimation(final Intent i){
        new android.os.Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                startActivity(i);
            }
        }, 550);
    }

}
