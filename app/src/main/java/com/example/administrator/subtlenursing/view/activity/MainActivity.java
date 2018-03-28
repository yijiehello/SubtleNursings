package com.example.administrator.subtlenursing.view.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.subtlenursing.R;
import com.example.administrator.subtlenursing.view.activity.fragment.ClassroomFragment;
import com.example.administrator.subtlenursing.view.activity.fragment.ForumFragment;
import com.example.administrator.subtlenursing.view.activity.fragment.MyFragment;
import com.example.administrator.subtlenursing.view.activity.fragment.WelfareFragment;
import com.example.administrator.subtlenursing.view.adapter.MyFragmentAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.forum_tv)
    TextView forum_tv;
    @BindView(R.id.classroom_tv)
    TextView classroom_tv;
    @BindView(R.id.welfare_tv)
    TextView welfare_tv;
    @BindView(R.id.my_tv)
    TextView my_tv;

    @BindView(R.id.forum_img)
    ImageView forum_img;
    @BindView(R.id.classroom_img)
    ImageView classroom_img;
    @BindView(R.id.welfare_img)
    ImageView welfare_img;
    @BindView(R.id.my_img)
    ImageView my_img;

    @BindView(R.id.forum_ll)
    RelativeLayout forum_ll;
    @BindView(R.id.classroom_ll)
    RelativeLayout classroom_ll;
    @BindView(R.id.welfare_ll)
    RelativeLayout welfare_ll;
    @BindView(R.id.my_ll)
    RelativeLayout my_ll;
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private MyFragmentAdapter adapter ;
    private int currentpageItem ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        ForumFragment forumFragment=new ForumFragment();
        ClassroomFragment classroomFragment=new ClassroomFragment();
        WelfareFragment welfareFragment=new WelfareFragment();
        MyFragment myFragment=new MyFragment();
        fragments.add(forumFragment);
        fragments.add(classroomFragment);
        fragments.add(welfareFragment);
        fragments.add(myFragment);
        adapter=new MyFragmentAdapter(getSupportFragmentManager(),fragments);
        vp.setAdapter(adapter);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                currentpageItem=position;

                if(currentpageItem==0){
                    initcolor();
                    forum_img.setImageResource(R.mipmap.forumgreen);
                    forum_tv.setTextColor(getResources().getColor(R.color.greenlight));

                }else
                if(currentpageItem==1){
                    initcolor();
                    classroom_img.setImageResource(R.mipmap.classroomgreen);
                    classroom_tv.setTextColor(getResources().getColor(R.color.greenlight));

                }else if(currentpageItem==2){
                    initcolor();
                    welfare_img.setImageResource(R.mipmap.welfaregreen);
                    welfare_tv.setTextColor(getResources().getColor(R.color.greenlight));

                }else if(currentpageItem==3){
                    initcolor();
                    my_img.setImageResource(R.mipmap.mygreen);
                    my_tv.setTextColor(getResources().getColor(R.color.greenlight));

                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        vp.setCurrentItem(0);

    }
    @OnClick({R.id.forum_ll,R.id.classroom_ll,R.id.welfare_ll,R.id.my_ll})
    public void onClick(View view){
        switch(view.getId()){
            case R.id.forum_ll:
                initcolor();
                forum_img.setImageResource(R.mipmap.forumgreen);
                forum_tv.setTextColor(getResources().getColor(R.color.greenlight));
                vp.setCurrentItem(0);
                break;
            case R.id.classroom_ll:
                initcolor();
                classroom_img.setImageResource(R.mipmap.classroomgreen);
                classroom_tv.setTextColor(getResources().getColor(R.color.greenlight));
                vp.setCurrentItem(1);
                break;
            case R.id.welfare_ll:
                initcolor();
                welfare_img.setImageResource(R.mipmap.welfaregreen);
                welfare_tv.setTextColor(getResources().getColor(R.color.greenlight));
                vp.setCurrentItem(2);
                break;
            case R.id.my_ll:
                initcolor();
                my_img.setImageResource(R.mipmap.mygreen);
                my_tv.setTextColor(getResources().getColor(R.color.greenlight));
                vp.setCurrentItem(3);
                break;
                default:
                    break;
        }
    }
    public void initcolor(){
        forum_img.setImageResource(R.mipmap.forumgrey);
        forum_tv.setTextColor(getResources().getColor(R.color.greytext));
        classroom_img.setImageResource(R.mipmap.classroomgrey);
        classroom_tv.setTextColor(getResources().getColor(R.color.greytext));
        welfare_img.setImageResource(R.mipmap.welfaregrey);
        welfare_tv.setTextColor(getResources().getColor(R.color.greytext));
        my_img.setImageResource(R.mipmap.mygrey);
        my_tv.setTextColor(getResources().getColor(R.color.greytext));
    }
}
