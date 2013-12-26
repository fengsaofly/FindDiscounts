package com.android.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.testbaiduapi.R;
import com.androidui.MiddleViewPageAdapter;
import com.discountsbar.controls.MyPagerChangeListener;

public class DiscountBaseAdapter extends SimpleAdapter {

	Context context = null;
	Activity activity = null;
	List<Map<String, Object>> list = null;
	ArrayList<String> keys = new ArrayList<String>();
	ArrayList<Integer> ids = new ArrayList<Integer>();
	int field;// 表示针对哪个页面进行赋值
	ViewHolder holder = new ViewHolder();;
	ViewPager viewPager = null;// 用于保存viewpager，以便切换图片
	Handler handler = null;
	ArrayList<View> views = null;
	int currentIndex = 0;

	public DiscountBaseAdapter(Context context, Activity act,
			List<Map<String, Object>> list, int type) {
		super(context, list, type, null, null);
		this.context = context;
		this.activity = act;
		this.list = list;
		this.field = type;
	}

	public DiscountBaseAdapter(Context context, Activity act,
			List<Map<String, Object>> list, int type, Handler handler) {
		// TODO Auto-generated constructor stub
		super(context, list, type, null, null);
		this.context = context;
		this.activity = act;
		this.list = list;
		this.handler = handler;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0)
			return 0;
		else
			return 1;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
//			if (getItemViewType(position) == 0) {
//				
//			} else {
//				
//			}

			if (field == GlobalParameter.FIRST_PAGE) {
				if (getItemViewType(position) == 0) {
					convertView = LayoutInflater.from(context).inflate(
							R.layout.viewpager_layout, null);

					// holder.imageView = (ImageView)
					// convertView.findViewById(R.id.about_physique_info);
					holder.viewPager = (ViewPager) convertView
							.findViewById(R.id.middleviewpager);

					holder.ll = (LinearLayout) convertView
							.findViewById(R.id.ll);
					views = new ArrayList<View>();

					LayoutInflater inflater = LayoutInflater.from(context);
					views.add(inflater.inflate(R.layout.what_new_one, null));
					views.add(inflater.inflate(R.layout.what_new_two, null));
					views.add(inflater.inflate(R.layout.what_new_three, null));
					views.add(inflater.inflate(R.layout.what_new_four, null));

					// 初始化Adapter

					MiddleViewPageAdapter vpAdapter = new MiddleViewPageAdapter(
							views, activity);

					holder.viewPager.setAdapter(vpAdapter);
					initDots();
					MyPagerChangeListener lis = new MyPagerChangeListener();
					lis.setDots(holder.dots);
					holder.viewPager.setOnPageChangeListener(lis);
					viewPager = holder.viewPager;// 保存viewPager
//					Message msg = handler.obtainMessage(3, viewPager);
//					handler.sendMessage(msg);

					// this.setViewPager(holder.viewPager);
					// holder.viewPager.setAdapter(arg0);
					// holder.imageView.setOnClickListener(onClickListener);
				} else {
					convertView = LayoutInflater.from(context).inflate(
							R.layout.friendlink_item, null);
					// holder = new ViewHolder();
					holder.title = (TextView) convertView
							.findViewById(R.id.discount_name);
					holder.title.setText(list.get(position - 1)
							.get("discount_name").toString());
					holder.content = (TextView) convertView
							.findViewById(R.id.discount_description);
					holder.content.setText(list.get(position - 1)
							.get("discount_description").toString());
					holder.imageView = (ImageView) convertView
							.findViewById(R.id.app_icon);
					holder.imageView.setImageDrawable(activity.getResources()
							.getDrawable(
									(Integer) list.get(position - 1).get(
											"app_icon")));

				}
			} else if (field == GlobalParameter.SECOND_PAGE) {
//				if (getItemViewType(position) < 1) {
//					convertView = LayoutInflater.from(context).inflate(
//							R.layout.viewpager_layout, null);
//					// holder.imageView = (ImageView)
//					// convertView.findViewById(R.id.about_physique_info);
//					holder.viewPager = (ViewPager) convertView
//							.findViewById(R.id.middleviewpager);
//
//					holder.ll = (LinearLayout) convertView
//							.findViewById(R.id.ll);
//					views = new ArrayList<View>();
//
//					LayoutInflater inflater = LayoutInflater.from(context);
//					views.add(inflater.inflate(R.layout.what_new_one, null));
//					views.add(inflater.inflate(R.layout.what_new_two, null));
//					views.add(inflater.inflate(R.layout.what_new_three, null));
//					views.add(inflater.inflate(R.layout.what_new_four, null));
//
//					// 初始化Adapter
//
//					MiddleViewPageAdapter vpAdapter = new MiddleViewPageAdapter(
//							views, activity);
//
//					holder.viewPager.setAdapter(vpAdapter);
//					initDots();
//					MyPagerChangeListener lis = new MyPagerChangeListener();
//					lis.setDots(holder.dots);
//					holder.viewPager.setOnPageChangeListener(lis);
//					viewPager = holder.viewPager;// 保存viewPager
//					Message msg = handler.obtainMessage(3, viewPager);
//					handler.sendMessage(msg);

					// this.setViewPager(holder.viewPager);
					// holder.viewPager.setAdapter(arg0);
					// holder.imageView.setOnClickListener(onClickListener);
//				} else {
					convertView = LayoutInflater.from(context).inflate(
							R.layout.nearby_grid_good_item, null);
					// holder = new ViewHolder();
					holder.title = (TextView) convertView
							.findViewById(R.id.grid_item_discount_type);
					holder.title.setText(list.get(position)
							.get("ItemText").toString());
					holder.content = (TextView) convertView
							.findViewById(R.id.grid_item_shop_name);
					holder.content.setText(list.get(position)
							.get("ItemText2").toString());
					holder.imageView = (ImageView) convertView
							.findViewById(R.id.grid_item_image);
					//holder.imageView.setImageBitmap(null);
//					(activity.getResources()
//							.getDrawable((Integer) list.get(position - 1).get("ItemImage")));

				}
//			}

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		return convertView;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(field == GlobalParameter.FIRST_PAGE)
		return list.size() + 1;
		else
			return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	public ViewPager getViewPager() {
		return holder.viewPager;
	}

	public void setViewPager(ViewPager vp) {
		this.viewPager = vp;
	}

	private void initDots() {

		holder.dots = new ImageView[views.size()];

		// 循环取得小点图片
		for (int i = 0; i < views.size(); i++) {
			holder.dots[i] = (ImageView) holder.ll.getChildAt(i);
			holder.dots[i].setEnabled(true);// 都设为灰色
		}

		currentIndex = 0;
		holder.dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
	}

}
