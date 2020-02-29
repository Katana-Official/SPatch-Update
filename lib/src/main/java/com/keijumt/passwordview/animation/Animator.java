package com.keijumt.passwordview.animation;

import android.view.View;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000 \n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\002\b\004\b&\030\0002\0020\001:\001\013B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\020\020\005\032\0020\0062\006\020\007\032\0020\bH\026J\b\020\t\032\0020\006H\026J\b\020\n\032\0020\006H&R\016\020\002\032\0020\003X\004¢\006\002\n\000¨\006\f"}, d2 = {"Lcom/keijumt/passwordview/animation/Animator;", "", "target", "Landroid/view/View;", "(Landroid/view/View;)V", "addListener", "", "listener", "Lcom/keijumt/passwordview/animation/Animator$AnimatorListener;", "removeAllListener", "start", "AnimatorListener", "library_release"}, k = 1, mv = {1, 1, 13})
public abstract class Animator {
  private final View target;
  
  public Animator(View paramView) { this.target = paramView; }
  
  public void addListener(AnimatorListener paramAnimatorListener) {
    Intrinsics.checkParameterIsNotNull(paramAnimatorListener, "listener");
    throw (Throwable)new RuntimeException("Stub!");
  }
  
  public void removeAllListener() { throw (Throwable)new RuntimeException("Stub!"); }
  
  public abstract void start();
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\002\030\002\n\002\020\000\n\000\n\002\020\002\n\000\bf\030\0002\0020\001J\b\020\002\032\0020\003H&¨\006\004"}, d2 = {"Lcom/keijumt/passwordview/animation/Animator$AnimatorListener;", "", "onAnimationEnd", "", "library_release"}, k = 1, mv = {1, 1, 13})
  public static interface AnimatorListener {
    void onAnimationEnd();
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\keijumt\passwordview\animation\Animator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */