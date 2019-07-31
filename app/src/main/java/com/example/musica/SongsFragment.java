package com.example.musica;

import android.content.Context;
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
import java.util.ArrayList;

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
        String theSong = getActivity().getIntent().getStringExtra("songs");
        getSongs(theSong);
        Log.i(TAG, "getSongs method passed");
        mRecyclerView = v.findViewById(R.id.songs_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

    public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder>{
        private ArrayList<Song> mSongs;
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

    public void getSongs(String track){
        final SpotifyService spotifyService = new SpotifyService();
        spotifyService.findSong(track, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                songs = spotifyService.processResults(response);
                final String data = response.body().toString();
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