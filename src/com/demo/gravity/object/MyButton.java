package com.demo.gravity.object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.demo.gravity.MainActivity;
import com.demo.gravity.view.GameView;

public class MyButton {

    public static final int SHAPE_CIRCLE = 0;
    public static final int SHAPE_RECT = 1;
    protected boolean isPattern = false;
    protected boolean isText = false;
    int bkgColor;
    int ptColor;
    int textColor;
    int textSize;
    protected String text;
    private Path mPath;
    float left, top, right, bottom;
    GameView gameView;
    float x, y, radius;
    float height = 40;
    int width = 400;
    int shape = 0;

    public MyButton() {

    }

    public MyButton(GameView gameView, float x, float y, float radius) {
        this.gameView = gameView;
        this.x = x;
        this.y = y;
        this.radius = radius;
        bkgColor = Color.BLUE;
        ptColor = Color.RED;
        shape = SHAPE_CIRCLE;
    }

    public MyButton(GameView gameView, float left, float top, float right,
            float bottom) {
        this.gameView = gameView;
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        bkgColor = Color.BLUE;
        ptColor = Color.RED;
        shape = SHAPE_RECT;
    }

    public MyButton(GameView gameView) {
        this.gameView = gameView;
        left = MainActivity.screenWidth / 2 - width / 2;
        right = MainActivity.screenWidth / 2 + width / 2;
        top = MainActivity.screenHeight;
        bottom = top + height;
        bkgColor = Color.BLUE;
        ptColor = Color.RED;
    }

    public void draw(Canvas canvas, Paint mPaint) {

        mPaint.setColor(bkgColor);
        float textX = 0;
        float textY = 0;
        switch (shape) {
        case SHAPE_CIRCLE:
            canvas.drawRect(left, top, right, bottom, mPaint);
            textX = (left + right - textSize * text.length()) / 2;
            textY = (top + bottom + textSize) / 2;
            break;
        case SHAPE_RECT:
            canvas.drawCircle(x, y, radius, mPaint);
            textX = (x - textSize*text.length())/2;
            textY = (y - textSize)/2;
            break;
        default:
            break;
        }

        if (isPattern && mPath != null) {
            mPaint.setColor(ptColor);
            canvas.drawPath(mPath, mPaint);
        }
        if (isText && text != null) {
            mPaint.setTextSize(textSize);
            mPaint.setColor(textColor);
            canvas.drawText(text, textX, textY, mPaint);
        }

    }

    public void setBackgroundColor(int color) {

        bkgColor = color;

    }

    public void setShape(int shape) {
        this.shape = shape;
    }

    public boolean isTouch(float x, float y) {

        if (y < bottom && y > top && x > left && x < right) {
            return true;
        }
        return false;

    }

    public void setButtonPosition(Float left, Float top, Float right,
            Float bottom) {

        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public void setPatternColor(int color) {

        ptColor = color;

    }

    public void setPattern(Path mPath) {

        this.mPath = mPath;
        isPattern = true;

    }

    public void setText(String text) {

        this.text = text;
        isText = true;
    }

    public void setTextSize(int size) {
        this.textSize = size;
    }

    public void setTextColor(int color) {
        this.textColor = color;
    }
}
