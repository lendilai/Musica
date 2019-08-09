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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity{

    private LinearLayout mCategoriesIcon;
    private LinearLayout mSongsIcon;
    private TextView mTagPhrase;
    private EditText mSongInput;
    private Button mSearchButton;
    private Switch mLogOut;
    private static final String TAG  = MainActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference mDatabaseReference;
    private ValueEventListener mValueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_SONG_NODE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCategoriesIcon = findViewById(R.id.categories);
        mSongsIcon = findViewById(R.id.songs);
        mTagPhrase = findViewById(R.id.tag_phrase);
        mSongInput = findViewById(R.id.song_input);
        mSearchButton = findViewById(R.id.search_button);
        mLogOut = findViewById(R.id.LogOut);
        mLogOut.setTextOff("Logged Out");
        Typeface OpenSans = Typeface.createFromAsset(getAssets(), "fonts/PlayfairDisplaySC-Regular.otf");
        mTagPhrase.setTypeface(OpenSans);
        mValueEventListener = mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot songData : dataSnapshot.getChildren()){
                    String song = songData.getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                saveSongToDB(songName);
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
                    mLogOut.setText("Logged In as, " + user.getDisplayName());
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

    public void saveSongToDB(String song){
        mDatabaseReference.push().setValue(song);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatabaseReference.removeEventListener(mValueEventListener);
    }
}
