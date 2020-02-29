package com.sk.spatch.xmltool.main;

import java.io.File;

public class editXMLMain {
  private static final String CMD_TXT = "[usage java -jar AXMLEditor.jar [-tag|-attr] [-i|-r|-m] [标签名|标签唯一ID|属性名|属性值] [输入文件|输出文件]";
  
  public static void doCommand(String[] paramArrayOfString) {
    String str;
    if ("-tag".equals(paramArrayOfString[0])) {
      if (paramArrayOfString.length < 2) {
        System.out.println("缺少参数...");
        System.out.println("[usage java -jar AXMLEditor.jar [-tag|-attr] [-i|-r|-m] [标签名|标签唯一ID|属性名|属性值] [输入文件|输出文件]");
        return;
      } 
      if ("-i".equals(paramArrayOfString[1])) {
        if (paramArrayOfString.length < 3) {
          System.out.println("缺少参数...");
          System.out.println("[usage java -jar AXMLEditor.jar [-tag|-attr] [-i|-r|-m] [标签名|标签唯一ID|属性名|属性值] [输入文件|输出文件]");
          return;
        } 
        str = paramArrayOfString[2];
        if (!(new File(str)).exists()) {
          System.out.println("插入标签xml文件不存在...");
          return;
        } 
        XmlEditor.addTag(str);
        System.out.println("插入标签完成...");
        return;
      } 
      if ("-r".equals(str[1])) {
        if (str.length < 4) {
          System.out.println("缺少参数...");
          System.out.println("[usage java -jar AXMLEditor.jar [-tag|-attr] [-i|-r|-m] [标签名|标签唯一ID|属性名|属性值] [输入文件|输出文件]");
          return;
        } 
        XmlEditor.removeTag(str[2], str[3]);
        System.out.println("删除标签完成...");
        return;
      } 
      System.out.println("操作标签参数有误...");
      System.out.println("[usage java -jar AXMLEditor.jar [-tag|-attr] [-i|-r|-m] [标签名|标签唯一ID|属性名|属性值] [输入文件|输出文件]");
      return;
    } 
    if ("-attr".equals(str[0])) {
      if (str.length < 2) {
        System.out.println("缺少参数...");
        System.out.println("[usage java -jar AXMLEditor.jar [-tag|-attr] [-i|-r|-m] [标签名|标签唯一ID|属性名|属性值] [输入文件|输出文件]");
        return;
      } 
      if ("-i".equals(str[1])) {
        if (str.length < 6) {
          System.out.println("缺少参数...");
          System.out.println("[usage java -jar AXMLEditor.jar [-tag|-attr] [-i|-r|-m] [标签名|标签唯一ID|属性名|属性值] [输入文件|输出文件]");
          return;
        } 
        XmlEditor.addAttr(str[2], str[3], str[4], str[5]);
        System.out.println("插入属性完成...");
        return;
      } 
      if ("-r".equals(str[1])) {
        if (str.length < 5) {
          System.out.println("缺少参数...");
          System.out.println("[usage java -jar AXMLEditor.jar [-tag|-attr] [-i|-r|-m] [标签名|标签唯一ID|属性名|属性值] [输入文件|输出文件]");
          return;
        } 
        XmlEditor.removeAttr(str[2], str[3], str[4]);
        System.out.println("删除属性完成...");
        return;
      } 
      if ("-m".equals(str[1])) {
        if (str.length < 6) {
          System.out.println("缺少参数...");
          System.out.println("[usage java -jar AXMLEditor.jar [-tag|-attr] [-i|-r|-m] [标签名|标签唯一ID|属性名|属性值] [输入文件|输出文件]");
          return;
        } 
        XmlEditor.modifyAttr(str[2], str[3], str[4], str[5]);
        System.out.println("修改属性完成...");
        return;
      } 
      System.out.println("操作属性参数有误...");
      System.out.println("[usage java -jar AXMLEditor.jar [-tag|-attr] [-i|-r|-m] [标签名|标签唯一ID|属性名|属性值] [输入文件|输出文件]");
    } 
  }
  
  public static void main(String[] paramArrayOfString) { // Byte code:
    //   0: aload_0
    //   1: arraylength
    //   2: iconst_3
    //   3: if_icmpge -> 23
    //   6: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   9: ldc '参数有误...'
    //   11: invokevirtual println : (Ljava/lang/String;)V
    //   14: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   17: ldc '[usage java -jar AXMLEditor.jar [-tag|-attr] [-i|-r|-m] [标签名|标签唯一ID|属性名|属性值] [输入文件|输出文件]'
    //   19: invokevirtual println : (Ljava/lang/String;)V
    //   22: return
    //   23: aload_0
    //   24: aload_0
    //   25: arraylength
    //   26: iconst_2
    //   27: isub
    //   28: aaload
    //   29: astore_3
    //   30: aload_0
    //   31: aload_0
    //   32: arraylength
    //   33: iconst_1
    //   34: isub
    //   35: aaload
    //   36: astore_2
    //   37: new java/io/File
    //   40: dup
    //   41: aload_3
    //   42: invokespecial <init> : (Ljava/lang/String;)V
    //   45: astore_3
    //   46: new java/io/File
    //   49: dup
    //   50: aload_2
    //   51: invokespecial <init> : (Ljava/lang/String;)V
    //   54: astore #9
    //   56: aload_3
    //   57: invokevirtual exists : ()Z
    //   60: ifne -> 72
    //   63: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   66: ldc '输入文件不存在...'
    //   68: invokevirtual println : (Ljava/lang/String;)V
    //   71: return
    //   72: aconst_null
    //   73: astore #8
    //   75: aconst_null
    //   76: astore_2
    //   77: aconst_null
    //   78: astore #7
    //   80: new java/io/FileInputStream
    //   83: dup
    //   84: aload_3
    //   85: invokespecial <init> : (Ljava/io/File;)V
    //   88: astore_3
    //   89: new java/io/ByteArrayOutputStream
    //   92: dup
    //   93: invokespecial <init> : ()V
    //   96: astore_2
    //   97: aload_2
    //   98: astore #4
    //   100: aload_3
    //   101: astore #5
    //   103: sipush #1024
    //   106: newarray byte
    //   108: astore #6
    //   110: aload_2
    //   111: astore #4
    //   113: aload_3
    //   114: astore #5
    //   116: aload_3
    //   117: aload #6
    //   119: invokevirtual read : ([B)I
    //   122: istore_1
    //   123: iload_1
    //   124: iconst_m1
    //   125: if_icmpeq -> 145
    //   128: aload_2
    //   129: astore #4
    //   131: aload_3
    //   132: astore #5
    //   134: aload_2
    //   135: aload #6
    //   137: iconst_0
    //   138: iload_1
    //   139: invokevirtual write : ([BII)V
    //   142: goto -> 110
    //   145: aload_2
    //   146: astore #4
    //   148: aload_3
    //   149: astore #5
    //   151: getstatic com/sk/spatch/xmltool/main/ParserChunkUtils.xmlStruct : Lcom/sk/spatch/xmltool/chunk/XmlStruct;
    //   154: aload_2
    //   155: invokevirtual toByteArray : ()[B
    //   158: putfield byteSrc : [B
    //   161: aload_3
    //   162: invokevirtual close : ()V
    //   165: aload_2
    //   166: invokevirtual close : ()V
    //   169: goto -> 282
    //   172: astore #6
    //   174: goto -> 206
    //   177: astore_0
    //   178: aconst_null
    //   179: astore #4
    //   181: goto -> 381
    //   184: astore #6
    //   186: aconst_null
    //   187: astore_2
    //   188: goto -> 206
    //   191: astore_0
    //   192: aconst_null
    //   193: astore #4
    //   195: aload_2
    //   196: astore_3
    //   197: goto -> 381
    //   200: astore #6
    //   202: aconst_null
    //   203: astore_2
    //   204: aload_2
    //   205: astore_3
    //   206: aload_2
    //   207: astore #4
    //   209: aload_3
    //   210: astore #5
    //   212: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   215: astore #10
    //   217: aload_2
    //   218: astore #4
    //   220: aload_3
    //   221: astore #5
    //   223: new java/lang/StringBuilder
    //   226: dup
    //   227: invokespecial <init> : ()V
    //   230: astore #11
    //   232: aload_2
    //   233: astore #4
    //   235: aload_3
    //   236: astore #5
    //   238: aload #11
    //   240: ldc 'parse xml error:'
    //   242: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   245: pop
    //   246: aload_2
    //   247: astore #4
    //   249: aload_3
    //   250: astore #5
    //   252: aload #11
    //   254: aload #6
    //   256: invokevirtual toString : ()Ljava/lang/String;
    //   259: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   262: pop
    //   263: aload_2
    //   264: astore #4
    //   266: aload_3
    //   267: astore #5
    //   269: aload #10
    //   271: aload #11
    //   273: invokevirtual toString : ()Ljava/lang/String;
    //   276: invokevirtual println : (Ljava/lang/String;)V
    //   279: goto -> 161
    //   282: aload_0
    //   283: invokestatic doCommand : ([Ljava/lang/String;)V
    //   286: aload #9
    //   288: invokevirtual exists : ()Z
    //   291: ifne -> 300
    //   294: aload #9
    //   296: invokevirtual delete : ()Z
    //   299: pop
    //   300: new java/io/FileOutputStream
    //   303: dup
    //   304: aload #9
    //   306: invokespecial <init> : (Ljava/io/File;)V
    //   309: astore_0
    //   310: aload_0
    //   311: getstatic com/sk/spatch/xmltool/main/ParserChunkUtils.xmlStruct : Lcom/sk/spatch/xmltool/chunk/XmlStruct;
    //   314: getfield byteSrc : [B
    //   317: invokevirtual write : ([B)V
    //   320: aload_0
    //   321: invokevirtual close : ()V
    //   324: aload_0
    //   325: invokevirtual close : ()V
    //   328: return
    //   329: astore_3
    //   330: aload_0
    //   331: astore_2
    //   332: aload_3
    //   333: astore_0
    //   334: goto -> 344
    //   337: goto -> 362
    //   340: astore_0
    //   341: aload #7
    //   343: astore_2
    //   344: aload_2
    //   345: ifnull -> 360
    //   348: aload_2
    //   349: invokevirtual close : ()V
    //   352: goto -> 360
    //   355: astore_2
    //   356: aload_2
    //   357: invokevirtual printStackTrace : ()V
    //   360: aload_0
    //   361: athrow
    //   362: aload_0
    //   363: ifnull -> 376
    //   366: aload_0
    //   367: invokevirtual close : ()V
    //   370: return
    //   371: astore_0
    //   372: aload_0
    //   373: invokevirtual printStackTrace : ()V
    //   376: return
    //   377: astore_0
    //   378: aload #5
    //   380: astore_3
    //   381: aload_3
    //   382: invokevirtual close : ()V
    //   385: aload #4
    //   387: invokevirtual close : ()V
    //   390: aload_0
    //   391: athrow
    //   392: astore_2
    //   393: goto -> 282
    //   396: astore_0
    //   397: aload #8
    //   399: astore_0
    //   400: goto -> 362
    //   403: astore_2
    //   404: goto -> 337
    //   407: astore_2
    //   408: goto -> 390
    // Exception table:
    //   from	to	target	type
    //   80	89	200	java/lang/Exception
    //   80	89	191	finally
    //   89	97	184	java/lang/Exception
    //   89	97	177	finally
    //   103	110	172	java/lang/Exception
    //   103	110	377	finally
    //   116	123	172	java/lang/Exception
    //   116	123	377	finally
    //   134	142	172	java/lang/Exception
    //   134	142	377	finally
    //   151	161	172	java/lang/Exception
    //   151	161	377	finally
    //   161	169	392	java/lang/Exception
    //   212	217	377	finally
    //   223	232	377	finally
    //   238	246	377	finally
    //   252	263	377	finally
    //   269	279	377	finally
    //   300	310	396	java/lang/Exception
    //   300	310	340	finally
    //   310	324	403	java/lang/Exception
    //   310	324	329	finally
    //   324	328	371	java/io/IOException
    //   348	352	355	java/io/IOException
    //   366	370	371	java/io/IOException
    //   381	390	407	java/lang/Exception }
  
  public static void testDemo() {}
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\main\editXMLMain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */