package com.example.luuvansang.servicetoplaymusic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnPlay, btnStop, btnNewPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();

        btnPlay.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnNewPage.setOnClickListener(this);
    }

    private void addControls() {
        btnNewPage=findViewById(R.id.btnNewPage);
        btnPlay=findViewById(R.id.btnPlay);
        btnStop=findViewById(R.id.btnStop);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnPlay:
                Intent intentPlayMusic = new Intent(MainActivity.this,MusicService.class);
                startService(intentPlayMusic);
                break;
            case R.id.btnStop:
                Intent intentStopMusic=new Intent(MainActivity.this,MusicService.class);
                stopService(intentStopMusic);
                break;
            case R.id.btnNewPage:
                Intent intentNewPage=new Intent(MainActivity.this,NewPage.class);
                startActivity(intentNewPage);
                break;
        }
    }
}
