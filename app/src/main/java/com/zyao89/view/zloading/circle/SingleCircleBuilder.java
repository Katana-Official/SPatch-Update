package com.zyao89.view.zloading.circle;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import com.zyao89.view.zloading.ZLoadingBuilder;

public class SingleCircleBuilder extends ZLoadingBuilder {
  private static final int FINAL_STATE = 2;
  
  private static final int OUTER_CIRCLE_ANGLE = 320;
  
  private int mCurrAnimatorState = 0;
  
  private RectF mOuterCircleRectF;
  
  private int mRotateAngle;
  
  private int mStartRotateAngle;
  
  private Paint mStrokePaint;
  
  private void initPaint(float paramFloat) {
    Paint paint = new Paint(1);
    this.mStrokePaint = paint;
    paint.setStyle(Paint.Style.STROKE);
    this.mStrokePaint.setStrokeWidth(paramFloat);
    this.mStrokePaint.setColor(-1);
    this.mStrokePaint.setDither(true);
    this.mStrokePaint.setFilterBitmap(true);
    this.mStrokePaint.setStrokeCap(Paint.Cap.ROUND);
    this.mStrokePaint.setStrokeJoin(Paint.Join.ROUND);
  }
  
  protected void computeUpdateValue(ValueAnimator paramValueAnimator, float paramFloat) {
    this.mStartRotateAngle = (int)(360.0F * paramFloat);
    int i = this.mCurrAnimatorState;
    if (i != 0) {
      if (i != 1)
        return; 
      this.mRotateAngle = 320 - (int)(paramFloat * 320.0F);
      return;
    } 
    this.mRotateAngle = (int)(paramFloat * 320.0F);
  }
  
  protected void initParams(Context paramContext) {
    float f = getAllSize();
    initPaint(0.6F * f * 0.4F);
    this.mStartRotateAngle = 0;
    RectF rectF = new RectF();
    this.mOuterCircleRectF = rectF;
    rectF.set(getViewCenterX() - f, getViewCenterY() - f, getViewCenterX() + f, getViewCenterY() + f);
  }
  
  public void onAnimationRepeat(Animator paramAnimator) {
    int i = this.mCurrAnimatorState + 1;
    this.mCurrAnimatorState = i;
    if (i > 2)
      this.mCurrAnimatorState = 0; 
  }
  
  protected void onDraw(Canvas paramCanvas) {
    paramCanvas.save();
    paramCanvas.drawArc(this.mOuterCircleRectF, (this.mStartRotateAngle % 360), (this.mRotateAngle % 360), false, this.mStrokePaint);
    paramCanvas.restore();
  }
  
  protected void prepareEnd() {}
  
  protected void prepareStart(ValueAnimator paramValueAnimator) {}
  
  protected void setAlpha(int paramInt) { this.mStrokePaint.setAlpha(paramInt); }
  
  protected void setColorFilter(ColorFilter paramColorFilter) { this.mStrokePaint.setColorFilter(paramColorFilter); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\zyao89\view\zloading\circle\SingleCircleBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */