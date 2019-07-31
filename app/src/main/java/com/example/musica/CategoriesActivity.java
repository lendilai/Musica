package com.example.musica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class CategoriesActivity extends AppCompatActivity {

    private String[] categories;
    private String[] names;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        categories = getResources().getStringArray(R.array.categories);
        names = getResources().getStringArray(R.array.categoryNames);
        gridView = (GridView) findViewById(R.id.categories_grid);
        gridView.setAdapter(new CategoriesAdapter(this, names, categories));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String mSearchChoice = categories[i];
                Intent intent = new Intent(CategoriesActivity.this, SongsActivity.class);
                intent.putExtra("songs", mSearchChoice);
                startActivity(intent);
            }
        });
    }
}
