package com.android.fragment;

import java.util.ArrayList;
import java.util.Map;

import com.android.fragment.NewDiscountFragment.MyLocationListenner;
import com.android.testbaiduapi.R;
import com.android.testbaiduapi.m_Shop;
import com.androidui.DiscountInfoActivity;
import com.androidui.MiddleViewPageAdapter;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.LocationData;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class NearByFragment extends Fragment{
	
	private ViewPager vp =null ;
	private MiddleViewPageAdapter vpAdapter=null;
	public static NearByFragment newInstance(){
		NearByFragment detail= new NearByFragment();
		
  	    return detail;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	     super.onActivityCreated(savedInstanceState);
	     
	     
		
	     
	 
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.what_new_three, container,false);
		
		

	      return view;
	  } 

}
