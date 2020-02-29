package com.sk.spatch.kt.srcs.task;

import com.sk.spatch.kt.srcs.util.ApkSignatureHelper;
import com.sk.spatch.kt.srcs.util.FileUtils;
import java.io.File;

public class SaveApkSignatureTask implements Runnable {
  private String apkPath;
  
  private String dstFilePath;
  
  public SaveApkSignatureTask(String paramString1, String paramString2) {
    this.apkPath = paramString1;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString2);
    stringBuilder.append(getSignInfoPath());
    this.dstFilePath = stringBuilder.toString().replace("/", File.separator);
  }
  
  public static native String getSignInfoPath();
  
  public void run() {
    String str = ApkSignatureHelper.getApkSignInfo(this.apkPath);
    if (str == null || str.isEmpty()) {
      System.out.println(" Get original signature failed !!!!");
      return;
    } 
    File file = (new File(this.dstFilePath)).getParentFile();
    if (!file.exists())
      file.mkdirs(); 
    FileUtils.writeFile(this.dstFilePath, str);
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\kt\srcs\task\SaveApkSignatureTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */