package com.beust.jcommander.internal;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Sets {
  public static <K> Set<K> newHashSet() { return new HashSet<K>(); }
  
  public static <K> Set<K> newLinkedHashSet() { return new LinkedHashSet<K>(); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\internal\Sets.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */