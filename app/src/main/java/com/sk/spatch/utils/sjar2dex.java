package com.sk.spatch.utils;

import java.io.PrintStream;

public class sjar2dex {
  public static void goDex(String paramString1, String paramString2, boolean paramBoolean) {
    if (paramBoolean)
      try {
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("jar2dex ");
        stringBuilder.append(paramString1);
        stringBuilder.append(" -> ");
        stringBuilder.append(paramString2);
        return;
      } finally {
        paramString1 = null;
      }  
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatc\\utils\sjar2dex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */