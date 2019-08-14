package com.example.musica.ui;

import androidx.fragment.app.Fragment;

public class SongsActivity extends CommonFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new SongsFragment();
    }

}
