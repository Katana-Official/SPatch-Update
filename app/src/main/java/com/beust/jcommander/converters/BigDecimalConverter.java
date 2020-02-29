package com.beust.jcommander.converters;

import com.beust.jcommander.ParameterException;
import java.math.BigDecimal;

public class BigDecimalConverter extends BaseConverter<BigDecimal> {
  public BigDecimalConverter(String paramString) { super(paramString); }
  
  public BigDecimal convert(String paramString) {
    try {
      return new BigDecimal(paramString);
    } catch (NumberFormatException numberFormatException) {
      throw new ParameterException(getErrorString(paramString, "a BigDecimal"));
    } 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\converters\BigDecimalConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */