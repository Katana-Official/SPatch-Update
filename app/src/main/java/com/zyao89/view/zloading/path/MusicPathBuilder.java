package com.zyao89.view.zloading.path;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import com.zyao89.view.zloading.base.BaseStateBuilder;
import java.util.Iterator;
import java.util.LinkedList;

public class MusicPathBuilder extends BaseStateBuilder {
  private final int MUSIC_LINE_COUNT = 5;
  
  private BounceInterpolator mBounceInterpolator;
  
  private DecelerateInterpolator mDecelerateInterpolator;
  
  private LinkedList<Path> mMusicDrawPaths;
  
  private LinkedList<MusicParam> mMusicParams;
  
  private LinkedList<Path> mMusicPaths;
  
  private boolean mOpenJump = false;
  
  private Paint mPaint;
  
  private PathMeasure mPathMeasure;
  
  private float mR;
  
  private void drawMusic(Canvas paramCanvas) {
    for (MusicParam musicParam : this.mMusicParams) {
      this.mPaint.setStrokeWidth(4.0F);
      paramCanvas.save();
      RectF rectF1 = musicParam.getCircleRectF();
      RectF rectF2 = new RectF(rectF1);
      float f = musicParam.getOffsetY();
      rectF2.set(rectF1.left, rectF1.top - f, rectF1.right, rectF1.bottom - f);
      paramCanvas.rotate(75.0F, rectF2.centerX(), rectF2.centerY());
      this.mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
      paramCanvas.drawOval(rectF2, this.mPaint);
      this.mPaint.setStyle(Paint.Style.STROKE);
      paramCanvas.restore();
      PointF pointF2 = musicParam.getLineStartPointF();
      PointF pointF1 = musicParam.getLineEndPointF();
      paramCanvas.drawLine(pointF2.x, pointF2.y - f, pointF1.x, pointF1.y - f, this.mPaint);
      this.mPaint.setStrokeWidth(2.0F);
    } 
  }
  
  private void initInterpolator() {
    this.mDecelerateInterpolator = new DecelerateInterpolator();
    this.mBounceInterpolator = new BounceInterpolator();
  }
  
  private void initMusicParams() {
    float f1 = this.mR;
    float f4 = 0.2F * f1;
    this.mMusicParams = new LinkedList<MusicParam>();
    float f2 = this.mR * 2.0F / 5.0F;
    float f3 = getViewCenterX();
    float f5 = f4 / 2.0F;
    f3 -= f5;
    f5 = getViewCenterX() + f5;
    float f6 = getViewCenterY() + f1 - 1.5F * f2;
    float f7 = getViewCenterY() + f1 - f2 * 0.5F;
    float f8 = this.mR;
    RectF rectF2 = new RectF(f3 - f8 * 0.5F, f6, f5 - f8 * 0.5F, f7);
    double d = f4 * 0.5D;
    f4 = (float)(Math.cos(75.0D) * d);
    MusicParam musicParam2 = new MusicParam(rectF2, new PointF(rectF2.right + f4, rectF2.centerY()), new PointF(rectF2.right + f4, rectF2.centerY() - f1));
    this.mMusicParams.add(musicParam2);
    f4 = this.mR;
    RectF rectF1 = new RectF(f3 + f4 * 0.5F, f6 - f2, f5 + f4 * 0.5F, f7 - f2);
    f2 = (float)(d * Math.cos(75.0D));
    MusicParam musicParam1 = new MusicParam(rectF1, new PointF(rectF1.right + f2, rectF1.centerY()), new PointF(rectF1.right + f2, rectF1.centerY() - f1));
    this.mMusicParams.add(musicParam1);
  }
  
  private void initPathMeasure() {
    this.mMusicDrawPaths = new LinkedList<Path>();
    for (int i = 0; i < 5; i++) {
      Path path = new Path();
      this.mMusicDrawPaths.add(path);
    } 
    this.mPathMeasure = new PathMeasure();
  }
  
  private void initPaths() {
    this.mMusicPaths = new LinkedList<Path>();
    float f1 = this.mR;
    float f2 = f1 * 2.0F / 5.0F;
    float f3 = getViewCenterX() - this.mR;
    float f4 = getViewCenterY();
    float f5 = this.mR;
    int i;
    for (i = 0; i < 5; i++) {
      Path path = new Path();
      float f = f4 + f5 - i * f2;
      path.moveTo(f3, f);
      path.lineTo(f3 + f1 * 2.0F, f);
      this.mMusicPaths.add(path);
    } 
  }
  
  private void resetDrawPath() {
    this.mOpenJump = false;
    for (Path path : this.mMusicDrawPaths) {
      path.reset();
      path.lineTo(0.0F, 0.0F);
    } 
    Iterator<MusicParam> iterator = this.mMusicParams.iterator();
    while (iterator.hasNext())
      ((MusicParam)iterator.next()).clear(); 
  }
  
