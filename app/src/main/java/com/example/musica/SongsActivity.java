package com.example.musica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class SongsActivity extends AppCompatActivity {

    private ListView mSongsList;
    private static final String TAG = SongsActivity.class.getSimpleName();
    private String[] songs;
    private String[] artists;
    private String[] durations;
    private String[] albums;
    private EditText mEnteredSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);
        Resources res = getResources();
        songs = res.getStringArray(R.array.songs);
        artists = res.getStringArray(R.array.artists);
        durations = res.getStringArray(R.array.duration);
        albums = res.getStringArray(R.array.albums);
        Log.d(TAG, String.valueOf(songs[2]));
        mEnteredSong = findViewById(R.id.song_text);
        String track = mEnteredSong.getText().toString();
        mSongsList = (ListView) findViewById(R.id.songs_list);
        SongsAdapter adapter = new SongsAdapter(this, android.R.layout.simple_list_item_1, songs, artists, durations, albums);
        mSongsList.setAdapter(adapter);
        mSongsList.setBackgroundResource(R.drawable.list_styles);
        mSongsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String unLoadable = " ' " + songs[i] + " ' " + " cannot load at the moment";
                Toast.makeText(SongsActivity.this, unLoadable, Toast.LENGTH_SHORT).show();
            }
        });
        getSongs("3n3Ppam7vgaVa1iaRUc9Lp");
    }

    private void getSongs(String track){
        final SpotifyService spotifyService = new SpotifyService();
        spotifyService.findSong(track, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                    String jsonData = response.body().string();
                    Log.v(TAG, jsonData);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
