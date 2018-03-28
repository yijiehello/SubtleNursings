package com.example.administrator.subtlenursing.view.activity.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.subtlenursing.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-03-24.
 */

public class MyFragment extends Fragment {
    View view;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_fragment, null);
        ButterKnife.bind(this, view);
        return view;

    }
}
