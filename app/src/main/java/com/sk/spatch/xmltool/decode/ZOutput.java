package com.sk.spatch.xmltool.decode;

import java.io.IOException;
import java.io.OutputStream;

public final class ZOutput {
  private OutputStream dos;
  
  private int written = 0;
  
  public ZOutput(OutputStream paramOutputStream) { this.dos = paramOutputStream; }
  
  public void close() throws IOException { this.dos.close(); }
  
  public int size() { return this.written; }
  
  public final void write(int paramInt) throws IOException {
    this.dos.write(paramInt);
    this.written++;
  }
  
  public final void writeByte(int paramInt) throws IOException {
    this.dos.write(paramInt);
    this.written++;
  }
  
  public final void writeChar(char paramChar) throws IOException {
    this.dos.write(paramChar & 0xFF);
    this.written++;
    this.dos.write(paramChar >>> 8 & 0xFF);
    this.written++;
  }
  
  public final void writeCharArray(char[] paramArrayOfchar) throws IOException {
    int j = paramArrayOfchar.length;
    for (int i = 0; i < j; i++)
      writeChar(paramArrayOfchar[i]); 
  }
  
  public final void writeFully(byte[] paramArrayOfbyte) throws IOException {
    this.dos.write(paramArrayOfbyte, 0, paramArrayOfbyte.length);
    this.written += paramArrayOfbyte.length;
  }
  
  public final void writeFully(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    this.dos.write(paramArrayOfbyte, paramInt1, paramInt2);
    this.written += paramInt2;
  }
  
  public final void writeInt(int paramInt) throws IOException {
    this.dos.write(paramInt & 0xFF);
    this.written++;
    this.dos.write(paramInt >>> 8 & 0xFF);
    this.written++;
    this.dos.write(paramInt >>> 16 & 0xFF);
    this.written++;
    this.dos.write(paramInt >>> 24 & 0xFF);
    this.written++;
  }
  
  public final void writeIntArray(int[] paramArrayOfint) throws IOException { writeIntArray(paramArrayOfint, 0, paramArrayOfint.length); }
  
  public final void writeIntArray(int[] paramArrayOfint, int paramInt1, int paramInt2) throws IOException {
    while (paramInt1 < paramInt2) {
      writeInt(paramArrayOfint[paramInt1]);
      paramInt1++;
    } 
  }
  
  public final void writeNulEndedString(String paramString, int paramInt, boolean paramBoolean) throws IOException {
    char[] arrayOfChar = paramString.toCharArray();
    int i;
    for (i = 0; i < arrayOfChar.length && paramInt != 0; i++) {
      writeChar(arrayOfChar[i]);
      paramInt--;
    } 
    if (paramBoolean)
      for (i = 0; i < paramInt * 2; i++) {
        this.dos.write(0);
        this.written++;
      }  
  }
  
  public final void writeShort(short paramShort) throws IOException {
    this.dos.write(paramShort & 0xFF);
    this.written++;
    this.dos.write(paramShort >>> 8 & 0xFF);
    this.written++;
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\ZOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */