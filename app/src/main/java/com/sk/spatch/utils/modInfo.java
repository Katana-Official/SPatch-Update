package com.sk.spatch.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import com.sk.spatch.core.SInvoker;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class modInfo {
  private static List<Mod> installedAppBuffer;
  
  private static List<Mod> installedModBuffer;
  
  private static List<Mod> installedVAppBuffer;
  
  static  {
  
  }
  
  public static List<Mod> getInstalledApps(Context paramContext) {
    List<Mod> list = installedAppBuffer;
    if (list != null) {
      installedAppBuffer = null;
      return list;
    } 
    LinkedList<Mod> linkedList = new LinkedList();
    try {
      PackageManager packageManager = paramContext.getPackageManager();
      Iterator<ApplicationInfo> iterator = packageManager.getInstalledApplications(128).iterator();
      while (true)
        return linkedList; 
    } finally {
      paramContext = null;
    } 
    return linkedList;
  }
  
  public static List<Mod> getInstalledMod(Context paramContext) {
    List<Mod> list = installedModBuffer;
    if (list != null) {
      installedModBuffer = null;
      return list;
    } 
    list = new LinkedList<Mod>();
    try {
      PackageManager packageManager = paramContext.getPackageManager();
      Iterator<ApplicationInfo> iterator = packageManager.getInstalledApplications(128).iterator();
      while (true)
        return list; 
    } finally {
      paramContext = null;
    } 
    return list;
  }
  
  public static List<Mod> getInstalledVApps(Context paramContext) {
    List<Mod> list = installedVAppBuffer;
    if (list != null) {
      installedVAppBuffer = null;
      return list;
    } 
    LinkedList<ModEx> linkedList = new LinkedList();
    try {
      PackageManager packageManager = paramContext.getPackageManager();
      Iterator<Object> iterator = ((List)SInvoker.invokeMethod(SInvoker.getStaticClass("com.sk.svm.local.client.core.VirtualCore"), "getInstalledApps", SInvoker.invokeStaticMethod("com.sk.svm.local.client.core.VirtualCore", "get", new Object[0]), new Object[] { Integer.valueOf(0) })).iterator();
      while (true)
        return (List)linkedList; 
    } finally {
      paramContext = null;
    } 
    return (List)linkedList;
  }
  
  public static void setupApps(Context paramContext) { installedAppBuffer = getInstalledApps(paramContext); }
  
  public static void setupMods(Context paramContext) { installedModBuffer = getInstalledMod(paramContext); }
  
  public static void setupVApps(Context paramContext) { installedVAppBuffer = getInstalledVApps(paramContext); }
  
  public static class Mod {
    public Drawable appIcon;
    
    public ApplicationInfo appInfoEx = null;
    
    public String szModDsp;
    
    public String szModName;
    
    public String szModPkg;
  }
  
  public static class ModEx extends Mod {
    public int vuid = 0;
    
    public ModEx clone() {
      ModEx modEx = new ModEx();
      modEx.appIcon = this.appIcon;
      modEx.appInfoEx = this.appInfoEx;
      modEx.szModDsp = this.szModDsp;
      modEx.szModName = this.szModName;
      modEx.szModPkg = this.szModPkg;
      modEx.vuid = this.vuid;
      return modEx;
    }
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatc\\utils\modInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */