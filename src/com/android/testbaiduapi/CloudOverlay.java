package com.android.testbaiduapi;

import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.widget.Toast;


import com.baidu.mapapi.cloud.CustomPoiInfo;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;

class CloudOverlay extends ItemizedOverlay {

    List<CustomPoiInfo> mLbsPoints;
    Activity mContext;
    
    public CloudOverlay(Activity context ,MapView mMapView) {
        super(null,mMapView);
        mContext = context;
    }

    public void setData(List<CustomPoiInfo> lbsPoints) {
        if (lbsPoints != null) {
            mLbsPoints = lbsPoints;
            
            
        }
        for ( CustomPoiInfo rec : mLbsPoints ){
            GeoPoint pt = new GeoPoint((int)(rec.latitude * 1e6), (int)(rec.longitude * 1e6));
            OverlayItem item = new OverlayItem(pt , rec.name, rec.address);
            Drawable marker1 = this.mContext.getResources().getDrawable(R.drawable.icon_marka);
            item.setMarker(marker1);
            addItem(item);
        }
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return super.clone();
    }
    
    @Override
    protected boolean onTap(int arg0) {
        CustomPoiInfo item = mLbsPoints.get(arg0);
        Toast.makeText(mContext, item.address,Toast.LENGTH_LONG).show();
        return super.onTap(arg0);
    }
    
}
