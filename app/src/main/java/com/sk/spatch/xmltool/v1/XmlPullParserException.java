package com.sk.spatch.xmltool.v1;

import java.io.PrintStream;

public class XmlPullParserException extends Exception {
  protected int column = -1;
  
  protected Throwable detail;
  
  protected int row = -1;
  
  public XmlPullParserException(String paramString) { super(paramString); }
  
  public XmlPullParserException(String paramString, XmlPullParser paramXmlPullParser, Throwable paramThrowable) {
    super(stringBuilder.toString());
    if (paramXmlPullParser != null) {
      this.row = paramXmlPullParser.getLineNumber();
      this.column = paramXmlPullParser.getColumnNumber();
    } 
    this.detail = paramThrowable;
  }
  
  public int getColumnNumber() { return this.column; }
  
  public Throwable getDetail() { return this.detail; }
  
  public int getLineNumber() { return this.row; }
  
  public void printStackTrace() {
    if (this.detail == null) {
      super.printStackTrace();
      return;
    } 
    synchronized (System.err) {
      PrintStream printStream = System.err;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(getMessage());
      stringBuilder.append("; nested exception is:");
      printStream.println(stringBuilder.toString());
      this.detail.printStackTrace();
      return;
    } 
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\v1\XmlPullParserException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */