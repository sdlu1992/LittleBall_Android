package com.demo.gravity.element;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Vibrator;

import com.demo.gravity.view.GameView;

public class Bomb extends Enemy {

    public Bomb(GameView gameView) {
        super(gameView);
        // TODO Auto-generated constructor stub
        type = GameView.TYPE_BOMB;
    }

    @Override
    public void draw(Canvas canvas, Paint mPaint) {
        mPaint.setColor(Color.RED);
        super.draw(canvas, mPaint);
    }

    /**
     * 没有保护罩guard时的普通处理
     * */
    protected void dealWithoutGuarding() {
        delSelf();
        try {
            for (Enemy eachEnemy : gameView.enemies) {
                eachEnemy.delSelf();
            }
        } catch (Exception e) {
            
        }
        // gameView.enemies.removeAll(gameView.enemies);
        gameView.mark /= 1.3;
        gameView.hero.v_X = -gameView.hero.v_X * 1.5f;
        gameView.hero.v_Y = -gameView.hero.v_Y * 1.5f;
        gameView.combo = 0;
        gameView.priCombo = 0;
        gameView.music.playSound(GameView.TYPE_BOMB);
        // 振动
        vibrator = (Vibrator) gameView.father
                .getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = { 50, 50, 50, 50 }; // OFF/ON/OFF/ON...
        vibrator.vibrate(pattern, -1);// -1不重复，非-1为从pattern的指定下标开始重复
    }

    /**
     * 有保护罩guard时的处理
     * */
    protected void dealWithGuarding() {
        delSelf();
        gameView.mark += (int) (gameView.hero.ballSize - this.radius)
                * gameView.addtion;
        gameView.combo++;
    }
}
