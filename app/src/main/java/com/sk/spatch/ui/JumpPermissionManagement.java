package com.sk.spatch.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

public class JumpPermissionManagement {
  private static final String MANUFACTURER_HUAWEI = "huawei";
  
  private static final String MANUFACTURER_LENOVO = "lenovo";
  
  private static final String MANUFACTURER_LETV = "letv";
  
  private static final String MANUFACTURER_LG = "lg";
  
  private static final String MANUFACTURER_MEIZU = "meizu";
  
  private static final String MANUFACTURER_OPPO = "oppo";
  
  private static final String MANUFACTURER_SAMSUNG = "samsung";
  
  private static final String MANUFACTURER_SONY = "sony";
  
  private static final String MANUFACTURER_VIVO = "vivo";
  
  private static final String MANUFACTURER_XIAOMI = "xiaomi";
  
  private static final String MANUFACTURER_YULONG = "yulong";
  
  private static final String MANUFACTURER_ZTE = "zte";
  
  public static void ApplicationInfo(Activity paramActivity) {
    Intent intent = new Intent();
    intent.addFlags(268435456);
    if (Build.VERSION.SDK_INT >= 9) {
      intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
      intent.setData(Uri.fromParts("package", paramActivity.getPackageName(), null));
    } else if (Build.VERSION.SDK_INT <= 8) {
      intent.setAction("android.intent.action.VIEW");
      intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
      intent.putExtra("com.android.settings.ApplicationPkgName", paramActivity.getPackageName());
    } 
    paramActivity.startActivity(intent);
  }
  
  public static void GoToSetting(Activity paramActivity) {
    byte b;
    String str = Build.MANUFACTURER.toLowerCase();
    switch (str.hashCode()) {
      default:
        b = -1;
        break;
      case 103777484:
        if (str.equals("meizu")) {
          b = 1;
          break;
        } 
      case 3536167:
        if (str.equals("sony")) {
          b = 3;
          break;
        } 
      case 3418016:
        if (str.equals("oppo")) {
          b = 4;
          break;
        } 
      case 3318203:
        if (str.equals("letv")) {
          b = 6;
          break;
        } 
      case 3451:
        if (str.equals("lg")) {
          b = 5;
          break;
        } 
      case -759499589:
        if (str.equals("xiaomi")) {
          b = 2;
          break;
        } 
      case -1206476313:
        if (str.equals("huawei")) {
          b = 0;
          break;
        } 
    } 
    switch (b) {
      default:
        ApplicationInfo(paramActivity);
        Log.e("goToSetting", "目前暂不支持此系统");
        return;
      case 6:
        Letv(paramActivity);
        return;
      case 5:
        LG(paramActivity);
        return;
      case 4:
        OPPO(paramActivity);
        return;
      case 3:
        Sony(paramActivity);
        return;
      case 2:
        Xiaomi(paramActivity);
        return;
      case 1:
        Meizu(paramActivity);
        return;
      case 0:
        break;
    } 
    Huawei(paramActivity);
  }
  
  public static void Huawei(Activity paramActivity) {
    Intent intent = new Intent();
    intent.setFlags(268435456);
    intent.putExtra("packageName", "com.sk.spatch");
    intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
    paramActivity.startActivity(intent);
  }
  
  public static void LG(Activity paramActivity) {
    Intent intent = new Intent("android.intent.action.MAIN");
    intent.setFlags(268435456);
    intent.putExtra("packageName", "com.sk.spatch");
    intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity"));
    paramActivity.startActivity(intent);
  }
  
  public static void Letv(Activity paramActivity) {
    Intent intent = new Intent();
    intent.setFlags(268435456);
    intent.putExtra("packageName", "com.sk.spatch");
    intent.setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps"));
    paramActivity.startActivity(intent);
  }
  
  public static void Meizu(Activity paramActivity) {
    Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.putExtra("packageName", "com.sk.spatch");
    paramActivity.startActivity(intent);
  }
  
  public static void OPPO(Activity paramActivity) {
    Intent intent = new Intent();
    intent.setFlags(268435456);
    intent.putExtra("packageName", "com.sk.spatch");
    intent.setComponent(new ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity"));
    paramActivity.startActivity(intent);
  }
  
  public static void Sony(Activity paramActivity) {
    Intent intent = new Intent();
    intent.setFlags(268435456);
    intent.putExtra("packageName", "com.sk.spatch");
    intent.setComponent(new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity"));
    paramActivity.startActivity(intent);
  }
  
  public static void SystemConfig(Activity paramActivity) { paramActivity.startActivity(new Intent("android.settings.SETTINGS")); }
  
  public static void Xiaomi(Activity paramActivity) {
    Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
    intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity"));
    intent.putExtra("extra_pkgname", "com.sk.spatch");
    paramActivity.startActivity(intent);
  }
  
  public static void _360(Activity paramActivity) {
    Intent intent = new Intent("android.intent.action.MAIN");
    intent.setFlags(268435456);
    intent.putExtra("packageName", "com.sk.spatch");
    intent.setComponent(new ComponentName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity"));
    paramActivity.startActivity(intent);
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatc\\ui\JumpPermissionManagement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */