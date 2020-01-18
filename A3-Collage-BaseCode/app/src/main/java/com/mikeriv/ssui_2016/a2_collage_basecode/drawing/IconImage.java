package com.mikeriv.ssui_2016.a2_collage_basecode.drawing;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class IconImage extends BaseVisualElement {
    private Bitmap bitmap;
    public IconImage(float x,float y,Bitmap image){
        super(x,y,image.getWidth(),image.getHeight());
        this.bitmap=image;
    }
    @Override
    public void draw(Canvas onCanvas){
        doLayout();
        Paint mPaint=new Paint();
        mPaint.setAntiAlias(true);
        onCanvas.drawBitmap(bitmap,super.getX(),super.getY(),mPaint);
        super.draw(onCanvas);
    }
}
