package com.sk.spatch;

import android.content.Context;
import com.tencent.tinker.entry.ApplicationLike;
import com.tencent.tinker.lib.listener.DefaultPatchListener;
import com.tencent.tinker.lib.listener.PatchListener;
import com.tencent.tinker.lib.patch.AbstractPatch;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.DefaultLoadReporter;
import com.tencent.tinker.lib.reporter.DefaultPatchReporter;
import com.tencent.tinker.lib.reporter.LoadReporter;
import com.tencent.tinker.lib.reporter.PatchReporter;
import com.tencent.tinker.lib.service.DefaultTinkerResultService;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.lib.util.UpgradePatchRetry;

public class TinkerManager {
  private static final String TAG = "Tinker.TinkerManager";
  
  private static ApplicationLike applicationLike;
  
  private static boolean isInstalled = false;
  
  static  {
  
  }
  
  public static ApplicationLike getTinkerApplicationLike() { return applicationLike; }
  
  public static void initFastCrashProtect() {}
  
  public static void installTinker(ApplicationLike paramApplicationLike) {
    if (isInstalled) {
      TinkerLog.w("Tinker.TinkerManager", "install tinker, but has installed, ignore", new Object[0]);
      return;
    } 
    TinkerInstaller.install(paramApplicationLike, (LoadReporter)new DefaultLoadReporter((Context)paramApplicationLike.getApplication()), (PatchReporter)new DefaultPatchReporter((Context)paramApplicationLike.getApplication()), (PatchListener)new DefaultPatchListener((Context)paramApplicationLike.getApplication()), DefaultTinkerResultService.class, (AbstractPatch)new UpgradePatch());
    isInstalled = true;
  }
  
  public static void sampleInstallTinker(ApplicationLike paramApplicationLike) {
    if (isInstalled) {
      TinkerLog.w("Tinker.TinkerManager", "install tinker, but has installed, ignore", new Object[0]);
      return;
    } 
    TinkerInstaller.install(paramApplicationLike);
    isInstalled = true;
  }
  
  public static void setTinkerApplicationLike(ApplicationLike paramApplicationLike) { applicationLike = paramApplicationLike; }
  
  public static void setUpgradeRetryEnable(boolean paramBoolean) { UpgradePatchRetry.getInstance((Context)applicationLike.getApplication()).setRetryEnable(paramBoolean); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\TinkerManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */