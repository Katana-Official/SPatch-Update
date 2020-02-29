package com.sk.spatch.xmltool.decode;

import com.sk.spatch.xmltool.decode.extrafield.ZipExtraField;
import java.util.zip.ZipException;

public final class JarMarker implements ZipExtraField {
  private static final JarMarker DEFAULT;
  
  private static final ZipShort ID = new ZipShort(51966);
  
  private static final byte[] NO_BYTES;
  
  private static final ZipShort NULL = new ZipShort(0);
  
  static  {
    NO_BYTES = new byte[0];
    DEFAULT = new JarMarker();
  }
  
  public static JarMarker getInstance() { return DEFAULT; }
  
  public byte[] getCentralDirectoryData() { return NO_BYTES; }
  
  public ZipShort getCentralDirectoryLength() { return NULL; }
  
  public ZipShort getHeaderId() { return ID; }
  
  public byte[] getLocalFileDataData() { return NO_BYTES; }
  
  public ZipShort getLocalFileDataLength() { return NULL; }
  
  public void parseFromLocalFileData(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws ZipException {
    if (paramInt2 == 0)
      return; 
    throw new ZipException("JarMarker doesn't expect any data");
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\JarMarker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */