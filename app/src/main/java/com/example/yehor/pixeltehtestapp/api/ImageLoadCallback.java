package com.example.yehor.pixeltehtestapp.api;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.github.piasy.biv.loader.ImageLoader;

import java.io.File;

public class ImageLoadCallback implements ImageLoader.Callback {
    private ProgressBar progressBar;

    public ImageLoadCallback(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    public void onCacheHit(File image) {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onCacheMiss(File image) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onProgress(int progress) {
        if(progressBar.getProgress()!=progress)
            progressBar.setProgress(progress);
        else progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onSuccess(File image) {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFail(Exception error) {

    }
}
