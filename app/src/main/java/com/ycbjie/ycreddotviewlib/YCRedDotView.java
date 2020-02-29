package com.ycbjie.ycreddotviewlib;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TabWidget;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import com.google.android.material.tabs.TabLayout;

public class YCRedDotView extends AppCompatTextView {
  private static final String ZERO = "0";
  
  private boolean mHideOnNull = true;
  
  public YCRedDotView(Context paramContext) { this(paramContext, null); }
  
  public YCRedDotView(Context paramContext, AttributeSet paramAttributeSet) { this(paramContext, paramAttributeSet, 16842884); }
  
  public YCRedDotView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    initView();
  }
  
  private int dip2Px(float paramFloat) { return (int)(paramFloat * (getContext().getResources().getDisplayMetrics()).density + 0.5F); }
  
  private void initView() {
    setLayoutParams();
    setTextView();
    setDefaultValues();
  }
  
  private void setBackground(int paramInt1, int paramInt2) {
    float f = dip2Px(paramInt1);
    ShapeDrawable shapeDrawable = new ShapeDrawable((Shape)new RoundRectShape(new float[] { f, f, f, f, f, f, f, f }, null, null));
    shapeDrawable.getPaint().setColor(paramInt2);
    if (Build.VERSION.SDK_INT >= 16)
      setBackground((Drawable)shapeDrawable); 
  }
  
  private void setBadgeCount(String paramString) { setText(paramString); }
  
  private void setDefaultValues() {
    setHideNull(true);
    setBadgeCount(0);
  }
  
  private void setLayoutParams() {
    if (!(getLayoutParams() instanceof FrameLayout.LayoutParams))
      setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -2, 8388661)); 
  }
  
  private void setTextView() {
    setTextColor(-1);
    setTypeface(Typeface.DEFAULT_BOLD);
    setTextSize(2, 10.0F);
    setPadding(dip2Px(5.0F), dip2Px(1.0F), dip2Px(5.0F), dip2Px(1.0F));
    setBackground(9, Color.parseColor("#f14850"));
    setGravity(17);
  }
  
  public int getBadgeCount() {
    if (getText() == null)
      return 0; 
    String str = getText().toString();
    try {
      return Integer.parseInt(str);
    } catch (NumberFormatException numberFormatException) {
      return 0;
    } 
  }
  
  public boolean isHideOnNull() { return this.mHideOnNull; }
  
  public void setBadgeCount(int paramInt) {
    String str;
    if (paramInt > 99) {
      str = "99+";
    } else {
      str = String.valueOf(paramInt);
    } 
    setBadgeCount(str);
  }
  
  public void setBadgeMargin(int paramInt) { setBadgeMargin(paramInt, paramInt, paramInt, paramInt); }
  
  public void setBadgeMargin(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)getLayoutParams();
    layoutParams.leftMargin = dip2Px(paramInt1);
    layoutParams.topMargin = dip2Px(paramInt2);
    layoutParams.rightMargin = dip2Px(paramInt3);
    layoutParams.bottomMargin = dip2Px(paramInt4);
    setLayoutParams((ViewGroup.LayoutParams)layoutParams);
  }
  
  public void setBadgeView(int paramInt) {
    setText("");
    float f = paramInt;
    setWidth(dip2Px(f));
    setHeight(dip2Px(f));
    setBackground(9, Color.parseColor("#f14850"));
  }
  
  public void setHideNull(boolean paramBoolean) {
    this.mHideOnNull = paramBoolean;
    setText(getText());
  }
  
  public void setRedHotViewGravity(int paramInt) {
    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)getLayoutParams();
    layoutParams.gravity = paramInt;
    setLayoutParams((ViewGroup.LayoutParams)layoutParams);
  }
  
  public void setTargetView(View paramView) {
    if (getParent() != null)
      ((ViewGroup)getParent()).removeView((View)this); 
    if (paramView == null)
      return; 
    if (paramView.getParent() instanceof FrameLayout) {
      ((FrameLayout)paramView.getParent()).addView((View)this);
      return;
    } 
    if (paramView.getParent() instanceof ViewGroup) {
      ViewGroup viewGroup = (ViewGroup)paramView.getParent();
      int i = viewGroup.indexOfChild(paramView);
      viewGroup.removeView(paramView);
      FrameLayout frameLayout = new FrameLayout(getContext());
      ViewGroup.LayoutParams layoutParams = paramView.getLayoutParams();
      frameLayout.setLayoutParams(layoutParams);
      paramView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
      viewGroup.addView((View)frameLayout, i, layoutParams);
      frameLayout.addView(paramView);
      frameLayout.addView((View)this);
      return;
    } 
    Log.e(getClass().getSimpleName(), "ParentView is must needed");
  }
  
  public void setTargetView(TabWidget paramTabWidget, int paramInt) { setTargetView(paramTabWidget.getChildTabViewAt(paramInt)); }
  
  public void setTargetView(TabLayout paramTabLayout, int paramInt) {
    TabLayout.Tab tab = paramTabLayout.getTabAt(paramInt);
    if (tab != null) {
      View view = tab.getCustomView();
    } else {
      tab = null;
    } 
    setTargetView((View)tab);
  }
  
  public void setText(CharSequence paramCharSequence, TextView.BufferType paramBufferType) {
    if (isHideOnNull()) {
      if (paramCharSequence == null || paramCharSequence.toString().equalsIgnoreCase("0")) {
        setVisibility(8);
      } else {
        setVisibility(0);
      } 
    } else {
      setVisibility(0);
    } 
    super.setText(paramCharSequence, paramBufferType);
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\ycbjie\ycreddotviewlib\YCRedDotView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */