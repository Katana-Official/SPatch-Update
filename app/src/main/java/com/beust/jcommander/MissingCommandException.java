package com.beust.jcommander;

public class MissingCommandException extends ParameterException {
  private final String unknownCommand;
  
  public MissingCommandException(String paramString) { this(paramString, null); }
  
  public MissingCommandException(String paramString1, String paramString2) {
    super(paramString1);
    this.unknownCommand = paramString2;
  }
  
  public String getUnknownCommand() { return this.unknownCommand; }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\MissingCommandException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */