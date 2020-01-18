package com.mikeriv.ssui_2016.a2_collage_basecode.drawing;

import android.graphics.Canvas;

public class PileLaout extends BaseVisualElement {
    public PileLaout(float x,float y,float w,float h){
        super(x,y,w,h);
    }
    //将所有的子类放到左上角，所以将所有的初始坐标置0；
    @Override
    public void doLayout(){
        for(int i=0;i<super.getNumChildren();i++){
            super.getChildAt(i).setX(0f);
            super.getChildAt(i).setY(0f);
            super.getChildAt(i).doLayout();
        }
    }
    @Override
    public void draw(Canvas onCanvas){
        doLayout();
        super.draw(onCanvas);
    }
}
