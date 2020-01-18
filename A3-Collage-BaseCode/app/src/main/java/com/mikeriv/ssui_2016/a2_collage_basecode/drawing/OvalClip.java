package com.mikeriv.ssui_2016.a2_collage_basecode.drawing;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class OvalClip extends BaseVisualElement {
    public OvalClip(float x, float y, float w, float h) {
        setX(x); setY(y); setW(w); setH(h);
    }

    public void draw(Canvas onCanvas) {
        doLayout();
        onCanvas.clipRect( getX(), getY(), getX() + getW(),getY() + getH());
        onCanvas.translate(getX(), getY());

        SolidBackDrop solidBackDrop = (SolidBackDrop)super.getChildAt(0);
        solidBackDrop.setTargetColor(Color.LTGRAY);
        solidBackDrop.draw(onCanvas);
        onCanvas.save();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        RectF oval = new RectF(0, 0, super.getW(), super.getH());
        onCanvas.drawOval(oval, paint);
        onCanvas.restore();
    }
}
