package com.beust.jcommander.converters;

import com.beust.jcommander.IStringConverter;

public abstract class BaseConverter<T> implements IStringConverter<T> {
  private String optionName;
  
  public BaseConverter(String paramString) { this.optionName = paramString; }
  
  protected String getErrorString(String paramString1, String paramString2) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("\"");
    stringBuilder.append(getOptionName());
    stringBuilder.append("\": couldn't convert \"");
    stringBuilder.append(paramString1);
    stringBuilder.append("\" to ");
    stringBuilder.append(paramString2);
    return stringBuilder.toString();
  }
  
  public String getOptionName() { return this.optionName; }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\converters\BaseConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */