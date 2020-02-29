package com.zyao89.view.zloading.star;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.animation.DecelerateInterpolator;
import com.zyao89.view.zloading.ZLoadingBuilder;

public class LeafBuilder extends ZLoadingBuilder {
  private static final int FINAL_STATE = 2;
  
  private float mCenterCircleR;
  
  private int mCurrAnimatorState = 0;
  
  private Paint mFullPaint;
  
  private int mRotateAngle;
  
  private float mStarInMidR;
  
  private float mStarInR;
  
  private float mStarOutMidR;
  
  private float mStarOutR;
  
  private Path mStarPath;
  
  private void createStarPath(Path paramPath, int paramInt1, int paramInt2) {
    paramPath.reset();
    int j = 360 / paramInt1;
    int k = j / 2;
    float f1 = getViewCenterX();
    float f2 = this.mStarOutMidR;
    int i = paramInt2 - 5;
    paramPath.moveTo(f1 + f2 * cos(i), getViewCenterY() + this.mStarOutMidR * sin(i));
    for (i = 0; i < paramInt1; i++) {
      int m = j * i + paramInt2;
      f1 = getViewCenterX();
      f2 = this.mStarOutMidR;
      int n = m - 5;
      paramPath.lineTo(f1 + f2 * cos(n), getViewCenterY() + this.mStarOutMidR * sin(n));
      f1 = getViewCenterX();
      f2 = this.mStarOutR;
      float f3 = cos(m);
      float f4 = getViewCenterY();
      float f5 = this.mStarOutR;
      float f6 = sin(m);
      float f7 = getViewCenterX();
      float f8 = this.mStarOutMidR;
      n = m + 5;
      paramPath.quadTo(f1 + f2 * f3, f4 + f5 * f6, f7 + f8 * cos(n), getViewCenterY() + this.mStarOutMidR * sin(n));
      f1 = getViewCenterX();
      f2 = this.mStarInR;
      m += k;
      n = m - 5;
      paramPath.lineTo(f1 + f2 * cos(n), getViewCenterY() + this.mStarInR * sin(n));
      f1 = getViewCenterX();
      f2 = this.mStarInMidR;
      f3 = cos(m);
      f4 = getViewCenterY();
      f5 = this.mStarInMidR;
      f6 = sin(m);
      f7 = getViewCenterX();
      f8 = this.mStarInR;
      m += 5;
      paramPath.quadTo(f1 + f2 * f3, f4 + f5 * f6, f7 + f8 * cos(m), getViewCenterY() + this.mStarInR * sin(m));
    } 
    paramPath.close();
  }
  
  private void initPaint() {
    Paint paint = new Paint(1);
    this.mFullPaint = paint;
    paint.setStyle(Paint.Style.FILL);
    this.mFullPaint.setStrokeWidth(2.0F);
    this.mFullPaint.setColor(-1);
    this.mFullPaint.setDither(true);
    this.mFullPaint.setFilterBitmap(true);
  }
  
  protected void computeUpdateValue(ValueAnimator paramValueAnimator, float paramFloat) {
    int i = this.mCurrAnimatorState;
    if (i != 0) {
      if (i != 1) {
        if (i != 2)
          return; 
        this.mStarOutMidR = getAllSize() * (1.0F - paramFloat);
        return;
      } 
      this.mRotateAngle = (int)((1.0F - paramFloat) * 360.0F);
      return;
    } 
    this.mStarOutMidR = getAllSize() * paramFloat;
    this.mRotateAngle = (int)(paramFloat * 360.0F);
  }
  
  protected final float cos(int paramInt) { return (float)Math.cos(paramInt * Math.PI / 180.0D); }
  
  protected void initParams(Context paramContext) {
    initPaint();
    float f = getAllSize();
    this.mStarOutR = f;
    this.mStarOutMidR = 0.9F * f;
    this.mStarInR = 0.7F * f;
    this.mStarInMidR = f * 0.3F;
    this.mCenterCircleR = dip2px(paramContext, 3.0F);
    this.mRotateAngle = 0;
    this.mStarPath = new Path();
  }
  
  public void onAnimationRepeat(Animator paramAnimator) {
    int i = this.mCurrAnimatorState + 1;
    this.mCurrAnimatorState = i;
    if (i > 2)
      this.mCurrAnimatorState = 0; 
  }
  
  protected void onDraw(Canvas paramCanvas) {
    paramCanvas.save();
    paramCanvas.rotate(this.mRotateAngle, getViewCenterX(), getViewCenterY());
    createStarPath(this.mStarPath, 5, -18);
    this.mStarPath.addCircle(getViewCenterX(), getViewCenterY(), this.mCenterCircleR, Path.Direction.CW);
    this.mStarPath.setFillType(Path.FillType.EVEN_ODD);
    paramCanvas.drawPath(this.mStarPath, this.mFullPaint);
    paramCanvas.restore();
  }
  
  protected void prepareEnd() {}
  
  protected void prepareStart(ValueAnimator paramValueAnimator) { paramValueAnimator.setInterpolator((TimeInterpolator)new DecelerateInterpolator()); }
  
  protected void setAlpha(int paramInt) { this.mFullPaint.setAlpha(paramInt); }
  
  protected void setColorFilter(ColorFilter paramColorFilter) { this.mFullPaint.setColorFilter(paramColorFilter); }
  
  protected final float sin(int paramInt) { return (float)Math.sin(paramInt * Math.PI / 180.0D); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\zyao89\view\zloading\star\LeafBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */