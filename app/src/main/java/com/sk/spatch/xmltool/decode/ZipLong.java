package com.sk.spatch.xmltool.decode;

public final class ZipLong implements Cloneable {
  private static final int BYTE_1 = 1;
  
  private static final int BYTE_1_MASK = 65280;
  
  private static final int BYTE_1_SHIFT = 8;
  
  private static final int BYTE_2 = 2;
  
  private static final int BYTE_2_MASK = 16711680;
  
  private static final int BYTE_2_SHIFT = 16;
  
  private static final int BYTE_3 = 3;
  
  private static final long BYTE_3_MASK = 4278190080L;
  
  private static final int BYTE_3_SHIFT = 24;
  
  private static final int BYTE_MASK = 255;
  
  private static final int WORD = 4;
  
  private long value;
  
  public ZipLong(long paramLong) { this.value = paramLong; }
  
  public ZipLong(byte[] paramArrayOfbyte) { this(paramArrayOfbyte, 0); }
  
  public ZipLong(byte[] paramArrayOfbyte, int paramInt) { this.value = getValue(paramArrayOfbyte, paramInt); }
  
  public static byte[] getBytes(long paramLong) { return new byte[] { (byte)(int)(0xFFL & paramLong), (byte)(int)((0xFF00L & paramLong) >> 8), (byte)(int)((0xFF0000L & paramLong) >> 16), (byte)(int)((paramLong & 0xFF000000L) >> 24) }; }
  
  public static long getValue(byte[] paramArrayOfbyte) { return getValue(paramArrayOfbyte, 0); }
  
  public static long getValue(byte[] paramArrayOfbyte, int paramInt) { return ((paramArrayOfbyte[paramInt + 3] << 24) & 0xFF000000L) + (paramArrayOfbyte[paramInt + 2] << 16 & 0xFF0000) + (paramArrayOfbyte[paramInt + 1] << 8 & 0xFF00) + (paramArrayOfbyte[paramInt] & 0xFF); }
  
  public static void putLong(long paramLong, byte[] paramArrayOfbyte, int paramInt) {
    int i = paramInt + 1;
    paramArrayOfbyte[paramInt] = (byte)(int)(0xFFL & paramLong);
    paramInt = i + 1;
    paramArrayOfbyte[i] = (byte)(int)((0xFF00L & paramLong) >> 8);
    paramArrayOfbyte[paramInt] = (byte)(int)((0xFF0000L & paramLong) >> 16);
    paramArrayOfbyte[paramInt + 1] = (byte)(int)((paramLong & 0xFF000000L) >> 24);
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
      if (!(paramObject instanceof ZipLong))
        return false; 
      bool1 = bool2;
      if (this.value == ((ZipLong)paramObject).getValue())
        bool1 = true; 
    } 
    return bool1;
  }
  
  public byte[] getBytes() { return getBytes(this.value); }
  
  public long getValue() { return this.value; }
  
  public int hashCode() { return (int)this.value; }
  
  public void putLong(byte[] paramArrayOfbyte, int paramInt) { putLong(this.value, paramArrayOfbyte, paramInt); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\ZipLong.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */