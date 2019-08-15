package com.example.musica.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.View.OnDragListener;

import com.example.musica.R;
import com.example.musica.models.Song;
import com.example.musica.util.ItemTouchHelperAdapter;
import com.example.musica.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Collections;

public class FirebaseSavedSongsAdapter extends FirebaseRecyclerAdapter<Song, FirebaseViewHolder> implements ItemTouchHelperAdapter {
    private Query mDatabaseReference;
    private Context mContext;
    private OnStartDragListener mOnDragStartListener;
    private ChildEventListener mChildEventListener;
    private ArrayList<Song> mSongs = new ArrayList<>();

    public FirebaseSavedSongsAdapter(FirebaseRecyclerOptions<Song> options, Query reference, Context context, OnStartDragListener dragStartListener){
        super(options);
        mDatabaseReference = reference.getRef();
        mContext = context;
        mOnDragStartListener = dragStartListener;

        mChildEventListener = mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                mSongs.add(dataSnapshot.getValue(Song.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onBindViewHolder(@NonNull FirebaseViewHolder firebaseViewHolder, int i, @NonNull Song song) {
        firebaseViewHolder.bindSong(song);
        firebaseViewHolder.mDragIcon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN){
                    mOnDragStartListener.onStartDrag(firebaseViewHolder);
                }
                return false;
            }
        });
    }

    @NonNull
    @Override
    public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_songs,parent,false);
        return new FirebaseViewHolder(view);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mSongs, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        setIndexInFirebase();
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        mSongs.remove(position);
        getRef(position).removeValue();
    }

    private void setIndexInFirebase(){
        for (Song song : mSongs){
            int index = mSongs.indexOf(song);
            DatabaseReference ref = getRef(index);
            ref.child("index").setValue(Integer.toString(index));
        }
    }

    @Override
    public void stopListening() {
        super.stopListening();
        mDatabaseReference.removeEventListener(mChildEventListener);
    }
}
