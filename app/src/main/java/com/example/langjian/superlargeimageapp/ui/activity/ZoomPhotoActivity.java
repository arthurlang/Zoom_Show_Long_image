package com.example.langjian.superlargeimageapp.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.langjian.superlargeimageapp.R;
import com.example.langjian.superlargeimageapp.ui.view.photoview.PhotoViewAttacher;

import samples.zoomable.ZoomableDraweeView;

/**
 * Description
 * Created by langjian on 2016/4/21.
 * Version
 */
public class ZoomPhotoActivity extends Activity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zoom_layout);
        PhotoViewAttacher photoViewAttacher = null;
        if("fresco".equals(getIntent().getStringExtra("type"))){
            final ZoomableDraweeView photo_view = (ZoomableDraweeView) findViewById(R.id.photo_view);
            photo_view.setImageResource(R.drawable.longphoto);
            photoViewAttacher = new PhotoViewAttacher(photo_view);
        }else{
            imageView = (ImageView) findViewById(R.id.normal_image);
            photoViewAttacher = new PhotoViewAttacher(imageView);
        }

        photoViewAttacher.setSupportTwiceZoom(false);
        photoViewAttacher.setMinGestureScale(0.5f);
        photoViewAttacher.setMaximumScale(1.5f);
        photoViewAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                Toast.makeText(ZoomPhotoActivity.this,"Tap View",Toast.LENGTH_LONG).show();
            }
        });
    }
}
