package com.sk.spatch.xmltool.decode.extrafield;

import com.sk.spatch.xmltool.decode.ZipShort;
import com.sk.spatch.xmltool.decode.ZipUtil;

public final class UnparseableExtraFieldData implements CentralDirectoryParsingZipExtraField {
  private static final ZipShort HEADER_ID = new ZipShort(44225);
  
  private byte[] centralDirectoryData;
  
  private byte[] localFileData;
  
  public byte[] getCentralDirectoryData() {
    byte[] arrayOfByte = this.centralDirectoryData;
    return (arrayOfByte == null) ? getLocalFileDataData() : ZipUtil.copy(arrayOfByte);
  }
  
  public ZipShort getCentralDirectoryLength() {
    byte[] arrayOfByte = this.centralDirectoryData;
    return (arrayOfByte == null) ? getLocalFileDataLength() : new ZipShort(arrayOfByte.length);
  }
  
  public ZipShort getHeaderId() { return HEADER_ID; }
  
  public byte[] getLocalFileDataData() { return ZipUtil.copy(this.localFileData); }
  
  public ZipShort getLocalFileDataLength() {
    int i;
    byte[] arrayOfByte = this.localFileData;
    if (arrayOfByte == null) {
      i = 0;
    } else {
      i = arrayOfByte.length;
    } 
    return new ZipShort(i);
  }
  
  public void parseFromCentralDirectoryData(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    byte[] arrayOfByte = new byte[paramInt2];
    this.centralDirectoryData = arrayOfByte;
    System.arraycopy(paramArrayOfbyte, paramInt1, arrayOfByte, 0, paramInt2);
    if (this.localFileData == null)
      parseFromLocalFileData(paramArrayOfbyte, paramInt1, paramInt2); 
  }
  
  public void parseFromLocalFileData(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    byte[] arrayOfByte = new byte[paramInt2];
    this.localFileData = arrayOfByte;
    System.arraycopy(paramArrayOfbyte, paramInt1, arrayOfByte, 0, paramInt2);
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\extrafield\UnparseableExtraFieldData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */