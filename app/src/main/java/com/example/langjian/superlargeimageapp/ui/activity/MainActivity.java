package com.example.langjian.superlargeimageapp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.langjian.superlargeimageapp.R;

/**
 * @author langjian  2016/7/26
 * Description 1、可缩放的大图。2、可横向或者纵向互动的大图。
 * Version @version V1.0
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showZoomWithfresco(View view){
        Intent intent = new Intent(this,ZoomPhotoActivity.class);
        startActivity(intent);
    }

    //滑动循环展示长图和宽图
    public void showSuperLongImage(View view){
        startActivity(new Intent(this, LongPhotoActivity.class));
    }

}
