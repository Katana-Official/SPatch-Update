package com.sk.spatch.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Build;
import com.sk.spatch.core.SInvoker;
import java.util.HashSet;
import java.util.Set;

public class DoLaunchApp {
  private static void checkAndLaunch(Context paramContext, Intent paramIntent, int paramInt, String paramString) {
    if (Build.VERSION.SDK_INT < 23) {
      launchActivity(paramIntent, paramInt);
      return;
    } 
    try {
      int i;
      Object object = SInvoker.invokeStaticMethod("com.sk.manager.pipe.VPackageManager", "get", new Object[0]);
      if (((ApplicationInfo)object.getClass().getMethod("getApplicationInfo", new Class[] { paramString.getClass(), int.class, int.class }).invoke(object, new Object[] { paramString, Integer.valueOf(0), Integer.valueOf(0) })).targetSdkVersion >= 23)
        return; 
      String[] arrayOfString = ((PackageInfo)object.getClass().getMethod("getPackageInfo", new Class[] { paramString.getClass(), int.class, int.class }).invoke(object, new Object[] { paramString, Integer.valueOf(4096), Integer.valueOf(0) })).requestedPermissions;
      object = new HashSet();
      Set set = (Set)SInvoker.getStaticFieldObject("com.sk.svm.conn.server.pm.parser.VPackage$PermissionComponent", "DANGEROUS_PERMISSION");
    } finally {
      paramContext = null;
      launchActivity(paramIntent, paramInt);
    } 
  }
  
  private static void launchActivity(Intent paramIntent, int paramInt) {
    try {
      return;
    } finally {
      paramIntent = null;
      paramIntent.printStackTrace();
    } 
  }
  
  @Deprecated
  public static void launchApp(Context paramContext, String paramString, int paramInt) { launchAppOdin(paramContext, paramString, paramInt); }
  
  public static void launchAppAsync(Context paramContext, String paramString, int paramInt) { (new Thread(new -$$Lambda$DoLaunchApp$8IIWgv3DTkjVDF0_jLtyVwPDMqU(paramContext, paramString, paramInt))).start(); }
  
  private static void launchAppOdin(Context paramContext, String paramString, int paramInt) { // Byte code:
    //   0: aload_0
    //   1: ldc 'activity'
    //   3: invokevirtual getSystemService : (Ljava/lang/String;)Ljava/lang/Object;
    //   6: checkcast android/app/ActivityManager
    //   9: astore #5
    //   11: ldc 'com.sk.manager.pipe.NewActivityManager'
    //   13: ldc 'get'
    //   15: iconst_0
    //   16: anewarray java/lang/Object
    //   19: invokestatic invokeStaticMethod : (Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
    //   22: astore #4
    //   24: aload #5
    //   26: ifnull -> 209
    //   29: aload #5
    //   31: invokevirtual getRunningAppProcesses : ()Ljava/util/List;
    //   34: invokeinterface iterator : ()Ljava/util/Iterator;
    //   39: astore #5
    //   41: aload #5
    //   43: invokeinterface hasNext : ()Z
    //   48: ifeq -> 209
    //   51: aload #5
    //   53: invokeinterface next : ()Ljava/lang/Object;
    //   58: checkcast android/app/ActivityManager$RunningAppProcessInfo
    //   61: astore #6
    //   63: aload #4
    //   65: invokevirtual getClass : ()Ljava/lang/Class;
    //   68: ldc 'getAppProcessName'
    //   70: iconst_1
    //   71: anewarray java/lang/Class
    //   74: dup
    //   75: iconst_0
    //   76: getstatic java/lang/Integer.TYPE : Ljava/lang/Class;
    //   79: aastore
    //   80: invokevirtual getMethod : (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   83: aload #4
    //   85: iconst_1
    //   86: anewarray java/lang/Object
    //   89: dup
    //   90: iconst_0
    //   91: aload #6
    //   93: getfield pid : I
    //   96: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   99: aastore
    //   100: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   103: checkcast java/lang/String
    //   106: aload_1
    //   107: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   110: ifeq -> 41
    //   113: iconst_1
    //   114: istore_3
    //   115: goto -> 118
    //   118: ldc 'com.sk.svm.local.client.core.VirtualCore'
    //   120: ldc 'get'
    //   122: iconst_0
    //   123: anewarray java/lang/Object
    //   126: invokestatic invokeStaticMethod : (Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
    //   129: astore #4
    //   131: aload #4
    //   133: invokevirtual getClass : ()Ljava/lang/Class;
    //   136: ldc 'getLaunchIntent'
    //   138: iconst_2
    //   139: anewarray java/lang/Class
    //   142: dup
    //   143: iconst_0
    //   144: aload_1
    //   145: invokevirtual getClass : ()Ljava/lang/Class;
    //   148: aastore
    //   149: dup
    //   150: iconst_1
    //   151: getstatic java/lang/Integer.TYPE : Ljava/lang/Class;
    //   154: aastore
    //   155: invokevirtual getMethod : (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   158: aload #4
    //   160: iconst_2
    //   161: anewarray java/lang/Object
    //   164: dup
    //   165: iconst_0
    //   166: aload_1
    //   167: aastore
    //   168: dup
    //   169: iconst_1
    //   170: iload_2
    //   171: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   174: aastore
    //   175: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   178: checkcast android/content/Intent
    //   181: astore #4
    //   183: iload_3
    //   184: ifeq -> 194
    //   187: aload #4
    //   189: iload_2
    //   190: invokestatic launchActivity : (Landroid/content/Intent;I)V
    //   193: return
    //   194: aload_0
    //   195: aload #4
    //   197: iload_2
    //   198: aload_1
    //   199: invokestatic checkAndLaunch : (Landroid/content/Context;Landroid/content/Intent;ILjava/lang/String;)V
    //   202: return
    //   203: astore_0
    //   204: aload_0
    //   205: invokevirtual printStackTrace : ()V
    //   208: return
    //   209: iconst_0
    //   210: istore_3
    //   211: goto -> 118
    // Exception table:
    //   from	to	target	type
    //   0	24	203	finally
    //   29	41	203	finally
    //   41	113	203	finally
    //   118	183	203	finally
    //   187	193	203	finally
    //   194	202	203	finally }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatc\\ui\DoLaunchApp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */