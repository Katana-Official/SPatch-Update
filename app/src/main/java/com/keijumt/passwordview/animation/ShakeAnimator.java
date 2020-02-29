package com.keijumt.passwordview.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\0008\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\t\n\002\b\005\n\002\020\b\n\002\b\013\n\002\020\002\n\000\n\002\030\002\n\002\b\003\b\000\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\020\020\032\032\0020\0332\006\020\034\032\0020\035H\026J\b\020\036\032\0020\033H\026J\b\020\037\032\0020\033H\026R\026\020\005\032\n \007*\004\030\0010\0060\006X\016¢\006\002\n\000R\032\020\b\032\0020\tX\016¢\006\016\n\000\032\004\b\n\020\013\"\004\b\f\020\rR\032\020\016\032\0020\017X\016¢\006\016\n\000\032\004\b\020\020\021\"\004\b\022\020\023R\032\020\024\032\0020\017X\016¢\006\016\n\000\032\004\b\025\020\021\"\004\b\026\020\023R\032\020\027\032\0020\tX\016¢\006\016\n\000\032\004\b\030\020\013\"\004\b\031\020\rR\016\020\002\032\0020\003X\004¢\006\002\n\000¨\006 "}, d2 = {"Lcom/keijumt/passwordview/animation/ShakeAnimator;", "Lcom/keijumt/passwordview/animation/Animator;", "target", "Landroid/view/View;", "(Landroid/view/View;)V", "animator", "Landroid/animation/ValueAnimator;", "kotlin.jvm.PlatformType", "duration", "", "getDuration", "()J", "setDuration", "(J)V", "shakeMaxWidth", "", "getShakeMaxWidth", "()I", "setShakeMaxWidth", "(I)V", "shakeTimes", "getShakeTimes", "setShakeTimes", "startDelay", "getStartDelay", "setStartDelay", "addListener", "", "listener", "Lcom/keijumt/passwordview/animation/Animator$AnimatorListener;", "removeAllListener", "start", "library_release"}, k = 1, mv = {1, 1, 13})
public final class ShakeAnimator extends Animator {
  private ValueAnimator animator;
  
  private long duration;
  
  private int shakeMaxWidth;
  
  private int shakeTimes;
  
  private long startDelay;
  
  private final View target;
  
  public ShakeAnimator(View paramView) {
    super(paramView);
    this.target = paramView;
    this.animator = ValueAnimator.ofFloat(new float[] { 0.0F, 1.0F });
    this.duration = 400L;
    this.shakeMaxWidth = 40;
    this.shakeTimes = 4;
  }
  
  public void addListener(Animator.AnimatorListener paramAnimatorListener) {
    Intrinsics.checkParameterIsNotNull(paramAnimatorListener, "listener");
    this.animator.addListener((Animator.AnimatorListener)new ShakeAnimator$addListener$1(paramAnimatorListener));
  }
  
  public final long getDuration() { return this.duration; }
  
  public final int getShakeMaxWidth() { return this.shakeMaxWidth; }
  
  public final int getShakeTimes() { return this.shakeTimes; }
  
  public final long getStartDelay() { return this.startDelay; }
  
  public void removeAllListener() { this.animator.removeAllListeners(); }
  
  public final void setDuration(long paramLong) { this.duration = paramLong; }
  
  public final void setShakeMaxWidth(int paramInt) { this.shakeMaxWidth = paramInt; }
  
  public final void setShakeTimes(int paramInt) { this.shakeTimes = paramInt; }
  
  public final void setStartDelay(long paramLong) { this.startDelay = paramLong; }
  
  public void start() {
    ValueAnimator valueAnimator = this.animator;
    valueAnimator.cancel();
    valueAnimator.setDuration(this.duration);
    valueAnimator.setStartDelay(this.startDelay);
    valueAnimator.addUpdateListener(new ShakeAnimator$start$$inlined$run$lambda$1(this));
    valueAnimator.start();
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\027\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000*\001\000\b\n\030\0002\0020\001J\022\020\002\032\0020\0032\b\020\004\032\004\030\0010\005H\026¨\006\006"}, d2 = {"com/keijumt/passwordview/animation/ShakeAnimator$addListener$1", "Landroid/animation/AnimatorListenerAdapter;", "onAnimationEnd", "", "animation", "Landroid/animation/Animator;", "library_release"}, k = 1, mv = {1, 1, 13})
  public static final class ShakeAnimator$addListener$1 extends AnimatorListenerAdapter {
    ShakeAnimator$addListener$1(Animator.AnimatorListener param1AnimatorListener) {}
    
    public void onAnimationEnd(Animator param1Animator) { this.$listener.onAnimationEnd(); }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\024\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\b\003\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005¨\006\006"}, d2 = {"<anonymous>", "", "it", "Landroid/animation/ValueAnimator;", "kotlin.jvm.PlatformType", "onAnimationUpdate", "com/keijumt/passwordview/animation/ShakeAnimator$start$1$1"}, k = 3, mv = {1, 1, 13})
  static final class ShakeAnimator$start$$inlined$run$lambda$1 implements ValueAnimator.AnimatorUpdateListener {
    ShakeAnimator$start$$inlined$run$lambda$1(ShakeAnimator param1ShakeAnimator) {}
    
    public final void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
      Intrinsics.checkExpressionValueIsNotNull(param1ValueAnimator, "it");
      Object object = param1ValueAnimator.getAnimatedValue();
      if (object != null) {
        float f = ((Float)object).floatValue();
        ShakeAnimator.this.target.setX(this.$initialPositionX + (float)Math.sin(ShakeAnimator.this.getShakeTimes() * Math.PI * f) * ShakeAnimator.this.getShakeMaxWidth() / 2);
        return;
      } 
      throw new TypeCastException("null cannot be cast to non-null type kotlin.Float");
    }
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\keijumt\passwordview\animation\ShakeAnimator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */