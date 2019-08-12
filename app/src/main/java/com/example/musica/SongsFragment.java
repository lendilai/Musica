package com.example.musica;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    private SharedPreferences mSharedPreferences;
    private String mSearchedSong;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_songs_list, container, false);
        String theSong = getActivity().getIntent().getStringExtra("songs");
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mSearchedSong = mSharedPreferences.getString(Constants.PREFERENCE_KEY, null);
        if (mSearchedSong != null){
            getSongs(mSearchedSong);
        }
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
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String uid = user.getUid();
                        DatabaseReference songsRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_SONGS).child(uid);
                        DatabaseReference pushReference = songsRef.push();
                        String pushId = pushReference.getKey();
                        mSongs.get(getLayoutPosition()).setPushId(pushId);
                        pushReference.setValue(mSongs.get(getLayoutPosition()));
                        Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
                    }
                });
                mContext = itemView.getContext();
            }

            public void bind(Song song){
                mNameText.setText(song.getTitle());
                mArtistText.setText(song.getArtist().getName());
                mDurationText.setText(song.getDuration() + "  seconds");
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.musica_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saved_songs:
                Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), SavedSongActivity.class);
            startActivity(intent);
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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