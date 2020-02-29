package com.sk.spatch.kt.srcs.task;

import com.sk.spatch.kt.srcs.util.FileUtils;
import com.sk.spatch.utils.bksm;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;

public class ApkModifyTask implements Runnable {
  private static final String JAR_FILE_NAME = "output-jar.jar";
  
  public static String SKVFlag = "m.zip";
  
  public static boolean isEnabled = false;
  
  private String applicationName;
  
  private int dexFileCount;
  
  private boolean keepJarFile;
  
  private boolean showAllLogs;
  
  private String unzipApkFilePath;
  
  static  {
  
  }
  
  public ApkModifyTask(boolean paramBoolean1, boolean paramBoolean2, String paramString1, String paramString2, int paramInt) {
    this.showAllLogs = paramBoolean1;
    this.unzipApkFilePath = paramString1;
    this.keepJarFile = paramBoolean2;
    this.applicationName = paramString2;
    this.dexFileCount = paramInt;
  }
  
  private boolean SKModifyDex(String paramString1, String paramString2, String paramString3) { return bksm.bsmali(paramString1, paramString2, paramString3, this.showAllLogs); }
  
  private boolean SKToDex(String paramString1, String paramString2) {
    boolean bool;
    File file = new File(paramString2);
    if (file.exists())
      file.delete(); 
    if (bksm.makeDex(paramString1, paramString2)) {
      bool = true;
      if (this.showAllLogs) {
        System.out.println("还原程序成功。");
        return true;
      } 
    } else {
      if (this.showAllLogs)
        System.out.println("还原二进制文件失败。"); 
      bool = false;
    } 
    return bool;
  }
  
  private ArrayList<String> createClassesDotDexFileList(int paramInt) {
    ArrayList<String> arrayList = new ArrayList();
    for (int i = 0; i < paramInt; i++) {
      if (i == 0) {
        arrayList.add("classes.dex");
      } else {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("classes");
        stringBuilder.append(i + 1);
        stringBuilder.append(".dex");
        arrayList.add(stringBuilder.toString());
      } 
    } 
    return arrayList;
  }
  
  private String dumpJarFile(int paramInt, String paramString1, String paramString2, String paramString3) {
    for (String str : createClassesDotDexFileList(paramInt)) {
      Runtime.getRuntime().gc();
      if (this.showAllLogs)
        System.out.println("正在处理dex。。。"); 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramString1);
      stringBuilder.append(str);
      if (SKModifyDex(stringBuilder.toString(), paramString2, paramString3)) {
        if (this.showAllLogs)
          System.out.println("处理成功，代码注入成功！"); 
        return str;
      } 
      if (this.showAllLogs)
        System.out.println("处理失败，代码注入失败。"); 
    } 
    bksm.is_success = false;
    return "";
  }
  
  public void run() {
    if (this.showAllLogs) {
      PrintStream printStream = System.out;
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("应用程序名：");
      stringBuilder1.append(this.applicationName);
      printStream.println(stringBuilder1.toString());
    } 
    bksm.is_success = true;
    File file = new File(this.unzipApkFilePath);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(file.getParent());
    stringBuilder.append(File.separator);
    stringBuilder.append("output-jar.jar");
    String str1 = stringBuilder.toString();
    String str2 = dumpJarFile(this.dexFileCount, this.unzipApkFilePath, str1, this.applicationName);
    if (this.showAllLogs) {
      PrintStream printStream = System.out;
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("应用Application入口应该在这个文件里面： ");
      stringBuilder1.append(str2);
      printStream.println(stringBuilder1.toString());
    } 
    if (!str2.equals("")) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(this.unzipApkFilePath);
      stringBuilder1.append(str2);
      str2 = stringBuilder1.toString();
      File file1 = new File(str2);
      if (file1.exists() && !file1.delete() && this.showAllLogs)
        System.out.println("删除dex文件失败。"); 
      SKToDex(str1, str2);
    } 
    if (!this.keepJarFile) {
      File file2 = new File(str1);
      if (file2.exists()) {
        if (this.showAllLogs)
          System.out.println("尝试清理掉文件夹。。。"); 
        FileUtils.deleteDir(file2);
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str1);
      stringBuilder1.append(SKVFlag);
      File file1 = new File(stringBuilder1.toString());
      if (file1.exists())
        file1.delete(); 
    } 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\kt\srcs\task\ApkModifyTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */