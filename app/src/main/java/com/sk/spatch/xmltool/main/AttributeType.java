package com.sk.spatch.xmltool.main;

import com.sk.spatch.xmltool.chunk.AttributeData;
import java.util.HashMap;

public class AttributeType {
  public static final int ATTR_ARGB4 = 30;
  
  public static final int ATTR_ATTRIBUTE = 2;
  
  public static final int ATTR_BOOLEAN = 18;
  
  public static final int ATTR_DIMENSION = 5;
  
  public static final int ATTR_FIRSTCOLOR = 28;
  
  public static final int ATTR_FIRSTINT = 16;
  
  public static final int ATTR_FLOAT = 4;
  
  public static final int ATTR_FRACTION = 6;
  
  public static final int ATTR_HEX = 17;
  
  public static final int ATTR_LASTCOLOR = 31;
  
  public static final int ATTR_LASTINT = 31;
  
  public static final int ATTR_NULL = 0;
  
  public static final int ATTR_REFERENCE = 1;
  
  public static final int ATTR_RGB4 = 31;
  
  public static final int ATTR_RGB8 = 29;
  
  public static final int ATTR_STRING = 3;
  
  public static final int COMPLEX_MANTISSA_MASK = 16777215;
  
  public static final int COMPLEX_MANTISSA_SHIFT = 8;
  
  public static final int COMPLEX_RADIX_0p23 = 3;
  
  public static final int COMPLEX_RADIX_16p7 = 1;
  
  public static final int COMPLEX_RADIX_23p0 = 0;
  
  public static final int COMPLEX_RADIX_8p15 = 2;
  
  public static final int COMPLEX_RADIX_MASK = 3;
  
  public static final int COMPLEX_RADIX_SHIFT = 4;
  
  public static final int COMPLEX_UNIT_DIP = 1;
  
  public static final int COMPLEX_UNIT_FRACTION = 0;
  
  public static final int COMPLEX_UNIT_FRACTION_PARENT = 1;
  
  public static final int COMPLEX_UNIT_IN = 4;
  
  public static final int COMPLEX_UNIT_MASK = 15;
  
  public static final int COMPLEX_UNIT_MM = 5;
  
  public static final int COMPLEX_UNIT_PT = 3;
  
  public static final int COMPLEX_UNIT_PX = 0;
  
  public static final int COMPLEX_UNIT_SHIFT = 0;
  
  public static final int COMPLEX_UNIT_SP = 2;
  
  private static final String[] DIMENSION_UNITS;
  
  private static final String[] FRACTION_UNITS;
  
  private static final float[] RADIX_MULTS = new float[] { 0.00390625F, 3.051758E-5F, 1.192093E-7F, 4.656613E-10F };
  
  public static HashMap<Integer, String> typeMap0;
  
  public static HashMap<String, Integer> typeMap1;
  
  static  {
    DIMENSION_UNITS = new String[] { "px", "dip", "sp", "pt", "in", "mm", "", "" };
    FRACTION_UNITS = new String[] { "%", "%p", "", "", "", "", "", "" };
    typeMap0 = new HashMap<Integer, String>();
    typeMap1 = new HashMap<String, Integer>();
    typeMap0.put(Integer.valueOf(0), "ATTR_NULL");
    typeMap0.put(Integer.valueOf(1), "ATTR_REFERENCE");
    typeMap0.put(Integer.valueOf(2), "ATTR_ATTRIBUTE");
    typeMap0.put(Integer.valueOf(3), "ATTR_STRING");
    typeMap0.put(Integer.valueOf(4), "ATTR_FLOAT");
    typeMap0.put(Integer.valueOf(5), "ATTR_DIMENSION");
    typeMap0.put(Integer.valueOf(6), "ATTR_FRACTION");
    typeMap0.put(Integer.valueOf(16), "ATTR_FIRSTINT");
    typeMap0.put(Integer.valueOf(17), "ATTR_HEX");
    typeMap0.put(Integer.valueOf(18), "ATTR_BOOLEAN");
    typeMap0.put(Integer.valueOf(28), "ATTR_FIRSTCOLOR");
    typeMap0.put(Integer.valueOf(29), "ATTR_RGB8");
    typeMap0.put(Integer.valueOf(30), "ATTR_ARGB4");
    typeMap0.put(Integer.valueOf(31), "ATTR_RGB4");
    for (Integer integer : typeMap0.keySet())
      typeMap1.put(typeMap0.get(integer), integer); 
  }
  
  public static float complexToFloat(int paramInt) { return (paramInt & 0xFFFFFF00) * RADIX_MULTS[paramInt >> 4 & 0x3]; }
  
  public static Integer getAttrType(String paramString) { return typeMap1.get(paramString); }
  
  public static String getAttrType(int paramInt) { return typeMap0.get(Integer.valueOf(paramInt)); }
  
  public static String getAttributeData(AttributeData paramAttributeData) {
    if (paramAttributeData.type == 3)
      return ParserChunkUtils.xmlStruct.stringChunk.stringContentList.get(paramAttributeData.data); 
    if (paramAttributeData.type == 2)
      return String.format("?%s%08X", new Object[] { getPackage(paramAttributeData.data), Integer.valueOf(paramAttributeData.data) }); 
    if (paramAttributeData.type == 1)
      return String.format("@%s%08X", new Object[] { getPackage(paramAttributeData.data), Integer.valueOf(paramAttributeData.data) }); 
    if (paramAttributeData.type == 4)
      return String.valueOf(Float.intBitsToFloat(paramAttributeData.data)); 
    if (paramAttributeData.type == 17)
      return String.format("0x%08X", new Object[] { Integer.valueOf(paramAttributeData.data) }); 
    if (paramAttributeData.type == 18)
      return (paramAttributeData.data != 0) ? "true" : "false"; 
    if (paramAttributeData.type == 5) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(Float.toString(complexToFloat(paramAttributeData.data)));
      stringBuilder.append(DIMENSION_UNITS[paramAttributeData.data & 0xF]);
      return stringBuilder.toString();
    } 
    if (paramAttributeData.type == 6) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(Float.toString(complexToFloat(paramAttributeData.data)));
      stringBuilder.append(FRACTION_UNITS[paramAttributeData.data & 0xF]);
      return stringBuilder.toString();
    } 
    return (paramAttributeData.type >= 28 && paramAttributeData.type <= 31) ? String.format("#%08X", new Object[] { Integer.valueOf(paramAttributeData.data) }) : ((paramAttributeData.type >= 16 && paramAttributeData.type <= 31) ? String.valueOf(paramAttributeData.data) : String.format("<0x%X, type 0x%02X>", new Object[] { Integer.valueOf(paramAttributeData.data), Integer.valueOf(paramAttributeData.type) }));
  }
  
  private static String getPackage(int paramInt) { return (paramInt >>> 24 == 1) ? "android:" : ""; }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\main\AttributeType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */