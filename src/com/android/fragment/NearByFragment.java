package com.android.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.android.testbaiduapi.R;
import com.androidui.MiddleViewPageAdapter;

public class NearByFragment extends Fragment{
	
	private ViewPager vp =null ;
	private MiddleViewPageAdapter vpAdapter=null;
	
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
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.nearby, container,false);
		
		class_gridview = (GridView)view.findViewById(R.id.nearby_gridview) ;
//		class_gridview.setAdapter(new SimpleAdapter(getActivity(), data, resource, from, to))		     

	     
		
	    //生成动态数组，并且转入数据  
	      ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();  
	      for(int i=0;i<10;i++)  
	      {  
	        HashMap<String, Object> map = new HashMap<String, Object>();  
	        map.put("ItemImage", R.drawable.discountsbar_logo);//添加图像资源的ID  
	    map.put("ItemText", "NO."+String.valueOf(i));//按序号做ItemText  
	        lstImageItem.add(map);  
	      }  
	      //生成适配器的ImageItem <====> 动态数组的元素，两者一一对应  
//	      new SimpleAdapter(context, data, resource, from, to)
	   
	      int res = R.layout.nearby_grid_good_item;
	      SimpleAdapter saImageItems = new SimpleAdapter(getActivity(), //没什么解释  
	                                                lstImageItem,//数据来源   
	                                                res,//night_item的XML实现  
	                                                  
	                                                //动态数组与ImageItem对应的子项          
	                                                new String[] {"ItemImage","ItemText"},   
	                                                  
	                                                //ImageItem的XML文件里面的一个ImageView,两个TextView ID  
	                                                new int[] {R.id.grid_item_image,R.id.grid_item_discount_type});  
	      //添加并且显示  
	      class_gridview.setAdapter(saImageItems);  
	      //添加消息处理  
	      class_gridview.setOnItemClickListener(new ItemClickListener());  

	    
	 	return view;
	  } 
	//当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件  
	class  ItemClickListener implements OnItemClickListener  
	{  
	public void onItemClick(AdapterView<?> arg0,//The AdapterView where the click happened   
	                                View arg1,//The view within the AdapterView that was clicked  
	                                int arg2,//The position of the view in the adapter  
	                                long arg3//The row id of the item that was clicked  
	                                ) {  
	  //在本例中arg2=arg3  
	  HashMap<String, Object> item=(HashMap<String, Object>) arg0.getItemAtPosition(arg2);  
	  //显示所选Item的ItemText  
//	  setTitle((String)item.get("ItemText"));  
}  

}
}
