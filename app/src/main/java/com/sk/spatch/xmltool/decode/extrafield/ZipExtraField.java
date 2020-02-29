package com.sk.spatch.xmltool.decode.extrafield;

import com.sk.spatch.xmltool.decode.ZipShort;
import java.util.zip.ZipException;

public interface ZipExtraField {
  byte[] getCentralDirectoryData();
  
  ZipShort getCentralDirectoryLength();
  
  ZipShort getHeaderId();
  
  byte[] getLocalFileDataData();
  
  ZipShort getLocalFileDataLength();
  
  void parseFromLocalFileData(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws ZipException;
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\extrafield\ZipExtraField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */