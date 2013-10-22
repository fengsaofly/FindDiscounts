package com.androidui;

import com.android.testbaiduapi.R;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TabHost;
import android.widget.TextView;

public class HomeLayout extends TabActivity implements TabHost.OnTabChangeListener { 
    private static final int TAB_INDEX_DISCOUNT= 0; 
    private static final int TAB_INDEX_SHOP = 1; 
   
    GridView grid = null;
    private TabHost mTabHost; 
    TextView title = null; 
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
        mTabHost = getTabHost(); 
        mTabHost.setOnTabChangedListener(this); 
        
        title = (TextView)findViewById(R.id.title);
        title.setText(R.string.app_name);
 
        // Setup the tabs 
        setupDiscountTab(); 
        setupShopTab(); 
        setCurrentTab(intent); 
    } 
 
    public void onTabChanged(String tabId) { 
         Activity activity = getLocalActivityManager().getActivity(tabId); 
            if (activity != null) { 
                activity.onWindowFocusChanged(true); 
            } 
    } 
     private void setupDiscountTab() { 
            // Force the class since overriding tab entries doesn't work 
            Intent intent = new Intent(); 
 
            intent.setClass(this, Other.class); 
            intent.putExtra("type","discount");
            
            mTabHost.addTab(mTabHost.newTabSpec("discount") 
                    .setIndicator("折扣")
                    .setContent(intent)); 
        } 
      
    private void setupShopTab() { 
        Intent intent = new Intent(); 
        intent.setClass(this, Other.class); 
        intent.putExtra("type","shop");
        mTabHost.addTab(mTabHost.newTabSpec("shop") 
                .setIndicator("商店")
                .setContent(intent)); 
    } 
 
    
 
    /**
     * Sets the current tab based on the intent's request type
     *
     * @param intent Intent that contains information about which tab should be selected
     */ 
    private void setCurrentTab(Intent intent) { 
        // Dismiss menu provided by any children activities 
        Activity activity = getLocalActivityManager(). 
                getActivity(mTabHost.getCurrentTabTag()); 
        if (activity != null) { 
            activity.closeOptionsMenu(); 
        } 
 
        // Tell the children activities that they should ignore any possible saved 
        // state and instead reload their state from the parent's intent 
        intent.putExtra("", true); 
 
        // Choose the tab based on the inbound intent 
        String componentName = intent.getComponent().getClassName(); 
        if (getClass().getName().equals(componentName)) { 
            if (false) { 
               //in a call, show the dialer tab(which allows going back to the call) 
                mTabHost.setCurrentTab(TAB_INDEX_DISCOUNT); 
            } else if ((intent.getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) != 0) { 
                // launched from history (long-press home) --> nothing to change 
            } else if (true) { 
                // The dialer was explicitly requested 
                mTabHost.setCurrentTab(TAB_INDEX_DISCOUNT); 
            }  
            
        } 
    } 
}



//自定义适配器 
class MyGridAdapter extends BaseAdapter{ 
    //上下文对象 
    private Context context; 
    //图片数组 
    int a[]={R.drawable.home_bottom_home,R.drawable.home_bottom_location,
    		R.drawable.home_bottom_icon_search,R.drawable.home_bottom_onehead};
    MyGridAdapter(Context context){ 
        this.context = context; 
    } 
    public int getCount() { 
        return a.length; 
    } 

    public Object getItem(int item) { 
        return item; 
    } 

    public long getItemId(int id) { 
        return id; 
    } 
     
    //创建View方法 
    public View getView(int position, View convertView, ViewGroup parent) { 
         Button bt; 
            if (convertView == null) { 
                bt = new Button(context); 
                bt.setLayoutParams(new GridView.LayoutParams(100,120));//设置ImageView对象布局 
               
            }  
            else { 
                bt = (Button) convertView; 
            } 
            bt.setBackgroundResource(a[position]);
            
           
           
            return bt; 
    } 
}