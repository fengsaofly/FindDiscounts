package com.androidui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint.Join;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.testbaiduapi.R;

public class HomeLayout extends Activity  { 
    private static final int TAB_INDEX_DISCOUNT= 0; 
    private static final int TAB_INDEX_SHOP = 1; 
    
    private Integer[] imgs = new Integer[6];
   
    
    private ImageView[] dots;
//    private TabHost mTabHost; 
    TextView title = null; 
	private List<View> views;
    private ViewPager vp;
	private MiddleViewPageAdapter vpAdapter;
    RelativeLayout search_box_layout = null;
 
    TextView searchBtn = null;
    EditText search_edit = null;
    TextView edit_delete =null;
    private int currentIndex;
    LinearLayout ll =null;
    
    ListView listview = null;
    
    private TextWatcher textWatcher = new TextWatcher() {
    	  @Override
    	  public void afterTextChanged(Editable s) {
    	   // TODO Auto-generated method stub
//    	   Log.d("TAG", "afterTextChanged--------------->");
    	  }
    	  @Override
    	  public void beforeTextChanged(CharSequence s, int start, int count,
    	    int after) {
    	   // TODO Auto-generated method stub
    	   Log.d("TAG", "beforeTextChanged--------------->");
    	  }
    	  @Override
    	  public void onTextChanged(CharSequence s, int start, int before,
    	    int count) {
    	   Log.d("TAG", "onTextChanged--------------->");
    	   if("".equals(search_edit.getText().toString().trim())){
    		   edit_delete.setVisibility(TextView.INVISIBLE);
    	   }
    	   else{
    		   edit_delete.setVisibility(TextView.VISIBLE);
    	   }
    	   
    	  }
    	};
    	
    	
    @Override 
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
 
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
         
        setContentView(R.layout.home_layout); 

        initial();

    }
    
    
    private Handler handler = new Handler() {  
        public void handleMessage(android.os.Message msg) { 
        	switch(msg.what){
        	case 1:
        		
				int next = (currentIndex + 1) % views.size(); 
				
				vp.setCurrentItem(next);
				setCurrentDot(next);
        		break;
        	}
        	super.handleMessage(msg);
           // vp.setCurrentItem(currentIndex);// 切换当前显示的图片  
        } 
    };  
    
    private void initDots() {


		dots = new ImageView[views.size()];

		// 循环取得小点图片
		for (int i = 0; i < views.size(); i++) {
			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(true);// 都设为灰色
		}

		currentIndex = 0;
		dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
	}

	private void setCurrentDot(int position) {
		if (position < 0 || position > views.size() - 1
				|| currentIndex == position) {
			return;
		}

		dots[position].setEnabled(false);
		dots[currentIndex].setEnabled(true);

		currentIndex = position;
		
	}
    public void initial(){
    	  ll = (LinearLayout)findViewById(R.id.ll);
    	  vp = (ViewPager)findViewById(R.id.middleviewpager);
    	  title = (TextView)findViewById(R.id.title);
          title.setText(R.string.app_name);
    	  search_box_layout =(RelativeLayout) findViewById(R.id.search_box_layout);
          edit_delete = (TextView)findViewById(R.id.edit_delete);
          searchBtn = (TextView) findViewById(R.id.action);
          search_edit = (EditText)findViewById(R.id.search_input);
          search_edit.addTextChangedListener(textWatcher);
          
          searchBtn.setOnClickListener(new OnClickListener() {

  			@Override
  			public void onClick(View v) {
  				// TODO Auto-generated method stub
  				if(search_box_layout.getVisibility() == RelativeLayout.VISIBLE)
  				{
  					search_box_layout.setVisibility(RelativeLayout.GONE);
  				}
  				else{
  					search_box_layout.setVisibility(RelativeLayout.VISIBLE);
  					
  					search_edit.setFocusable(true);
  					search_edit.setFocusableInTouchMode(true);
  					search_edit.requestFocus();
  				}
  					
  				
  			}
          	
          });
          edit_delete.setOnClickListener(new OnClickListener() {
  			
  			@Override
  			public void onClick(View v) {
  				// TODO Auto-generated method stub
  				search_edit.setText(null);
  				
  			}
  		});
          
        views = new ArrayList<View>();
  		
  		
       LayoutInflater inflater = LayoutInflater.from(this);
  			views.add(inflater.inflate(R.layout.what_new_one, null));
  			views.add(inflater.inflate(R.layout.what_new_one, null));
  			views.add(inflater.inflate(R.layout.what_new_one, null));
  			views.add(inflater.inflate(R.layout.what_new_one, null));
  	

  		// 初始化Adapter
  		
  		vpAdapter = new MiddleViewPageAdapter(views, this);
  		
  		
  		vp.setAdapter(this.vpAdapter);
  		initDots();
  		
  		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					try{
						Thread.sleep(2000);
						handler.sendEmptyMessage(1);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}).start();	
  		
  		listview = (ListView) findViewById(R.id.home_listview);
  		SimpleAdapter adapter = new SimpleAdapter(this,getData(),R.layout.friendlink_item,
				new String[]{"discount_name","discount_description","app_icon"},
				new int[]{R.id.discount_name,R.id.discount_description,R.id.app_icon});
  		listview.setAdapter(adapter);
  		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent();
				intent.setClass(HomeLayout.this,DiscountInfoActivity.class);
				float price = 10.90f;
				Bundle b = new Bundle();
				b.putFloatArray("value", new float[]{(float)position,price});
//				intent.putExtra("position", new Float[]{(float)position,price});
				 
				intent.putExtra("position", b);
				startActivity(intent);
				
			}
		});
	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("discount_name", "G1");
		map.put("discount_description", "google 1");
		map.put("app_icon", R.drawable.home_bottom_home);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("discount_name", "G2");
		map.put("discount_description", "google 2");
		map.put("app_icon", R.drawable.home_bottom_icon_search);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("discount_name", "G3");
		map.put("discount_description", "google 3");
		map.put("app_icon", R.drawable.home_bottom_onehead);
		list.add(map);
		
		return list;
	}
  		
    
}
