package com.sk.spatch.xmltool.decode.extrafield;

import com.sk.spatch.xmltool.decode.ZipLong;
import com.sk.spatch.xmltool.decode.ZipShort;
import java.io.UnsupportedEncodingException;
import java.util.zip.CRC32;
import java.util.zip.ZipException;

public abstract class AbstractUnicodeExtraField implements ZipExtraField {
  private byte[] data;
  
  private long nameCRC32;
  
  private byte[] unicodeName;
  
  protected AbstractUnicodeExtraField() {}
  
  protected AbstractUnicodeExtraField(String paramString, byte[] paramArrayOfbyte) { this(paramString, paramArrayOfbyte, 0, paramArrayOfbyte.length); }
  
  protected AbstractUnicodeExtraField(String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    CRC32 cRC32 = new CRC32();
    cRC32.update(paramArrayOfbyte, paramInt1, paramInt2);
    this.nameCRC32 = cRC32.getValue();
    try {
      this.unicodeName = paramString.getBytes("UTF-8");
      return;
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      throw new RuntimeException("FATAL: UTF-8 encoding not supported.", unsupportedEncodingException);
    } 
  }
  
  private void assembleData() {
    byte[] arrayOfByte = this.unicodeName;
    if (arrayOfByte == null)
      return; 
    arrayOfByte = new byte[arrayOfByte.length + 5];
    this.data = arrayOfByte;
    arrayOfByte[0] = 1;
    System.arraycopy(ZipLong.getBytes(this.nameCRC32), 0, this.data, 1, 4);
    arrayOfByte = this.unicodeName;
    System.arraycopy(arrayOfByte, 0, this.data, 5, arrayOfByte.length);
  }
  
  public byte[] getCentralDirectoryData() {
    if (this.data == null)
      assembleData(); 
    return this.data;
  }
  
  public ZipShort getCentralDirectoryLength() {
    if (this.data == null)
      assembleData(); 
    return new ZipShort(this.data.length);
  }
  
  public byte[] getLocalFileDataData() { return getCentralDirectoryData(); }
  
  public ZipShort getLocalFileDataLength() { return getCentralDirectoryLength(); }
  
  public long getNameCRC32() { return this.nameCRC32; }
  
  public byte[] getUnicodeName() { return this.unicodeName; }
  
  public void parseFromLocalFileData(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws ZipException {
    if (paramInt2 >= 5) {
      byte b = paramArrayOfbyte[paramInt1];
      if (b == 1) {
        this.nameCRC32 = ZipLong.getValue(paramArrayOfbyte, paramInt1 + 1);
        paramInt2 -= 5;
        byte[] arrayOfByte = new byte[paramInt2];
        this.unicodeName = arrayOfByte;
        System.arraycopy(paramArrayOfbyte, paramInt1 + 5, arrayOfByte, 0, paramInt2);
        this.data = null;
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unsupported version [");
      stringBuilder.append(b);
      stringBuilder.append("] for UniCode path extra data.");
      throw new ZipException(stringBuilder.toString());
    } 
    throw new ZipException("UniCode path extra data must have at least 5 bytes.");
  }
  
  public void setNameCRC32(long paramLong) {
    this.nameCRC32 = paramLong;
    this.data = null;
  }
  
  public void setUnicodeName(byte[] paramArrayOfbyte) {
    this.unicodeName = paramArrayOfbyte;
    this.data = null;
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\extrafield\AbstractUnicodeExtraField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */