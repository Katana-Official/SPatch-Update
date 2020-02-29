package com.beust.jcommander.converters;

import com.beust.jcommander.ParameterException;
import java.net.MalformedURLException;
import java.net.URL;

public class URLConverter extends BaseConverter<URL> {
  public URLConverter(String paramString) { super(paramString); }
  
  public URL convert(String paramString) {
    try {
      return new URL(paramString);
    } catch (MalformedURLException malformedURLException) {
      throw new ParameterException(getErrorString(paramString, "a RFC 2396 and RFC 2732 compliant URL"));
    } 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\converters\URLConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */