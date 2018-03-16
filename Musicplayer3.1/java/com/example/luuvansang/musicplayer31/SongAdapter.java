package com.example.luuvansang.musicplayer31;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by LUU VAN SANG on 16-Mar-18.
 */

public class SongAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<Song> songList;

    public SongAdapter(Context context, int layout, List<Song> songList) {
        this.context = context;
        this.layout = layout;
        this.songList = songList;
    }

    @Override
    public int getCount() {
        return songList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(layout,null);

        //map
        TextView tv_title = view.findViewById(R.id.tv_song_title);
        tv_title.setText(position+". "+songList.get(position).getTitle());
        TextView tv_martist = view.findViewById(R.id.tv_song_artist);
        tv_martist.setText(songList.get(position).getArtist());
        TextView tv_song_duration = view.findViewById(R.id.tv_song_duration);
        SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
        tv_song_duration.setText(timeFormat.format(songList.get(position).getDuration()));

        return view;
    }
}
