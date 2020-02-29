package com.beust.jcommander.defaultprovider;

import com.beust.jcommander.IDefaultProvider;
import com.beust.jcommander.ParameterException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class PropertyFileDefaultProvider implements IDefaultProvider {
  public static final String DEFAULT_FILE_NAME = "jcommander.properties";
  
  private Properties properties;
  
  public PropertyFileDefaultProvider() { init("jcommander.properties"); }
  
  public PropertyFileDefaultProvider(String paramString) { init(paramString); }
  
  private void init(String paramString) {
    try {
      this.properties = new Properties();
      URL uRL = ClassLoader.getSystemResource(paramString);
      if (uRL != null) {
        this.properties.load(uRL.openStream());
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Could not find property file: ");
      stringBuilder.append(paramString);
      stringBuilder.append(" on the class path");
      throw new ParameterException(stringBuilder.toString());
    } catch (IOException iOException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Could not open property file: ");
      stringBuilder.append(paramString);
      throw new ParameterException(stringBuilder.toString());
    } 
  }
  
  public String getDefaultValueFor(String paramString) {
    int i;
    for (i = 0; i < paramString.length() && !Character.isLetterOrDigit(paramString.charAt(i)); i++);
    paramString = paramString.substring(i);
    return this.properties.getProperty(paramString);
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\defaultprovider\PropertyFileDefaultProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */