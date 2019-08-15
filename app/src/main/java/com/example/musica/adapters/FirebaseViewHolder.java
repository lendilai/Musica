package com.example.musica.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.musica.R;
import com.example.musica.models.Song;
import com.example.musica.util.ItemTouchHelperViewHolder;

public class FirebaseViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

    View mView;
    public ImageView mDragIcon;
    Context mContext;

    public FirebaseViewHolder(View itemView){
        super(itemView);
        ImageView saveIcon = itemView.findViewById(R.id.save_icon);
        saveIcon.setVisibility(View.INVISIBLE);
        mDragIcon = itemView.findViewById(R.id.drag_icon);
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

    @Override
    public void onItemSelected(){
        itemView.animate().alpha(0.7f).scaleX(0.9f).scaleY(0.9f).setDuration(500);
    }

    @Override
    public void onItemClear(){
        itemView.animate().alpha(1f).scaleY(1f).scaleX(1f);
    }
}
