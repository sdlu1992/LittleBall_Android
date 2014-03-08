package com.demo.gravity.object;

import java.util.ArrayList;
import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.demo.gravity.view.GameView;

public class Corpse {

    private ArrayList<MyPoint> lists;

    float x;
    float y;
    float radius;
    int type;
    int color;
    int number = 0;

    public Corpse(float x, float y, float radius, int type) {

        this.x = x;
        this.y = y;
        this.radius = radius;
        color = getColor(type);
        createLists();

    }

    private int getColor(int type2) {
        // TODO Auto-generated method stub
        switch (type2) {
        case GameView.TYPE_NORMAL:
            return Color.BLACK;
        case GameView.TYPE_GOD:
            return Color.YELLOW;
        case GameView.TYPE_GUARD:
            return Color.GREEN;
        case GameView.TYPE_SPEED:
            return Color.MAGENTA;
        case GameView.TYPE_BOMB:
            return Color.RED;
        case GameView.TYPE_TIME:
            return Color.BLUE;
        default:
            return Color.YELLOW;
        }
    }

    private void createLists() {
        lists = new ArrayList<MyPoint>();
        for (int i = 0; i < radius * 2; i++) {
            MyPoint p = new MyPoint(x, y, color);
            p.setEnemyRadius(radius);
            p.setPointList(lists);
            lists.add(p);
            number++;
        }
    }

    public void draw(Canvas canvas, Paint mPaint) {
        Paint paint = new Paint();
        paint.setColor(color);
        for (int i = 0; i < lists.size(); i++) {
            lists.get(i).draw(canvas, paint);
        }

    }

    public boolean dealWith() {
        for (int i = 0; i < lists.size(); i++) {
            // 避免数组越界
//            if (lists.isEmpty()) {
//                return true;
//            }
//            if (i > lists.size() - 1) {
//                break;
//            }
            MyPoint eachPoint = lists.get(i);
            if (eachPoint != null && eachPoint.dealWith()) {
                eachPoint = null;
                break;
            }
        }

        if (lists.isEmpty()) {
            return true;
        }
        return false;
    }

    public void countDown() {
        for (int i = 0; i < lists.size(); i++) {
            try {
            	lists.get(i).changeVelocity();
			} catch (Exception e) {
				// TODO: handle exception
			}
        }
//        for(MyPoint mp:lists){
//            mp.changeVelocity();
//        }
    }
}
