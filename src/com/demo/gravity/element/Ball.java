package com.demo.gravity.element;

import com.demo.gravity.view.GameView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Ball{

    public float X;
    public float Y;
    public float radius;
    public GameView gameView;
    public int alpha = 100;
    public int color;

    public Ball(GameView gameView) {
        this.gameView = gameView;
        X = (float) Math.random() * gameView.father.screenWidth;
        Y = (float) Math.random() * gameView.father.screenHeight;
        radius = (float) (Math.random() * 30 + 10) * gameView.father.widthScale;
    }
    
    public Ball(Float x,Float y, int color){
        this.X = x;
        this.Y = y;
        this.color= color;
    }

    public void setAlpha(int alpha){
        this.alpha = alpha;
    }
    
    public void draw(Canvas canvas, Paint mPaint) {
        mPaint.setAlpha(alpha);
        canvas.drawCircle(X, Y, radius, mPaint);
        mPaint.setStyle(Style.FILL);
        mPaint.setAlpha(255);
    }
}
