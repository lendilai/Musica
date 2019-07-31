package com.example.musica;

import android.os.Bundle;
import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

public class SongDetailFragment extends Fragment {
    public static final String EXTRA_SONG_KEY = "song";
    private ImageView mArtistImage;
    private TextView mSongName;
    private TextView mArtistName;
    private TextView mDuration;
    private Song mSong;
    private ViewPager mViewPager;
    private SongPagerAdapter mSongPagerAdapter;
    ArrayList<Song> songs = new ArrayList<>();

    public static SongDetailFragment newInstance(Song song){
        SongDetailFragment songDetailFragment = new SongDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_SONG_KEY, Parcels.wrap(song));
        songDetailFragment.setArguments(args);
        return songDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSong = Parcels.unwrap(getArguments().getParcelable(EXTRA_SONG_KEY));
        int startpos = getActivity().getIntent().getIntExtra("position", 0);
        mSongPagerAdapter = new SongPagerAdapter(getFragmentManager(), songs);
        mViewPager.setAdapter(mSongPagerAdapter);
        mViewPager.setCurrentItem(startpos);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_song_detail, container, false);
        mArtistImage = v.findViewById(R.id.artistImage);
        mSongName = v.findViewById(R.id.detail_song_name);
        mArtistName = v.findViewById(R.id.detail_artist_name);
        mDuration = v.findViewById(R.id.detail_song_duration);
        mViewPager = v.findViewById(R.id.song_details);
        Picasso.get().load(mSong.getArtist().getPictureBig()).into(mArtistImage);
        mSongName.setText(mSong.getTitle());
        mArtistName.setText(mSong.getArtist().getName());
        mDuration.setText(mSong.getDuration());
        return v;
    }
}
