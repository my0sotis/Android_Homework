package com.mikeriv.ssui_2016.a2_collage_basecode.drawing;

import android.graphics.Canvas;

public class RowLayout extends BaseVisualElement {
    public RowLayout(float x,float y,float w,float h){
        super(x,y,w,h);
    }
    //要求的是垂直居中
    @Override
    public void doLayout(){
        float sum=0f;
        for(int i=0;i<super.getNumChildren();i++){
            super.getChildAt(i).setX(sum);
            sum=sum+super.getChildAt(i).getW();
            float Calculate=super.getH()/2-super.getChildAt(i).getH();
            super.getChildAt(i).setY(Calculate);
            super.getChildAt(i).doLayout();
        }
    }
    @Override
    public void draw(Canvas onCanvas){
        doLayout();
        super.draw(onCanvas);
    }

}
