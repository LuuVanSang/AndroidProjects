package com.example.luuvansang.musicplayer31;

/**
 * Created by LUU VAN SANG on 16-Mar-18.
 */

public class Song {
    private String title, artist, path, album;
    private int duration;

    public Song(String title, String artist, String path, String album, int duration) {
        this.title = title;
        this.artist = artist;
        this.path = path;
        this.album = album;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
