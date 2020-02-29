package com.sk.spatch.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.sk.spatch.VAPPLike;
import com.sk.spatch.core.provider.XProviderSPatch;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SettingsControl {
  private static boolean canInstallAppExternal = false;
  
  private static final String szAlertWhenDone = "alertWhenDone";
  
  private static final String szAutoUnpack = "autoUnpack";
  
  private static final String szDebug = "debugMode";
  
  private static final String szDisableMod = "disableMod";
  
  private static final String szDisableSafeMode = "disableSafeMode";
  
  private static final String szExper = "experiment";
  
  private static final String szNonApkMod = "isApkMod";
  
  private static final String szPathNoRedirectTag = "path_no_redirect";
  
  private static final String szTemporaryLaunchEngine = "TempLaunchEngine";
  
  private static final String szUniqueIdFlag = "unique_ids";
  
  static  {
    try {
      System.loadLibrary("spatch");
    } finally {
      Exception exception = null;
    } 
  }
  
  public static void ensureIsNonApkModifyCanWork(Context paramContext) {
    share(paramContext).edit().putBoolean("isApkMod", true).apply();
    if (XProviderSPatch.getString(paramContext, "non_modify") == null)
      XProviderSPatch.save(paramContext, "non_modify", "Enabled"); 
  }
  
  public static boolean getAlertWhenDone(Context paramContext) { return share(paramContext).getBoolean("alertWhenDone", false); }
  
  public static boolean getAutoUnpack(Context paramContext) {
    SharedPreferences sharedPreferences = share(paramContext);
    boolean bool = false;
    if (sharedPreferences.getBoolean("autoUnpack", false) || XProviderSPatch.getString(paramContext, "use_unpack") != null)
      bool = true; 
    return bool;
  }
  
  public static boolean getCanInstallAppExternal() { return canInstallAppExternal; }
  
  public static boolean getDebug(Context paramContext) { return share(paramContext).getBoolean("debugMode", false); }
  
  public static boolean getDisableMod(Context paramContext) { return share(paramContext).getBoolean("disableMod", false); }
  
  public static boolean getDisableSafeMode(Context paramContext) { return share(paramContext).getBoolean("disableSafeMode", false); }
  
  public static native String getEnvNonModTask(String paramString);
  
  public static boolean getExperiment(Context paramContext) { return share(paramContext).getBoolean("experiment", true); }
  
  public static boolean getIsNonApkModify(Context paramContext) {
    SharedPreferences sharedPreferences = share(paramContext);
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (sharedPreferences.getBoolean("isApkMod", false)) {
      bool1 = bool2;
      if (XProviderSPatch.getString(paramContext, "non_modify") != null)
        bool1 = true; 
    } 
    return bool1;
  }
  
  public static boolean getIsTemporaryLaunchEngine(Context paramContext) {
    if (XProviderSPatch.getString(paramContext, "TempLaunchEngine") != null) {
      XProviderSPatch.remove(paramContext, "TempLaunchEngine");
      return true;
    } 
    return false;
  }
  
  public static List<String> getPathNoRedirect(Context paramContext) {
    LinkedList<String> linkedList = new LinkedList();
    String str = XProviderSPatch.getString(paramContext, "path_no_redirect");
    if (str != null)
      linkedList.addAll(Arrays.asList(str.split(";"))); 
    return linkedList;
  }
  
  public static String getUniqueIds(Context paramContext, String paramString) { return share(paramContext).getString("unique_ids", paramString); }
  
  public static void removePathNoRedirect(Context paramContext) {
    if (XProviderSPatch.getString(paramContext, "path_no_redirect") != null)
      XProviderSPatch.remove(paramContext, "path_no_redirect"); 
  }
  
  public static void setAlertWhenDone(Context paramContext, boolean paramBoolean) { share(paramContext).edit().putBoolean("alertWhenDone", paramBoolean).apply(); }
  
  public static void setAutoUnpack(Context paramContext, boolean paramBoolean) {
    share(paramContext).edit().putBoolean("autoUnpack", paramBoolean).apply();
    if (paramBoolean) {
      if (XProviderSPatch.getString(paramContext, "use_unpack") == null) {
        XProviderSPatch.save(paramContext, "use_unpack", "Enabled");
        return;
      } 
    } else if (XProviderSPatch.getString(paramContext, "use_unpack") != null) {
      XProviderSPatch.remove(paramContext, "use_unpack");
    } 
  }
  
  public static void setCanInstallAppExternal(boolean paramBoolean) { canInstallAppExternal = paramBoolean; }
  
  public static void setDebug(Context paramContext, boolean paramBoolean) { share(paramContext).edit().putBoolean("debugMode", paramBoolean).apply(); }
  
  public static void setDisableMod(Context paramContext, boolean paramBoolean) { share(paramContext).edit().putBoolean("disableMod", paramBoolean).apply(); }
  
  public static void setDisableSafeMode(Context paramContext, boolean paramBoolean) { share(paramContext).edit().putBoolean("disableSafeMode", paramBoolean).apply(); }
  
  public static void setExperiment(Context paramContext, boolean paramBoolean) { share(paramContext).edit().putBoolean("experiment", paramBoolean).apply(); }
  
  public static void setIsNonApkModify(Context paramContext, boolean paramBoolean) {
    (new Thread(new -$$Lambda$SettingsControl$z_BhupevayPrzN4vFosbp6VEY1g(paramBoolean))).start();
    if (paramBoolean) {
      VAPPLike.getApp().initVSKCore(paramContext);
      return;
    } 
    share(paramContext).edit().putBoolean("isApkMod", false).apply();
    if (XProviderSPatch.getString(paramContext, "non_modify") != null)
      XProviderSPatch.remove(paramContext, "non_modify"); 
    VAPPLike.getApp().killVSKCore(paramContext);
  }
  
  public static void setPathNoRedirect(Context paramContext, String paramString) {
    if (XProviderSPatch.getString(paramContext, "path_no_redirect") != null)
      XProviderSPatch.remove(paramContext, "path_no_redirect"); 
    XProviderSPatch.save(paramContext, "path_no_redirect", paramString);
  }
  
  public static void setPathNoRedirect(Context paramContext, List<String> paramList) {
    if (XProviderSPatch.getString(paramContext, "path_no_redirect") != null)
      XProviderSPatch.remove(paramContext, "path_no_redirect"); 
    StringBuilder stringBuilder = new StringBuilder();
    Iterator<String> iterator = paramList.iterator();
    while (iterator.hasNext()) {
      stringBuilder.append(iterator.next());
      stringBuilder.append(";");
    } 
    XProviderSPatch.save(paramContext, "path_no_redirect", stringBuilder.toString());
  }
  
  public static void setTemporaryLaunchEngine(Context paramContext) {
    if (XProviderSPatch.getString(paramContext, "TempLaunchEngine") == null)
      XProviderSPatch.save(paramContext, "TempLaunchEngine", "Test"); 
  }
  
  public static void setUniqueIds(Context paramContext, String paramString) { share(paramContext).edit().putString("unique_ids", paramString).apply(); }
  
  private static SharedPreferences share(Context paramContext) { return paramContext.getSharedPreferences("main_settings", 0); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatc\\utils\SettingsControl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */