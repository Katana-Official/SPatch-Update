package com.sk.spatch.xmltool.decode.extrafield;

import com.sk.spatch.xmltool.decode.ZipShort;
import com.sk.spatch.xmltool.decode.ZipUtil;

public class UnrecognizedExtraField implements CentralDirectoryParsingZipExtraField {
  private byte[] centralData;
  
  private ZipShort headerId;
  
  private byte[] localData;
  
  public byte[] getCentralDirectoryData() {
    byte[] arrayOfByte = this.centralData;
    return (arrayOfByte != null) ? ZipUtil.copy(arrayOfByte) : getLocalFileDataData();
  }
  
  public ZipShort getCentralDirectoryLength() {
    byte[] arrayOfByte = this.centralData;
    return (arrayOfByte != null) ? new ZipShort(arrayOfByte.length) : getLocalFileDataLength();
  }
  
  public ZipShort getHeaderId() { return this.headerId; }
  
  public byte[] getLocalFileDataData() { return ZipUtil.copy(this.localData); }
  
  public ZipShort getLocalFileDataLength() { return new ZipShort(this.localData.length); }
  
  public void parseFromCentralDirectoryData(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    byte[] arrayOfByte = new byte[paramInt2];
    System.arraycopy(paramArrayOfbyte, paramInt1, arrayOfByte, 0, paramInt2);
    setCentralDirectoryData(arrayOfByte);
    if (this.localData == null)
      setLocalFileDataData(arrayOfByte); 
  }
  
  public void parseFromLocalFileData(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    byte[] arrayOfByte = new byte[paramInt2];
    System.arraycopy(paramArrayOfbyte, paramInt1, arrayOfByte, 0, paramInt2);
    setLocalFileDataData(arrayOfByte);
  }
  
  public void setCentralDirectoryData(byte[] paramArrayOfbyte) { this.centralData = ZipUtil.copy(paramArrayOfbyte); }
  
  public void setHeaderId(ZipShort paramZipShort) { this.headerId = paramZipShort; }
  
  public void setLocalFileDataData(byte[] paramArrayOfbyte) { this.localData = ZipUtil.copy(paramArrayOfbyte); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\extrafield\UnrecognizedExtraField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */