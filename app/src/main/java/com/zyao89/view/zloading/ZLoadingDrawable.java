package com.zyao89.view.zloading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;

public class ZLoadingDrawable extends Drawable implements Animatable {
  private final ZLoadingBuilder mZLoadingBuilder;
  
  ZLoadingDrawable(ZLoadingBuilder paramZLoadingBuilder) {
    this.mZLoadingBuilder = paramZLoadingBuilder;
    paramZLoadingBuilder.setCallback(new Drawable.Callback() {
          public void invalidateDrawable(Drawable param1Drawable) { ZLoadingDrawable.this.invalidateSelf(); }
          
          public void scheduleDrawable(Drawable param1Drawable, Runnable param1Runnable, long param1Long) { ZLoadingDrawable.this.scheduleSelf(param1Runnable, param1Long); }
          
          public void unscheduleDrawable(Drawable param1Drawable, Runnable param1Runnable) { ZLoadingDrawable.this.unscheduleSelf(param1Runnable); }
        });
  }
  
  public void draw(Canvas paramCanvas) {
    if (!getBounds().isEmpty())
      this.mZLoadingBuilder.draw(paramCanvas); 
  }
  
  public int getIntrinsicHeight() { return (int)this.mZLoadingBuilder.getIntrinsicHeight(); }
  
  public int getIntrinsicWidth() { return (int)this.mZLoadingBuilder.getIntrinsicWidth(); }
  
  public int getOpacity() { return -3; }
  
  void initParams(Context paramContext) {
    ZLoadingBuilder zLoadingBuilder = this.mZLoadingBuilder;
    if (zLoadingBuilder != null) {
      zLoadingBuilder.init(paramContext);
      this.mZLoadingBuilder.initParams(paramContext);
    } 
  }
  
  public boolean isRunning() { return this.mZLoadingBuilder.isRunning(); }
  
  public void setAlpha(int paramInt) { this.mZLoadingBuilder.setAlpha(paramInt); }
  
  public void setColorFilter(ColorFilter paramColorFilter) { this.mZLoadingBuilder.setColorFilter(paramColorFilter); }
  
  public void start() { this.mZLoadingBuilder.start(); }
  
  public void stop() { this.mZLoadingBuilder.stop(); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\zyao89\view\zloading\ZLoadingDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */