package com.example.yehor.pixeltehtestapp.api;

import com.example.yehor.pixeltehtestapp.application.App;
import com.example.yehor.pixeltehtestapp.model.UnsplashDataModel;
import com.example.yehor.pixeltehtestapp.model.UnsplashSearchDataModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UnsplashAPI {
    @GET("/photos?client_id="+App.CLIENT_ID)
    Call<ArrayList<UnsplashDataModel>> getPhotos(@Query("page") int page);
    @GET("/search/photos?client_id="+ App.CLIENT_ID)
    Call<UnsplashSearchDataModel> searchPhotos(@Query("page") int page, @Query("query") String search);
}
