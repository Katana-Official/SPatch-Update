package com.zyao89.view.zloading.ball;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import com.zyao89.view.zloading.ZLoadingBuilder;
import java.util.LinkedList;

abstract class BaseBallBuilder extends ZLoadingBuilder {
  private static final float PROP_VALUE = 0.55191505F;
  
  protected final LinkedList<CirclePoint> mBallPoints = new LinkedList<CirclePoint>();
  
  protected Paint mPaint;
  
  protected final void drawBall(Canvas paramCanvas, Path paramPath, Paint paramPaint) {
    paramPath.reset();
    paramPath.moveTo(((CirclePoint)this.mBallPoints.get(0)).getX(), ((CirclePoint)this.mBallPoints.get(0)).getY());
    paramPath.cubicTo(((CirclePoint)this.mBallPoints.get(1)).getX(), ((CirclePoint)this.mBallPoints.get(1)).getY(), ((CirclePoint)this.mBallPoints.get(2)).getX(), ((CirclePoint)this.mBallPoints.get(2)).getY(), ((CirclePoint)this.mBallPoints.get(3)).getX(), ((CirclePoint)this.mBallPoints.get(3)).getY());
    paramPath.cubicTo(((CirclePoint)this.mBallPoints.get(4)).getX(), ((CirclePoint)this.mBallPoints.get(4)).getY(), ((CirclePoint)this.mBallPoints.get(5)).getX(), ((CirclePoint)this.mBallPoints.get(5)).getY(), ((CirclePoint)this.mBallPoints.get(6)).getX(), ((CirclePoint)this.mBallPoints.get(6)).getY());
    paramPath.cubicTo(((CirclePoint)this.mBallPoints.get(7)).getX(), ((CirclePoint)this.mBallPoints.get(7)).getY(), ((CirclePoint)this.mBallPoints.get(8)).getX(), ((CirclePoint)this.mBallPoints.get(8)).getY(), ((CirclePoint)this.mBallPoints.get(9)).getX(), ((CirclePoint)this.mBallPoints.get(9)).getY());
    paramPath.cubicTo(((CirclePoint)this.mBallPoints.get(10)).getX(), ((CirclePoint)this.mBallPoints.get(10)).getY(), ((CirclePoint)this.mBallPoints.get(11)).getX(), ((CirclePoint)this.mBallPoints.get(11)).getY(), ((CirclePoint)this.mBallPoints.get(0)).getX(), ((CirclePoint)this.mBallPoints.get(0)).getY());
    paramCanvas.drawPath(paramPath, paramPaint);
  }
  
  protected void initPaint(float paramFloat) {
    Paint paint = new Paint(1);
    this.mPaint = paint;
    paint.setStyle(Paint.Style.FILL_AND_STROKE);
    this.mPaint.setStrokeWidth(paramFloat);
    this.mPaint.setColor(-16777216);
    this.mPaint.setDither(true);
    this.mPaint.setFilterBitmap(true);
    this.mPaint.setStrokeCap(Paint.Cap.ROUND);
    this.mPaint.setStrokeJoin(Paint.Join.ROUND);
  }
  
  protected final void initPoints(float paramFloat) {
    float f1 = getViewCenterX();
    float f5 = getViewCenterY();
    float f2 = f1 - paramFloat;
    CirclePoint circlePoint = new CirclePoint(f2, f5);
    this.mBallPoints.add(circlePoint);
    float f6 = 0.55191505F * paramFloat;
    float f7 = f5 + f6;
    circlePoint = new CirclePoint(f2, f7);
    this.mBallPoints.add(circlePoint);
    float f3 = f1 - f6;
    float f8 = f5 + paramFloat;
    circlePoint = new CirclePoint(f3, f8);
    this.mBallPoints.add(circlePoint);
    circlePoint = new CirclePoint(f1, f8);
    this.mBallPoints.add(circlePoint);
    float f4 = f1 + f6;
    circlePoint = new CirclePoint(f4, f8);
    this.mBallPoints.add(circlePoint);
    f8 = f1 + paramFloat;
    circlePoint = new CirclePoint(f8, f7);
    this.mBallPoints.add(circlePoint);
    circlePoint = new CirclePoint(f8, f5);
    this.mBallPoints.add(circlePoint);
    f6 = f5 - f6;
    circlePoint = new CirclePoint(f8, f6);
    this.mBallPoints.add(circlePoint);
    paramFloat = f5 - paramFloat;
    circlePoint = new CirclePoint(f4, paramFloat);
    this.mBallPoints.add(circlePoint);
    circlePoint = new CirclePoint(f1, paramFloat);
    this.mBallPoints.add(circlePoint);
    circlePoint = new CirclePoint(f3, paramFloat);
    this.mBallPoints.add(circlePoint);
    circlePoint = new CirclePoint(f2, f6);
    this.mBallPoints.add(circlePoint);
  }
  
  protected void setAlpha(int paramInt) { this.mPaint.setAlpha(paramInt); }
  
  protected void setColorFilter(ColorFilter paramColorFilter) { this.mPaint.setColorFilter(paramColorFilter); }
  
  static class CirclePoint {
    private boolean mEnabled = true;
    
    private float mOffsetX = 0.0F;
    
    private float mOffsetY = 0.0F;
    
    private final float mX;
    
    private final float mY;
    
    CirclePoint(float param1Float1, float param1Float2) {
      this.mX = param1Float1;
      this.mY = param1Float2;
    }
    
    void draw(Canvas param1Canvas, float param1Float, Paint param1Paint) {
      if (this.mEnabled)
        param1Canvas.drawCircle(getX(), getY(), param1Float, param1Paint); 
    }
    
    float getX() { return this.mX + this.mOffsetX; }
    
    float getY() { return this.mY + this.mOffsetY; }
    
    public void setEnabled(boolean param1Boolean) { this.mEnabled = param1Boolean; }
    
    void setOffsetX(float param1Float) { this.mOffsetX = param1Float; }
    
    void setOffsetY(float param1Float) { this.mOffsetY = param1Float; }
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\zyao89\view\zloading\ball\BaseBallBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */