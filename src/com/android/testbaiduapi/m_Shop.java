package com.android.testbaiduapi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;



  public class m_Shop   implements Parcelable {  //商家
	/**
	 * 
	 */

	List<m_Discount>  discounts = new ArrayList<m_Discount>() ;
	int m_shop_id;
    String m_shop_name;
    float m_shop_longitude;
    float m_shop_latitude;
    String m_shop_type;
    int m_shop_owner;
    String m_shop_pic;
    String m_shop_addr;
    String m_shop_tags;
   public m_Shop(){
    	
    }
    m_Shop(List<m_Discount>  discounts,
	int m_shop_id,
    String m_shop_name,
    float m_shop_longitude,
    float m_shop_latitude,
    String m_shop_type,
    int m_shop_owner,
    String m_shop_pic,
    String m_shop_addr,
    String m_shop_tags){
    	this.discounts = new ArrayList<m_Discount>();
    	this.m_shop_id = m_shop_id;
    	this.m_shop_name = m_shop_name;
    	this.m_shop_longitude = m_shop_longitude;
    	this.m_shop_latitude = m_shop_latitude;
    	this.m_shop_type = m_shop_type;
    	this.m_shop_owner = m_shop_owner;
    	this.m_shop_pic= m_shop_pic;
    	this.m_shop_addr = m_shop_addr;
    	this.m_shop_tags = m_shop_tags;
    	
	
    }
	public List<m_Discount> getDiscounts() {
		return discounts;
	}
	public void setDiscounts(List<m_Discount> discounts) {
		this.discounts = discounts;
	}
	public int getM_shop_id() {
		return m_shop_id;
	}
	public void setM_shop_id(int m_shop_id) {
		this.m_shop_id = m_shop_id;
	}
	public String getM_shop_name() {
		return m_shop_name;
		
		
	}
	public void setM_shop_name(String m_shop_name) {
		this.m_shop_name = m_shop_name;
	}
	public float getM_shop_longitude() {
		return m_shop_longitude;
	}
	public void setM_shop_longitude(float m_shop_longitude) {
		this.m_shop_longitude = m_shop_longitude;
	}
	public float getM_shop_latitude() {
		return m_shop_latitude;
	}
	public void setM_shop_latitude(float m_shop_latitude) {
		this.m_shop_latitude = m_shop_latitude;
	}
	public String getM_shop_type() {
		return m_shop_type;
	}
	public void setM_shop_type(String m_shop_type) {
		this.m_shop_type = m_shop_type;
	}
	public int getM_shop_owner() {
		return m_shop_owner;
	}
	public void setM_shop_owner(int m_shop_owner) {
		this.m_shop_owner = m_shop_owner;
	}
	public String getM_shop_pic() {
		return m_shop_pic;
	}
	public void setM_shop_pic(String m_shop_pic) {
		this.m_shop_pic = m_shop_pic;
	}
	public String getM_shop_addr() {
		return m_shop_addr;
	}
	public void setM_shop_addr(String m_shop_addr) {
		this.m_shop_addr = m_shop_addr;
	}
	public String getM_shop_tags() {
		return m_shop_tags;
	}
	public void setM_shop_tags(String m_shop_tags) {
		this.m_shop_tags = m_shop_tags;
	}
	
	 public static final Parcelable.Creator<m_Shop> CREATOR = new Creator<m_Shop>() {  
	        public m_Shop createFromParcel(Parcel source) {    //%%%%%%注意：create和write要一一对应

	        	m_Shop mBook = new m_Shop();  
	        	mBook.discounts = source.readArrayList(m_Discount.class.getClassLoader());
	            mBook.m_shop_id = source.readInt();  
	            mBook.m_shop_name = source.readString();  
	            mBook.m_shop_longitude = source.readFloat();  
	            mBook.m_shop_latitude = source.readFloat(); 
	            mBook.m_shop_type = source.readString(); 
	            mBook.m_shop_owner = source.readInt(); 
	            mBook.m_shop_pic = source.readString(); 
	            mBook.m_shop_addr = source.readString(); 
	            mBook.m_shop_tags = source.readString(); 
	            return mBook;  
	        }  
	        public m_Shop[] newArray(int size) {  
	            return new m_Shop[size];  
	        }  
	    };  
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel parcel, int arg1) {
		// TODO Auto-generated method stub
		parcel.writeList(discounts);
		parcel.writeInt(m_shop_id); 
		parcel.writeString(m_shop_name); 
		parcel.writeFloat(m_shop_longitude); 
		parcel.writeFloat(m_shop_latitude); 
		parcel.writeString(m_shop_type); 
		parcel.writeInt(m_shop_owner); 
		parcel.writeString(m_shop_pic); 
		
		parcel.writeString(m_shop_addr); 
		parcel.writeString(m_shop_tags); 
  
        
	}
    
}
