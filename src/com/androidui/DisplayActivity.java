package com.androidui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.testbaiduapi.R;

public class DisplayActivity extends Activity{
	private static final String[] orderType={"发布时间","距离","品牌"};
	TextView dp = null;
	TextView order = null;
	 Spinner orderSpinner = null;
	 EditText searchKeyword = null;
	 Button search = null;
	 ListView discountList = null;
	 SimpleAdapter lvAdapter = null;
	 private ArrayAdapter<String> adapter;
	 List<Map<String,Object>> list = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display);    
        initial();
		
	}
	public void initial(){
		dp = (TextView)findViewById(R.id.display);
		order = (TextView)findViewById(R.id.order);
		orderSpinner = (Spinner)findViewById(R.id.orderSpinner);
		searchKeyword  = (EditText)findViewById(R.id.searchKeyword);
		search  =(Button)findViewById(R.id.searchButton);
		discountList = (ListView)findViewById(R.id.discountList);
		
		//测试数据
		list = new ArrayList<Map<String,Object>>();  
		for(int i=0;i<10;i++){
			Map map = new HashMap<String, Object>();
			map.put("discountName","discountName"+i);
			map.put("distance", "distance"+i);
			list.add(map);
		}
		lvAdapter = new SimpleAdapter(this, list, R.layout.discountitems, new String[]{"discountName","distance"}, new int[]{R.id.name,R.id.distance});
		discountList.setAdapter(lvAdapter);
		discountList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				startActivity(new Intent(DisplayActivity.this,DiscountInfoActivity.class));
			}
		});
		
		
		int w = this.getResources().getDisplayMetrics().widthPixels;
		
	
		Intent intent  = getIntent();
		dp.setText(intent.getStringExtra("clickContent").toString());
		 adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,orderType);  
         
	        //设置下拉列表的风格  
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
	          
	        //将adapter 添加到spinner中  
	        orderSpinner.setAdapter(adapter);  
	          
	    	initWidthAndHeight(order,w/8);
			initWidthAndHeight(orderSpinner,w/8*3);
			initWidthAndHeight(searchKeyword,w/8*3);
			initWidthAndHeight(search,w/8);
	        //添加事件Spinner事件监听    
	     //   orderSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());  
	          

		//在代码中设置控件大小的方法
		
	}
	
	public void initWidthAndHeight(View view,int newWidth){  //在代码里面设置控件的大小
		
		        LayoutParams lp;        
		        lp=view.getLayoutParams();
		        lp.width=newWidth;      
		        view.setLayoutParams(lp);
	}
	

}
