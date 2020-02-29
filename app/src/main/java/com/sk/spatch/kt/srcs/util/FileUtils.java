package com.sk.spatch.kt.srcs.util;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.zip.ZipOutputStream;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;

public class FileUtils {
  static final int BUFFER = 8192;
  
  private static void close(Closeable paramCloseable) {
    if (paramCloseable != null)
      try {
        paramCloseable.close();
        return;
      } catch (IOException iOException) {
        iOException.printStackTrace();
      }  
  }
  
  private static void compress(File paramFile, ZipOutputStream paramZipOutputStream, String paramString, boolean paramBoolean) throws IOException {
    if (paramFile.isDirectory()) {
      compressDirectory(paramFile, paramZipOutputStream, paramString, paramBoolean);
      return;
    } 
    compressFile(paramFile, paramZipOutputStream, paramString);
  }
  
  private static void compressDirectory(File paramFile, ZipOutputStream paramZipOutputStream, String paramString, boolean paramBoolean) throws IOException {
    File[] arrayOfFile = paramFile.listFiles();
    if (arrayOfFile == null)
      return; 
    int i;
    for (i = 0; i < arrayOfFile.length; i++) {
      String str;
      if (!paramBoolean) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(paramString);
        stringBuilder.append(paramFile.getName());
        stringBuilder.append("/");
        str = stringBuilder.toString();
      } else {
        str = "";
      } 
      compress(arrayOfFile[i], paramZipOutputStream, str, false);
    } 
  }
  
  private static void compressFile(File paramFile, ZipOutputStream paramZipOutputStream, String paramString) throws IOException {
    Object[] arrayOfObject;
    if (!paramFile.exists())
      return; 
    StringBuilder stringBuilder = null;
    try {
      BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(paramFile));
    } finally {
      paramZipOutputStream = null;
    } 
    if (arrayOfObject != null)
      arrayOfObject.close(); 
    throw paramZipOutputStream;
  }
  
  public static void compressToZip(String paramString1, String paramString2) { // Byte code:
    //   0: new java/io/File
    //   3: dup
    //   4: aload_0
    //   5: invokespecial <init> : (Ljava/lang/String;)V
    //   8: astore #4
    //   10: new java/io/File
    //   13: dup
    //   14: aload_1
    //   15: invokespecial <init> : (Ljava/lang/String;)V
    //   18: astore_1
    //   19: aload #4
    //   21: invokevirtual exists : ()Z
    //   24: ifne -> 61
    //   27: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   30: astore_1
    //   31: new java/lang/StringBuilder
    //   34: dup
    //   35: invokespecial <init> : ()V
    //   38: astore_2
    //   39: aload_2
    //   40: aload_0
    //   41: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   44: pop
    //   45: aload_2
    //   46: ldc ' does not exist ！'
    //   48: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   51: pop
    //   52: aload_1
    //   53: aload_2
    //   54: invokevirtual toString : ()Ljava/lang/String;
    //   57: invokevirtual println : (Ljava/lang/String;)V
    //   60: return
    //   61: new java/io/FileOutputStream
    //   64: dup
    //   65: aload_1
    //   66: invokespecial <init> : (Ljava/io/File;)V
    //   69: astore_1
    //   70: new java/util/zip/ZipOutputStream
    //   73: dup
    //   74: new java/util/zip/CheckedOutputStream
    //   77: dup
    //   78: aload_1
    //   79: new java/util/zip/CRC32
    //   82: dup
    //   83: invokespecial <init> : ()V
    //   86: invokespecial <init> : (Ljava/io/OutputStream;Ljava/util/zip/Checksum;)V
    //   89: invokespecial <init> : (Ljava/io/OutputStream;)V
    //   92: astore_0
    //   93: aload_0
    //   94: astore_2
    //   95: aload_1
    //   96: astore_3
    //   97: aload #4
    //   99: aload_0
    //   100: ldc ''
    //   102: iconst_1
    //   103: invokestatic compress : (Ljava/io/File;Ljava/util/zip/ZipOutputStream;Ljava/lang/String;Z)V
    //   106: aload_0
    //   107: invokevirtual closeEntry : ()V
    //   110: aload_0
    //   111: astore_2
    //   112: aload_1
    //   113: astore_3
    //   114: goto -> 126
    //   117: astore_2
    //   118: aload_2
    //   119: invokevirtual printStackTrace : ()V
    //   122: aload_1
    //   123: astore_3
    //   124: aload_0
    //   125: astore_2
    //   126: aload_2
    //   127: invokestatic close : (Ljava/io/Closeable;)V
    //   130: aload_3
    //   131: invokestatic close : (Ljava/io/Closeable;)V
    //   134: return
    //   135: astore #4
    //   137: goto -> 167
    //   140: astore_0
    //   141: aconst_null
    //   142: astore_2
    //   143: goto -> 256
    //   146: astore #4
    //   148: aconst_null
    //   149: astore_0
    //   150: goto -> 167
    //   153: astore_0
    //   154: aconst_null
    //   155: astore_2
    //   156: aload_2
    //   157: astore_1
    //   158: goto -> 256
    //   161: astore #4
    //   163: aconst_null
    //   164: astore_0
    //   165: aload_0
    //   166: astore_1
    //   167: aload_0
    //   168: astore_2
    //   169: aload_1
    //   170: astore_3
    //   171: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   174: astore #5
    //   176: aload_0
    //   177: astore_2
    //   178: aload_1
    //   179: astore_3
    //   180: new java/lang/StringBuilder
    //   183: dup
    //   184: invokespecial <init> : ()V
    //   187: astore #6
    //   189: aload_0
    //   190: astore_2
    //   191: aload_1
    //   192: astore_3
    //   193: aload #6
    //   195: ldc ' compress exception = '
    //   197: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   200: pop
    //   201: aload_0
    //   202: astore_2
    //   203: aload_1
    //   204: astore_3
    //   205: aload #6
    //   207: aload #4
    //   209: invokevirtual getMessage : ()Ljava/lang/String;
    //   212: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   215: pop
    //   216: aload_0
    //   217: astore_2
    //   218: aload_1
    //   219: astore_3
    //   220: aload #5
    //   222: aload #6
    //   224: invokevirtual toString : ()Ljava/lang/String;
    //   227: invokevirtual println : (Ljava/lang/String;)V
    //   230: aload_0
    //   231: astore_2
    //   232: aload_1
    //   233: astore_3
    //   234: aload_0
    //   235: ifnull -> 126
    //   238: aload_0
    //   239: invokevirtual closeEntry : ()V
    //   242: aload_0
    //   243: astore_2
    //   244: aload_1
    //   245: astore_3
    //   246: goto -> 126
    //   249: astore_2
    //   250: goto -> 118
    //   253: astore_0
    //   254: aload_3
    //   255: astore_1
    //   256: aload_2
    //   257: ifnull -> 272
    //   260: aload_2
    //   261: invokevirtual closeEntry : ()V
    //   264: goto -> 272
    //   267: astore_3
    //   268: aload_3
    //   269: invokevirtual printStackTrace : ()V
    //   272: aload_2
    //   273: invokestatic close : (Ljava/io/Closeable;)V
    //   276: aload_1
    //   277: invokestatic close : (Ljava/io/Closeable;)V
    //   280: aload_0
    //   281: athrow
    // Exception table:
    //   from	to	target	type
    //   61	70	161	java/io/IOException
    //   61	70	153	finally
    //   70	93	146	java/io/IOException
    //   70	93	140	finally
    //   97	106	135	java/io/IOException
    //   97	106	253	finally
    //   106	110	117	java/io/IOException
    //   171	176	253	finally
    //   180	189	253	finally
    //   193	201	253	finally
    //   205	216	253	finally
    //   220	230	253	finally
    //   238	242	249	java/io/IOException
    //   260	264	267	java/io/IOException }
  
  public static boolean compressToZipEx(String paramString1, String paramString2) {
    File[] arrayOfFile = (new File(paramString1)).listFiles();
    if (arrayOfFile == null)
      return false; 
    ZipParameters zipParameters = new ZipParameters();
    zipParameters.setEncryptFiles(false);
    zipParameters.setCompressionLevel(CompressionLevel.MAXIMUM);
    zipParameters.setCompressionMethod(CompressionMethod.DEFLATE);
    try {
      int i;
      ZipFile zipFile = new ZipFile(paramString2);
      return true;
    } finally {
      arrayOfFile = null;
      arrayOfFile.printStackTrace();
    } 
  }
  
  public static void copyFile(File paramFile1, File paramFile2) {
    Object object1;
    object2 = null;
    ByteBuffer byteBuffer = null;
    try {
      Object object;
    } catch (IOException object2) {
    
    } finally {
      Exception exception = null;
      paramFile1 = null;
      object1 = object2;
    } 
    try {
      object2.printStackTrace();
      close((Closeable)object1);
      return;
    } finally {
      Exception exception = null;
    } 
  }
  
  public static void copyFile(String paramString1, String paramString2) { copyFile(new File(paramString1), new File(paramString2)); }
  
  public static void copyFileFromJar(String paramString1, String paramString2) { // Byte code:
    //   0: aload_0
    //   1: invokestatic getInputStreamFromFile : (Ljava/lang/String;)Ljava/io/InputStream;
    //   4: astore_0
    //   5: aconst_null
    //   6: astore #4
    //   8: aconst_null
    //   9: astore_3
    //   10: aconst_null
    //   11: astore #6
    //   13: new java/io/BufferedInputStream
    //   16: dup
    //   17: aload_0
    //   18: invokespecial <init> : (Ljava/io/InputStream;)V
    //   21: astore_0
    //   22: aload #4
    //   24: astore_3
    //   25: aload_0
    //   26: astore #4
    //   28: new java/io/BufferedOutputStream
    //   31: dup
    //   32: new java/io/FileOutputStream
    //   35: dup
    //   36: aload_1
    //   37: invokespecial <init> : (Ljava/lang/String;)V
    //   40: invokespecial <init> : (Ljava/io/OutputStream;)V
    //   43: astore_1
    //   44: sipush #1024
    //   47: newarray byte
    //   49: astore_3
    //   50: aload_0
    //   51: aload_3
    //   52: invokevirtual read : ([B)I
    //   55: istore_2
    //   56: iload_2
    //   57: iconst_m1
    //   58: if_icmpeq -> 71
    //   61: aload_1
    //   62: aload_3
    //   63: iconst_0
    //   64: iload_2
    //   65: invokevirtual write : ([BII)V
    //   68: goto -> 50
    //   71: aload_1
    //   72: invokestatic close : (Ljava/io/Closeable;)V
    //   75: goto -> 128
    //   78: astore #4
    //   80: aload_1
    //   81: astore_3
    //   82: aload #4
    //   84: astore_1
    //   85: goto -> 137
    //   88: astore #5
    //   90: goto -> 114
    //   93: astore #5
    //   95: aload #6
    //   97: astore_1
    //   98: goto -> 114
    //   101: astore_1
    //   102: aconst_null
    //   103: astore_0
    //   104: goto -> 137
    //   107: astore #5
    //   109: aconst_null
    //   110: astore_0
    //   111: aload #6
    //   113: astore_1
    //   114: aload_1
    //   115: astore_3
    //   116: aload_0
    //   117: astore #4
    //   119: aload #5
    //   121: invokevirtual printStackTrace : ()V
    //   124: aload_1
    //   125: invokestatic close : (Ljava/io/Closeable;)V
    //   128: aload_0
    //   129: invokestatic close : (Ljava/io/Closeable;)V
    //   132: return
    //   133: astore_1
    //   134: aload #4
    //   136: astore_0
    //   137: aload_3
    //   138: invokestatic close : (Ljava/io/Closeable;)V
    //   141: aload_0
    //   142: invokestatic close : (Ljava/io/Closeable;)V
    //   145: aload_1
    //   146: athrow
    // Exception table:
    //   from	to	target	type
    //   13	22	107	java/io/IOException
    //   13	22	101	finally
    //   28	44	93	java/io/IOException
    //   28	44	133	finally
    //   44	50	88	java/io/IOException
    //   44	50	78	finally
    //   50	56	88	java/io/IOException
    //   50	56	78	finally
    //   61	68	88	java/io/IOException
    //   61	68	78	finally
    //   119	124	133	finally }
  
  public static boolean decompressZip(String paramString1, String paramString2) {
    try {
      (new ZipFile(paramString1)).extractAll(paramString2);
      return true;
    } catch (ZipException zipException) {
      zipException.printStackTrace();
      return false;
    } 
  }
  
  public static void deleteDir(File paramFile) {
    if (paramFile.isDirectory()) {
      File[] arrayOfFile = paramFile.listFiles();
      if (arrayOfFile != null && arrayOfFile.length > 0) {
        int j = arrayOfFile.length;
        for (int i = 0; i < j; i++)
          deleteDir(arrayOfFile[i]); 
      } 
    } 
    paramFile.delete();
  }
  
  private static InputStream getInputStreamFromFile(String paramString) { return FileUtils.class.getClassLoader().getResourceAsStream(paramString); }
  
  public static void writeFile(String paramString1, String paramString2) { // Byte code:
    //   0: aload_0
    //   1: ifnull -> 171
    //   4: aload_0
    //   5: invokevirtual isEmpty : ()Z
    //   8: ifeq -> 12
    //   11: return
    //   12: aload_1
    //   13: ifnull -> 171
    //   16: aload_1
    //   17: invokevirtual isEmpty : ()Z
    //   20: ifeq -> 24
    //   23: return
    //   24: new java/io/File
    //   27: dup
    //   28: aload_0
    //   29: invokespecial <init> : (Ljava/lang/String;)V
    //   32: astore_0
    //   33: aload_0
    //   34: invokevirtual getParentFile : ()Ljava/io/File;
    //   37: invokevirtual exists : ()Z
    //   40: ifne -> 51
    //   43: aload_0
    //   44: invokevirtual getParentFile : ()Ljava/io/File;
    //   47: invokevirtual mkdirs : ()Z
    //   50: pop
    //   51: new java/io/FileOutputStream
    //   54: dup
    //   55: aload_0
    //   56: invokespecial <init> : (Ljava/io/File;)V
    //   59: astore_0
    //   60: new java/io/BufferedWriter
    //   63: dup
    //   64: new java/io/OutputStreamWriter
    //   67: dup
    //   68: aload_0
    //   69: invokespecial <init> : (Ljava/io/OutputStream;)V
    //   72: invokespecial <init> : (Ljava/io/Writer;)V
    //   75: astore #4
    //   77: aload #4
    //   79: astore_2
    //   80: aload_0
    //   81: astore_3
    //   82: aload #4
    //   84: aload_1
    //   85: invokevirtual write : (Ljava/lang/String;)V
    //   88: aload #4
    //   90: astore_2
    //   91: aload_0
    //   92: astore_3
    //   93: aload #4
    //   95: invokevirtual flush : ()V
    //   98: aload #4
    //   100: astore_1
    //   101: goto -> 149
    //   104: astore_2
    //   105: aload #4
    //   107: astore_1
    //   108: aload_2
    //   109: astore #4
    //   111: goto -> 140
    //   114: astore_1
    //   115: goto -> 125
    //   118: astore_1
    //   119: goto -> 133
    //   122: astore_1
    //   123: aconst_null
    //   124: astore_0
    //   125: aconst_null
    //   126: astore_2
    //   127: goto -> 161
    //   130: astore_1
    //   131: aconst_null
    //   132: astore_0
    //   133: aconst_null
    //   134: astore_2
    //   135: aload_1
    //   136: astore #4
    //   138: aload_2
    //   139: astore_1
    //   140: aload_1
    //   141: astore_2
    //   142: aload_0
    //   143: astore_3
    //   144: aload #4
    //   146: invokevirtual printStackTrace : ()V
    //   149: aload_0
    //   150: invokestatic close : (Ljava/io/Closeable;)V
    //   153: aload_1
    //   154: invokestatic close : (Ljava/io/Closeable;)V
    //   157: return
    //   158: astore_1
    //   159: aload_3
    //   160: astore_0
    //   161: aload_0
    //   162: invokestatic close : (Ljava/io/Closeable;)V
    //   165: aload_2
    //   166: invokestatic close : (Ljava/io/Closeable;)V
    //   169: aload_1
    //   170: athrow
    //   171: return
    // Exception table:
    //   from	to	target	type
    //   51	60	130	java/lang/Exception
    //   51	60	122	finally
    //   60	77	118	java/lang/Exception
    //   60	77	114	finally
    //   82	88	104	java/lang/Exception
    //   82	88	158	finally
    //   93	98	104	java/lang/Exception
    //   93	98	158	finally
    //   144	149	158	finally }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\kt\src\\util\FileUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */