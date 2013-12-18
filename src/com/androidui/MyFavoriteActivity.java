package com.androidui;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.testbaiduapi.R;

public class MyFavoriteActivity extends Activity{
	
	SimpleAdapter discountsLvAdapter = null;
	SimpleAdapter shopsLvAdapter = null;
	ListView itemList = null;
	
	TextView favorProductsTab = null;
	TextView favorBrandsTab = null;
	
	ArrayList<HashMap<String, Object>> discountsList,shopsList;
	
	int tabIndex =0; //默认为收藏折扣，1-收藏店铺
    @Override 
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
 
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
//        System.out.println("Oncreate____________");
 
        setContentView(R.layout.myenjoy_layout); 
        connectVC();
        initInstance();
       
    }
    public void connectVC(){
    	itemList = (ListView)findViewById(R.id.myenjoy_list);
    	favorProductsTab = (TextView)findViewById(R.id.favorProductsTextSelected);
    	favorBrandsTab = (TextView)findViewById(R.id.favorBrandsTextSelected);
    	
    	favorProductsTab.setVisibility(View.VISIBLE);
		favorBrandsTab.setVisibility(View.INVISIBLE);
		tabIndex = 0;
    	
    }
    
    public void initInstance(){
    	
    	  //生成动态数组，并且转入数据  
	      shopsList = new ArrayList<HashMap<String, Object>>();  
	      for(int i=0;i<5;i++)  
	      {  
	        HashMap<String, Object> map = new HashMap<String, Object>();  
	        map.put("ItemImage", R.drawable.discountsbar_logo);//添加图像资源的ID  
	        map.put("ItemText", "第"+String.valueOf(i)+"行");//按序号做ItemText  
	        shopsList.add(map);  
	      }  
	      
	      //生成动态数组，并且转入数据  
	       discountsList = new ArrayList<HashMap<String, Object>>();  
	      for(int i=0;i<10;i++)  
	      {  
	        HashMap<String, Object> map = new HashMap<String, Object>();  
	        map.put("ItemImage", R.drawable.discountsbar_logo);//添加图像资源的ID  
	        map.put("ItemText", "NO."+String.valueOf(i));//按序号做ItemText  
	    	discountsList.add(map);  
	      }  
    	//设置adapter
	      discountsLvAdapter = new SimpleAdapter(this, discountsList, R.layout.myenjoy_item, new String[]{"ItemImage","ItemText"}, 
    										new int[]{R.id.goods_icon,R.id.myenjoy_name});
	      shopsLvAdapter = new SimpleAdapter(this, shopsList, R.layout.myenjoy_item, new String[]{"ItemImage","ItemText"}, 
					new int[]{R.id.goods_icon,R.id.myenjoy_name});
	   
	     itemList.setAdapter(discountsLvAdapter);
    	//监听事件处理
    	itemList.setOnItemClickListener(new OnItemClickListener() {
    		

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				
				// TODO Auto-generated method stub
//				startActivity(new Intent(DisplayActivity.this,DiscountInfoActivity.class));
//				AlertDialog.Builder();
				if(tabIndex == 0 ){
				new AlertDialog.Builder(MyFavoriteActivity.this).setTitle("item"+position).setMessage(""+discountsList.get(position).get("ItemText")).
				setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				}).create().show();
				}
				else if(tabIndex == 1){
					new AlertDialog.Builder(MyFavoriteActivity.this).setTitle("item"+position).setMessage(""+shopsList.get(position).get("ItemText")).
					setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					}).create().show();
				}
			}
		});
    }
    public void onClick(View view) {
		switch(view.getId()){
			case R.id.favorProductsText:
				favorProductsTab.setVisibility(View.VISIBLE);
				favorBrandsTab.setVisibility(View.INVISIBLE);
				tabIndex = 0;
				itemList.setAdapter(discountsLvAdapter);
				break;
			case R.id.favorBrandsText:
				favorProductsTab.setVisibility(View.INVISIBLE);
				favorBrandsTab.setVisibility(View.VISIBLE);
				tabIndex = 1;
				itemList.setAdapter(shopsLvAdapter);
				break;
		}
		
	}
}
