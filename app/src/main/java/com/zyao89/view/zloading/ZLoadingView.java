package com.zyao89.view.zloading;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class ZLoadingView extends ImageView {
  protected ZLoadingBuilder mZLoadingBuilder;
  
  private ZLoadingDrawable mZLoadingDrawable;
  
  public ZLoadingView(Context paramContext) { this(paramContext, null); }
  
  public ZLoadingView(Context paramContext, AttributeSet paramAttributeSet) { this(paramContext, paramAttributeSet, -1); }
  
  public ZLoadingView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet);
  }
  
  private void init(Context paramContext, AttributeSet paramAttributeSet) {
    try {
      TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ZLoadingView);
      int i = typedArray.getInt(R.styleable.ZLoadingView_z_type, 0);
      int j = typedArray.getColor(R.styleable.ZLoadingView_z_color, -16777216);
      float f = typedArray.getFloat(R.styleable.ZLoadingView_z_duration_percent, 1.0F);
      typedArray.recycle();
      setLoadingBuilder(Z_TYPE.values()[i], f);
      setColorFilter(j);
      return;
    } catch (Exception exception) {
      exception.printStackTrace();
      return;
    } 
  }
  
  private void initDurationTimePercent(double paramDouble) {
    ZLoadingBuilder zLoadingBuilder = this.mZLoadingBuilder;
    if (zLoadingBuilder != null) {
      zLoadingBuilder.setDurationTimePercent(paramDouble);
      return;
    } 
    throw new RuntimeException("mZLoadingBuilder is null.");
  }
  
  private void initZLoadingDrawable() {
    if (this.mZLoadingBuilder != null) {
      ZLoadingDrawable zLoadingDrawable = new ZLoadingDrawable(this.mZLoadingBuilder);
      this.mZLoadingDrawable = zLoadingDrawable;
      zLoadingDrawable.initParams(getContext());
      setImageDrawable(this.mZLoadingDrawable);
      return;
    } 
    throw new RuntimeException("mZLoadingBuilder is null.");
  }
  
  private void startAnimation() {
    ZLoadingDrawable zLoadingDrawable = this.mZLoadingDrawable;
    if (zLoadingDrawable != null)
      zLoadingDrawable.start(); 
  }
  
  private void stopAnimation() {
    ZLoadingDrawable zLoadingDrawable = this.mZLoadingDrawable;
    if (zLoadingDrawable != null)
      zLoadingDrawable.stop(); 
  }
  
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    startAnimation();
  }
  
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    stopAnimation();
  }
  
  protected void onVisibilityChanged(View paramView, int paramInt) {
    super.onVisibilityChanged(paramView, paramInt);
    if (paramInt == 0 && getVisibility() == 0) {
      paramInt = 1;
    } else {
      paramInt = 0;
    } 
    if (paramInt != 0) {
      startAnimation();
      return;
    } 
    stopAnimation();
  }
  
  public void setLoadingBuilder(Z_TYPE paramZ_TYPE) {
    this.mZLoadingBuilder = paramZ_TYPE.newInstance();
    initZLoadingDrawable();
  }
  
  public void setLoadingBuilder(Z_TYPE paramZ_TYPE, double paramDouble) {
    setLoadingBuilder(paramZ_TYPE);
    initDurationTimePercent(paramDouble);
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\zyao89\view\zloading\ZLoadingView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */