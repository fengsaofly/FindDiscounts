package com.android.testbaiduapi;

import java.io.File;
import java.util.List;



import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;

public class MyImageAdapter extends BaseAdapter {  //设置画廊显示的适配器
	private Context mContext;
	private List<String> goodsPicPaths;

		public MyImageAdapter(Context context,List<String> goodsPicPaths) {
		mContext = context;
		this.goodsPicPaths = goodsPicPaths;
	}

	public int getCount() { 
		return goodsPicPaths.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		   ImageView view = new ImageView(this.mContext);
		                //获取指定索引的图片的ID
		                String good = goodsPicPaths.get(position);
		                Bitmap bm = null;

						//BitmapFactory.Options optsa = new BitmapFactory.Options();
						//optsa.inSampleSize = 10;
						bm = BitmapFactory.decodeFile(good);
						//bm = ZoomImage.zoomImage(bm, 100, 100);
						view.setImageBitmap(zoomBitmap(bm,128,128)); //设置Bitmap
		            
		                //对ImageView进行布局
		               // view.setLayoutParams(new Gallery.LayoutParams(128,128));
		                //设置ImageView的拉升类型，这里采用居中，您可以尝试不同的类型
		              view.setScaleType(ImageView.ScaleType.FIT_XY);
		               return view;
	}
	
	public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {  
		   int width = bitmap.getWidth();  
		   int height = bitmap.getHeight();  
		   Matrix matrix = new Matrix();  
		   float scaleWidht = ((float) w / width);  
		   float scaleHeight = ((float) h / height);  
		   matrix.postScale(scaleWidht, scaleHeight);  
		   Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);  
		   return newbmp;  
		} 
}
