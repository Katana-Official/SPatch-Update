package com.beust.jcommander.internal;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Maps {
  public static <K, V> Map<K, V> newHashMap() { return new HashMap<K, V>(); }
  
  public static <T> Map<T, T> newHashMap(T... paramVarArgs) {
    Map<?, ?> map = newHashMap();
    for (int i = 0; i < paramVarArgs.length; i += 2)
      map.put(paramVarArgs[i], paramVarArgs[i + 1]); 
    return (Map)map;
  }
  
  public static <K, V> Map<K, V> newLinkedHashMap() { return new LinkedHashMap<K, V>(); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\internal\Maps.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */