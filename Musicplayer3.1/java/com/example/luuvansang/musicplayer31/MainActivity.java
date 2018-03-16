package com.example.luuvansang.musicplayer31;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //add Controls
    ListView lv_song_list;
    TextView tv_current_time, tv_time_total, tv_title;
    SeekBar sb_time_now;
    ImageButton btn_prev, btn_play, btn_stop, btn_next, btn_repeat;

    ArrayList<Song> arr_song_list;

    MediaPlayer mediaPlayer;
    private int positon_of_current_song = 1;
    int repeat_status = 2-1; //0 is repeat off, 1 is repeat one, 2 repeat all. schuffle?

    SimpleDateFormat timeFormat = new SimpleDateFormat("mm.ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        get_music_in_external_storage();
        init_music();
        set_repeat_music();
        event_buttons();
        event_listview();
        event_seekbar();
        update_current_time();
    }

    private void update_current_time() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sb_time_now.setProgress(mediaPlayer.getCurrentPosition());
                tv_current_time.setText(timeFormat.format(mediaPlayer.getCurrentPosition()));
                handler.postDelayed(this,200);
            }
        }, 10);
    }

    private void event_seekbar() {
        sb_time_now.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv_current_time.setText(timeFormat.format(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }

    private void event_listview() {
        lv_song_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                positon_of_current_song = i;
                init_music();
                play_music();
            }
        });
    }

    private void init_music() {
        try {
            mediaPlayer.reset();

            mediaPlayer.setDataSource(arr_song_list.get(positon_of_current_song).getPath());

            mediaPlayer.prepare();

            tv_title.setText(arr_song_list.get(positon_of_current_song).getTitle());

            tv_time_total.setText(timeFormat.format(arr_song_list.get(positon_of_current_song).getDuration()));

            sb_time_now.setMax(arr_song_list.get(positon_of_current_song).getDuration());
            sb_time_now.setProgress(0);

            btn_play.setImageResource(R.drawable.play);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void event_buttons() {
        btn_prev.setOnClickListener(this);
        btn_play.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_repeat.setOnClickListener(this);
    }

    private void get_music_in_external_storage() {
        Uri audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String projection[] = new String[]
                {
                  MediaStore.Audio.AudioColumns.TITLE,
                  MediaStore.Audio.AudioColumns.ARTIST,
                  MediaStore.Audio.AudioColumns.DATA,
                  MediaStore.Audio.AudioColumns.ALBUM,
                  MediaStore.Audio.AudioColumns.DURATION
                };

        String where = MediaStore.Audio.AudioColumns.DISPLAY_NAME+ " LIKE '%.mp3'";

        Cursor cursor = MainActivity.this.getContentResolver().query(audioUri,projection,where,
                                                                    null, null);

        String title, artist, path, album;
        int duration;

        int index_title = cursor.getColumnIndex(projection[0]);
        int index_artist = cursor.getColumnIndex(projection[1]);
        int index_path = cursor.getColumnIndex(projection[2]);
        int index_album = cursor.getColumnIndex(projection[3]);
        int index_duration = cursor.getColumnIndex(projection[4]);

        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            title = cursor.getString(index_title);
            artist = cursor.getString(index_artist);
            path = cursor.getString(index_path);
            album = cursor.getString(index_album);
            duration = cursor.getInt(index_duration);

            arr_song_list.add(new Song(title,artist,path,album,duration));

            cursor.moveToNext();
        }
        cursor.close();

        if(arr_song_list.size()==0)
        {
            //no music found!
            Toast.makeText(MainActivity.this, "Music not found!", Toast.LENGTH_SHORT).show();
            return;
        }

        //post to listview
        SongAdapter songAdapter = new SongAdapter(MainActivity.this,
                                                R.layout.layout_song,
                                                arr_song_list);
        lv_song_list.setAdapter(songAdapter);

    }

    private void addControls() {
        //map controls
        lv_song_list = findViewById(R.id.lv_song_list);
        tv_current_time = findViewById(R.id.tv_current_time);
        tv_title = findViewById(R.id.tv_title);
        tv_time_total = findViewById(R.id.tv_time_total);
        sb_time_now = findViewById(R.id.sb_time_now);
        btn_prev = findViewById(R.id.btn_prev);
        btn_play = findViewById(R.id.btn_play);
        btn_stop = findViewById(R.id.btn_stop);
        btn_next = findViewById(R.id.btn_next);
        btn_repeat = findViewById(R.id.btn_repeat);

        arr_song_list = new ArrayList<>();

        mediaPlayer = new MediaPlayer();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_prev:
                play_last_music();
                break;
            case R.id.btn_play:
                if(mediaPlayer.isPlaying())
                {
                    pause_music();
                } else {
                    play_music();
                }
                break;
            case R.id.btn_stop:
                stop_music();
                break;
            case R.id.btn_next:
                play_next_music();
                break;
            case R.id.btn_repeat:
                set_repeat_music();
                break;
            default:
                break;

        }

    }

    private void set_repeat_music() {
        //0 is repeat off, 1 is repeat one, 2 repeat all. 4 is schuffle
        repeat_status++;
        repeat_status = repeat_status%4;
        if(repeat_status == 0)
        {
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stop_music();
                }
            });

            btn_repeat.setImageResource(R.drawable.repeat_off);
        } else
        if(repeat_status == 1)
        {
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stop_music();
                    play_music();
                }
            });
            btn_repeat.setImageResource(R.drawable.repeat_one);
        } else
        if(repeat_status == 2)
        {
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {

                    play_next_music();
                }
            });

            btn_repeat.setImageResource(R.drawable.repeat_all);
        } else
        {
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Random random = new Random();
                    positon_of_current_song = random.nextInt(arr_song_list.size());
                    init_music();
                    play_next_music();
                }
            });

            btn_repeat.setImageResource(R.drawable.shuffle);
        }
    }

    private void play_last_music() {
        positon_of_current_song--;
        if(positon_of_current_song<0)
        {
            positon_of_current_song = arr_song_list.size()-1;
        }
        init_music();
        play_music();
    }

    private void play_next_music() {
        positon_of_current_song++;
        if(positon_of_current_song>=arr_song_list.size())
        {
            positon_of_current_song = 0;
        }
        init_music();
        play_music();
    }

    private void pause_music() {
        mediaPlayer.pause();

        btn_play.setImageResource(R.drawable.play);
    }

    private void stop_music() {
        mediaPlayer.stop();

        init_music();

        btn_play.setImageResource(R.drawable.play);
    }

    private void play_music() {
        mediaPlayer.start();

        btn_play.setImageResource(R.drawable.pause);
    }
}
