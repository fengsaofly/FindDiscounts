

package com.android.testbaiduapi;

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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.cloud.CustomPoiInfo;
import com.baidu.mapapi.cloud.GeoSearchResult;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MKOLSearchRecord;
import com.baidu.mapapi.map.MKOLUpdateElement;
import com.baidu.mapapi.map.MKOfflineMap;
import com.baidu.mapapi.map.MKOfflineMapListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKRoute;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKStep;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
/**
 * 此demo用来展示如何结合定位SDK实现定位，并使用MyLocationOverlay绘制定位位置
 * 同时展示如何使用自定义图标绘制并点击时弹出泡泡
 *
 */
public class LocationOverlayDemo extends Activity implements MKOfflineMapListener{
	
	//离线地图相关
	private MKOfflineMap mOffline = null;

	
	
	private static  SharedPreferences sp;
	private MKSearch searchModel;

	
	int searchScope  = 0;
	Rrotate rt = null;
	/**
	 * 已下载的离线地图信息列表
	 */
	private ArrayList<MKOLUpdateElement>localMapList =null;
	private LocalMapAdapter lAdapter = null;
	List<CustomPoiInfo> mLbsPoints = null;  //所有打折商家信息
	// 定位相关
	LocationClient mLocClient;
	LocationData locData = null;
	public MyLocationListenner myListener  = null;
	private static final String Get_URL="http://api.map.baidu.com/geodata/v2/poi/list?geotable_id=31937&ak=C8e5f84b57555c9e0478c87526b878c0";//get请求url,从lbs上更新数据并显示到地图上
	//?geotable_id=31937&ak=C8e5f84b57555c9e0478c87526b878c0
	
	//定位图层
	locationOverlay myLocationOverlay = null;
	//弹出泡泡图层
	private PopupOverlay   pop  = null;//弹出泡泡图层，浏览节点时使用
	private TextView  popupText = null;//泡泡view
	private View viewCache = null;
	private ImageButton leftButton = null;
	private ImageButton rightButton = null;
	GeoSearchResult result =null;
	int DIR_LEFT = 0,DIR_RIGHT = 1;
	m_Shop shop;
	
	//地图相关，使用继承MapView的MyLocationMapView目的是重写touch事件实现泡泡处理
	//如果不处理touch事件，则无需继承，直接使用MapView即可
	MapView mMapView = null;	// 地图View
	ImageView rotateIv =null;
	ImageView currentImage = null;
	 BMapManager mBMapManager = null;
	private MapController mMapController = null;

	//UI相关
	//OnCheckedChangeListener radioButtonListener = null;
	//Button requestLocButton = null;
	boolean isRequest = false;//是否手动触发请求定位
	boolean isFirstLoc = true;//是否首次定位
	
   	private ListView lv;
	private SlidingDrawer sd;
	private ImageView iv;
	SimpleAdapter adapter = null;
	DiscountAdapter discountAdapter = null;
	DiscountAdapter searchAdapter = null;
	List<Map<String,Object>> list = null; //抽屉菜单
	List<Map<String,Object>> discount = null;
	List<Map<String,Object>> searchResult = null;
	
	JsonParser jp = new JsonParser(); //存储搜索结果的JsonParser
	JsonParser nearByJp = new JsonParser();//存储附近结果的JsonParser
	
	int overlookAngle = -30; //设置地图俯角显示的角度
	public static int LeftDownFlag=0;
	public static int rightDownFlag=0;
	
	//抽屉每个选项弹出对话框相关:
	int cityId=0;
	int clickNearBy = 0;
	   Context mContext = null;

		 View layout = null;  //显示离线地图的layout

		 View layout2 = null; //显示设置的layout
		 
		 View layout3 = null;//显示附近的layout
		 View layout4 = null;//显示搜索结果的layout
		 
		 EditText searchWord = null; //查询词
		 Button ok = null;
		 TextView cid = null;
		 EditText et = null;
		 Button search = null;
		 Button start = null;
		 Button stop = null;
		 TextView ratio = null;
		 ListView alreadyDownloadList = null; //已经下载好的离线地图列表
		 ListView discountList = null; //附近或搜索出来得打折信息的list
		 ListView searchList = null;
		 public static AlertDialog  ad =null;  //离线地图
		 AlertDialog.Builder ab =null;
		 public static AlertDialog ad2 = null;
		 AlertDialog.Builder ab2 =null; //设置
		 public static AlertDialog ad3 = null;
		 AlertDialog.Builder ab3 =null; //附近
		 public static AlertDialog ad4 = null;
		 AlertDialog.Builder ab4 =null; //搜索
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initEngineManager(getApplicationContext());
        setContentView(R.layout.activity_locationoverlay);
        
        currentImage = (ImageView)findViewById(R.id.currentImage);
        currentImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				new AlertDialog.Builder(LocationOverlayDemo.this)
//				.setTitle("安踏").setMessage("满100送100\n"+"截止日期：2013-10-20\n"+"联系电话："+"15200010021\n"+"地址："+"成都市武侯区马鞍南路122号")
//				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//					
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						// TODO Auto-generated method stub
//						
//					}
//				})
//				.create().show();
				Message msg = handler.obtainMessage();
		        msg.what =4;
		        List<String> goodsPicPaths =   new ArrayList<String>();
		        goodsPicPaths.add("/mnt/sdcard/findDiscount/15/discountid_27.jpg");
		        goodsPicPaths.add("/mnt/sdcard/findDiscount/15/discountid_23.jpg");
		        goodsPicPaths.add("/mnt/sdcard/findDiscount/15/discountid_22.jpg");
		        msg.obj = goodsPicPaths;
		        handler.sendMessage(msg);
			}
		});
        leftButton = (ImageButton)findViewById(R.id.leftButton);
        rightButton = (ImageButton)findViewById(R.id.rightButton);
        
        leftButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rt.Move(DIR_LEFT);
			}
		});
        
        rightButton.setOnClickListener(new OnClickListener() {
			
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				rt.Move(DIR_RIGHT);
    			}
    		});
        
        leftButton.setOnLongClickListener(new MyOnLongClickListener(DIR_LEFT));
        rightButton.setOnLongClickListener(new MyOnLongClickListener(DIR_RIGHT));
        leftButton.setOnTouchListener(new MyOnTouchListener(DIR_LEFT));
        rightButton.setOnTouchListener(new MyOnTouchListener(DIR_RIGHT));
        
        
   
        
    
        
        

        mContext = LocationOverlayDemo.this;
        sp=getSharedPreferences("FindDiscount_1.0", Context.MODE_PRIVATE);
        cityId = sp.getInt("cityId", 0); //从sp里面读出上次的选择,并显示
        rotateIv = (ImageView)findViewById(R.id.imageView1);
       
       

		  

        myListener = new MyLocationListenner();
        
        //抽屉
  
        		lv = (ListView) findViewById(R.id.allSelection);
        		sd = (SlidingDrawer) findViewById(R.id.sliding);
        		iv = (ImageView) findViewById(R.id.imageViewIcon);
        		iv.setImageResource(R.drawable.close);// 响应开抽屉事件
        		
       
        		
        		
        		lv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						 LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
						 layout = inflater.inflate(R.layout.alertdialogview, (ViewGroup) findViewById(R.id.alertDialogLayout));//加载离线地图的布局文件
						 layout2 = inflater.inflate(R.layout.alertdialogview_edittext, (ViewGroup) findViewById(R.id.settinglayout));//加载设置的布局文件
						 layout3 = inflater.inflate(R.layout.alertdialogview_listview, (ViewGroup) findViewById(R.id.listlayout));//加载设置的布局文件
						 layout4 = inflater.inflate(R.layout.alertdialogview_listview_search, (ViewGroup) findViewById(R.id.searchlayout));//加载设置的布局文件
						
						// TODO Auto-generated method stub
						switch(position){
						
						case 0: //附近
							sd.close();
							clickNearBy++;
							if(clickNearBy%2==1){
							leftButton.setVisibility(View.VISIBLE);
							
							
							rotateIv.setVisibility(View.VISIBLE);
							rt = new Rrotate(handler, LocationOverlayDemo.this, LocationOverlayDemo.this);
						    if(!rt.getCircleFull()){
						    	rightButton.setClickable(false);
						    	
						    }
						    else{
						    	rightButton.setVisibility(View.VISIBLE);
						    }
						    
							}
							else{
								currentImage.setVisibility(View.INVISIBLE);
								leftButton.setVisibility(View.INVISIBLE);
								rightButton.setVisibility(View.INVISIBLE);
								rotateIv.setVisibility(View.INVISIBLE);
							}
						//	startActivity(new Intent(LocationOverlayDemo.this,NearByActivity.class));
//							searchScope = sp.getInt("scope", 10000);
//							discountList = (ListView)layout3.findViewById(R.id.discountList);
//							discount = new ArrayList<Map<String,Object>>();
//						
//							System.out.println("searchScope: "+searchScope+"");
//							for(int i = 0;i<1;i++){
//								Map<String,Object> map = new HashMap<String, Object>();
//	                    		map.put("discount","nearBy");
//	                    		discount.add(map);
//							}
////							try{
////								
////							for(CustomPoiInfo ci : mLbsPoints){
////								if(calculateDistance(ci.latitude, ci.longitude, locData.latitude, locData.longitude)<searchScope){
////									Map<String,Object> map = new HashMap<String, Object>();
////		                    		map.put("discount",ci.address);
////		                    		discount.add(map);
////								}
////							}
////								
////							}catch(NullPointerException e){
////								System.out.println("size:"+mLbsPoints.size());
////								e.printStackTrace();
////							}
//							GeoPoint pt =   new GeoPoint((int)(locData.latitude* 1e6), (int)(locData.longitude *  1e6));
////							//discountAdapter = new SimpleAdapter(LocationOverlayDemo.this, discount, R.layout.discountitem, new String[]{"discount"}, new int[]{R.id.discountitem});
//							discountAdapter = new DiscountAdapter(pt,searchModel,LocationOverlayDemo.this, discount,nearByJp,handler);
//							discountList.setAdapter(discountAdapter);
//							String queryUrl = "http://discountsbar.sinaapp.com/discounts/getInfo?ak="+GlobalParameter.serverKey+"&longitude="+locData.longitude
//									+"&latitude="+locData.latitude+"&radius="+searchScope;
//							queryShops(queryUrl,"nearBy");
//	                		ab3 = new AlertDialog.Builder(LocationOverlayDemo.this);
//							ab3.setTitle("FindDiscount");
//							ab3.setMessage("附近");
//							ab3.setView(layout3);
//							ad3 = ab3.create();
//							ad3.show();
//							
							
							break;
						case 1:  //搜索

							searchWord = (EditText)layout4.findViewById(R.id.searchWord);
							ok = (Button)layout4.findViewById(R.id.ok);
							
			                searchList = (ListView)layout4.findViewById(R.id.searchList);
							searchResult = new ArrayList<Map<String,Object>>();
							
							for(int i=0;i<1;i++){
								
									Map<String,Object> map = new HashMap<String, Object>();
		                    		map.put("discount","search");
		                    		searchResult.add(map);
								
							}
							GeoPoint pt2 =   new GeoPoint((int)(locData.latitude* 1e6), (int)(locData.longitude *  1e6));
							searchAdapter = new DiscountAdapter(pt2,searchModel,LocationOverlayDemo.this, searchResult,jp,handler);
							 
							searchList.setAdapter(searchAdapter);
							  ok.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										String queryUrl = "http://discountsbar.sinaapp.com/discounts/getInfo"+"?ak="+GlobalParameter.serverKey
								        		 +"&keywords="+searchWord.getText().toString();
										queryShops(queryUrl,"search");
										
										// TODO Auto-generated method stub
										
									}
								});
								

	                		ab4 = new AlertDialog.Builder(LocationOverlayDemo.this);
							ab4.setTitle("FindDiscount");
							
							ab4.setView(layout4);
							ad4 = ab4.create();
							ad4.show();
							break;
							
                          
						
						case 2:  //定位
							//Toast.makeText(LocationOverlayDemo.this, "定位", Toast.LENGTH_SHORT).show();
							requestLocClick();
							break;
						case 3: //离线地图
				
							cid = (TextView)layout.findViewById(R.id.cid);
							  et = (EditText)layout.findViewById(R.id.editview);
							  search = (Button)layout.findViewById(R.id.search);
							  start = (Button)layout.findViewById(R.id.start);
							  stop = (Button)layout.findViewById(R.id.stop);
							  ratio = (TextView)layout.findViewById(R.id.ratio);
							  alreadyDownloadList = (ListView)layout.findViewById(R.id.alreadyDownloadList);
							  alreadyDownloadList.setAdapter(lAdapter);
							  cid.setText("当前城市id: "+cityId);
						       et.setText(sp.getString("cityName", ""));
							  search.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub
									ArrayList<MKOLSearchRecord> records = mOffline.searchCity(et.getText().toString());
						    		if(records == null || records.size() != 1)
						    			Toast.makeText(mContext, "查询出错,请重新输入", Toast.LENGTH_SHORT).show();
						    		
						    		else {  //搜索成功则将上次的结果存到sharedPreferences保存
						    			SharedPreferences.Editor editor = sp.edit();
						    			editor.putInt("cityId", records.get(0).cityID);
						    			editor.putString("cityName", et.getText().toString());
						    			editor.commit();
						    			cityId = records.get(0).cityID;
						    			cid.setText("cityId: "+String.valueOf(records.get(0).cityID)+"大小: "+formatDataSize(records.get(0).size));
						    		}
								}
							});
							  start.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									mOffline.start(cityId);
								}
							});
							  
							  stop.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									mOffline.pause(cityId);
								}
							});
							  ab = new AlertDialog.Builder(LocationOverlayDemo.this);
							  ab.setTitle("FindDiscount");
							  ab.setMessage("离线地图");
							  ab.setView(layout);
							  ab.setNegativeButton("返回", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										
									}
								});
							
							ad = ab.create();
							ad.show();
							break;
							
						case 4:  //设置
							//LayoutInflater inflater2 = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
							
							final EditText e = (EditText)layout2.findViewById(R.id.scope);
							ab2 = new AlertDialog.Builder(LocationOverlayDemo.this);
							ab2.setTitle("FindDiscount");
							ab2.setMessage("设置搜索范围");
							ab2.setView(layout2);
							ab2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									if(e.getText().toString().equals("")||e.getText().toString().contains(".")||(!e.getText().toString().matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$"))){
										Toast.makeText(mContext, "你的输入有误，请重新输入！（请输入整数）", Toast.LENGTH_SHORT).show();
									}
									else {
	
									SharedPreferences.Editor editor = sp.edit();
									editor.putInt("scope", Integer.parseInt(e.getText().toString()));
									System.out.println("1233");
									Toast.makeText(mContext, "设置成功", Toast.LENGTH_SHORT).show();
									editor.commit();
									}
								}
							});
							ab2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									
								}
							});
							ad2 = ab2.create();
							ad2.show();
							
							//Toast.makeText(LocationOverlayDemo.this, "设置", Toast.LENGTH_SHORT).show();
							break;
						}
					}
				});
        		
        		sd.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener()// 开抽屉
        		{
        			@Override
        			public void onDrawerOpened() {
        				lv = (ListView) findViewById(R.id.allSelection);
        				iv.setImageResource(R.drawable.open);// 响应开抽屉事件
        				list = new ArrayList<Map<String,Object>>();
                		String []s = {"附近","搜索","定位","离线地图","设置"};
                		for(int i=0;i<s.length;i++){
                			Map<String,Object> map = new HashMap<String, Object>();
                    		map.put("name",s[i]);
                    		list.add(map);
                    	
                		}
        				adapter = new SimpleAdapter(LocationOverlayDemo.this, list, R.layout.item, new String[]{"name"}, new int[]{R.id.ItemText});
                		lv.setAdapter(adapter);
                		adapter.notifyDataSetChanged();
                		
        																// ，把图片设为向下的
        			}
        		});
        		sd.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
        			@Override
        			public void onDrawerClosed() {
        				iv.setImageResource(R.drawable.close);// 响应开抽屉事件
        				lv = (ListView) findViewById(R.id.allSelection);
        		
        				list = new ArrayList<Map<String,Object>>();
                		String []s = {""};
                		for(int i=0;i<s.length;i++){
                			Map<String,Object> map = new HashMap<String, Object>();
                    		map.put("name",s[i]);
                    		list.add(map);
                    	
                		}
        				adapter = new SimpleAdapter(LocationOverlayDemo.this, list, R.layout.item, new String[]{"name"}, new int[]{R.id.ItemText});
                		lv.setAdapter(adapter);
                		
        			}
        		});
        	

        CharSequence titleLable="FindDiscount";
        setTitle(titleLable);
       
  
        
		//地图初始化
        mMapView = (MapView)findViewById(R.id.bmapView);
        mMapController = mMapView.getController();
        int zoomax=mMapView.getMaxZoomLevel();  
		Log.i("zoom", ""+zoomax);
		mMapController.setZoom(zoomax);//设置地图zoom级别
		mMapController.setOverlooking(overlookAngle); //设置俯角
        //mMapView.getController().setZoom(14);
        mMapView.getController().enableClick(true);
        mMapView.setBuiltInZoomControls(true);
      //创建 弹出泡泡图层
        createPaopao();
        
        //初始化离线地图
        mOffline = new MKOfflineMap();   
        
         
        
        /**
         * 初始化离线地图模块,MapControler可从MapView.getController()获取
         */
        
        mOffline.init(mMapController, this);
        
        
        //获取已下过的离线地图信息
        localMapList = mOffline.getAllUpdateInfo();
        if ( localMapList == null ){
            localMapList = new ArrayList<MKOLUpdateElement>();	
        }
        

        lAdapter = new LocalMapAdapter();
       // alreadyDownloadList.setAdapter(lAdapter);
        
        

        
        //定位初始化
        mLocClient = new LocationClient( this );
        locData = new LocationData();
        mLocClient.registerLocationListener( myListener );
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);//打开gps
        option.setCoorType("bd09ll");     //设置坐标类型
        option.setScanSpan(5000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        
//        com.baidu.mapapi.map.GroundOverlay mGroundOverlay = new com.baidu.mapapi.map.GroundOverlay(mMapView);
//		GeoPoint leftBottom = new GeoPoint((int) (mLat5 * 1E6),(int) (mLon5 * 1E6));
//		GeoPoint rightTop = new GeoPoint((int) (mLat6 * 1E6),(int) (mLon6 * 1E6));
//		Drawable d = getResources().getDrawable(R.drawable.ground_overlay);
//		Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
//		Ground mGround = new Ground(bitmap, leftBottom, rightTop); 
        
       
        //定位图层初始化
		myLocationOverlay = new locationOverlay(mMapView);
	
		System.out.println("111");
		//设置定位数据
	    myLocationOverlay.setData(locData);
	    //添加定位图层
		mMapView.getOverlays().add(myLocationOverlay);
		System.out.println("mMapView.getOverlays()11: "+mMapView.getOverlays());
		myLocationOverlay.enableCompass();
		 
		//修改定位数据后刷新图层生效
		
		//modifyLocationOverlayIcon(getResources().getDrawable(R.drawable.icon_geo)); //设置为默认图标
		mMapView.refresh();
	//	 GeoSearchManager.getInstance().init(LocationOverlayDemo.this);
		//searchLocal();
		//getDiscountInformation();  //得到打折信息并在地图上标注
		initialSearch();//初始化导航
		ScanMap();
//		searchDiscounts();
    }
    
    public void ScanMap(){
    	Intent intent = getIntent();
    	float lat = intent.getFloatExtra("lat", 0);
    	float lng = intent.getFloatExtra("lng", 0);
    	System.out.println("lat: "+lat);
    	
    	
//    	//pop demo
//    	//创建pop对象，注册点击事件监听接口
//    	PopupOverlay pop = new PopupOverlay(mMapView,new PopupClickListener() {                
//    	        @Override
//    	        public void onClickedPopup(int index) {
//    	                //在此处理pop点击事件，index为点击区域索引,点击区域最多可有三个
//    	        }
//    	});
//    	/**  准备pop弹窗资源，根据实际情况更改
//    	 *  弹出包含三张图片的窗口，可以传入三张图片、两张图片、一张图片。
//    	 *  弹出的窗口，会根据图片的传入顺序，组合成一张图片显示.
//    	 *  点击到不同的图片上时，回调函数会返回当前点击到的图片索引index
//    	 */
//    	Bitmap[] bmps = new Bitmap[1];
//    	try {
//    	bmps[0] = BitmapFactory.decodeStream(getAssets().open("marker1.png"));
//    	  
//    	} catch (IOException e) {
//    	         e.printStackTrace();
//    	}
//    	//弹窗弹出位置
//    	//GeoPoint ptTAM = new GeoPoint((int)(39.915 * 1E6), (int) (116.404 * 1E6));
//    	GeoPoint g = new GeoPoint((int)(lat*1e6),(int)(lng*1e6));
//    	mMapView.getController().animateTo(g);
//    	
//    	//弹出pop,隐藏pop
//    	pop.showPopup(bmps, g, 32);
//    	//隐藏弹窗
//    	//  pop.hidePop();

    }
    
    
    
    
   public  class MyOnLongClickListener implements View.OnLongClickListener{
    	
    	private int m_type;
    	public MyOnLongClickListener(int type){
    		this.m_type = type;
    	}

		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub
			
          new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				LeftDownFlag=0;
				rightDownFlag=0;
				
				while(LeftDownFlag==0&&rightDownFlag==0){
					try{
						Thread.sleep(200);
						rt.Move(m_type);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}).start();
			return false;
		}
    	
    }
    
   public  class MyOnTouchListener implements View.OnTouchListener{

    	private int m_type;
    	public MyOnTouchListener(int type){
    		this.m_type = type;
    	}
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			
			if(event.getAction()==MotionEvent.ACTION_UP){
				if(m_type==DIR_LEFT)
				LeftDownFlag=1;
				else if(m_type==DIR_RIGHT)
					rightDownFlag=1;
			}
			return false;
		}
    	
    }
    
    
  @Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyUp(keyCode, event);
	}


Handler handler = new Handler(){
	  public void handleMessage(Message msg){
		  
		  LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
	      View ll = inflater.inflate(R.layout.scan, (ViewGroup) findViewById(R.id.scanLayout));//加载离线地图的布局文件\
	       TextView name = (TextView)ll.findViewById(R.id.scan_name);
	      ImageView shopPic  = (ImageView)ll.findViewById(R.id.scan_image);
	       Gallery goods = (Gallery)ll.findViewById(R.id.scan_discountGoods);
	       TextView address = (TextView)ll.findViewById(R.id.scan_address);
	       
	       //rightButton = (Button)findViewById(R.id.rightButton);
	     
		  switch(msg.what){
		  case 0:
			  searchAdapter.notifyDataSetChanged(); //通知搜索信息已经改变了
			  break;
		  case 1:
			  discountAdapter.notifyDataSetChanged();//通知附近信息已经改变了
			  break;
		  case 2: //点击查看按钮后的响应(下载图片并显示)
			  System.out.println("handler2"+"-------------");
//			  
			  if(ad3!=null&&ad3.isShowing())
				  ad3.dismiss();
			 if(ad4!=null&&ad4.isShowing())
				  ad4.dismiss();

			 
			  shop = (m_Shop)msg.obj;
			  System.out.println("shopid:"+shop.getM_shop_id());
			
			  List<Integer> ids = new ArrayList<Integer>();
			  List<String> urls = new ArrayList<String>();
			  ids.add(shop.getM_shop_id());
			  urls.add(shop.getM_shop_pic());
			  for(int i=0;i<shop.getDiscounts().size();i++){
				  ids.add(shop.getDiscounts().get(i).getM_discount_id());
				  urls.add(shop.getDiscounts().get(i).getM_discount_picture());
			  }
			  DownloadBitmap dd = new DownloadBitmap(LocationOverlayDemo.this, LocationOverlayDemo.this, ids, urls,handler);
			  dd.execute();
			  
			  break;
		  case 3://点击导航按钮后取消搜索对话框
			  System.out.println("取消搜索对话框消");
			  if(ad3!=null&&ad3.isShowing())
				  ad3.dismiss();
			 if(ad4!=null&&ad4.isShowing())
				  ad4.dismiss();
			    currentImage.setVisibility(View.INVISIBLE);
				leftButton.setVisibility(View.INVISIBLE);
				rightButton.setVisibility(View.INVISIBLE);
				rotateIv.setVisibility(View.INVISIBLE);
			 

			  break;
		  case 4: //图片下载完成后显示出来
			  System.out.println("handler4"+"------------------");
			  name.setText
			  (
					  //shop.getM_shop_name().toString()
					  "安踏"
					  );
			  address.setText(
					 // shop.getM_shop_addr()
					  "成都市武侯区马鞍南路122号"
					  );
			  List<String> goodsPicPaths = (List<String>)msg.obj;
			  goods.setAdapter(new MyImageAdapter(mContext,goodsPicPaths));
			  goods.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					new AlertDialog.Builder(LocationOverlayDemo.this)
					.setTitle("打折信息").setMessage(
							//shop.getDiscounts().get(position).getM_discount_description()
							"满100送50，时间有限，速速抢购"
							)
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					})
					.create().show();
				}
			});
//			  final  float lat = shop.getM_shop_latitude();
//			  final  float lon = shop.getM_shop_longitude();
			  Bitmap bm = null;
//
//				//BitmapFactory.Options optsa = new BitmapFactory.Options();
//				//optsa.inSampleSize = 10;
				bm = BitmapFactory.decodeFile(
//						Environment.getExternalStorageDirectory().getPath() + "/"+"findDiscount"+"/"+shop.getM_shop_id()
//						+"/"+"shopid_"+shop.getM_shop_id()+".jpg"
						"/mnt/sdcard/findDiscount/15/shopid_15.jpg"
						
						);
//				//bm = ZoomImage.zoomImage(bm, 100, 100);
				shopPic.setImageBitmap(bm); //设置Bitmap
//				
//				//shopPic.setScaleType(ImageView.ScaleType.FIT_XY);
//				//shopPic.setLayoutParams(new Gallery.LayoutParams(136,88));
//				//i.setBackgroundResource(mGalleryItemBackground);
			  
			  new AlertDialog.Builder(LocationOverlayDemo.this)
				.setView(ll).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				})
				.setPositiveButton("查看地图位置", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						// TODO Auto-generated method stub

						// mMapController.animateTo(new GeoPoint((int)(lat* 1e6), (int)(lon *  1e6)));
					}
				})
				.create().show();

				
			  break;
		  case 5:
			  Bitmap b = (Bitmap) msg.obj;
			  rotateIv.setImageBitmap(b);
			  break;
		  case 6:
			  rightButton.setClickable(true);
			  rightButton.setVisibility(View.VISIBLE);
			  currentImage.setVisibility(View.VISIBLE);
			  
			  break;
		  case 7:
			  Bitmap cb = (Bitmap) msg.obj;
			  currentImage.setImageBitmap(cb);
			  break;
		  
			  
		  }
		  super.handleMessage(msg);
	  }
	  
	  
  };
    

    /**
     * 手动触发一次定位请求
     */
    public void requestLocClick(){
    	isRequest = true;
        mLocClient.requestLocation();
        Toast.makeText(LocationOverlayDemo.this, "正在定位……", Toast.LENGTH_SHORT).show();
    }
    /**
     * 修改位置图标
     * @param marker
     */
    public void modifyLocationOverlayIcon(Drawable marker){
    	//当传入marker为null时，使用默认图标绘制
    	myLocationOverlay.setMarker(marker);
    	//修改图层，需要刷新MapView生效
    	//createPaopao();
    	mMapView.refresh();
    }
    /**
	 * 创建弹出泡泡图层
	 */
	public void createPaopao(){
		viewCache = getLayoutInflater().inflate(R.layout.custom_text_view, null);
        popupText =(TextView) viewCache.findViewById(R.id.textcache);
        //泡泡点击响应回调
        PopupClickListener popListener = new PopupClickListener(){
			@Override
			public void onClickedPopup(int index) {
				Log.v("click", "clickapoapo");
			}
        };
        pop = new PopupOverlay(mMapView,popListener);
        
       // MyLocationMapView.pop = pop;
	}
	/**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {
    	
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return ;
            System.out.println("333");
            locData.latitude = location.getLatitude();
            locData.longitude = location.getLongitude();
            //如果不显示定位精度圈，将accuracy赋值为0即可
            locData.accuracy = location.getRadius();
            System.out.println("我的精度为: "+location.getRadius());
            locData.direction = location.getDerect();
            //更新定位数据
            myLocationOverlay.setData(locData);
           
            //更新图层数据执行刷新后生效
            mMapView.refresh();
            
            System.out.println("mMapView.getOverlays()22: "+mMapView.getOverlays());
            //是手动触发请求或首次定位时，移动到定位点
            if (isRequest || isFirstLoc){
            	//移动地图到定位点
                mMapController.animateTo(new GeoPoint((int)(locData.latitude* 1e6), (int)(locData.longitude *  1e6)));
               // mMapView.
                isRequest = false;
            }
            //首次定位完成
            isFirstLoc = false;
        }
        
        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null){
                return ;
            }
        }
    }
    
    //继承MyLocationOverlay重写dispatchTap实现点击处理
  	public class locationOverlay extends MyLocationOverlay{

  		public locationOverlay(MapView mapView) {
  			super(mapView);
  			// TODO Auto-generated constructor stub
  		}
  		@Override
  		protected boolean dispatchTap() {
  			// TODO Auto-generated method stub
  			//处理点击事件,弹出泡泡
  			popupText.setBackgroundResource(R.drawable.popup);
			popupText.setText("我的位置");
			pop.showPopup(BMapUtil.getBitmapFromView(popupText),
					new GeoPoint((int)(locData.latitude*1e6), (int)(locData.longitude*1e6)),
					8);
  			return true;
  		}
  		
  	}

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	System.out.println("Onstop");
    }
    
    @Override
    protected void onResume() {
        mMapView.onResume();

        super.onResume();
    }
    
    @Override
    protected void onDestroy() {
    	//退出时销毁定位
        if (mLocClient != null)
            mLocClient.stop();
        mMapView.destroy();
        super.onDestroy();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	mMapView.onSaveInstanceState(outState);
    	
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	super.onRestoreInstanceState(savedInstanceState);
    	mMapView.onRestoreInstanceState(savedInstanceState);
    }
    
    

    public void initialSearch(){
    	searchModel = new MKSearch();
        //设置路线策略为最短距离
        searchModel.setDrivingPolicy(MKSearch.ECAR_DIS_FIRST);
        searchModel.init(mBMapManager, new MKSearchListener() {
			//获取驾车路线回调方法
			@Override
			public void onGetDrivingRouteResult(MKDrivingRouteResult res, int error) {
				// 错误号可参考MKEvent中的定义
				if (error != 0 || res == null) {
					Toast.makeText(LocationOverlayDemo.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
					return;
				}
				RouteOverlay routeOverlay = new RouteOverlay(LocationOverlayDemo.this, mMapView);
				
			    // 此处仅展示一个方案作为示例
				MKRoute route = res.getPlan(0).getRoute(0);
				int distanceM = route.getDistance();
				String distanceKm = String.valueOf(distanceM / 1000) +"."+String.valueOf(distanceM % 1000);
				System.out.println("距离:"+distanceKm+"公里---节点数量:"+route.getNumSteps());
				for (int i = 0; i < route.getNumSteps(); i++) {
					MKStep step = route.getStep(i);
					System.out.println("节点信息："+step.getContent());
				}
			    routeOverlay.setData(route);
			    //mMapView.getOverlays().clear();
			    mMapView.getOverlays().add(routeOverlay);
			    mMapView.refresh();
			    
			    System.out.println("mMapView.getOverlays()33: "+mMapView.getOverlays());
			    mMapView.getController().animateTo(res.getStart().pt);
			}
			
			//以下两种方式和上面的驾车方案实现方法一样
			@Override
			public void onGetWalkingRouteResult(MKWalkingRouteResult res, int error) {
				//获取步行路线
				if (error != 0 || res == null) {
					Toast.makeText(LocationOverlayDemo.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
					return;
				}
				RouteOverlay routeOverlay = new RouteOverlay(LocationOverlayDemo.this, mMapView);
				
			    // 此处仅展示一个方案作为示例
				MKRoute route = res.getPlan(0).getRoute(0);
				int distanceM = route.getDistance();
				String distanceKm = String.valueOf(distanceM / 1000) +"."+String.valueOf(distanceM % 1000);
				System.out.println("距离:"+distanceKm+"公里---节点数量:"+route.getNumSteps());
				String s ="";
				for (int i = 0; i < route.getNumSteps(); i++) {
					MKStep step = route.getStep(i);
					s+=step.getContent()+"\n";
					System.out.println("节点信息："+step.getContent());
				}
				new AlertDialog.Builder(LocationOverlayDemo.this)
				.setTitle("导航路线").setMessage("距离:"+distanceKm+"公里"+"\n+"+s)
				.setPositiveButton("查看地图线路", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				})
				.create().show();
			    routeOverlay.setData(route);
			  //  mMapView.getOverlays().clear();
			    mMapView.getOverlays().add(routeOverlay);
			    mMapView.invalidate();
			    System.out.println("mMapView.getOverlays()44: "+mMapView.getOverlays());
			    mMapView.getController().animateTo(res.getStart().pt);
			}
			
			@Override
			public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
				//获取公交线路
			}
			
			@Override
			public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
			}
			@Override
			public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
			}
			@Override
			public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
			}
			@Override
			public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {
			}

			@Override
			public void onGetPoiDetailSearchResult(int arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1,
					int arg2) {
				// TODO Auto-generated method stub
				
			}
		});
    	
    	
    }
    
    public void getDiscountInformation(){
    	
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mLbsPoints = new ArrayList<CustomPoiInfo>();
		    	
		    	System.out.println("进入getDiscountInformation函数");
		    	 String resultStr="";
		    	 HttpClient httpclient = new DefaultHttpClient();
		    	 List<NameValuePair> nameValuePairs =new ArrayList<NameValuePair>();
		    	 //?geotable_id=31937&ak=C8e5f84b57555c9e0478c87526b878c0
		         nameValuePairs.add(new BasicNameValuePair("geotable_id","31937"));
		         nameValuePairs.add(new BasicNameValuePair("ak",GlobalParameter.browerKey));
		         HttpGet httpget = new HttpGet(Get_URL);
		         try {
		        	 //httpget.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		            HttpResponse response; 
		            response=httpclient.execute(httpget);
		            if (response.getStatusLine().getStatusCode() == 200) {
		                // 获取返回的数据
		            	resultStr = EntityUtils.toString(response.getEntity(), "UTF-8");
		                Log.i("HttpGet", "HttpGet方式请求成功，返回数据如下：");
		              //  System.out.println("请求结果:"+resultStr.toString());
		                JSONObject demoJson = new JSONObject(resultStr);

		                JSONArray numberList = demoJson.getJSONArray("pois");



		                for(int i=0; i<numberList.length(); i++){

		                      //获取数组中的数组
		                	  CustomPoiInfo currentPoi= new CustomPoiInfo();
		                	  String latAndLog = numberList.getJSONObject(i).get("location").toString().replace("[", "").replace("]", ""); 
		                      String latitude = latAndLog.split(",")[1];
		                      String logitude = latAndLog.split(",")[0];
		                	  System.out.println("location:"+numberList.getJSONObject(i).get("location"));
		                      System.out.println("address:"+numberList.getJSONObject(i).get("address"));
		                      System.out.println("latitude:"+latitude);
		                      System.out.println("logitude:"+logitude);
		                      System.out.println("discountsName:"+"");
		                      currentPoi.latitude = Float.parseFloat(latitude);
		                      currentPoi.longitude = Float.parseFloat(logitude);
		                      currentPoi.address = numberList.getJSONObject(i).get("address").toString();
		                     // currentPoi.name = numberList.getJSONObject(i).get("discountsName").toString();
		                      currentPoi.name = "FindDiscount";
		                      mLbsPoints.add(currentPoi);
		                }
		                
		                CloudOverlay poiOverlay = new CloudOverlay(LocationOverlayDemo.this, mMapView);
		                poiOverlay.setData(mLbsPoints);
		               // mMapView.getOverlays().clear();
		                mMapView.getOverlays().add(poiOverlay);
		                System.out.println("mMapView.getOverlays()55: "+mMapView.getOverlays());
		                mMapView.refresh ();
		                if(mLbsPoints!=null&&mLbsPoints.size()!=0){
		                mMapView.getController().animateTo(new GeoPoint((int)(mLbsPoints.get(0).latitude * 1e6), (int)(mLbsPoints.get(0).longitude * 1e6)));
		                }
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
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
	public void initEngineManager(Context context) {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }

        if (!mBMapManager.init(GlobalParameter.appKey,new MyGeneralListener())) {
            Toast.makeText(LocationOverlayDemo.this, 
                    "BMapManager  初始化错误!", Toast.LENGTH_LONG).show();
        }
	}
	
	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
    static class MyGeneralListener implements MKGeneralListener {
        
        @Override
        public void onGetNetworkState(int iError) {
            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
//                Toast.makeText(LocationOverlayDemo.this, "您的网络出错啦！",
//                    Toast.LENGTH_LONG).show();
                
                      System.out.println("BMapManager  初始化错误!");  
            }
            else if (iError == MKEvent.ERROR_NETWORK_DATA) {
//                Toast.makeText(LocationOverlayDemo.getInstance().getApplicationContext(), "输入正确的检索条件！",
//                        Toast.LENGTH_LONG).show();
            	System.out.println("输入正确的检索条件！");  
            }
        }

        @Override
        public void onGetPermissionState(int iError) {
            if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
                //授权Key错误：
//                Toast.makeText(LocationOverlayDemo.getInstance().getApplicationContext(), 
//                        "请在 DemoApplication.java文件输入正确的授权Key！", Toast.LENGTH_LONG).show();
//                LocationOverlayDemo.getInstance().m_bKeyRight = false;
            	System.out.println("不正确的授权Key！");  
            }
        }
    }
    
    
    /**
     * 更新状态显示 
     */
    public void updateView(){
    	localMapList = mOffline.getAllUpdateInfo();
        if ( localMapList == null ){
            localMapList = new ArrayList<MKOLUpdateElement>();	
        }
    	lAdapter.notifyDataSetChanged();
    }
    
    public  String formatDataSize(int size) {
        String ret = "";
        if (size < (1024 * 1024)) {
            ret = String.format("%dK", size / 1024);
        } else {
            ret = String.format("%.1fM", size / (1024 * 1024.0));
        }
        return ret;
    }
    
    
	private final double EARTH_RADIUS = 6378137.0;  
	private double calculateDistance(double lat_a, double lng_a, double lat_b, double lng_b) { //根据两点的精度和纬度计算距离
	       double radLat1 = (lat_a * Math.PI / 180.0);
	       double radLat2 = (lat_b * Math.PI / 180.0);
	       double a = radLat1 - radLat2;
	       double b = (lng_a - lng_b) * Math.PI / 180.0;
	       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
	              + Math.cos(radLat1) * Math.cos(radLat2)
	              * Math.pow(Math.sin(b / 2), 2)));
	       s = s * EARTH_RADIUS;
	       s = Math.round(s * 10000) / 10000;
	       System.out.println("距离:  "+""+s);
	       return s;
	    }
	
	
	public void queryShops(final String url,final String type){ //查询附近或搜索出来的打折商家信息
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
		    	 String resultStr="";
		    	 HttpClient httpclient = new DefaultHttpClient();
		         HttpGet httpget = new HttpGet(url
		    	 );
		         try {
		        	 //httpget.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		            HttpResponse response; 
		            response=httpclient.execute(httpget);
		            if (response.getStatusLine().getStatusCode() == 200) {
		                // 获取返回的数据
		            	resultStr = EntityUtils.toString(response.getEntity(), "UTF-8");
		                Log.i("HttpGet", "搜索请求成功，返回数据如下：");
		                System.out.println("请求结果:"+resultStr.toString());
		                JSONObject demoJson = new JSONObject(resultStr);
		                String numList = demoJson.get("shopids").toString();
		                String a[] = numList.split(" ");
//		                System.out
//								.println("a[0]:"+a[0]+"a[1]:"+a[1]);
		                int size = a.length;
		                System.out
								.println("size:"+size);
		               // String a[] = numList.getJSONObject(0).getString("ids").split(" ");

		                JSONArray numberList = demoJson.getJSONArray("content");
		                
		                m_Discount d;
		                m_Shop s;
		                List<List<m_Discount>> ds = new ArrayList<List<m_Discount>>(size);
		                for(int k=0;k<size;k++){
		                	ds.add(new ArrayList<m_Discount>());
		                }
		                List<m_Shop> ss = new ArrayList<m_Shop>(size);
		                for(int k=0;k<size;k++){
		                	ss.add(new m_Shop());
		                }
		                
		                for(int i=0; i<numberList.length(); i++){
		                	

		                      //获取数组中的数组
		                	int m_discount_id = numberList.getJSONObject(i).getInt("discount_id");
		                    String m_discount_good = numberList.getJSONObject(i).getString("discount_good");
		                    int m_discount_shop = numberList.getJSONObject(i).getInt("discount_shop");
		                    String m_discount_deadline = numberList.getJSONObject(i).getString("discount_deadline");
		                    String m_discount_picture =numberList.getJSONObject(i).getString("discount_picture");
		                    String m_create_time = numberList.getJSONObject(i).getString("create_time");
		                    String m_discount_type = numberList.getJSONObject(i).getString("discount_type");
		                    String m_discount_description =numberList.getJSONObject(i).getString("discount_description");
		                    int m_shop_id = numberList.getJSONObject(i).getInt("shop_id");
		                    String m_shop_name = numberList.getJSONObject(i).getString("shop_name");
		                    float m_shop_longitude =Float.parseFloat(numberList.getJSONObject(i).getString("shop_longitude"));
		                    float m_shop_latitude = Float.parseFloat(numberList.getJSONObject(i).getString("shop_latitude"));
		                    String m_shop_type =  numberList.getJSONObject(i).getString("shop_type");
		                    int m_shop_owner =numberList.getJSONObject(i).getInt("shop_owner");
		                    String m_shop_pic =numberList.getJSONObject(i).getString("shop_pic");
		                    String m_shop_addr =numberList.getJSONObject(i).getString("shop_addr");
		                    String m_shop_tags =numberList.getJSONObject(i).getString("shop_tags");
		                	  System.out.println("shop_name:"+numberList.getJSONObject(i).get("shop_name"));
		                      System.out.println("shop_latitude:"+numberList.getJSONObject(i).get("shop_latitude"));
		                      
		                      System.out.println("shop_longitude:"+numberList.getJSONObject(i).get("shop_longitude"));
		                       d = new m_Discount();
		                      s = new m_Shop();
		                      for(int j=0;j<size;j++){
		                      if(numberList.getJSONObject(i).getInt("shop_id")==Integer.parseInt(a[j])){
		                    	  d.setM_create_time(m_create_time);
		                          d.setM_discount_deadline(m_discount_deadline);
			                      d.setM_discount_description(m_discount_description);
			                      d.setM_discount_good(m_discount_good);
			                      d.setM_discount_id(m_discount_id);
			                      d.setM_discount_picture(m_discount_picture);
			                      d.setM_discount_shop(m_discount_shop);
			                      d.setM_discount_type(m_discount_type);
			                      s.setM_shop_addr(m_shop_addr);
			                      s.setM_shop_id(m_shop_id);
			                      s.setM_shop_latitude(m_shop_latitude);
			                      s.setM_shop_longitude(m_shop_longitude);
			                      s.setM_shop_name(m_shop_name);
			                      s.setM_shop_owner(m_shop_owner);
			                      s.setM_shop_pic(m_shop_pic);
			                      s.setM_shop_tags(m_shop_tags);
			                      s.setM_shop_type(m_shop_type);
		                    	  ds.get(j).add(d);
		                    	  
		                    	  ss.set(j, s);
		                          break;
		                      }
		                      }
		                      

		                }
		                for(int i=0;i<size;i++){
		                	ss.get(i).setDiscounts(ds.get(i));
		                }
		                if(type.equals("search")){
		                	jp.setShops(ss);
			                
			                for(int i=0;i<size;i++){
			                	System.out
										.println(jp.getShops().get(i).getM_shop_name()+jp.getShops().get(i).getM_shop_id()+jp.getShops().get(i).getM_shop_addr()+"---\n");
			                	                for(int j=0;j<jp.getShops().get(i).getDiscounts().size();j++){
			                	                	System.out
															.println(""+jp.getShops().get(i).getDiscounts().get(j).getM_discount_id()+"分隔符");
			                	                }
												
			                }
			                searchResult.clear();
			                for(int i=0;i<jp.shops.size();i++){
								
								Map<String,Object> map = new HashMap<String, Object>();
	                    		map.put("discount",jp.shops.get(i).getM_shop_name());
	                    		searchResult.add(map);
							
						}
			                
			                handler.sendEmptyMessage(0);
		                }
		                
		                else if(type.equals("nearBy")){
		                	nearByJp.setShops(ss);
		                	 for(int i=0;i<size;i++){
				                	System.out
											.println(nearByJp.getShops().get(i).getM_shop_name()+nearByJp.getShops().get(i).getM_shop_id()+nearByJp.getShops().get(i).getM_shop_addr()+"---\n");
				                	                for(int j=0;j<nearByJp.getShops().get(i).getDiscounts().size();j++){
				                	                	System.out
																.println(""+nearByJp.getShops().get(i).getDiscounts().get(j).getM_discount_id()+"分隔符");
				                	                }
													
				                }
		                	 discount.clear();
				                for(int i=0;i<nearByJp.shops.size();i++){
									
									Map<String,Object> map = new HashMap<String, Object>();
		                    		map.put("discount",nearByJp.shops.get(i).getM_shop_name());
		                    		discount.add(map);
								
							}
				                
				                handler.sendEmptyMessage(1);
		                }
		                
						//searchAdapter.notifyDataSetChanged();

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
    
    
    
    public class LocalMapAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return localMapList.size();
		}

		@Override
		public Object getItem(int index) {
			return localMapList.get(index);
		}

		@Override
		public long getItemId(int index) {
			return index;
		}

		@Override
		public View getView(int index, View view, ViewGroup arg2) {
			MKOLUpdateElement e = (MKOLUpdateElement) getItem(index);
		    view = View.inflate(LocationOverlayDemo.this, R.layout.offline_localmap_list,null);
		    initViewItem(view,e);
			return view;
		}
		
		void initViewItem(View view , final MKOLUpdateElement e){
			Button display = (Button)view.findViewById(R.id.display);
			Button remove  = (Button)view.findViewById(R.id.remove); 
			TextView title = (TextView)view.findViewById(R.id.title);
			TextView update = (TextView)view.findViewById(R.id.update);
			TextView ratio = (TextView)view.findViewById(R.id.ratio);
			ratio.setText(e.ratio+"%");
			title.setText(e.cityName);
			if ( e.update){
				update.setText("可更新");
			}
			else{
				update.setText("最新");
			}
			if ( e.ratio != 100 ){
				display.setEnabled(false);
			}
			else{
				display.setEnabled(true);
			}
			remove.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {				
					 mOffline.remove(e.cityID);
                     updateView();
				}
			});
			display.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					
					
					ad.dismiss();
					ad=null;
					ab=null;
					
					mMapController.animateTo(new GeoPoint((int)(e.geoPt.getLatitudeE6()), (int)(e.geoPt.getLongitudeE6())));	

				}
			});
		}
		
	}
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	// TODO Auto-generated method stub
    	 if(keyCode == KeyEvent.KEYCODE_BACK){
//    		 if(ab!=null)
//    			 ab=null;
//    		 else if(ad.isShowing())
//    		 {
//    			 ad.dismiss();
//    			 ad=null;
//    		 }
    	 }
    	return super.onKeyDown(keyCode, event);
    }

	@Override
	public void onGetOfflineMapState(int type, int state) {
		// TODO Auto-generated method stub
		switch (type) {
		case MKOfflineMap.TYPE_DOWNLOAD_UPDATE:
			{
				MKOLUpdateElement update = mOffline.getUpdateInfo(state);
				//处理下载进度更新提示
				if ( update != null ){
				    ratio.setText( "当前下载进度:"+update.ratio);
				    updateView();
				}
			}
			break;
		case MKOfflineMap.TYPE_NEW_OFFLINE:
			//有新离线地图安装
			Log.d("OfflineDemo", String.format("add offlinemap num:%d", state));
			break;
		case MKOfflineMap.TYPE_VER_UPDATE:
		    // 版本更新提示
            	//MKOLUpdateElement e = mOffline.getUpdateInfo(state);
			
			break;
		}
		
	}

}


