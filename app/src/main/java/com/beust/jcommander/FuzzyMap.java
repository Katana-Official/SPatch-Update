package com.beust.jcommander;

import java.util.Map;

public class FuzzyMap {
  private static <V> V findAbbreviatedValue(Map<? extends IKey, V> paramMap, IKey paramIKey, boolean paramBoolean) { // Byte code:
    //   0: aload_1
    //   1: invokeinterface getName : ()Ljava/lang/String;
    //   6: astore #7
    //   8: invokestatic newHashMap : ()Ljava/util/Map;
    //   11: astore #6
    //   13: aload_0
    //   14: invokeinterface keySet : ()Ljava/util/Set;
    //   19: invokeinterface iterator : ()Ljava/util/Iterator;
    //   24: astore #8
    //   26: aload #8
    //   28: invokeinterface hasNext : ()Z
    //   33: istore #5
    //   35: iconst_1
    //   36: istore #4
    //   38: iload #5
    //   40: ifeq -> 134
    //   43: aload #8
    //   45: invokeinterface next : ()Ljava/lang/Object;
    //   50: checkcast com/beust/jcommander/FuzzyMap$IKey
    //   53: astore #9
    //   55: aload #9
    //   57: invokeinterface getName : ()Ljava/lang/String;
    //   62: astore #10
    //   64: iload_2
    //   65: ifeq -> 81
    //   68: iload #4
    //   70: istore_3
    //   71: aload #10
    //   73: aload #7
    //   75: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   78: ifne -> 109
    //   81: iload_2
    //   82: ifne -> 107
    //   85: aload #10
    //   87: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   90: aload #7
    //   92: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   95: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   98: ifeq -> 107
    //   101: iload #4
    //   103: istore_3
    //   104: goto -> 109
    //   107: iconst_0
    //   108: istore_3
    //   109: iload_3
    //   110: ifeq -> 26
    //   113: aload #6
    //   115: aload #10
    //   117: aload_0
    //   118: aload #9
    //   120: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   125: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   130: pop
    //   131: goto -> 26
    //   134: aload #6
    //   136: invokeinterface size : ()I
    //   141: iconst_1
    //   142: if_icmpgt -> 176
    //   145: aload #6
    //   147: invokeinterface size : ()I
    //   152: iconst_1
    //   153: if_icmpne -> 174
    //   156: aload #6
    //   158: invokeinterface values : ()Ljava/util/Collection;
    //   163: invokeinterface iterator : ()Ljava/util/Iterator;
    //   168: invokeinterface next : ()Ljava/lang/Object;
    //   173: areturn
    //   174: aconst_null
    //   175: areturn
    //   176: new java/lang/StringBuilder
    //   179: dup
    //   180: invokespecial <init> : ()V
    //   183: astore_0
    //   184: aload_0
    //   185: ldc 'Ambiguous option: '
    //   187: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   190: pop
    //   191: aload_0
    //   192: aload_1
    //   193: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   196: pop
    //   197: aload_0
    //   198: ldc ' matches '
    //   200: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   203: pop
    //   204: aload_0
    //   205: aload #6
    //   207: invokeinterface keySet : ()Ljava/util/Set;
    //   212: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   215: pop
    //   216: new com/beust/jcommander/ParameterException
    //   219: dup
    //   220: aload_0
    //   221: invokevirtual toString : ()Ljava/lang/String;
    //   224: invokespecial <init> : (Ljava/lang/String;)V
    //   227: athrow }
  
  public static <V> V findInMap(Map<? extends IKey, V> paramMap, IKey paramIKey, boolean paramBoolean1, boolean paramBoolean2) {
    if (paramBoolean2)
      return findAbbreviatedValue(paramMap, paramIKey, paramBoolean1); 
    if (paramBoolean1)
      return paramMap.get(paramIKey); 
    for (IKey iKey : paramMap.keySet()) {
      if (iKey.getName().equalsIgnoreCase(paramIKey.getName()))
        return paramMap.get(iKey); 
    } 
    return null;
  }
  
  static interface IKey {
    String getName();
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\beust\jcommander\FuzzyMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */