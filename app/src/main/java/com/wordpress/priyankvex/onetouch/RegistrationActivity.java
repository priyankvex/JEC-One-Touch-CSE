package com.wordpress.priyankvex.onetouch;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by priyank on 1/2/15.
 */
public class RegistrationActivity extends Activity{

    public Spinner spin1, spin2;
    EditText field_name;
    EditText field_email;
    EditText field_enroll;

    Typeface typeface;

    GoogleCloudMessaging gcm;

    String SENDER_ID = "199429070096";

    String regid;

    String name;
    String email;
    String enroll;
    String year;
    String branch;

    int regid_set = 0;

    ProgressDialog progress;

    public ArrayAdapter<String> yrada;

    public ArrayAdapter<String> brada;

    public String[] yeardata={
            "-Select Year-",
            "1",
            "2",
            "3",
            "4",
    };

    public String[] branchdata={
            "-Select Branch-",
            "Civil",
            "Computer Science",
            "Electrical",
            "Electronics and Communication",
            "Industrial Production",
            "Information Technology",
            "Mechanical"
    };

    public static final Pattern EMAIL_ADDRESS
            = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );


    @Override
    public void onCreate(Bundle bun) {
        super.onCreate(bun);
        setContentView(R.layout.activity_registration);

        // change ActionBar color just if an ActionBar is available
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            Log.d("onetouch", "color should changed");
            ActionBar bar = getActionBar();
            bar.setBackgroundDrawable(new ColorDrawable(0xFF3F9FE0));

        }

        typeface = Typeface.createFromAsset(getAssets(), "fonts/Ubuntu-L.ttf");

        spin1 =(Spinner)findViewById(R.id.spinner_year);
        yrada= new ArrayAdapter<String>(RegistrationActivity.this,android.R.layout.simple_spinner_dropdown_item, yeardata);
        spin1.setAdapter(yrada);

        /*spin2 =(Spinner)findViewById(R.id.spinner_branch);
        brada= new ArrayAdapter<String>(RegistrationActivity.this,android.R.layout.simple_spinner_dropdown_item, branchdata);
        spin2.setAdapter(brada);*/

        field_name=(EditText)findViewById(R.id.edittext_name);
        field_email=(EditText)findViewById(R.id.edittext_email);
        field_enroll=(EditText)findViewById(R.id.edittext_enroll);

        field_name.setTypeface(typeface);
        field_email.setTypeface(typeface);
        field_enroll.setTypeface(typeface);

        // Setting the typeface on the text views
        TextView text_name = (TextView)findViewById(R.id.textview_name);
        TextView text_email = (TextView)findViewById(R.id.textview_email);
        TextView text_enroll = (TextView)findViewById(R.id.textview_enroll);
        TextView text_year = (TextView)findViewById(R.id.textview_year);
        //TextView text_branch = (TextView)findViewById(R.id.textview_branch);
        TextView text_registration = (TextView)findViewById(R.id.textview_registration);
        TextView text_note = (TextView)findViewById(R.id.text_note);

        text_name.setTypeface(typeface);
        text_email.setTypeface(typeface);
        text_enroll.setTypeface(typeface);
        text_year.setTypeface(typeface);
        //text_branch.setTypeface(typeface);
        text_registration.setTypeface(typeface);
        text_note.setTypeface(typeface);

    }


    public void forward(View v) {

        boolean bool;

        bool = validation();
        if (bool){
            // instantiating the gcm object.
            gcm = GoogleCloudMessaging.getInstance(RegistrationActivity.this);
            // getting the gcm reg id
            // After getting reg id, in onPostExecute() we send the form data + regid to the server
            // And save all the data in preferences.
            registerBackground();
        }
    }

    public boolean validation(){

        name = field_name.getText().toString();
        email = field_email.getText().toString();
        enroll = field_enroll.getText().toString();
        year = spin1.getSelectedItem().toString();
        //branch = spin2.getSelectedItem().toString();

        int len= enroll.length();

        boolean status = true;

        if (name.equalsIgnoreCase("")|| email.equalsIgnoreCase("")|| enroll.equalsIgnoreCase(""))
        {
            if( name.equalsIgnoreCase("")){
                YoYo.with(Techniques.Shake).playOn(field_name);
            }
            if( email.equalsIgnoreCase("")){
                YoYo.with(Techniques.Shake).playOn(field_email);
            }
            if( enroll.equalsIgnoreCase("")){
                YoYo.with(Techniques.Shake).playOn(field_enroll);
            }
            status = false;
        }

        if ( !(EMAIL_ADDRESS.matcher(email).matches()))
        {
            YoYo.with(Techniques.Shake).playOn(field_email);
            status = false;
        }

        if(len<12 || len>12)
        {
            YoYo.with(Techniques.Shake).playOn(field_enroll);
            status = false;
        }

        if(spin1.getSelectedItem().toString().equalsIgnoreCase("-Select Year-"))
        {
            YoYo.with(Techniques.Shake).playOn(spin1);
            status = false;
        }

        /*if(spin2.getSelectedItem().toString().equalsIgnoreCase("-Select Branch-"))
        {
            YoYo.with(Techniques.Shake).playOn(spin2);
            status = false;
        }*/

        return status;
    }

    class SendData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            // Sending regid to the app server.
            Log.v("OneTouch", "Sending regid to the app server");
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("https://limitless-citadel-6638.herokuapp.com/");

            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("regid", regid));
                nameValuePairs.add(new BasicNameValuePair("name", name));
                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("enroll", enroll));
                nameValuePairs.add(new BasicNameValuePair("year", year));
                nameValuePairs.add(new BasicNameValuePair("branch", branch));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                httpclient.execute(httppost);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                Log.d("OneTouch", "Cant send");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progress.isShowing()){
                progress.dismiss();
            }
            Intent i = new Intent(RegistrationActivity.this, MainActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
            Log.v("OneTouch", "Done");
        }
    }

    private void registerBackground() {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progress = new ProgressDialog(RegistrationActivity.this);
                progress.setMessage("Working...");
                progress.show();
            }

            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(RegistrationActivity.this);
                    }

                    Log.d("OneTouch", "Registering Prior");

                    regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration id=" + regid;
                    regid_set = 1;

                    // You should send the registration ID to your server over HTTP, so it
                    // can use GCM/HTTP or CCS to send messages to your app.

                    // For this demo: we don't need to send it because the device will send
                    // upstream messages to a server that echo back the message using the
                    // 'from' address in the message.
                    Log.d("OneTouch", "Registering");

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                    return "error";
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // First sending the data to the server
                // Then saving it in preferences.
                if (s.equals("error")) {
                    if (progress.isShowing()){
                        progress.dismiss();
                    }
                    Log.d("OneTouch", "Could not register the device");
                    alertbox("Failed", "You can't always get what you want. Or may be if you try with a working internet connection.", RegistrationActivity.this);
                } else {
                    new SendData().execute();
                    storeRegistrationId();
                }
            }
        }.execute(null, null, null);
    }

    // Helper function to show
    public void alertbox(String title, String message,Activity activiy) {
        final AlertDialog alertDialog = new AlertDialog.Builder(activiy).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.cancel();
            } });
        alertDialog.show();
    }

    /**
     * Stores the data in shared preferences
     */
    private void storeRegistrationId() {
        final SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        int appVersion = getAppVersion(RegistrationActivity.this);
        Log.v("OneTouch", "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("reg_id", regid);
        editor.putString("name", regid);
        editor.putString("email", email);
        editor.putString("enroll", enroll);
        editor.putString("year", year);
        editor.putString("branch", branch);
        editor.putInt("app_version", appVersion);
        editor.commit();
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

}
