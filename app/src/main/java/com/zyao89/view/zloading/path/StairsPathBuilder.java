package com.zyao89.view.zloading.path;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.animation.DecelerateInterpolator;
import com.zyao89.view.zloading.base.BaseStateBuilder;

public class StairsPathBuilder extends BaseStateBuilder {
  private final int FLOOR_NUM = 6;
  
  private Path mDrawPath;
  
  private Paint mPaint;
  
  private Path mPath;
  
  private Path mPathFinal;
  
  private PathMeasure mPathMeasure;
  
  private float mR;
  
  private void initPathMeasure() {
    this.mDrawPath = new Path();
    this.mPathMeasure = new PathMeasure();
  }
  
  private void initPaths() {
    this.mPath = new Path();
    float f1 = this.mR * 2.0F / 6.0F;
    float f2 = getViewCenterX() - this.mR;
    float f3 = getViewCenterY() + this.mR;
    this.mPath.moveTo(f2, f3);
    int i = 0;
    while (i < 6) {
      Path path1 = this.mPath;
      float f4 = i;
      float f5 = ++i * f1;
      float f6 = f3 - f5;
      path1.lineTo(f4 * f1 + f2, f6);
      this.mPath.lineTo(f5 + f2, f6);
    } 
    Path path = new Path(this.mPath);
    this.mPathFinal = path;
    path.lineTo(f1 * 6.0F + f2, f3);
    this.mPathFinal.lineTo(f2, f3);
  }
  
  private void resetDrawPath() {
    this.mDrawPath.reset();
    this.mDrawPath.lineTo(0.0F, 0.0F);
  }
  
  protected int getStateCount() { return 3; }
  
  protected void initParams(Context paramContext, Paint paramPaint) {
    this.mPaint = paramPaint;
    this.mR = getAllSize();
    initPathMeasure();
    initPaths();
  }
  
  protected void onComputeUpdateValue(ValueAnimator paramValueAnimator, float paramFloat, int paramInt) {
    if (paramInt != 0 && paramInt != 1) {
      if (paramInt != 2) {
        if (paramInt != 3)
          return; 
        resetDrawPath();
        this.mPathMeasure.setPath(this.mPathFinal, false);
        float f2 = this.mPathMeasure.getLength();
        this.mPathMeasure.getSegment(0.0F, f2 * (1.0F - paramFloat), this.mDrawPath, true);
        return;
      } 
      resetDrawPath();
      this.mPathMeasure.setPath(this.mPathFinal, false);
      float f1 = this.mPathMeasure.getLength();
      this.mPathMeasure.getSegment(0.0F, f1 * paramFloat, this.mDrawPath, true);
      return;
    } 
    resetDrawPath();
    this.mPathMeasure.setPath(this.mPath, false);
    float f = this.mPathMeasure.getLength() * paramFloat;
    paramFloat = (float)(f - (0.5D - Math.abs(paramFloat - 0.5D)) * 200.0D);
    this.mPathMeasure.getSegment(paramFloat, f, this.mDrawPath, true);
  }
  
  protected void onDraw(Canvas paramCanvas) { paramCanvas.drawPath(this.mDrawPath, this.mPaint); }
  
  protected void prepareEnd() {}
  
  protected void prepareStart(ValueAnimator paramValueAnimator) { paramValueAnimator.setInterpolator((TimeInterpolator)new DecelerateInterpolator()); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\zyao89\view\zloading\path\StairsPathBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */