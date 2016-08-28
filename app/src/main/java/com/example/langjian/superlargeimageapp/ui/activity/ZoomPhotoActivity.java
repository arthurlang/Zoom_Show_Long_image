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
    private ZoomableDraweeView fresco_photo_view;
    private PhotoViewAttacher mPhotoViewAttacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zoom_layout);
        fresco_photo_view = (ZoomableDraweeView) findViewById(R.id.photo_view);
        fresco_photo_view.setImageResource(R.drawable.longphoto);
        fresco_photo_view.setVisibility(View.VISIBLE);
        initPhotoAttacher();
    }

    private void initPhotoAttacher() {
        mPhotoViewAttacher = new PhotoViewAttacher(fresco_photo_view);
        mPhotoViewAttacher.setMaximumScale(1.5f);
        mPhotoViewAttacher.setSupportTwiceZoom(false);
        mPhotoViewAttacher.setMinGestureScale(0.5f);
        mPhotoViewAttacher.setSupportTwiceZoom(false);
        mPhotoViewAttacher.setMinGestureScale(0.5f);
        mPhotoViewAttacher.setMaximumScale(1.5f);
        mPhotoViewAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                Toast.makeText(ZoomPhotoActivity.this,"Tap View",Toast.LENGTH_LONG).show();
            }
        });
        fresco_photo_view.post(new Runnable() {
            @Override
            public void run() {
                int width = fresco_photo_view.getWidth();
                int height = fresco_photo_view.getHeight();
                if(height > 0 && width>0 ){
                    if (width / height > 10) {
                        mPhotoViewAttacher.setMaximumScale(25f);
                    } else if (height / width > 10) {
                        mPhotoViewAttacher.setMaximumScale(15f);
                    }
                }
                mPhotoViewAttacher.update();//do not forget update
            }
        });
    }
}
