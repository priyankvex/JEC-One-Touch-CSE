package com.wordpress.priyankvex.onetouch;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by priyank on 3/3/15.
 */
public class DevelopersActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);

        Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/Ubuntu-L.ttf");

        TextView developers = (TextView) findViewById(R.id.textViewDevelopers);
        TextView priyank = (TextView) findViewById(R.id.textViewPriyank);
        TextView priyankContact = (TextView) findViewById(R.id.textViewPriyankContact);
        TextView ronak = (TextView) findViewById(R.id.textViewRonak);
        TextView ronakContact = (TextView) findViewById(R.id.textViewRonakContact);
        TextView funyQuote = (TextView) findViewById(R.id.textViewfunQuote);

        developers.setTypeface(tf);
        priyank.setTypeface(tf);
        priyankContact.setTypeface(tf);
        ronak.setTypeface(tf);
        ronakContact.setTypeface(tf);
        funyQuote.setTypeface(tf);

    }
}
