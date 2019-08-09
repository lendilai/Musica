package com.example.musica;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.datatype.Duration;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SongsFragment extends Fragment {
    public static final String TAG = "SongsActivity";
    private ArrayList<Song> songs;
    private RecyclerView mRecyclerView;
    private SongAdapter mSongAdapter;
    private SharedPreferences mSharedPreferences;
    private String mSongName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_songs_list, container, false);
//        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        mSongName = mSharedPreferences.getString(Constants.PREFERENCE_KEY, null);
//        if (mSongName != null){
//            getSongs(mSongName);
//        }
        String theSong = getActivity().getIntent().getStringExtra("songs");
        getSongs(theSong);
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
            private ImageView mShareButton;
            private Context mContext;
            private ImageView mSaveButton;

            public SongHolder(View itemView){
                super(itemView);
                mNameText = itemView.findViewById(R.id.song_name);
                mArtistText = itemView.findViewById(R.id.song_artist);
                mDurationText = itemView.findViewById(R.id.song_duration);
                mShareButton = itemView.findViewById(R.id.share_icon);
                mSaveButton = itemView.findViewById(R.id.save_icon);
                mShareButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, mSongs.get(getLayoutPosition()).getTitle() + " by " + mSongs.get(getLayoutPosition()).getArtist().getName());
                        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.song_subject));
                        intent = Intent.createChooser(intent, getString(R.string.share_on));
                        startActivity(intent);
                    }
                });
                mSaveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference songsRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_SONGS);
                        songsRef.push().setValue(mSongs);
                        Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
                    }
                });
                mContext = itemView.getContext();
//                itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        int itemPosition = getLayoutPosition();
//                        Intent intent = new Intent(mContext, SongDetailActivity.class);
//                        intent.putExtra("position", itemPosition);
//                        String meh = mSongs.get(itemPosition).getTitle();
//                        Log.i(TAG, meh);
//                        intent.putExtra(SongDetailFragment.EXTRA_SONG_KEY, Parcels.wrap(mSongs));
//                        mContext.startActivity(intent);
//                    }
//                });
            }

            public void bind(Song song){
                mNameText.setText(song.getTitle());
                mArtistText.setText(song.getArtist().getName());
                mDurationText.setText(song.getDuration() + "  seconds");
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