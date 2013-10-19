package com.android.testbaiduapi;

import java.util.ArrayList;
import java.util.List;



  public class m_Shop{  //商家
	List<m_Discount>  discounts;
	int m_shop_id;
    String m_shop_name;
    float m_shop_longitude;
    float m_shop_latitude;
    String m_shop_type;
    int m_shop_owner;
    String m_shop_pic;
    String m_shop_addr;
    String m_shop_tags;
    m_Shop(){
    	
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
    
}
