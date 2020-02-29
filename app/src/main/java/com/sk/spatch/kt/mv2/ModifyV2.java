package com.sk.spatch.kt.mv2;

import android.content.Context;
import android.os.Build;
import com.sk.spatch.kt.srcs.task.SaveApkSignatureTask;
import com.sk.spatch.kt.srcs.task.SoAndDexCopyTask;
import com.sk.spatch.kt.srcs.util.ApkSignatureHelper;
import com.sk.spatch.kt.srcs.util.FileUtils;
import com.sk.spatch.xmltool.android.content.res.AXmlResourceParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;

public class ModifyV2 {
  public static final String AndroidManifestFileName = "AndroidManifest.xml";
  
  public static final String BackupApkPath;
  
  private static final String[] DIMENSION_UNITS;
  
  private static final String[] FRACTION_UNITS;
  
  private static final float[] RADIX_MULTS;
  
  public static final String TempAndroidJarPath;
  
  public static final String TempApkUnsigned;
  
  public static final String TempAppNameStorePath;
  
  public static final String TempCompiledPath;
  
  public static final String TempLibAaptPath;
  
  private static boolean customApplication;
  
  private static String customApplicationName;
  
  public static boolean isDebug;
  
  private static String packageName;
  
  public static final String szManifestBufferPath;
  
  public static final String szOutManifestPath;
  
  public static String szPatchPath;
  
  public static final String szPrinterFileBuffer;
  
  public static cleanBufferCallback theCallback;
  
  public static String[] xposedModLists;
  
  static  {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(File.separatorChar);
    stringBuilder.append("xmltemp");
    szManifestBufferPath = stringBuilder.toString();
    stringBuilder = new StringBuilder();
    stringBuilder.append(File.separatorChar);
    stringBuilder.append("AndroidManifest.xml");
    szPrinterFileBuffer = stringBuilder.toString();
    stringBuilder = new StringBuilder();
    stringBuilder.append(File.separatorChar);
    stringBuilder.append("AndroidManifest_M.xml");
    szOutManifestPath = stringBuilder.toString();
    stringBuilder = new StringBuilder();
    stringBuilder.append(File.separatorChar);
    stringBuilder.append("saapt.run");
    TempLibAaptPath = stringBuilder.toString();
    stringBuilder = new StringBuilder();
    stringBuilder.append(File.separatorChar);
    stringBuilder.append("tempCpl.xml");
    TempCompiledPath = stringBuilder.toString();
    stringBuilder = new StringBuilder();
    stringBuilder.append(File.separatorChar);
    stringBuilder.append("android.jar");
    TempAndroidJarPath = stringBuilder.toString();
    stringBuilder = new StringBuilder();
    stringBuilder.append(File.separatorChar);
    stringBuilder.append("assets");
    stringBuilder.append(File.separatorChar);
    stringBuilder.append("AppName.txt");
    TempAppNameStorePath = stringBuilder.toString();
    stringBuilder = new StringBuilder();
    stringBuilder.append(File.separatorChar);
    stringBuilder.append("unsigned.apk");
    TempApkUnsigned = stringBuilder.toString();
    stringBuilder = new StringBuilder();
    stringBuilder.append(File.separatorChar);
    stringBuilder.append("assets");
    stringBuilder.append(File.separatorChar);
    stringBuilder.append("backup.apk");
    BackupApkPath = stringBuilder.toString();
    isDebug = true;
    szPatchPath = null;
    xposedModLists = null;
    RADIX_MULTS = new float[] { 0.00390625F, 3.051758E-5F, 1.192093E-7F, 4.656613E-10F };
    DIMENSION_UNITS = new String[] { "px", "dip", "sp", "pt", "in", "mm", "", "" };
    FRACTION_UNITS = new String[] { "%", "%p", "", "", "", "", "", "" };
    theCallback = null;
    customApplication = false;
  }
  
  public static void UnZipAssetsFolder(Context paramContext, String paramString1, String paramString2) throws Exception {
    ClassLoader classLoader = paramContext.getClassLoader();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("assets");
    stringBuilder.append(File.separatorChar);
    stringBuilder.append(paramString1);
    InputStream inputStream = classLoader.getResourceAsStream(stringBuilder.toString());
    FileOutputStream fileOutputStream = new FileOutputStream(new File(paramString2));
    byte[] arrayOfByte = new byte[1024];
    while (true) {
      int i = inputStream.read(arrayOfByte);
      if (i != -1) {
        fileOutputStream.write(arrayOfByte, 0, i);
        fileOutputStream.flush();
        continue;
      } 
      break;
    } 
    inputStream.close();
    fileOutputStream.close();
  }
  
  public static float complexToFloat(int paramInt) { return (paramInt & 0xFFFFFF00) * RADIX_MULTS[paramInt >> 4 & 0x3]; }
  
  private static void copyModToBasePath(String paramString) throws Exception {
    String[] arrayOfString = xposedModLists;
    if (arrayOfString != null && arrayOfString.length > 0) {
      int k = arrayOfString.length;
      int i = 0;
      int j = i;
      while (i < k) {
        String str = arrayOfString[i].trim();
        if (str.length() != 0) {
          File file = new File(str);
          if (file.exists()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(SoAndDexCopyTask.getSpModNameSfx());
            stringBuilder.append(j);
            stringBuilder.append(".so");
            File file1 = new File(paramString, stringBuilder.toString());
            if (Build.VERSION.SDK_INT >= 26) {
              Files.copy(file.toPath(), file1.toPath(), new java.nio.file.CopyOption[0]);
            } else {
              FileUtils.copyFile(file, file1);
            } 
            j++;
          } 
        } 
        i++;
      } 
    } 
  }
  
  public static boolean decompileXML(String paramString1, String paramString2) {
    try {
      return xmlDecompiler(paramString1, paramString2);
    } finally {
      paramString1 = null;
      paramString1.printStackTrace();
    } 
  }
  
  public static void doCleanBeforeStart(String paramString1, String paramString2) {
    try {
      FileUtils.deleteDir(new File(paramString1));
      (new File(paramString1)).mkdirs();
      if ((new File(paramString2)).exists())
        return; 
    } finally {
      paramString1 = null;
    } 
  }
  
  public static native String getApplicationNameFromFile(String paramString);
  
  private static String getAttributeValue(AXmlResourceParser paramAXmlResourceParser, int paramInt) {
    int i = paramAXmlResourceParser.getAttributeValueType(paramInt);
    int j = paramAXmlResourceParser.getAttributeValueData(paramInt);
    if (i == 3)
      return paramAXmlResourceParser.getAttributeValue(paramInt); 
    if (i == 2)
      return String.format("?%s%08X", new Object[] { getPackage(j), Integer.valueOf(j) }); 
    if (i == 1)
      return String.format("@%s%08X", new Object[] { getPackage(j), Integer.valueOf(j) }); 
    if (i == 4)
      return String.valueOf(Float.intBitsToFloat(j)); 
    if (i == 17)
      return String.format("0x%08X", new Object[] { Integer.valueOf(j) }); 
    if (i == 18)
      return (j != 0) ? "true" : "false"; 
    if (i == 5) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(Float.toString(complexToFloat(j)));
      stringBuilder.append(DIMENSION_UNITS[j & 0xF]);
      return stringBuilder.toString();
    } 
    if (i == 6) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(Float.toString(complexToFloat(j)));
      stringBuilder.append(FRACTION_UNITS[j & 0xF]);
      return stringBuilder.toString();
    } 
    return (i >= 28 && i <= 31) ? String.format("#%08X", new Object[] { Integer.valueOf(j) }) : ((i >= 16 && i <= 31) ? String.valueOf(j) : String.format("<0x%X, type 0x%02X>", new Object[] { Integer.valueOf(j), Integer.valueOf(i) }));
  }
  
  private static String getNamespacePrefix(String paramString) {
    if (paramString == null || paramString.length() == 0)
      return ""; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append(":");
    return stringBuilder.toString();
  }
  
  private static String getPackage(int paramInt) { return (paramInt >>> 24 == 1) ? "android:" : ""; }
  
  public static native String getX86Arm();
  
  private static byte[] parseManifest(InputStream paramInputStream) throws Exception { // Byte code:
    //   0: iconst_0
    //   1: istore_2
    //   2: iconst_0
    //   3: istore #4
    //   5: iconst_0
    //   6: putstatic com/sk/spatch/kt/mv2/ModifyV2.customApplication : Z
    //   9: aconst_null
    //   10: putstatic com/sk/spatch/kt/mv2/ModifyV2.customApplicationName : Ljava/lang/String;
    //   13: aload_0
    //   14: invokestatic decode : (Ljava/io/InputStream;)Lcom/sk/spatch/xmltool/decode/AXmlDecoder;
    //   17: astore_0
    //   18: new com/sk/spatch/xmltool/decode/AXmlResourceParser
    //   21: dup
    //   22: invokespecial <init> : ()V
    //   25: astore #9
    //   27: aload #9
    //   29: new java/io/ByteArrayInputStream
    //   32: dup
    //   33: aload_0
    //   34: invokevirtual getData : ()[B
    //   37: invokespecial <init> : ([B)V
    //   40: aload_0
    //   41: getfield mTableStrings : Lcom/sk/spatch/xmltool/decode/StringDecoder;
    //   44: invokevirtual open : (Ljava/io/InputStream;Lcom/sk/spatch/xmltool/decode/StringDecoder;)V
    //   47: aload #9
    //   49: invokevirtual next : ()I
    //   52: istore_3
    //   53: iload_2
    //   54: istore_1
    //   55: iload_3
    //   56: iconst_1
    //   57: if_icmpeq -> 538
    //   60: iload_3
    //   61: iconst_2
    //   62: if_icmpeq -> 68
    //   65: goto -> 47
    //   68: aload #9
    //   70: invokevirtual getName : ()Ljava/lang/String;
    //   73: ldc_w 'manifest'
    //   76: invokevirtual equals : (Ljava/lang/Object;)Z
    //   79: ifeq -> 126
    //   82: aload #9
    //   84: invokevirtual getAttributeCount : ()I
    //   87: istore_3
    //   88: iconst_0
    //   89: istore_1
    //   90: iload_1
    //   91: iload_3
    //   92: if_icmpge -> 47
    //   95: aload #9
    //   97: iload_1
    //   98: invokevirtual getAttributeName : (I)Ljava/lang/String;
    //   101: ldc_w 'package'
    //   104: invokevirtual equals : (Ljava/lang/Object;)Z
    //   107: ifeq -> 119
    //   110: aload #9
    //   112: iload_1
    //   113: invokevirtual getAttributeValue : (I)Ljava/lang/String;
    //   116: putstatic com/sk/spatch/kt/mv2/ModifyV2.packageName : Ljava/lang/String;
    //   119: iload_1
    //   120: iconst_1
    //   121: iadd
    //   122: istore_1
    //   123: goto -> 90
    //   126: aload #9
    //   128: invokevirtual getName : ()Ljava/lang/String;
    //   131: ldc_w 'application'
    //   134: invokevirtual equals : (Ljava/lang/Object;)Z
    //   137: ifeq -> 47
    //   140: aload #9
    //   142: invokevirtual getAttributeCount : ()I
    //   145: istore #6
    //   147: iconst_0
    //   148: istore_1
    //   149: iload_1
    //   150: iload #6
    //   152: if_icmpge -> 232
    //   155: aload #9
    //   157: iload_1
    //   158: invokevirtual getAttributeNameResource : (I)I
    //   161: ldc_w 16842755
    //   164: if_icmpne -> 225
    //   167: iconst_1
    //   168: putstatic com/sk/spatch/kt/mv2/ModifyV2.customApplication : Z
    //   171: aload #9
    //   173: iload_1
    //   174: invokevirtual getAttributeValue : (I)Ljava/lang/String;
    //   177: putstatic com/sk/spatch/kt/mv2/ModifyV2.customApplicationName : Ljava/lang/String;
    //   180: aload_0
    //   181: getfield mTableStrings : Lcom/sk/spatch/xmltool/decode/StringDecoder;
    //   184: invokevirtual getSize : ()I
    //   187: istore_2
    //   188: aload_0
    //   189: invokevirtual getData : ()[B
    //   192: astore #10
    //   194: aload #9
    //   196: getfield currentAttributeStart : I
    //   199: iload_1
    //   200: bipush #20
    //   202: imul
    //   203: iadd
    //   204: bipush #8
    //   206: iadd
    //   207: istore_3
    //   208: aload #10
    //   210: iload_3
    //   211: iload_2
    //   212: invokestatic writeInt : ([BII)V
    //   215: aload #10
    //   217: iload_3
    //   218: bipush #8
    //   220: iadd
    //   221: iload_2
    //   222: invokestatic writeInt : ([BII)V
    //   225: iload_1
    //   226: iconst_1
    //   227: iadd
    //   228: istore_1
    //   229: goto -> 149
    //   232: getstatic com/sk/spatch/kt/mv2/ModifyV2.customApplication : Z
    //   235: ifne -> 536
    //   238: aload #9
    //   240: getfield currentAttributeStart : I
    //   243: istore #5
    //   245: aload_0
    //   246: invokevirtual getData : ()[B
    //   249: astore #10
    //   251: aload #10
    //   253: arraylength
    //   254: bipush #20
    //   256: iadd
    //   257: newarray byte
    //   259: astore #11
    //   261: aload #10
    //   263: iconst_0
    //   264: aload #11
    //   266: iconst_0
    //   267: iload #5
    //   269: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
    //   272: iload #5
    //   274: bipush #20
    //   276: iadd
    //   277: istore #8
    //   279: aload #10
    //   281: iload #5
    //   283: aload #11
    //   285: iload #8
    //   287: aload #10
    //   289: arraylength
    //   290: iload #5
    //   292: isub
    //   293: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
    //   296: iload #5
    //   298: bipush #32
    //   300: isub
    //   301: istore_1
    //   302: aload #11
    //   304: iload_1
    //   305: aload #11
    //   307: iload_1
    //   308: invokestatic readInt : ([BI)I
    //   311: bipush #20
    //   313: iadd
    //   314: invokestatic writeInt : ([BII)V
    //   317: aload #11
    //   319: iload #5
    //   321: bipush #8
    //   323: isub
    //   324: iload #6
    //   326: iconst_1
    //   327: iadd
    //   328: invokestatic writeInt : ([BII)V
    //   331: aload #9
    //   333: ldc_w 16842755
    //   336: invokevirtual findResourceID : (I)I
    //   339: istore #7
    //   341: iload #7
    //   343: iconst_m1
    //   344: if_icmpeq -> 525
    //   347: iconst_0
    //   348: istore_3
    //   349: iload_3
    //   350: iload #6
    //   352: if_icmpge -> 412
    //   355: aload #9
    //   357: iload_3
    //   358: invokevirtual getAttributeNameResource : (I)I
    //   361: ldc_w 16842755
    //   364: if_icmple -> 405
    //   367: iload #4
    //   369: istore_2
    //   370: iload #5
    //   372: istore_1
    //   373: iload_3
    //   374: ifeq -> 417
    //   377: iload_3
    //   378: bipush #20
    //   380: imul
    //   381: istore_1
    //   382: aload #11
    //   384: iload #8
    //   386: aload #11
    //   388: iload #5
    //   390: iload_1
    //   391: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
    //   394: iload #5
    //   396: iload_1
    //   397: iadd
    //   398: istore_1
    //   399: iload #4
    //   401: istore_2
    //   402: goto -> 417
    //   405: iload_3
    //   406: iconst_1
    //   407: iadd
    //   408: istore_3
    //   409: goto -> 349
    //   412: iconst_1
    //   413: istore_2
    //   414: iload #5
    //   416: istore_1
    //   417: iload_1
    //   418: istore_3
    //   419: iload_2
    //   420: ifeq -> 446
    //   423: iload #6
    //   425: bipush #20
    //   427: imul
    //   428: istore_2
    //   429: aload #11
    //   431: iload_1
    //   432: bipush #20
    //   434: iadd
    //   435: aload #11
    //   437: iload_1
    //   438: iload_2
    //   439: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
    //   442: iload_1
    //   443: iload_2
    //   444: iadd
    //   445: istore_3
    //   446: aload #11
    //   448: iload_3
    //   449: aload_0
    //   450: getfield mTableStrings : Lcom/sk/spatch/xmltool/decode/StringDecoder;
    //   453: ldc_w 'http://schemas.android.com/apk/res/android'
    //   456: invokevirtual find : (Ljava/lang/String;)I
    //   459: invokestatic writeInt : ([BII)V
    //   462: aload #11
    //   464: iload_3
    //   465: iconst_4
    //   466: iadd
    //   467: iload #7
    //   469: invokestatic writeInt : ([BII)V
    //   472: aload #11
    //   474: iload_3
    //   475: bipush #8
    //   477: iadd
    //   478: aload_0
    //   479: getfield mTableStrings : Lcom/sk/spatch/xmltool/decode/StringDecoder;
    //   482: invokevirtual getSize : ()I
    //   485: invokestatic writeInt : ([BII)V
    //   488: aload #11
    //   490: iload_3
    //   491: bipush #12
    //   493: iadd
    //   494: ldc_w 50331656
    //   497: invokestatic writeInt : ([BII)V
    //   500: aload #11
    //   502: iload_3
    //   503: bipush #16
    //   505: iadd
    //   506: aload_0
    //   507: getfield mTableStrings : Lcom/sk/spatch/xmltool/decode/StringDecoder;
    //   510: invokevirtual getSize : ()I
    //   513: invokestatic writeInt : ([BII)V
    //   516: aload_0
    //   517: aload #11
    //   519: invokevirtual setData : ([B)V
    //   522: goto -> 536
    //   525: new java/lang/Exception
    //   528: dup
    //   529: ldc_w 'idIndex == -1'
    //   532: invokespecial <init> : (Ljava/lang/String;)V
    //   535: athrow
    //   536: iconst_1
    //   537: istore_1
    //   538: iload_1
    //   539: ifeq -> 599
    //   542: new java/util/ArrayList
    //   545: dup
    //   546: aload_0
    //   547: getfield mTableStrings : Lcom/sk/spatch/xmltool/decode/StringDecoder;
    //   550: invokevirtual getSize : ()I
    //   553: invokespecial <init> : (I)V
    //   556: astore #9
    //   558: aload_0
    //   559: getfield mTableStrings : Lcom/sk/spatch/xmltool/decode/StringDecoder;
    //   562: aload #9
    //   564: invokevirtual getStrings : (Ljava/util/List;)V
    //   567: aload #9
    //   569: ldc_w 'com.sk.skmain.SKApp'
    //   572: invokevirtual add : (Ljava/lang/Object;)Z
    //   575: pop
    //   576: new java/io/ByteArrayOutputStream
    //   579: dup
    //   580: invokespecial <init> : ()V
    //   583: astore #10
    //   585: aload_0
    //   586: aload #9
    //   588: aload #10
    //   590: invokevirtual write : (Ljava/util/List;Ljava/io/OutputStream;)V
    //   593: aload #10
    //   595: invokevirtual toByteArray : ()[B
    //   598: areturn
    //   599: new java/lang/Exception
    //   602: dup
    //   603: ldc_w 'Unknown error!!! Not success!!!'
    //   606: invokespecial <init> : (Ljava/lang/String;)V
    //   609: athrow }
  
  private static void printXML(String paramString1, String paramString2, Object... paramVarArgs) throws Exception {
    if (isDebug) {
      System.out.printf(paramString2, paramVarArgs);
      System.out.println();
    } 
    if (writeToFileAppend(paramString1, String.format(paramString2, paramVarArgs)))
      return; 
    throw new Exception("write to xml buffer failed.");
  }
  
  private static int readInt(byte[] paramArrayOfbyte, int paramInt) {
    byte b1 = paramArrayOfbyte[paramInt + 3];
    byte b2 = paramArrayOfbyte[paramInt + 2];
    byte b3 = paramArrayOfbyte[paramInt + 1];
    return paramArrayOfbyte[paramInt] & 0xFF | b1 << 24 | (b2 & 0xFF) << 16 | (b3 & 0xFF) << 8;
  }
  
  private static void setupSign(String paramString1, String paramString2) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString1);
    stringBuilder.append("/");
    stringBuilder.append(SaveApkSignatureTask.getSignInfoPath());
    paramString1 = stringBuilder.toString().replace("/", File.separator);
    paramString2 = ApkSignatureHelper.getApkSignInfo(paramString2);
    if (paramString2 == null || paramString2.isEmpty()) {
      if (isDebug)
        System.err.println(" Get original signature failed !!!!"); 
      return;
    } 
    File file = (new File(paramString1)).getParentFile();
    if (!file.exists())
      file.mkdirs(); 
    FileUtils.writeFile(paramString1, paramString2);
  }
  
  public static boolean startModifyV2(Context paramContext, String paramString1, String paramString2, String paramString3, boolean paramBoolean, theCallback paramtheCallback) { // Byte code:
    //   0: iload #4
    //   2: putstatic com/sk/spatch/kt/mv2/ModifyV2.isDebug : Z
    //   5: aload_2
    //   6: aload_3
    //   7: invokestatic doCleanBeforeStart : (Ljava/lang/String;Ljava/lang/String;)V
    //   10: new net/lingala/zip4j/ZipFile
    //   13: dup
    //   14: aload_1
    //   15: invokespecial <init> : (Ljava/lang/String;)V
    //   18: astore_3
    //   19: new java/lang/StringBuilder
    //   22: dup
    //   23: invokespecial <init> : ()V
    //   26: astore #11
    //   28: aload #11
    //   30: aload_2
    //   31: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   34: pop
    //   35: aload #11
    //   37: getstatic com/sk/spatch/kt/mv2/ModifyV2.szManifestBufferPath : Ljava/lang/String;
    //   40: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   43: pop
    //   44: aload_3
    //   45: ldc 'AndroidManifest.xml'
    //   47: aload #11
    //   49: invokevirtual toString : ()Ljava/lang/String;
    //   52: invokevirtual extractFile : (Ljava/lang/String;Ljava/lang/String;)V
    //   55: new java/lang/StringBuilder
    //   58: dup
    //   59: invokespecial <init> : ()V
    //   62: astore_3
    //   63: aload_3
    //   64: aload_2
    //   65: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   68: pop
    //   69: aload_3
    //   70: getstatic com/sk/spatch/kt/mv2/ModifyV2.szManifestBufferPath : Ljava/lang/String;
    //   73: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   76: pop
    //   77: aload_3
    //   78: getstatic java/io/File.separatorChar : C
    //   81: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   84: pop
    //   85: aload_3
    //   86: ldc 'AndroidManifest.xml'
    //   88: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   91: pop
    //   92: aload_3
    //   93: invokevirtual toString : ()Ljava/lang/String;
    //   96: astore_3
    //   97: new java/lang/StringBuilder
    //   100: dup
    //   101: invokespecial <init> : ()V
    //   104: astore #11
    //   106: aload #11
    //   108: aload_2
    //   109: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   112: pop
    //   113: aload #11
    //   115: getstatic com/sk/spatch/kt/mv2/ModifyV2.szPrinterFileBuffer : Ljava/lang/String;
    //   118: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   121: pop
    //   122: aload_3
    //   123: aload #11
    //   125: invokevirtual toString : ()Ljava/lang/String;
    //   128: invokestatic decompileXML : (Ljava/lang/String;Ljava/lang/String;)Z
    //   131: istore #7
    //   133: iload #7
    //   135: ifne -> 159
    //   138: getstatic com/sk/spatch/kt/mv2/ModifyV2.isDebug : Z
    //   141: ifeq -> 153
    //   144: getstatic java/lang/System.err : Ljava/io/PrintStream;
    //   147: ldc_w '处理xml的时候发生异常，退出。'
    //   150: invokevirtual println : (Ljava/lang/String;)V
    //   153: iconst_0
    //   154: ireturn
    //   155: astore_0
    //   156: goto -> 4364
    //   159: getstatic com/sk/spatch/kt/mv2/ModifyV2.isDebug : Z
    //   162: istore #7
    //   164: iload #7
    //   166: ifeq -> 242
    //   169: new java/lang/StringBuilder
    //   172: dup
    //   173: invokespecial <init> : ()V
    //   176: astore_3
    //   177: aload_3
    //   178: aload_2
    //   179: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   182: pop
    //   183: aload_3
    //   184: getstatic com/sk/spatch/kt/mv2/ModifyV2.szPrinterFileBuffer : Ljava/lang/String;
    //   187: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   190: pop
    //   191: aload_3
    //   192: invokevirtual toString : ()Ljava/lang/String;
    //   195: invokestatic getApplicationNameFromFile : (Ljava/lang/String;)Ljava/lang/String;
    //   198: astore_3
    //   199: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   202: astore #11
    //   204: new java/lang/StringBuilder
    //   207: dup
    //   208: invokespecial <init> : ()V
    //   211: astore #12
    //   213: aload #12
    //   215: ldc_w '尝试获取APP的AppName：'
    //   218: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   221: pop
    //   222: aload #12
    //   224: aload_3
    //   225: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   228: pop
    //   229: aload #11
    //   231: aload #12
    //   233: invokevirtual toString : ()Ljava/lang/String;
    //   236: invokevirtual println : (Ljava/lang/String;)V
    //   239: goto -> 245
    //   242: ldc ''
    //   244: astore_3
    //   245: new java/lang/StringBuilder
    //   248: dup
    //   249: invokespecial <init> : ()V
    //   252: astore #11
    //   254: aload #11
    //   256: aload_2
    //   257: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   260: pop
    //   261: aload #11
    //   263: getstatic com/sk/spatch/kt/mv2/ModifyV2.TempLibAaptPath : Ljava/lang/String;
    //   266: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   269: pop
    //   270: aload #11
    //   272: invokevirtual toString : ()Ljava/lang/String;
    //   275: astore #11
    //   277: new java/lang/StringBuilder
    //   280: dup
    //   281: invokespecial <init> : ()V
    //   284: astore #12
    //   286: aload #12
    //   288: ldc_w 'libapk'
    //   291: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   294: pop
    //   295: aload #12
    //   297: getstatic java/io/File.separatorChar : C
    //   300: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   303: pop
    //   304: aload #12
    //   306: ldc 'android.jar'
    //   308: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   311: pop
    //   312: aload #12
    //   314: invokevirtual toString : ()Ljava/lang/String;
    //   317: astore #12
    //   319: new java/lang/StringBuilder
    //   322: dup
    //   323: invokespecial <init> : ()V
    //   326: astore #13
    //   328: aload #13
    //   330: aload_2
    //   331: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   334: pop
    //   335: aload #13
    //   337: getstatic com/sk/spatch/kt/mv2/ModifyV2.TempAndroidJarPath : Ljava/lang/String;
    //   340: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   343: pop
    //   344: aload_0
    //   345: aload #12
    //   347: aload #13
    //   349: invokevirtual toString : ()Ljava/lang/String;
    //   352: invokestatic UnZipAssetsFolder : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    //   355: getstatic android/os/Build$VERSION.SDK_INT : I
    //   358: istore #6
    //   360: iload #6
    //   362: bipush #21
    //   364: if_icmplt -> 519
    //   367: invokestatic getX86Arm : ()Ljava/lang/String;
    //   370: ldc_w 'x86'
    //   373: invokevirtual equals : (Ljava/lang/Object;)Z
    //   376: ifeq -> 447
    //   379: new java/lang/StringBuilder
    //   382: dup
    //   383: invokespecial <init> : ()V
    //   386: astore #12
    //   388: aload #12
    //   390: ldc_w 'libapk'
    //   393: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   396: pop
    //   397: aload #12
    //   399: getstatic java/io/File.separatorChar : C
    //   402: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   405: pop
    //   406: aload #12
    //   408: ldc_w 'x86-pie'
    //   411: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   414: pop
    //   415: aload #12
    //   417: getstatic java/io/File.separatorChar : C
    //   420: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   423: pop
    //   424: aload #12
    //   426: ldc_w 'aapt'
    //   429: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   432: pop
    //   433: aload_0
    //   434: aload #12
    //   436: invokevirtual toString : ()Ljava/lang/String;
    //   439: aload #11
    //   441: invokestatic UnZipAssetsFolder : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    //   444: goto -> 668
    //   447: new java/lang/StringBuilder
    //   450: dup
    //   451: invokespecial <init> : ()V
    //   454: astore #12
    //   456: aload #12
    //   458: ldc_w 'libapk'
    //   461: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   464: pop
    //   465: aload #12
    //   467: getstatic java/io/File.separatorChar : C
    //   470: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   473: pop
    //   474: aload #12
    //   476: ldc_w 'armeabi-pie'
    //   479: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   482: pop
    //   483: aload #12
    //   485: getstatic java/io/File.separatorChar : C
    //   488: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   491: pop
    //   492: aload #12
    //   494: ldc_w 'aapt'
    //   497: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   500: pop
    //   501: aload_0
    //   502: aload #12
    //   504: invokevirtual toString : ()Ljava/lang/String;
    //   507: aload #11
    //   509: invokestatic UnZipAssetsFolder : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    //   512: goto -> 668
    //   515: astore_0
    //   516: goto -> 4360
    //   519: invokestatic getX86Arm : ()Ljava/lang/String;
    //   522: ldc_w 'x86'
    //   525: invokevirtual equals : (Ljava/lang/Object;)Z
    //   528: istore #7
    //   530: iload #7
    //   532: ifeq -> 603
    //   535: new java/lang/StringBuilder
    //   538: dup
    //   539: invokespecial <init> : ()V
    //   542: astore #12
    //   544: aload #12
    //   546: ldc_w 'libapk'
    //   549: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   552: pop
    //   553: aload #12
    //   555: getstatic java/io/File.separatorChar : C
    //   558: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   561: pop
    //   562: aload #12
    //   564: ldc_w 'x86'
    //   567: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   570: pop
    //   571: aload #12
    //   573: getstatic java/io/File.separatorChar : C
    //   576: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   579: pop
    //   580: aload #12
    //   582: ldc_w 'aapt'
    //   585: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   588: pop
    //   589: aload_0
    //   590: aload #12
    //   592: invokevirtual toString : ()Ljava/lang/String;
    //   595: aload #11
    //   597: invokestatic UnZipAssetsFolder : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    //   600: goto -> 668
    //   603: new java/lang/StringBuilder
    //   606: dup
    //   607: invokespecial <init> : ()V
    //   610: astore #12
    //   612: aload #12
    //   614: ldc_w 'libapk'
    //   617: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   620: pop
    //   621: aload #12
    //   623: getstatic java/io/File.separatorChar : C
    //   626: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   629: pop
    //   630: aload #12
    //   632: ldc_w 'armeabi'
    //   635: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   638: pop
    //   639: aload #12
    //   641: getstatic java/io/File.separatorChar : C
    //   644: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   647: pop
    //   648: aload #12
    //   650: ldc_w 'aapt'
    //   653: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   656: pop
    //   657: aload_0
    //   658: aload #12
    //   660: invokevirtual toString : ()Ljava/lang/String;
    //   663: aload #11
    //   665: invokestatic UnZipAssetsFolder : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    //   668: new java/io/File
    //   671: dup
    //   672: aload #11
    //   674: invokespecial <init> : (Ljava/lang/String;)V
    //   677: invokevirtual exists : ()Z
    //   680: ifeq -> 4320
    //   683: getstatic com/sk/spatch/kt/mv2/ModifyV2.isDebug : Z
    //   686: istore #7
    //   688: iload #7
    //   690: ifeq -> 734
    //   693: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   696: astore #12
    //   698: new java/lang/StringBuilder
    //   701: dup
    //   702: invokespecial <init> : ()V
    //   705: astore #13
    //   707: aload #13
    //   709: ldc_w 'chmod file... '
    //   712: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   715: pop
    //   716: aload #13
    //   718: aload #11
    //   720: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   723: pop
    //   724: aload #12
    //   726: aload #13
    //   728: invokevirtual toString : ()Ljava/lang/String;
    //   731: invokevirtual println : (Ljava/lang/String;)V
    //   734: new java/io/File
    //   737: dup
    //   738: aload #11
    //   740: invokespecial <init> : (Ljava/lang/String;)V
    //   743: astore #12
    //   745: aload #12
    //   747: iconst_1
    //   748: iconst_0
    //   749: invokevirtual setExecutable : (ZZ)Z
    //   752: ifeq -> 4309
    //   755: new java/lang/StringBuilder
    //   758: dup
    //   759: invokespecial <init> : ()V
    //   762: astore #12
    //   764: aload #12
    //   766: aload_2
    //   767: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   770: pop
    //   771: aload #12
    //   773: getstatic com/sk/spatch/kt/mv2/ModifyV2.szPrinterFileBuffer : Ljava/lang/String;
    //   776: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   779: pop
    //   780: aload #12
    //   782: invokevirtual toString : ()Ljava/lang/String;
    //   785: astore #12
    //   787: new java/lang/StringBuilder
    //   790: dup
    //   791: invokespecial <init> : ()V
    //   794: astore #13
    //   796: aload #13
    //   798: aload_2
    //   799: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   802: pop
    //   803: aload #13
    //   805: getstatic com/sk/spatch/kt/mv2/ModifyV2.TempCompiledPath : Ljava/lang/String;
    //   808: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   811: pop
    //   812: aload #13
    //   814: invokevirtual toString : ()Ljava/lang/String;
    //   817: astore #13
    //   819: getstatic com/sk/spatch/kt/mv2/ModifyV2.isDebug : Z
    //   822: istore #7
    //   824: iload #7
    //   826: ifeq -> 902
    //   829: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   832: astore #14
    //   834: new java/lang/StringBuilder
    //   837: dup
    //   838: invokespecial <init> : ()V
    //   841: astore #15
    //   843: aload #15
    //   845: ldc_w 'executing aapt...'
    //   848: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   851: pop
    //   852: aload #15
    //   854: iconst_5
    //   855: anewarray java/lang/String
    //   858: dup
    //   859: iconst_0
    //   860: aload #11
    //   862: aastore
    //   863: dup
    //   864: iconst_1
    //   865: ldc_w 'compile'
    //   868: aastore
    //   869: dup
    //   870: iconst_2
    //   871: aload #12
    //   873: aastore
    //   874: dup
    //   875: iconst_3
    //   876: ldc_w '-o'
    //   879: aastore
    //   880: dup
    //   881: iconst_4
    //   882: aload #13
    //   884: aastore
    //   885: invokestatic toString : ([Ljava/lang/Object;)Ljava/lang/String;
    //   888: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   891: pop
    //   892: aload #14
    //   894: aload #15
    //   896: invokevirtual toString : ()Ljava/lang/String;
    //   899: invokevirtual println : (Ljava/lang/String;)V
    //   902: new java/lang/StringBuilder
    //   905: dup
    //   906: invokespecial <init> : ()V
    //   909: astore #11
    //   911: aload #11
    //   913: aload_2
    //   914: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   917: pop
    //   918: aload #11
    //   920: getstatic com/sk/spatch/kt/mv2/ModifyV2.TempAppNameStorePath : Ljava/lang/String;
    //   923: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   926: pop
    //   927: aload #11
    //   929: invokevirtual toString : ()Ljava/lang/String;
    //   932: astore #12
    //   934: new java/io/File
    //   937: dup
    //   938: aload #12
    //   940: invokespecial <init> : (Ljava/lang/String;)V
    //   943: invokevirtual getParentFile : ()Ljava/io/File;
    //   946: invokevirtual mkdirs : ()Z
    //   949: pop
    //   950: aload_3
    //   951: ldc ''
    //   953: invokevirtual equals : (Ljava/lang/Object;)Z
    //   956: istore #7
    //   958: iload #7
    //   960: ifeq -> 982
    //   963: getstatic com/sk/spatch/kt/mv2/ModifyV2.isDebug : Z
    //   966: ifeq -> 978
    //   969: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   972: ldc_w 'AppName为空，使用默认app。'
    //   975: invokevirtual println : (Ljava/lang/String;)V
    //   978: ldc_w 'none'
    //   981: astore_3
    //   982: aload_3
    //   983: ldc_w 'none'
    //   986: invokevirtual equals : (Ljava/lang/Object;)Z
    //   989: istore #7
    //   991: iload #7
    //   993: ifeq -> 1135
    //   996: bipush #8
    //   998: anewarray java/lang/String
    //   1001: astore #11
    //   1003: aload #11
    //   1005: iconst_0
    //   1006: ldc_w '-attr'
    //   1009: aastore
    //   1010: aload #11
    //   1012: iconst_1
    //   1013: ldc_w '-i'
    //   1016: aastore
    //   1017: aload #11
    //   1019: iconst_2
    //   1020: ldc_w 'application'
    //   1023: aastore
    //   1024: aload #11
    //   1026: iconst_3
    //   1027: ldc_w 'package'
    //   1030: aastore
    //   1031: aload #11
    //   1033: iconst_4
    //   1034: ldc_w 'name'
    //   1037: aastore
    //   1038: aload #11
    //   1040: iconst_5
    //   1041: ldc_w 'com.sk.skmain.SKApp'
    //   1044: aastore
    //   1045: new java/lang/StringBuilder
    //   1048: dup
    //   1049: invokespecial <init> : ()V
    //   1052: astore #13
    //   1054: aload #13
    //   1056: aload_2
    //   1057: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1060: pop
    //   1061: aload #13
    //   1063: getstatic com/sk/spatch/kt/mv2/ModifyV2.szManifestBufferPath : Ljava/lang/String;
    //   1066: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1069: pop
    //   1070: aload #13
    //   1072: getstatic java/io/File.separatorChar : C
    //   1075: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   1078: pop
    //   1079: aload #13
    //   1081: ldc 'AndroidManifest.xml'
    //   1083: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1086: pop
    //   1087: aload #11
    //   1089: bipush #6
    //   1091: aload #13
    //   1093: invokevirtual toString : ()Ljava/lang/String;
    //   1096: aastore
    //   1097: new java/lang/StringBuilder
    //   1100: dup
    //   1101: invokespecial <init> : ()V
    //   1104: astore #13
    //   1106: aload #13
    //   1108: aload_2
    //   1109: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1112: pop
    //   1113: aload #13
    //   1115: getstatic com/sk/spatch/kt/mv2/ModifyV2.szOutManifestPath : Ljava/lang/String;
    //   1118: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1121: pop
    //   1122: aload #11
    //   1124: bipush #7
    //   1126: aload #13
    //   1128: invokevirtual toString : ()Ljava/lang/String;
    //   1131: aastore
    //   1132: goto -> 1271
    //   1135: bipush #8
    //   1137: anewarray java/lang/String
    //   1140: astore #11
    //   1142: aload #11
    //   1144: iconst_0
    //   1145: ldc_w '-attr'
    //   1148: aastore
    //   1149: aload #11
    //   1151: iconst_1
    //   1152: ldc_w '-m'
    //   1155: aastore
    //   1156: aload #11
    //   1158: iconst_2
    //   1159: ldc_w 'application'
    //   1162: aastore
    //   1163: aload #11
    //   1165: iconst_3
    //   1166: ldc_w 'package'
    //   1169: aastore
    //   1170: aload #11
    //   1172: iconst_4
    //   1173: ldc_w 'name'
    //   1176: aastore
    //   1177: aload #11
    //   1179: iconst_5
    //   1180: ldc_w 'com.sk.skmain.SKApp'
    //   1183: aastore
    //   1184: new java/lang/StringBuilder
    //   1187: dup
    //   1188: invokespecial <init> : ()V
    //   1191: astore #13
    //   1193: aload #13
    //   1195: aload_2
    //   1196: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1199: pop
    //   1200: aload #13
    //   1202: getstatic com/sk/spatch/kt/mv2/ModifyV2.szManifestBufferPath : Ljava/lang/String;
    //   1205: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1208: pop
    //   1209: aload #13
    //   1211: getstatic java/io/File.separatorChar : C
    //   1214: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   1217: pop
    //   1218: aload #13
    //   1220: ldc 'AndroidManifest.xml'
    //   1222: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1225: pop
    //   1226: aload #11
    //   1228: bipush #6
    //   1230: aload #13
    //   1232: invokevirtual toString : ()Ljava/lang/String;
    //   1235: aastore
    //   1236: new java/lang/StringBuilder
    //   1239: dup
    //   1240: invokespecial <init> : ()V
    //   1243: astore #13
    //   1245: aload #13
    //   1247: aload_2
    //   1248: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1251: pop
    //   1252: aload #13
    //   1254: getstatic com/sk/spatch/kt/mv2/ModifyV2.szOutManifestPath : Ljava/lang/String;
    //   1257: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1260: pop
    //   1261: aload #11
    //   1263: bipush #7
    //   1265: aload #13
    //   1267: invokevirtual toString : ()Ljava/lang/String;
    //   1270: aastore
    //   1271: iload #4
    //   1273: ifeq -> 1320
    //   1276: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   1279: astore #13
    //   1281: new java/lang/StringBuilder
    //   1284: dup
    //   1285: invokespecial <init> : ()V
    //   1288: astore #14
    //   1290: aload #14
    //   1292: ldc_w '命令行参数：'
    //   1295: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1298: pop
    //   1299: aload #14
    //   1301: aload #11
    //   1303: invokestatic toString : ([Ljava/lang/Object;)Ljava/lang/String;
    //   1306: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1309: pop
    //   1310: aload #13
    //   1312: aload #14
    //   1314: invokevirtual toString : ()Ljava/lang/String;
    //   1317: invokevirtual println : (Ljava/lang/String;)V
    //   1320: new java/lang/StringBuilder
    //   1323: dup
    //   1324: invokespecial <init> : ()V
    //   1327: astore #11
    //   1329: aload #11
    //   1331: aload_2
    //   1332: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1335: pop
    //   1336: aload #11
    //   1338: getstatic com/sk/spatch/kt/mv2/ModifyV2.szManifestBufferPath : Ljava/lang/String;
    //   1341: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1344: pop
    //   1345: aload #11
    //   1347: getstatic java/io/File.separatorChar : C
    //   1350: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   1353: pop
    //   1354: aload #11
    //   1356: ldc 'AndroidManifest.xml'
    //   1358: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1361: pop
    //   1362: new java/io/FileInputStream
    //   1365: dup
    //   1366: new java/io/File
    //   1369: dup
    //   1370: aload #11
    //   1372: invokevirtual toString : ()Ljava/lang/String;
    //   1375: invokespecial <init> : (Ljava/lang/String;)V
    //   1378: invokespecial <init> : (Ljava/io/File;)V
    //   1381: invokestatic parseManifest : (Ljava/io/InputStream;)[B
    //   1384: astore #13
    //   1386: aload_3
    //   1387: ldc_w 'none'
    //   1390: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1393: istore #4
    //   1395: aload_3
    //   1396: astore #11
    //   1398: iload #4
    //   1400: ifeq -> 1446
    //   1403: aload_3
    //   1404: astore #11
    //   1406: getstatic com/sk/spatch/kt/mv2/ModifyV2.customApplication : Z
    //   1409: ifeq -> 1446
    //   1412: aload_3
    //   1413: astore #11
    //   1415: getstatic com/sk/spatch/kt/mv2/ModifyV2.customApplicationName : Ljava/lang/String;
    //   1418: ifnull -> 1446
    //   1421: getstatic com/sk/spatch/kt/mv2/ModifyV2.customApplicationName : Ljava/lang/String;
    //   1424: astore_3
    //   1425: aload_3
    //   1426: astore #11
    //   1428: getstatic com/sk/spatch/kt/mv2/ModifyV2.isDebug : Z
    //   1431: ifeq -> 1446
    //   1434: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   1437: ldc_w '二次检测发现AppName，正在修正当中。'
    //   1440: invokevirtual println : (Ljava/lang/String;)V
    //   1443: aload_3
    //   1444: astore #11
    //   1446: aload #12
    //   1448: aload #11
    //   1450: invokestatic writeToFileAppend : (Ljava/lang/String;Ljava/lang/String;)Z
    //   1453: istore #4
    //   1455: iload #4
    //   1457: ifne -> 1477
    //   1460: getstatic com/sk/spatch/kt/mv2/ModifyV2.isDebug : Z
    //   1463: ifeq -> 1475
    //   1466: getstatic java/lang/System.err : Ljava/io/PrintStream;
    //   1469: ldc_w '写入AppName失败，退出。'
    //   1472: invokevirtual println : (Ljava/lang/String;)V
    //   1475: iconst_0
    //   1476: ireturn
    //   1477: aload #12
    //   1479: new java/io/File
    //   1482: dup
    //   1483: aload_1
    //   1484: invokespecial <init> : (Ljava/lang/String;)V
    //   1487: invokevirtual length : ()J
    //   1490: invokestatic valueOf : (J)Ljava/lang/String;
    //   1493: invokestatic writeToFileAppend : (Ljava/lang/String;Ljava/lang/String;)Z
    //   1496: istore #4
    //   1498: iload #4
    //   1500: ifne -> 1520
    //   1503: getstatic com/sk/spatch/kt/mv2/ModifyV2.isDebug : Z
    //   1506: ifeq -> 1518
    //   1509: getstatic java/lang/System.err : Ljava/io/PrintStream;
    //   1512: ldc_w '写入原文件体积失败，退出。'
    //   1515: invokevirtual println : (Ljava/lang/String;)V
    //   1518: iconst_0
    //   1519: ireturn
    //   1520: new java/lang/StringBuilder
    //   1523: dup
    //   1524: invokespecial <init> : ()V
    //   1527: astore_3
    //   1528: aload_3
    //   1529: aload_2
    //   1530: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1533: pop
    //   1534: aload_3
    //   1535: getstatic com/sk/spatch/kt/mv2/ModifyV2.szOutManifestPath : Ljava/lang/String;
    //   1538: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1541: pop
    //   1542: new java/io/FileOutputStream
    //   1545: dup
    //   1546: new java/io/File
    //   1549: dup
    //   1550: aload_3
    //   1551: invokevirtual toString : ()Ljava/lang/String;
    //   1554: invokespecial <init> : (Ljava/lang/String;)V
    //   1557: invokespecial <init> : (Ljava/io/File;)V
    //   1560: astore_3
    //   1561: new java/io/BufferedOutputStream
    //   1564: dup
    //   1565: aload_3
    //   1566: invokespecial <init> : (Ljava/io/OutputStream;)V
    //   1569: astore #11
    //   1571: aload #11
    //   1573: aload #13
    //   1575: invokevirtual write : ([B)V
    //   1578: aload #11
    //   1580: invokevirtual close : ()V
    //   1583: aload_3
    //   1584: invokevirtual close : ()V
    //   1587: new java/io/File
    //   1590: dup
    //   1591: aload_1
    //   1592: invokespecial <init> : (Ljava/lang/String;)V
    //   1595: astore_3
    //   1596: new java/lang/StringBuilder
    //   1599: dup
    //   1600: invokespecial <init> : ()V
    //   1603: astore #11
    //   1605: aload #11
    //   1607: aload_2
    //   1608: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1611: pop
    //   1612: aload #11
    //   1614: getstatic com/sk/spatch/kt/mv2/ModifyV2.TempApkUnsigned : Ljava/lang/String;
    //   1617: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1620: pop
    //   1621: aload #11
    //   1623: invokevirtual toString : ()Ljava/lang/String;
    //   1626: astore #14
    //   1628: new java/lang/StringBuilder
    //   1631: dup
    //   1632: invokespecial <init> : ()V
    //   1635: astore #11
    //   1637: aload #11
    //   1639: aload_2
    //   1640: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1643: pop
    //   1644: aload #11
    //   1646: getstatic com/sk/spatch/kt/mv2/ModifyV2.BackupApkPath : Ljava/lang/String;
    //   1649: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1652: pop
    //   1653: aload #11
    //   1655: invokevirtual toString : ()Ljava/lang/String;
    //   1658: astore #15
    //   1660: new java/io/File
    //   1663: dup
    //   1664: aload #15
    //   1666: invokespecial <init> : (Ljava/lang/String;)V
    //   1669: invokevirtual getParentFile : ()Ljava/io/File;
    //   1672: invokevirtual mkdirs : ()Z
    //   1675: pop
    //   1676: getstatic com/sk/spatch/kt/mv2/ModifyV2.isDebug : Z
    //   1679: istore #4
    //   1681: iload #4
    //   1683: ifeq -> 1702
    //   1686: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   1689: ldc_w 'Tryna copy files...'
    //   1692: invokevirtual println : (Ljava/lang/String;)V
    //   1695: goto -> 1702
    //   1698: astore_0
    //   1699: goto -> 4299
    //   1702: getstatic android/os/Build$VERSION.SDK_INT : I
    //   1705: istore #6
    //   1707: iload #6
    //   1709: bipush #26
    //   1711: if_icmplt -> 1787
    //   1714: aload_3
    //   1715: invokevirtual toPath : ()Ljava/nio/file/Path;
    //   1718: astore #11
    //   1720: new java/io/File
    //   1723: dup
    //   1724: aload #14
    //   1726: invokespecial <init> : (Ljava/lang/String;)V
    //   1729: invokevirtual toPath : ()Ljava/nio/file/Path;
    //   1732: astore #12
    //   1734: aload #11
    //   1736: aload #12
    //   1738: iconst_0
    //   1739: anewarray java/nio/file/CopyOption
    //   1742: invokestatic copy : (Ljava/nio/file/Path;Ljava/nio/file/Path;[Ljava/nio/file/CopyOption;)Ljava/nio/file/Path;
    //   1745: pop
    //   1746: aload_3
    //   1747: invokevirtual toPath : ()Ljava/nio/file/Path;
    //   1750: astore_3
    //   1751: new java/io/File
    //   1754: dup
    //   1755: aload #15
    //   1757: invokespecial <init> : (Ljava/lang/String;)V
    //   1760: invokevirtual toPath : ()Ljava/nio/file/Path;
    //   1763: astore #11
    //   1765: aload_3
    //   1766: aload #11
    //   1768: iconst_0
    //   1769: anewarray java/nio/file/CopyOption
    //   1772: invokestatic copy : (Ljava/nio/file/Path;Ljava/nio/file/Path;[Ljava/nio/file/CopyOption;)Ljava/nio/file/Path;
    //   1775: pop
    //   1776: goto -> 1929
    //   1779: astore_0
    //   1780: goto -> 4299
    //   1783: astore_0
    //   1784: goto -> 4299
    //   1787: aconst_null
    //   1788: astore #12
    //   1790: aconst_null
    //   1791: astore #13
    //   1793: aconst_null
    //   1794: astore_3
    //   1795: new java/io/FileInputStream
    //   1798: dup
    //   1799: aload_1
    //   1800: invokespecial <init> : (Ljava/lang/String;)V
    //   1803: astore #11
    //   1805: new java/io/FileOutputStream
    //   1808: dup
    //   1809: aload #14
    //   1811: invokespecial <init> : (Ljava/lang/String;)V
    //   1814: astore_3
    //   1815: sipush #1024
    //   1818: newarray byte
    //   1820: astore #12
    //   1822: aload #11
    //   1824: aload #12
    //   1826: invokevirtual read : ([B)I
    //   1829: istore #6
    //   1831: iload #6
    //   1833: iconst_m1
    //   1834: if_icmpeq -> 1853
    //   1837: aload_3
    //   1838: aload #12
    //   1840: iconst_0
    //   1841: iload #6
    //   1843: invokevirtual write : ([BII)V
    //   1846: goto -> 1822
    //   1849: astore_1
    //   1850: goto -> 4438
    //   1853: aload #11
    //   1855: invokevirtual close : ()V
    //   1858: aload_3
    //   1859: invokevirtual close : ()V
    //   1862: new java/io/FileInputStream
    //   1865: dup
    //   1866: aload_1
    //   1867: invokespecial <init> : (Ljava/lang/String;)V
    //   1870: astore #12
    //   1872: new java/io/FileOutputStream
    //   1875: dup
    //   1876: aload #15
    //   1878: invokespecial <init> : (Ljava/lang/String;)V
    //   1881: astore #11
    //   1883: sipush #1024
    //   1886: newarray byte
    //   1888: astore_3
    //   1889: aload #12
    //   1891: aload_3
    //   1892: invokevirtual read : ([B)I
    //   1895: istore #6
    //   1897: iload #6
    //   1899: iconst_m1
    //   1900: if_icmpeq -> 1919
    //   1903: aload #11
    //   1905: aload_3
    //   1906: iconst_0
    //   1907: iload #6
    //   1909: invokevirtual write : ([BII)V
    //   1912: goto -> 1889
    //   1915: astore_0
    //   1916: goto -> 4417
    //   1919: aload #12
    //   1921: invokevirtual close : ()V
    //   1924: aload #11
    //   1926: invokevirtual close : ()V
    //   1929: aload_2
    //   1930: aload_1
    //   1931: invokestatic setupSign : (Ljava/lang/String;Ljava/lang/String;)V
    //   1934: new net/lingala/zip4j/ZipFile
    //   1937: dup
    //   1938: aload #14
    //   1940: invokespecial <init> : (Ljava/lang/String;)V
    //   1943: astore_1
    //   1944: new net/lingala/zip4j/model/ZipParameters
    //   1947: dup
    //   1948: invokespecial <init> : ()V
    //   1951: astore_3
    //   1952: getstatic com/sk/spatch/kt/mv2/ModifyV2.isDebug : Z
    //   1955: istore #4
    //   1957: iload #4
    //   1959: ifeq -> 1971
    //   1962: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   1965: ldc_w 'Adding files...'
    //   1968: invokevirtual println : (Ljava/lang/String;)V
    //   1971: aload_3
    //   1972: ldc 'AndroidManifest.xml'
    //   1974: invokevirtual setFileNameInZip : (Ljava/lang/String;)V
    //   1977: aload_1
    //   1978: ldc 'AndroidManifest.xml'
    //   1980: invokevirtual removeFile : (Ljava/lang/String;)V
    //   1983: new java/lang/StringBuilder
    //   1986: dup
    //   1987: invokespecial <init> : ()V
    //   1990: astore #11
    //   1992: aload #11
    //   1994: aload_2
    //   1995: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1998: pop
    //   1999: aload #11
    //   2001: getstatic com/sk/spatch/kt/mv2/ModifyV2.szOutManifestPath : Ljava/lang/String;
    //   2004: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2007: pop
    //   2008: aload_1
    //   2009: aload #11
    //   2011: invokevirtual toString : ()Ljava/lang/String;
    //   2014: aload_3
    //   2015: invokevirtual addFile : (Ljava/lang/String;Lnet/lingala/zip4j/model/ZipParameters;)V
    //   2018: aload_1
    //   2019: invokevirtual getFileHeaders : ()Ljava/util/List;
    //   2022: invokeinterface iterator : ()Ljava/util/Iterator;
    //   2027: astore_3
    //   2028: iconst_1
    //   2029: istore #6
    //   2031: iconst_0
    //   2032: istore #8
    //   2034: iconst_0
    //   2035: istore #7
    //   2037: iconst_0
    //   2038: istore #4
    //   2040: aload_3
    //   2041: invokeinterface hasNext : ()Z
    //   2046: istore #9
    //   2048: iload #9
    //   2050: ifeq -> 2209
    //   2053: aload_3
    //   2054: invokeinterface next : ()Ljava/lang/Object;
    //   2059: checkcast net/lingala/zip4j/model/FileHeader
    //   2062: astore #11
    //   2064: aload #11
    //   2066: invokevirtual getFileName : ()Ljava/lang/String;
    //   2069: ldc_w 'res'
    //   2072: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   2075: ifne -> 2040
    //   2078: aload #11
    //   2080: invokevirtual getFileName : ()Ljava/lang/String;
    //   2083: ldc_w 'META-INF'
    //   2086: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   2089: ifne -> 2040
    //   2092: aload #11
    //   2094: invokevirtual getFileName : ()Ljava/lang/String;
    //   2097: ldc_w 'classes'
    //   2100: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   2103: ifeq -> 2129
    //   2106: aload #11
    //   2108: invokevirtual getFileName : ()Ljava/lang/String;
    //   2111: ldc_w '.dex'
    //   2114: invokevirtual endsWith : (Ljava/lang/String;)Z
    //   2117: ifeq -> 2129
    //   2120: iload #6
    //   2122: iconst_1
    //   2123: iadd
    //   2124: istore #6
    //   2126: goto -> 2040
    //   2129: aload #11
    //   2131: invokevirtual getFileName : ()Ljava/lang/String;
    //   2134: ldc_w 'lib/arm64-v8a/'
    //   2137: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   2140: ifeq -> 2153
    //   2143: iconst_1
    //   2144: istore #4
    //   2146: iload #4
    //   2148: istore #9
    //   2150: goto -> 2220
    //   2153: aload #11
    //   2155: invokevirtual getFileName : ()Ljava/lang/String;
    //   2158: ldc_w 'lib/armeabi/'
    //   2161: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   2164: ifeq -> 2177
    //   2167: iconst_1
    //   2168: istore #7
    //   2170: iload #7
    //   2172: istore #4
    //   2174: goto -> 2040
    //   2177: aload #11
    //   2179: invokevirtual getFileName : ()Ljava/lang/String;
    //   2182: ldc_w 'lib/armeabi-v7a/'
    //   2185: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   2188: istore #9
    //   2190: iload #9
    //   2192: ifeq -> 2040
    //   2195: iconst_1
    //   2196: istore #8
    //   2198: iload #8
    //   2200: istore #4
    //   2202: goto -> 2040
    //   2205: astore_0
    //   2206: goto -> 4264
    //   2209: iconst_0
    //   2210: istore #10
    //   2212: iload #4
    //   2214: istore #9
    //   2216: iload #10
    //   2218: istore #4
    //   2220: getstatic com/sk/spatch/kt/mv2/ModifyV2.isDebug : Z
    //   2223: istore #10
    //   2225: iload #10
    //   2227: ifeq -> 2329
    //   2230: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   2233: astore_3
    //   2234: new java/lang/StringBuilder
    //   2237: dup
    //   2238: invokespecial <init> : ()V
    //   2241: astore #11
    //   2243: aload #11
    //   2245: ldc_w 'Native Library Found: '
    //   2248: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2251: pop
    //   2252: aload #11
    //   2254: iload #9
    //   2256: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   2259: pop
    //   2260: aload #11
    //   2262: ldc_w '，ARM11/ARMv7/ARMv8Lib Found: '
    //   2265: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2268: pop
    //   2269: aload #11
    //   2271: iload #7
    //   2273: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   2276: pop
    //   2277: aload #11
    //   2279: ldc_w '/'
    //   2282: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2285: pop
    //   2286: aload #11
    //   2288: iload #8
    //   2290: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   2293: pop
    //   2294: aload #11
    //   2296: ldc_w '/'
    //   2299: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2302: pop
    //   2303: aload #11
    //   2305: iload #4
    //   2307: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   2310: pop
    //   2311: aload #11
    //   2313: ldc_w '. Now copy SK library files.'
    //   2316: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2319: pop
    //   2320: aload_3
    //   2321: aload #11
    //   2323: invokevirtual toString : ()Ljava/lang/String;
    //   2326: invokevirtual println : (Ljava/lang/String;)V
    //   2329: iload #9
    //   2331: ifne -> 2650
    //   2334: new java/lang/StringBuilder
    //   2337: dup
    //   2338: invokespecial <init> : ()V
    //   2341: astore_3
    //   2342: aload_3
    //   2343: aload_2
    //   2344: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2347: pop
    //   2348: aload_3
    //   2349: getstatic java/io/File.separatorChar : C
    //   2352: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   2355: pop
    //   2356: aload_3
    //   2357: ldc_w 'lib'
    //   2360: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2363: pop
    //   2364: aload_3
    //   2365: getstatic java/io/File.separatorChar : C
    //   2368: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   2371: pop
    //   2372: aload_3
    //   2373: ldc_w 'armeabi'
    //   2376: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2379: pop
    //   2380: new java/io/File
    //   2383: dup
    //   2384: aload_3
    //   2385: invokevirtual toString : ()Ljava/lang/String;
    //   2388: invokespecial <init> : (Ljava/lang/String;)V
    //   2391: astore_3
    //   2392: aload_3
    //   2393: invokevirtual exists : ()Z
    //   2396: ifeq -> 2403
    //   2399: aload_3
    //   2400: invokestatic deleteDir : (Ljava/io/File;)V
    //   2403: aload_3
    //   2404: invokevirtual mkdirs : ()Z
    //   2407: pop
    //   2408: new java/lang/StringBuilder
    //   2411: dup
    //   2412: invokespecial <init> : ()V
    //   2415: astore_3
    //   2416: aload_3
    //   2417: ldc_w 'lib'
    //   2420: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2423: pop
    //   2424: aload_3
    //   2425: getstatic java/io/File.separatorChar : C
    //   2428: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   2431: pop
    //   2432: aload_3
    //   2433: ldc_w 'armeabi-v7a'
    //   2436: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2439: pop
    //   2440: aload_3
    //   2441: getstatic java/io/File.separatorChar : C
    //   2444: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   2447: pop
    //   2448: aload_3
    //   2449: ldc_w 'libsandhook.so'
    //   2452: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2455: pop
    //   2456: aload_3
    //   2457: invokevirtual toString : ()Ljava/lang/String;
    //   2460: astore_3
    //   2461: new java/lang/StringBuilder
    //   2464: dup
    //   2465: invokespecial <init> : ()V
    //   2468: astore #11
    //   2470: aload #11
    //   2472: aload_2
    //   2473: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2476: pop
    //   2477: aload #11
    //   2479: getstatic java/io/File.separatorChar : C
    //   2482: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   2485: pop
    //   2486: aload #11
    //   2488: ldc_w 'lib'
    //   2491: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2494: pop
    //   2495: aload #11
    //   2497: getstatic java/io/File.separatorChar : C
    //   2500: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   2503: pop
    //   2504: aload #11
    //   2506: ldc_w 'armeabi'
    //   2509: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2512: pop
    //   2513: aload #11
    //   2515: getstatic java/io/File.separatorChar : C
    //   2518: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   2521: pop
    //   2522: aload #11
    //   2524: ldc_w 'libsandhook.so'
    //   2527: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2530: pop
    //   2531: aload_0
    //   2532: aload_3
    //   2533: aload #11
    //   2535: invokevirtual toString : ()Ljava/lang/String;
    //   2538: invokestatic UnZipAssetsFolder : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    //   2541: new java/lang/StringBuilder
    //   2544: dup
    //   2545: invokespecial <init> : ()V
    //   2548: astore_3
    //   2549: aload_3
    //   2550: aload_2
    //   2551: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2554: pop
    //   2555: aload_3
    //   2556: getstatic java/io/File.separatorChar : C
    //   2559: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   2562: pop
    //   2563: aload_3
    //   2564: ldc_w 'lib'
    //   2567: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2570: pop
    //   2571: aload_1
    //   2572: new java/io/File
    //   2575: dup
    //   2576: aload_3
    //   2577: invokevirtual toString : ()Ljava/lang/String;
    //   2580: invokespecial <init> : (Ljava/lang/String;)V
    //   2583: invokevirtual addFolder : (Ljava/io/File;)V
    //   2586: new java/lang/StringBuilder
    //   2589: dup
    //   2590: invokespecial <init> : ()V
    //   2593: astore_3
    //   2594: aload_3
    //   2595: aload_2
    //   2596: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2599: pop
    //   2600: aload_3
    //   2601: getstatic java/io/File.separatorChar : C
    //   2604: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   2607: pop
    //   2608: aload_3
    //   2609: ldc_w 'lib'
    //   2612: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2615: pop
    //   2616: aload_3
    //   2617: getstatic java/io/File.separatorChar : C
    //   2620: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   2623: pop
    //   2624: aload_3
    //   2625: ldc_w 'armeabi'
    //   2628: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2631: pop
    //   2632: aload_3
    //   2633: getstatic java/io/File.separatorChar : C
    //   2636: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   2639: pop
    //   2640: aload_3
    //   2641: invokevirtual toString : ()Ljava/lang/String;
    //   2644: invokestatic copyModToBasePath : (Ljava/lang/String;)V
    //   2647: goto -> 3913
    //   2650: iload #8
    //   2652: ifeq -> 3056
    //   2655: new java/lang/StringBuilder
    //   2658: dup
    //   2659: invokespecial <init> : ()V
    //   2662: astore_3
    //   2663: aload_3
    //   2664: aload_2
    //   2665: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2668: pop
    //   2669: aload_3
    //   2670: getstatic java/io/File.separatorChar : C
    //   2673: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   2676: pop
    //   2677: aload_3
    //   2678: ldc_w 'lib'
    //   2681: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2684: pop
    //   2685: aload_3
    //   2686: getstatic java/io/File.separatorChar : C
    //   2689: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   2692: pop
    //   2693: aload_3
    //   2694: ldc_w 'armeabi-v7a'
    //   2697: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2700: pop
    //   2701: new java/io/File
    //   2704: dup
    //   2705: aload_3
    //   2706: invokevirtual toString : ()Ljava/lang/String;
    //   2709: invokespecial <init> : (Ljava/lang/String;)V
    //   2712: astore_3
    //   2713: aload_3
    //   2714: invokevirtual exists : ()Z
    //   2717: ifeq -> 2724
    //   2720: aload_3
    //   2721: invokestatic deleteDir : (Ljava/io/File;)V
    //   2724: aload_3
    //   2725: invokevirtual mkdirs : ()Z
    //   2728: pop
    //   2729: new java/lang/StringBuilder
    //   2732: dup
    //   2733: invokespecial <init> : ()V
    //   2736: astore_3
    //   2737: aload_3
    //   2738: ldc_w 'lib'
    //   2741: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2744: pop
    //   2745: aload_3
    //   2746: getstatic java/io/File.separatorChar : C
    //   2749: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   2752: pop
    //   2753: aload_3
    //   2754: ldc_w 'armeabi-v7a'
    //   2757: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2760: pop
    //   2761: aload_3
    //   2762: getstatic java/io/File.separatorChar : C
    //   2765: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   2768: pop
    //   2769: aload_3
    //   2770: ldc_w 'libsandhook.so'
    //   2773: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2776: pop
    //   2777: aload_3
    //   2778: invokevirtual toString : ()Ljava/lang/String;
    //   2781: astore_3
    //   2782: new java/lang/StringBuilder
    //   2785: dup
    //   2786: invokespecial <init> : ()V
    //   2789: astore #11
    //   2791: aload #11
    //   2793: aload_2
    //   2794: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2797: pop
    //   2798: aload #11
    //   2800: getstatic java/io/File.separatorChar : C
    //   2803: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   2806: pop
    //   2807: aload #11
    //   2809: ldc_w 'lib'
    //   2812: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2815: pop
    //   2816: aload #11
    //   2818: getstatic java/io/File.separatorChar : C
    //   2821: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   2824: pop
    //   2825: aload #11
    //   2827: ldc_w 'armeabi-v7a'
    //   2830: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2833: pop
    //   2834: aload #11
    //   2836: getstatic java/io/File.separatorChar : C
    //   2839: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   2842: pop
    //   2843: aload #11
    //   2845: ldc_w 'libsandhook.so'
    //   2848: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2851: pop
    //   2852: aload_0
    //   2853: aload_3
    //   2854: aload #11
    //   2856: invokevirtual toString : ()Ljava/lang/String;
    //   2859: invokestatic UnZipAssetsFolder : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    //   2862: new java/lang/StringBuilder
    //   2865: dup
    //   2866: invokespecial <init> : ()V
    //   2869: astore_3
    //   2870: aload_3
    //   2871: ldc_w 'lib'
    //   2874: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2877: pop
    //   2878: aload_3
    //   2879: getstatic java/io/File.separatorChar : C
    //   2882: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   2885: pop
    //   2886: aload_3
    //   2887: ldc_w 'armeabi-v7a'
    //   2890: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2893: pop
    //   2894: aload_3
    //   2895: getstatic java/io/File.separatorChar : C
    //   2898: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   2901: pop
    //   2902: aload_3
    //   2903: ldc_w 'libsandhook-native.so'
    //   2906: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2909: pop
    //   2910: aload_3
    //   2911: invokevirtual toString : ()Ljava/lang/String;
    //   2914: astore_3
    //   2915: new java/lang/StringBuilder
    //   2918: dup
    //   2919: invokespecial <init> : ()V
    //   2922: astore #11
    //   2924: aload #11
    //   2926: aload_2
    //   2927: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2930: pop
    //   2931: aload #11
    //   2933: getstatic java/io/File.separatorChar : C
    //   2936: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   2939: pop
    //   2940: aload #11
    //   2942: ldc_w 'lib'
    //   2945: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2948: pop
    //   2949: aload #11
    //   2951: getstatic java/io/File.separatorChar : C
    //   2954: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   2957: pop
    //   2958: aload #11
    //   2960: ldc_w 'armeabi-v7a'
    //   2963: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2966: pop
    //   2967: aload #11
    //   2969: getstatic java/io/File.separatorChar : C
    //   2972: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   2975: pop
    //   2976: aload #11
    //   2978: ldc_w 'libsandhook-native.so'
    //   2981: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2984: pop
    //   2985: aload_0
    //   2986: aload_3
    //   2987: aload #11
    //   2989: invokevirtual toString : ()Ljava/lang/String;
    //   2992: invokestatic UnZipAssetsFolder : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    //   2995: new java/lang/StringBuilder
    //   2998: dup
    //   2999: invokespecial <init> : ()V
    //   3002: astore_3
    //   3003: aload_3
    //   3004: aload_2
    //   3005: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3008: pop
    //   3009: aload_3
    //   3010: getstatic java/io/File.separatorChar : C
    //   3013: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3016: pop
    //   3017: aload_3
    //   3018: ldc_w 'lib'
    //   3021: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3024: pop
    //   3025: aload_3
    //   3026: getstatic java/io/File.separatorChar : C
    //   3029: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3032: pop
    //   3033: aload_3
    //   3034: ldc_w 'armeabi-v7a'
    //   3037: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3040: pop
    //   3041: aload_3
    //   3042: getstatic java/io/File.separatorChar : C
    //   3045: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3048: pop
    //   3049: aload_3
    //   3050: invokevirtual toString : ()Ljava/lang/String;
    //   3053: invokestatic copyModToBasePath : (Ljava/lang/String;)V
    //   3056: iload #7
    //   3058: ifeq -> 3462
    //   3061: new java/lang/StringBuilder
    //   3064: dup
    //   3065: invokespecial <init> : ()V
    //   3068: astore_3
    //   3069: aload_3
    //   3070: aload_2
    //   3071: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3074: pop
    //   3075: aload_3
    //   3076: getstatic java/io/File.separatorChar : C
    //   3079: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3082: pop
    //   3083: aload_3
    //   3084: ldc_w 'lib'
    //   3087: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3090: pop
    //   3091: aload_3
    //   3092: getstatic java/io/File.separatorChar : C
    //   3095: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3098: pop
    //   3099: aload_3
    //   3100: ldc_w 'armeabi'
    //   3103: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3106: pop
    //   3107: new java/io/File
    //   3110: dup
    //   3111: aload_3
    //   3112: invokevirtual toString : ()Ljava/lang/String;
    //   3115: invokespecial <init> : (Ljava/lang/String;)V
    //   3118: astore_3
    //   3119: aload_3
    //   3120: invokevirtual exists : ()Z
    //   3123: ifeq -> 3130
    //   3126: aload_3
    //   3127: invokestatic deleteDir : (Ljava/io/File;)V
    //   3130: aload_3
    //   3131: invokevirtual mkdirs : ()Z
    //   3134: pop
    //   3135: new java/lang/StringBuilder
    //   3138: dup
    //   3139: invokespecial <init> : ()V
    //   3142: astore_3
    //   3143: aload_3
    //   3144: ldc_w 'lib'
    //   3147: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3150: pop
    //   3151: aload_3
    //   3152: getstatic java/io/File.separatorChar : C
    //   3155: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3158: pop
    //   3159: aload_3
    //   3160: ldc_w 'armeabi-v7a'
    //   3163: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3166: pop
    //   3167: aload_3
    //   3168: getstatic java/io/File.separatorChar : C
    //   3171: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3174: pop
    //   3175: aload_3
    //   3176: ldc_w 'libsandhook.so'
    //   3179: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3182: pop
    //   3183: aload_3
    //   3184: invokevirtual toString : ()Ljava/lang/String;
    //   3187: astore_3
    //   3188: new java/lang/StringBuilder
    //   3191: dup
    //   3192: invokespecial <init> : ()V
    //   3195: astore #11
    //   3197: aload #11
    //   3199: aload_2
    //   3200: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3203: pop
    //   3204: aload #11
    //   3206: getstatic java/io/File.separatorChar : C
    //   3209: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3212: pop
    //   3213: aload #11
    //   3215: ldc_w 'lib'
    //   3218: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3221: pop
    //   3222: aload #11
    //   3224: getstatic java/io/File.separatorChar : C
    //   3227: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3230: pop
    //   3231: aload #11
    //   3233: ldc_w 'armeabi'
    //   3236: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3239: pop
    //   3240: aload #11
    //   3242: getstatic java/io/File.separatorChar : C
    //   3245: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3248: pop
    //   3249: aload #11
    //   3251: ldc_w 'libsandhook.so'
    //   3254: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3257: pop
    //   3258: aload_0
    //   3259: aload_3
    //   3260: aload #11
    //   3262: invokevirtual toString : ()Ljava/lang/String;
    //   3265: invokestatic UnZipAssetsFolder : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    //   3268: new java/lang/StringBuilder
    //   3271: dup
    //   3272: invokespecial <init> : ()V
    //   3275: astore_3
    //   3276: aload_3
    //   3277: ldc_w 'lib'
    //   3280: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3283: pop
    //   3284: aload_3
    //   3285: getstatic java/io/File.separatorChar : C
    //   3288: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3291: pop
    //   3292: aload_3
    //   3293: ldc_w 'armeabi-v7a'
    //   3296: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3299: pop
    //   3300: aload_3
    //   3301: getstatic java/io/File.separatorChar : C
    //   3304: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3307: pop
    //   3308: aload_3
    //   3309: ldc_w 'libsandhook-native.so'
    //   3312: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3315: pop
    //   3316: aload_3
    //   3317: invokevirtual toString : ()Ljava/lang/String;
    //   3320: astore_3
    //   3321: new java/lang/StringBuilder
    //   3324: dup
    //   3325: invokespecial <init> : ()V
    //   3328: astore #11
    //   3330: aload #11
    //   3332: aload_2
    //   3333: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3336: pop
    //   3337: aload #11
    //   3339: getstatic java/io/File.separatorChar : C
    //   3342: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3345: pop
    //   3346: aload #11
    //   3348: ldc_w 'lib'
    //   3351: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3354: pop
    //   3355: aload #11
    //   3357: getstatic java/io/File.separatorChar : C
    //   3360: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3363: pop
    //   3364: aload #11
    //   3366: ldc_w 'armeabi'
    //   3369: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3372: pop
    //   3373: aload #11
    //   3375: getstatic java/io/File.separatorChar : C
    //   3378: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3381: pop
    //   3382: aload #11
    //   3384: ldc_w 'libsandhook-native.so'
    //   3387: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3390: pop
    //   3391: aload_0
    //   3392: aload_3
    //   3393: aload #11
    //   3395: invokevirtual toString : ()Ljava/lang/String;
    //   3398: invokestatic UnZipAssetsFolder : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    //   3401: new java/lang/StringBuilder
    //   3404: dup
    //   3405: invokespecial <init> : ()V
    //   3408: astore_3
    //   3409: aload_3
    //   3410: aload_2
    //   3411: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3414: pop
    //   3415: aload_3
    //   3416: getstatic java/io/File.separatorChar : C
    //   3419: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3422: pop
    //   3423: aload_3
    //   3424: ldc_w 'lib'
    //   3427: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3430: pop
    //   3431: aload_3
    //   3432: getstatic java/io/File.separatorChar : C
    //   3435: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3438: pop
    //   3439: aload_3
    //   3440: ldc_w 'armeabi'
    //   3443: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3446: pop
    //   3447: aload_3
    //   3448: getstatic java/io/File.separatorChar : C
    //   3451: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3454: pop
    //   3455: aload_3
    //   3456: invokevirtual toString : ()Ljava/lang/String;
    //   3459: invokestatic copyModToBasePath : (Ljava/lang/String;)V
    //   3462: iload #4
    //   3464: ifeq -> 3868
    //   3467: new java/lang/StringBuilder
    //   3470: dup
    //   3471: invokespecial <init> : ()V
    //   3474: astore_3
    //   3475: aload_3
    //   3476: aload_2
    //   3477: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3480: pop
    //   3481: aload_3
    //   3482: getstatic java/io/File.separatorChar : C
    //   3485: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3488: pop
    //   3489: aload_3
    //   3490: ldc_w 'lib'
    //   3493: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3496: pop
    //   3497: aload_3
    //   3498: getstatic java/io/File.separatorChar : C
    //   3501: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3504: pop
    //   3505: aload_3
    //   3506: ldc_w 'arm64-v8a'
    //   3509: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3512: pop
    //   3513: new java/io/File
    //   3516: dup
    //   3517: aload_3
    //   3518: invokevirtual toString : ()Ljava/lang/String;
    //   3521: invokespecial <init> : (Ljava/lang/String;)V
    //   3524: astore_3
    //   3525: aload_3
    //   3526: invokevirtual exists : ()Z
    //   3529: ifeq -> 3536
    //   3532: aload_3
    //   3533: invokestatic deleteDir : (Ljava/io/File;)V
    //   3536: aload_3
    //   3537: invokevirtual mkdirs : ()Z
    //   3540: pop
    //   3541: new java/lang/StringBuilder
    //   3544: dup
    //   3545: invokespecial <init> : ()V
    //   3548: astore_3
    //   3549: aload_3
    //   3550: ldc_w 'lib'
    //   3553: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3556: pop
    //   3557: aload_3
    //   3558: getstatic java/io/File.separatorChar : C
    //   3561: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3564: pop
    //   3565: aload_3
    //   3566: ldc_w 'arm64-v8a'
    //   3569: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3572: pop
    //   3573: aload_3
    //   3574: getstatic java/io/File.separatorChar : C
    //   3577: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3580: pop
    //   3581: aload_3
    //   3582: ldc_w 'libsandhook.so'
    //   3585: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3588: pop
    //   3589: aload_3
    //   3590: invokevirtual toString : ()Ljava/lang/String;
    //   3593: astore_3
    //   3594: new java/lang/StringBuilder
    //   3597: dup
    //   3598: invokespecial <init> : ()V
    //   3601: astore #11
    //   3603: aload #11
    //   3605: aload_2
    //   3606: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3609: pop
    //   3610: aload #11
    //   3612: getstatic java/io/File.separatorChar : C
    //   3615: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3618: pop
    //   3619: aload #11
    //   3621: ldc_w 'lib'
    //   3624: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3627: pop
    //   3628: aload #11
    //   3630: getstatic java/io/File.separatorChar : C
    //   3633: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3636: pop
    //   3637: aload #11
    //   3639: ldc_w 'arm64-v8a'
    //   3642: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3645: pop
    //   3646: aload #11
    //   3648: getstatic java/io/File.separatorChar : C
    //   3651: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3654: pop
    //   3655: aload #11
    //   3657: ldc_w 'libsandhook.so'
    //   3660: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3663: pop
    //   3664: aload_0
    //   3665: aload_3
    //   3666: aload #11
    //   3668: invokevirtual toString : ()Ljava/lang/String;
    //   3671: invokestatic UnZipAssetsFolder : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    //   3674: new java/lang/StringBuilder
    //   3677: dup
    //   3678: invokespecial <init> : ()V
    //   3681: astore_3
    //   3682: aload_3
    //   3683: ldc_w 'lib'
    //   3686: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3689: pop
    //   3690: aload_3
    //   3691: getstatic java/io/File.separatorChar : C
    //   3694: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3697: pop
    //   3698: aload_3
    //   3699: ldc_w 'arm64-v8a'
    //   3702: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3705: pop
    //   3706: aload_3
    //   3707: getstatic java/io/File.separatorChar : C
    //   3710: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3713: pop
    //   3714: aload_3
    //   3715: ldc_w 'libsandhook-native.so'
    //   3718: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3721: pop
    //   3722: aload_3
    //   3723: invokevirtual toString : ()Ljava/lang/String;
    //   3726: astore_3
    //   3727: new java/lang/StringBuilder
    //   3730: dup
    //   3731: invokespecial <init> : ()V
    //   3734: astore #11
    //   3736: aload #11
    //   3738: aload_2
    //   3739: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3742: pop
    //   3743: aload #11
    //   3745: getstatic java/io/File.separatorChar : C
    //   3748: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3751: pop
    //   3752: aload #11
    //   3754: ldc_w 'lib'
    //   3757: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3760: pop
    //   3761: aload #11
    //   3763: getstatic java/io/File.separatorChar : C
    //   3766: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3769: pop
    //   3770: aload #11
    //   3772: ldc_w 'arm64-v8a'
    //   3775: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3778: pop
    //   3779: aload #11
    //   3781: getstatic java/io/File.separatorChar : C
    //   3784: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3787: pop
    //   3788: aload #11
    //   3790: ldc_w 'libsandhook-native.so'
    //   3793: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3796: pop
    //   3797: aload_0
    //   3798: aload_3
    //   3799: aload #11
    //   3801: invokevirtual toString : ()Ljava/lang/String;
    //   3804: invokestatic UnZipAssetsFolder : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    //   3807: new java/lang/StringBuilder
    //   3810: dup
    //   3811: invokespecial <init> : ()V
    //   3814: astore_3
    //   3815: aload_3
    //   3816: aload_2
    //   3817: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3820: pop
    //   3821: aload_3
    //   3822: getstatic java/io/File.separatorChar : C
    //   3825: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3828: pop
    //   3829: aload_3
    //   3830: ldc_w 'lib'
    //   3833: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3836: pop
    //   3837: aload_3
    //   3838: getstatic java/io/File.separatorChar : C
    //   3841: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3844: pop
    //   3845: aload_3
    //   3846: ldc_w 'arm64-v8a'
    //   3849: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3852: pop
    //   3853: aload_3
    //   3854: getstatic java/io/File.separatorChar : C
    //   3857: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3860: pop
    //   3861: aload_3
    //   3862: invokevirtual toString : ()Ljava/lang/String;
    //   3865: invokestatic copyModToBasePath : (Ljava/lang/String;)V
    //   3868: new java/lang/StringBuilder
    //   3871: dup
    //   3872: invokespecial <init> : ()V
    //   3875: astore_3
    //   3876: aload_3
    //   3877: aload_2
    //   3878: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3881: pop
    //   3882: aload_3
    //   3883: getstatic java/io/File.separatorChar : C
    //   3886: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   3889: pop
    //   3890: aload_3
    //   3891: ldc_w 'lib'
    //   3894: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3897: pop
    //   3898: aload_1
    //   3899: new java/io/File
    //   3902: dup
    //   3903: aload_3
    //   3904: invokevirtual toString : ()Ljava/lang/String;
    //   3907: invokespecial <init> : (Ljava/lang/String;)V
    //   3910: invokevirtual addFolder : (Ljava/io/File;)V
    //   3913: new java/lang/StringBuilder
    //   3916: dup
    //   3917: invokespecial <init> : ()V
    //   3920: astore_3
    //   3921: aload_3
    //   3922: ldc_w 'classes'
    //   3925: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3928: pop
    //   3929: aload_3
    //   3930: iload #6
    //   3932: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   3935: pop
    //   3936: aload_3
    //   3937: ldc_w '.dex'
    //   3940: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3943: pop
    //   3944: aload_3
    //   3945: invokevirtual toString : ()Ljava/lang/String;
    //   3948: astore_3
    //   3949: new net/lingala/zip4j/model/ZipParameters
    //   3952: dup
    //   3953: invokespecial <init> : ()V
    //   3956: astore #11
    //   3958: aload #11
    //   3960: aload_3
    //   3961: invokevirtual setFileNameInZip : (Ljava/lang/String;)V
    //   3964: getstatic com/sk/spatch/kt/mv2/ModifyV2.szPatchPath : Ljava/lang/String;
    //   3967: ifnull -> 4083
    //   3970: new java/io/File
    //   3973: dup
    //   3974: getstatic com/sk/spatch/kt/mv2/ModifyV2.szPatchPath : Ljava/lang/String;
    //   3977: invokespecial <init> : (Ljava/lang/String;)V
    //   3980: invokevirtual exists : ()Z
    //   3983: ifeq -> 3998
    //   3986: aload_1
    //   3987: getstatic com/sk/spatch/kt/mv2/ModifyV2.szPatchPath : Ljava/lang/String;
    //   3990: aload #11
    //   3992: invokevirtual addFile : (Ljava/lang/String;Lnet/lingala/zip4j/model/ZipParameters;)V
    //   3995: goto -> 4165
    //   3998: new java/lang/StringBuilder
    //   4001: dup
    //   4002: invokespecial <init> : ()V
    //   4005: astore #12
    //   4007: aload #12
    //   4009: aload_2
    //   4010: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   4013: pop
    //   4014: aload #12
    //   4016: getstatic java/io/File.separatorChar : C
    //   4019: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   4022: pop
    //   4023: aload #12
    //   4025: aload_3
    //   4026: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   4029: pop
    //   4030: aload_0
    //   4031: ldc_w 'classes.dex'
    //   4034: aload #12
    //   4036: invokevirtual toString : ()Ljava/lang/String;
    //   4039: invokestatic UnZipAssetsFolder : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    //   4042: new java/lang/StringBuilder
    //   4045: dup
    //   4046: invokespecial <init> : ()V
    //   4049: astore_0
    //   4050: aload_0
    //   4051: aload_2
    //   4052: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   4055: pop
    //   4056: aload_0
    //   4057: getstatic java/io/File.separatorChar : C
    //   4060: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   4063: pop
    //   4064: aload_0
    //   4065: aload_3
    //   4066: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   4069: pop
    //   4070: aload_1
    //   4071: aload_0
    //   4072: invokevirtual toString : ()Ljava/lang/String;
    //   4075: aload #11
    //   4077: invokevirtual addFile : (Ljava/lang/String;Lnet/lingala/zip4j/model/ZipParameters;)V
    //   4080: goto -> 4165
    //   4083: new java/lang/StringBuilder
    //   4086: dup
    //   4087: invokespecial <init> : ()V
    //   4090: astore #12
    //   4092: aload #12
    //   4094: aload_2
    //   4095: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   4098: pop
    //   4099: aload #12
    //   4101: getstatic java/io/File.separatorChar : C
    //   4104: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   4107: pop
    //   4108: aload #12
    //   4110: aload_3
    //   4111: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   4114: pop
    //   4115: aload_0
    //   4116: ldc_w 'classes.dex'
    //   4119: aload #12
    //   4121: invokevirtual toString : ()Ljava/lang/String;
    //   4124: invokestatic UnZipAssetsFolder : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    //   4127: new java/lang/StringBuilder
    //   4130: dup
    //   4131: invokespecial <init> : ()V
    //   4134: astore_0
    //   4135: aload_0
    //   4136: aload_2
    //   4137: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   4140: pop
    //   4141: aload_0
    //   4142: getstatic java/io/File.separatorChar : C
    //   4145: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   4148: pop
    //   4149: aload_0
    //   4150: aload_3
    //   4151: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   4154: pop
    //   4155: aload_1
    //   4156: aload_0
    //   4157: invokevirtual toString : ()Ljava/lang/String;
    //   4160: aload #11
    //   4162: invokevirtual addFile : (Ljava/lang/String;Lnet/lingala/zip4j/model/ZipParameters;)V
    //   4165: new java/lang/StringBuilder
    //   4168: dup
    //   4169: invokespecial <init> : ()V
    //   4172: astore_0
    //   4173: aload_0
    //   4174: aload_2
    //   4175: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   4178: pop
    //   4179: aload_0
    //   4180: getstatic java/io/File.separatorChar : C
    //   4183: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   4186: pop
    //   4187: aload_0
    //   4188: ldc 'assets'
    //   4190: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   4193: pop
    //   4194: aload_1
    //   4195: new java/io/File
    //   4198: dup
    //   4199: aload_0
    //   4200: invokevirtual toString : ()Ljava/lang/String;
    //   4203: invokespecial <init> : (Ljava/lang/String;)V
    //   4206: invokevirtual addFolder : (Ljava/io/File;)V
    //   4209: getstatic com/sk/spatch/kt/mv2/ModifyV2.isDebug : Z
    //   4212: ifeq -> 4224
    //   4215: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   4218: ldc_w 'SKApp repack done, setup callback...'
    //   4221: invokevirtual println : (Ljava/lang/String;)V
    //   4224: new java/lang/StringBuilder
    //   4227: dup
    //   4228: invokespecial <init> : ()V
    //   4231: astore_0
    //   4232: aload_0
    //   4233: aload_2
    //   4234: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   4237: pop
    //   4238: aload_0
    //   4239: getstatic com/sk/spatch/kt/mv2/ModifyV2.TempApkUnsigned : Ljava/lang/String;
    //   4242: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   4245: pop
    //   4246: new com/sk/spatch/kt/mv2/ModifyV2$1
    //   4249: dup
    //   4250: aload_2
    //   4251: aload_0
    //   4252: invokevirtual toString : ()Ljava/lang/String;
    //   4255: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;)V
    //   4258: putstatic com/sk/spatch/kt/mv2/ModifyV2.theCallback : Lcom/sk/spatch/kt/mv2/ModifyV2$cleanBufferCallback;
    //   4261: iconst_1
    //   4262: ireturn
    //   4263: astore_0
    //   4264: aload #5
    //   4266: iconst_0
    //   4267: invokeinterface setInSuccess : (Z)V
    //   4272: aload_0
    //   4273: athrow
    //   4274: aload_1
    //   4275: invokevirtual close : ()V
    //   4278: aload_3
    //   4279: invokevirtual close : ()V
    //   4282: aload_0
    //   4283: athrow
    //   4284: aload_2
    //   4285: invokevirtual close : ()V
    //   4288: aload_0
    //   4289: invokevirtual close : ()V
    //   4292: aload_1
    //   4293: athrow
    //   4294: astore_0
    //   4295: goto -> 4299
    //   4298: astore_0
    //   4299: aload #5
    //   4301: iconst_0
    //   4302: invokeinterface setInSuccess : (Z)V
    //   4307: aload_0
    //   4308: athrow
    //   4309: new java/lang/Exception
    //   4312: dup
    //   4313: ldc_w 'chmod failed!'
    //   4316: invokespecial <init> : (Ljava/lang/String;)V
    //   4319: athrow
    //   4320: new java/lang/StringBuilder
    //   4323: dup
    //   4324: invokespecial <init> : ()V
    //   4327: astore_0
    //   4328: aload_0
    //   4329: aload #11
    //   4331: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   4334: pop
    //   4335: aload_0
    //   4336: ldc_w ' file not found...'
    //   4339: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   4342: pop
    //   4343: new java/io/FileNotFoundException
    //   4346: dup
    //   4347: aload_0
    //   4348: invokevirtual toString : ()Ljava/lang/String;
    //   4351: invokespecial <init> : (Ljava/lang/String;)V
    //   4354: athrow
    //   4355: astore_0
    //   4356: goto -> 4360
    //   4359: astore_0
    //   4360: goto -> 4364
    //   4363: astore_0
    //   4364: aload #5
    //   4366: iconst_0
    //   4367: invokeinterface setInSuccess : (Z)V
    //   4372: aload_0
    //   4373: invokevirtual printStackTrace : ()V
    //   4376: aload_0
    //   4377: invokevirtual getCause : ()Ljava/lang/Throwable;
    //   4380: ifnull -> 4399
    //   4383: aload #5
    //   4385: aload_0
    //   4386: invokevirtual getCause : ()Ljava/lang/Throwable;
    //   4389: invokevirtual getMessage : ()Ljava/lang/String;
    //   4392: invokeinterface setFailedReason : (Ljava/lang/String;)V
    //   4397: iconst_0
    //   4398: ireturn
    //   4399: aload #5
    //   4401: aload_0
    //   4402: invokevirtual getMessage : ()Ljava/lang/String;
    //   4405: invokeinterface setFailedReason : (Ljava/lang/String;)V
    //   4410: iconst_0
    //   4411: ireturn
    //   4412: astore_0
    //   4413: goto -> 4299
    //   4416: astore_0
    //   4417: aload #11
    //   4419: astore_3
    //   4420: goto -> 4424
    //   4423: astore_0
    //   4424: aload #12
    //   4426: astore_1
    //   4427: goto -> 4274
    //   4430: astore_0
    //   4431: aload #11
    //   4433: astore_1
    //   4434: goto -> 4274
    //   4437: astore_1
    //   4438: aload_3
    //   4439: astore_0
    //   4440: goto -> 4447
    //   4443: astore_1
    //   4444: aload #13
    //   4446: astore_0
    //   4447: aload #11
    //   4449: astore_2
    //   4450: goto -> 4284
    //   4453: astore_1
    //   4454: aload #12
    //   4456: astore_2
    //   4457: aload_3
    //   4458: astore_0
    //   4459: goto -> 4284
    //   4462: astore_0
    //   4463: goto -> 4364
    //   4466: astore_0
    //   4467: goto -> 4364
    // Exception table:
    //   from	to	target	type
    //   10	133	4363	finally
    //   138	153	155	finally
    //   159	164	4363	finally
    //   169	239	155	finally
    //   245	360	4363	finally
    //   367	444	515	finally
    //   447	512	515	finally
    //   519	530	4359	finally
    //   535	600	515	finally
    //   603	668	4359	finally
    //   668	688	4359	finally
    //   693	734	515	finally
    //   734	745	4359	finally
    //   745	755	4466	finally
    //   755	824	4359	finally
    //   829	902	515	finally
    //   902	958	4359	finally
    //   963	978	515	finally
    //   982	991	4359	finally
    //   996	1003	515	finally
    //   1045	1132	515	finally
    //   1135	1142	4359	finally
    //   1184	1271	4359	finally
    //   1276	1320	515	finally
    //   1320	1395	4359	finally
    //   1406	1412	515	finally
    //   1415	1425	515	finally
    //   1428	1443	515	finally
    //   1446	1455	4359	finally
    //   1460	1475	515	finally
    //   1477	1498	4359	finally
    //   1503	1518	515	finally
    //   1520	1587	4359	finally
    //   1587	1681	4298	finally
    //   1686	1695	1698	finally
    //   1702	1707	4298	finally
    //   1714	1734	1698	finally
    //   1734	1765	1698	finally
    //   1765	1776	1698	finally
    //   1795	1805	4453	finally
    //   1805	1815	4443	finally
    //   1815	1822	4437	finally
    //   1822	1831	4437	finally
    //   1837	1846	1849	finally
    //   1853	1862	4298	finally
    //   1862	1872	4430	finally
    //   1872	1883	4423	finally
    //   1883	1889	4416	finally
    //   1889	1897	4416	finally
    //   1903	1912	1915	finally
    //   1919	1929	4298	finally
    //   1929	1957	4298	finally
    //   1962	1971	1698	finally
    //   1971	2018	4298	finally
    //   2018	2028	4263	finally
    //   2040	2048	4263	finally
    //   2053	2120	2205	finally
    //   2129	2143	2205	finally
    //   2153	2167	2205	finally
    //   2177	2190	2205	finally
    //   2220	2225	4263	finally
    //   2230	2329	2205	finally
    //   2334	2403	2205	finally
    //   2403	2647	2205	finally
    //   2655	2724	4263	finally
    //   2724	3056	4263	finally
    //   3061	3130	4263	finally
    //   3130	3462	4263	finally
    //   3467	3536	4263	finally
    //   3536	3868	4263	finally
    //   3868	3913	4263	finally
    //   3913	3995	4263	finally
    //   3998	4080	4263	finally
    //   4083	4165	4263	finally
    //   4165	4224	4263	finally
    //   4224	4261	4263	finally
    //   4264	4272	4412	finally
    //   4272	4274	4294	finally
    //   4274	4284	4294	finally
    //   4284	4294	4294	finally
    //   4299	4307	4462	finally
    //   4307	4309	4355	finally
    //   4309	4320	4355	finally
    //   4320	4355	4355	finally }
  
  private static void writeInt(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    int i = paramInt1 + 1;
    paramArrayOfbyte[paramInt1] = (byte)(paramInt2 & 0xFF);
    paramInt1 = i + 1;
    paramArrayOfbyte[i] = (byte)(paramInt2 >>> 8 & 0xFF);
    paramArrayOfbyte[paramInt1] = (byte)(paramInt2 >>> 16 & 0xFF);
    paramArrayOfbyte[paramInt1 + 1] = (byte)(paramInt2 >>> 24 & 0xFF);
  }
  
  public static native boolean writeToFileAppend(String paramString1, String paramString2);
  
  public static boolean xmlDecompiler(String paramString1, String paramString2) {
    try {
      AXmlResourceParser aXmlResourceParser = new AXmlResourceParser();
      aXmlResourceParser.open(new FileInputStream(paramString1));
      StringBuilder stringBuilder = new StringBuilder(10);
      while (true) {
        int i = aXmlResourceParser.next();
        if (i == 1)
          return true; 
        if (i != 0) {
          if (i != 2) {
            if (i != 3) {
              if (i != 4)
                continue; 
              printXML(paramString2, "%s%s", new Object[] { stringBuilder, aXmlResourceParser.getText() });
              continue;
            } 
            stringBuilder.setLength(stringBuilder.length() - 1);
            printXML(paramString2, "%s</%s%s>", new Object[] { stringBuilder, getNamespacePrefix(aXmlResourceParser.getPrefix()), aXmlResourceParser.getName() });
            continue;
          } 
          printXML(paramString2, "%s<%s%s", new Object[] { stringBuilder, getNamespacePrefix(aXmlResourceParser.getPrefix()), aXmlResourceParser.getName() });
          stringBuilder.append("\t");
          i = aXmlResourceParser.getNamespaceCount(aXmlResourceParser.getDepth() - 1);
          int j = aXmlResourceParser.getNamespaceCount(aXmlResourceParser.getDepth());
          while (i != j) {
            printXML(paramString2, "%sxmlns:%s=\"%s\"", new Object[] { stringBuilder, aXmlResourceParser.getNamespacePrefix(i), aXmlResourceParser.getNamespaceUri(i) });
            i++;
          } 
          for (i = 0; i != aXmlResourceParser.getAttributeCount(); i++) {
            printXML(paramString2, "%s%s%s=\"%s\"", new Object[] { stringBuilder, getNamespacePrefix(aXmlResourceParser.getAttributePrefix(i)), aXmlResourceParser.getAttributeName(i), getAttributeValue(aXmlResourceParser, i) });
          } 
          printXML(paramString2, "%s>", new Object[] { stringBuilder });
          continue;
        } 
        printXML(paramString2, "<?xml version=\"1.0\" encoding=\"utf-8\"?>", new Object[0]);
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
      if (isDebug)
        System.err.println("xmlDecompile函数失败，退出。"); 
      return false;
    } 
  }
  
  public static interface cleanBufferCallback {
    void cleanBuffer();
    
    String getUnsignedPath();
  }
  
  public static interface theCallback {
    String getFailedReason();
    
    boolean isSuccess();
    
    void setFailedReason(String param1String);
    
    void setInSuccess(boolean param1Boolean);
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\kt\mv2\ModifyV2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */