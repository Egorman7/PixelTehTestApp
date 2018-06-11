package com.example.yehor.pixeltehtestapp.application;

import android.app.Application;

import com.example.yehor.pixeltehtestapp.api.UnsplashAPI;
import com.example.yehor.pixeltehtestapp.api.UnsplashDeserializer;
import com.example.yehor.pixeltehtestapp.model.UnsplashDataModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    public final static String API_URL = "https://api.unsplash.com/",
        CLIENT_ID = "67338cee21c81d99280c3313f7e0f614f525b8056ed60394626b48e75588c0d3";

    private static UnsplashAPI unsplashAPI;
    private Retrofit retrofit;
    private Gson gson;

    @Override
    public void onCreate() {
        super.onCreate();

        gson = new GsonBuilder()
                .registerTypeAdapter(UnsplashDataModel.class, new UnsplashDeserializer())
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        unsplashAPI = retrofit.create(UnsplashAPI.class);
    }

    public static UnsplashAPI getApi(){
        return unsplashAPI;
    }
}
