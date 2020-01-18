package com.mikeriv.ssui_2016.a2_collage_basecode.drawing;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import org.w3c.dom.Text;

import java.lang.reflect.Type;

public class TextVisualElement extends BaseVisualElement {
    private String text;
    private Typeface typeface;
    private float textSize;
    public TextVisualElement(float x, float y, String Text, Typeface face,float textSize){
        super(x,y,(float)textSize*Text.length(),textSize);
        this.text=Text;
        this.typeface=face;
        this.textSize=textSize;
    };
    @Override
    public void draw(Canvas onCanvas){
        doLayout();
        Paint mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(textSize);
        mPaint.setTypeface(typeface);
        onCanvas.drawText(text,super.getX(),super.getY()+20 ,mPaint);
        super.draw(onCanvas);
    }
}
