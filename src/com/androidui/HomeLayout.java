package com.androidui;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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



import com.android.testbaiduapi.LocationOverlayDemo;
import com.android.testbaiduapi.R;
import com.baidu.mapapi.cloud.CustomPoiInfo;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class HomeLayout extends Activity  { 
    private static final int TAB_INDEX_DISCOUNT= 0; 
    private static final int TAB_INDEX_SHOP = 1; 
    
    private Integer[] imgs = new Integer[6];
   
    
    private ImageView[] dots;
//    private TabHost mTabHost; 
    TextView title = null; 
	private List<View> views;
	private ListView listview = null;
    private ViewPager vp;
	private MiddleViewPageAdapter vpAdapter;
    RelativeLayout search_box_layout = null;
    LinearLayout hide = null;
    TextView searchBtn = null;
    EditText search_edit = null;
    TextView edit_delete =null;
    private int currentIndex;
    LinearLayout ll =null;
    List<Map<String, Object>> list;
    SimpleAdapter adapter = null;
    int clickTime = 0; 
    JSONArray numberList = null;
    private static final String Get_URL="http://api.map.baidu.com/geodata/v2/poi/list?geotable_id=31937&ak=C8e5f84b57555c9e0478c87526b878c0";//get请求url,从lbs上更新数据并显示到地图上
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
    	
    	  getDiscountInformation();
    	  list = new ArrayList<Map<String, Object>>();
    	  ll = (LinearLayout)findViewById(R.id.ll);
    	  vp = (ViewPager)findViewById(R.id.middleviewpager);
    	  title = (TextView)findViewById(R.id.title);
          title.setText(R.string.app_name);
          hide = (LinearLayout)findViewById(R.id.hide);
          title.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				clickTime++;
				if(clickTime%2==1){
					hide.setVisibility(View.VISIBLE);
				}
				else hide.setVisibility(View.GONE);
				// TODO Auto-generated method stub
				
			}
		});
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
  		 adapter = new SimpleAdapter(this,list,R.layout.friendlink_item,
				new String[]{"discount_name","discount_description","discount_distance","app_icon"},
				new int[]{R.id.discount_name,R.id.discount_description,R.id.discount_distance,R.id.app_icon});
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

  		
   
    
    
 
    
    
 public void getDiscountInformation(){
    	
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
		    	
		    	System.out.println("进入getDiscountInformation函数");
		    	 String resultStr="";
		    	 HttpClient httpclient = new DefaultHttpClient();
		    	 List<NameValuePair> nameValuePairs =new ArrayList<NameValuePair>();
		    	 //?geotable_id=31937&ak=C8e5f84b57555c9e0478c87526b878c0
		         nameValuePairs.add(new BasicNameValuePair("geotable_id","31937"));
		         nameValuePairs.add(new BasicNameValuePair("ak",com.android.testbaiduapi.GlobalParameter.browerKey));
		         HttpGet httpget = new HttpGet(Get_URL);
		         try {
		        	 //httpget.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		            HttpResponse response; 
		            response=httpclient.execute(httpget);
		            if (response.getStatusLine().getStatusCode() == 200) {
		                // 获取返回的数据
		            	resultStr = EntityUtils.toString(response.getEntity(), "UTF-8");
		            	System.out.println("结果："+resultStr+"\n\n\n\n"+"--------------");
		                Log.i("HttpGet", "HttpGet方式请求成功，返回数据如下：");
		              //  System.out.println("请求结果:"+resultStr.toString());
		                JSONObject demoJson = new JSONObject(resultStr);

		                 numberList = demoJson.getJSONArray("pois");
		                 list.clear();



		                for(int i=0; i<numberList.length(); i++){

		                      //获取数组中的数组
		                	Map<String, Object> map = new HashMap<String, Object>();
		                	String name = numberList.getJSONObject(i).get("title").toString();
		                	String description = numberList.getJSONObject(i).get("tags").toString();
		                	if(name.length()>8){
		                		name = name.substring(0, 6)+"...";
		                	}
		                	if(description.length()>11){
		                		description = description.substring(0, 9)+"...";
		                	}
		            		map.put("discount_name", name);
		            		map.put("discount_description", description);
		            		map.put("discount_distance", "1000米");
		            		map.put("app_icon", R.drawable.home_bottom_home);
		            		list.add(map);
		                	  String latAndLog = numberList.getJSONObject(i).get("location").toString().replace("[", "").replace("]", ""); 
		                      String latitude = latAndLog.split(",")[1];
		                      String logitude = latAndLog.split(",")[0];
		                      System.out.println("title:"+numberList.getJSONObject(i).get("title"));
		                	  System.out.println("location:"+numberList.getJSONObject(i).get("location"));
		                	  System.out.println("tags:"+numberList.getJSONObject(i).get("tags"));
		                      System.out.println("address:"+numberList.getJSONObject(i).get("address"));
		                      System.out.println("latitude:"+latitude);
		                      System.out.println("logitude:"+logitude);
		                      System.out.println("discountsName:"+"");
		                     
		                      
		                }
		                
		                adapter.notifyDataSetChanged();
		                
		            
		            }
		           
		        } catch (UnsupportedEncodingException e) {
		            
		            e.printStackTrace();
		        } catch (ClientProtocolException e) {
		           
		            e.printStackTrace();
		        } catch (IOException e) {
		          
		            e.printStackTrace();
		        } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
    	
    }

}
