package com.beust.jcommander;

public interface IStringConverterFactory {
  <T> Class<? extends IStringConverter<T>> getConverter(Class<T> paramClass);
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\IStringConverterFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */