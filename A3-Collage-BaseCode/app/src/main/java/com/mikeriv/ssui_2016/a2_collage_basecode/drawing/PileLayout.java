package com.mikeriv.ssui_2016.a2_collage_basecode.drawing;

import android.graphics.Canvas;

public class PileLayout extends BaseVisualElement {
    public PileLayout(float x, float y, float w, float h) {
        setX(x); setY(y); setW(w); setH(h);
    }

    @Override
    public void doLayout() {
        for (int i = 0; i < getNumChildren(); i++) {
            VisualElement child = getChildAt(i);
            child.setPosition(0,0);
            child.doLayout();
        }
    }

    @Override
    public void draw(Canvas onCanvas) {
        doLayout();
        super.draw(onCanvas);
    }
}
