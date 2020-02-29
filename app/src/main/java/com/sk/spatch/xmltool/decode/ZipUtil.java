package com.sk.spatch.xmltool.decode;

public abstract class ZipUtil {
  public static byte[] copy(byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte != null) {
      int i = paramArrayOfbyte.length;
      byte[] arrayOfByte = new byte[i];
      System.arraycopy(paramArrayOfbyte, 0, arrayOfByte, 0, i);
      return arrayOfByte;
    } 
    return null;
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\ZipUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */