package com.zyao89.view.zloading.ball;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

public class InfectionBallBuilder extends BaseBallBuilder {
  private static final int FINAL_STATE = 4;
  
  private static final int SUM_POINT_POS = 3;
  
  private float mBallR;
  
  private float mCanvasTranslateOffset;
  
  private int mCurrAnimatorState = 0;
  
  private long mDurationTime = 888L;
  
  private long mDurationTime_1 = 222L;
  
  private long mDurationTime_2 = 333L;
  
  private long mDurationTime_3 = 1333L;
  
  private long mDurationTime_4 = 1333L;
  
  private Path mPath;
  
  private void drawBall(Canvas paramCanvas) {
    paramCanvas.save();
    this.mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    paramCanvas.translate(0.0F, -this.mCanvasTranslateOffset);
    drawBall(paramCanvas, this.mPath, this.mPaint);
    paramCanvas.restore();
  }
  
  protected void computeUpdateValue(ValueAnimator paramValueAnimator, float paramFloat) {
    BaseBallBuilder.CirclePoint circlePoint;
    float f = this.mCanvasTranslateOffset;
    int i = this.mCurrAnimatorState;
    if (i != 0) {
      int j = 0;
      if (i != 1) {
        if (i != 2) {
          if (i != 3) {
            if (i != 4)
              return; 
            paramValueAnimator.setDuration(this.mDurationTime_4);
            this.mPaint.setAlpha((int)((1.0F - paramFloat) * 255.0F));
            return;
          } 
          paramValueAnimator.setDuration(this.mDurationTime_3);
          paramValueAnimator.setInterpolator((TimeInterpolator)new DecelerateInterpolator());
          circlePoint = this.mBallPoints.get(8);
          float f1 = paramFloat * f;
          float f2 = f1 + f;
          circlePoint.setOffsetY(f2);
          ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(9)).setOffsetY(f2);
          ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(10)).setOffsetY(f2);
          ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(5)).setOffsetX(f1);
          ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(6)).setOffsetX(f1);
          ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(7)).setOffsetX(f1);
          circlePoint = this.mBallPoints.get(1);
          paramFloat = -paramFloat * f;
          circlePoint.setOffsetX(paramFloat);
          ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(0)).setOffsetX(paramFloat);
          ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(11)).setOffsetX(paramFloat);
          return;
        } 
        circlePoint.setDuration(this.mDurationTime_2);
        circlePoint.setInterpolator((TimeInterpolator)new AccelerateInterpolator());
        while (j < this.mBallPoints.size()) {
          if (j > 10 || j < 8) {
            ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(j)).setOffsetY(paramFloat * f + f);
          } else {
            ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(j)).setOffsetY(paramFloat * f);
          } 
          j++;
        } 
      } else {
        circlePoint.setDuration(this.mDurationTime_1);
        circlePoint.setInterpolator((TimeInterpolator)new LinearInterpolator());
        circlePoint = this.mBallPoints.get(5);
        paramFloat *= f;
        circlePoint.setOffsetY(paramFloat);
        ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(6)).setOffsetY(paramFloat);
        ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(7)).setOffsetY(paramFloat);
        ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(1)).setOffsetY(paramFloat);
        ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(0)).setOffsetY(paramFloat);
        ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(11)).setOffsetY(paramFloat);
        return;
      } 
    } else {
      circlePoint.setDuration(this.mDurationTime);
      circlePoint.setInterpolator((TimeInterpolator)new AccelerateInterpolator());
      circlePoint = this.mBallPoints.get(2);
      paramFloat *= f;
      circlePoint.setOffsetY(paramFloat);
      ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(3)).setOffsetY(paramFloat);
      ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(4)).setOffsetY(paramFloat);
    } 
  }
  
  protected void initParams(Context paramContext) {
    this.mBallR = getAllSize() / 3.0F;
    this.mCanvasTranslateOffset = getIntrinsicWidth() / 3.0F;
    this.mPath = new Path();
    initPaint(5.0F);
    initPoints(this.mBallR);
  }
  
  public void onAnimationRepeat(Animator paramAnimator) {
    int i = this.mCurrAnimatorState + 1;
    this.mCurrAnimatorState = i;
    if (i > 4) {
      this.mCurrAnimatorState = 0;
      for (BaseBallBuilder.CirclePoint circlePoint : this.mBallPoints) {
        circlePoint.setOffsetY(0.0F);
        circlePoint.setOffsetX(0.0F);
      } 
      this.mPaint.setAlpha(255);
    } 
  }
  
  protected void onDraw(Canvas paramCanvas) { drawBall(paramCanvas); }
  
  protected void prepareEnd() {}
  
  protected void prepareStart(ValueAnimator paramValueAnimator) {
    this.mDurationTime = ceil(getAnimationDuration() * 0.7D);
    this.mDurationTime_1 = ceil(getAnimationDuration() * 0.2D);
    this.mDurationTime_2 = ceil(getAnimationDuration() * 0.3D);
    this.mDurationTime_3 = getAnimationDuration();
    this.mDurationTime_4 = getAnimationDuration();
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\zyao89\view\zloading\ball\InfectionBallBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */