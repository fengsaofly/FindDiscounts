package com.android.testbaiduapi;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 
 * @{# GuideActivity.java Create on 2013-5-2 下午10:59:08
 * 
 *     class desc: 引导界面
 * 
 *     <p>
 *     Copyright: Copyright(c) 2013
 *     </p>
 * @Version 1.0
 * @Author <a href="mailto:gaolei_xj@163.com">Leo</a>
 * 
 * 
 */
public class MainActivity extends Activity  {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		
		
		

//	
		
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			while(true){
//				try {
//					Thread.sleep(3*1000);
////					System.out.println("currentIndex:"+currentIndex);
//					currentIndex = (currentIndex + 1) % views.size(); 
//					
//					vp.setCurrentItem(currentIndex);
//					setCurrentDot(currentIndex);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			
//			}
//			
//		}


		
		
		
	}
//   public void setViews(List<View> views){
//	   this.views = new ArrayList<View>();
//	   for(View view:views){
//		   this.views.add(view);
//	   }
//   }
//	public void initVP(ViewPager vp,ViewPagerAdapter vpAdapter) {
//		this.vp = vp;
//		this.vpAdapter = vpAdapter;
//		LayoutInflater inflater = LayoutInflater.from(this);
//
//		views = new ArrayList<View>();
//		// 初始化引导图片列表
//		views.add(inflater.inflate(R.layout.what_new_one, null));
//		views.add(inflater.inflate(R.layout.what_new_two, null));
//		views.add(inflater.inflate(R.layout.what_new_three, null));
//		views.add(inflater.inflate(R.layout.what_new_four, null));
//
//		// 初始化Adapter
//		vpAdapter = new ViewPagerAdapter(views, this);
//		
//		vp = (ViewPager) findViewById(R.id.viewpager);
//		vp.setAdapter(vpAdapter);
//		// 绑定回调
//		vp.setOnPageChangeListener(this);
		
//	}


}
