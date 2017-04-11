package com.stevensbytes.steven.househelper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.stevensbytes.steven.househelper.R;
import com.stevensbytes.steven.househelper.audio.AudioOb;
import com.stevensbytes.steven.househelper.audio.BaseAudioOb;

import java.util.ArrayList;

/**
 * Created by Steven on 4/9/2017.
 */

public class PlayListAdapter extends BaseAdapter {

    private final ArrayList<AudioOb> contentList;
    private final Context context;

    public PlayListAdapter(Context context, ArrayList<AudioOb> contentList) {
        this.contentList = contentList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return contentList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.playlist_item, parent, false);
            holder = new ViewHolder(context);
            holder.name = (TextView) convertView.findViewById(R.id.playlist_item_name);
            holder.mark = (ImageView) convertView.findViewById(R.id.playlist_item_img);
            convertView.setTag(holder);
        }

        AudioOb vo = contentList.get(position);
        holder.name.setText(vo.getName());
        holder.mark.setTag(position);

        return convertView;
    }

    static class ViewHolder{
        public TextView name;
        public ImageView mark;

        public ViewHolder(Context context){
            this.name = new TextView(context);
            this.mark = new ImageView(context);
        }
    }
}
