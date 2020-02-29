package com.sk.spatch.xmltool.decode;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class ZInput {
  protected final DataInputStream dis;
  
  private int size;
  
  protected final byte[] work;
  
  public ZInput(InputStream paramInputStream) throws IOException {
    this.dis = new DataInputStream(paramInputStream);
    this.work = new byte[8];
    this.size = 0;
  }
  
  public int available() throws IOException { return this.dis.available(); }
  
  public void close() throws IOException { this.dis.close(); }
  
  public int getOffset() throws IOException { return this.size; }
  
  public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    paramInt1 = this.dis.read(paramArrayOfbyte, paramInt1, paramInt2);
    this.size += paramInt1;
    return paramInt1;
  }
  
  public final boolean readBoolean() throws IOException {
    this.size++;
    return this.dis.readBoolean();
  }
  
  public final byte readByte() throws IOException {
    this.size++;
    return this.dis.readByte();
  }
  
  public final char readChar() throws IOException {
    this.dis.readFully(this.work, 0, 2);
    this.size += 2;
    byte[] arrayOfByte = this.work;
    byte b = arrayOfByte[1];
    return (char)(arrayOfByte[0] & 0xFF | (b & 0xFF) << 8);
  }
  
  public final double readDouble() throws IOException { return Double.longBitsToDouble(readLong()); }
  
  public final float readFloat() throws IOException { return Float.intBitsToFloat(readInt()); }
  
  public final void readFully(byte[] paramArrayOfbyte) throws IOException {
    this.dis.readFully(paramArrayOfbyte, 0, paramArrayOfbyte.length);
    this.size += paramArrayOfbyte.length;
  }
  
  public final void readFully(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    this.dis.readFully(paramArrayOfbyte, paramInt1, paramInt2);
    this.size += paramInt2;
  }
  
  public final int readInt() throws IOException {
    this.dis.readFully(this.work, 0, 4);
    this.size += 4;
    byte[] arrayOfByte = this.work;
    byte b1 = arrayOfByte[3];
    byte b2 = arrayOfByte[2];
    byte b3 = arrayOfByte[1];
    return arrayOfByte[0] & 0xFF | b1 << 24 | (b2 & 0xFF) << 16 | (b3 & 0xFF) << 8;
  }
  
  public int[] readIntArray(int paramInt) throws IOException {
    int[] arrayOfInt = new int[paramInt];
    for (int i = 0; i < paramInt; i++)
      arrayOfInt[i] = readInt(); 
    return arrayOfInt;
  }
  
  public final long readLong() throws IOException {
    this.dis.readFully(this.work, 0, 8);
    this.size += 8;
    byte[] arrayOfByte = this.work;
    long l1 = arrayOfByte[7];
    long l2 = arrayOfByte[6];
    long l3 = arrayOfByte[5];
    long l4 = arrayOfByte[4];
    long l5 = arrayOfByte[3];
    long l6 = arrayOfByte[2];
    long l7 = arrayOfByte[1];
    return arrayOfByte[0] & 0xFFL | l1 << 56 | (l2 & 0xFFL) << 48 | (l3 & 0xFFL) << 40 | (l4 & 0xFFL) << 32 | (l5 & 0xFFL) << 24 | (l6 & 0xFFL) << 16 | (l7 & 0xFFL) << 8;
  }
  
  public String readNullEndedString(int paramInt, boolean paramBoolean) throws IOException {
    int i;
    StringBuilder stringBuilder = new StringBuilder(16);
    while (true) {
      i = paramInt - 1;
      if (paramInt != 0) {
        paramInt = readShort();
        if (paramInt == 0)
          break; 
        stringBuilder.append((char)paramInt);
        paramInt = i;
        continue;
      } 
      break;
    } 
    if (paramBoolean)
      skipBytes(i * 2); 
    return stringBuilder.toString();
  }
  
  public final short readShort() throws IOException {
    this.dis.readFully(this.work, 0, 2);
    this.size += 2;
    byte[] arrayOfByte = this.work;
    byte b = arrayOfByte[1];
    return (short)(arrayOfByte[0] & 0xFF | (b & 0xFF) << 8);
  }
  
  public final int readUnsignedShort() throws IOException {
    this.dis.readFully(this.work, 0, 2);
    this.size += 2;
    byte[] arrayOfByte = this.work;
    byte b = arrayOfByte[1];
    return arrayOfByte[0] & 0xFF | (b & 0xFF) << 8;
  }
  
  public void skipByOffset(int paramInt) throws IOException {
    paramInt -= getOffset();
    if (paramInt > 0)
      skipBytes(paramInt); 
  }
  
  public final int skipBytes(int paramInt) throws IOException {
    this.size += paramInt;
    return this.dis.skipBytes(paramInt);
  }
  
  public void skipCheckByte(byte paramByte) throws IOException {
    byte b = readByte();
    if (b == paramByte)
      return; 
    throw new IOException(String.format("Expected: 0x%08x, got: 0x%08x", new Object[] { Byte.valueOf(paramByte), Byte.valueOf(b) }));
  }
  
  public void skipCheckChunkTypeInt(int paramInt1, int paramInt2) throws IOException {
    int i = readInt();
    if (i == paramInt2) {
      skipCheckChunkTypeInt(paramInt1, -1);
      return;
    } 
    if (i == paramInt1)
      return; 
    throw new IOException(String.format("Expected: 0x%08x, got: 0x%08x", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(i) }));
  }
  
  public void skipCheckInt(int paramInt) throws IOException {
    int i = readInt();
    if (i == paramInt)
      return; 
    throw new IOException(String.format("Expected: 0x%08x, got: 0x%08x", new Object[] { Integer.valueOf(paramInt), Integer.valueOf(i) }));
  }
  
  public void skipCheckShort(short paramShort) throws IOException {
    short s = readShort();
    if (s == paramShort)
      return; 
    throw new IOException(String.format("Expected: 0x%08x, got: 0x%08x", new Object[] { Short.valueOf(paramShort), Short.valueOf(s) }));
  }
  
  public void skipInt() throws IOException { skipBytes(4); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\ZInput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */