package com.example.musica;

import android.content.Context;
import android.widget.ArrayAdapter;

public class CategoriesAdapter extends ArrayAdapter {
    private String[] mCategories;
    private String[] mDescriptions;
    private Context mContext;

    public CategoriesAdapter(Context context, int resource, String[] categories, String[] descriptions){
     super(context, resource);
     this.mContext = context;
     this.mCategories = categories;
     this.mDescriptions = descriptions;
    }

    @Override
    public Object getItem(int position){
        String category = mCategories[position];
        String desc = mDescriptions[position];
        return String.format("%s", category);
    }

    @Override
    public int getCount(){
        return mCategories.length;
    }
}
