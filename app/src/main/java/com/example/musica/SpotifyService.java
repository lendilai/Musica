package com.example.musica;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.HttpUrl;
import okhttp3.Request;


public class SpotifyService {

    public static void findSong(String track, Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        HttpUrl.Builder  builder = HttpUrl.parse(Constants.SPOTIFY_BASE_URL).newBuilder();
        builder.addQueryParameter(Constants.SPOTIFY_SEARCH_ITEM, track);
        String url = builder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", Constants.SPOTIFY_TOKEN)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }
}
