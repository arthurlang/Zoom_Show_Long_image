package com.example.langjian.superlargeimageapp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.langjian.superlargeimageapp.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showAsCenterInSide(View view){startActivity(new Intent(this,CenterInsideActivity.class));}

    public void showAsPages(View view){
        startActivity(new Intent(this,PagesActivity.class));
    }

    public void showZoom(View view){
        startActivity(new Intent(this,ZoomPhotoActivity.class));
    }

    public void showZoomWithfresco(View view){
        Intent intent = new Intent(this,ZoomPhotoActivity.class);
        intent.putExtra("type","fresco");
        startActivity(intent);
    }

}
