package com.zyao89.view.zloading.clock;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import com.zyao89.view.zloading.ZLoadingBuilder;

public class CircleBuilder extends ZLoadingBuilder {
  private static final float DEFAULT_ANGLE = -90.0F;
  
  private float mEndAngle;
  
  private RectF mInnerCircleRectF;
  
  private boolean mIsFirstState = true;
  
  private Paint mPaint;
  
  private float mStartAngle;
  
  private void initValues(Context paramContext) {
    float f1 = getAllSize() - dip2px(paramContext, 3.0F);
    this.mInnerCircleRectF = new RectF();
    this.mStartAngle = -90.0F;
    this.mEndAngle = -90.0F;
    float f2 = getViewCenterX();
    float f3 = getViewCenterY();
    this.mInnerCircleRectF.set(f2 - f1, f3 - f1, f2 + f1, f3 + f1);
  }
  
  protected void computeUpdateValue(ValueAnimator paramValueAnimator, float paramFloat) {
    if (this.mIsFirstState) {
      this.mEndAngle = paramFloat * 360.0F - 90.0F;
      return;
    } 
    this.mStartAngle = paramFloat * 360.0F - 90.0F;
  }
  
  protected Paint getPaint() { return this.mPaint; }
  
  protected void initParams(Context paramContext) {
    Paint paint = new Paint(1);
    this.mPaint = paint;
    paint.setColor(-16777216);
    initValues(paramContext);
  }
  
  public void onAnimationCancel(Animator paramAnimator) {
    this.mStartAngle = -90.0F;
    this.mEndAngle = -90.0F;
  }
  
  public void onAnimationEnd(Animator paramAnimator) {
    this.mStartAngle = -90.0F;
    this.mEndAngle = -90.0F;
  }
  
  public void onAnimationRepeat(Animator paramAnimator) {
    int i = this.mIsFirstState ^ true;
    this.mIsFirstState = i;
    if (i != 0) {
      this.mStartAngle = -90.0F;
      this.mEndAngle = -90.0F;
      return;
    } 
    this.mStartAngle = -90.0F;
    this.mEndAngle = 270.0F;
  }
  
  public void onAnimationStart(Animator paramAnimator) {
    this.mStartAngle = -90.0F;
    this.mEndAngle = -90.0F;
  }
  
  protected void onDraw(Canvas paramCanvas) {
    paramCanvas.save();
    RectF rectF = this.mInnerCircleRectF;
    float f = this.mStartAngle;
    paramCanvas.drawArc(rectF, f, this.mEndAngle - f, true, this.mPaint);
    paramCanvas.restore();
  }
  
  protected void prepareEnd() {}
  
  protected void prepareStart(ValueAnimator paramValueAnimator) {}
  
  protected void setAlpha(int paramInt) { this.mPaint.setAlpha(paramInt); }
  
  protected void setColorFilter(ColorFilter paramColorFilter) { this.mPaint.setColorFilter(paramColorFilter); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\zyao89\view\zloading\clock\CircleBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */