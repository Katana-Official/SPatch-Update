package com.sk.spatch.xmltool.main;

import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Utils {
  public static byte[] addByte(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    if (paramArrayOfbyte1 == null)
      return null; 
    if (paramArrayOfbyte2 == null)
      return paramArrayOfbyte1; 
    int j = paramArrayOfbyte1.length + paramArrayOfbyte2.length;
    byte[] arrayOfByte = new byte[j];
    int i;
    for (i = 0; i < paramArrayOfbyte1.length; i++)
      arrayOfByte[i] = paramArrayOfbyte1[i]; 
    for (i = paramArrayOfbyte1.length; i < j; i++)
      arrayOfByte[i] = paramArrayOfbyte2[i - paramArrayOfbyte1.length]; 
    return arrayOfByte;
  }
  
  public static short byte2Short(byte[] paramArrayOfbyte) {
    short s = (short)(paramArrayOfbyte[0] & 0xFF);
    return (short)((short)((short)(paramArrayOfbyte[1] & 0xFF) << 8) | s);
  }
  
  public static int byte2int(byte[] paramArrayOfbyte) {
    byte b1 = paramArrayOfbyte[0];
    byte b2 = paramArrayOfbyte[1];
    byte b3 = paramArrayOfbyte[2];
    return paramArrayOfbyte[3] << 24 | b1 & 0xFF | b2 << 8 & 0xFF00 | b3 << 24 >>> 8;
  }
  
  public static byte[] byteConcat(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt) {
    for (int i = 0; i < paramArrayOfbyte2.length; i++)
      paramArrayOfbyte1[i + paramInt] = paramArrayOfbyte2[i]; 
    return paramArrayOfbyte1;
  }
  
  public static String bytesToHexString(byte[] paramArrayOfbyte) {
    paramArrayOfbyte = reverseBytes(paramArrayOfbyte);
    StringBuilder stringBuilder = new StringBuilder("");
    if (paramArrayOfbyte == null || paramArrayOfbyte.length <= 0)
      return null; 
    for (int i = 0; i < paramArrayOfbyte.length; i++) {
      String str = Integer.toHexString(paramArrayOfbyte[i] & 0xFF);
      if (str.length() < 2)
        stringBuilder.append(0); 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str);
      stringBuilder1.append(" ");
      stringBuilder.append(stringBuilder1.toString());
    } 
    return stringBuilder.toString();
  }
  
  public static byte[] copyByte(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    if (paramArrayOfbyte == null)
      return null; 
    if (paramInt1 > paramArrayOfbyte.length)
      return null; 
    if (paramInt1 + paramInt2 > paramArrayOfbyte.length)
      return null; 
    if (paramInt1 < 0)
      return null; 
    if (paramInt2 <= 0)
      return null; 
    byte[] arrayOfByte = new byte[paramInt2];
    for (int i = 0; i < paramInt2; i++)
      arrayOfByte[i] = paramArrayOfbyte[i + paramInt1]; 
    return arrayOfByte;
  }
  
  public static String filterStringNull(String paramString) {
    String str = paramString;
    if (paramString != null) {
      if (paramString.length() == 0)
        return paramString; 
      byte[] arrayOfByte = paramString.getBytes();
      ArrayList<Byte> arrayList = new ArrayList();
      boolean bool = false;
      int i;
      for (i = 0; i < arrayOfByte.length; i++) {
        if (arrayOfByte[i] != 0)
          arrayList.add(Byte.valueOf(arrayOfByte[i])); 
      } 
      int j = arrayList.size();
      arrayOfByte = new byte[j];
      for (i = bool; i < j; i++)
        arrayOfByte[i] = ((Byte)arrayList.get(i)).byteValue(); 
      str = new String(arrayOfByte);
    } 
    return str;
  }
  
  public static char[] getChars(byte[] paramArrayOfbyte) {
    Charset charset = Charset.forName("UTF-8");
    ByteBuffer byteBuffer = ByteBuffer.allocate(paramArrayOfbyte.length);
    byteBuffer.put(paramArrayOfbyte);
    byteBuffer.flip();
    return charset.decode(byteBuffer).array();
  }
  
  public static String getStringFromByteAry(byte[] paramArrayOfbyte, int paramInt) {
    if (paramArrayOfbyte == null)
      return ""; 
    if (paramInt < 0)
      return ""; 
    if (paramInt >= paramArrayOfbyte.length)
      return ""; 
    byte b = paramArrayOfbyte[paramInt];
    ArrayList<Byte> arrayList = new ArrayList();
    for (int i = 1; b != 0; i++) {
      int j = paramInt + i;
      arrayList.add(Byte.valueOf(paramArrayOfbyte[j]));
      j = paramArrayOfbyte[j];
    } 
    paramArrayOfbyte = new byte[arrayList.size()];
    for (paramInt = 0; paramInt < arrayList.size(); paramInt++)
      paramArrayOfbyte[paramInt] = ((Byte)arrayList.get(paramInt)).byteValue(); 
    try {
      return new String(paramArrayOfbyte, "UTF-8");
    } catch (Exception exception) {
      PrintStream printStream = System.out;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("encode error:");
      stringBuilder.append(exception.toString());
      printStream.println(stringBuilder.toString());
      return "";
    } 
  }
  
  public static byte[] insertByte(byte[] paramArrayOfbyte1, int paramInt, byte[] paramArrayOfbyte2) {
    int i;
    if (paramArrayOfbyte1 == null)
      return null; 
    if (paramInt > paramArrayOfbyte1.length)
      return null; 
    byte[] arrayOfByte = new byte[paramArrayOfbyte1.length + paramArrayOfbyte2.length];
    byte b = 0;
    int j = 0;
    while (true) {
      i = b;
      if (j < paramInt) {
        arrayOfByte[j] = paramArrayOfbyte1[j];
        j++;
        continue;
      } 
      break;
    } 
    while (true) {
      j = paramInt;
      if (i < paramArrayOfbyte2.length) {
        arrayOfByte[i + paramInt] = paramArrayOfbyte2[i];
        i++;
        continue;
      } 
      break;
    } 
    while (j < paramArrayOfbyte1.length) {
      arrayOfByte[paramArrayOfbyte2.length + j] = paramArrayOfbyte1[j];
      j++;
    } 
    return arrayOfByte;
  }
  
  public static byte[] int2Byte(int paramInt) {
    byte b1 = (byte)(paramInt >> 24 & 0xFF);
    byte b2 = (byte)(paramInt >> 16 & 0xFF);
    byte b3 = (byte)(paramInt >> 8 & 0xFF);
    return new byte[] { (byte)(paramInt & 0xFF), b3, b2, b1 };
  }
  
  public static byte[] removeByte(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    int i;
    if (paramArrayOfbyte == null)
      return null; 
    if (paramInt1 > paramArrayOfbyte.length)
      return null; 
    int k = paramInt1 + paramInt2;
    if (k > paramArrayOfbyte.length)
      return null; 
    if (paramInt1 < 0)
      return null; 
    if (paramInt2 <= 0)
      return null; 
    byte[] arrayOfByte = new byte[paramArrayOfbyte.length - paramInt2];
    boolean bool = false;
    int j = 0;
    while (true) {
      paramInt2 = bool;
      i = k;
      if (j <= paramInt1) {
        arrayOfByte[j] = paramArrayOfbyte[j];
        j++;
        continue;
      } 
      break;
    } 
    while (i < paramArrayOfbyte.length) {
      arrayOfByte[paramInt1 + paramInt2] = paramArrayOfbyte[i];
      paramInt2++;
      i++;
    } 
    return arrayOfByte;
  }
  
  public static byte[] replaceBytes(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt) {
    if (paramArrayOfbyte1 == null)
      return null; 
    if (paramArrayOfbyte2 == null)
      return paramArrayOfbyte1; 
    if (paramInt > paramArrayOfbyte1.length)
      return paramArrayOfbyte1; 
    if (paramArrayOfbyte2.length + paramInt > paramArrayOfbyte1.length)
      return paramArrayOfbyte1; 
    byte[] arrayOfByte = new byte[paramArrayOfbyte2.length];
    for (int i = 0; i < paramArrayOfbyte2.length; i++) {
      int j = i + paramInt;
      arrayOfByte[i] = paramArrayOfbyte1[j];
      paramArrayOfbyte1[j] = paramArrayOfbyte2[i];
    } 
    return paramArrayOfbyte1;
  }
  
  public static byte[] reverseBytes(byte[] paramArrayOfbyte) {
    int k = paramArrayOfbyte.length;
    byte[] arrayOfByte = new byte[k];
    int j = 0;
    int i;
    for (i = 0; i < paramArrayOfbyte.length; i++)
      arrayOfByte[i] = paramArrayOfbyte[i]; 
    i = j;
    if (k % 2 != 0)
      return arrayOfByte; 
    while (i < k / 2) {
      byte b = arrayOfByte[i];
      j = k - i - 1;
      arrayOfByte[i] = arrayOfByte[j];
      arrayOfByte[j] = b;
      i++;
    } 
    return arrayOfByte;
  }
  
  public static byte[] shortToByte(short paramShort) {
    byte[] arrayOfByte = new byte[2];
    boolean bool = false;
    int i = paramShort;
    paramShort = bool;
    while (paramShort < 2) {
      arrayOfByte[paramShort] = (new Integer(i & 0xFF)).byteValue();
      i >>= 8;
      int j = paramShort + 1;
    } 
    return arrayOfByte;
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\main\Utils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */