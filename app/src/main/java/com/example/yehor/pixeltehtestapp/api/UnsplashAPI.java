package com.example.yehor.pixeltehtestapp.api;

import com.example.yehor.pixeltehtestapp.application.App;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UnsplashAPI {
    @GET("/photos?client_id="+App.CLIENT_ID)
    Call<String> getPhotos(@Query("page") int page);
}
