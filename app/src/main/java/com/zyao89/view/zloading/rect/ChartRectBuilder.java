package com.zyao89.view.zloading.rect;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import com.zyao89.view.zloading.base.BaseStateBuilder;

public class ChartRectBuilder extends BaseStateBuilder {
  private final int SUM_NUM = 5;
  
  private volatile long mAnimateDurationTime = 500L;
  
  private volatile float mCurrAnimatedValue = 0.0F;
  
  private volatile int mCurrStateNum = 0;
  
  private Paint mPaint;
  
  private float mR;
  
  private RectF mStairRectF;
  
  protected int getStateCount() { return 6; }
  
  protected void initParams(Context paramContext, Paint paramPaint) {
    this.mPaint = paramPaint;
    paramPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    this.mR = getAllSize();
    this.mStairRectF = new RectF();
  }
  
  protected void onComputeUpdateValue(ValueAnimator paramValueAnimator, float paramFloat, int paramInt) {
    this.mCurrStateNum = paramInt;
    this.mCurrAnimatedValue = paramFloat;
  }
  
  protected void onDraw(Canvas paramCanvas) {
    float f1 = this.mR * 2.0F / 5.0F;
    float f2 = f1 * 0.5F;
    float f3 = getViewCenterX() - this.mR;
    float f4 = getViewCenterY() + this.mR;
    this.mStairRectF.setEmpty();
    int i;
    for (i = 0; i < 5; i++) {
      if (i > this.mCurrStateNum)
        return; 
      float f = Math.abs(this.mCurrAnimatedValue - 0.5F);
      int j = i % 3;
      if (i == this.mCurrStateNum) {
        this.mStairRectF.set(i * f1 + f3, f4 - (j + 1) * f1 * this.mCurrAnimatedValue, (i + 1) * f1 + f3 - f2, f4);
      } else {
        this.mStairRectF.set(i * f1 + f3, f4 - (j + 1) * f1 - (0.5F - f) * f1, (i + 1) * f1 + f3 - f2, f4);
      } 
      paramCanvas.drawRect(this.mStairRectF, this.mPaint);
    } 
  }
  
  protected void prepareEnd() {
    this.mCurrStateNum = 0;
    this.mCurrAnimatedValue = 0.0F;
  }
  
  protected void prepareStart(ValueAnimator paramValueAnimator) {
    this.mAnimateDurationTime = ceil(getAnimationDuration() * 0.4D);
    paramValueAnimator.setDuration(this.mAnimateDurationTime);
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\zyao89\view\zloading\rect\ChartRectBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */