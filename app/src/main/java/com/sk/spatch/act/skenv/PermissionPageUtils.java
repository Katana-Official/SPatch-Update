package com.sk.spatch.act.skenv;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PermissionPageUtils {
  private final String TAG = "PermissionPageManager";
  
  private Context mContext;
  
  private String packageName = "com.sk.spatch";
  
  public PermissionPageUtils(Context paramContext) { this.mContext = paramContext; }
  
  private void doStartApplicationWithPackageName(String paramString) {
    try {
      object = this.mContext.getPackageManager().getPackageInfo(paramString, 0);
    } catch (android.content.pm.PackageManager.NameNotFoundException object) {
      object.printStackTrace();
      object = null;
    } 
    if (object == null)
      return; 
    Intent intent = new Intent("android.intent.action.MAIN", null);
    intent.addCategory("android.intent.category.LAUNCHER");
    intent.setPackage(object.packageName);
    ResolveInfo resolveInfo = this.mContext.getPackageManager().queryIntentActivities(intent, 0).iterator().next();
    if (resolveInfo != null) {
      object = resolveInfo.activityInfo.packageName;
      String str = resolveInfo.activityInfo.name;
      Intent intent1 = new Intent("android.intent.action.MAIN");
      intent1.addCategory("android.intent.category.LAUNCHER");
      intent1.setComponent(new ComponentName((String)object, str));
      try {
        this.mContext.startActivity(intent1);
        return;
      } catch (Exception exception) {
        goIntentSetting();
        exception.printStackTrace();
      } 
    } 
  }
  
  private Intent getAppDetailSettingIntent() {
    Intent intent = new Intent();
    intent.addFlags(268435456);
    if (Build.VERSION.SDK_INT >= 9) {
      intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
      intent.setData(Uri.fromParts("package", this.mContext.getPackageName(), null));
      return intent;
    } 
    if (Build.VERSION.SDK_INT <= 8) {
      intent.setAction("android.intent.action.VIEW");
      intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
      intent.putExtra("com.android.settings.ApplicationPkgName", this.mContext.getPackageName());
    } 
    return intent;
  }
  
  private static String getMiuiVersion() {
    BufferedReader bufferedReader;
    try {
      Runtime runtime = Runtime.getRuntime();
      null = new StringBuilder();
      null.append("getprop ");
      null.append("ro.miui.ui.version.name");
      bufferedReader = new BufferedReader(new InputStreamReader(runtime.exec(null.toString()).getInputStream()), 1024);
    } finally {
      null = null;
    } 
    try {
      null.printStackTrace();
    } finally {
      try {
        bufferedReader.close();
      } finally {
        bufferedReader = null;
      } 
    } 
  }
  
  private void goCoolpadMainager() { doStartApplicationWithPackageName("com.yulong.android.security:remote"); }
  
  private void goHuaWeiMainager() {
    try {
      Intent intent = new Intent(this.packageName);
      intent.setFlags(268435456);
      intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
      this.mContext.startActivity(intent);
      return;
    } catch (Exception exception) {
      exception.printStackTrace();
      goIntentSetting();
      return;
    } 
  }
  
  private void goIntentSetting() {
    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
    intent.setData(Uri.fromParts("package", this.mContext.getPackageName(), null));
    try {
      this.mContext.startActivity(intent);
      return;
    } catch (Exception exception) {
      exception.printStackTrace();
      return;
    } 
  }
  
  private void goLGMainager() {
    try {
      Intent intent = new Intent(this.packageName);
      intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity"));
      this.mContext.startActivity(intent);
      return;
    } catch (Exception exception) {
      exception.printStackTrace();
      goIntentSetting();
      return;
    } 
  }
  
  private void goMeizuMainager() {
    try {
      Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
      return;
    } finally {
      Exception exception = null;
      exception.printStackTrace();
      goIntentSetting();
    } 
  }
  
  private void goOppoMainager() { doStartApplicationWithPackageName("com.coloros.safecenter"); }
  
  private void goSangXinMainager() { goIntentSetting(); }
  
  private void goSonyMainager() {
    try {
      Intent intent = new Intent(this.packageName);
      intent.setComponent(new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity"));
      this.mContext.startActivity(intent);
      return;
    } catch (Exception exception) {
      exception.printStackTrace();
      goIntentSetting();
      return;
    } 
  }
  
  private void goVivoMainager() { doStartApplicationWithPackageName("com.bairenkeji.icaller"); }
  
  private void goXiaoMiMainager() {
    String str = getMiuiVersion();
    Intent intent = new Intent();
    if ("V6".equals(str) || "V7".equals(str)) {
      intent.setAction("miui.intent.action.APP_PERM_EDITOR");
      intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
      intent.putExtra("extra_pkgname", this.packageName);
    } else if ("V8".equals(str) || "V9".equals(str)) {
      intent.setAction("miui.intent.action.APP_PERM_EDITOR");
      intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
      intent.putExtra("extra_pkgname", this.packageName);
    } else {
      goIntentSetting();
    } 
    this.mContext.startActivity(intent);
  }
  
  public void jumpPermissionPage() {
    byte b;
    String str = Build.MANUFACTURER;
    switch (str.hashCode()) {
      default:
        b = -1;
        break;
      case 2141820391:
        if (str.equals("HUAWEI")) {
          b = 0;
          break;
        } 
      case 1864941562:
        if (str.equals("samsung")) {
          b = 6;
          break;
        } 
      case 74224812:
        if (str.equals("Meizu")) {
          b = 4;
          break;
        } 
      case 3620012:
        if (str.equals("vivo")) {
          b = 1;
          break;
        } 
      case 2582855:
        if (str.equals("Sony")) {
          b = 7;
          break;
        } 
      case 2432928:
        if (str.equals("OPPO")) {
          b = 2;
          break;
        } 
      case 2427:
        if (str.equals("LG")) {
          b = 8;
          break;
        } 
      case -1675632421:
        if (str.equals("Xiaomi")) {
          b = 5;
          break;
        } 
      case -1678088054:
        if (str.equals("Coolpad")) {
          b = 3;
          break;
        } 
    } 
    switch (b) {
      default:
        goIntentSetting();
        return;
      case 8:
        goLGMainager();
        return;
      case 7:
        goSonyMainager();
        return;
      case 6:
        goSangXinMainager();
        return;
      case 5:
        goXiaoMiMainager();
        return;
      case 4:
        goMeizuMainager();
        return;
      case 3:
        goCoolpadMainager();
        return;
      case 2:
        goOppoMainager();
        return;
      case 1:
        goVivoMainager();
        return;
      case 0:
        break;
    } 
    goHuaWeiMainager();
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\act\skenv\PermissionPageUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */