package com.example.musica.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.musica.R;

public class CategoriesAdapter extends BaseAdapter {
    private String[] mCategories;
    private String[] mDescriptions;
    private Context mContext;

    public CategoriesAdapter(Context context, String[] categories, String[] descriptions){
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;

        if (convertView == null){
            gridView = inflater.inflate(R.layout.category_items, null);
            TextView categoryView =  (TextView) gridView.findViewById(R.id.category_item);
            categoryView.setText(mCategories[position]);
        }else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
