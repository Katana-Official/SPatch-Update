package com.sk.spatch.xmltool.chunk;

import com.sk.spatch.xmltool.main.ParserChunkUtils;
import com.sk.spatch.xmltool.main.Utils;

public class AttributeData {
  public int data = 0;
  
  public byte[] dataB;
  
  public int name;
  
  public byte[] nameB;
  
  public int nameSpaceUri;
  
  public byte[] nameSpaceUriB;
  
  public int offset;
  
  public int type = 0;
  
  public byte[] typeB;
  
  public int valueString;
  
  public byte[] valueStringB;
  
  public static AttributeData createAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    AttributeData attributeData = new AttributeData();
    attributeData.nameSpaceUriB = Utils.int2Byte(paramInt1);
    attributeData.nameB = Utils.int2Byte(paramInt2);
    attributeData.valueStringB = Utils.int2Byte(paramInt3);
    attributeData.typeB = Utils.int2Byte(paramInt4);
    attributeData.dataB = Utils.int2Byte(paramInt5);
    return attributeData;
  }
  
  public static AttributeData createAttribute(byte[] paramArrayOfbyte) {
    AttributeData attributeData = new AttributeData();
    attributeData.nameSpaceUriB = Utils.copyByte(paramArrayOfbyte, 0, 4);
    attributeData.nameB = Utils.copyByte(paramArrayOfbyte, 4, 4);
    attributeData.valueStringB = Utils.copyByte(paramArrayOfbyte, 8, 4);
    attributeData.typeB = Utils.copyByte(paramArrayOfbyte, 12, 4);
    attributeData.dataB = Utils.copyByte(paramArrayOfbyte, 16, 4);
    return attributeData;
  }
  
  public byte[] getByte() {
    byte[] arrayOfByte = new byte[20];
    Utils.byteConcat(arrayOfByte, this.nameSpaceUriB, 0);
    Utils.byteConcat(arrayOfByte, this.nameB, 4);
    Utils.byteConcat(arrayOfByte, this.valueStringB, 8);
    Utils.byteConcat(arrayOfByte, this.typeB, 12);
    Utils.byteConcat(arrayOfByte, this.dataB, 16);
    return arrayOfByte;
  }
  
  public String getData() { return (this.data < 0) ? "" : ParserChunkUtils.xmlStruct.stringChunk.stringContentList.get(this.data); }
  
  public int getLen() { return 20; }
  
  public String getName() { return (this.name < 0) ? "" : ParserChunkUtils.xmlStruct.stringChunk.stringContentList.get(this.name); }
  
  public String getNameSpaceUri() { return (this.nameSpaceUri < 0) ? "" : ParserChunkUtils.xmlStruct.stringChunk.stringContentList.get(this.nameSpaceUri); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\chunk\AttributeData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */