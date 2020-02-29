package com.beust.jcommander.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Lists {
  public static <K> List<K> newArrayList() { return new ArrayList<K>(); }
  
  public static <K> List<K> newArrayList(int paramInt) { return new ArrayList<K>(paramInt); }
  
  public static <K> List<K> newArrayList(Collection<K> paramCollection) { return new ArrayList<K>(paramCollection); }
  
  public static <K> List<K> newArrayList(K... paramVarArgs) { return new ArrayList<K>(Arrays.asList(paramVarArgs)); }
  
  public static <K> LinkedList<K> newLinkedList() { return new LinkedList<K>(); }
  
  public static <K> LinkedList<K> newLinkedList(Collection<K> paramCollection) { return new LinkedList<K>(paramCollection); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\internal\Lists.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */