package com.beust.jcommander;

public class ParameterException extends RuntimeException {
  public ParameterException(String paramString) { super(paramString); }
  
  public ParameterException(String paramString, Throwable paramThrowable) { super(paramString, paramThrowable); }
  
  public ParameterException(Throwable paramThrowable) { super(paramThrowable); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\ParameterException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */