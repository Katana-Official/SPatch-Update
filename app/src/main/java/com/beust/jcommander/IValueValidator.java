package com.beust.jcommander;

public interface IValueValidator<T> {
  void validate(String paramString, T paramT) throws ParameterException;
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\IValueValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */