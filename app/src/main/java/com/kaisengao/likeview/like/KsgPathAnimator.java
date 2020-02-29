package com.kaisengao.likeview.like;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

public class KsgPathAnimator extends BasePathAnimator {
  private int mCurrentPathCounts;
  
  private int mCurveDuration;
  
  private int mEnterDuration;
  
  private SparseArray<BasePathAnimator.CurveEvaluator> mPathArray = new SparseArray();
  
  private int mPicHeight;
  
  private int mPicWidth;
  
  private int mViewHeight;
  
  private int mViewWidth;
  
  KsgPathAnimator(int paramInt1, int paramInt2) {
    this.mEnterDuration = paramInt1;
    this.mCurveDuration = paramInt2;
  }
  
  private ValueAnimator generateCurveAnimation(BasePathAnimator.CurveEvaluator paramCurveEvaluator, View paramView) {
    byte b;
    PointF pointF = new PointF((this.mViewWidth - this.mPicWidth) / 2.0F, (this.mViewHeight - this.mPicHeight));
    float f = ((this.mViewWidth - this.mPicWidth) / 2);
    if (this.mRandom.nextBoolean()) {
      b = 1;
    } else {
      b = -1;
    } 
    ValueAnimator valueAnimator = ValueAnimator.ofObject(paramCurveEvaluator, new Object[] { pointF, new PointF(f + (b * this.mRandom.nextInt(100)), 0.0F) });
    valueAnimator.addUpdateListener(new CurveUpdateLister(paramView));
    valueAnimator.setInterpolator((TimeInterpolator)new LinearInterpolator());
    valueAnimator.setDuration(this.mCurveDuration);
    return valueAnimator;
  }
  
  private AnimatorSet generateEnterAnimation(View paramView) {
    AnimatorSet animatorSet = new AnimatorSet();
    ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(paramView, View.ALPHA, new float[] { 0.2F, 1.0F });
    ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(paramView, View.SCALE_X, new float[] { 0.2F, 1.0F });
    ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(paramView, View.SCALE_Y, new float[] { 0.2F, 1.0F });
    animatorSet.setInterpolator((TimeInterpolator)new LinearInterpolator());
    animatorSet.playTogether(new Animator[] { (Animator)objectAnimator2, (Animator)objectAnimator3, (Animator)objectAnimator1 });
    animatorSet.setDuration(this.mEnterDuration);
    return animatorSet;
  }
  
  private PointF getTogglePoint(int paramInt) {
    PointF pointF = new PointF();
    pointF.x = this.mRandom.nextInt(this.mViewWidth - 100);
    pointF.y = this.mRandom.nextInt(this.mViewHeight - 100) / paramInt;
    return pointF;
  }
  
  void setPic(int paramInt1, int paramInt2) {
    this.mPicWidth = paramInt1;
    this.mPicHeight = paramInt2;
  }
  
  void setView(int paramInt1, int paramInt2) {
    this.mViewWidth = paramInt1;
    this.mViewHeight = paramInt2;
  }
  
  public void start(View paramView, ViewGroup paramViewGroup, RelativeLayout.LayoutParams paramLayoutParams) {
    BasePathAnimator.CurveEvaluator curveEvaluator;
    paramViewGroup.addView(paramView, (ViewGroup.LayoutParams)paramLayoutParams);
    int i = this.mCurrentPathCounts + 1;
    this.mCurrentPathCounts = i;
    if (i > 10) {
      curveEvaluator = (BasePathAnimator.CurveEvaluator)this.mPathArray.get(Math.abs(this.mRandom.nextInt() % 10) + 1);
    } else {
      curveEvaluator = createPath(getTogglePoint(1), getTogglePoint(2));
      this.mPathArray.put(this.mCurrentPathCounts, curveEvaluator);
    } 
    AnimatorSet animatorSet1 = generateEnterAnimation(paramView);
    ValueAnimator valueAnimator = generateCurveAnimation(curveEvaluator, paramView);
    AnimatorSet animatorSet2 = new AnimatorSet();
    animatorSet2.playTogether(new Animator[] { (Animator)animatorSet1, (Animator)valueAnimator });
    animatorSet2.addListener((Animator.AnimatorListener)new AnimationEndListener(paramView, paramViewGroup));
    animatorSet2.start();
  }
  
  private class AnimationEndListener extends AnimatorListenerAdapter {
    private ViewGroup mParent;
    
    private View mTarget;
    
    AnimationEndListener(View param1View, ViewGroup param1ViewGroup) {
      this.mTarget = param1View;
      this.mParent = param1ViewGroup;
    }
    
    public void onAnimationEnd(Animator param1Animator) {
      super.onAnimationEnd(param1Animator);
      this.mParent.removeView(this.mTarget);
    }
  }
  
  private class CurveUpdateLister implements ValueAnimator.AnimatorUpdateListener {
    private View mTarget;
    
    CurveUpdateLister(View param1View) { this.mTarget = param1View; }
    
    public void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
      PointF pointF = (PointF)param1ValueAnimator.getAnimatedValue();
      this.mTarget.setX(pointF.x);
      this.mTarget.setY(pointF.y);
      this.mTarget.setAlpha(1.0F - param1ValueAnimator.getAnimatedFraction());
    }
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\kaisengao\likeview\like\KsgPathAnimator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */