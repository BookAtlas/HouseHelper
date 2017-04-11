package com.stevensbytes.steven.househelper.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steven on 4/8/2017.
 */

public class GroupDataSource {
    private SQLiteDatabase db;
    private HouseHelperDbHelper dbHelper;
    private String[] allColumns = {HouseHelperDbHelper.TaskGroup.COLUMN_ID,
            HouseHelperDbHelper.TaskGroup.COLUMN_NAME};

    public GroupDataSource(Context context){
        dbHelper = new HouseHelperDbHelper(context);
    }

    public void open() throws SQLException{
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Group createGroup(String name){
        ContentValues vals = new ContentValues();
        vals.put(HouseHelperDbHelper.TaskGroup.COLUMN_NAME, name);
        long newID = db.insert(HouseHelperDbHelper.TaskGroup.TABLE_NAME, null, vals);
        Cursor cursor = db.query(HouseHelperDbHelper.TaskGroup.TABLE_NAME, allColumns,
                HouseHelperDbHelper.TaskGroup.COLUMN_ID + "=" + newID, null, null, null, null);
        cursor.moveToFirst();
        Group createdGroup = cursorToGroup(cursor);
        cursor.close();
        return createdGroup;
    }

    public void deleteGroup(Group group){
        long id = group.getID();
        db.delete(HouseHelperDbHelper.TaskGroup.TABLE_NAME,
                HouseHelperDbHelper.TaskGroup.COLUMN_ID + "=" + id, null);
    }

    public List<Group> getAllGroups(){
        List<Group> groups = new ArrayList<Group>();
        Cursor cursor = db.query(HouseHelperDbHelper.TaskGroup.TABLE_NAME, allColumns,
                null, null, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Group group = cursorToGroup(cursor);
            groups.add(group);
            cursor.moveToNext();
        }

        cursor.close();
        return groups;
    }

    private Group cursorToGroup(Cursor cursor) {
        Group group = new Group();
        group.setID(cursor.getLong(0));
        group.setName(cursor.getString(1));
        return group;
    }
}
