package com.hitomi.refresh.view;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.sk.spatch.R;

abstract class FunGameView extends View {
  static final float DIVIDING_LINE_SIZE = 1.0F;
  
  static final int STATUS_GAME_FINISHED = 3;
  
  static final int STATUS_GAME_OVER = 2;
  
  static final int STATUS_GAME_PLAY = 1;
  
  static final int STATUS_GAME_PREPAR = 0;
  
  static final float VIEW_HEIGHT_RATIO = 0.161F;
  
  protected float controllerPosition;
  
  protected int controllerSize;
  
  protected int lModelColor;
  
  protected int mModelColor;
  
  protected Paint mPaint;
  
  protected int rModelColor;
  
  protected int screenHeight;
  
  protected int screenWidth;
  
  protected int status = 0;
  
  private String textGameOver;
  
  private String textLoading;
  
  private String textLoadingFinished;
  
  protected TextPaint textPaint;
  
  public FunGameView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.FunGame);
    this.lModelColor = typedArray.getColor(0, Color.rgb(0, 0, 0));
    this.mModelColor = typedArray.getColor(1, -16777216);
    this.rModelColor = typedArray.getColor(2, Color.parseColor("#A5A5A5"));
    typedArray.recycle();
    initBaseTools();
    initBaseConfigParams(paramContext);
    initConcreteView();
  }
  
  private void drawBoundary(Canvas paramCanvas) {
    this.mPaint.setColor(Color.parseColor("#606060"));
    paramCanvas.drawLine(0.0F, 0.0F, this.screenWidth, 0.0F, this.mPaint);
    paramCanvas.drawLine(0.0F, getMeasuredHeight(), this.screenWidth, getMeasuredHeight(), this.mPaint);
  }
  
  private void drawText(Canvas paramCanvas) {
    int i = this.status;
    if (i != 0 && i != 1) {
      if (i != 2) {
        if (i != 3)
          return; 
        this.textPaint.setTextSize(40.0F);
        promptText(paramCanvas, this.textLoadingFinished);
        return;
      } 
      this.textPaint.setTextSize(50.0F);
      promptText(paramCanvas, this.textGameOver);
      return;
    } 
    this.textPaint.setTextSize(50.0F);
    promptText(paramCanvas, this.textLoading);
  }
  
  private DisplayMetrics getScreenMetrics(Context paramContext) {
    WindowManager windowManager = (WindowManager)paramContext.getSystemService("window");
    DisplayMetrics displayMetrics = new DisplayMetrics();
    windowManager.getDefaultDisplay().getMetrics(displayMetrics);
    return displayMetrics;
  }
  
  private void promptText(Canvas paramCanvas, String paramString) { paramCanvas.drawText(paramString, (paramCanvas.getWidth() - this.textPaint.measureText(paramString)) * 0.5F, paramCanvas.getHeight() * 0.5F - (this.textPaint.ascent() + this.textPaint.descent()) * 0.5F, (Paint)this.textPaint); }
  
  protected abstract void drawGame(Canvas paramCanvas);
  
  public int getCurrStatus() { return this.status; }
  
  public String getTextGameOver() { return this.textGameOver; }
  
  public String getTextLoading() { return this.textLoading; }
  
  public String getTextLoadingFinished() { return this.textLoadingFinished; }
  
  protected void initBaseConfigParams(Context paramContext) {
    this.controllerPosition = 1.0F;
    this.screenWidth = (getScreenMetrics(paramContext)).widthPixels;
    this.screenHeight = (getScreenMetrics(paramContext)).heightPixels;
  }
  
  protected void initBaseTools() {
    TextPaint textPaint1 = new TextPaint(1);
    this.textPaint = textPaint1;
    textPaint1.setColor(Color.parseColor("#C1C2C2"));
    Paint paint = new Paint(1);
    this.mPaint = paint;
    paint.setStrokeWidth(1.0F);
  }
  
  protected abstract void initConcreteView();
  
  public void moveController(float paramFloat) {
    float f2 = getMeasuredHeight() - 2.0F - this.controllerSize;
    float f1 = paramFloat;
    if (paramFloat > f2)
      f1 = f2; 
    this.controllerPosition = f1;
    postInvalidate();
  }
  
  public void moveController2StartPoint(long paramLong) {
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(new float[] { this.controllerPosition, 1.0F });
    valueAnimator.setDuration(paramLong);
    valueAnimator.setInterpolator((TimeInterpolator)new AccelerateDecelerateInterpolator());
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
          public void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
            FunGameView.this.controllerPosition = Float.parseFloat(param1ValueAnimator.getAnimatedValue().toString());
            FunGameView.this.postInvalidate();
          }
        });
    valueAnimator.start();
  }
  
  protected void onDraw(Canvas paramCanvas) {
    drawBoundary(paramCanvas);
    drawText(paramCanvas);
    drawGame(paramCanvas);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) { setMeasuredDimension(this.screenWidth, (int)(this.screenHeight * 0.161F)); }
  
  public void postStatus(int paramInt) {
    this.status = paramInt;
    if (paramInt == 0)
      resetConfigParams(); 
    postInvalidate();
  }
  
  protected abstract void resetConfigParams();
  
  public void setTextGameOver(String paramString) { this.textGameOver = paramString; }
  
  public void setTextLoading(String paramString) { this.textLoading = paramString; }
  
  public void setTextLoadingFinished(String paramString) { this.textLoadingFinished = paramString; }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\hitomi\refresh\view\FunGameView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */