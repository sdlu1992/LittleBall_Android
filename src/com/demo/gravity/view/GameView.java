package com.demo.gravity.view;

import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.demo.gravity.MainActivity;
import com.demo.gravity.element.Enemy;
import com.demo.gravity.element.Hero;
import com.demo.gravity.object.Corpse;
//import com.demo.gravity.object.Knowledge;
import com.demo.gravity.object.MyButton;
import com.demo.gravity.thread.CorpseThread;
import com.demo.gravity.thread.DrawThread;
import com.demo.gravity.thread.GameThread;
import com.demo.gravity.thread.TimeThread;
import com.demo.gravity.utils.MovableHelper;
import com.demo.gravity.utils.Music;

public class GameView extends MySurfaceView implements SurfaceHolder.Callback {

    public boolean isPause = false;
    public Hero hero;
    public int priCombo = 0;
    public int combo = 0;
    public int basicComboSize = 30;
    public int bigestCombo = 0;
    private int comboSize = basicComboSize;
    public float addtion = 0;
    public int timeIndex = 1;// 当前显示几个时间
    public int nowState = STATE_BEGIN;

    public final static int STATE_BEGIN = 0;
    public final static int STATE_GAME = 1;
    public final static int STATE_END = 2;

    public final static int TYPE_NORMAL = 0;
    public final static int TYPE_BOMB = 1;
    public final static int TYPE_GOD = 2;
    public final static int TYPE_GUARD = 3;
    public final static int TYPE_SPEED = 4;
    public final static int TYPE_TIME = 5;

    public Vector<Enemy> enemies;
    public Vector<Enemy> guards;
    public Vector<Corpse> corpses;
    public int mark = 0;
    public float time = 10.00f;

    public MainActivity father;
    public MovableHelper moveHelper;// 有关各种运动的类，包括圆心坐标，速度加速度
    public int driftDistance = 0;
    public GameThread gt;
    public DrawThread dt;
    public TimeThread at;
    public CorpseThread ct;
    public boolean isAddTime = false;
    public int addTimeTextSize = 0;
    public int addSeconds = 0;
    public Music music;
    public MyButton btContinue;
    public MyButton btBegin;
    public WelcomeView welcomeView;
    public EndView endView;

    public Typeface jianzhi;
    Typeface yejing;

    public Integer top[] =new Integer[11];

    public GameView(Context context) {
        super(context);
    };

    public GameView(MainActivity context) {
        super(context);
        // TODO Auto-generated constructor stub
        getHolder().addCallback(this);
        this.father = context;
        hero = new Hero(this);
        enemies = new Vector<Enemy>();
        guards = new Vector<Enemy>();
        corpses = new Vector<Corpse>();
        moveHelper = new MovableHelper();
        initBall();
        gt = new GameThread(this);
        dt = new DrawThread(getHolder(), this);
        at = new TimeThread(this);
        ct = new CorpseThread(this);
        music = new Music(father);
        welcomeView = new WelcomeView(father);
        endView = new EndView(father);

        jianzhi = Typeface.createFromAsset(context.getAssets(),
                "fonts/combo.ttf");
        yejing = Typeface.createFromAsset(context.getAssets(),
                "fonts/yejing.ttf");
        initButtons();
        for (int i = 0; i < 11; i++) {
            top[i] = 0;
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
        // TODO Auto-generated method stub

    }

    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        // 每次resume的时候也会调用此方法，所以应该判断一下
        at.isGameOn = false;
        gt.isGameOn = false;
        dt.isGameOn = true;
        ct.isGameOn = false;
        if (isPause||nowState == STATE_BEGIN) {
            at.isGameOn = false;
            gt.isGameOn = false;
            ct.isGameOn = false;
        }
        if (!dt.isAlive()) {
            dt.start();
        }
        if (!gt.isAlive()) {
            gt.start();
        }
        if (!at.isAlive()) {
            at.start();
        }
        if (!ct.isAlive()) {
            ct.start();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub

    }

    public void doDraw(Canvas canvas) {
        Paint mPaint = new Paint();
        switch (nowState) {
        case STATE_BEGIN:
            welcomeView.doDraw(canvas);
            break;
        case STATE_GAME:// 玩游戏的时候
            canvas.drawColor(0xffffffff);
            mPaint.setAntiAlias(true);
            // 画尸体
            drawCorpses(canvas, mPaint);
            // 画圈圈
            drawEnemies(canvas, mPaint);
            hero.draw(canvas, mPaint);
            // 如果暂停就画按键。
            if (isPause) {
                // drawButtons(canvas, mPaint);
                btBegin.draw(canvas, mPaint);
                btContinue.draw(canvas, mPaint);
            }
            // 画分数
            drawMarks(canvas, mPaint);
            // 画倒计时
            drawTime(canvas, mPaint);
            // 画保护圈倒计时
            if (hero.isGuarding) {
                drawGuardTime(canvas, mPaint);
            }
            if (isAddTime) {
                // 画增加的时间
                drawAddTime(canvas, mPaint);
            }
            // 画连击数
            drawCombo(canvas, mPaint);
            // mKnowledge.draw(canvas, mPaint);
            break;
        case STATE_END:
            endView.draw(canvas, mPaint);
            break;
        default:
            break;
        }

    }

    private void drawAddTime(Canvas canvas, Paint mPaint) {
        mPaint.setColor(Color.CYAN);
        mPaint.setTextSize(addTimeTextSize);
        canvas.drawText("+" + String.valueOf(addSeconds),
                MainActivity.screenWidth - 105, (++timeIndex) * 40, mPaint);
    }

    private void drawCombo(Canvas canvas, Paint mPaint) {
        mPaint.setColor(Color.RED);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        if (combo > priCombo) {
            if (comboSize < basicComboSize + 20 && priCombo != combo) {
                comboSize += 7;
            } else if (comboSize >= basicComboSize + 20) {
                priCombo = combo;
                comboSize = basicComboSize;
            }
        }

        if (combo >= 5) {
            mPaint.setTypeface(jianzhi);
            mPaint.setTextSize(25);
            canvas.drawText("Combo :", 10, 100, mPaint);
            mPaint.setTextSize(comboSize);
            canvas.drawText(String.valueOf(combo), 150, 100, mPaint);
        }
        if (combo >= 10) {
            mPaint.setTextSize(basicComboSize);
            canvas.drawText("+" + (int) (Math.rint((addtion - 1) * 10) * 10)
                    + "%", 10, 180, mPaint);
        }
    }

    private void drawGuardTime(Canvas canvas, Paint mPaint) {

        String str = null;
        if (hero.isGuarding) {
            mPaint.setTypeface(yejing);
            str = String.valueOf(5 - (hero.guardBeginTime - time)).substring(0,
                    5);

        } else if (!hero.isGuarding && driftDistance > 0) {
            str = "0.000";
        }
        mPaint.setTextSize(30);
        mPaint.setColor(Color.GREEN);
        canvas.drawText(str, MainActivity.screenWidth - driftDistance,
                (++timeIndex) * 40, mPaint);
    }

    private void drawTime(Canvas canvas, Paint mPaint) {
        mPaint.setTypeface(yejing);
        timeIndex = 1;
        if (time < 10) {// 如果在10秒内就变大便粗变红
            mPaint.setColor(Color.RED);
            mPaint.setTextSize(35);
        }
        canvas.drawText(String.valueOf(time).substring(0, 5),
                MainActivity.screenWidth - 105, timeIndex * 40, mPaint);
    }

    private void drawMarks(Canvas canvas, Paint mPaint) {
        mPaint.setColor(Color.CYAN);
        mPaint.setTextSize(30 * MainActivity.widthScale);
        canvas.drawText("分数： " + String.valueOf(mark), 20 * MainActivity.widthScale,
                40 * MainActivity.heightScale, mPaint);
    }

    private void drawCorpses(Canvas canvas, Paint mPaint) {
            for (int i=0; i<corpses.size();i++) {
                corpses.get(i).draw(canvas, mPaint);
            }
    }

    private void drawEnemies(Canvas canvas, Paint mPaint) {
            for (int i = 0, len = enemies.size(); i < len; i++) {
                // 避免数组越界
                if (i >= enemies.size() - 1) {
                    break;
                }
                Enemy eachEnemy = enemies.get(i);
                eachEnemy.draw(canvas, mPaint);
            }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();
        double disToHero;
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            switch(nowState){
            case STATE_BEGIN:
                //点击开始游戏
                disToHero = Math.sqrt(moveHelper
                        .sumOfSquares(pointX, pointY, welcomeView.hero.X, welcomeView.hero.Y));
                if (disToHero < welcomeView.hero.ballSize) {
                    welcomeView.isPreparing = true;
                }
                break;
            case STATE_GAME:
                if(!isPause){
                    if (pointX > (int) MainActivity.screenWidth >> 1) {
                        if (combo >= 10) {
                            hero.v_X = (float) (-hero.v_X * 1.5);
                            hero.v_Y = (float) (-hero.v_Y * 1.5);
                            combo -= 10;
                        }
                    }
                }
                break;
            case STATE_END:
                if (endView.btBegin.isTouch(pointX, pointY)) {
                    initBall();
                    resume();
                }
                break;
            }
            break;
        case MotionEvent.ACTION_UP:
            // 在暂停状态下判断触屏的位置以作出响应
            if (isPause && nowState != STATE_END) {
                // 如果按左边按钮则从暂停中恢复。
                if (btContinue.isTouch(pointX, pointY)) {
                    resume();
                }
                // 如果按右边按钮则重新玩
                else if (btBegin.isTouch(pointX, pointY)) {
                    initBall();
                    resume();
                }
            }
            break;
        }
        return true;
    }

    public void initBall() {
        enemies.removeAll(enemies);
        mark = 0;
        combo = 0;
        priCombo = 0;
        bigestCombo = 0;
        basicComboSize = 30;
        comboSize = basicComboSize;
        time = 60;
        hero.X = MainActivity.screenWidth / 2;
        hero.Y = MainActivity.screenHeight / 2;
        hero.v_X = 0;
        hero.v_Y = 0;
        hero.isGuarding = false;
    }

    private void initButtons() {
        // TODO Auto-generated method stub

        // 继续游戏的button
        btContinue = new MyButton(this, MainActivity.screenWidth / 2 - 170,
                MainActivity.screenHeight / 2 - 60, MainActivity.screenWidth / 2 - 50,
                MainActivity.screenHeight / 2 + 60);
        // 重新开始游戏的button
        btBegin = new MyButton(this, MainActivity.screenWidth / 2 + 50,
                MainActivity.screenHeight / 2 - 60, MainActivity.screenWidth / 2 + 170,
                MainActivity.screenHeight / 2 + 60);

        Path mPath = new Path();
        mPath.moveTo(MainActivity.screenWidth / 2 - 150, MainActivity.screenHeight / 2 + 50);
        mPath.lineTo(MainActivity.screenWidth / 2 - 150, MainActivity.screenHeight / 2 - 50);
        mPath.lineTo(MainActivity.screenWidth / 2 - 60, MainActivity.screenHeight / 2);
        mPath.lineTo(MainActivity.screenWidth / 2 - 150, MainActivity.screenHeight / 2 + 50);
        btContinue.setPatternColor(Color.GRAY);
        btContinue.setPattern(mPath);

        btBegin.setTextSize(30);
        btBegin.setTextColor(Color.GRAY);
        btBegin.setText("重玩");

    }

    public void pause() {
        isPause = true;
        gt.isGameOn = false;
        at.isGameOn = false;
        // hero.isGuarding = false;
        father.endSensorManager();
        father.saveInfo();
    }

    public void resume() {
        nowState = STATE_GAME;
        isPause = false;
        gt.isGameOn = true;
        at.isGameOn = true;
        father.startSensorManager();
    }
}
