package com.demo.gravity.thread;

import android.util.Log;

import com.demo.gravity.object.Corpse;
import com.demo.gravity.view.GameView;

public class CorpseThread extends Thread {
    GameView gameView;
    boolean flag = false;
    public boolean isGameOn = false;
    private String TAG = "CorpseThread";
    public CorpseThread(GameView father) {
    	Log.e(TAG, "ThreadCreate");
        this.gameView = father;
        this.flag = true;
    }

    public void run() {

    	Log.e(TAG, "ThreadStart");
        while (flag) {
            while (isGameOn) {

                for (int i = 0; i < gameView.corpses.size(); i++) {
                    // 避免数组越界
                    if (i > gameView.corpses.size() - 1) {
                        i = 0;
                        // break;
                    }
                    Corpse eachCorpse = gameView.corpses.get(i);
                    if (eachCorpse != null && eachCorpse.dealWith()) {
                        gameView.corpses.remove(eachCorpse);
                        eachCorpse = null;
                        break;
                    }

                }
            }
            try {
                sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
