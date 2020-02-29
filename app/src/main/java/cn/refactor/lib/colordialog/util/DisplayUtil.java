package cn.refactor.lib.colordialog.util;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

public class DisplayUtil {
  public static int dp2px(Context paramContext, float paramFloat) { return (int)(paramFloat * (paramContext.getResources().getDisplayMetrics()).density + 0.5F); }
  
  public static Point getScreenSize(Context paramContext) {
    Point point = new Point();
    ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getSize(point);
    return point;
  }
  
  public static int px2dp(Context paramContext, float paramFloat) { return (int)(paramFloat / (paramContext.getResources().getDisplayMetrics()).density + 0.5F); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\cn\refactor\lib\colordialo\\util\DisplayUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */