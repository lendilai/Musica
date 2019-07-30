package com.example.musica;

import android.graphics.Point;

public class Song {
    private String mTitle;
    private int mDuration;
    private boolean mExplicit;
    private String mArtist;
    private String mPicture;

    public Song(String title, int duration, boolean explicit, String artist, String picture){
        mTitle = title;
        mDuration = duration;
        mExplicit = explicit;
        mArtist = artist;
        mPicture = picture;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public boolean isExplicit() {
        return mExplicit;
    }

    public void setExplicit(boolean explicit) {
        mExplicit = explicit;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public String getPicture() {
        return mPicture;
    }

    public void setPicture(String picture) {
        mPicture = picture;
    }
}
