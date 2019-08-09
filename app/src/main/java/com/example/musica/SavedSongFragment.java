package com.example.musica;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;

public class SavedSongFragment extends Fragment {

    private DatabaseReference mDatabaseReference;
    private FirebaseRecyclerAdapter<Song, FirebaseViewHolder> mFirebaseRecyclerAdapter;
    private RecyclerView mRecyclerView;
    @BindView(R.id.share_icon) ImageView mShareButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_songs_list, container, false);
        mRecyclerView = v.findViewById(R.id.songs_recycler_view);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_SONGS).child(uid);
        mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent = Intent.createChooser(intent, getString(R.string.share_on));
                startActivity(intent);
            }
        });
        setUpFireBaseAdapter();
        return v;
    }

    private void setUpFireBaseAdapter(){
        FirebaseRecyclerOptions<Song> options =new FirebaseRecyclerOptions.Builder<Song>()
                .setQuery(mDatabaseReference, Song.class).build();

        mFirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Song, FirebaseViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewHolder firebaseViewHolder, int i, @NonNull Song song) {
                firebaseViewHolder.bindSong(song);
            }

            @NonNull
            @Override
            public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_songs,parent,false);
                FirebaseViewHolder firebaseViewHolder = new FirebaseViewHolder(view);
                return firebaseViewHolder;
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mFirebaseRecyclerAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseRecyclerAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mFirebaseRecyclerAdapter != null){
            mFirebaseRecyclerAdapter.stopListening();
        }
    }
}
