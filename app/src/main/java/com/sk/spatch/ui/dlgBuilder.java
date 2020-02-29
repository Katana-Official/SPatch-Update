package com.sk.spatch.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import com.sk.spatch.MainActivity;
import com.sk.spatch.core.SInvoker;
import com.sk.spatch.kt.srcs.util.FileUtils;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;

public class dlgBuilder {
  private static final boolean canSkipPermission = false;
  
  private static Object redirectStorage;
  
  private static final String szCID = "channelIDSK";
  
  private static final String szCName = "SPatchMain";
  
  static  {
  
  }
  
  public static void buildCoreError(Context paramContext) { ((Activity)paramContext).runOnUiThread(new -$$Lambda$dlgBuilder$ER-RRyCTOwDtyRIORCBdFuEkvXY(paramContext)); }
  
  public static void buildNotificationFinish(Context paramContext) {
    NotificationManager notificationManager = (NotificationManager)paramContext.getSystemService("notification");
    if (Build.VERSION.SDK_INT >= 26) {
      NotificationChannel notificationChannel = new NotificationChannel("channelIDSK", "SPatchMain", 4);
      if (notificationManager != null)
        notificationManager.createNotificationChannel(notificationChannel); 
    } 
    PendingIntent pendingIntent = PendingIntent.getActivity(paramContext, 0, new Intent(paramContext, MainActivity.class), 134217728);
    Notification notification = (new NotificationCompat.Builder(paramContext, "channelIDSK")).setTicker(paramContext.getResources().getString(2131689500)).setOngoing(true).setSmallIcon(2131558401).setContentTitle(paramContext.getResources().getString(2131689613)).setContentText(paramContext.getResources().getString(2131689613)).setContentIntent(pendingIntent).setChannelId("channelIDSK").setAutoCancel(true).build();
    if (notificationManager != null)
      notificationManager.notify(193, notification); 
  }
  
  public static Bitmap drawable2Bitmap(Drawable paramDrawable) {
    if (paramDrawable instanceof BitmapDrawable)
      return ((BitmapDrawable)paramDrawable).getBitmap(); 
    if (paramDrawable instanceof android.graphics.drawable.NinePatchDrawable) {
      Bitmap.Config config;
      int i = paramDrawable.getIntrinsicWidth();
      int j = paramDrawable.getIntrinsicHeight();
      if (paramDrawable.getOpacity() != -1) {
        config = Bitmap.Config.ARGB_8888;
      } else {
        config = Bitmap.Config.RGB_565;
      } 
      Bitmap bitmap = Bitmap.createBitmap(i, j, config);
      Canvas canvas = new Canvas(bitmap);
      paramDrawable.setBounds(0, 0, paramDrawable.getIntrinsicWidth(), paramDrawable.getIntrinsicHeight());
      paramDrawable.draw(canvas);
      return bitmap;
    } 
    return null;
  }
  
  private static String getAppRootOfSdCardRemovable(Context paramContext) {
    if (!Environment.getExternalStorageState().equals("mounted"))
      return null; 
    StorageManager storageManager = (StorageManager)paramContext.getSystemService("storage");
    try {
      Class<?> clazz = Class.forName("android.os.storage.StorageVolume");
      Method method3 = storageManager.getClass().getMethod("getVolumeList", new Class[0]);
      Method method1 = clazz.getMethod("getPath", new Class[0]);
      Method method2 = clazz.getMethod("isRemovable", new Class[0]);
      Object object = method3.invoke(storageManager, new Object[0]);
      if (object != null) {
        int j = Array.getLength(object);
        for (int i = 0;; i++) {
          if (i < j) {
            Object object1 = Array.get(object, i);
            String str = (String)method1.invoke(object1, new Object[0]);
            if (((Boolean)method2.invoke(object1, new Object[0])).booleanValue())
              return str; 
          } else {
            return null;
          } 
        } 
      } 
      throw new IOException("Storage is null!!!");
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return null;
  }
  
  public static void getMoreOptVapp(Activity paramActivity, appv paramappv) { (new Thread(new -$$Lambda$dlgBuilder$Z6mv4hNdy2btM56DFd8PAxAU7ZY(paramappv, paramActivity))).start(); }
  
  public static String getVSPath(Context paramContext, String paramString) {
    StringBuilder stringBuilder2;
    String str1;
    String str2 = getAppRootOfSdCardRemovable(paramContext);
    if (str2 != null && (new File(str2)).canWrite()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(File.separatorChar);
      stringBuilder.append("SKVirtualStorage");
      stringBuilder.append(File.separatorChar);
      stringBuilder.append("xStorage");
      stringBuilder.append(File.separatorChar);
      stringBuilder.append(paramString);
      stringBuilder.append(File.separatorChar);
      str1 = stringBuilder.toString();
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append(str2);
      stringBuilder2.append(str1);
      return stringBuilder2.toString();
    } 
    Object object = str1.getExternalFilesDir("");
    if (object == null) {
      object = new StringBuilder();
      object.append(str1.getCacheDir().getAbsolutePath());
      object.append("/");
      object.append((String)stringBuilder2);
      object.append("/");
      return object.toString();
    } 
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(object.getAbsolutePath());
    stringBuilder1.append("/");
    stringBuilder1.append((String)stringBuilder2);
    stringBuilder1.append("/");
    return stringBuilder1.toString();
  }
  
  private static void gotoPermSettings(Activity paramActivity) {
    AlertDialog.Builder builder = new AlertDialog.Builder((Context)paramActivity);
    builder.setCancelable(false);
    builder.setPositiveButton(2131689572, new -$$Lambda$dlgBuilder$6o_oDJ0cuwUN_XG5PLrp-Mvx1ow(paramActivity));
    builder.setOnDismissListener(new -$$Lambda$dlgBuilder$T-u3-Keptr7uSNaEC1hv4BVc4Rs(paramActivity));
    builder.setTitle(2131689619);
    builder.setMessage(2131689618);
    paramActivity.runOnUiThread(new -$$Lambda$dlgBuilder$cxaxD40rzqzcsgWVColoFL-wpZY(builder));
  }
  
  public static void loadSPInfo(Activity paramActivity, boolean paramBoolean) {
    AlertDialog.Builder builder = new AlertDialog.Builder((Context)paramActivity);
    builder.setTitle(2131689571);
    builder.setMessage(2131689599);
    builder.setIcon(2131558403);
    if (paramBoolean) {
      builder.setCancelable(false);
      builder.setPositiveButton(2131689592, new -$$Lambda$dlgBuilder$1pjIRW8wtsQDpErNPk06LvcMO3I(paramActivity));
      builder.setNegativeButton(2131689572, new -$$Lambda$dlgBuilder$c_E7YrZhepQGoyrLtyRtXQWRHoo(paramActivity));
    } else {
      builder.setCancelable(true);
      builder.setPositiveButton(2131689584, -$$Lambda$dlgBuilder$OHc4lG6K2htwgCUkFYSCZsGjLow.INSTANCE);
    } 
    paramActivity.runOnUiThread(new -$$Lambda$dlgBuilder$HGSPvELKcq6k6HdaKxlYcy8CHFI(builder));
  }
  
  private static void openAssignFolder(String paramString, Context paramContext) {
    File file = new File(paramString);
    if (!file.exists())
      return; 
    Intent intent = new Intent("android.intent.action.GET_CONTENT");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(268435456);
    if (Build.VERSION.SDK_INT >= 24) {
      intent.setDataAndType(FileProvider.getUriForFile(paramContext, "com.sk.spatch.provider", file), "file/*");
    } else {
      intent.setDataAndType(Uri.fromFile(file), "file/*");
    } 
    try {
      return;
    } finally {
      file = null;
      file.printStackTrace();
    } 
  }
  
  public static void setupVSPath(Context paramContext, String paramString) {
    try {
      Class clazz = SInvoker.getStaticClass("com.sk.manager.pipe.VirtualStorageManager");
      return;
    } finally {
      paramContext = null;
    } 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatc\\ui\dlgBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */