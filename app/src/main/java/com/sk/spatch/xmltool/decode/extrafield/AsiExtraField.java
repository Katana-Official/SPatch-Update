package com.sk.spatch.xmltool.decode.extrafield;

import com.sk.spatch.xmltool.decode.UnixStat;
import com.sk.spatch.xmltool.decode.ZipLong;
import com.sk.spatch.xmltool.decode.ZipShort;
import java.util.zip.CRC32;
import java.util.zip.ZipException;

public class AsiExtraField implements ZipExtraField, UnixStat, Cloneable {
  private static final ZipShort HEADER_ID = new ZipShort(30062);
  
  private static final int WORD = 4;
  
  private CRC32 crc = new CRC32();
  
  private boolean dirFlag = false;
  
  private int gid = 0;
  
  private String link = "";
  
  private int mode = 0;
  
  private int uid = 0;
  
  public Object clone() {
    try {
      AsiExtraField asiExtraField = (AsiExtraField)super.clone();
      asiExtraField.crc = new CRC32();
      return asiExtraField;
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      throw new RuntimeException(cloneNotSupportedException);
    } 
  }
  
  public byte[] getCentralDirectoryData() { return getLocalFileDataData(); }
  
  public ZipShort getCentralDirectoryLength() { return getLocalFileDataLength(); }
  
  public int getGroupId() { return this.gid; }
  
  public ZipShort getHeaderId() { return HEADER_ID; }
  
  public String getLinkedFile() { return this.link; }
  
  public byte[] getLocalFileDataData() {
    int i = getLocalFileDataLength().getValue() - 4;
    byte[] arrayOfByte1 = new byte[i];
    System.arraycopy(ZipShort.getBytes(getMode()), 0, arrayOfByte1, 0, 2);
    byte[] arrayOfByte2 = getLinkedFile().getBytes();
    System.arraycopy(ZipLong.getBytes(arrayOfByte2.length), 0, arrayOfByte1, 2, 4);
    System.arraycopy(ZipShort.getBytes(getUserId()), 0, arrayOfByte1, 6, 2);
    System.arraycopy(ZipShort.getBytes(getGroupId()), 0, arrayOfByte1, 8, 2);
    System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 10, arrayOfByte2.length);
    this.crc.reset();
    this.crc.update(arrayOfByte1);
    long l = this.crc.getValue();
    arrayOfByte2 = new byte[i + 4];
    System.arraycopy(ZipLong.getBytes(l), 0, arrayOfByte2, 0, 4);
    System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 4, i);
    return arrayOfByte2;
  }
  
  public ZipShort getLocalFileDataLength() { return new ZipShort((getLinkedFile().getBytes()).length + 14); }
  
  public int getMode() { return this.mode; }
  
  protected int getMode(int paramInt) {
    char c;
    if (isLink()) {
      c = 'ꀀ';
    } else if (isDirectory()) {
      c = '䀀';
    } else {
      c = '耀';
    } 
    return paramInt & 0xFFF | c;
  }
  
  public int getUserId() { return this.uid; }
  
  public boolean isDirectory() { return (this.dirFlag && !isLink()); }
  
  public boolean isLink() { return (getLinkedFile().length() != 0); }
  
  public void parseFromLocalFileData(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws ZipException {
    long l1 = ZipLong.getValue(paramArrayOfbyte, paramInt1);
    paramInt2 -= 4;
    byte[] arrayOfByte = new byte[paramInt2];
    boolean bool = false;
    System.arraycopy(paramArrayOfbyte, paramInt1 + 4, arrayOfByte, 0, paramInt2);
    this.crc.reset();
    this.crc.update(arrayOfByte);
    long l2 = this.crc.getValue();
    if (l1 == l2) {
      paramInt1 = ZipShort.getValue(arrayOfByte, 0);
      paramInt2 = (int)ZipLong.getValue(arrayOfByte, 2);
      paramArrayOfbyte = new byte[paramInt2];
      this.uid = ZipShort.getValue(arrayOfByte, 6);
      this.gid = ZipShort.getValue(arrayOfByte, 8);
      if (paramInt2 == 0) {
        this.link = "";
      } else {
        System.arraycopy(arrayOfByte, 10, paramArrayOfbyte, 0, paramInt2);
        this.link = new String(paramArrayOfbyte);
      } 
      if ((paramInt1 & 0x4000) != 0)
        bool = true; 
      setDirectory(bool);
      setMode(paramInt1);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("bad CRC checksum ");
    stringBuilder.append(Long.toHexString(l1));
    stringBuilder.append(" instead of ");
    stringBuilder.append(Long.toHexString(l2));
    throw new ZipException(stringBuilder.toString());
  }
  
  public void setDirectory(boolean paramBoolean) {
    this.dirFlag = paramBoolean;
    this.mode = getMode(this.mode);
  }
  
  public void setGroupId(int paramInt) { this.gid = paramInt; }
  
  public void setLinkedFile(String paramString) {
    this.link = paramString;
    this.mode = getMode(this.mode);
  }
  
  public void setMode(int paramInt) { this.mode = getMode(paramInt); }
  
  public void setUserId(int paramInt) { this.uid = paramInt; }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\extrafield\AsiExtraField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */