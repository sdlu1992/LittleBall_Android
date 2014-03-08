package com.demo.gravity.element;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Vibrator;

import com.demo.gravity.object.Corpse;
import com.demo.gravity.view.GameView;

public class Enemy extends Ball {

    public int type = 0;
    int flag1 = 0;// 为了防止重叠而做的flag
    public float remainingTime = 10 * 1000;
    private boolean isPrepare = false;
    Vibrator vibrator;
    public float velocity = 0;
    public float angle = 0;
    public int alpha = 0;
    public int maxAlpha = 255;

    public Enemy(GameView gameView) {
        super(gameView);
        randomVelocity();
        if (!isCoinside()) {
            gameView.enemies.add(this);
        }
        Random ran = new Random();
        maxAlpha = ran.nextInt(100)+100;

    }

    private void randomVelocity() {
        // TODO Auto-generated method stub
        velocity = (float) Math.random() * 3;
        angle = (float) (Math.random() * Math.PI * 2);
    }

    private boolean isCoinside() {
        // TODO Auto-generated method stub
        // 防止第一次添加的时候出空指针
        // 判断是否重叠，即两圆心距离是否大于两半径之和
        for (Enemy eachEnemy : gameView.enemies) {

            float distance = gameView.moveHelper.getDistance(eachEnemy, this);
            if (distance < (eachEnemy.radius + this.radius)) {
                return true;
            }
            // GOD和guard同时只能有一个,概率够小了，不用限制了
            // if (eachEnemy.type == this.type) {
            // switch (this.type) {
            // case GameView.TYPE_GOD:
            // case GameView.TYPE_GUARD:
            // return true;
            // }
            // }
        }
        return false;
    }

    public void draw(Canvas canvas, Paint mPaint) {
//        if (remainingTime <= 0 && radius < 0) {
//            return;
//        }
        mPaint.setAlpha(alpha);
        canvas.drawCircle(X, Y, radius, mPaint);
        mPaint.setStyle(Style.FILL);

    }

    public boolean dealWith() {
        boundaryCollision();
        X += velocity * Math.cos(angle);
        Y += velocity * Math.sin(angle);

        if (isPrepare) {
            return false;
        }
        float distance = getDistanceToBall();
        if (distance < gameView.hero.ballSize + this.radius) {
            if (gameView.hero.isGuarding) {
                dealWithGuarding();
            } else {
                dealWithoutGuarding();
            }
            return true;
        }
        return false;
    }

    /**
     * 有保护罩guard时的处理
     * */
    protected void dealWithGuarding() {
        dealWithoutGuarding();
    }

    /**
     * 没有保护罩guard时的普通处理
     * */
    protected void dealWithoutGuarding() {

    }

    /**
     * 计算一enemy与ball的圆心的距离
     * 
     * @return enemy与ball的圆心距
     * */
    public float getDistanceToBall() {

        return (float) Math.sqrt(gameView.moveHelper.sumOfSquares(
                gameView.hero.X, gameView.hero.Y, X, Y));
    }

    public boolean countDown() {
//        if (isPrepare) {
//            alpha += 5;
//            if (alpha > maxAlpha) {
//                isPrepare = false;
//            }
//        }
        if(alpha < maxAlpha){
            alpha+=5;
        }
        if (isPrepare) {
            return false;
        }
        remainingTime -= gameView.dt.gap;
        if (remainingTime <= 0 && radius <= 0) {
            try {
                gameView.enemies.remove(this);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (remainingTime <= 0 && radius > 0) {
            radius -= 4;
        }
        return false;
    }

    public void delSelf() {
        Corpse cp = new Corpse(X, Y, radius, type);
        gameView.corpses.add(cp);
        gameView.enemies.remove(this);
    }

    /**
     * 对边界的碰撞处理
     * */
    private void boundaryCollision() {
        if (X < radius) {
            X = radius;
            angle = (float) Math.PI - angle;
            return;
        }

        if (Y < radius) {
            Y = radius;
            angle = -angle;
            return;
        }

        if (X > gameView.father.screenWidth - radius) {
            X = gameView.father.screenWidth - radius;
            angle = (float) Math.PI - angle;
            return;
        }

        if (Y > gameView.father.screenHeight - radius) {
            Y = gameView.father.screenHeight - radius;
            angle = -angle;
            return;
        }
    }

}
