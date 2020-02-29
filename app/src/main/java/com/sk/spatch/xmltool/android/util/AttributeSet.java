package com.sk.spatch.xmltool.android.util;

public interface AttributeSet {
  boolean getAttributeBooleanValue(int paramInt, boolean paramBoolean);
  
  boolean getAttributeBooleanValue(String paramString1, String paramString2, boolean paramBoolean);
  
  int getAttributeCount();
  
  float getAttributeFloatValue(int paramInt, float paramFloat);
  
  float getAttributeFloatValue(String paramString1, String paramString2, float paramFloat);
  
  int getAttributeIntValue(int paramInt1, int paramInt2);
  
  int getAttributeIntValue(String paramString1, String paramString2, int paramInt);
  
  int getAttributeListValue(int paramInt1, String[] paramArrayOfString, int paramInt2);
  
  int getAttributeListValue(String paramString1, String paramString2, String[] paramArrayOfString, int paramInt);
  
  String getAttributeName(int paramInt);
  
  int getAttributeNameResource(int paramInt);
  
  int getAttributeResourceValue(int paramInt1, int paramInt2);
  
  int getAttributeResourceValue(String paramString1, String paramString2, int paramInt);
  
  int getAttributeUnsignedIntValue(int paramInt1, int paramInt2);
  
  int getAttributeUnsignedIntValue(String paramString1, String paramString2, int paramInt);
  
  String getAttributeValue(int paramInt);
  
  String getAttributeValue(String paramString1, String paramString2);
  
  int getAttributeValueData(int paramInt);
  
  int getAttributeValueType(int paramInt);
  
  String getClassAttribute();
  
  String getIdAttribute();
  
  int getIdAttributeResourceValue(int paramInt);
  
  String getPositionDescription();
  
  int getStyleAttribute();
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\androi\\util\AttributeSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */