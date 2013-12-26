package com.android.fragment;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification.Style;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.application.MyApplication;
import com.android.testbaiduapi.JsonParser;
import com.android.testbaiduapi.R;
import com.android.testbaiduapi.m_Discount;
import com.android.testbaiduapi.m_Shop;
import com.android.utils.DiscountBaseAdapter;
import com.android.utils.DiscountsListView;
import com.android.utils.GlobalParameter;
import com.androidui.DiscountInfoActivity;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKEvent;

public class NewDiscountFragment extends Fragment {
	private static final int TAB_INDEX_DISCOUNT = 0;
	private static final int TAB_INDEX_SHOP = 1;
	private ProgressDialog pd = null;

	private Integer[] imgs = new Integer[6];
	// 定位相关：
	public MyLocationListenner myListener;
	LocationClient mLocClient;
	LocationData locData;
	JsonParser jp = new JsonParser(); // 存储结果的JsonParser
	private ImageView[] dots;
	// private TabHost mTabHost;
	TextView title = null;
	private List<View> views;
	private DiscountsListView listview = null;
	// private ViewPager vp;
	// private MiddleViewPageAdapter vpAdapter;
	RelativeLayout search_box_layout = null;
	LinearLayout hide = null;
	TextView searchBtn = null;
	EditText search_edit = null;
	TextView edit_delete = null;
	private int currentIndex;
	BMapManager mBMapManager = null;
	LinearLayout ll = null;
	List<Map<String, Object>> list;
	SimpleAdapter adapter = null;
	int clickTime = 0;
	JSONArray numberList = null;
	boolean firstFlag = true; // 第一次定位
	private static double EARTH_RADIUS = 6378137.0;

	private static final String Get_URL = "http://api.map.baidu.com/geodata/v2/poi/list?geotable_id=31937&ak=C8e5f84b57555c9e0478c87526b878c0";// get请求url,从lbs上更新数据并显示到地图上
	private TextWatcher textWatcher = new TextWatcher() {
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			// Log.d("TAG", "afterTextChanged--------------->");
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
			if ("".equals(search_edit.getText().toString().trim())) {
				edit_delete.setVisibility(TextView.INVISIBLE);
			} else {
				edit_delete.setVisibility(TextView.VISIBLE);
			}

		}
	};

	public static NewDiscountFragment newInstance() {
		NewDiscountFragment detail = new NewDiscountFragment();

		return detail;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onActivityCreated(savedInstanceState);
		initEngineManager(getActivity());

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.home, container, false);
		pd = new ProgressDialog(getActivity());
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setCancelable(false);
		
		myListener = new MyLocationListenner();
		mLocClient = new LocationClient(getActivity());
		locData = new LocationData();
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		// option.setOpenGps(true);//打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(5000);
		mLocClient.setLocOption(option);
		mLocClient.start();

		// mLocClient.requestLocation();
		// getDiscountInformation();
		list = new ArrayList<Map<String, Object>>();
		ll = (LinearLayout) view.findViewById(R.id.ll);
		// vp = (ViewPager)view.findViewById(R.id.middleviewpager);
		title = (TextView) view.findViewById(R.id.title);
		title.setText(R.string.app_name);
		hide = (LinearLayout) view.findViewById(R.id.hide);
		title.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				clickTime++;
				if (clickTime % 2 == 1) {
					hide.setVisibility(View.VISIBLE);
				} else
					hide.setVisibility(View.GONE);
				// TODO Auto-generated method stub

			}
		});
		search_box_layout = (RelativeLayout) view
				.findViewById(R.id.search_box_layout);
		edit_delete = (TextView) view.findViewById(R.id.edit_delete);
		searchBtn = (TextView) view.findViewById(R.id.action);
		search_edit = (EditText) view.findViewById(R.id.search_input);
		search_edit.addTextChangedListener(textWatcher);

		searchBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (search_box_layout.getVisibility() == RelativeLayout.VISIBLE) {
					search_box_layout.setVisibility(RelativeLayout.GONE);
				} else {
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

		LayoutInflater inflater2 = LayoutInflater.from(getActivity());
		views.add(inflater.inflate(R.layout.what_new_one, null));
		views.add(inflater.inflate(R.layout.what_new_two, null));
		views.add(inflater.inflate(R.layout.what_new_three, null));
		views.add(inflater.inflate(R.layout.what_new_four, null));

		// 初始化Adapter

		// vpAdapter = new MiddleViewPageAdapter(views, getActivity());
		//
		//
		// vp.setAdapter(this.vpAdapter);
		// initDots();

		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// while(true){
		// try{
		// Thread.sleep(2000);
		// handler.sendEmptyMessage(1);
		// }catch(Exception e){
		// e.printStackTrace();
		// }
		// }
		// }
		// }).start();

		listview = (DiscountsListView) view.findViewById(R.id.home_listview);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), DiscountInfoActivity.class);

				m_Shop ms = jp.getShops().get(position);
				// List<m_Discount> md =
				// jp.getShops().get(position).getDiscounts();

				Bundle mBundle = new Bundle();
				mBundle.putParcelable("shop", ms);
				// mBundle.putParcelableArrayList("list",md);
				// intent.putParcelableArrayListExtra("list", md);

				intent.putExtras(mBundle);

				startActivity(intent);

			}
		});

		return view;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mLocClient.stop();

		mLocClient.unRegisterLocationListener(myListener);
	}

	public void initEngineManager(Context context) {
		if (mBMapManager == null) {
			mBMapManager = new BMapManager(context);
		}

		if (!mBMapManager.init(GlobalParameter.appKey, new MyGeneralListener())) {
			Toast.makeText(getActivity(), "BMapManager  初始化错误!",
					Toast.LENGTH_LONG).show();
		}
	}

	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
	static class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				// Toast.makeText(LocationOverlayDemo.this, "您的网络出错啦！",
				// Toast.LENGTH_LONG).show();

				System.out.println("BMapManager  初始化错误!");
			} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				// Toast.makeText(LocationOverlayDemo.getInstance().getApplicationContext(),
				// "输入正确的检索条件！",
				// Toast.LENGTH_LONG).show();
				System.out.println("输入正确的检索条件！");
			}
		}

		@Override
		public void onGetPermissionState(int iError) {
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				// 授权Key错误：
				// Toast.makeText(LocationOverlayDemo.getInstance().getApplicationContext(),
				// "请在 DemoApplication.java文件输入正确的授权Key！",
				// Toast.LENGTH_LONG).show();
				// LocationOverlayDemo.getInstance().m_bKeyRight = false;
				System.out.println("不正确的授权Key！");
			}
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:

				// int next = (currentIndex + 1) % views.size();

				// vp.setCurrentItem(next);
				// setCurrentDot(next);
				break;
			case 2:
				// adapter.notifyDataSetChanged();
				pd.dismiss();
				DiscountBaseAdapter discountAdapter = new DiscountBaseAdapter(
						getActivity(), getActivity(), list,
						GlobalParameter.FIRST_PAGE);
				listview.setAdapter(discountAdapter);
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

	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			System.out.println("55555555555555555555");
			if (location == null)
				return;
			System.out.println("333");
			double tempLat = 0;
			double tempLon = 0;

			locData.latitude = location.getLatitude();
			locData.longitude = location.getLongitude();

			if ((locData.latitude != tempLat || locData.longitude != tempLon)
					&& firstFlag) {
				tempLat = locData.latitude;
				tempLon = locData.longitude;
				firstFlag = false;
				// getDiscountInformation();
				pd.show();
				queryShops();
				((MyApplication) getActivity().getApplication()).currentLat = locData.latitude;
				((MyApplication) getActivity().getApplication()).currentLng = locData.longitude;
			}
			// 如果不显示定位精度圈，将accuracy赋值为0即可
			System.out.println(" locData.latitude:  " + locData.latitude);
			System.out.println("locData.longitude:  " + locData.longitude);
			locData.accuracy = location.getRadius();
			System.out.println("我的精度为: " + location.getRadius());
			locData.direction = location.getDerect();
			// 更新定位数据

		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

	public void queryShops() { // 查询附近或搜索出来的打折商家信息
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				String queryUrl = "http://discountsbar.sinaapp.com/discounts/getInfo?ak="
						+ GlobalParameter.serverKey
						+ "&longitude="
						+ locData.longitude
						+ "&latitude="
						+ locData.latitude
						+ "&radius=" + "100000";

				String resultStr = "";
				HttpClient httpclient = new DefaultHttpClient();
				HttpGet httpget = new HttpGet(queryUrl);
				try {
					// httpget.setEntity(new
					// UrlEncodedFormEntity(nameValuePairs));
					HttpResponse response;
					response = httpclient.execute(httpget);
					if (response.getStatusLine().getStatusCode() == 200) {
						// 获取返回的数据
						resultStr = EntityUtils.toString(response.getEntity(),
								"UTF-8");
						Log.i("HttpGet", "搜索请求成功，返回数据如下：");
						System.out.println("请求结果:" + resultStr.toString());
						JSONObject demoJson = new JSONObject(resultStr);
						String numList = demoJson.get("shopids").toString();
						String a[] = numList.split(" ");
						// System.out
						// .println("a[0]:"+a[0]+"a[1]:"+a[1]);
						int size = a.length;
						System.out.println("size:" + size);
						// String a[] =
						// numList.getJSONObject(0).getString("ids").split(" ");

						JSONArray numberList = demoJson.getJSONArray("content");

						m_Discount d;
						m_Shop s;
						List<m_Discount> allDiscount = new ArrayList<m_Discount>();
						List<List<m_Discount>> ds = new ArrayList<List<m_Discount>>(
								size);
						for (int k = 0; k < size; k++) {
							ds.add(new ArrayList<m_Discount>());
						}
						List<m_Shop> ss = new ArrayList<m_Shop>(size);
						for (int k = 0; k < size; k++) {
							ss.add(new m_Shop());
						}

						for (int i = 0; i < numberList.length(); i++) {

							// 获取数组中的数组
							int m_discount_id = numberList.getJSONObject(i)
									.getInt("discount_id");
							String m_discount_good = numberList
									.getJSONObject(i)
									.getString("discount_good");
							int m_discount_shop = numberList.getJSONObject(i)
									.getInt("discount_shop");
							String m_discount_deadline = numberList
									.getJSONObject(i).getString(
											"discount_deadline");
							String m_discount_picture = numberList
									.getJSONObject(i).getString(
											"discount_picture");
							String m_create_time = numberList.getJSONObject(i)
									.getString("create_time");
							String m_discount_type = numberList
									.getJSONObject(i)
									.getString("discount_type");
							String m_discount_description = numberList
									.getJSONObject(i).getString(
											"discount_description");
							int m_shop_id = numberList.getJSONObject(i).getInt(
									"shop_id");
							String m_shop_name = numberList.getJSONObject(i)
									.getString("shop_name");
							float m_shop_longitude = Float
									.parseFloat(numberList.getJSONObject(i)
											.getString("shop_longitude"));
							float m_shop_latitude = Float.parseFloat(numberList
									.getJSONObject(i)
									.getString("shop_latitude"));
							String m_shop_type = numberList.getJSONObject(i)
									.getString("shop_type");
							int m_shop_owner = numberList.getJSONObject(i)
									.getInt("shop_owner");
							String m_shop_pic = numberList.getJSONObject(i)
									.getString("shop_pic");
							String m_shop_addr = numberList.getJSONObject(i)
									.getString("shop_addr");
							String m_shop_tags = numberList.getJSONObject(i)
									.getString("shop_tags");
							System.out.println("shop_name:"
									+ numberList.getJSONObject(i).get(
											"shop_name"));
							System.out.println("shop_latitude:"
									+ numberList.getJSONObject(i).get(
											"shop_latitude"));

							System.out.println("shop_longitude:"
									+ numberList.getJSONObject(i).get(
											"shop_longitude"));
							d = new m_Discount();
							s = new m_Shop();
							for (int j = 0; j < size; j++) {
								if (numberList.getJSONObject(i).getInt(
										"shop_id") == Integer.parseInt(a[j])) {
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
									allDiscount.add(d);
									ss.set(j, s);
									break;
								}
							}

						}
						for (int i = 0; i < size; i++) {
							ss.get(i).setDiscounts(ds.get(i));
						}

						jp.setShops(ss);
						((MyApplication) getActivity().getApplication()).jp = jp;
						((MyApplication) getActivity().getApplication()).allShops = ss;
						((MyApplication) getActivity().getApplication()).allDiscount = allDiscount;

						for (int i = 0; i < size; i++) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("discount_name", jp.getShops().get(i)
									.getM_shop_name());

							map.put("discount_description", jp.getShops()
									.get(i).getM_shop_type());

							map.put("discount_distance",
									""
											+ ((MyApplication) getActivity()
													.getApplication())
													.calculateDistance(
															locData.latitude,
															locData.longitude,
															jp.getShops()
																	.get(i)
																	.getM_shop_latitude(),
															jp.getShops()
																	.get(i)
																	.getM_shop_longitude())
											+ "米");
							map.put("app_icon", R.drawable.discountsbar_logo);
							list.add(map);
							// System.out
							// .println(jp.getShops().get(i).getM_shop_name()+jp.getShops().get(i).getM_shop_id()+jp.getShops().get(i).getM_shop_addr()+"---\n");
							// for(int
							// j=0;j<jp.getShops().get(i).getDiscounts().size();j++){
							// System.out
							// .println(""+jp.getShops().get(i).getDiscounts().get(j).getM_discount_id()+"分隔符");
							// }

						}
						handler.sendEmptyMessage(2);

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
