package com.example.musica;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.parceler.Parcels;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SongsFragment extends Fragment {
    public static final String TAG = "SongsActivity";
    private ArrayList<Song> songs;
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
        return v;
    }


//Adapter
    public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder>{
        private ArrayList<Song> mSongs = new ArrayList<>();
        private Context mContext;


        @NonNull
        @Override
        public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.activity_songs,parent,false);
            SongHolder holder = new SongHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull SongHolder holder, int position) {
            holder.bind(mSongs.get(position));
        }

        @Override
        public int getItemCount() {
            return mSongs.size();
        }

        public SongAdapter(Context context, ArrayList<Song> songs){
            mContext = context;
            mSongs = songs;
        }


        //Holder
        public class SongHolder extends RecyclerView.ViewHolder{
            private TextView mNameText;
            private TextView mArtistText;
            private TextView mDurationText;
            private Context mContext;

            public SongHolder(View itemView){
                super(itemView);
                mNameText = itemView.findViewById(R.id.song_name);
                mArtistText = itemView.findViewById(R.id.song_artist);
                mDurationText = itemView.findViewById(R.id.song_duration);
                mContext = itemView.getContext();
//                itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        int itemPosition = getLayoutPosition();
//                        Intent intent = new Intent(mContext, SongDetailActivity.class);
//                        intent.putExtra("position", itemPosition);
//                        intent.putExtra("song", Parcels.wrap(mSongs));
//                        mContext.startActivity(intent);
//                    }
//                });
            }

            public void bind(Song song){
                mNameText.setText(song.getTitle());
                mArtistText.setText(song.getArtist().getName());
                mDurationText.setText(song.getDuration());
            }
        }
    }


    //Fetch songs
    public void getSongs(String track){
        final SpotifyService spotifyService = new SpotifyService();
        spotifyService.findSong(track, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "before running onResponse");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "before running process results");
                songs = spotifyService.processResults(response);
                final String data = response.body().toString();
                Log.i(TAG, "before running on the main thread");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSongAdapter = new SongAdapter(getActivity(), songs);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                        mRecyclerView.setAdapter(mSongAdapter);
                        Log.v(TAG, data);
                    }
                });
            }
        });
    }
}
































//Previous viewHolder
//    public class SongHolder extends RecyclerView.ViewHolder{
//        private TextView mNameText;
//        private TextView mArtistText;
//        private TextView mDurationText;
//        private Context mContext;
//        private Song mSong;
//
//        public SongHolder(LayoutInflater inflater, ViewGroup parent){
//            super(inflater.inflate(R.layout.activity_songs, parent, false));
//            mNameText = itemView.findViewById(R.id.song_name);
//            mArtistText = itemView.findViewById(R.id.song_artist);
//            mDurationText = itemView.findViewById(R.id.song_duration);
//            mContext = itemView.getContext();
//        }
//
//        private void bind(Song song){
//            mNameText.setText(song.getTitle());
//            mArtistText.setText(song.getArtist());
//            mDurationText.setText(song.getDuration().toString());
//        }
//    }
//
//    //Adapter
//    private class SongAdapter extends RecyclerView.Adapter<SongHolder>{
//        private Context mContext;
//        private ArrayList<Song> mSongs;
//
//        public SongAdapter(Context context, ArrayList<Song> songs){
//            mContext = context;
//            mSongs = songs;
//        }
//
//        @NonNull
//        @Override
//        public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            LayoutInflater inflater = LayoutInflater.from(getActivity());
//            return new SongHolder(inflater, parent);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull SongHolder holder, int position) {
//            Song song = mSongs.get(position);
//            holder.bind(song);
//        }
//
//        @Override
//        public int getItemCount() {
//            return mSongs.size();
//        }
//
//    }