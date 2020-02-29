package com.beust.jcommander.converters;

import com.beust.jcommander.ParameterException;
import java.net.URI;
import java.net.URISyntaxException;

public class URIConverter extends BaseConverter<URI> {
  public URIConverter(String paramString) { super(paramString); }
  
  public URI convert(String paramString) {
    try {
      return new URI(paramString);
    } catch (URISyntaxException uRISyntaxException) {
      throw new ParameterException(getErrorString(paramString, "a RFC 2396 and RFC 2732 compliant URI"));
    } 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\converters\URIConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */