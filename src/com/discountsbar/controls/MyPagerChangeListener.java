package com.discountsbar.controls;

import java.util.ArrayList;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;

public class MyPagerChangeListener implements OnPageChangeListener {

	ArrayList<ImageView> dots =new ArrayList<ImageView>();
	@Override
	public void onPageSelected(int position) {
//		Toast.makeText(getActivity(), "position=" + position, 1).show();
		setCurrentDot(position);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}
	private void setCurrentDot(int position) {
		
		for(int i=0;i<dots.size();i++){
			dots.get(i).setEnabled(true);
		}

		dots.get(position).setEnabled(false);
//		holder.dots[currentIndex].setEnabled(true);

//		currentIndex = position;
		
	}
	public void setDots(ImageView[] dots){
		for(ImageView dot : dots){
			this.dots.add(dot);
		}
	}
}