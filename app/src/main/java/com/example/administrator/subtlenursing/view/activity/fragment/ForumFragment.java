package com.example.administrator.subtlenursing.view.activity.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.subtlenursing.R;
import com.example.administrator.subtlenursing.model.customview.XListView;
import com.example.administrator.subtlenursing.view.activity.SlideShowViewHome;
import com.example.administrator.subtlenursing.view.activity.VideoViewActivity;
import com.example.administrator.subtlenursing.view.adapter.HomepageListviewAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018-03-24.
 */

public class ForumFragment extends Fragment{
    View view;
    @BindView(R.id.home_view)
    View home_view;
    @BindView(R.id.nurse_view)
    View nurse_view;
    @BindView(R.id.recovery_view)
    View recovery_view;
    @BindView(R.id.emotion_view)
    View emotion_view;

    @BindView(R.id.home_re)
    RelativeLayout home_re;
    @BindView(R.id.nurse_re)
    RelativeLayout nurse_re;
    @BindView(R.id.recovery_re)
    RelativeLayout recovery_re;
    @BindView(R.id.emotion_re)
    RelativeLayout emotion_re;
    @BindView(R.id.slider_re)
    RelativeLayout slider_re;

    @BindView(R.id.home_tv)
    TextView home_tv;
    @BindView(R.id.nurse_tv)
    TextView nurse_tv;
    @BindView(R.id.recovery_tv)
    TextView recovery_tv;
    @BindView(R.id.emotion_tv)
    TextView emotion_tv;

    @BindView(R.id.info_img)
    ImageView info_img;
    @BindView(R.id.publish_img)
    ImageView publish_img;


    @BindView(R.id.list_view)
    XListView listview;
    private HomepageListviewAdapter listviewAdapter;
    private ArrayList<Map<String,Object>> data=new ArrayList<>();
    private int imgs[]={R.mipmap.debugimg,R.mipmap.womenuser,R.mipmap.debugimg,R.mipmap.womenuser,R.mipmap.debugimg,R.mipmap.womenuser};
    private String title[]={"话题一","话题二","话题三","话题四","话题五","话题六"};
    private String strTime;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forum_fragment, null);
        ButterKnife.bind(this, view);
        init();
        return view;

    }

    private void init() {
        //添加SlideShowView
        SlideShowViewHome slideShowView = new SlideShowViewHome(getActivity());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        slideShowView.setLayoutParams(params);
        slider_re.addView(slideShowView);

        data.clear();
        getdata();
        listview.setPullLoadEnable(false);//隐藏上拉布局
        listviewAdapter=new HomepageListviewAdapter(data,getActivity());
        listview.setAdapter(listviewAdapter);
        listview.setPullRefreshEnable(true);
        listview.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                onLoad();
            }
            @Override
            public void onLoadMore() {
                //上拉加载更多数据
                 onLoadFinish();
            }
        });

    }
    //正在加载时显示的内容
    private void onLoad(){
        SimpleDateFormat formatter = new SimpleDateFormat ("MM月dd日 HH:mm ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        strTime = formatter.format(curDate);
        listview.stopRefresh();
        listview.stopLoadMore();
        listview.setRefreshTime(strTime);
    }
    private void onLoadFinish(){
        listview.stopRefresh();
        listview.stopLoadMore();
    }
    @OnClick({R.id.home_re,R.id.nurse_re,R.id.recovery_re,R.id.emotion_re,R.id.info_img,R.id.publish_img})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.home_re:
                initcolor();
                home_tv.setTextColor(getResources().getColor(R.color.greenlight));
                home_view.setVisibility(View.VISIBLE);
                break;
            case R.id.nurse_re:
                initcolor();
                nurse_tv.setTextColor(getResources().getColor(R.color.greenlight));
                nurse_view.setVisibility(View.VISIBLE);
                break;
            case R.id.recovery_re:
                initcolor();
                recovery_tv.setTextColor(getResources().getColor(R.color.greenlight));
                recovery_view.setVisibility(View.VISIBLE);
                break;
            case R.id.emotion_re:
                initcolor();
                emotion_tv.setTextColor(getResources().getColor(R.color.greenlight));
                emotion_view.setVisibility(View.VISIBLE);
                break;
            case R.id.info_img:
                startActivity(new Intent(getActivity(), VideoViewActivity.class));
                break;
            case R.id.publish_img:

                break;
            default:
                break;
        }

    }
    public void initcolor(){
        home_view.setVisibility(View.GONE);
        home_tv.setTextColor(getResources().getColor(R.color.greytext));
        nurse_view.setVisibility(View.GONE);
        nurse_tv.setTextColor(getResources().getColor(R.color.greytext));
        recovery_view.setVisibility(View.GONE);
        recovery_tv.setTextColor(getResources().getColor(R.color.greytext));
        emotion_view.setVisibility(View.GONE);
        emotion_tv.setTextColor(getResources().getColor(R.color.greytext));
    }
    private ArrayList<Map<String,Object>> getdata() {
        for(int i=0;i<imgs.length;i++){
            Map<String, Object> datas = new HashMap<String,Object>();
            datas.put("title",title[i]);
            datas.put("img",imgs[i]);
            data.add(datas);
        }
        return data;

    }
}
