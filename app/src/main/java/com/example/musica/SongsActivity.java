package com.example.musica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;


public class SongsActivity extends AppCompatActivity {

    private ListView mSongsList;
    private String[] songs = new String[]{
            "Not Afraid", "Juicy",  "My Name Is", "Time", "Homicide", "Isis", "No Good", "Blood // Water", "Come With me Now", "So Close", "All This Time"
    };
    private String[] artists = new String[]{
            "Eminem", "Biggie", "Eminem", "NF", "Logic ft Eminem", "Logic ft Joyner Lucas", "KALEO", "grandson", "KOMGOS", "NOTD ft Felix Jaehn", "Deorro"
    };
    private String[] durations = new String[]{
            "4:00", "3:08", "3:45", "5:56", "4:20", "4:02", "2:56", "3:49", "4:56", "4:01", "3:23"
    };
    private String[] albums = new String[]{
            "Recovery", "Ready To Die",  "My Name Is", "The search", "Homicide", "Isis", "A / B", "Blood // Water", "Come With me Now", "So Close", "All This Time"
    };
//    private String[] songs = getResources().getStringArray(R.array.songs);
//    private String[] artists = getResources().getStringArray(R.array.artists);
//    private String[] durations = getResources().getStringArray(R.array.duration);
//    private String[] albums = getResources().getStringArray(R.array.albums);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);
        mSongsList = (ListView) findViewById(R.id.songs_list);

        SongsAdapter adapter = new SongsAdapter(this, android.R.layout.simple_list_item_1, songs, artists, durations, albums);
        mSongsList.setAdapter(adapter);
    }
}
