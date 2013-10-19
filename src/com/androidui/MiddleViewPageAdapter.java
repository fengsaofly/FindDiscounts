package com.androidui;

import java.util.List;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.android.testbaiduapi.R;
import com.android.testbaiduapi.ViewPagerAdapter;

public class MiddleViewPageAdapter extends ViewPagerAdapter {

	public MiddleViewPageAdapter(List<View> views, Activity activity) {
		super(views, activity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object instantiateItem(View arg0, int arg1) {
		// TODO Auto-generated method stub
		((ViewPager) arg0).addView(views.get(arg1), 0);

		return views.get(arg1);
	}
	
	

}
