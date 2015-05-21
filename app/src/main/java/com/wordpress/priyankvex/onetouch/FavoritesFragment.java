package com.wordpress.priyankvex.onetouch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by priyank on 29/1/15.
 * Fragment class to show the pinned favorites.
 */
@SuppressLint("NewApi")
public class FavoritesFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    DatabaseHandler mDatabaseHandler;
    SQLiteDatabase db;

    ArrayList<Bundle> noticeList;
    ArrayList<String> titles;
    ArrayList<Integer> notice_ids;

    private ListView noticesListView;

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

        position = getArguments().getInt(ARG_POSITION);

        titles = new ArrayList<>();
        notice_ids = new ArrayList<>();

        // Initializing the database objects
        mDatabaseHandler = new DatabaseHandler(getActivity());
        db = mDatabaseHandler.getWritableDatabase();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_livefeed, container, false);

        noticesListView = (ListView) root.findViewById(R.id.noticesListView);
        registerForContextMenu(noticesListView);

        // Change this line to readFavorite()
        noticeList = mDatabaseHandler.readFavorite(db);
        for (Bundle b  : noticeList){
            //Log.d("OneTouch", b.getString("Title"));
            titles.add(b.getString("Title"));
            notice_ids.add(b.getInt("id"));
        }

        for (int id : notice_ids){
           // Log.d("OneTouch", "Notice id : " + id);
        }

        NoticeListAdapter adapter = new NoticeListAdapter(getActivity(), R.layout.livefeed_item_row, noticeList);

        noticesListView.setAdapter(adapter);

        noticesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), NoticeActivity.class);
                i.putExtras(noticeList.get(position));
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        return root;

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        //Log.d("OneTouch", "In onContextMenu()");
        if (v.getId() == R.id.noticesListView){
            // Here goes the context menu.
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_favorites, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int listPosition = info.position;
        //Log.d("OneTouch", "Delete " + titles.get(listPosition));
        // Delete the list item here
        mDatabaseHandler.deleteFavorite(notice_ids.get(listPosition));
        noticeList = mDatabaseHandler.readFavorite(db);
        titles.clear();
        notice_ids.clear();
        for (Bundle b  : noticeList){
            //Log.d("OneTouch", b.getString("Title"));
            titles.add(b.getString("Title"));
            notice_ids.add(b.getInt("id"));
        }
        NoticeListAdapter adapter = new NoticeListAdapter(getActivity(), R.layout.livefeed_item_row, noticeList);
        noticesListView.setAdapter(adapter);
        return true;
    }
}
