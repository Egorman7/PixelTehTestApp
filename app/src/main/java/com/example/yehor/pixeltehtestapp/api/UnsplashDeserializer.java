package com.example.yehor.pixeltehtestapp.api;

import android.util.Log;

import com.example.yehor.pixeltehtestapp.model.UnsplashDataModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UnsplashDeserializer implements JsonDeserializer<UnsplashDataModel>{
    @Override
    public UnsplashDataModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        UnsplashDataModel unsplashDataElement = new UnsplashDataModel();
        JsonObject obj = json.getAsJsonObject();
        unsplashDataElement.setLikes(obj.get("likes").getAsInt());
        JsonObject urls = obj.get("urls").getAsJsonObject();
        unsplashDataElement.setImageLink(urls.get("raw").getAsString());
        unsplashDataElement.setPreviewLink(urls.get("small").getAsString());
        JsonObject links = obj.get("links").getAsJsonObject();
        unsplashDataElement.setDownloadLink(links.get("download").getAsString());
        JsonObject user = obj.get("user").getAsJsonObject();
        unsplashDataElement.setUsername(user.get("name").getAsString());
        return unsplashDataElement;
    }
}
