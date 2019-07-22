package com.example.musica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.categories) LinearLayout mCategoriesIcon;
    @BindView(R.id.songs) LinearLayout mSongsIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
