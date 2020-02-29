package com.sk.spatch.xmltool.chunk;

import com.sk.spatch.xmltool.main.Utils;

public class StartNameSpaceChunk {
  public byte[] lineNumber = new byte[4];
  
  public byte[] prefix = new byte[4];
  
  public byte[] size = new byte[4];
  
  public byte[] type = new byte[4];
  
  public byte[] unknown = new byte[4];
  
  public byte[] uri = new byte[4];
  
  public static StartNameSpaceChunk createChunk(byte[] paramArrayOfbyte) {
    StartNameSpaceChunk startNameSpaceChunk = new StartNameSpaceChunk();
    startNameSpaceChunk.type = Utils.copyByte(paramArrayOfbyte, 0, 4);
    startNameSpaceChunk.size = Utils.copyByte(paramArrayOfbyte, 4, 4);
    startNameSpaceChunk.lineNumber = Utils.copyByte(paramArrayOfbyte, 8, 4);
    startNameSpaceChunk.unknown = Utils.copyByte(paramArrayOfbyte, 12, 4);
    startNameSpaceChunk.prefix = Utils.copyByte(paramArrayOfbyte, 16, 4);
    startNameSpaceChunk.uri = Utils.copyByte(paramArrayOfbyte, 20, 4);
    return startNameSpaceChunk;
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\chunk\StartNameSpaceChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */