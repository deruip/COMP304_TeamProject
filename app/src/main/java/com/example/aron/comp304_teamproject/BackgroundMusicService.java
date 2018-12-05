package com.example.aron.comp304_teamproject;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BackgroundMusicService extends Service {
    public static final String TAG = "stop.the.music.please";
    public MediaPlayer mp = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    public int onStartCommand(Intent intent, int flags, int startId)
    {
        List<Integer> songs = new ArrayList<>();
        songs.add(R.raw.music);
        songs.add(R.raw.music2);
        songs.add(R.raw.music3);


        int randomizer = (new Random().nextInt(songs.size()));
        int player = songs.get(randomizer);

        mp = MediaPlayer.create(this,player);
        mp.setLooping(true);
        mp.setVolume(35,100);
        mp.start();
        return Service.START_STICKY;
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
       mp.stop();
       mp.release();

    }

    @Override
    public void onLowMemory() {
        onDestroy();
    }
}
