package com.mikeriv.ssui_2016.a2_collage_basecode.drawing;

import android.graphics.Canvas;

public class ColumnLayout extends BaseVisualElement {
    public ColumnLayout(float x,float y,float w,float h){
        super(x,y,w,h);
    }
    //子元素在水平方向居中，在垂直方向无要求
    @Override
    public void doLayout(){
        float sum=0f;
        for(int i=0;i<super.getNumChildren();i++){
            super.getChildAt(i).setY(sum);
            sum=sum+super.getChildAt(i).getH();
            float Calculate=super.getW()/2-super.getChildAt(i).getW();
            super.getChildAt(i).setX(Calculate);
            super.getChildAt(i).doLayout();
        }
    }
    @Override
    public void draw(Canvas onCanvas){
        doLayout();
        super.draw(onCanvas);
    }
}
