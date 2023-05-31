package com.example.musicplayer;

import android.net.Uri;

public class Song {
    // members
    String title;
    Uri uri;
    Uri artworkUri;
    int size;
    int duration;

    // constructor (right click --> generate --> constructor)

    public Song(String title, Uri uri, Uri artworkUri, int size, int duration) {
        this.title = title;
        this.uri = uri;
        this.artworkUri = artworkUri;
        this.size = size;
        this.duration = duration;
    }

    // getters (right click --> generate --> getter)

    public String getTitle() {
        return title;
    }

    public Uri getUri() {
        return uri;
    }

    public Uri getArtworkUri() {
        return artworkUri;
    }

    public int getSize() {
        return size;
    }

    public int getDuration() {
        return duration;
    }
}
