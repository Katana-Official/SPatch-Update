package com.beust.jcommander.converters;

import java.util.Arrays;
import java.util.List;

public class CommaParameterSplitter implements IParameterSplitter {
  public List<String> split(String paramString) { return Arrays.asList(paramString.split(",")); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\converters\CommaParameterSplitter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */