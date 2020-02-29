package com.keijumt.passwordview.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import com.keijumt.passwordview.CircleView;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\0006\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\t\n\002\b\b\n\002\020\b\n\002\b\005\n\002\020\002\n\000\n\002\030\002\n\002\b\005\b \030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\020\020\026\032\0020\0272\006\020\030\032\0020\031H\026J\b\020\032\032\0020\021H$J\020\020\033\032\0020\0272\006\020\034\032\0020\021H&J\b\020\035\032\0020\027H\026R\020\020\005\032\004\030\0010\006X\016¢\006\002\n\000R\032\020\007\032\0020\bX\016¢\006\016\n\000\032\004\b\t\020\n\"\004\b\013\020\fR\032\020\r\032\0020\bX\016¢\006\016\n\000\032\004\b\016\020\n\"\004\b\017\020\fR\032\020\020\032\0020\021X\016¢\006\016\n\000\032\004\b\022\020\023\"\004\b\024\020\025¨\006\036"}, d2 = {"Lcom/keijumt/passwordview/animation/ColorChangeAnimation;", "Lcom/keijumt/passwordview/animation/Animator;", "target", "Lcom/keijumt/passwordview/CircleView;", "(Lcom/keijumt/passwordview/CircleView;)V", "animator", "Landroid/animation/ValueAnimator;", "duration", "", "getDuration", "()J", "setDuration", "(J)V", "startDelay", "getStartDelay", "setStartDelay", "toColor", "", "getToColor", "()I", "setToColor", "(I)V", "addListener", "", "listener", "Lcom/keijumt/passwordview/animation/Animator$AnimatorListener;", "getColor", "setColor", "color", "start", "library_release"}, k = 1, mv = {1, 1, 13})
public abstract class ColorChangeAnimation extends Animator {
  private ValueAnimator animator;
  
  private long duration = 200L;
  
  private long startDelay = 100L;
  
  private int toColor;
  
  public ColorChangeAnimation(CircleView paramCircleView) { super((View)paramCircleView); }
  
  public void addListener(Animator.AnimatorListener paramAnimatorListener) {
    Intrinsics.checkParameterIsNotNull(paramAnimatorListener, "listener");
    ValueAnimator valueAnimator = this.animator;
    if (valueAnimator != null)
      valueAnimator.addListener((Animator.AnimatorListener)new ColorChangeAnimation$addListener$1(paramAnimatorListener)); 
  }
  
  protected abstract int getColor();
  
  public final long getDuration() { return this.duration; }
  
  public final long getStartDelay() { return this.startDelay; }
  
  public final int getToColor() { return this.toColor; }
  
  public abstract void setColor(int paramInt);
  
  public final void setDuration(long paramLong) { this.duration = paramLong; }
  
  public final void setStartDelay(long paramLong) { this.startDelay = paramLong; }
  
  public final void setToColor(int paramInt) { this.toColor = paramInt; }
  
  public void start() {
    ValueAnimator valueAnimator = ValueAnimator.ofArgb(new int[] { getColor(), this.toColor });
    valueAnimator.setDuration(this.duration);
    valueAnimator.setStartDelay(this.startDelay);
    valueAnimator.addUpdateListener(new ColorChangeAnimation$start$$inlined$apply$lambda$1());
    this.animator = valueAnimator;
    if (valueAnimator != null)
      valueAnimator.start(); 
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\027\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000*\001\000\b\n\030\0002\0020\001J\022\020\002\032\0020\0032\b\020\004\032\004\030\0010\005H\026¨\006\006"}, d2 = {"com/keijumt/passwordview/animation/ColorChangeAnimation$addListener$1", "Landroid/animation/AnimatorListenerAdapter;", "onAnimationEnd", "", "animation", "Landroid/animation/Animator;", "library_release"}, k = 1, mv = {1, 1, 13})
  public static final class ColorChangeAnimation$addListener$1 extends AnimatorListenerAdapter {
    ColorChangeAnimation$addListener$1(Animator.AnimatorListener param1AnimatorListener) {}
    
    public void onAnimationEnd(Animator param1Animator) { this.$listener.onAnimationEnd(); }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\024\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\b\003\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005¨\006\006"}, d2 = {"<anonymous>", "", "it", "Landroid/animation/ValueAnimator;", "kotlin.jvm.PlatformType", "onAnimationUpdate", "com/keijumt/passwordview/animation/ColorChangeAnimation$start$1$1"}, k = 3, mv = {1, 1, 13})
  static final class ColorChangeAnimation$start$$inlined$apply$lambda$1 implements ValueAnimator.AnimatorUpdateListener {
    public final void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
      ColorChangeAnimation colorChangeAnimation = ColorChangeAnimation.this;
      Intrinsics.checkExpressionValueIsNotNull(param1ValueAnimator, "it");
      Object object = param1ValueAnimator.getAnimatedValue();
      if (object != null) {
        colorChangeAnimation.setColor(((Integer)object).intValue());
        return;
      } 
      throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
    }
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\keijumt\passwordview\animation\ColorChangeAnimation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */