package com.example.musica;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class FirebaseViewHolder extends RecyclerView.ViewHolder {

    View mView;
    Context mContext;

    public FirebaseViewHolder(View itemView){
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindSong(Song song){
        TextView mNameText = itemView.findViewById(R.id.song_name);
        TextView mArtistText = itemView.findViewById(R.id.song_artist);
        TextView mDurationText = itemView.findViewById(R.id.song_duration);
        mNameText.setText(song.getTitle());
        mArtistText.setText(song.getArtist().getName());
        mDurationText.setText(song.getDuration() + "  seconds");
    }
}
