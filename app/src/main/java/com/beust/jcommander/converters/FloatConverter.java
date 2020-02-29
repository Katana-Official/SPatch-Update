package com.beust.jcommander.converters;

import com.beust.jcommander.ParameterException;

public class FloatConverter extends BaseConverter<Float> {
  public FloatConverter(String paramString) { super(paramString); }
  
  public Float convert(String paramString) {
    try {
      float f = Float.parseFloat(paramString);
      return Float.valueOf(f);
    } catch (NumberFormatException numberFormatException) {
      throw new ParameterException(getErrorString(paramString, "a float"));
    } 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\converters\FloatConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */