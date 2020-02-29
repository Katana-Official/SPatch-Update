package com.hitomi.refresh.view;

import android.content.Context;
import android.util.AttributeSet;

public class FunGameFactory {
  static final int BATTLECITY = 1;
  
  static final int HITBLOCK = 0;
  
  static FunGameView createFunGameView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) { return (FunGameView)((paramInt != 0) ? ((paramInt != 1) ? new HitBlockView(paramContext, paramAttributeSet) : new BattleCityView(paramContext, paramAttributeSet)) : new HitBlockView(paramContext, paramAttributeSet)); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\hitomi\refresh\view\FunGameFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */