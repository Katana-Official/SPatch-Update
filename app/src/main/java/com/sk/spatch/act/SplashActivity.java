package com.sk.spatch.act;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.sk.spatch.MainActivity;
import com.sk.spatch.VAPPLike;
import com.sk.spatch.core.SInvoker;
import com.sk.spatch.utils.modInfo;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;
import jonathanfinerty.once.Once;
import site.gemus.openingstartanimation.DrawStrategy;
import site.gemus.openingstartanimation.OpeningStartAnimation;
import site.gemus.openingstartanimation.RotationDrawStrategy;

public class SplashActivity extends AppCompatActivity {
  private static boolean is_initialized = false;
  
  private static boolean is_setup_analysis = false;
  
  public static final long waitTime_NotVEngine = 2000L;
  
  public static final long waitTime_VEngine = 3000L;
  
  static  {
  
  }
  
  private void ensureEngineStarted() {
    try {
      Class clazz = SInvoker.getStaticClass("com.sk.svm.local.client.core.VirtualCore");
      Object object = SInvoker.invokeStaticMethod(clazz, "get", new Object[0]);
      return;
    } finally {
      Exception exception = null;
    } 
  }
  
  private void goDesktop() {
    if (VAPPLike.getApp().getIsEnabledNonApkModify()) {
      MainActivity.goHome((Context)this);
    } else {
      startActivity(new Intent((Context)this, XHome.class));
    } 
    if (!is_setup_analysis) {
      StatConfig.setDebugEnable(false);
      StatService.registerActivityLifecycleCallbacks(getApplication());
      is_setup_analysis = true;
    } 
    finish();
  }
  
  private void goDesktopWithPrivacyCheck() {
    if (!Once.beenDone("user_privacy")) {
      AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
      builder.setTitle(2131689636);
      builder.setMessage(2131689637);
      builder.setPositiveButton(2131689572, new -$$Lambda$SplashActivity$pCTL5RfjQQQo-kBj6GApHtlV5E0(this));
      builder.setNegativeButton(2131689592, new -$$Lambda$SplashActivity$M5esoH1JmuY-qX4JqTf3QU-3m_Q(this));
      builder.create().show();
      return;
    } 
    goDesktop();
  }
  
  private void setupInstalledInfoBuffer() {
    modInfo.setupMods((Context)this);
    modInfo.setupApps((Context)this);
    if (VAPPLike.getApp().getIsEnabledNonApkModify()) {
      ensureEngineStarted();
      modInfo.setupVApps((Context)this);
    } 
  }
  
  protected void onCreate(Bundle paramBundle) {
    long l;
    super.onCreate(paramBundle);
    if (is_initialized) {
      goDesktopWithPrivacyCheck();
      return;
    } 
    getWindow().setFlags(1024, 1024);
    if (VAPPLike.getApp().getIsEnabledNonApkModify()) {
      l = 3000L;
    } else {
      l = 2000L;
    } 
    try {
      OpeningStartAnimation.Builder builder = (new OpeningStartAnimation.Builder((Context)this)).setAppIcon(getResources().getDrawable(2131558402, getTheme())).setColorOfAppIcon(Color.rgb(33, 195, 235)).setAppName(getApplicationInfo().loadLabel(getPackageManager()).toString()).setColorOfAppName(-1).setAppStatement(getString(2131689501)).setColorOfAppStatement(-1);
      double d = l / 4.0D;
      builder.setAnimationInterval((long)(3.0D * d)).setAnimationFinishTime((long)d).setColorOfBackground(Color.rgb(33, 33, 33)).setDrawStategy((DrawStrategy)new RotationDrawStrategy()).create().show((Activity)this);
    } finally {
      paramBundle = null;
      setContentView(2131427361);
    } 
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
    if (paramMenuItem.getItemId() == 16908332)
      onBackPressed(); 
    return true;
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\act\SplashActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */