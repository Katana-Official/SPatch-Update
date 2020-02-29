package com.zyao89.view.zloading.circle;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import com.zyao89.view.zloading.ZLoadingBuilder;

public class RotateCircleBuilder extends ZLoadingBuilder {
  private static final int CIRCLE_NUM = 10;
  
  private float mDefaultAngle;
  
  private Paint mFullPaint;
  
  private float mInterRadius;
  
  private float mOuterRadius;
  
  private void drawCircles(Canvas paramCanvas) {
    int i;
    for (i = 0; i < 10; i++) {
      int j = (int)((36 * i) + this.mDefaultAngle);
      float f1 = getViewCenterX();
      float f2 = this.mOuterRadius;
      float f3 = cos(j);
      float f4 = getViewCenterY();
      float f5 = this.mOuterRadius;
      float f6 = sin(j);
      this.mFullPaint.setAlpha(25 * i);
      paramCanvas.drawCircle(f1 + f2 * f3, f4 + f5 * f6, i + this.mInterRadius, this.mFullPaint);
    } 
  }
  
  private void initPaint() {
    Paint paint = new Paint(1);
    this.mFullPaint = paint;
    paint.setStyle(Paint.Style.FILL);
    this.mFullPaint.setColor(-16777216);
    this.mFullPaint.setDither(true);
    this.mFullPaint.setFilterBitmap(true);
    this.mFullPaint.setStrokeCap(Paint.Cap.ROUND);
    this.mFullPaint.setStrokeJoin(Paint.Join.ROUND);
  }
  
  protected void computeUpdateValue(ValueAnimator paramValueAnimator, float paramFloat) { this.mDefaultAngle = paramFloat * 360.0F; }
  
  protected final float cos(int paramInt) { return (float)Math.cos(paramInt * Math.PI / 180.0D); }
  
  protected void initParams(Context paramContext) {
    this.mOuterRadius = getAllSize();
    initPaint();
    this.mInterRadius = dip2px(paramContext, 2.0F);
  }
  
  protected void onDraw(Canvas paramCanvas) { drawCircles(paramCanvas); }
  
  protected void prepareEnd() {}
  
  protected void prepareStart(ValueAnimator paramValueAnimator) {}
  
  protected void setAlpha(int paramInt) { this.mFullPaint.setAlpha(paramInt); }
  
  protected void setColorFilter(ColorFilter paramColorFilter) { this.mFullPaint.setColorFilter(paramColorFilter); }
  
  protected final float sin(int paramInt) { return (float)Math.sin(paramInt * Math.PI / 180.0D); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\zyao89\view\zloading\circle\RotateCircleBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */