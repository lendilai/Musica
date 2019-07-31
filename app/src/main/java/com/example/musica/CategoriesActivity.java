package com.example.musica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

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

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String mDescText = descriptions[i];
//                Toast toast = Toast.makeText(CategoriesActivity.this, mDescText, Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//                toast.sho
                Intent intent = new Intent(CategoriesActivity.this, SongsActivity.class);

            }
        });
    }
}
