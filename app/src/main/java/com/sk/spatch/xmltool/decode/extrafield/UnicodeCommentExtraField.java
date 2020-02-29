package com.sk.spatch.xmltool.decode.extrafield;

import com.sk.spatch.xmltool.decode.ZipShort;

public class UnicodeCommentExtraField extends AbstractUnicodeExtraField {
  public static final ZipShort UCOM_ID = new ZipShort(25461);
  
  public UnicodeCommentExtraField() {}
  
  public UnicodeCommentExtraField(String paramString, byte[] paramArrayOfbyte) { super(paramString, paramArrayOfbyte); }
  
  public UnicodeCommentExtraField(String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) { super(paramString, paramArrayOfbyte, paramInt1, paramInt2); }
  
  public ZipShort getHeaderId() { return UCOM_ID; }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\extrafield\UnicodeCommentExtraField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */