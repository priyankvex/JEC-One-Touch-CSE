package com.wordpress.priyankvex.onetouch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.facebook.AppEventsLogger;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;

import java.util.ArrayList;

/**
 * Created by priyank on 29/1/15.
 */
@SuppressLint("NewApi")
public class OptionsFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private UiLifecycleHelper uiHelper;

    private int position;

    public static OptionsFragment newInstance(int position) {
        OptionsFragment f = new OptionsFragment();
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


        View root = inflater.inflate(R.layout.fragment_options, container, false);

        if( savedInstanceState != null){
            Log.d("onetouch", "Fragment saved");
        }

        ListView optionsList = (ListView)root.findViewById(R.id.options_list);

        // initializing UiLifecycleHelper
        uiHelper = new UiLifecycleHelper(getActivity(), null);
        if ( savedInstanceState != null) {
            uiHelper.onCreate(savedInstanceState);
        }

        ArrayList<String> options_titles = new ArrayList<>();
        options_titles.add("About Jec");
        options_titles.add("Feedback");
        options_titles.add("Create a Buzz");
        options_titles.add("Developers");
        options_titles.add("Settings");
        options_titles.add("Rate Us");
        options_titles.add("Share App");

        OptionsListAdapter mAdapter = new OptionsListAdapter(getActivity(), options_titles);

        optionsList.setAdapter(mAdapter);

        optionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 6){
                    FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(getActivity())
                            .setLink("https://developers.facebook.com/android")
                            .setDescription("This is the para of the post")
                            .build();
                    uiHelper.trackPendingDialogCall(shareDialog.present());
                }
            }
        });

        return root;
    }

    // callback handler that's invoked when the share dialog closes and control returns to the calling app
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
            @Override
            public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
                Log.e("Activity", String.format("Error: %s", error.toString()));
            }

            @Override
            public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
                Log.i("Activity", "Success!");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(getActivity());
        uiHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(getActivity());
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

}
