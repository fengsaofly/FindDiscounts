package com.androidui;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.testbaiduapi.GlobalParameter;
import com.android.testbaiduapi.R;
import com.android.utils.GeneralFunctions;

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
	TextView registerBtn = null;
	
	List<BasicNameValuePair> postData;
	
	private static final String[] COUNTRIES = new String[] {
	         "Belgium", "France", "Italy", "Germany", "Spain"
	     };
//	int tabIndex = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_and_register_layout); 
		
		connectVC();
		setAutoCompleteData();
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
		registUserPassValue = (EditText)findViewById(R.id.userPassValue1);
		
		registerBtn = (TextView)findViewById(R.id.registerButton);
		 registerBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Boolean success = checkRegistInfo();
					if(success){
						System.out.println("成功~");
						post(GlobalParameter.REGIST_BASEURL, postData);
					
					}
					else{
						
					}
					// TODO Auto-generated method stub
					
				}
			});
	}
	Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case 1:
				showTab(0);//显示登录界面
				break;
			case 2:
				System.out.println("post请求失败！");
				break;
			}
			super.handleMessage(msg);
		}
	};
	public void post(String url,final List<BasicNameValuePair> postData) {
		new Thread(new Runnable() {
			 
			@Override
			public void run() {
				String strResult = "";
				// TODO Auto-generated method stub
				String url = GlobalParameter.REGIST_BASEURL;
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost post = new HttpPost(url);
//				DefaultHttpClient httpClient = new DefaultHttpClient();  
//		        HttpPost post = new HttpPost(url);  
		       
		        UrlEncodedFormEntity entity;
				try {
					entity = new UrlEncodedFormEntity(postData,HTTP.UTF_8);
					
					 post.setEntity(entity);  
				        HttpResponse response = httpClient.execute(post);  
				  
				  
				        // 若状态码为200 ok  
				        if (response.getStatusLine().getStatusCode() == 200) {  
				            // 取出回应字串  
				            strResult = EntityUtils.toString(response.getEntity());  
				            Message msg = handler.obtainMessage();
				            msg.what=1;
				            msg.obj = strResult;
				            handler.sendMessage(msg);
				            
				        }  
				        else
				        {
				        	handler.sendEmptyMessage(2);
				        }
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					handler.sendEmptyMessage(2);
					e.printStackTrace();
				}//过时了?  
				 catch (ParseException e) {
									// TODO Auto-generated catch block
					 handler.sendEmptyMessage(2);
									e.printStackTrace();
								} 
				catch (IOException e) {
									// TODO Auto-generated catch block
					handler.sendEmptyMessage(2);
									e.printStackTrace();
								}
						       
		         

				
			}
		}).start();
	}
	public void setAutoCompleteData(){
	      ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	                 android.R.layout.simple_dropdown_item_1line, COUNTRIES);
	        
	      registEmailValue.setAdapter(adapter);
	     

	  
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
//			case R.id.registerButton:
//				checkRegistInfo();
//				break;
		}
		
	}
    public Boolean checkRegistInfo() {
    	
    	
    
    	String email = registEmailValue.getText().toString();
    	String user = registUserNameValue.getText().toString();
    	String pwd = registUserPassValue.getText().toString();
    	String msg = "";
//    	System.out.println(email+":"+user+":"+pwd);
    	
    	Boolean satisfaction = true;
    	
    	
    	if(!GeneralFunctions.isEmail(email)){
    		satisfaction = false;
    		msg = "邮箱输入格式错误!";
//    		return satisfaction;
    	}
    	else if(user.length() < 4 || user.length() > 12){
    		satisfaction = false;
    		msg = "用户名长度错误!";
//    		return satisfaction;
    	}
    	else if(pwd.length() < 4 || pwd.length() > 12){
    		satisfaction = false;
    		msg = "密码长度错误!";
//    		return satisfaction;
    	}
    	if(!satisfaction){
    		new AlertDialog.Builder(LoginAndRegistActivity.this).setTitle("警告").setMessage(msg).
			setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			}).create().show();
    		return satisfaction;
    	}
    	//数据格式正确，保存在postData中
	   	 postData = new ArrayList<BasicNameValuePair>();  
	    
         postData.add(new BasicNameValuePair("email", email));  
         postData.add(new BasicNameValuePair("username", user));
         postData.add(new BasicNameValuePair("password", pwd));  
//	         System.out.print(entry.getValue());  
	      
    	
    	return satisfaction;
		
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
