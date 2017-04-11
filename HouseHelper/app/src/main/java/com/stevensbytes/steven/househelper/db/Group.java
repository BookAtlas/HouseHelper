package com.stevensbytes.steven.househelper.db;

/**
 * Created by Steven on 4/8/2017.
 */

public class Group {
    private long id;
    private String name;

    public long getID() {
        return this.id;
    }

    public void setID(long value) {
        this.id = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }
}
