package com.zyao89.view.zloading.ball;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import java.util.Iterator;
import java.util.LinkedList;

public class ElasticBallBuilder extends BaseBallBuilder {
  private static final int FINAL_STATE = 2;
  
  private static final int SUM_POINT_POS = 5;
  
  private final LinkedList<BaseBallBuilder.CirclePoint> mBGCircles = new LinkedList<BaseBallBuilder.CirclePoint>();
  
  private float mBallR;
  
  private float mCanvasTranslateOffset;
  
  private int mCurrAnimatorState = 0;
  
  private int mCurrPointPos = 0;
  
  private long mDurationTime = 333L;
  
  private boolean mIsReverse = false;
  
  private Path mPath;
  
  private void drawBG(Canvas paramCanvas) {
    paramCanvas.save();
    this.mPaint.setStyle(Paint.Style.STROKE);
    Iterator<BaseBallBuilder.CirclePoint> iterator = this.mBGCircles.iterator();
    while (iterator.hasNext())
      ((BaseBallBuilder.CirclePoint)iterator.next()).draw(paramCanvas, this.mBallR, this.mPaint); 
    paramCanvas.restore();
  }
  
  private void drawBall(Canvas paramCanvas) {
    paramCanvas.save();
    this.mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    float f1 = (this.mBGCircles.size() / 2);
    float f2 = this.mCanvasTranslateOffset;
    paramCanvas.translate(-(f1 * f2) + f2 * this.mCurrPointPos, 0.0F);
    drawBall(paramCanvas, this.mPath, this.mPaint);
    paramCanvas.restore();
  }
  
  private void initBGPoints() {
    float f1 = getViewCenterX();
    float f2 = getViewCenterY();
    BaseBallBuilder.CirclePoint circlePoint1 = new BaseBallBuilder.CirclePoint(f1 - this.mCanvasTranslateOffset * 2.0F, f2);
    BaseBallBuilder.CirclePoint circlePoint2 = new BaseBallBuilder.CirclePoint(f1 - this.mCanvasTranslateOffset, f2);
    BaseBallBuilder.CirclePoint circlePoint3 = new BaseBallBuilder.CirclePoint(f1, f2);
    BaseBallBuilder.CirclePoint circlePoint4 = new BaseBallBuilder.CirclePoint(this.mCanvasTranslateOffset + f1, f2);
    BaseBallBuilder.CirclePoint circlePoint5 = new BaseBallBuilder.CirclePoint(f1 + this.mCanvasTranslateOffset * 2.0F, f2);
    circlePoint1.setEnabled(false);
    this.mBGCircles.add(circlePoint1);
    this.mBGCircles.add(circlePoint2);
    this.mBGCircles.add(circlePoint3);
    this.mBGCircles.add(circlePoint4);
    this.mBGCircles.add(circlePoint5);
  }
  
