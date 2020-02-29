package com.sk.spatch.xmltool.android.content.res;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public final class IntReader {
  private boolean m_bigEndian;
  
  private int m_position;
  
  private InputStream m_stream;
  
  public IntReader() {}
  
  public IntReader(InputStream paramInputStream, boolean paramBoolean) { reset(paramInputStream, paramBoolean); }
  
  public final int available() throws IOException { return this.m_stream.available(); }
  
  public final void close() {
    InputStream inputStream = this.m_stream;
    if (inputStream == null)
      return; 
    try {
      inputStream.close();
    } catch (IOException iOException) {}
    reset(null, false);
  }
  
  public final int getPosition() { return this.m_position; }
  
  public final InputStream getStream() { return this.m_stream; }
  
  public final boolean isBigEndian() { return this.m_bigEndian; }
  
  public final int readByte() throws IOException { return readInt(1); }
  
  public final byte[] readByteArray(int paramInt) throws IOException {
    byte[] arrayOfByte = new byte[paramInt];
    int i = this.m_stream.read(arrayOfByte);
    this.m_position += i;
    if (i == paramInt)
      return arrayOfByte; 
    throw new EOFException();
  }
  
  public final void readFully(byte[] paramArrayOfbyte) throws IOException { (new DataInputStream(this.m_stream)).readFully(paramArrayOfbyte); }
  
  public final int readInt() throws IOException { return readInt(4); }
  
  public final int readInt(int paramInt) throws IOException {
    if (paramInt >= 0 && paramInt <= 4) {
      boolean bool = this.m_bigEndian;
      int j = 0;
      int i = 0;
      if (bool) {
        int k = (paramInt - 1) * 8;
        paramInt = i;
        while (true) {
          i = paramInt;
          if (k >= 0) {
            i = this.m_stream.read();
            if (i != -1) {
              this.m_position++;
              paramInt |= i << k;
              k -= 8;
              continue;
            } 
            throw new EOFException();
          } 
          break;
        } 
      } else {
        int k = 0;
        i = j;
        while (i != paramInt * 8) {
          j = this.m_stream.read();
          if (j != -1) {
            this.m_position++;
            k |= j << i;
            i += 8;
            continue;
          } 
          throw new EOFException();
        } 
        i = k;
      } 
      return i;
    } 
    throw new IllegalArgumentException();
  }
  
  public final void readIntArray(int[] paramArrayOfint, int paramInt1, int paramInt2) throws IOException {
    while (paramInt2 > 0) {
      paramArrayOfint[paramInt1] = readInt();
      paramInt2--;
      paramInt1++;
    } 
  }
  
  public final int[] readIntArray(int paramInt) throws IOException {
    int[] arrayOfInt = new int[paramInt];
    readIntArray(arrayOfInt, 0, paramInt);
    return arrayOfInt;
  }
  
  public final int readShort() throws IOException { return readInt(2); }
  
  public final void reset(InputStream paramInputStream, boolean paramBoolean) {
    this.m_stream = paramInputStream;
    this.m_bigEndian = paramBoolean;
    this.m_position = 0;
  }
  
  public final void setBigEndian(boolean paramBoolean) { this.m_bigEndian = paramBoolean; }
  
  public final void skip(int paramInt) throws IOException {
    if (paramInt <= 0)
      return; 
    InputStream inputStream = this.m_stream;
    long l1 = paramInt;
    long l2 = inputStream.skip(l1);
    this.m_position = (int)(this.m_position + l2);
    if (l2 == l1)
      return; 
    throw new EOFException();
  }
  
  public final void skipInt() throws IOException { skip(4); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\android\content\res\IntReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */