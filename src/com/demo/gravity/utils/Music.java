package com.demo.gravity.utils;

import com.demo.gravity.MainActivity;
import com.demo.gravity.R;
import com.demo.gravity.R.raw;
import com.demo.gravity.view.GameView;

import android.media.AudioManager;
import android.media.SoundPool;
import android.util.SparseIntArray;

public class Music {

    MainActivity father;
    public SoundPool soundPool;
    private SparseIntArray soundArray;
    
    public Music(MainActivity context){
        father = context;
        initSound();
        initMusic();
    }
    
    private void initSound(){
        soundPool = new SoundPool(10,AudioManager.STREAM_MUSIC, 100);
        soundArray= new SparseIntArray();
        //soundMap.put(R.raw.normal, soundPool.load(father, R.raw.normal,1));
        //soundMap.put(R.raw.bomb, soundPool.load(father, R.raw.bomb,1));
        soundArray.put(GameView.TYPE_NORMAL, soundPool.load(father, R.raw.normal,1));
        soundArray.put(GameView.TYPE_BOMB, soundPool.load(father, R.raw.bomb,1));
    }
    
    private void initMusic(){
        
    }
    
    public void playSound(int type){
        Integer soundId = soundArray.get(type);
        soundPool.play(soundId, 1, 1, 1, 0, 1);
    }
}
