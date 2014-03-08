package com.demo.gravity.element;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Vibrator;

import com.demo.gravity.view.GameView;

public class Speed extends Enemy {

    public Speed(GameView gameView) {
        super(gameView);
        // TODO Auto-generated constructor stub
        remainingTime = 5*1000;
        type = GameView.TYPE_SPEED;
    }

    @Override
    public void draw(Canvas canvas, Paint mPaint) {
        mPaint.setColor(Color.MAGENTA);
        super.draw(canvas, mPaint);
    }

    /**
     * 没有保护罩guard时的普通处理
     * */
    protected void dealWithoutGuarding() {
        delSelf();
        gameView.hero.v_X *= 1000;
        gameView.hero.v_Y *= 1000;
        // 振动
        vibrator = (Vibrator) gameView.father
                .getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = { 50, 50, 50, 50 }; // OFF/ON/OFF/ON...
        vibrator.vibrate(pattern, -1);// -1不重复，非-1为从pattern的指定下标开始重复
    }
}
