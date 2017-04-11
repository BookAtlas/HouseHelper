package com.stevensbytes.steven.househelper.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Steven on 4/5/2017.
 */

public class HouseHelperDbHelper extends SQLiteOpenHelper {

    public static class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "task";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_DETAILS = "details";
        public static final String COLUMN_GROUP = "task_group";
    }

    public static class TaskGroup implements BaseColumns {
        public static final String TABLE_NAME = "groups";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "group_name";
    }

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "HouseHelperTask.db";


    private static final String SQL_CREATE_TASK =
            "CREATE TABLE " + TaskEntry.TABLE_NAME + " (" +
                    TaskEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    TaskEntry.COLUMN_DETAILS + " TEXT," +
                    TaskEntry.COLUMN_GROUP + " INTEGER)";

    private static final String SQL_CREATE_GROUPS =
            "CREATE TABLE " + TaskGroup.TABLE_NAME + " (" +
                    TaskGroup.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    TaskGroup.COLUMN_NAME + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TaskEntry.TABLE_NAME + ";DROP TABLE IF EXISTS " +
                    TaskGroup.TABLE_NAME;

    public HouseHelperDbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TASK);
        db.execSQL(SQL_CREATE_GROUPS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
