package com.demo.gravity.thread;

import android.util.Log;

import com.demo.gravity.element.Enemy;
import com.demo.gravity.view.GameView;

public class GameThread extends Thread {

    public boolean isGameOn = false;
    public boolean flag = false;
    GameView gameView;
    private int sensorPause = 0;
    private boolean isSensorPause = false;
    private static final int GAP_MILLS = 20;
    private static final int SENSOR_PAUSE_TIME = 200;

    private final String TAG = "GameThread";
    
    public GameThread(GameView father) {
    	Log.e(TAG, "GameThreadCreate");
        this.gameView = father;
        this.flag = true;
    }

    public void run() {

    	Log.e(TAG, "GameThreadStart");
        while (flag) {
            while (isGameOn) {
                gameView.addtion = (float) (gameView.combo / 10 / 10.0 + 1);
                // 对各种坐标的处理
                
                boundaryCollision();// 对边界的碰撞处理
                if (sensorPause > 0) {
                    if (!isSensorPause) {
                        gameView.father.endSensorManager();
                        isSensorPause = true;
                    }
                    gameView.hero.X += gameView.hero.v_X;
                    gameView.hero.Y += gameView.hero.v_Y;
                } else {
                    if (isSensorPause) {
                        gameView.father.startSensorManager();
                        isSensorPause = false;
                    }
                }
                gameView.basicComboSize = (gameView.combo <= 100) ? (gameView.combo / 10 * 2 + 30)
                        : gameView.basicComboSize;
                // 對敵人的相應，如果是普通敵人就消滅加分，god全屏消滅加雙倍分，bomb全屏消滅減一半分
                if (gameView.enemies.isEmpty()) {
                    continue;
                }
                try {
                    for (int i = 0, len = gameView.enemies.size(); i < len; i++) {
                        // 避免数组越界
                        Enemy eachEnemy = gameView.enemies.get(i);
                        if (eachEnemy != null && eachEnemy.dealWith()) {
                            eachEnemy = null;
                            break;
                        }
                    }
                    sleep(GAP_MILLS);

                } catch (Exception e) {

                }

            }
            try {
                sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 对边界的碰撞处理
     * */
    private void boundaryCollision() {
        
        if (gameView.hero.X < gameView.hero.ballSize) {
            gameView.hero.X = gameView.hero.ballSize;
            gameView.hero.v_X = -gameView.hero.v_X / 1.5f;
            sensorPause = SENSOR_PAUSE_TIME;
        }

        if (gameView.hero.Y < gameView.hero.ballSize) {
            gameView.hero.Y = gameView.hero.ballSize;
            gameView.hero.v_Y = -gameView.hero.v_Y / 1.5f;
            sensorPause = SENSOR_PAUSE_TIME;
        }

        if (gameView.hero.X > gameView.father.screenWidth
                - gameView.hero.ballSize) {
            gameView.hero.X = gameView.father.screenWidth
                    - gameView.hero.ballSize;
            gameView.hero.v_X = -gameView.hero.v_X / 1.5f;
            sensorPause = SENSOR_PAUSE_TIME;
        }

        if (gameView.hero.Y > gameView.father.screenHeight
                - gameView.hero.ballSize) {
            gameView.hero.Y = gameView.father.screenHeight
                    - gameView.hero.ballSize;
            gameView.hero.v_Y = -gameView.hero.v_Y / 1.5f;
            sensorPause = SENSOR_PAUSE_TIME;
        }
        if (sensorPause >= 0) {
            sensorPause -= GAP_MILLS;
        }
    }

}
