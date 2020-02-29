package com.sk.spatch.xmltool.v1;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public interface XmlSerializer {
  XmlSerializer attribute(String paramString1, String paramString2, String paramString3) throws IOException, IllegalArgumentException, IllegalStateException;
  
  void cdsect(String paramString) throws IOException, IllegalArgumentException, IllegalStateException;
  
  void comment(String paramString) throws IOException, IllegalArgumentException, IllegalStateException;
  
  void docdecl(String paramString) throws IOException, IllegalArgumentException, IllegalStateException;
  
  void endDocument() throws IOException, IllegalArgumentException, IllegalStateException;
  
  XmlSerializer endTag(String paramString1, String paramString2) throws IOException, IllegalArgumentException, IllegalStateException;
  
  void entityRef(String paramString) throws IOException, IllegalArgumentException, IllegalStateException;
  
  void flush() throws IOException;
  
  int getDepth();
  
  boolean getFeature(String paramString);
  
  String getName();
  
  String getNamespace();
  
  String getPrefix(String paramString, boolean paramBoolean) throws IllegalArgumentException;
  
  Object getProperty(String paramString);
  
  void ignorableWhitespace(String paramString) throws IOException, IllegalArgumentException, IllegalStateException;
  
  void processingInstruction(String paramString) throws IOException, IllegalArgumentException, IllegalStateException;
  
  void setFeature(String paramString, boolean paramBoolean) throws IllegalArgumentException, IllegalStateException;
  
  void setOutput(OutputStream paramOutputStream, String paramString) throws IOException, IllegalArgumentException, IllegalStateException;
  
  void setOutput(Writer paramWriter) throws IOException, IllegalArgumentException, IllegalStateException;
  
  void setPrefix(String paramString1, String paramString2) throws IOException, IllegalArgumentException, IllegalStateException;
  
  void setProperty(String paramString, Object paramObject) throws IllegalArgumentException, IllegalStateException;
  
  void startDocument(String paramString, Boolean paramBoolean) throws IOException, IllegalArgumentException, IllegalStateException;
  
  XmlSerializer startTag(String paramString1, String paramString2) throws IOException, IllegalArgumentException, IllegalStateException;
  
  XmlSerializer text(String paramString) throws IOException, IllegalArgumentException, IllegalStateException;
  
  XmlSerializer text(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException, IllegalArgumentException, IllegalStateException;
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\v1\XmlSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */