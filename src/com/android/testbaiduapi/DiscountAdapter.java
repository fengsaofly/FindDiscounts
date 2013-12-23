package com.android.testbaiduapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class DiscountAdapter extends BaseAdapter{
	
	
	    	private Context con;
	    	private MKSearch m_searchModel;
	    	GeoPoint m_pt;//当前位置
	    	JsonParser jsonPaser;
	    	private LayoutInflater lif;
	    	Handler handler;
	    	private List<Map<String, Object>> alls = new ArrayList<Map<String, Object>>();
	        
	    	// private Handler handler;
	    	
	    	public DiscountAdapter(GeoPoint pt, MKSearch searchModel,Context context,List<Map<String, Object>> alls,JsonParser jp,Handler handler){
	    		this.m_pt = pt;
	    		this.m_searchModel = searchModel;
	    		this.con = context;
	    		this.lif = (LayoutInflater) con.
	        	getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    		this.alls = alls;
	    		this.jsonPaser = jp;
	    		this.handler = handler;
	    	}
	    	
//	    	// handler回发是为了处理每个按钮的点击事件
//	    	public void setHandler(Handler handler){
//	    		this.handler = handler;
//	    	}
	    	
	    	@Override
	    	public int getCount() {
	    		// TODO Auto-generated method stub
	    		return alls.size();
	    	}

	    	@Override
	    	public Object getItem(int position) {
	    		// TODO Auto-generated method stub
	    		return alls.get(position);
	    	}

	    	@Override
	    	public long getItemId(int position) {
	    		// TODO Auto-generated method stub
	    		return position;
	    	}

	    	@Override
	    	public View getView(final int position, View view, ViewGroup parent) {
	    		// TODO Auto-generated method stub
	    		
	    		ViewHolder holder = null;
	    		if (view == null) {
//	    			view = this.lif.inflate(R.layout.discountitem, null);
//	    			holder = new ViewHolder();
//	    			holder.scan = (Button)view.findViewById(R.id.scan);
//	    			holder.daohang = (Button)view.findViewById(R.id.daohang);
//	    			holder.address = (TextView)view.findViewById(R.id.discountitem);
	    			
	    			
	    			view.setTag(holder);
	    		}else{
	    			holder = (ViewHolder)view.getTag();
	    		}
	    		
	    		// 赋值
	    		final Map<String, Object> map = alls.get(position);
	    		String s = "";
	    		
	    		try{
	    			s =map.get("discount").toString();
	    		}catch(NullPointerException e){
	    			//Toast.makeText(con, "无记录", Toast.LENGTH_SHORT).show();
	    		}
	    		if(!s.equals("")){
	    			
	    		
	    		if(s.length()>12)
	    			s = s.substring(0, 12);
	    		holder.address.setText(s);
	    		
	    		if(s.equals("nearBy")||s.equals("search")){
	    			holder.scan.setVisibility(View.GONE);
	    			holder.daohang.setVisibility(View.GONE);
	    		}
	    		else{
	    			holder.scan.setVisibility(View.VISIBLE
	    					);
	    			holder.daohang.setVisibility(View.VISIBLE
	    					);
	    		}
	    		}
    			
				
				// 单击事件
				holder.scan.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//Toast.makeText(con, "你点击了第"+position+"行的查看按钮", Toast.LENGTH_SHORT).show();
						Message msg = handler.obtainMessage();
						msg.what = 2;
						msg.obj = jsonPaser.shops.get(position);
						handler.sendMessage(msg);
					}
					
				});
				
				holder.daohang.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						
						// TODO Auto-generated method stub
						//String destination = map.get("discount").toString();
//						String destination = "春熙路";
						//System.out.println("destination: "+destination);
						
						//设置起始地（当前位置）
						MKPlanNode startNode = new MKPlanNode();
						startNode.pt = m_pt;
						//设置目的地
						MKPlanNode endNode = new MKPlanNode(); 
						//endNode.name = destination; //根据名称搜索
						
						GeoPoint pt =   new GeoPoint((int)(jsonPaser.shops.get(position).getM_shop_latitude()* 1e6), (int)(jsonPaser.shops.get(position).getM_shop_longitude()*  1e6));
						endNode.pt = pt;  //根据经纬度搜索
						
						//展开搜索的城市
						String city = "成都";
//						System.out.println("----"+city+"---"+destination+"---"+pt);
						//m_searchModel.drivingSearch(city, startNode, city, endNode);
						//步行路线
						m_searchModel.walkingSearch(city, startNode, city, endNode);
						handler.sendEmptyMessage(3);
						
						
						//公交路线
//						searchModel.transitSearch(city, startNode, endNode);
						//Toast.makeText(con, "你点击了第"+position+"行的导航按钮", Toast.LENGTH_SHORT).show();
					}
				});
	    		
	    		return view;
	    	}

	    	
	    	/**
	    	 * 
	    	 * 列表项控件集合
	    	 *
	    	 */
	    	public class ViewHolder{
	    		Button scan;//查看按钮
	    		Button daohang;//导航按钮
	    		TextView address;
	    		private void print() {
					// TODO Auto-generated method stub

				}
	    	}
	    }
	    
	    
