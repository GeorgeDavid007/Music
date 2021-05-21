package com.sample.mark9;

import java.io.Serializable;

public class Song implements Serializable {
    private long id;
    private String path;
    private String title;
    private String artist;
    private String album;
    private String duration;
    private String  image;


    public Song(Long ID, String path, String title, String artist, String album, String duration) {
        this.path = path;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.id=ID;


    }

    public Song() {
    }

    public Song(String image){
        this.image=image;
    }

    public String getImage() {
        return image;
    }

    public long getID(){return id;}

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

}
