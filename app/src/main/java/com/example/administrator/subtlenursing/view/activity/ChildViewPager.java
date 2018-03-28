package com.example.administrator.subtlenursing.view.activity;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by yiheyu on 2016/11/15.
 */

public class ChildViewPager extends ViewPager {
    //触摸时按下的点
    PointF downP = new PointF();
    //触摸时当前的点
    PointF curP = new PointF();
    OnSingleTouchListener onSingleTouchListener;
    public ChildViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public ChildViewPager(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        // TODO Auto-generated method stub
        //当拦截触摸事件到达此位置的时候，返回true，
        //说明将onTouch拦截在此控件，进而执行此控件的onTouchEvent
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        // TODO Auto-generated method stub
        //每次进行onTouch事件都记录当前的按下的坐标
        curP.x = arg0.getX();
        curP.y = arg0.getY();

        if(arg0.getAction() == MotionEvent.ACTION_DOWN){

            //记录按下时候的坐标
            downP.x = arg0.getX();
            downP.y = arg0.getY();
            //通知父ViewPager现在进行的是本控件的操作，不要对此操作进行干扰
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        if(arg0.getAction() == MotionEvent.ACTION_MOVE){

            //通知父ViewPager现在进行的是本控件的操作，不要对此操作进行干扰
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        if(arg0.getAction() == MotionEvent.ACTION_UP){

            //在up时判断是否按下和松手的坐标为一个点，如果是一个点，将执行点击事件
            if(downP.x==curP.x && downP.y==curP.y){
                onSingleTouch();
                return true;
            }
        }

        return super.onTouchEvent(arg0);
    }

    /**
     * 单击
     */
    public void onSingleTouch() {
        if (onSingleTouchListener!= null) {
            onSingleTouchListener.onSingleTouch();
        }
    }

    /**
     * 创建点击事件接口
     */
    public interface OnSingleTouchListener {
        void onSingleTouch();
    }
}
