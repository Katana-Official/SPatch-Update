package com.sk.spatch.xmltool.decode;

import com.sk.spatch.xmltool.decode.extrafield.CentralDirectoryParsingZipExtraField;
import com.sk.spatch.xmltool.decode.extrafield.ExtraFieldUtils;
import com.sk.spatch.xmltool.decode.extrafield.UnparseableExtraFieldData;
import com.sk.spatch.xmltool.decode.extrafield.ZipExtraField;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;

public class ZipEntry extends ZipEntry implements Cloneable {
  public static final int PLATFORM_FAT = 0;
  
  public static final int PLATFORM_UNIX = 3;
  
  private static final int SHORT_MASK = 65535;
  
  private static final int SHORT_SHIFT = 16;
  
  private long externalAttributes = 0L;
  
  private LinkedHashMap<ZipShort, ZipExtraField> extraFields = null;
  
  private int hash = 0;
  
  private int internalAttributes = 0;
  
  private String name = null;
  
  private String parent = null;
  
  private int platform = 0;
  
  private String simpleName = null;
  
  private UnparseableExtraFieldData unparseableExtra = null;
  
  protected ZipEntry() { super(""); }
  
  public ZipEntry(ZipEntry paramZipEntry) throws ZipException {
    this(paramZipEntry);
    setInternalAttributes(paramZipEntry.getInternalAttributes());
    setExternalAttributes(paramZipEntry.getExternalAttributes());
    setExtraFields(paramZipEntry.getExtraFields(true));
  }
  
  public ZipEntry(String paramString) {
    super(paramString);
    setName(paramString);
  }
  
  public ZipEntry(ZipEntry paramZipEntry) throws ZipException {
    super(paramZipEntry);
    setName(paramZipEntry.getName());
    byte[] arrayOfByte = paramZipEntry.getExtra();
    if (arrayOfByte != null) {
      setExtraFields(ExtraFieldUtils.parse(arrayOfByte, true, ExtraFieldUtils.UnparseableExtraField.READ));
      return;
    } 
    setExtra();
  }
  
  private void mergeExtraFields(ZipExtraField[] paramArrayOfZipExtraField, boolean paramBoolean) throws ZipException {
    if (this.extraFields == null) {
      setExtraFields(paramArrayOfZipExtraField);
      return;
    } 
    int j = paramArrayOfZipExtraField.length;
    for (int i = 0; i < j; i++) {
      Object object;
      ZipExtraField zipExtraField = paramArrayOfZipExtraField[i];
      if (zipExtraField instanceof UnparseableExtraFieldData) {
        object = this.unparseableExtra;
      } else {
        object = getExtraField(zipExtraField.getHeaderId());
      } 
      if (object == null) {
        addExtraField(zipExtraField);
      } else {
        byte[] arrayOfByte;
        if (paramBoolean || !(object instanceof CentralDirectoryParsingZipExtraField)) {
          arrayOfByte = zipExtraField.getLocalFileDataData();
          object.parseFromLocalFileData(arrayOfByte, 0, arrayOfByte.length);
        } else {
          arrayOfByte = arrayOfByte.getCentralDirectoryData();
          ((CentralDirectoryParsingZipExtraField)object).parseFromCentralDirectoryData(arrayOfByte, 0, arrayOfByte.length);
        } 
      } 
    } 
    setExtra();
  }
  
  private void setParentAndSimpleName() {
    String str = this.name;
    if (str != null) {
      if (str.length() == 0)
        return; 
      str = this.name;
      if (str.charAt(str.length() - 1) == '/') {
        str = this.name;
        int j = str.lastIndexOf('/', str.length() - 2);
        if (j == -1) {
          str = this.name;
          this.simpleName = str.substring(0, str.length() - 1);
          this.parent = null;
          return;
        } 
        str = this.name;
        this.simpleName = str.substring(++j, str.length() - 1);
        this.parent = this.name.substring(0, j);
        return;
      } 
      int i = this.name.lastIndexOf('/');
      if (i == -1) {
        this.simpleName = this.name;
        this.parent = null;
        return;
      } 
      str = this.name;
      this.simpleName = str.substring(++i);
      this.parent = this.name.substring(0, i);
    } 
  }
  
  public void addAsFirstExtraField(ZipExtraField paramZipExtraField) {
    if (paramZipExtraField instanceof UnparseableExtraFieldData) {
      this.unparseableExtra = (UnparseableExtraFieldData)paramZipExtraField;
    } else {
      LinkedHashMap<ZipShort, ZipExtraField> linkedHashMap = this.extraFields;
      LinkedHashMap<Object, Object> linkedHashMap1 = new LinkedHashMap<Object, Object>();
      this.extraFields = (LinkedHashMap)linkedHashMap1;
      linkedHashMap1.put(paramZipExtraField.getHeaderId(), paramZipExtraField);
      if (linkedHashMap != null) {
        linkedHashMap.remove(paramZipExtraField.getHeaderId());
        this.extraFields.putAll(linkedHashMap);
      } 
    } 
    setExtra();
  }
  
  public void addExtraField(ZipExtraField paramZipExtraField) {
    if (paramZipExtraField instanceof UnparseableExtraFieldData) {
      this.unparseableExtra = (UnparseableExtraFieldData)paramZipExtraField;
    } else {
      if (this.extraFields == null)
        this.extraFields = new LinkedHashMap<ZipShort, ZipExtraField>(); 
      this.extraFields.put(paramZipExtraField.getHeaderId(), paramZipExtraField);
    } 
    setExtra();
  }
  
  public Object clone() {
    ZipEntry zipEntry = (ZipEntry)super.clone();
    zipEntry.setInternalAttributes(getInternalAttributes());
    zipEntry.setExternalAttributes(getExternalAttributes());
    zipEntry.setExtraFields(getExtraFields(true));
    return zipEntry;
  }
  
  public boolean equals(Object paramObject) { return (this == paramObject); }
  
  public byte[] getCentralDirectoryExtra() { return ExtraFieldUtils.mergeCentralDirectoryData(getExtraFields(true)); }
  
  public long getExternalAttributes() { return this.externalAttributes; }
  
  public ZipExtraField getExtraField(ZipShort paramZipShort) {
    LinkedHashMap<ZipShort, ZipExtraField> linkedHashMap = this.extraFields;
    return (linkedHashMap != null) ? linkedHashMap.get(paramZipShort) : null;
  }
  
  public ZipExtraField[] getExtraFields() { return getExtraFields(false); }
  
  public ZipExtraField[] getExtraFields(boolean paramBoolean) {
    if (this.extraFields == null) {
      if (paramBoolean) {
        UnparseableExtraFieldData unparseableExtraFieldData = this.unparseableExtra;
        if (unparseableExtraFieldData != null)
          return new ZipExtraField[] { (ZipExtraField)unparseableExtraFieldData }; 
      } 
      return new ZipExtraField[0];
    } 
    ArrayList<UnparseableExtraFieldData> arrayList = new ArrayList(this.extraFields.values());
    if (paramBoolean) {
      UnparseableExtraFieldData unparseableExtraFieldData = this.unparseableExtra;
      if (unparseableExtraFieldData != null)
        arrayList.add(unparseableExtraFieldData); 
    } 
    return arrayList.toArray(new ZipExtraField[0]);
  }
  
  public int getInternalAttributes() { return this.internalAttributes; }
  
  public Date getLastModifiedDate() { return new Date(getTime()); }
  
  public byte[] getLocalFileDataExtra() {
    byte[] arrayOfByte = getExtra();
    return (arrayOfByte != null) ? arrayOfByte : new byte[0];
  }
  
  public String getName() {
    String str2 = this.name;
    String str1 = str2;
    if (str2 == null)
      str1 = super.getName(); 
    return str1;
  }
  
  public String getParent() { return this.parent; }
  
  public int getPlatform() { return this.platform; }
  
  public String getSimpleName() { return this.simpleName; }
  
  public int getUnixMode() { return (this.platform != 3) ? 0 : (int)(getExternalAttributes() >> 16 & 0xFFFFL); }
  
  public UnparseableExtraFieldData getUnparseableExtraFieldData() { return this.unparseableExtra; }
  
  public int hashCode() {
    int j = this.hash;
    int i = j;
    if (j == 0) {
      i = getName().hashCode();
      this.hash = i;
    } 
    return i;
  }
  
  public boolean isDirectory() { return getName().endsWith("/"); }
  
  public void removeExtraField(ZipShort paramZipShort) {
    LinkedHashMap<ZipShort, ZipExtraField> linkedHashMap = this.extraFields;
    if (linkedHashMap != null) {
      if (linkedHashMap.remove(paramZipShort) != null) {
        setExtra();
        return;
      } 
      throw new NoSuchElementException();
    } 
    throw new NoSuchElementException();
  }
  
  public void removeUnparseableExtraFieldData() {
    if (this.unparseableExtra != null) {
      this.unparseableExtra = null;
      setExtra();
      return;
    } 
    throw new NoSuchElementException();
  }
  
  public void setCentralDirectoryExtra(byte[] paramArrayOfbyte) {
    try {
      mergeExtraFields(ExtraFieldUtils.parse(paramArrayOfbyte, false, ExtraFieldUtils.UnparseableExtraField.READ), false);
      return;
    } catch (Exception exception) {
      throw new RuntimeException(exception.getMessage(), exception);
    } 
  }
  
  public void setComprSize(long paramLong) { setCompressedSize(paramLong); }
  
  public void setExternalAttributes(long paramLong) { this.externalAttributes = paramLong; }
  
  protected void setExtra() { super.setExtra(ExtraFieldUtils.mergeLocalFileDataData(getExtraFields(true))); }
  
  public void setExtra(byte[] paramArrayOfbyte) throws RuntimeException {
    try {
      mergeExtraFields(ExtraFieldUtils.parse(paramArrayOfbyte, true, ExtraFieldUtils.UnparseableExtraField.READ), true);
      return;
    } catch (Exception exception) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Error parsing extra fields for entry: ");
      stringBuilder.append(getName());
      stringBuilder.append(" - ");
      stringBuilder.append(exception.getMessage());
      throw new RuntimeException(stringBuilder.toString(), exception);
    } 
  }
  
  public void setExtraFields(ZipExtraField[] paramArrayOfZipExtraField) {
    this.extraFields = new LinkedHashMap<ZipShort, ZipExtraField>();
    int j = paramArrayOfZipExtraField.length;
    for (int i = 0; i < j; i++) {
      ZipExtraField zipExtraField = paramArrayOfZipExtraField[i];
      if (zipExtraField instanceof UnparseableExtraFieldData) {
        this.unparseableExtra = (UnparseableExtraFieldData)zipExtraField;
      } else {
        this.extraFields.put(zipExtraField.getHeaderId(), zipExtraField);
      } 
    } 
    setExtra();
  }
  
  public void setInternalAttributes(int paramInt) { this.internalAttributes = paramInt; }
  
  public void setName(String paramString) {
    String str = paramString;
    if (paramString != null) {
      str = paramString;
      if (getPlatform() == 0) {
        str = paramString;
        if (!paramString.contains("/"))
          str = paramString.replace('\\', '/'); 
      } 
    } 
    this.name = str;
    setParentAndSimpleName();
  }
  
  protected void setPlatform(int paramInt) { this.platform = paramInt; }
  
  public void setUnixMode(int paramInt) {
    boolean bool;
    byte b = 0;
    if ((paramInt & 0x80) == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    if (isDirectory())
      b = 16; 
    setExternalAttributes((bool | paramInt << 16 | b));
    this.platform = 3;
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\ZipEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */