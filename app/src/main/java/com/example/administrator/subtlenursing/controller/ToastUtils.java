package com.example.administrator.subtlenursing.controller;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Monster.chen
 */

public class ToastUtils {
    private Context context;
    /** toast 对象 */
    private Toast toast;
    /** 是否在显�?*/
    private boolean isshow = false;

    public ToastUtils(Context context) {
        this.context = context;

    }

    private static ToastUtils toastUtils;

    public static ToastUtils getUtils(Context context) {
        if (toastUtils == null) {
            toastUtils = new ToastUtils(context);
        }
        return toastUtils;
    }

    /** 判断如果toast是显示状态，就取消掉，显示下�?�� */
    public void show(String str) {
        if (isshow == true) {
            toast.cancel();
            isshow = !isshow;
        }
        toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        toast.show();
        isshow = !isshow;
    }
    public static void ToastUtils(Context context, String str){
        Toast toast= Toast.makeText(context,str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,10);
        toast.show();
    }

}
