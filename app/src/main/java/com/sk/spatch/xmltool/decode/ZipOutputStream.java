package com.sk.spatch.xmltool.decode;

import com.sk.spatch.xmltool.decode.encoding.ZipEncoding;
import com.sk.spatch.xmltool.decode.encoding.ZipEncodingHelper;
import com.sk.spatch.xmltool.decode.extrafield.UnicodeCommentExtraField;
import com.sk.spatch.xmltool.decode.extrafield.UnicodePathExtraField;
import com.sk.spatch.xmltool.decode.extrafield.ZipExtraField;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import java.util.zip.ZipException;

public class ZipOutputStream extends FilterOutputStream {
  public static final int BUFFER_SIZE = 10240;
  
  private static final int BYTE_MASK = 255;
  
  protected static final byte[] CFH_SIG;
  
  protected static final byte[] DD_SIG;
  
  public static final int DEFAULT_COMPRESSION = -1;
  
  static final String DEFAULT_ENCODING;
  
  public static final int DEFLATED = 8;
  
  private static final int DEFLATER_BLOCK_SIZE = 8192;
  
  public static final int EFS_FLAG = 2048;
  
  protected static final byte[] EOCD_SIG;
  
  public static final int LEVEL_BEST = 9;
  
  public static final int LEVEL_BETTER = 7;
  
  public static final int LEVEL_DEFAULT = -1;
  
  public static final int LEVEL_FASTER = 3;
  
  public static final int LEVEL_FASTEST = 1;
  
  protected static final byte[] LFH_SIG;
  
  private static final byte[] LZERO;
  
  private static final int SHORT = 2;
  
  public static final int STORED = 0;
  
  public static final int UFT8_NAMES_FLAG = 2048;
  
  private static final int WORD = 4;
  
  private static final byte[] ZERO = new byte[] { 0, 0 };
  
  protected byte[] buf = new byte[512];
  
  private long cdLength = 0L;
  
  private long cdOffset = 0L;
  
  private String comment = "";
  
  private final CRC32 crc = new CRC32();
  
  private UnicodeExtraFieldPolicy createUnicodeExtraFields = UnicodeExtraFieldPolicy.NEVER;
  
  private boolean currentIsRawEntry;
  
  private long dataStart = 0L;
  
  protected Deflater def = new Deflater(this.level, true);
  
  private String encoding = null;
  
  private final List<ZipEntry> entries = new LinkedList<ZipEntry>();
  
  private ZipEntry entry;
  
  private boolean fallbackToUTF8 = false;
  
  private boolean hasCompressionLevelChanged = false;
  
  private int level = -1;
  
  private long localDataStart = 0L;
  
  private int method = 8;
  
  private final Map<ZipEntry, Long> offsets = new HashMap<ZipEntry, Long>();
  
  private RandomAccessFile raf = null;
  
  private boolean useUTF8Flag = true;
  
  private long written = 0L;
  
  private ZipEncoding zipEncoding = ZipEncodingHelper.UTF8_ZIP_ENCODING;
  
  static  {
    LZERO = new byte[] { 0, 0, 0, 0 };
    LFH_SIG = ZipLong.getBytes(67324752L);
    DD_SIG = ZipLong.getBytes(134695760L);
    CFH_SIG = ZipLong.getBytes(33639248L);
    EOCD_SIG = ZipLong.getBytes(101010256L);
  }
  
  public ZipOutputStream(File paramFile) throws IOException {
    super(null);
    try {
      RandomAccessFile randomAccessFile = new RandomAccessFile(paramFile, "rw");
      this.raf = randomAccessFile;
      randomAccessFile.setLength(0L);
      return;
    } catch (IOException iOException) {
      RandomAccessFile randomAccessFile = this.raf;
      if (randomAccessFile != null) {
        try {
          randomAccessFile.close();
        } catch (IOException iOException1) {}
        this.raf = null;
      } 
      this.out = new FileOutputStream(paramFile);
      return;
    } 
  }
  
  public ZipOutputStream(OutputStream paramOutputStream) { super(paramOutputStream); }
  
  protected static long adjustToLong(int paramInt) { return (paramInt < 0) ? (paramInt + 4294967296L) : paramInt; }
  
  private void deflateUntilInputIsNeeded() throws IOException {
    while (!this.def.needsInput())
      deflate(); 
  }
  
