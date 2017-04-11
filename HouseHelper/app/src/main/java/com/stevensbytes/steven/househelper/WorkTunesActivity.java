package com.stevensbytes.steven.househelper;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import com.stevensbytes.steven.househelper.adapter.MainViewPagerAdapter;
import com.stevensbytes.steven.househelper.audio.AudioOb;
import com.stevensbytes.steven.househelper.audio.BaseAudioOb;
import com.stevensbytes.steven.househelper.service.MusicService;
import com.stevensbytes.steven.househelper.view.PlayListView;
import com.stevensbytes.steven.househelper.view.PlayView;

/**
 * Created by Steven on 4/9/2017.
 */

public class WorkTunesActivity extends AppCompatActivity {

    private ArrayList<AudioOb> contentList = new ArrayList<AudioOb>();
    private ViewPager viewPager;
    private MusicService musicService;
    private ImageView playView;
    private Intent playIntent;
    private boolean bound = false;
    private boolean paused = false;

    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            musicService = binder.getService();
            musicService.setTracks(contentList);
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        initInfo();
        initViewPager();
        if(playIntent == null){
            Log.d("AUDIO", "SETTING INTENT, PLAY SERVICE IS: " + musicConnection.toString());
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
        else{
            Log.d("AUDIO", "playIntent is NULL!!");
        }
    }

    @Override
    protected void onDestroy() {
        stopService(playIntent);
        musicService=null;
        super.onDestroy();
    }

    public void playTrack(View view){
        if(!musicService.isPlaying() && !paused) {
            musicService.play();
            Toast.makeText(this, "Loading track...", Toast.LENGTH_LONG).show();
            ((ImageView) view).setImageResource(R.drawable.pause_button);
        } else if (paused){
            musicService.resume();
            ((ImageView) view).setImageResource(R.drawable.pause_button);
            paused = false;
        } else {
            musicService.pause();
            ((ImageView) view).setImageResource(R.drawable.playscreen_play_play);
            paused = true;
        }
    }

    public void nextTrack(View view){
        musicService.next();
        paused = false;
        Toast.makeText(this, "Loading track...", Toast.LENGTH_LONG).show();
    }

    public void prevTrack(View view){
        musicService.previous();
        paused = false;
        Toast.makeText(this, "Loading track...", Toast.LENGTH_LONG).show();
    }

    public void skipForward(View view){
        musicService.skipForward();
    }

    public void skipBack(View view){
        musicService.skipBack();
    }

    private void initInfo() {
        AudioOb m1 = new AudioOb();
        m1.setURL("http://other.web.rh01.sycdn.kuwo.cn/resource/n3/77/1/1061700123.mp3");
        m1.setName("One Moment");

        AudioOb m2 = new AudioOb();
        m2.setURL("http://other.web.re01.sycdn.kuwo.cn/resource/n2/41/79/3442130618.mp3");
        m2.setName("I will remember you");

        AudioOb m3 = new AudioOb();
        m3.setURL("http://other.web.ra01.sycdn.kuwo.cn/resource/n2/128/72/74/2639398911.mp3");
        m3.setName("Young for you");

        contentList.add(m1);
        contentList.add(m2);
        contentList.add(m3);
    }

    private void initViewPager() {
        ArrayList<View> viewList = new ArrayList<View>();
        viewList.add(new PlayView(this));
        viewList.add(new PlayListView(this));
        viewPager = (ViewPager) findViewById(R.id.main_view_pager);
        viewPager.setAdapter(new MainViewPagerAdapter(viewList));
    }

    public void pickSong(View view){
        musicService.setTrack(Integer.parseInt(view.getTag().toString()));
        musicService.play();
    }

    public ArrayList<AudioOb> getContent(){
        return contentList;
    }
}
