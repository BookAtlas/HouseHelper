package com.stevensbytes.steven.househelper.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.stevensbytes.steven.househelper.audio.AudioOb;
import com.stevensbytes.steven.househelper.audio.BaseAudioOb;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steven on 4/9/2017.
 */

public class MusicService extends Service implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
    private static final String ACTION_PLAY = "com.stevensbytes.steven.action.PLAY";
    private final IBinder binder = new MusicBinder();

    private MediaPlayer mediaPlayer;
    private ArrayList<AudioOb> tracks;
    private int trackPos;
    private BaseAudioOb currTrack;

    @Override
    public void onCreate(){
        trackPos = 0;
        mediaPlayer = new MediaPlayer();
        musicPlayerSetup();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public boolean onUnbind(Intent intent){
        mediaPlayer.stop();
        mediaPlayer.release();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    public void musicPlayerSetup(){
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);

    }

    public void setTracks(ArrayList<AudioOb> allTracks){
        this.tracks = allTracks;
    }

    public void play(){
        mediaPlayer.reset();
        BaseAudioOb track = tracks.get(trackPos);
        try{
            mediaPlayer.setDataSource(track.getURL());
        } catch (IOException e){
            Log.e("HOUSE HELPER TUNES", "ERROR SETTING DATA SOURCE", e);
        }
        mediaPlayer.prepareAsync();
    }

    public void setTrack(int trackIndex){
        trackPos = trackIndex;
    }

    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }

    public void next() {
        trackPos += 1;
        if ( trackPos >= tracks.size()){
            trackPos = 0;
        }
        this.play();
    }

    public void pause() {
        this.mediaPlayer.pause();
    }

    public void resume() {
        this.mediaPlayer.start();
    }

    public void previous() {
        trackPos -= 1;
        if ( trackPos < 0){
            trackPos = tracks.size() - 1;
        }
        this.play();
    }

    public void skipForward() {
        int currPos = mediaPlayer.getCurrentPosition();
        int length = mediaPlayer.getDuration();
        currPos += 15000;
        if (currPos >= length){
            currPos = length - 5000;
        }
        else {
            mediaPlayer.seekTo(currPos);
        }
    }

    public void skipBack() {
        int currPos = mediaPlayer.getCurrentPosition();
        currPos -= 15000;
        if (currPos < 0){
            currPos = 0;
        }
            mediaPlayer.seekTo(currPos);
    }

    public class MusicBinder extends Binder {
        public MusicService getService(){
            return MusicService.this;
        }
    }
}
