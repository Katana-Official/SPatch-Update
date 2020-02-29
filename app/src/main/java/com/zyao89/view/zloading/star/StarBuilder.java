package com.zyao89.view.zloading.star;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import com.zyao89.view.zloading.ZLoadingBuilder;

public class StarBuilder extends ZLoadingBuilder {
  private final ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
      public void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
        float f = ((Float)param1ValueAnimator.getAnimatedValue()).floatValue();
        StarBuilder starBuilder = StarBuilder.this;
        StarBuilder.access$002(starBuilder, starBuilder.getViewCenterY() * 0.4F * f);
        starBuilder = StarBuilder.this;
        StarBuilder.access$202(starBuilder, (starBuilder.mOffsetTranslateY + 10.0F) * 0.9F);
      }
    };
  
  private Paint mFullPaint;
  
  private float mOffsetTranslateY;
  
  private RectF mOvalRectF;
  
  private ValueAnimator mShadowAnimator;
  
  private float mShadowWidth;
  
  private float mStarInMidR;
  
  private float mStarInR;
  
  private float mStarOutMidR;
  
  private float mStarOutR;
  
  private Path mStarPath;
  
  private int mStartAngle;
  
  private Path createStarPath(int paramInt1, int paramInt2) {
    Path path = new Path();
    int j = 360 / paramInt1;
    int k = j / 2;
    float f1 = getViewCenterX();
    float f2 = this.mStarOutMidR;
    int i = paramInt2 - 5;
    path.moveTo(f1 + f2 * cos(i), getViewCenterY() + this.mStarOutMidR * sin(i));
    for (i = 0; i < paramInt1; i++) {
      int m = j * i + paramInt2;
      f1 = getViewCenterX();
      f2 = this.mStarOutMidR;
      int n = m - 5;
      path.lineTo(f1 + f2 * cos(n), getViewCenterY() + this.mStarOutMidR * sin(n));
      f1 = getViewCenterX();
      f2 = this.mStarOutR;
      float f3 = cos(m);
      float f4 = getViewCenterY();
      float f5 = this.mStarOutR;
      float f6 = sin(m);
      float f7 = getViewCenterX();
      float f8 = this.mStarOutMidR;
      n = m + 5;
      path.quadTo(f1 + f2 * f3, f4 + f5 * f6, f7 + f8 * cos(n), getViewCenterY() + this.mStarOutMidR * sin(n));
      f1 = getViewCenterX();
      f2 = this.mStarInR;
      m += k;
      n = m - 5;
      path.lineTo(f1 + f2 * cos(n), getViewCenterY() + this.mStarInR * sin(n));
      f1 = getViewCenterX();
      f2 = this.mStarInMidR;
      f3 = cos(m);
      f4 = getViewCenterY();
      f5 = this.mStarInMidR;
      f6 = sin(m);
      f7 = getViewCenterX();
      f8 = this.mStarInR;
      m += 5;
      path.quadTo(f1 + f2 * f3, f4 + f5 * f6, f7 + f8 * cos(m), getViewCenterY() + this.mStarInR * sin(m));
    } 
    path.close();
    return path;
  }
  
  private void initAnimator() {
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(new float[] { 0.0F, 1.0F, 0.0F });
    this.mShadowAnimator = valueAnimator;
    valueAnimator.setRepeatCount(-1);
    this.mShadowAnimator.setDuration(getAnimationDuration());
    this.mShadowAnimator.setStartDelay(getAnimationStartDelay());
    this.mShadowAnimator.setInterpolator((TimeInterpolator)new AccelerateDecelerateInterpolator());
  }
  
  private void initValue(Context paramContext) {
    float f = getAllSize() - dip2px(paramContext, 5.0F);
    this.mStarOutR = f;
    f *= 0.9F;
    this.mStarOutMidR = f;
    f *= 0.6F;
    this.mStarInR = f;
    this.mStarInMidR = f * 0.9F;
    this.mStartAngle = 0;
    this.mOffsetTranslateY = 0.0F;
    this.mStarPath = createStarPath(5, -18);
    this.mShadowWidth = this.mStarOutR;
    this.mOvalRectF = new RectF();
  }
  
  protected void computeUpdateValue(ValueAnimator paramValueAnimator, float paramFloat) { this.mStartAngle = (int)(paramFloat * 360.0F); }
  
  protected final float cos(int paramInt) { return (float)Math.cos(paramInt * Math.PI / 180.0D); }
  
  protected void initParams(Context paramContext) {
    Paint paint = new Paint(1);
    this.mFullPaint = paint;
    paint.setStyle(Paint.Style.FILL_AND_STROKE);
    this.mFullPaint.setStrokeWidth(2.0F);
    this.mFullPaint.setColor(-16777216);
    this.mFullPaint.setDither(true);
    this.mFullPaint.setFilterBitmap(true);
    initValue(paramContext);
    initAnimator();
  }
  
  protected void onDraw(Canvas paramCanvas) {
    paramCanvas.save();
    paramCanvas.translate(0.0F, this.mOffsetTranslateY);
    paramCanvas.rotate(this.mStartAngle, getViewCenterX(), getViewCenterY());
    paramCanvas.drawPath(this.mStarPath, this.mFullPaint);
    paramCanvas.restore();
    this.mOvalRectF.set(getViewCenterX() - this.mShadowWidth, getIntrinsicHeight() - 20.0F, getViewCenterX() + this.mShadowWidth, getIntrinsicHeight() - 10.0F);
    paramCanvas.drawOval(this.mOvalRectF, this.mFullPaint);
  }
  
  protected void prepareEnd() {
    this.mShadowAnimator.removeAllUpdateListeners();
    this.mShadowAnimator.removeAllListeners();
    this.mShadowAnimator.setRepeatCount(0);
    this.mShadowAnimator.setDuration(0L);
    this.mShadowAnimator.end();
  }
  
  protected void prepareStart(ValueAnimator paramValueAnimator) {
    paramValueAnimator.setInterpolator((TimeInterpolator)new DecelerateInterpolator());
    this.mShadowAnimator.setRepeatCount(-1);
    this.mShadowAnimator.setDuration(getAnimationDuration());
    this.mShadowAnimator.setStartDelay(getAnimationStartDelay());
    this.mShadowAnimator.addUpdateListener(this.mAnimatorUpdateListener);
    this.mShadowAnimator.start();
  }
  
  protected void setAlpha(int paramInt) { this.mFullPaint.setAlpha(paramInt); }
  
  protected void setColorFilter(ColorFilter paramColorFilter) { this.mFullPaint.setColorFilter(paramColorFilter); }
  
  protected final float sin(int paramInt) { return (float)Math.sin(paramInt * Math.PI / 180.0D); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\zyao89\view\zloading\star\StarBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */