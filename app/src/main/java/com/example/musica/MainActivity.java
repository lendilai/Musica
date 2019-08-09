package com.example.musica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity{

    private LinearLayout mCategoriesIcon;
    private LinearLayout mSongsIcon;
    private TextView mTagPhrase;
    private EditText mSongInput;
    private Button mSearchButton;
    private Switch mLogOut;
    private TextView mWelcome;
    private static final String TAG  = MainActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCategoriesIcon = findViewById(R.id.categories);
        mSongsIcon = findViewById(R.id.songs);
        mTagPhrase = findViewById(R.id.tag_phrase);
        mSongInput = findViewById(R.id.song_input);
        mSearchButton = findViewById(R.id.search_button);
        mWelcome = findViewById(R.id.welcomeMessage);
        mLogOut = findViewById(R.id.LogOut);
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
                intent.putExtra("songs", songName);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    mWelcome.setText("Welcome to Musica, " + user.getDisplayName());
                }
            }
        };

        mLogOut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                logout();
            }
        });
    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthStateListener != null){
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }
}
