package com.mikeriv.ssui_2016.a2_collage_basecode.drawing;

import android.graphics.Canvas;

public class CircleLayout extends BaseVisualElement {
    private float CenterX;
    private float CenterY;
    private float CenterRadius;
    public CircleLayout(float x,float y,float w,float h,
                        float layoutCenterX,float layoutCenterY,
                        float layourtRadius){
        super(x,y,w,h);
        this.CenterX=layoutCenterX;
        this.CenterY=layoutCenterY;
        this.CenterRadius=layourtRadius;
    }
    //
    @Override
    public void doLayout(){
        float sum=0f;
        for(int i=0;i<super.getNumChildren();i++){
            super.getChildAt(i).setX((super.getX()+super.getW())/2);
            sum=super.getChildAt(i).getH()+sum;
            super.getChildAt(i).setH(sum);
            super.getChildAt(i).doLayout();
        }
    }
    @Override
    public void draw(Canvas onCanvas){
        doLayout();
        super.draw(onCanvas);
    }
}
