package com.sk.spatch.xmltool.decode;

import com.sk.spatch.xmltool.decode.encoding.ZipEncoding;
import com.sk.spatch.xmltool.decode.encoding.ZipEncodingHelper;
import com.sk.spatch.xmltool.decode.extrafield.AbstractUnicodeExtraField;
import com.sk.spatch.xmltool.decode.extrafield.UnicodeCommentExtraField;
import com.sk.spatch.xmltool.decode.extrafield.UnicodePathExtraField;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipException;

public class ZipFile implements Closeable {
  private static final int BYTE_SHIFT = 8;
  
  private static final int CFD_LOCATOR_OFFSET = 16;
  
  private static final int CFH_LEN = 42;
  
  private static final int HASH_SIZE = 509;
  
  private static final long LFH_OFFSET_FOR_FILENAME_LENGTH = 26L;
  
  private static final int MAX_EOCD_SIZE = 65557;
  
  private static final int MIN_EOCD_SIZE = 22;
  
  private static final int NIBLET_MASK = 15;
  
  private static final int POS_0 = 0;
  
  private static final int POS_1 = 1;
  
  private static final int POS_2 = 2;
  
  private static final int POS_3 = 3;
  
  private static final int SHORT = 2;
  
  private static final int WORD = 4;
  
  private final RandomAccessFile archive;
  
  private final Map<ZipEntry, OffsetEntry> entries = new HashMap<ZipEntry, OffsetEntry>(509);
  
  private final Map<String, ZipEntry> nameMap = new HashMap<String, ZipEntry>(509);
  
  private final boolean useUnicodeExtraFields;
  
  private final ZipEncoding zipEncoding;
  
  public ZipFile(File paramFile) throws IOException { this(paramFile, null); }
  
  public ZipFile(File paramFile, String paramString) throws IOException { this(paramFile, paramString, true); }
  
  public ZipFile(File paramFile, String paramString, boolean paramBoolean) throws IOException {
    this.zipEncoding = ZipEncodingHelper.getZipEncoding(paramString);
    this.useUnicodeExtraFields = paramBoolean;
    this.archive = new RandomAccessFile(paramFile, "r");
    try {
      return;
    } finally {
      try {
        this.archive.close();
      } catch (IOException iOException) {}
    } 
  }
  
  public ZipFile(String paramString) throws IOException { this(new File(paramString), null); }
  
  public ZipFile(String paramString1, String paramString2) throws IOException { this(new File(paramString1), paramString2, true); }
  
  public static void closeQuietly(ZipFile paramZipFile) {
    if (paramZipFile != null)
      try {
        paramZipFile.close();
        return;
      } catch (IOException iOException) {
        return;
      }  
  }
  
