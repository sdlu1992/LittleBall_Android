package com.demo.gravity.thread;

import java.util.Arrays;
import java.util.Comparator;

import android.util.Log;

import com.demo.gravity.element.Bomb;
import com.demo.gravity.element.God;
import com.demo.gravity.element.Guard;
import com.demo.gravity.element.NormalEnemy;
import com.demo.gravity.element.Speed;
import com.demo.gravity.element.TimeAdder;
import com.demo.gravity.view.GameView;

public class TimeThread extends Thread {

    private GameView gameView;
    public boolean isGameOn = false;
    public boolean flag = false;

    public int gapMills = 25;
    public int gapFlag = 0;
    int n = 1;
    int i = 0;

    public float displayProbBomb = 0.05f;
    public float displayProbGod = 1 - 0.02f;
    public float displayGuard = 1 - 0.04f;
    public float displaySpeed = 1 - 0.08f;
    public float displayTime = 1 - 0.13f;

    public double numRandom = 0;

    private final String TAG = "TimeThread";
    public TimeThread(GameView gameView) {
        Log.e(TAG, "TimeThreadCreate");
        this.gameView = gameView;
        this.flag = true;
    }

    @Override
    public void run() {
        Log.e(TAG, "TimeThreadStart");
        while (flag) {
            while (isGameOn) {
                // try {
                // 每gapFlag×25毫秒加一个敌人，敌人数为20就不加了
                if ((gapFlag % (int) (20 / gameView.addtion) == 0)
                        && gameView.enemies.size() < 20) {
                    addEnemy();
                }
                if (gameView.hero.isGuarding) {
                    if (gameView.driftDistance <= 100) {
                        i++;
                        gameView.driftDistance += i;
                    }
                } else if (gameView.driftDistance > 0) {
                    i++;
                    gameView.driftDistance -= i;
                } else if (gameView.driftDistance < 0 || i != 0) {
                    i = 0;
                    gameView.driftDistance = 0;
                }
                // 五秒后防护罩消失
                if (gameView.hero.isGuarding
                        && (gameView.hero.guardBeginTime - gameView.time) > 5) {
                    i = 0;
                    gameView.hero.isGuarding = false;
                }
                if (gameView.isAddTime && gameView.addTimeTextSize <= 30) {
                    gameView.addTimeTextSize += 3;
                } else if (gameView.isAddTime && gameView.addTimeTextSize >= 30) {
                    gameView.isAddTime = false;
                    gameView.addTimeTextSize = 0;
                }
                try {
                    sleep(gapMills);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                gameView.time = gameView.time > 0 ? gameView.time
                        - (float) gapMills / 1000f : 0;
                gapFlag = gapFlag > 2400 ? 0 : gapFlag + 1;
                // if (gapFlag * gapMills % 10000 == 0) {
                // gameView.mKnowledge.state = 1;
                // gameView.mKnowledge.createKnowledge();
                // }
                // gameView.mKnowledge.changePosition();
                for (int i = 0, len = gameView.enemies.size(); i < len; i++) {
                    // if(i>gameView.enemies.size()-1){
                    // break;
                    // }
                    if (gameView.enemies.get(i).countDown()) {
                        break;
                    }
                }
                for (int i = 0, len = gameView.corpses.size(); i < len; i++) {
                    // if(i>gameView.enemies.size()-1){
                    // break;
                    // }
                    try{
                    	gameView.corpses.get(i).countDown();
                    }catch(Exception e){
                    	
                    }
                }
                // for (Enemy eachEnemy : gameView.enemies) {
                // // 倒计时，放这吧。。
                // if (eachEnemy.countDown()) {
                // break;
                // }
                // }

                // for (Corpse eachCorpse: gameView.corpses) {
                // // 倒计时，放这吧。。
                // eachCorpse.countDown();
                // }

                // 时间完了以后的处理
                if (gameView.time <= 0) {
                    gameView.time = 0f;
                    gameView.nowState = GameView.STATE_END;
                    // gameView.mKnowledge.state = gameView.mKnowledge.GONE;
                    gameView.endView.bigestCombo = gameView.bigestCombo;
                    gameView.endView.mark = gameView.mark;
                    gameView.initBall();
                    gameView.pause();
                    gameView.top[10] = gameView.endView.mark;
                    gameView.enemies.removeAllElements();
                    Arrays.sort(gameView.top, new sortTop());
                }
                // } catch (Exception e) {
                // e.printStackTrace();
                // }
            }
            try {
                sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void addEnemy() {

        numRandom = Math.random();
        if (numRandom < displayProbBomb) {
            new Bomb(gameView);
        } else if (numRandom > displayProbGod) {
            new God(gameView);
        } else if (numRandom > displayGuard) {
            new Guard(gameView);
        } else if (numRandom > displaySpeed) {
            new Speed(gameView);
        } else if (numRandom > displayTime) {
            new TimeAdder(gameView);
        } else {
            new NormalEnemy(gameView);
        }

    }

    class sortTop implements Comparator<Integer> {

        public int compare(Integer lhs, Integer rhs) {
            // TODO Auto-generated method stub
            return rhs - lhs;
        }

    }

}
