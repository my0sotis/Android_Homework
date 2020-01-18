package com.mikeriv.ssui_2016.a2_collage_basecode.drawing;

import android.graphics.Canvas;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.RectF;

public class NinePartImage extends BaseVisualElement{
    private NinePatch patches;
    public NinePartImage(float x, float y, float w, float h, NinePatch patches){
        super(x,y,w,h);
        this.patches=patches;
    };
    @Override
    public void draw(Canvas onCanvas){
        doLayout();
        patches.draw(onCanvas,new RectF(super.getX(),super.getY(),super.getX()+super.getW(),super.getY()+super.getH()));
        super.draw(onCanvas);
    }
}
