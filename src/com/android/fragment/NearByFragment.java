package com.android.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;

import com.android.application.MyApplication;
import com.android.testbaiduapi.R;
import com.android.testbaiduapi.m_Discount;
import com.android.testbaiduapi.m_Shop;
import com.android.utils.DownLoadNearByBitmaps;
import com.androidui.DiscountInfoActivity;
import com.androidui.MiddleViewPageAdapter;

public class NearByFragment extends Fragment{
	
	private ViewPager vp =null ;
	private MiddleViewPageAdapter vpAdapter=null;
	ArrayList<Map<String, Object>> lstImageItem = null;
	ArrayList<Map<String, Object>> distanceWithId = null;
	SimpleAdapter saImageItems = null;
	List<m_Discount> allDiscounts = null;
	
	
	//类别
	private GridView class_gridview =null; 
	public static NearByFragment newInstance(){
		NearByFragment detail= new NearByFragment();
		
  	    return detail;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	     super.onActivityCreated(savedInstanceState);
	   
		
	     
	 
	}
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg){
			 List<Integer> ids = new ArrayList<Integer>(); 
			 List<String> urls = new ArrayList<String>();
			  List<Integer> positions = new ArrayList<Integer>();
			switch(msg.what){
			
			case 0:
				
				int position = (Integer)msg.obj;
				if(position==0){
					ids.clear();
					urls.clear();
					positions.clear();
					for(int i=0;i<4;i++){
						ids.add(allDiscounts.get(position+i).getM_discount_id());
						urls.add(allDiscounts.get(position+i).getM_discount_picture());
						positions.add(position+i);
					}
					
				}
				else {
					ids.clear();
					urls.clear();
					positions.clear();
					if(position+4<=saImageItems.getCount()){
						for(int i=2;i<4;i++){
							ids.add(allDiscounts.get(position+i).getM_discount_id());
							urls.add(allDiscounts.get(position+i).getM_discount_picture());
							positions.add(position+i);
						}
						
						if(position+5==saImageItems.getCount()){  //下载最后的图
							if(position+6==saImageItems.getCount()){
								for(int i=4;i<6;i++){
									ids.add(allDiscounts.get(position+i).getM_discount_id());
									urls.add(allDiscounts.get(position+i).getM_discount_picture());
									positions.add(position+i);
								}
								
							}
							else{
						
								ids.add(allDiscounts.get(position+4).getM_discount_id());
								urls.add(allDiscounts.get(position+4).getM_discount_picture());
								positions.add(position+4);
							}
							}
					}
				}
					
				
				((MyApplication)getActivity().getApplication()).positions = positions;
				class_gridview.getItemAtPosition(position);
				DownLoadNearByBitmaps db = new DownLoadNearByBitmaps(getActivity(), getActivity(), ids, urls, handler,positions);
				
				db.execute();
			
				break;
			case 1:
				break;
			case 2:
//				Bundle b = msg.getData();
//				int downloadType = b.getInt("downloadType");
				System.out.println("已经进入case2"+"====");
				
				List<String> goodsPicPaths = (List<String>)msg.obj;
				System.out.println("size：  "+goodsPicPaths.size()+"\n");
				for(int i=0;i<goodsPicPaths.size();i++){
					
					HashMap<String,Object> h = new HashMap<String, Object>();
					BitmapFactory.Options opts = new BitmapFactory.Options();
					opts.inJustDecodeBounds = true;
					BitmapFactory.decodeFile(goodsPicPaths.get(i), opts);
					             
					opts.inSampleSize = ((MyApplication)getActivity().getApplication()).computeSampleSize(opts, -1, 128*128);       
					opts.inJustDecodeBounds = false;
			        Bitmap bm;
//			        BitmapFactory.Options opt = new BitmapFactory.Options();
//			        opt.inJustDecodeBounds = true;
//			        opt.inSampleSize = ((MyApplication)getActivity().getApplication()).computeSampleSize(opt, -1, 128*128);
			        bm = BitmapFactory.decodeFile(goodsPicPaths.get(i),opts);
					h.put("ItemImage",bm);
					h.put("ItemText", lstImageItem.get(((MyApplication)getActivity().getApplication()).positions.get(i)).get("ItemText"));
					h.put("ItemText2", lstImageItem.get(((MyApplication)getActivity().getApplication()).positions.get(i)).get("ItemText2"));
					
					lstImageItem.set(((MyApplication)getActivity().getApplication()).positions.get(i),  h);
					//bm.recycle();
					//opt.inJustDecodeBounds = false;
					System.out.println("positions:"+((MyApplication)getActivity().getApplication()).positions);
				}
				
				saImageItems.notifyDataSetChanged();
				
				break;
			}
			super.handleMessage(msg);
		}
		
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.nearby, container,false);
		
		class_gridview = (GridView)view.findViewById(R.id.nearby_gridview) ;

		 OrderByDistance();
	      
	      //添加消息处理  
	      class_gridview.setOnItemClickListener(new ItemClickListener());  
	      class_gridview.setOnScrollListener(new ScrollListener());
	  //    handler.sendEmptyMessage(0);
	      saImageItems = new SimpleAdapter(getActivity(), // 没什么解释
					lstImageItem,// 数据来源
					R.layout.nearby_grid_good_item,// night_item的XML实现

					// 动态数组与ImageItem对应的子项
					new String[] { "ItemImage", "ItemText", "ItemText2" },

					// ImageItem的XML文件里面的一个ImageView,两个TextView ID
					new int[] { R.id.grid_item_image,
							R.id.grid_item_discount_type,
							R.id.grid_item_shop_name });
//	      saImageItems = new DiscountBaseAdapter(getActivity(), getActivity(), lstImageItem, GlobalParameter.SECOND_PAGE);
			// 添加并且显示
			class_gridview.setAdapter(saImageItems);
			saImageItems.setViewBinder(new ViewBinder() {
				public boolean setViewValue(View arg0, Object arg1,
						String textRepresentation) {
					if ((arg0 instanceof ImageView) & (arg1 instanceof Bitmap)) {
						ImageView imageView = (ImageView) arg0;
						Bitmap bitmap = (Bitmap) arg1;
						imageView.setImageBitmap(bitmap);
						return true;
					} else {
						return false;
					}

				}
			});
			class_gridview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					Intent intent  = new Intent(getActivity(),DiscountInfoActivity.class);
					intent.putExtra("discountId", allDiscounts.get(position).getM_discount_id());
					intent.putExtra("shopId", allDiscounts.get(position).getM_discount_shop());
					startActivity(intent);
				}
			});


		return view;
	  } 
	//当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件  
	class ItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0,View arg1,int arg2,long arg3) {
			// 在本例中arg2=arg3
			HashMap<String, Object> item = (HashMap<String, Object>) arg0
					.getItemAtPosition(arg2);
			// 显示所选Item的ItemText
			// setTitle((String)item.get("ItemText"));
		}

	}
	
	class ScrollListener implements OnScrollListener{

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			// 不滚动时保存当前滚动到的位置
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
				int mPosition = class_gridview.getFirstVisiblePosition();
				System.out.println("当前位置："+mPosition+"-------------");
				Message msg = handler.obtainMessage();
				msg.what=0;
				msg.obj = mPosition;
				handler.sendMessage(msg);
			}
		}
		
	}
	
	
	public void OrderByDistance(){
		lstImageItem = new ArrayList<Map<String, Object>>(); 
		distanceWithId = new ArrayList<Map<String, Object>>(); 
		allDiscounts =new ArrayList<m_Discount>();
		m_Shop temp = new m_Shop();
		List<m_Shop> allShops = ((MyApplication)getActivity().getApplication()).getAllShops();  //默认按距离排序
	
		for(int i=0;i<allShops.size();i++){  //排序
			for(int j=i+1;j<allShops.size();j++){
				 
				double distance = ((MyApplication)getActivity().getApplication()).calculateDistance(allShops.get(i).getM_shop_latitude(), allShops.get(i).getM_shop_longitude()
						, ((MyApplication)getActivity().getApplication()).getCurrentLat(), ((MyApplication)getActivity().getApplication()).getCurrentLng());
				double distance2 = ((MyApplication)getActivity().getApplication()).calculateDistance(allShops.get(j).getM_shop_latitude(), allShops.get(j).getM_shop_longitude()
						, ((MyApplication)getActivity().getApplication()).getCurrentLat(), ((MyApplication)getActivity().getApplication()).getCurrentLng());
				if(distance>distance2){
					temp = allShops.get(i);
					allShops.set(i, allShops.get(j));
					allShops.set(j, temp);
				}
			}
		}
		
		for (int k = 0; k < allShops.size(); k++) {   //创建id与distance和name的哈希索引
			HashMap<String, Object> map = new HashMap<String, Object>();

			int id = allShops.get(k).getM_shop_id();
			double tempDistance = ((MyApplication) getActivity()
					.getApplication()).calculateDistance(allShops.get(k)
					.getM_shop_latitude(), allShops.get(k)
					.getM_shop_longitude(), ((MyApplication) getActivity()
					.getApplication()).getCurrentLat(),
					((MyApplication) getActivity().getApplication())
							.getCurrentLng());
			map.put(id + "distance", tempDistance);
			map.put(id + "name", allShops.get(k).getM_shop_name());
			distanceWithId.add(map);
		}
	//	List<m_Discount> discountsOrderByDistance = new ArrayList<m_Discount>();
		for(int i=0;i<allShops.size();i++){
			for(int j=0;j<allShops.get(i).getDiscounts().size();j++){
				allDiscounts.add(allShops.get(i).getDiscounts().get(j));
				 HashMap<String, Object> map = new HashMap<String, Object>();  
				 Bitmap bm = drawableToBitmap(getResources().getDrawable(R.drawable.discountsbar_logo));
			        map.put("ItemImage", bm);//添加图像资源的ID  
			        map.put("ItemText", allShops.get(i).getDiscounts().get(j).getM_discount_type());//按序号做ItemText  
			        map.put("ItemText2", distanceWithId.get(i).get(allShops.get(i).getM_shop_id()+"distance"));//
			        lstImageItem.add(map); 
			        
				//discountsOrderByDistance.add(allShops.get(i).getDiscounts().get(j));
			}
		}

	}
	
	public  Bitmap drawableToBitmap(Drawable drawable) {    
	    /* 
	     * Drawable转化为Bitmap  
	    */  
	    int width = drawable.getIntrinsicWidth();    
	    int height = drawable.getIntrinsicHeight();    
	    Bitmap bitmap = Bitmap.createBitmap(width, height,    
	                                  drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888: Bitmap.Config.RGB_565);    
	    Canvas canvas = new Canvas(bitmap);    
	    drawable.setBounds(0,0,width,height);    
	    drawable.draw(canvas);    
	    return bitmap;    
	 }   
}
