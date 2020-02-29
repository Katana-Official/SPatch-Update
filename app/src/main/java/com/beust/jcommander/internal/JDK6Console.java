package com.beust.jcommander.internal;

import com.beust.jcommander.ParameterException;
import java.io.PrintWriter;

public class JDK6Console implements Console {
  private Object console;
  
  private PrintWriter writer;
  
  public JDK6Console(Object paramObject) throws Exception {
    this.console = paramObject;
    this.writer = (PrintWriter)paramObject.getClass().getDeclaredMethod("writer", new Class[0]).invoke(paramObject, new Object[0]);
  }
  
  public void print(String paramString) { this.writer.print(paramString); }
  
  public void println(String paramString) { this.writer.println(paramString); }
  
  public char[] readPassword(boolean paramBoolean) {
    try {
      this.writer.flush();
      return paramBoolean ? ((String)this.console.getClass().getDeclaredMethod("readLine", new Class[0]).invoke(this.console, new Object[0])).toCharArray() : (char[])this.console.getClass().getDeclaredMethod("readPassword", new Class[0]).invoke(this.console, new Object[0]);
    } catch (Exception exception) {
      throw new ParameterException(exception);
    } 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\internal\JDK6Console.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */