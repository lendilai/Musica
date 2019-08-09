package com.example.musica;

import androidx.fragment.app.Fragment;

public class SavedSongActivity extends CommonFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new SavedSongFragment();
    }
}
