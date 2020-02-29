package com.zyao89.view.zloading.circle;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import com.zyao89.view.zloading.ZLoadingBuilder;

public class PacManBuilder extends ZLoadingBuilder {
  private static final int FINAL_STATE = 9;
  
  private static final int MAX_MOUTH_ANGLE = 45;
  
  private int HorizontalAngle;
  
  private int mCurrAnimatorState = 0;
  
  private float mDefaultStartMoveX;
  
  private long mDurationTime = 333L;
  
  private Paint mFullPaint;
  
  private float mLastMoveDistance;
  
  private float mMaxMoveRange;
  
  private int mMouthAngle;
  
  private float mMoveDistance;
  
  private RectF mOuterCircleRectF;
  
  private void initPaint() {
    Paint paint = new Paint(1);
    this.mFullPaint = paint;
    paint.setStyle(Paint.Style.FILL);
    this.mFullPaint.setColor(-1);
    this.mFullPaint.setDither(true);
    this.mFullPaint.setFilterBitmap(true);
    this.mFullPaint.setStrokeCap(Paint.Cap.ROUND);
    this.mFullPaint.setStrokeJoin(Paint.Join.ROUND);
  }
  
  protected void computeUpdateValue(ValueAnimator paramValueAnimator, float paramFloat) {
    float f = this.mMaxMoveRange / 5;
    if (this.mCurrAnimatorState < 5) {
      this.HorizontalAngle = 0;
      this.mMoveDistance = this.mLastMoveDistance + f * paramFloat;
    } else {
      this.HorizontalAngle = 180;
      this.mMoveDistance = this.mLastMoveDistance - f * paramFloat;
    } 
    if (this.mCurrAnimatorState % 2 == 0) {
      this.mMouthAngle = (int)(paramFloat * 45.0F) + 5;
      return;
    } 
    this.mMouthAngle = (int)((1.0F - paramFloat) * 45.0F) + 5;
  }
  
  protected void initParams(Context paramContext) {
    float f = getAllSize() * 0.7F;
    this.mMaxMoveRange = getIntrinsicWidth() + 2.0F * f;
    initPaint();
    this.mMouthAngle = 45;
    this.HorizontalAngle = 0;
    this.mDefaultStartMoveX = -this.mMaxMoveRange * 0.5F;
    this.mMoveDistance = 0.0F;
    this.mOuterCircleRectF = new RectF(getViewCenterX() - f, getViewCenterY() - f, getViewCenterX() + f, getViewCenterY() + f);
  }
  
  public void onAnimationRepeat(Animator paramAnimator) {
    int i = this.mCurrAnimatorState + 1;
    this.mCurrAnimatorState = i;
    if (i > 9)
      this.mCurrAnimatorState = 0; 
    float f = this.mMaxMoveRange / 5;
    i = this.mCurrAnimatorState;
    if (i < 5) {
      this.mLastMoveDistance = f * i;
      return;
    } 
    this.mLastMoveDistance = f * (5 - i % 5);
  }
  
  protected void onDraw(Canvas paramCanvas) {
    paramCanvas.save();
    paramCanvas.translate(this.mDefaultStartMoveX + this.mMoveDistance, 0.0F);
    paramCanvas.rotate(this.HorizontalAngle, getViewCenterX(), getViewCenterY());
    RectF rectF = this.mOuterCircleRectF;
    int i = this.mMouthAngle;
    paramCanvas.drawArc(rectF, i, (360 - i * 2), true, this.mFullPaint);
    paramCanvas.restore();
  }
  
  protected void prepareEnd() {}
  
  protected void prepareStart(ValueAnimator paramValueAnimator) {
    long l = ceil(getAnimationDuration() * 0.3D);
    this.mDurationTime = l;
    paramValueAnimator.setDuration(l);
  }
  
  protected void setAlpha(int paramInt) { this.mFullPaint.setAlpha(paramInt); }
  
  protected void setColorFilter(ColorFilter paramColorFilter) { this.mFullPaint.setColorFilter(paramColorFilter); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\zyao89\view\zloading\circle\PacManBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */