package com.demo.gravity.utils;

import com.demo.gravity.element.Enemy;

public class MovableHelper {

    

    public float velocity;
    public float acceler;
    public float gravity = 9.8f;
    public float energy = 0;

    public MovableHelper() {
//        gravity = getGravity();
    }

    /**
     * 计算两个enemy之间的距离
     * */
    public float getDistance(Enemy a, Enemy b) {

        return (float)Math.sqrt(sumOfSquares(a.X, a.Y, b.X, b.Y));
    }

    /**
     * 计算一点与一enemy之间的距离
     * */
    public float getDistance(float x, float y, Enemy b) {
        return (float)Math.sqrt(sumOfSquares(x, y, b.X, b.Y));
    }

    public float sumOfSquares(float x1, float y1, float x2, float y2) {
        float sum = (float) Math.pow(x1 - x2, 2)
                + (float) Math.pow(y1 - y2, 2);
        return sum;
    }
}
