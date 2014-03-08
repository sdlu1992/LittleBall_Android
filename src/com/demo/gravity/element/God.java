package com.demo.gravity.element;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.demo.gravity.view.GameView;

public class God extends Enemy {

    public God(GameView gameView) {
        super(gameView);
        // TODO Auto-generated constructor stub
        type = GameView.TYPE_GOD;
        remainingTime = 5*1000;
    }

    @Override
    public void draw(Canvas canvas, Paint mPaint) {
        mPaint.setColor(Color.YELLOW);
    //    mPaint.setColor(Color.)
        super.draw(canvas, mPaint);
    }

    /**
     * 没有保护罩guard时的普通处理
     * */
    protected void dealWithoutGuarding() {

        for (Enemy eachE : gameView.enemies) {
            gameView.mark += (int) 2 * gameView.addtion
                    * (gameView.hero.ballSize - eachE.radius);
            gameView.combo++;
        }
        if(gameView.combo>gameView.bigestCombo){
            gameView.bigestCombo = gameView.combo;
        }
        gameView.enemies.removeAll(gameView.enemies);

    }
}
