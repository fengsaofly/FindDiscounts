package com.android.testbaiduapi;


import java.util.HashMap;
import java.util.Map;
import java.util.Vector;





import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * User: ye.yang
 * Date: 13-5-10
 * Time: 上午10:57
 */
public class Rrotate  {
    

    private Canvas c;
    private Paint p;
    private Point circleCenter = new Point();
   // private Handler handler = new Handler();
    private ImageView iv;
    private int radius = 150;
    private int w,h;
    Handler mHandler;
    Context mContext;
    Activity activity;
    public Rrotate(Handler handler,Context context,Activity activity){
    	this.mContext = context;
    	this.mHandler = handler;
    	this.activity = activity;
    	initial();
    }
   
   
    public  Bitmap backgroundBmp = null;
    public static Bitmap[] b = new Bitmap[12];
    int pictureNumbers = 0;
    int index=0;
    int first=0;
    float angle = 0;
    Vector<MyPoint> vc=new Vector<MyPoint>();
    double wspeed =0;
    int count = 0;
    int  showNum = 12;
    boolean circleFull = false;
    
    public boolean getCircleFull(){
    
    	return circleFull;
    }
    private Runnable task = new Runnable() {
        @Override
        public void run() {
          
         //   mHandler.postDelayed(this,(long)(0.01*200000));
//            
//            pictureNumbers++;
//            
//            angle=0;
//            
//            if(pictureNumbers==b.length+1){ //图片都显示完成了，等待下一张图片进来，在更新界面
//            	/**
//            	 *     具体操作后面要添上去
//            	 */
//            	pictureNumbers=0;
//            }
            	
            
//            Bitmap[] tempB = new Bitmap[pictureNumbers];
//            
//            for(int j=0;j<pictureNumbers;j++){
//            	tempB[j] = b[pictureNumbers-j-1];
//            }
//            
//           
//            //c.save();
//            for(int i = 0;i<tempB.length;i++){
//               int left = (int)(radius*Math.cos(angle)-0)+circleCenter.x;
//                
//               int top = (int)(radius*Math.sin(angle)-0)+circleCenter.y;
//               int size = (int)(64+Math.abs(32*Math.sin(angle)));
//               
//               c.drawBitmap(zoomBitmap(tempB[i],size,size),left-size/2, top-size/2, p); 
//               angle+=Math.PI/4;
//            }
           // iv.setImageBitmap(backgroundBmp);
            
        	int zoomFactor=0;
            if(count==12){
            	circleFull = true;
            }
            //c.rotate(earthDegree,circleCenter.x, circleCenter.y);
             c.save();
            if(!circleFull){
            	
	            for(int i=0;i<count;i++){
	            	
		            //Bitmap pic = addTextToBitmap(String.valueOf(count-i-1));
		            //c.drawBitmap(pic, null, r, p);  
	            	 zoomFactor = (int) (20+(Math.sin((2*Math.PI)/showNum*i)+1)*64);
	            	if(zoomFactor<0){
	            		System.out.println("zoomFactor小于0！");
	            	}
		            c.drawBitmap(zoomBitmap(b[count-i-1],zoomFactor,zoomFactor), (float) vc.get(i).getX(), (float) vc.get(i).getY(), p);
	            
	            } 
            }
            else{
                mHandler.sendEmptyMessage(6);
            	int j = 0;
            	for(int i=0;i<count;i++){
            		 j = (vc.size()-i+index)%vc.size();
            		//Bitmap pic = addTextToBitmap(String.valueOf(j));
   		            //c.drawBitmap(pic, null, r, p);
            		 zoomFactor = (int) (20+(Math.sin((2*Math.PI)/showNum*i)+1)*64);
 		            c.drawBitmap(zoomBitmap(b[j],zoomFactor,zoomFactor), (float) vc.get(i).getX(), (float) vc.get(i).getY(), p);
   	            
            	}
            	//index=(index+1)%20;
            	int m = (vc.size()-showNum/4+index)%vc.size();
            	  zoomFactor = (int) (20+(2*64));
                  c.drawBitmap(zoomBitmap(b[m],zoomFactor,zoomFactor), (float) vc.get(showNum/4).getX(), (float) vc.get(showNum/4).getY(), p);
                  Message msg = mHandler.obtainMessage();
                  msg.what = 7;
                  msg.obj = zoomBitmap(b[m],96,96);
                  mHandler.sendMessage(msg);
            }
            
          
            
            
            Message msg = mHandler.obtainMessage();
            msg.what = 5;
            msg.obj = backgroundBmp;
            mHandler.sendMessage(msg);
            

        }
    };

    public void initial() {
       // super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        w=getMetrics().get("width");
        h=getMetrics().get("height");
        circleCenter.x = w/2;
        circleCenter.y = h/2-150;
       
      
        
        for(int i=0;i<showNum;i++){
        	 int curId = mContext.getResources().getIdentifier("shop" + (i+1), "drawable", mContext.getPackageName());  
        	 b[i]= 		BitmapFactory.decodeResource(mContext.getResources(),curId);
        }
       
//        b[1]=		BitmapFactory.decodeResource(mContext.getResources(), R.drawable.earth1);
//        b[2]=		BitmapFactory.decodeResource(mContext.getResources(), R.drawable.earth2);
//        b[3]=		BitmapFactory.decodeResource(mContext.getResources(),R.drawable.earth3);
//        b[4]=		BitmapFactory.decodeResource(mContext.getResources(),R.drawable.earth4);
//        b[5]=		BitmapFactory.decodeResource(mContext.getResources(), R.drawable.earth5);
//        b[6]=		BitmapFactory.decodeResource(mContext.getResources(), R.drawable.earth6);
//        b[7]=		BitmapFactory.decodeResource(mContext.getResources(), R.drawable.earth7);
        
       // iv = (ImageView)findViewById(R.id.rotate_iv);
        
        backgroundBmp = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        if(backgroundBmp==null){
        	System.out.println("创建不成功！！！！");
        }
        c = new Canvas(backgroundBmp);
   
        p = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
        p.setColor(Color.RED);
        
        double curX =radius;
        double curY = 0;
        wspeed = 2*Math.PI/showNum;
       
        for(int i=0;i<showNum;i++){
        	
        	MyPoint curPoint = new MyPoint(curX+circleCenter.x-32, curY+circleCenter.y-32);
        	vc.add(curPoint);
        	double tempX = curX,tempY = curY;
        	curX = Math.cos(wspeed)*tempX - Math.sin(wspeed)*tempY;
        	curY = Math.cos(wspeed)*tempY + Math.sin(wspeed)*tempX;
        	
        	
        }
       // c.drawCircle(circleCenter.x, circleCenter.y, radius, p);
        mHandler.post(task);
    }
    public Bitmap addTextToBitmap(String str){
    	
    	Bitmap bmp  = Bitmap.createBitmap(64,64, Bitmap.Config.ARGB_8888); //图象大小要根据文字大小算下,以和文本长度对应
    	
    	Canvas canvasTemp = new Canvas(bmp);
    	canvasTemp.drawColor(Color.WHITE); 
    	Paint p = new Paint(); 
    	String familyName ="宋体"; 
    	Typeface font = Typeface.create(familyName,Typeface.BOLD); 
    	p.setColor(Color.RED); p.setTypeface(font); 
    	p.setTextSize(22); 
    	canvasTemp.drawText(str,20,50,p); 
    	
    	return bmp;
    }

    private Map<String, Integer> getMetrics() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = activity.getWindowManager();
        if(wm == null)
            wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(displayMetrics);
        }
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        Map<String, Integer> resultMap = new HashMap<String, Integer>();
        resultMap.put("width", width);
        resultMap.put("height", height);
        return resultMap;
    }
    
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {  
 	   int width = bitmap.getWidth();  
 	   int height = bitmap.getHeight();  
 	   Matrix matrix = new Matrix();  
 	   float scaleWidht = ((float) w / width);  
 	   float scaleHeight = ((float) h / height);  
 	   matrix.postScale(scaleWidht, scaleHeight);  
 	   Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);  
 	   return newbmp;  
 	} 
    public void Move(int type){
    	//左移
    	if(type==0){
    		index++;
    		if(index==vc.size()-1) index=0;
    		if(!circleFull)
    			count = (count+1)%(showNum+1);
    		
    	}
    	else{
    		index--;
    		if(index==-1)
    			index=vc.size()-1;
    		if(!circleFull){
    			count--;
    			if(count<1) count=1;
    		}
    	}
    	mHandler.post(task);
    }
    
    
  
        
      
  
      
       

    }  
    
    


