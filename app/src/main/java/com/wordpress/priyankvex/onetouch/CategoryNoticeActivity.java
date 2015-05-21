package com.wordpress.priyankvex.onetouch;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by priyank on 1/3/15.
 * Activity class to display the notices filtered on the basis of category.
 */
public class CategoryNoticeActivity extends Activity{

    DatabaseHandler mDatabaseHandler;
    SQLiteDatabase db;

    ArrayList<Bundle> noticeList;
    ArrayList<String> titles;
    ArrayList<Integer> notice_ids;

    private ListView noticesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_livefeed);

        Bundle bundle = getIntent().getExtras();
        String tag = bundle.getString("tag");

        titles = new ArrayList<>();
        notice_ids = new ArrayList<>();

        // Initializing the database objects
        mDatabaseHandler = new DatabaseHandler(this);
        db = mDatabaseHandler.getWritableDatabase();

        noticesListView = (ListView) findViewById(R.id.noticesListView);

        noticeList = mDatabaseHandler.readCategoryNotice(db, tag);

        for (Bundle b  : noticeList){
            //Log.d("OneTouch", b.getString("Title"));
            titles.add(b.getString("Title"));
            notice_ids.add(b.getInt("id"));
        }

        for (int id : notice_ids){
            //Log.d("OneTouch", "Notice id : " + id);
        }

        NoticeListAdapter adapter = new NoticeListAdapter(this, R.layout.livefeed_item_row, noticeList);

        noticesListView.setAdapter(adapter);

        noticesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(CategoryNoticeActivity.this, NoticeActivity.class);
                i.putExtras(noticeList.get(position));
                startActivity(i);
                CategoryNoticeActivity.this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

    }
}
