package com.example.yehor.pixeltehtestapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yehor.pixeltehtestapp.R;
import com.example.yehor.pixeltehtestapp.model.UnsplashDataModel;
import com.example.yehor.pixeltehtestapp.view.ImageActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UnsplashListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_POST = 0, VIEW_TYPE_LOADING = 1;
    public static final String TYPE_ADAPTER_POSTS = "posts";
    public static final String TYPE_ADAPTER_SEARCH = "search";

    private ArrayList<UnsplashDataModel> unsplashData;
    private Context context;
    private boolean isLoading;
    private static int THRESHOLD = 1;
    private int lastVisibleItem, totalItemCount;
    private String adapterType;
    private int page;
    private OnLoadMoreListener onLoadMoreListener;

    public UnsplashListAdapter(ArrayList<UnsplashDataModel> unsplashData, Context context, RecyclerView target, String type, int page) {
        this.unsplashData = unsplashData;
        this.context = context;
        isLoading=false;
        adapterType = type;
        this.page = page;

        final LinearLayoutManager llm = (LinearLayoutManager)target.getLayoutManager();
        target.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = llm.getItemCount();
                lastVisibleItem = llm.findLastVisibleItemPosition();
                if(!isLoading && totalItemCount<=(lastVisibleItem+THRESHOLD)){
                    isLoading = true;
                    onLoadMoreListener.onLoadMore(adapterType);
                }
            }
        });
    }

    public void addData(ArrayList<UnsplashDataModel> data) {
        if (unsplashData == null) unsplashData = new ArrayList<>();
        unsplashData.addAll(data);
        notifyDataSetChanged();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener){
        onLoadMoreListener = listener;
    }

    public void setAdapterType(String type){
        adapterType = type;
    }

    public String getAdapterType() {
        return adapterType;
    }

    public void setLoaded(){isLoading = false;}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i== VIEW_TYPE_POST)
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_unsplash, viewGroup, false));
        else return new LoadingHolder(LayoutInflater.from(context).inflate(R.layout.item_list_unsplash_load, viewGroup, false));
    }

    @Override
    public int getItemViewType(int position) {
        return unsplashData.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_POST;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) viewHolder;
            holder.username.setText(unsplashData.get(i).getUsername());
            holder.likes.setText(String.valueOf(unsplashData.get(i).getLikes()));
            Picasso.get().load(unsplashData.get(i).getPreviewLink())
                    .error(R.drawable.icon_warning)
                    .placeholder(R.drawable.icon_image)
                    .into(holder.image);
            final String username = unsplashData.get(i).getUsername();
            final String image = unsplashData.get(i).getImageLink();
            final String download = unsplashData.get(i).getDownloadLink();
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // open activity with image
                    Intent intent = new Intent(context, ImageActivity.class);
                    intent.putExtra(ImageActivity.EXTRAS_USERNAME, username);
                    intent.putExtra(ImageActivity.EXTRAS_IMAGE_LINK, image);
                    intent.putExtra(ImageActivity.EXTRAS_DOWNLOAD_LINK, download);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return unsplashData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView username, likes;
        ImageView image;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.item_list_card);
            username = itemView.findViewById(R.id.item_list_username);
            likes = itemView.findViewById(R.id.item_list_likes);
            image = itemView.findViewById(R.id.item_list_image);
        }
    }

    class LoadingHolder extends RecyclerView.ViewHolder{
        LoadingHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
