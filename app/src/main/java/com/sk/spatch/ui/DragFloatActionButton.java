package com.sk.spatch.ui;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DragFloatActionButton extends FloatingActionButton {
  private boolean isDrag;
  
  private int lastX;
  
  private int lastY;
  
  private int screenHeight;
  
  private int screenWidth;
  
  private int screenWidthHalf;
  
  private int statusHeight;
  
  private int virtualHeight;
  
  public DragFloatActionButton(Context paramContext) {
    super(paramContext);
    init();
  }
  
  public DragFloatActionButton(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    init();
  }
  
  public DragFloatActionButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }
  
  private void init() {
    int i = ScreenUtils.getScreenWidth(getContext());
    this.screenWidth = i;
    this.screenWidthHalf = i / 2;
    this.screenHeight = ScreenUtils.getScreenHeight(getContext());
    this.statusHeight = ScreenUtils.getStatusHeight(getContext());
    this.virtualHeight = ScreenUtils.getVirtualBarHeigh(getContext());
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    int i = (int)paramMotionEvent.getRawX();
    int j = (int)paramMotionEvent.getRawY();
    int k = paramMotionEvent.getAction() & 0xFF;
    boolean bool = false;
    if (k != 0) {
      float f = 0.0F;
      if (k != 1) {
        if (k == 2) {
          this.isDrag = true;
          k = i - this.lastX;
          int m = j - this.lastY;
          if ((int)Math.sqrt((k * k + m * m)) < 3) {
            this.isDrag = false;
          } else {
            float f1;
            float f3 = getX() + k;
            float f2 = getY() + m;
            if (f3 < 0.0F) {
              f1 = 0.0F;
            } else {
              f1 = f3;
              if (f3 > (this.screenWidth - getWidth()))
                f1 = (this.screenWidth - getWidth()); 
            } 
            if (f2 >= 0.0F)
              f = f2; 
            f2 = f;
            if (f > (this.screenHeight - this.statusHeight - getHeight()))
              f2 = (this.screenHeight - this.statusHeight - getHeight()); 
            setX(f1);
            setY(f2);
            this.lastX = i;
            this.lastY = j;
          } 
        } 
      } else if (this.isDrag) {
        setPressed(false);
        if (i >= this.screenWidthHalf) {
          animate().setInterpolator((TimeInterpolator)new DecelerateInterpolator()).setDuration(500L).xBy((this.screenWidth - getWidth()) - getX()).start();
        } else {
          ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "x", new float[] { getX(), 0.0F });
          objectAnimator.setInterpolator((TimeInterpolator)new DecelerateInterpolator());
          objectAnimator.setDuration(500L);
          objectAnimator.start();
        } 
      } 
    } else {
      this.isDrag = false;
      getParent().requestDisallowInterceptTouchEvent(true);
      this.lastX = i;
      this.lastY = j;
    } 
    if (this.isDrag || super.onTouchEvent(paramMotionEvent))
      bool = true; 
    return bool;
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatc\\ui\DragFloatActionButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */