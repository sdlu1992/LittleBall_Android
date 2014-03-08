package com.demo.gravity.element;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import com.demo.gravity.view.GameView;

public class Guard extends Enemy {

    public int n = 1;
    
    public Guard(GameView gameView) {
        super(gameView);
        // TODO Auto-generated constructor stub
        type = GameView.TYPE_GUARD;
        radius = 30;
        remainingTime = 5*1000;
    }

    @Override
    public void draw(Canvas canvas, Paint mPaint) {
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Style.STROKE);
        mPaint.setStrokeWidth(5);
        changeGuardRadius();
        gameView.guards.add(this);
        super.draw(canvas, mPaint);
    }

    /**
     * 有保护罩guard时的处理
     * */
    protected void dealWithGuarding() {
        delSelf();
        gameView.hero.guardBeginTime = gameView.time;
        gameView.combo++;
        if(gameView.combo>gameView.bigestCombo){
            gameView.bigestCombo = gameView.combo;
        }
        super.dealWithGuarding();
    }

    /**
     * 没有保护罩guard时的普通处理
     * */
    protected void dealWithoutGuarding() {

        delSelf();
        gameView.guards.remove(this);
        gameView.hero.isGuarding = true;
        gameView.hero.guardBeginTime = gameView.time;
    }

    private void changeGuardRadius() {
        if(remainingTime<=0){
            return;
        }
        if (radius >= 30)
            n = -1;
        else if (radius <= 0) {
            n = 1;
        }
        radius += n;
    }

}
