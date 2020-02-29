package com.sk.spatch.kt.srcs.util;

import com.sk.spatch.xmltool.android.content.res.AXmlResourceParser;
import com.sk.spatch.xmltool.v1.XmlPullParserException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

public class ManifestParser {
  public static Pair parseManifestFile(String paramString) {
    AXmlResourceParser aXmlResourceParser = new AXmlResourceParser();
    Object object3 = new File(paramString);
    boolean bool = object3.exists();
    Object object4 = null;
    Object object2 = null;
    if (!bool) {
      object2 = System.out;
      object3 = new StringBuilder();
      object3.append(" manifest file not exist!!! filePath -> ");
      object3.append(paramString);
      object2.println(object3.toString());
      return null;
    } 
    try {
      aXmlResourceParser.open(new FileInputStream((File)object3));
      paramString = null;
      label52: while (true) {
        object4 = object2;
        object3 = paramString;
        Object object = object2;
        String str = paramString;
        try {
          int i = aXmlResourceParser.next();
          if (i == 1)
            break; 
          if (i == 2) {
            object4 = object2;
            object3 = paramString;
            object = object2;
            str = paramString;
            int j = aXmlResourceParser.getAttributeCount();
            i = 0;
            String str1 = paramString;
            PrintStream printStream = (PrintStream)object2;
            while (true) {
              object2 = printStream;
              paramString = str1;
              if (i < j) {
                object4 = printStream;
                object3 = str1;
                object = printStream;
                str = str1;
                String str3 = aXmlResourceParser.getAttributeName(i);
                object4 = printStream;
                object3 = str1;
                object = printStream;
                str = str1;
                String str4 = aXmlResourceParser.getName();
                object1 = printStream;
                object4 = printStream;
                object3 = str1;
                object = printStream;
                str = str1;
                if ("manifest".equals(str4)) {
                  object1 = printStream;
                  object4 = printStream;
                  object3 = str1;
                  object = printStream;
                  str = str1;
                  if ("package".equals(str3)) {
                    object4 = printStream;
                    object3 = str1;
                    object = printStream;
                    str = str1;
                    object1 = aXmlResourceParser.getAttributeValue(i);
                  } 
                } 
                String str2 = str1;
                Object object5 = object1;
                object3 = str1;
                object = object1;
                str = str1;
                if ("application".equals(str4)) {
                  str2 = str1;
                  object5 = object1;
                  object3 = str1;
                  object = object1;
                  str = str1;
                  if ("name".equals(str3)) {
                    object5 = object1;
                    object3 = str1;
                    object = object1;
                    str = str1;
                    str2 = aXmlResourceParser.getAttributeValue(i);
                  } 
                } 
                if (object1 != null) {
                  object5 = object1;
                  object3 = str2;
                  object = object1;
                  str = str2;
                  if (object1.length() > 0 && str2 != null) {
                    object5 = object1;
                    object3 = str2;
                    object = object1;
                    str = str2;
                    if (str2.length() > 0) {
                      object5 = object1;
                      object3 = str2;
                      object = object1;
                      str = str2;
                      return new Pair((String)object1, str2);
                    } 
                  } 
                } 
                i++;
                Object object6 = object1;
                str1 = str2;
                continue;
              } 
              continue label52;
            } 
          } 
        } catch (XmlPullParserException null) {
          object4 = object;
          object3 = str;
          // Byte code: goto -> 489
        } catch (IOException null) {
          // Byte code: goto -> 489
        } 
      } 
    } catch (XmlPullParserException xmlPullParserException) {
      object3 = null;
      xmlPullParserException.printStackTrace();
      object2 = System.out;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("parseManifestFile failed, reason --> ");
      stringBuilder.append(xmlPullParserException.getMessage());
      object2.println(stringBuilder.toString());
      object1 = object3;
      object2 = object4;
    } catch (IOException object1) {}
    return new Pair((String)object2, (String)object1);
  }
  
  public static class Pair {
    public String applicationName;
    
    public String packageName;
    
    public Pair(String param1String1, String param1String2) {
      this.packageName = param1String1;
      this.applicationName = param1String2;
    }
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\kt\src\\util\ManifestParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */