package com.gauravk.bubblenavigation.util;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import com.gauravk.bubblenavigation.R;

public class ViewUtils {
  public static int getThemeAccentColor(Context paramContext) {
    TypedValue typedValue = new TypedValue();
    paramContext.getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
    return typedValue.data;
  }
  
  public static void updateDrawableColor(Drawable paramDrawable, int paramInt) {
    if (paramDrawable == null)
      return; 
    if (Build.VERSION.SDK_INT >= 21) {
      paramDrawable.setTint(paramInt);
      return;
    } 
    paramDrawable.setColorFilter(paramInt, PorterDuff.Mode.SRC_ATOP);
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\gauravk\bubblenavigatio\\util\ViewUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */