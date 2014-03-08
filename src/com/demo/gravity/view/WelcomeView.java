package com.demo.gravity.view;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.demo.gravity.MainActivity;
import com.demo.gravity.element.Hero;
import com.demo.gravity.object.MyButton;
import com.demo.gravity.utils.MovableHelper;

public class WelcomeView {

    MainActivity father;
    int waveRadius;
    int initWaveRadius;
    int mini = 30;
    boolean isPreparing = false;
    MovableHelper moveHelper = new MovableHelper();
    float ballSize;
    Hero hero;
    int alpha = 0;
    MyButton btIsMusicOn;
    MyButton btIsSoundOn;
    /**
     * 开启gameview
     */
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            father.gv.isPause = false;
            father.gv.at.isGameOn = true;
            father.gv.gt.isGameOn = true;
            father.gv.ct.isGameOn = true;
            father.startSensorManager();
            father.gv.initBall();
            father.gv.resume();
        }
    };

    public WelcomeView(MainActivity father) {
        // TODO Auto-generated constructor stub
        this.father = father;
        
        hero = new Hero(null);
       // hero = father.gv.hero;
        initWaveRadius = (int) Math.sqrt(moveHelper.sumOfSquares(0, 0,
                MainActivity.screenHeight / 2, MainActivity.screenWidth / 2));
        waveRadius = initWaveRadius;
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        SharedPreferences info = father.getSharedPreferences("info", 0);
        hero.ballSize = info.getInt("ballSize", 45);
//        hero.X = info.getFloat("ballX", father.screenWidth / 2);
//        hero.Y = info.getFloat("ballY", father.screenHeight / 2);
        hero.X = MainActivity.screenWidth/2;
        hero.Y = MainActivity.screenHeight/2;
        initButton();
    }

    private void initButton() {
        // TODO Auto-generated method stub
        btIsMusicOn = new MyButton();
    }

    public void doDraw(Canvas canvas) {
        canvas.drawColor(Color.LTGRAY);
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        hero.draw(canvas, mPaint);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Style.STROKE);
        if (isPreparing) {
            for (int i = 0; i < 10; i++) {
                if (waveRadius - i * 10 < ballSize) {
                    continue;
                }
                mPaint.setAlpha(25 * i);
                canvas.drawCircle(hero.X, hero.Y, waveRadius - i * 10, mPaint);
            }
            if (waveRadius - 10 > ballSize) {
                mini = mini>3?mini--:3;
                waveRadius = waveRadius - mini;
                Log.e("waveRadius", waveRadius + "");
            } else {
                mHandler.sendEmptyMessage(0);
            }
        }
    }

}
