package com.example.musica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

public class CategoriesActivity extends AppCompatActivity {

    private String[] categories;
    private String[] descriptions;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        categories = getResources().getStringArray(R.array.categories);
        descriptions = getResources().getStringArray(R.array.descriptions);
        gridView = (GridView) findViewById(R.id.categories_grid);
        gridView.setAdapter(new CategoriesAdapter(this, categories, descriptions));
    }
}
