package com.beust.jcommander.converters;

import com.beust.jcommander.ParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ISO8601DateConverter extends BaseConverter<Date> {
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
  
  public ISO8601DateConverter(String paramString) { super(paramString); }
  
  public Date convert(String paramString) {
    try {
      return DATE_FORMAT.parse(paramString);
    } catch (ParseException parseException) {
      throw new ParameterException(getErrorString(paramString, String.format("an ISO-8601 formatted date (%s)", new Object[] { DATE_FORMAT.toPattern() })));
    } 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\converters\ISO8601DateConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */