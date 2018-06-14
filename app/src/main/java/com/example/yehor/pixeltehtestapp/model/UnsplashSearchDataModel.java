package com.example.yehor.pixeltehtestapp.model;

import java.util.ArrayList;

public class UnsplashSearchDataModel {
    private int total;
    private ArrayList<UnsplashDataModel> results;

    public UnsplashSearchDataModel(int total, ArrayList<UnsplashDataModel> results) {
        this.total = total;
        this.results = results;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<UnsplashDataModel> getResults() {
        return results;
    }

    public void setResults(ArrayList<UnsplashDataModel> results) {
        this.results = results;
    }
}
