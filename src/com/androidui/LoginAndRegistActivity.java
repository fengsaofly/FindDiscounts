package com.androidui;

import com.android.testbaiduapi.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class LoginAndRegistActivity extends Activity {
	
	RelativeLayout loginLayout = null;
	RelativeLayout registLayout = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_and_register_layout); 
		
		connectVC();
		handleIntent();
	}
	public void connectVC() {
		loginLayout = (RelativeLayout)findViewById(R.id.loginLay);
		registLayout = (RelativeLayout)findViewById(R.id.registerLay);
	}
	public void handleIntent() {
		Intent intent = getIntent();
		String str = intent.getStringExtra("type");
		if(str.equals("login")){
			loginLayout.setVisibility(View.VISIBLE);
			registLayout.setVisibility(View.GONE);
		}
		else if(str.equals("regist")){
			loginLayout.setVisibility(View.GONE);
			registLayout.setVisibility(View.VISIBLE);
		}
		
	}
	
}
