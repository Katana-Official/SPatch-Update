package com.keijumt.passwordview.animation;

import com.keijumt.passwordview.CircleView;
import kotlin.Metadata;

@Metadata(bv = {1, 0, 3}, d1 = {"\000 \n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\000\n\002\020\002\n\002\b\002\b\000\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\b\020\005\032\0020\006H\024J\020\020\007\032\0020\b2\006\020\t\032\0020\006H\026R\016\020\002\032\0020\003X\004¢\006\002\n\000¨\006\n"}, d2 = {"Lcom/keijumt/passwordview/animation/FillColorChangeAnimation;", "Lcom/keijumt/passwordview/animation/ColorChangeAnimation;", "circleView", "Lcom/keijumt/passwordview/CircleView;", "(Lcom/keijumt/passwordview/CircleView;)V", "getColor", "", "setColor", "", "color", "library_release"}, k = 1, mv = {1, 1, 13})
public final class FillColorChangeAnimation extends ColorChangeAnimation {
  private final CircleView circleView;
  
  public FillColorChangeAnimation(CircleView paramCircleView) {
    super(paramCircleView);
    this.circleView = paramCircleView;
  }
  
  protected int getColor() { return this.circleView.getFillCircleColor(); }
  
  public void setColor(int paramInt) { this.circleView.setFillCircleColor(paramInt); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\keijumt\passwordview\animation\FillColorChangeAnimation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */