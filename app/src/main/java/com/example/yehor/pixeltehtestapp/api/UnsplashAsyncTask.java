package com.example.yehor.pixeltehtestapp.api;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.yehor.pixeltehtestapp.adapters.OnAsyncDoneListener;
import com.example.yehor.pixeltehtestapp.adapters.UnsplashListAdapter;
import com.example.yehor.pixeltehtestapp.application.App;
import com.example.yehor.pixeltehtestapp.model.UnsplashDataModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class UnsplashAsyncTask extends AsyncTask <Void, Void, ArrayList<UnsplashDataModel>> {
    private WeakReference<Context> contextRef;
    private WeakReference<RecyclerView> viewRef;
    private int page;
    private OnAsyncDoneListener listener;

    public UnsplashAsyncTask(Context context, RecyclerView view, int page, OnAsyncDoneListener listener) {
        contextRef = new WeakReference<>(context);
        viewRef = new WeakReference<>(view);
        this.page = page;
        this.listener = listener;
    }

    @Override
    protected ArrayList<UnsplashDataModel> doInBackground(Void... integers) {
        try {
            ArrayList<UnsplashDataModel> data = App.getApi().getPhotos(page).execute().body();
            Log.d("GET", "data size = " + data.size() + "\nfirst: " + data.get(0).getUsername());
            return data;
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<UnsplashDataModel> unsplashDataModels) {
        Context context = contextRef.get();
        if(unsplashDataModels != null) {
            Toast.makeText(context, "Данные загружены!", Toast.LENGTH_SHORT).show();
            Log.d("LOADING", "Data for page " + page + " loaded!");
            UnsplashListAdapter adapter = (UnsplashListAdapter)viewRef.get().getAdapter();
            if(adapter!= null) {adapter.addData(unsplashDataModels); adapter.setLoaded(); adapter.setAdapterType(UnsplashListAdapter.TYPE_ADAPTER_POSTS);}
            else {
                viewRef.get().setAdapter(new UnsplashListAdapter(unsplashDataModels, context, viewRef.get(), UnsplashListAdapter.TYPE_ADAPTER_POSTS, page));
                listener.doItWhenDone((UnsplashListAdapter)viewRef.get().getAdapter());
            }
        }
        else Toast.makeText(context, "Ошибка при загрузке данных!", Toast.LENGTH_SHORT).show();
    }
}
