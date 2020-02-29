package com.sk.spatch.xmltool.chunk;

import com.sk.spatch.xmltool.main.Utils;

public class TextChunk {
  public byte[] lineNumber = new byte[4];
  
  public byte[] name = new byte[4];
  
  public byte[] size = new byte[4];
  
  public byte[] type = new byte[4];
  
  public byte[] unknown = new byte[4];
  
  public byte[] unknown1 = new byte[4];
  
  public byte[] unknown2 = new byte[4];
  
  public static TextChunk createChunk(byte[] paramArrayOfbyte) {
    TextChunk textChunk = new TextChunk();
    textChunk.type = Utils.copyByte(paramArrayOfbyte, 0, 4);
    textChunk.size = Utils.copyByte(paramArrayOfbyte, 4, 4);
    textChunk.lineNumber = Utils.copyByte(paramArrayOfbyte, 8, 4);
    textChunk.unknown = Utils.copyByte(paramArrayOfbyte, 12, 4);
    textChunk.name = Utils.copyByte(paramArrayOfbyte, 16, 4);
    textChunk.unknown1 = Utils.copyByte(paramArrayOfbyte, 20, 4);
    textChunk.unknown2 = Utils.copyByte(paramArrayOfbyte, 24, 4);
    return textChunk;
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\chunk\TextChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */