package com.sk.spatch.xmltool.decode.encoding;

public class ZipEncodingHelper {
  static final String UTF8 = "UTF8";
  
  public static final ZipEncoding UTF8_ZIP_ENCODING = new FallbackZipEncoding("UTF-8");
  
  private static final String UTF_DASH_8 = "utf-8";
  
  public static ZipEncoding getZipEncoding(String paramString) { return new FallbackZipEncoding(paramString); }
  
  public static boolean isUTF8(String paramString) {
    String str = paramString;
    if (paramString == null)
      str = System.getProperty("file.encoding"); 
    return ("UTF8".equalsIgnoreCase(str) || "utf-8".equalsIgnoreCase(str));
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\sk\spatch\xmltool\decode\encoding\ZipEncodingHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */