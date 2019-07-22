package com.example.musica;

import android.content.Context;
import android.widget.ArrayAdapter;

public class SongsAdapter extends ArrayAdapter {
    private String[] mSongNames;
    private String[] mArtists;
    private String[] mDurations;
    private String[] mAlbums;
    private Context mContext;

    public SongsAdapter(Context mContext, int resource, String[] names, String[] artists, String[] durations, String[] albums){
        super(mContext, resource);
        this.mContext = mContext;
        this.mSongNames = names;
        this.mArtists = artists;
        this.mDurations = durations;
        this.mAlbums = albums;
    }

    @Override
    public Object getItem(int position){
        String songName = mSongNames[position];
        String artist = mArtists[position];
        String duration = mDurations[position];
        String album = mAlbums[position];
        return String.format("%s ~ %s \n( %s ) \n%s", songName, artist, album, duration);
    }

    @Override
    public int getCount(){
        return mSongNames.length;
    }
}