  private static void putBytes(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3) {
    int i = 0;
    while (i < paramInt2) {
      paramArrayOfbyte2[paramInt3] = paramArrayOfbyte1[paramInt1 + i];
      i++;
      paramInt3++;
    } 
  }
  
  private static void putBytes(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt) { putBytes(paramArrayOfbyte1, 0, paramArrayOfbyte1.length, paramArrayOfbyte2, paramInt); }
  
  protected static long toDosTime(long paramLong) {
    Date date = new Date(paramLong);
    int i = date.getYear() + 1900;
    return (i < 1980) ? 8448L : (i - 1980 << 25 | date.getMonth() + 1 << 21 | date.getDate() << 16 | date.getHours() << 11 | date.getMinutes() << 5 | date.getSeconds() >> 1);
  }
  
  private void writeVersionNeededToExtractAndGeneralPurposeBits(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, boolean paramBoolean) throws IOException {
    int i;
    if (this.useUTF8Flag || paramBoolean) {
      i = 2048;
    } else {
      i = 0;
    } 
    if (paramInt2 == 8 && this.raf == null) {
      paramInt2 = 20;
      i |= 0x8;
    } else {
      paramInt2 = 10;
    } 
    ZipShort.putShort(paramInt2, paramArrayOfbyte, paramInt1);
    ZipShort.putShort(i, paramArrayOfbyte, paramInt1 + 2);
  }
  
  public void close() throws IOException {
    finish();
    RandomAccessFile randomAccessFile = this.raf;
    if (randomAccessFile != null)
      randomAccessFile.close(); 
    if (this.out != null)
      this.out.close(); 
  }
  
  public void closeEntry() throws IOException {
    if (this.entry == null)
      return; 
    if (this.currentIsRawEntry) {
      this.crc.reset();
    } else {
      long l = this.crc.getValue();
      this.crc.reset();
      if (this.entry.getMethod() == 8) {
        this.def.finish();
        while (!this.def.finished())
          deflate(); 
        this.entry.setSize(adjustToLong(this.def.getTotalIn()));
        this.entry.setCompressedSize(adjustToLong(this.def.getTotalOut()));
        this.entry.setCrc(l);
        this.def.reset();
        this.written += this.entry.getCompressedSize();
      } else if (this.raf == null) {
        if (this.entry.getCrc() == l) {
          if (this.entry.getSize() != this.written - this.dataStart) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("bad size for entry ");
            stringBuilder.append(this.entry.getName());
            stringBuilder.append(": ");
            stringBuilder.append(this.entry.getSize());
            stringBuilder.append(" instead of ");
            stringBuilder.append(this.written - this.dataStart);
            throw new ZipException(stringBuilder.toString());
          } 
        } else {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("bad CRC checksum for entry ");
          stringBuilder.append(this.entry.getName());
          stringBuilder.append(": ");
          stringBuilder.append(Long.toHexString(this.entry.getCrc()));
          stringBuilder.append(" instead of ");
          stringBuilder.append(Long.toHexString(l));
          throw new ZipException(stringBuilder.toString());
        } 
      } else {
        long l1 = this.written - this.dataStart;
        this.entry.setSize(l1);
        this.entry.setCompressedSize(l1);
        this.entry.setCrc(l);
      } 
      RandomAccessFile randomAccessFile = this.raf;
      if (randomAccessFile != null) {
        l = randomAccessFile.getFilePointer();
        this.raf.seek(this.localDataStart);
        byte[] arrayOfByte = new byte[12];
        ZipLong.putLong(this.entry.getCrc(), arrayOfByte, 0);
        ZipLong.putLong(this.entry.getCompressedSize(), arrayOfByte, 4);
        ZipLong.putLong(this.entry.getSize(), arrayOfByte, 8);
        writeOut(arrayOfByte);
        this.raf.seek(l);
      } 
    } 
    writeDataDescriptor(this.entry);
    this.entry = null;
  }
  
  public void copyZipEntry(ZipEntry paramZipEntry, ZipFile paramZipFile) throws IOException {
    InputStream inputStream = paramZipFile.getRawInputStream(paramZipEntry);
    putNextRawEntry(paramZipEntry);
    byte[] arrayOfByte = new byte[10240];
    while (true) {
      int i = inputStream.read(arrayOfByte);
      if (i > 0) {
        writeRaw(arrayOfByte, 0, i);
        continue;
      } 
      break;
    } 
    closeEntry();
  }
  
  protected final void deflate() throws IOException {
    Deflater deflater = this.def;
    byte[] arrayOfByte = this.buf;
    int i = deflater.deflate(arrayOfByte, 0, arrayOfByte.length);
    if (i > 0)
      writeOut(this.buf, 0, i); 
  }
  
  public void finish() throws IOException {
    closeEntry();
    this.cdOffset = this.written;
    Iterator<ZipEntry> iterator = this.entries.iterator();
    while (iterator.hasNext())
      writeCentralFileHeader(iterator.next()); 
    this.cdLength = this.written - this.cdOffset;
    writeCentralDirectoryEnd();
    this.offsets.clear();
    this.entries.clear();
    this.def.end();
  }
  
  public void flush() throws IOException {
    if (this.out != null)
      this.out.flush(); 
  }
  
  public String getEncoding() { return this.encoding; }
  
  public boolean isSeekable() { return (this.raf != null); }
  
  public void putNextEntry(ZipEntry paramZipEntry) throws IOException {
    closeEntry();
    this.entry = paramZipEntry;
    this.entries.add(paramZipEntry);
    this.currentIsRawEntry = false;
    if (this.entry.getMethod() == -1)
      this.entry.setMethod(this.method); 
    if (this.entry.getTime() == -1L)
      this.entry.setTime(System.currentTimeMillis()); 
    if (this.entry.getMethod() == 0 && this.raf == null)
      if (this.entry.getSize() != -1L) {
        if (this.entry.getCrc() != -1L) {
          paramZipEntry = this.entry;
          paramZipEntry.setCompressedSize(paramZipEntry.getSize());
        } else {
          throw new ZipException("crc checksum is required for STORED method when not writing to a file");
        } 
      } else {
        throw new ZipException("uncompressed size is required for STORED method when not writing to a file");
      }  
    if (this.entry.getMethod() == 8 && this.hasCompressionLevelChanged) {
      this.def.setLevel(this.level);
      this.hasCompressionLevelChanged = false;
    } 
    writeLocalFileHeader(this.entry);
  }
  
  public void putNextEntry(String paramString) throws IOException { putNextEntry(new ZipEntry(paramString)); }
  
  public void putNextRawEntry(ZipEntry paramZipEntry) throws IOException {
    closeEntry();
    this.entry = paramZipEntry;
    this.entries.add(paramZipEntry);
    this.currentIsRawEntry = true;
    if (this.entry.getMethod() == 0 && this.raf == null)
      if (this.entry.getSize() != -1L) {
        if (this.entry.getCrc() != -1L) {
          paramZipEntry = this.entry;
          paramZipEntry.setCompressedSize(paramZipEntry.getSize());
        } else {
          throw new ZipException("crc checksum is required for STORED method when not writing to a file");
        } 
      } else {
        throw new ZipException("uncompressed size is required for STORED method when not writing to a file");
      }  
    if (this.entry.getMethod() == 8 && this.hasCompressionLevelChanged) {
      this.def.setLevel(this.level);
      this.hasCompressionLevelChanged = false;
    } 
    writeLocalFileHeader(this.entry);
  }
  
  public void setComment(String paramString) { this.comment = paramString; }
  
  public void setCreateUnicodeExtraFields(UnicodeExtraFieldPolicy paramUnicodeExtraFieldPolicy) { this.createUnicodeExtraFields = paramUnicodeExtraFieldPolicy; }
  
  public void setEncoding(String paramString) {
    ZipEncoding zipEncoding1;
    this.encoding = paramString;
    boolean bool = ZipEncodingHelper.isUTF8(paramString);
    if (bool) {
      zipEncoding1 = ZipEncodingHelper.UTF8_ZIP_ENCODING;
    } else {
      zipEncoding1 = ZipEncodingHelper.getZipEncoding((String)zipEncoding1);
    } 
    this.zipEncoding = zipEncoding1;
    if (this.useUTF8Flag && !bool)
      this.useUTF8Flag = false; 
  }
  
  public void setFallbackToUTF8(boolean paramBoolean) { this.fallbackToUTF8 = paramBoolean; }
  
  public void setLevel(int paramInt) {
    if (paramInt >= -1 && paramInt <= 9) {
      boolean bool;
      if (this.level != paramInt) {
        bool = true;
      } else {
        bool = false;
      } 
      this.hasCompressionLevelChanged = bool;
      this.level = paramInt;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Invalid compression level: ");
    stringBuilder.append(paramInt);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void setMethod(int paramInt) { this.method = paramInt; }
  
  public void setUseLanguageEncodingFlag(boolean paramBoolean) {
    if (paramBoolean && ZipEncodingHelper.isUTF8(this.encoding)) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    } 
    this.useUTF8Flag = paramBoolean;
  }
  
  public void setZipEncoding(ZipEncoding paramZipEncoding) {
    this.zipEncoding = paramZipEncoding;
    String str = paramZipEncoding.getEncoding();
    this.encoding = str;
    if (this.useUTF8Flag && !ZipEncodingHelper.isUTF8(str))
      this.useUTF8Flag = false; 
  }
  
  public void write(int paramInt) throws IOException { write(new byte[] { (byte)(paramInt & 0xFF) }, 0, 1); }
  
  public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    if (this.entry.getMethod() == 8) {
      if (paramInt2 > 0 && !this.def.finished())
        if (paramInt2 <= 8192) {
          this.def.setInput(paramArrayOfbyte, paramInt1, paramInt2);
          deflateUntilInputIsNeeded();
        } else {
          int j = paramInt2 / 8192;
          int i;
          for (i = 0; i < j; i++) {
            this.def.setInput(paramArrayOfbyte, i * 8192 + paramInt1, 8192);
            deflateUntilInputIsNeeded();
          } 
          i = j * 8192;
          if (i < paramInt2) {
            this.def.setInput(paramArrayOfbyte, paramInt1 + i, paramInt2 - i);
            deflateUntilInputIsNeeded();
          } 
        }  
    } else {
      writeOut(paramArrayOfbyte, paramInt1, paramInt2);
      this.written += paramInt2;
    } 
    this.crc.update(paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  protected void writeCentralDirectoryEnd() throws IOException {
    ByteBuffer byteBuffer = this.zipEncoding.encode(this.comment);
    byte[] arrayOfByte = new byte[byteBuffer.limit() + 22];
    putBytes(EOCD_SIG, arrayOfByte, 0);
    ZipShort.putShort(this.entries.size(), arrayOfByte, 8);
    ZipShort.putShort(this.entries.size(), arrayOfByte, 10);
    ZipLong.putLong(this.cdLength, arrayOfByte, 12);
    ZipLong.putLong(this.cdOffset, arrayOfByte, 16);
    ZipShort.putShort(byteBuffer.limit(), arrayOfByte, 20);
    putBytes(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.limit(), arrayOfByte, 22);
    writeOut(arrayOfByte);
  }
  
  protected void writeCentralFileHeader(ZipEntry paramZipEntry) throws IOException {
    ZipEncoding zipEncoding1;
    int i = paramZipEntry.getMethod();
    boolean bool = this.zipEncoding.canEncode(paramZipEntry.getName());
    if (!bool && this.fallbackToUTF8) {
      zipEncoding1 = ZipEncodingHelper.UTF8_ZIP_ENCODING;
    } else {
      zipEncoding1 = this.zipEncoding;
    } 
    ByteBuffer byteBuffer2 = zipEncoding1.encode(paramZipEntry.getName());
    byte[] arrayOfByte3 = paramZipEntry.getCentralDirectoryExtra();
    String str2 = paramZipEntry.getComment();
    String str1 = str2;
    if (str2 == null)
      str1 = ""; 
    ByteBuffer byteBuffer1 = zipEncoding1.encode(str1);
    byte[] arrayOfByte1 = new byte[byteBuffer2.limit() + 46 + arrayOfByte3.length + byteBuffer1.limit()];
    byte[] arrayOfByte2 = CFH_SIG;
    boolean bool2 = false;
    putBytes(arrayOfByte2, arrayOfByte1, 0);
    this.written += 4L;
    ZipShort.putShort(paramZipEntry.getPlatform() << 8 | 0x14, arrayOfByte1, 4);
    this.written += 2L;
    boolean bool1 = bool2;
    if (!bool) {
      bool1 = bool2;
      if (this.fallbackToUTF8)
        bool1 = true; 
    } 
    writeVersionNeededToExtractAndGeneralPurposeBits(arrayOfByte1, 6, i, bool1);
    this.written += 4L;
    ZipShort.putShort(i, arrayOfByte1, 10);
    this.written += 2L;
    ZipLong.putLong(toDosTime(paramZipEntry.getTime()), arrayOfByte1, 12);
    this.written += 4L;
    ZipLong.putLong(paramZipEntry.getCrc(), arrayOfByte1, 16);
    ZipLong.putLong(paramZipEntry.getCompressedSize(), arrayOfByte1, 20);
    ZipLong.putLong(paramZipEntry.getSize(), arrayOfByte1, 24);
    this.written += 12L;
    ZipShort.putShort(byteBuffer2.limit(), arrayOfByte1, 28);
    this.written += 2L;
    ZipShort.putShort(arrayOfByte3.length, arrayOfByte1, 30);
    this.written += 2L;
    ZipShort.putShort(byteBuffer1.limit(), arrayOfByte1, 32);
    long l = this.written + 2L;
    this.written = l;
    this.written = l + 2L;
    ZipShort.putShort(paramZipEntry.getInternalAttributes(), arrayOfByte1, 36);
    this.written += 2L;
    ZipLong.putLong(paramZipEntry.getExternalAttributes(), arrayOfByte1, 38);
    this.written += 4L;
    ZipLong.putLong(((Long)this.offsets.get(paramZipEntry)).longValue(), arrayOfByte1, 42);
    this.written += 4L;
    putBytes(byteBuffer2.array(), byteBuffer2.arrayOffset(), byteBuffer2.limit(), arrayOfByte1, 46);
    this.written += byteBuffer2.limit();
    putBytes(arrayOfByte3, arrayOfByte1, byteBuffer2.limit() + 46);
    this.written += arrayOfByte3.length;
    putBytes(byteBuffer1.array(), byteBuffer1.arrayOffset(), byteBuffer1.limit(), arrayOfByte1, byteBuffer2.limit() + 46 + arrayOfByte3.length);
    this.written += byteBuffer1.limit();
    writeOut(arrayOfByte1);
  }
  
  protected void writeDataDescriptor(ZipEntry paramZipEntry) throws IOException {
    if (paramZipEntry.getMethod() == 8) {
      if (this.raf != null)
        return; 
      byte[] arrayOfByte = new byte[16];
      putBytes(DD_SIG, arrayOfByte, 0);
      ZipLong.putLong(this.entry.getCrc(), arrayOfByte, 4);
      ZipLong.putLong(this.entry.getCompressedSize(), arrayOfByte, 8);
      ZipLong.putLong(this.entry.getSize(), arrayOfByte, 12);
      writeOut(arrayOfByte);
      this.written += 16L;
    } 
  }
  
  public void writeFully(InputStream paramInputStream) throws IOException {
    byte[] arrayOfByte = new byte[10240];
    while (true) {
      int i = paramInputStream.read(arrayOfByte);
      if (i > 0) {
        write(arrayOfByte, 0, i);
        continue;
      } 
      break;
    } 
  }
  
  protected void writeLocalFileHeader(ZipEntry paramZipEntry) throws IOException {
    ZipEncoding zipEncoding1;
    boolean bool = this.zipEncoding.canEncode(paramZipEntry.getName());
    if (!bool && this.fallbackToUTF8) {
      zipEncoding1 = ZipEncodingHelper.UTF8_ZIP_ENCODING;
    } else {
      zipEncoding1 = this.zipEncoding;
    } 
    ByteBuffer byteBuffer = zipEncoding1.encode(paramZipEntry.getName());
    if (this.createUnicodeExtraFields != UnicodeExtraFieldPolicy.NEVER) {
      if (this.createUnicodeExtraFields == UnicodeExtraFieldPolicy.ALWAYS || !bool)
        paramZipEntry.addExtraField((ZipExtraField)new UnicodePathExtraField(paramZipEntry.getName(), byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.limit())); 
      String str = paramZipEntry.getComment();
      if (str != null && !"".equals(str)) {
        boolean bool3 = this.zipEncoding.canEncode(str);
        if (this.createUnicodeExtraFields == UnicodeExtraFieldPolicy.ALWAYS || !bool3) {
          ByteBuffer byteBuffer1 = zipEncoding1.encode(str);
          paramZipEntry.addExtraField((ZipExtraField)new UnicodeCommentExtraField(str, byteBuffer1.array(), byteBuffer1.arrayOffset(), byteBuffer1.limit()));
        } 
      } 
    } 
    this.offsets.put(paramZipEntry, Long.valueOf(this.written));
    byte[] arrayOfByte3 = new byte[byteBuffer.limit() + 30];
    byte[] arrayOfByte1 = LFH_SIG;
    boolean bool2 = false;
    putBytes(arrayOfByte1, arrayOfByte3, 0);
    this.written += 4L;
    int i = paramZipEntry.getMethod();
    boolean bool1 = bool2;
    if (!bool) {
      bool1 = bool2;
      if (this.fallbackToUTF8)
        bool1 = true; 
    } 
    writeVersionNeededToExtractAndGeneralPurposeBits(arrayOfByte3, 4, i, bool1);
    this.written += 4L;
    ZipShort.putShort(i, arrayOfByte3, 8);
    this.written += 2L;
    ZipLong.putLong(toDosTime(paramZipEntry.getTime()), arrayOfByte3, 10);
    long l = this.written + 4L;
    this.written = l;
    this.localDataStart = l;
    if (this.currentIsRawEntry) {
      ZipLong.putLong(paramZipEntry.getCrc(), arrayOfByte3, 14);
      ZipLong.putLong(paramZipEntry.getCompressedSize(), arrayOfByte3, 18);
      ZipLong.putLong(paramZipEntry.getSize(), arrayOfByte3, 22);
    } else if (i != 8 && this.raf == null) {
      ZipLong.putLong(paramZipEntry.getCrc(), arrayOfByte3, 14);
      ZipLong.putLong(paramZipEntry.getSize(), arrayOfByte3, 18);
      ZipLong.putLong(paramZipEntry.getSize(), arrayOfByte3, 22);
    } 
    this.written += 12L;
    ZipShort.putShort(byteBuffer.limit(), arrayOfByte3, 26);
    this.written += 2L;
    byte[] arrayOfByte2 = paramZipEntry.getLocalFileDataExtra();
    arrayOfByte1 = arrayOfByte2;
    if (paramZipEntry.getMethod() == 0) {
      arrayOfByte1 = arrayOfByte2;
      if ((this.written + 2L + byteBuffer.limit() + arrayOfByte2.length) % 4L != 0L)
        arrayOfByte1 = new byte[4 - (int)((this.written + 2L + byteBuffer.limit()) % 4L)]; 
    } 
    ZipShort.putShort(arrayOfByte1.length, arrayOfByte3, 28);
    this.written += 2L;
    putBytes(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.limit(), arrayOfByte3, 30);
    this.written += byteBuffer.limit();
    writeOut(arrayOfByte3);
    if (arrayOfByte1.length > 0) {
      writeOut(arrayOfByte1);
      this.written += arrayOfByte1.length;
    } 
    this.dataStart = this.written;
  }
  
  protected final void writeOut(byte[] paramArrayOfbyte) throws IOException { writeOut(paramArrayOfbyte, 0, paramArrayOfbyte.length); }
  
  protected final void writeOut(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    RandomAccessFile randomAccessFile = this.raf;
    if (randomAccessFile != null) {
      randomAccessFile.write(paramArrayOfbyte, paramInt1, paramInt2);
      return;
    } 
    this.out.write(paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public void writeRaw(byte[] paramArrayOfbyte) throws IOException { writeRaw(paramArrayOfbyte, 0, paramArrayOfbyte.length); }
  
  public void writeRaw(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    writeOut(paramArrayOfbyte, paramInt1, paramInt2);
    this.written += paramInt2;
  }
  
  public static final class UnicodeExtraFieldPolicy {
    public static final UnicodeExtraFieldPolicy ALWAYS = new UnicodeExtraFieldPolicy("always");
    
    public static final UnicodeExtraFieldPolicy NEVER = new UnicodeExtraFieldPolicy("never");
    
    public static final UnicodeExtraFieldPolicy NOT_ENCODEABLE = new UnicodeExtraFieldPolicy("not encodeable");
    
    private final String name;
    
    private UnicodeExtraFieldPolicy(String param1String) { this.name = param1String; }
    
    public String toString() { return this.name; }
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\ZipOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */