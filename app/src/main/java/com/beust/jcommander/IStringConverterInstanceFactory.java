package com.beust.jcommander;

public interface IStringConverterInstanceFactory {
  IStringConverter<?> getConverterInstance(Parameter paramParameter, Class<?> paramClass, String paramString);
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\IStringConverterInstanceFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */