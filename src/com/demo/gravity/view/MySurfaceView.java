package com.demo.gravity.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

import com.demo.gravity.MainActivity;

public class MySurfaceView extends SurfaceView{

    public MySurfaceView(Context context){
        super(context);
    }
    public MySurfaceView(MainActivity context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
    
    public void doDraw(Canvas canvas){
        
    }
    
    public void doDraw(Canvas canvas,Paint mPaint){
        
    }

}
