package com.androidui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.testbaiduapi.LocationOverlayDemo;
import com.android.testbaiduapi.R;

public class DiscountInfoActivity extends Activity {
	TextView back = null;
	TextView share = null;
	TextView deadline = null;
	ImageView goods_icon = null;
	ImageView triangle_left = null;
	ImageView triangle = null;
	TextView discount_type = null;
	TextView market_price = null;
	TextView discount_description = null;
	ImageView shopIcon = null;
	TextView shopName = null;
	TextView shopType = null;
	TextView shopAddress = null;
	TextView shopTag = null;
	TextView shopHour = null;
	RelativeLayout gotoMap = null;
	int currentDiscount  = 0;
	com.android.testbaiduapi.m_Shop ms;
	List<String> goodsPicPaths = new ArrayList();
	//剩余时间模拟数据
    int totleseconds = 1000000;  //全部换算成秒
	int millseconds =100;
	
	ImageView shopRightArrowIcon = null;
	

	TextView price =null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		
		setContentView(R.layout.goods_item_layout);
		initial();
	
		
	}
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case 1:
				goods_icon.setImageBitmap(BitmapFactory.decodeFile(
						goodsPicPaths.get(currentDiscount)
						));
				break;
			case 2: //图片下载完成，返回折扣图片的存储路径
				 Bitmap shopBm = null;
				 Bitmap currentDiscountBm = null;
				 goodsPicPaths = (List<String>)msg.obj;
				
				shopBm = BitmapFactory.decodeFile(
						Environment.getExternalStorageDirectory().getPath() + "/"+"findDiscount"+"/"+ms.getM_shop_id()
						+"/"+"shopid_"+ms.getM_shop_id()+".jpg"
						);
				
				currentDiscountBm = BitmapFactory.decodeFile(
						goodsPicPaths.get(currentDiscount)
						);
				shopIcon.setImageBitmap(shopBm);
				goods_icon.setImageBitmap(currentDiscountBm);
				break;
			case 3:
				millseconds-=10;
				if(millseconds<=0){
					millseconds=100;
					totleseconds -=1;
				}
				int day = totleseconds/86400;//一天等于86400秒
				int hour = totleseconds%86400/3600; //一小时3600秒
				int minute = totleseconds%86400%3600/60; //一分钟等于60秒
				int seconds = totleseconds%86400%3600%60;
				
				deadline.setText(day+"  天  "+hour+"  时  "+minute+"  分  "+seconds+"  秒  "+millseconds);
				break;
				
			}
			super.handleMessage(msg);
		}
		
	};
	
	
	public void initial(){
//		 shopId=getIntent().getIntExtra("shopId", -1);
		
		
		 
		 new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					try{
						Thread.sleep(100);
						handler.sendEmptyMessage(3);
					}
					catch(Exception e){
						e.printStackTrace();
					}
			}
			}
		}).start();
		 back = (TextView)findViewById(R.id.back_to);
		 share = (TextView)findViewById(R.id.action);
		 deadline = (TextView)findViewById(R.id.deadline);
		 goods_icon = (ImageView)findViewById(R.id.goods_icon);
		 triangle_left = (ImageView)findViewById(R.id.triangle_left);
		 triangle = (ImageView)findViewById(R.id.triangle);
		 discount_type = (TextView)findViewById(R.id.buying_price);
		 market_price = (TextView)findViewById(R.id.market_price);
		 discount_description = (TextView)findViewById(R.id.discountDescription);
		 shopIcon = (ImageView)findViewById(R.id.shop_icon);
		 shopName = (TextView)findViewById(R.id.shopname);
		 shopType = (TextView)findViewById(R.id.shopType);
		 shopAddress = (TextView)findViewById(R.id.shopAddress);
		 shopTag = (TextView)findViewById(R.id.shopTag);
		 shopHour = (TextView)findViewById(R.id.workHour);
		 gotoMap = (RelativeLayout)findViewById(R.id.goods_property_tag);
		 
		 
			 ms = (com.android.testbaiduapi.m_Shop)getIntent().getParcelableExtra("shop");    
			System.out.println(ms.getM_shop_id()
					+"  "+ms.getM_shop_addr()
					+"  "+ms.getM_shop_latitude()
					+"  "+ms.getM_shop_longitude()
					+"  "+ms.getM_shop_name()
					+"  "+ms.getDiscounts().get(0).getM_discount_description()
					);
			//deadline.setText(ms.getDiscounts().get(currentDiscount).getM_discount_deadline());
			discount_type.setText(ms.getDiscounts().get(currentDiscount).getM_discount_type());
			market_price.setText(ms.getDiscounts().get(currentDiscount).getM_discount_good());
			discount_description.setText(ms.getDiscounts().get(currentDiscount).getM_discount_description());
			shopName.setText(ms.getM_shop_name());
			shopType.setText(ms.getM_shop_type());
			shopAddress.setText(ms.getM_shop_addr());
			shopTag.setText(ms.getM_shop_tags());
			
			List<Integer> ids = null;  //传入需要下载的图片id,id[0]为商家图片
			List<String> urls = null;  //传入需要下载的图片的url,url[0]为商家的url;
			ids = new ArrayList();
			urls = new ArrayList();
			ids.add(ms.getM_shop_id());
			urls.add(ms.getM_shop_pic());
			for(int i = 0;i<ms.getDiscounts().size();i++){
				ids.add(ms.getDiscounts().get(i).getM_discount_id());
				urls.add(ms.getDiscounts().get(i).getM_discount_picture());
			}
			com.android.testbaiduapi.DownloadBitmap dd = new 
			com.android.testbaiduapi.DownloadBitmap(DiscountInfoActivity.this, DiscountInfoActivity.this, ids, urls,handler);//下载图片
			dd.execute();
		
	}
	
	public void myOnclick(View v){
		switch(v.getId()){
		case R.id.back_to:   //点击返回键的响应
			finish();
			break;
		case R.id.action:    //分享建
			break;
		case R.id.triangle_left:  //左一张图片
			if(currentDiscount<=0){
				currentDiscount=0;
			}
			else if(currentDiscount>=goodsPicPaths.size()-1){
				currentDiscount = goodsPicPaths.size()-1;
			}
			if(goodsPicPaths.size()!=0&&currentDiscount>0){
				currentDiscount--;
				handler.sendEmptyMessage(1);
			}
			
			break;
		case R.id.triangle:  //右一张图片
			if(currentDiscount<=0){
				currentDiscount=0;
			}
			else if(currentDiscount>=goodsPicPaths.size()-1){
				currentDiscount = goodsPicPaths.size()-1;
			}
			if(goodsPicPaths.size()!=0&&currentDiscount<goodsPicPaths.size()-1){
				currentDiscount++;
				handler.sendEmptyMessage(1);
			}
			
			break;
		case R.id.goods_property_tag:  //查看地图
			Intent intent  = new Intent();
			intent.setClass(DiscountInfoActivity.this, LocationOverlayDemo.class);
			intent.putExtra("lat", ms.getM_shop_latitude());
			intent.putExtra("lng", ms.getM_shop_longitude());
			startActivity(intent);
			break;
		case R.id.goods_details:
			Log.i("shop", "进来了");
			shopRightArrowIcon = (ImageView)findViewById(R.id.shop_info_right_arrow_img);
			
			LinearLayout shopDetails =  (LinearLayout)findViewById(R.id.shop_detail_layout);
			if(shopDetails.getVisibility() == View.VISIBLE){
				shopDetails.setVisibility(View.GONE);
				shopRightArrowIcon.setImageResource(R.drawable.triangle_down);
			}
			else
			{
				shopDetails.setVisibility(View.VISIBLE);
				shopRightArrowIcon.setImageResource(R.drawable.triangle_up);
			}
		}
		
			
		
	}
	
}
