package com.example.musica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    private LinearLayout mCategoriesIcon;
    private LinearLayout mSongsIcon;
    private TextView mTagPhrase;
    private EditText mSongInput;
    private Button mSearchButton;
    private static final String TAG  = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCategoriesIcon = (LinearLayout) findViewById(R.id.categories);
        mSongsIcon = (LinearLayout) findViewById(R.id.songs);
        mTagPhrase = (TextView) findViewById(R.id.tag_phrase);
        mSongInput = findViewById(R.id.song_input);
        FragmentManager fragmentManager = getSupportFragmentManager();
        mSearchButton = findViewById(R.id.search_button);
        Typeface OpenSans = Typeface.createFromAsset(getAssets(), "fonts/PlayfairDisplaySC-Regular.otf");
        mTagPhrase.setTypeface(OpenSans);
        mSongsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "intent sent");
                Intent intent = new Intent(MainActivity.this, SongsActivity.class);
                startActivity(intent);
            }
        });

        mCategoriesIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CategoriesActivity.class);
                startActivity(intent);
            }
        });

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String songName = mSongInput.getText().toString();
                Intent intent = new Intent(MainActivity.this, SongsActivity.class);
                intent.putExtra("songName", songName);
                startActivity(intent);
            }
        });
    }


}
