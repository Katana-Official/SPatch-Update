package com.sk.spatch.xmltool.android.content.res;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class StringBlock {
  private static final int CHUNK_TYPE = 1835009;
  
  private static final int UTF8_FLAG = 256;
  
  private final CharsetDecoder UTF16LE_DECODER = Charset.forName("UTF-16LE").newDecoder();
  
  private final CharsetDecoder UTF8_DECODER = Charset.forName("UTF-8").newDecoder();
  
  private boolean m_isUTF8;
  
  private int[] m_stringOffsets;
  
  private byte[] m_strings;
  
  private int[] m_styleOffsets;
  
  private int[] m_styles;
  
  private String decodeString(int paramInt1, int paramInt2) {
    try {
      CharsetDecoder charsetDecoder;
      if (this.m_isUTF8) {
        charsetDecoder = this.UTF8_DECODER;
      } else {
        charsetDecoder = this.UTF16LE_DECODER;
      } 
      return charsetDecoder.decode(ByteBuffer.wrap(this.m_strings, paramInt1, paramInt2)).toString();
    } catch (CharacterCodingException characterCodingException) {
      return null;
    } 
  }
  
  private static final int getShort(byte[] paramArrayOfbyte, int paramInt) {
    byte b = paramArrayOfbyte[paramInt + 1];
    return paramArrayOfbyte[paramInt] & 0xFF | (b & 0xFF) << 8;
  }
  
  private static final int getShort(int[] paramArrayOfint, int paramInt) {
    int i = paramArrayOfint[paramInt / 4];
    return (paramInt % 4 / 2 == 0) ? (i & 0xFFFF) : (i >>> 16);
  }
  
  private int[] getStyle(int paramInt) {
    int[] arrayOfInt3 = this.m_styleOffsets;
    int[] arrayOfInt2 = null;
    int[] arrayOfInt1 = arrayOfInt2;
    if (arrayOfInt3 != null) {
      arrayOfInt1 = arrayOfInt2;
      if (this.m_styles != null) {
        if (paramInt >= arrayOfInt3.length)
          return null; 
        paramInt = arrayOfInt3[paramInt] / 4;
        boolean bool = false;
        int i = paramInt;
        int j = 0;
        while (true) {
          arrayOfInt1 = this.m_styles;
          if (i >= arrayOfInt1.length || arrayOfInt1[i] == -1)
            break; 
          j++;
          i++;
        } 
        arrayOfInt1 = arrayOfInt2;
        if (j != 0) {
          if (j % 3 != 0)
            return null; 
          arrayOfInt2 = new int[j];
          i = bool;
          while (true) {
            arrayOfInt3 = this.m_styles;
            arrayOfInt1 = arrayOfInt2;
            if (paramInt < arrayOfInt3.length) {
              if (arrayOfInt3[paramInt] == -1)
                return arrayOfInt2; 
              arrayOfInt2[i] = arrayOfInt3[paramInt];
              i++;
              paramInt++;
              continue;
            } 
            break;
          } 
        } 
      } 
    } 
    return arrayOfInt1;
  }
  
  private static final int[] getUtf16(byte[] paramArrayOfbyte, int paramInt) {
    int i = (paramArrayOfbyte[paramInt + 1] & 0xFF) << 8 | paramArrayOfbyte[paramInt] & 0xFF;
    return (i == 32768) ? new int[] { 4, (((paramArrayOfbyte[paramInt + 3] & 0xFF) << 8) + (paramArrayOfbyte[paramInt + 2] & 0xFF)) * 2 } : new int[] { 2, i * 2 };
  }
  
  private static final int[] getUtf8(byte[] paramArrayOfbyte, int paramInt) {
    if ((paramArrayOfbyte[paramInt] & 0x80) != 0) {
      paramInt += 2;
    } else {
      paramInt++;
    } 
    if ((paramArrayOfbyte[paramInt] & 0x80) != 0) {
      paramInt += 2;
    } else {
      paramInt++;
    } 
    int i;
    for (i = 0; paramArrayOfbyte[paramInt + i] != 0; i++);
    return new int[] { paramInt, i };
  }
  
  public static StringBlock read(IntReader paramIntReader) throws IOException {
    boolean bool;
    ChunkUtil.readCheckType(paramIntReader, 1835009);
    int j = paramIntReader.readInt();
    int i = paramIntReader.readInt();
    int n = paramIntReader.readInt();
    int i1 = paramIntReader.readInt();
    int m = paramIntReader.readInt();
    int k = paramIntReader.readInt();
    StringBlock stringBlock = new StringBlock();
    if ((i1 & 0x100) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    stringBlock.m_isUTF8 = bool;
    stringBlock.m_stringOffsets = paramIntReader.readIntArray(i);
    if (n != 0)
      stringBlock.m_styleOffsets = paramIntReader.readIntArray(n); 
    if (k == 0) {
      i = j;
    } else {
      i = k;
    } 
    byte[] arrayOfByte = new byte[i - m];
    stringBlock.m_strings = arrayOfByte;
    paramIntReader.readFully(arrayOfByte);
    if (k != 0) {
      i = j - k;
      if (i % 4 == 0) {
        stringBlock.m_styles = paramIntReader.readIntArray(i / 4);
        return stringBlock;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Style data size is not multiple of 4 (");
      stringBuilder.append(i);
      stringBuilder.append(").");
      throw new IOException(stringBuilder.toString());
    } 
    return stringBlock;
  }
  
  public int find(String paramString) {
    if (paramString == null)
      return -1; 
    int i = 0;
    while (true) {
      int[] arrayOfInt = this.m_stringOffsets;
      if (i != arrayOfInt.length) {
        int j = arrayOfInt[i];
        int k = getShort(this.m_strings, j);
        if (k == paramString.length()) {
          int m;
          for (m = 0; m != k; m++) {
            j += 2;
            if (paramString.charAt(m) != getShort(this.m_strings, j))
              break; 
          } 
          if (m == k)
            return i; 
        } 
        i++;
        continue;
      } 
      break;
    } 
    return -1;
  }
  
  public CharSequence get(int paramInt) { return getString(paramInt); }
  
  public int getCount() {
    int[] arrayOfInt = this.m_stringOffsets;
    return (arrayOfInt != null) ? arrayOfInt.length : 0;
  }
  
  public String getHTML(int paramInt) {
    Object object;
    String str = getString(paramInt);
    if (str == null)
      return str; 
    int[] arrayOfInt = getStyle(paramInt);
    if (arrayOfInt == null)
      return str; 
    StringBuilder stringBuilder = new StringBuilder(str.length() + 32);
    paramInt = 0;
    while (true) {
      Object object1;
      int i;
      int j = 0;
      byte b = -1;
      while (true) {
        j += 3;
        object1 = SYNTHETIC_LOCAL_VARIABLE_3;
      } 
      if (object1 != -1) {
        j = arrayOfInt[object1 + 1];
      } else {
        j = str.length();
      } 
      int m = 0;
      while (m != arrayOfInt.length) {
        int n;
        int i1 = m + 2;
        int i2 = arrayOfInt[i1];
        Object object2 = object;
        if (i2 != -1)
          if (i2 >= j) {
            object2 = object;
          } else {
            object2 = object;
            if (object <= i2) {
              n = i2 + 1;
              stringBuilder.append(str, object, n);
            } 
            arrayOfInt[i1] = -1;
            stringBuilder.append('<');
            stringBuilder.append('/');
            stringBuilder.append(getString(arrayOfInt[m]));
            stringBuilder.append('>');
          }  
        m += 3;
        i = n;
      } 
      int k = i;
      if (i < j) {
        stringBuilder.append(str, i, j);
        k = j;
      } 
      if (object1 == -1)
        return stringBuilder.toString(); 
      continue;
      stringBuilder.append('<');
      stringBuilder.append(getString(arrayOfInt[SYNTHETIC_LOCAL_VARIABLE_4]));
      stringBuilder.append('>');
      arrayOfInt[SYNTHETIC_LOCAL_VARIABLE_4 + 1] = -1;
      object = SYNTHETIC_LOCAL_VARIABLE_3;
    } 
  }
  
  public String getString(int paramInt) {
    if (paramInt >= 0) {
      int[] arrayOfInt = this.m_stringOffsets;
      if (arrayOfInt != null && paramInt < arrayOfInt.length) {
        int i;
        paramInt = arrayOfInt[paramInt];
        if (this.m_isUTF8) {
          arrayOfInt = getUtf8(this.m_strings, paramInt);
          paramInt = arrayOfInt[0];
          i = arrayOfInt[1];
        } else {
          arrayOfInt = getUtf16(this.m_strings, paramInt);
          int j = arrayOfInt[0];
          i = arrayOfInt[1];
          paramInt += j;
        } 
        return decodeString(paramInt, i);
      } 
    } 
    return null;
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\android\content\res\StringBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */