package com.example.musica;

import androidx.fragment.app.Fragment;

public class SongsActivity extends CommonFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new SongsFragment();
    }

}
