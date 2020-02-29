package com.sk.spatch;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Process;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDelegate;
import cn.refactor.lib.colordialog.PromptDialog;
import com.simple.spiderman.SpiderMan;
import com.sk.spatch.core.SInvoker;
import com.sk.spatch.core.provider.XProviderSPatch;
import com.sk.spatch.utils.SettingsControl;
import com.tencent.tinker.entry.ApplicationLike;
import com.tencent.tinker.entry.DefaultApplicationLike;
import com.tencent.tinker.lib.library.TinkerLoadLibrary;
import com.tencent.tinker.lib.tinker.Tinker;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;
import java.lang.reflect.Method;
import jonathanfinerty.once.Once;

public class VAPPLike extends DefaultApplicationLike {
  private static VAPPLike gApp;
  
  private boolean isEnabledNonApkModify = false;
  
  private Object xConfig;
  
  public VAPPLike(Application paramApplication, int paramInt, boolean paramBoolean, long paramLong1, long paramLong2, Intent paramIntent) { super(paramApplication, paramInt, paramBoolean, paramLong1, paramLong2, paramIntent); }
  
  private void doInit(Context paramContext) {
    try {
      SInvoker.setStaticFieldObject("com.sk.svm.local.client.core.SettingConfig", paramContext, "hostPackage", "com.sk.spatch");
      return;
    } finally {
      paramContext = null;
    } 
  }
  
  public static VAPPLike getApp() { return gApp; }
  
  private void showFailedDialog(Throwable paramThrowable, Activity paramActivity) { (new Thread(new -$$Lambda$VAPPLike$vwIJ-Hc6Y3_GcklBmZimQqRhXoE(paramThrowable, paramActivity))).start(); }
  
  public boolean getIsEnabledNonApkModify() { return this.isEnabledNonApkModify; }
  
  public void initVSKCore(Context paramContext) {
    Application application;
    if (this.isEnabledNonApkModify) {
      (new Thread(new -$$Lambda$VAPPLike$sqTl9432OPX2kzgtH0GxgBenvNs(paramContext))).start();
      return;
    } 
    this.isEnabledNonApkModify = true;
    if (paramContext == null)
      application = getApplication(); 
    ZLoadingDialog zLoadingDialog = new ZLoadingDialog((Context)application);
    if (application instanceof Activity) {
      zLoadingDialog.setLoadingBuilder(Z_TYPE.CHART_RECT).setLoadingColor(-1).setHintText(application.getString(2131689638)).setHintTextSize(16.0F).setHintTextColor(-1).setDurationTime(0.5D).setCancelable(false).setDialogBackgroundColor(Color.parseColor("#CC111111"));
      Activity activity = (Activity)application;
      zLoadingDialog.getClass();
      activity.runOnUiThread(new -$$Lambda$BADIMc0yy4KsYHx0OMmnSUw36VQ(zLoadingDialog));
    } 
    (new Thread(new -$$Lambda$VAPPLike$eynCFGe_71d1LHS8ZEHlnxYy9TE(this, (Context)application, zLoadingDialog))).start();
  }
  
  public void killVSKCore(Context paramContext) { (new Thread(new -$$Lambda$VAPPLike$wKjp919kPeuFHpRTEouLrYsp8Xc(this, paramContext))).start(); }
  
  public void onBaseContextAttached(Context paramContext) {
    super.onBaseContextAttached(paramContext);
    try {
      SInvoker.setStaticFieldObject("com.sk.svm.qihoo.helper.utils.VLog", "OPEN_LOG", Boolean.valueOf(false));
    } finally {
      Exception exception;
    } 
    try {
      TinkerManager.setTinkerApplicationLike((ApplicationLike)this);
      TinkerManager.initFastCrashProtect();
      TinkerManager.setUpgradeRetryEnable(true);
      TinkerManager.installTinker((ApplicationLike)this);
      Tinker.with((Context)getApplication());
      TinkerLoadLibrary.installNavitveLibraryABI(paramContext, "armeabi-v7a");
      if (XProviderSPatch.getString(paramContext, "non_modify") == null) {
        if (SettingsControl.getIsTemporaryLaunchEngine(paramContext))
          this.isEnabledNonApkModify = true; 
      } else {
        this.isEnabledNonApkModify = true;
      } 
    } finally {
      paramContext = null;
    } 
  }
  
  public void onCreate() {
    if (this.isEnabledNonApkModify && XProviderSPatch.getString((Context)getApplication(), "use_unpack") != null)
      try {
        SInvoker.invokeStaticMethod(getApplication().getClassLoader().loadClass("com.sk.dexdumper.DumpDexV2"), "dumpDexDirectly", new Object[] { "", getApplication().getClassLoader() });
      } finally {
        Exception exception = null;
      }  
    gApp = this;
    super.onCreate();
    Once.initialise((Context)getApplication());
    if (this.isEnabledNonApkModify)
      try {
        Object object = SInvoker.invokeStaticMethod("com.sk.svm.local.client.core.VirtualCore", "get", new Object[0]);
        -$$Lambda$VAPPLike$KXJxGIJdDROOgcjtaGbf2khhHss -$$Lambda$VAPPLike$KXJxGIJdDROOgcjtaGbf2khhHss = new -$$Lambda$VAPPLike$KXJxGIJdDROOgcjtaGbf2khhHss(object);
      } finally {
        Exception exception = null;
      }  
    SpiderMan.init((Context)getApplication());
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\VAPPLike.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */