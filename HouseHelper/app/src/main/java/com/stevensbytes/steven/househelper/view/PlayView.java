package com.stevensbytes.steven.househelper.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stevensbytes.steven.househelper.MainActivity;
import com.stevensbytes.steven.househelper.R;
import com.stevensbytes.steven.househelper.WorkTunesActivity;

/**
 * Created by Steven on 4/9/2017.
 */

public class PlayView extends LinearLayout {

    private final View view;
    private WorkTunesActivity mContext;

    TextView name;

    public PlayView(Context context) {
        super(context);
        this.mContext = (WorkTunesActivity)context;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.viewpage_player, this);
    }

    public void update(){

    }
}
