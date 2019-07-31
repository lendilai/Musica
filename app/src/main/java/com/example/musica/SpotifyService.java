package com.example.musica;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;


public class SpotifyService {

    public static void findSong(String track, Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        HttpUrl.Builder  builder = HttpUrl.parse(Constants.DEEZER_BASE_URL).newBuilder();
        builder.addQueryParameter(Constants.DEEZER_SEARCH_ITEM, track);
        String url = builder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", Constants.DEEZER_TOKEN)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public static ArrayList<Song> processResults(Response response){
        ArrayList<Song> foundSongs = new ArrayList<>();
        Gson gson = new Gson();
        try {
            String jsonData = response.body().string();
            JSONObject deezerJson = new JSONObject(jsonData);
            JSONArray tracksArray = deezerJson.getJSONArray("data");
            if (response.isSuccessful()){
                Type listType = new TypeToken<List<Song>>() {}.getType();
                foundSongs = gson.fromJson(tracksArray.toString(), listType);
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException ex){
            ex.printStackTrace();
        }
        return foundSongs;
    }
}
