package com.sk.spatch.xmltool.decode;

public final class ZipShort implements Cloneable {
  private static final int BYTE_1_MASK = 65280;
  
  private static final int BYTE_1_SHIFT = 8;
  
  private static final int BYTE_MASK = 255;
  
  private int value;
  
  public ZipShort(int paramInt) { this.value = paramInt; }
  
  public ZipShort(byte[] paramArrayOfbyte) { this(paramArrayOfbyte, 0); }
  
  public ZipShort(byte[] paramArrayOfbyte, int paramInt) { this.value = getValue(paramArrayOfbyte, paramInt); }
  
  public static byte[] getBytes(int paramInt) { return new byte[] { (byte)(paramInt & 0xFF), (byte)((paramInt & 0xFF00) >> 8) }; }
  
  public static int getValue(byte[] paramArrayOfbyte) { return getValue(paramArrayOfbyte, 0); }
  
  public static int getValue(byte[] paramArrayOfbyte, int paramInt) { return (paramArrayOfbyte[paramInt + 1] << 8 & 0xFF00) + (paramArrayOfbyte[paramInt] & 0xFF); }
  
  public static void putShort(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) {
    paramArrayOfbyte[paramInt2] = (byte)(paramInt1 & 0xFF);
    paramArrayOfbyte[paramInt2 + 1] = (byte)((paramInt1 & 0xFF00) >> 8);
  }
  
  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      throw new RuntimeException(cloneNotSupportedException);
    } 
  }
  
  public boolean equals(Object paramObject) {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramObject != null) {
      if (!(paramObject instanceof ZipShort))
        return false; 
      bool1 = bool2;
      if (this.value == ((ZipShort)paramObject).getValue())
        bool1 = true; 
    } 
    return bool1;
  }
  
  public byte[] getBytes() {
    int i = this.value;
    return new byte[] { (byte)(i & 0xFF), (byte)((i & 0xFF00) >> 8) };
  }
  
  public int getValue() { return this.value; }
  
  public int hashCode() { return this.value; }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\ZipShort.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */