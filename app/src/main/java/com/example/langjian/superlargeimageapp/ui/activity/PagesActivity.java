package com.example.langjian.superlargeimageapp.ui.activity;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.langjian.superlargeimageapp.R;
import com.example.langjian.superlargeimageapp.ui.adapter.SuperHighImageAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Description
 * Created by langjian on 2016/4/21.
 * Version
 */
public class PagesActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.large_on_pages);

        final ListView longPhotoView = (ListView) findViewById(R.id.long_image_view);
        longPhotoView.setVisibility(View.VISIBLE);
        longPhotoView.setSelector(new BitmapDrawable());
        longPhotoView.setDividerHeight(0);

//ioException image format not supported BitmapRegionDecoder newInstance(InputStream...)
/*        try {
            inputStream = getResources().getAssets().open("png_photo.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SuperHighImageAdapter adapter = new SuperHighImageAdapter(this,inputStream);*/

        //copy the longphoto.jpg to your sdcard root directory
        String path = Environment.getExternalStorageDirectory()+"/longphoto.jpg";
        SuperHighImageAdapter adapter = new SuperHighImageAdapter(this,path);
        longPhotoView.setAdapter(adapter);

   }
}
