package com.androidui;

import com.android.testbaiduapi.R;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class HomeLayout extends Activity  { 
    private static final int TAB_INDEX_DISCOUNT= 0; 
    private static final int TAB_INDEX_SHOP = 1; 
    
    private Integer[] imgs = new Integer[6];
   
    GridView gv = null;
    HomeViewPager viewpager  =null;
//    private TabHost mTabHost; 
    TextView title = null; 
    String type = "discount";//判断tab

    RelativeLayout search_box_layout = null;
    HomeViewPager viewPager = null;
    TextView searchBtn = null;
    EditText search_edit = null;
    TextView edit_delete =null;
    private TextWatcher textWatcher = new TextWatcher() {
    	  @Override
    	  public void afterTextChanged(Editable s) {
    	   // TODO Auto-generated method stub
    	   Log.d("TAG", "afterTextChanged--------------->");
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
        
 
        final Intent intent = getIntent(); 
 
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
         
        setContentView(R.layout.home_layout); 
        //设置ui页面中gridView的adapter
//        grid = (GridView)findViewById(R.id.grid2);
//        grid.setAdapter(new MyGridAdapter(this)); 
       //设置TabHost的监听器
//        mTabHost = getTabHost(); 
//        mTabHost.setOnTabChangedListener(this); 
        
        title = (TextView)findViewById(R.id.title);
        title.setText(R.string.app_name);
        
//        viewPager = (ViewPager)findViewById(R.id.middleviewpager);
        viewpager = new HomeViewPager(HomeLayout.this);
       
        // Setup the tabs 
//        setupDiscountTab(); 
//        setupShopTab(); 
//        setCurrentTab(intent); 
        
//        type = intent.getStringExtra("type");
//        updateTabContent(type);
        
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
        
        
	
		
 
    } 
//    public void updateTabContent(String type){
//    	if("discount".equals(type)){
//			 for(int i=0;i<6;i++){
//				 imgs[i]=R.drawable.ic_launcher;
//			 }
//			
//			
//		}
//		else{
//			for(int j=0;j<6;j++){
//				imgs[j]=R.drawable.arrow_toleft;
//			}
//		}
//    }
//    public void initGridView(){
//		gv = (GridView)findViewById(R.id.home_grid);
//	       gv.setAdapter(new MyAdapter(this,imgs)); 
//	        //注册监听事件 
//	     
//	        gv.setOnItemClickListener(new OnItemClickListener()  //点击六个分类图标中的某一个，便进入displayActivity
//	        { 
//	        
//	            public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
//	            { 
//	               // Toast.makeText(Other.this, "pic" + position, Toast.LENGTH_SHORT).show(); 
//	            	Intent intent = new Intent();
//	            	if(type.equals("discount")){
//	            	intent.putExtra("clickContent", type+"$"+position);
//	            	
//	            	intent.setClass(getApplicationContext(), DisplayActivity.class);
//	            	startActivity(intent);
//	            	}
//	            	
//	            	
//	            }
//	       
//	           
//				
//	        });
//    }
//    public void onTabChanged(String tabId) { 
//         Activity activity = getLocalActivityManager().getActivity(tabId); 
//            if (activity != null) { 
//                activity.onWindowFocusChanged(true); 
//            } 
//         
//    } 
//     private void setupDiscountTab() { 
//            // Force the class since overriding tab entries doesn't work 
//            Intent intent = new Intent(); 
// 
//            intent.setClass(this, Other.class); 
//            intent.putExtra("type","discount");
//            
//            mTabHost.addTab(mTabHost.newTabSpec("discount") 
//                    .setIndicator("折扣")
//                    .setContent(intent)); 
//        } 
      
//    private void setupShopTab() { 
//        Intent intent = new Intent(); 
//        intent.setClass(this, Other.class); 
//        intent.putExtra("type","shop");
//        mTabHost.addTab(mTabHost.newTabSpec("shop") 
//                .setIndicator("商店")
//                .setContent(intent)); 
//    } 
 
    
 
    /**
     * Sets the current tab based on the intent's request type
     *
     * @param intent Intent that contains information about which tab should be selected
     */ 
//    private void setCurrentTab(Intent intent) { 
//        // Dismiss menu provided by any children activities 
//        Activity activity = getLocalActivityManager(). 
//                getActivity(mTabHost.getCurrentTabTag()); 
//        if (activity != null) { 
//            activity.closeOptionsMenu(); 
//        } 
// 
//        // Tell the children activities that they should ignore any possible saved 
//        // state and instead reload their state from the parent's intent 
//        intent.putExtra("", true); 
// 
//        // Choose the tab based on the inbound intent 
//        String componentName = intent.getComponent().getClassName(); 
//        if (getClass().getName().equals(componentName)) { 
//           if ((intent.getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) != 0) { 
//                // launched from history (long-press home) --> nothing to change 
//            } else if (true) { 
//                // The dialer was explicitly requested 
//                mTabHost.setCurrentTab(TAB_INDEX_DISCOUNT); 
//            }  
//            
//        } 
//    } 
}



//自定义适配器 
//class MyGridAdapter extends BaseAdapter{ 
//    //上下文对象 
//    private Context context; 
//    //图片数组 
//    int a[]={R.drawable.home_bottom_home,R.drawable.home_bottom_location,
//    		R.drawable.home_bottom_icon_search,R.drawable.home_bottom_onehead};
//    MyGridAdapter(Context context){ 
//        this.context = context; 
//    } 
//    public int getCount() { 
//        return a.length; 
//    } 
//
//    public Object getItem(int item) { 
//        return item; 
//    } 
//
//    public long getItemId(int id) { 
//        return id; 
//    } 
//     
//    //创建View方法 
//    public View getView(int position, View convertView, ViewGroup parent) { 
//         Button bt; 
//            if (convertView == null) { 
//                bt = new Button(context); 
//                bt.setLayoutParams(new GridView.LayoutParams(100,120));//设置ImageView对象布局 
//               
//            }  
//            else { 
//                bt = (Button) convertView; 
//            } 
//            bt.setBackgroundResource(a[position]);
//            
//           
//           
//            return bt; 
//    } 
//自定义适配器 
//class MyAdapter extends BaseAdapter{ 
//    //上下文对象 
//    private Context context; 
//    //图片数组 
//    private Integer[] imgs ;
//    MyAdapter(Context context, Integer[] imgs){ 
//        this.context = context; 
//        this.imgs = imgs;
//    } 
//    public int getCount() { 
//        return imgs.length; 
//    } 
//
//    public Object getItem(int item) { 
//        return item; 
//    } 
//
//    public long getItemId(int id) { 
//        return id; 
//    } 
//     
//    //创建View方法 
//    public View getView(int position, View convertView, ViewGroup parent) { 
//         ImageView imageView; 
//            if (convertView == null) { 
//                imageView = new ImageView(context); 
//                imageView.setLayoutParams(new GridView.LayoutParams(100,100));//设置ImageView对象布局 
//                imageView.setAdjustViewBounds(false);//设置边界对齐 
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);//设置刻度的类型 
//                imageView.setPadding(8, 8, 8, 8);//设置间距 
//            }  
//            else { 
//                imageView = (ImageView) convertView; 
//            } 
//            imageView.setImageResource(imgs[position]);//为ImageView设置图片资源 
//            return imageView; 
//    } 
//    
//
//
//}