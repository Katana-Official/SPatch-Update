package com.beust.jcommander.converters;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;
import java.util.EnumSet;

public class EnumConverter<T extends Enum<T>> implements IStringConverter<T> {
  private final Class<T> clazz;
  
  private final String optionName;
  
  public EnumConverter(String paramString, Class<T> paramClass) {
    this.optionName = paramString;
    this.clazz = paramClass;
  }
  
  public T convert(String paramString) {
    try {
      return (T)Enum.valueOf((Class)this.clazz, paramString);
    } catch (IllegalArgumentException illegalArgumentException) {
      return (T)Enum.valueOf((Class)this.clazz, paramString.toUpperCase());
    } catch (Exception exception) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Invalid value for ");
      stringBuilder.append(this.optionName);
      stringBuilder.append(" parameter. Allowed values:");
      stringBuilder.append(EnumSet.allOf(this.clazz));
      throw new ParameterException(stringBuilder.toString());
    } 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\converters\EnumConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */