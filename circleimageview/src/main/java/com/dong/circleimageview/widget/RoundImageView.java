package com.dong.circleimageview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * @author donghuadong
 * @description 圆角图形
 */
public class RoundImageView extends View {

    private Bitmap bitmap;
    private int resourceID;
    private String path;

    private int targetWidth;
    private int targetHeight;

    private int radius;
    private int imageScale;

    private int backgroudColor = Color.WHITE;

    public static final int FIT_XY = 0;
    public static final int CENTER_INSIDE = 1;
    public static final int CENTER_CROP = 2;
    private Target target;

    /*
     * 构造器
     */
    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundImageView(Context context) {
        super(context);
    }

    private RoundImageView setTarget(int targetWidth, int targetHeight) {
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
        return this;
    }

    public RoundImageView setBackgroudColor(int backgroudColor) {
        this.backgroudColor = backgroudColor;
        return this;
    }

    public RoundImageView setResourceID(int resourceID) {
        this.resourceID = resourceID;
        //对图片压缩防止出现OOM
        BitmapFactory.Options ops = new BitmapFactory.Options();
        ops.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), resourceID, ops);
        //Bitmap的尺寸
        int outWidth = ops.outWidth;
        int outHeight = ops.outHeight;

        int widthScale = 1;
        int heightScale = 1;

        if (targetWidth > 0 && targetHeight > 0) {
            widthScale = outWidth / targetWidth;
            heightScale = outHeight / targetHeight;
        }

        ops.inJustDecodeBounds = false;
        if (widthScale >= heightScale && heightScale > 1) {
            ops.inSampleSize = heightScale;
            bitmap = BitmapFactory.decodeResource(getResources(), resourceID, ops);
        } else if (heightScale >= widthScale || widthScale > 1) {
            ops.inSampleSize = widthScale;
            bitmap = BitmapFactory.decodeResource(getResources(), resourceID, ops);
        } else {
            bitmap = BitmapFactory.decodeResource(getResources(), resourceID);
        }

        return this;
    }

    public RoundImageView setPath(String path) {
        this.path = path;

        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                RoundImageView.this.bitmap = bitmap;
                RoundImageView.this.invalidate();
                target = null;
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        Picasso.get().load(path).into(target);

        return this;
    }


    /*
     * 设置角度
     */
    public RoundImageView setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    /*
     * 设置填充类型
     */
    public RoundImageView setImageScale(int imageScale) {
        this.imageScale = imageScale;
        return this;
    }

    /*
     * 在画布上绘制生成的圆角图像
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap bmp = createImage(getWidth(), getHeight());
        canvas.drawBitmap(bmp, 0, 0, null);
    }

    /*
     * 生成圆角图像
     */
    private Bitmap createImage(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        Canvas canvas = new Canvas(bitmap);
        //上传的图片，等比例缩放并绘制
        if (this.bitmap != null) {
            Matrix matrix = new Matrix();
            float scaleWidth = 1;
            float scaleHeight = 1;
            switch (imageScale) {
                case FIT_XY:
                    scaleWidth = (float) bitmap.getWidth() / (float) this.bitmap.getWidth();
                    scaleHeight = (float) bitmap.getHeight() / (float) this.bitmap.getHeight();
                    matrix.setScale(scaleWidth, scaleHeight, 0, 0);
                    canvas.drawBitmap(this.bitmap, matrix, paint);
                    break;
                case CENTER_INSIDE:
                    float scaleInside = 1;
                    scaleWidth = (float) bitmap.getWidth() / (float) this.bitmap.getWidth();
                    scaleHeight = (float) bitmap.getHeight() / (float) this.bitmap.getHeight();
                    scaleInside = scaleWidth < scaleHeight ? scaleWidth : scaleHeight;
                    matrix.setScale(scaleInside, scaleInside, 0, 0);
                    matrix.postTranslate((bitmap.getWidth() - this.bitmap.getWidth() * scaleInside) / 2, (bitmap.getHeight() - this.bitmap.getHeight() * scaleInside) / 2);
                    canvas.drawBitmap(this.bitmap, matrix, paint);
                    break;
                case CENTER_CROP:
                    float scaleCrop = 1;
                    scaleWidth = (float) bitmap.getWidth() / (float) this.bitmap.getWidth();
                    scaleHeight = (float) bitmap.getHeight() / (float) this.bitmap.getHeight();
                    scaleCrop = scaleWidth < scaleHeight ? scaleHeight:scaleWidth;
                    matrix.setScale(scaleCrop, scaleCrop, 0, 0);
                    matrix.postTranslate((bitmap.getWidth() - this.bitmap.getWidth() * scaleCrop) / 2, (bitmap.getHeight() - this.bitmap.getHeight() * scaleCrop) / 2);
                    canvas.drawBitmap(this.bitmap, matrix, paint);
                    break;
            }
        }
        //显示类型
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_ATOP));
        //绘制圆角矩形
        canvas.drawBitmap(createRoundCircle(width, height), 0, 0, paint);

        return bitmap;
    }

    /*
     * 绘制圆角
     */
    private Bitmap createRoundCircle(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(backgroudColor);
        Canvas canvas = new Canvas(bitmap);
        RectF rectF = new RectF(0, 0, width, height);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        return bitmap;
    }

}