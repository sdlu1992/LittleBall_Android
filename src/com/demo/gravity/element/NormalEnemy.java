package com.demo.gravity.element;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.demo.gravity.view.GameView;

public class NormalEnemy extends Enemy {

    public NormalEnemy(GameView gameView) {
        super(gameView);
        // TODO Auto-generated constructor stub
        type = GameView.TYPE_NORMAL;
    }

    @Override
    public void draw(Canvas canvas, Paint mPaint) {
        mPaint.setColor(0xCC000000);
        super.draw(canvas, mPaint);
    }

    /**
     * 没有保护罩guard时的普通处理
     * */
    protected void dealWithoutGuarding() {
        delSelf();
        gameView.mark += (int) (gameView.hero.ballSize - this.radius)
                * gameView.addtion;
        gameView.hero.v_X /= 1.1;
        gameView.hero.v_Y /= 1.1;
        gameView.combo++;
        if(gameView.combo>gameView.bigestCombo){
            gameView.bigestCombo = gameView.combo;
        }
        gameView.music.playSound(GameView.TYPE_NORMAL);
    }
}
