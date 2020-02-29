package com.beust.jcommander.converters;

import com.beust.jcommander.IStringConverter;
import java.io.File;

public class FileConverter implements IStringConverter<File> {
  public File convert(String paramString) { return new File(paramString); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\converters\FileConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */