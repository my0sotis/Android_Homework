package com.mikeriv.ssui_2016.a2_collage_basecode.drawing;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class SolidBackDrop extends BaseVisualElement {
    private int color;

    public SolidBackDrop(float x,float y,float w,float h,int color){
        super(x,y,w,h);
        this.color=color;
    };

    public void setColor(int color){
        this.color=color;
    }

    @Override
    public void draw(Canvas onCanvas){
        doLayout();
        Paint mpaint=new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setColor(color);
        onCanvas.drawRect(super.getX(),super.getY(),super.getX()+super.getW(),super.getY()+super.getH(),mpaint);
        super.draw(onCanvas);
    }
}
