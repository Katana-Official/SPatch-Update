package com.sk.spatch.utils;

import java.util.Random;

public class SPatchXUtils {
  public static String getClassName() { return Thread.currentThread().getStackTrace()[2].getClassName(); }
  
  public static String getFileName() { return Thread.currentThread().getStackTrace()[2].getFileName(); }
  
  public static int getLineNumber() { return Thread.currentThread().getStackTrace()[2].getLineNumber(); }
  
  public static String getMethodName() { return Thread.currentThread().getStackTrace()[2].getMethodName(); }
  
  public static String getRandomString(int paramInt) {
    Random random = new Random();
    StringBuffer stringBuffer = new StringBuffer();
    for (int i = 0; i < paramInt; i++)
      stringBuffer.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".charAt(random.nextInt(62))); 
    return stringBuffer.toString();
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatc\\utils\SPatchXUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */