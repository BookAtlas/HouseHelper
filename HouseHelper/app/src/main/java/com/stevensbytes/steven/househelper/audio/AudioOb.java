package com.stevensbytes.steven.househelper.audio;

/**
 * Created by Steven on 4/9/2017.
 */

public class AudioOb extends BaseAudioOb {
    private long id;
    private String name;
    private String artist;
    private String size;
    private String duration;
    private String info;
    private String iconUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
