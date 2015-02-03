package com.wordpress.priyankvex.onetouch;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
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

import java.util.regex.Pattern;

/**
 * Created by priyank on 1/2/15.
 */
public class RegistrationActivity extends Activity{

    public Spinner spin1,spin2;
    EditText field_name;
    EditText field_email;
    EditText field_enroll;

    Typeface typeface;

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

        spin2 =(Spinner)findViewById(R.id.spinner_branch);
        brada= new ArrayAdapter<String>(RegistrationActivity.this,android.R.layout.simple_spinner_dropdown_item, branchdata);
        spin2.setAdapter(brada);

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
        TextView text_branch = (TextView)findViewById(R.id.textview_branch);
        TextView text_registration = (TextView)findViewById(R.id.textview_registration);
        TextView text_note = (TextView)findViewById(R.id.text_note);

        text_name.setTypeface(typeface);
        text_email.setTypeface(typeface);
        text_enroll.setTypeface(typeface);
        text_year.setTypeface(typeface);
        text_branch.setTypeface(typeface);
        text_registration.setTypeface(typeface);
        text_note.setTypeface(typeface);

    }


    public void forward(View v) {


        // RegistrationActivity reg=new RegistrationActivity();

        boolean bool;

        bool=validation();
        if (bool){
            // Send the data to the server by making an http post request
            // Save the data in preferences
            Intent next = new Intent(RegistrationActivity.this, MainActivity.class);
            startActivity(next);
            RegistrationActivity.this.finish();
        }


    }

    public boolean validation()
    {
        String name;
        String email;
        String enroll;

        name = field_name.getText().toString();
        email = field_email.getText().toString();
        enroll = field_enroll.getText().toString();

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

        if(spin2.getSelectedItem().toString().equalsIgnoreCase("-Select Branch-"))
        {
            YoYo.with(Techniques.Shake).playOn(spin2);
            status = false;
        }

        return status;
    }
}
