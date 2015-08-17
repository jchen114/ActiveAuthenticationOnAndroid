package com.example.hooligan.cameradatadumper;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Hooligan on 8/13/2015.
 */
public class ImageViewBoundingBox extends ImageView {

    private ImageViewer mImageViewer;

    public ImageViewBoundingBox(Context context, ImageViewer iv) {
        super(context);
        mImageViewer = iv;
        setImageBitmap(BitmapFactory.decodeFile(mImageViewer.mFile.getAbsolutePath()));
    }


    public ImageViewBoundingBox(Context context) {
        super(context);
    }

    ImageViewBoundingBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    ImageViewBoundingBox(Context context, AttributeSet attrs, int defStyle) {
        super(context,attrs,defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        float leftx = mImageViewer.X;
        float topy = mImageViewer.Y;
        float rightx = mImageViewer.X + mImageViewer.width;
        float bottomy = mImageViewer.Y + mImageViewer.height;
        canvas.drawRect(leftx, topy, rightx, bottomy, paint);
    }
}
