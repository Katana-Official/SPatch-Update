package com.beust.jcommander.validators;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class PositiveInteger implements IParameterValidator {
  public void validate(String paramString1, String paramString2) throws ParameterException {
    if (Integer.parseInt(paramString2) >= 0)
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Parameter ");
    stringBuilder.append(paramString1);
    stringBuilder.append(" should be positive (found ");
    stringBuilder.append(paramString2);
    stringBuilder.append(")");
    throw new ParameterException(stringBuilder.toString());
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\validators\PositiveInteger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */