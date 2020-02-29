package com.sk.spatch.utils;

import android.app.Application;
import android.content.Context;
import com.sk.spatch.VAPPLike;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class SKVMInstaller {
  private static boolean acquireRestore() {
    try {
      return true;
    } finally {
      Exception exception = null;
      exception.printStackTrace();
    } 
  }
  
  private static boolean acquireUpdate() {
    StringBuilder stringBuilder;
    Application application = VAPPLike.getApp().getApplication();
    String str = application.getCacheDir().getAbsolutePath();
    Object object = null;
    try {
      File file = application.getExternalFilesDir("");
    } finally {
      Exception exception = null;
      exception.printStackTrace();
    } 
    object = stringBuilder;
    if (stringBuilder == null) {
      stringBuilder = new StringBuilder();
      stringBuilder.append(str);
      stringBuilder.append(File.separatorChar);
      stringBuilder.append("trump.db");
      object = stringBuilder.toString();
      try {
        oo0ooooo0o0o0((Context)application, "vmx.7z", (String)object);
      } catch (Exception exception) {
        exception.printStackTrace();
        return false;
      } 
    } 
    TinkerInstaller.onReceiveUpgradePatch((Context)application, (String)object);
    return true;
  }
  
  public static boolean doEnv(boolean paramBoolean) { return paramBoolean ? acquireUpdate() : acquireRestore(); }
  
  private static void oo0ooooo0o0o0(Context paramContext, String paramString1, String paramString2) throws Exception {
    ClassLoader classLoader = paramContext.getClassLoader();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("assets");
    stringBuilder.append(File.separatorChar);
    stringBuilder.append(paramString1);
    InputStream inputStream = classLoader.getResourceAsStream(stringBuilder.toString());
    FileOutputStream fileOutputStream = new FileOutputStream(new File(paramString2));
    byte[] arrayOfByte = new byte[1024];
    while (true) {
      int i = inputStream.read(arrayOfByte);
      if (i != -1) {
        fileOutputStream.write(arrayOfByte, 0, i);
        fileOutputStream.flush();
        continue;
      } 
      break;
    } 
    inputStream.close();
    fileOutputStream.close();
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatc\\utils\SKVMInstaller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */