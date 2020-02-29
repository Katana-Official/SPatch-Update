package com.kaisengao.likeview.like;

import android.animation.TypeEvaluator;
import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import java.util.Random;

public abstract class BasePathAnimator {
  final int MAX_PATH_COUNTS = 10;
  
  Random mRandom = new Random();
  
  CurveEvaluator createPath(PointF paramPointF1, PointF paramPointF2) { return new CurveEvaluator(paramPointF1, paramPointF2); }
  
  public abstract void start(View paramView, ViewGroup paramViewGroup, RelativeLayout.LayoutParams paramLayoutParams);
  
  protected class CurveEvaluator implements TypeEvaluator<PointF> {
    private PointF mControlP1;
    
    private PointF mControlP2;
    
    private CurveEvaluator(PointF param1PointF1, PointF param1PointF2) {
      this.mControlP1 = param1PointF1;
      this.mControlP2 = param1PointF2;
    }
    
    public PointF evaluate(float param1Float, PointF param1PointF1, PointF param1PointF2) {
      float f5 = 1.0F - param1Float;
      PointF pointF = new PointF();
      double d1 = f5;
      float f1 = (float)Math.pow(d1, 3.0D);
      float f2 = param1PointF1.x;
      float f3 = (float)Math.pow(d1, 2.0D);
      float f4 = this.mControlP1.x;
      f5 *= 3.0F;
      double d2 = param1Float;
      pointF.x = f1 * f2 + f3 * 3.0F * param1Float * f4 + (float)Math.pow(d2, 2.0D) * f5 * this.mControlP2.x + (float)Math.pow(d2, 3.0D) * param1PointF2.x;
      pointF.y = (float)Math.pow(d1, 3.0D) * param1PointF1.y + (float)Math.pow(d1, 2.0D) * 3.0F * param1Float * this.mControlP1.y + f5 * param1Float * param1Float * this.mControlP2.y + (float)Math.pow(d2, 3.0D) * param1PointF2.y;
      return pointF;
    }
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\kaisengao\likeview\like\BasePathAnimator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */