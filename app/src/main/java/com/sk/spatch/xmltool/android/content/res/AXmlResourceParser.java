package com.sk.spatch.xmltool.android.content.res;

import com.sk.spatch.xmltool.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class AXmlResourceParser implements XmlResourceParser {
  private static final int ATTRIBUTE_IX_NAME = 1;
  
  private static final int ATTRIBUTE_IX_NAMESPACE_URI = 0;
  
  private static final int ATTRIBUTE_IX_VALUE_DATA = 4;
  
  private static final int ATTRIBUTE_IX_VALUE_STRING = 2;
  
  private static final int ATTRIBUTE_IX_VALUE_TYPE = 3;
  
  private static final int ATTRIBUTE_LENGHT = 5;
  
  private static final int CHUNK_AXML_FILE = 524291;
  
  private static final int CHUNK_RESOURCEIDS = 524672;
  
  private static final int CHUNK_XML_END_NAMESPACE = 1048833;
  
  private static final int CHUNK_XML_END_TAG = 1048835;
  
  private static final int CHUNK_XML_FIRST = 1048832;
  
  private static final int CHUNK_XML_LAST = 1048836;
  
  private static final int CHUNK_XML_START_NAMESPACE = 1048832;
  
  private static final int CHUNK_XML_START_TAG = 1048834;
  
  private static final int CHUNK_XML_TEXT = 1048836;
  
  private static final String E_NOT_SUPPORTED = "Method is not supported.";
  
  private int[] m_attributes;
  
  private int m_classAttribute;
  
  private boolean m_decreaseDepth;
  
  private int m_event;
  
  private int m_idAttribute;
  
  private int m_lineNumber;
  
  private int m_name;
  
  private int m_namespaceUri;
  
  private NamespaceStack m_namespaces = new NamespaceStack();
  
  private boolean m_operational = false;
  
  private IntReader m_reader;
  
  private int[] m_resourceIDs;
  
  private StringBlock m_strings;
  
  private int m_styleAttribute;
  
  public AXmlResourceParser() { resetEventInfo(); }
  
  private final void doNext() throws IOException {
    int i;
    if (this.m_strings == null) {
      ChunkUtil.readCheckType(this.m_reader, 524291);
      this.m_reader.skipInt();
      this.m_strings = StringBlock.read(this.m_reader);
      this.m_namespaces.increaseDepth();
      this.m_operational = true;
    } 
    int j = this.m_event;
    if (j == 1)
      return; 
    resetEventInfo();
    while (true) {
      if (this.m_decreaseDepth) {
        this.m_decreaseDepth = false;
        this.m_namespaces.decreaseDepth();
      } 
      int k = 3;
      if (j == 3 && this.m_namespaces.getDepth() == 1 && this.m_namespaces.getCurrentCount() == 0) {
        this.m_event = 1;
        return;
      } 
      if (j == 0) {
        i = 1048834;
      } else {
        i = this.m_reader.readInt();
      } 
      if (i == 524672) {
        i = this.m_reader.readInt();
        if (i >= 8 && i % 4 == 0) {
          this.m_resourceIDs = this.m_reader.readIntArray(i / 4 - 2);
          continue;
        } 
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Invalid resource ids size (");
        stringBuilder1.append(i);
        stringBuilder1.append(").");
        throw new IOException(stringBuilder1.toString());
      } 
      if (i >= 1048832 && i <= 1048836) {
        if (i == 1048834 && j == -1) {
          this.m_event = 0;
          return;
        } 
        this.m_reader.skipInt();
        int m = this.m_reader.readInt();
        this.m_reader.skipInt();
        if (i == 1048832 || i == 1048833) {
          if (i == 1048832) {
            i = this.m_reader.readInt();
            k = this.m_reader.readInt();
            this.m_namespaces.push(i, k);
            continue;
          } 
          this.m_reader.skipInt();
          this.m_reader.skipInt();
          this.m_namespaces.pop();
          continue;
        } 
        this.m_lineNumber = m;
        if (i == 1048834) {
          this.m_namespaceUri = this.m_reader.readInt();
          this.m_name = this.m_reader.readInt();
          this.m_reader.skipInt();
          i = this.m_reader.readInt();
          this.m_idAttribute = (i >>> 16) - 1;
          j = this.m_reader.readInt();
          this.m_classAttribute = j;
          this.m_styleAttribute = (j >>> 16) - 1;
          this.m_classAttribute = (0xFFFF & j) - 1;
          this.m_attributes = this.m_reader.readIntArray((i & 0xFFFF) * 5);
          i = k;
          while (true) {
            int[] arrayOfInt = this.m_attributes;
            if (i < arrayOfInt.length) {
              arrayOfInt[i] = arrayOfInt[i] >>> 24;
              i += 5;
              continue;
            } 
            break;
          } 
          this.m_namespaces.increaseDepth();
          this.m_event = 2;
          return;
        } 
        if (i == 1048835) {
          this.m_namespaceUri = this.m_reader.readInt();
          this.m_name = this.m_reader.readInt();
          this.m_event = 3;
          this.m_decreaseDepth = true;
          return;
        } 
        if (i == 1048836) {
          this.m_name = this.m_reader.readInt();
          this.m_reader.skipInt();
          this.m_reader.skipInt();
          this.m_event = 4;
          return;
        } 
        continue;
      } 
      break;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Invalid chunk type (");
    stringBuilder.append(i);
    stringBuilder.append(").");
    throw new IOException(stringBuilder.toString());
  }
  
  private final int findAttribute(String paramString1, String paramString2) {
    StringBlock stringBlock = this.m_strings;
    if (stringBlock != null) {
      byte b;
      if (paramString2 == null)
        return -1; 
      int j = stringBlock.find(paramString2);
      if (j == -1)
        return -1; 
      if (paramString1 != null) {
        b = this.m_strings.find(paramString1);
      } else {
        b = -1;
      } 
      int i = 0;
      while (true) {
        int[] arrayOfInt = this.m_attributes;
        if (i != arrayOfInt.length) {
          int k = i + 1;
          if (j == arrayOfInt[k] && (b == -1 || b == arrayOfInt[i + 0]))
            return i / 5; 
          i = k;
          continue;
        } 
        break;
      } 
    } 
    return -1;
  }
  
  private final int getAttributeOffset(int paramInt) {
    if (this.m_event == 2) {
      int i = paramInt * 5;
      if (i < this.m_attributes.length)
        return i; 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Invalid attribute index (");
      stringBuilder.append(paramInt);
      stringBuilder.append(").");
      throw new IndexOutOfBoundsException(stringBuilder.toString());
    } 
    throw new IndexOutOfBoundsException("Current event is not START_TAG.");
  }
  
  private final void resetEventInfo() {
    this.m_event = -1;
    this.m_lineNumber = -1;
    this.m_name = -1;
    this.m_namespaceUri = -1;
    this.m_attributes = null;
    this.m_idAttribute = -1;
    this.m_classAttribute = -1;
    this.m_styleAttribute = -1;
  }
  
  public void close() {
    if (!this.m_operational)
      return; 
    this.m_operational = false;
    this.m_reader.close();
    this.m_reader = null;
    this.m_strings = null;
    this.m_resourceIDs = null;
    this.m_namespaces.reset();
    resetEventInfo();
  }
  
  public void defineEntityReplacementText(String paramString1, String paramString2) throws XmlPullParserException { throw new XmlPullParserException("Method is not supported."); }
  
  public boolean getAttributeBooleanValue(int paramInt, boolean paramBoolean) { throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n"); }
  
  public boolean getAttributeBooleanValue(String paramString1, String paramString2, boolean paramBoolean) {
    int i = findAttribute(paramString1, paramString2);
    return (i == -1) ? paramBoolean : getAttributeBooleanValue(i, paramBoolean);
  }
  
  public int getAttributeCount() { return (this.m_event != 2) ? -1 : (this.m_attributes.length / 5); }
  
  public float getAttributeFloatValue(int paramInt, float paramFloat) {
    paramInt = getAttributeOffset(paramInt);
    int[] arrayOfInt = this.m_attributes;
    return (arrayOfInt[paramInt + 3] == 4) ? Float.intBitsToFloat(arrayOfInt[paramInt + 4]) : paramFloat;
  }
  
  public float getAttributeFloatValue(String paramString1, String paramString2, float paramFloat) {
    int i = findAttribute(paramString1, paramString2);
    return (i == -1) ? paramFloat : getAttributeFloatValue(i, paramFloat);
  }
  
  public int getAttributeIntValue(int paramInt1, int paramInt2) {
    paramInt1 = getAttributeOffset(paramInt1);
    int[] arrayOfInt = this.m_attributes;
    int i = arrayOfInt[paramInt1 + 3];
    return (i >= 16 && i <= 31) ? arrayOfInt[paramInt1 + 4] : paramInt2;
  }
  
  public int getAttributeIntValue(String paramString1, String paramString2, int paramInt) {
    int i = findAttribute(paramString1, paramString2);
    return (i == -1) ? paramInt : getAttributeIntValue(i, paramInt);
  }
  
  public int getAttributeListValue(int paramInt1, String[] paramArrayOfString, int paramInt2) { return 0; }
  
  public int getAttributeListValue(String paramString1, String paramString2, String[] paramArrayOfString, int paramInt) { return 0; }
  
  public String getAttributeName(int paramInt) {
    paramInt = getAttributeOffset(paramInt);
    paramInt = this.m_attributes[paramInt + 1];
    return (paramInt == -1) ? "" : this.m_strings.getString(paramInt);
  }
  
  public int getAttributeNameResource(int paramInt) {
    paramInt = getAttributeOffset(paramInt);
    paramInt = this.m_attributes[paramInt + 1];
    int[] arrayOfInt = this.m_resourceIDs;
    return (arrayOfInt == null || paramInt < 0 || paramInt >= arrayOfInt.length) ? 0 : arrayOfInt[paramInt];
  }
  
  public String getAttributeNamespace(int paramInt) {
    paramInt = getAttributeOffset(paramInt);
    paramInt = this.m_attributes[paramInt + 0];
    return (paramInt == -1) ? "" : this.m_strings.getString(paramInt);
  }
  
  public String getAttributePrefix(int paramInt) {
    paramInt = getAttributeOffset(paramInt);
    paramInt = this.m_attributes[paramInt + 0];
    paramInt = this.m_namespaces.findPrefix(paramInt);
    return (paramInt == -1) ? "" : this.m_strings.getString(paramInt);
  }
  
  public int getAttributeResourceValue(int paramInt1, int paramInt2) {
    paramInt1 = getAttributeOffset(paramInt1);
    int[] arrayOfInt = this.m_attributes;
    return (arrayOfInt[paramInt1 + 3] == 1) ? arrayOfInt[paramInt1 + 4] : paramInt2;
  }
  
  public int getAttributeResourceValue(String paramString1, String paramString2, int paramInt) {
    int i = findAttribute(paramString1, paramString2);
    return (i == -1) ? paramInt : getAttributeResourceValue(i, paramInt);
  }
  
  public String getAttributeType(int paramInt) { return "CDATA"; }
  
  public int getAttributeUnsignedIntValue(int paramInt1, int paramInt2) { return getAttributeIntValue(paramInt1, paramInt2); }
  
  public int getAttributeUnsignedIntValue(String paramString1, String paramString2, int paramInt) {
    int i = findAttribute(paramString1, paramString2);
    return (i == -1) ? paramInt : getAttributeUnsignedIntValue(i, paramInt);
  }
  
  public String getAttributeValue(int paramInt) {
    paramInt = getAttributeOffset(paramInt);
    int[] arrayOfInt = this.m_attributes;
    if (arrayOfInt[paramInt + 3] == 3) {
      paramInt = arrayOfInt[paramInt + 2];
      return this.m_strings.getString(paramInt);
    } 
    paramInt = arrayOfInt[paramInt + 4];
    return "";
  }
  
  public String getAttributeValue(String paramString1, String paramString2) {
    int i = findAttribute(paramString1, paramString2);
    return (i == -1) ? null : getAttributeValue(i);
  }
  
  public int getAttributeValueData(int paramInt) {
    paramInt = getAttributeOffset(paramInt);
    return this.m_attributes[paramInt + 4];
  }
  
  public int getAttributeValueType(int paramInt) {
    paramInt = getAttributeOffset(paramInt);
    return this.m_attributes[paramInt + 3];
  }
  
  public String getClassAttribute() {
    int i = this.m_classAttribute;
    if (i == -1)
      return null; 
    i = getAttributeOffset(i);
    i = this.m_attributes[i + 2];
    return this.m_strings.getString(i);
  }
  
  public int getColumnNumber() { return -1; }
  
  public int getDepth() { return this.m_namespaces.getDepth() - 1; }
  
  public int getEventType() throws XmlPullParserException { return this.m_event; }
  
  public boolean getFeature(String paramString) { return false; }
  
  public String getIdAttribute() {
    int i = this.m_idAttribute;
    if (i == -1)
      return null; 
    i = getAttributeOffset(i);
    i = this.m_attributes[i + 2];
    return this.m_strings.getString(i);
  }
  
  public int getIdAttributeResourceValue(int paramInt) {
    int i = this.m_idAttribute;
    if (i == -1)
      return paramInt; 
    i = getAttributeOffset(i);
    int[] arrayOfInt = this.m_attributes;
    return (arrayOfInt[i + 3] != 1) ? paramInt : arrayOfInt[i + 4];
  }
  
  public String getInputEncoding() { return null; }
  
  public int getLineNumber() { return this.m_lineNumber; }
  
  public String getName() {
    if (this.m_name != -1) {
      int i = this.m_event;
      if (i == 2 || i == 3)
        return this.m_strings.getString(this.m_name); 
    } 
    return null;
  }
  
  public String getNamespace() { return this.m_strings.getString(this.m_namespaceUri); }
  
  public String getNamespace(String paramString) { throw new RuntimeException("Method is not supported."); }
  
  public int getNamespaceCount(int paramInt) throws XmlPullParserException { return this.m_namespaces.getAccumulatedCount(paramInt); }
  
  public String getNamespacePrefix(int paramInt) throws XmlPullParserException {
    paramInt = this.m_namespaces.getPrefix(paramInt);
    return this.m_strings.getString(paramInt);
  }
  
  public String getNamespaceUri(int paramInt) throws XmlPullParserException {
    paramInt = this.m_namespaces.getUri(paramInt);
    return this.m_strings.getString(paramInt);
  }
  
  public String getPositionDescription() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("XML line #");
    stringBuilder.append(getLineNumber());
    return stringBuilder.toString();
  }
  
  public String getPrefix() {
    int i = this.m_namespaces.findPrefix(this.m_namespaceUri);
    return this.m_strings.getString(i);
  }
  
  public Object getProperty(String paramString) { return null; }
  
  final StringBlock getStrings() { return this.m_strings; }
  
  public int getStyleAttribute() {
    int i = this.m_styleAttribute;
    if (i == -1)
      return 0; 
    i = getAttributeOffset(i);
    return this.m_attributes[i + 4];
  }
  
  public String getText() {
    int i = this.m_name;
    return (i == -1 || this.m_event != 4) ? null : this.m_strings.getString(i);
  }
  
  public char[] getTextCharacters(int[] paramArrayOfint) {
    String str = getText();
    if (str == null)
      return null; 
    paramArrayOfint[0] = 0;
    paramArrayOfint[1] = str.length();
    char[] arrayOfChar = new char[str.length()];
    str.getChars(0, str.length(), arrayOfChar, 0);
    return arrayOfChar;
  }
  
  public boolean isAttributeDefault(int paramInt) { return false; }
  
  public boolean isEmptyElementTag() throws XmlPullParserException { return false; }
  
  public boolean isWhitespace() throws XmlPullParserException { return false; }
  
  public int next() throws XmlPullParserException, IOException {
    if (this.m_reader != null)
      try {
        doNext();
        return this.m_event;
      } catch (IOException iOException) {
        close();
        throw iOException;
      }  
    throw new XmlPullParserException("Parser is not opened.", this, null);
  }
  
  public int nextTag() throws XmlPullParserException, IOException {
    int j = next();
    int i = j;
    if (j == 4) {
      i = j;
      if (isWhitespace())
        i = next(); 
    } 
    if (i != 2) {
      if (i == 3)
        return i; 
      throw new XmlPullParserException("Expected start or end tag.", this, null);
    } 
    return i;
  }
  
  public String nextText() throws XmlPullParserException, IOException {
    if (getEventType() == 2) {
      int i = next();
      if (i == 4) {
        String str = getText();
        if (next() == 3)
          return str; 
        throw new XmlPullParserException("Event TEXT must be immediately followed by END_TAG.", this, null);
      } 
      if (i == 3)
        return ""; 
      throw new XmlPullParserException("Parser must be on START_TAG or TEXT to read text.", this, null);
    } 
    throw new XmlPullParserException("Parser must be on START_TAG to read next text.", this, null);
  }
  
  public int nextToken() throws XmlPullParserException, IOException { return next(); }
  
  public void open(InputStream paramInputStream) {
    close();
    if (paramInputStream != null)
      this.m_reader = new IntReader(paramInputStream, false); 
  }
  
  public void require(int paramInt, String paramString1, String paramString2) throws XmlPullParserException, IOException {
    if (paramInt == getEventType() && (paramString1 == null || paramString1.equals(getNamespace())) && (paramString2 == null || paramString2.equals(getName())))
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(TYPES[paramInt]);
    stringBuilder.append(" is expected.");
    throw new XmlPullParserException(stringBuilder.toString(), this, null);
  }
  
  public void setFeature(String paramString, boolean paramBoolean) throws XmlPullParserException { throw new XmlPullParserException("Method is not supported."); }
  
  public void setInput(InputStream paramInputStream, String paramString) throws XmlPullParserException { throw new XmlPullParserException("Method is not supported."); }
  
  public void setInput(Reader paramReader) throws XmlPullParserException { throw new XmlPullParserException("Method is not supported."); }
  
  public void setProperty(String paramString, Object paramObject) throws XmlPullParserException { throw new XmlPullParserException("Method is not supported."); }
  
  private static final class NamespaceStack {
    private int m_count;
    
    private int[] m_data = new int[32];
    
    private int m_dataLength;
    
    private int m_depth;
    
    private void ensureDataCapacity(int param1Int) {
      int[] arrayOfInt1 = this.m_data;
      int j = arrayOfInt1.length;
      int i = this.m_dataLength;
      j -= i;
      if (j > param1Int)
        return; 
      int[] arrayOfInt2 = new int[(arrayOfInt1.length + j) * 2];
      System.arraycopy(arrayOfInt1, 0, arrayOfInt2, 0, i);
      this.m_data = arrayOfInt2;
    }
    
    private final int find(int param1Int, boolean param1Boolean) {
      int i = this.m_dataLength;
      if (i == 0)
        return -1; 
      i--;
      int j;
      for (j = this.m_depth; j != 0; j--) {
        int k = this.m_data[i];
        i -= 2;
        while (k != 0) {
          if (param1Boolean) {
            int[] arrayOfInt = this.m_data;
            if (arrayOfInt[i] == param1Int)
              return arrayOfInt[i + 1]; 
          } else {
            int[] arrayOfInt = this.m_data;
            if (arrayOfInt[i + 1] == param1Int)
              return arrayOfInt[i]; 
          } 
          i -= 2;
          k--;
        } 
      } 
      return -1;
    }
    
    private final int get(int param1Int, boolean param1Boolean) {
      if (this.m_dataLength != 0) {
        if (param1Int < 0)
          return -1; 
        int i = 0;
        int k = this.m_depth;
        int j = param1Int;
        param1Int = k;
        while (param1Int != 0) {
          k = this.m_data[i];
          if (j >= k) {
            j -= k;
            i += k * 2 + 2;
            param1Int--;
            continue;
          } 
          i += j * 2 + 1;
          param1Int = i;
          if (!param1Boolean)
            param1Int = i + 1; 
          return this.m_data[param1Int];
        } 
      } 
      return -1;
    }
    
    public final void decreaseDepth() {
      int i = this.m_dataLength;
      if (i == 0)
        return; 
      int j = i - 1;
      int k = this.m_data[j];
      int m = k * 2;
      if (j - 1 - m == 0)
        return; 
      this.m_dataLength = i - m + 2;
      this.m_count -= k;
      this.m_depth--;
    }
    
    public final int findPrefix(int param1Int) { return find(param1Int, false); }
    
    public final int findUri(int param1Int) { return find(param1Int, true); }
    
    public final int getAccumulatedCount(int param1Int) {
      int i = this.m_dataLength;
      int k = 0;
      int j = 0;
      if (i != 0) {
        if (param1Int < 0)
          return 0; 
        k = this.m_depth;
        i = param1Int;
        if (param1Int > k)
          i = k; 
        k = 0;
        param1Int = j;
        j = k;
        while (true) {
          k = param1Int;
          if (i != 0) {
            k = this.m_data[j];
            param1Int += k;
            j += k * 2 + 2;
            i--;
            continue;
          } 
          break;
        } 
      } 
      return k;
    }
    
    public final int getCurrentCount() {
      int i = this.m_dataLength;
      return (i == 0) ? 0 : this.m_data[i - 1];
    }
    
    public final int getDepth() { return this.m_depth; }
    
    public final int getPrefix(int param1Int) { return get(param1Int, true); }
    
    public final int getTotalCount() { return this.m_count; }
    
    public final int getUri(int param1Int) { return get(param1Int, false); }
    
    public final void increaseDepth() {
      ensureDataCapacity(2);
      int i = this.m_dataLength;
      int[] arrayOfInt = this.m_data;
      arrayOfInt[i] = 0;
      arrayOfInt[i + 1] = 0;
      this.m_dataLength = i + 2;
      this.m_depth++;
    }
    
    public final boolean pop() {
      int i = this.m_dataLength;
      if (i == 0)
        return false; 
      int j = i - 1;
      int[] arrayOfInt = this.m_data;
      int k = arrayOfInt[j];
      if (k == 0)
        return false; 
      k--;
      j -= 2;
      arrayOfInt[j] = k;
      arrayOfInt[j - k * 2 + 1] = k;
      this.m_dataLength = i - 2;
      this.m_count--;
      return true;
    }
    
    public final boolean pop(int param1Int1, int param1Int2) {
      int i = this.m_dataLength;
      if (i == 0)
        return false; 
      int k = i - 1;
      int m = this.m_data[k];
      i = k - 2;
      int j = 0;
      while (j != m) {
        int[] arrayOfInt = this.m_data;
        if (arrayOfInt[i] != param1Int1 || arrayOfInt[i + 1] != param1Int2) {
          j++;
          i -= 2;
          continue;
        } 
        param1Int1 = m - 1;
        if (j == 0) {
          arrayOfInt[i] = param1Int1;
          arrayOfInt[i - param1Int1 * 2 + 1] = param1Int1;
        } else {
          arrayOfInt[k] = param1Int1;
          arrayOfInt[k - param1Int1 * 2 + 3] = param1Int1;
          System.arraycopy(arrayOfInt, i + 2, arrayOfInt, i, this.m_dataLength - i);
        } 
        this.m_dataLength -= 2;
        this.m_count--;
        return true;
      } 
      return false;
    }
    
    public final void push(int param1Int1, int param1Int2) {
      if (this.m_depth == 0)
        increaseDepth(); 
      ensureDataCapacity(2);
      int i = this.m_dataLength;
      int j = i - 1;
      int[] arrayOfInt = this.m_data;
      int k = arrayOfInt[j];
      int m = k + 1;
      arrayOfInt[j - 1 - k * 2] = m;
      arrayOfInt[j] = param1Int1;
      arrayOfInt[j + 1] = param1Int2;
      arrayOfInt[j + 2] = m;
      this.m_dataLength = i + 2;
      this.m_count++;
    }
    
    public final void reset() {
      this.m_dataLength = 0;
      this.m_count = 0;
      this.m_depth = 0;
    }
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\android\content\res\AXmlResourceParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */