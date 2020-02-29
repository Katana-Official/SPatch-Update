package com.sk.spatch.xmltool.decode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtil {
  public static void close(ZipFile paramZipFile) {
    if (paramZipFile != null)
      try {
        paramZipFile.close();
        return;
      } catch (IOException iOException) {
        return;
      }  
  }
  
  public static void close(InputStream paramInputStream) {
    if (paramInputStream != null)
      try {
        paramInputStream.close();
        return;
      } catch (IOException iOException) {
        return;
      }  
  }
  
  public static void close(OutputStream paramOutputStream) {
    if (paramOutputStream != null)
      try {
        paramOutputStream.close();
        return;
      } catch (IOException iOException) {
        return;
      }  
  }
  
  public static byte[] readBytes(InputStream paramInputStream) throws IOException {
    byte[] arrayOfByte2 = new byte[10240];
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    while (true) {
      int i = paramInputStream.read(arrayOfByte2);
      if (i != -1) {
        byteArrayOutputStream.write(arrayOfByte2, 0, i);
        continue;
      } 
      break;
    } 
    byte[] arrayOfByte1 = byteArrayOutputStream.toByteArray();
    byteArrayOutputStream.close();
    return arrayOfByte1;
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\StreamUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */