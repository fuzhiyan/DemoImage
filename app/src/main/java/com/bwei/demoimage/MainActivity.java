package com.bwei.demoimage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.lang.reflect.Method;

public class MainActivity extends Activity implements View.OnTouchListener{
    //放大缩小
    Matrix matrix=new Matrix();
    Matrix savedMatrix=new Matrix();

    PointF start=new PointF();
    PointF mid=new PointF();
    float oldDist;

    private ImageView myImageView;

    //模式
    static final int NONE=0;
    static final int DRAG=1;
    static final int ZOOM=2;
    int mode=NONE;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myImageView=(ImageView) findViewById(R.id.image);
        myImageView.setOnTouchListener(this);
        Button but= (Button) findViewById(R.id.but);
        Button but1= (Button) findViewById(R.id.but1);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ThreeActivity.class);
                startActivity(intent);
            }
        });
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(MainActivity.this,TwoActivity.class);
                startActivity(in);
            }
        });

    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView myImageView=(ImageView) v;
        switch(event.getAction()&MotionEvent.ACTION_MASK){
            //设置拖拉模式
            case MotionEvent.ACTION_DOWN:
                matrix.set(myImageView.getImageMatrix());
                savedMatrix.set(matrix);
                start.set(event.getX(),event.getY());
                mode=DRAG;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode=NONE;
                break;

            //设置多点触摸模式
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist=spacing(event);
                if(oldDist>10f){
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode=ZOOM;
                }
                break;
            //若为DRAG模式，则点击移动图片
            case MotionEvent.ACTION_MOVE:
                if(mode==DRAG){
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX()-start.x,event.getY()-start.y);
                }
                //若为ZOOM模式，则点击触摸缩放
                else if(mode==ZOOM){
                    float newDist=spacing(event);
                    if(newDist>10f){
                        matrix.set(savedMatrix);
                        float scale=newDist/oldDist;
                        //设置硕放比例和图片的中点位置
                        matrix.postScale(scale,scale, mid.x,mid.y);
                    }
                }
                break;
        }
        myImageView.setImageMatrix(matrix);
        return true;
    }
    //计算移动距离
    private float spacing(MotionEvent event){
        float x=event.getX(0)-event.getX(1);
        float y=event.getY(0)-event.getY(1);

        return (float) Math.sqrt(x*x+y*y);
    }
    //计算中点位置
    private void midPoint(PointF point,MotionEvent event){
        float x=event.getX(0)+event.getX(1);
        float y=event.getY(0)+event.getY(1);
        point.set(x/2,y/2);
    }
    }

