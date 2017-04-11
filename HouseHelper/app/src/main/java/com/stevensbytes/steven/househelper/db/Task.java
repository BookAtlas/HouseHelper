package com.stevensbytes.steven.househelper.db;

import android.util.Log;

/**
 * Created by Steven on 4/8/2017.
 */

public class Task {
    private long id;
    private String details;
    private long group;

    public long getID(){
        return this.id;
    }

    public void setID(long value){
        this.id = value;
    }

    public String getDetails(){
        Log.d("TEST MESSAG", "details are: " + this.details);
        return this.details;
    }

    public void setDetails(String value){
        this.details = value;
    }

    public long getGroup(){
        return this.group;
    }

    public void setGroup(long value){
        this.group = value;
    }
}
