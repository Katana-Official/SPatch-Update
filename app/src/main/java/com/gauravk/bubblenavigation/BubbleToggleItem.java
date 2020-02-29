package com.gauravk.bubblenavigation;

import android.graphics.drawable.Drawable;

class BubbleToggleItem {
  private int badgeBackgroundColor = -16777216;
  
  private String badgeText;
  
  private int badgeTextColor = -1;
  
  private float badgeTextSize;
  
  private int colorActive = -16776961;
  
  private int colorInactive = -16777216;
  
  private Drawable icon;
  
  private float iconHeight;
  
  private float iconWidth;
  
  private int internalPadding;
  
  private Drawable shape;
  
  private int shapeColor = Integer.MIN_VALUE;
  
  private String title = "";
  
  private int titlePadding;
  
  private float titleSize;
  
  int getBadgeBackgroundColor() { return this.badgeBackgroundColor; }
  
  String getBadgeText() { return this.badgeText; }
  
  int getBadgeTextColor() { return this.badgeTextColor; }
  
  float getBadgeTextSize() { return this.badgeTextSize; }
  
  int getColorActive() { return this.colorActive; }
  
  int getColorInactive() { return this.colorInactive; }
  
  Drawable getIcon() { return this.icon; }
  
  float getIconHeight() { return this.iconHeight; }
  
  float getIconWidth() { return this.iconWidth; }
  
  int getInternalPadding() { return this.internalPadding; }
  
  Drawable getShape() { return this.shape; }
  
  int getShapeColor() { return this.shapeColor; }
  
  String getTitle() { return this.title; }
  
  int getTitlePadding() { return this.titlePadding; }
  
  float getTitleSize() { return this.titleSize; }
  
  void setBadgeBackgroundColor(int paramInt) { this.badgeBackgroundColor = paramInt; }
  
  void setBadgeText(String paramString) { this.badgeText = paramString; }
  
  void setBadgeTextColor(int paramInt) { this.badgeTextColor = paramInt; }
  
  void setBadgeTextSize(float paramFloat) { this.badgeTextSize = paramFloat; }
  
  void setColorActive(int paramInt) { this.colorActive = paramInt; }
  
  void setColorInactive(int paramInt) { this.colorInactive = paramInt; }
  
  void setIcon(Drawable paramDrawable) { this.icon = paramDrawable; }
  
  void setIconHeight(float paramFloat) { this.iconHeight = paramFloat; }
  
  void setIconWidth(float paramFloat) { this.iconWidth = paramFloat; }
  
  void setInternalPadding(int paramInt) { this.internalPadding = paramInt; }
  
  void setShape(Drawable paramDrawable) { this.shape = paramDrawable; }
  
  void setShapeColor(int paramInt) { this.shapeColor = paramInt; }
  
  void setTitle(String paramString) { this.title = paramString; }
  
  void setTitlePadding(int paramInt) { this.titlePadding = paramInt; }
  
  void setTitleSize(float paramFloat) { this.titleSize = paramFloat; }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\gauravk\bubblenavigation\BubbleToggleItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */