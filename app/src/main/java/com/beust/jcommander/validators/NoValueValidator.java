package com.beust.jcommander.validators;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;

public class NoValueValidator<T> implements IValueValidator<T> {
  public void validate(String paramString, T paramT) throws ParameterException {}
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\validators\NoValueValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */