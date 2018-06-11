package com.example.yehor.pixeltehtestapp.api;

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

public class UnsplashDeserializer implements JsonDeserializer<List<UnsplashDataModel>>{
    @Override
    public List<UnsplashDataModel> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ArrayList<UnsplashDataModel> unsplashData = new ArrayList<>();
        JsonArray data = json.getAsJsonArray();
        for(JsonElement el : data){
            UnsplashDataModel unsplashDataElement = new UnsplashDataModel();
            JsonObject obj = el.getAsJsonObject();
            unsplashDataElement.setLikes(obj.get("likes").getAsInt());
            JsonObject urls = obj.get("urls").getAsJsonObject();
            unsplashDataElement.setImageLink(urls.get("raw").getAsString());
            unsplashDataElement.setPreviewLink(urls.get("thumb").getAsString());
            JsonObject links = obj.get("links").getAsJsonObject();
            unsplashDataElement.setDownloadLink(links.get("download").getAsString());
            JsonObject user = obj.get("user").getAsJsonObject();
            unsplashDataElement.setUsername(user.get("name").getAsString());
            unsplashData.add(unsplashDataElement);
        }
        return unsplashData;
    }
}
