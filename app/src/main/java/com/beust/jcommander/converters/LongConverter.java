package com.beust.jcommander.converters;

import com.beust.jcommander.ParameterException;

public class LongConverter extends BaseConverter<Long> {
  public LongConverter(String paramString) { super(paramString); }
  
  public Long convert(String paramString) {
    try {
      long l = Long.parseLong(paramString);
      return Long.valueOf(l);
    } catch (NumberFormatException numberFormatException) {
      throw new ParameterException(getErrorString(paramString, "a long"));
    } 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\converters\LongConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */