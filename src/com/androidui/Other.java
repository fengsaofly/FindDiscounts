package com.androidui;

import java.util.ArrayList;
import java.util.List;

import com.android.testbaiduapi.R;
import com.android.testbaiduapi.ViewPagerAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Other extends Activity implements OnPageChangeListener{
	GridView gv = null;
	private Integer[] imgs = new Integer[6];
	
	private ViewPager vp;
	String type = "discount";
	private MiddleViewPageAdapter vpAdapter;
	
	private List<View> views;
	private Thread thread= null;
	// 底部小点图片
	private ImageView[] dots;
	// 记录当前选中位置
	private int currentIndex;
	 // 切换当前显示的图片  
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
        };  
    };  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content);
		
		

		
		// 初始化Adapter
//		vpAdapter = new ViewPagerAdapter(views, this);
		
		
		
	
		
		Intent intent =getIntent();
		
		
		
		type = intent.getStringExtra("type");
		System.out.println("type:  "+type);
		if(type.equals("discount")){
			 for(int i=0;i<6;i++){
				 imgs[i]=R.drawable.ic_launcher;
			 }
			
			
		}
		else{
			for(int j=0;j<6;j++){
				imgs[j]=R.drawable.arrow_toleft;
			}
		}
		
		
		gv = (GridView)findViewById(R.id.grid);
	       gv.setAdapter(new MyAdapter(this,imgs)); 
	        //注册监听事件 
	        gv.setOnItemClickListener(new OnItemClickListener()  //点击六个分类图标中的某一个，便进入displayActivity
	        { 
	        
	            public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
	            { 
	               // Toast.makeText(Other.this, "pic" + position, Toast.LENGTH_SHORT).show(); 
	            	Intent intent = new Intent();
	            	if(type.equals("discount")){
	            	intent.putExtra("clickContent", type+"$"+position);
	            	
	            	intent.setClass(Other.this, DisplayActivity.class);
	            	startActivity(intent);
	            	}
	            	
	            	
	            }
	       
	           
				
	        });
	        
    	//设置viewPager
		vp = (ViewPager) findViewById(R.id.middleviewpager);
		
		// 初始化页面
		initViews(type);
		// 初始化底部小点
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
	}
	
	private void initViews(String tabValue) {
		
//		this.vp = vp;
//		this.vpAdapter = vpAdapter;
		LayoutInflater inflater = LayoutInflater.from(this);

		views = new ArrayList<View>();
		
		if(tabValue.equals("discount")){
			// 初始化引导图片列表
			views.add(inflater.inflate(R.layout.what_new_one, null));
			views.add(inflater.inflate(R.layout.what_new_one, null));
			views.add(inflater.inflate(R.layout.what_new_one, null));
			views.add(inflater.inflate(R.layout.what_new_one, null));
		}
		else{
			// 初始化引导图片列表
			views.add(inflater.inflate(R.layout.what_new_two, null));
			views.add(inflater.inflate(R.layout.what_new_two, null));
			views.add(inflater.inflate(R.layout.what_new_two, null));
			views.add(inflater.inflate(R.layout.what_new_two, null));
			
		}


		// 初始化Adapter
		vpAdapter = new MiddleViewPageAdapter(views, this);
		
		
		this.vp.setAdapter(this.vpAdapter);
		// 绑定回调
		this.vp.setOnPageChangeListener(this);
	}

	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

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

	// 当滑动状态改变时调用
	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	// 当当前页面被滑动时调用
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	// 当新的页面被选中时调用
	@Override
	public void onPageSelected(int arg0) {
		// 设置底部小点选中状态
		setCurrentDot(arg0);
	}
	    
	} 
	    //自定义适配器 
	    class MyAdapter extends BaseAdapter{ 
	        //上下文对象 
	        private Context context; 
	        //图片数组 
	        private Integer[] imgs ;
	        MyAdapter(Context context, Integer[] imgs){ 
	            this.context = context; 
	            this.imgs = imgs;
	        } 
	        public int getCount() { 
	            return imgs.length; 
	        } 
	 
	        public Object getItem(int item) { 
	            return item; 
	        } 
	 
	        public long getItemId(int id) { 
	            return id; 
	        } 
	         
	        //创建View方法 
	        public View getView(int position, View convertView, ViewGroup parent) { 
	             ImageView imageView; 
	                if (convertView == null) { 
	                    imageView = new ImageView(context); 
	                    imageView.setLayoutParams(new GridView.LayoutParams(100,100));//设置ImageView对象布局 
	                    imageView.setAdjustViewBounds(false);//设置边界对齐 
	                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);//设置刻度的类型 
	                    imageView.setPadding(8, 8, 8, 8);//设置间距 
	                }  
	                else { 
	                    imageView = (ImageView) convertView; 
	                } 
	                imageView.setImageResource(imgs[position]);//为ImageView设置图片资源 
	                return imageView; 
	        } 
	        


	}