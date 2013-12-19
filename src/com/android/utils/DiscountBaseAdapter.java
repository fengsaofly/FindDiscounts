package com.android.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.android.testbaiduapi.R;
import com.androidui.MiddleViewPageAdapter;

public class DiscountBaseAdapter extends BaseAdapter {
	
	Context context = null;
	Activity activity = null;
	List<Map<String, Object>> list=null;
	ArrayList<String> keys = new ArrayList<String>();
	ArrayList<Integer> ids = new ArrayList<Integer>();
	public DiscountBaseAdapter(Context	context) {
		// TODO Auto-generated constructor stub
		super();
		this.context = context;
	}
	public DiscountBaseAdapter(Context	context,Activity act,List<Map<String, Object>> list,String[] strKeys,int[] intIds) {
		// TODO Auto-generated constructor stub
		super();
		this.context = context;
		this.activity = act;
		this.list = list;
		for(String key :strKeys){
			keys.add(key);
		}
		
		for(int id:intIds){
			ids.add(id);
		}
		
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
        ViewHolder holder;
        if (convertView == null) {
            if (getItemViewType(position) == 0) {
                convertView = LayoutInflater.from(context).inflate(R.layout.viewpager_layout, null);
		    } 
            else {
		        convertView = LayoutInflater.from(context).inflate(R.layout.friendlink_item, null);
		    }
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.discount_name);
            holder.title.setText(list.get(position).get("discount_name").toString());
            holder.content = (TextView) convertView.findViewById(R.id.discount_description);
            holder.content.setText(list.get(position).get("discount_description").toString());
            holder.imageView = (ImageView) convertView.findViewById(R.id.app_icon);
            holder.imageView.setImageDrawable(activity.getResources().getDrawable((Integer) list.get(position).get("app_icon")));
            if (getItemViewType(position) == 0) {
                //holder.imageView = (ImageView) convertView.findViewById(R.id.about_physique_info);
                holder.viewPager=(ViewPager)convertView.findViewById(R.id.middleviewpager);
                ArrayList<View> views = new ArrayList<View>();
          		
          		
                LayoutInflater inflater = LayoutInflater.from(context);
           			views.add(inflater.inflate(R.layout.what_new_one, null));
           			views.add(inflater.inflate(R.layout.what_new_two, null));
           			views.add(inflater.inflate(R.layout.what_new_three, null));
           			views.add(inflater.inflate(R.layout.what_new_four, null));
           	

           		// 初始化Adapter
           		
           		MiddleViewPageAdapter vpAdapter = new MiddleViewPageAdapter(views, activity);
           		
           		
           		holder.viewPager.setAdapter(vpAdapter);
//                holder.viewPager.setAdapter(arg0);
                //holder.imageView.setOnClickListener(onClickListener);
            }
            convertView.setTag(holder);
		}
        else {
		    holder = (ViewHolder) convertView.getTag();
		}
        
		return convertView;
			
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}
}

	
