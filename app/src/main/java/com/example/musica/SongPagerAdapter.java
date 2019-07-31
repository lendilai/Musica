package com.example.musica;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class SongPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Song> mSongs;

    public SongPagerAdapter(FragmentManager fragmentManager, ArrayList<Song> songs){
        super(fragmentManager);
        mSongs = songs;
    }

    @Override
    public Fragment getItem(int position) {
        return SongDetailFragment.newInstance(mSongs.get(position));
    }

    @Override
    public int getCount() {
        return mSongs.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mSongs.get(position).getTitle();
    }
}
