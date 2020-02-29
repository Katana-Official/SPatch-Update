package com.sk.spatch.xmltool.decode.extrafield;

import java.util.zip.ZipException;

public interface CentralDirectoryParsingZipExtraField extends ZipExtraField {
  void parseFromCentralDirectoryData(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws ZipException;
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\extrafield\CentralDirectoryParsingZipExtraField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */