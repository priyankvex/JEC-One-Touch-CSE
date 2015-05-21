package com.wordpress.priyankvex.onetouch;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * Created by priyank on 17/2/15.
 * Activity class to show the full notice.
 */
public class NoticeActivity extends Activity {

    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        Button buttonShare = (Button) findViewById(R.id.buttonShare);
        ImageView imageView = (ImageView) findViewById(R.id.noticeImageView);
        TextView textViewTitle = (TextView) findViewById(R.id.noticeTextViewTitle);
        TextView textViewText = (TextView) findViewById(R.id.noticeTextViewText);
        TextView textViewLink = (TextView) findViewById(R.id.noticeTextViewLink);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Ubuntu-L.ttf");
        textViewTitle.setTypeface(font);
        textViewText.setTypeface(font);

        b = getIntent().getExtras();

        final File imgFile = new File(android.os.Environment.getExternalStorageDirectory() + "/OneTouch/" + b.getString("Title")+ b.getString("date") + ".png");
        if (imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);
        }

        textViewTitle.setText(b.getString("Title"));
        textViewText.setText(b.getString("Text"));

        if (b.getString("link") != null && (!b.getString("link").equals("")) ){
            textViewLink.setTypeface(font);
            textViewLink.setText("Click Here");
            textViewLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(b.getString("link")));
                    startActivity(i);
                }
            });
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(imgFile.getAbsolutePath())), "image/*");
                startActivity(intent);
            }
        });

        buttonShare.setTypeface(font);

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String message = b.getString("Title") + "\n" + b.getString("Text") + "\n" + b.getString("link") + "\nShared Via : JEC One Touch CSE App";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, b.getString("Title"));
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

    }



}
