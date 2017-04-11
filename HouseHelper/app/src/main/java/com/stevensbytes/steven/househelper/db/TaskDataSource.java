package com.stevensbytes.steven.househelper.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steven on 4/8/2017.
 */

public class TaskDataSource {
    private SQLiteDatabase db;
    private HouseHelperDbHelper dbHelper;
    private String[] allColumns = {HouseHelperDbHelper.TaskEntry.COLUMN_ID,
            HouseHelperDbHelper.TaskEntry.COLUMN_DETAILS,
            HouseHelperDbHelper.TaskEntry.COLUMN_GROUP};

    public TaskDataSource(Context context){
        dbHelper = new HouseHelperDbHelper(context);
    }

    public void open() throws SQLException{
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Task createTask(String desc, long group){
        ContentValues vals = new ContentValues();
        vals.put(HouseHelperDbHelper.TaskEntry.COLUMN_DETAILS, desc);
        vals.put(HouseHelperDbHelper.TaskEntry.COLUMN_GROUP, group);
        long newID = db.insert(HouseHelperDbHelper.TaskEntry.TABLE_NAME, null, vals);
        Cursor cursor = db.query(HouseHelperDbHelper.TaskEntry.TABLE_NAME, allColumns,
                HouseHelperDbHelper.TaskEntry.COLUMN_ID + "=" + newID, null, null, null, null);
        cursor.moveToFirst();
        Task createdTask = cursorToTask(cursor);
        cursor.close();
        return createdTask;
    }

    public void deleteTask(Task task){
        long id = task.getID();
        db.delete(HouseHelperDbHelper.TaskEntry.TABLE_NAME,
                HouseHelperDbHelper.TaskEntry.COLUMN_ID + "=" + id, null);
    }

    public List<Task> getAllTasks(){
        List<Task> tasks = new ArrayList<Task>();
        this.open();
        Cursor cursor = db.query(HouseHelperDbHelper.TaskEntry.TABLE_NAME, allColumns, null, null,
                null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Task task = cursorToTask(cursor);
            tasks.add(task);
            cursor.moveToNext();
        }

        cursor.close();
        return tasks;
    }

    private Task cursorToTask(Cursor cursor) {
        Task task = new Task();
        task.setID(cursor.getLong(0));
        task.setDetails(cursor.getString(1));
        task.setGroup(cursor.getLong(2));
        return task;
    }
}
