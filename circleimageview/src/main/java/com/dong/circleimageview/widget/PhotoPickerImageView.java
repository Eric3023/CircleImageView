package com.dong.circleimageview.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.dong.circleimageview.R;
import com.dong.circleimageview.util.BitmapUtil;

import java.io.File;

/**
 * Created by Administrator on 2018/3/25.
 */

public class PhotoPickerImageView extends View {

    private Context context;

    private String filePath;
    private Bitmap bitmap;

    private int maskID;
    private Bitmap maskBitmap;

    private int radius = (int) getResources().getDimension(R.dimen.x100);

    public static final int CICLE_IMG = 0;
    public static final int SQUARE_IMG = 1;
    private int shape;

    private float maxScale = 4;
    private float minScale = 0.5f;

    private float touchScale = 1;
    private float touchCenterX = 0;
    private float touchCenterY = 0;
    private float touchTranslateX = 0;
    private float touchTranslateY = 0;

    private Matrix matrix;

    private float downX0;
    private float downY0;
    private float oldDis;

    private boolean isUp;

    private float[] values = new float[9];
    private float scale;

    private float translateX = -1;
    private float translateY = -1;
    private int viewWidth;
    private int viewHeight;

    public PhotoPickerImageView(Context context) {
        super(context);
        init(context);
    }

    public PhotoPickerImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PhotoPickerImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        this.context = context;

        maskBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.photo_mask);
        setClickable(true);
    }

    public String getFilePath() {
        return filePath;
    }

    public PhotoPickerImageView setFilePath(String filePath) {
        this.filePath = filePath;

        bitmap = BitmapFactory.decodeFile(filePath);

        return this;
    }

    public PhotoPickerImageView setMaskID(int maskID) {
        this.maskID = maskID;
        maskBitmap = BitmapFactory.decodeResource(getResources(), maskID);
        return this;
    }

    public PhotoPickerImageView setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    public PhotoPickerImageView setShape(int shape) {
        this.shape = shape;
        return this;
    }

    public PhotoPickerImageView setMaxScale(float maxScale) {
        this.maxScale = maxScale;
        return this;
    }

    public PhotoPickerImageView setMinScale(float minScale) {
        this.minScale = minScale;
        return this;
    }

    public PhotoPickerImageView reset() {
        matrix = null;
        invalidate();
        return this;
    }

    public PhotoPickerImageView saveBitmap(){
        Bitmap bitmap = Bitmap.createBitmap(radius * 2, radius * 2, Bitmap.Config.ARGB_8888);
        Canvas canvasMask = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        //上传的图片，等比例缩放并绘制
        if (this.bitmap != null && matrix!= null) {
            matrix.postTranslate(-(viewWidth/2 - radius), -(viewHeight/2 - radius));
            canvasMask.drawBitmap(this.bitmap, matrix, paint);
        }

        String savePath = BitmapUtil.saveBitmap(context, bitmap);
        Log.i("Dong","path:"+savePath);

        return this;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        viewWidth = getWidth();
        viewHeight = getHeight();

        //绘制选取的图片
        drawPicture(canvas, viewWidth, viewHeight);

        //绘制遮罩图片
        drawMask(canvas, viewWidth, viewHeight);
    }

    private void drawPicture(Canvas canvas, int viewWidth, int viewHeight) {
        //上传的图片，等比例缩放并绘制
        if (this.bitmap != null) {
            if (matrix == null) {
                matrix = new Matrix();
                float scaleWidth = 1;
                float scaleHeight = 1;
                scale = 1;
                scaleWidth = (float) radius * 2 / (float) this.bitmap.getWidth();
                scaleHeight = (float) radius * 2 / (float) this.bitmap.getHeight();
                scale = scaleWidth > scaleHeight ? scaleWidth : scaleHeight;

                matrix.setScale(scale, scale, 0, 0);
                matrix.postTranslate((viewWidth - this.bitmap.getWidth() * scale) / 2, (viewHeight - this.bitmap.getHeight() * scale) / 2);
            } else{

                if(touchScale!=0) {
                    matrix.postScale(touchScale, touchScale, touchCenterX, touchCenterY);
                    matrix.getValues(values);
                    if (values[0] > maxScale * scale && values[0] != 0)
                        matrix.postScale(maxScale * scale / values[0], maxScale * scale / values[0], touchCenterX, touchCenterY);
                    else if (values[0] < minScale * scale && values[0] != 0)
                        matrix.postScale(scale * minScale / values[0], scale * minScale / values[0], touchCenterX, touchCenterY);
                }

                if(touchTranslateX != 0 || touchTranslateY != 0){
                    matrix.postTranslate(touchTranslateX, touchTranslateY);
                }

                if (isUp) {
                    matrix.getValues(values);
                    if (values[0] < scale) {
                        matrix.postScale(scale / values[0], scale / values[0], touchCenterX, touchCenterY);
                    }
                    matrix.getValues(values);
                    if (values[2] > viewWidth / 2 - radius) {
                        matrix.postTranslate((viewWidth / 2 - radius) - values[2], 0);
                    }
                    matrix.getValues(values);
                    if (values[5] > viewHeight / 2 - radius) {
                        matrix.postTranslate(0, (viewHeight / 2 - radius) - values[5]);
                    }
                    matrix.getValues(values);
                    if (viewWidth - (bitmap.getWidth() * values[0] + values[2]) > viewWidth / 2 - radius) {
                        matrix.postTranslate(viewWidth - (bitmap.getWidth() * values[0] + values[2]) - (viewWidth / 2 - radius), 0);
                    }
                    matrix.getValues(values);
                    if (viewHeight - (bitmap.getHeight() * values[0] + values[5]) > viewHeight / 2 - radius) {
                        matrix.postTranslate(0, viewHeight - (bitmap.getHeight() * values[0] + values[5]) - (viewHeight / 2 - radius));
                    }
                }
            }
            canvas.drawBitmap(this.bitmap, matrix, null);
        }
    }


    private void drawMask(Canvas canvas, int viewWidth, int viewHeight) {
        Bitmap bitmapMask = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
        Canvas canvasMask = new Canvas(bitmapMask);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        //上传的图片，等比例缩放并绘制
        if (this.maskBitmap != null) {
            Matrix matrix = new Matrix();
            float scaleWidth = 1;
            float scaleHeight = 1;
            float scale = 1;
            scaleWidth = (float) viewWidth / (float) this.maskBitmap.getWidth();
            scaleHeight = (float) viewHeight / (float) this.maskBitmap.getHeight();
            matrix.setScale(scaleWidth, scaleHeight, 0, 0);
            canvasMask.drawBitmap(this.maskBitmap, matrix, paint);
        }

        //显示类型
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        //绘制遮罩形状
        canvasMask.drawBitmap(createRoundCircle(), viewWidth / 2 - radius, viewHeight / 2 - radius, paint);

        canvas.drawBitmap(bitmapMask, 0, 0, null);
    }

    private Bitmap createRoundCircle() {
        Bitmap bitmap = Bitmap.createBitmap(radius * 2, radius * 2, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.WHITE);
        Canvas canvas = new Canvas(bitmap);
        switch (shape) {
            case CICLE_IMG:
                canvas.drawCircle(radius, radius, radius, paint);
                break;
            case SQUARE_IMG:
                canvas.drawRect(0, 0, radius * 2, radius * 2, paint);
                break;
            default:
                canvas.drawCircle(radius, radius, radius, paint);
        }
        return bitmap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();

        switch (action) {

            case MotionEvent.ACTION_DOWN:
                isUp = false;
                downX0 = event.getX();
                downY0 = event.getY();
                break;
            case MotionEvent.ACTION_POINTER_2_DOWN:
                isUp = false;
                float downX1 = event.getX(event.getActionIndex());
                float downY1 = event.getY(event.getActionIndex());
                oldDis = (float) Math.sqrt(Math.pow(downX1 - downX0, 2) + Math.pow(downY1 - downY0, 2));
                break;
            case MotionEvent.ACTION_MOVE:
                isUp = false;
                if (event.getPointerCount() == 2) {

                    touchTranslateX = 0;
                    touchTranslateY = 0;

                    translateX = -1;
                    translateY = -1;

                    float moveX0 = event.getX(0);
                    float moveY0 = event.getY(0);

                    float moveX1 = event.getX(1);
                    float moveY1 = event.getY(1);

                    float moveDis = (float) Math.sqrt(Math.pow(moveX1 - moveX0, 2) + Math.pow(moveY1 - moveY0, 2));

                    touchCenterX = (moveX1 + moveX0) / 2;
                    touchCenterY = (moveY1 + moveY0) / 2;

                    if (oldDis != 0)
                        touchScale = (float) Math.sqrt(moveDis / oldDis);

                    oldDis = moveDis;
                    invalidate();
                } else if (event.getPointerCount() == 1) {

                    touchScale = 1;

                    float eventX = event.getX();
                    float eventY = event.getY();

                    if(translateX >0 && translateY >0){
                        touchTranslateX = eventX - translateX;
                        touchTranslateY = eventY - translateY;
                    }

                    translateX = eventX;
                    translateY = eventY;

                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:

                translateX = -1;
                translateY = -1;

                touchTranslateX = 0;
                touchTranslateY = 0;

                touchScale = 1;

                isUp = true;
                invalidate();
                break;
        }


        return super.onTouchEvent(event);
    }
}