  private static long dosToJavaTime(long paramLong) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(1, (int)(paramLong >> 25 & 0x7FL) + 1980);
    calendar.set(2, (int)(paramLong >> 21 & 0xFL) - 1);
    calendar.set(5, (int)(paramLong >> 16) & 0x1F);
    calendar.set(11, (int)(paramLong >> 11) & 0x1F);
    calendar.set(12, (int)(paramLong >> 5) & 0x3F);
    calendar.set(13, (int)(paramLong << true) & 0x3E);
    return calendar.getTime().getTime();
  }
  
  protected static Date fromDosTime(ZipLong paramZipLong) { return new Date(dosToJavaTime(paramZipLong.getValue())); }
  
  private String getUnicodeStringIfOriginalMatches(AbstractUnicodeExtraField paramAbstractUnicodeExtraField, byte[] paramArrayOfbyte) {
    if (paramAbstractUnicodeExtraField != null) {
      CRC32 cRC32 = new CRC32();
      cRC32.update(paramArrayOfbyte);
      if (cRC32.getValue() == paramAbstractUnicodeExtraField.getNameCRC32())
        try {
          return ZipEncodingHelper.UTF8_ZIP_ENCODING.decode(paramAbstractUnicodeExtraField.getUnicodeName());
        } catch (IOException iOException) {
          return null;
        }  
    } 
    return null;
  }
  
  private Map<ZipEntry, NameAndComment> populateFromCentralDirectory() throws IOException {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    positionAtCentralDirectory();
    byte[] arrayOfByte3 = new byte[42];
    byte[] arrayOfByte2 = new byte[4];
    this.archive.readFully(arrayOfByte2);
    long l2 = ZipLong.getValue(arrayOfByte2);
    long l3 = ZipLong.getValue(ZipOutputStream.CFH_SIG);
    byte[] arrayOfByte1 = arrayOfByte2;
    long l1 = l2;
    if (l2 != l3)
      if (!startsWithLocalFileHeader()) {
        arrayOfByte1 = arrayOfByte2;
        l1 = l2;
      } else {
        throw new IOException("central directory is empty, can't expand corrupt archive.");
      }  
    while (l1 == l3) {
      ZipEncoding zipEncoding1;
      this.archive.readFully(arrayOfByte3);
      ZipEntry zipEntry = new ZipEntry();
      boolean bool = false;
      zipEntry.setPlatform(ZipShort.getValue(arrayOfByte3, 0) >> 8 & 0xF);
      if ((ZipShort.getValue(arrayOfByte3, 4) & 0x800) != 0)
        bool = true; 
      if (bool) {
        zipEncoding1 = ZipEncodingHelper.UTF8_ZIP_ENCODING;
      } else {
        zipEncoding1 = this.zipEncoding;
      } 
      zipEntry.setMethod(ZipShort.getValue(arrayOfByte3, 6));
      zipEntry.setTime(dosToJavaTime(ZipLong.getValue(arrayOfByte3, 8)));
      zipEntry.setCrc(ZipLong.getValue(arrayOfByte3, 12));
      zipEntry.setCompressedSize(ZipLong.getValue(arrayOfByte3, 16));
      zipEntry.setSize(ZipLong.getValue(arrayOfByte3, 20));
      int i = ZipShort.getValue(arrayOfByte3, 24);
      int j = ZipShort.getValue(arrayOfByte3, 26);
      int k = ZipShort.getValue(arrayOfByte3, 28);
      zipEntry.setInternalAttributes(ZipShort.getValue(arrayOfByte3, 32));
      zipEntry.setExternalAttributes(ZipLong.getValue(arrayOfByte3, 34));
      byte[] arrayOfByte4 = new byte[i];
      this.archive.readFully(arrayOfByte4);
      zipEntry.setName(zipEncoding1.decode(arrayOfByte4));
      OffsetEntry offsetEntry = new OffsetEntry();
      OffsetEntry.access$202(offsetEntry, ZipLong.getValue(arrayOfByte3, 38));
      this.entries.put(zipEntry, offsetEntry);
      this.nameMap.put(zipEntry.getName(), zipEntry);
      byte[] arrayOfByte5 = new byte[j];
      this.archive.readFully(arrayOfByte5);
      zipEntry.setCentralDirectoryExtra(arrayOfByte5);
      arrayOfByte5 = new byte[k];
      this.archive.readFully(arrayOfByte5);
      zipEntry.setComment(zipEncoding1.decode(arrayOfByte5));
      this.archive.readFully(arrayOfByte1);
      l1 = ZipLong.getValue(arrayOfByte1);
      if (!bool && this.useUnicodeExtraFields)
        hashMap.put(zipEntry, new NameAndComment(arrayOfByte4, arrayOfByte5)); 
    } 
    return (Map)hashMap;
  }
  
  private void positionAtCentralDirectory() throws IOException {
    long l1 = this.archive.length() - 22L;
    long l3 = Math.max(0L, this.archive.length() - 65557L);
    boolean bool = true;
    long l2 = l1;
    if (l1 >= 0L) {
      byte[] arrayOfByte = ZipOutputStream.EOCD_SIG;
      while (true) {
        l2 = l1;
        if (l1 >= l3) {
          this.archive.seek(l1);
          int i = this.archive.read();
          if (i == -1) {
            l2 = l1;
            // Byte code: goto -> 142
          } 
          if (i == arrayOfByte[0] && this.archive.read() == arrayOfByte[1] && this.archive.read() == arrayOfByte[2] && this.archive.read() == arrayOfByte[3])
            break; 
          l1--;
          continue;
        } 
        // Byte code: goto -> 142
      } 
    } else {
      bool = false;
      l1 = l2;
    } 
    if (bool) {
      this.archive.seek(l1 + 16L);
      byte[] arrayOfByte = new byte[4];
      this.archive.readFully(arrayOfByte);
      this.archive.seek(ZipLong.getValue(arrayOfByte));
      return;
    } 
    throw new ZipException("archive is not a ZIP archive");
  }
  
  private void resolveLocalFileHeaderData(Map<ZipEntry, NameAndComment> paramMap) throws IOException {
    Enumeration<?> enumeration = Collections.enumeration(new HashSet(this.entries.keySet()));
    while (enumeration.hasMoreElements()) {
      ZipEntry zipEntry = (ZipEntry)enumeration.nextElement();
      OffsetEntry offsetEntry = this.entries.get(zipEntry);
      long l = offsetEntry.headerOffset;
      RandomAccessFile randomAccessFile = this.archive;
      l += 26L;
      randomAccessFile.seek(l);
      byte[] arrayOfByte = new byte[2];
      this.archive.readFully(arrayOfByte);
      int j = ZipShort.getValue(arrayOfByte);
      this.archive.readFully(arrayOfByte);
      int k = ZipShort.getValue(arrayOfByte);
      int i = j;
      while (i > 0) {
        int m = this.archive.skipBytes(i);
        if (m > 0) {
          i -= m;
          continue;
        } 
        throw new RuntimeException("failed to skip file name in local file header");
      } 
      arrayOfByte = new byte[k];
      this.archive.readFully(arrayOfByte);
      zipEntry.setExtra(arrayOfByte);
      OffsetEntry.access$002(offsetEntry, l + 2L + 2L + j + k);
      if (paramMap.containsKey(zipEntry)) {
        this.entries.remove(zipEntry);
        setNameAndCommentFromExtraFields(zipEntry, paramMap.get(zipEntry));
        this.entries.put(zipEntry, offsetEntry);
      } 
    } 
  }
  
  private void setNameAndCommentFromExtraFields(ZipEntry paramZipEntry, NameAndComment paramNameAndComment) {
    UnicodePathExtraField unicodePathExtraField = (UnicodePathExtraField)paramZipEntry.getExtraField(UnicodePathExtraField.UPATH_ID);
    String str1 = paramZipEntry.getName();
    String str2 = getUnicodeStringIfOriginalMatches((AbstractUnicodeExtraField)unicodePathExtraField, paramNameAndComment.name);
    if (str2 != null && !str1.equals(str2)) {
      paramZipEntry.setName(str2);
      this.nameMap.remove(str1);
      this.nameMap.put(str2, paramZipEntry);
    } 
    if (paramNameAndComment.comment != null && paramNameAndComment.comment.length > 0) {
      String str = getUnicodeStringIfOriginalMatches((AbstractUnicodeExtraField)paramZipEntry.getExtraField(UnicodeCommentExtraField.UCOM_ID), paramNameAndComment.comment);
      if (str != null)
        paramZipEntry.setComment(str); 
    } 
  }
  
  private boolean startsWithLocalFileHeader() throws IOException {
    this.archive.seek(0L);
    byte[] arrayOfByte = new byte[4];
    this.archive.readFully(arrayOfByte);
    for (int i = 0; i < 4; i++) {
      if (arrayOfByte[i] != ZipOutputStream.LFH_SIG[i])
        return false; 
    } 
    return true;
  }
  
  public void close() throws IOException { this.archive.close(); }
  
  public String getEncoding() { return this.zipEncoding.getEncoding(); }
  
  public Enumeration<ZipEntry> getEntries() { return Collections.enumeration(this.entries.keySet()); }
  
  public ZipEntry getEntry(String paramString) { return this.nameMap.get(paramString); }
  
  public int getEntrySize() { return this.entries.size(); }
  
  public InputStream getInputStream(ZipEntry paramZipEntry) throws IOException {
    OffsetEntry offsetEntry = this.entries.get(paramZipEntry);
    if (offsetEntry == null)
      return null; 
    Object object = new BoundedInputStream(offsetEntry.dataOffset, paramZipEntry.getCompressedSize());
    int i = paramZipEntry.getMethod();
    if (i != 0) {
      final Inflater inflater;
      if (i == 8) {
        object.addDummy();
        inflater = new Inflater(true);
        return new InflaterInputStream((InputStream)object, inflater) {
            public void close() throws IOException {
              super.close();
              inflater.end();
            }
          };
      } 
      object = new StringBuilder();
      object.append("Found unsupported compression method ");
      object.append(inflater.getMethod());
      throw new ZipException(object.toString());
    } 
    return (InputStream)object;
  }
  
  public InputStream getRawInputStream(ZipEntry paramZipEntry) throws IOException {
    OffsetEntry offsetEntry = this.entries.get(paramZipEntry);
    return (offsetEntry == null) ? null : new BoundedInputStream(offsetEntry.dataOffset, paramZipEntry.getCompressedSize());
  }
  
  public ZipEncoding getZipEncoding() { return this.zipEncoding; }
  
  private class BoundedInputStream extends InputStream {
    private boolean addDummyByte = false;
    
    private long loc;
    
    private long remaining;
    
    BoundedInputStream(long param1Long1, long param1Long2) {
      this.remaining = param1Long2;
      this.loc = param1Long1;
    }
    
    void addDummy() { this.addDummyByte = true; }
    
    public int read() throws IOException {
      long l = this.remaining;
      this.remaining = l - 1L;
      if (l <= 0L) {
        if (this.addDummyByte) {
          this.addDummyByte = false;
          return 0;
        } 
        return -1;
      } 
      synchronized (ZipFile.this.archive) {
        RandomAccessFile randomAccessFile = ZipFile.this.archive;
        l = this.loc;
        this.loc = 1L + l;
        randomAccessFile.seek(l);
        return ZipFile.this.archive.read();
      } 
    }
    
    public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
      long l = this.remaining;
      if (l <= 0L) {
        if (this.addDummyByte) {
          this.addDummyByte = false;
          param1ArrayOfbyte[param1Int1] = 0;
          return 1;
        } 
        return -1;
      } 
      if (param1Int2 <= 0)
        return 0; 
      int i = param1Int2;
      if (param1Int2 > l)
        i = (int)l; 
      synchronized (ZipFile.this.archive) {
        ZipFile.this.archive.seek(this.loc);
        param1Int1 = ZipFile.this.archive.read(param1ArrayOfbyte, param1Int1, i);
        if (param1Int1 > 0) {
          l = this.loc;
          long l1 = param1Int1;
          this.loc = l + l1;
          this.remaining -= l1;
        } 
        return param1Int1;
      } 
    }
  }
  
  private static final class NameAndComment {
    private final byte[] comment;
    
    private final byte[] name;
    
    private NameAndComment(byte[] param1ArrayOfbyte1, byte[] param1ArrayOfbyte2) {
      this.name = param1ArrayOfbyte1;
      this.comment = param1ArrayOfbyte2;
    }
  }
  
  private static final class OffsetEntry {
    private long dataOffset = -1L;
    
    private long headerOffset = -1L;
    
    private OffsetEntry() {}
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\ZipFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */