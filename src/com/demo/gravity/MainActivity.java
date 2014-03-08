package com.demo.gravity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.demo.gravity.element.Bomb;
import com.demo.gravity.element.Enemy;
import com.demo.gravity.element.God;
import com.demo.gravity.element.Guard;
import com.demo.gravity.element.NormalEnemy;
import com.demo.gravity.element.Speed;
import com.demo.gravity.element.TimeAdder;
import com.demo.gravity.utils.ActionListener;
import com.demo.gravity.view.GameView;
import com.demo.gravity.view.WelcomeView;

public class MainActivity extends Activity {

    private SensorManager mManager = null;
    private Sensor mSensor = null;
    private ActionListener mAction;
    public GameView gv;
    public WelcomeView wv;
    WindowManager manager;
    Display display;
    public static float screenWidth = 0;
    public static float screenHeight = 0;
    public static float widthScale = 1;
    public static float heightScale = 1;

    PowerManager powerManager = null;
    WakeLock wakeLock = null;
    AudioManager audioManager;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getScreenInfo();
        gv = new GameView(this);
        // setContentView(gv);
      //  wv = new WelcomeView(this);
        setContentView(gv);
        mManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        mSensor = mManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        audioManager = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
        mAction = new ActionListener(this);
        getInfo();
        setPowerManager();

    }

    private void setPowerManager() {
        // TODO Auto-generated method stub
        this.powerManager = (PowerManager) this
                .getSystemService(Context.POWER_SERVICE);
        this.wakeLock = this.powerManager.newWakeLock(
                PowerManager.FULL_WAKE_LOCK, "My Lock");
    }

    public void getInfo() {
        // TODO Auto-generated method stub
        SharedPreferences info = getSharedPreferences("info", 0);
        if (0 != info.getInt("ballSize", 0)) {
            
            for (int i = 0,len = 11; i < len; i++) {
                gv.top[i] = info.getInt("top" + i, 0);
//                Log.e("DemoGravityActivity||top" + i, gv.top[i] + "");
            }
            if(info.getInt("nowState", 0) == 0 || info.getInt("nowState", 0) == 2){
                return;
            }
            gv.hero.ballSize = info.getInt("ballSize", 0);
            gv.hero.X = info.getFloat("ballX", 0);
            gv.hero.Y = info.getFloat("ballY", 0);
            gv.mark = info.getInt("mark", 0);
            gv.combo = info.getInt("combo", 0);
            gv.bigestCombo = info.getInt("bigestCombo", 0);
            gv.time = info.getFloat("time", 60);
            gv.hero.isGuarding = info.getBoolean("isGuarding", false);
            gv.hero.guardBeginTime = info.getFloat("guardBeginTime", 0);

            int length = info.getInt("enemyAmount", 0);
            for (int i = 0; i < length; i++) {
                int type = info.getInt("type" + i, 0);
                Enemy enemy = null;
                switch (type) {
                case GameView.TYPE_NORMAL:
                    enemy = new NormalEnemy(gv);
                    break;
                case GameView.TYPE_GOD:
                    enemy = new God(gv);
                    break;
                case GameView.TYPE_BOMB:
                    enemy = new Bomb(gv);
                    break;
                case GameView.TYPE_GUARD:
                    enemy = new Guard(gv);
                    break;
                case GameView.TYPE_SPEED:
                    enemy = new Speed(gv);
                    break;
                case GameView.TYPE_TIME:
                    enemy = new TimeAdder(gv);
                default:
                    break;
                }
                enemy.type = info.getInt("type" + i, 0);
                enemy.X = info.getFloat("X" + i, 0);
                enemy.Y = info.getFloat("Y" + i, 0);
                enemy.radius = info.getFloat("radius" + i, 0);
                enemy.remainingTime = info.getFloat("remainingTime" + i, 0);
                enemy.alpha = info.getInt("alpha" + i, 0);
                enemy.velocity = info.getFloat("velocity" + i, 0);
                enemy.angle = info.getFloat("angle" + i, 0);
                // gv.enemies.add(enemy);
            }
            gv.at.isGameOn = false;
            gv.gt.isGameOn = false;
            gv.isPause = true;
            gv.father.endSensorManager();
        }

    }

    public void saveInfo() {
        // TODO Auto-generated method stub
        SharedPreferences info = getSharedPreferences("info", 0);
        Editor editor = info.edit();
        editor.clear();
        try {
            editor.putInt("nowState", gv.nowState);
            editor.putFloat("ballX", gv.hero.X);
            editor.putFloat("ballY", gv.hero.Y);
            editor.putInt("ballSize", gv.hero.ballSize);
            editor.putFloat("currentVX", gv.hero.v_X);
            editor.putFloat("currentVY", gv.hero.v_Y);
            editor.putInt("enemyAmount", gv.enemies.size());
            editor.putInt("mark", gv.mark);
            editor.putInt("combo", gv.combo);
            editor.putInt("bigest", gv.bigestCombo);
            editor.putFloat("time", gv.time);
            editor.putBoolean("isGuarding", gv.hero.isGuarding);
            editor.putFloat("guardBeginTime", gv.hero.guardBeginTime);
            for (int i = 0,len = gv.top.length; i < len; i++) {
                editor.putInt("top" + i, gv.top[i]);
//                Log.e("DemoGravityActivity||top" + i, gv.top[i] + "");
            }
            int i = 0;
            for (Enemy eachEnemy : gv.enemies) {
                editor.putFloat("X" + i, eachEnemy.X);
                editor.putFloat("Y" + i, eachEnemy.Y);
                editor.putInt("type" + i, eachEnemy.type);
                editor.putFloat("radius" + i, eachEnemy.radius);
                editor.putFloat("remainingTime" + i, eachEnemy.remainingTime);
                editor.putInt("alpha" + i, eachEnemy.alpha);
                editor.putFloat("velocity" + i, eachEnemy.velocity);
                editor.putFloat("angle" + i, eachEnemy.angle);
                i++;
            }
        } catch (Exception e) {

        } finally {
            editor.commit();
        }
    }

    public void startSensorManager() {
        // mManager.registerListener(mAction, mSensor,
        // SensorManager.SENSOR_DELAY_FASTEST);
        if (android.os.Build.VERSION.SDK_INT > 8) {
            mManager.registerListener(mAction, mSensor,
                    SensorManager.SENSOR_DELAY_GAME);
        } else {
            mManager.registerListener(mAction, mSensor,
                    SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    public void endSensorManager() {
        mManager.unregisterListener(mAction);
    }

    private void getScreenInfo() {
        manager = getWindowManager();
        display = manager.getDefaultDisplay();
        screenWidth = display.getWidth();
        screenHeight = display.getHeight();
        widthScale = screenWidth / 800f;
        heightScale = screenHeight / 480f;
    }

    @Override
    public void onPause() {

        gv.pause();
        this.wakeLock.release();
        super.onPause();
    }

    @Override
    public void onResume() {

        super.onResume();
        this.wakeLock.acquire();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        gv.at.isGameOn = false;
        gv.dt.isGameOn = false;
        gv.gt.isGameOn = false;
        gv.music.soundPool.release();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
        case KeyEvent.KEYCODE_BACK:
            if (!(gv.nowState == GameView.STATE_GAME) || gv.isPause) {
                // 如果现在正在暂停界面则
                createExitDialog();
            } else if (!gv.isPause) {
                gv.pause();
            }
            break;
        case KeyEvent.KEYCODE_VOLUME_UP:
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND
                            | AudioManager.FLAG_SHOW_UI);
            return true;
        case KeyEvent.KEYCODE_VOLUME_DOWN:
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND
                            | AudioManager.FLAG_SHOW_UI);
            return true;
        default:
            break;
        }

        return true;
    }

    private void createExitDialog() {
        // TODO Auto-generated method stub
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(getString(R.string.exit))
                .setMessage(getString(R.string.isexit))
                .setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                // 按钮事件
                                gv.pause();
                                finish();
                            }
                        })
                .setNegativeButton(getString(R.string.dismiss),
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                    int which) {
                                // TODO Auto-generated method stub

                            }
                        }).show();
    }

}