package com.beust.jcommander.converters;

import com.beust.jcommander.ParameterException;

public class DoubleConverter extends BaseConverter<Double> {
  public DoubleConverter(String paramString) { super(paramString); }
  
  public Double convert(String paramString) {
    try {
      double d = Double.parseDouble(paramString);
      return Double.valueOf(d);
    } catch (NumberFormatException numberFormatException) {
      throw new ParameterException(getErrorString(paramString, "a double"));
    } 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\converters\DoubleConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */