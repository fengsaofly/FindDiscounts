package com.androidui;

import com.android.testbaiduapi.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DiscountInfoActivity extends Activity {

	TextView price =null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		
		setContentView(R.layout.goods_item_layout);
		
		Bundle in = getIntent().getBundleExtra("position");
//		float[] pos = in.getFloatExtra("position", 0);
		float[] pos = in.getFloatArray("value");
		System.out.println(pos);
		float pricef = pos[1]+ pos[0];
		price = (TextView)findViewById(R.id.buying_price);
		price.setText(""+pricef);
		
	}
	
}
