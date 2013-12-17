package com.androidui;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.fragment.MyDiscountFragment;
import com.android.fragment.NearByFragment;
import com.android.fragment.NewDiscountFragment;
import com.android.fragment.ShopCarFragment;
import com.android.testbaiduapi.R;

public class HomeLayout extends FragmentActivity  {
	private FragmentManager fManager;
	private FrameLayout home = null;
	private FrameLayout nearBy = null;
	private FrameLayout shopCar = null;
	private FrameLayout myDiscount = null;
    
	private ImageView homeImage = null;
	private ImageView nearbyImage = null;
	private ImageView cartImage = null;
	private ImageView accountImage = null;
	
    	
    @Override 
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
 
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        System.out.println("Oncreate____________");
 
        setContentView(R.layout.home_layout); 
        initial();
       
    }
    

    public void initial(){
    	home = (FrameLayout)findViewById(R.id.home);
    	nearBy = (FrameLayout)findViewById(R.id.nearBy);
    	shopCar=(FrameLayout)findViewById(R.id.shopCar);
    	myDiscount = (FrameLayout)findViewById(R.id.myDiscount);
    	
    	homeImage = (ImageView)findViewById(R.id.home_bottom_homepage_img);
    	nearbyImage = (ImageView)findViewById(R.id.home_bottom_location_img);
    	cartImage = (ImageView)findViewById(R.id.home_bottom_cart_img);
    	accountImage = (ImageView)findViewById(R.id.home_bottom_account_img);
    	
    	fManager = getSupportFragmentManager();
		Fragment details =(Fragment)fManager.findFragmentById(R.id.main_part);
		
		 if(details==null)
		 {
			 details = NewDiscountFragment.newInstance();
			 fManager.beginTransaction().replace(R.id.main_part, details).commit();
			 myButtonBgChanged(R.id.home);
		 }

  		
    }
    
    
    public void myOnclick(View view){ 
		Fragment details;
		Intent intent = new Intent();
		switch(view.getId()){
		case R.id.home:
			 details = NewDiscountFragment.newInstance();
			 fManager.beginTransaction().replace(R.id.main_part, details).commit();
			 myButtonBgChanged(R.id.home);
			 break;
			 
		case R.id.nearBy:
			 details = NearByFragment.newInstance();
			 fManager.beginTransaction().replace(R.id.main_part, details).commit();
			 myButtonBgChanged(R.id.nearBy);
			 break;
		case R.id.shopCar:
			 details = ShopCarFragment.newInstance();
			 fManager.beginTransaction().replace(R.id.main_part, details).commit();
			 myButtonBgChanged(R.id.shopCar);
			 break;
		case R.id.myDiscount:
			 details = MyDiscountFragment.newInstance();
			 fManager.beginTransaction().replace(R.id.main_part, details).commit();
			 myButtonBgChanged(R.id.myDiscount);
			break;
		case R.id.login_btn:

			
			intent.putExtra("type","login");
			intent.setClass(this,LoginAndRegistActivity.class);
			startActivity(intent);
			break;
		 case R.id.regist_btn:

			
			intent.putExtra("type","regist");
			intent.setClass(this,LoginAndRegistActivity.class);
			startActivity(intent);
			break;
		 case R.id.MyFavoriteLayout:

				
				intent.putExtra("type","regist");
				intent.setClass(this,MyFavoriteActivity.class);
				startActivity(intent);
				break;
		}
   
	
	}
		
	
	
	public void myButtonBgChanged(int id){

		switch(id){
		case R.id.home:
//			System.out.println("点击了home按钮");
			homeImage.setImageResource(R.drawable.public_toolbar_icon_home_highlight);
			nearbyImage.setImageResource(R.drawable.public_toolbar_icon_location);
			cartImage.setImageResource(R.drawable.public_toolbar_icon_cart);
			accountImage.setImageResource(R.drawable.public_toolbar_icon_account);
			break;
		case R.id.nearBy:
//			System.out.println("点击了home按钮");
			homeImage.setImageResource(R.drawable.public_toolbar_icon_home_final);
			nearbyImage.setImageResource(R.drawable.public_toolbar_icon_location_highlight);
			cartImage.setImageResource(R.drawable.public_toolbar_icon_cart);
			accountImage.setImageResource(R.drawable.public_toolbar_icon_account);
			break;
		case R.id.shopCar:
//			System.out.println("点击了home按钮");
			homeImage.setImageResource(R.drawable.public_toolbar_icon_home_final);
			nearbyImage.setImageResource(R.drawable.public_toolbar_icon_location);
			cartImage.setImageResource(R.drawable.public_toolbar_icon_cart_highlight);
			accountImage.setImageResource(R.drawable.public_toolbar_icon_account);
			break;
		case R.id.myDiscount:
//			System.out.println("点击了home按钮");
			homeImage.setImageResource(R.drawable.public_toolbar_icon_home_final);
			nearbyImage.setImageResource(R.drawable.public_toolbar_icon_location);
			cartImage.setImageResource(R.drawable.public_toolbar_icon_cart);
			accountImage.setImageResource(R.drawable.public_toolbar_icon_account_highlight);
			break;
			default:
				break;
		}
		
	}
    


}