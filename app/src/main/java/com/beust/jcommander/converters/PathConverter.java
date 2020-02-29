package com.beust.jcommander.converters;

import com.beust.jcommander.IStringConverter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathConverter implements IStringConverter<Path> {
  public Path convert(String paramString) { return Paths.get(paramString, new String[0]); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\converters\PathConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */