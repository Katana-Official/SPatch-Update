package com.sk.spatch.ui;

import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import com.sk.spatch.utils.modInfo;

public class appv {
  private modInfo.Mod pkMod;
  
  public appv(modInfo.Mod paramMod) { this.pkMod = paramMod; }
  
  public ApplicationInfo getAppInfo() { return this.pkMod.appInfoEx; }
  
  public String getDsp() { return this.pkMod.szModDsp; }
  
  public Drawable getIcon() { return this.pkMod.appIcon; }
  
  public String getName() { return this.pkMod.szModName; }
  
  public String getPkg() { return this.pkMod.szModPkg; }
  
  public int getvuid() {
    modInfo.Mod mod = this.pkMod;
    return (mod instanceof modInfo.ModEx) ? ((modInfo.ModEx)mod).vuid : 0;
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatc\\ui\appv.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */