package com.wordpress.priyankvex.onetouch;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Handling of GCM messages.
 */
public class GcmBroadcastReceiver extends WakefulBroadcastReceiver{
    static final String TAG = "GCM";
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    Context ctx;

    Bundle b;

    DatabaseHandler mDatabaseHandlerHandler;
    SQLiteDatabase db;

    String date;

    @Override
    public void onReceive(Context context, Intent intent) {
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
        ctx = context;
        String messageType = gcm.getMessageType(intent);
        b = intent.getExtras();

        // Initializing the DB objects
        mDatabaseHandlerHandler = new DatabaseHandler(ctx);
        db = mDatabaseHandlerHandler.getWritableDatabase();

        // Getting the date when the notification is delivered on the device.
        date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        b.putString("date", date);

        // Storing the notice in the database
        mDatabaseHandlerHandler.addNotice(db, b);

        // Bundle b has all the info related to a notice and we need to save that.

        if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
            sendNotification(b);
        } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
            sendNotification(b);
        } else {
            if (b.getString("Title") != null)
                Log.v(TAG, b.getString("Title"));
            sendNotification(b);
            if (!b.getString("img_url").equals("")){
                // Load the image if any image url is passed
                new LoadImage().execute(b.getString("img_url"));
            }
        }
        setResultCode(Activity.RESULT_OK);
    }

    // Put the GCM message into a notification and post it.
    private void sendNotification(Bundle mBundle) {
        mNotificationManager = (NotificationManager)
                ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
                new Intent(ctx, MainActivity.class), 0);

        // Start without a delay
        // Vibrate for 100 milliseconds
        // Sleep for 1000 milliseconds
        long[] pattern = {0, 100, 1000};

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(ctx)
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle(mBundle.getString("Title"))
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(mBundle.getString("Text")))
                        .setContentText(mBundle.getString("Text"))
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setVibrate(pattern);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    class LoadImage extends AsyncTask<String, Void, Void>{

        Bitmap bitmap = null;

        @Override
        protected Void doInBackground(String... params) {
            Log.d("OneTouch", "Downloading the image for " + b.getString("Title"));
            try {
                URL smallImageLink = new URL(params[0]);
                bitmap = BitmapFactory.decodeStream(smallImageLink.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("OneTouch", "Done downloading the image");
            // Create folder to store images on sd card if it is not present
            createAppImagesFolder();
            // Now save the image into the sd card folder
            if (bitmap != null)
                saveBitmapToAppFolder(bitmap);
            else
                Log.d("OneTouch", "Bitmap was null");
        }
    }

    // Helper function to create images folder for the app
    private void createAppImagesFolder(){

        File direct = new File(android.os.Environment.getExternalStorageDirectory() + "/OneTouch");

        if(!direct.exists()){

            if(direct.mkdir()){

                //directory is created;
                Log.d("OneTouch", "Folder Created. Not present previously.");
            }
        }
    }

    // Helper function to save the bitmap to the app's image folder
    private void saveBitmapToAppFolder(Bitmap bitmap){

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(android.os.Environment.getExternalStorageDirectory() + "/OneTouch/" + b.getString("Title")+ date + ".png");
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            Log.d("OneTouch", "Saved the image to the folder");
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}