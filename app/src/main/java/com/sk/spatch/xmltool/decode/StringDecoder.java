package com.sk.spatch.xmltool.decode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.List;

public class StringDecoder {
  private static final int CHUNK_NULL_TYPE = 0;
  
  private static final int CHUNK_STRINGPOOL_TYPE = 1835009;
  
  public static final int IS_UTF8 = 256;
  
  private static final CharsetDecoder UTF16LE_DECODER = Charset.forName("UTF-16LE").newDecoder();
  
  private static final CharsetDecoder UTF8_DECODER = Charset.forName("UTF-8").newDecoder();
  
  private int chunkSize;
  
  private int flags;
  
  private boolean m_isUTF8;
  
  private String[] m_strings;
  
  private int m_strings_size;
  
  private int[] m_styleOffsets;
  
  private int[] m_styles;
  
  private int styleOffsetCount;
  
  private int stylesOffset;
  
  private static String decodeString(int paramInt1, int paramInt2, boolean paramBoolean, byte[] paramArrayOfbyte) {
    if (paramBoolean)
      try {
        CharsetDecoder charsetDecoder1 = UTF8_DECODER;
        return charsetDecoder1.decode(ByteBuffer.wrap(paramArrayOfbyte, paramInt1, paramInt2)).toString();
      } catch (CharacterCodingException characterCodingException) {
        return null;
      }  
    CharsetDecoder charsetDecoder = UTF16LE_DECODER;
    return charsetDecoder.decode(ByteBuffer.wrap((byte[])characterCodingException, paramInt1, paramInt2)).toString();
  }
  
  private static int getShort(byte[] paramArrayOfbyte, int paramInt) {
    byte b = paramArrayOfbyte[paramInt + 1];
    return paramArrayOfbyte[paramInt] & 0xFF | (b & 0xFF) << 8;
  }
  
  protected static byte[] getVarBytes(int paramInt) { return ((paramInt & 0x7F) == paramInt) ? new byte[] { (byte)paramInt } : new byte[] { (byte)(paramInt >>> 8 | 0x80), (byte)(paramInt & 0xFF) }; }
  
  private static int[] getVarint(byte[] paramArrayOfbyte, int paramInt) {
    if ((paramArrayOfbyte[paramInt] & 0x80) == 0)
      return new int[] { paramArrayOfbyte[paramInt] & 0x7F, 1 }; 
    byte b = paramArrayOfbyte[paramInt];
    return new int[] { paramArrayOfbyte[paramInt + 1] & 0xFF | (b & 0x7F) << 8, 2 };
  }
  
  public static StringDecoder read(ZInput paramZInput) throws IOException {
    boolean bool;
    paramZInput.skipCheckChunkTypeInt(1835009, 0);
    StringDecoder stringDecoder = new StringDecoder();
    int j = paramZInput.readInt();
    stringDecoder.chunkSize = j;
    int i = paramZInput.readInt();
    int n = paramZInput.readInt();
    stringDecoder.styleOffsetCount = n;
    int i1 = paramZInput.readInt();
    stringDecoder.flags = i1;
    int m = paramZInput.readInt();
    int k = paramZInput.readInt();
    stringDecoder.stylesOffset = k;
    if ((i1 & 0x100) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    stringDecoder.m_isUTF8 = bool;
    int[] arrayOfInt = paramZInput.readIntArray(i);
    if (n != 0)
      stringDecoder.m_styleOffsets = paramZInput.readIntArray(n); 
    if (k == 0) {
      i = j;
    } else {
      i = k;
    } 
    i -= m;
    byte[] arrayOfByte = new byte[i];
    paramZInput.readFully(arrayOfByte);
    stringDecoder.m_strings_size = i;
    if (k != 0) {
      i = j - k;
      stringDecoder.m_styles = paramZInput.readIntArray(i / 4);
      i %= 4;
      if (i >= 1)
        while (i > 0) {
          paramZInput.readByte();
          i--;
        }  
    } 
    stringDecoder.m_strings = new String[arrayOfInt.length];
    n = arrayOfInt.length;
    j = 0;
    for (i = j; j < n; i++) {
      k = arrayOfInt[j];
      if (!stringDecoder.m_isUTF8) {
        m = getShort(arrayOfByte, k) * 2;
        k += 2;
      } else {
        k += getVarint(arrayOfByte, k)[1];
        int[] arrayOfInt1 = getVarint(arrayOfByte, k);
        k += arrayOfInt1[1];
        m = arrayOfInt1[0];
      } 
      stringDecoder.m_strings[i] = decodeString(k, m, stringDecoder.m_isUTF8, arrayOfByte);
      j++;
    } 
    return stringDecoder;
  }
  
  public int find(String paramString) {
    if (paramString == null)
      return -1; 
    for (int i = 0; i < this.m_strings.length; i++) {
      if (getString(i).equals(paramString))
        return i; 
    } 
    return -1;
  }
  
  public int getChunkSize() { return this.chunkSize; }
  
  public int getSize() { return this.m_strings.length; }
  
  public String getString(int paramInt) { return (paramInt >= 0) ? this.m_strings[paramInt] : null; }
  
  public void getStrings(List<String> paramList) {
    int j = getSize();
    for (int i = 0; i < j; i++)
      paramList.add(getString(i)); 
  }
  
  public boolean isUtf8() { return this.m_isUTF8; }
  
  public void setString(int paramInt, String paramString) { this.m_strings[paramInt] = paramString; }
  
  public void write(ZOutput paramZOutput) throws IOException { write(this.m_strings, paramZOutput); }
  
  public void write(String[] paramArrayOfString, ZOutput paramZOutput) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
    ZOutput zOutput1 = new ZOutput(byteArrayOutputStream1);
    int m = paramArrayOfString.length;
    int[] arrayOfInt2 = new int[m];
    ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
    ZOutput zOutput2 = new ZOutput(byteArrayOutputStream2);
    boolean bool1 = this.m_isUTF8;
    boolean bool = false;
    if (bool1) {
      int n = 0;
      int i1 = n;
      while (n < m) {
        arrayOfInt2[n] = i1;
        String str = paramArrayOfString[n];
        byte[] arrayOfByte4 = getVarBytes((str.toCharArray()).length);
        zOutput2.writeFully(arrayOfByte4);
        int i2 = arrayOfByte4.length;
        byte[] arrayOfByte3 = str.getBytes("UTF-8");
        arrayOfByte4 = getVarBytes(arrayOfByte3.length);
        zOutput2.writeFully(arrayOfByte4);
        int i3 = arrayOfByte4.length;
        zOutput2.writeFully(arrayOfByte3);
        int i4 = arrayOfByte3.length;
        zOutput2.writeByte(0);
        i1 = i1 + i2 + i3 + i4 + 1;
        n++;
      } 
    } else {
      int n = 0;
      int i1 = n;
      while (n < m) {
        arrayOfInt2[n] = i1;
        char[] arrayOfChar = paramArrayOfString[n].toCharArray();
        zOutput2.writeShort((short)arrayOfChar.length);
        int i3 = arrayOfChar.length;
        int i2;
        for (i2 = 0; i2 < i3; i2++)
          zOutput2.writeChar(arrayOfChar[i2]); 
        zOutput2.writeShort((short)0);
        i1 += arrayOfChar.length * 2 + 4;
        n++;
      } 
    } 
    int j = byteArrayOutputStream2.size();
    int k = j % 4;
    int i = j;
    if (k != 0) {
      int n;
      i = 0;
      while (true) {
        n = 4 - k;
        if (i < n) {
          byteArrayOutputStream2.write(0);
          i++;
          continue;
        } 
        break;
      } 
      i = j + n;
    } 
    byte[] arrayOfByte2 = byteArrayOutputStream2.toByteArray();
    zOutput1.writeInt(m);
    zOutput1.writeInt(this.styleOffsetCount);
    zOutput1.writeInt(this.flags);
    zOutput1.writeInt((m + this.styleOffsetCount) * 4 + 28);
    j = this.m_strings_size;
    k = this.stylesOffset;
    if (k == 0) {
      i = 0;
    } else {
      i = k + (i - j) * 4;
    } 
    zOutput1.writeInt(i);
    zOutput1.writeIntArray(arrayOfInt2);
    if (this.styleOffsetCount != 0) {
      arrayOfInt2 = this.m_styleOffsets;
      j = arrayOfInt2.length;
      for (i = bool; i < j; i++)
        zOutput1.writeInt(arrayOfInt2[i]); 
    } 
    zOutput1.writeFully(arrayOfByte2);
    int[] arrayOfInt1 = this.m_styles;
    if (arrayOfInt1 != null)
      zOutput1.writeIntArray(arrayOfInt1); 
    paramZOutput.writeInt(1835009);
    byte[] arrayOfByte1 = byteArrayOutputStream1.toByteArray();
    byteArrayOutputStream1.close();
    zOutput1.close();
    paramZOutput.writeInt(arrayOfByte1.length + 8);
    paramZOutput.writeFully(arrayOfByte1);
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\StringDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */