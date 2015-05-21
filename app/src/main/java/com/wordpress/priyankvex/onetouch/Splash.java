package com.wordpress.priyankvex.onetouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by priyank on 17/2/15.
 */
public class Splash extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        /* New Handler to start the Menu-Activity
                * and close this Splash-Screen after some seconds.*/
        new android.os.Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Splash.this,MainActivity.class);
                Splash.this.startActivity(mainIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                Splash.this.finish();
            }
        }, 1250);
    }
}