  protected int getStateCount() { return 3; }
  
  protected void initParams(Context paramContext, Paint paramPaint) {
    this.mPaint = paramPaint;
    paramPaint.setStrokeWidth(2.0F);
    this.mR = getAllSize();
    initPaths();
    initPathMeasure();
    initMusicParams();
    initInterpolator();
  }
  
  protected void onComputeUpdateValue(ValueAnimator paramValueAnimator, float paramFloat, int paramInt) {
    MusicParam musicParam;
    boolean bool2 = false;
    boolean bool1 = false;
    if (paramInt != 0) {
      if (paramInt != 1) {
        if (paramInt != 2) {
          if (paramInt != 3)
            return; 
          this.mOpenJump = true;
          float f = this.mR * 2.0F / 5.0F;
          for (paramInt = bool1; paramInt < this.mMusicParams.size(); paramInt++) {
            musicParam = this.mMusicParams.get(paramInt);
            if (paramInt % 2 == 0) {
              musicParam.setOffsetY((1.0F - paramFloat) * f);
            } else {
              musicParam.setOffsetY(paramFloat * f);
            } 
          } 
        } else {
          musicParam.setInterpolator((TimeInterpolator)this.mBounceInterpolator);
          this.mOpenJump = true;
          float f = this.mR * 2.0F / 5.0F;
          for (paramInt = bool2; paramInt < this.mMusicParams.size(); paramInt++) {
            musicParam = this.mMusicParams.get(paramInt);
            if (paramInt % 2 == 0) {
              musicParam.setOffsetY(paramFloat * f);
            } else {
              musicParam.setOffsetY((1.0F - paramFloat) * f);
            } 
          } 
        } 
      } else {
        resetDrawPath();
        for (paramInt = 0; paramInt < 5; paramInt++) {
          this.mPathMeasure.setPath(this.mMusicPaths.get(paramInt), false);
          if (paramInt % 2 == 0) {
            float f = this.mPathMeasure.getLength();
            this.mPathMeasure.getSegment(0.0F, f * paramFloat, this.mMusicDrawPaths.get(paramInt), true);
          } else {
            float f1 = this.mPathMeasure.getLength();
            float f2 = this.mPathMeasure.getLength();
            this.mPathMeasure.getSegment(f2 * (1.0F - paramFloat), f1, this.mMusicDrawPaths.get(paramInt), true);
          } 
        } 
      } 
    } else {
      musicParam.setInterpolator((TimeInterpolator)this.mDecelerateInterpolator);
      resetDrawPath();
      for (paramInt = 0; paramInt < 5; paramInt++) {
        this.mPathMeasure.setPath(this.mMusicPaths.get(paramInt), false);
        if (paramInt % 2 == 0) {
          float f1 = this.mPathMeasure.getLength() * paramFloat;
          float f2 = (float)(f1 - (0.5D - Math.abs(paramFloat - 0.5D)) * 200.0D);
          this.mPathMeasure.getSegment(f2, f1, this.mMusicDrawPaths.get(paramInt), true);
        } else {
          float f2 = this.mPathMeasure.getLength();
          float f1 = 1.0F - paramFloat;
          f2 *= f1;
          f1 = (float)(f2 - (0.5D - Math.abs(f1 - 0.5D)) * 200.0D);
          this.mPathMeasure.getSegment(f1, f2, this.mMusicDrawPaths.get(paramInt), true);
        } 
      } 
    } 
  }
  
  protected void onDraw(Canvas paramCanvas) {
    Iterator<Path> iterator = this.mMusicDrawPaths.iterator();
    while (iterator.hasNext())
      paramCanvas.drawPath(iterator.next(), this.mPaint); 
    if (this.mOpenJump)
      drawMusic(paramCanvas); 
  }
  
  protected void prepareEnd() {}
  
  protected void prepareStart(ValueAnimator paramValueAnimator) { paramValueAnimator.setInterpolator((TimeInterpolator)this.mDecelerateInterpolator); }
  
  static class MusicParam {
    private final RectF mCircleRectF;
    
    private final PointF mLineEndPointF;
    
    private final PointF mLineStartPointF;
    
    private float mOffsetY = 0.0F;
    
    MusicParam(RectF param1RectF, PointF param1PointF1, PointF param1PointF2) {
      this.mCircleRectF = param1RectF;
      this.mLineStartPointF = param1PointF1;
      this.mLineEndPointF = param1PointF2;
    }
    
    void clear() { this.mOffsetY = 0.0F; }
    
    RectF getCircleRectF() { return this.mCircleRectF; }
    
    PointF getLineEndPointF() { return this.mLineEndPointF; }
    
    PointF getLineStartPointF() { return this.mLineStartPointF; }
    
    float getOffsetY() { return this.mOffsetY; }
    
    void setOffsetY(float param1Float) { this.mOffsetY = param1Float; }
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\zyao89\view\zloading\path\MusicPathBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */