package com.android.testbaiduapi;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public  class m_Discount  implements Parcelable{  //打折信息
	int m_discount_id;
    String m_discount_good;
    int m_discount_shop;
    String m_discount_deadline;
    String m_discount_picture;
    String m_create_time;
    String m_discount_type;
    String m_discount_description;
    public m_Discount(){
    	
    }
    public m_Discount(	int m_discount_id,
    String m_discount_good,
    int m_discount_shop,
    String m_discount_deadline,
    String m_discount_picture,
    String m_create_time,
    String m_discount_type,
    String m_discount_description) {
		// TODO Auto-generated constructor stub
    	this.m_discount_id = m_discount_id;
    	this.m_discount_good = m_discount_good;
    	this.m_discount_shop = m_discount_shop;
    	this.m_discount_deadline = m_discount_deadline;
    	this.m_discount_picture = m_discount_picture;
    	this.m_create_time = m_create_time;
    	this.m_discount_type = m_discount_type;
    	this.m_discount_description = m_discount_description;	
	}
	public int getM_discount_id() {
		return m_discount_id;
	}
	public void setM_discount_id(int m_discount_id) {
		this.m_discount_id = m_discount_id;
	}
	public String getM_discount_good() {
		return m_discount_good;
	}
	public void setM_discount_good(String m_discount_good) {
		this.m_discount_good = m_discount_good;
	}
	public int getM_discount_shop() {
		return m_discount_shop;
	}
	public void setM_discount_shop(int m_discount_shop) {
		this.m_discount_shop = m_discount_shop;
	}
	public String getM_discount_deadline() {
		return m_discount_deadline;
	}
	public void setM_discount_deadline(String m_discount_deadline) {
		this.m_discount_deadline = m_discount_deadline;
	}
	public String getM_discount_picture() {
		return m_discount_picture;
	}
	public void setM_discount_picture(String m_discount_picture) {
		this.m_discount_picture = m_discount_picture;
	}
	public String getM_create_time() {
		return m_create_time;
	}
	public void setM_create_time(String m_create_time) {
		this.m_create_time = m_create_time;
	}
	public String getM_discount_type() {
		return m_discount_type;
	}
	public void setM_discount_type(String m_discount_type) {
		this.m_discount_type = m_discount_type;
	}
	public String getM_discount_description() {
		return m_discount_description;
	}
	public void setM_discount_description(String m_discount_description) {
		this.m_discount_description = m_discount_description;
	}
	
	public static final Parcelable.Creator<m_Discount> CREATOR = new Creator<m_Discount>() {  
        public m_Discount createFromParcel(Parcel source) {  

        	m_Discount mBook = new m_Discount();  
        	
            mBook.m_discount_id = source.readInt();  
            mBook.m_discount_good = source.readString();  
            mBook.m_discount_shop = source.readInt();  
            mBook.m_discount_deadline = source.readString(); 
            mBook.m_discount_picture = source.readString(); 
            mBook.m_create_time = source.readString(); 
            mBook.m_discount_type = source.readString(); 
            mBook.m_discount_description = source.readString(); 
            
            return mBook;  
        }  
        public m_Discount[] newArray(int size) {  
            return new m_Discount[size];  
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
		//
//    	int m_discount_id;
//        String m_discount_good;
//        int m_discount_shop;
//        String m_discount_deadline;
//        String m_discount_picture;
//        String m_create_time;
//        String m_discount_type;
//        String m_discount_description;
		parcel.writeInt(m_discount_id); 
		parcel.writeString(m_discount_good); 
		parcel.writeInt(m_discount_shop); 
		parcel.writeString(m_discount_deadline); 
		parcel.writeString(m_discount_picture); 
		parcel.writeString(m_create_time); 
		parcel.writeString(m_discount_type); 
		parcel.writeString(m_discount_description); 
		
		
	}
    
    
	
}
