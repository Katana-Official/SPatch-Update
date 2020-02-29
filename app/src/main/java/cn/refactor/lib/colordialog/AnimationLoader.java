package cn.refactor.lib.colordialog;

import android.content.Context;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;

public class AnimationLoader {
  public static AnimationSet getInAnimation(Context paramContext) {
    AnimationSet animationSet = new AnimationSet(paramContext, null);
    AlphaAnimation alphaAnimation = new AlphaAnimation(0.0F, 1.0F);
    alphaAnimation.setDuration(90L);
    ScaleAnimation scaleAnimation1 = new ScaleAnimation(0.8F, 1.05F, 0.8F, 1.05F, 1, 0.5F, 1, 0.5F);
    scaleAnimation1.setDuration(135L);
    ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.05F, 0.95F, 1.05F, 0.95F, 1, 0.5F, 1, 0.5F);
    scaleAnimation2.setDuration(105L);
    scaleAnimation2.setStartOffset(135L);
    ScaleAnimation scaleAnimation3 = new ScaleAnimation(0.95F, 1.0F, 0.95F, 1.0F, 1, 0.5F, 1, 0.5F);
    scaleAnimation3.setDuration(60L);
    scaleAnimation3.setStartOffset(240L);
    animationSet.addAnimation((Animation)alphaAnimation);
    animationSet.addAnimation((Animation)scaleAnimation1);
    animationSet.addAnimation((Animation)scaleAnimation2);
    animationSet.addAnimation((Animation)scaleAnimation3);
    return animationSet;
  }
  
  public static AnimationSet getOutAnimation(Context paramContext) {
    AnimationSet animationSet = new AnimationSet(paramContext, null);
    AlphaAnimation alphaAnimation = new AlphaAnimation(1.0F, 0.0F);
    alphaAnimation.setDuration(150L);
    ScaleAnimation scaleAnimation = new ScaleAnimation(1.0F, 0.6F, 1.0F, 0.6F, 1, 0.5F, 1, 0.5F);
    scaleAnimation.setDuration(150L);
    animationSet.addAnimation((Animation)alphaAnimation);
    animationSet.addAnimation((Animation)scaleAnimation);
    return animationSet;
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\cn\refactor\lib\colordialog\AnimationLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */