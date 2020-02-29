package com.beust.jcommander.converters;

import com.beust.jcommander.ParameterException;

public class BooleanConverter extends BaseConverter<Boolean> {
  public BooleanConverter(String paramString) { super(paramString); }
  
  public Boolean convert(String paramString) {
    if ("false".equalsIgnoreCase(paramString) || "true".equalsIgnoreCase(paramString))
      return Boolean.valueOf(Boolean.parseBoolean(paramString)); 
    throw new ParameterException(getErrorString(paramString, "a boolean"));
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\converters\BooleanConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */