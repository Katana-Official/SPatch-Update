package com.zyao89.view.zloading.ball;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.animation.AccelerateInterpolator;

public class IntertwineBuilder extends BaseBallBuilder {
  private static final int FINAL_STATE = 1;
  
  private float mBallR;
  
  private int mCurrAnimatorState = 0;
  
  private Path mPath;
  
  private void drawBall(Canvas paramCanvas) {
    paramCanvas.save();
    this.mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    drawBall(paramCanvas, this.mPath, this.mPaint);
    paramCanvas.restore();
  }
  
  protected void computeUpdateValue(ValueAnimator paramValueAnimator, float paramFloat) {
    BaseBallBuilder.CirclePoint circlePoint;
    int j = this.mCurrAnimatorState;
    boolean bool = false;
    int i = 0;
    if (j != 0) {
      if (j != 1)
        return; 
      paramValueAnimator.setInterpolator((TimeInterpolator)new AccelerateInterpolator());
      while (i < this.mBallPoints.size()) {
        circlePoint = this.mBallPoints.get(i);
        if (2 <= i && i <= 7) {
          float f1 = -this.mBallR;
          float f2 = 1.0F - paramFloat;
          circlePoint.setOffsetX(f1 * f2);
          circlePoint.setOffsetY(-this.mBallR * f2);
        } else {
          float f1 = this.mBallR;
          float f2 = 1.0F - paramFloat;
          circlePoint.setOffsetX(f1 * f2);
          circlePoint.setOffsetY(this.mBallR * f2);
        } 
        i++;
      } 
    } else {
      circlePoint.setInterpolator((TimeInterpolator)new AccelerateInterpolator());
      for (i = bool; i < this.mBallPoints.size(); i++) {
        circlePoint = this.mBallPoints.get(i);
        if (2 <= i && i <= 7) {
          circlePoint.setOffsetX(-this.mBallR * paramFloat);
          circlePoint.setOffsetY(-this.mBallR * paramFloat);
        } else {
          circlePoint.setOffsetX(this.mBallR * paramFloat);
          circlePoint.setOffsetY(this.mBallR * paramFloat);
        } 
      } 
    } 
  }
  
  protected void initParams(Context paramContext) {
    this.mBallR = getAllSize();
    this.mPath = new Path();
    initPaint(5.0F);
    initPoints(this.mBallR);
  }
  
  public void onAnimationRepeat(Animator paramAnimator) {
    int i = this.mCurrAnimatorState + 1;
    this.mCurrAnimatorState = i;
    if (i > 1) {
      this.mCurrAnimatorState = 0;
      for (BaseBallBuilder.CirclePoint circlePoint : this.mBallPoints) {
        circlePoint.setOffsetY(0.0F);
        circlePoint.setOffsetX(0.0F);
      } 
    } 
  }
  
  protected void onDraw(Canvas paramCanvas) { drawBall(paramCanvas); }
  
  protected void prepareEnd() {}
  
  protected void prepareStart(ValueAnimator paramValueAnimator) {}
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\zyao89\view\zloading\ball\IntertwineBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */