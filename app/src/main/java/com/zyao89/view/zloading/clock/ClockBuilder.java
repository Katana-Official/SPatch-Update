package com.zyao89.view.zloading.clock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;

public class ClockBuilder extends CircleBuilder {
  private RectF mBottomBtnRectF;
  
  private RectF mBtnRectF;
  
  private float mCircleSpace;
  
  private Paint mFillPaint;
  
  private float mOuterRadius;
  
  private Paint mStrokePaint;
  
  private void createFillPaint() {
    Paint paint = new Paint(1);
    this.mFillPaint = paint;
    paint.setStyle(Paint.Style.FILL_AND_STROKE);
    this.mFillPaint.setColor(-16777216);
  }
  
  private void createStrokePaint() {
    Paint paint = new Paint(1);
    this.mStrokePaint = paint;
    paint.setStyle(Paint.Style.STROKE);
    this.mStrokePaint.setStrokeWidth(this.mCircleSpace);
    this.mStrokePaint.setColor(-16777216);
  }
  
  private void initOptions(Context paramContext) {
    float f1 = getAllSize();
    this.mCircleSpace = 4.0F;
    this.mOuterRadius = f1 - 4.0F;
    float f6 = dip2px(paramContext, 8.0F);
    float f4 = dip2px(paramContext, 3.0F);
    float f3 = dip2px(paramContext, 3.0F);
    float f2 = dip2px(paramContext, 2.0F);
    float f5 = getViewCenterX();
    f6 /= 2.0F;
    this.mBtnRectF = new RectF(f5 - f6, getViewCenterY() - f1 - f2 - f4, getViewCenterX() + f6, getViewCenterY() - f1 - f2);
    f4 = getViewCenterX();
    f3 /= 2.0F;
    this.mBottomBtnRectF = new RectF(f4 - f3, getViewCenterY() - f1 - f2, getViewCenterX() + f3, getViewCenterY() - f1);
  }
  
  protected void initParams(Context paramContext) {
    super.initParams(paramContext);
    initOptions(paramContext);
    createStrokePaint();
    createFillPaint();
  }
  
  protected void onDraw(Canvas paramCanvas) {
    super.onDraw(paramCanvas);
    paramCanvas.drawCircle(getViewCenterX(), getViewCenterY(), this.mOuterRadius, this.mStrokePaint);
    paramCanvas.drawRect(this.mBtnRectF, this.mFillPaint);
    paramCanvas.drawRect(this.mBottomBtnRectF, this.mFillPaint);
    paramCanvas.save();
    paramCanvas.rotate(45.0F, getViewCenterX(), getViewCenterY());
    paramCanvas.drawRect(this.mBottomBtnRectF, this.mFillPaint);
    paramCanvas.restore();
    paramCanvas.translate(0.0F, 20.0F);
  }
  
  protected void setAlpha(int paramInt) {
    super.setAlpha(paramInt);
    this.mStrokePaint.setAlpha(paramInt);
    this.mFillPaint.setAlpha(paramInt);
  }
  
  protected void setColorFilter(ColorFilter paramColorFilter) {
    super.setColorFilter(paramColorFilter);
    this.mStrokePaint.setColorFilter(paramColorFilter);
    this.mFillPaint.setColorFilter(paramColorFilter);
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\zyao89\view\zloading\clock\ClockBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */