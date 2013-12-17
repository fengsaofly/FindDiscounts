package com.android.fragment;

import com.android.testbaiduapi.R;
import com.androidui.DiscountInfoActivity;
import com.androidui.LoginAndRegistActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class MyDiscountFragment extends Fragment{
	
	Boolean isLogin =false;
	
	RelativeLayout loginLayout = null;
	RelativeLayout registLayout = null;
	RelativeLayout myDiscountLoginAndRegistLayout = null;
	
	
	public static MyDiscountFragment newInstance(){
		MyDiscountFragment detail= new MyDiscountFragment();
		
  	    return detail;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	     super.onActivityCreated(savedInstanceState);
	     checkLogin();
	 
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.account, container,false);
		connectVC(view);
		initInterface();
		

	      return view;
	  } 
	public void connectVC(View view) {
		
		myDiscountLoginAndRegistLayout =  (RelativeLayout)view.findViewById(R.id.myDiscountLoginAndRegistLayout);
	}
	public void checkLogin() {
		if(false){
			//todo something
			isLogin = true;
		}
		else
		{
			isLogin = false;
		}
	}
	public void initInterface() {
		if(isLogin){
			myDiscountLoginAndRegistLayout.setVisibility(View.GONE);//不显示登陆，注册键
		}
		else
		{
			myDiscountLoginAndRegistLayout.setVisibility(View.VISIBLE);//显示登陆，注册键
		}
	}
	public void myDiscountOnClick(View v){
		switch(v.getId()){
		
			case R.id.login_btn:
				
				Intent intent = new Intent();
				intent.setClass(getActivity(),LoginAndRegistActivity.class);
				startActivity(intent);
				break;
		}
	}

}
