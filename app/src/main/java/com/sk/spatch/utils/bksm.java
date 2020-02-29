package com.sk.spatch.utils;

import com.sk.spatch.kt.srcs.util.FileUtils;
import java.io.File;
import java.io.PrintStream;

public class bksm {
  public static boolean is_success = true;
  
  static  {
  
  }
  
  public static boolean bsmali(String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
    try {
      File file = new File(paramString2);
      if (file.exists())
        FileUtils.deleteDir(file); 
      if (paramBoolean)
        System.out.println("尝试Baksmali"); 
      Class.forName("org.jf.baksmali.Main").getMethod("main", new Class[] { String[].class }).invoke(null, new Object[] { { "disassemble", "-o", paramString2, paramString1 } });
      if (paramBoolean) {
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("输出路径：");
        stringBuilder.append(paramString2);
        stringBuilder.append("，app名：");
        stringBuilder.append(paramString3);
        printStream.println(stringBuilder.toString());
        System.out.println("调试模式状况：true");
      } 
      return true;
    } finally {
      paramString1 = null;
      paramString1.printStackTrace();
    } 
  }
  
  private static native boolean chkCanInject(String paramString1, String paramString2);
  
  private static native boolean doInject(String paramString1, String paramString2);
  
  private static native String getSmaliPath(String paramString1, String paramString2);
  
  public static boolean makeDex(String paramString1, String paramString2) {
    try {
      File file = new File(paramString2);
      return true;
    } finally {
      paramString1 = null;
      paramString1.printStackTrace();
    } 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatc\\utils\bksm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */