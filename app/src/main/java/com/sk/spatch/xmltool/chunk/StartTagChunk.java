package com.sk.spatch.xmltool.chunk;

import com.sk.spatch.xmltool.main.Utils;
import java.util.ArrayList;

public class StartTagChunk implements Chunk {
  public byte[] attCount;
  
  public ArrayList<AttributeData> attrList;
  
  public byte[] attribute;
  
  public byte[] classAttr = new byte[4];
  
  public byte[] flag = new byte[4];
  
  public byte[] lineNumber = new byte[4];
  
  public byte[] name;
  
  public int offset;
  
  public byte[] size;
  
  public byte[] type = Utils.int2Byte(1048834);
  
  public byte[] unknown = new byte[4];
  
  public byte[] uri;
  
  public StartTagChunk() { this.flag = Utils.int2Byte(1310740); }
  
  public static StartTagChunk createChunk(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfbyte) {
    StartTagChunk startTagChunk = new StartTagChunk();
    startTagChunk.size = new byte[4];
    startTagChunk.name = Utils.int2Byte(paramInt1);
    startTagChunk.uri = Utils.int2Byte(paramInt3);
    startTagChunk.attCount = Utils.int2Byte(paramInt2);
    startTagChunk.attribute = paramArrayOfbyte;
    startTagChunk.size = Utils.int2Byte(startTagChunk.getLen());
    return startTagChunk;
  }
  
  public static StartTagChunk createChunk(byte[] paramArrayOfbyte, int paramInt) {
    StartTagChunk startTagChunk = new StartTagChunk();
    startTagChunk.offset = paramInt;
    startTagChunk.type = Utils.copyByte(paramArrayOfbyte, 0, 4);
    startTagChunk.size = Utils.copyByte(paramArrayOfbyte, 4, 4);
    startTagChunk.lineNumber = Utils.copyByte(paramArrayOfbyte, 8, 4);
    startTagChunk.unknown = Utils.copyByte(paramArrayOfbyte, 12, 4);
    startTagChunk.uri = Utils.copyByte(paramArrayOfbyte, 16, 4);
    startTagChunk.name = Utils.copyByte(paramArrayOfbyte, 20, 4);
    startTagChunk.flag = Utils.copyByte(paramArrayOfbyte, 24, 4);
    byte[] arrayOfByte = Utils.copyByte(paramArrayOfbyte, 28, 4);
    startTagChunk.attCount = arrayOfByte;
    int j = Utils.byte2int(arrayOfByte);
    startTagChunk.classAttr = Utils.copyByte(paramArrayOfbyte, 32, 4);
    startTagChunk.attribute = Utils.copyByte(paramArrayOfbyte, 36, j * 20);
    startTagChunk.attrList = new ArrayList<AttributeData>(j);
    for (int i = 0; i < j; i++) {
      Integer[] arrayOfInteger = new Integer[5];
      AttributeData attributeData = new AttributeData();
      for (int k = 0; k < 5; k++) {
        int n = i * 20;
        int m = Utils.byte2int(Utils.copyByte(paramArrayOfbyte, n + 36 + k * 4, 4));
        attributeData.offset = paramInt + 36 + n;
        if (k != 0) {
          if (k != 1) {
            if (k != 2) {
              if (k != 3) {
                if (k == 4)
                  attributeData.data = m; 
              } else {
                m >>= 24;
                attributeData.type = m;
              } 
            } else {
              attributeData.valueString = m;
            } 
          } else {
            attributeData.name = m;
          } 
        } else {
          attributeData.nameSpaceUri = m;
        } 
        arrayOfInteger[k] = Integer.valueOf(m);
      } 
      startTagChunk.attrList.add(attributeData);
    } 
    return startTagChunk;
  }
  
  public byte[] getChunkByte() { return Utils.byteConcat(Utils.byteConcat(Utils.byteConcat(Utils.byteConcat(Utils.byteConcat(Utils.byteConcat(Utils.byteConcat(Utils.byteConcat(Utils.byteConcat(Utils.byteConcat(new byte[getLen()], this.type, 0), this.size, 4), this.lineNumber, 8), this.unknown, 12), this.uri, 16), this.name, 20), this.flag, 24), this.attCount, 28), this.classAttr, 32), this.attribute, 36); }
  
  public int getLen() { return this.type.length + this.size.length + this.lineNumber.length + this.unknown.length + this.uri.length + this.name.length + this.flag.length + this.attCount.length + this.classAttr.length + this.attribute.length; }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\chunk\StartTagChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */