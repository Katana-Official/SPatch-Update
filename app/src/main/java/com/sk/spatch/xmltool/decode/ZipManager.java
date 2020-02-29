package com.sk.spatch.xmltool.decode;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;

public class ZipManager {
  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yy-MM-dd HH:mm");
  
  private final File file;
  
  public File tmp;
  
  private final ZipEntry[] ze;
  
  public final ZipFile zipFile;
  
  private ZipOutputStream zos;
  
  public ZipManager(File paramFile) throws IOException {
    this.file = paramFile;
    this.zipFile = new ZipFile(paramFile);
    ArrayList<ZipEntry> arrayList = new ArrayList(this.zipFile.getEntrySize());
    Enumeration<ZipEntry> enumeration = this.zipFile.getEntries();
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    label31: while (enumeration.hasMoreElements()) {
      ZipEntry zipEntry = enumeration.nextElement();
      if (!zipEntry.isDirectory()) {
        arrayList.add(zipEntry);
      } else if (!hashMap.containsKey(zipEntry.getName())) {
        hashMap.put(zipEntry.getName(), zipEntry);
      } else {
        continue;
      } 
      String str = zipEntry.getParent();
      if (str != null) {
        long l = zipEntry.getTime();
        String str1 = str;
        if (!hashMap.containsKey(str))
          while (true) {
            ZipEntry zipEntry1 = new ZipEntry(str);
            zipEntry1.setTime(l);
            hashMap.put(str, zipEntry1);
            str1 = zipEntry1.getParent();
            if (str1 != null) {
              str = str1;
              if (hashMap.containsKey(str1))
                continue label31; 
              continue;
            } 
            continue label31;
          }  
        while (true) {
          ZipEntry zipEntry1 = (ZipEntry)hashMap.get(str1);
          if (zipEntry1.getTime() < l) {
            zipEntry1.setTime(l);
            String str2 = zipEntry1.getParent();
            str1 = str2;
            if (str2 == null)
              continue label31; 
            continue;
          } 
          continue label31;
        } 
      } 
    } 
    arrayList.addAll(hashMap.values());
    hashMap.clear();
    Collections.sort(arrayList, new Comparator<ZipEntry>() {
          public int compare(ZipEntry param1ZipEntry1, ZipEntry param1ZipEntry2) { return param1ZipEntry1.getName().compareToIgnoreCase(param1ZipEntry2.getName()); }
        });
    ZipEntry[] arrayOfZipEntry = new ZipEntry[arrayList.size()];
    this.ze = arrayOfZipEntry;
    arrayList.toArray(arrayOfZipEntry);
    arrayList.clear();
  }
  
  public static String getEntryTime(ZipEntry paramZipEntry) { return DATE_FORMAT.format(paramZipEntry.getLastModifiedDate()); }
  
  public void close() throws IOException { this.zipFile.close(); }
  
  public void copyEntries(CopyEntryCallback paramCopyEntryCallback) throws IOException {
    int i = 0;
    while (true) {
      ZipEntry[] arrayOfZipEntry = this.ze;
      if (i < arrayOfZipEntry.length) {
        ZipEntry zipEntry2 = arrayOfZipEntry[i];
        ZipEntry zipEntry1 = paramCopyEntryCallback.filter(zipEntry2, ++i, arrayOfZipEntry.length);
        if (zipEntry1 == null)
          continue; 
        if (zipEntry1.isDirectory()) {
          this.zos.putNextEntry(zipEntry1);
        } else {
          InputStream inputStream = this.zipFile.getRawInputStream(zipEntry1);
          this.zos.putNextRawEntry(zipEntry1);
          long l2 = zipEntry1.getCompressedSize();
          long l1 = 0L;
          byte[] arrayOfByte = new byte[10240];
          while (true) {
            int j = inputStream.read(arrayOfByte);
            if (j > 0) {
              this.zos.writeRaw(arrayOfByte, 0, j);
              l1 += j;
              paramCopyEntryCallback.onProgress(l1, l2);
              continue;
            } 
            break;
          } 
        } 
        this.zos.closeEntry();
        paramCopyEntryCallback.done(zipEntry1);
        continue;
      } 
      break;
    } 
  }
  
  public void copyOtherZipManagerEntries(ZipManager paramZipManager, CopyEntryCallback paramCopyEntryCallback) throws IOException {
    int i = 0;
    while (true) {
      ZipEntry[] arrayOfZipEntry = paramZipManager.ze;
      if (i < arrayOfZipEntry.length) {
        ZipEntry zipEntry2 = arrayOfZipEntry[i];
        int j = i + 1;
        ZipEntry zipEntry1 = paramCopyEntryCallback.filter(zipEntry2, j, arrayOfZipEntry.length);
        if (zipEntry1 != null) {
          if (zipEntry1.isDirectory()) {
            this.zos.putNextEntry(zipEntry1);
          } else {
            InputStream inputStream = paramZipManager.zipFile.getRawInputStream(paramZipManager.ze[i]);
            this.zos.putNextRawEntry(zipEntry1);
            long l2 = zipEntry1.getCompressedSize();
            long l1 = 0L;
            byte[] arrayOfByte = new byte[10240];
            while (true) {
              i = inputStream.read(arrayOfByte);
              if (i > 0) {
                this.zos.writeRaw(arrayOfByte, 0, i);
                l1 += i;
                paramCopyEntryCallback.onProgress(l1, l2);
                continue;
              } 
              break;
            } 
          } 
          this.zos.closeEntry();
          paramCopyEntryCallback.done(zipEntry1);
        } 
        i = j;
        continue;
      } 
      break;
    } 
  }
  
  public ZipOutputStream createTempZipOutputStream() throws IOException {
    File file1;
    String str = "";
    do {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str);
      stringBuilder1.append(System.currentTimeMillis() % 1000000L);
      str = stringBuilder1.toString();
      file1 = this.file.getParentFile();
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(str);
      stringBuilder2.append(".tmp");
      file1 = new File(file1, stringBuilder2.toString());
      this.tmp = file1;
    } while (file1.exists());
    ZipOutputStream zipOutputStream = new ZipOutputStream(this.tmp);
    this.zos = zipOutputStream;
    zipOutputStream.setZipEncoding(this.zipFile.getZipEncoding());
    return this.zos;
  }
  
  public void extract(ExtractCallback paramExtractCallback) throws IOException {
    int i = 0;
    while (true) {
      ZipEntry[] arrayOfZipEntry = this.ze;
      if (i < arrayOfZipEntry.length) {
        StringBuilder stringBuilder;
        ZipEntry zipEntry = arrayOfZipEntry[i];
        File file1 = paramExtractCallback.filter(zipEntry, ++i, arrayOfZipEntry.length);
        if (file1 == null)
          continue; 
        if (zipEntry.isDirectory()) {
          if (!file1.exists() && !file1.mkdirs()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("mkdir \"");
            stringBuilder.append(file1.getPath());
            stringBuilder.append("\" failed");
            throw new IOException(stringBuilder.toString());
          } 
        } else {
          InputStream inputStream = this.zipFile.getInputStream(zipEntry);
          BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file1));
          byte[] arrayOfByte = new byte[10240];
          long l2 = zipEntry.getSize();
          long l1 = 0L;
          while (true) {
            int j = inputStream.read(arrayOfByte);
            if (j > 0) {
              bufferedOutputStream.write(arrayOfByte, 0, j);
              l1 += j;
              if (l2 > 0L)
                stringBuilder.onProgress(l1, l2); 
              continue;
            } 
            break;
          } 
          inputStream.close();
          bufferedOutputStream.close();
        } 
        file1.setLastModified(zipEntry.getTime());
        stringBuilder.done(zipEntry, file1);
        continue;
      } 
      break;
    } 
  }
  
  public void extractZipEntry(ZipEntry paramZipEntry, File paramFile) throws IOException {
    InputStream inputStream = this.zipFile.getInputStream(paramZipEntry);
    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(paramFile));
    byte[] arrayOfByte = new byte[10240];
    while (true) {
      int i = inputStream.read(arrayOfByte);
      if (i > 0) {
        bufferedOutputStream.write(arrayOfByte, 0, i);
        continue;
      } 
      break;
    } 
    inputStream.close();
    bufferedOutputStream.close();
  }
  
  public int getEntrySize() { return this.ze.length; }
  
  public long getTotalCompressedSize() {
    ZipEntry[] arrayOfZipEntry = this.ze;
    int j = arrayOfZipEntry.length;
    long l = 0L;
    int i = 0;
    while (i < j) {
      ZipEntry zipEntry = arrayOfZipEntry[i];
      long l1 = l;
      if (!zipEntry.isDirectory())
        l1 = l + zipEntry.getCompressedSize(); 
      i++;
      l = l1;
    } 
    return l;
  }
  
  public long getTotalSize() {
    ZipEntry[] arrayOfZipEntry = this.ze;
    int j = arrayOfZipEntry.length;
    long l = 0L;
    int i = 0;
    while (i < j) {
      ZipEntry zipEntry = arrayOfZipEntry[i];
      long l1 = l;
      if (!zipEntry.isDirectory())
        l1 = l + zipEntry.getSize(); 
      i++;
      l = l1;
    } 
    return l;
  }
  
  public ZipEntry[] getZipEntries() { return this.ze; }
  
  public HashMap<String, ZipEntry> getZipFileEntryMap() {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    for (ZipEntry zipEntry : this.ze) {
      if (!zipEntry.isDirectory())
        hashMap.put(zipEntry.getName().toLowerCase(), zipEntry); 
    } 
    return (HashMap)hashMap;
  }
  
  public ArrayList<ZipEntry> list(String paramString) {
    Object object;
    if (paramString == null || paramString.length() <= 1) {
      object = null;
    } else {
      object = paramString;
      if (paramString.charAt(paramString.length() - 1) != '/') {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(paramString);
        stringBuilder.append("/");
        object = stringBuilder.toString();
      } 
    } 
    ArrayList<ZipEntry> arrayList = new ArrayList(this.ze.length);
    for (ZipEntry zipEntry : this.ze) {
      String str = zipEntry.getParent();
      if (str == null) {
        if (object == null)
          arrayList.add(zipEntry); 
      } else if (str.equals(object)) {
        arrayList.add(zipEntry);
      } 
    } 
    arrayList.trimToSize();
    return arrayList;
  }
  
  public void zip(File paramFile, long paramLong, String paramString, ZipCallback paramZipCallback) throws IOException {
    long l = 0L;
    int i = paramLong cmp 0L;
    if (i > 0)
      paramZipCallback.onProgress(0L, paramLong); 
    FileInputStream fileInputStream = new FileInputStream(paramFile);
    ZipEntry zipEntry = new ZipEntry(paramString);
    zipEntry.setTime(paramFile.lastModified());
    this.zos.putNextEntry(zipEntry);
    byte[] arrayOfByte = new byte[10240];
    while (true) {
      int j = fileInputStream.read(arrayOfByte);
      if (j > 0) {
        this.zos.write(arrayOfByte, 0, j);
        long l1 = l + j;
        l = l1;
        if (i > 0) {
          paramZipCallback.onProgress(l1, paramLong);
          l = l1;
        } 
        continue;
      } 
      break;
    } 
    fileInputStream.close();
    this.zos.closeEntry();
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\ZipManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */