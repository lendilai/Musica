package com.example.musica;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SongsFragment extends Fragment {
    public static final String TAG = "SongsActivity";
    private ArrayList<Song> songs = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private SongAdapter mSongAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_songs_list, container, false);
        getSongs("eminem");
        Log.i(TAG, "getSongs method passed");
        mRecyclerView = v.findViewById(R.id.songs_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return v;
    }

    //ViewHolder
    private class SongHolder extends RecyclerView.ViewHolder{
        public SongHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.activity_songs, parent, false));
        }
    }

    //Adapter
    private class SongAdapter extends RecyclerView.Adapter<SongHolder>{
        private String[] mSongs;
        private String[] mArtists;
        private String[] mDuration;

        public SongAdapter(String[] songs, String[] artist, String[] duration){
            mSongs = songs;
            mArtists = artist;
            mDuration = duration;
        }

        @NonNull
        @Override
        public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new SongHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SongHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mSongs.length;
        }

    }

    private void updateUI(){
        String[] songs = getResources().getStringArray(R.array.songs);
        String[] artists = getResources().getStringArray(R.array.artists);
        String[] durations = getResources().getStringArray(R.array.duration);
        mSongAdapter = new SongAdapter(songs, artists, durations);
        mRecyclerView.setAdapter(mSongAdapter);
    }


    private void getSongs(String track){
        final SpotifyService spotifyService = new SpotifyService();
        spotifyService.findSong(track, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                songs = spotifyService.processResults(response);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] songNames = new String[songs.size()];
                        for (int i = 0; i<songNames.length; i++ ){
                            songNames[i] = songs.get(i).getTitle();
                        }
                    }
                });
            }
        });
    }
}
