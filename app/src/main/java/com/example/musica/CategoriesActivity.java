package com.example.musica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

public class CategoriesActivity extends AppCompatActivity {

    private String[] categories = new String[]{
        "Hip-Hop", "Rock n' Roll", "Pop", "R & B", "Afro", "Electronic Dance", "Jazz", "Reggae", "Country", "Latin"
    };

    private String[] descriptions = new String[]{
      "Cool beats with fast flow of words", "Extraordinary style of music", "Fast paced beats", "Awesome voices", "African themed", "Beats only", "Low key", "Jamaican", "Old school", "Latin music"
    };

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        gridView = (GridView) findViewById(R.id.categories_grid);
        gridView.setAdapter(new CategoriesAdapter(this, categories, descriptions));
    }
}
