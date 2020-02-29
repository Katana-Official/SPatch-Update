package com.sk.spatch.xmltool.v1;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class XmlPullParserFactory {
  public static final String PROPERTY_NAME = "org.xmlpull.v1.XmlPullParserFactory";
  
  private static final String RESOURCE_NAME = "/META-INF/services/org.xmlpull.v1.XmlPullParserFactory";
  
  static final Class referenceContextClass = (new XmlPullParserFactory()).getClass();
  
  protected String classNamesLocation;
  
  protected Hashtable features = new Hashtable<Object, Object>();
  
  protected Vector parserClasses;
  
  protected Vector serializerClasses;
  
  public static XmlPullParserFactory newInstance() throws XmlPullParserException { return newInstance(null, null); }
  
  public static XmlPullParserFactory newInstance(String paramString, Class paramClass) throws XmlPullParserException { // Byte code:
    //   0: aload_1
    //   1: astore #7
    //   3: aload_1
    //   4: ifnonnull -> 12
    //   7: getstatic com/sk/spatch/xmltool/v1/XmlPullParserFactory.referenceContextClass : Ljava/lang/Class;
    //   10: astore #7
    //   12: aload_0
    //   13: ifnull -> 75
    //   16: aload_0
    //   17: invokevirtual length : ()I
    //   20: ifeq -> 75
    //   23: ldc 'DEFAULT'
    //   25: aload_0
    //   26: invokevirtual equals : (Ljava/lang/Object;)Z
    //   29: ifeq -> 35
    //   32: goto -> 75
    //   35: new java/lang/StringBuilder
    //   38: dup
    //   39: invokespecial <init> : ()V
    //   42: astore_1
    //   43: aload_1
    //   44: ldc 'parameter classNames to newInstance() that contained ''
    //   46: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   49: pop
    //   50: aload_1
    //   51: aload_0
    //   52: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   55: pop
    //   56: aload_1
    //   57: ldc '''
    //   59: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   62: pop
    //   63: aload_1
    //   64: invokevirtual toString : ()Ljava/lang/String;
    //   67: astore #7
    //   69: aload_0
    //   70: astore #8
    //   72: goto -> 149
    //   75: aload #7
    //   77: ldc '/META-INF/services/org.xmlpull.v1.XmlPullParserFactory'
    //   79: invokevirtual getResourceAsStream : (Ljava/lang/String;)Ljava/io/InputStream;
    //   82: astore_0
    //   83: aload_0
    //   84: ifnull -> 426
    //   87: new java/lang/StringBuffer
    //   90: dup
    //   91: invokespecial <init> : ()V
    //   94: astore_1
    //   95: aload_0
    //   96: invokevirtual read : ()I
    //   99: istore_3
    //   100: iload_3
    //   101: ifge -> 408
    //   104: aload_0
    //   105: invokevirtual close : ()V
    //   108: aload_1
    //   109: invokevirtual toString : ()Ljava/lang/String;
    //   112: astore #8
    //   114: new java/lang/StringBuilder
    //   117: dup
    //   118: invokespecial <init> : ()V
    //   121: astore_0
    //   122: aload_0
    //   123: ldc 'resource /META-INF/services/org.xmlpull.v1.XmlPullParserFactory that contained ''
    //   125: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   128: pop
    //   129: aload_0
    //   130: aload #8
    //   132: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   135: pop
    //   136: aload_0
    //   137: ldc '''
    //   139: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   142: pop
    //   143: aload_0
    //   144: invokevirtual toString : ()Ljava/lang/String;
    //   147: astore #7
    //   149: new java/util/Vector
    //   152: dup
    //   153: invokespecial <init> : ()V
    //   156: astore #11
    //   158: new java/util/Vector
    //   161: dup
    //   162: invokespecial <init> : ()V
    //   165: astore #12
    //   167: aconst_null
    //   168: astore_0
    //   169: iconst_0
    //   170: istore_3
    //   171: iload_3
    //   172: aload #8
    //   174: invokevirtual length : ()I
    //   177: if_icmpge -> 374
    //   180: aload #8
    //   182: bipush #44
    //   184: iload_3
    //   185: invokevirtual indexOf : (II)I
    //   188: istore #5
    //   190: iload #5
    //   192: istore #4
    //   194: iload #5
    //   196: iconst_m1
    //   197: if_icmpne -> 207
    //   200: aload #8
    //   202: invokevirtual length : ()I
    //   205: istore #4
    //   207: aload #8
    //   209: iload_3
    //   210: iload #4
    //   212: invokevirtual substring : (II)Ljava/lang/String;
    //   215: astore #13
    //   217: aload #13
    //   219: invokestatic forName : (Ljava/lang/String;)Ljava/lang/Class;
    //   222: astore_1
    //   223: aload_1
    //   224: invokevirtual newInstance : ()Ljava/lang/Object;
    //   227: astore #9
    //   229: goto -> 237
    //   232: aconst_null
    //   233: astore_1
    //   234: aconst_null
    //   235: astore #9
    //   237: aload_0
    //   238: astore #10
    //   240: aload_1
    //   241: ifnull -> 363
    //   244: aload #9
    //   246: instanceof com/sk/spatch/xmltool/v1/XmlPullParser
    //   249: istore #6
    //   251: iconst_1
    //   252: istore #5
    //   254: iload #6
    //   256: ifeq -> 270
    //   259: aload #11
    //   261: aload_1
    //   262: invokevirtual addElement : (Ljava/lang/Object;)V
    //   265: iconst_1
    //   266: istore_3
    //   267: goto -> 272
    //   270: iconst_0
    //   271: istore_3
    //   272: aload #9
    //   274: instanceof com/sk/spatch/xmltool/v1/XmlSerializer
    //   277: ifeq -> 288
    //   280: aload #12
    //   282: aload_1
    //   283: invokevirtual addElement : (Ljava/lang/Object;)V
    //   286: iconst_1
    //   287: istore_3
    //   288: aload #9
    //   290: instanceof com/sk/spatch/xmltool/v1/XmlPullParserFactory
    //   293: ifeq -> 317
    //   296: aload_0
    //   297: astore_1
    //   298: iload #5
    //   300: istore_3
    //   301: aload_0
    //   302: ifnonnull -> 319
    //   305: aload #9
    //   307: checkcast com/sk/spatch/xmltool/v1/XmlPullParserFactory
    //   310: astore_1
    //   311: iload #5
    //   313: istore_3
    //   314: goto -> 319
    //   317: aload_0
    //   318: astore_1
    //   319: iload_3
    //   320: ifeq -> 329
    //   323: aload_1
    //   324: astore #10
    //   326: goto -> 363
    //   329: new java/lang/StringBuilder
    //   332: dup
    //   333: invokespecial <init> : ()V
    //   336: astore_0
    //   337: aload_0
    //   338: ldc 'incompatible class: '
    //   340: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   343: pop
    //   344: aload_0
    //   345: aload #13
    //   347: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   350: pop
    //   351: new com/sk/spatch/xmltool/v1/XmlPullParserException
    //   354: dup
    //   355: aload_0
    //   356: invokevirtual toString : ()Ljava/lang/String;
    //   359: invokespecial <init> : (Ljava/lang/String;)V
    //   362: athrow
    //   363: iload #4
    //   365: iconst_1
    //   366: iadd
    //   367: istore_3
    //   368: aload #10
    //   370: astore_0
    //   371: goto -> 171
    //   374: aload_0
    //   375: astore_1
    //   376: aload_0
    //   377: ifnonnull -> 388
    //   380: new com/sk/spatch/xmltool/v1/XmlPullParserFactory
    //   383: dup
    //   384: invokespecial <init> : ()V
    //   387: astore_1
    //   388: aload_1
    //   389: aload #11
    //   391: putfield parserClasses : Ljava/util/Vector;
    //   394: aload_1
    //   395: aload #12
    //   397: putfield serializerClasses : Ljava/util/Vector;
    //   400: aload_1
    //   401: aload #7
    //   403: putfield classNamesLocation : Ljava/lang/String;
    //   406: aload_1
    //   407: areturn
    //   408: iload_3
    //   409: bipush #32
    //   411: if_icmple -> 95
    //   414: iload_3
    //   415: i2c
    //   416: istore_2
    //   417: aload_1
    //   418: iload_2
    //   419: invokevirtual append : (C)Ljava/lang/StringBuffer;
    //   422: pop
    //   423: goto -> 95
    //   426: new com/sk/spatch/xmltool/v1/XmlPullParserException
    //   429: dup
    //   430: ldc 'resource not found: /META-INF/services/org.xmlpull.v1.XmlPullParserFactory make sure that parser implementing XmlPull API is available'
    //   432: invokespecial <init> : (Ljava/lang/String;)V
    //   435: athrow
    //   436: astore_0
    //   437: new com/sk/spatch/xmltool/v1/XmlPullParserException
    //   440: dup
    //   441: aconst_null
    //   442: aconst_null
    //   443: aload_0
    //   444: invokespecial <init> : (Ljava/lang/String;Lcom/sk/spatch/xmltool/v1/XmlPullParser;Ljava/lang/Throwable;)V
    //   447: athrow
    //   448: astore_1
    //   449: goto -> 232
    //   452: astore #9
    //   454: goto -> 234
    // Exception table:
    //   from	to	target	type
    //   75	83	436	java/lang/Exception
    //   87	95	436	java/lang/Exception
    //   95	100	436	java/lang/Exception
    //   104	114	436	java/lang/Exception
    //   217	223	448	java/lang/Exception
    //   223	229	452	java/lang/Exception
    //   417	423	436	java/lang/Exception
    //   426	436	436	java/lang/Exception }
  
  public boolean getFeature(String paramString) {
    Boolean bool = (Boolean)this.features.get(paramString);
    return (bool != null) ? bool.booleanValue() : false;
  }
  
  public boolean isNamespaceAware() { return getFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces"); }
  
  public boolean isValidating() { return getFeature("http://xmlpull.org/v1/doc/features.html#validation"); }
  
  public XmlPullParser newPullParser() throws XmlPullParserException {
    Vector vector = this.parserClasses;
    if (vector != null) {
      if (vector.size() != 0) {
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (i < this.parserClasses.size()) {
          Class<XmlPullParser> clazz = (Class)this.parserClasses.elementAt(i);
          try {
            XmlPullParser xmlPullParser = clazz.newInstance();
            Enumeration<String> enumeration = this.features.keys();
            while (enumeration.hasMoreElements()) {
              String str = enumeration.nextElement();
              Boolean bool = (Boolean)this.features.get(str);
              if (bool != null && bool.booleanValue())
                xmlPullParser.setFeature(str, true); 
            } 
            return xmlPullParser;
          } catch (Exception exception) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(clazz.getName());
            stringBuilder3.append(": ");
            stringBuilder3.append(exception.toString());
            stringBuilder3.append("; ");
            stringBuffer.append(stringBuilder3.toString());
            i++;
          } 
        } 
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("could not create parser: ");
        stringBuilder2.append(stringBuffer);
        throw new XmlPullParserException(stringBuilder2.toString());
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("No valid parser classes found in ");
      stringBuilder1.append(this.classNamesLocation);
      throw new XmlPullParserException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Factory initialization was incomplete - has not tried ");
    stringBuilder.append(this.classNamesLocation);
    throw new XmlPullParserException(stringBuilder.toString());
  }
  
  public XmlSerializer newSerializer() throws XmlPullParserException {
    Vector vector = this.serializerClasses;
    if (vector != null) {
      if (vector.size() != 0) {
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (i < this.serializerClasses.size()) {
          Class<XmlSerializer> clazz = (Class)this.serializerClasses.elementAt(i);
          try {
            return clazz.newInstance();
          } catch (Exception exception) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(clazz.getName());
            stringBuilder3.append(": ");
            stringBuilder3.append(exception.toString());
            stringBuilder3.append("; ");
            stringBuffer.append(stringBuilder3.toString());
            i++;
          } 
        } 
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("could not create serializer: ");
        stringBuilder2.append(stringBuffer);
        throw new XmlPullParserException(stringBuilder2.toString());
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("No valid serializer classes found in ");
      stringBuilder1.append(this.classNamesLocation);
      throw new XmlPullParserException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Factory initialization incomplete - has not tried ");
    stringBuilder.append(this.classNamesLocation);
    throw new XmlPullParserException(stringBuilder.toString());
  }
  
  public void setFeature(String paramString, boolean paramBoolean) throws XmlPullParserException { this.features.put(paramString, new Boolean(paramBoolean)); }
  
  public void setNamespaceAware(boolean paramBoolean) { this.features.put("http://xmlpull.org/v1/doc/features.html#process-namespaces", new Boolean(paramBoolean)); }
  
  public void setValidating(boolean paramBoolean) { this.features.put("http://xmlpull.org/v1/doc/features.html#validation", new Boolean(paramBoolean)); }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\v1\XmlPullParserFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */