package com.demo.gravity.utils;

import com.demo.gravity.MainActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class ActionListener implements SensorEventListener {

    private MainActivity father;
    int n = 10;

    public ActionListener(MainActivity context) {
        this.father = context;
        if (android.os.Build.VERSION.SDK_INT > 8) {
            n = 10;
        } else {
            n = 50;
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        // 因为是横屏，X与Y反过来了

        father.gv.hero.a_Y = event.values[SensorManager.DATA_X];
        father.gv.hero.a_X = event.values[SensorManager.DATA_Y];
        father.gv.hero.a_Z = event.values[SensorManager.DATA_Z];
        father.gv.hero.v_X += father.gv.hero.a_X/n;
        father.gv.hero.v_Y += father.gv.hero.a_Y/n;
        father.gv.hero.X += father.gv.hero.v_X+father.gv.hero.a_X;
        father.gv.hero.Y += father.gv.hero.v_Y+father.gv.hero.a_Y;
//        Log.e("ActionListener", "x:"+father.gv.hero.X+"______y:"+father.gv.hero.Y);
    }

}
