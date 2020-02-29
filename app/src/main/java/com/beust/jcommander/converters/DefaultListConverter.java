package com.beust.jcommander.converters;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.internal.Lists;
import java.util.List;

public class DefaultListConverter<T> implements IStringConverter<List<T>> {
  private final IStringConverter<T> converter;
  
  private final IParameterSplitter splitter;
  
  public DefaultListConverter(IParameterSplitter paramIParameterSplitter, IStringConverter<T> paramIStringConverter) {
    this.splitter = paramIParameterSplitter;
    this.converter = paramIStringConverter;
  }
  
  public List<T> convert(String paramString) {
    List<Object> list = Lists.newArrayList();
    for (String str : this.splitter.split(paramString))
      list.add(this.converter.convert(str)); 
    return (List)list;
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\converters\DefaultListConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */