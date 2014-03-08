package com.demo.gravity.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.demo.gravity.MainActivity;
import com.demo.gravity.object.MyButton;

public class EndView {

    MainActivity father;
    public int mark = 0;
    public int bigestCombo = 0;
    MyButton btBegin;
    float ws;
    float hs;

    public EndView(MainActivity father) {

        this.father = father;
        ws = MainActivity.widthScale;
        hs = MainActivity.heightScale;
        initButtons();

    }

    public void draw(Canvas canvas, Paint mPaint) {
        drawBackground(canvas, mPaint);
        drawStatistics(canvas, mPaint);
        drawTop(canvas, mPaint);
    }

    private void drawBackground(Canvas canvas, Paint mPaint) {

        canvas.drawColor(Color.LTGRAY);

    }

    private void drawStatistics(Canvas canvas, Paint mPaint) {

        mPaint.setColor(Color.RED);
        mPaint.setTextSize(30*ws);
        mPaint.setShadowLayer(2, 0, 0, Color.BLACK);
        canvas.drawRect(50 * ws, 100 * hs, 250 * ws, 370 * hs, mPaint);
        mPaint.clearShadowLayer();
        mPaint.setColor(Color.BLACK);
        canvas.drawText("分数 : " + String.valueOf(mark), 60 * ws, 130 * hs,
                mPaint);
        mPaint.setTextSize(25*ws);
        canvas.drawText("最大连击数 : " + String.valueOf(bigestCombo), 60 * ws,
                160 * hs, mPaint);
        btBegin.draw(canvas, mPaint);
    }

    private void drawTop(Canvas canvas, Paint mPaint) {
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(30*ws);
        mPaint.setShadowLayer(2, 0, 0, Color.BLACK);
        canvas.drawRect((50 + 400)*ws, 50*hs, (250 + 400)*ws, 420*hs, mPaint);
        mPaint.clearShadowLayer();
        mPaint.setColor(Color.BLACK);
        btBegin.draw(canvas, mPaint);
        canvas.drawText("排行榜 : ", (60 + 400)*ws, 80*hs, mPaint);
        mPaint.setTextSize(25*hs);
        if (father.gv.top[0] == 0) {
            return;
        }
        canvas.drawText("1st : " + String.valueOf(father.gv.top[0]), (80 + 400)*ws,
                110*hs, mPaint);
        if (father.gv.top[1] == 0) {
            return;
        }
        canvas.drawText("2nd : " + String.valueOf(father.gv.top[1]), (80 + 400)*ws,
                140*hs, mPaint);
        for (int i = 2; father.gv.top[i] != 0 && i < 10; i++) {
            canvas.drawText(i + "th : " + father.gv.top[i], (80 + 400)*ws,
                    (110 + 30 * i)*hs, mPaint);
        }
    }

    private void initButtons() {
        // TODO Auto-generated method stub
        // 重新开始游戏的button
        btBegin = new MyButton(father.gv, MainActivity.screenWidth - 80*ws,
                MainActivity.screenHeight - 60*ws, MainActivity.screenWidth*ws,
                MainActivity.screenHeight);

        btBegin.setTextSize((int)(30*ws));
        btBegin.setTextColor(Color.GRAY);
        btBegin.setText("重玩");

    }
}
