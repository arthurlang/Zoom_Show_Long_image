package com.example.langjian.superlargeimageapp.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.langjian.superlargeimageapp.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Description
 * Created by langjian on 2016/4/21.
 * Version
 */
public class SuperHighImageAdapter extends BaseAdapter{
    private final static String TAG = "SuperHighImageAdapter";
    InputStream inputStream = null;
    private Context mContext;
    private LayoutInflater mInflater;
    private String mSrcPath;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mCount;
    private int mPhotoWidth;
    private int mPhotoHeight;
    private Holder holder;

    public SuperHighImageAdapter(Context ctx, String path) {
        mContext = ctx;
        mInflater = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        mSrcPath = path;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) ctx).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;
        mScreenHeight = displayMetrics.heightPixels;
    }

    public SuperHighImageAdapter(Context ctx,InputStream inputStream) {
        mContext = ctx;
        mInflater = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        this.inputStream = inputStream;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) ctx).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;
        mScreenHeight = displayMetrics.heightPixels;
    }

    @Override
    public int getCount() {
        return getSubImageCount();
    }

    private int getSubImageCount() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        if(inputStream !=null){
              Rect outPaddingRect = new Rect();
              BitmapFactory.decodeStream(inputStream, outPaddingRect, options);//此时返回bm为空
        }else{
            if(mSrcPath != null && !"".equals(mSrcPath)){
                BitmapFactory.decodeFile(mSrcPath, options); //此时返回bm为空
            }
          }
        if (options.outHeight * options.outWidth <= 0 || options.outHeight * options.outWidth <= mScreenWidth * mScreenHeight / 4) { //Probably invalid photo size
            mCount = 1;
        } else {
            int imgScreenHeight = options.outHeight * mScreenWidth / options.outWidth;
            mCount = imgScreenHeight / mScreenHeight;
            if (imgScreenHeight % mScreenHeight != 0) {
                mCount++;
            }
        }

        mPhotoHeight = options.outHeight>0?options.outHeight:mPhotoHeight;
        mPhotoWidth = options.outWidth>0?options.outWidth:mPhotoWidth;
        return mCount;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(mPhotoHeight < 0 || mPhotoWidth < 0 ||inputStream == null){return new View(mContext);}
        ImageView partialImageView;
        BitmapRegionDecoder decoder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.part_image_item_layout, parent, false);
            partialImageView = (ImageView) convertView.findViewById(R.id.part_image);
            try {
                if(inputStream != null){
                    decoder = BitmapRegionDecoder.newInstance(inputStream, false);
                }else{
                    if(mSrcPath != null && !"".equals(mSrcPath)){
                        decoder = BitmapRegionDecoder.newInstance(mSrcPath, false);
                    }
                }
            } catch (IOException e) {
                decoder = null;
                Toast.makeText(mContext,e.toString(),Toast.LENGTH_SHORT);
                e.printStackTrace();
            }
            holder = new Holder();
            holder.imgView = partialImageView;
            holder.decoder = decoder;
            convertView.setTag(holder);
        }

        Holder holder = (Holder) convertView.getTag();
        if (holder.decoder != null) {
            int low_y, high_y;

            int cellHeight = Math.round((float) mScreenHeight * mPhotoWidth / mScreenWidth);
            low_y = cellHeight * position;
            // last one
            if (position == mCount - 1) {
                high_y = mPhotoHeight - 1;
            } else {
                high_y = cellHeight * (position + 1);
            }

            Log.i(TAG, "low_y = " + low_y + " high_y = " + high_y);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bMap = holder.decoder.decodeRegion(new Rect(0, low_y, mPhotoWidth, high_y), options);
            Log.i(TAG, "bMap width = " + bMap.getWidth() + " height = " + bMap.getHeight());
            ViewGroup.LayoutParams para;
            para = holder.imgView.getLayoutParams();

            para.height = Math.round((float) (high_y - low_y) * mScreenWidth / mPhotoWidth);
            para.width = mScreenWidth;
            holder.imgView.setLayoutParams(para);

            Log.d(TAG, "layout height = " + para.height + " width = " + para.width);

            holder.imgView.setImageBitmap(bMap);
            holder.imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {}
            });
        }
        return convertView;
    }

    class Holder {
        public ImageView imgView;
        public BitmapRegionDecoder decoder;
    }
}
