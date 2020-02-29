package com.sk.spatch.xmltool.test;

import com.sk.spatch.xmltool.android.content.res.AXmlResourceParser;
import java.io.FileInputStream;

public class AXMLPrinter {
  private static final String[] DIMENSION_UNITS;
  
  private static final String[] FRACTION_UNITS;
  
  private static final float[] RADIX_MULTS = new float[] { 0.00390625F, 3.051758E-5F, 1.192093E-7F, 4.656613E-10F };
  
  static  {
    DIMENSION_UNITS = new String[] { "px", "dip", "sp", "pt", "in", "mm", "", "" };
    FRACTION_UNITS = new String[] { "%", "%p", "", "", "", "", "", "" };
  }
  
  public static float complexToFloat(int paramInt) { return (paramInt & 0xFFFFFF00) * RADIX_MULTS[paramInt >> 4 & 0x3]; }
  
  private static String getAttributeValue(AXmlResourceParser paramAXmlResourceParser, int paramInt) {
    int i = paramAXmlResourceParser.getAttributeValueType(paramInt);
    int j = paramAXmlResourceParser.getAttributeValueData(paramInt);
    if (i == 3)
      return paramAXmlResourceParser.getAttributeValue(paramInt); 
    if (i == 2)
      return String.format("?%s%08X", new Object[] { getPackage(j), Integer.valueOf(j) }); 
    if (i == 1)
      return String.format("@%s%08X", new Object[] { getPackage(j), Integer.valueOf(j) }); 
    if (i == 4)
      return String.valueOf(Float.intBitsToFloat(j)); 
    if (i == 17)
      return String.format("0x%08X", new Object[] { Integer.valueOf(j) }); 
    if (i == 18)
      return (j != 0) ? "true" : "false"; 
    if (i == 5) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(Float.toString(complexToFloat(j)));
      stringBuilder.append(DIMENSION_UNITS[j & 0xF]);
      return stringBuilder.toString();
    } 
    if (i == 6) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(Float.toString(complexToFloat(j)));
      stringBuilder.append(FRACTION_UNITS[j & 0xF]);
      return stringBuilder.toString();
    } 
    return (i >= 28 && i <= 31) ? String.format("#%08X", new Object[] { Integer.valueOf(j) }) : ((i >= 16 && i <= 31) ? String.valueOf(j) : String.format("<0x%X, type 0x%02X>", new Object[] { Integer.valueOf(j), Integer.valueOf(i) }));
  }
  
  private static String getNamespacePrefix(String paramString) {
    if (paramString == null || paramString.length() == 0)
      return ""; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append(":");
    return stringBuilder.toString();
  }
  
  private static String getPackage(int paramInt) { return (paramInt >>> 24 == 1) ? "android:" : ""; }
  
  private static void log(String paramString, Object... paramVarArgs) {
    System.out.printf(paramString, paramVarArgs);
    System.out.println();
  }
  
  public static void main(String[] paramArrayOfString) {
    if (paramArrayOfString.length < 1) {
      log("Usage: AXMLPrinter <binary xml file>", new Object[0]);
      return;
    } 
    try {
      AXmlResourceParser aXmlResourceParser = new AXmlResourceParser();
      aXmlResourceParser.open(new FileInputStream(paramArrayOfString[0]));
      StringBuilder stringBuilder = new StringBuilder(10);
      while (true) {
        int i = aXmlResourceParser.next();
        if (i == 1)
          return; 
        if (i != 0) {
          if (i != 2) {
            if (i != 3) {
              if (i != 4)
                continue; 
              log("%s%s", new Object[] { stringBuilder, aXmlResourceParser.getText() });
              continue;
            } 
            stringBuilder.setLength(stringBuilder.length() - 1);
            log("%s</%s%s>", new Object[] { stringBuilder, getNamespacePrefix(aXmlResourceParser.getPrefix()), aXmlResourceParser.getName() });
            continue;
          } 
          log("%s<%s%s", new Object[] { stringBuilder, getNamespacePrefix(aXmlResourceParser.getPrefix()), aXmlResourceParser.getName() });
          stringBuilder.append("\t");
          i = aXmlResourceParser.getNamespaceCount(aXmlResourceParser.getDepth() - 1);
          int j = aXmlResourceParser.getNamespaceCount(aXmlResourceParser.getDepth());
          while (i != j) {
            log("%sxmlns:%s=\"%s\"", new Object[] { stringBuilder, aXmlResourceParser.getNamespacePrefix(i), aXmlResourceParser.getNamespaceUri(i) });
            i++;
          } 
          for (i = 0; i != aXmlResourceParser.getAttributeCount(); i++) {
            log("%s%s%s=\"%s\"", new Object[] { stringBuilder, getNamespacePrefix(aXmlResourceParser.getAttributePrefix(i)), aXmlResourceParser.getAttributeName(i), getAttributeValue(aXmlResourceParser, i) });
          } 
          log("%s>", new Object[] { stringBuilder });
          continue;
        } 
        log("<?xml version=\"1.0\" encoding=\"utf-8\"?>", new Object[0]);
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
      return;
    } 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\test\AXMLPrinter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */