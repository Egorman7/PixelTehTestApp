package com.example.yehor.pixeltehtestapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.yehor.pixeltehtestapp.R;
import com.example.yehor.pixeltehtestapp.adapters.OnAsyncDoneListener;
import com.example.yehor.pixeltehtestapp.adapters.OnLoadMoreListener;
import com.example.yehor.pixeltehtestapp.adapters.UnsplashListAdapter;
import com.example.yehor.pixeltehtestapp.api.UnsplashAsyncTask;
import com.example.yehor.pixeltehtestapp.api.UnsplashSearchTask;

public class MainActivity extends AppCompatActivity implements OnAsyncDoneListener {

    private RecyclerView mRecyclerView;
    private int page;
    private SearchView mSearchView;
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        page = 1;

        mRecyclerView = findViewById(R.id.main_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        setUpAdapter();
        mSearchView = findViewById(R.id.main_search_view);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                query = s;
                mRecyclerView.setAdapter(null);
                page=1;
                try{
                    new UnsplashSearchTask(MainActivity.this, mRecyclerView, page, MainActivity.this).execute(s);
                } catch (Exception ex) {ex.printStackTrace(); Toast.makeText(MainActivity.this, "Ошибка поиска!", Toast.LENGTH_SHORT).show();}
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                setUpAdapter();
                return true;
            }
        });

    }

    @Override
    public void doItWhenDone(UnsplashListAdapter adapter) {
        adapter.setLoaded();
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(String adapterType) {
                page++;
                Log.d("LOADING", "Load more!");
                if(adapterType.equals(UnsplashListAdapter.TYPE_ADAPTER_POSTS)){
                    Log.d("LOADING", "Load more - POSTS!");
                    new UnsplashAsyncTask(MainActivity.this, mRecyclerView, page, MainActivity.this).execute();
                    return;
                } else if(adapterType.equals(UnsplashListAdapter.TYPE_ADAPTER_SEARCH)){
                    Log.d("LOADING", "Load more - SEARCH!");
                    new UnsplashSearchTask(MainActivity.this, mRecyclerView, page, MainActivity.this).execute(query);
                    return;
                }
            }
        });
    }

    private void setUpAdapter(){
        mRecyclerView.setAdapter(null);
        page=1;
        try {
            new UnsplashAsyncTask(this, mRecyclerView, page, this).execute();
        } catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(this, "Ошибка установки адаптера!", Toast.LENGTH_SHORT).show();
        }
    }
}
