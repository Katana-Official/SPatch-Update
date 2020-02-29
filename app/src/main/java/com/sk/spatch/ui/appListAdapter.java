package com.sk.spatch.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ycbjie.ycreddotviewlib.YCRedDotView;
import java.util.List;

public class appListAdapter extends ArrayAdapter<appv> {
  private Context ctx;
  
  private List<appv> list;
  
  private int resourceId;
  
  public appListAdapter(Context paramContext, int paramInt, List<appv> paramList) {
    super(paramContext, paramInt, paramList);
    this.resourceId = paramInt;
    this.list = paramList;
  }
  
  public static void setRedDot(View paramView) { setRedDot(paramView, null); }
  
  public static void setRedDot(View paramView, Context paramContext) {
    Context context = paramContext;
    if (paramContext == null)
      context = paramView.getContext(); 
    YCRedDotView yCRedDotView = new YCRedDotView(context);
    yCRedDotView.setTargetView(paramView);
    yCRedDotView.setBadgeCount(10);
    yCRedDotView.setRedHotViewGravity(8388613);
    yCRedDotView.getBadgeCount();
    yCRedDotView.setBadgeView(10);
    yCRedDotView.setBadgeMargin(0, 10, 20, 0);
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
    paramView = LayoutInflater.from(getContext()).inflate(this.resourceId, paramViewGroup, false);
    try {
      appv appv = this.list.get(paramInt);
      ImageView imageView = (ImageView)paramView.findViewById(2131230950);
      TextView textView1 = (TextView)paramView.findViewById(2131231151);
      TextView textView2 = (TextView)paramView.findViewById(2131231145);
      imageView.setImageDrawable(appv.getIcon());
      return paramView;
    } finally {
      paramViewGroup = null;
      paramViewGroup.printStackTrace();
    } 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatc\\ui\appListAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */