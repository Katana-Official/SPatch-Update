package com.zyao89.view.zloading;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.animation.LinearInterpolator;

public abstract class ZLoadingBuilder implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
  protected static final long ANIMATION_DURATION = 1333L;
  
  protected static final long ANIMATION_START_DELAY = 333L;
  
  public static float DEFAULT_SIZE = 56.0F;
  
  private float mAllSize;
  
  private Drawable.Callback mCallback;
  
  private double mDurationTimePercent = 1.0D;
  
  private ValueAnimator mFloatValueAnimator;
  
  private float mViewHeight;
  
  private float mViewWidth;
  
  static  {
  
  }
  
  protected static long ceil(double paramDouble) { return (long)Math.ceil(paramDouble); }
  
  protected static float dip2px(Context paramContext, float paramFloat) { return paramFloat * (paramContext.getResources().getDisplayMetrics()).density; }
  
  private void initAnimators() {
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(new float[] { 0.0F, 1.0F });
    this.mFloatValueAnimator = valueAnimator;
    valueAnimator.setRepeatCount(-1);
    this.mFloatValueAnimator.setDuration(getAnimationDuration());
    this.mFloatValueAnimator.setStartDelay(getAnimationStartDelay());
    this.mFloatValueAnimator.setInterpolator((TimeInterpolator)new LinearInterpolator());
  }
  
  private void invalidateSelf() {
    Drawable.Callback callback = this.mCallback;
    if (callback != null)
      callback.invalidateDrawable(null); 
  }
  
  protected abstract void computeUpdateValue(ValueAnimator paramValueAnimator, float paramFloat);
  
  void draw(Canvas paramCanvas) { onDraw(paramCanvas); }
  
  protected final float getAllSize() { return this.mAllSize; }
  
  protected long getAnimationDuration() { return ceil(this.mDurationTimePercent * 1333.0D); }
  
  protected long getAnimationStartDelay() { return 333L; }
  
  protected float getIntrinsicHeight() { return this.mViewHeight; }
  
  protected float getIntrinsicWidth() { return this.mViewWidth; }
  
  protected final float getViewCenterX() { return getIntrinsicWidth() * 0.5F; }
  
  protected final float getViewCenterY() { return getIntrinsicHeight() * 0.5F; }
  
  void init(Context paramContext) {
    this.mAllSize = dip2px(paramContext, DEFAULT_SIZE * 0.5F - 12.0F);
    this.mViewWidth = dip2px(paramContext, DEFAULT_SIZE);
    this.mViewHeight = dip2px(paramContext, DEFAULT_SIZE);
    initAnimators();
  }
  
  protected abstract void initParams(Context paramContext);
  
  boolean isRunning() { return this.mFloatValueAnimator.isRunning(); }
  
  public void onAnimationCancel(Animator paramAnimator) {}
  
  public void onAnimationEnd(Animator paramAnimator) {}
  
  public void onAnimationRepeat(Animator paramAnimator) {}
  
  public void onAnimationStart(Animator paramAnimator) {}
  
  public final void onAnimationUpdate(ValueAnimator paramValueAnimator) {
    computeUpdateValue(paramValueAnimator, ((Float)paramValueAnimator.getAnimatedValue()).floatValue());
    invalidateSelf();
  }
  
  protected abstract void onDraw(Canvas paramCanvas);
  
  protected abstract void prepareEnd();
  
  protected abstract void prepareStart(ValueAnimator paramValueAnimator);
  
  protected abstract void setAlpha(int paramInt);
  
  void setCallback(Drawable.Callback paramCallback) { this.mCallback = paramCallback; }
  
  protected abstract void setColorFilter(ColorFilter paramColorFilter);
  
  public void setDurationTimePercent(double paramDouble) {
    if (paramDouble <= 0.0D) {
      this.mDurationTimePercent = 1.0D;
      return;
    } 
    this.mDurationTimePercent = paramDouble;
  }
  
  void start() {
    if (this.mFloatValueAnimator.isStarted())
      return; 
    this.mFloatValueAnimator.addUpdateListener(this);
    this.mFloatValueAnimator.addListener(this);
    this.mFloatValueAnimator.setRepeatCount(-1);
    this.mFloatValueAnimator.setDuration(getAnimationDuration());
    prepareStart(this.mFloatValueAnimator);
    this.mFloatValueAnimator.start();
  }
  
  void stop() {
    this.mFloatValueAnimator.removeAllUpdateListeners();
    this.mFloatValueAnimator.removeAllListeners();
    this.mFloatValueAnimator.setRepeatCount(0);
    this.mFloatValueAnimator.setDuration(0L);
    prepareEnd();
    this.mFloatValueAnimator.end();
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\zyao89\view\zloading\ZLoadingBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */