package com.beust.jcommander.converters;

import com.beust.jcommander.ParameterException;

public class IntegerConverter extends BaseConverter<Integer> {
  public IntegerConverter(String paramString) { super(paramString); }
  
  public Integer convert(String paramString) {
    try {
      int i = Integer.parseInt(paramString);
      return Integer.valueOf(i);
    } catch (NumberFormatException numberFormatException) {
      throw new ParameterException(getErrorString(paramString, "an integer"));
    } 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\converters\IntegerConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */