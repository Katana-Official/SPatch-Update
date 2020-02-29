package com.swift.sandhook.utils;

import android.os.Build;
import android.system.Os;
import android.text.TextUtils;
import com.swift.sandhook.HookLog;
import com.swift.sandhook.SandHookConfig;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {
  public static final boolean IS_USING_PROTECTED_STORAGE;
  
  static  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 24) {
      bool = true;
    } else {
      bool = false;
    } 
    IS_USING_PROTECTED_STORAGE = bool;
  }
  
  public static void chmod(String paramString, int paramInt) throws Exception {
    Object object;
    if (SandHookConfig.SDK_INT >= 21)
      try {
        Os.chmod(paramString, paramInt);
        return;
      } catch (Exception null) {} 
    if ((new File(paramString)).isDirectory()) {
      object = "chmod  -R ";
    } else {
      object = "chmod ";
    } 
    String str = String.format("%o", new Object[] { Integer.valueOf(paramInt) });
    Runtime runtime = Runtime.getRuntime();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append((String)object);
    stringBuilder.append(str);
    stringBuilder.append(" ");
    stringBuilder.append(paramString);
    runtime.exec(stringBuilder.toString()).waitFor();
  }
  
  public static void delete(File paramFile) throws IOException {
    for (File file : paramFile.listFiles()) {
      if (file.isDirectory()) {
        delete(file);
      } else if (!file.delete()) {
        throw new IOException();
      } 
    } 
    if (paramFile.delete())
      return; 
    throw new IOException();
  }
  
  public static String getDataPathPrefix() { return IS_USING_PROTECTED_STORAGE ? "/data/user_de/0/" : "/data/data/"; }
  
  public static String getPackageName(String paramString) {
    if (TextUtils.isEmpty(paramString)) {
      HookLog.e("getPackageName using empty dataDir");
      return "";
    } 
    int i = paramString.lastIndexOf("/");
    return (i < 0) ? paramString : paramString.substring(i + 1);
  }
  
  public static String readLine(File paramFile) {
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(paramFile));
    } finally {
      paramFile = null;
    } 
  }
  
  public static void writeLine(File paramFile, String paramString) {
    try {
      paramFile.createNewFile();
    } catch (IOException iOException) {}
    try {
      BufferedWriter bufferedWriter;
    } finally {
      paramString = null;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("error writing line to file ");
      stringBuilder.append(paramFile);
      stringBuilder.append(": ");
      stringBuilder.append(paramString.getMessage());
      HookLog.e(stringBuilder.toString());
    } 
  }
  
  public static interface FileMode {
    public static final int MODE_755 = 493;
    
    public static final int MODE_IRGRP = 32;
    
    public static final int MODE_IROTH = 4;
    
    public static final int MODE_IRUSR = 256;
    
    public static final int MODE_ISGID = 1024;
    
    public static final int MODE_ISUID = 2048;
    
    public static final int MODE_ISVTX = 512;
    
    public static final int MODE_IWGRP = 16;
    
    public static final int MODE_IWOTH = 2;
    
    public static final int MODE_IWUSR = 128;
    
    public static final int MODE_IXGRP = 8;
    
    public static final int MODE_IXOTH = 1;
    
    public static final int MODE_IXUSR = 64;
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\swift\sandhoo\\utils\FileUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */