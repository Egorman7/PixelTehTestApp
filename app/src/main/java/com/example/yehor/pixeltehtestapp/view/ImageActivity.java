package com.example.yehor.pixeltehtestapp.view;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.yehor.pixeltehtestapp.R;
import com.example.yehor.pixeltehtestapp.api.ImageLoadCallback;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.indicator.progresspie.ProgressPieIndicator;
import com.github.piasy.biv.loader.ImageLoader;
import com.github.piasy.biv.loader.fresco.FrescoImageLoader;
import com.github.piasy.biv.view.BigImageView;
import com.github.piasy.biv.view.ImageSaveCallback;

import java.io.File;

public class ImageActivity extends AppCompatActivity {

    public static final String EXTRAS_USERNAME = "user", EXTRAS_IMAGE_LINK = "image", EXTRAS_DOWNLOAD_LINK = "download";
    private static final int WRITE_STORAGE_REQUEST = 112;
    private BigImageView mImageView;
    private ImageButton mDownloadButton;
    private CardView mCard;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BigImageViewer.initialize(FrescoImageLoader.with(getApplicationContext()));
        setContentView(R.layout.activity_image);
        if(getActionBar()!=null) getActionBar().hide();
        if(getSupportActionBar() !=null) getSupportActionBar().hide();

        mImageView = findViewById(R.id.image_view_big);
        mDownloadButton = findViewById(R.id.image_download);
        mCard = findViewById(R.id.image_card);
        mProgressBar = findViewById(R.id.image_progress);
        mImageView.setProgressIndicator(new ProgressPieIndicator());
        mImageView.setImageLoaderCallback(new ImageLoadCallback(mProgressBar));
        mImageView.setImageSaveCallback(new ImageSaveCallback() {
            @Override
            public void onSuccess(String uri) {
                Toast.makeText(ImageActivity.this, "Изображение загружено!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(Throwable t) {
                Toast.makeText(ImageActivity.this, "Ошибка при загрузке!", Toast.LENGTH_SHORT).show();
            }
        });
        mImageView.showImage(Uri.parse(getIntent().getStringExtra(EXTRAS_IMAGE_LINK)));
        mDownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    requestPermission(ImageActivity.this);
                    mImageView.saveImageIntoGallery();
                } catch (SecurityException ex){
                    Toast.makeText(ImageActivity.this, "Нет доступа к хранилищу!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCard.setVisibility((mCard.getVisibility()==View.VISIBLE) ? View.GONE : View.VISIBLE);
            }
        });
    }

    private void requestPermission(Activity context){
        boolean hasPerm = (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if(!hasPerm){
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_STORAGE_REQUEST);
        }
    }
}
