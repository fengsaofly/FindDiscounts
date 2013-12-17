package com.androidui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.testbaiduapi.R;

public class LoginAndRegistActivity extends Activity {
	
	RelativeLayout loginLayout = null;
	RelativeLayout registLayout = null;

	LinearLayout loginTabLayout = null;
	LinearLayout registTabLayout = null;
	
	CheckBox login_show_password = null;
	EditText userPassValue = null;
	AutoCompleteTextView registEmailValue = null;
	EditText registUserNameValue = null;
	EditText registUserPassValue = null;
//	int tabIndex = 0;
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
		
		loginTabLayout = (LinearLayout)findViewById(R.id.loginTab_select);
		registTabLayout = (LinearLayout)findViewById(R.id.registerTab_select);
		
		login_show_password = (CheckBox)findViewById(R.id.login_show_password);
		userPassValue = (EditText)findViewById(R.id.userPassValue);
		//注册
		registEmailValue = (AutoCompleteTextView)findViewById(R.id.emailValue);
		registUserNameValue = (EditText)findViewById(R.id.userNameValue);
		registUserPassValue = (EditText)findViewById(R.id.userPassValue);
	}
	public void handleIntent() {
		Intent intent = getIntent();
		String str = intent.getStringExtra("type");
		if(str.equals("login")){
			showTab(0);
		}
		else if(str.equals("regist")){
			showTab(1);
		}
		
	}
    public void onClick(View view) {
		switch(view.getId()){
			case R.id.loginTab:
				showTab(0);
				break;
			case R.id.registerTab:
				showTab(1);
				break;
			case R.id.login_show_password:
				//显示密码
				if(login_show_password.isChecked()){
					userPassValue.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//					userPassValue.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
					userPassValue.setSelection(userPassValue.getText().length());//将光标放在行尾
					}
				//隐藏密码
				else
				{
					userPassValue.setTransformationMethod(PasswordTransformationMethod.getInstance());
					userPassValue.setSelection(userPassValue.getText().length());
					
				}
				break;
			case R.id.registerButton:
				checkRegistInfo();
				break;
		}
		
	}
    public Boolean checkRegistInfo() {
    	String email = registEmailValue.getText().toString();
    	String user = registUserNameValue.getText().toString();
    	String pwd = registUserPassValue.getText().toString();
    	System.out.println(email+":"+user+":"+pwd);
    	
    	return true;
//		if()
	}
    public void showTab(int index) {
		switch(index){
		case 0:
			loginTabLayout.setVisibility(View.VISIBLE);
			registTabLayout.setVisibility(View.INVISIBLE);
//			tabIndex = 0;
			loginLayout.setVisibility(View.VISIBLE);
			registLayout.setVisibility(View.GONE);
			break;
		case 1:
			loginTabLayout.setVisibility(View.INVISIBLE);
			registTabLayout.setVisibility(View.VISIBLE);
//			tabIndex = 1;
			loginLayout.setVisibility(View.GONE);
			registLayout.setVisibility(View.VISIBLE);
			break;
		}
	}
	
}
