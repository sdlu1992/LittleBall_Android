package com.demo.gravity.element;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import com.demo.gravity.view.GameView;

public class Hero {
    
    public int ballSize = 45;
    public float X;
    public float Y;
    public float v_X;
    public float v_Y;
    public float a_X;
    public float a_Y;
    public float a_Z;
    
    public boolean isGuarding = false;
    public float guardBeginTime = 0f;
    public float guardTime = 5f;
    GameView gameView;

    public Hero(GameView gameView) {
        this.gameView = gameView;
        
    }
    
    public void draw(Canvas canvas,Paint mPaint){
        mPaint.setColor(Color.RED);
     
        canvas.drawCircle(X + a_X * 1.5f, Y
                + a_Y * 1.5f, ballSize / 1.5f, mPaint);
        if (isGuarding) {// 如果吃了guard则外圈变绿
            if ((guardBeginTime - gameView.time) > 4) {
                if ((int) ((guardBeginTime - gameView.time) * 20) % 2 == 1) {
                    mPaint.setColor(Color.GREEN);
                } else {
                    mPaint.setColor(Color.YELLOW);
                }
            } else {
                mPaint.setColor(Color.GREEN);
            }
        } else {
            mPaint.setColor(Color.BLUE);
        }
        mPaint.setStyle(Style.STROKE);
        mPaint.setStrokeWidth(5);
        canvas.drawCircle(X, Y, ballSize, mPaint);
        mPaint.setStyle(Style.FILL);
    }

}
