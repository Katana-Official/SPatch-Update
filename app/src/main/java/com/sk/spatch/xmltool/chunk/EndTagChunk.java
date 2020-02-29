package com.sk.spatch.xmltool.chunk;

import com.sk.spatch.xmltool.main.Utils;

public class EndTagChunk implements Chunk {
  public byte[] lineNumber = new byte[4];
  
  public byte[] name = new byte[4];
  
  public int offset;
  
  public byte[] size = new byte[4];
  
  public String tagValue;
  
  public byte[] type = new byte[4];
  
  public byte[] unknown = new byte[4];
  
  public byte[] uri = new byte[4];
  
  public EndTagChunk() {
    this.type = Utils.int2Byte(1048835);
    this.size = Utils.int2Byte(24);
    this.lineNumber = new byte[4];
    this.unknown = new byte[4];
    this.uri = Utils.int2Byte(-1);
  }
  
  public static EndTagChunk createChunk(int paramInt) {
    EndTagChunk endTagChunk = new EndTagChunk();
    endTagChunk.name = Utils.int2Byte(paramInt);
    return endTagChunk;
  }
  
  public static EndTagChunk createChunk(byte[] paramArrayOfbyte, int paramInt) {
    EndTagChunk endTagChunk = new EndTagChunk();
    endTagChunk.offset = paramInt;
    endTagChunk.type = Utils.copyByte(paramArrayOfbyte, 0, 4);
    endTagChunk.size = Utils.copyByte(paramArrayOfbyte, 4, 4);
    endTagChunk.lineNumber = Utils.copyByte(paramArrayOfbyte, 8, 4);
    endTagChunk.unknown = Utils.copyByte(paramArrayOfbyte, 12, 4);
    endTagChunk.uri = Utils.copyByte(paramArrayOfbyte, 16, 4);
    endTagChunk.name = Utils.copyByte(paramArrayOfbyte, 20, 4);
    return endTagChunk;
  }
  
  public byte[] getChunkByte() { return Utils.byteConcat(Utils.byteConcat(Utils.byteConcat(Utils.byteConcat(Utils.byteConcat(Utils.byteConcat(new byte[getLen()], this.type, 0), this.size, 4), this.lineNumber, 8), this.unknown, 12), this.uri, 16), this.name, 20); }
  
  public int getLen() { return this.type.length + this.size.length + this.lineNumber.length + this.unknown.length + this.uri.length + this.name.length; }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\chunk\EndTagChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */