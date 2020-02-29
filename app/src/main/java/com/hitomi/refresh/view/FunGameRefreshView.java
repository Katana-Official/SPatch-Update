package com.hitomi.refresh.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

public class FunGameRefreshView extends LinearLayout implements View.OnTouchListener {
  public static final int STATUS_AGAIN_DOWN = 3;
  
  public static final int STATUS_PULL_TO_REFRESH = 0;
  
  public static final int STATUS_REFRESHING = 2;
  
  public static final int STATUS_REFRESH_FINISHED = 4;
  
  public static final int STATUS_RELEASE_TO_REFRESH = 1;
  
  private static final float STICK_RATIO = 0.65F;
  
  private boolean ableToPull;
  
  private View contentView;
  
  private int currentStatus = 4;
  
  private FunGameHeader header;
  
  private ViewGroup.MarginLayoutParams headerLayoutParams;
  
  private int hideHeaderHeight;
  
  private boolean isExecComplete;
  
  private FunGameRefreshListener mListener;
  
  private boolean once;
  
  private float preDownY;
  
  private int tempHeaderTopMargin;
  
  public FunGameRefreshView(Context paramContext) { this(paramContext, null); }
  
  public FunGameRefreshView(Context paramContext, AttributeSet paramAttributeSet) { this(paramContext, paramAttributeSet, 0); }
  
  public FunGameRefreshView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    if (getChildCount() <= 1) {
      setOrientation(1);
      initView(paramContext, paramAttributeSet);
      return;
    } 
    throw new RuntimeException("FunGameRefreshView can only contain one View");
  }
  
  private void checkAblePull(MotionEvent paramMotionEvent) {
    if (this.contentView != null) {
      if (!canContentViewScrollUp()) {
        if (!this.ableToPull)
          this.preDownY = paramMotionEvent.getRawY(); 
        this.ableToPull = true;
        return;
      } 
      int i = this.headerLayoutParams.topMargin;
      int j = this.hideHeaderHeight;
      if (i != j)
        setHeaderTopMarign(j); 
      this.ableToPull = false;
      return;
    } 
    this.ableToPull = true;
  }
  
  private void disableContentView() {
    this.contentView.setPressed(false);
    this.contentView.setFocusable(false);
    this.contentView.setFocusableInTouchMode(false);
  }
  
  private boolean handleAgainDownAction(MotionEvent paramMotionEvent) {
    int i = paramMotionEvent.getAction();
    if (i != 0) {
      if (i != 1) {
        if (i == 2) {
          float f = (paramMotionEvent.getRawY() - this.preDownY) * 0.65F;
          this.header.moveRacket(f);
          setHeaderTopMarign((int)f);
        } 
      } else {
        this.currentStatus = 2;
        if (this.isExecComplete) {
          rollbackHeader(false);
        } else {
          rollBack2Header(false);
        } 
      } 
    } else {
      this.currentStatus = 3;
      this.preDownY = paramMotionEvent.getRawY();
    } 
    disableContentView();
    return true;
  }
  
  private void initView(Context paramContext, AttributeSet paramAttributeSet) {
    FunGameHeader funGameHeader = new FunGameHeader(paramContext, paramAttributeSet);
    this.header = funGameHeader;
    addView((View)funGameHeader, 0);
  }
  
  private void rollBack2Header(boolean paramBoolean) {
    ValueAnimator valueAnimator = ValueAnimator.ofInt(new int[] { this.headerLayoutParams.topMargin, 0 });
    long l2 = (long)(this.headerLayoutParams.topMargin * 1.1F);
    long l1 = 0L;
    if (l2 >= 0L)
      l1 = (long)(this.headerLayoutParams.topMargin * 1.1F); 
    valueAnimator.setDuration(l1);
    valueAnimator.setInterpolator((TimeInterpolator)new AccelerateDecelerateInterpolator());
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
          public void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
            int i = Integer.parseInt(param1ValueAnimator.getAnimatedValue().toString());
            FunGameRefreshView.this.setHeaderTopMarign(i);
          }
        });
    if (paramBoolean)
      valueAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator param1Animator) { (new AsyncTask<Void, Void, Void>() {
                  protected Void doInBackground(Void... param2VarArgs) {
                    if (FunGameRefreshView.this.mListener != null) {
                      long l = System.currentTimeMillis();
                      FunGameRefreshView.this.mListener.onPullRefreshing();
                      l = System.currentTimeMillis() - l;
                      if (l < 1500L)
                        SystemClock.sleep(1500L - l); 
                    } 
                    return null;
                  }
                  
                  protected void onPostExecute(Void param2Void) {
                    if (FunGameRefreshView.this.mListener != null)
                      FunGameRefreshView.this.mListener.onRefreshComplete(); 
                    FunGameRefreshView.this.finishRefreshing();
                  }
                  
                  protected void onPreExecute() {
                    FunGameRefreshView.access$102(FunGameRefreshView.this, 2);
                    FunGameRefreshView.this.header.postStart();
                  }
                }).execute((Object[])new Void[0]); }
          }); 
    this.header.back2StartPoint(l1);
    valueAnimator.start();
  }
  
  private void rollbackHeader(boolean paramBoolean) {
    this.tempHeaderTopMargin = this.headerLayoutParams.topMargin;
    ValueAnimator valueAnimator = ValueAnimator.ofInt(new int[] { 0, this.header.getHeight() + this.tempHeaderTopMargin });
    valueAnimator.setDuration(300L);
    valueAnimator.setInterpolator((TimeInterpolator)new AccelerateDecelerateInterpolator());
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
          public void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
            int i = Integer.parseInt(param1ValueAnimator.getAnimatedValue().toString());
            FunGameRefreshView funGameRefreshView = FunGameRefreshView.this;
            funGameRefreshView.setHeaderTopMarign(-i + funGameRefreshView.tempHeaderTopMargin);
          }
        });
    valueAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
          public void onAnimationEnd(Animator param1Animator) {
            if (FunGameRefreshView.this.currentStatus == 0 || FunGameRefreshView.this.currentStatus == 4) {
              FunGameRefreshView.access$102(FunGameRefreshView.this, 4);
              return;
            } 
            FunGameRefreshView.access$102(FunGameRefreshView.this, 4);
            FunGameRefreshView.access$502(FunGameRefreshView.this, false);
            FunGameRefreshView.this.header.postEnd();
          }
        });
    if (paramBoolean)
      valueAnimator.setStartDelay(500L); 
    valueAnimator.start();
  }
  
  private void setHeaderTopMarign(int paramInt) {
    this.headerLayoutParams.topMargin = paramInt;
    this.header.setLayoutParams((ViewGroup.LayoutParams)this.headerLayoutParams);
  }
  
  public boolean canContentViewScrollUp() {
    if (Build.VERSION.SDK_INT < 14) {
      Object object = this.contentView;
      boolean bool = object instanceof android.widget.AbsListView;
      boolean bool2 = true;
      boolean bool1 = true;
      if (bool) {
        object = object;
        if (object.getChildCount() > 0)
          if (object.getFirstVisiblePosition() <= 0) {
            if (object.getChildAt(0).getTop() < object.getPaddingTop())
              return true; 
          } else {
            return bool1;
          }  
        return false;
      } 
      bool1 = bool2;
      if (!object.canScrollVertically(-1)) {
        if (this.contentView.getScrollY() > 0)
          return true; 
        bool1 = false;
      } 
      return bool1;
    } 
    return this.contentView.canScrollVertically(-1);
  }
  
  public void finishRefreshing() {
    this.header.postComplete();
    this.isExecComplete = true;
    if (this.currentStatus != 3)
      rollbackHeader(true); 
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if (paramBoolean && !this.once) {
      this.hideHeaderHeight = -this.header.getHeight();
      ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)this.header.getLayoutParams();
      this.headerLayoutParams = marginLayoutParams;
      marginLayoutParams.topMargin = this.hideHeaderHeight;
      View view = getChildAt(1);
      this.contentView = view;
      view.setOnTouchListener(this);
      this.once = true;
    } 
  }
  
  public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
    checkAblePull(paramMotionEvent);
    if (!this.ableToPull)
      return false; 
    if (this.currentStatus == 3)
      return handleAgainDownAction(paramMotionEvent); 
    int i = paramMotionEvent.getAction();
    if (i != 0) {
      if (i != 1) {
        if (i == 2) {
          float f = paramMotionEvent.getRawY() - this.preDownY;
          if (f <= 0.0F && this.headerLayoutParams.topMargin <= this.hideHeaderHeight)
            return false; 
          if (this.headerLayoutParams.topMargin > 0)
            this.currentStatus = 1; 
          if (this.headerLayoutParams.topMargin > 0) {
            this.currentStatus = 1;
          } else {
            this.currentStatus = 0;
          } 
          setHeaderTopMarign((int)(0.65F * f + this.hideHeaderHeight));
        } 
      } else {
        if (this.currentStatus == 0)
          rollbackHeader(false); 
        if (this.currentStatus == 1)
          rollBack2Header(true); 
      } 
    } else {
      this.preDownY = paramMotionEvent.getRawY();
      if (this.currentStatus == 2) {
        this.currentStatus = 3;
        setHeaderTopMarign(0);
      } 
    } 
    i = this.currentStatus;
    if (i == 0 || i == 1) {
      disableContentView();
      return true;
    } 
    return false;
  }
  
  public void setBottomMaskText(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return; 
    this.header.setBottomMaskViewText(paramString);
  }
  
  public void setGameOverText(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return; 
    this.header.setHeaderGameOverStr(paramString);
  }
  
  public void setLoadingFinishedText(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return; 
    this.header.setHeaderLoadingFinishedStr(paramString);
  }
  
  public void setLoadingText(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return; 
    this.header.setHeaderLodingStr(paramString);
  }
  
  public void setOnRefreshListener(FunGameRefreshListener paramFunGameRefreshListener) { this.mListener = paramFunGameRefreshListener; }
  
  public void setTopMaskText(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return; 
    this.header.setTopMaskViewText(paramString);
  }
  
  public static interface FunGameRefreshListener {
    void onPullRefreshing();
    
    void onRefreshComplete();
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\hitomi\refresh\view\FunGameRefreshView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */