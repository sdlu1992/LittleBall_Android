package com.demo.gravity.element;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.demo.gravity.view.GameView;

public class TimeAdder extends Enemy{

    int addSeconds = 0;
    public TimeAdder(GameView gameView) {
        super(gameView);
        // TODO Auto-generated constructor stub
        radius = 30;
        addSeconds = (int)(Math.random()*9+1);
        type = GameView.TYPE_TIME;
    }
    
    public void draw(Canvas canvas,Paint mPaint){
        
        mPaint.setColor(Color.BLUE);
        super.draw(canvas, mPaint);
        mPaint.setColor(Color.MAGENTA);
        mPaint.setTextSize(30);
        canvas.drawText(String.valueOf(addSeconds), X-8, Y+8, mPaint);
    }
    
    public void dealWithoutGuarding(){
        delSelf();
        gameView.time += addSeconds;
        if(gameView.hero.isGuarding){
            gameView.hero.guardBeginTime += addSeconds;
        }
        gameView.addSeconds = addSeconds;
        gameView.isAddTime = true;
    }

}
