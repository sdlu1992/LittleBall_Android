package com.demo.gravity.object;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import com.demo.gravity.element.Ball;

public class MyPoint extends Ball {

    float enemyRadius = 0f;
    ArrayList<MyPoint> lists;
    //用角度表示看看行不行
    float angle = 0;
    float velocity = 0;

    public MyPoint(float x, float y, int color) {
        super(x, y, color);
        // TODO Auto-generated constructor stub
        radius = 5;
        alpha = 255;
        randomVelocity();
    }

    private void randomVelocity() {
        // TODO Auto-generated method stub
        Random random = new Random();
        velocity = random.nextInt(10);
        angle = (float)Math.PI*random.nextFloat()*2;
    }

    /***
     * 加入修正值，改变点点的起始位置
     */
    private void calCoordinate() {
        // TODO Auto-generated method stub
        Random random = new Random();
        Float minute = (float)Math.PI*radius;
        Float a = random.nextFloat();
        X += minute*Math.sin(a);
        Y += minute*Math.cos(a);
    }

    public boolean changeVelocity() {
        X += Math.sin(angle)*velocity;
        Y += Math.cos(angle)*velocity;
        if(velocity>2){
            velocity -= 0.3;
            alpha -= 5;
        } else{
            return true;
        }
        return false;
    }

    public boolean dealWith() {
        if (velocity < 3 || alpha <= 0) {
            if (!lists.isEmpty()) {
                lists.remove(this);
            }
        }
        return false;
    }

    public void setEnemyRadius(float radius) {
        enemyRadius = radius;
        calCoordinate();
    }

    public void setPointList(ArrayList<MyPoint> lists2) {
        this.lists = lists2;
    }

    public void draw(Canvas canvas, Paint mPaint) {
        mPaint.setAlpha(alpha);
        canvas.drawCircle(X, Y, radius, mPaint);
        mPaint.setStyle(Style.FILL);
        mPaint.setAlpha(255);
    }

}
