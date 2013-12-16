package com.android.fragment;

import com.android.testbaiduapi.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyDiscountFragment extends Fragment{
	
	public static MyDiscountFragment newInstance(){
		MyDiscountFragment detail= new MyDiscountFragment();
		
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

		View view = inflater.inflate(R.layout.what_new_one, container,false);
		
		

	      return view;
	  } 

}
