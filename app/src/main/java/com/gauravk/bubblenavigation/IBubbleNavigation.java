package com.gauravk.bubblenavigation;

import android.graphics.Typeface;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

public interface IBubbleNavigation {
  int getCurrentActiveItemPosition();
  
  void setBadgeValue(int paramInt, String paramString);
  
  void setCurrentActiveItem(int paramInt);
  
  void setNavigationChangeListener(BubbleNavigationChangeListener paramBubbleNavigationChangeListener);
  
  void setTypeface(Typeface paramTypeface);
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\gauravk\bubblenavigation\IBubbleNavigation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */