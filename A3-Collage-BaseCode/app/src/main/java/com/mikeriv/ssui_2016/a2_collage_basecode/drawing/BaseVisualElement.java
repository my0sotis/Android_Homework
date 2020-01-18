package com.mikeriv.ssui_2016.a2_collage_basecode.drawing;

import android.graphics.Canvas;
import android.graphics.PointF;

import com.mikeriv.ssui_2016.a2_collage_basecode.views.CollageView;

import java.util.ArrayList;

public class BaseVisualElement extends PrebaseVisualElement {
    //传入元素的x,y坐标以及大小
    private float x;
    private float y;
    private float width;
    private float height;
    //父级元素，没有则为null
    private BaseVisualElement parent=null;
    //子集元素
    private  ArrayList<VisualElement> children=new ArrayList<>();

    //继承父类的无参构造函数
    public BaseVisualElement() {
        this(0,0);
    }
    //继承父类有位置的构造函数
    public BaseVisualElement(float xLoc, float yLoc) {
        this(xLoc,yLoc,13.7f,17.9f);
    }
    //继承父类完整的构造函数给出位置和大小。
    public BaseVisualElement(float xLoc, float yLoc, float width, float height) {
        setPosition(xLoc,yLoc);
        setSize(width,height);
    }

    //设置位置
    @Override
    public void setPosition(PointF pos) {
        if (pos != null) {
            setPosition(pos.x, pos.y);
        }
    }
    @Override
    public void setPosition(float x, float y) {
        setX(x);
        setY(y);
    }
    @Override
    public void setX(float x){
        this.x=x;
    }
    @Override
    public void setY(float y){
        this.y=y;
    }
    //获取位置
    @Override
    public PointF getPosition() {
        return new PointF(getX(),getY());
    }
    @Override
    public float getX(){return x;}
    @Override
    public float getY(){return y;}

    @Override
    //这个方法暂时不知道干些什么
    public boolean sizeIsIntrinsic() {
        // default value -- override in subclasses that need to...
        return false;
    }

    //设置大小
    @Override
    public void setSize(PointF size) {
        if (size != null) {
            setSize(size.x,size.y);
        }
    }
    @Override
    public void setSize(float w, float h) {
        setW(w);
        setH(h);
    }
    @Override
    public void setW(float w){this.width=w;}
    @Override
    public void setH(float h){this.height=h;}

    //得到大小
    @Override
    public PointF getSize(){return new PointF(getW(),getH());}
    @Override
    public float getW(){return width;}
    @Override
    public float getH(){return height;}

    //返回父级
    @Override
    public BaseVisualElement getParent(){return parent;}
    //设置父级
    @Override
    public void setParent(VisualElement newParent){
        BaseVisualElement newParent1=(BaseVisualElement) newParent;
        parent=newParent1;}
    //返回子元素的数量
    @Override
    public int getNumChildren(){return children.size();}
    //得到指定位置的子集
    @Override
    public VisualElement getChildAt(int index){
        if(index<0 || index>=getNumChildren()){
            return null;
        }else {
            return children.get(index);
        }
    }
    //在子列表中找到给定的子节点并返回其索引。如果给定的子节点不是此VisualElement的子节点，则此方法返回-1。
    @Override
    public int findChild(VisualElement child1){
        return children.indexOf(child1);
    }
    //增加一个子元素
    @Override
    public void addChild(VisualElement child1){
        if(child1!=null){
            children.add(child1);
        }
    }
    //移除索引处的子元素
    @Override
    public void removeChildAt(int index){
        for(int i=0;i<children.size();i++){
            if (i == index){
                children.remove(i);
                break;
            }
        }
    }
    @Override
    public void removeChild(VisualElement child1){
        for(int i=0;i<children.size();i++){
            if (children.get(i) == child1){
                children.remove(i);
                break;
            }
        }
    }
    //将子元素移到第一
    @Override
    public void moveChildFirst(VisualElement child1){
        for (int i=0;i<children.size();i++){
            if (children.get(i) == child1){
                children.remove(i);
                children.add(0,child1);
            }
        }
    }
    //将子元素移到最后
    @Override
    public void moveChildLast(VisualElement child1){
        for (int i=0;i<children.size();i++){
            if (children.get(i) == child1){
                children.remove(i);
                children.add(child1);
            }
        }
    }
    //子元素往前移
    @Override
    public void moveChildEarlier(VisualElement child1){
        for (int i=0;i<children.size();i++){
            if (children.get(i) == child1){
                if(i!=0){
                    children.remove(i);
                    children.add((i-1),child1);
                }
            }
        }
    }
    //子元素往后移
    @Override
    public void moveChildLater(VisualElement child1){
        for (int i=0;i<children.size();i++){
            if (children.get(i) == child1){
                if(i!=children.size()-1){
                    children.remove(i);
                    children.add((i+1),child1);
                }
            }
        }
    }
    @Override
    public void doLayout(){
        for(int i=0;i<children.size();i++){
            children.get(i).doLayout();
        }
    }
    @Override
    public void draw(Canvas onCanvas){
        doLayout();
        //规定父类的矩形范围
        float rectStartX = this.getX();
        float rectStartY = this.getY();
        float rectWidth = this.getW();
        float rectHeight = this.getH();
        onCanvas.clipRect(
                rectStartX,
                rectStartY,
                rectStartX + rectWidth,
                rectStartY + rectHeight);
        onCanvas.translate(rectStartX,rectStartY);

        for (int i=0;i<children.size();i++) {
//            //规定子类的范围
            onCanvas.save();
            float rectStartXX = children.get(i).getX();
            float rectStartyYY = children.get(i).getY();
            float rectWidthW = children.get(i).getW();
            float rectHeightH = children.get(i).getH();
            onCanvas.clipRect(
                    rectStartXX,
                    rectStartyYY,
                    rectStartXX+rectWidthW,
                    rectStartyYY+rectHeightH);
            children.get(i).draw(onCanvas);
            onCanvas.restore();
        }
    }

}
