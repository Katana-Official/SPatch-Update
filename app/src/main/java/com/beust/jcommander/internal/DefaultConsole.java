package com.beust.jcommander.internal;

import com.beust.jcommander.ParameterException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DefaultConsole implements Console {
  public void print(String paramString) { System.out.print(paramString); }
  
  public void println(String paramString) { System.out.println(paramString); }
  
  public char[] readPassword(boolean paramBoolean) {
    try {
      return (new BufferedReader(new InputStreamReader(System.in))).readLine().toCharArray();
    } catch (IOException iOException) {
      throw new ParameterException(iOException);
    } 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\internal\DefaultConsole.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */