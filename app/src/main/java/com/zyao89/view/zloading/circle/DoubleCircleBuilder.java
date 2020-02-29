package com.zyao89.view.zloading.circle;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import com.zyao89.view.zloading.ZLoadingBuilder;

public class DoubleCircleBuilder extends ZLoadingBuilder {
  private static final int INTER_CIRCLE_ANGLE = 90;
  
  private static final int OUTER_CIRCLE_ANGLE = 270;
  
  private RectF mInnerCircleRectF;
  
  private RectF mOuterCircleRectF;
  
  private int mRotateAngle;
  
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
  
  protected void computeUpdateValue(ValueAnimator paramValueAnimator, float paramFloat) { this.mRotateAngle = (int)(paramFloat * 360.0F); }
  
  protected void initParams(Context paramContext) {
    float f1 = getAllSize();
    float f2 = 0.6F * f1;
    initPaint(0.4F * f2);
    this.mRotateAngle = 0;
    RectF rectF = new RectF();
    this.mOuterCircleRectF = rectF;
    rectF.set(getViewCenterX() - f1, getViewCenterY() - f1, getViewCenterX() + f1, getViewCenterY() + f1);
    rectF = new RectF();
    this.mInnerCircleRectF = rectF;
    rectF.set(getViewCenterX() - f2, getViewCenterY() - f2, getViewCenterX() + f2, getViewCenterY() + f2);
  }
  
  protected void onDraw(Canvas paramCanvas) {
    paramCanvas.save();
    paramCanvas.drawArc(this.mOuterCircleRectF, (this.mRotateAngle % 360), 270.0F, false, this.mStrokePaint);
    paramCanvas.drawArc(this.mInnerCircleRectF, (270 - this.mRotateAngle % 360), 90.0F, false, this.mStrokePaint);
    paramCanvas.restore();
  }
  
  protected void prepareEnd() {}
  
  protected void prepareStart(ValueAnimator paramValueAnimator) {}
  
  protected void setAlpha(int paramInt) { this.mStrokePaint.setAlpha(paramInt); }
  
  protected void setColorFilter(ColorFilter paramColorFilter) { this.mStrokePaint.setColorFilter(paramColorFilter); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\zyao89\view\zloading\circle\DoubleCircleBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */