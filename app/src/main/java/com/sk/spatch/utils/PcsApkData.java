package com.sk.spatch.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import com.sk.spatch.kt.mv2.ModifyV2;
import com.sk.spatch.ui.appv;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class PcsApkData {
  private Context bindAct = null;
  
  private String failedReason = "";
  
  private List<Runnable> mXpatchTasks = new ArrayList<Runnable>();
  
  private String szApkPath = "";
  
  private String[] xposedModLists = null;
  
  static  {
    try {
      return;
    } finally {
      Exception exception = null;
      exception.printStackTrace();
    } 
  }
  
  public PcsApkData() { this.bindAct = null; }
  
  public PcsApkData(Context paramContext) { this.bindAct = paramContext; }
  
  public static File Uri2File(Uri paramUri, Context paramContext) {
    try {
      return uriToFile(paramUri, paramContext);
    } finally {
      paramUri = null;
      paramUri.printStackTrace();
    } 
  }
  
  private int findDexFileCount(String paramString) {
    File file = new File(paramString);
    boolean bool = file.exists();
    int i = 0;
    if (!bool)
      return 0; 
    File[] arrayOfFile = file.listFiles();
    if (arrayOfFile != null) {
      if (arrayOfFile.length == 0)
        return 0; 
      int k = arrayOfFile.length;
      int j;
      for (j = 0; i < k; j = m) {
        int m = j;
        if (Pattern.matches("classes.*\\.dex", arrayOfFile[i].getName()))
          m = j + 1; 
        i++;
      } 
      return j;
    } 
    return 0;
  }
  
  private static native String getMFileNameAndroid();
  
  public static native String getOPFileName();
  
  public static native String getTMDirName();
  
  private static File uriToFile(Uri paramUri, Context paramContext) {
    Object object;
    String str;
    if ("file".equals(paramUri.getScheme())) {
      str = paramUri.getEncodedPath();
      String str1 = str;
      if (str != null) {
        str1 = Uri.decode(str);
        ContentResolver contentResolver = paramContext.getContentResolver();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(_data='");
        stringBuilder.append(str1);
        stringBuilder.append("')");
        String str2 = stringBuilder.toString();
        object = contentResolver.query(uri, new String[] { "_id", "_data" }, str2, null, null);
        int i = 0;
        byte b = 0;
        str = str1;
        if (object != null) {
          object.moveToFirst();
          str = str1;
          i = b;
          while (!object.isAfterLast()) {
            i = object.getInt(object.getColumnIndex("_id"));
            str = object.getString(object.getColumnIndex("_data"));
            object.moveToNext();
          } 
        } 
        if (object != null)
          object.close(); 
        str1 = str;
        if (i != 0) {
          object = new StringBuilder();
          object.append("content://media/external/images/media/");
          object.append(i);
          Uri.parse(object.toString());
          str1 = str;
        } 
      } 
      if (str1 != null)
        return new File(str1); 
    } else if ("content".equals(str.getScheme())) {
      object = object.getContentResolver().query((Uri)str, new String[] { "_data" }, null, null, null);
      if (object != null && object.moveToFirst()) {
        str = object.getString(object.getColumnIndexOrThrow("_data"));
      } else {
        str = null;
      } 
      if (object != null)
        object.close(); 
      if (str != null)
        return new File(str); 
    } 
    return null;
  }
  
  public String getCacheDir() { return this.bindAct.getCacheDir().getAbsolutePath(); }
  
  public String getDataDir() {
    File file = this.bindAct.getExternalFilesDir("");
    return (file != null) ? file.getAbsolutePath() : this.bindAct.getFilesDir().getAbsolutePath();
  }
  
  public String getFailedReason() { return this.failedReason; }
  
  public String getPath() { return this.szApkPath; }
  
  public String processApk(String paramString) {
    try {
      boolean bool;
      Context context = this.bindAct;
      boolean bool1 = false;
      if (context != null) {
        bool = SettingsControl.getDebug(this.bindAct);
      } else {
        bool = false;
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(getCacheDir());
      stringBuilder1.append(File.separatorChar);
      String str2 = stringBuilder1.toString();
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(getDataDir());
      stringBuilder2.append(File.separatorChar);
      String str3 = stringBuilder2.toString();
    } finally {
      paramString = null;
      paramString.printStackTrace();
    } 
    return paramString;
  }
  
  public void setFailedReason(String paramString) { this.failedReason = paramString; }
  
  public void setPath(String paramString) { this.szApkPath = paramString; }
  
  public void setupMod(List<appv> paramList) {
    Iterator<appv> iterator = paramList.iterator();
    while (iterator.hasNext()) {
      appv appv = iterator.next();
      if (appv.getAppInfo() != null && (appv.getAppInfo()).sourceDir == null)
        iterator.remove(); 
    } 
    this.xposedModLists = new String[paramList.size()];
    int i = 0;
    for (appv appv : paramList) {
      if (appv.getAppInfo() != null)
        this.xposedModLists[i] = (appv.getAppInfo()).sourceDir; 
      i++;
    } 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatc\\utils\PcsApkData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */