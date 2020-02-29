package com.sk.spatch.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
  private static final int BUFFER_SIZE = 2048;
  
  public static boolean isDebug = false;
  
  static  {
  
  }
  
  private static void compress(File paramFile, ZipOutputStream paramZipOutputStream, String paramString, boolean paramBoolean) throws Exception {
    FileInputStream fileInputStream;
    byte[] arrayOfByte = new byte[2048];
    boolean bool = paramFile.isFile();
    int i = 0;
    if (bool) {
      paramZipOutputStream.putNextEntry(new ZipEntry(paramString));
      fileInputStream = new FileInputStream(paramFile);
      while (true) {
        i = fileInputStream.read(arrayOfByte);
        if (i != -1) {
          paramZipOutputStream.write(arrayOfByte, 0, i);
          continue;
        } 
        break;
      } 
      paramZipOutputStream.closeEntry();
      fileInputStream.close();
      return;
    } 
    Object[] arrayOfObject = (Object[])fileInputStream.listFiles();
    if (arrayOfObject == null || arrayOfObject.length == 0) {
      if (paramBoolean) {
        arrayOfObject = (Object[])new StringBuilder();
        arrayOfObject.append(paramString);
        arrayOfObject.append("/");
        paramZipOutputStream.putNextEntry(new ZipEntry(arrayOfObject.toString()));
        paramZipOutputStream.closeEntry();
      } 
      return;
    } 
    int j = arrayOfObject.length;
    while (i < j) {
      Object object = arrayOfObject[i];
      if (paramBoolean) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(paramString);
        stringBuilder.append("/");
        stringBuilder.append(object.getName());
        compress((File)object, paramZipOutputStream, stringBuilder.toString(), paramBoolean);
      } else {
        compress((File)object, paramZipOutputStream, object.getName(), paramBoolean);
      } 
      i++;
    } 
  }
  
  public static void main(String[] paramArrayOfString) throws Exception {
    toZip("E:/testZip", new FileOutputStream(new File("E:/testZip.zip")), true);
    ArrayList<File> arrayList = new ArrayList();
    arrayList.add(new File("D:/Java/jdk1.7.0_45_64bit/bin/jar.exe"));
    arrayList.add(new File("D:/Java/jdk1.7.0_45_64bit/bin/java.exe"));
    toZip(arrayList, new FileOutputStream(new File("c:/mytest02.zip")));
  }
  
  public static void toZip(String paramString, OutputStream paramOutputStream, boolean paramBoolean) throws RuntimeException {
    long l = System.currentTimeMillis();
    Object object = null;
    StringBuilder stringBuilder = null;
    try {
      paramOutputStream = new ZipOutputStream(paramOutputStream);
      try {
        File file = new File(paramString);
        compress(file, (ZipOutputStream)paramOutputStream, file.getName(), paramBoolean);
        long l1 = System.currentTimeMillis();
        if (isDebug) {
          PrintStream printStream = System.out;
          stringBuilder = new StringBuilder();
          stringBuilder.append("压缩完成，耗时：");
          stringBuilder.append(l1 - l);
          stringBuilder.append(" ms");
          printStream.println(stringBuilder.toString());
        } 
      } catch (Exception exception) {
      
      } finally {
        OutputStream outputStream;
        paramString = null;
      } 
    } catch (Exception exception) {
      Object object1 = object;
    } finally {}
    throw new RuntimeException("zip error from ZipUtils", (Throwable)paramString);
  }
  
  public static void toZip(List<File> paramList, OutputStream paramOutputStream) throws RuntimeException {
    long l = System.currentTimeMillis();
    Object object = null;
    byte[] arrayOfByte = null;
    try {
      paramOutputStream = new ZipOutputStream(paramOutputStream);
      try {
        for (File null : paramList) {
          arrayOfByte = new byte[2048];
          paramOutputStream.putNextEntry(new ZipEntry(object.getName()));
          object = new FileInputStream((File)object);
          while (true) {
            int i = object.read(arrayOfByte);
            if (i != -1) {
              paramOutputStream.write(arrayOfByte, 0, i);
              continue;
            } 
            break;
          } 
          paramOutputStream.closeEntry();
          object.close();
        } 
        long l1 = System.currentTimeMillis();
        if (isDebug) {
          PrintStream printStream = System.out;
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("压缩完成，耗时：");
          stringBuilder.append(l1 - l);
          stringBuilder.append(" ms");
          printStream.println(stringBuilder.toString());
        } 
      } catch (Exception exception) {
      
      } finally {
        OutputStream outputStream;
        paramList = null;
      } 
    } catch (Exception exception) {
      Object object1 = object;
    } finally {}
    throw new RuntimeException("zip error from ZipUtils", (Throwable)paramList);
  }
  
  public static void toZipEx(String paramString, OutputStream paramOutputStream, boolean paramBoolean) throws RuntimeException {
    long l = System.currentTimeMillis();
    Object object = null;
    File file = null;
    try {
      paramOutputStream = new ZipOutputStream(paramOutputStream);
      try {
        File[] arrayOfFile = (new File(paramString)).listFiles();
        if (arrayOfFile != null) {
          int j = arrayOfFile.length;
          for (int i = 0; i < j; i++) {
            file = arrayOfFile[i];
            compress(file, (ZipOutputStream)paramOutputStream, file.getName(), paramBoolean);
          } 
        } 
        long l1 = System.currentTimeMillis();
        if (isDebug) {
          PrintStream printStream = System.out;
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("压缩完成，耗时：");
          stringBuilder.append(l1 - l);
          stringBuilder.append(" ms");
          printStream.println(stringBuilder.toString());
        } 
      } catch (Exception exception) {
      
      } finally {
        OutputStream outputStream;
        paramString = null;
      } 
    } catch (Exception exception) {
      Object object1 = object;
    } finally {}
    throw new RuntimeException("zip error from ZipUtils", (Throwable)paramString);
  }
  
  public static void unZip(File paramFile, String paramString) throws RuntimeException {
    long l = System.currentTimeMillis();
    if (paramFile.exists()) {
      Object object = null;
      Enumeration<? extends ZipEntry> enumeration = null;
      try {
        object1 = new ZipFile(paramFile);
        try {
          enumeration = object1.entries();
          while (enumeration.hasMoreElements()) {
            object = enumeration.nextElement();
            if (isDebug) {
              PrintStream printStream = System.out;
              StringBuilder stringBuilder2 = new StringBuilder();
              stringBuilder2.append("解压");
              stringBuilder2.append(object.getName());
              printStream.println(stringBuilder2.toString());
            } 
            boolean bool = object.isDirectory();
            if (bool) {
              StringBuilder stringBuilder2 = new StringBuilder();
              stringBuilder2.append(paramString);
              stringBuilder2.append("/");
              stringBuilder2.append(object.getName());
              (new File(stringBuilder2.toString())).mkdirs();
              continue;
            } 
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append(paramString);
            stringBuilder1.append("/");
            stringBuilder1.append(object.getName());
            File file = new File(stringBuilder1.toString());
            if (!file.getParentFile().exists())
              file.getParentFile().mkdirs(); 
            file.createNewFile();
            object = object1.getInputStream((ZipEntry)object);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] arrayOfByte = new byte[2048];
            while (true) {
              int i = object.read(arrayOfByte);
              if (i != -1) {
                fileOutputStream.write(arrayOfByte, 0, i);
                continue;
              } 
              break;
            } 
            fileOutputStream.close();
            object.close();
          } 
          long l1 = System.currentTimeMillis();
        } catch (Exception exception) {
        
        } finally {
          paramString = null;
          Object object2 = object1;
        } 
      } catch (Exception exception) {
        Object object1 = object;
      } finally {}
      throw new RuntimeException("unzip error from ZipUtils", (Throwable)paramFile);
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramFile.getPath());
    stringBuilder.append("所指文件不存在");
    throw new RuntimeException(stringBuilder.toString());
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatc\\utils\ZipUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */