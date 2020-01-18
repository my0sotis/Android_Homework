package com.mikeriv.ssui_2016.a2_collage_basecode.drawing;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class OverClip extends BaseVisualElement {

    public OverClip(float x,float y,float w,float h){
        super(x,y,w,h);
    }
    @Override
    public void draw(Canvas onCanvas){
        doLayout();
        onCanvas.clipRect( getX(), getY(), getX() + getW(),getY() + getH());
        onCanvas.translate(getX(), getY());

        SolidBackDrop solidBackDrop=(SolidBackDrop) super.getChildAt(0);
        solidBackDrop.setColor(Color.LTGRAY);
        solidBackDrop.draw(onCanvas);
        onCanvas.save();
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        RectF oval = new RectF(0, 0, super.getW(),super.getH());
        onCanvas.drawOval(oval,paint);
        onCanvas.restore();

    }
}
