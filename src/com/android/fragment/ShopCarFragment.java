package com.android.fragment;

import com.android.testbaiduapi.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ShopCarFragment extends Fragment{
	public static ShopCarFragment newInstance(){
		ShopCarFragment detail= new ShopCarFragment();
		
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

		View view = inflater.inflate(R.layout.cart, container,false);
		
		

	      return view;
	  } 

}
