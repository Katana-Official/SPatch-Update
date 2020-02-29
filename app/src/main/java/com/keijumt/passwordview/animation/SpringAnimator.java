package com.keijumt.passwordview.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.view.View;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\0006\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\t\n\002\b\005\n\002\020\007\n\002\b\f\n\002\020\002\n\000\n\002\030\002\n\002\b\002\b\000\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\020\020\032\032\0020\0332\006\020\034\032\0020\035H\026J\b\020\036\032\0020\033H\026R\016\020\005\032\0020\006X\004¢\006\002\n\000R\032\020\007\032\0020\bX\016¢\006\016\n\000\032\004\b\t\020\n\"\004\b\013\020\fR$\020\017\032\0020\0162\006\020\r\032\0020\016@FX\016¢\006\016\n\000\032\004\b\020\020\021\"\004\b\022\020\023R$\020\024\032\0020\0162\006\020\r\032\0020\016@FX\016¢\006\016\n\000\032\004\b\025\020\021\"\004\b\026\020\023R\032\020\027\032\0020\bX\016¢\006\016\n\000\032\004\b\030\020\n\"\004\b\031\020\fR\016\020\002\032\0020\003X\004¢\006\002\n\000¨\006\037"}, d2 = {"Lcom/keijumt/passwordview/animation/SpringAnimator;", "Lcom/keijumt/passwordview/animation/Animator;", "target", "Landroid/view/View;", "(Landroid/view/View;)V", "animatorSet", "Landroid/animation/AnimatorSet;", "duration", "", "getDuration", "()J", "setDuration", "(J)V", "value", "", "moveBottomY", "getMoveBottomY", "()F", "setMoveBottomY", "(F)V", "moveTopY", "getMoveTopY", "setMoveTopY", "startDelay", "getStartDelay", "setStartDelay", "addListener", "", "listener", "Lcom/keijumt/passwordview/animation/Animator$AnimatorListener;", "start", "library_release"}, k = 1, mv = {1, 1, 13})
public final class SpringAnimator extends Animator {
  private final AnimatorSet animatorSet;
  
  private long duration;
  
  private float moveBottomY;
  
  private float moveTopY;
  
  private long startDelay;
  
  private final View target;
  
  public SpringAnimator(View paramView) {
    super(paramView);
    this.target = paramView;
    this.animatorSet = new AnimatorSet();
    this.duration = 150L;
    this.moveTopY = this.target.getY() - 30.0F;
    this.moveBottomY = this.target.getY() + 10.0F;
  }
  
  public void addListener(Animator.AnimatorListener paramAnimatorListener) {
    Intrinsics.checkParameterIsNotNull(paramAnimatorListener, "listener");
    this.animatorSet.addListener((Animator.AnimatorListener)new SpringAnimator$addListener$1(paramAnimatorListener));
  }
  
  public final long getDuration() { return this.duration; }
  
  public final float getMoveBottomY() { return this.moveBottomY; }
  
  public final float getMoveTopY() { return this.moveTopY; }
  
  public final long getStartDelay() { return this.startDelay; }
  
  public final void setDuration(long paramLong) { this.duration = paramLong; }
  
  public final void setMoveBottomY(float paramFloat) { this.moveBottomY = this.target.getY() + paramFloat; }
  
  public final void setMoveTopY(float paramFloat) { this.moveTopY = this.target.getY() - paramFloat; }
  
  public final void setStartDelay(long paramLong) { this.startDelay = paramLong; }
  
  public void start() {
    AnimatorSet animatorSet1 = this.animatorSet;
    float f = this.target.getY();
    ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(this.target, "y", new float[] { this.moveTopY });
    ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(this.target, "y", new float[] { this.moveBottomY });
    ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(this.target, "y", new float[] { f });
    animatorSet1.playSequentially(new Animator[] { (Animator)objectAnimator1, (Animator)objectAnimator2, (Animator)objectAnimator3 });
    animatorSet1.setInterpolator((TimeInterpolator)new FastOutSlowInInterpolator());
    animatorSet1.setDuration(this.duration);
    animatorSet1.setStartDelay(this.startDelay);
    animatorSet1.start();
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\027\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000*\001\000\b\n\030\0002\0020\001J\022\020\002\032\0020\0032\b\020\004\032\004\030\0010\005H\026¨\006\006"}, d2 = {"com/keijumt/passwordview/animation/SpringAnimator$addListener$1", "Landroid/animation/AnimatorListenerAdapter;", "onAnimationEnd", "", "animation", "Landroid/animation/Animator;", "library_release"}, k = 1, mv = {1, 1, 13})
  public static final class SpringAnimator$addListener$1 extends AnimatorListenerAdapter {
    SpringAnimator$addListener$1(Animator.AnimatorListener param1AnimatorListener) {}
    
    public void onAnimationEnd(Animator param1Animator) { this.$listener.onAnimationEnd(); }
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\keijumt\passwordview\animation\SpringAnimator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */