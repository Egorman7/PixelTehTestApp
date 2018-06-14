package com.example.yehor.pixeltehtestapp.api;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.yehor.pixeltehtestapp.adapters.OnAsyncDoneListener;
import com.example.yehor.pixeltehtestapp.adapters.UnsplashListAdapter;
import com.example.yehor.pixeltehtestapp.application.App;
import com.example.yehor.pixeltehtestapp.model.UnsplashSearchDataModel;

import java.lang.ref.WeakReference;

public class UnsplashSearchTask extends AsyncTask<String, Integer, UnsplashSearchDataModel> {

    private WeakReference<Context> contextRef;
    private WeakReference<RecyclerView> viewRef;
    private int page;
    private OnAsyncDoneListener listener;

    public UnsplashSearchTask(Context context, RecyclerView view, int page, OnAsyncDoneListener listener) {
        contextRef = new WeakReference<>(context);
        viewRef = new WeakReference<>(view);
        this.page = page;
        this.listener = listener;
    }

    @Override
    protected UnsplashSearchDataModel doInBackground(String... strings) {
        try {
            UnsplashSearchDataModel data = App.getApi().searchPhotos(page, strings[0]).execute().body();
            return data;
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(UnsplashSearchDataModel unsplashSearchDataModel) {
        Context context = contextRef.get();
        if(unsplashSearchDataModel != null) {
            Toast.makeText(context, "Данные загружены!", Toast.LENGTH_SHORT).show();
            Log.d("LOADING", "Search for page " + page + " loaded!");
            UnsplashListAdapter adapter = (UnsplashListAdapter)viewRef.get().getAdapter();
            if(adapter!= null) {adapter.addData(unsplashSearchDataModel.getResults()); adapter.setLoaded(); adapter.setAdapterType(UnsplashListAdapter.TYPE_ADAPTER_SEARCH);}
            else {
                viewRef.get().setAdapter(new UnsplashListAdapter(unsplashSearchDataModel.getResults(), context, viewRef.get(), UnsplashListAdapter.TYPE_ADAPTER_SEARCH, page));
                listener.doItWhenDone((UnsplashListAdapter)viewRef.get().getAdapter());
            }
        }
        else Toast.makeText(context, "Ошибка при загрузке данных!", Toast.LENGTH_SHORT).show();
    }
}
