package com.dong.circleimageview.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2018/3/21.
 */

public class CircleImageView extends View {

    private int resourceID;
    private String path;
    private Bitmap bitmap;

    private int targetWidth;
    private int targetHeight;

    private int radius;

    private boolean isEdge;
    private int edgeColor;
    private int edgeWidth;

    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private CircleImageView setTarget(int targetWidth, int targetHeight) {
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
        return this;
    }

    public CircleImageView setResourceID(int resourceID) {
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

    public CircleImageView setPath(String path) {
        this.path = path;

//        //对图片压缩防止出现OOM
//        BitmapFactory.Options ops = new BitmapFactory.Options();
//        ops.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(getResources(), resourceID, ops);
//        //Bitmap的尺寸
//        int outWidth = ops.outWidth;
//        int outHeight = ops.outHeight;
//
//        double widthScale = 1;
//        double heightScale = 1;
//
//        if (targetWidth > 0 && targetHeight > 0) {
//            widthScale = outWidth / targetWidth;
//            heightScale = outHeight / targetHeight;
//        }
//
//        if(widthScale >= heightScale && heightScale>1)
//            targetWidth = (int) (targetHeight*outWidth/outHeight);
//        else
//            targetHeight = (int) (targetWidth*outHeight/outWidth);
//
//        if (targetWidth > 0 && targetWidth > 0) {
//            Picasso.get().load(path).resize(targetWidth, targetHeight).into(new Target() {
//                @Override
//                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                    CircleImageView.this.bitmap = bitmap;
//                    CircleImageView.this.invalidate();
//                }
//
//                @Override
//                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//
//                }
//
//                @Override
//                public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//                }
//            });
//        } else {
            Picasso.get().load(path).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    CircleImageView.this.bitmap = bitmap;
                    CircleImageView.this.invalidate();
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
//        }
        return this;
    }

    public CircleImageView setEdge(boolean edge) {
        isEdge = edge;
        return  this;
    }

    public CircleImageView setEdgeColor(int edgeColor) {
        this.edgeColor = edgeColor;
        return this;
    }

    public CircleImageView setEdgeWidth(int edgeWidth) {
        this.edgeWidth = edgeWidth;
        return this;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int viewWidth = getWidth();
        int viewHeight = getHeight();

        radius = viewWidth > viewHeight ? viewHeight : viewWidth;

        Bitmap bmp = createCircleImage(radius, radius);
        canvas.drawBitmap(bmp, 0, 0, null);
    }

    /*
     * 生成圆角图像
     */
    private Bitmap createCircleImage(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        Canvas canvas = new Canvas(bitmap);
        //上传的图片，等比例缩放并绘制
        if (this.bitmap != null) {
            Matrix matrix = new Matrix();
            float scaleWidth = 1;
            float scaleHeight = 1;
            float scale = 1;
            scaleWidth = (float) bitmap.getWidth() / (float) this.bitmap.getWidth();
            scaleHeight = (float) bitmap.getHeight() / (float) this.bitmap.getHeight();
            scale = scaleWidth > scaleHeight ? scaleWidth : scaleHeight;
            matrix.setScale(scale, scale, 0, 0);
            matrix.postTranslate((bitmap.getWidth() - this.bitmap.getWidth() * scale) / 2, (bitmap.getHeight() - this.bitmap.getHeight() * scale) / 2);
            canvas.drawBitmap(this.bitmap, matrix, paint);
        }
        //显示类型
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        //绘制圆
        canvas.drawBitmap(createRoundCircle(width, height), 0, 0, paint);

        if(isEdge)
            drawEdge(canvas, width, height);

        return bitmap;
    }

    /*
     * 绘制圆
     */
    private Bitmap createRoundCircle(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.RED);
        Canvas canvas = new Canvas(bitmap);
        if (width > height) {
            canvas.drawCircle(height / 2, height / 2, height / 2, paint);

        } else {
            canvas.drawCircle(width / 2, width / 2, width / 2, paint);
        }

        return bitmap;
    }

    /*
     * 绘制边缘
     */
    private void drawEdge(Canvas canvas, int width, int height) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(edgeColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(edgeWidth);
        if (width > height) {
            canvas.drawCircle(height / 2, height / 2, height / 2- edgeWidth/2, paint);

        } else {
            canvas.drawCircle(width / 2, width / 2, width / 2 - edgeWidth/2, paint);
        }    }

}
