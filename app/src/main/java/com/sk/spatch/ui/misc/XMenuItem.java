package com.sk.spatch.ui.misc;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

public class XMenuItem implements MenuItem {
  private int id = 0;
  
  public XMenuItem(int paramInt) { this.id = paramInt; }
  
  public boolean collapseActionView() { return false; }
  
  public boolean expandActionView() { return false; }
  
  public ActionProvider getActionProvider() { return null; }
  
  public View getActionView() { return null; }
  
  public char getAlphabeticShortcut() { return Character.MIN_VALUE; }
  
  public int getGroupId() { return 0; }
  
  public Drawable getIcon() { return null; }
  
  public Intent getIntent() { return null; }
  
  public int getItemId() { return this.id; }
  
  public ContextMenu.ContextMenuInfo getMenuInfo() { return null; }
  
  public char getNumericShortcut() { return Character.MIN_VALUE; }
  
  public int getOrder() { return 0; }
  
  public SubMenu getSubMenu() { return null; }
  
  public CharSequence getTitle() { return null; }
  
  public CharSequence getTitleCondensed() { return null; }
  
  public boolean hasSubMenu() { return false; }
  
  public boolean isActionViewExpanded() { return false; }
  
  public boolean isCheckable() { return false; }
  
  public boolean isChecked() { return false; }
  
  public boolean isEnabled() { return false; }
  
  public boolean isVisible() { return false; }
  
  public MenuItem setActionProvider(ActionProvider paramActionProvider) { return null; }
  
  public MenuItem setActionView(int paramInt) { return null; }
  
  public MenuItem setActionView(View paramView) { return null; }
  
  public MenuItem setAlphabeticShortcut(char paramChar) { return null; }
  
  public MenuItem setCheckable(boolean paramBoolean) { return null; }
  
  public MenuItem setChecked(boolean paramBoolean) { return null; }
  
  public MenuItem setEnabled(boolean paramBoolean) { return null; }
  
  public MenuItem setIcon(int paramInt) { return null; }
  
  public MenuItem setIcon(Drawable paramDrawable) { return null; }
  
  public MenuItem setIntent(Intent paramIntent) { return null; }
  
  public MenuItem setNumericShortcut(char paramChar) { return null; }
  
  public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener paramOnActionExpandListener) { return null; }
  
  public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener paramOnMenuItemClickListener) { return null; }
  
  public MenuItem setShortcut(char paramChar1, char paramChar2) { return null; }
  
  public void setShowAsAction(int paramInt) {}
  
  public MenuItem setShowAsActionFlags(int paramInt) { return null; }
  
  public MenuItem setTitle(int paramInt) { return null; }
  
  public MenuItem setTitle(CharSequence paramCharSequence) { return null; }
  
  public MenuItem setTitleCondensed(CharSequence paramCharSequence) { return null; }
  
  public MenuItem setVisible(boolean paramBoolean) { return null; }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatc\\ui\misc\XMenuItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */