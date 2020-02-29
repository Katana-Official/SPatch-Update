package com.zyao89.view.zloading.circle;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import com.zyao89.view.zloading.ZLoadingBuilder;

public class SnakeCircleBuilder extends ZLoadingBuilder {
  private static final int FINAL_STATE = 1;
  
  private int mAlpha = 255;
  
  private float mAntiRotateAngle;
  
  private int mCurrAnimatorState = 0;
  
  private Path mDrawPath;
  
  private RectF mInterCircleRectF;
  
  private float mInterRF;
  
  private RectF mOuterCircleRectF;
  
  private float mOuterRF;
  
  private Path mPath;
  
  private PathMeasure mPathMeasure;
  
  private float mRotateAngle;
  
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
  
  private void initPathMeasure() {
    this.mDrawPath = new Path();
    this.mPathMeasure = new PathMeasure();
  }
  
  private void initPaths() {
    Path path = new Path();
    this.mPath = path;
    float f2 = this.mOuterRF;
    float f1 = f2 * 0.3F;
    f2 = f2 * 0.3F * 0.5F;
    path.moveTo(getViewCenterX() - this.mOuterRF * 0.8F, getViewCenterY());
    this.mPath.lineTo(getViewCenterX() - f1, getViewCenterY());
    this.mPath.lineTo(getViewCenterX() - f2, getViewCenterY() + f2);
    this.mPath.lineTo(getViewCenterX() + f2, getViewCenterY() - f2);
    this.mPath.lineTo(getViewCenterX() + f1, getViewCenterY());
    this.mPath.lineTo(getViewCenterX() + this.mOuterRF * 0.8F, getViewCenterY());
  }
  
  private void resetDrawPath() {
    this.mDrawPath.reset();
    this.mDrawPath.lineTo(0.0F, 0.0F);
  }
  
  protected void computeUpdateValue(ValueAnimator paramValueAnimator, float paramFloat) {
    this.mRotateAngle = paramFloat * 360.0F;
    this.mAntiRotateAngle = (1.0F - paramFloat) * 360.0F;
    int i = this.mCurrAnimatorState;
    if (i != 0) {
      if (i != 1)
        return; 
      resetDrawPath();
      this.mPathMeasure.setPath(this.mPath, false);
      float f1 = this.mPathMeasure.getLength();
      float f2 = this.mPathMeasure.getLength();
      this.mPathMeasure.getSegment(f2 * paramFloat, f1, this.mDrawPath, true);
      return;
    } 
    resetDrawPath();
    this.mPathMeasure.setPath(this.mPath, false);
    float f = this.mPathMeasure.getLength();
    this.mPathMeasure.getSegment(0.0F, f * paramFloat, this.mDrawPath, true);
  }
  
  protected void initParams(Context paramContext) {
    float f = getAllSize() * 1.0F;
    this.mOuterRF = f;
    f *= 0.7F;
    this.mInterRF = f;
    initPaint(f * 0.4F);
    this.mRotateAngle = 0.0F;
    RectF rectF = new RectF();
    this.mOuterCircleRectF = rectF;
    rectF.set(getViewCenterX() - this.mOuterRF, getViewCenterY() - this.mOuterRF, getViewCenterX() + this.mOuterRF, getViewCenterY() + this.mOuterRF);
    rectF = new RectF();
    this.mInterCircleRectF = rectF;
    rectF.set(getViewCenterX() - this.mInterRF, getViewCenterY() - this.mInterRF, getViewCenterX() + this.mInterRF, getViewCenterY() + this.mInterRF);
    initPathMeasure();
    initPaths();
  }
  
  public void onAnimationRepeat(Animator paramAnimator) {
    int i = this.mCurrAnimatorState + 1;
    this.mCurrAnimatorState = i;
    if (i > 1)
      this.mCurrAnimatorState = 0; 
  }
  
  protected void onDraw(Canvas paramCanvas) {
    paramCanvas.save();
    this.mStrokePaint.setStrokeWidth(this.mOuterRF * 0.05F);
    this.mStrokePaint.setAlpha((int)(this.mAlpha * 0.6F));
    paramCanvas.drawCircle(getViewCenterX(), getViewCenterY(), this.mOuterRF, this.mStrokePaint);
    paramCanvas.drawCircle(getViewCenterX(), getViewCenterY(), this.mInterRF, this.mStrokePaint);
    paramCanvas.restore();
    paramCanvas.save();
    this.mStrokePaint.setStrokeWidth(this.mOuterRF * 0.1F);
    this.mStrokePaint.setAlpha(this.mAlpha);
    paramCanvas.rotate(this.mRotateAngle, getViewCenterX(), getViewCenterY());
    paramCanvas.drawArc(this.mOuterCircleRectF, 0.0F, 120.0F, false, this.mStrokePaint);
    paramCanvas.drawArc(this.mOuterCircleRectF, 180.0F, 120.0F, false, this.mStrokePaint);
    paramCanvas.restore();
    paramCanvas.save();
    this.mStrokePaint.setAlpha((int)(this.mAlpha * 0.6F));
    paramCanvas.drawPath(this.mDrawPath, this.mStrokePaint);
    paramCanvas.restore();
    paramCanvas.save();
    this.mStrokePaint.setStrokeWidth(this.mOuterRF * 0.1F);
    this.mStrokePaint.setAlpha(this.mAlpha);
    paramCanvas.rotate(this.mAntiRotateAngle, getViewCenterX(), getViewCenterY());
    paramCanvas.drawArc(this.mInterCircleRectF, 60.0F, 60.0F, false, this.mStrokePaint);
    paramCanvas.drawArc(this.mInterCircleRectF, 180.0F, 180.0F, false, this.mStrokePaint);
    paramCanvas.restore();
  }
  
  protected void prepareEnd() {}
  
  protected void prepareStart(ValueAnimator paramValueAnimator) {}
  
  protected void setAlpha(int paramInt) { this.mAlpha = paramInt; }
  
  protected void setColorFilter(ColorFilter paramColorFilter) { this.mStrokePaint.setColorFilter(paramColorFilter); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\zyao89\view\zloading\circle\SnakeCircleBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */