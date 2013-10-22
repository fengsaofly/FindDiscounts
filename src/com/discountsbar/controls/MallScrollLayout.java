package com.discountsbar.controls;

import android.R.integer;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;
//import com.jm.android.jumei.JuMeiMallActivity;

public class MallScrollLayout extends ViewGroup
{
  private static final int SNAP_VELOCITY = 600;
  private static final String TAG = "ScrollLayout";
  private static final int TOUCH_STATE_REST = 0;
  private static final int TOUCH_STATE_SCROLLING = 1;
  private Context context;
  private int mCurScreen = 0;
  private int mDefaultScreen = 0;
  private float mLastMotionX;
  private float mLastMotionY;
  private Scroller mScroller;
  private int mTouchSlop;
  private int mTouchState = 0;
  private VelocityTracker mVelocityTracker;
  private int page = 0;
  private MallScrollLayoutPageListener pageListener;
  private int pageSize = 0;

  public MallScrollLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
    this.context = paramContext;
  }

  public MallScrollLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.context = paramContext;
    this.mScroller = new Scroller(paramContext);
    this.mCurScreen = this.mDefaultScreen;
    this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
  }

  public void computeScroll()
  {
    if (!this.mScroller.computeScrollOffset())
      return;
    scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
    postInvalidate();
  }

  public int getCurScreen()
  {
    return this.mCurScreen;
  }

  public int getPage()
  {
    return this.page;
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean i = true;
    getFocusedChild();
    requestFocus();
//    ((JuMeiMallActivity)this.context).a();
    int j = paramMotionEvent.getAction();
    if ((j == 2) && (this.mTouchState != 0))
       return i;
    float f1;
	label41:
     f1 = paramMotionEvent.getX();
    float f2 = paramMotionEvent.getY();
    switch (j)
    {
    default:
    case 2:
    case 0:
    case 1:
    case 3:
    }
    while (true)
    {
    	int i1;
      label88: if (this.mTouchState == 0);
      i1 = 0;
     // break label41;
      if ((int)Math.abs(this.mLastMotionX - f1) <= this.mTouchSlop)
        continue;
      this.mTouchState = i1;
    //  continue;
      this.mLastMotionX = f1;
      this.mLastMotionY = f2;
      if (this.mScroller.isFinished());
      for (int k = 0; ; k = i1)
      {
        this.mTouchState = k;
      //  break label88;
      }
   //   this.mTouchState = 0;
    }
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getChildCount();
    int j = 0;
    int k = 0;
    while (j < i)
    {
      View localView = getChildAt(j);
      if (localView.getVisibility() != 8)
      {
        int l = localView.getMeasuredWidth();
        localView.layout(k, 0, k + l, localView.getMeasuredHeight());
        k += l;
      }
      ++j;
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    int i = View.MeasureSpec.getSize(paramInt1);
    View.MeasureSpec.getMode(paramInt1);
    int j = getChildCount();
    for (int k = 0; k < j; ++k)
      getChildAt(k).measure(paramInt1, paramInt2);
    scrollTo(i * this.mCurScreen, 0);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    getFocusedChild();
    requestFocus();
//    ((JuMeiMallActivity)this.context).a();
    if (this.mVelocityTracker == null)
      this.mVelocityTracker = VelocityTracker.obtain();
    this.mVelocityTracker.addMovement(paramMotionEvent);
    int i = paramMotionEvent.getAction();
    float f = paramMotionEvent.getX();
    paramMotionEvent.getY();
    switch (i)
    {
    default:
    case 0:
    case 2:
    case 1:
    case 3:
    }
    while (true)
    {
      label92:// return true;
      if (!this.mScroller.isFinished())
        this.mScroller.abortAnimation();
      this.mLastMotionX = f;
     // continue;
      int k = (int)(this.mLastMotionX - f);
      this.mLastMotionX = f;
      if (this.mCurScreen == 0)
        if (k >= 0);
      for (int l = 0; ; l = 1)
        while (true)
        {
          if (l != 0);
          scrollBy(k, 0);
        //  break label92;
          l = 1;
         // continue;
          if (this.mCurScreen != -1 + getChildCount())
            break;
          if (k < 0)
            l = 1;
          l = 0;
        }
    /*  VelocityTracker localVelocityTracker = this.mVelocityTracker;
      localVelocityTracker.computeCurrentVelocity(1000);
      int j = (int)localVelocityTracker.getXVelocity();
      if ((j > 600) && (this.mCurScreen > 0))
      {
        snapToScreen(-1 + this.mCurScreen);
        this.page = this.mCurScreen;
        if (this.page < 0)
          this.page = 0;
        this.pageListener.page(Math.abs(this.page));
      }
      while (true)
      {
        if (this.mVelocityTracker != null)
        {
          this.mVelocityTracker.recycle();
          this.mVelocityTracker = null;
        }
        this.mTouchState = 0;
        break label92;
        if ((j < -600) && (this.mCurScreen < -1 + getChildCount()))
        {
          snapToScreen(1 + this.mCurScreen);
          this.page = this.mCurScreen;
          this.pageListener.page(Math.abs(this.page));
        }
        snapToDestination();
      }
      this.mTouchState = 0;*/
    }
  }

  public void setCurScreen(int paramInt)
  {
    this.mCurScreen = paramInt;
  }

  public void setPageListener(MallScrollLayoutPageListener paramPageListener)
  {
    this.pageListener = paramPageListener;
  }

  public void snapToDestination()
  {
    int i = getWidth();
    snapToScreen((getScrollX() + i * 9 / 10) / i);
  }

  public void snapToScreen(int paramInt)
  {
    int i = Math.max(0, Math.min(paramInt, -1 + getChildCount()));
    if (getScrollX() != i * getWidth())
    {
      int j = i * getWidth() - getScrollX();
      this.mScroller.startScroll(getScrollX(), 0, j, 0, 2 * Math.abs(j));
      this.mCurScreen = i;
      invalidate();
    }
    if (this.pageListener == null)
      return;
    this.pageListener.page(i);
  }
}

/* Location:           D:\Program Files (x86)\Android-反编译工具\apk2java\dex2jar-0.0.9.9\classes_dex2jar.jar
 * Qualified Name:     com.jm.android.jumei.controls.MallScrollLayout
 * JD-Core Version:    0.5.4
 */