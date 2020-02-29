package com.sk.spatch.xmltool.decode.extrafield;

import com.sk.spatch.xmltool.decode.ZipShort;

public class UnicodePathExtraField extends AbstractUnicodeExtraField {
  public static final ZipShort UPATH_ID = new ZipShort(28789);
  
  public UnicodePathExtraField() {}
  
  public UnicodePathExtraField(String paramString, byte[] paramArrayOfbyte) { super(paramString, paramArrayOfbyte); }
  
  public UnicodePathExtraField(String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) { super(paramString, paramArrayOfbyte, paramInt1, paramInt2); }
  
  public ZipShort getHeaderId() { return UPATH_ID; }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\extrafield\UnicodePathExtraField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */