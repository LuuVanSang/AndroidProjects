package com.example.luuvansang.servicetoplaymusic;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by LUU VAN SANG on 11-Mar-18.
 */

public class MusicService extends Service {
    MediaPlayer song;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        song=MediaPlayer.create(this,R.raw.nhantuoi20);
        song.setLooping(true);
        Toast.makeText(this,"Music ready!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        song.start();
        Toast.makeText(this,"Music started!",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        song.stop();
        Toast.makeText(this,"Music stopped!",Toast.LENGTH_LONG).show();
    }
}
