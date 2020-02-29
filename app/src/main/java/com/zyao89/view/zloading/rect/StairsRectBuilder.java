package com.zyao89.view.zloading.rect;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.animation.DecelerateInterpolator;
import com.zyao89.view.zloading.base.BaseStateBuilder;

public class StairsRectBuilder extends BaseStateBuilder {
  private final int FLOOR_NUM = 5;
  
  private volatile long mAnimateDurationTime = 500L;
  
  private volatile float mCurrAnimatedValue = 0.0F;
  
  private volatile int mCurrFloorNum = 0;
  
  private Paint mPaint;
  
  private float mR;
  
  private RectF mStairRectF;
  
  protected int getStateCount() { return 5; }
  
  protected void initParams(Context paramContext, Paint paramPaint) {
    this.mPaint = paramPaint;
    paramPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    this.mR = getAllSize();
    this.mStairRectF = new RectF();
  }
  
  protected void onComputeUpdateValue(ValueAnimator paramValueAnimator, float paramFloat, int paramInt) {
    this.mCurrFloorNum = paramInt;
    this.mCurrAnimatedValue = paramFloat;
  }
  
  protected void onDraw(Canvas paramCanvas) {
    float f1 = this.mR * 2.0F / 5.0F;
    float f2 = 0.5F * f1;
    float f3 = getViewCenterX() - this.mR;
    float f4 = getViewCenterY() + this.mR;
    this.mStairRectF.setEmpty();
    int i;
    for (i = 0; i < 5; i++) {
      if (i > this.mCurrFloorNum)
        return; 
      if (i == this.mCurrFloorNum) {
        RectF rectF = this.mStairRectF;
        float f = (i + 1) * f1;
        rectF.set(f3, f4 - f + f2, (f + f3) * this.mCurrAnimatedValue, f4 - i * f1);
      } else {
        RectF rectF = this.mStairRectF;
        float f = (i + 1) * f1;
        rectF.set(f3, f4 - f + f2, f + f3, f4 - i * f1);
      } 
      paramCanvas.drawRect(this.mStairRectF, this.mPaint);
    } 
  }
  
  protected void prepareEnd() {
    this.mCurrFloorNum = 0;
    this.mCurrAnimatedValue = 0.0F;
  }
  
  protected void prepareStart(ValueAnimator paramValueAnimator) {
    this.mAnimateDurationTime = ceil(getAnimationDuration() * 0.5D);
    paramValueAnimator.setDuration(this.mAnimateDurationTime);
    paramValueAnimator.setInterpolator((TimeInterpolator)new DecelerateInterpolator());
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\zyao89\view\zloading\rect\StairsRectBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */