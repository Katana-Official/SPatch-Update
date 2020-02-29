package com.sk.spatch.kt.srcs.task;

import com.sk.spatch.kt.srcs.util.FileUtils;
import java.io.File;
import java.util.HashMap;

public class SoAndDexCopyTask implements Runnable {
  private static final String SO_FILE_NAME = "libsandhook.so";
  
  private static final String SO_FILE_SUFFIX = ".so";
  
  private final String[] APK_LIB_PATH_ARRAY = new String[] { "lib/armeabi-v7a/", "lib/armeabi/", "lib/arm64-v8a/" };
  
  private final HashMap<String, String> SO_FILE_PATH_MAP = new HashMap<String, String>() {
    
    };
  
  private String XPOSED_MODULE_FILE_NAME_PREFIX = getSpModNameSfx();
  
  private int dexFileCount;
  
  private String unzipApkFilePath;
  
  private String[] xposedModuleArray;
  
  public SoAndDexCopyTask(int paramInt, String paramString, String[] paramArrayOfString) {
    this.dexFileCount = paramInt;
    this.unzipApkFilePath = paramString;
    this.xposedModuleArray = paramArrayOfString;
  }
  
  private void copyDexFile(int paramInt) {
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("classes");
    stringBuilder1.append(paramInt + 1);
    stringBuilder1.append(".dex");
    String str = stringBuilder1.toString();
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(this.unzipApkFilePath);
    stringBuilder2.append(str);
    FileUtils.copyFileFromJar("assets/classes.dex", stringBuilder2.toString());
  }
  
  private void copyLibFile(String paramString1, String paramString2) {
    File file = new File(paramString1);
    if (!file.exists())
      file.mkdirs(); 
    FileUtils.copyFileFromJar(paramString2, (new File(file, paramString2.substring(paramString2.lastIndexOf('/'), paramString2.length()))).getAbsolutePath());
  }
  
  private void copySoFile() {
    String[] arrayOfString = this.APK_LIB_PATH_ARRAY;
    int k = arrayOfString.length;
    int i = 0;
    int j = i;
    while (i < k) {
      String str1 = arrayOfString[i];
      String str2 = fullLibPath(str1);
      if ((new File(str2)).exists()) {
        copyLibFile(str2, this.SO_FILE_PATH_MAP.get(str1));
        j = 1;
      } 
      i++;
    } 
    if (j == 0)
      copyLibFile(fullLibPath(getSpecLibPath()), this.SO_FILE_PATH_MAP.get(getSpecLibPath())); 
    arrayOfString = this.xposedModuleArray;
    if (arrayOfString != null && arrayOfString.length > 0) {
      int m = arrayOfString.length;
      i = 0;
      j = i;
      while (i < m) {
        String str = arrayOfString[i].trim();
        if (str.length() != 0) {
          File file = new File(str);
          if (file.exists()) {
            String[] arrayOfString1 = this.APK_LIB_PATH_ARRAY;
            int n = arrayOfString1.length;
            for (k = 0; k < n; k++) {
              String str1 = fullLibPath(arrayOfString1[k]);
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append(this.XPOSED_MODULE_FILE_NAME_PREFIX);
              stringBuilder.append(j);
              stringBuilder.append(".so");
              String str2 = stringBuilder.toString();
              if ((new File(str1)).exists())
                FileUtils.copyFile(file, new File(str1, str2)); 
            } 
            j++;
          } 
        } 
        i++;
      } 
    } 
  }
  
  private void deleteMetaInfo() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.unzipApkFilePath);
    stringBuilder.append("META-INF");
    File file = new File(stringBuilder.toString());
    if (!file.exists())
      return; 
    File[] arrayOfFile = file.listFiles();
    if (arrayOfFile != null) {
      if (arrayOfFile.length == 0)
        return; 
      int j = arrayOfFile.length;
      for (int i = 0; i < j; i++) {
        File file1 = arrayOfFile[i];
        String str = file1.getName().toUpperCase();
        if (str.endsWith(".MF") || str.endsWith(".RAS") || str.endsWith(".SF"))
          file1.delete(); 
      } 
    } 
  }
  
  private String fullLibPath(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.unzipApkFilePath);
    stringBuilder.append(paramString.replace("/", File.separator));
    return stringBuilder.toString();
  }
  
  public static native String getSpModNameSfx();
  
  private static native String getSpecLibPath();
  
  private static native String getSpecLibPath2();
  
  private static native String getSpecLibPathOld();
  
  public void run() {
    copySoFile();
    copyDexFile(this.dexFileCount);
    deleteMetaInfo();
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\kt\srcs\task\SoAndDexCopyTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */