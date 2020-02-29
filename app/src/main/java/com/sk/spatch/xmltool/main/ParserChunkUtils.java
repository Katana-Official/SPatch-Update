package com.sk.spatch.xmltool.main;

import com.sk.spatch.xmltool.chunk.EndNameSpaceChunk;
import com.sk.spatch.xmltool.chunk.EndTagChunk;
import com.sk.spatch.xmltool.chunk.ResourceChunk;
import com.sk.spatch.xmltool.chunk.StartNameSpaceChunk;
import com.sk.spatch.xmltool.chunk.StartTagChunk;
import com.sk.spatch.xmltool.chunk.StringChunk;
import com.sk.spatch.xmltool.chunk.TagChunk;
import com.sk.spatch.xmltool.chunk.TextChunk;
import com.sk.spatch.xmltool.chunk.XmlStruct;
import java.util.ArrayList;
import java.util.List;

public class ParserChunkUtils {
  public static boolean isApplication = false;
  
  public static boolean isManifest = false;
  
  public static int nextChunkOffset = 0;
  
  public static int resourceChunkOffset = 0;
  
  public static int stringChunkOffset = 8;
  
  public static List<TagChunk> tagChunkList;
  
  public static XmlStruct xmlStruct = new XmlStruct();
  
  static  {
    isApplication = false;
    isManifest = false;
    tagChunkList = new ArrayList<TagChunk>();
  }
  
  public static void clear() {
    resourceChunkOffset = 0;
    nextChunkOffset = 0;
    isApplication = false;
    isManifest = false;
    tagChunkList.clear();
    xmlStruct.clear();
  }
  
  public static boolean isEnd(int paramInt) { return (nextChunkOffset >= paramInt); }
  
  public static void parserEndNamespaceChunk(byte[] paramArrayOfbyte) { xmlStruct.endNamespaceChunk = EndNameSpaceChunk.createChunk(paramArrayOfbyte); }
  
  public static void parserEndTagChunk(byte[] paramArrayOfbyte, int paramInt) {
    EndTagChunk endTagChunk = EndTagChunk.createChunk(paramArrayOfbyte, paramInt);
    List<TagChunk> list = tagChunkList;
    TagChunk tagChunk = list.remove(list.size() - 1);
    tagChunk.endTagChunk = endTagChunk;
    xmlStruct.endTagChunkList.add(endTagChunk);
    xmlStruct.tagChunkList.add(tagChunk);
  }
  
  public static void parserResourceChunk(byte[] paramArrayOfbyte) {
    xmlStruct.resChunk = ResourceChunk.createChunk(paramArrayOfbyte, resourceChunkOffset);
    int i = Utils.byte2int(Utils.copyByte(paramArrayOfbyte, resourceChunkOffset + 4, 4));
    i = resourceChunkOffset + i;
    nextChunkOffset = i;
    XmlEditor.tagStartChunkOffset = i;
  }
  
  public static void parserStartNamespaceChunk(byte[] paramArrayOfbyte) { xmlStruct.startNamespaceChunk = StartNameSpaceChunk.createChunk(paramArrayOfbyte); }
  
  public static void parserStartTagChunk(byte[] paramArrayOfbyte, int paramInt) {
    StartTagChunk startTagChunk = StartTagChunk.createChunk(paramArrayOfbyte, paramInt);
    xmlStruct.startTagChunkList.add(startTagChunk);
    TagChunk tagChunk = new TagChunk();
    tagChunk.startTagChunk = startTagChunk;
    tagChunkList.add(tagChunk);
    paramInt = Utils.byte2int(Utils.copyByte(paramArrayOfbyte, 20, 4));
    if (((String)xmlStruct.stringChunk.stringContentList.get(paramInt)).equals("application"))
      isApplication = true; 
  }
  
  public static void parserStringChunk(byte[] paramArrayOfbyte) {
    xmlStruct.stringChunk = StringChunk.createChunk(paramArrayOfbyte, stringChunkOffset);
    paramArrayOfbyte = Utils.copyByte(paramArrayOfbyte, 12, 4);
    resourceChunkOffset = stringChunkOffset + Utils.byte2int(paramArrayOfbyte);
  }
  
  public static void parserTextChunk(byte[] paramArrayOfbyte) { xmlStruct.textChunkList.add(TextChunk.createChunk(paramArrayOfbyte)); }
  
  public static void parserXml() {
    clear();
    parserXmlHeader(xmlStruct.byteSrc);
    parserStringChunk(xmlStruct.byteSrc);
    parserResourceChunk(xmlStruct.byteSrc);
    parserXmlContent(xmlStruct.byteSrc);
  }
  
  public static void parserXmlContent(byte[] paramArrayOfbyte) {
    while (!isEnd(paramArrayOfbyte.length)) {
      byte[] arrayOfByte1 = Utils.copyByte(paramArrayOfbyte, nextChunkOffset, 4);
      byte[] arrayOfByte2 = Utils.copyByte(paramArrayOfbyte, nextChunkOffset + 4, 4);
      int i = Utils.byte2int(arrayOfByte1);
      int j = Utils.byte2int(arrayOfByte2);
      switch (i) {
        case 1048835:
          parserEndTagChunk(Utils.copyByte(paramArrayOfbyte, nextChunkOffset, j), nextChunkOffset);
          break;
        case 1048834:
          parserStartTagChunk(Utils.copyByte(paramArrayOfbyte, nextChunkOffset, j), nextChunkOffset);
          if (isApplication) {
            XmlEditor.subAppTagChunkOffset = nextChunkOffset + j;
            isApplication = false;
          } 
          if (isManifest) {
            XmlEditor.subTagChunkOffsets = nextChunkOffset + j;
            isManifest = false;
          } 
          break;
        case 1048833:
          parserEndNamespaceChunk(Utils.copyByte(paramArrayOfbyte, nextChunkOffset, j));
          break;
        case 1048832:
          parserStartNamespaceChunk(Utils.copyByte(paramArrayOfbyte, nextChunkOffset, j));
          isManifest = true;
          break;
      } 
      nextChunkOffset += j;
    } 
  }
  
  public static void parserXmlHeader(byte[] paramArrayOfbyte) {
    byte[] arrayOfByte = Utils.copyByte(paramArrayOfbyte, 0, 4);
    paramArrayOfbyte = Utils.copyByte(paramArrayOfbyte, 4, 4);
    xmlStruct.magicNumber = arrayOfByte;
    xmlStruct.fileSize = paramArrayOfbyte;
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\main\ParserChunkUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */