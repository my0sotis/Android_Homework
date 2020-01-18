package com.mikeriv.ssui_2016.a2_collage_basecode.drawing;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class SimpleFrame extends BaseVisualElement {
    public SimpleFrame(float x,float y,float w,float h){
        super(x,y,w,h);
    };
    @Override
    public void draw(Canvas onCanvas){
        doLayout();
        Paint mpaint=new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setColor(Color.BLACK);
        mpaint.setStyle(Paint.Style.STROKE);
        onCanvas.drawRect(super.getX(),super.getY(),super.getX()+super.getW(),super.getY()+super.getH(),mpaint);
        super.draw(onCanvas);
    }
}
