package com.sk.spatch.xmltool.main;

import com.sk.spatch.xmltool.chunk.AttributeData;
import com.sk.spatch.xmltool.chunk.EndTagChunk;
import com.sk.spatch.xmltool.chunk.StartTagChunk;
import com.sk.spatch.xmltool.chunk.StringChunk;
import com.sk.spatch.xmltool.chunk.TagChunk;
import java.io.FileInputStream;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class XmlEditor {
  public static String[] isNotAppTag = new String[] { 
      "uses-permission", "uses-sdk", "compatible-screens", "instrumentation", "library", "original-package", "package-verifier", "permission", "permission-group", "permission-tree", 
      "protected-broadcast", "resource-overlay", "supports-input", "supports-screens", "upgrade-key-set", "uses-configuration", "uses-feature" };
  
  public static String prefixStr = "http://schemas.android.com/apk/res/android";
  
  public static int subAppTagChunkOffset;
  
  public static int subTagChunkOffsets;
  
  public static int tagEndChunkOffset;
  
  public static int tagStartChunkOffset;
  
  public static void addAttr(String paramString1, String paramString2, String paramString3, String paramString4) {
    ParserChunkUtils.parserXml();
    int[] arrayOfInt = getAttrType(paramString4);
    int i = getStrIndex(paramString3);
    int j = getStrIndex(paramString4);
    AttributeData attributeData = AttributeData.createAttribute(getStrIndex(prefixStr), i, j, arrayOfInt[0], arrayOfInt[1]);
    for (StartTagChunk startTagChunk : ParserChunkUtils.xmlStruct.startTagChunkList) {
      i = Utils.byte2int(startTagChunk.name);
      if (paramString1.equals(ParserChunkUtils.xmlStruct.stringChunk.stringContentList.get(i))) {
        if (paramString1.equals("application") || paramString1.equals("manifest")) {
          i = startTagChunk.offset;
          byte[] arrayOfByte = Utils.int2Byte(startTagChunk.attrList.size() + 1);
          ParserChunkUtils.xmlStruct.byteSrc = Utils.replaceBytes(ParserChunkUtils.xmlStruct.byteSrc, arrayOfByte, i + 28);
          i = startTagChunk.offset;
          j = Utils.byte2int(startTagChunk.size);
          arrayOfByte = Utils.int2Byte(j + 20);
          ParserChunkUtils.xmlStruct.byteSrc = Utils.replaceBytes(ParserChunkUtils.xmlStruct.byteSrc, arrayOfByte, i + 4);
          ParserChunkUtils.xmlStruct.byteSrc = Utils.insertByte(ParserChunkUtils.xmlStruct.byteSrc, startTagChunk.offset + j, attributeData.getByte());
          modifStringChunk();
          modifyFileSize();
          break;
        } 
        for (AttributeData attributeData1 : startTagChunk.attrList) {
          if ("name".equals(ParserChunkUtils.xmlStruct.stringChunk.stringContentList.get(attributeData1.name))) {
            i = startTagChunk.offset;
            byte[] arrayOfByte = Utils.int2Byte(startTagChunk.attrList.size() + 1);
            ParserChunkUtils.xmlStruct.byteSrc = Utils.replaceBytes(ParserChunkUtils.xmlStruct.byteSrc, arrayOfByte, i + 28);
            i = startTagChunk.offset;
            j = Utils.byte2int(startTagChunk.size);
            arrayOfByte = Utils.int2Byte(j + 20);
            ParserChunkUtils.xmlStruct.byteSrc = Utils.replaceBytes(ParserChunkUtils.xmlStruct.byteSrc, arrayOfByte, i + 4);
            ParserChunkUtils.xmlStruct.byteSrc = Utils.insertByte(ParserChunkUtils.xmlStruct.byteSrc, startTagChunk.offset + j, attributeData.getByte());
            modifStringChunk();
            modifyFileSize();
            return;
          } 
        } 
      } 
    } 
  }
  
  public static void addTag(String paramString) {
    Object object;
    int i;
    ParserChunkUtils.parserXml();
    try {
      object = XmlPullParserFactory.newInstance().newPullParser();
      object.setInput(new FileInputStream(paramString), "UTF-8");
      i = object.getEventType();
    } catch (XmlPullParserException xmlPullParserException) {
      object = System.out;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("parse xml err:");
      stringBuilder.append(xmlPullParserException.toString());
      object.println(stringBuilder.toString());
      modifStringChunk();
      modifyFileSize();
      return;
    } catch (IOException iOException) {
      object = System.out;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("parse xml err:");
      stringBuilder.append(iOException.toString());
      object.println(stringBuilder.toString());
    } 
    while (true) {
      if (i != 1) {
        if (i != 2) {
          if (i == 3) {
            paramString = object.getName();
            EndTagChunk endTagChunk = EndTagChunk.createChunk(getStrIndex(paramString));
            if (isNotAppTag(paramString)) {
              ParserChunkUtils.xmlStruct.byteSrc = Utils.insertByte(ParserChunkUtils.xmlStruct.byteSrc, subTagChunkOffsets, endTagChunk.getChunkByte());
              subTagChunkOffsets += (endTagChunk.getChunkByte()).length;
            } else {
              ParserChunkUtils.xmlStruct.byteSrc = Utils.insertByte(ParserChunkUtils.xmlStruct.byteSrc, subAppTagChunkOffset, endTagChunk.getChunkByte());
              subAppTagChunkOffset += (endTagChunk.getChunkByte()).length;
            } 
          } 
        } else {
          String str = object.getName();
          int j = getStrIndex(str);
          int k = object.getAttributeCount();
          byte[] arrayOfByte = new byte[k * 20];
          for (i = 0; i < object.getAttributeCount(); i++) {
            int m = getStrIndex(prefixStr);
            String[] arrayOfString = object.getAttributeName(i).split(":");
            int[] arrayOfInt = getAttrType(object.getAttributeValue(i));
            AttributeData attributeData = AttributeData.createAttribute(m, getStrIndex(arrayOfString[1]), getStrIndex(object.getAttributeValue(i)), arrayOfInt[0], arrayOfInt[1]);
            arrayOfByte = Utils.byteConcat(arrayOfByte, attributeData.getByte(), attributeData.getLen() * i);
          } 
          StartTagChunk startTagChunk = StartTagChunk.createChunk(j, k, -1, arrayOfByte);
          if (isNotAppTag(str)) {
            ParserChunkUtils.xmlStruct.byteSrc = Utils.insertByte(ParserChunkUtils.xmlStruct.byteSrc, subTagChunkOffsets, startTagChunk.getChunkByte());
            subTagChunkOffsets += (startTagChunk.getChunkByte()).length;
          } else {
            ParserChunkUtils.xmlStruct.byteSrc = Utils.insertByte(ParserChunkUtils.xmlStruct.byteSrc, subAppTagChunkOffset, startTagChunk.getChunkByte());
            subAppTagChunkOffset += (startTagChunk.getChunkByte()).length;
          } 
        } 
        i = object.next();
        continue;
      } 
      modifStringChunk();
      modifyFileSize();
      return;
    } 
  }
  
  public static int[] getAttrType(String paramString) {
    int[] arrayOfInt = new int[2];
    if (paramString.equals("true") || paramString.equals("false")) {
      arrayOfInt[0] = arrayOfInt[0] | 0x12;
      if (paramString.equals("true")) {
        arrayOfInt[1] = 1;
      } else {
        arrayOfInt[1] = 0;
      } 
      arrayOfInt[0] = arrayOfInt[0] | 0x8000000;
      arrayOfInt[0] = Utils.byte2int(Utils.reverseBytes(Utils.int2Byte(arrayOfInt[0])));
      return arrayOfInt;
    } 
    if (paramString.equals("singleTask") || paramString.equals("standard") || paramString.equals("singleTop") || paramString.equals("singleInstance")) {
      arrayOfInt[0] = arrayOfInt[0] | 0x10;
      if (paramString.equals("standard")) {
        arrayOfInt[1] = 0;
      } else if (paramString.equals("singleTop")) {
        arrayOfInt[1] = 1;
      } else if (paramString.equals("singleTask")) {
        arrayOfInt[1] = 2;
      } else {
        arrayOfInt[1] = 3;
      } 
      arrayOfInt[0] = arrayOfInt[0] | 0x8000000;
      arrayOfInt[0] = Utils.byte2int(Utils.reverseBytes(Utils.int2Byte(arrayOfInt[0])));
      return arrayOfInt;
    } 
    if (paramString.equals("minSdkVersion") || paramString.equals("versionCode")) {
      arrayOfInt[0] = arrayOfInt[0] | 0x10;
      arrayOfInt[1] = Integer.valueOf(paramString).intValue();
      arrayOfInt[0] = arrayOfInt[0] | 0x8000000;
      arrayOfInt[0] = Utils.byte2int(Utils.reverseBytes(Utils.int2Byte(arrayOfInt[0])));
      return arrayOfInt;
    } 
    if (paramString.startsWith("@")) {
      arrayOfInt[0] = arrayOfInt[0] | true;
      arrayOfInt[1] = 2130706432;
    } else if (paramString.startsWith("#")) {
      arrayOfInt[0] = arrayOfInt[0] | 0x1E;
      arrayOfInt[1] = -1;
    } else {
      arrayOfInt[0] = arrayOfInt[0] | 0x3;
      arrayOfInt[1] = getStrIndex(paramString);
    } 
    arrayOfInt[0] = arrayOfInt[0] | 0x8000000;
    arrayOfInt[0] = Utils.byte2int(Utils.reverseBytes(Utils.int2Byte(arrayOfInt[0])));
    return arrayOfInt;
  }
  
  public static int getStrIndex(String paramString) {
    if (paramString == null || paramString.length() == 0)
      return -1; 
    for (int i = 0; i < ParserChunkUtils.xmlStruct.stringChunk.stringContentList.size(); i++) {
      if (((String)ParserChunkUtils.xmlStruct.stringChunk.stringContentList.get(i)).equals(paramString))
        return i; 
    } 
    ParserChunkUtils.xmlStruct.stringChunk.stringContentList.add(paramString);
    return ParserChunkUtils.xmlStruct.stringChunk.stringContentList.size() - 1;
  }
  
  public static boolean isNotAppTag(String paramString) {
    String[] arrayOfString = isNotAppTag;
    int j = arrayOfString.length;
    for (int i = 0; i < j; i++) {
      if (arrayOfString[i].equals(paramString))
        return true; 
    } 
    return false;
  }
  
  private static void modifStringChunk() {
    StringChunk stringChunk = ParserChunkUtils.xmlStruct.stringChunk;
    byte[] arrayOfByte = stringChunk.getByte(ParserChunkUtils.xmlStruct.stringChunk.stringContentList);
    ParserChunkUtils.xmlStruct.byteSrc = Utils.removeByte(ParserChunkUtils.xmlStruct.byteSrc, ParserChunkUtils.stringChunkOffset, Utils.byte2int(stringChunk.size));
    ParserChunkUtils.xmlStruct.byteSrc = Utils.insertByte(ParserChunkUtils.xmlStruct.byteSrc, ParserChunkUtils.stringChunkOffset, arrayOfByte);
  }
  
  public static void modifyAttr(String paramString1, String paramString2, String paramString3, String paramString4) {
    ParserChunkUtils.parserXml();
    removeAttr(paramString1, paramString2, paramString3);
    ParserChunkUtils.parserXml();
    addAttr(paramString1, paramString2, paramString3, paramString4);
  }
  
  public static void modifyFileSize() {
    byte[] arrayOfByte = Utils.int2Byte(ParserChunkUtils.xmlStruct.byteSrc.length);
    ParserChunkUtils.xmlStruct.byteSrc = Utils.replaceBytes(ParserChunkUtils.xmlStruct.byteSrc, arrayOfByte, 4);
  }
  
  public static void removeAttr(String paramString1, String paramString2, String paramString3) {
    ParserChunkUtils.parserXml();
    for (StartTagChunk startTagChunk : ParserChunkUtils.xmlStruct.startTagChunkList) {
      int i = Utils.byte2int(startTagChunk.name);
      if (paramString1.equals(ParserChunkUtils.xmlStruct.stringChunk.stringContentList.get(i))) {
        byte[] arrayOfByte;
        if (paramString1.equals("application") || paramString1.equals("manifest"))
          for (AttributeData attributeData : startTagChunk.attrList) {
            if (paramString3.equals(ParserChunkUtils.xmlStruct.stringChunk.stringContentList.get(attributeData.name))) {
              if (startTagChunk.attrList.size() == 1) {
                removeTag(paramString1, paramString2);
                return;
              } 
              i = startTagChunk.offset;
              arrayOfByte = Utils.int2Byte(startTagChunk.attrList.size() - 1);
              ParserChunkUtils.xmlStruct.byteSrc = Utils.replaceBytes(ParserChunkUtils.xmlStruct.byteSrc, arrayOfByte, i + 28);
              i = startTagChunk.offset;
              arrayOfByte = Utils.int2Byte(Utils.byte2int(startTagChunk.size) - 20);
              ParserChunkUtils.xmlStruct.byteSrc = Utils.replaceBytes(ParserChunkUtils.xmlStruct.byteSrc, arrayOfByte, i + 4);
              i = attributeData.offset;
              int j = attributeData.getLen();
              ParserChunkUtils.xmlStruct.byteSrc = Utils.removeByte(ParserChunkUtils.xmlStruct.byteSrc, i, j);
              modifyFileSize();
              return;
            } 
          }  
        for (AttributeData attributeData : startTagChunk.attrList) {
          if ("name".equals(ParserChunkUtils.xmlStruct.stringChunk.stringContentList.get(attributeData.name)) && paramString2.equals(ParserChunkUtils.xmlStruct.stringChunk.stringContentList.get(attributeData.valueString)))
            label30: for (AttributeData attributeData : startTagChunk.attrList) {
              if (paramString3.equals(ParserChunkUtils.xmlStruct.stringChunk.stringContentList.get(attributeData.name))) {
                if (startTagChunk.attrList.size() == 1) {
                  removeTag((String)arrayOfByte, paramString2);
                  return;
                } 
                i = startTagChunk.offset;
                arrayOfByte = Utils.int2Byte(startTagChunk.attrList.size() - 1);
                ParserChunkUtils.xmlStruct.byteSrc = Utils.replaceBytes(ParserChunkUtils.xmlStruct.byteSrc, arrayOfByte, i + 28);
                i = startTagChunk.offset;
                arrayOfByte = Utils.int2Byte(Utils.byte2int(startTagChunk.size) - 20);
                ParserChunkUtils.xmlStruct.byteSrc = Utils.replaceBytes(ParserChunkUtils.xmlStruct.byteSrc, arrayOfByte, i + 4);
                i = attributeData.offset;
                int j = attributeData.getLen();
                ParserChunkUtils.xmlStruct.byteSrc = Utils.removeByte(ParserChunkUtils.xmlStruct.byteSrc, i, j);
                modifyFileSize();
                break label30;
              } 
            }  
        } 
      } 
    } 
  }
  
  public static void removeTag(String paramString1, String paramString2) {
    ParserChunkUtils.parserXml();
    for (TagChunk tagChunk : ParserChunkUtils.xmlStruct.tagChunkList) {
      int i = Utils.byte2int(tagChunk.startTagChunk.name);
      if (paramString1.equals(ParserChunkUtils.xmlStruct.stringChunk.stringContentList.get(i)))
        label12: for (AttributeData attributeData : tagChunk.startTagChunk.attrList) {
          if ("name".equals(ParserChunkUtils.xmlStruct.stringChunk.stringContentList.get(attributeData.name)) && paramString2.equals(ParserChunkUtils.xmlStruct.stringChunk.stringContentList.get(attributeData.valueString))) {
            i = Utils.byte2int(tagChunk.endTagChunk.size);
            int j = tagChunk.startTagChunk.offset;
            int k = tagChunk.endTagChunk.offset;
            int m = tagChunk.startTagChunk.offset;
            ParserChunkUtils.xmlStruct.byteSrc = Utils.removeByte(ParserChunkUtils.xmlStruct.byteSrc, j, k - m + i);
            modifyFileSize();
            break label12;
          } 
        }  
    } 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\main\XmlEditor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */