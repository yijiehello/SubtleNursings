package com.example.administrator.subtlenursing.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.subtlenursing.R;
import com.example.administrator.subtlenursing.controller.ActivityCollector;


/**
 * Created by Monster.chen
 */

public class BaseActivity extends AppCompatActivity {

    ActionBar actionBar; //声明ActionBar
    public Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //隐去标题栏（应用程序的名字）
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        context = getApplicationContext();
        actionBar = getSupportActionBar(); //得到ActionBar
        actionBar.hide(); //隐藏ActionBar
        Translucent();
        ActivityCollector.addActivity(this);
    }
    private void Translucent() {
        // 透明状态栏
        getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
//        getWindow().addFlags(
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }


}
