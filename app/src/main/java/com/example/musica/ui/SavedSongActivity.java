package com.example.musica.ui;

import androidx.fragment.app.Fragment;

public class SavedSongActivity extends CommonFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new SavedSongFragment();
    }
}
