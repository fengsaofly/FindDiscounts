package com.android.testbaiduapi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

public class SaveImgToSD {  //将下载的图片缓存到sd卡

	public static void SaveBmp(Bitmap bm, String path,String name) {
		if (bm == null) {
			Log.w("TAG", " trying to savenull bitmap");
			// return;
		} else {
			// 判断sd卡内存是否足够
			String SDMountType = Environment.getExternalStorageState();
			if (SDMountType.equals(Environment.MEDIA_MOUNTED)) {
				

//				File dir = new File(Environment.getExternalStorageDirectory()
//						+ "/sjba/" + deviceId + "/" + pid + "/");
//				if (!dir.exists())
//					dir.mkdirs();
//
//				Log.e("dir path", dir.getAbsolutePath());

				//File file = new File(path);
				
//				File f = null;
//				         try {
//				             f = new File(path);
//				             if (!f.exists()) {
//				                 f.mkdirs();
//				             }
//				        } catch (Exception e) {
//				        }

//				Log.e("file path", f.getAbsolutePath());
				try {
					File file = new File(path+name);
					if(!file.exists()){
						file.createNewFile();
					}
					OutputStream outStream = new FileOutputStream(file);
					bm.compress(Bitmap.CompressFormat.JPEG, 80, outStream);
					outStream.flush();
					outStream.close();
					Log.i("TAG", "Image saved tosd");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					Log.w("TAG", "FileNotFoundException");
				} catch (IOException e) {
					e.printStackTrace();
					Log.w("TAG", "IOException");
				}
			}
		}

	}

}
