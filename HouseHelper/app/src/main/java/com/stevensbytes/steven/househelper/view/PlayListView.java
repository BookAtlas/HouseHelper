package com.stevensbytes.steven.househelper.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.stevensbytes.steven.househelper.R;
import com.stevensbytes.steven.househelper.WorkTunesActivity;
import com.stevensbytes.steven.househelper.adapter.PlayListAdapter;

/**
 * Created by Steven on 4/9/2017.
 */

public class PlayListView extends LinearLayout {
    private final View view;
    private WorkTunesActivity mContext;
    private ListView listView;

    public PlayListView(Context context) {
        super(context);
        this.mContext = (WorkTunesActivity) context;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.play_listview, this);
        listView = (ListView) findViewById(R.id.playlist_listview);
        initListView();

    }

    private void initListView() {
        listView.setAdapter(new PlayListAdapter(mContext,mContext.getContent()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
