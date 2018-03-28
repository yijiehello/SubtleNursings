package com.example.administrator.subtlenursing.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Monster.chen
 */
public class MyFragmentAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> fragments ;
	
	public MyFragmentAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
		super(fm);
		this.fragments = fragments ;
		
	}

	@Override
	public Fragment getItem(int arg0) {
		
		return fragments == null?null : fragments.get(arg0);
	}

	@Override
	public int getCount() {
		return fragments == null ? 0 : fragments.size();
	}

}
