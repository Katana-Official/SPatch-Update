package com.sk.spatch.kt.srcs.task;

import com.sk.spatch.kt.srcs.util.FileUtils;
import com.sk.spatch.utils.bksm;
import com.sk.spatch.utils.signApk;
import java.io.File;

public class BuildAndSignApkTask implements Runnable {
  private boolean keepUnsignedApkFile = false;
  
  private String signedApkPath;
  
  private String unzipApkFilePath;
  
  public BuildAndSignApkTask(boolean paramBoolean, String paramString1, String paramString2) {
    this.unzipApkFilePath = paramString1;
    this.signedApkPath = paramString2;
  }
  
  private boolean signApkEx(String paramString1, String paramString2, String paramString3) { return signApk.sign(paramString1, paramString2, paramString3); }
  
  public void run() {
    File file1 = new File(this.unzipApkFilePath);
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(file1.getParent());
    stringBuilder1.append(File.separator);
    stringBuilder1.append("unsigned.apk");
    Object object = stringBuilder1.toString();
    if (!FileUtils.compressToZipEx(this.unzipApkFilePath, (String)object)) {
      bksm.is_success = false;
      object = new File((String)object);
      if (!this.keepUnsignedApkFile && object.exists())
        object.delete(); 
      return;
    } 
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(file1.getParent());
    stringBuilder2.append(File.separator);
    stringBuilder2.append("keystore");
    String str = stringBuilder2.toString();
    file1 = new File(str);
    FileUtils.copyFileFromJar("assets/keystore", str);
    signApkEx((String)object, this.signedApkPath, this.unzipApkFilePath);
    object = new File((String)object);
    File file2 = new File(this.signedApkPath);
    if (!this.keepUnsignedApkFile && object.exists() && file2.exists())
      object.delete(); 
    if (file1.exists())
      file1.delete(); 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\kt\srcs\task\BuildAndSignApkTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */