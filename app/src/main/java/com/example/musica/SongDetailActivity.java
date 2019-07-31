package com.example.musica;

import androidx.fragment.app.Fragment;

public class SongDetailActivity extends CommonFragmentActivity{

    @Override
    protected Fragment createFragment(){
        return new SongDetailFragment();
    }
}
