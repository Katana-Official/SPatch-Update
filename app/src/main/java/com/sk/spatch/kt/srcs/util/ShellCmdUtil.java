package com.sk.spatch.kt.srcs.util;

import java.io.Closeable;
import java.io.File;

public class ShellCmdUtil {
  private static void close(Closeable paramCloseable) {
    if (paramCloseable != null)
      try {
        paramCloseable.close();
        return;
      } catch (Exception exception) {
        return;
      }  
  }
  
  public static String execCmd(String paramString, File paramFile) throws Exception { // Byte code:
    //   0: new java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore #5
    //   9: aconst_null
    //   10: astore_3
    //   11: invokestatic getRuntime : ()Ljava/lang/Runtime;
    //   14: aload_0
    //   15: aconst_null
    //   16: aload_1
    //   17: invokevirtual exec : (Ljava/lang/String;[Ljava/lang/String;Ljava/io/File;)Ljava/lang/Process;
    //   20: astore_2
    //   21: aload_2
    //   22: invokevirtual waitFor : ()I
    //   25: pop
    //   26: new java/io/BufferedReader
    //   29: dup
    //   30: new java/io/InputStreamReader
    //   33: dup
    //   34: aload_2
    //   35: invokevirtual getInputStream : ()Ljava/io/InputStream;
    //   38: ldc 'UTF-8'
    //   40: invokespecial <init> : (Ljava/io/InputStream;Ljava/lang/String;)V
    //   43: invokespecial <init> : (Ljava/io/Reader;)V
    //   46: astore #4
    //   48: new java/io/BufferedReader
    //   51: dup
    //   52: new java/io/InputStreamReader
    //   55: dup
    //   56: aload_2
    //   57: invokevirtual getErrorStream : ()Ljava/io/InputStream;
    //   60: ldc 'UTF-8'
    //   62: invokespecial <init> : (Ljava/io/InputStream;Ljava/lang/String;)V
    //   65: invokespecial <init> : (Ljava/io/Reader;)V
    //   68: astore_1
    //   69: aload #4
    //   71: invokevirtual readLine : ()Ljava/lang/String;
    //   74: astore_0
    //   75: aload_0
    //   76: ifnull -> 97
    //   79: aload #5
    //   81: aload_0
    //   82: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   85: pop
    //   86: aload #5
    //   88: bipush #10
    //   90: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   93: pop
    //   94: goto -> 69
    //   97: aload_1
    //   98: invokevirtual readLine : ()Ljava/lang/String;
    //   101: astore_0
    //   102: aload_0
    //   103: ifnull -> 124
    //   106: aload #5
    //   108: aload_0
    //   109: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   112: pop
    //   113: aload #5
    //   115: bipush #10
    //   117: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   120: pop
    //   121: goto -> 97
    //   124: aload #4
    //   126: invokestatic close : (Ljava/io/Closeable;)V
    //   129: aload_1
    //   130: invokestatic close : (Ljava/io/Closeable;)V
    //   133: aload_2
    //   134: ifnull -> 184
    //   137: goto -> 180
    //   140: astore_0
    //   141: goto -> 147
    //   144: astore_0
    //   145: aconst_null
    //   146: astore_1
    //   147: aload #4
    //   149: astore_3
    //   150: goto -> 164
    //   153: astore_0
    //   154: aconst_null
    //   155: astore_1
    //   156: goto -> 164
    //   159: astore_0
    //   160: aconst_null
    //   161: astore_2
    //   162: aload_2
    //   163: astore_1
    //   164: aload_0
    //   165: invokevirtual printStackTrace : ()V
    //   168: aload_3
    //   169: invokestatic close : (Ljava/io/Closeable;)V
    //   172: aload_1
    //   173: invokestatic close : (Ljava/io/Closeable;)V
    //   176: aload_2
    //   177: ifnull -> 184
    //   180: aload_2
    //   181: invokevirtual destroy : ()V
    //   184: aload #5
    //   186: invokevirtual toString : ()Ljava/lang/String;
    //   189: areturn
    //   190: astore_0
    //   191: aload_3
    //   192: invokestatic close : (Ljava/io/Closeable;)V
    //   195: aload_1
    //   196: invokestatic close : (Ljava/io/Closeable;)V
    //   199: aload_2
    //   200: ifnull -> 207
    //   203: aload_2
    //   204: invokevirtual destroy : ()V
    //   207: aload_0
    //   208: athrow
    // Exception table:
    //   from	to	target	type
    //   11	21	159	finally
    //   21	48	153	finally
    //   48	69	144	finally
    //   69	75	140	finally
    //   79	94	140	finally
    //   97	102	140	finally
    //   106	121	140	finally
    //   164	168	190	finally }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\kt\src\\util\ShellCmdUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */