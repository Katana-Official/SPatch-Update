package com.sk.spatch.xmltool.decode.extrafield;

import com.sk.spatch.xmltool.decode.JarMarker;
import com.sk.spatch.xmltool.decode.ZipShort;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipException;

public class ExtraFieldUtils {
  private static final int WORD = 4;
  
  private static final Map<ZipShort, Class> implementations = (Map)new HashMap<ZipShort, Class<?>>();
  
  static  {
    register(AsiExtraField.class);
    register(JarMarker.class);
    register(UnicodePathExtraField.class);
    register(UnicodeCommentExtraField.class);
  }
  
  public static ZipExtraField createExtraField(ZipShort paramZipShort) throws InstantiationException, IllegalAccessException {
    Class<ZipExtraField> clazz = implementations.get(paramZipShort);
    if (clazz != null)
      return clazz.newInstance(); 
    UnrecognizedExtraField unrecognizedExtraField = new UnrecognizedExtraField();
    unrecognizedExtraField.setHeaderId(paramZipShort);
    return unrecognizedExtraField;
  }
  
  public static byte[] mergeCentralDirectoryData(ZipExtraField[] paramArrayOfZipExtraField) {
    boolean bool;
    if (paramArrayOfZipExtraField.length > 0 && paramArrayOfZipExtraField[paramArrayOfZipExtraField.length - 1] instanceof UnparseableExtraFieldData) {
      bool = true;
    } else {
      bool = false;
    } 
    int j = paramArrayOfZipExtraField.length;
    int i = j;
    if (bool)
      i = j - 1; 
    int k = i * 4;
    int m = paramArrayOfZipExtraField.length;
    for (j = 0; j < m; j++)
      k += paramArrayOfZipExtraField[j].getCentralDirectoryLength().getValue(); 
    byte[] arrayOfByte = new byte[k];
    j = 0;
    k = j;
    while (j < i) {
      System.arraycopy(paramArrayOfZipExtraField[j].getHeaderId().getBytes(), 0, arrayOfByte, k, 2);
      System.arraycopy(paramArrayOfZipExtraField[j].getCentralDirectoryLength().getBytes(), 0, arrayOfByte, k + 2, 2);
      byte[] arrayOfByte1 = paramArrayOfZipExtraField[j].getCentralDirectoryData();
      System.arraycopy(arrayOfByte1, 0, arrayOfByte, k + 4, arrayOfByte1.length);
      k += arrayOfByte1.length + 4;
      j++;
    } 
    if (bool) {
      byte[] arrayOfByte1 = paramArrayOfZipExtraField[paramArrayOfZipExtraField.length - 1].getCentralDirectoryData();
      System.arraycopy(arrayOfByte1, 0, arrayOfByte, k, arrayOfByte1.length);
    } 
    return arrayOfByte;
  }
  
  public static byte[] mergeLocalFileDataData(ZipExtraField[] paramArrayOfZipExtraField) {
    boolean bool;
    if (paramArrayOfZipExtraField.length > 0 && paramArrayOfZipExtraField[paramArrayOfZipExtraField.length - 1] instanceof UnparseableExtraFieldData) {
      bool = true;
    } else {
      bool = false;
    } 
    int j = paramArrayOfZipExtraField.length;
    int i = j;
    if (bool)
      i = j - 1; 
    int k = i * 4;
    int m = paramArrayOfZipExtraField.length;
    for (j = 0; j < m; j++)
      k += paramArrayOfZipExtraField[j].getLocalFileDataLength().getValue(); 
    byte[] arrayOfByte = new byte[k];
    j = 0;
    k = j;
    while (j < i) {
      System.arraycopy(paramArrayOfZipExtraField[j].getHeaderId().getBytes(), 0, arrayOfByte, k, 2);
      System.arraycopy(paramArrayOfZipExtraField[j].getLocalFileDataLength().getBytes(), 0, arrayOfByte, k + 2, 2);
      byte[] arrayOfByte1 = paramArrayOfZipExtraField[j].getLocalFileDataData();
      System.arraycopy(arrayOfByte1, 0, arrayOfByte, k + 4, arrayOfByte1.length);
      k += arrayOfByte1.length + 4;
      j++;
    } 
    if (bool) {
      byte[] arrayOfByte1 = paramArrayOfZipExtraField[paramArrayOfZipExtraField.length - 1].getLocalFileDataData();
      System.arraycopy(arrayOfByte1, 0, arrayOfByte, k, arrayOfByte1.length);
    } 
    return arrayOfByte;
  }
  
  public static ZipExtraField[] parse(byte[] paramArrayOfbyte) throws ZipException { return parse(paramArrayOfbyte, true, UnparseableExtraField.THROW); }
  
  public static ZipExtraField[] parse(byte[] paramArrayOfbyte, boolean paramBoolean) throws ZipException { return parse(paramArrayOfbyte, paramBoolean, UnparseableExtraField.THROW); }
  
  public static ZipExtraField[] parse(byte[] paramArrayOfbyte, boolean paramBoolean, UnparseableExtraField paramUnparseableExtraField) throws ZipException {
    ArrayList<UnparseableExtraFieldData> arrayList = new ArrayList();
    int i = 0;
    while (i <= paramArrayOfbyte.length - 4) {
      StringBuilder stringBuilder;
      ZipShort zipShort = new ZipShort(paramArrayOfbyte, i);
      int j = (new ZipShort(paramArrayOfbyte, i + 2)).getValue();
      int k = i + 4;
      if (k + j > paramArrayOfbyte.length) {
        k = paramUnparseableExtraField.getKey();
        if (k != 0) {
          if (k != 1) {
            UnparseableExtraFieldData unparseableExtraFieldData;
            if (k == 2) {
              unparseableExtraFieldData = new UnparseableExtraFieldData();
              if (paramBoolean) {
                unparseableExtraFieldData.parseFromLocalFileData(paramArrayOfbyte, i, paramArrayOfbyte.length - i);
              } else {
                unparseableExtraFieldData.parseFromCentralDirectoryData(paramArrayOfbyte, i, paramArrayOfbyte.length - i);
              } 
              arrayList.add(unparseableExtraFieldData);
              break;
            } 
            stringBuilder = new StringBuilder();
            stringBuilder.append("unknown UnparseableExtraField key: ");
            stringBuilder.append(unparseableExtraFieldData.getKey());
            throw new ZipException(stringBuilder.toString());
          } 
          break;
        } 
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("bad extra field starting at ");
        stringBuilder1.append(i);
        stringBuilder1.append(".  Block length of ");
        stringBuilder1.append(j);
        stringBuilder1.append(" bytes exceeds remaining data of ");
        stringBuilder1.append(stringBuilder.length - i - 4);
        stringBuilder1.append(" bytes.");
        throw new ZipException(stringBuilder1.toString());
      } 
      try {
        ZipExtraField zipExtraField = createExtraField(zipShort);
        if (paramBoolean || !(zipExtraField instanceof CentralDirectoryParsingZipExtraField)) {
          zipExtraField.parseFromLocalFileData((byte[])stringBuilder, k, j);
        } else {
          ((CentralDirectoryParsingZipExtraField)zipExtraField).parseFromCentralDirectoryData((byte[])stringBuilder, k, j);
        } 
        arrayList.add(zipExtraField);
        i += j + 4;
      } catch (InstantiationException instantiationException) {
        throw new ZipException(instantiationException.getMessage());
      } catch (IllegalAccessException illegalAccessException) {
        throw new ZipException(illegalAccessException.getMessage());
      } 
    } 
    return arrayList.toArray(new ZipExtraField[arrayList.size()]);
  }
  
  public static void register(Class<ZipExtraField> paramClass) {
    try {
      ZipExtraField zipExtraField = paramClass.newInstance();
      implementations.put(zipExtraField.getHeaderId(), paramClass);
      return;
    } catch (ClassCastException classCastException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramClass);
      stringBuilder.append(" doesn't implement ZipExtraField");
      throw new RuntimeException(stringBuilder.toString());
    } catch (InstantiationException instantiationException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramClass);
      stringBuilder.append(" is not a concrete class");
      throw new RuntimeException(stringBuilder.toString());
    } catch (IllegalAccessException illegalAccessException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramClass);
      stringBuilder.append("'s no-arg constructor is not public");
      throw new RuntimeException(stringBuilder.toString());
    } 
  }
  
  public static final class UnparseableExtraField {
    public static final UnparseableExtraField READ;
    
    public static final int READ_KEY = 2;
    
    public static final UnparseableExtraField SKIP;
    
    public static final int SKIP_KEY = 1;
    
    public static final UnparseableExtraField THROW = new UnparseableExtraField(0);
    
    public static final int THROW_KEY = 0;
    
    private final int key;
    
    static  {
      SKIP = new UnparseableExtraField(1);
      READ = new UnparseableExtraField(2);
    }
    
    private UnparseableExtraField(int param1Int) { this.key = param1Int; }
    
    public int getKey() { return this.key; }
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\extrafield\ExtraFieldUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */