package com.android.application;


import java.util.List;

import android.app.Application;
import android.graphics.BitmapFactory;

import com.android.testbaiduapi.JsonParser;
import com.android.testbaiduapi.m_Discount;
import com.android.testbaiduapi.m_Shop;



public class MyApplication extends Application 
{
	public JsonParser jp  = null; //存储结果的JsonParser
	public List<m_Shop> allShops = null;
	public List<m_Discount> allDiscount = null;
	public double currentLat = 0;
	public double currentLng = 0;
	
	public List<Integer> positions;
	
	
	

	public List<Integer> getPositions() {
		return positions;
	}

	public void setPositions(List<Integer> positions) {
		this.positions = positions;
	}

	public double getCurrentLat() {
		return currentLat;
	}

	public void setCurrentLat(double currentLat) {
		this.currentLat = currentLat;
	}

	public double getCurrentLng() {
		return currentLng;
	}

	public void setCurrentLng(double currentLng) {
		this.currentLng = currentLng;
	}

	public JsonParser getJp() {
		return jp;
	}

	public void setJp(JsonParser jp) {
		this.jp = jp;
	}

	public List<m_Shop> getAllShops() {
		return allShops;
	}

	public void setAllShops(List<m_Shop> allShops) {
		this.allShops = allShops;
	}

	public List<m_Discount> getAllDiscount() {
		return allDiscount;
	}

	public void setAllDiscount(List<m_Discount> allDiscount) {
		this.allDiscount = allDiscount;
	}
	
	private static double EARTH_RADIUS = 6378137.0;  
	
	  public  double calculateDistance(double lat_a, double lng_a, double lat_b, double lng_b) { //根据两点的精度和纬度计算距离
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
	  
	  public static int computeSampleSize(BitmapFactory.Options options,
		        int minSideLength, int maxNumOfPixels) {
		    int initialSize = computeInitialSampleSize(options, minSideLength,
		            maxNumOfPixels);
		 
		    int roundedSize;
		    if (initialSize <= 8) {
		        roundedSize = 1;
		        while (roundedSize < initialSize) {
		            roundedSize <<= 1;
		        }
		    } else {
		        roundedSize = (initialSize + 7) / 8 * 8;
		    }
		 
		    return roundedSize;
		}
	  
	  private static int computeInitialSampleSize(BitmapFactory.Options options,
		        int minSideLength, int maxNumOfPixels) {
		    double w = options.outWidth;
		    double h = options.outHeight;
		 
		    int lowerBound = (maxNumOfPixels == -1) ? 1 :
		            (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
		    int upperBound = (minSideLength == -1) ? 128 :
		            (int) Math.min(Math.floor(w / minSideLength),
		            Math.floor(h / minSideLength));
		 
		    if (upperBound < lowerBound) {
		        // return the larger one when there is no overlapping zone.
		        return lowerBound;
		    }
		 
		    if ((maxNumOfPixels == -1) &&
		            (minSideLength == -1)) {
		        return 1;
		    } else if (minSideLength == -1) {
		        return lowerBound;
		    } else {
		        return upperBound;
		    }
		} 
	

	
	
	
	
	


}
