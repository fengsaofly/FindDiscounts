package com.android.fragment;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.testbaiduapi.R;
import com.android.utils.GlobalParameter;
import com.androidui.LoginAndRegistActivity;

public class MyDiscountFragment extends Fragment{
	
	Boolean isLogin =false;
	
	RelativeLayout loginLayout = null;
	RelativeLayout registLayout = null;
	RelativeLayout myDiscountLoginAndRegistLayout = null;
	
	TextView userNameText = null;
	static String username;
	
	HashMap<String, Object> userinfoMap = new HashMap<String, Object>();
	
	
	public static MyDiscountFragment newInstance(){
		MyDiscountFragment detail= new MyDiscountFragment();
		
  	    return detail;
	}
	public static MyDiscountFragment newInstance(String user){
		MyDiscountFragment detail= new MyDiscountFragment();
		username = user;
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
		userNameText = (TextView)view.findViewById(R.id.userName);
		userNameText.setText(username);
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

	public void handleIntent(){
		Intent intent = getActivity().getIntent();
		int type = intent.getIntExtra("type", 0);
		switch(type){
			//登录成功
			case GlobalParameter.LOGIN_SUCCESS :
				userinfoMap = (HashMap<String, Object>) intent.getSerializableExtra("userinfo");
				userNameText.setText(userinfoMap.get("user_name").toString());
				break;
		}
	}
	

}