  protected void computeUpdateValue(ValueAnimator paramValueAnimator, float paramFloat) {
    int i;
    float f = this.mCanvasTranslateOffset;
    if (this.mIsReverse) {
      i = this.mCurrAnimatorState + 3;
    } else {
      i = this.mCurrAnimatorState;
    } 
    if (i != 0) {
      if (i != 1) {
        if (i != 2) {
          if (i != 3) {
            if (i != 4) {
              if (i != 5)
                return; 
              paramValueAnimator.setDuration(this.mDurationTime + 333L);
              paramValueAnimator.setInterpolator((TimeInterpolator)new BounceInterpolator());
              circlePoint = this.mBallPoints.get(5);
              paramFloat = f * (1.0F - paramFloat);
              circlePoint.setOffsetX(paramFloat);
              ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(6)).setOffsetX(paramFloat);
              ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(7)).setOffsetX(paramFloat);
              return;
            } 
            circlePoint.setDuration(this.mDurationTime + 111L);
            circlePoint.setInterpolator((TimeInterpolator)new DecelerateInterpolator());
            circlePoint = this.mBallPoints.get(2);
            paramFloat = f * (1.0F - paramFloat);
            circlePoint.setOffsetX(paramFloat);
            ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(3)).setOffsetX(paramFloat);
            ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(4)).setOffsetX(paramFloat);
            ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(8)).setOffsetX(paramFloat);
            ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(9)).setOffsetX(paramFloat);
            ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(10)).setOffsetX(paramFloat);
            return;
          } 
          circlePoint.setDuration(this.mDurationTime);
          circlePoint.setInterpolator((TimeInterpolator)new AccelerateInterpolator());
          circlePoint = this.mBallPoints.get(0);
          paramFloat = f * (1.0F - paramFloat);
          circlePoint.setOffsetX(paramFloat);
          ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(1)).setOffsetX(paramFloat);
          ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(11)).setOffsetX(paramFloat);
          return;
        } 
        circlePoint.setDuration(this.mDurationTime + 333L);
        circlePoint.setInterpolator((TimeInterpolator)new BounceInterpolator());
        circlePoint = this.mBallPoints.get(0);
        paramFloat = f * paramFloat;
        circlePoint.setOffsetX(paramFloat);
        ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(1)).setOffsetX(paramFloat);
        ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(11)).setOffsetX(paramFloat);
        return;
      } 
      circlePoint.setDuration(this.mDurationTime + 111L);
      circlePoint.setInterpolator((TimeInterpolator)new DecelerateInterpolator());
      circlePoint = this.mBallPoints.get(2);
      paramFloat = f * paramFloat;
      circlePoint.setOffsetX(paramFloat);
      ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(3)).setOffsetX(paramFloat);
      ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(4)).setOffsetX(paramFloat);
      ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(8)).setOffsetX(paramFloat);
      ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(9)).setOffsetX(paramFloat);
      ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(10)).setOffsetX(paramFloat);
      return;
    } 
    circlePoint.setDuration(this.mDurationTime);
    circlePoint.setInterpolator((TimeInterpolator)new AccelerateInterpolator());
    BaseBallBuilder.CirclePoint circlePoint = this.mBallPoints.get(5);
    paramFloat = f * paramFloat;
    circlePoint.setOffsetX(paramFloat);
    ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(6)).setOffsetX(paramFloat);
    ((BaseBallBuilder.CirclePoint)this.mBallPoints.get(7)).setOffsetX(paramFloat);
  }
  
  protected void initParams(Context paramContext) {
    this.mBallR = getAllSize() / 5.0F;
    this.mCanvasTranslateOffset = getIntrinsicWidth() / 5.0F;
    this.mPath = new Path();
    initPaint(5.0F);
    initPoints(this.mBallR);
    initBGPoints();
  }
  
  public void onAnimationRepeat(Animator paramAnimator) {
    int i = this.mCurrAnimatorState + 1;
    this.mCurrAnimatorState = i;
    if (i > 2) {
      this.mCurrAnimatorState = 0;
      if (this.mIsReverse) {
        this.mCurrPointPos--;
      } else {
        this.mCurrPointPos++;
      } 
      i = this.mCurrPointPos;
      if (i >= 4) {
        this.mIsReverse = true;
        this.mCurrPointPos = 3;
        for (i = 0; i < this.mBGCircles.size(); i++) {
          BaseBallBuilder.CirclePoint circlePoint = this.mBGCircles.get(i);
          if (i == this.mBGCircles.size() - 1) {
            circlePoint.setEnabled(true);
          } else {
            circlePoint.setEnabled(false);
          } 
        } 
      } else if (i < 0) {
        this.mIsReverse = false;
        this.mCurrPointPos = 0;
        for (i = 0; i < this.mBGCircles.size(); i++) {
          BaseBallBuilder.CirclePoint circlePoint = this.mBGCircles.get(i);
          if (i == 0) {
            circlePoint.setEnabled(false);
          } else {
            circlePoint.setEnabled(true);
          } 
        } 
      } 
      if (this.mIsReverse) {
        Iterator<BaseBallBuilder.CirclePoint> iterator1 = this.mBallPoints.iterator();
        while (iterator1.hasNext())
          ((BaseBallBuilder.CirclePoint)iterator1.next()).setOffsetX(this.mCanvasTranslateOffset); 
        ((BaseBallBuilder.CirclePoint)this.mBGCircles.get(this.mCurrPointPos + 1)).setEnabled(true);
        return;
      } 
      Iterator<BaseBallBuilder.CirclePoint> iterator = this.mBallPoints.iterator();
      while (iterator.hasNext())
        ((BaseBallBuilder.CirclePoint)iterator.next()).setOffsetX(0.0F); 
      ((BaseBallBuilder.CirclePoint)this.mBGCircles.get(this.mCurrPointPos)).setEnabled(false);
    } 
  }
  
  protected void onDraw(Canvas paramCanvas) {
    drawBG(paramCanvas);
    drawBall(paramCanvas);
  }
  
  protected void prepareEnd() {}
  
  protected void prepareStart(ValueAnimator paramValueAnimator) {
    long l = ceil(((float)getAnimationDuration() * 0.3F));
    this.mDurationTime = l;
    paramValueAnimator.setDuration(l);
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\zyao89\view\zloading\ball\ElasticBallBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */