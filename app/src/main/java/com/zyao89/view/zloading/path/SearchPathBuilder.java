package com.zyao89.view.zloading.path;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.view.animation.DecelerateInterpolator;
import com.zyao89.view.zloading.ZLoadingBuilder;

public class SearchPathBuilder extends ZLoadingBuilder {
  private static final int FINAL_STATE = 3;
  
  private int mCurrAnimatorState = 0;
  
  private Path mDrawPath;
  
  private Paint mPaint;
  
  private Path mPath;
  
  private PathMeasure mPathMeasure;
  
  private Path mPathZoom;
  
  private float mR;
  
  private void initPaint() {
    Paint paint = new Paint(1);
    this.mPaint = paint;
    paint.setStyle(Paint.Style.STROKE);
    this.mPaint.setStrokeWidth(15.0F);
    this.mPaint.setColor(-16777216);
    this.mPaint.setDither(true);
    this.mPaint.setFilterBitmap(true);
    this.mPaint.setStrokeCap(Paint.Cap.ROUND);
    this.mPaint.setStrokeJoin(Paint.Join.ROUND);
  }
  
  private void initPathMeasure() {
    this.mDrawPath = new Path();
    this.mPathMeasure = new PathMeasure();
  }
  
  private void initPaths() {
    float f = this.mR * 0.4F;
    Path path1 = new Path();
    this.mPath = path1;
    path1.addArc(new RectF(getViewCenterX() - this.mR, getViewCenterY() - this.mR, getViewCenterX() + this.mR, getViewCenterY() + this.mR), 45.0F, 359.9F);
    this.mPathMeasure.setPath(this.mPath, false);
    float[] arrayOfFloat = new float[2];
    this.mPathMeasure.getPosTan(0.0F, arrayOfFloat, null);
    Path path2 = new Path();
    this.mPathZoom = path2;
    path2.addArc(new RectF(getViewCenterX() - f, getViewCenterY() - f, getViewCenterX() + f, getViewCenterY() + f), 45.0F, 359.9F);
    this.mPathZoom.lineTo(arrayOfFloat[0], arrayOfFloat[1]);
  }
  
  private void resetDrawPath() {
    this.mDrawPath.reset();
    this.mDrawPath.lineTo(0.0F, 0.0F);
  }
  
  protected void computeUpdateValue(ValueAnimator paramValueAnimator, float paramFloat) {
    int i = this.mCurrAnimatorState;
    if (i != 0 && i != 1) {
      if (i != 2) {
        if (i != 3)
          return; 
        this.mPathMeasure.setPath(this.mPathZoom, false);
        float f2 = this.mPathMeasure.getLength();
        this.mPathMeasure.getSegment((1.0F - paramFloat) * f2, f2, this.mDrawPath, true);
        return;
      } 
      resetDrawPath();
      this.mPathMeasure.setPath(this.mPath, false);
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
  
  protected void initParams(Context paramContext) {
    this.mR = getAllSize();
    initPaint();
    initPathMeasure();
    initPaths();
  }
  
  public void onAnimationRepeat(Animator paramAnimator) {
    int i = this.mCurrAnimatorState + 1;
    this.mCurrAnimatorState = i;
    if (i > 3)
      this.mCurrAnimatorState = 0; 
  }
  
  protected void onDraw(Canvas paramCanvas) { paramCanvas.drawPath(this.mDrawPath, this.mPaint); }
  
  protected void prepareEnd() {}
  
  protected void prepareStart(ValueAnimator paramValueAnimator) { paramValueAnimator.setInterpolator((TimeInterpolator)new DecelerateInterpolator()); }
  
  protected void setAlpha(int paramInt) { this.mPaint.setAlpha(paramInt); }
  
  protected void setColorFilter(ColorFilter paramColorFilter) { this.mPaint.setColorFilter(paramColorFilter); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\zyao89\view\zloading\path\SearchPathBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */