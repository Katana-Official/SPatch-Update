package com.sk.spatch.utils;

import com.sk.spatch.kt.srcs.util.FileUtils;

public class signApk {
  private static native String getKsFileByMachine(int paramInt);
  
  public static boolean sign(String paramString1, String paramString2, String paramString3) {
    try {
      String str = getKsFileByMachine(1);
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(paramString3);
      stringBuilder2.append(getKsFileByMachine(2));
      FileUtils.copyFileFromJar(str, stringBuilder2.toString());
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(paramString3);
      return true;
    } finally {
      paramString1 = null;
      paramString1.printStackTrace();
    } 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatc\\utils\signApk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */