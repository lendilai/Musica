package com.example.musica.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musica.adapters.FirebaseSavedSongsAdapter;
import com.example.musica.constants.Constants;
import com.example.musica.adapters.FirebaseViewHolder;
import com.example.musica.R;
import com.example.musica.models.Song;
import com.example.musica.util.ItemTouchCallback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SavedSongFragment extends Fragment {

    private DatabaseReference mDatabaseReference;
    private FirebaseSavedSongsAdapter mFirebaseRecyclerAdapter;
    private RecyclerView mRecyclerView;
    private ItemTouchHelper mItemTouchHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_songs_list, container, false);
        mRecyclerView = v.findViewById(R.id.songs_recycler_view);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_SONGS).child(uid);
        setUpFireBaseAdapter();
        return v;
    }

    private void setUpFireBaseAdapter(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        Query query = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_SONGS).child(uid).orderByChild(Constants.QUERY_INDEX);
        FirebaseRecyclerOptions<Song> options =new FirebaseRecyclerOptions.Builder<Song>()
                .setQuery(mDatabaseReference, Song.class).build();
        mFirebaseRecyclerAdapter = new FirebaseSavedSongsAdapter(options, query, getActivity(), this::onStartDrag);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mFirebaseRecyclerAdapter);
        ItemTouchHelper.Callback callback = new ItemTouchCallback(mFirebaseRecyclerAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mFirebaseRecyclerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                mFirebaseRecyclerAdapter.notifyDataSetChanged();
            }
        });
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

    public void onStartDrag(RecyclerView.ViewHolder viewHolder){
        mItemTouchHelper.startDrag(viewHolder);
    }
}
