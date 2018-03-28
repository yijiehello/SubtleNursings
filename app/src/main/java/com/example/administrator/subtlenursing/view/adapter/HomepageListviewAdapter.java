package com.example.administrator.subtlenursing.view.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.administrator.subtlenursing.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Monster.chen
 */

public class HomepageListviewAdapter extends BaseAdapter {

    //初始化数据源
    private LayoutInflater ml ;
    private List<Map<String,Object>> datas ;
    private Context c;


    public HomepageListviewAdapter(List<Map<String, Object>> datas, Context c) {
        this.datas = datas;
        this.c = c;
        ml = LayoutInflater.from(c);
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if(view == null){
            view = ml.inflate(R.layout.homepage_listview_item,null);
            holder=new ViewHolder();
            holder.classify_tv = (TextView) view.findViewById(R.id.classify_tv);
            holder.title_tv = (TextView) view.findViewById(R.id.title_tv);
            holder.content_tv = (TextView) view.findViewById(R.id.content_tv);
            holder.name_tv = (TextView) view.findViewById(R.id.name_tv);
            holder.comment_tv = (TextView) view.findViewById(R.id.comment_tv);
            holder.like_tv = (TextView) view.findViewById(R.id.like_tv);
            holder.user_img= (ImageView) view.findViewById(R.id.user_img);
            view.setTag(holder);
        }else {

            holder = (ViewHolder) view.getTag();
        }

        holder.title_tv.setText(datas.get(i).get("title").toString());
        holder.user_img.setImageResource(Integer.parseInt(datas.get(i).get("img").toString()));

        return view;
    }
    class ViewHolder{
        public TextView classify_tv,title_tv,content_tv,name_tv,comment_tv,like_tv;
        ImageView user_img;
    }
}
