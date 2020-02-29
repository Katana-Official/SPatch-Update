package com.beust.jcommander.converters;

import com.beust.jcommander.IStringConverter;

public class CharArrayConverter implements IStringConverter<char[]> {
  public char[] convert(String paramString) { return paramString.toCharArray(); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\converters\CharArrayConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */