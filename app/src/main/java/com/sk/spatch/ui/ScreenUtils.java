package com.sk.spatch.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class ScreenUtils {
  private ScreenUtils() { throw new UnsupportedOperationException("cannot be instantiated"); }
  
  public static int getScreenHeight(Context paramContext) {
    WindowManager windowManager = (WindowManager)paramContext.getSystemService("window");
    DisplayMetrics displayMetrics = new DisplayMetrics();
    windowManager.getDefaultDisplay().getMetrics(displayMetrics);
    return displayMetrics.heightPixels;
  }
  
  public static int getScreenWidth(Context paramContext) {
    WindowManager windowManager = (WindowManager)paramContext.getSystemService("window");
    DisplayMetrics displayMetrics = new DisplayMetrics();
    windowManager.getDefaultDisplay().getMetrics(displayMetrics);
    return displayMetrics.widthPixels;
  }
  
  public static int getStatusHeight(Context paramContext) {
    try {
      Class<?> clazz = Class.forName("com.android.internal.R$dimen");
      Object object = clazz.newInstance();
      null = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
      return paramContext.getResources().getDimensionPixelSize(null);
    } catch (Exception exception) {
      exception.printStackTrace();
      return -1;
    } 
  }
  
  public static int getVirtualBarHeigh(Activity paramActivity) {
    Rect rect = new Rect();
    paramActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
    int i = rect.top;
    return paramActivity.getWindow().findViewById(16908290).getTop() - i;
  }
  
  public static int getVirtualBarHeigh(Context paramContext) {
    WindowManager windowManager = (WindowManager)paramContext.getSystemService("window");
    Display display = windowManager.getDefaultDisplay();
    DisplayMetrics displayMetrics = new DisplayMetrics();
    try {
      Class.forName("android.view.Display").getMethod("getRealMetrics", new Class[] { DisplayMetrics.class }).invoke(display, new Object[] { displayMetrics });
      int i = displayMetrics.heightPixels;
      int j = windowManager.getDefaultDisplay().getHeight();
      return i - j;
    } catch (Exception exception) {
      exception.printStackTrace();
      return 0;
    } 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatc\\ui\ScreenUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */