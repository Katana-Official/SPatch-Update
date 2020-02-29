package com.beust.jcommander.converters;

import com.beust.jcommander.IStringConverter;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressConverter implements IStringConverter<InetAddress> {
  public InetAddress convert(String paramString) {
    try {
      return InetAddress.getByName(paramString);
    } catch (UnknownHostException unknownHostException) {
      throw new IllegalArgumentException(paramString, unknownHostException);
    } 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\converters\InetAddressConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */