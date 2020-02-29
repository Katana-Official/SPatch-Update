package com.sk.spatch.kt.srcs.util;

import java.io.InputStream;
import java.security.cert.Certificate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ApkSignatureHelper {
  public static String getApkSignInfo(String paramString) { // Byte code:
    //   0: sipush #8192
    //   3: newarray byte
    //   5: astore #4
    //   7: new java/util/jar/JarFile
    //   10: dup
    //   11: aload_0
    //   12: invokespecial <init> : (Ljava/lang/String;)V
    //   15: astore #5
    //   17: aload #5
    //   19: invokevirtual entries : ()Ljava/util/Enumeration;
    //   22: astore #6
    //   24: aconst_null
    //   25: astore_0
    //   26: aload #6
    //   28: invokeinterface hasMoreElements : ()Z
    //   33: ifeq -> 148
    //   36: aload #6
    //   38: invokeinterface nextElement : ()Ljava/lang/Object;
    //   43: checkcast java/util/jar/JarEntry
    //   46: astore_3
    //   47: aload_3
    //   48: invokevirtual isDirectory : ()Z
    //   51: ifeq -> 57
    //   54: goto -> 26
    //   57: aload_3
    //   58: invokevirtual getName : ()Ljava/lang/String;
    //   61: ldc 'META-INF/'
    //   63: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   66: ifeq -> 72
    //   69: goto -> 26
    //   72: aload #5
    //   74: aload_3
    //   75: aload #4
    //   77: invokestatic loadCertificates : (Ljava/util/jar/JarFile;Ljava/util/jar/JarEntry;[B)[Ljava/security/cert/Certificate;
    //   80: astore_3
    //   81: aload_0
    //   82: ifnonnull -> 218
    //   85: aload_3
    //   86: astore_0
    //   87: goto -> 26
    //   90: iload_1
    //   91: aload_0
    //   92: arraylength
    //   93: if_icmpge -> 26
    //   96: iconst_0
    //   97: istore_2
    //   98: iload_2
    //   99: aload_3
    //   100: arraylength
    //   101: if_icmpge -> 230
    //   104: aload_0
    //   105: iload_1
    //   106: aaload
    //   107: ifnull -> 223
    //   110: aload_0
    //   111: iload_1
    //   112: aaload
    //   113: aload_3
    //   114: iload_2
    //   115: aaload
    //   116: invokevirtual equals : (Ljava/lang/Object;)Z
    //   119: ifeq -> 223
    //   122: iconst_1
    //   123: istore_2
    //   124: goto -> 127
    //   127: iload_2
    //   128: ifeq -> 141
    //   131: aload_0
    //   132: arraylength
    //   133: aload_3
    //   134: arraylength
    //   135: if_icmpeq -> 235
    //   138: goto -> 141
    //   141: aload #5
    //   143: invokevirtual close : ()V
    //   146: aconst_null
    //   147: areturn
    //   148: aload #5
    //   150: invokevirtual close : ()V
    //   153: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   156: astore_3
    //   157: new java/lang/StringBuilder
    //   160: dup
    //   161: invokespecial <init> : ()V
    //   164: astore #4
    //   166: aload #4
    //   168: ldc '  getApkSignInfo  result -->  '
    //   170: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   173: pop
    //   174: aload #4
    //   176: aload_0
    //   177: iconst_0
    //   178: aaload
    //   179: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   182: pop
    //   183: aload_3
    //   184: aload #4
    //   186: invokevirtual toString : ()Ljava/lang/String;
    //   189: invokevirtual println : (Ljava/lang/String;)V
    //   192: new java/lang/String
    //   195: dup
    //   196: aload_0
    //   197: iconst_0
    //   198: aaload
    //   199: invokevirtual getEncoded : ()[B
    //   202: invokestatic toChars : ([B)[C
    //   205: invokespecial <init> : ([C)V
    //   208: astore_0
    //   209: aload_0
    //   210: areturn
    //   211: astore_0
    //   212: aload_0
    //   213: invokevirtual printStackTrace : ()V
    //   216: aconst_null
    //   217: areturn
    //   218: iconst_0
    //   219: istore_1
    //   220: goto -> 90
    //   223: iload_2
    //   224: iconst_1
    //   225: iadd
    //   226: istore_2
    //   227: goto -> 98
    //   230: iconst_0
    //   231: istore_2
    //   232: goto -> 127
    //   235: iload_1
    //   236: iconst_1
    //   237: iadd
    //   238: istore_1
    //   239: goto -> 90
    // Exception table:
    //   from	to	target	type
    //   7	24	211	java/lang/Exception
    //   26	54	211	java/lang/Exception
    //   57	69	211	java/lang/Exception
    //   72	81	211	java/lang/Exception
    //   90	96	211	java/lang/Exception
    //   98	104	211	java/lang/Exception
    //   110	122	211	java/lang/Exception
    //   131	138	211	java/lang/Exception
    //   141	146	211	java/lang/Exception
    //   148	209	211	java/lang/Exception }
  
  private static Certificate[] loadCertificates(JarFile paramJarFile, JarEntry paramJarEntry, byte[] paramArrayOfbyte) {
    try {
      InputStream inputStream = paramJarFile.getInputStream(paramJarEntry);
      while (inputStream.read(paramArrayOfbyte, 0, paramArrayOfbyte.length) != -1);
      inputStream.close();
      if (paramJarEntry != null) {
        null = paramJarEntry.getCertificates();
        return null;
      } 
    } catch (Exception exception) {
      return null;
    } 
    paramJarFile = null;
    return (Certificate[])paramJarFile;
  }
  
  private static char[] toChars(byte[] paramArrayOfbyte) {
    int j = paramArrayOfbyte.length;
    char[] arrayOfChar = new char[j * 2];
    for (int i = 0; i < j; i++) {
      byte b = paramArrayOfbyte[i];
      int k = b >> 4 & 0xF;
      int m = i * 2;
      if (k >= 10) {
        k = k + 97 - 10;
      } else {
        k += 48;
      } 
      arrayOfChar[m] = (char)k;
      k = b & 0xF;
      if (k >= 10) {
        k = k + 97 - 10;
      } else {
        k += 48;
      } 
      arrayOfChar[m + 1] = (char)k;
    } 
    return arrayOfChar;
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\kt\src\\util\ApkSignatureHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */