package com.zyao89.view.zloading.base;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import com.zyao89.view.zloading.ZLoadingBuilder;

public abstract class BaseStateBuilder extends ZLoadingBuilder {
  private int mCurrAnimatorState = 0;
  
  private Paint mPaint;
  
  private void initPaint() {
    Paint paint = new Paint(1);
    this.mPaint = paint;
    paint.setStyle(Paint.Style.STROKE);
    this.mPaint.setStrokeWidth(6.0F);
    this.mPaint.setColor(-16777216);
    this.mPaint.setDither(true);
    this.mPaint.setFilterBitmap(true);
    this.mPaint.setStrokeCap(Paint.Cap.ROUND);
    this.mPaint.setStrokeJoin(Paint.Join.ROUND);
  }
  
  protected void computeUpdateValue(ValueAnimator paramValueAnimator, float paramFloat) { onComputeUpdateValue(paramValueAnimator, paramFloat, this.mCurrAnimatorState); }
  
  protected abstract int getStateCount();
  
  protected final void initParams(Context paramContext) {
    initPaint();
    initParams(paramContext, this.mPaint);
  }
  
  protected abstract void initParams(Context paramContext, Paint paramPaint);
  
  public void onAnimationRepeat(Animator paramAnimator) {
    int i = getStateCount();
    int j = this.mCurrAnimatorState + 1;
    this.mCurrAnimatorState = j;
    if (j > i)
      this.mCurrAnimatorState = 0; 
  }
  
  protected abstract void onComputeUpdateValue(ValueAnimator paramValueAnimator, float paramFloat, int paramInt);
  
  protected void setAlpha(int paramInt) { this.mPaint.setAlpha(paramInt); }
  
  protected void setColorFilter(ColorFilter paramColorFilter) { this.mPaint.setColorFilter(paramColorFilter); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\zyao89\view\zloading\base\BaseStateBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */