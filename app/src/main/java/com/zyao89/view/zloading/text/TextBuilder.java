package com.zyao89.view.zloading.text;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.animation.AccelerateInterpolator;
import com.zyao89.view.zloading.ZLoadingBuilder;

public class TextBuilder extends ZLoadingBuilder {
  private static final int BASE_ALPHA = 100;
  
  private static final String DEFAULT_TEXT = "Zyao89";
  
  private int mDrawTextCount = 0;
  
  private String mTextChars;
  
  private Paint mTextPaint;
  
  private boolean isNotEmpty() {
    String str = this.mTextChars;
    return (str != null && !str.isEmpty());
  }
  
  protected void computeUpdateValue(ValueAnimator paramValueAnimator, float paramFloat) { this.mTextPaint.setAlpha((int)(paramFloat * 155.0F) + 100); }
  
  protected void initParams(Context paramContext) {
    Paint paint = new Paint(1);
    this.mTextPaint = paint;
    paint.setColor(-16777216);
    this.mTextPaint.setDither(true);
    this.mTextPaint.setFilterBitmap(true);
    this.mTextPaint.setTextSize(getAllSize());
    this.mTextPaint.setStyle(Paint.Style.FILL);
    this.mTextPaint.setTextAlign(Paint.Align.LEFT);
    this.mTextChars = "Zyao89";
  }
  
  public void onAnimationRepeat(Animator paramAnimator) {
    if (isNotEmpty()) {
      int i = this.mDrawTextCount + 1;
      this.mDrawTextCount = i;
      if (i > (this.mTextChars.toCharArray()).length)
        this.mDrawTextCount = 0; 
    } 
  }
  
  protected void onDraw(Canvas paramCanvas) {
    if (isNotEmpty()) {
      int i = (this.mTextChars.toCharArray()).length;
      float f2 = this.mTextPaint.measureText(this.mTextChars, 0, i);
      Paint paint = new Paint(this.mTextPaint);
      paint.setAlpha(100);
      String str = this.mTextChars;
      float f1 = getViewCenterX();
      f2 /= 2.0F;
      paramCanvas.drawText(str, 0, i, f1 - f2, getViewCenterY(), paint);
      paramCanvas.drawText(this.mTextChars, 0, this.mDrawTextCount, getViewCenterX() - f2, getViewCenterY(), this.mTextPaint);
    } 
  }
  
  protected void prepareEnd() {}
  
  protected void prepareStart(ValueAnimator paramValueAnimator) {
    paramValueAnimator.setDuration(ceil(((float)getAnimationDuration() * 0.3F)));
    paramValueAnimator.setInterpolator((TimeInterpolator)new AccelerateInterpolator());
  }
  
  protected void setAlpha(int paramInt) { this.mTextPaint.setAlpha(paramInt); }
  
  protected void setColorFilter(ColorFilter paramColorFilter) { this.mTextPaint.setColorFilter(paramColorFilter); }
  
  public void setText(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return; 
    float f = this.mTextPaint.measureText(paramString);
    if (f >= getIntrinsicWidth()) {
      f /= getAllSize();
      this.mTextPaint.setTextSize(getIntrinsicWidth() / f);
    } 
    this.mTextChars = paramString;
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\zyao89\view\zloading\text\TextBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */