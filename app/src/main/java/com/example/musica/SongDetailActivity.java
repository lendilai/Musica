package com.example.musica;

import android.os.Bundle;
import android.os.Parcel;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import org.parceler.Parcels;

import java.util.ArrayList;

public class SongDetailActivity extends CommonFragmentActivity{
    private ViewPager mViewPager;
    private SongPagerAdapter mSongPagerAdapter;
    ArrayList<Song> songs = new ArrayList<>();

    @Override
    protected Fragment createFragment(){
        return new SongDetailFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);
        mViewPager = findViewById(R.id.song_details);
        songs = Parcels.unwrap(getIntent().getParcelableExtra(SongDetailFragment.EXTRA_SONG_KEY));
        int start = getIntent().getIntExtra("position", 0);
        mSongPagerAdapter = new SongPagerAdapter(getSupportFragmentManager(), songs);
        mViewPager.setAdapter(mSongPagerAdapter);
        mViewPager.setCurrentItem(start);
    }
}
