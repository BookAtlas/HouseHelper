package com.stevensbytes.steven.househelper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.stevensbytes.steven.househelper.R;
import com.stevensbytes.steven.househelper.db.Task;
import com.stevensbytes.steven.househelper.db.TaskDataSource;

import java.util.List;

/**
 * Created by Steven on 4/8/2017.
 */

public class TaskListAdapter extends BaseAdapter {
    private final Context mContext;
    private final LayoutInflater mInflater;

    private List<Task> taskList;

    public TaskListAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public TaskListAdapter(Context context, List<Task> tasks) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.taskList = tasks;
    }

    @Override
    public int getCount() {
        return this.taskList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.taskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void notifyDataSetChanged(){
        TaskDataSource ds = new TaskDataSource(this.mContext);
        this.taskList = ds.getAllTasks();
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_list_task, parent, false);
            holder = new ViewHolder();
            holder.textView = (TextView)convertView.findViewById(R.id.adapter_list_lr_text);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(((Task)this.getItem(position)).getDetails());
        return convertView;
    }

}

class ViewHolder {
    public TextView textView;
}
