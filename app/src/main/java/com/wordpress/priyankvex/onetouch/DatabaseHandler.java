package com.wordpress.priyankvex.onetouch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by priyank on 16/2/15.
 * Class to manage all the database operations.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "oneTouchDB";

    // Table to store the notices
    private static final String TABLE_NOTICES = "notices";
    // Table to store the favorite notices
    private static final String TABLE_FAVORITES = "favorites";

    // Names of columns in TABLE_NOTICES and TABLE_FAVORITES
    private static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_TEXT = "text";
    private static final String KEY_IMGURL = "img_url";
    private static final String KEY_LINK = "link";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_DATE = "date";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //SQL queries to create the tables.
        String queryCreateTableNotices = "CREATE TABLE " + TABLE_NOTICES+ "( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_TITLE + " TEXT, "
                + KEY_TEXT + " TEXT, "
                + KEY_IMGURL + " TEXT, "
                + KEY_LINK + " TEXT, "
                + KEY_CATEGORY + " TEXT, "
                + KEY_DATE + " TEXT"
                + ")";
        String queryCreateTableFavorites = "CREATE TABLE " + TABLE_FAVORITES+ "( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_TITLE + " TEXT, "
                + KEY_TEXT + " TEXT, "
                + KEY_IMGURL + " TEXT, "
                + KEY_LINK + " TEXT, "
                + KEY_CATEGORY + " TEXT, "
                + KEY_DATE + " TEXT"
                + ")";

        db.execSQL(queryCreateTableNotices);
        db.execSQL(queryCreateTableFavorites);

    }

    //We are just going to replace the database for a version change.
    //This is not an app to store top secret info after all.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTICES);

        // Create tables again
        onCreate(db);
    }


    // Method to add the notice to the table
    void addNotice(SQLiteDatabase db, Bundle b){

        ContentValues values = new ContentValues();

        values.put(KEY_TITLE, b.getString("Title"));
        values.put(KEY_TEXT, b.getString("Text"));
        values.put(KEY_IMGURL, b.getString("img_url"));
        values.put(KEY_LINK, b.getString("link"));
        values.put(KEY_CATEGORY, b.getString("category"));
        values.put(KEY_DATE, b.getString("date"));

        //Inserting this content value of a single song into the table
        db.insert(TABLE_NOTICES, null, values);

    }

    // Method to add the notice to the favorites table
    void addFavorite(SQLiteDatabase db, Bundle b){

        ContentValues values = new ContentValues();

        values.put(KEY_TITLE, b.getString("Title"));
        values.put(KEY_TEXT, b.getString("Text"));
        values.put(KEY_IMGURL, b.getString("img_url"));
        values.put(KEY_LINK, b.getString("link"));
        values.put(KEY_CATEGORY, b.getString("category"));
        values.put(KEY_DATE, b.getString("date"));

        //Inserting this content value of a single song into the table
        db.insert(TABLE_FAVORITES, null, values);
    }

    // Method to read the notice from the database
    ArrayList readNotice(SQLiteDatabase db){

        ArrayList<Bundle> noticeList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTICES;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do{
                Bundle b = new Bundle();
                b.putInt("id", cursor.getInt(0));
                b.putString("Title", cursor.getString(1));
                b.putString("Text", cursor.getString(2));
                b.putString("img_url", cursor.getString(3));
                b.putString("link", cursor.getString(4));
                b.putString("category", cursor.getString(5));
                b.putString("date", cursor.getString(6));
                noticeList.add(b);
            }while (cursor.moveToNext());
        }

        return noticeList;
    }

    // Method to read the notice filtered by the category
    ArrayList readCategoryNotice(SQLiteDatabase db, String category){

        ArrayList<Bundle> noticeList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTICES + " WHERE " + KEY_CATEGORY + " = \"" + category + "\"";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do{
                Bundle b = new Bundle();
                b.putInt("id", cursor.getInt(0));
                b.putString("Title", cursor.getString(1));
                b.putString("Text", cursor.getString(2));
                b.putString("img_url", cursor.getString(3));
                b.putString("link", cursor.getString(4));
                b.putString("category", cursor.getString(5));
                b.putString("date", cursor.getString(6));
                noticeList.add(b);
            }while (cursor.moveToNext());
        }

        return noticeList;
    }

    // Method to read the notice from the favorite table
    ArrayList readFavorite(SQLiteDatabase db){
        ArrayList<Bundle> noticeList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FAVORITES;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do{
                Bundle b = new Bundle();
                b.putInt("id", cursor.getInt(0));
                b.putString("Title", cursor.getString(1));
                b.putString("Text", cursor.getString(2));
                b.putString("img_url", cursor.getString(3));
                b.putString("link", cursor.getString(4));
                b.putString("category", cursor.getString(5));
                b.putString("date", cursor.getString(6));
                noticeList.add(b);
            }while (cursor.moveToNext());
        }

        return noticeList;
    }

    // Remove the notice from the table
    void deleteNotice(int id){
        String sql = "DELETE FROM " + TABLE_NOTICES + " WHERE " + KEY_ID + " = " + String.valueOf(id);
        Log.d("OneTouch", sql);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
        Log.d("OneTouch", "Done deleting");
    }

    // Remove the notice from the favorite table
    void deleteFavorite(int id){
        String sql = "DELETE FROM " + TABLE_FAVORITES + " WHERE " + KEY_ID + " = " + String.valueOf(id);
        Log.d("OneTouch", sql);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
        Log.d("OneTouch", "Done deleting");
    }

    // Check if there are notices stored on the device or not
    boolean isNoticePresent(SQLiteDatabase db){
        String query = "SELECT count(*) FROM " + TABLE_NOTICES;
        Cursor mCursor = db.rawQuery(query, null);
        mCursor.moveToFirst();
        int status = mCursor.getInt(0);
        if (status == 0){
            return false;
        }
        return true;
    }

    // Check if there are any favorites or not
    boolean isFavoritePresent(SQLiteDatabase db){
        String query = "SELECT count(*) FROM " + TABLE_FAVORITES;
        Cursor mCursor = db.rawQuery(query, null);
        mCursor.moveToFirst();
        int status = mCursor.getInt(0);
        if (status == 0){
            return false;
        }
        return true;
    }



}
