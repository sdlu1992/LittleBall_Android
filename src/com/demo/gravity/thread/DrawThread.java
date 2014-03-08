package com.demo.gravity.thread;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import com.demo.gravity.view.MySurfaceView;

public class DrawThread extends Thread {

    SurfaceHolder mHolder;
    MySurfaceView gv;
    public boolean isGameOn = false;
    public boolean flag = false;
    public int gap = 10;
    public final String TAG = "DrawThread";
    public DrawThread(SurfaceHolder mHolder, MySurfaceView gv) {

    	Log.e(TAG, "ThreadCreate");
        this.mHolder = mHolder;
        this.gv = gv;
        this.flag = true;
    }

    public void run() {
    	Log.e(TAG, "ThreadStart");
        Canvas canvas;
        while (isGameOn) {
            canvas = null;
            try {
                canvas = mHolder.lockCanvas(null);
                synchronized (mHolder) {
                    gv.doDraw(canvas);
                }
                sleep(gap);
            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                if (canvas != null)
                    mHolder.unlockCanvasAndPost(canvas);
            }
        }

    }

}
