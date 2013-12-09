package com.android.testbaiduapi;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {
	List<m_Shop> shops;
	JsonParser(List<m_Shop> shops){
		this.shops = shops;
	}
	public JsonParser(){
		shops = new ArrayList<m_Shop>();
	}
	

    
    public List<m_Shop> getShops() {
		return shops;
	}
	public void setShops(List<m_Shop> shops) {
		this.shops = shops;
	}





	
    

}


