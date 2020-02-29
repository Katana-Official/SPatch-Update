package com.sk.spatch.xmltool.chunk;

import com.sk.spatch.xmltool.main.Utils;

public class EndNameSpaceChunk {
  public byte[] lineNumber = new byte[4];
  
  public byte[] prefix = new byte[4];
  
  public byte[] size = new byte[4];
  
  public byte[] type = new byte[4];
  
  public byte[] unknown = new byte[4];
  
  public byte[] uri = new byte[4];
  
  public static EndNameSpaceChunk createChunk(byte[] paramArrayOfbyte) {
    EndNameSpaceChunk endNameSpaceChunk = new EndNameSpaceChunk();
    endNameSpaceChunk.type = Utils.copyByte(paramArrayOfbyte, 0, 4);
    endNameSpaceChunk.size = Utils.copyByte(paramArrayOfbyte, 4, 4);
    endNameSpaceChunk.lineNumber = Utils.copyByte(paramArrayOfbyte, 8, 4);
    endNameSpaceChunk.unknown = Utils.copyByte(paramArrayOfbyte, 12, 4);
    endNameSpaceChunk.prefix = Utils.copyByte(paramArrayOfbyte, 16, 4);
    endNameSpaceChunk.uri = Utils.copyByte(paramArrayOfbyte, 20, 4);
    return endNameSpaceChunk;
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\chunk\EndNameSpaceChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */