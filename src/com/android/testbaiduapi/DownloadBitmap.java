package com.android.testbaiduapi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

class DownloadBitmap extends AsyncTask<Void, Integer, Void>{
	private ProgressDialog dialog;
	private Context mContext;
	private Activity myActivity;
	private List<Integer> ids = null;  //传入需要下载的图片id,id[0]为商家图片
	private List<String> urls = null;  //传入需要下载的图片的url,url[0]为商家的url;
	private List<String> goodsPicPaths = null;
	Handler handler;
	
	public DownloadBitmap(Context context,Activity activity,List<Integer> ids,List<String> urls,Handler handler){
		this.mContext = context;
		this.myActivity = activity;
		this.ids = ids;
		this.urls = urls;
		this.goodsPicPaths = new ArrayList<String>();
		this.handler = handler;
	}
    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        dialog = new ProgressDialog(myActivity);
		
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		
		dialog.setTitle("FindDiscount");
		
		dialog.setMessage("正在从网络获取数据,请等待");
		
		dialog.setIcon(android.R.drawable.ic_dialog_alert);
		
		dialog.setCancelable(false);
		// 显示
		dialog.show();
		
        
      
    }
    @Override
    protected void onPostExecute(Void result) {//通知handler下载完成
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        Message msg = handler.obtainMessage();
        msg.what =4;
        msg.obj = goodsPicPaths;
        handler.sendMessage(msg);
        System.out.println("下载完成");
        dialog.dismiss(); //Hide Progress Dialog else use dismiss() to dismiss the dialog
    }
    @Override
    protected Void doInBackground(Void... params) {
        // TODO Auto-generated method stub
        /*
         * Perform download and Bitmap conversion here
         *
         */
    	String s ="";
    	String path = Environment.getExternalStorageDirectory().getPath() + "/"+"findDiscount"+"/"+ids.get(0);  
    	
    	for(int i=0;i<ids.size();i++){
    		if(i==0){
    			s="/"+"shopid_"+ids.get(0)+".jpg";
    		}
    		else{
    			s="/"+"discountid_"+ids.get(i)+".jpg";
    			goodsPicPaths.add(path+s);
    		}
    		File file = new File(path);
    		if(!file.exists())
    			file.mkdirs();
    		File f = new File(path+s);
    		System.out.println("path:"+f.getAbsolutePath()+"222222");
    		if(f.exists()) //如果图片存在,就跳过,不去下载
    			{
//    			Message msg = handler.obtainMessage();
//    			msg.obj = f.getAbsolutePath();
//    			msg.what = 4;
//    			handler.sendMessage(msg);
    			System.out.println("文件不存在");
    			continue;
    			}
    		else { //不存在则从网上下载,并将下载的图片存到sd卡里
    			 HttpClient httpclient = new DefaultHttpClient();
    			 HttpGet httpget = new HttpGet(urls.get(i));
		         try {
		        	 //httpget.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		            HttpResponse response; 
		            response=httpclient.execute(httpget);
		            if (response.getStatusLine().getStatusCode() == 200) {
		                // 获取返回的数据
		            	//String resultStr = EntityUtils.toString(response.getEntity(), "UTF-8");
		            	HttpEntity entity = response.getEntity();
		            	if (entity != null) {
							InputStream inputStream = null;
							try {

								inputStream = entity.getContent();

								Log.e("..........length", String.valueOf(entity
										.getContentLength()));
								Log.e("...........contentType: ", entity
										.getContentType().getValue());
								Log.e("............stream",
										String.valueOf(entity.isStreaming()));

								// final Bitmap bitmap =
								// BitmapFactory.decodeStream(inputStream);
								Bitmap bitmap = BitmapFactory
										.decodeStream(inputStream);
								if (bitmap != null) {  //存入sd卡
								    //SaveImgToSD.SaveBmp(bitmap, path,s);
									String SDMountType = Environment.getExternalStorageState();
									if (SDMountType.equals(Environment.MEDIA_MOUNTED)) {
										

//	
										try {
											File file2 = new File(path+s);
											if(!file2.exists()){
												file2.createNewFile();
											}
											OutputStream outStream = new FileOutputStream(file2);
											bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outStream);
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
									
								} else {
									Log.e("Error", "bitmap is null!");
									continue;
								}

							} finally {
								if (inputStream != null) {
									inputStream.close();
								}
								entity.consumeContent();
							}
		           
		            	}
		            }
		         }catch (UnsupportedEncodingException e) {
		            
		            e.printStackTrace();
		        } catch (ClientProtocolException e) {
		           
		            e.printStackTrace();
		        } catch (IOException e) {
		          
		            e.printStackTrace();
		        }
			}
		
    		}
    	
    	
        return null;
    }
}
