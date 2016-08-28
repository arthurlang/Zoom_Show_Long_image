package com.example.langjian.superlargeimageapp.ui.view;

/**
 * Description
 * Created by langjian on 2016/7/20.
 * Version
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.langjian.superlargeimageapp.R;

/**
 * Created by shangbin on 2016/6/16.
 * Email: sahadev@foxmail.com
 *
 * @version2.0 modified by langjian@qiyi.com 2016/08/21
 */
public class CylinderImageView extends View {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    //用于裁剪的原始图片资源
    private Bitmap mSourceBitmap = null;
    MotionEvent event_down = null;
    MotionEvent event_up = null;
    MotionEvent base_cor_move_step = null;

    // 图片的高宽
    private int mBitmapHeight, mBitmapWidth;

    // 移动单位，每次移动多少个单位
    private
    int mMoveUnit = 1;
    // 图片整体移动的偏移量
    private int xOffset = 0;
    // 图片整体移动的偏移量
    private int yOffset = 0;
    // 用于持有两张拼接图片的引用，并释放原先的图片资源
    private Bitmap mBitmapB;
    /**
     * 循环滚动标志位
     */
    private boolean mRunningFlag;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                invalidate();
            }
        }
    };
    private float start_x;
    private float start_y;
    private int mOritation;
    private Bitmap mBitmapA;

    public CylinderImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CylinderImageView(Context context) {
        this(context, null);
    }

    public CylinderImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        //读取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CylinderImageView,defStyle,0);
        int index = typedArray.getInt(R.styleable.CylinderImageView_oritation,-1);

        if(index >= 0){
            setOritation(index);
        }

        initVideoView();
    }

    private void setOritation(int index) {
        mOritation = index;
    }

    private void initVideoView() {
        // 获取需要循环展示的图片的高宽
        if(mOritation == HORIZONTAL){
            mSourceBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.widthphoto);
        }else if(mOritation == VERTICAL){
            mSourceBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.longphoto);
        }

        mBitmapHeight = mSourceBitmap.getHeight();
        mBitmapWidth = mSourceBitmap.getWidth();
        //mRunningFlag = true;

        setFocusableInTouchMode(true);
        requestFocus();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(mOritation == HORIZONTAL){
            // 简单设置一下控件的宽高,这里的高度以图片的高度为准
            setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(mBitmapHeight, MeasureSpec.getMode(heightMeasureSpec)));
        }else{
            setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(1500, MeasureSpec.getMode(widthMeasureSpec)));
        }
  }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(mOritation == HORIZONTAL){
                start_x = event.getX();
            }else{
                start_y = event.getY();
            }

        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            if(mOritation == HORIZONTAL){

                mMoveUnit = (int) (event.getX() - start_x);
                start_x = event.getX();
                // 累计图片的x偏移量
                xOffset -= mMoveUnit;
            }else{

                mMoveUnit = (int) (event.getY() - start_y);
                start_y = event.getY();
                // 累计图片的x偏移量
                yOffset -= mMoveUnit;
            }


        }


        invalidate();

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        recycleTmpBitmap();

        final int left = getLeft();
        final int top = getTop();
        final int right = getRight();
        final int bottom = getBottom();

        // 计算图片的高度
        int tempHeight = bottom - top;
        // 第一张图的宽带
        int tempWidth = right - left;

        Paint bitmapPaint = new Paint();
        if(mOritation == HORIZONTAL){
            // 如果一张图片轮播完，则从头开始
            if (xOffset >= mBitmapWidth) {
                xOffset = 0;
            }
            // 重新计算截取的图的宽度
            tempWidth = xOffset + tempWidth >= mBitmapWidth ? mBitmapWidth - xOffset : tempWidth;
            if(xOffset < 0){
                xOffset = -xOffset;
            }
            mBitmapA = Bitmap
                    .createBitmap(mSourceBitmap, xOffset, 0, tempWidth, tempHeight > mBitmapHeight ? mBitmapHeight : tempHeight);
            // 绘制这张图
            canvas.drawBitmap(mBitmapA, getMatrix(), bitmapPaint);
            // 如果最后的图片已经不足以填充整个屏幕，则截取图片的头部以连接上尾部，形成一个闭环
            if (tempWidth < right - left) {
                Rect dst = new Rect(tempWidth, 0, right, mBitmapHeight);
                mBitmapB = Bitmap.createBitmap(mSourceBitmap, 0, 0, right - left - tempWidth,
                        tempHeight > mBitmapHeight ? mBitmapHeight : tempHeight);

                // 将另一张图片绘制在这张图片的后半部分
                canvas.drawBitmap(mBitmapB, null, dst, bitmapPaint);
            }

        }else{
            // 如果一张图片轮播完，则从头开始
            if(yOffset >= mBitmapHeight){
                yOffset = 0;
            }
            if(yOffset < 0){
                yOffset = -yOffset;
            }

            tempHeight = (yOffset + tempHeight) >= mBitmapHeight ? mBitmapHeight-yOffset:tempHeight;
            mBitmapA = Bitmap.createBitmap(mSourceBitmap,0,yOffset,tempWidth>mBitmapWidth ? mBitmapWidth : tempWidth,tempHeight);
            // 绘制这张图
            canvas.drawBitmap(mBitmapA, getMatrix(), bitmapPaint);

            if(tempHeight < bottom - top){
                Rect dst = new Rect(tempWidth, 0, right, mBitmapHeight);
                mBitmapB = Bitmap.createBitmap(mSourceBitmap, 0, 0, tempWidth>mBitmapWidth ? mBitmapWidth : tempWidth,
                        bottom - top - tempHeight);

                // 将另一张图片绘制在这张图片的后半部分
                canvas.drawBitmap(mBitmapB, null, dst, bitmapPaint);
            }
        }
        //由handler的延迟发送产生绘制间隔
        if (mRunningFlag) {
            mHandler.sendEmptyMessageDelayed(0, 1);
        }
    }

    /**
     * 回收临时图像
     */
    private void recycleTmpBitmap() {
        if (mBitmapA != null) {
            mBitmapA.recycle();
            mBitmapA = null;
        }

        if (mBitmapB != null) {
            mBitmapB.recycle();
            mBitmapB = null;
        }
    }

    /**
     * 恢复
     */
    public void resume() {
        mRunningFlag = true;
        invalidate();
    }

    /**
     * 暂停
     */
    public void pause() {
        mRunningFlag = false;
    }

    /**
     * 回收清理工作
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        pause();
        recycleTmpBitmap();
        mSourceBitmap.recycle();
    }
}