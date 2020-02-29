package com.sk.spatch.xmltool.decode.encoding;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface ZipEncoding {
  boolean canEncode(String paramString);
  
  String decode(byte[] paramArrayOfbyte) throws IOException;
  
  ByteBuffer encode(String paramString) throws IOException;
  
  String getEncoding();
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\encoding\ZipEncoding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */