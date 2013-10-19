package com.android.testbaiduapi;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class ZoomImage {
	public static Bitmap zoomImage(Bitmap bgimage, int newWidth, int newHeight) {
		// 获取这个图片的宽和高
		int width = bgimage.getWidth();
		int height = bgimage.getHeight();
		// Log.v("ZoomImage", height + ":" + width);
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算缩放率，新尺寸除原始尺寸
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// Log.v("ChangeZoomImage", scaleHeight + ":" + scaleWidth);
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, width, height,
				matrix, true);
		
		return bitmap;
	}

}
