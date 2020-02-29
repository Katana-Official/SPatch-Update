package com.sk.spatch.xmltool.decode.encoding;

import java.io.IOException;
import java.nio.ByteBuffer;

public class FallbackZipEncoding implements ZipEncoding {
  private final String charset = null;
  
  private final DetectEncoding de;
  
  public FallbackZipEncoding() { this.de = new DetectEncoding(); }
  
  public FallbackZipEncoding(String paramString) {
    if (paramString != null) {
      paramString = null;
    } else {
      detectEncoding = new DetectEncoding();
    } 
    this.de = detectEncoding;
  }
  
  public boolean canEncode(String paramString) { return true; }
  
  public String decode(byte[] paramArrayOfbyte) throws IOException {
    if (this.charset == null) {
      this.de.update(paramArrayOfbyte);
      return new String(paramArrayOfbyte, this.de.getEncode());
    } 
    return new String(paramArrayOfbyte, this.charset);
  }
  
  public ByteBuffer encode(String paramString) throws IOException {
    String str = this.charset;
    return (str == null) ? ByteBuffer.wrap(paramString.getBytes(this.de.getEncode())) : ByteBuffer.wrap(paramString.getBytes(str));
  }
  
  public String getEncoding() {
    String str2 = this.charset;
    String str1 = str2;
    if (str2 == null)
      str1 = this.de.getEncode().name(); 
    return str1;
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\encoding\FallbackZipEncoding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */